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

package eldaEditor.commands;

import genericUMLDiagramEditor.commands.VisualElementSetConstraintCommand;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import eldaEditor.editparts.VisualElementEditPartDSC;

/**
 * A command to resize and/or move a shape.
 * The command can be undone or redone.
 *
 *@author samuele
 */
public class VisualElementSetConstraintCommandDSC extends VisualElementSetConstraintCommand {
/**
 * Create a command that can resize and/or move a shape. 
 * @param shape	the shape to manipulate
 * @param req		the move and resize request
 * @param newBounds the new size and location
 * @throws IllegalArgumentException if any of the parameters is null
 */
public VisualElementSetConstraintCommandDSC(VisualElement shape, ChangeBoundsRequest req, 
		Rectangle newBounds,VisualElementEditPartDSC child) {
	super(shape,req,newBounds,child);
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	oldBounds = new Rectangle(shape.getLocation(), shape.getSize());
	
	Dimension newSize;
	if(shape instanceof HorizontalLineSeparator || shape instanceof VerticalLineSeparator)
		super.execute();
	else {
		if( shape instanceof StartPoint || shape instanceof DeepHistory || shape instanceof  ShallowHistory){// || shape instanceof EndPoint ) {
			// Fisso le nuove dimensioni in maniera che quando ingrandisco
			// la figura prendo la dimensione massima, altrimenti la minima
			Dimension old_size = shape.getSize();
			Dimension new_size = newBounds.getSize();
			int new_dim=0;
			if(old_size.height<new_size.height || old_size.width<new_size.width)
				new_dim = Math.max(newBounds.width,newBounds.height);
			else
				new_dim = Math.min(newBounds.width,newBounds.height);
			newBounds.setSize(new_dim,new_dim);
			newSize = newBounds.getSize();
		}
		else 
			newSize = newBounds.getSize();
		shape.setSize(newSize);
		
		// Verifico che l'elemento non vengo portato o troppo in alto o troppo a sx
		Point newPos = newBounds.getLocation();
		if(newPos.x<MIN_DIST) newPos.x = MIN_DIST;
		if(newPos.y<MIN_DIST) {
			if(shape.getContainer()!=null)  newPos.y = 2*MIN_DIST;
			else 							newPos.y = MIN_DIST;
		}
		shape.setLocation(newPos);
	}
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	execute();
}
}
