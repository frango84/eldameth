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

package eldaframework.eldaevent.management.lifecycle;

import java.io.Serializable;
import java.util.Vector;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.ELDAActiveState;

/**
 * @author Samuele
 *
 */
public class ELDAEventCreate extends ELDAEventLifecycle implements Serializable {


	protected String activeStateURL;
	
	protected Object[] params;

	/**
	 * Number of Agents that will be created
	 */
	protected int nAgents;

	/**
	 * Locations where Agents will be created
	 */
//	protected Vector<String> locations=new Vector<String>();
	protected Vector locations=new Vector();

	/**
	 * Proposed Names for the Agents that will be created
	 */
	//protected Vector<ELDAId> names = new Vector<ELDAId>();
	protected Vector names = new Vector();

	/**
	 * The flag value will be true if the Agent requests a creation notification
	 */
	protected boolean notify;

	/**
	 * Attended event that will be sent back
	 */
	protected ELDAEventCreateNotify backEvent;

	
//	public ELDAEventCreate(ELDAId source, ELDAId target, String activeStateURL, Object[] params,
//			int nAgents,  Vector<String> locations, Vector<ELDAId> names,
//			boolean notify, ELDAEventCreateNotify backEvent) {
//		super(source, target);
//		this.activeStateURL = activeStateURL;
//		this.nAgents = nAgents;
//		this.locations = locations;
//		this.names = names;
//		this.notify = notify;
//		this.backEvent = backEvent;
//		this.params=params;
//	}

	/**
	 * Creazione locale senza notifica
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param agentId
	 */
	
	public ELDAEventCreate(ELDAId source, String activeStateURL, Object[] params,ELDAId agentId) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.nAgents = 1;
		this.locations = null;
		this.names.add(agentId);
		this.notify = false;
		this.backEvent = null;
		this.params=params;
	}
	
	/**
	 * Creazione locale con notifica
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param agentId
	 * @param notify
	 * @param backEvent
	 */
	public ELDAEventCreate(ELDAId source, String activeStateURL, Object[] params,ELDAId agentId, 
			boolean notify,	ELDAEventCreateNotify backEvent) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.nAgents = 1;
		this.locations = null;
		this.names.add(agentId);
		this.notify = notify;
		this.backEvent = backEvent;
		this.params=params;
	}
	
	/**
	 * Creazione di n agenti in diverse locazioni senza notifica
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param nAgents
	 * @param locations
	 * @param names
	 * @param notify
	 * @param backEvent
	 */
	public ELDAEventCreate(ELDAId source, String activeStateURL, Object[] params,int nAgents,
			Vector<String> locations, Vector<ELDAId> names) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.params=params;
		this.nAgents = nAgents;
		this.locations = locations;
		this.names = names;
		this.notify = false;
		this.backEvent = null;
		
	}
	
	/**
	 * Creazione di n agenti in diverse locazioni con notifica
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param nAgents
	 * @param locations
	 * @param names
	 * @param notify
	 * @param backEvent
	 */
	public ELDAEventCreate(ELDAId source, String activeStateURL, Object[] params,int nAgents,
			Vector<String> locations, Vector<ELDAId> names, boolean notify,
			ELDAEventCreateNotify backEvent) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.nAgents = nAgents;
		this.locations = locations;
		this.names = names;
		this.notify = notify;
		this.backEvent = backEvent;
		this.params=params;
	}
	
	/**
	 * Creazione remota senza notifica  
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param names
	 * @param notify
	 * @param backEvent
	 */
	public ELDAEventCreate(ELDAId source, String activeStateURL,Object[] params, String location,
			ELDAId agentId) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.nAgents = 1;
		this.locations.add(location);
		this.names.add(agentId);
		this.notify = false;
		this.backEvent = null;
		this.params=params;
	}
	
	/**
	 * Creazione remota con notifica  
	 * @param source
	 * @param activeStateURL
	 * @param params
	 * @param names
	 * @param notify
	 * @param backEvent
	 */
	public ELDAEventCreate(ELDAId source, String activeStateURL,Object[] params, String location,
			ELDAId agentId, boolean notify, ELDAEventCreateNotify backEvent) {
		super(source, source);
		this.activeStateURL = activeStateURL;
		this.nAgents = 1;
		this.locations.add(location);
		this.names.add(agentId);
		this.notify = notify;
		this.backEvent = backEvent;
		this.params=params;
	}
	
	
	
	

	public String getActiveState() {
		return activeStateURL;
	}

	public int getNAgents() {
		return nAgents;
	}

	public Vector<String> getLocations() {
		return locations;
	}

	public Vector<ELDAId> getNames() {
		return names;
	}

	public boolean getNotify() {
		return notify;
	}

	public ELDAEventCreateNotify getBackEvent() {
		return backEvent;
	}

	public Object[] getParams() {
		return params;
	}

}
