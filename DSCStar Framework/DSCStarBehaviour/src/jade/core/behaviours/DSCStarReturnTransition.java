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
 * DSCStarReturnTransition: return transition "<<R>>" for DSC*.
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCStarReturnTransition extends DSCStarNormalTransition {
	
	/**
	 * Constructor.
	 * @param sourceState source state of the transition (it's a composite state of DSC*, that is a DSCStarBehaviour).
	 * @param targetState target state of the transition (it's a composite state of DSC*, that is a DSCStarBehaviour). For this state, the history is used automatically.
	 */
	public DSCStarReturnTransition(DSCStarBehaviour sourceState, DSCStarBehaviour targetState) {
		//Lo stato di destinazione e quello sorgente della transizione di tipo "<<R>>" sono degli stati composti, cioè dei "DSCStarBehaviour".
		//Una "DSCStarReturnTransition" viene nominata automaticamente nella forma "ReturnFromX", dove "X" è lo stato composto sorgente della transizione.
		//Per questo tipo di transizione, viene utilizzata automaticamente la storia superficiale per lo stato di destinazione
		super("ReturnFrom" + sourceState.getBehaviourName(), sourceState, targetState);
	}
	
	/** This method is used to test if the transition can fire.
	 * @param source The behaviour that is the source of the transition.
	 * @param msg the ACLMessage to test.
	 * @param sourceVariablesAndParameters object that contains variables and parameters of source DSCStarBehaviour.
	 * @param rootVariablesAndParameters object that contains global variables and parameters of root DSCStarBehaviour.
	 * @return true if the transition can fire, false otherwise.
	 */
	public boolean trigger(Behaviour source, ACLMessage msg, DSCStarVariablesAndParameters sourceVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters) {
		if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getName().equalsIgnoreCase("ReturnFrom" + getSourceState().getBehaviourName()))){
			//le transizioni di tipo "DSCStarReturnTransition" (cioè, di tipo "<<R>>") accettano i
			//"DSCStarEvent" di tipo "ReturnFrom", che hanno un nome di tipo "ReturnFromX", dove "X"
			//è lo stato a cui si riferisce l'evento, cioè lo stato sorgente della transizione
			return true;
		}
		return false;
	}
	
}
