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

package eldasim;

import jaf.Message;
import jaf.Timer;

import java.sql.SQLException;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.components.ITLH;
import eldaframework.eldaevent.ELDAEvent;
import eldaframework.eldaevent.management.ELDAEventManagement;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMoveRequest;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuit;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuitRequest;
import eldaframework.eldaevent.management.timer.ELDAEventCreateTimer;
import eldaframework.eldaevent.management.timer.ELDAEventTimeoutNotify;
import eldaframework.eldaevent.simulated.ActionDuration;
import eldaframework.eldaevent.simulated.SimulationEvent;
import eldasim.agent.SimELDA;
import eldasim.handler.EventRouter;
import eldasim.handler.PrimaryEventHandler;
import eldasim.lowlevelevent.LowLevelEvent;
import eldasim.network.Proxy;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;
import eldasim.statistics.ELDAEventTracing;

class TLH extends EventRouter implements ITLH {

	public Vector<String> quittingAgent = new Vector<String>();

	public TLH(AgentServer as, String name, Vector<PrimaryEventHandler> v) {
		super(as, name, null);
		if (v != null)
			for (int i = 0; i < v.size(); i++)
				addPrimaryEventHandler(v.get(i));
	}

	private PrimaryEventHandler getPrimaryEventHandler(ELDAEvent evt) {
		return (PrimaryEventHandler) getMessageTarget(evt);
	}

	private void addPrimaryEventHandler(PrimaryEventHandler eh) {
		addSuccessor(eh);
		setASasPredessor(eh);
	}

	private void setASasPredessor(PrimaryEventHandler eh) {
		eh.setPredecessor(AS);
	}

	@Override
	protected void handler(Message m) {
		// mai chiamato dal motore di simulazione

	}

	public void manageMessage(Msg msg) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		if (msg.type == Msg.REMOTE_NOTIFICATION || msg.type == Msg.REMOTE_REQUEST) {
			ELDAEvent evt = (ELDAEvent) msg.getCargo();
			msg.receiver = getPrimaryEventHandler(evt);
			sendMsg(msg);
		}

