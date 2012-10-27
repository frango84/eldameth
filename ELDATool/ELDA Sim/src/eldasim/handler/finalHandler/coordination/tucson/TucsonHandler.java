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

package eldasim.handler.finalHandler.coordination.tucson;

import jaf.Message;

import java.util.Vector;

import alice.logictuple.InvalidLogicTupleException;
import alice.logictuple.LogicTuple;
import alice.tucson.api.AgentId;
import alice.tucson.api.ContextNotAvailableException;
import alice.tucson.api.DefaultContextDescription;
import alice.tucson.api.InvalidAgentIdException;
import alice.tucson.api.InvalidContextDescriptionException;
import alice.tucson.api.InvalidTupleCentreIdException;
import alice.tucson.api.OperationNotAllowedException;
import alice.tucson.api.Tucson;
import alice.tucson.api.TucsonContext;
import alice.tucson.api.TupleCentreId;
import alice.tucson.api.UnreachableNodeException;
import eldaframework.eldaevent.coordination.tuples.ELDAEventIn;
import eldaframework.eldaevent.coordination.tuples.ELDAEventOut;
import eldaframework.eldaevent.coordination.tuples.ELDAEventRd;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.setup.MASSimulation;

public class TucsonHandler extends AbstractFinalEventHandler {

	TucsonContext ctx;
	TupleCentreId tc;

	public TucsonHandler(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
		// TODO Auto-generated constructor stub
		try {
			AgentId agentId = new AgentId("ta_" + as.toString());
			try {

				ctx = Tucson.enterContext(new DefaultContextDescription(agentId));
				// System.out.println(ctx);

			} catch (ContextNotAvailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidContextDescriptionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (InvalidAgentIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			tc = new TupleCentreId("tc_" + as.toString());
			System.out.println(tc);
		} catch (InvalidTupleCentreIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName());
		Msg msg = (Msg) m;

		if (msg.getCargo() instanceof ELDAEventOut) {
			ELDAEventOut evt = (ELDAEventOut) msg.getCargo();
			LogicTuple logicTuple = null;
			try {
				logicTuple = LogicTuple.parse((String) (evt).getContent());
			} catch (InvalidLogicTupleException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ctx.out(tc, logicTuple);
				// ctx.exit();
			} catch (OperationNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnreachableNodeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (msg.getCargo() instanceof ELDAEventRd) {
			ELDAEventRd evt = (ELDAEventRd) msg.getCargo();
			LogicTuple logicTuple = null;
			try {
				logicTuple = LogicTuple.parse((String) (evt).getTupleMatch());
			} catch (InvalidLogicTupleException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LogicTuple read = null;
			if (evt.isSync())
				try {
					read = ctx.rd(tc, logicTuple);
				} catch (OperationNotAllowedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreachableNodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					read = ctx.rdp(tc, logicTuple);
				} catch (OperationNotAllowedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreachableNodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (read != null)
				evt.getBackEvent().setContent(read.toString());
			Msg tuple = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt.getBackEvent());
			sendMsg(tuple);
		} else if (msg.getCargo() instanceof ELDAEventIn) {
			ELDAEventIn evt = (ELDAEventIn) msg.getCargo();
			LogicTuple logicTuple = null;
			try {
				logicTuple = LogicTuple.parse((String) (evt).getTupleMatch());
			} catch (InvalidLogicTupleException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LogicTuple read = null;
			if (evt.isSync())
				try {
					read = ctx.in(tc, logicTuple);
				} catch (OperationNotAllowedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreachableNodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else
				try {
					read = ctx.inp(tc, logicTuple);
				} catch (OperationNotAllowedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnreachableNodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (read != null)
				evt.getBackEvent().setContent(read.toString());
			Msg tuple = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt.getBackEvent());
			sendMsg(tuple);
		}
	}

	public void clearTC(String template) {
		LogicTuple logicTuple = null;
		try {
			logicTuple = LogicTuple.parse(template);
		} catch (InvalidLogicTupleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ctx.inp(tc, logicTuple);
		} catch (OperationNotAllowedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnreachableNodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
