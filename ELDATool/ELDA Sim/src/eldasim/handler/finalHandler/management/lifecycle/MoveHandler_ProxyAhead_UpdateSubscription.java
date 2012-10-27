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
import jaf.Timer;

import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.management.lifecycle.ELDAEventExecute;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMoveRequest;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.agent.SimELDA;
import eldasim.lowlevelevent.AckProxyCreation;
import eldasim.lowlevelevent.MovedAgent;
import eldasim.lowlevelevent.ReSubscribe;
import eldasim.lowlevelevent.RequestProxyCreation;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class MoveHandler_ProxyAhead_UpdateSubscription extends MoveHandler_ProxyAhead {

	public MoveHandler_ProxyAhead_UpdateSubscription(AgentServer as, Vector<Class> v) {
		super(as, v);
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
		} else if (msg.getType() == Msg.READY_TO) {
			if (msg.getCargo() instanceof ELDAEventMove)
				handlingReadyTo(msg);

		} else if (msg.getType() == Msg.REMOTE_REQUEST || msg.getType() == Msg.REMOTE_NOTIFICATION)
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

	@Override
	protected void handlingRequestProxyCreation(Msg msg) {
		super.handlingRequestProxyCreation(msg);
		RequestProxyCreation evt = (RequestProxyCreation) msg.getCargo();
		ELDAId idIncomingAgent = evt.getSource();
		// Updating eventual subscription
		idIncomingAgent.setCurrLocation(AS.getName().toString());
		ReSubscribe reSubscribe = new ReSubscribe(idIncomingAgent);
		Msg reSubMsg = new Msg(AS.getName(), MASSimulation.getPublish_subscribe().getName(), Msg.REMOTE_REQUEST, reSubscribe);
		VirtualNetwork.send(reSubMsg);
	}

	@Override
	protected void sendMoveMsg(Msg msg) {
		new Timer().setMaxPriority(msg);
	}
}
