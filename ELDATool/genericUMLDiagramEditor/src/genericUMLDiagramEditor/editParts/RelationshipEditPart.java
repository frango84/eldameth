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
import java.util.ArrayList;

import genericUMLDiagramEditor.anchor.GenericDiagramAnchor;
import genericUMLDiagramEditor.dialogs.RelationshipPropertiesInputDialog;
import genericUMLDiagramEditor.editPolicies.RelationshipEditPolicyImpl;
import genericUMLDiagramEditor.editPolicies.GraphicalNodeEditPolicyImpl;
import genericUMLDiagramModel.*;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * The controller for the Relationship
 * 
 * @author marguu&zaza
 * 
 */
public class RelationshipEditPart extends AbstractConnectionEditPart implements NodeEditPart{

protected Adapter modelListener = new AdapterImpl() {
	public void notifyChanged(Notification msg) {
		handlePropertyChanged(msg);
	}
};

/**
 * Upon activation, attach to the model element as a property change listener.
 */
public void activate() {
	if (!isActive()) {
		super.activate();
		((Notifier)getModel()).eAdapters().add(modelListener);
	}
}

/**
 * Upon deactivation, detach from the model element as a property change listener.
 */
public void deactivate() {
	if (isActive()) {
		super.deactivate();
		((Notifier) getModel()).eAdapters().remove(modelListener);
	}
}	

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	installEditPolicy(EditPolicy.CONNECTION_ROLE, new RelationshipEditPolicyImpl());
	installEditPolicy(EditPolicy.NODE_ROLE, new GraphicalNodeEditPolicyImpl());
	installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new genericUMLDiagramEditor.editPolicies.BendpointEditPolicyImpl());
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
 */
protected IFigure createFigure() {
	return new PolylineConnection();
}

/**
 * Return the Relationship associated to this editPart
 */
protected Relationship getCastedModel() {
	return (Relationship) getModel();
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	List output = new ArrayList();
	if(getCastedModel().getConnectionLabel()!=null)
		output.add(getCastedModel().getConnectionLabel());
	return output;
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
 */
protected List getModelSourceConnections() {
	return getCastedModel().getSourceRelationships();
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
 */
protected List getModelTargetConnections() {
	return getCastedModel().getTargetRelationships();
}

/**
 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
 */
public void performRequest(Request req) {
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Relationship ) ) {
		Relationship model=(Relationship)getModel();

		RelationshipPropertiesInputDialog dialog=new RelationshipPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();

		// Effettuo il refresh della selezione
		setSelected(SELECTED);
	}
}

/**
 * Manage the model changes
 * 
 * @param msg	the notification of a change
 */
protected void handlePropertyChanged(Notification msg) {
	switch (msg.getFeatureID(Relationship.class)) {
		case GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE:
		case GenericUMLDiagramModelPackage.RELATIONSHIP__NAME:
		case GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION:
		case GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS:
			refreshVisuals();
			break;
		case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
			refreshSourceConnections();
			break;
		case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
			refreshTargetConnections();
			break;
	}
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
 */
protected void refreshVisuals() {
	super.refreshVisuals();
	
	PolylineConnection fig = (PolylineConnection) getFigure();
	Object constraint = getCastedModel().getBendpoints();
	fig.setRoutingConstraint(constraint);
	
	// Effettuo il refresh della ConnectionLabel associata
	if(!getChildren().isEmpty()) {
		ConnLabelEditPart connEP = (ConnLabelEditPart) getChildren().get(0);
		connEP.refreshVisuals();
	}
}

/**
 * @see org.eclipse.gef.EditPart#setSelected(int)
 */
public void setSelected(int value) {
	super.setSelected(value);

	if(getCastedModel().getConnectionLabel()!=null) {
		ConnLabelEditPart part = (ConnLabelEditPart)children.get(0);
		if(part.getSelected()!=value)
			part.setSelected(value);
	}
}

/**
 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
 */
public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
	return new GenericDiagramAnchor((PolylineConnection)getFigure());
}

/**
 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.ConnectionEditPart)
 */
public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
	return new GenericDiagramAnchor((PolylineConnection)getFigure());
}

/**
 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
 */
public ConnectionAnchor getSourceConnectionAnchor(Request request) {
	return new GenericDiagramAnchor((PolylineConnection)getFigure());
}

/**
 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
 */
public ConnectionAnchor getTargetConnectionAnchor(Request request) {
	return new GenericDiagramAnchor((PolylineConnection)getFigure());
}
}
