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

import genericUMLDiagramEditor.actions.ExportAction;
import genericUMLDiagramEditor.actions.ExportSelectionAction;
import genericUMLDiagramEditor.actions.RefreshAction;
import genericUMLDiagramEditor.actions.ShowAllAction;
import genericUMLDiagramEditor.actions.VisibilityAction;

import org.eclipse.jface.action.*;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.gef.*;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;

/**
 * Create the context menù for the GenericEditor
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramContextMenuProvider extends ContextMenuProvider
{
	protected ActionRegistry actionRegistry;

	public GenericDiagramContextMenuProvider(ActionRegistry ar, GraphicalViewer gv)
	{
		super(gv);
		actionRegistry = ar;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void buildContextMenu(IMenuManager manager)
	{
		GEFActionConstants.addStandardActionGroups(manager);

		IAction action;
		
		action = actionRegistry.getAction(GEFActionConstants.ZOOM_IN);
		manager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		action = actionRegistry.getAction(GEFActionConstants.ZOOM_OUT);
		manager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);
		
		action = actionRegistry.getAction(RefreshAction.ID);
		if (action.isEnabled())
			manager.appendToGroup(GEFActionConstants.GROUP_VIEW, action);

		action = actionRegistry.getAction(VisibilityAction.ID);
		if (action.isEnabled()) manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(ShowAllAction.ID);
		manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		addAlignmentAction(manager);

		MenuManager submenu = new MenuManager("Match");
		action = actionRegistry.getAction(GEFActionConstants.MATCH_WIDTH);
		if (action.isEnabled()) submenu.add(action);
		action = actionRegistry.getAction(GEFActionConstants.MATCH_HEIGHT);
		if (action.isEnabled()) submenu.add(action);
		if (!submenu.isEmpty())
			manager.appendToGroup(GEFActionConstants.GROUP_EDIT, submenu);
		
		String id = ActionFactory.UNDO.getId();
		action = actionRegistry.getAction(id);
		manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
		id = ActionFactory.REDO.getId();
		action = actionRegistry.getAction(id);
		manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		id = ActionFactory.COPY.getId();
		action = actionRegistry.getAction(id);
		manager.appendToGroup(GEFActionConstants.GROUP_COPY, action);
		id = ActionFactory.PASTE.getId();
		action = actionRegistry.getAction(id);
		manager.appendToGroup(GEFActionConstants.GROUP_COPY, action);
		
		id = ActionFactory.DELETE.getId();
		action = actionRegistry.getAction(id);
		if (action.isEnabled())
			manager.appendToGroup(GEFActionConstants.GROUP_REST, action);
		
		MenuManager submenuExport = new MenuManager("Export");
		action = actionRegistry.getAction(ExportSelectionAction.ID);
		submenuExport.add(action);
		action = actionRegistry.getAction(ExportAction.ID);
		submenuExport.add(action);
		manager.appendToGroup(GEFActionConstants.GROUP_PRINT, submenuExport);
	}
	
	/**
	 * Build the submenù for the alignment actions
	 * 
	 * @param manager  the IMenuManager
	 */
	protected void addAlignmentAction(IMenuManager manager) {
		// Alignment Actions
		MenuManager submenu = new MenuManager("Align");
		IAction action;

		action = actionRegistry.getAction(GEFActionConstants.ALIGN_TOP);
		if (action.isEnabled()) submenu.add(action);

		action = actionRegistry.getAction(GEFActionConstants.ALIGN_BOTTOM);
		if (action.isEnabled()) submenu.add(action);

		action = actionRegistry.getAction(GEFActionConstants.ALIGN_MIDDLE);
		if (action.isEnabled()) submenu.add(action);

		submenu.add(new Separator());
		
		action = actionRegistry.getAction(GEFActionConstants.ALIGN_LEFT);
		if (action.isEnabled()) submenu.add(action);

		action = actionRegistry.getAction(GEFActionConstants.ALIGN_RIGHT);
		if (action.isEnabled()) submenu.add(action);

		action = actionRegistry.getAction(GEFActionConstants.ALIGN_CENTER);
		if (action.isEnabled()) submenu.add(action);

		if (!submenu.isEmpty())
			manager.appendToGroup(GEFActionConstants.GROUP_EDIT, submenu);
	}
}
