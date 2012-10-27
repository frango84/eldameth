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

package eldaEditor.editpolicies;

import genericUMLDiagramEditor.editPolicies.GraphicalNodeEditPolicyImpl;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import dscDiagramModel.DSCState;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import eldaEditor.commands.RelationshipCreateCommandDSC;
import eldaEditor.commands.RelationshipReconnectCommandDSC;

/**
 * Classe che ritorna i comandi  necessari alla creazione 
 * e alla riconnessione di una connessione
 * @author samuele
 *
 */
public class GraphicalNodeEditPolicyImplDSC extends GraphicalNodeEditPolicyImpl {
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		Relationship conn = (Relationship)request.getNewObject();
		ModelElement source = (ModelElement)getHost().getModel();

//		// Un top state non può essere source di una transition
//		if(conn instanceof Transition && source instanceof DSCState && source.getParent()!=null && isTopMost(source))
//			return null;
		
		// Se non posso effettuare una Transition
		if(conn instanceof Transition && !checkSourceTransition()) return null;
		
		Command cmd = new RelationshipCreateCommandDSC((Relationship)request.getNewObject(), (ModelElement)getHost().getModel());
		request.setStartCommand(cmd);
		return cmd;
	}	

	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		ModelElement target = (ModelElement)getHost().getModel();
		RelationshipCreateCommandDSC command = (RelationshipCreateCommandDSC) request.getStartCommand();
		command.setTarget(target);
		return command;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		Relationship conn = (Relationship)request.getConnectionEditPart().getModel();
		ModelElement source = (ModelElement)getHost().getModel();

		// Un top state non può essere source di una transition
		if(source instanceof DSCState && source.getParent()!=null && isTopMost(source)) return null;

		// Se non posso effettuare una Transition
		if(conn instanceof Transition && !checkSourceTransition()) return null;

		return new RelationshipReconnectCommandDSC(conn, (ModelElement)getHost().getModel(), true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// Se non posso effettuare una relazione
		if(!checkRelationship(request.getLocation().getCopy())) return null;

		ModelElement target = (ModelElement)getHost().getModel();

		// Un top state non può essere target di una transition
		if(target instanceof DSCState && target.getParent()!=null && isTopMost(target)) return null;
		
		return new RelationshipReconnectCommandDSC(
				(Relationship)request.getConnectionEditPart().getModel(),
				(ModelElement)getHost().getModel(), false);
	}
	
	private boolean checkSourceTransition() {
		ModelElement source = (ModelElement)getHost().getModel();
		if(source instanceof StartPoint){// || source instanceof Join) {
			// Se ci sono altre transizioni in cui lo StartPoint (o il Fork) è source non continuo
			for(int i=0; i<source.getSourceRelationships().size(); i++) {
				if(source.getSourceRelationships().get(i) instanceof Transition)
					return false;
			}
		}
		if(source instanceof Relationship ||source instanceof Note 
			|| source instanceof HorizontalLineSeparator || source instanceof VerticalLineSeparator)
			return false;
		
		// Default
		return true;
	}
	
	protected boolean isTopMost(ModelElement newElement) {
		// Un top state non può essere source o target di una transition
		int count = 0;
		if(newElement.getParent()!=null && ((VisualElement)newElement).getContainer()==null) {
			for(int i=0; i<newElement.getParent().getModelElements().size(); i++) {
				// Controlla quanti elementi hanno getContainer==null
				if((newElement.getParent().getModelElements().get(i) instanceof Classifier || 
						newElement.getParent().getModelElements().get(i) instanceof Container) 
					&& ((VisualElement)newElement.getParent().getModelElements().get(i)).getContainer()==null)
					count++;
			}
		}
		return (count==1);
	}
}
