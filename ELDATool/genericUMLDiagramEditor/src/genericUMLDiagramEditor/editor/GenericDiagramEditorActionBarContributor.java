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

package genericUMLDiagramEditor.editor;

import genericUMLDiagramEditor.actions.CopyRetargetAction;
import genericUMLDiagramEditor.actions.PasteRetargetAction;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;

import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.draw2d.PositionConstants;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightRetargetAction;
import org.eclipse.gef.ui.actions.MatchWidthRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;

/**
 * Contributes actions to a toolbar.
 * This class is tied to the editor in the definition of editor-extension (see plugin.xml)
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramEditorActionBarContributor extends ActionBarContributor {

/**
 * Create actions managed by this contributor.
 * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
 */
protected void buildActions() {
	addRetargetAction(new UndoRetargetAction());
	addRetargetAction(new RedoRetargetAction());
	
	addRetargetAction(new CopyRetargetAction());
	addRetargetAction(new PasteRetargetAction());
	
	addRetargetAction(new ZoomInRetargetAction());
	addRetargetAction(new ZoomOutRetargetAction());

	addRetargetAction(new MatchWidthRetargetAction());
	addRetargetAction(new MatchHeightRetargetAction());
	
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
	addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
}

/**
 * Add actions to the given toolbar.
 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
 */
public void contributeToToolBar(IToolBarManager toolBarManager) {
	super.contributeToToolBar(toolBarManager);
	
	toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
	toolBarManager.add(getAction(ActionFactory.REDO.getId()));
	
	toolBarManager.add(new Separator());
	toolBarManager.add(getAction(ActionFactory.COPY.getId()));
	toolBarManager.add(getAction(ActionFactory.PASTE.getId()));
	
	toolBarManager.add(new Separator());
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));
	toolBarManager.add(new Separator());
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
	toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));

	toolBarManager.add(new Separator());
	toolBarManager.add(getAction(GEFActionConstants.MATCH_WIDTH));
	toolBarManager.add(getAction(GEFActionConstants.MATCH_HEIGHT));

	toolBarManager.add(new Separator());
	toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
	toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
	String[] zoomStrings = new String[] {ZoomManager.FIT_ALL, ZoomManager.FIT_HEIGHT, ZoomManager.FIT_WIDTH };
	toolBarManager.add(new ZoomComboContributionItem(getPage(), zoomStrings));
}

/**
 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(IMenuManager)
 */
public void contributeToMenu(IMenuManager menubar) {
	super.contributeToMenu(menubar);

	MenuManager viewMenu = new MenuManager("View");
	viewMenu.add(getAction(GEFActionConstants.ZOOM_IN));
	viewMenu.add(getAction(GEFActionConstants.ZOOM_OUT));
	viewMenu.add(new Separator());
	viewMenu.add(getAction(GEFActionConstants.ALIGN_TOP));
	viewMenu.add(getAction(GEFActionConstants.ALIGN_BOTTOM));
	viewMenu.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
	viewMenu.add(getAction(GEFActionConstants.ALIGN_LEFT));
	viewMenu.add(getAction(GEFActionConstants.ALIGN_RIGHT));
	viewMenu.add(getAction(GEFActionConstants.ALIGN_CENTER));
	viewMenu.add(new Separator());
	viewMenu.add(getAction(GEFActionConstants.MATCH_WIDTH));
	viewMenu.add(getAction(GEFActionConstants.MATCH_HEIGHT));
	menubar.insertAfter(IWorkbenchActionConstants.M_EDIT, viewMenu);
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
