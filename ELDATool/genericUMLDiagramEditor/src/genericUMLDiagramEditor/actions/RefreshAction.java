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

import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramEditor.icons.Icons;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.ui.*;

/**
 * Let users refresh the whole diagram
 * 
 * @author marguu&zaza
 *
 */
public class RefreshAction extends SelectionAction
{
	public static final String ID = "REFRESH";
	private IWorkbenchPart part;

	public RefreshAction(IWorkbenchPart part) {
		super(part);
		
		this.part = part;
	}

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#init()
	 */
	public void init() {
		setId(ID);
		setText("Refresh               F5");
		setImageDescriptor(ImageDescriptor.createFromFile(Icons.class, "refresh.gif"));
	}

	/**
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return (getSelectedObjects().size()>0);
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		if(getSelectedObjects().size()>0) {
			// Effettuo il refresh di tutto il diagramma
			GenericDiagramEditor editor = (GenericDiagramEditor) part.getSite().getWorkbenchWindow().getActivePage().getActiveEditor();
			EditPart editPart = (EditPart) getSelectedObjects().get(0);
			AbstractGraphicalEditPart grephPart = (AbstractGraphicalEditPart) editor.getGraphViewer().getEditPartRegistry().get(EcoreUtil.getRootContainer((EObject) editPart.getModel()));
			grephPart.getFigure().repaint();
		}
	}
}
