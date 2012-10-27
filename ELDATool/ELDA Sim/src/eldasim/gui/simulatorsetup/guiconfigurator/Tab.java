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

public class Tab {
	private String name;
	private Vector<Component> components;
	private String descrition;

	Tab(String name) {
		this(name, new Vector<Component>());
	}

	Tab(String name, Vector<Component> components) {
		this(name, components, null);
	}

	Tab(String name, Vector<Component> components, String description) {
		this.name = name;
		this.components = components;
		this.descrition = description;
	}

	public String getName() {
		return name;
	}

	public Vector<Component> getComponents() {
		return components;
	}

	void addComponent(Component comp) {
		components.add(comp);
	}

	public String getDescrition() {
		return descrition;
	}

	void setDescrition(String descrition) {
		this.descrition = descrition;
	}
}
