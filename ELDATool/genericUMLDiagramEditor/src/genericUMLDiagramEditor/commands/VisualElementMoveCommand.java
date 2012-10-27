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

import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

/**
 * A command to move a VisualElement
 *
 * @author marguu&zaza
 */
public class VisualElementMoveCommand extends Command {
/** Stores the new size and location. */
private final Point newPos;
/** Stores the old size and location. */
private Point oldPos;
private VisualElement shape;

public VisualElementMoveCommand(Point newPos,VisualElement child) {
	if (newPos == null || child == null) {
		throw new IllegalArgumentException();
	}
	this.newPos = newPos.getCopy();
	this.shape=child;
	setLabel("move / resize");
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	oldPos = shape.getLocation();
	shape.setLocation(newPos);
}

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	shape.setLocation(newPos);
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	shape.setLocation(oldPos);
}
}
