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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramEditor.editPolicies.ConnTextMovePolicy;

/**
 * The controller for the ConnectionLabel
 * 
 * @author marguu&zaza
 *
 */
public class ConnLabelEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

	protected RelationshipEditPart parent;

	public IFigure createFigure() {
		// Memorizzo l'editPart padre
		parent = (RelationshipEditPart) getParent();

		// Restituisco una nuova label
		return new Label();
	}

	/**
	 * @see org.eclipse.gef.EditPart#activate()
	 */
	public void activate() {
		if (isActive()==false) {
			super.activate();
			((ConnectionLabel)getModel()).addPropertyChangeListener(this);
		}
	}

	/**
	 * @see org.eclipse.gef.EditPart#deactivate()
	 */
	public void deactivate() {
		if (isActive()) {
			super.deactivate();
			((ConnectionLabel)getModel()).removePropertyChangeListener(this);
		}
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt)  {
		String request = evt.getPropertyName();
		System.out.println("REQUEST________"+ request);
		if(request.equals("positionChange") || request.equals("textChange"))
			refreshVisuals();
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	public void createEditPolicies() {
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ConnTextMovePolicy());
	}

	public DragTracker getDragTracker(Request request) {
		return new ConnTextTracker(this,parent); 	
	}

	/**
	 * @see org.eclipse.gef.EditPart#performRequest(org.eclipse.gef.Request)
	 */
	public void performRequest(Request req) {
		if(req.getType().equals(REQ_OPEN))
			parent.performRequest(req);
	}

	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	public void refreshVisuals() {
		Label figure = (Label)getFigure();
		PolylineConnection connFigure = (PolylineConnection)parent.getFigure();

		ConnLabelConstraint constraint = new ConnLabelConstraint(createText(), getCastedModel(),connFigure);
		parent.setLayoutConstraint(this,figure,constraint);
	}

	/**
	 * Return the ConnectionLabel associated to the editPart
	 */
	public ConnectionLabel getCastedModel() {
		return (ConnectionLabel)getModel();
	}

	/**
	 * Return the text of the label
	 */
	public String createText() {
		// Ricavo le informazioni su stereotipo e nome
		String stereotype = getCastedModel().getRelationship().getStereotype();
		String name = getCastedModel().getRelationship().getName();

		// Costruisco la label da stampare
		StringBuffer text = new StringBuffer();
		if(stereotype!=null && stereotype.trim().length()>0)
			text.append("«" + stereotype + "»");
		if((stereotype!=null && stereotype.trim().length()>0) 
				&& (name!=null && name.trim().length()>0))
			text.append("\n");
		if(name!=null && name.trim().length()>0)
			text.append(name);

		return text.toString();
	}

	/**
	 * Sets selected the label when the connection is selected and viceversa
	 */
	public void setSelected(int value) {
		if(createText().trim().length()>0 && getCastedModel().isIsVisible()) {
			super.setSelected(value);

			if(parent.getSelected()!=value)
				parent.setSelected(value);
		}
		else {
			super.setSelected(SELECTED_NONE);
			if(parent.getSelected()!=value)
				parent.setSelected(value);
		}
	}
}
