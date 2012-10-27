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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import genericUMLDiagramEditor.commands.MoveConnTextCommand;
import genericUMLDiagramEditor.editParts.RelationshipEditPart;
import genericUMLDiagramModel.ConnectionLabel;

/**
 * Return the commands to manage the ConnectionLabel
 * 
 * @author marguu&zaza
 *
 */
public class ConnTextMovePolicy extends NonResizableEditPolicy {
	// La distanza massima dal centro della connessione alla quale si
	// può trovare la label
	private final int MAX_DIST = 150;
	
	/**
	 * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#getMoveCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	public Command getMoveCommand(ChangeBoundsRequest request) {
	  ConnectionLabel model = (ConnectionLabel)getHost().getModel();
	  Point delta = request.getMoveDelta();
	  
	  Point oldOffset = model.getOffset();
	  Point newOffset = new Point(oldOffset.x+delta.x, oldOffset.y+delta.y);
	  
	  int x = newOffset.x;
	  int y = newOffset.y;
	  
	  // Se provo ad allontare troppo la label dal centro della transizione
	  // a cui è collegato mi viene impedita l'operazione
//	  if(Math.sqrt(x*x+y*y)>MAX_DIST)
//		  return null;
	  
	  MoveConnTextCommand command = new MoveConnTextCommand(getHost(),model,getParentFigure(),delta);
	  return command; 
	}
	
	/**
	 * Return  the figure of the parent EditPart, that is a Polyline
	 */
	public Figure getParentFigure() {
		RelationshipEditPart edge = (RelationshipEditPart)getHost().getViewer().getEditPartRegistry().get(((ConnectionLabel)getHost().getModel()).getRelationship());
		return (Figure)edge.getFigure();
	}
}
