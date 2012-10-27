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
import eldasim.lowlevelevent.MovedAgent;
import eldasim.lowlevelevent.RequestMailBox;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class MoveHandler extends AbstractFinalEventHandler {

	public MoveHandler(AgentServer as, Vector<Class> v) {
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
					sendMsg(msgx);
				} else {
					System.err.println("Current location is the same of destination.");
					if (evt.getReactivationEvent() != null) {
						Msg reactivationMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, evt.getReactivationEvent());
						sendMsg(reactivationMsg);
					}
				}
			}
		} else if (msg.getType() == Msg.READY_TO) {
			if (msg.getCargo() instanceof ELDAEventMove) {// perform migration
				ELDAEventMove evt = (ELDAEventMove) msg.getCargo();
				ELDAId idAgentToMigrate = evt.getTarget();
				SimELDA agentToMigrate = (SimELDA) AS.lookUp(idAgentToMigrate);
				AS.unregister(idAgentToMigrate);
				Proxy proxy = new Proxy(evt.getDestination().toString(), false, false);
				AS.registerProxy(idAgentToMigrate, proxy);
				MovedAgent movedAgent = new MovedAgent(idAgentToMigrate, agentToMigrate, evt.getReactivationEvent());
				Msg migrationMsg = new Msg(AS.getName(), evt.getDestination().toString(), Msg.REMOTE_REQUEST, movedAgent);
				VirtualNetwork.send(migrationMsg);
			}
		} else if (msg.getType() == Msg.REMOTE_REQUEST || msg.getType() == Msg.REMOTE_NOTIFICATION)
			if (msg.getCargo() instanceof MovedAgent) { // received agent
				MovedAgent evt = (MovedAgent) msg.getCargo();
				SimELDA simELDA = evt.getSimELDA();
				Object oldLocation = new Object();
				oldLocation = simELDA.getELDAId().getCurrLocation();

				AS.registerAgent(simELDA);

				ELDAEventExecute execute = new ELDAEventExecute(evt.getTarget());
				Msg eventMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, execute);
				sendMsg(eventMsg);
				if (evt.getReactivationEvent() != null) {
					Msg reactivationMsg = new Msg(AS, Msg.ELDAFRAMEWORK_EVENT, evt.getReactivationEvent());
					sendMsg(reactivationMsg);
				}
				RequestMailBox request = new RequestMailBox(evt.getTarget());
				Msg requestMsg = new Msg(AS.getName(), oldLocation.toString(), Msg.REMOTE_REQUEST, request);
				VirtualNetwork.send(requestMsg);
			} else if (msg.getCargo() instanceof RequestMailBox) {
				RequestMailBox evt = (RequestMailBox) msg.getCargo();
				Vector mailBox = new Vector();
				mailBox = ((Proxy) AS.lookUp(evt.getTarget())).getMailBox();
				for (int i = 0; i < mailBox.size(); i++) {
					Msg mailMsg = new Msg(AS.getName(), msg.getSourceURL(), Msg.ELDAFRAMEWORK_EVENT, mailBox.get(i));
					VirtualNetwork.send(mailMsg);
				}
				((Proxy) AS.lookUp(evt.getTarget())).resetMailBox();
				((Proxy) AS.lookUp(evt.getTarget())).setForwardingOn();
			}
	}
}
