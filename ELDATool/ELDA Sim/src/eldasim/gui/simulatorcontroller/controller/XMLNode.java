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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import eldasim.gui.simulatorcontroller.exception.InvalidAttributeException;
import eldasim.gui.simulatorcontroller.exception.InvalidNodeException;

public class XMLNode {
	private String name;
	private String content;

	private Hashtable<String, String> attribs;
	private Vector<XMLNode> children;

	XMLNode(String name) {
		this.name = name;

		attribs = new Hashtable<String, String>();
		children = new Vector<XMLNode>();
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	void setContent(String content) {
		this.content = content;
	}

	void addAttrib(String name, String value) {
		attribs.put(name, value);
	}

	public String getAttribValue(String name) throws InvalidAttributeException {
		String value = attribs.get(name);

		if (value == null)
			throw new InvalidAttributeException();

		return value;
	}

	void addChild(XMLNode node) {
		children.add(node);
	}

	public XMLNode getChild(String name) throws InvalidNodeException {
		Iterator<XMLNode> iter = children.iterator();

		while (iter.hasNext()) {
			XMLNode node = iter.next();

			if (node.name.equals(name))
				return node;
		}

		throw new InvalidNodeException();
	}

	public Vector<XMLNode> getChildren() {
		return children;
	}

	XMLNode getDescendantNode(String path) throws InvalidNodeException {
		int i = path.indexOf('/');

		if (i != -1)
			return getChild(path.substring(0, i)).getDescendantNode(path.substring(i + 1));

		return getChild(path);
	}
}
