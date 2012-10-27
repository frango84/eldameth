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

import genericUMLDiagramModel.*;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import genericUMLDiagramEditor.commands.RelationshipDeleteCommand;

/**
 * The controller for the Relationship in the outline
 * 
 * @author marguu&zaza
 * 
 */
public class RelationshipTreeEditPart extends GenericDiagramAbstractTreeEditPart  {
	public RelationshipTreeEditPart(Relationship model) {
		super(model);
	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies() {
		// allow removal of the associated model element
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new ConnectionEditPolicy() {
			protected Command getDeleteCommand(GroupRequest request) {
				return new RelationshipDeleteCommand((Relationship)getModel());
			}
		});
	}

	/**
	 * @see genericUMLDiagramEditor.editParts.GenericDiagramAbstractTreeEditPart#handlePropertyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	protected void handlePropertyChanged(Notification msg) {
		refreshVisuals();
	}
	
	/**
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
	 */
	protected String getText() {
		return ((Relationship)getModel()).toString()+" source:" +"\"" + ((Relationship)getModel()).getSource()+ "\""+"  target:"+ "\""+((Relationship)getModel()).getTarget()+"\"";
	}
}
