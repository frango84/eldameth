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

package eldasim.handler.finalHandler.management.timer;

import jaf.Message;
import jaf.Timer;

import java.util.Vector;

import eldaframework.eldaevent.management.timer.ELDAEventCreateTimer;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.setup.MASSimulation;

public class TimerHandler extends AbstractFinalEventHandler {

	public TimerHandler(AgentServer as, Vector<Class> v) {
		super(as, "TimerHandler", v);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		Msg msg = (Msg) m;
		// System.err.println(msg);
		if (msg.getType() == Msg.REMOTE_REQUEST) {

		} else if (msg.getType() == Msg.REMOTE_NOTIFICATION) {

		} else if (msg.getType() == Msg.ELDAFRAMEWORK_EVENT)
			if (msg.getCargo() instanceof ELDAEventCreateTimer) { // richiesta di
				// clonazione
				// ricevuta da un
				// agente locale
				ELDAEventCreateTimer evt = (ELDAEventCreateTimer) msg.getCargo();
				Msg timeout = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt.getBackEventT());
				new Timer().set(timeout, evt.getDelay());
			}
	}
}
