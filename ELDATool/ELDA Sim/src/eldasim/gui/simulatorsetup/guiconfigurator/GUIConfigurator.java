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

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import eldasim.gui.simulatorsetup.exception.GUIConfigException;
import eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer.PropertiesFacilitator;
import eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer.PropertiesInitializer;

public class GUIConfigurator {
	private static String globalConfigGUICompsXMLFile;
	private static String simConfigGUICompsXMLFile;

	public static GUIConfiguration getGUIConfiguration() throws GUIConfigException {
		boolean initialized = PropertiesInitializer.initializeProjectProps();

		if (!initialized)
			return null;

		GUIConfiguration config = new GUIConfiguration();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(true);

		String path = PropertiesFacilitator.getProperty(PropertiesInitializer.PROJECT_HOME_PATH) + "\\"
				+ PropertiesFacilitator.getProperty(PropertiesInitializer.GUI_CONFIG_DIR);
		globalConfigGUICompsXMLFile = path + "\\" + PropertiesFacilitator.getProperty(PropertiesInitializer.GUI_COMPS_GLOBAL_CONFIG);
		simConfigGUICompsXMLFile = path + "\\" + PropertiesFacilitator.getProperty(PropertiesInitializer.GUI_COMPS_SIM_CONFIG);

		try {
			factory.newSAXParser().parse(new File(globalConfigGUICompsXMLFile), new XMLParsingHandler(config, false));
			factory.newSAXParser().parse(new File(simConfigGUICompsXMLFile), new XMLParsingHandler(config, true));
		} catch (Exception e) {
			throw new GUIConfigException(e);
		}

		return config;
	}

	private static class XMLParsingHandler extends DefaultHandler {
		private static final int TEXTBOX = 0;
		private static final int COMBOBOX = 1;
		private static final int CHECKBOX = 2;

		private final String WINDOW_TAG = "Window";
		private final String TAB_TAG = "Tab";
		private final String NAME_TAG = "Name";
		private final String DESCRIPTION_TAG = "Description";
		private final String COMPONENTS_TAG = "Components";
		private final String COMPONENT_TAG = "Component";
		private final String TEXTBOX_TAG = "TextBox";
		private final String COMBOBOX_TAG = "ComboBox";
		private final String CB_OPTION_TAG = "CB_Option";
		private final String CB_OPTION_DESCR_TAG = "CB_Option_Description";
		private final String DEPENDENT_COMPONENT_TAG = "DependentComponent";
		private final String CHECKBOX_TAG = "CheckBox";

		private final String TITLE_ATTRIB = "title";
		private final String VISIBLE_ATTRIB = "visible";
		private final String ENABLE_ATTRIB = "enable";
		private final String DEFAULT_VALUE_ATTRIB = "defaultValue";
		private final String IS_PASSWORD_FIELD_ATTRIB = "passwordField";
		private final String ID_ATTRIB = "id";
		private final String SELECTED_ITEM_FIELD_ATTRIB = "selectedItemField";

		private Tab tab;

		private String id;
		private boolean visible;
		private boolean enable;
		private String name;
		private String description;
		private int type;
		private String defaultValue;

		private boolean isPasswordField;
		private String comboBoxOptionId;
		private String comboBoxOptionDescr;
		private String comboBoxSelectionItemField;
		private Hashtable<String, ComboBoxOption> comboBoxOptions;
		private Vector<String> dependentComps;

		private GUIConfiguration config;
		private boolean simConfiguration;
		private boolean paramDefinition;

		private String currentTag;

