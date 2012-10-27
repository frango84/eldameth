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

import eldaframework.eldaevent.management.lifecycle.ELDAEventClone;
import eldasim.agent.SimELDA;

public class RemoteClone extends ELDAEventClone implements LowLevelEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ELDAEventClone evt;
	SimELDA simELDA;

	public RemoteClone(ELDAEventClone evt2, SimELDA simELDA2) {
		super(null);
		this.evt = evt2;
		this.simELDA = simELDA2;

	}

	public ELDAEventClone getEvt() {
		return evt;
	}

	public SimELDA getSimELDA() {
		return simELDA;
	}
}
