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

package eldasim.gui.util;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ExceptionDialog {
	private JPanel buttonsPane = null;
	private JButton okButton = null;
	private JButton detailsButton = null;
	private JScrollPane scrollPane = null;
	private JDialog dialog = null;

	public void showExceptionMessage(JFrame parentComponent, Throwable ex, String title) {
		JPanel mainPane = new JPanel();
		mainPane.setLayout(new GridBagLayout());

		GridBagConstraints buttonsPaneConstraints = new GridBagConstraints();
		buttonsPaneConstraints.gridx = 0;
		buttonsPaneConstraints.gridy = 0;
		buttonsPaneConstraints.weightx = 1.0;
		buttonsPaneConstraints.weighty = 1.0;
		buttonsPaneConstraints.insets = new Insets(10, 10, 10, 10);

		GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
		scrollPaneConstraints.gridx = 0;
		scrollPaneConstraints.gridy = 1;
		scrollPaneConstraints.weightx = 1.0;
		scrollPaneConstraints.weighty = 1.0;
		scrollPaneConstraints.insets = new Insets(0, 10, 10, 10);

		mainPane.add(getButtonsPane(), buttonsPaneConstraints);
		mainPane.add(getScrollPane(ex), scrollPaneConstraints);

		initializeDialog(parentComponent, ex.toString(), title, new Object[] { mainPane });

		dialog.setVisible(true);
	}

	private void initializeDialog(JFrame parentComponent, String message, String title, Object[] options) {
		JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, null);
		dialog = optionPane.createDialog(parentComponent, title);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		packAndCenter();
	}

	private void packAndCenter() {
		dialog.pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		Point center = new Point((int) (dimension.getWidth() - dialog.getWidth()) / 2, (int) (dimension.getHeight() - dialog.getHeight()) / 2);
		dialog.setLocation(center);
	}

	private JPanel getButtonsPane() {
		if (buttonsPane == null)
			buttonsPane = new JPanel();

		buttonsPane.setLayout(new GridBagLayout());

		GridBagConstraints okButtonConstraints = new GridBagConstraints();
		okButtonConstraints.gridx = 0;
		okButtonConstraints.gridy = 0;
		okButtonConstraints.weightx = 1.0;
		okButtonConstraints.weighty = 1.0;
		okButtonConstraints.insets = new Insets(0, 0, 0, 5);

		GridBagConstraints detailsButtonConstraints = new GridBagConstraints();
		detailsButtonConstraints.gridx = 1;
		detailsButtonConstraints.gridy = 0;
		detailsButtonConstraints.weightx = 1.0;
		detailsButtonConstraints.weighty = 1.0;
		detailsButtonConstraints.insets = new Insets(0, 5, 0, 0);

		buttonsPane.add(getOkButton(), okButtonConstraints);
		buttonsPane.add(getDetailsButton(), detailsButtonConstraints);

		return buttonsPane;
	}

	private JButton getOkButton() {
		if (okButton == null)
			okButton = new JButton();

		okButton.setText("Ok");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		return okButton;
	}

	private JButton getDetailsButton() {
		if (detailsButton == null)
			detailsButton = new JButton();

		detailsButton.setText("Details..");

		detailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(!scrollPane.isVisible());
				packAndCenter();
			}
		});

		return detailsButton;
	}

	private JScrollPane getScrollPane(Throwable ex) {
		Throwable e = ex;
		JTextArea textArea = new JTextArea();

		while (e != null) {
			textArea.append(e.toString() + "\n");

			StackTraceElement[] trace = ex.getStackTrace();
			for (int i = 0; i < trace.length; i++)
				textArea.append("    at " + trace[i] + "\n");

			textArea.append("\n");
			e = e.getCause();
		}

		if (scrollPane == null)
			scrollPane = new JScrollPane(textArea);

		scrollPane.setVisible(false);
		scrollPane.setPreferredSize(new Dimension(700, 300));
		textArea.setCaretPosition(0);
		textArea.setEditable(false);
		textArea.setBackground(Color.WHITE);
		
		return scrollPane;
	}
}
