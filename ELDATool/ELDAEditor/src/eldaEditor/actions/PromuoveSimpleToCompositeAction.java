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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import dscDiagramModel.DSCState;
import eldaEditor.commands.PromuoveCommandDSC;
import eldaEditor.editparts.DSCStateEditPart;
/**
 * Paste the current object on the clipboard
 * 
 * @author samuele
 *
 */
public class PromuoveSimpleToCompositeAction extends SelectionAction
{
	public static final String ID = "Promuove_Action";

/**
 * Constructor for PasteActionSequence.
 * @param editor
 */
public PromuoveSimpleToCompositeAction(IWorkbenchPart editor) {
	super(editor);
	this.setId(ID);
	this.setText("Promuove Simple to Composite");
}

protected boolean calculateEnabled() {
	List selection= this.getSelectedObjects();
	for(int i=0; i<selection.size(); i++) {
		if (!(selection.get(i)instanceof DSCStateEditPart))
			return false;
		
		if (selection.get(i)instanceof DSCStateEditPart && (!((DSCState) ((DSCStateEditPart)selection.get(i)).getModel()).isIsSimple()))
			return false;
	}
	return true;
	
}


/**
 * Executes the command returned by {@link #createPasteCommand()}.
 */
public void run() {
	CompoundCommand comPlus = new CompoundCommand();
	List selection = this.getSelectedObjects();
	for(int i=0; i<selection.size(); i++) {
		Command com = new PromuoveCommandDSC(selection.get(i));
		comPlus.add(com);
	}
	execute(comPlus);
}
}
