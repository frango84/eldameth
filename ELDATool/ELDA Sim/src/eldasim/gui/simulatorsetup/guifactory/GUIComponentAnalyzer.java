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

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class GUIComponentAnalyzer {
	public static String getComponentValue(JComponent comp) throws IllegalArgumentException {
		if (comp instanceof JTextField)
			return ((JTextField) comp).getText();
		else if (comp instanceof JComboBox)
			return ((JComboBox) comp).getSelectedItem().toString();
		else if (comp instanceof JCheckBox)
			return ((JCheckBox) comp).isSelected() ? "true" : "false";
		else
			throw new IllegalArgumentException();
	}
}
