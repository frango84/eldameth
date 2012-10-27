/*****************************************************************
ELDATool
Copyright (C) 2012 G. Fortino

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

package eldasim.lowlevelevent;

import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.ELDAEvent;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldasim.agent.SimELDA;

public class MovedAgent extends ELDAEventMove implements LowLevelEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimELDA simELDA;

	public MovedAgent(ELDAId agent, SimELDA simELDA, ELDAEvent reactivation) {
		super(agent, null, reactivation);
		this.simELDA = simELDA;
		// TODO Auto-generated constructor stub
	}

	public SimELDA getSimELDA() {
		return simELDA;
	}

}
