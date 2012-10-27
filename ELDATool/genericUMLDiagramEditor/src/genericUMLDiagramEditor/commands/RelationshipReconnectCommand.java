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

import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;

import org.eclipse.gef.commands.Command;

/**
 * A command to reconnect a connection to a different start point or end point.
 * 
 * @author marguu&zaza
 * 
 */
public class RelationshipReconnectCommand extends Command {

/** The connection instance to reconnect. */
protected Relationship connection;
/** The new source endpoint. */
protected ModelElement newElement;
/** The original source endpoint. */
protected ModelElement oldElement;

protected int viewIndex;

protected boolean isSource;

public RelationshipReconnectCommand(Relationship conn,ModelElement newElement,boolean isSource ) {
	if (conn == null) {
		throw new IllegalArgumentException();
	}
	this.connection = conn;
	this.newElement=newElement;
	this.isSource=isSource;
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	return connection != null && newElement != null;
}

/**
 * Reconnect the connection to newSource (if setNewSource(...) was invoked before)
 * or newTarget (if setNewTarget(...) was invoked before).
 */
public void execute() {
	if (isSource) {
		oldElement = connection.getSource();
		viewIndex = oldElement.getSourceRelationships().indexOf(connection);
		connection.setSource(newElement);
	} else {
		oldElement = connection.getTarget();
		viewIndex = oldElement.getTargetRelationships().indexOf(connection);
		connection.setTarget(newElement);
	}
}

/**
 * Reconnect the connection to its original source and target endpoints.
 */
public void undo() {
	if (isSource) {
		newElement.getSourceRelationships().remove(connection);
		oldElement.getSourceRelationships().add(viewIndex, connection);
	} else {
		newElement.getTargetRelationships().remove(connection);
		oldElement.getTargetRelationships().add(viewIndex, connection);
	}
	oldElement = null;
}
}
