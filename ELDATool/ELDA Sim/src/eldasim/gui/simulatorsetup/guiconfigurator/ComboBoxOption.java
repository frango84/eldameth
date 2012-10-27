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

public class ComboBoxOption {
	private String id;
	private String descr;
	private Vector<String> dependentComps;

	ComboBoxOption(String id, String descr) {
		this(id, descr, new Vector<String>());
	}

	ComboBoxOption(String id, String descr, Vector<String> dependentComps) {
		this.id = id;
		this.descr = descr;
		this.dependentComps = dependentComps;
	}

	public String getId() {
		return id;
	}

	public String getDescr() {
		return descr;
	}

	public Vector<String> getDependentComps() {
		return dependentComps;
	}

	void setDependentComps(Vector<String> dependentComps) {
		this.dependentComps = dependentComps;
	}

	@Override
	public String toString() {
		return descr;
	}
}
