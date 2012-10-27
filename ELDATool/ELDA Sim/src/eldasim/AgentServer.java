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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.ELDAEvent;
import eldaframework.eldaevent.coordination.ELDAEventCoordination;
import eldaframework.eldaevent.coordination.direct.ELDAEventDirect;
import eldaframework.eldaevent.coordination.direct.ELDAEventMSG;
import eldaframework.eldaevent.coordination.direct.ELDAEventMSGRequest;
import eldaframework.eldaevent.coordination.direct.ELDAEventRPCRequest;
import eldaframework.eldaevent.coordination.p_s.ELDAEventEVTNotification;
import eldaframework.eldaevent.coordination.p_s.ELDAEventPublish;
import eldaframework.eldaevent.coordination.p_s.ELDAEventSubscribe;
import eldaframework.eldaevent.coordination.p_s.ELDAEventUnsubscribe;
import eldaframework.eldaevent.coordination.tuples.ELDAEventIn;
import eldaframework.eldaevent.coordination.tuples.ELDAEventOut;
import eldaframework.eldaevent.coordination.tuples.ELDAEventRd;
import eldaframework.eldaevent.internal.ELDAEventInternal;
import eldaframework.eldaevent.management.ELDAEventManagement;
import eldaframework.eldaevent.management.information.ELDAEventInformation;
import eldaframework.eldaevent.management.information.ELDAEventRegisteredAgent;
import eldaframework.eldaevent.management.information.ELDAEventRegisteredAgentRequest;
import eldaframework.eldaevent.management.lifecycle.ELDAEventClone;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCloneNotify;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCreate;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCreateNotify;
import eldaframework.eldaevent.management.lifecycle.ELDAEventDestroy;
import eldaframework.eldaevent.management.lifecycle.ELDAEventExecute;
import eldaframework.eldaevent.management.lifecycle.ELDAEventInvoke;
import eldaframework.eldaevent.management.lifecycle.ELDAEventLifecycle;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMoveRequest;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuit;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuitRequest;
import eldaframework.eldaevent.management.lifecycle.ELDAEventWait;
import eldaframework.eldaevent.management.lifecycle.ELDAEventWaitRequest;
import eldaframework.eldaevent.management.lifecycle.ELDAEventWakeUp;
import eldaframework.eldaevent.management.timer.ELDAEventCreateTimer;
import eldaframework.eldaevent.management.timer.ELDAEventCreateTimerNotify;
import eldasim.TLH.FreezedAgent;
import eldasim.agent.SimELDA;
import eldasim.handler.EventHandler;
import eldasim.handler.IntermediateEventHandler;
import eldasim.handler.PrimaryEventHandler;
import eldasim.handler.finalHandler.FinalHandler;
import eldasim.handler.finalHandler.coordination.direct.AsyncMPHandler_ProxyAhead;
import eldasim.handler.finalHandler.coordination.p_s.ELVIN_based_P_SHandler;
import eldasim.handler.finalHandler.coordination.p_s.P_SHandler;
import eldasim.handler.finalHandler.coordination.tucson.TucsonHandler;
import eldasim.handler.finalHandler.management.information.DFHandler;
import eldasim.handler.finalHandler.management.lifecycle.CloneHandler;
import eldasim.handler.finalHandler.management.lifecycle.CreateHandler;
import eldasim.handler.finalHandler.management.lifecycle.MoveHandler_ProxyAhead_UpdateSubscription;
import eldasim.handler.finalHandler.management.lifecycle.QuitHandler;
import eldasim.handler.finalHandler.management.timer.TimerHandler;
import eldasim.handler.primaryHandler.InternalHandler;
import eldasim.network.Proxy;
import eldasim.setup.MASSimulation;

public class AgentServer extends jaf.Actor implements WhitePageAccess {
	private TLH Tlh;
	public static final byte Initialized = 0;
	private boolean publishSubscribe = false;

	private EventHandler tucsonHandler;

	private EventHandler p_sHandler;

	private String type;

	private Hashtable userAgentList;

	private WhitePages wp;

	public AgentServer(String name, String type) {
		if (MASSimulation.debug)
			System.out.println("AS:" + name + "->Status:Initialized");
		this.name = name;
		this.type = type;
		wp = new WhitePages();
		userAgentList = new Hashtable();
		inizializeHandler();
		become(Initialized);
	}

	public AgentServer(String name) {
		this.name = name;
		wp = new WhitePages();
		inizializeHandler();
	}

