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

import jaf.VirtualClock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.eclipse.core.resources.ResourcesPlugin;

import eldasim.gui.simulatorcontroller.exception.SimulatorInitException;
import eldasim.gui.util.ExceptionDialog;

public class SimulatorController extends JFrame {
	private static final long serialVersionUID = 1L;

	private final int SIMULATION_MODE = 0;
	private final int SETUP_MODE = 1;
	private final String WINDOW_TITLE = "ELDASim Controller";

	private JPanel jContentPane = null;
	private JPanel northPanel = null;
	private JPanel centerPanel = null;
	private JPanel southPanel = null;

	private JButton jButton_Play = null;
	private JButton jButton_Pause = null;
	private JButton jButton_Stop = null;

	private JTextField jTextField_Project = null;
	private JTextField jTextField_MainClass = null;
	private JTextField jTextField_Configuration = null;
	private JButton jButton_Project = null;
	private JButton jButton_MainClass = null;
	private JButton jButton_Configuration = null;
	private JButton jButton_Init = null;
	private JLabel jLabel_Project = null;
	private JLabel jLabel_MainClass = null;
	private JLabel jLabel_Configuration = null;

	private JPanel statusSubPanel = null;
	private JLabel jLabel_SimulationTimerLab = null;
	private JLabel jLabel_SimulationTimer = null;
	private JLabel jLabel_SimulationTimerUnit = null;
	private JLabel jLabel_RealTimerLab = null;
	private JLabel jLabel_RealTimer = null;
	private JLabel jLabel_RealTimerUnit = null;
	private JLabel jLabel_SimulationConsole = null;
	private JToggleButton jToggleButton_Console = null;
	private JProgressBar jProgressBar = null;

	private ELDAEngineController simulatorEngineController;

	private Thread simStatusDaemon;
	private Thread simConsoleDaemon;

	public SimulatorController() {
		super();
		initialize();

		SimulatorConsole.startSimulatorConsole();
	}

