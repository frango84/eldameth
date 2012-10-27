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

import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import eldasim.gui.simulatorsetup.guiconfigurator.CheckBox;
import eldasim.gui.simulatorsetup.guiconfigurator.ComboBox;
import eldasim.gui.simulatorsetup.guiconfigurator.ComboBoxOption;
import eldasim.gui.simulatorsetup.guiconfigurator.Component;
import eldasim.gui.simulatorsetup.guiconfigurator.TextBox;

class GUIComponentFactory {
	public static GUIComponent getGUIComponent(Component comp) throws IllegalArgumentException {
		JLabel label = new JLabel();
		label.setText(comp.getName());
		label.setVisible(comp.isVisible());

		if (comp instanceof TextBox)
			return new GUIComponent(label, getTextBox((TextBox) comp));
		else if (comp instanceof ComboBox)
			return new GUIComponent(label, getComboBox((ComboBox) comp), ((ComboBox) comp).getSelectedItemField());
		else if (comp instanceof CheckBox)
			return new GUIComponent(label, getCheckBox((CheckBox) comp), ((CheckBox) comp).getDependentComps());
		else
			throw new IllegalArgumentException();
	}

	private static JComponent getTextBox(TextBox comp) {
		JTextField textBox = comp.isPasswordField() ? new JPasswordField() : new JTextField();

		textBox.setName(comp.getId());
		textBox.setToolTipText(comp.getDescription());
		textBox.setEnabled(comp.isEnable());
		textBox.setVisible(comp.isVisible());

		Dimension size = new Dimension(150, 20);
		textBox.setPreferredSize(size);
		textBox.setMinimumSize(size);

		if (comp.getDefaulValue() != null)
			textBox.setText(comp.getDefaulValue());

		return textBox;
	}

	private static JComponent getComboBox(ComboBox comp) {
		JComboBox comboBox = new JComboBox();

		comboBox.setName(comp.getId());
		comboBox.setToolTipText(comp.getDescription());
		comboBox.setEnabled(comp.isEnable());
		comboBox.setVisible(comp.isVisible());

		Dimension size = new Dimension(150, 20);
		comboBox.setPreferredSize(size);
		comboBox.setMinimumSize(size);
		
		Iterator<ComboBoxOption> iter = comp.getComboBoxOptions().values().iterator();
		while (iter.hasNext())
			comboBox.addItem(iter.next());

		if (comp.getDefaulValue() != null)
			comboBox.setSelectedItem(comp.getComboBoxOptions().get(comp.getDefaulValue()));

		return comboBox;
	}

	private static JComponent getCheckBox(CheckBox comp) {
		JCheckBox checkBox = new JCheckBox();

		checkBox.setName(comp.getId());
		checkBox.setToolTipText(comp.getDescription());
		checkBox.setEnabled(comp.isEnable());
		checkBox.setVisible(comp.isVisible());

		if (comp.getDefaulValue() != null)
			checkBox.setSelected(new Boolean(comp.getDefaulValue()));

		return checkBox;
	}
}
