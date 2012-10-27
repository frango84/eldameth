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

import java.io.File;
import java.util.Stack;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import eldasim.gui.simulatorcontroller.exception.SimulatorInitException;
import eldasim.gui.util.RuntimeClassLoader;
import eldasim.setup.MASSimulation;

class SimulatorInitializer {
	public static MASSimulation initialize(String classPath, String simulationClassName) throws SimulatorInitException {
		try {
			RuntimeClassLoader.initializeClassLoader(SimulatorInitializer.class.getClassLoader(), classPath);
			Class simulation = RuntimeClassLoader.getClassLoader().loadClass(simulationClassName);

			if (!simulation.getSuperclass().equals(MASSimulation.class))
				throw new SimulatorInitException();

			return (MASSimulation) simulation.newInstance();
		} catch (Exception e) {
			throw new SimulatorInitException(e);
		}
	}

	public static XMLTree loadConfiguration(String configurationFileName) throws SimulatorInitException {
		XMLTree xmlTree = new XMLTree();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);

		try {
			factory.newSAXParser().parse(new File(configurationFileName), new XMLParsingHandler(xmlTree));
		} catch (Exception e) {
			throw new SimulatorInitException(e);
		}

		return xmlTree;
	}

	private static class XMLParsingHandler extends DefaultHandler {
		private XMLTree xmlTree;
		private Stack<XMLNode> stack;

		public XMLParsingHandler(XMLTree xmlTree) {
			this.xmlTree = xmlTree;
			this.stack = new Stack<XMLNode>();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			XMLNode node = new XMLNode(localName);

			for (int i = 0; i < attributes.getLength(); i++)
				node.addAttrib(attributes.getQName(i), attributes.getValue(i));

			if (!stack.isEmpty())
				stack.peek().addChild(node);

			stack.push(node);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			XMLNode node = stack.pop();

			if (stack.isEmpty())
				xmlTree.addRoot(node);
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			String content = new String(ch, start, length);
			stack.peek().setContent(content);
		}
	}
}
