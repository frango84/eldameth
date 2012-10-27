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

package eldasim.handler;

import jaf.Message;
import jaf.Timer;

import java.util.Iterator;
import java.util.Vector;

import eldaframework.eldaevent.ELDAEvent;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMoveRequest;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.setup.MASSimulation;

public abstract class EventRouter extends EventHandler {

	public EventRouter(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		ELDAEvent evt = (ELDAEvent) ((Msg) m).getCargo();
		EventHandler target = null;
		Msg msg = (Msg) m;
		if (msg.type == Msg.ELDAFRAMEWORK_EVENT) {
			if (evt.isOut()) { // ROUTING OUT
				if (!successorMap.isEmpty()) {
					target = getMessageTarget(evt);
					m.receiver = target;
					if (MASSimulation.debug)
						System.out.println("Message sent to " + m.receiver.getName());
					// Modified in 19/06/08
					if (evt instanceof ELDAEventMoveRequest)
						new Timer().setMaxPriority(m);
					else
						sendMsg(m);
				} else
					System.out.println("No available successor");
			} else if (predecessor != null) {// ROUTING IN
				m.receiver = predecessor;
				if (MASSimulation.debug)
					System.out.println("Message sent to " + predecessor.getName());
				if (evt instanceof ELDAEventMove)
					new Timer().setMaxPriority(m);
				else
					sendMsg(m);
			} else
				System.out.println("No available predecessor");
		} else if (!successorMap.isEmpty()) {// MSG.type=READY_TO
			target = getMessageTarget(evt);
			m.receiver = target;
			if (MASSimulation.debug)
				System.out.println("Message sent to " + m.receiver.getName());
			if (evt instanceof ELDAEventMove)
				new Timer().setMaxPriority(m);
			else
				sendMsg(m);
		} else
			System.out.println("No available successor");
	}

	protected EventHandler getMessageTarget(ELDAEvent evt) {
		EventHandler target = null;
		target = successorMap.get(evt);
		/*
		 * se non ci sono entry direttamente riconosciute verifico che ne esista una
		 * che sia una generalizzazione
		 */
		if (target == null) {
			Iterator iterator = successorMap.keySet().iterator();
			while (iterator.hasNext()) {
				Class entryKey = (Class) iterator.next();
				if (entryKey.isInstance(evt))
					target = successorMap.get(entryKey);
			}
		}
		return target;
	}
}
