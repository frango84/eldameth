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

import genericUMLDiagramEditor.dialogs.DiagramVisualElementPropertiesInputDialog;
import genericUMLDiagramEditor.dialogs.NotePropertiesInputDialog;
import genericUMLDiagramEditor.dialogs.TextLabelPropertiesInputDialog;
import genericUMLDiagramEditor.editPolicies.ElementComponentEditPolicy;
import genericUMLDiagramEditor.editPolicies.GraphicalNodeEditPolicyImpl;
import genericUMLDiagramEditor.editPolicies.TextEditPolicy;
import genericUMLDiagramEditor.figures.ClassifierFigure;
import genericUMLDiagramEditor.figures.GenericFigure;
import genericUMLDiagramEditor.figures.HorizontalLineFigure;
import genericUMLDiagramEditor.figures.NoteFigure;
import genericUMLDiagramEditor.figures.TextLabelFigure;
import genericUMLDiagramEditor.figures.VerticalLineFigure;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.HorizontalLineSeparator;

import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
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
 * EditPart used for VisualElement instances
 *
 * @author marguu&zaza
 * 
 */
public class VisualElementEditPart extends GenericDiagramAbstractEditPart {
/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// allow removal of the associated model element
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
	
	// permette la creazione di connessioni
	installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicyImpl());
	
	// permette la modifica rapida del nome del classifier
	installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new TextEditPolicy());
}
	
/**
 * Return a IFigure depending on the instance of the current model element.
 * This allows this EditPart to be used for both sublasses of Shape. 
 */
/**
 * Return
 */
protected IFigure createFigureForModel() {
	if (getModel() instanceof Classifier) {
		ClassifierFigure classifierFigure=new ClassifierFigure((Classifier)getModel()); 
		classifierFigure.setBackgroundColor(ColorConstants.cyan);
		return classifierFigure;
	}
	else if (getModel() instanceof HorizontalLineSeparator) {
		return new HorizontalLineFigure();
	} 
	else if (getModel() instanceof VerticalLineSeparator) {
		return new VerticalLineFigure();
	} 
	else if (getModel() instanceof TextLabel) {
		return new TextLabelFigure(new String());
	} 
	if (getModel() instanceof Note) {
		return new NoteFigure(new String(),(Note)getModel());
//		return new NoteFigure(new Label());
	}
	else {
		// if Shapes gets extended the conditions above must be updated
		throw new IllegalArgumentException();
	}
}

/**
 * Return the VisualElement for this editPart
 */
protected VisualElement getCastedModel() {
	return (VisualElement) getModel();
}

/**
 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
 */
public void performRequest(Request req) {
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Classifier ) ) {
		Classifier model=(Classifier)getModel();

		DiagramVisualElementPropertiesInputDialog dialog=new DiagramVisualElementPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();
		if(!dialog.getCancelled()) {
			if (model.getParent()!=null){
				Iterator i = model.getParent().getModelElements().iterator();
			    while (i.hasNext()) {
			        ModelElement m =(ModelElement) i.next();
			       
			        if ((m instanceof Classifier)&&(model!=m)&&(model.getName().equals(m.getName())))
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, elemento: "+model.getName()+" già esistente nel diagramma!");
			    }
			}
			else{
				Iterator i = model.getContainer().getVisualElements().iterator();
			    while (i.hasNext()) {
			        VisualElement m =(VisualElement) i.next();
			        if ((m instanceof Classifier)&&(model!=m)&&(model.getName().equals(m.getName())))
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, elemento: "+model.getName()+" già esistente nel contenitore!");
			    }
			}
		}
	
	}
	else if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Note ) ) {
		Note model=(Note)getModel();

		NotePropertiesInputDialog dialog=new NotePropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();
	}
	else if(req.getType().equals(REQ_OPEN) && (getModel() instanceof TextLabel ) ) {
		TextLabel model=(TextLabel)getModel();
		
		TextLabelPropertiesInputDialog dialog=new TextLabelPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,model);
		dialog.open();
	}
	else if (req.getType() == RequestConstants.REQ_DIRECT_EDIT && (getModel() instanceof Classifier ) ) {
		GenericFigure fig = (GenericFigure) getFigure();
		Rectangle bounds = fig.getNameBounds().getCopy();
		bounds.translate(2,4);
		fig.translateToAbsolute(bounds);
		performDirectEdit(bounds, getCastedModel().getName());
	}
	
	refreshVisuals();
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
 */
protected void refreshVisuals() {
	// transfer the size and location from the model instance to the corresponding figure
	Point Location = new Point(getCastedModel().getLocation().x,getCastedModel().getLocation().y);
	Rectangle bounds = new Rectangle(getCastedModel().getLocation(),
									getCastedModel().getSize());
	
	figure.setBounds(bounds);
	((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
	
	if (getModel() instanceof Classifier){
		ClassifierFigure figure = (ClassifierFigure)getFigure();
		Classifier model = (Classifier)getModel();
		figure.setStereotype(model.getStereotype());
		figure.setName(model.getName());
		figure.repaint();
	}
	
	if (getModel() instanceof Note){
		Note model = (Note)getModel();
		NoteFigure figure = (NoteFigure)getFigure();
		figure.setComment(model.getComment());
		figure.repaint();
		
	}
	if (getModel() instanceof TextLabel){
		TextLabelFigure figure = (TextLabelFigure)getFigure();
		TextLabel model = (TextLabel)getModel();
		figure.setText(model.getText());
	    
	    bounds=new Rectangle(Location,FigureUtilities.getTextExtents(figure.getText(), figure.getFont()));	
	    ((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
	    figure.repaint();
	}
}

/**
 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification notification) {
	int featureId = notification.getFeatureID( VisualElement.class );
	switch( featureId ) {
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
			refreshTargetConnections();
			break;		
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
			refreshSourceConnections();
			break;
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION:
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE:
		case GenericUMLDiagramModelPackage.CLASSIFIER__NAME:
		case GenericUMLDiagramModelPackage.CLASSIFIER__STEREOTYPE:
		case GenericUMLDiagramModelPackage.CLASSIFIER__DESCRIPTION:
			refreshVisuals();
	   }
	}
}
