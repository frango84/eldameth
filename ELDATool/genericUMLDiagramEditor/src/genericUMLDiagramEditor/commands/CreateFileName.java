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

package genericUMLDiagramEditor.commands;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;


/**
 * Create the filename of the open diagram
 * 
 * @author marguu&zaza
 *
 */
public class CreateFileName {

    public CreateFileName() {}
	
	/**
	 * Return the filename
	 */
	public static String getName() {
		// Cerco il nome del file
		IFile file=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();
		String strFile = file.getFullPath().toString();
		
		// Elimino dal path trovato il nome del progetto
		strFile = strFile.replaceAll(file.getProject().getFullPath()+"/","");
		return filter(strFile);
	}
	
	/**
	 * Repalce the symbols à è ì ò ù with the symbols a' e' i' o' u'
	 * 
	 * @param text	the text to be modified
	 * Return		the modified text
	 */
	protected static String filter(String text) {
	    // Controllo se nella stringa passata sono presenti lettere accentate
		text = text.replaceAll("à","a'");
		text = text.replaceAll("è","e'");
		text = text.replaceAll("ì","i'");
		text = text.replaceAll("ò","o'");
		text = text.replaceAll("ù","u'");
	      
	    return text;
	}
}
