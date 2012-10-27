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

import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;
import genericUMLDiagramModel.Note;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.EditPart;

/**
 * A command to resize and/or move a VisualElement
 * 
 * @author marguu&zaza
 *
 */
public class VisualElementSetConstraintCommand extends Command {
/** Stores the new size and location. */
protected final Rectangle newBounds;
/** Stores the old size and location. */
protected Rectangle oldBounds;
/** A request to move/resize an edit part. */
protected final ChangeBoundsRequest request;

/** Shape to manipulate. */
protected final VisualElement shape;
protected EditPart child;

//La distanza minima dal bordo sinistro o quello superiore
protected int MIN_DIST = 20;

public VisualElementSetConstraintCommand(VisualElement shape, ChangeBoundsRequest req, 
		Rectangle newBounds,EditPart child) {
	if (shape == null || req == null || newBounds == null) {
		throw new IllegalArgumentException();
	}
	this.shape = shape;
	this.request = req;
	this.newBounds = newBounds.getCopy();
	this.child=child;
	setLabel("move / resize");
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	Object type = request.getType();
	
	// make sure the Request is of a type we support:
	return (RequestConstants.REQ_MOVE.equals(type)
			|| RequestConstants.REQ_MOVE_CHILDREN.equals(type) 
			|| RequestConstants.REQ_RESIZE.equals(type)
			|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
			|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type));
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	oldBounds = new Rectangle(shape.getLocation(), shape.getSize());
	
	Dimension newSize;
	if(shape instanceof HorizontalLineSeparator)
		newSize=new Dimension(newBounds.getSize().width,shape.getSize().height);
	else if(shape instanceof VerticalLineSeparator)
		newSize=new Dimension(shape.getSize().width,newBounds.getSize().height);
	else if(shape instanceof Note) {
		newSize = newBounds.getSize();
		
		// Verifico di non aver ristretto troppo la nota
		if(newSize.width<MIN_DIST*4) newSize.width = MIN_DIST*4;
		if(newSize.height<MIN_DIST*2) newSize.height = MIN_DIST*2;
	}
	else 
		newSize = newBounds.getSize();
	
	// Verifico che l'elemento non vengo portato o troppo in alto o troppo a sx
	Point newPos = newBounds.getLocation();
	if(newPos.x<MIN_DIST) newPos.x = MIN_DIST;
	if(newPos.y<MIN_DIST) newPos.y = MIN_DIST;
	
	shape.setSize(newSize);
	shape.setLocation(newPos);
	
	if(shape instanceof HorizontalLineSeparator)
		shape.setLocation(newBounds.getLocation());
}

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	execute();
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	shape.setSize(oldBounds.getSize());
	shape.setLocation(oldBounds.getLocation());
}
}
