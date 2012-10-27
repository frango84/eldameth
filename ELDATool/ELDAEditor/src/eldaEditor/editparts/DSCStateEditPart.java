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

import genericUMLDiagramEditor.editParts.ContainerEditPart;
import genericUMLDiagramEditor.editPolicies.TextEditPolicy;
import genericUMLDiagramEditor.figures.GenericFigure;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.Relationship;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.swt.widgets.Shell;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.Transition;
import eldaEditor.dialogs.StatePropertiesInputDialog;
import eldaEditor.editpolicies.CompositeStateXYLayoutEditPolicy;
import eldaEditor.editpolicies.ElementComponentEditPolicyDSC;
import eldaEditor.editpolicies.GraphicalNodeEditPolicyImplDSC;
import eldaEditor.figures.StateFigure;

/**
 * EditPart used for State instances.
 * <p>This edit part must implement the PropertyChangeListener interface, 
 * so it can be notified of property changes in the corresponding model element.
 * </p>
 *
 *@author samuele
 */
public class DSCStateEditPart extends ContainerEditPart {
private boolean automaticResize = true;
private boolean calledFromCommand=true;



public void activate()
{
	super.activate();
	
	
}
/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// allow removal of the associated model element
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicyDSC());
	

	//install the layout manager only for composite state
	if (((DSCState) getModel()).isIsSimple()==false)
	{
		XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
		installEditPolicy(EditPolicy.LAYOUT_ROLE,  new CompositeStateXYLayoutEditPolicy(layout));
	}
	
	// allow the creation of connections and 
	// and the reconnection of connections between Shape instances
	installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicyImplDSC());
	
	// permette la modifica rapida del nome dello stato
	installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TextEditPolicy());
}

/**
 * Ritorna la figura corrispondente all'elemento associato a questo EditPart 
 */
protected IFigure createFigureForModel() {
		return new StateFigure((DSCState)getModel());
	}


public void performRequest(Request req) {
	if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		GenericFigure fig = (GenericFigure)getFigure();
		Rectangle bounds = fig.getNameBounds().getCopy();
		bounds.translate(0,-4);
		fig.translateToAbsolute(bounds);
		performDirectEdit(bounds, getCastedModel().getName());
	}
	else {
		DSCState model=(DSCState)getModel();
		DSCDiagram parent=(DSCDiagram)EcoreUtil.getRootContainer(model); 
		StatePropertiesInputDialog dialog=new StatePropertiesInputDialog(new Shell(),model,automaticResize,parent);
		dialog.open();
		if(!dialog.getCancelled()) {
		    automaticResize = dialog.getAutomaticResize();
		    
		    // Aggiungo tutte le internalTransition al parent di state
//		    for(int j=0; j<model.getInternalTransition().size(); j++)
//		    	((Transition) model.getInternalTransition().get(j)).setState((State)getModel());
		}
	}
}

/* (non-Javadoc)
 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
 */
