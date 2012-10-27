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

package eldasim.gui.simulatorsetup;

import javax.swing.SwingUtilities;

import eldasim.gui.simulatorsetup.exception.GUIConfigException;
import eldasim.gui.simulatorsetup.exception.GUIInitException;
import eldasim.gui.simulatorsetup.guiconfigurator.GUIConfiguration;
import eldasim.gui.simulatorsetup.guiconfigurator.GUIConfigurator;
import eldasim.gui.simulatorsetup.guifactory.GUIFacotry;

public class SimulatorSetupLancher {
	public static void startGUI() {
		try {
			final GUIConfiguration config = GUIConfigurator.getGUIConfiguration();

			if (config == null)
				return;

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						GUIFacotry.createNewGUI(config);
					} catch (GUIInitException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (GUIConfigException e) {
			e.printStackTrace();
		}
	}
}
