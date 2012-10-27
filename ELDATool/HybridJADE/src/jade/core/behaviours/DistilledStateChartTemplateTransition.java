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
 * DistilledStateChartTemplateTransition: transition defined to match a specific MessageTemplate.
 * 
 * @author G. Fortino, F. Rango
 */
public class DistilledStateChartTemplateTransition extends DistilledStateChartTransition {

	private MessageTemplate template = null;

	/**
	 * Default constructor. The code requires that the behaviour that you are adding
	 * has already been added as a state in the parent <code>DistilledStateChartBehaviour</code>.
	 * It will throw a runtime exception if you have not done this.
	 * @param aName a String name of the transition
	 * @param tgt the target behaviour
	 * @param aTemplate the MessageTemplate
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC.
	 */
	public DistilledStateChartTemplateTransition(String aName, Behaviour tgt, MessageTemplate aTemplate) {
		super(aName, tgt);
		template = aTemplate;
	}
	
	/**
	 * Constructor that sets also the history.
	 * @param aName a String name of the transition
	 * @param tgt the target behaviour
	 * @param history history type to use for target state
	 * @param aTemplate the MessageTemplate
	 * @exception RuntimeException if we are adding a behaviour that has not
	 * already been assigned to a DSC or if we set shallow or deep history and target state
	 * isn't a <code>DistilledStateChartBehaviour</code>
	 */
	public DistilledStateChartTemplateTransition(String aName, Behaviour tgt, int history, MessageTemplate aTemplate) {
		super(aName, tgt, history);
		template = aTemplate;
	}

	/** Trigger that watches for specified MessageTemplate.
	 * @param source The behaviour that is the source of the transition.
	 * @param msg The ACLMessage that we check to see if it matches.
	 * @return A boolean indicating that the transition is ready to fire.
	 */
	public boolean trigger(Behaviour source, ACLMessage msg) { // should be event
		if (msg == null) {
			return false;
		}
		if (template == null) {
			return false;
		}
		boolean test = template.match(msg);
		//printInfo(3, getTransitionName() + ".trigger, test=" + test + ", msg=" + msg);
		return test;
	}
}
