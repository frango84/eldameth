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

package eldaEditor.actions;

import genericUMLDiagramEditor.actions.PasteAction;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.ui.IWorkbenchPart;

import eldaEditor.commands.PasteCommandDSC;

/**
 * Paste the current object on the clipboard
 * 
 * @author samuele
 *
 */
public class PasteActionDSC extends PasteAction 
{

/**
 * Constructor for PasteActionSequence.
 * @param editor
 */
public PasteActionDSC(IWorkbenchPart editor) {
	super(editor);
}

/**
 * Executes the command returned by {@link #createPasteCommand()}.
 */
public void run() {
	CompoundCommand comPlus = new CompoundCommand();
	List selection = (List) Clipboard.getDefault().getContents();
	for(int i=0; i<selection.size(); i++) {
		Command com = new PasteCommandDSC(editor, selection.get(i));
		comPlus.add(com);
	}
	execute(comPlus);
}
}
