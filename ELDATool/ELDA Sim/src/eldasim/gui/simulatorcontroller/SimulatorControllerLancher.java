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

package eldasim.gui.simulatorcontroller;

import javax.swing.SwingUtilities;

import eldasim.gui.simulatorcontroller.controller.SimulatorController;

public class SimulatorControllerLancher {
	public static void startGUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SimulatorController();
			}
		});
	}
}
