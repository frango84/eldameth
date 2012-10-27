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

package elda_multicoordination.eldaevent.coordination.p_s;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event used for notification in Publish/Subscribe model.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventEVTNotification extends ELDAEventP_S {
	
	/**
	 * Name of the topic related to this notification event
	 */
	private String topic;

 	/**
 	 * Eventual data of the event (it's that published through a previous ELDAEventPublish)
 	 */
	private java.io.Serializable data;
	
	/**
	 * Constructor of the event.
	 * Sender and receiver of this event are automatically specified
	 * when the event is sent.
	 */
 	public ELDAEventEVTNotification(){
  		super(null, null);
  		
  		//il mittente ed il destinatario di questo evento vengono
		//settati automaticamente al momento dell'invio dell'evento
		//(altrimenti, se li settiamo adesso e poi l'agente destinatario
  		//viene clonato, questo evento viene ricevuto solo dal destinatario
  		//indicato e non dal suo clone)
 		
 		setLanguage(ELDAEvent.IN);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public java.io.Serializable getData() {
		return data;
	}

	public void setData(java.io.Serializable data) {
		this.data = data;
	}
	
}