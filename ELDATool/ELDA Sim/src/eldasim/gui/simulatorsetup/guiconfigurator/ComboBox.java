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

import java.util.Hashtable;

public class ComboBox extends Component {
	private Hashtable<String, ComboBoxOption> comboBoxOptions;
	private String selectedItemField;

	ComboBox(String id, String name) {
		this(id, name, null);
	}

	ComboBox(String id, String name, String description) {
		this(id, name, description, true, true, null);
	}

	ComboBox(String id, String name, String description, boolean visible, boolean enable, String defaultValue) {
		super(id, name, description, visible, enable, defaultValue);
		comboBoxOptions = new Hashtable<String, ComboBoxOption>();
	}

	public Hashtable<String, ComboBoxOption> getComboBoxOptions() {
		return comboBoxOptions;
	}

	void setComboBoxOptions(Hashtable<String, ComboBoxOption> comboBoxOptions) {
		this.comboBoxOptions = comboBoxOptions;
	}

	public String getSelectedItemField() {
		return selectedItemField;
	}

	public void setSelectedItemField(String selectedItemField) {
		this.selectedItemField = selectedItemField;
	}
}
