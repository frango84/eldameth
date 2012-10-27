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

package eldasim.handler.finalHandler.coordination.p_s;

import jaf.Message;
import jaf.VirtualClock;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.coordination.p_s.ELDAEventEVTNotification;
import eldaframework.eldaevent.coordination.p_s.ELDAEventPublish;
import eldaframework.eldaevent.coordination.p_s.ELDAEventSubscribe;
import eldaframework.eldaevent.coordination.p_s.ELDAEventUnsubscribe;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.lowlevelevent.ReSubscribe;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class ELVIN_based_P_SHandler extends P_SHandler {

	private Hashtable<Object, Vector<AgentEventAssociation>> topicAssociation = null;
	private Hashtable<ELDAId, Vector<Object>> agentTopics = null;

	public ELVIN_based_P_SHandler(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
		if (as.isPublishSubscribe()) {
			topicAssociation = new Hashtable<Object, Vector<AgentEventAssociation>>();
			agentTopics = new Hashtable<ELDAId, Vector<Object>>();
		}
	}

	@Override
	public void clear() {
		if (super.AS.isPublishSubscribe()) {
			topicAssociation = new Hashtable<Object, Vector<AgentEventAssociation>>();
			agentTopics = new Hashtable<ELDAId, Vector<Object>>();
		}
	}

	@Override
	protected void handler(Message m) {
		Msg msg = (Msg) m;
		// System.err.println(msg);
		if (!AS.isPublishSubscribe()) {
			if (msg.getType() == Msg.REMOTE_NOTIFICATION) {
				Msg forward = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, msg.getCargo());
				sendMsg(forward);
			} else {
				Msg forward = new Msg(AS.getName(), MASSimulation.getPublish_subscribe().getName(), Msg.REMOTE_REQUEST, msg.getCargo());
				VirtualNetwork.send(forward);
			}
		} else if (msg.getType() == Msg.REMOTE_REQUEST || msg.getType() == Msg.ELDAFRAMEWORK_EVENT)
			if (msg.getCargo() instanceof ELDAEventPublish) {
				ELDAEventPublish evt = (ELDAEventPublish) msg.getCargo();
				Vector vector = topicAssociation.get(evt.getEventTopic());
				if (vector != null) {
					Iterator i = vector.iterator();
					if (MASSimulation.debug) {
						System.out.print("Time: " + VirtualClock.getSimulationTime());
						System.out.print(" Run: " + MASSimulation.currentRun);
						System.out.print(" Publisher: " + evt.getSource().getUID() + " topic: " + evt.getData());
						System.out.println(" Num Subscripter: " + vector.size());
					}

					while (i.hasNext()) {
						AgentEventAssociation ass = (AgentEventAssociation) i.next();
						ELDAEventEVTNotification notify = ass.getNotification();
						notify.setEventTopic(evt.getEventTopic());
						notify.setData(evt.getData());
						// Modified in 03_07_08
						/*
						 * If the Curr Location is the same AS, sendMsg is used else
						 * VirtualNetwork.send is used
						 */
						if (!evt.getSource().getUID().equals(ass.getAgent().getUID()))
							if (ass.getAgent().getCurrLocation().equals(AS.getName())) {
								Msg msgNotify = new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, notify);
								sendMsg(msgNotify);
								if (MASSimulation.debug)
									System.out.println(" Sent Local Notification to subscriber: " + ass.getAgent());
							} else {
								Msg msgNotify = new Msg(AS.getName(), ass.getAgent().getCurrLocation(), Msg.REMOTE_NOTIFICATION, notify);
								VirtualNetwork.send(msgNotify);
								if (MASSimulation.debug)
									System.out.println(" Sent Remote Notification to subscriber: " + ass.getAgent());
							}
					}
				}

			} else if (msg.getCargo() instanceof ReSubscribe) {
				ReSubscribe evt = (ReSubscribe) msg.getCargo();
				if (MASSimulation.debug)
					System.out.println("ReSubsciption Num Run " + MASSimulation.currentRun + " " + evt.getSource());
				Vector<Object> topics = agentTopics.get(evt.getSource());
				if (topics != null) {
					Iterator topicIterator = topics.iterator();
					while (topicIterator.hasNext()) {
						Object o = topicIterator.next();
						Vector<AgentEventAssociation> association = topicAssociation.get(o);
						Iterator<AgentEventAssociation> associationIterator = association.iterator();
						boolean found = false;
						while (associationIterator.hasNext() && !found) {
							AgentEventAssociation agentEventAssociation = associationIterator.next();
							if (agentEventAssociation.getAgent().equals(evt.getSource())) {
								found = true;
								AgentEventAssociation newAss = new AgentEventAssociation(evt.getSource(), agentEventAssociation.getNotification());
								association.remove(agentEventAssociation);
								association.add(newAss);
							}
						}
					}
				}

			} else if (msg.getCargo() instanceof ELDAEventSubscribe) {
				ELDAEventSubscribe evt = (ELDAEventSubscribe) msg.getCargo();

				if (MASSimulation.debug)
					System.out.println("Subsciption Num Run " + MASSimulation.currentRun + " " + evt.getSource());
				Vector<Object> topics = null;

				// Handling agent-topics associations to enable re-subscriptions
				if (agentTopics.get(evt.getSource()) == null)
					topics = new Vector<Object>();
				else
					topics = agentTopics.get(evt.getSource());
				topics.add(evt.getEventTopic());
				agentTopics.remove(evt.getSource());
				agentTopics.put(evt.getSource(), topics);

				// Handling topic-agents associations to enable notifications
				AgentEventAssociation agentEventAssociation = new AgentEventAssociation(evt.getSource(), evt.getBackEvent());
				Vector<AgentEventAssociation> vector = null;

				if (topicAssociation.get(evt.getEventTopic()) == null)
					vector = new Vector<AgentEventAssociation>();
				else
					vector = topicAssociation.get(evt.getEventTopic());
				vector.add(agentEventAssociation);
				topicAssociation.remove(evt.getEventTopic());
				topicAssociation.put(evt.getEventTopic(), vector);
			} else if (msg.getCargo() instanceof ELDAEventUnsubscribe) {
				ELDAEventUnsubscribe evt = (ELDAEventUnsubscribe) msg.getCargo();
				ELDAId requester = evt.getSource();
				Vector<AgentEventAssociation> vector = topicAssociation.get(evt.getEventTopic());
				if (vector != null) {
					Iterator i = vector.iterator();
					while (i.hasNext()) {
						AgentEventAssociation ass = (AgentEventAssociation) i.next();
						if (ass.getAgent().equals(requester)) {
							i.remove();
							break;
						}
					}
				}
			}
	}
}
