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

import jaf.Actor;
import jaf.Message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import eldasim.AgentServer;

public abstract class EventHandler extends jaf.Actor {

	protected HashMap<Class, EventHandler> successorMap = new HashMap<Class, EventHandler>();
	protected Actor predecessor;
	private Vector handledEvent = new Vector<Class>();

	protected AgentServer AS;

	public EventHandler(AgentServer as, String name, Vector<Class> v) {
		this.AS = as;
		this.name = name;
		if (v != null)
			for (int i = 0; i < v.size(); i++)
				handledEvent.add(v.get(i));
	}

	public void addSuccessor(EventHandler eh) {
		Iterator eventList = eh.handledEvent.iterator();
		Class eventCode;
		while (eventList.hasNext()) {
			eventCode = ((Class) eventList.next());
			successorMap.put(eventCode, eh);
		}
		setThisHandlerAsPredecessorOf(eh);
	}

	protected void setThisHandlerAsPredecessorOf(EventHandler eh) {
		eh.predecessor = this;
	}

	public void sendMsg(Message m) {
		super.send(m);

	}

	public void setPredecessor(Actor predecessor) {
		this.predecessor = predecessor;
	}

	public AgentServer getAS() {
		return AS;
	}

}