		if (MASSimulation.traceELDAEvent) {// Modified in 29-08-08 Not Tested
			Class[] interfaces = msg.getCargo().getClass().getInterfaces();
			for (int i = 0; i < interfaces.length; i++)
				if (interfaces[i] == LowLevelEvent.class)
					traceLowLevelEvent(msg, "Handler");
		}
	}

	public void deliverToAgent(ELDAEvent evt) {
		ELDAId target = evt.getTarget();

		// Quitting agents cannot receive events which are different from Quit
		if (!quittingAgent.contains(target.getUID()) || evt instanceof ELDAEventQuit) {
			if (MASSimulation.debug)
				System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
						+ this.AS.getName() + " target " + target + " cargo " + evt.getClass());

			Object o = AS.lookUp(target);
			// If the agent has been freezed to simulate action duration
			if (o instanceof FreezedAgent) {
				FreezedAgent freezedAgent = (FreezedAgent) o;
				if (evt instanceof ELDAEventTimeoutNotify && freezedAgent.unfreezeEvent.equals(evt)) {
					freezedAgent.flushMemory();
					this.AS.registerAgent(freezedAgent.getRequester());
					if (MASSimulation.traceELDAEvent)
						traceDeliveredEvent(evt, "EndAction");
				} else {
					freezedAgent.insertIntoMemory(evt);
					if (MASSimulation.traceELDAEvent)
						traceDeliveredEvent(evt, "FreezedAgent");
				}
			}
			// If the agent is active
			else if (o instanceof SimELDA) {
				SimELDA sim = (SimELDA) o;
				sim.receive(evt);
				if (MASSimulation.traceELDAEvent)
					traceDeliveredEvent(evt, "ActiveAgent");
			}

			// If the agent is migrating or migrated
			else if (o instanceof Proxy) {
				Proxy px = (Proxy) o;
				// Management events aren't forwarded
				if (!(evt instanceof ELDAEventManagement))
					if (px.isActive()) {
						Msg m = new Msg(AS.getName(), px.getForward(), Msg.ELDAFRAMEWORK_EVENT, evt);
						VirtualNetwork.send(m);
						if (MASSimulation.traceELDAEvent)
							traceDeliveredEvent(evt, "ProxyON");
					} else {
						px.insertMailBox(evt);
						if (MASSimulation.traceELDAEvent)
							traceDeliveredEvent(evt, "ProxyOFF");
					}
			} else if (o == null) {
				if (MASSimulation.traceELDAEvent)
					traceDeliveredEvent(evt, "NotDeliveryEvent");
				System.err.println("TLH: CAN'T DELIVERY EVENT!");
			}

			if (evt instanceof ELDAEventMove || evt instanceof ELDAEventQuit) {
				Msg readyMsg = new Msg(getPrimaryEventHandler(evt), Msg.READY_TO, evt);
				// Modified in 17/06/08
				new Timer().setMaxPriority(readyMsg);
				if (MASSimulation.traceELDAEvent)
					traceLowLevelEvent(readyMsg, "ReadyTo");
			}
		} else if (MASSimulation.traceELDAEvent)
			traceDeliveredEvent(evt, "QuittingAgent");
	}

	public void generate(ELDAEvent evt) {
		ELDAId copy = new ELDAId(evt.getSource());
		evt.setSource(copy);

		if (evt instanceof ELDAEventQuitRequest && evt.getSource().equals(evt.getTarget()))
			quittingAgent.add(copy.getUID());

		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName() + " cargo " + evt.getClass());

		if (MASSimulation.traceELDAEvent)
			traceGeneratedEvent(evt);

		if (evt instanceof SimulationEvent) {
			ActionDuration simEvent = (ActionDuration) evt;
			SimELDA requester = (SimELDA) this.AS.lookUp(simEvent.getSource());
			ELDAEventTimeoutNotify unfreeze = new ActionFinishNotification(simEvent.getSource());
			ELDAEventCreateTimer timer = new ELDAEventCreateTimer(simEvent.getSource(), simEvent.getDuration(), unfreeze);
			FreezedAgent freezedAgent = new FreezedAgent(requester, this.getAS(), unfreeze);
			this.AS.registerFreezedAgent(freezedAgent);
			Msg m = new Msg(getPrimaryEventHandler(timer), Msg.ELDAFRAMEWORK_EVENT, timer);
			sendMsg(m);
		}
		// Modified 18/06/08
		else if (evt instanceof ELDAEventMoveRequest) {
			Msg m = new Msg(getPrimaryEventHandler(evt), Msg.ELDAFRAMEWORK_EVENT, evt);
			new Timer().setMaxPriority(m);
		} else {
			Msg m = new Msg(getPrimaryEventHandler(evt), Msg.ELDAFRAMEWORK_EVENT, evt);
			sendMsg(m);
		}
	}

	private void traceGeneratedEvent(ELDAEvent evt) {
		try {
			ELDAEventTracing.traceGeneratedEvent(evt, AS.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void traceDeliveredEvent(ELDAEvent evt, String prefix) {
		try {
			ELDAEventTracing.traceDeliveredEvent(evt, prefix, AS.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void traceLowLevelEvent(Msg msg, String arg) {
		try {
			ELDAEventTracing.traceLowLevelEvent(msg, arg, AS.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	class FreezedAgent {
		private SimELDA requester;
		private AgentServer as;
		private Vector<ELDAEvent> agentMemory;
		public ELDAEventTimeoutNotify unfreezeEvent;

		public FreezedAgent(SimELDA requester, AgentServer as, ELDAEventTimeoutNotify unfreezeEvent) {
			this.requester = requester;
			this.as = as;
			this.unfreezeEvent = unfreezeEvent;
			this.agentMemory = new Vector<ELDAEvent>();
		}

		public void insertIntoMemory(ELDAEvent e) {
			agentMemory.add(e);
		}

		public void flushMemory() {
			for (int i = 0; i < agentMemory.size(); i++) {
				Msg m = new Msg(this.as, Msg.ELDAFRAMEWORK_EVENT, agentMemory.get(i));
				sendMsg(m);
			}
		}

		public SimELDA getRequester() {
			return requester;
		}

	}

	class ActionFinishNotification extends ELDAEventTimeoutNotify {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ActionFinishNotification(ELDAId source) {
			super(source, null);
			// TODO Auto-generated constructor stub
		}
	}
}
