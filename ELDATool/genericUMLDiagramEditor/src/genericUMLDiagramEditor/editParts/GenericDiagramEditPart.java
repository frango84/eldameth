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

import genericUMLDiagramEditor.editPolicies.GenericDiagramXYLayoutEditPolicy;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;

import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;

/**
 * EditPart for the a GenericDiagram instance.
 * <p>This edit part server as the main diagram container, the white area where
 * everything else is in. Also responsible for the container's layout (the
 * way the container rearanges is contents) and the container's capabilities
 * (edit policies).
 * 
 * @author marguu&zaza
 * 
 */
public class GenericDiagramEditPart extends GenericDiagramAbstractEditPart  {
/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// disallows the removal of this edit part from its parent
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new RootComponentEditPolicy());
	// handles constraint changes (e.g. moving and/or resizing) of model elements
	// and creation of new model elements
	XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
	installEditPolicy(EditPolicy.LAYOUT_ROLE,  new GenericDiagramXYLayoutEditPolicy(layout));
	// disable selection feedback for this edit part
	installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
 */
protected IFigure createFigure() {
	Figure f = new Figure();
	f.setLayoutManager(new FreeformLayout());
	f.setOpaque(true);
	f.setBackgroundColor(ColorConstants.white);
	
	ConnectionLayer connLayer = (ConnectionLayer)getLayer(LayerConstants.CONNECTION_LAYER);
	FanRouter router = new FanRouter();
	router.setSeparation(20);
	router.setNextRouter(new BendpointConnectionRouter());
	connLayer.setConnectionRouter(router);
	return f;
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	return ((GenericDiagram) getModel()).getSortedModelElements();
}

/**
 * @see genericUMLDiagramEditor.editParts.GenericDiagramAbstractEditPart#handlePropertyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification notification) {
	switch (notification.getFeatureID(GenericDiagram.class)) {
	case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
		refreshChildren();
	}
}
}
