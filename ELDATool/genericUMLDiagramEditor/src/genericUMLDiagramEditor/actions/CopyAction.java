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

package genericUMLDiagramEditor.actions;

import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Relationship;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;

/**
 * Copy the current object on the clipboard
 * 
 * @author marguu&zaza
 * 
 */
public class CopyAction	extends SelectionAction 
{

/**
 * Constructor for CopyAction.
 * @param editor
 */
public CopyAction(IWorkbenchPart editor) {
	super(editor);
}

/**
 * @see org.eclipse.gef.ui.actions.EditorPartAction#init()
 */
protected void init() {
	setId(ActionFactory.COPY.getId());
	setText("Copy");
	setToolTipText("Copy");
	ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
	setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
}

/**
 * Return <code>true</code> if {@link #createPasteCommand()} returns an executable command
 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
 */
protected boolean calculateEnabled() {
	return (filter().size()>0);
}

/**
 * Creates and returns a command (which may be <code>null</code>) to create new elements
 * based on the selected elements
 * 
 * Return the copy command
 */
protected Command createCopyCommand() {
	List selection = filter();
	List output = new ArrayList();
	for(int i=0; i<selection.size(); i++) {
		Object obj = selection.get(i);
		if (obj instanceof GraphicalEditPart) {
			// Recupero le informazioni sull'oggetto selezionato
			// e le aggiungo alla lista da inserire nella clipboard
			GraphicalEditPart gep = (GraphicalEditPart)obj;
			output.add(gep.getModel());
		}			
	}
	Clipboard.getDefault().setContents(output);
	
	return null;
}

/**
 * Filters the selected objects in order to obtain only the
 * selected good elements.
 * 
 * Return List The selected good elements
 */
private List filter() {
	// Lascio nella lista degli oggetti selezionati solo le attivazioni
	List output = new ArrayList();
	for(int i=0; i<getSelectedObjects().size(); i++) {
		Object obj = getSelectedObjects().get(i);
		if(obj instanceof AbstractGraphicalEditPart) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) obj;
			if( !(part.getModel() instanceof GenericDiagram) &&
				!(part.getModel() instanceof ConnectionLabel) &&
				!(part.getModel() instanceof Relationship))
				output.add(obj);
		}
		
	}

	return output;
}

/**
 * Executes the command returned by {@link #createCopyCommand()}.
 */
public void run() {
	execute(createCopyCommand());
}
}