protected void refreshVisuals() {
	// transfer the size and location from the model instance to the corresponding figure
	Rectangle newBounds = new Rectangle(getCastedModel().getLocation(),getCastedModel().getSize());
	figure.setBounds(newBounds);

	// notify parent container of changed position & location
	// if this line is removed, the XYLayoutManager used by the parent container 
	// (the Figure of the ShapesDiagramEditPart), will not know the bounds of this figure
	// and will not draw it correctly.
	((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, newBounds);
	StateFigure figure = (StateFigure)getFigure();
	DSCState model = (DSCState )getModel();
	figure.setStereotype(model.getStereotype());
	figure.setName(model.getName());
//	figure.refreshInfo();
	figure.repaint();

	for(int i=0; i<model.getSourceRelationships().size(); i++) {
		Relationship conn = ((Relationship) model.getSourceRelationships().get(i));
		RelationshipEditPartDSC conn_part = (RelationshipEditPartDSC) getViewer().getEditPartRegistry().get(conn);
		if(conn_part!=null)	conn_part.refreshVisuals();
	}
}

public void refreshAutotransition(Rectangle oldBounds, Rectangle newBounds, Point pos_state, Dimension diff_pos, Container movedState) {
	
	// Calcolo la misura dello cambiamento della dimensione
	Dimension diff_dim = newBounds.getSize().getDifference(oldBounds.getSize());
	
	//
	Container ancestor;
	if (movedState==null)
		ancestor=getCastedModel();
	else
		ancestor=movedState;
	
	// Cerco le autotransizioni di questo stato
	List source_list = getCastedModel().getSourceRelationships();
	List target_list = getCastedModel().getTargetRelationships();
	for(int i=0; i<source_list.size(); i++) {
		if(target_list.contains(source_list.get(i))) {
			// Ho trovato una autotransizione
			Transition conn = (Transition) source_list.get(i);
			// Sposto in maniera statica le autotransizioni di questo stato
			Dimension dim_state = oldBounds.getSize();
			Dimension diff;

			int delta_x=0, delta_y=0; 
			for(int j=0; j<conn.getBendpoints().size(); j++) {
				Point pos_bend = ((Point)conn.getBendpoints().get(j));

				if(pos_bend.x>pos_state.x+dim_state.width)  delta_x = diff_dim.width;
				else if(pos_bend.x<pos_state.x)				delta_x = 0;
				else										delta_x = diff_dim.width/2;

				if(pos_bend.y>pos_state.y+dim_state.height) delta_y = diff_dim.height;
				else if(pos_bend.y<pos_state.y)				delta_y = 0;
				else										delta_y = diff_dim.height/2;

				diff = new Dimension(diff_pos.width+delta_x,diff_pos.height+delta_y);
				((Point)conn.getBendpoints().get(j)).setLocation(((Point)conn.getBendpoints().get(j)).getTranslated(diff));
			}
		}
		//codice relativo alle transizioni con target interno allo stato che si sta muovendo
		else if (EcoreUtil.isAncestor(ancestor,((Transition)source_list.get(i)).getTarget()))
		{
			Transition conn = (Transition) source_list.get(i);
			Dimension dim_state = oldBounds.getSize();
			Dimension diff;

			int delta_x=0, delta_y=0; 
			for(int j=0; j<conn.getBendpoints().size(); j++) {
				Point pos_bend = ((Point)conn.getBendpoints().get(j));

				if(pos_bend.x>pos_state.x+dim_state.width)  delta_x = diff_dim.width;
				else if(pos_bend.x<pos_state.x)				delta_x = 0;
				else										delta_x = diff_dim.width/2;

				if(pos_bend.y>pos_state.y+dim_state.height) delta_y = diff_dim.height;
				else if(pos_bend.y<pos_state.y)				delta_y = 0;
				else										delta_y = diff_dim.height/2;

				diff = new Dimension(diff_pos.width+delta_x,diff_pos.height+delta_y);
				((Point)conn.getBendpoints().get(j)).setLocation(((Point)conn.getBendpoints().get(j)).getTranslated(diff));
			}
			
		}
		
	}
	
	// Verifico se è cambiata la dimensione di questo stato;
	// se è così non è cambiata quella degli stati interni
	if(!oldBounds.getSize().equals(newBounds.getSize()))
		newBounds.setSize(oldBounds.getSize());

	// Sposto in maniera statica le autotransizioni di tutti i figli di questo stato
	for(int i=0; i<getChildren().size(); i++) {
		if(getChildren().get(i) instanceof DSCStateEditPart)
			((DSCStateEditPart) getChildren().get(i)).refreshAutotransition(oldBounds,newBounds,pos_state,diff_pos,ancestor);
	}
}



public void setAutomaticResize(boolean flag) {
	automaticResize = flag;
}

/**
 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification notification) {
	int featureId = notification.getFeatureID( Container.class );
	switch( featureId ) {
		case GenericUMLDiagramModelPackage.CONTAINER__TARGET_RELATIONSHIPS:
			refreshTargetConnections();
			break;		
		case GenericUMLDiagramModelPackage.CONTAINER__SOURCE_RELATIONSHIPS:
			refreshSourceConnections();
			break;
			
		//se il simple è stato promosso a composite installo il gestore del layout
		case DscDiagramModelPackage.DSC_STATE__IS_SIMPLE:
			XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
			if (((DSCState) getModel()).isIsSimple()==false)
				installEditPolicy(EditPolicy.LAYOUT_ROLE,  new CompositeStateXYLayoutEditPolicy(layout));
			refreshVisuals();
			break;
		case GenericUMLDiagramModelPackage.CONTAINER__LOCATION:
		case GenericUMLDiagramModelPackage.CONTAINER__SIZE:
		case GenericUMLDiagramModelPackage.CONTAINER__NAME:
		case GenericUMLDiagramModelPackage.CONTAINER__STEREOTYPE:
		case GenericUMLDiagramModelPackage.CONTAINER__DESCRIPTION:
			refreshVisuals();
			break;
		case GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS:
//		case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
			refreshChildren();
		    break;
		default:
			refreshVisuals();
	   }
	}
}
