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

package genericUMLDiagramEditor.commands;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.EditPart;

import org.eclipse.gef.commands.Command;

import genericUMLDiagramModel.ConnectionLabel;

import genericUMLDiagramEditor.editParts.ConnLabelConstraint;
import genericUMLDiagramEditor.editParts.ConnLabelEditPart;
import genericUMLDiagramEditor.editParts.RelationshipEditPart;

/**
 * A command to move the ConnectionLabel
 *
 * @author marguu&zaza
 */
public class MoveConnTextCommand extends Command {
  
  ConnectionLabel label = null;
  Point location = null;
  PolylineConnection parent = null;
  Figure child = null;
  Point oldOffset, newOffset;
  EditPart part;
  
  public MoveConnTextCommand(EditPart part, ConnectionLabel label,Figure parent,Point location) {
		this.label = label;
		this.parent = (PolylineConnection) parent;
		this.child = (Figure) parent.getChildren().get(0);
		this.location = location;
		this.part = part;
		setLabel("move connection label");
	  }
 
/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	// Memorizzo il vecchio offset
	oldOffset = label.getOffset();
	
	// Creo il nuovo offset
	newOffset = oldOffset.getTranslated(location);

	// Aggiorno le figure
	updateFigure(newOffset);
  }

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	  updateFigure(newOffset);
  }

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	updateFigure(oldOffset);
  }
  
/**
 * Update the label
 * 
 * @param offset the distance from the center of the relationship
 */
private void updateFigure(Point offset) {
	// Aggiorno l'offset della Connection
	label.setOffset(offset);

	// Aggiorna i constraint della figure padre
	RelationshipEditPart parentEP = (RelationshipEditPart) part.getViewer().getEditPartRegistry().get(label.getRelationship());
	ConnLabelEditPart childEP = (ConnLabelEditPart) part.getViewer().getEditPartRegistry().get(label);
	ConnLabelConstraint constraint = new ConnLabelConstraint(childEP.createText(),label,parent);
	parentEP.setLayoutConstraint(childEP,child,constraint);
  }
}
