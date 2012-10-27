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

package eldasim.gui.simulatorsetup.simconfigurator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JComponent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.wutka.dtd.DTD;
import com.wutka.dtd.DTDAny;
import com.wutka.dtd.DTDAttribute;
import com.wutka.dtd.DTDCardinal;
import com.wutka.dtd.DTDChoice;
import com.wutka.dtd.DTDDecl;
import com.wutka.dtd.DTDElement;
import com.wutka.dtd.DTDEmpty;
import com.wutka.dtd.DTDEnumeration;
import com.wutka.dtd.DTDItem;
import com.wutka.dtd.DTDMixed;
import com.wutka.dtd.DTDName;
import com.wutka.dtd.DTDPCData;
import com.wutka.dtd.DTDParser;
import com.wutka.dtd.DTDSequence;

import eldasim.gui.simulatorsetup.exception.InvalidConfigParamValueException;
import eldasim.gui.simulatorsetup.exception.MissingConfigParamException;
import eldasim.gui.simulatorsetup.exception.SimInitException;
import eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer.PropertiesFacilitator;
import eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer.PropertiesInitializer;
import eldasim.gui.simulatorsetup.guifactory.GUIComponentAnalyzer;

public class SimConfigurator {
	private static String globalConfigDTDFile;
	private static String simConfigDTDFile;

	private static String configurationDTDFile;
	private static String configurationXMLFile;

	private static TransformerHandler handler;
	private static DTD dtd;

	private static Hashtable<String, JComponent> guiComponents;
	private static Hashtable<String, Queue<String>> multipleElementsID;
	private static String multipleElementName;

	public static void createSimConfigFile(Hashtable<String, JComponent> guiComps) throws SimInitException {
		guiComponents = guiComps;

		String path = PropertiesFacilitator.getProperty(PropertiesInitializer.PROJECT_HOME_PATH) + "\\"
				+ PropertiesFacilitator.getProperty(PropertiesInitializer.CONFIG_DIR);
		globalConfigDTDFile = path + "\\" + PropertiesFacilitator.getProperty(PropertiesInitializer.GLOBAL_CONFIG);
		simConfigDTDFile = path + "\\" + PropertiesFacilitator.getProperty(PropertiesInitializer.SIM_CONFIG);
		configurationXMLFile = path + "\\" + PropertiesFacilitator.getProperty(PropertiesInitializer.OUTPUT_CONFIG_FILE_NAME);
		configurationDTDFile = configurationXMLFile.substring(0, configurationXMLFile.lastIndexOf(".")) + ".dtd";

		try {
			mergeDTDFile(configurationDTDFile, globalConfigDTDFile, simConfigDTDFile);
			createXMLConfigFile(configurationXMLFile, configurationDTDFile);
		} catch (Exception e) {
			throw new SimInitException(e);
		}
	}

	private static void mergeDTDFile(String newDTDFileName, String DTDFileName_1, String DTDFileName_2) throws IOException {
		readDTDFile(DTDFileName_1);
		DTDElement rootElement_1 = (DTDElement) dtd.items.get(0);

		readDTDFile(DTDFileName_2);
		DTDElement rootElement_2 = (DTDElement) dtd.items.get(0);

		File newDTDFile = new File(newDTDFileName);
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(newDTDFile));

		String rootElement = "<!ELEMENT Configuration (" + rootElement_1.getName() + "," + rootElement_2.getName() + ")>\n";
		fileWriter.write(rootElement);

		appendFile(new File(DTDFileName_1), fileWriter);
		appendFile(new File(DTDFileName_2), fileWriter);

