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

import java.util.Vector;

import eldaframework.eldaevent.coordination.direct.ELDAEventMSG;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class AsyncMPHandler_ProxyAhead extends AsyncMPHandler {

	public AsyncMPHandler_ProxyAhead(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handlingProxy(Object o, ELDAEventMSG evt_r) {
		Proxy px = (Proxy) o;
		Msg msgAnswer = null;
		// Se il px è temp (l'agente deve ancora arrivare) uso la home loc
		if (px.isTemporary()) {
			if (MASSimulation.debug)
				System.out.println("Message sent to " + predecessor.getName());
			msgAnswer = new Msg(AS.getName(), evt_r.getTarget().getHomeLocation(), Msg.ELDAFRAMEWORK_EVENT, evt_r);
			VirtualNetwork.send(msgAnswer);
		} else // Fw il msg se il px è attivo
		if (px.isActive()) {
			msgAnswer = new Msg(AS.getName(), px.getForward(), Msg.ELDAFRAMEWORK_EVENT, evt_r);
			if (MASSimulation.debug)
				System.out.println("Message sent to " + px.getForward());
			VirtualNetwork.send(msgAnswer);
		}
		// Store il msg se il px è non attivo
		else {
			msgAnswer = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt_r);
			if (MASSimulation.debug)
				System.out.println("Message sent to " + predecessor.getName());
			sendMsg(msgAnswer);
		}
	}
}
