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

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Java filter for file chooser.
 * 
 * @author G. Fortino, F. Rango
 */
public class JavaFilter extends FileFilter {

	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		String filename = file.toString().toLowerCase();
		if (filename.endsWith(".java")) {
			return true;
		}
		return false;
	}

	public String getDescription() {
		return "Java File (.java)";
	}
}
