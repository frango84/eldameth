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

package eldasim.gui.simulatorsetup.guiconfigurator;

import java.util.Vector;

public class GUIConfiguration {
	private String globalConfigWindowTitle;
	private String simulationConfigWindowTitle;

	private Vector<Tab> globalConfigTabs;
	private Vector<Tab> simulationConfigTabs;

	GUIConfiguration() {
		globalConfigTabs = new Vector<Tab>();
		simulationConfigTabs = new Vector<Tab>();
	}

	public String getGlobalConfigWindowTitle() {
		return globalConfigWindowTitle;
	}

	void setGlobalConfigWindowTitle(String title) {
		globalConfigWindowTitle = title;
	}

	public String getSimulationConfigWindowTitle() {
		return simulationConfigWindowTitle;
	}

	void setSimulationConfigWindowTitle(String title) {
		simulationConfigWindowTitle = title;
	}

	public Vector<Tab> getGlobalConfigTabs() {
		return globalConfigTabs;
	}

	void addGlobalConfigTab(Tab tab) {
		globalConfigTabs.add(tab);
	}

	public Vector<Tab> getSimulationConfigTabs() {
		return simulationConfigTabs;
	}

	void addSimulationConfigTab(Tab tab) {
		simulationConfigTabs.add(tab);
	}
}
