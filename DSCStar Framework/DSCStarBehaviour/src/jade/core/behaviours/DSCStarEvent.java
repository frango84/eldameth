/*****************************************************************
"DSCStarBehaviour" is a work based on the library "HSMBehaviour"
(authors: G. Caire, R. Delucchi, M. Griss, R. Kessler, B. Remick).
Changed files: "HSMBehaviour.java", "HSMEvent.java", "HSMPerformativeTransition.java",
"HSMTemplateTransition.java", "HSMTransition.java".
Last change date: 18/06/2010
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

package jade.core.behaviours;

import jade.lang.acl.ACLMessage;

/**
 * DSCStarEvent: it indicates "Call" or "ReturnFrom" or "CPE" or "RPE" or "E" event for DSC*.
 * To send this event, you must use the "postMessage" method (using the "send" method may cause serious problems).
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCStarEvent extends ACLMessage {
	
	//ATTENZIONE: per usare questo tipo di evento, bisogna mandarlo per forza tramite il metodo "postMessage";
	//infatti, quando viene utilizzato il metodo "send" per mandare un evento che estende la classe
	//ACLMessage (come questo evento) e la piattaforma è distribuita (cioè, costituita da più container),
	//l'evento giunge a destinazione come un semplice ACLMessage e non risulta più come istanza della
	//classe originaria che estende ACLMessage.
	
	public static final int CALL = 1; //indica che l'evento è di tipo "Call"
	public static final int RETURN_FROM = 2; //indica che l'evento è di tipo "ReturnFrom"
	public static final int CPE = 3; //indica che l'evento è di tipo "CPE"
	public static final int RPE = 4; //indica che l'evento è di tipo "RPE"
	public static final int E = 5; //indica che l'evento è di tipo "E"
	
	private int type; //indica il tipo di evento per DSC* ("Call" o "ReturnFrom" o "CPE" o "RPE")
	
	private DSCStarBehaviour state; //stato composto (cioè, di tipo "DSCStarBehaviour") a cui si
									//riferisce l'evento:
									//- per l'evento "Call", è lo stato di destinazione della transizione
									//di tipo "*" che si vuole scatenare con questo evento (ad esempio,
									//per la transizione A ---CallB---*---> B, è lo stato "B")
									//- per l'evento "ReturnFrom", è lo stato sorgente della transizione
									//di tipo "<<R>>" che si vuole scatenare con questo evento (ad esempio,
									//per la transizione B ---ReturnFromB--<<R>>--> A(H), è lo stato "B")
									//- per gli eventi "CPE" e "RPE", è lo stato che possiede i parametri
									//contenuti in questi eventi
									//- per l'evento "E", non c'è uno stato di riferimento
	
	private String name; //nome dell'evento:
						//- per l'evento "Call", il nome dell'evento appare nella forma "CallX", dove "X" è lo stato di riferimento dell'evento
						//- per l'evento "ReturnFrom", il nome dell'evento appare nella forma "ReturnFromX", dove "X" è lo stato di riferimento dell'evento
						//- per l'evento "CPE", il nome dell'evento appare nella forma "CPE_X", dove "X" è lo stato di riferimento dell'evento
						//- per l'evento "RPE", il nome dell'evento appare nella forma "RPE_X", dove "X" è lo stato di riferimento dell'evento
						//- per l'evento "E", il nome dell'evento appare nella forma "E"
	
	/**
	 * Constructor.
	 * @param type event type (DSCStarEvent.CALL or DSCStarEvent.RETURN_FROM or DSCStarEvent.CPE or DSCStarEvent.RPE or DSCStarEvent.E).
	 * @param state composite state to which refers the event.
	 */
	public DSCStarEvent(int type, DSCStarBehaviour state){
		super(ACLMessage.UNKNOWN); //setta la performative a "UNKNOWN"
		setType(type);
		setState(state);
		createEventName();
	}
	
	/** Create event name.
	 */
	private void createEventName(){
		if(this.type == DSCStarEvent.CALL){
			setName("Call" + state.getBehaviourName());
		}
		else if(this.type == DSCStarEvent.RETURN_FROM){
			setName("ReturnFrom" + state.getBehaviourName());
		}
		else if(this.type == DSCStarEvent.CPE){
			setName("CPE_" + state.getBehaviourName());
		}
		else if(this.type == DSCStarEvent.RPE){
			setName("RPE_" + state.getBehaviourName());
		}
		else if(this.type == DSCStarEvent.E){
			setName("E");
		}
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public DSCStarBehaviour getState() {
		return state;
	}

	public void setState(DSCStarBehaviour state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
