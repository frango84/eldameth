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
 * Event used for agent waiting.
 * The agents that receive this event must wait.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventWaitRequest extends ELDAEventLifecycle {

 	/**
 	 * The time to wait (in milliseconds)
 	 */ 
 	private long time;

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param agentsToWait Agents that must wait
	 * @param time The time to wait (in milliseconds)
	 */
	public ELDAEventWaitRequest(AID source, java.util.List<AID> agentsToWait, long time){
	 	super(source, agentsToWait); //target = agents that must wait
	 	
 		setLanguage(ELDAEvent.OUT);

	 	this.time = time;
	}
	
	/**
	 * Constructor that allows to wait the sender of the event.
	 * @param source Sender of the event
	 */
	public ELDAEventWaitRequest(AID source, long time){
  		this(source, null, time); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}