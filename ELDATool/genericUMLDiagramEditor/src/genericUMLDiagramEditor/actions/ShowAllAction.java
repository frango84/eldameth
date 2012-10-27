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

import genericUMLDiagramEditor.commands.ShowAllCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;

/**
 * Let users show all the elements in the diagram
 * 
 * @author marguu&zaza
 *
 */
public class ShowAllAction extends SelectionAction {

	public static final String ID = "ShowAll_action";

	protected GenericDiagramEditor editor;

	public ShowAllAction(GenericDiagramEditor part) {
		super(part);
		this.editor = part;
		setId(ID);
		
		setText("Show All");
	}

	/**
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return true;
	}
	
	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		Collection coll = editor.getGraphViewer().getEditPartRegistry().values();
		List selected = new ArrayList();
		selected.addAll(coll);
		ShowAllCommand com = new ShowAllCommand(selected);
		execute(com);
	}
}
