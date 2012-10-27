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

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perch� vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event used to wake up the agents (if they are waiting).
 * The agents that receive this event must wake up.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventWakeUp extends ELDAEventLifecycle {

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param agentsToWakeUp Agents to wake up
	 */
	public ELDAEventWakeUp(AID source, java.util.List<AID> agentsToWakeUp){
	 	super(source, agentsToWakeUp); //target = agents that must wake up
	 	
	 	setLanguage(ELDAEvent.OUT);
	}
	
	/**
	 * Constructor that allows to wake up the sender of the event.
	 * @param source Sender of the event
	 */
	public ELDAEventWakeUp(AID source){
  		this(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
	}

}