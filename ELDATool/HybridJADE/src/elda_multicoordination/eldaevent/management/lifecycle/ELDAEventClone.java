/*****************************************************************
Multi-Coordination for JADE agents
Copyright (C) 2010 G. Fortino, F. Rango

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

package elda_multicoordination.eldaevent.management.lifecycle;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event used to clone an agent.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventClone extends ELDAEventLifecycle {
	
	/**
	 * AID of the agent to clone
	 */ 
	private AID agentToClone;

	/**
	 * The location where the cloned agent will start
	 */ 
 	private jade.core.Location destination;
 	
	/**
	 * The name that will be given to the cloned agent
	 */ 
 	private String newName;
 	
	/**
	 * This variable is true if a clonation notification is required
	 */
	private boolean notify;
	
	/**
	 * Clonation notification that will be sent back when the clonation is successfully executed
	 * (it can be null if the variable "notify" is false)
	 */
	private ELDAEventCloneNotify backEvent;

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param agentToClone AID of the agent to clone
	 * @param destination The location where the cloned agent will start
	 * @param newName The name that will be given to the cloned agent
	 * @param notify This parameter is true if a clonation notification is required
	 * @param backEvent Clonation notification that will be sent back when the clonation is successfully executed (it can be null if the parameter "notify" is false)
	 */
	public ELDAEventClone(AID source, AID agentToClone, jade.core.Location destination,
			String newName, boolean notify, ELDAEventCloneNotify backEvent){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
	 	
 		this.agentToClone = agentToClone;
	 	this.destination = destination;
	 	this.newName = newName;
	 	this.notify = notify;
	 	this.backEvent = backEvent;
	}
	
	/**
	 * Constructor without clonation notification.
	 * @param source Sender of the event
	 * @param agentToClone AID of the agent to clone
	 * @param destination The location where the cloned agent will start
	 * @param newName The name that will be given to the cloned agent
	 */
	public ELDAEventClone(AID source, AID agentToClone, jade.core.Location destination, String newName){
  		this(source, agentToClone, destination, newName, false, null);
	}
	
	/**
	 * Constructor that allows to clone the sender of the event.
	 * @param source Sender of the event to clone
	 * @param destination The location where the cloned agent will start
	 * @param newName The name that will be given to the cloned agent
	 * @param notify This parameter is true if a clonation notification is required
	 * @param backEvent Clonation notification that will be sent back when the clonation is successfully executed (it can be null if the parameter "notify" is false)
	 */
	public ELDAEventClone(AID source, jade.core.Location destination,
			String newName, boolean notify, ELDAEventCloneNotify backEvent){
		this(source, source, destination, newName, notify, backEvent);
	}
	
	/**
	 * Constructor that allows to clone the sender of the event without clonation notification.
	 * @param source Sender of the event to clone
	 * @param destination The location where the cloned agent will start
	 * @param newName The name that will be given to the cloned agent
	 */
	public ELDAEventClone(AID source, jade.core.Location destination, String newName){
		this(source, source, destination, newName, false, null);
	}

	public jade.core.Location getDestination() {
		return destination;
	}

	public void setDestination(jade.core.Location destination) {
		this.destination = destination;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public ELDAEventCloneNotify getBackEvent() {
		return backEvent;
	}

	public void setBackEvent(ELDAEventCloneNotify backEvent) {
		this.backEvent = backEvent;
	}

	public AID getAgentToClone() {
		return agentToClone;
	}

	public void setAgentToClone(AID agentToClone) {
		this.agentToClone = agentToClone;
	}

}