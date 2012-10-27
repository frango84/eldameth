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

package genericUMLDiagramEditor.editParts;

import java.util.List;

import genericUMLDiagramModel.*;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * EditPart for the a GenericDiagram instance in the outline
 * 
 * @author marguu&zaza
 *
 */

public class GenericDiagramTreeEditPart extends GenericDiagramAbstractTreeEditPart {
/** 
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null ShapesDiagram instance
 */
public GenericDiagramTreeEditPart(GenericDiagram model) {
	super(model);
}

/**
 * @see org.eclipse.gef.examples.shapes.parts.ShapeTreeEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// If this editpart is the root content of the viewer, then disallow removal
	if (getParent() instanceof RootEditPart) {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
	}
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	return ((GenericDiagram) getModel()).getSortedModelElements();
}

/**
 * @see genericUMLDiagramEditor.editParts.GenericDiagramAbstractTreeEditPart#handlePropertyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification notification) {
	switch (notification.getFeatureID(GenericDiagram.class)) {
	case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
		refreshChildren();
	}
}
}