		public XMLParsingHandler(GUIConfiguration config, boolean simConfiguration) {
			this.config = config;
			this.simConfiguration = simConfiguration;

			paramDefinition = false;
			restoreDefaultCompInfo();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			currentTag = localName;

			if (currentTag.equalsIgnoreCase(WINDOW_TAG) && attributes.getValue(TITLE_ATTRIB) != null) {
				if (!simConfiguration)
					config.setGlobalConfigWindowTitle(attributes.getValue(TITLE_ATTRIB));
				else
					config.setSimulationConfigWindowTitle(attributes.getValue(TITLE_ATTRIB));

			} else if (currentTag.equalsIgnoreCase(COMPONENTS_TAG))
				paramDefinition = true;
			else if (currentTag.equalsIgnoreCase(COMPONENT_TAG)) {
				id = attributes.getValue(ID_ATTRIB);
				if (attributes.getValue(VISIBLE_ATTRIB) != null)
					visible = attributes.getValue(VISIBLE_ATTRIB).equals("true") ? true : false;
				if (attributes.getValue(ENABLE_ATTRIB) != null)
					enable = attributes.getValue(ENABLE_ATTRIB).equals("true") ? true : false;

			} else if (currentTag.equalsIgnoreCase(TEXTBOX_TAG)) {
				type = TEXTBOX;
				if (attributes.getValue(IS_PASSWORD_FIELD_ATTRIB) != null)
					isPasswordField = attributes.getValue(IS_PASSWORD_FIELD_ATTRIB).equals("true") ? true : false;

			} else if (currentTag.equalsIgnoreCase(COMBOBOX_TAG)) {
				type = COMBOBOX;
				comboBoxSelectionItemField = attributes.getValue(SELECTED_ITEM_FIELD_ATTRIB);

			} else if (currentTag.equalsIgnoreCase(CB_OPTION_TAG))
				comboBoxOptionId = attributes.getValue(ID_ATTRIB);
			else if (currentTag.equalsIgnoreCase(DEPENDENT_COMPONENT_TAG)) {
				if (dependentComps == null)
					dependentComps = new Vector<String>();
				dependentComps.add(attributes.getValue(ID_ATTRIB));

			} else if (currentTag.equalsIgnoreCase(CHECKBOX_TAG))
				type = CHECKBOX;

			if (type != -1 && attributes.getValue(DEFAULT_VALUE_ATTRIB) != null)
				defaultValue = attributes.getValue(DEFAULT_VALUE_ATTRIB);
		}

		@Override
		public void endElement(String uri, String localName, String qName) {
			currentTag = localName;

			if (currentTag.equalsIgnoreCase(COMPONENTS_TAG))
				paramDefinition = false;
			else if (currentTag.equalsIgnoreCase(COMPONENT_TAG))
				createComponent();
			else if (currentTag.equalsIgnoreCase(CB_OPTION_TAG))
				createComboBoxOption();
			else if (currentTag.equalsIgnoreCase(TAB_TAG)) {
				if (!simConfiguration)
					config.addGlobalConfigTab(tab);
				else
					config.addSimulationConfigTab(tab);

				tab = null;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) {
			String element = new String(ch, start, length);

			if (!element.trim().equals(""))
				if (!paramDefinition) {
					if (currentTag.equalsIgnoreCase(NAME_TAG))
						tab = new Tab(element);
					else if (currentTag.equalsIgnoreCase(DESCRIPTION_TAG))
						tab.setDescrition(element);

				} else if (currentTag.equalsIgnoreCase(NAME_TAG))
					name = element;
				else if (currentTag.equalsIgnoreCase(DESCRIPTION_TAG))
					description = element;
				else if (currentTag.equalsIgnoreCase(CB_OPTION_DESCR_TAG))
					comboBoxOptionDescr = element;
		}

		private void createComponent() {
			Component comp = null;

			switch (type) {
				case TEXTBOX:
					comp = new TextBox(id, name, description, visible, enable, defaultValue);
					((TextBox) comp).setPasswordField(isPasswordField);
					break;

				case COMBOBOX:
					comp = new ComboBox(id, name, description, visible, enable, defaultValue);
					((ComboBox) comp).setSelectedItemField(comboBoxSelectionItemField);

					if (comboBoxOptions != null)
						((ComboBox) comp).setComboBoxOptions(comboBoxOptions);
					break;

				case CHECKBOX:
					comp = new CheckBox(id, name, description, visible, enable, defaultValue);
					if (dependentComps != null)
						((CheckBox) comp).setDependentComps(dependentComps);
			}

			tab.addComponent(comp);

			restoreDefaultCompInfo();
		}

		private void createComboBoxOption() {
			ComboBoxOption opt = new ComboBoxOption(comboBoxOptionId, comboBoxOptionDescr);
			if (dependentComps != null)
				opt.setDependentComps(dependentComps);

			if (comboBoxOptions == null)
				comboBoxOptions = new Hashtable<String, ComboBoxOption>();

			comboBoxOptions.put(comboBoxOptionId, opt);

			comboBoxOptionId = null;
			comboBoxOptionDescr = null;
			dependentComps = null;
		}

		private void restoreDefaultCompInfo() {
			id = null;
			visible = true;
			enable = true;
			name = null;
			description = null;
			type = -1;
			defaultValue = null;

			isPasswordField = false;
			comboBoxOptions = null;
			comboBoxSelectionItemField = null;
			dependentComps = null;
		}
	}
}
