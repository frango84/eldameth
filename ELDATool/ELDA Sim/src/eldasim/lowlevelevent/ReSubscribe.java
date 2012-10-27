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
import eldaframework.eldaevent.coordination.p_s.ELDAEventSubscribe;

public class ReSubscribe extends ELDAEventSubscribe implements LowLevelEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReSubscribe(ELDAId source) {
		super(source, null, null);
		// TODO Auto-generated constructor stub
	}

}
