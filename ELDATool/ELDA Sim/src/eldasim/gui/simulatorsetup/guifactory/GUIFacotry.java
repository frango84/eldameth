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

package eldasim.gui.simulatorsetup.guifactory;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import eldasim.gui.simulatorsetup.exception.GUIInitException;
import eldasim.gui.simulatorsetup.guiconfigurator.ComboBoxOption;
import eldasim.gui.simulatorsetup.guiconfigurator.Component;
import eldasim.gui.simulatorsetup.guiconfigurator.GUIConfiguration;
import eldasim.gui.simulatorsetup.guiconfigurator.Tab;
import eldasim.gui.simulatorsetup.simconfigurator.SimConfigurator;
import eldasim.gui.util.ExceptionDialog;

public class GUIFacotry extends JFrame {
	private static final long serialVersionUID = 1L;

	private GUIConfiguration config;
	private int compNum;

	private static final String WINDOW_TITLE = "Simulation Setup";
	private static final int GLOBAL_CONFIG_PANEL = 0;
	private static final int SIMULATION_CONFIG_PANEL = 1;

	private int selectedPanel;

	private JPanel jContentPane = null;
	private JPanel northPanel = null;
	private JPanel southPanel = null;

	private JTabbedPane globalConfigTabs = null;
	private JTabbedPane simulationConfigTabs = null;

	private JButton nextButton = null;
	private JButton createButton = null;
	private JButton abortButton = null;

	private Hashtable<String, JComponent> guiComponents;

	private GUIFacotry(GUIConfiguration config) throws GUIInitException {
		super();

		this.config = config;
		this.guiComponents = new Hashtable<String, JComponent>();

		initialize();
	}

	private void initialize() throws GUIInitException {
		try {
			setTitle(WINDOW_TITLE);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setContentPane(getJContentPane());
			setResizable(false);
			packAndSetOptimalDimension();

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			Point center = new Point((dimension.width - getSize().width) / 2, (dimension.height - getSize().height) / 2);
			setLocation(center);

			setVisible(true);
		} catch (Exception e) {
			throw new GUIInitException(e);
		}
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());

			GridBagConstraints northPanelConstraints = new GridBagConstraints();
			northPanelConstraints.gridx = 0;
			northPanelConstraints.gridy = 0;
			northPanelConstraints.weightx = 1.0;
			northPanelConstraints.weighty = 1.0;
			northPanelConstraints.fill = GridBagConstraints.BOTH;
			northPanelConstraints.insets = new Insets(10, 10, 10, 10);

			GridBagConstraints southPanelConstraints = new GridBagConstraints();
			southPanelConstraints.gridx = 0;
			southPanelConstraints.gridy = 1;
			southPanelConstraints.weightx = 1.0;
			southPanelConstraints.weighty = 1.0;
			southPanelConstraints.anchor = GridBagConstraints.SOUTHEAST;
			southPanelConstraints.insets = new Insets(10, 10, 10, 10);

