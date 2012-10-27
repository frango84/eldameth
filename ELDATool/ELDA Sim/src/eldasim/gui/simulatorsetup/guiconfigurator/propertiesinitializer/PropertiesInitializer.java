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

package eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public class PropertiesInitializer {
	private static final String PROPERTIES_FILENAME = "Properties.xml";

	public static final String PROJECT_HOME_PATH = "ProjectHomePath";
	public static final String GUI_CONFIG_DIR = "GUIConfigDir";
	public static final String GUI_COMPS_GLOBAL_CONFIG = "GUICompsGlobalConfig";
	public static final String GUI_COMPS_SIM_CONFIG = "GUICompsSimConfig";
	public static final String CONFIG_DIR = "ConfigDir";
	public static final String GLOBAL_CONFIG = "GlobalConfig";
	public static final String SIM_CONFIG = "SimConfig";
	public static final String OUTPUT_CONFIG_FILE_NAME = "OutputConfigFileName";

	public static boolean initializeProjectProps() {
		while (true) {
			String projectHomePath = openDirectoryChooser("Select project main directory");

			if (projectHomePath == null)
				return false;

			try {
				PropertiesFacilitator.loadProperties(projectHomePath + "\\" + PROPERTIES_FILENAME);
				PropertiesFacilitator.addProperty(PROJECT_HOME_PATH, projectHomePath);

				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "File Properties.xml not found or invalid format!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private static String openDirectoryChooser(String title) {
		String path = null;

		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
		JFileChooser chooser = new JFileChooser(new File(wsroot.getLocation().toOSString()));
		chooser.setDialogTitle(title);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showDialog(null, "Select") == JFileChooser.APPROVE_OPTION)
			path = chooser.getSelectedFile().getAbsolutePath();

		return path;
	}
}
