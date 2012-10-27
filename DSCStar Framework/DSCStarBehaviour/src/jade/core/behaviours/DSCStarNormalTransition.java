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

import jade.lang.acl.*;

/**
 * DSCStarNormalTransition: normal transition for DSC*.
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCStarNormalTransition implements java.io.Serializable {
	
	//Questa è la superclasse di tutte le transizioni dei DSC*.
	//Nei DSC*, le transizioni normali (cioè, NON di tipo "*"/"<<R>>") NON possono utilizzare la storia.
	
	private Behaviour sourceState; //stato sorgente della transizione
	
	private Behaviour targetState; //stato di destinazione della transizione
	
	private String name; //nome della transizione
	
	/**
	 * Constructor.
	 * @param name name of the transition.
	 * @param sourceState the source state of the transition.
	 * @param targetState the target state of the transition.
	 */
	public DSCStarNormalTransition(String name, Behaviour sourceState, Behaviour targetState) {
		this.name = name;
		this.sourceState = sourceState;
		this.targetState = targetState;
	}
	
	/** This method represents the action of the transition.
	 * Usually this method will be overridden.
	 * @param msg the ACLMessage that caused the transition to fire.
	 * @param sourceVariablesAndParameters object that contains variables and parameters of source DSCStarBehaviour.
	 * @param rootVariablesAndParameters object that contains global variables and parameters of root DSCStarBehaviour.
	 */
	public void action(ACLMessage msg, DSCStarVariablesAndParameters sourceVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters){
		//Questo metodo rappresenta l'azione di una transizione per DSC* e viene normalmente ridefinito.
		//Il parametro "sourceVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri
		//del DSCStarBehaviour di origine della transizione (se lo stato sorgente della transizione è uno
		//stato semplice, il DSCStarBehaviour di origine della transizione è rappresentato dallo stato
		//composto che contiene lo stato sorgente della transizione).
		//Il parametro "rootVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri globali
		//del DSCStarBehaviour root.
	}

	/** This method is used to test if the transition can fire.
	 * Usually this method will be overridden.
	 * @param source The behaviour that is the source of the transition.
	 * @param msg the ACLMessage to test.
	 * @param sourceVariablesAndParameters object that contains variables and parameters of source DSCStarBehaviour.
	 * @param rootVariablesAndParameters object that contains global variables and parameters of root DSCStarBehaviour.
	 * @return true if the transition can fire, false otherwise.
	 */
	public boolean trigger(Behaviour source, ACLMessage msg, DSCStarVariablesAndParameters sourceVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters){
		
		//Questo metodo rappresenta il trigger/guardia di una transizione per DSC* e viene normalmente ridefinito.
		//Il parametro "sourceVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri
		//del DSCStarBehaviour di origine della transizione (se lo stato sorgente della transizione è uno
		//stato semplice, il DSCStarBehaviour di origine della transizione è rappresentato dallo stato
		//composto che contiene lo stato sorgente della transizione).
		//Il parametro "rootVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri globali
		//del DSCStarBehaviour root.
		
		if (msg == null) {
			return false;
		}
		return true;
	}

	public Behaviour getSourceState() {
		return sourceState;
	}

	public void setSourceState(Behaviour sourceState) {
		this.sourceState = sourceState;
	}

	public Behaviour getTargetState() {
		return targetState;
	}

	public void setTargetState(Behaviour targetState) {
		this.targetState = targetState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