			jContentPane.add(getNorthPanel(), northPanelConstraints);
			jContentPane.add(getSouthPanel(), southPanelConstraints);
		}

		return jContentPane;
	}

	private JPanel getNorthPanel() {
		if (northPanel == null) {
			northPanel = new JPanel();
			northPanel.setLayout(new GridBagLayout());

			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);

			northPanel.add(getGlobalConfigurationTabs(), gridBagConstraints);
			northPanel.add(getSimulationConfigurationTabs(), gridBagConstraints);
		}

		return northPanel;
	}

	private JTabbedPane getGlobalConfigurationTabs() {
		if (globalConfigTabs == null)
			globalConfigTabs = new JTabbedPane();

		Iterator<Tab> iter = config.getGlobalConfigTabs().iterator();

		while (iter.hasNext()) {
			Tab tab = iter.next();
			globalConfigTabs.addTab(tab.getName(), null, createPanel(tab), tab.getDescrition());
		}

		return globalConfigTabs;
	}

	private JTabbedPane getSimulationConfigurationTabs() {
		if (simulationConfigTabs == null)
			simulationConfigTabs = new JTabbedPane();

		Iterator<Tab> iter = config.getSimulationConfigTabs().iterator();

		while (iter.hasNext()) {
			Tab tab = iter.next();
			simulationConfigTabs.addTab(tab.getName(), null, createPanel(tab), tab.getDescrition());
		}

		return simulationConfigTabs;
	}

	private JPanel createPanel(Tab tab) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		Vector<GUIComponent> guiComps = convertToGUIComps(tab.getComponents());

		for (compNum = 0; compNum < guiComps.size(); compNum++)
			addComponentsToPanel(panel, guiComps.get(compNum));

		addActionListeners(guiComps);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(15, 15, 15, 15);

		mainPanel.add(panel, gridBagConstraints);

		return mainPanel;
	}

	private Vector<GUIComponent> convertToGUIComps(Vector<Component> comps) {
		Vector<GUIComponent> guiComps = new Vector<GUIComponent>();

		for (int i = 0; i < comps.size(); i++)
			guiComps.add(GUIComponentFactory.getGUIComponent(comps.get(i)));

		return guiComps;
	}

	private void addComponentsToPanel(JPanel panel, GUIComponent component) {
		panel.add(component.getLabel(), getComponentConstraints(true));
		panel.add(component.getComponent(), getComponentConstraints(false));

		guiComponents.put(component.getComponent().getName(), component.getComponent());
	}

	private GridBagConstraints getComponentConstraints(boolean isLabel) {
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = isLabel ? 0 : 1;
		gridBagConstraints.gridy = compNum;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.anchor = isLabel ? GridBagConstraints.EAST : GridBagConstraints.WEST;
		gridBagConstraints.insets = isLabel ? new Insets(5, 5, 5, 10) : new Insets(5, 5, 5, 5);

		return gridBagConstraints;
	}

	private void addActionListeners(Vector<GUIComponent> comps) {
		for (int i = 0; i < comps.size(); i++) {
			GUIComponent component = comps.get(i);

			if (component.getComponent() instanceof JComboBox) {
				JComboBox comboBox = (JComboBox) component.getComponent();
				comboBox.addActionListener(new ComboBoxSelectedItemFieldInitializer(comboBox.getName(), component.getDependentComps()
						.firstElement()));

				for (int k = 0; k < comboBox.getItemCount(); k++)
					if (!((ComboBoxOption) comboBox.getItemAt(k)).getDependentComps().isEmpty()) {
						comboBox.addActionListener(new ComboBoxDependentCompsActivator(comboBox.getName()));
						break;
					}

			} else if (component.getComponent() instanceof JCheckBox) {
				JCheckBox checkBox = (JCheckBox) component.getComponent();

				if (!component.getDependentComps().isEmpty())
					checkBox.addActionListener(new CheckBoxDependentCompsActivator(component.getDependentComps()));
			}
		}
	}

	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setLayout(new GridBagLayout());

			GridBagConstraints nextButtonConstraints = new GridBagConstraints();
			nextButtonConstraints.gridx = 0;
			nextButtonConstraints.gridy = 0;
			nextButtonConstraints.weightx = 1.0;
			nextButtonConstraints.weighty = 1.0;
			nextButtonConstraints.insets = new Insets(0, 0, 0, 10);

			GridBagConstraints startButtonConstraints = new GridBagConstraints();
			startButtonConstraints.gridx = 1;
			startButtonConstraints.gridy = 0;
			startButtonConstraints.weightx = 1.0;
			startButtonConstraints.weighty = 1.0;
			startButtonConstraints.insets = new Insets(0, 5, 0, 5);

			GridBagConstraints abortButtonConstraints = new GridBagConstraints();
			abortButtonConstraints.gridx = 2;
			abortButtonConstraints.gridy = 0;
			abortButtonConstraints.weightx = 1.0;
			abortButtonConstraints.weighty = 1.0;
			abortButtonConstraints.insets = new Insets(0, 5, 0, 0);

			southPanel.add(getNextButton(), nextButtonConstraints);
			southPanel.add(getCreateButton(), startButtonConstraints);
			southPanel.add(getAbortButton(), abortButtonConstraints);
		}

		return southPanel;
	}

	private JButton getNextButton() {
		if (nextButton == null) {
			nextButton = new JButton();
			nextButton.setText("Next >");

			nextButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (selectedPanel) {
						case GLOBAL_CONFIG_PANEL:
							selectPanel(SIMULATION_CONFIG_PANEL);
							nextButton.setText("< Back");
							createButton.setEnabled(true);
							break;

						case SIMULATION_CONFIG_PANEL:
							selectPanel(GLOBAL_CONFIG_PANEL);
							nextButton.setText("Next >");
							createButton.setEnabled(false);
							break;
					}
				}
			});
		}

		return nextButton;
	}

	private JButton getAbortButton() {
		if (abortButton == null) {
			abortButton = new JButton();
			abortButton.setText("Abort");

			abortButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (showConfirmMessage("Do you want to abort setup?"))
						dispose();
				}
			});
		}

		return abortButton;
	}

	private JButton getCreateButton() {
		if (createButton == null) {
			createButton = new JButton();
			createButton.setText("Generate");
			createButton.setEnabled(false);

			createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (checkCorrectSimInit())
						try {
							SimConfigurator.createSimConfigFile(guiComponents);
							showInfoMessage("XML configuration file successfully created.");
						} catch (Exception ex) {
							showExceptionMessage(ex);
						} finally {
							dispose();
						}
					else
						showErrorMessage("One or more parameters are not initialized!");
				}
			});
		}

		return createButton;
	}

	private boolean checkCorrectSimInit() {
		Enumeration<JComponent> enumer = guiComponents.elements();

		while (enumer.hasMoreElements()) {
			JComponent component = enumer.nextElement();

			if (!component.isEnabled() || !component.isVisible())
				continue;

			if ((component instanceof JTextField && ((JTextField) component).getText().equals(""))
					|| (component instanceof JComboBox && ((JComboBox) component).getSelectedItem().toString().equals("")))
				return false;
		}

		return true;
	}

	private void showExceptionMessage(Throwable ex) {
		new ExceptionDialog().showExceptionMessage(this, ex, WINDOW_TITLE + ": Exception");
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, WINDOW_TITLE + ": Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showInfoMessage(String message) {
		JOptionPane.showMessageDialog(this, message, WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean showConfirmMessage(String message) {
		if (JOptionPane.showConfirmDialog(this, message, WINDOW_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			return true;

		return false;
	}

	private void selectPanel(int panel) {
		selectedPanel = panel;

		switch (selectedPanel) {
			case GLOBAL_CONFIG_PANEL:
				northPanel.setBorder(BorderFactory.createTitledBorder(null, config.getGlobalConfigWindowTitle(),
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), null));
				globalConfigTabs.setVisible(true);
				simulationConfigTabs.setVisible(false);
				break;

			case SIMULATION_CONFIG_PANEL:
				northPanel.setBorder(BorderFactory.createTitledBorder(null, config.getSimulationConfigWindowTitle(),
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), null));
				globalConfigTabs.setVisible(false);
				simulationConfigTabs.setVisible(true);
				break;
		}
	}

	private void packAndSetOptimalDimension() {
		selectPanel(GLOBAL_CONFIG_PANEL);
		pack();
		Dimension globalTabDim = northPanel.getSize();

		selectPanel(SIMULATION_CONFIG_PANEL);
		pack();
		Dimension simTabDim = northPanel.getSize();

		double width = globalTabDim.getWidth() > simTabDim.getWidth() ? globalTabDim.getWidth() : simTabDim.getWidth();
		double height = globalTabDim.getHeight() > simTabDim.getHeight() ? globalTabDim.getHeight() : simTabDim.getHeight();
		Dimension size = new Dimension((int) width, (int) height);

		northPanel.setPreferredSize(size);
		northPanel.setMinimumSize(size);

		selectPanel(GLOBAL_CONFIG_PANEL);
	}

	// Action Listeners
	private class ComboBoxSelectedItemFieldInitializer implements ActionListener {
		private String guiComponentName;
		private String selectedItemField;

		public ComboBoxSelectedItemFieldInitializer(String guiComponentName, String selectedItemField) {
			this.guiComponentName = guiComponentName;
			this.selectedItemField = selectedItemField;

			updateGUIStatus();
		}

		public void actionPerformed(ActionEvent e) {
			updateGUIStatus();
		}

		private void updateGUIStatus() {
			JComboBox comboBox = (JComboBox) guiComponents.get(guiComponentName);
			ComboBoxOption selectedItem = (ComboBoxOption) comboBox.getSelectedItem();

			JTextField textField = (JTextField) guiComponents.get(selectedItemField);
			textField.setText(selectedItem.getDescr());
		}
	}

	private class ComboBoxDependentCompsActivator implements ActionListener {
		private String guiComponentName;
		private ComboBoxOption previousSelectedItem;

		public ComboBoxDependentCompsActivator(String guiComponentName) {
			this.guiComponentName = guiComponentName;

			updateGUIStatus();
		}

		public void actionPerformed(ActionEvent e) {
			updateGUIStatus();
		}

		private void updateGUIStatus() {
			JComboBox comboBox = (JComboBox) guiComponents.get(guiComponentName);
			ComboBoxOption selectedItem = (ComboBoxOption) comboBox.getSelectedItem();

			if (previousSelectedItem != null)
				changeElementState(previousSelectedItem.getDependentComps());

			changeElementState(selectedItem.getDependentComps());

			previousSelectedItem = selectedItem;
		}

		private void changeElementState(Vector<String> dependentParams) {
			Iterator<String> iter = dependentParams.iterator();

			while (iter.hasNext()) {
				JComponent guiElement = guiComponents.get(iter.next());
				guiElement.setEnabled(!guiElement.isEnabled());
			}
		}
	}

	private class CheckBoxDependentCompsActivator implements ActionListener {
		private Vector<String> dependentComps;

		public CheckBoxDependentCompsActivator(Vector<String> dependentComps) {
			this.dependentComps = dependentComps;

			updateGUIStatus();
		}

		public void actionPerformed(ActionEvent e) {
			updateGUIStatus();
		}

		private void updateGUIStatus() {
			Iterator<String> iter = dependentComps.iterator();

			while (iter.hasNext()) {
				JComponent guiElement = guiComponents.get(iter.next());
				guiElement.setEnabled(!guiElement.isEnabled());
			}
		}
	}

	// static methods
	public static void createNewGUI(GUIConfiguration config) throws GUIInitException {
		new GUIFacotry(config);
	}
}
