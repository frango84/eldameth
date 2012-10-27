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
import jaf.VirtualClock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.management.lifecycle.ELDAEventClone;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCloneNotify;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.agent.SimELDA;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.lowlevelevent.RemoteClone;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class CloneHandler extends AbstractFinalEventHandler {

	public CloneHandler(AgentServer as, Vector<Class> v) {
		super(as, "CloneHandler", v);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName() + " when virtual clock was " + VirtualClock.getSimulationTime());
		Msg msg = (Msg) m;
		if (msg.getType() == Msg.DELAYED_ELDAFRAMEWORK_EVENT) {
			if (msg.getCargo() instanceof RemoteClone) {
				RemoteClone remote = (RemoteClone) msg.getCargo();
				ELDAId clonedId = cloneAgentLocally(remote.getEvt(), remote.getSimELDA());
				if (remote.getEvt().getNotify()) {
					remote.getEvt().getBackEvent().setClonedELDAIds(clonedId);
					Msg msgResponse = new Msg(this.AS.getName(), msg.getSourceURL(), Msg.REMOTE_NOTIFICATION, remote.getEvt().getBackEvent());
					if (MASSimulation.debug)
						System.out.println("Message sent to " + msg.getSourceURL());
					VirtualNetwork.send(msgResponse);
				}
			} else if (msg.getCargo() instanceof ELDAEventClone) {
				ELDAEventClone evt = (ELDAEventClone) msg.getCargo();
				SimELDA simELDA = ((SimELDA) AS.lookUp(evt.getSource()));
				ELDAId clonedId = cloneAgentLocally(evt, simELDA);
				if (evt.getNotify()) {
					evt.getBackEvent().setClonedELDAIds(clonedId);
					Msg msgResponse = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt.getBackEvent());
					if (MASSimulation.debug)
						System.out.println("Message sent to " + predecessor.getName());
					sendMsg(msgResponse);
				}
			}
		} else if (msg.getType() == Msg.REMOTE_REQUEST) {
			if (msg.getCargo() instanceof RemoteClone) {// richiesta di clonazione
				// ricevuta da un agente su un
				// sito remoto
				msg.setType(Msg.DELAYED_ELDAFRAMEWORK_EVENT);
				new Timer().set(msg, 100);
			}
		} else if (msg.getType() == Msg.REMOTE_NOTIFICATION) {
			if (msg.getCargo() instanceof ELDAEventCloneNotify) {// notifica remota di
				// avvenuta
				// creazione
				ELDAEventCloneNotify evt = (ELDAEventCloneNotify) msg.getCargo();
				sendMsg(new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt));
			}
		} else if (msg.getType() == Msg.ELDAFRAMEWORK_EVENT)
			if (msg.getCargo() instanceof ELDAEventClone) { // richiesta di clonazione
				// ricevuta da un agente
				// locale
				ELDAEventClone evt = (ELDAEventClone) msg.getCargo();
				if (evt.getLocation() == null) {// richiesta di clonazione locale
					// Auto-sending
					msg.setType(Msg.DELAYED_ELDAFRAMEWORK_EVENT);
					new Timer().set(msg, 100);
				} else {// richiesta di clonazione da mandare ad un sito remoto
					SimELDA simELDA = ((SimELDA) AS.lookUp(evt.getSource()));
					RemoteClone remoteEvent = new RemoteClone(evt, simELDA);
					Msg remoteClone = new Msg(AS.getName(), evt.getLocation().toString(), Msg.REMOTE_REQUEST, remoteEvent);
					VirtualNetwork.send(remoteClone);
				}
			}
	}

	private ELDAId cloneAgentLocally(ELDAEventClone evt, SimELDA original) {

		ELDAId id = new ELDAId(evt.getSource().getHLName() + "_clone", AS.getName());
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		try {
			ObjectOutputStream o = new ObjectOutputStream(buf);
			o.writeObject(original);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buf.toByteArray()));
			try {
				SimELDA cloned = (SimELDA) in.readObject();
				cloned.setELDAId(id);

				AS.registerAgent(cloned);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
