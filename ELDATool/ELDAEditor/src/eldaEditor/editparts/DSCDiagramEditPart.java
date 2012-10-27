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

package eldaEditor.editparts;

import genericUMLDiagramEditor.editParts.GenericDiagramEditPart;
import dscDiagramModel.DSCDiagram;

import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import eldaEditor.dialogs.DiagramPropertiesInputDialog;
import eldaEditor.editpolicies.DSCDiagramXYLayoutEditPolicy;


/**
 * @author samuele
 * EditPart for the a StatechartDiagram instance.
 */
public class DSCDiagramEditPart extends GenericDiagramEditPart  {

/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// disallows the removal of this edit part from its parent
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
	// handles constraint changes (e.g. moving and/or resizing) of model elements
	// and creation of new model elements
	XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
	installEditPolicy(EditPolicy.LAYOUT_ROLE,  new DSCDiagramXYLayoutEditPolicy(layout));
	// disable selection feedback for this edit part
	installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
}

public void performRequest(Request req)
{
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof DSCDiagram) ) {
		DiagramPropertiesInputDialog dialog= new DiagramPropertiesInputDialog(new Shell(),(DSCDiagram)getModel());
		dialog.open();
	}
}
}
