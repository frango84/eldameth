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

package eldasim.gui.simulatorcontroller.controller;

import eldasim.gui.simulatorcontroller.exception.InvalidNodeException;

public class XMLTree {
	private XMLNode root;

	XMLTree() {
	}

	void addRoot(XMLNode root) {
		this.root = root;
	}

	public XMLNode getNode(String path) throws InvalidNodeException {
		int i = path.indexOf('/');

		if (i != -1 && path.substring(0, i).equals(root.getName()))
			return root.getDescendantNode(path.substring(i + 1));

		throw new InvalidNodeException();
	}
}
