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

import genericUMLDiagramEditor.dialogs.ContainerPropertiesInputDialog;
import genericUMLDiagramEditor.editPolicies.ContainerXYLayoutEditPolicy;
import genericUMLDiagramEditor.editPolicies.ElementComponentEditPolicy;
import genericUMLDiagramEditor.editPolicies.GraphicalNodeEditPolicyImpl;
import genericUMLDiagramEditor.editPolicies.TextEditPolicy;
import genericUMLDiagramEditor.figures.ContainerFigure;
import genericUMLDiagramEditor.figures.GenericFigure;

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.PlatformUI;

/**
 * EditPart used for Container instances
 * 
 * @author marguu&zaza
 *
 */
public class ContainerEditPart extends GenericDiagramAbstractEditPart {
/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// allow removal of the associated model element
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
	
	XYLayout layout = (XYLayout) getContentPane().getLayoutManager();
	installEditPolicy(EditPolicy.LAYOUT_ROLE,  new ContainerXYLayoutEditPolicy(layout));
	
	// allow the creation of connections and and the reconnection of connections
	installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicyImpl());
	
	// permette la modifica rapida del nome del container
	installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TextEditPolicy());
}

/**
 * Return a IFigure depending on the instance of the current model element.
 * This allows this EditPart to be used for both sublasses of Shape. 
 *
 * @see genericUMLDiagramEditor.editParts.GenericDiagramAbstractEditPart#createFigureForModel()
 */
protected IFigure createFigureForModel() {
	 if (getModel() instanceof Container) {
	 	return new ContainerFigure((Container)getModel());
	} 
	else {
		// if Shapes gets extended the conditions above must be updated
		throw new IllegalArgumentException();
	}
}

/**
 * Return the Container associated to the edtiPart
 */
protected Container getCastedModel() {
	return (Container) getModel();
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	return getCastedModel().getVisualElements();
}

/**
 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
 */
public void performRequest(Request req) {
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Container ) ) {
		Container model=(Container)getModel();

		ContainerPropertiesInputDialog dialog=new ContainerPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();
		if(!dialog.getCancelled()) {
			if (model.getParent()!=null){
				Iterator i = model.getParent().getModelElements().iterator();
			    while (i.hasNext()) {
			        ModelElement m =(ModelElement) i.next();
			       
			        if ((m instanceof Container)&&(model!=m)&&(model.getName().equals(m.getName())))
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, contenitore: "+model.getName()+" già esistente nel diagramma!");
			    }
			}
			else{
				Iterator i = model.getContainer().getVisualElements().iterator();
			    while (i.hasNext()) {
			        VisualElement m =(VisualElement) i.next();
			        if ((m instanceof Container)&&(model!=m)&&(model.getName().equals(m.getName())))
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, contenitore: "+model.getName()+" già esistente nel contenitore!");
			    }
			}
		}
	}
	else if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
		GenericFigure fig = (GenericFigure) getFigure();
		Rectangle bounds = fig.getNameBounds().getCopy();
		bounds.translate(2,4);
		fig.translateToAbsolute(bounds);
		performDirectEdit(bounds, getCastedModel().getName());
	}
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
 */
protected void refreshVisuals() {
	// transfer the size and location from the model instance to the corresponding figure
	Rectangle bounds = new Rectangle(getCastedModel().getLocation().x,getCastedModel().getLocation().y,getCastedModel().getSize().width,	
			getCastedModel().getSize().height);
	figure.setBounds(bounds);
	// notify parent container of changed position & location
	// if this line is removed, the XYLayoutManager used by the parent container 
	// (the Figure of the ShapesDiagramEditPart), will not know the bounds of this figure
	// and will not draw it correctly.
	((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);

	ContainerFigure figure = (ContainerFigure)getFigure();
	Container model = (Container)getModel();
	figure.setStereotype(model.getStereotype());
	figure.setName(model.getName());
	figure.repaint();
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
		case GenericUMLDiagramModelPackage.CONTAINER__LOCATION:
		case GenericUMLDiagramModelPackage.CONTAINER__SIZE:
		case GenericUMLDiagramModelPackage.CONTAINER__NAME:
		case GenericUMLDiagramModelPackage.CONTAINER__STEREOTYPE:
		case GenericUMLDiagramModelPackage.CONTAINER__DESCRIPTION:
			refreshVisuals();
			break;
		case GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS:
			refreshChildren();
		    break;
		default:
			refreshVisuals();
	   }
	}
}
