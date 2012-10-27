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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SimulatorConsole extends JFrame {
	private static final long serialVersionUID = 1L;
	private static SimulatorConsole console;

	private final String WINDOW_TITLE = "ELDASim Controller Console";

	private JPanel jContentPane = null;
	private JScrollPane jScrollPane_ConsolePane = null;
	private JTextArea jTextArea_Console = null;
	private JButton jButton_Clear = null;

	public SimulatorConsole() {
		super();

		initialize();
	}

	private void initialize() {
		setTitle(WINDOW_TITLE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		setContentPane(getJContentPane());
		setResizable(false);
		pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Point center = new Point((dimension.width - getSize().width) / 2, (dimension.height - getSize().height) / 2);
		setLocation(center);

		setVisible(false);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());

			GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
			scrollPaneConstraints.gridx = 0;
			scrollPaneConstraints.gridy = 0;
			scrollPaneConstraints.weightx = 1.0;
			scrollPaneConstraints.weighty = 1.0;
			scrollPaneConstraints.insets = new Insets(10, 10, 10, 10);
			scrollPaneConstraints.fill = GridBagConstraints.BOTH;

			GridBagConstraints clearButtonConstraints = new GridBagConstraints();
			clearButtonConstraints.gridx = 0;
			clearButtonConstraints.gridy = 1;
			clearButtonConstraints.weightx = 1.0;
			clearButtonConstraints.weighty = 1.0;
			clearButtonConstraints.insets = new Insets(10, 0, 10, 10);
			clearButtonConstraints.anchor = GridBagConstraints.EAST;

			jContentPane.add(getJScrollPane_ConsolePane(), scrollPaneConstraints);
			jContentPane.add(getJButton_Clear(), clearButtonConstraints);
		}

		return jContentPane;
	}

	private JScrollPane getJScrollPane_ConsolePane() {
		if (jScrollPane_ConsolePane == null) {
			jTextArea_Console = new JTextArea();
			jScrollPane_ConsolePane = new JScrollPane(jTextArea_Console);
		}

		jTextArea_Console.setEditable(false);
		jTextArea_Console.setBackground(Color.WHITE);

		jScrollPane_ConsolePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollPane_ConsolePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane_ConsolePane.setPreferredSize(new Dimension(700, 300));
		jScrollPane_ConsolePane.setVisible(true);

		return jScrollPane_ConsolePane;
	}

	private JButton getJButton_Clear() {
		if (jButton_Clear == null) {
			jButton_Clear = new JButton();
			jButton_Clear.setText("Clear");

			jButton_Clear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jTextArea_Console.setText("");
				}
			});
		}

		return jButton_Clear;
	}

	public static void startSimulatorConsole() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				console = new SimulatorConsole();
			}
		});
	}

	public static void setConsoleVisible(boolean flag) {
		console.setVisible(flag);
	}

	public static boolean isConsoleVisible() {
		return console.isVisible();
	}

	public static void write(String str) {
		console.jTextArea_Console.append(str);
		console.jTextArea_Console.setCaretPosition(console.jTextArea_Console.getText().length());
	}

	public static void clearConsole(){
		console.jTextArea_Console.setText("");
	}
	
	public static void stopSimulatorConsole() {
		if (console != null)
			console.dispose();
	}
}
