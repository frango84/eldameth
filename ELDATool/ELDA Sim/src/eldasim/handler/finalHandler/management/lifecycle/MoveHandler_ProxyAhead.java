/*****************************************************************
ELDATool
Copyright (C) 2012 G. Fortino

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation;
version 2.1 of the License.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*****************************************************************/

package eldasim.handler.finalHandler.management.lifecycle;

import jaf.Message;

import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.management.lifecycle.ELDAEventExecute;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMoveRequest;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.agent.SimELDA;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.lowlevelevent.AckProxyCreation;
import eldasim.lowlevelevent.MovedAgent;
import eldasim.lowlevelevent.RequestProxyCreation;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class MoveHandler_ProxyAhead extends AbstractFinalEventHandler {

	public MoveHandler_ProxyAhead(AgentServer as, Vector<Class> v) {
		super(as, "MoveHandler", v);
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		Msg msg = (Msg) m;

		if (msg.getType() == Msg.ELDAFRAMEWORK_EVENT) {
			if (msg.getCargo() instanceof ELDAEventMoveRequest) { // migration request
				ELDAEventMoveRequest evt = (ELDAEventMoveRequest) msg.getCargo();
				if (!evt.getDestination().equals(evt.getTarget().getCurrLocation())) {
					ELDAEventMove moveEvt = new ELDAEventMove(evt.getTarget(), evt.getDestination(), evt.getReactivationEvent());
					Msg msgx = new Msg(this.predecessor, Msg.ELDAFRAMEWORK_EVENT, moveEvt);
					// Modified in 17/06/08
					sendMoveMsg(msgx);
				} else {
					System.err.println("Current location is the same of destination.");
					if (evt.getReactivationEvent() != null) {
						Msg reactivationMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, evt.getReactivationEvent());
						sendMsg(reactivationMsg);
					}
				}
			}
		} else if (msg.getType() == Msg.READY_TO)
			handlingReadyTo(msg);
		else if (msg.getType() == Msg.REMOTE_REQUEST || msg.getType() == Msg.REMOTE_NOTIFICATION)
			if (msg.getCargo() instanceof RequestProxyCreation)
				handlingRequestProxyCreation(msg);

			else if (msg.getCargo() instanceof MovedAgent) { // received agent
				MovedAgent evt = (MovedAgent) msg.getCargo();
				SimELDA simELDA = evt.getSimELDA();
				Object oldLocation = new Object();
				oldLocation = simELDA.getELDAId().getCurrLocation();

				// Recovering mail
				Proxy px = (Proxy) AS.lookUp(simELDA.getELDAId());
				Vector mail = px.getMailBox();

				// Unregistering the proxy
				AS.unregister(simELDA.getELDAId());

				// Registering the agent
				AS.registerAgent(simELDA);

				// Executing the agent
				ELDAEventExecute execute = new ELDAEventExecute(evt.getTarget());
				Msg eventMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, execute);
				sendMsg(eventMsg);

				// Sending reactivation event, if any
				if (evt.getReactivationEvent() != null) {
					Msg reactivationMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, evt.getReactivationEvent());
					sendMsg(reactivationMsg);
				}

				for (int i = 0; i < mail.size(); i++) {
					eventMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, mail.get(i));
					sendMsg(eventMsg);
				}
				px.resetMailBox();
			} else if (msg.getCargo() instanceof AckProxyCreation) {
				AckProxyCreation ack = (AckProxyCreation) msg.getCargo();
				Proxy px = (Proxy) AS.lookUp(ack.getSource());
				Vector mail = px.getMailBox();
				for (int i = 0; i < mail.size(); i++) {
					Msg eventMsg = new Msg(AS.getName(), ack.getSource().getCurrLocation(), Msg.ELDAFRAMEWORK_EVENT, mail.get(i));
					VirtualNetwork.send(eventMsg);
				}
				if (px != null)
					px.setForwardingOn();
				px.resetMailBox();

			}
	}

	protected void handlingReadyTo(Msg msg) {
		if (msg.getCargo() instanceof ELDAEventMove) {// perform migration
			ELDAEventMove evt = (ELDAEventMove) msg.getCargo();
			ELDAId idAgentToMigrate = evt.getTarget();
			SimELDA agentToMigrate = (SimELDA) AS.lookUp(idAgentToMigrate);

			if (agentToMigrate != null) {

				// Requesting the destination proxy creation (non active)
				RequestProxyCreation requestProxyCreation = new RequestProxyCreation(idAgentToMigrate);
				Msg requestMsg = new Msg(AS.getName(), evt.getDestination().toString(), Msg.REMOTE_REQUEST, requestProxyCreation);
				VirtualNetwork.send(requestMsg);

				// Unregistering the migrating agent
				AS.unregister(idAgentToMigrate);

				// Registering the local proxy (non active, temp false)
				Proxy proxy = new Proxy(evt.getDestination().toString(), false, false);
				AS.registerProxy(idAgentToMigrate, proxy);

				// Sending the agent
				MovedAgent movedAgent = new MovedAgent(idAgentToMigrate, agentToMigrate, evt.getReactivationEvent());
				Msg migrationMsg = new Msg(AS.getName(), evt.getDestination().toString(), Msg.REMOTE_REQUEST, movedAgent);
				VirtualNetwork.send(migrationMsg);
			}
		}
	}

	protected void handlingRequestProxyCreation(Msg msg) {
		RequestProxyCreation evt = (RequestProxyCreation) msg.getCargo();
		ELDAId idIncomingAgent = evt.getSource();

		// Register local proxy (non active, temp true
		Proxy px = (Proxy) AS.lookUp(idIncomingAgent);
		if (px == null) {
			px = new Proxy(null, false, true);
			AS.registerProxy(idIncomingAgent, px);
		} else
			px.setForwardingOff();
		// Sending the ack
		AckProxyCreation ack = new AckProxyCreation(idIncomingAgent);
		Msg ackMsg = new Msg(AS.getName(), msg.getSourceURL(), Msg.REMOTE_NOTIFICATION, ack);
		VirtualNetwork.send(ackMsg);
	}

	protected void sendMoveMsg(Msg msg) {
		sendMsg(msg);
	}
}
