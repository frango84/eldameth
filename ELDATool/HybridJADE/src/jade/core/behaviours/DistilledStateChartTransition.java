/*****************************************************************
"DistilledStateChartBehaviour" is a work based on the library "HSMBehaviour"
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
 * DistilledStateChartTransition: basic transition class.
 * 
 * @author G. Fortino, F. Rango
 */
public class DistilledStateChartTransition implements java.io.Serializable {

	private Behaviour targetState = null;
	private String myName = null;
	private int history; //indica se la transizione utilizza la storia (superficiale o profonda) per lo stato destinazione
	
	/** Indicates that the transition doesn't use history for its target state **/
	public static final int NO_HISTORY = 0; //indica che la transizione NON utilizza la storia per lo stato destinazione
	
	/** Indicates that the transition uses shallow history for its target state **/
	public static final int SHALLOW_HISTORY = 1; //indica che la transizione utilizza la storia superficiale per lo stato destinazione
	
	/** Indicates that the transition uses deep history for its target state **/
	public static final int DEEP_HISTORY = 2; //indica che la transizione utilizza la storia profonda per lo stato destinazione

	/**
	 * Default constructor. Source, target, and name must be set manually.
	 */
	public DistilledStateChartTransition() {}

	/**
	 * Default constructor. The code requires that the behaviour that you are adding
	 * has already been added as a state in the parent <code>DistilledStateChartBehaviour</code>.
	 * It will throw a runtime exception if you have not done this.
	 * @param aName a String name of the transition
	 * @param tgt the target behaviour
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC.
	 */
	public DistilledStateChartTransition(String aName, Behaviour tgt) {
		setTransitionName(aName);
		setHistory(DistilledStateChartTransition.NO_HISTORY); //senza storia
		setTarget(tgt);
	}

	/**
	 * Constructor that sets also the history.
	 * @param aName a string name of the transition
	 * @param tgt the target behaviour
	 * @param history history type to use for target state
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC or if we set shallow or deep history and target state
	 * isn't a <code>DistilledStateChartBehaviour</code>
	 */
	public DistilledStateChartTransition(String aName, Behaviour tgt, int history) {
		
		//costruttore per settare anche la storia
		//parametro "Behaviour tgt" = stato di destinazione (di tipo "DistilledStateChartBehaviour") di cui si vuole analizzare la storia (è lo stato che contiene il connettore di storia)
		
		setTransitionName(aName);
		setHistory(history);
		setTarget(tgt);
	}

	/** Test legal containment.
	 * @param state The state to verify that it is legal. Must either be a
	 * <code>DistilledStateChartBehaviour</code> itself, or its parent must be a
	 * <code>DistilledStateChartBehaviour</code>.
	 * @return boolean indicating if the state is legal (true) or not (false)
	 */
	private boolean legalState(Behaviour state) {
		if (state instanceof DistilledStateChartBehaviour)
			return true;
		if (state.parent instanceof DistilledStateChartBehaviour)
			return true;
		return false;
	}

	/** This method is invoked when a transition is firing.
	 * Usually this method will be overridden.
	 * @param msg the ACLMessage that caused the transition to fire.
	 */
	public void action(ACLMessage msg) {}

	/** This method is used to test if the transition can fire.
	 * Naturally, this method is normally overridden.
	 * @param source The behaviour that is the source of the transition.
	 * @param msg the ACLMessage to test. The msg could be a <code>DistilledStateChartEvent</code> object.
	 * @return true if we are to trigger on the message, false otherwise.
	 */
	public boolean trigger(Behaviour source, ACLMessage msg) {
		if (msg == null) {
			return false;
		}
		return true;
	}

	/** Return the name of the transition.
	 * @return the name.
	 */
	public String getTransitionName() {
		return myName;
	}

	/** Set the name of the transition.
	 * @param n the name of the transition.
	 */
	public void setTransitionName(String n) {
		myName = n;
	}

	/** Return the behaviour that is assigned as the target of the transition.
	 * @return the target behaviour.
	 */
	public Behaviour getTarget() {
		return targetState;
	}

	/** Set the target state.
	 * @param aTarget the target state.
	 * @throws RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC or if we set shallow or deep history and target state
	 * isn't a <code>DistilledStateChartBehaviour</code>.
	 */
	public void setTarget(Behaviour aTarget) {
		if (aTarget != null && !legalState(aTarget)) {
			throw new RuntimeException("ERROR: Target state or state.parent must be a DistilledStateChartBehaviour!");
		}
		else if (aTarget != null && (getHistory() != DistilledStateChartTransition.NO_HISTORY) && ((aTarget instanceof DistilledStateChartBehaviour) == false)) {
			//se la transizione deve utilizzare la storia per lo stato destinazione e
			//quest'ultimo non è un DistilledStateChartBehaviour, allora viene lanciata un'eccezione
			//(perchè i connettori di storia devono essere contenuti solo in stati composti,
			//cioè in DistilledStateChartBehaviour)
			throw new RuntimeException("ERROR: target state of a transition that uses history must be a DistilledStateChartBehaviour!");
		}
		
		targetState = aTarget;
	}

	/** Return the history type.
	 * @return The history type.
	 */
	public int getHistory() {
		return history;
	}

	/** Set the history type.
	 * @param history The history type.
	 */
	public void setHistory(int history) {
		this.history = history;
	}
	
}
