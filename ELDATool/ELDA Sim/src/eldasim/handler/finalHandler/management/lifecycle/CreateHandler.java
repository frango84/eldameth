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
import jaf.Timer;
import jaf.VirtualClock;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.ELDAActiveState;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCreate;
import eldaframework.eldaevent.management.lifecycle.ELDAEventCreateNotify;
import eldaframework.eldaevent.management.lifecycle.ELDAEventInvoke;
import eldasim.AgentServer;
import eldasim.Msg;
import eldasim.agent.SimELDA;
import eldasim.gui.util.RuntimeClassLoader;
import eldasim.gui.util.exception.RuntimeClassLoaderNotInitializedException;
import eldasim.handler.AbstractFinalEventHandler;
import eldasim.lowlevelevent.RemoteCreate;
import eldasim.network.VirtualNetwork;
import eldasim.setup.MASSimulation;

public class CreateHandler extends AbstractFinalEventHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<ELDAEventCreateNotify, Info> suspendedNotification = new HashMap<ELDAEventCreateNotify, Info>();

	private static long CREATION_TIME = 0;

	public CreateHandler(AgentServer as, Vector<Class> v) {
		super(as, "CreateHandler", v);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handler(Message m) {
		if (MASSimulation.debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of " + this.getName() + " at "
					+ this.AS.getName() + " at " + this.AS.getName() + " when virtual clock was " + VirtualClock.getSimulationTime());

		Msg msg = (Msg) m;
		if (msg.getType() == Msg.DELAYED_ELDAFRAMEWORK_EVENT) {
			if (msg.getCargo() instanceof RemoteCreate) {
				RemoteCreate evt = (RemoteCreate) msg.getCargo();
				ELDAId id = evt.getNames().get(0);
				createAgentLocally(id, evt);
				if (evt.getNotify()) {
					evt.getBackEvent().getCreatedELDAIds().add(id);
					Msg msgResponse = new Msg(this.AS.getName(), msg.getSourceURL(), Msg.REMOTE_NOTIFICATION, evt.getBackEvent());
					if (MASSimulation.debug)
						System.out.println("Remote notification sent to " + msg.getSourceURL());
					VirtualNetwork.send(msgResponse);

				}
			} else if (msg.getCargo() instanceof ELDAEventCreate) {
				ELDAEventCreate evt = (ELDAEventCreate) msg.getCargo();
				for (int i = 0; i < evt.getNAgents(); i++) {
					ELDAId id = evt.getNames().get(i);
					createAgentLocally(id, evt);
					if (evt.getNotify())
						evt.getBackEvent().getCreatedELDAIds().add(id);
				}
				if (evt.getNotify()) {
					Info info = suspendedNotification.get(evt.getBackEvent());
					int count = info.getNumCreatedAgent();
					count = count + evt.getNAgents();
					info.setNumCreatedAgent(count);
					suspendedNotification.put(evt.getBackEvent(), info);
					if (info.getNumTotalAgent() == info.getNumCreatedAgent())
						sendMsg(new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt.getBackEvent()));
				}
			}
		} else if (msg.getType() == Msg.REMOTE_REQUEST) {
			if (msg.getCargo() instanceof RemoteCreate) {
				if (MASSimulation.debug)
					System.out.println("Received Remote Request at " + this.AS.getName());
				msg.setType(Msg.DELAYED_ELDAFRAMEWORK_EVENT);
				new Timer().set(msg, CREATION_TIME);
			}
		}

		else if (msg.getType() == Msg.REMOTE_NOTIFICATION) {
			if (msg.getCargo() instanceof ELDAEventCreateNotify) {
				if (MASSimulation.debug)
					System.out.println("Received Remote Notification at " + this.AS.getName());
				ELDAEventCreateNotify evt = (ELDAEventCreateNotify) msg.getCargo();
				Info info = suspendedNotification.get(evt);
				int count = info.getNumCreatedAgent();
				count = count + 1;
				info.setNumCreatedAgent(count);
				suspendedNotification.put(evt, info);
				if (info.getNumTotalAgent() == info.getNumCreatedAgent())
					sendMsg(new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, evt));
			}
		} else if (msg.getType() == Msg.ELDAFRAMEWORK_EVENT)
			if (msg.getCargo() instanceof ELDAEventCreate) { // richiesta di creazione
				ELDAEventCreate evt = (ELDAEventCreate) msg.getCargo();
				// TODO verificare
				suspendedNotification.put(evt.getBackEvent(), new Info(1));
				// richiesta di creazione interamente locale
				if (evt.getLocations() == null) {
					msg.setType(Msg.DELAYED_ELDAFRAMEWORK_EVENT);
					new Timer().set(msg, CREATION_TIME);
				} else {// richiesta di creazione locale e/o remota
					int numberOfLoc = evt.getLocations().size();
					suspendedNotification.put(evt.getBackEvent(), new Info(numberOfLoc));
					int localAgent = 0;
					Vector<ELDAId> names = new Vector<ELDAId>();

					for (int i = 0; i < numberOfLoc; i++) {
						String currentLocation = evt.getLocations().get(i);
						if (currentLocation.equals(this.AS.getName())) {
							names.add(evt.getNames().get(i));
							localAgent++;
						} else {
							Vector id = new Vector<ELDAId>(1);
							id.add(evt.getNames().get(i));
							RemoteCreate remoteCreation = new RemoteCreate(evt.getSource(), evt.getActiveState(), evt.getParams(), id, evt.getNotify(),
									evt.getBackEvent());
							Msg msgSingleRemoteCreation = new Msg(this.AS.getName(), currentLocation, Msg.REMOTE_REQUEST, remoteCreation);
							VirtualNetwork.send(msgSingleRemoteCreation);
						}
					}
					if (localAgent != 0) {
						ELDAEventCreate aggregateReq = new ELDAEventCreate(evt.getSource(), evt.getActiveState(), evt.getParams(), localAgent, null,
								names, evt.getNotify(), evt.getBackEvent());
						new Timer().set(new Msg(this, Msg.DELAYED_ELDAFRAMEWORK_EVENT, aggregateReq), 100 * localAgent);
					}

				}
			}
	}

	private void createAgentLocally(ELDAId id, ELDAEventCreate evt) {
		ELDAId copy = new ELDAId(id);
		ELDAActiveState activeState = null;
		try {
			if (evt.getParams() == null)
				activeState = (ELDAActiveState) RuntimeClassLoader.getClassLoader().loadClass(evt.getActiveState()).getConstructor(new Class[0]).newInstance(new Object[0]);
			// activeState = (ELDAActiveState) Class.forName(evt.getActiveState()).getConstructor(new Class[0]).newInstance(new Object[0]);
			else
				activeState = (ELDAActiveState) RuntimeClassLoader.getClassLoader().loadClass(evt.getActiveState()).getConstructors()[0].newInstance(evt.getParams());
			// activeState = (ELDAActiveState) Class.forName(evt.getActiveState()).getConstructors()[0].newInstance(evt.getParams());
			copy.setHomeLocation(AS.getName());
			copy.setCurrLocation(AS.getName());
			SimELDA simELDA = new SimELDA(copy, activeState, AS);
			AS.registerAgent(simELDA);
			ELDAEventInvoke invoke = new ELDAEventInvoke(copy);
			sendMsg(new Msg(predecessor, Msg.ELDAFRAMEWORK_EVENT, invoke));

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeClassLoaderNotInitializedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Info {
	private int numCreatedAgent;
	private int numTotalAgent;

	public Info(int a) {
		numCreatedAgent = 0;
		numTotalAgent = a;
	}

	public int getNumCreatedAgent() {
		return numCreatedAgent;
	}

	public void setNumCreatedAgent(int numCreatedAgent) {
		this.numCreatedAgent = numCreatedAgent;
	}

	public int getNumTotalAgent() {
		return numTotalAgent;
	}

}
