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
 * DistilledStateChartPerformativeTransition: transition defined to match a specific performative.
 * 
 * @author G. Fortino, F. Rango
 */
public class DistilledStateChartPerformativeTransition extends DistilledStateChartTransition {
	
	private int thePerformative = 0;

	/** Default constructor.
	 * @param aName the name of the transition.
	 * @param tgt the target behaviour.
	 * @param perf the performative that we will check.
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC.
	 */
	public DistilledStateChartPerformativeTransition(String aName, Behaviour tgt, int perf) {
		super(aName, tgt);
		thePerformative = perf;
	}
	
	/**
	 * Constructor that sets also the history.
	 * @param aName a string name of the transition.
	 * @param tgt the target behaviour.
	 * @param history history type to use for target state.
	 * @param perf the performative that we will check.
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC or if we set shallow or deep history and target state
	 * isn't a <code>DistilledStateChartBehaviour</code>.
	 */
	public DistilledStateChartPerformativeTransition(String aName, Behaviour tgt, int history, int perf) {
		super(aName, tgt, history);
		thePerformative = perf;
	}

	/** This constructor assumes that the name and target will be set later.
	 * @param aPerformative The performative that we will match.
	 */
	public DistilledStateChartPerformativeTransition(int aPerformative) {
		super();
		thePerformative = aPerformative;
	}

	/** This constructor uses the Integer version of the performative.
	 * @param aPerformative The performative that we will match.
	 */
	public DistilledStateChartPerformativeTransition(Integer aPerformative) {
		super();
		thePerformative = aPerformative.intValue();
	}

	/** Trigger that watches for specified performative.
	 * @param source The behaviour that is the source of the transition.
	 * @param msg The ACLMessage that we check to see if it has a performative match.
	 * @return A boolean indicating that the transition is ready to fire.
	 */
	public boolean trigger(Behaviour source, ACLMessage msg) {
		boolean test = false;
		if (msg != null && msg.getPerformative() == thePerformative) {
			test = true;
		}
		//printInfo(3, getTransitionName() + ".trigger, test=" + test + ", msg=" + msg);
		return test;
	}

	/** Return the performative associated with this transition.
	 * @return The performative.
	 */
	public int getPerformative() {
		return thePerformative;
	}

	/** Set the associated performative of this transition.
	 * @param p The new performative.
	 */
	public void setPerformative(int p) {
		thePerformative = p;
	}
}