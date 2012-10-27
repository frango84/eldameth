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
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuit;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuitRequest;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.network.VirtualNetwork;

public class QuitHandler extends AbstractFinalEventHandler {

	public QuitHandler(AgentServer as, Vector<Class> v) {
		super(as, "QuitHandler", v);
	}

	@Override
	protected void handler(Message m) {
		// Modified by Samuele on 13-07-09
		Msg msg = (Msg) m;
		if (msg.getType() == Msg.READY_TO) {
			if (msg.getCargo() instanceof ELDAEventQuit) {// perform quit
				ELDAEventQuit evt = (ELDAEventQuit) msg.getCargo();
				ELDAId id = evt.getTarget();
				AS.unregister(id);
			}
		} else if (msg.getType() == Msg.ELDAFRAMEWORK_EVENT)
			if (msg.getCargo() instanceof ELDAEventQuitRequest) {
				ELDAEventQuitRequest evt = (ELDAEventQuitRequest) msg.getCargo();
				ELDAId id = evt.getTarget();
				ELDAEventQuit quitEvt = new ELDAEventQuit(id);
				Msg quitMsg = null;
				if (this.AS.getName().equals(quitEvt.getTarget().getCurrLocation())) {
					quitMsg = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, quitEvt);
					sendMsg(quitMsg);
				} else {
					quitMsg = new Msg(this.AS.getName(), quitEvt.getTarget().getCurrLocation(), Msg.ELDAFRAMEWORK_EVENT, quitEvt);
					VirtualNetwork.send(quitMsg);
				}
			}
	}
}
