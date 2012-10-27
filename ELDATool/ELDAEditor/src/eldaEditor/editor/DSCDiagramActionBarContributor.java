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

package eldaEditor.editor;

import genericUMLDiagramEditor.editor.GenericDiagramEditorActionBarContributor;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

import eldaEditor.actions.eldatools.ActionEditorAction;
import eldaEditor.actions.eldatools.CodeGeneratorAction;
import eldaEditor.actions.eldatools.CodeGeneratorForJADEAction;
import eldaEditor.actions.eldatools.EventEditorAction;
import eldaEditor.actions.eldatools.GuardEditorAction;
import eldaEditor.actions.eldatools.SupportFunctionEditorAction;


/**
 * Contributes actions to a toolbar.
 * This class is tied to the editor in the definition of editor-extension (see plugin.xml)
 * 
 * @author S. Mascillaro, F. Rango
 *
 */
public class DSCDiagramActionBarContributor extends GenericDiagramEditorActionBarContributor {



/**
 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(IMenuManager)
 */
public void contributeToMenu(IMenuManager menubar) {
	super.contributeToMenu(menubar);

	MenuManager menuModeling = new MenuManager("ELDA Modeling");
	menuModeling.add(new EventEditorAction());
	menuModeling.add(new GuardEditorAction());
	menuModeling.add(new ActionEditorAction());
	menuModeling.add(new SupportFunctionEditorAction());
	
	MenuManager menuImpl = new MenuManager("ELDA Implementation");
	menuImpl.add(new CodeGeneratorAction());
	menuImpl.add(new CodeGeneratorForJADEAction());
	
	menubar.insertAfter(IWorkbenchActionConstants.M_PROJECT, menuImpl);
	menubar.insertAfter(IWorkbenchActionConstants.M_PROJECT, menuModeling);
}

/**
 * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
 */
protected void declareGlobalActionKeys() {
	addGlobalActionKey(ActionFactory.PRINT.getId());
	addGlobalActionKey(ActionFactory.SELECT_ALL.getId());
	addGlobalActionKey(ActionFactory.DELETE.getId());
	addGlobalActionKey(ActionFactory.COPY.getId());
	addGlobalActionKey(ActionFactory.PASTE.getId());
}

}
