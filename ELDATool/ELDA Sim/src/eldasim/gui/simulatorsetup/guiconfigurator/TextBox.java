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

public class TextBox extends Component {
	private boolean isPasswordField;

	TextBox(String id, String name) {
		super(id, name);
	}

	TextBox(String id, String name, String description) {
		super(id, name, description);
	}

	TextBox(String id, String name, String description, boolean visible, boolean enable, String defaultValue) {
		super(id, name, description, visible, enable, defaultValue);
	}

	public boolean isPasswordField() {
		return isPasswordField;
	}

	void setPasswordField(boolean isPasswordField) {
		this.isPasswordField = isPasswordField;
	}
}
