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


import genericUMLDiagramEditor.editor.GenericDiagramContextMenuProvider;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;

import eldaEditor.actions.PromuoveSimpleToCompositeAction;
import eldaEditor.actions.PromuoveStateToDHSAction;
import eldaEditor.actions.eldatools.CodeGeneratorAction;




/**
 * @author samuele
 *
 */
public class DSCDiagramContextMenuProvider extends GenericDiagramContextMenuProvider
{
	
	

	public DSCDiagramContextMenuProvider(ActionRegistry ar, GraphicalViewer gv)
	{
		super(ar,gv);
		actionRegistry = ar;
	}
	public void buildContextMenu(IMenuManager manager)
	{
		super.buildContextMenu(manager);
		
		IAction action;
		
						
		action = actionRegistry.getAction(PromuoveSimpleToCompositeAction.ID);
		if (action.isEnabled())
			manager.appendToGroup(GEFActionConstants.GROUP_REST, action);
		
		action = actionRegistry.getAction(PromuoveStateToDHSAction.ID);
		if (action.isEnabled())
			manager.appendToGroup(GEFActionConstants.GROUP_REST, action);
		
		
	}

}
