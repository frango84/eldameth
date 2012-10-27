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

import genericUMLDiagramEditor.commands.PasteCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;

import java.util.List;

/**
 * Paste the current object on the clipboard
 * 
 * @author marguu&zaza
 *
 */
public class PasteAction extends SelectionAction 
{
	protected GenericDiagramEditor editor;

/**
 * Constructor for PasteAction.
 * @param editor
 */
public PasteAction(IWorkbenchPart editor) {
	super(editor);
	
	this.editor = (GenericDiagramEditor) editor;
}

/**
 * @see org.eclipse.gef.ui.actions.EditorPartAction#init()
 */
protected void init() {
	setId(ActionFactory.PASTE.getId());
	setText("Paste");
	ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
	setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
	setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
	setToolTipText("Paste");
}

/**
 * Return <code>true</code> if {@link #createPasteCommand()} returns an executable command
 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
 */
protected boolean calculateEnabled() {
	return true;
}

/**
 * Executes the command returned by {@link #createPasteCommand()}.
 */
public void run() {
	CompoundCommand comPlus = new CompoundCommand();
	List selection = (List) Clipboard.getDefault().getContents();
	if(selection!=null) {
		for(int i=0; i<selection.size(); i++) {
			Command com = new PasteCommand(editor, selection.get(i));
			comPlus.add(com);
		}
		execute(comPlus);
	}
}
}
