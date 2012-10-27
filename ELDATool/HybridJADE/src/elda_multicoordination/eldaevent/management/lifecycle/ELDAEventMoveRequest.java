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
 * Event used for agent migration.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventMoveRequest extends ELDAEventLifecycle {

 	/**
 	 * Migration destination
 	 */ 
 	private jade.core.Location destination;
 	
 	/**
 	 * Eventual event to send when the agent has migrated
 	 */ 
 	private ELDAEvent reactivationEvent;
 	
 	/**
 	 * AID of the agent to move
 	 */ 
 	private AID agentToMove;

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param agentToMove AID of the agent to move
	 * @param destination Migration destination
	 * @param reactivationEvent Eventual event to send when the agent has migrated
	 */
	public ELDAEventMoveRequest(AID source, AID agentToMove, jade.core.Location destination, ELDAEvent reactivationEvent){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
	 	
 		this.agentToMove = agentToMove;
	 	this.destination = destination;
	 	this.reactivationEvent = reactivationEvent;
	}
	
	/**
	 * Constructor without reactivation event.
	 * @param source Sender of the event
	 * @param agentToMove AID of the agent to move
	 * @param destination Migration destination
	 */
	public ELDAEventMoveRequest(AID source, AID agentToMove, jade.core.Location destination){
  		this(source, agentToMove, destination, null);
	}
	
	/**
	 * Constructor that allows to move the sender of the event.
	 * @param source Sender of the event
	 * @param destination Migration destination
	 * @param reactivationEvent Eventual event to send when the agent has migrated
	 */
	public ELDAEventMoveRequest(AID source, jade.core.Location destination, ELDAEvent reactivationEvent){
		this(source, source, destination, reactivationEvent);
	}
	
	/**
	 * Constructor that allows to move the sender of the event without reactivation event.
	 * @param source Sender of the event
	 * @param destination Migration destination
	 */
	public ELDAEventMoveRequest(AID source, jade.core.Location destination){
		this(source, source, destination, null);
	}

	public jade.core.Location getDestination() {
		return destination;
	}

	public void setDestination(jade.core.Location destination) {
		this.destination = destination;
	}

	public ELDAEvent getReactivationEvent() {
		return reactivationEvent;
	}

	public void setReactivationEvent(ELDAEvent reactivationEvent) {
		this.reactivationEvent = reactivationEvent;
	}

	public AID getAgentToMove() {
		return agentToMove;
	}

	public void setAgentToMove(AID agentToMove) {
		this.agentToMove = agentToMove;
	}

}