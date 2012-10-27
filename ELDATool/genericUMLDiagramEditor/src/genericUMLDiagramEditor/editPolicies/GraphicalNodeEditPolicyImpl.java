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

package genericUMLDiagramEditor.editPolicies;

import genericUMLDiagramEditor.commands.*;
import genericUMLDiagramEditor.editParts.*;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.TextLabel;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.*;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;

/**
 * Return the commands to create or reconnect a relationship
 *
 * @author marguu&zaza
 */
public class GraphicalNodeEditPolicyImpl extends GraphicalNodeEditPolicy {
	/**
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		Relationship conn = (Relationship)request.getNewObject();
		if (conn instanceof Relationship ) {
			Command cmd = new RelationshipCreateCommand((Relationship)request.getNewObject(),
					(ModelElement)getHost().getModel());
			request.setStartCommand(cmd);
			return cmd;
		}
		
		/*
		 * The disallow cursor will be shown IF you return null.  If you return
		 * UnexecutableCommand.INSTANCE, the disallow cursor will not appear.  This is
		 * because since this is the first step of the Command it doesn't check to 
		 * see if it's executable or not (which it most likely isn't).
		 */
		return null;
	}
	
	/**
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		ModelElement target = (ModelElement)getHost().getModel();
		RelationshipCreateCommand command = (RelationshipCreateCommand) request.getStartCommand();
		command.setTarget(target);
		return command;
	}

	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		return new RelationshipReconnectCommand(
				(Relationship)request.getConnectionEditPart().getModel(),
				(ModelElement)getHost().getModel(), false);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		Relationship conn = (Relationship)request.getConnectionEditPart().getModel();
		if (conn instanceof Relationship ) 
			return new RelationshipReconnectCommand(conn, (ModelElement)getHost().getModel(), true);
		return UnexecutableCommand.INSTANCE;
	}
	
	/**
	 * @param mouse		the mouse location
	 * Return			true if you can create a relationship
	 */
	protected boolean checkRelationship(Point mouse) {
		// Una ConnectionLabel non la posso collegare a niente
		if(getHost() instanceof RelationshipEditPart) {
			if(getHost().getChildren().get(0)!=null) {
				AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) getHost().getChildren().get(0);
				Figure fig = (Figure) part.getFigure();
				fig.translateFromParent(mouse);
				fig.translateToRelative(mouse);
				if(fig.getBounds().contains(mouse)) return false;
			}
		}
		
		// Ad una label di testo non posso collegare nulla
		if(getHost().getModel() instanceof TextLabel) return false;

		// Default
		return true;
	}
}
