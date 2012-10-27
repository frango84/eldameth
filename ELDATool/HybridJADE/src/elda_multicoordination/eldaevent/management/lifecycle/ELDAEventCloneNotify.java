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
* Event used to notify that an agent was cloned.
* 
* @author G. Fortino, F. Rango
*/
public class ELDAEventCloneNotify extends ELDAEventLifecycle {
	
	/**
	 * AID of the cloned agent
	 */
	private AID clonedAgentAID;

	/**
	 * Constructor of the event.
	 * Sender and receiver of this event are automatically specified
	 * when the event is sent.
	 */
 	public ELDAEventCloneNotify(){
  		super(null, null);
  		
  		//il mittente ed il destinatario di questo evento vengono
		//settati automaticamente al momento dell'invio dell'evento
		//(altrimenti, se li settiamo adesso e poi l'agente destinatario
  		//viene clonato, questo evento viene ricevuto solo dal destinatario
  		//indicato e non dal suo clone)
 		
 		setLanguage(ELDAEvent.IN);
	}

	public AID getClonedAgentAID() {
		return clonedAgentAID;
	}

	public void setClonedAgentAID(AID clonedAgentAID) {
		this.clonedAgentAID = clonedAgentAID;
	}

}