	public AgentServer(String name, boolean publishSubscribe) {
		this.name = name;
		this.publishSubscribe = publishSubscribe;
		wp = new WhitePages();
		inizializeHandler();
	}

	// public void setPublishSubscribe(){
	// this.publishSubscribe=true;
	// }

	@Override
	public void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName());
		Msg msg = (Msg) m;
		if (msg.type == Msg.ELDAFRAMEWORK_EVENT) {
			ELDAEvent evt = (ELDAEvent) msg.getCargo();
			this.Tlh.deliverToAgent(evt);
		} else
			this.Tlh.manageMessage(msg);
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	private Vector filterQueue(ELDAId eid) {
		return null;
	}

	public Object lookUp(ELDAId mid) {
		return this.wp.lookup(mid);
	}

	public void registerProxy(ELDAId mid, Proxy obj) {
		this.wp.register(mid, obj);
	}

	public void registerAgent(SimELDA agent) {
		agent.setAs(this);
		this.wp.register(agent.getELDAId(), agent);
	}

	public void registerFreezedAgent(FreezedAgent agent) {
		this.wp.register(agent.getRequester().getELDAId(), agent);
	}

	public void unregister(ELDAId mid) {
		this.wp.unregister(mid);
	}

	public void clearWP() {
		this.wp.clear();
		this.Tlh.quittingAgent = new Vector<String>();
	}

	public Vector<String> getAll() {
		Vector<String> v = new Vector<String>();
		v = this.wp.getRegistred();
		return v;
	}

	public void clearTC(String template) {
		((TucsonHandler) tucsonHandler).clearTC(template);
	}

	public void clearPS() {
		((P_SHandler) p_sHandler).clear();
	}

	public void startAgent(SimELDA agent) {
		ELDAEventInvoke invoke = new ELDAEventInvoke(agent.getELDAId());
		this.Tlh.deliverToAgent(invoke);
	}

	public TLH getTlh() {
		return Tlh;
	}

	protected void inizializeHandler() {
		System.out.print("Setup Handlers...........");

		// primary handler
		Vector v = new Vector();
		v.add(ELDAEventInternal.class);
		EventHandler internalHandler = new InternalHandler(this, v);

		v.clear();
		v.add(ELDAEventManagement.class);
		EventHandler managementHandler = new PrimaryEventHandler(this, "managementHandler", v);

		v.clear();
		v.add(ELDAEventCoordination.class);
		EventHandler coordinationHandler = new PrimaryEventHandler(this, "coordinationHandler", v);

		// intermediate handler
		v.clear();
		v.add(ELDAEventLifecycle.class);
		EventHandler lifecycleHandler = new IntermediateEventHandler(this, "lifecycleHandler", v);

		v.clear();
		v.add(ELDAEventInformation.class);
		EventHandler informationHandler = new IntermediateEventHandler(this, "informationHandler", v); // Added
		// at
		// 13-01-2009

		v.clear();
		v.add(ELDAEventDirect.class);
		EventHandler directHandler = new IntermediateEventHandler(this, "directHandler", v);

		// final handler
		v.clear();
		v.add(ELDAEventCreate.class);
		v.add(ELDAEventCreateNotify.class);
		EventHandler createHandler = new CreateHandler(this, v);

		v.clear();
		v.add(ELDAEventClone.class);
		v.add(ELDAEventCloneNotify.class);
		EventHandler cloneHandler = new CloneHandler(this, v);

		v.clear();
		v.add(ELDAEventExecute.class);
		v.add(ELDAEventMove.class);
		v.add(ELDAEventMoveRequest.class);
		EventHandler moveHandler = new MoveHandler_ProxyAhead_UpdateSubscription(this, v);

		v.clear();
		v.add(ELDAEventDestroy.class);
		v.add(ELDAEventQuit.class);
		v.add(ELDAEventQuitRequest.class);
		EventHandler quitHandler = new QuitHandler(this, v);

		v.clear();
		v.add(ELDAEventCreateTimer.class);
		v.add(ELDAEventCreateTimerNotify.class);
		EventHandler timerHandler = new TimerHandler(this, v);

		v.clear();
		v.add(ELDAEventMSGRequest.class);
		v.add(ELDAEventMSG.class);
		EventHandler asynchMPHandler = new AsyncMPHandler_ProxyAhead(this, "asynchMPHandler", v);

		v.clear();
		v.add(ELDAEventOut.class);
		v.add(ELDAEventRd.class);
		v.add(ELDAEventIn.class);
		tucsonHandler = new TucsonHandler(this, "tucsonHandler", v);

		v.clear();
		v.add(ELDAEventWaitRequest.class);
		v.add(ELDAEventWait.class);
		v.add(ELDAEventWakeUp.class);
		EventHandler waitHandler = new FinalHandler(this, "waitHandler", v);

		v.clear();
		v.add(ELDAEventRPCRequest.class);
		EventHandler synchHandler = new FinalHandler(this, "synchMPHandler", v);

		v.clear();
		v.add(ELDAEventSubscribe.class);
		v.add(ELDAEventUnsubscribe.class);
		v.add(ELDAEventPublish.class);
		v.add(ELDAEventEVTNotification.class);
		p_sHandler = new ELVIN_based_P_SHandler(this, "P_SHandler", v);

		v.clear();
		v.add(ELDAEventRegisteredAgentRequest.class);
		v.add(ELDAEventRegisteredAgent.class);
		EventHandler dfHandler = new DFHandler(this, "DFHandler", v);

		v.clear();
		v.add(managementHandler);
		v.add(coordinationHandler);
		v.add(internalHandler);
		this.Tlh = new TLH(this, "TLH", v);

		managementHandler.addSuccessor(lifecycleHandler);
		managementHandler.addSuccessor(informationHandler); // added at 13-01-2009
		managementHandler.addSuccessor(timerHandler);

		informationHandler.addSuccessor(dfHandler); // added at 13-01-2009

		coordinationHandler.addSuccessor(directHandler);
		coordinationHandler.addSuccessor(p_sHandler);
		coordinationHandler.addSuccessor(tucsonHandler);

		lifecycleHandler.addSuccessor(waitHandler);
		lifecycleHandler.addSuccessor(createHandler);
		lifecycleHandler.addSuccessor(cloneHandler);
		lifecycleHandler.addSuccessor(moveHandler);
		lifecycleHandler.addSuccessor(quitHandler);

		directHandler.addSuccessor(asynchMPHandler);
		directHandler.addSuccessor(synchHandler);

		System.out.println("Setup Handlers...........DONE\n");
	}

	class WhitePages {

		private Hashtable<ELDAId, Object> ht;

		public WhitePages() {
			ht = new Hashtable<ELDAId, Object>();
		}

		Object lookup(ELDAId mid) {
			return ht.get(mid);
		}

		void register(ELDAId mid, Object obj) {
			// obj = SimMao or Proxy
			Object previousObj = ht.put(mid, obj);
			// test the previousObj
		}

		void unregister(ELDAId mid) {
			Object obj = ht.remove(mid);
		}

		void clear() {
			ht.clear();
		}

		Vector getRegistred() {
			Vector v = new Vector<String>();
			Enumeration e = ht.keys();
			while (e.hasMoreElements())
				v.add(e.nextElement().toString());
			return v;

		}

	}

	public boolean isPublishSubscribe() {
		return publishSubscribe;
	}

	public void setPublishSubscribe(boolean ps) {
		publishSubscribe = ps;
	}

	// class TLH extends EventRouter implements ITLH{

	// private Vector<String> quittingAgent=new Vector<String>();

	// public TLH(AgentServer as, String name, Vector<PrimaryEventHandler> v) {
	// super(as, name, null);
	// if (v != null)
	// for (int i = 0; i < v.size(); i++)
	// addPrimaryEventHandler(v.get(i));
	// }

	// private PrimaryEventHandler getPrimaryEventHandler(ELDAEvent evt) {
	// return (PrimaryEventHandler) getMessageTarget(evt);
	// }

	// private void addPrimaryEventHandler(PrimaryEventHandler eh) {
	// addSuccessor(eh);
	// setASasPredessor(eh);
	// }

	// private void setASasPredessor(PrimaryEventHandler eh) {
	// eh.setPredecessor(AS);
	// }

	// protected void handler(Message m) {
	// // mai chiamato dal motore di simulazione

	// }

	// private void manageMessage(Msg msg) {
	// if (MASSimulation.debug)
	// System.out.println("Handled by "
	// + new Exception().getStackTrace()[0].getMethodName()
	// + " of " + this.getName() + " at " + this.AS.getName());
	// if (msg.type == Msg.REMOTE_NOTIFICATION
	// || msg.type == Msg.REMOTE_REQUEST) {
	// ELDAEvent evt = (ELDAEvent) msg.getCargo();
	// msg.receiver = getPrimaryEventHandler(evt);
	// sendMsg(msg);
	// }

	// Class[] interfaces=msg.getCargo().getClass().getInterfaces();
	// for(int i=0; i<interfaces.length; i++ )
	// if(interfaces[i]== LowLevelEvent.class)
	// if(MASSimulation.traceELDAEvent)
	// traceLowLevelEvent(msg, "Handler");

	// }

	// public void deliverToAgent(ELDAEvent evt) {
	// ELDAId target = evt.getTarget();

	// //Quitting agents cannot receive events which are different from Quit
	// if (!quittingAgent.contains(target.getUID()) || evt instanceof
	// ELDAEventQuit ){
	// if (MASSimulation.debug)
	// System.out.println("Handled by "
	// + new Exception().getStackTrace()[0].getMethodName()
	// + " of " + this.getName() + " at " + this.AS.getName());

	// Object o = AS.lookUp(target);
	// //If the agent has been freezed to simulate action duration
	// if (o instanceof FreezedAgent) {
	// FreezedAgent freezedAgent=(FreezedAgent) o;
	// if (evt instanceof ELDAEventTimeoutNotify &&
	// freezedAgent.unfreezeEvent.equals(evt) ){
	// freezedAgent.flushMemory();
	// this.AS.registerAgent(freezedAgent.getRequester());
	// if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt, "EndAction");
	// }
	// else{
	// freezedAgent.insertIntoMemory(evt);
	// if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt, "FreezedAgent");
	// }
	// }
	// //If the agent is active
	// else if (o instanceof SimELDA) {
	// SimELDA sim = (SimELDA) o;
	// sim.receive(evt);
	// if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt, "ActiveAgent");
	// }

	// //If the agent is migrating or migrated
	// else if (o instanceof Proxy) {
	// Proxy px = (Proxy) o;
	// //Management events aren't forwarded
	// if (!(evt instanceof ELDAEventManagement))
	// if (px.isActive()) {
	// Msg m = new Msg(AS.getName(), px.getForward(),Msg.ELDAFRAMEWORK_EVENT,
	// evt);
	// VirtualNetwork.send(m);
	// if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt, "ProxyON");
	// }
	// else{
	// px.insertMailBox(evt);
	// if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt, "ProxyOFF");
	// }
	// }
	// else if (o ==null)
	// System.err.println("LOST MESSAGE");

	// if (evt instanceof ELDAEventMove || evt instanceof ELDAEventQuit) {
	// Msg readyMsg = new Msg(getPrimaryEventHandler(evt), Msg.READY_TO, evt);
	// // Modified in 17/06/08
	// new Timer().setMaxPriority(readyMsg);
	// if (MASSimulation.traceELDAEvent) traceLowLevelEvent(readyMsg, "ReadyTo");
	// }
	// }
	// else if (MASSimulation.traceELDAEvent) traceDeliveredEvent(evt,
	// "QuittingAgent");
	// }

	// public void generate(ELDAEvent evt) {
	// ELDAId copy=new ELDAId(evt.getSource());
	// evt.setSource(copy);

	// if (evt instanceof ELDAEventQuitRequest){
	// quittingAgent.add(copy.getUID());
	// }

	// if (MASSimulation.debug)
	// System.out.println("Handled by "
	// + new Exception().getStackTrace()[0].getMethodName()
	// + " of " + this.getName() + " at " + this.AS.getName());

	// if (MASSimulation.traceELDAEvent) traceGeneratedEvent(evt);

	// if(evt instanceof SimulationEvent){
	// ActionDuration simEvent=(ActionDuration)evt;
	// SimELDA requester=(SimELDA)this.AS.lookUp(simEvent.getSource());
	// ELDAEventTimeoutNotify unfreeze=new
	// ActionFinishNotification(simEvent.getSource());
	// ELDAEventCreateTimer timer= new
	// ELDAEventCreateTimer(simEvent.getSource(),simEvent.getDuration(),unfreeze);
	// FreezedAgent freezedAgent= new
	// FreezedAgent(requester,this.getAS(),unfreeze);
	// this.AS.registerFreezedAgent(freezedAgent);
	// Msg m = new Msg(getPrimaryEventHandler(timer), Msg.ELDAFRAMEWORK_EVENT,
	// timer);
	// sendMsg(m);
	// }
	// //Modified 18/06/08
	// else if(evt instanceof ELDAEventMoveRequest){
	// Msg m = new Msg(getPrimaryEventHandler(evt), Msg.ELDAFRAMEWORK_EVENT, evt);
	// new Timer().setMaxPriority(m);
	// }
	// else{

	// Msg m = new Msg(getPrimaryEventHandler(evt), Msg.ELDAFRAMEWORK_EVENT, evt);
	// sendMsg(m);
	// }
	// }

	// private void traceGeneratedEvent(ELDAEvent evt){
	// String callingAction=new Exception().getStackTrace()[4].getMethodName();
	// int point=evt.getClass().getName().lastIndexOf(".")+1;
	// String SQLInsert="INSERT INTO GENERATED_EVENTS_"+
	// MASSimulation.typeSolution+"_"+ MASSimulation.currentRun+" "+
	// "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TIPO_EVENTO) " +
	// "VALUES "+
	// "("+"'"+AS.getName()+"'"+","+
	// VirtualClock.getSimulationTime()+","+
	// "'"+evt.getSource().getUID()+"',"+
	// "'"+callingAction+": "+evt.getClass().getName().substring(point)+"'"+
	// ");";

	// Statement stmt=null;
	// try {
	// stmt = MASSimulation.con.createStatement();
	// stmt.execute(SQLInsert);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	// private void traceDeliveredEvent(ELDAEvent evt, String prefix){
	// int point=evt.getClass().getName().lastIndexOf(".")+1;
	// String SQLInsert="INSERT INTO DELIVERED_EVENTS_"+
	// MASSimulation.typeSolution+"_"+ MASSimulation.currentRun+" "+
	// "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TARGET, TIPO_EVENTO) " +
	// "VALUES "+
	// "("+"'"+AS.getName()+"'"+","+
	// VirtualClock.getSimulationTime()+","+
	// "'"+evt.getSource().getUID()+"@"+evt.getSource().getCurrLocation()+"',"+
	// "'"+evt.getTarget().getUID()+"@"+evt.getTarget().getCurrLocation()+"',"+
	// "'"+prefix+": "+evt.getClass().getName().substring(point)+"'"+
	// ");";

	// Statement stmt=null;
	// try {
	// stmt = MASSimulation.con.createStatement();
	// stmt.execute(SQLInsert);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// }
	// private void traceLowLevelEvent(Msg msg, String arg){
	// ELDAEvent evt=(ELDAEvent)msg.getCargo();
	// int point=evt.getClass().getName().lastIndexOf(".")+1;
	// String newLoc="";
	// if(evt instanceof ReSubscribe)
	// newLoc=((ReSubscribe)evt).getSource().getCurrLocation();

	// String SQLInsert="INSERT INTO DELIVERED_EVENTS_"+
	// MASSimulation.typeSolution+"_"+ MASSimulation.currentRun+" "+
	// "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TARGET, TIPO_EVENTO) " +
	// "VALUES "+
	// "("+"'"+AS.getName()+"'"+","+
	// VirtualClock.getSimulationTime()+","+
	// "'"+evt.getSource().getUID()+"@"+evt.getSource().getCurrLocation()+"',"+
	// "'"+arg+"',"+
	// "'"+evt.getClass().getName().substring(point)+"'"+
	// ");";

	// Statement stmt=null;
	// try {
	// stmt = MASSimulation.con.createStatement();
	// stmt.execute(SQLInsert);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }

	// }

	// class FreezedAgent{
	// private SimELDA requester;
	// private AgentServer as;
	// private Vector<ELDAEvent> agentMemory;
	// private ELDAEventTimeoutNotify unfreezeEvent;

	// public FreezedAgent(SimELDA requester,AgentServer as,ELDAEventTimeoutNotify
	// unfreezeEvent) {
	// this.requester=requester;
	// this.as=as;
	// this.unfreezeEvent=unfreezeEvent;
	// this.agentMemory=new Vector<ELDAEvent>();
	// }

	// public void insertIntoMemory(ELDAEvent e){
	// agentMemory.add(e);
	// }

	// public void flushMemory(){
	// for(int i=0; i<agentMemory.size();i++){
	// Msg m=new Msg(this.as,Msg.ELDAFRAMEWORK_EVENT,agentMemory.get(i));
	// sendMsg(m);
	// }
	// }

	// public SimELDA getRequester() {
	// return requester;
	// }

	// }

	// class ActionFinishNotification extends ELDAEventTimeoutNotify{

	// public ActionFinishNotification(ELDAId source) {
	// super(source, null);
	// // TODO Auto-generated constructor stub
	// }
	// }
	// }
}
