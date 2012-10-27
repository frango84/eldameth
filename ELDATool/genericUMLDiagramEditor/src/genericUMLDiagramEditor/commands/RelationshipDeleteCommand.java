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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;

import org.eclipse.gef.commands.Command;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A command to disconnect (remove) a connection from its endpoints
 * 
 * @author marguu&zaza
 * 
 */
public class RelationshipDeleteCommand extends Command {

/** Connection instance to disconnect. */
protected final Relationship connection;
protected Command com;
protected ModelElement source,target;
protected int srcIndex, targetIndex;
protected GenericDiagram parent;
protected List commands=new ArrayList();
protected int size1,size2;

public RelationshipDeleteCommand(Relationship conn) {
	if (conn == null) throw new IllegalArgumentException();
	
	setLabel("relationship deletion");
	this.connection = conn;
	this.source = conn.getSource();
	this.target = conn.getTarget();
	this.parent = (GenericDiagram) EcoreUtil.getRootContainer(connection);
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	return connection != null && source != null && target != null;
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	srcIndex = source.getSourceRelationships().indexOf(connection);
	targetIndex = target.getTargetRelationships().indexOf(connection);
	
	this.size1=connection.getSourceRelationships().size();
	for(int i=0;i<size1;i++) {
		Relationship link = (Relationship) connection.getSourceRelationships().get(0);
		com=new RelationshipDeleteCommand(link);
		com.execute();
		commands.add(com);
	}
	
	this.size2=connection.getTargetRelationships().size();
	for(int i=0;i<size2;i++) {
		Relationship link = (Relationship) connection.getTargetRelationships().get(0);
		com=new RelationshipDeleteCommand(link);
		com.execute();
		commands.add(com);
	}
	
	if (srcIndex != -1 && targetIndex != -1) {
		source.getSourceRelationships().remove(connection);
		target.getTargetRelationships().remove(connection);
	}
	
	// Rimuovo la relazione dal diagramma
	connection.setParent(null);
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
	if (srcIndex != -1 && targetIndex != -1) {
		if(!source.getSourceRelationships().contains(connection))
			source.getSourceRelationships().add(srcIndex, connection);
		if(!target.getTargetRelationships().contains(connection))
			target.getTargetRelationships().add(targetIndex, connection);

//		source.getSourceRelationships().add(srcIndex, connection);
//		target.getTargetRelationships().add(targetIndex, connection);

	    Iterator i = commands.iterator();
	    while (i.hasNext()) {
	        Command o =(Command) i.next();
	        o.undo();
	        i.remove();
	    };
	}
	
	connection.setParent(parent);
}
}
