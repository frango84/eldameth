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

package eldasim.gui.simulatorsetup.guifactory;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;

class GUIComponent {
	private JLabel label;
	private JComponent component;

	private Vector<String> dependentComps;

	public GUIComponent(JLabel label, JComponent component) {
		this(label, component, new Vector<String>());
	}

	public GUIComponent(JLabel label, JComponent component, String dependentComps) {
		this(label, component, new Vector<String>());

		this.dependentComps.add(dependentComps);
	}

	public GUIComponent(JLabel label, JComponent component, Vector<String> dependentComps) {
		this.label = label;
		this.component = component;
		this.dependentComps = dependentComps;
	}

	public JLabel getLabel() {
		return label;
	}

	public JComponent getComponent() {
		return component;
	}

	public Vector<String> getDependentComps() {
		return dependentComps;
	}
}
