/*****************************************************************
DSC* TRANSLATOR
Copyright (C) 2010 G. Fortino, F. Rango

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

package gui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Application starter.
 * 
 * @author G. Fortino, F. Rango
 */
public class Starter {

	public static void main(String [] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error with graphics!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
        javax.swing.SwingUtilities.invokeLater(new Runnable() { //necessario per la barra di avanzamento grafica
            public void run() {
            	new ApplicationFrame().setVisible(true);
            }
        });
	}

}
