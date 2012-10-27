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

public class CheckBox extends Component {
	private Vector<String> dependentComps;

	CheckBox(String id, String name) {
		this(id, name, null);
	}

	CheckBox(String id, String name, String description) {
		this(id, name, description, true, true, null);
	}

	CheckBox(String id, String name, String description, boolean visible, boolean enable, String defaultValue) {
		super(id, name, description, visible, enable, defaultValue);
		dependentComps = new Vector<String>();
	}

	public Vector<String> getDependentComps() {
		return dependentComps;
	}

	void setDependentComps(Vector<String> dependentComps) {
		this.dependentComps = dependentComps;
	}
}
