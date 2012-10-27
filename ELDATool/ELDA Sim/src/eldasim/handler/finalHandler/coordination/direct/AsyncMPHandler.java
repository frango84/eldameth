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

package eldasim.handler.finalHandler.coordination.direct;

import jaf.Message;

import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.coordination.direct.ELDAEventMSG;
import eldaframework.eldaevent.coordination.direct.ELDAEventMSGRequest;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class AsyncMPHandler extends AbstractFinalEventHandler {

	public AsyncMPHandler(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		Msg msg = (Msg) m;
		ELDAEventMSGRequest evt = (ELDAEventMSGRequest) msg.getCargo();
		ELDAEventMSG evt_r = evt.getMSG();
		ELDAId target = new ELDAId(evt_r.getTarget());
		Object o = AS.lookUp(target);

		if (o == null) {
			Msg msgAnswer = new Msg(AS.getName(), target.getHomeLocation(), Msg.ELDAFRAMEWORK_EVENT, evt_r);
			VirtualNetwork.send(msgAnswer);
		} else if (o instanceof Proxy)
			handlingProxy(o, evt_r);
		else { // Active Agent or Freezed Agent
			Msg msgAnswer = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt_r);
			if (MASSimulation.debug)
				System.out.println("Message sent to " + predecessor.getName());
			sendMsg(msgAnswer);
		}
	}

	protected void handlingProxy(Object o, ELDAEventMSG evt_r) {
		Proxy px = (Proxy) o;
		Msg msgAnswer = new Msg(AS.getName(), px.getForward(), Msg.ELDAFRAMEWORK_EVENT, evt_r);
		if (MASSimulation.debug)
			System.out.println("Message sent to " + px.getForward());
		VirtualNetwork.send(msgAnswer);
	}
}
