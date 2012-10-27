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

import genericUMLDiagramEditor.editParts.VisualElementEditPart;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;

import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import eldaEditor.editpolicies.ElementComponentEditPolicyDSC;
import eldaEditor.editpolicies.GraphicalNodeEditPolicyImplDSC;
import eldaEditor.figures.DeepHistoryFigure;
import eldaEditor.figures.ShallowHistoryFigure;
import eldaEditor.figures.StartPointFigure;

/**
 * EditPart used for VisualElement instances
 * @author samuele
 */
public class VisualElementEditPartDSC extends VisualElementEditPart {
	
/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// allow removal of the associated model element
	//Non permetto la cancellazione di questi elementi del modello rimuovendo il policy
	if (!(getModel()instanceof DeepHistory || getModel() instanceof ShallowHistory || getModel() instanceof StartPoint))
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicyDSC());
	
	installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new GraphicalNodeEditPolicyImplDSC());
}
	
/**
 * Return a IFigure depending on the instance of the current model element.
 * This allows this EditPart to be used for both sublasses of Shape. 
 */
/**
 * @return
 */
protected IFigure createFigureForModel() {
	if (getModel() instanceof HorizontalLineSeparator || getModel() instanceof VerticalLineSeparator ||
		getModel() instanceof Note || getModel() instanceof TextLabel)
		return super.createFigureForModel();
	else if (getModel() instanceof StartPoint)
		return new StartPointFigure();
	else if (getModel() instanceof DeepHistory)
		return new DeepHistoryFigure();
	else if (getModel() instanceof ShallowHistory )
		return new ShallowHistoryFigure();
	else 
		// if Shapes gets extended the conditions above must be updated
		throw new IllegalArgumentException();
}

public void performRequest(Request req) {
	if(req.getType().equals(REQ_OPEN) && (getModel() instanceof Note || getModel() instanceof TextLabel) )
		super.performRequest(req);
}

/* (non-Javadoc)
 * @see java.beans.PropertyChangeListener#refreshVisuals(java.beans.PropertyChangeEvent)
 */
protected void refreshVisuals() {
	if (getModel() instanceof Note || getModel() instanceof TextLabel)
		super.refreshVisuals();
	else {
		Point Location = new Point(getCastedModel().getLocation().x,getCastedModel().getLocation().y);
		Rectangle bounds;
//		if (getModel() instanceof Join) {
//			if(((Join)getModel()).isIsHorizontal()) {
//				Dimension horizontalDimension=new Dimension(getCastedModel().getSize().width,6);
//				bounds = new Rectangle(Location,horizontalDimension);
//			}
//			else {
//				Dimension verticalDimension=new Dimension(6,getCastedModel().getSize().height);
//				bounds = new Rectangle(Location,verticalDimension);
//			}
//		}
//		else if (getModel() instanceof Fork) {
//			if(((Fork)getModel()).isIsHorizontal()) {
//				Dimension horizontalDimension=new Dimension(getCastedModel().getSize().width,6);
//				bounds = new Rectangle(Location,horizontalDimension);
//			}
//			else {
//				Dimension verticalDimension=new Dimension(6,getCastedModel().getSize().height);
//				bounds = new Rectangle(Location,verticalDimension);
//			}
//		}
		 if (getModel() instanceof StartPoint){ //|| getModel() instanceof EndPoint) {
			// transfer the size and location from the model instance to the corresponding figure
			bounds = new Rectangle(Location,getCastedModel().getSize());
			Figure figure;
			if(getModel() instanceof StartPoint)
				figure = (StartPointFigure) getFigure();
			else if (getModel() instanceof ShallowHistory)
				figure= (ShallowHistoryFigure)getFigure();
			// allora è un deep hostory
			else
				figure = (DeepHistoryFigure) getFigure();
			figure.repaint();
		}
		else
			bounds = new Rectangle(Location,getCastedModel().getSize());

		figure.setBounds(bounds);
		// notify parent container of changed position & location
		((GraphicalEditPart) getParent()).setLayoutConstraint(this, figure, bounds);
	}
}
}
