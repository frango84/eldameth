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

import genericUMLDiagramModel.*;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;

/**
 * A command to create a connection between two VisualElement
 * 
 * @author marguu&zaza
 */
public class RelationshipCreateCommand extends Command {
/** The connection instance. */
protected Relationship connection;
/** Start endpoint for the connection. */
protected final ModelElement source;
/** Target endpoint for the connection. */
protected ModelElement target;
/** The top-most diagram. */
protected GenericDiagram parent;

public RelationshipCreateCommand(Relationship conn, ModelElement source) {
	super("relationship creation");
	if (source == null) {
		throw new IllegalArgumentException();
	}

	this.source = source;
	this.connection = conn;
	this.parent=(GenericDiagram) EcoreUtil.getRootContainer(source);
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	if(source instanceof TextLabel || source instanceof HorizontalLineSeparator ||source instanceof VerticalLineSeparator ||
		target instanceof TextLabel || target instanceof HorizontalLineSeparator || target instanceof VerticalLineSeparator)
		return false;
	if (source.equals(target))
		return false;
	if(source instanceof Relationship && target instanceof Relationship)
		return false;
	return true;
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	// Cerco il nome del file
	String strFile = CreateFileName.getName();

	// Setto l'identificativo del nuovo oggetto al più alto identificativo
	// degli oggetti contenuti dal padre incrementato di una unità
	if (connection.getID()==null) {
		// Setto l'identificativo
		connection.setID(strFile + (parent.getMaxID()+1));
	
		// Incremento il maxID del padre
		parent.setMaxID(parent.getMaxID()+1);
	}
	
	GenericUMLDiagramModelFactory factory = GenericUMLDiagramModelFactory.eINSTANCE;
	ConnectionLabel label = factory.createConnectionLabel();
	label.setRelationship(connection);
	
	// Setto l'ID della ConnectionLabel
	if (label.getID()==null) {
		// Setto l'identificativo
		label.setID(strFile + (parent.getMaxID()+1));
	
		// Incremento il maxID del padre
		parent.setMaxID(parent.getMaxID()+1);
	}
	
	// create a new connection between source and target
	connection.setSource(source);
	connection.setTarget(target);
	connection.setParent(parent);
}

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	execute();
}

/**
 * Set the target endpoint for the connection.
 * @param target that target endpoint (a non-null Shape instance)
 * @throws IllegalArgumentException if target is null
 */
public void setTarget(ModelElement target) {
	if (target == null) {
		throw new IllegalArgumentException();
	}
	this.target = target;
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	connection.setSource(null);
	connection.setTarget(null);
}
}
