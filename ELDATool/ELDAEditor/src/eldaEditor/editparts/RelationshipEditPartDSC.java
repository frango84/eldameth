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

import genericUMLDiagramEditor.editParts.RelationshipEditPart;
import genericUMLDiagramEditor.editPolicies.BendpointEditPolicyImpl;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Shell;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.Transition;
import eldaEditor.dialogs.TransitionPropertiesInputDialog;
import eldaEditor.editpolicies.GraphicalNodeEditPolicyImplDSC;
import eldaEditor.editpolicies.RelationshipEditPolicyImpl;

/**
 * EditPart associato ad un oggetto relationship
 * @author marguu&zaza, F. Rango
 *
 */
public class RelationshipEditPartDSC extends RelationshipEditPart {
/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
 */
protected IFigure createFigure() {
	if (getModel() instanceof Transition) {
		PolylineConnection fig = new PolylineConnection();
		PolylineDecoration decoration = new PolylineDecoration();
		decoration.setScale(15,4);
		fig.setTargetDecoration(decoration);
		return fig;
	}

	return super.createFigure();
}
	
/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());
	installEditPolicy(EditPolicy.CONNECTION_ROLE, new RelationshipEditPolicyImpl());
	installEditPolicy(EditPolicy.NODE_ROLE, new GraphicalNodeEditPolicyImplDSC());
	installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new BendpointEditPolicyImpl());
}

/* (non-Javadoc)
 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
 */
public void performRequest(Request req) {
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Transition) ) {
		Transition model=(Transition)getModel();
		
		TransitionPropertiesInputDialog dialog=new TransitionPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();

		// Effettuo il refresh della selezione
		setSelected(SELECTED);
		
		refreshVisuals();
	}
}

/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
 */
public void refreshVisuals() {
	super.refreshVisuals();
	
	if (getModel() instanceof AnchorNoteToItem) {
		PolylineConnection fig = (PolylineConnection)getFigure();
		fig.setLineStyle(SWT.LINE_DASH);
		fig.setLineWidth(2);
	}
	if (getModel() instanceof Transition){
		Transition t=(Transition)getModel();
		if(!t.isTriggeredByEvent()){
			PolylineConnection fig = (PolylineConnection)getFigure();
			fig.setForegroundColor(ColorConstants.red);
		}
	}
}

/**
 * Metodo per la gestione dei cambiamenti che avvengono nell'elemento del modello associato all'EditPart
 * @param msg	Notifica di un evento avvenuto nell'elemento del modello associato all'EditPart
 */
protected void handlePropertyChanged(Notification msg) {
	switch (msg.getFeatureID(Transition.class)) {
	case DscDiagramModelPackage.TRANSITION__ACTION_ID:
	case DscDiagramModelPackage.TRANSITION__EVENT_ID:
	case DscDiagramModelPackage.TRANSITION__GUARD_ID:
	case DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES:
	case DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID:
	case DscDiagramModelPackage.TRANSITION__TRANSITION_ID:
	case DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT:
//	case StatechartUMLDiagramModelPackage.TRANSITION__TRIGGER:
//	case StatechartUMLDiagramModelPackage.TRANSITION__STEREOTYPE:
//	case StatechartUMLDiagramModelPackage.TRANSITION__BENDPOINTS:
		refreshVisuals();
		break;
	case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
		refreshSourceConnections();
		break;
	case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
	  	refreshTargetConnections();
		break;
	}
}
}