	private void initialize() {
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				SimulatorConsole.stopSimulatorConsole();
			}
		});

		setContentPane(getJContentPane());
		setResizable(false);
		pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Point center = new Point((dimension.width - getSize().width) / 2, (dimension.height - getSize().height) / 2);
		setLocation(center);

		setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());

			GridBagConstraints northPanelConstraints = new GridBagConstraints();
			northPanelConstraints.gridx = 0;
			northPanelConstraints.gridy = 0;
			northPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
			northPanelConstraints.insets = new Insets(5, 5, 5, 5);

			GridBagConstraints centerPanelConstraints = new GridBagConstraints();
			centerPanelConstraints.gridx = 0;
			centerPanelConstraints.gridy = 1;
			centerPanelConstraints.fill = GridBagConstraints.BOTH;
			centerPanelConstraints.insets = new Insets(5, 5, 5, 5);

			GridBagConstraints southPanelConstraints = new GridBagConstraints();
			southPanelConstraints.gridx = 0;
			southPanelConstraints.gridy = 3;
			southPanelConstraints.fill = GridBagConstraints.BOTH;
			southPanelConstraints.insets = new Insets(5, 5, 5, 5);

			jContentPane.add(getNorthPanel(), northPanelConstraints);
			jContentPane.add(getCenterPanel(), centerPanelConstraints);
			jContentPane.add(getSouthPanel(), southPanelConstraints);
		}

		return jContentPane;
	}

	private JPanel getNorthPanel() {
		if (northPanel == null) {
			northPanel = new JPanel();
			northPanel.setLayout(new GridBagLayout());
			northPanel.setBorder(BorderFactory.createTitledBorder(null, " ELDAEngine Controller ", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			GridBagConstraints playButtonConstraints = new GridBagConstraints();
			playButtonConstraints.gridx = 0;
			playButtonConstraints.gridy = 0;
			playButtonConstraints.insets = new Insets(10, 10, 10, 20);

			GridBagConstraints pauseButtonConstraints = new GridBagConstraints();
			pauseButtonConstraints.gridx = 1;
			pauseButtonConstraints.gridy = 0;
			pauseButtonConstraints.insets = new Insets(10, 10, 10, 10);

			GridBagConstraints stopButtonConstraints = new GridBagConstraints();
			stopButtonConstraints.gridx = 2;
			stopButtonConstraints.gridy = 0;
			stopButtonConstraints.insets = new Insets(10, 20, 10, 10);

			northPanel.add(getJBuotton_Play(), playButtonConstraints);
			northPanel.add(getJButton_Pause(), pauseButtonConstraints);
			northPanel.add(getJButton_Stop(), stopButtonConstraints);
		}

		return northPanel;
	}

	private JButton getJBuotton_Play() {
		if (jButton_Play == null) {
			jButton_Play = new JButton();
			jButton_Play.setText("PLAY");
			jButton_Play.setEnabled(false);

			jButton_Play.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startSimDaemons();
					simulatorEngineController.play();
				}
			});
		}

		return jButton_Play;
	}

	private JButton getJButton_Pause() {
		if (jButton_Pause == null) {
			jButton_Pause = new JButton();
			jButton_Pause.setText("PAUSE");
			jButton_Pause.setEnabled(false);

			jButton_Pause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					simulatorEngineController.pause();
				}
			});
		}

		return jButton_Pause;
	}

	private JButton getJButton_Stop() {
		if (jButton_Stop == null) {
			jButton_Stop = new JButton();
			jButton_Stop.setText("STOP");
			jButton_Stop.setEnabled(false);

			jButton_Stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (simulatorEngineController.getCurrentStatus() == ELDAEngineController.PLAY) {
						simulatorEngineController.pause();

						if (showConfirmMessage("Do you want to STOP simulation?")) {
							simulatorEngineController.stop();
							setButtonStatus(SETUP_MODE);
						} else
							simulatorEngineController.play();
					} else if (simulatorEngineController.getCurrentStatus() == ELDAEngineController.PAUSE) {
						if (showConfirmMessage("Do you want to STOP simulation?")) {
							simulatorEngineController.stop();
							setButtonStatus(SETUP_MODE);
						}
					} else {
						setButtonStatus(SETUP_MODE);
					}
				}
			});
		}

		return jButton_Stop;
	}

	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(new GridBagLayout());
			centerPanel.setBorder(BorderFactory.createTitledBorder(null, " Simulator Configuration ", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			jLabel_Project = new JLabel();
			jLabel_Project.setText("Simulation project");

			jLabel_MainClass = new JLabel();
			jLabel_MainClass.setText("Simulation main class");

			jLabel_Configuration = new JLabel();
			jLabel_Configuration.setText("Simulation configuration");

			GridBagConstraints classPathLabelConstraints = new GridBagConstraints();
			classPathLabelConstraints.gridx = 0;
			classPathLabelConstraints.gridy = 0;
			classPathLabelConstraints.anchor = GridBagConstraints.WEST;
			classPathLabelConstraints.insets = new Insets(10, 10, 0, 0);

			GridBagConstraints classPathTextFieldConstraints = new GridBagConstraints();
			classPathTextFieldConstraints.gridx = 0;
			classPathTextFieldConstraints.gridy = 1;
			classPathTextFieldConstraints.anchor = GridBagConstraints.WEST;
			classPathTextFieldConstraints.insets = new Insets(5, 10, 5, 5);

			GridBagConstraints classPathButtonConstraints = new GridBagConstraints();
			classPathButtonConstraints.gridx = 1;
			classPathButtonConstraints.gridy = 1;
			classPathButtonConstraints.insets = new Insets(5, 5, 5, 10);

			GridBagConstraints mainClassLabelConstraints = new GridBagConstraints();
			mainClassLabelConstraints.gridx = 0;
			mainClassLabelConstraints.gridy = 2;
			mainClassLabelConstraints.anchor = GridBagConstraints.WEST;
			mainClassLabelConstraints.insets = new Insets(10, 10, 0, 0);

			GridBagConstraints mainClassTextFieldConstraints = new GridBagConstraints();
			mainClassTextFieldConstraints.gridx = 0;
			mainClassTextFieldConstraints.gridy = 3;
			mainClassTextFieldConstraints.anchor = GridBagConstraints.WEST;
			mainClassTextFieldConstraints.insets = new Insets(5, 10, 5, 5);

			GridBagConstraints mainClassButtonConstraints = new GridBagConstraints();
			mainClassButtonConstraints.gridx = 1;
			mainClassButtonConstraints.gridy = 3;
			mainClassButtonConstraints.insets = new Insets(5, 5, 5, 10);

			GridBagConstraints configurationLabelConstraints = new GridBagConstraints();
			configurationLabelConstraints.gridx = 0;
			configurationLabelConstraints.gridy = 4;
			configurationLabelConstraints.anchor = GridBagConstraints.WEST;
			configurationLabelConstraints.insets = new Insets(25, 10, 0, 0);

			GridBagConstraints configurationTextFieldConstraints = new GridBagConstraints();
			configurationTextFieldConstraints.gridx = 0;
			configurationTextFieldConstraints.gridy = 5;
			configurationTextFieldConstraints.anchor = GridBagConstraints.WEST;
			configurationTextFieldConstraints.insets = new Insets(5, 10, 5, 5);

			GridBagConstraints configurationButtonConstraints = new GridBagConstraints();
			configurationButtonConstraints.gridx = 1;
			configurationButtonConstraints.gridy = 5;
			configurationButtonConstraints.insets = new Insets(5, 5, 5, 10);

			GridBagConstraints initButtonConstraints = new GridBagConstraints();
			initButtonConstraints.gridx = 0;
			initButtonConstraints.gridy = 6;
			initButtonConstraints.gridwidth = 2;
			initButtonConstraints.anchor = GridBagConstraints.SOUTH;
			initButtonConstraints.insets = new Insets(25, 0, 10, 0);

			centerPanel.add(jLabel_Project, classPathLabelConstraints);
			centerPanel.add(getJTextField_Project(), classPathTextFieldConstraints);
			centerPanel.add(getJButton_Project(), classPathButtonConstraints);
			centerPanel.add(jLabel_MainClass, mainClassLabelConstraints);
			centerPanel.add(getJTextField_MainClass(), mainClassTextFieldConstraints);
			centerPanel.add(getJButton_MainClass(), mainClassButtonConstraints);
			centerPanel.add(jLabel_Configuration, configurationLabelConstraints);
			centerPanel.add(getJTextField_Configuration(), configurationTextFieldConstraints);
			centerPanel.add(getJButton_Configuration(), configurationButtonConstraints);
			centerPanel.add(getJButton_Init(), initButtonConstraints);
		}

		return centerPanel;
	}

	private JTextField getJTextField_Project() {
		if (jTextField_Project == null) {
			jTextField_Project = new JTextField();
			jTextField_Project.setPreferredSize(new Dimension(220, 20));
			jTextField_Project.setEditable(false);
			jTextField_Project.setBackground(Color.white);
		}

		return jTextField_Project;
	}

	private JTextField getJTextField_MainClass() {
		if (jTextField_MainClass == null) {
			jTextField_MainClass = new JTextField();
			jTextField_MainClass.setPreferredSize(new Dimension(220, 20));
			jTextField_MainClass.setEditable(false);
		}

		return jTextField_MainClass;
	}

	private JTextField getJTextField_Configuration() {
		if (jTextField_Configuration == null) {
			jTextField_Configuration = new JTextField();
			jTextField_Configuration.setPreferredSize(new Dimension(220, 20));
			jTextField_Configuration.setEditable(false);
			jTextField_Configuration.setBackground(Color.white);
		}

		return jTextField_Configuration;
	}

	private JButton getJButton_Project() {
		if (jButton_Project == null) {
			jButton_Project = new JButton();
			jButton_Project.setText("...");

			jButton_Project.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File path = null;

					if (!jTextField_Project.getText().equals(""))
						path = new File(jTextField_Project.getText());

					String str = openDirectoryChooser("Select simulation project", path);

					if (str != null) {
						jTextField_Project.setText(str);

						jTextField_MainClass.setBackground(Color.white);
						jButton_MainClass.setEnabled(true);
					}
				}
			});
		}

		return jButton_Project;
	}

	private JButton getJButton_MainClass() {
		if (jButton_MainClass == null) {
			jButton_MainClass = new JButton();
			jButton_MainClass.setText("...");
			jButton_MainClass.setEnabled(false);

			jButton_MainClass.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String str = openFileChooser("Select simulation main class", new FileNameExtensionFilter("Java class file (.class)", "class"),
							new File(jTextField_Project.getText() + "\\bin"));

					if (str != null) {
						String path = jTextField_Project.getText() + "\\bin\\";

						if (str.length() > path.length() && str.substring(0, path.length()).equals(path))
							jTextField_MainClass.setText(OSPathToJavaPath(str.substring(path.length())));
						else
							showErrorMessage("Invalid class path.");
					}
				}
			});
		}

		return jButton_MainClass;
	}

	private String OSPathToJavaPath(String filePath) {
		StringBuffer javaPath = new StringBuffer("");

		filePath = filePath.substring(0, filePath.lastIndexOf("."));
		StringTokenizer str = new StringTokenizer(filePath, "\\");

		while (str.hasMoreTokens()) {
			if (javaPath.length() > 0)
				javaPath.append(".");

			javaPath.append(str.nextToken());
		}

		return javaPath.toString();
	}

	private JButton getJButton_Configuration() {
		if (jButton_Configuration == null) {
			jButton_Configuration = new JButton();
			jButton_Configuration.setText("...");

			jButton_Configuration.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					File path = null;

					if (!jTextField_Configuration.getText().equals(""))
						path = new File(jTextField_Configuration.getText());
					else if (!jTextField_Project.getText().equals(""))
						path = new File(jTextField_Project.getText());

					String str = openFileChooser("Select simulation configuration file", new FileNameExtensionFilter("XML file (.xml)", "xml"), path);

					if (str != null)
						jTextField_Configuration.setText(str);
				}
			});
		}

		return jButton_Configuration;
	}

	private JButton getJButton_Init() {
		if (jButton_Init == null) {
			jButton_Init = new JButton();
			jButton_Init.setText("Init simulator");

			jButton_Init.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						simulatorEngineController = new ELDAEngineController();
						simulatorEngineController
								.init(jTextField_Project.getText(), jTextField_MainClass.getText(), jTextField_Configuration.getText());

						SimulatorConsole.clearConsole();
						initSimDaemons();
					} catch (SimulatorInitException ex) {
						showExceptionMessage(ex);
						showErrorMessage("Invalid parameters.");
					}
				}
			});
		}

		return jButton_Init;
	}

	private JPanel getSouthPanel() {
		if (southPanel == null) {
			southPanel = new JPanel();
			southPanel.setLayout(new GridBagLayout());
			southPanel.setBorder(BorderFactory.createTitledBorder(null, " Simulation Status ", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));

			GridBagConstraints statusSubPanelConstraints = new GridBagConstraints();
			statusSubPanelConstraints.gridx = 0;
			statusSubPanelConstraints.gridy = 0;
			statusSubPanelConstraints.insets = new Insets(5, 10, 10, 10);
			statusSubPanelConstraints.anchor = GridBagConstraints.CENTER;

			GridBagConstraints progressBarConstraints = new GridBagConstraints();
			progressBarConstraints.gridx = 0;
			progressBarConstraints.gridy = 1;
			progressBarConstraints.insets = new Insets(10, 10, 10, 10);

			southPanel.add(getStatusSubPanel(), statusSubPanelConstraints);
			southPanel.add(getJProgressBar(), progressBarConstraints);
		}

		return southPanel;
	}

	private JPanel getStatusSubPanel() {
		if (statusSubPanel == null) {
			statusSubPanel = new JPanel();
			statusSubPanel.setLayout(new GridBagLayout());

			jLabel_RealTimerLab = new JLabel();
			jLabel_RealTimerLab.setText("Real timer");
			jLabel_RealTimer = new JLabel();
			jLabel_RealTimer.setPreferredSize(new Dimension(90, 20));
			jLabel_RealTimer.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_RealTimerUnit = new JLabel();
			jLabel_RealTimerUnit.setText("ms");

			jLabel_SimulationTimerLab = new JLabel();
			jLabel_SimulationTimerLab.setText("Simulation timer");
			jLabel_SimulationTimer = new JLabel();
			jLabel_SimulationTimer.setPreferredSize(new Dimension(90, 20));
			jLabel_SimulationTimer.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_SimulationTimerUnit = new JLabel();
			jLabel_SimulationTimerUnit.setText("ms");

			jLabel_SimulationConsole = new JLabel();
			jLabel_SimulationConsole.setText("Simulation Console");

			GridBagConstraints realTimerLabelConstraints = new GridBagConstraints();
			realTimerLabelConstraints.gridx = 0;
			realTimerLabelConstraints.gridy = 0;
			realTimerLabelConstraints.insets = new Insets(0, 0, 0, 0);
			realTimerLabelConstraints.anchor = GridBagConstraints.WEST;

			GridBagConstraints realTimerConstraints = new GridBagConstraints();
			realTimerConstraints.gridx = 1;
			realTimerConstraints.gridy = 0;
			realTimerConstraints.insets = new Insets(5, 20, 5, 5);

			GridBagConstraints realTimerUnitConstraints = new GridBagConstraints();
			realTimerUnitConstraints.gridx = 2;
			realTimerUnitConstraints.gridy = 0;
			realTimerUnitConstraints.insets = new Insets(0, 0, 0, 0);

			GridBagConstraints simulationTimerLabelConstraints = new GridBagConstraints();
			simulationTimerLabelConstraints.gridx = 0;
			simulationTimerLabelConstraints.gridy = 1;
			simulationTimerLabelConstraints.insets = new Insets(0, 0, 0, 0);
			simulationTimerLabelConstraints.anchor = GridBagConstraints.WEST;

			GridBagConstraints simulationTimerConstraints = new GridBagConstraints();
			simulationTimerConstraints.gridx = 1;
			simulationTimerConstraints.gridy = 1;
			simulationTimerConstraints.insets = new Insets(5, 20, 5, 5);

			GridBagConstraints simulationTimerUnitConstraints = new GridBagConstraints();
			simulationTimerUnitConstraints.gridx = 2;
			simulationTimerUnitConstraints.gridy = 1;
			simulationTimerUnitConstraints.insets = new Insets(0, 0, 0, 0);

			GridBagConstraints simulationConsoleLabelConstraints = new GridBagConstraints();
			simulationConsoleLabelConstraints.gridx = 0;
			simulationConsoleLabelConstraints.gridy = 2;
			simulationConsoleLabelConstraints.insets = new Insets(0, 0, 0, 0);
			simulationConsoleLabelConstraints.anchor = GridBagConstraints.WEST;

			GridBagConstraints simulationConsoleToggleButtonConstraints = new GridBagConstraints();
			simulationConsoleToggleButtonConstraints.gridx = 1;
			simulationConsoleToggleButtonConstraints.gridy = 2;
			simulationConsoleToggleButtonConstraints.insets = new Insets(10, 0, 10, 0);
			simulationConsoleToggleButtonConstraints.gridwidth = 2;
			simulationConsoleToggleButtonConstraints.anchor = GridBagConstraints.EAST;

			statusSubPanel.add(jLabel_RealTimerLab, realTimerLabelConstraints);
			statusSubPanel.add(jLabel_RealTimer, realTimerConstraints);
			statusSubPanel.add(jLabel_SimulationTimerUnit, realTimerUnitConstraints);
			statusSubPanel.add(jLabel_SimulationTimerLab, simulationTimerLabelConstraints);
			statusSubPanel.add(jLabel_SimulationTimer, simulationTimerConstraints);
			statusSubPanel.add(jLabel_RealTimerUnit, simulationTimerUnitConstraints);
			statusSubPanel.add(jLabel_SimulationConsole, simulationConsoleLabelConstraints);
			statusSubPanel.add(getJToggleButton_Console(), simulationConsoleToggleButtonConstraints);
		}

		return statusSubPanel;
	}

	private JToggleButton getJToggleButton_Console() {
		if (jToggleButton_Console == null) {
			jToggleButton_Console = new JToggleButton();
			jToggleButton_Console.setText(">>");

			jToggleButton_Console.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SimulatorConsole.setConsoleVisible(!SimulatorConsole.isConsoleVisible());
				}
			});

		}

		return jToggleButton_Console;
	}

	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setPreferredSize(new Dimension(270, 18));
			jProgressBar.setToolTipText("Current simulation run");
		}

		return jProgressBar;
	}

	private void initSimDaemons() {
		simStatusDaemon = new Thread(new SimulationStatusDaemon());
		simStatusDaemon.setDaemon(true);

		simConsoleDaemon = new Thread(new SimulatorConsoleDaemon());
		simConsoleDaemon.setDaemon(true);
	}

	private void startSimDaemons() {
		if (simStatusDaemon.getState().equals(Thread.State.NEW))
			simStatusDaemon.start();

		if (simConsoleDaemon.getState().equals(Thread.State.NEW))
			simConsoleDaemon.start();
	}

	private String openFileChooser(String title, FileNameExtensionFilter filter, File currentDir) {
		if (currentDir == null)
			currentDir = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());

		JFileChooser chooser = new JFileChooser(currentDir);
		chooser.setDialogTitle(title);
		chooser.setFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile().getAbsolutePath();

		return null;
	}

	private String openDirectoryChooser(String title, File currentDir) {
		if (currentDir == null)
			currentDir = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());

		JFileChooser chooser = new JFileChooser(currentDir);
		chooser.setDialogTitle(title);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (chooser.showDialog(this, null) == JFileChooser.APPROVE_OPTION)
			return chooser.getSelectedFile().getAbsolutePath();

		return null;
	}

	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, WINDOW_TITLE + ": Error", JOptionPane.ERROR_MESSAGE);
	}

	private void showExceptionMessage(Throwable ex) {
		new ExceptionDialog().showExceptionMessage(this, ex, WINDOW_TITLE + ": Exception");
	}

	private void showInfoMessage(String message) {
		JOptionPane.showMessageDialog(this, message, WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean showConfirmMessage(String message) {
		if (JOptionPane.showConfirmDialog(this, message, WINDOW_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			return true;

		return false;
	}

	private void setButtonStatus(int status) {
		switch (status) {
			case SIMULATION_MODE:
				jButton_Play.setEnabled(true);
				jButton_Pause.setEnabled(true);
				jButton_Stop.setEnabled(true);

				jButton_Project.setEnabled(false);
				jButton_MainClass.setEnabled(false);
				jButton_Configuration.setEnabled(false);
				jButton_Init.setEnabled(false);
				break;

			case SETUP_MODE:
				jButton_Play.setEnabled(false);
				jButton_Pause.setEnabled(false);
				jButton_Stop.setEnabled(false);

				jButton_Project.setEnabled(true);
				jButton_MainClass.setEnabled(true);
				jButton_Configuration.setEnabled(true);
				jButton_Init.setEnabled(true);
				break;
		}
	}

	private void initSimulationInfo(int progressBarMaxValue) {
		jLabel_RealTimer.setText("0");
		jLabel_SimulationTimer.setText("0");

		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(progressBarMaxValue);
		jProgressBar.setValue(0);
		jProgressBar.setStringPainted(true);
		jProgressBar.setString("0");
	}

	private class SimulationStatusDaemon implements Runnable {
		private long initialTime = 0;
		private long currentTime = 0;

		public SimulationStatusDaemon() {
			setButtonStatus(SIMULATION_MODE);
			initSimulationInfo(simulatorEngineController.getSimulationRun());
		}

		public void run() {
			initialTime = System.currentTimeMillis();

			while (true) {
				synchronized (this) {
					try {
						wait(250);
					} catch (InterruptedException e) {
					}
				}

				updateSimulationStatus();
				int currentStatus = simulatorEngineController.getCurrentStatus();

				if (currentStatus == ELDAEngineController.PLAY)
					continue;
				else if (currentStatus == ELDAEngineController.PAUSE) {
					simulatorEngineController.waitingPauseCondition();
					initialTime = System.currentTimeMillis() - currentTime;
				} else if (currentStatus == ELDAEngineController.STOP) {
					break;
				}
			}

			if (simulatorEngineController.getCurrentSimulationRun() >= simulatorEngineController.getSimulationRun()) {
				Toolkit.getDefaultToolkit().beep();
				showInfoMessage("Simulation successfully completed.");
			}

			setButtonStatus(SETUP_MODE);
		}

		private void updateSimulationStatus() {
			currentTime = System.currentTimeMillis() - initialTime;

			jLabel_RealTimer.setText(Long.toString(currentTime));
			jLabel_SimulationTimer.setText(Long.toString((long) VirtualClock.getSimulationTime()));

			int currentRun = simulatorEngineController.getCurrentSimulationRun();

			if (currentRun < jProgressBar.getMinimum())
				currentRun = jProgressBar.getMinimum();

			if (currentRun > jProgressBar.getMaximum())
				currentRun = jProgressBar.getMaximum();

			jProgressBar.setValue(currentRun);
			jProgressBar.setString(Integer.toString(currentRun));

		}
	}

	private class SimulatorConsoleDaemon implements Runnable {
		public SimulatorConsoleDaemon() {
			System.setOut(new PrintStream(new Writer(), true));
			System.setErr(new PrintStream(new Writer(), true));

		}

		public void run() {
			while (simulatorEngineController.getCurrentStatus() != ELDAEngineController.STOP) {
				synchronized (this) {
					try {
						wait(250);
					} catch (InterruptedException e) {
					}
				}
			}
		}

		private class Writer extends OutputStream {
			private StringBuilder str;

			@Override
			public void write(int b) throws IOException {
				if (str == null)
					str = new StringBuilder();

				str.append((char) b);
			}

			@Override
			public void flush() {
				SimulatorConsole.write(str.toString());
				str = new StringBuilder();
			}
		}
	}
}
