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

import genericUMLDiagramModel.GenericDiagram;

import genericUMLDiagramEditor.commands.ChangeVisibilityCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

/**
 * Let users modify the visibility for the selected
 * elements in the diagram. The hidden items became
 * visible and viceversa 
 * 
 * @author marguu&zaza
 *
 */
public class VisibilityAction extends SelectionAction {

	public static final String ID = "Visibility_action";
	private List selected = new ArrayList();
	private boolean isVisible;

	protected GenericDiagramEditor editor;

	public VisibilityAction(GenericDiagramEditor part) {
		super(part);
		this.editor = part;
		setId(ID);
	}

	/**
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		// Tolgo dagli elementi selezionati, nel diagramma o
		// nell'outline il GenericDiagram se presente
		selected.clear();
		for(int i=0; i<getSelectedObjects().size(); i++) {
			if(getSelectedObjects().get(i) instanceof AbstractGraphicalEditPart) {
				AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) getSelectedObjects().get(i);
				if(!(part.getModel() instanceof GenericDiagram))
					selected.add(getSelectedObjects().get(i));
			}
			else if(getSelectedObjects().get(i) instanceof AbstractTreeEditPart) {
				AbstractTreeEditPart partTree = (AbstractTreeEditPart) getSelectedObjects().get(i);
				AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) editor.getGraphViewer().getEditPartRegistry().get(partTree.getModel());
				if(!(part.getModel() instanceof GenericDiagram))
					selected.add(part);
			}
		}
		
		if(selected.size()==0) return false;

		// Verifico che tutti gli oggetti selezionati siano visibili o non visibili
		AbstractGraphicalEditPart part0 = (AbstractGraphicalEditPart) selected.get(0);
		isVisible = part0.getFigure().isVisible();
		for(int i=1; i<selected.size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) selected.get(i);
			if(part.getFigure().isVisible()!=isVisible) return false;
		}
		
		if(isVisible) setText("Hide");
		else		  setText("Show");
		
		return true;
	}
	
	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		ChangeVisibilityCommand com = new ChangeVisibilityCommand(selected);
		execute(com);
	}
}
