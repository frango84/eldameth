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

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.gef.commands.Command;

/**
 * A command to remove a shape from its parent
 *
 * @author samuele
 */
public class ElementDeleteCommandDSC extends Command {
/** Shape to remove. */
private final VisualElement child;
private Command com;

public ElementDeleteCommandDSC(VisualElement child) {
	if (child == null) {
		throw new IllegalArgumentException();
	}
	setLabel("element deletion");
	this.child = child;
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	if(child instanceof Container) {
		com = new DSCStateDeleteCommand((Container)child);
		if (com.canExecute()) com.execute();
	}
	else if(child instanceof VisualElement) {
		com = new VisualElementDeleteCommandDSC(child);
		if (com.canExecute()) com.execute();
	}
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
	com.undo();	
}
}
