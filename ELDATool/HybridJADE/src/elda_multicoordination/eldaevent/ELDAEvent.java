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

package elda_multicoordination.eldaevent;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import jade.core.*;

/**
 * Generic ELDA event.
 * To use this event, you have to insert it inside an ACLMessage through the "setContentObject" method.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEvent implements java.io.Serializable {
	
	//ATTENZIONE: per usare questo tipo di evento, bisogna inserirlo all'interno di un ACLMessage
	//tramite il metodo "setContentObject"; inoltre, le variabili di questo evento (e delle sue
	//sottoclassi) devono essere serializzabili

	/**
	 * Sender of the event
	 */
	private AID source;
	
	/**
	 * Receivers of the event
	 */
	private java.util.List<AID> target;
	
	/**
	 * Language of the event. It's set to ELDAEvent.OUT (for OUT-events) or to
	 * ELDAEvent.IN (for IN-events), so it's possible to know what events must be received
	 * by the ELDAEventHandler and what events must be received by the DistilledStateChartBehaviour.
	 */
	private String language;

	public static final String IN = "ELDA_IN"; //used to indicate an IN-event
	public static final String OUT = "ELDA_OUT"; //used to indicate an OUT-event

	/**
	 * Constructor of the event.
	 * @param source Sender of the event
	 * @param target Receivers of the event
	 */
	public ELDAEvent(AID source, java.util.List<AID> target){
		this.source = source;
		this.target = target;
	}

	public AID getSource() {
		return source;
	}

	public void setSource(AID source) {
		this.source = source;
	}

	public java.util.List<AID> getTarget() {
		return target;
	}

	public void setTarget(java.util.List<AID> target) {
		this.target = target;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}