		fileWriter.close();
	}

	private static void appendFile(File sourceFile, BufferedWriter destFile) throws IOException {
		BufferedReader fileReader = new BufferedReader(new FileReader(sourceFile));

		destFile.write("\n\t");
		String str = null;

		while ((str = fileReader.readLine()) != null)
			destFile.write(str + "\n\t");

		fileReader.close();
	}

	private static void createXMLConfigFile(String XMLFileName, String DTDFileName) throws SimInitException, IOException,
			TransformerConfigurationException, SAXException, TransformerFactoryConfigurationError, MissingConfigParamException,
			InvalidConfigParamValueException {
		readDTDFile(DTDFileName);
		createXMLFile(XMLFileName, DTDFileName);
	}

	private static void readDTDFile(String DTDFileName) throws IOException {
		File DTDFile = new File(DTDFileName);
		DTDParser dtdParser = new DTDParser(DTDFile);

		dtd = dtdParser.parse();
	}

	private static void createXMLFile(String XMLFileName, String DTDFileName) throws SAXException, TransformerConfigurationException,
			FileNotFoundException, TransformerFactoryConfigurationError, MissingConfigParamException, InvalidConfigParamValueException {
		initializeXMLTransformerHandler(XMLFileName, DTDFileName);

		handler.startDocument();
		DTDElement rootElement = (DTDElement) dtd.items.get(0);
		analizeElement(rootElement);
		handler.endDocument();
	}

	private static void initializeXMLTransformerHandler(String XMLFileName, String DTDFileName) throws TransformerConfigurationException,
			TransformerFactoryConfigurationError, FileNotFoundException {
		DTDFileName = DTDFileName.substring(DTDFileName.lastIndexOf('\\') + 1);

		handler = ((SAXTransformerFactory) SAXTransformerFactory.newInstance()).newTransformerHandler();

		Transformer serializer = handler.getTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DTDFileName);
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");

		File XMLFile = new File(XMLFileName);
		handler.setResult(new StreamResult(new FileOutputStream(XMLFile)));
	}

	private static void analizeElement(DTDElement element) throws SAXException, MissingConfigParamException, InvalidConfigParamValueException {
		handler.startElement("", "", element.getName(), getElementAttributes(element));
		exploreElementContent(element);
		handler.endElement("", "", element.getName());
	}

	private static AttributesImpl getElementAttributes(DTDElement element) throws MissingConfigParamException,
			InvalidConfigParamValueException {
		AttributesImpl elementAttributes = new AttributesImpl();
		Enumeration<String> attList = element.attributes.keys();

		while (attList.hasMoreElements()) {
			DTDAttribute attr = element.getAttribute(attList.nextElement());

			String value = "";
			String guiParamName = element.getName() + "." + attr.getName();

			if (multipleElementName != null)
				guiParamName = multipleElementsID.get(multipleElementName).peek() + "_" + guiParamName;

			if (attr.getDecl().equals(DTDDecl.FIXED))
				value = attr.getDefaultValue();
			else if (attr.getDecl().equals(DTDDecl.IMPLIED)) {
				if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled())
					value = GUIComponentAnalyzer.getComponentValue(guiComponents.get(guiParamName));
				else
					continue;

			} else if (attr.getDecl().equals(DTDDecl.REQUIRED)) {
				if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled())
					value = GUIComponentAnalyzer.getComponentValue(guiComponents.get(guiParamName));
				else
					throw new MissingConfigParamException(guiParamName);

			} else if (attr.getDecl().equals(DTDDecl.VALUE))
				if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled())
					value = GUIComponentAnalyzer.getComponentValue(guiComponents.get(guiParamName));
				else
					value = attr.getDefaultValue();

			if (attr.getType() instanceof DTDEnumeration && !((DTDEnumeration) attr.getType()).getItemsVec().contains(value))
				throw new InvalidConfigParamValueException(guiParamName + "=" + value);

			elementAttributes.addAttribute("", "", attr.getName(), "", value);
		}

		return elementAttributes;
	}

	private static void exploreElementContent(DTDElement element) throws SAXException, MissingConfigParamException,
			InvalidConfigParamValueException {
		String guiParamName = element.getName();
		DTDItem content = element.getContent();

		if (multipleElementName != null)
			guiParamName = multipleElementsID.get(multipleElementName).peek() + "_" + guiParamName;

		if (content instanceof DTDEmpty)
			return;

		else if (content instanceof DTDAny)
			throw new IllegalArgumentException(element.getName() + ": ANY DTD-Element is not supported!");

		else if (content instanceof DTDMixed) {
			DTDItem[] items = ((DTDMixed) element.getContent()).getItems();

			for (int i = 0; i < items.length; i++)
				if (items[i] instanceof DTDPCData)
					if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled()) {
						String value = GUIComponentAnalyzer.getComponentValue(guiComponents.get(guiParamName));
						handler.characters(value.toCharArray(), 0, value.length());
						break;
					} else
						throw new MissingConfigParamException(guiParamName);

		} else if (content instanceof DTDSequence) {
			DTDItem[] items = ((DTDSequence) element.getContent()).getItems();

			for (int i = 0; i < items.length; i++)
				if (items[i] instanceof DTDName) {
					DTDName dtdName = (DTDName) items[i];
					DTDElement elem = getDTDElement(dtdName.getValue());

					analizeElement(elem, dtdName.getCardinal());
				}

		} else if (content instanceof DTDChoice) {
			DTDItem[] items = ((DTDChoice) element.getContent()).getItems();

			if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled()) {
				String value = GUIComponentAnalyzer.getComponentValue(guiComponents.get(guiParamName));

				for (int i = 0; i < items.length; i++)
					if (items[i] instanceof DTDName) {
						String dtdName = ((DTDName) items[i]).getValue();
						if (dtdName.equals(value) || dtdName.equals(guiParamName + ":" + value)) {
							analizeElement(getDTDElement(dtdName));
							return;
						}
					}

				throw new InvalidConfigParamValueException(guiParamName + "=" + value);
			} else
				throw new MissingConfigParamException(guiParamName);
		}
	}

	private static void analizeElement(DTDElement element, DTDCardinal cardinal) throws SAXException, MissingConfigParamException,
			InvalidConfigParamValueException {
		if (cardinal.equals(DTDCardinal.NONE))
			analizeElement(element);
		else if (cardinal.equals(DTDCardinal.OPTIONAL)) {
			if (isPresent(element))
				analizeElement(element);

		} else if (cardinal.equals(DTDCardinal.ONEMANY) || cardinal.equals(DTDCardinal.ZEROMANY))
			analizeMultipleElement(element, cardinal);
	}

	private static boolean isPresent(DTDElement optionalElement) {
		try {
			if (getElementAttributes(optionalElement).getLength() > 0)
				return true;
		} catch (MissingConfigParamException e) {
			return false;
		} catch (InvalidConfigParamValueException e) {
		}

		String guiParamName = optionalElement.getName();
		DTDItem content = optionalElement.getContent();

		if (multipleElementName != null)
			guiParamName = multipleElementsID.get(multipleElementName).peek() + "_" + guiParamName;

		if (content instanceof DTDMixed) {
			DTDItem[] items = ((DTDMixed) optionalElement.getContent()).getItems();

			for (int i = 0; i < items.length; i++)
				if (items[i] instanceof DTDPCData)
					if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled())
						return true;
					else
						return false;

		} else if (content instanceof DTDSequence) {
			DTDItem[] items = ((DTDSequence) optionalElement.getContent()).getItems();

			for (int i = 0; i < items.length; i++)
				if (items[i] instanceof DTDName) {
					DTDName dtdName = (DTDName) items[i];
					DTDElement elem = getDTDElement(dtdName.getValue());

					if (dtdName.getCardinal().equals(DTDCardinal.NONE))
						return isPresent(elem);
					else if (dtdName.getCardinal().equals(DTDCardinal.OPTIONAL)) {
						if (isPresent(elem))
							return true;

					} else if (dtdName.getCardinal().equals(DTDCardinal.ONEMANY) || dtdName.getCardinal().equals(DTDCardinal.ZEROMANY)) {
						boolean result = false;

						try {
							initializeMultipleElementsID(elem, dtdName.getCardinal());
							result = !multipleElementsID.get(elem.getName()).isEmpty();
						} catch (Exception e) {
						}

						multipleElementsID.remove(elem.getName());
						return result;
					}
				}

		} else if (content instanceof DTDChoice)
			if (guiComponents.containsKey(guiParamName) && guiComponents.get(guiParamName).isEnabled())
				return true;
			else
				return false;

		return false;
	}

	private static void analizeMultipleElement(DTDElement multipleElement, DTDCardinal cardinal) throws SAXException,
			MissingConfigParamException, InvalidConfigParamValueException {
		initializeMultipleElementsID(multipleElement, cardinal);

		while (!multipleElementsID.get(multipleElement.getName()).isEmpty()) {
			multipleElementName = multipleElement.getName();
			analizeElement(multipleElement);
			multipleElementsID.get(multipleElement.getName()).poll();
		}

		multipleElementsID.remove(multipleElement.getName());
		multipleElementName = null;
	}

	private static void initializeMultipleElementsID(DTDElement multipleElement, DTDCardinal cardinal) throws SAXException,
			MissingConfigParamException {
		if (multipleElementsID == null)
			multipleElementsID = new Hashtable<String, Queue<String>>();

		multipleElementsID.put(multipleElement.getName(), new LinkedList<String>());

		String guiParamName = null;
		Enumeration<String> attList = multipleElement.attributes.keys();

		while (attList.hasMoreElements()) {
			DTDAttribute attr = multipleElement.getAttribute(attList.nextElement());

			if (attr.getType().equals("ID") && attr.getDecl().equals(DTDDecl.REQUIRED)) {
				guiParamName = multipleElement.getName() + "." + attr.getName();
				break;
			}
		}

		if (guiParamName == null)
			throw new SAXException(multipleElement.getName() + ": no #REQUIRED ID attribute present!");

		if (multipleElementName != null)
			guiParamName = multipleElementsID.get(multipleElementName).peek() + "_" + guiParamName;

		Enumeration<String> enumer = guiComponents.keys();

		while (enumer.hasMoreElements()) {
			String key = enumer.nextElement();

			if (key.contains("_" + guiParamName) && guiComponents.get(key).isEnabled()) {
				String elementID = GUIComponentAnalyzer.getComponentValue(guiComponents.get(key));
				multipleElementsID.get(multipleElement.getName()).offer(elementID);
			}
		}

		if (multipleElementsID.get(multipleElement.getName()).isEmpty() && cardinal.equals(DTDCardinal.ONEMANY))
			throw new MissingConfigParamException(guiParamName);
	}

	private static DTDElement getDTDElement(String name) {
		for (int i = 0; i < dtd.getItems().length; i++)
			if (dtd.getItem(i) instanceof DTDElement && ((DTDElement) dtd.getItem(i)).getName().equals(name))
				return (DTDElement) dtd.getItem(i);

		return null;
	}
}
