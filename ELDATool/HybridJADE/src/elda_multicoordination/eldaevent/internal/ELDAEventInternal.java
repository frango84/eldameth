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

package elda_multicoordination.eldaevent.internal;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import jade.core.*;
import elda_multicoordination.eldaevent.ELDAEvent;

/**
 * This event is sent and received by the same agent.
 * To receive this event before the others, the agent has to put it in front of the message queue.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventInternal extends ELDAEvent {
 	
	/**
	 * Constructor of the event.
	 * @param source Sender and receiver of the event
	 */
 	public ELDAEventInternal(AID source){
 		super(source, null);
 		
 		//set receiver = sender
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.IN);
 	}

}