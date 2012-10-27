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

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;

/**
 * A command to remove a VisualElement from its parent
 * 
 * @author marguu&zaza
 * 
 */
public class ContainerDeleteCommand extends Command {
/** Shape to remove. */
private final Container child;
/** ShapeDiagram to remove from. */
private List commands=new ArrayList();
private GenericDiagram parent;
private Container container;
private Map sourceConnections;
private Map targetConnections;
private int index;
private int size1,size2;
private Command com;

/**
 * Create a command that will remove the shape from its parent.
 * @param parent the ShapesDiagram containing the child
 * @param child    the Shape to remove
 * @throws IllegalArgumentException if any parameter is null
 */
public ContainerDeleteCommand(Container child) {
	if ( child == null) {
		throw new IllegalArgumentException();
	}

	setLabel("element deletion");
	this.child = child;
	
	if(child.getParent()!=null)	this.parent = child.getParent();
	else if(child.getContainer()!=null)this.container=child.getContainer();
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	if(parent!=null)		
		index = parent.getModelElements().indexOf(child);
	else if(container!=null)
		index=container.getVisualElements().indexOf(child);

	// There's always the possibility that this node has already been deleted by the
	// time this command is executed (eg., when the package containing this class
	// is also selected).  Hence, we check to see if the node exists in the diagram.
	// If not, do nothing.
	if (index == -1) return;

	sourceConnections = new HashMap();
	targetConnections = new HashMap();
	this.size1=child.getSourceRelationships().size();
	for(int i=0;i<size1;i++) {
		Relationship link = (Relationship) child.getSourceRelationships().get(0);
		sourceConnections.put(link, link.getTarget());
		com=new RelationshipDeleteCommand(link);
		com.execute();
		commands.add(com);
	}
	this.size2=child.getTargetRelationships().size();
	for(int i=0;i<size2;i++) {
		Relationship link = (Relationship) child.getTargetRelationships().get(0);
		targetConnections.put(link, link.getSource());
		com=new RelationshipDeleteCommand(link);
		com.execute();
		commands.add(com);
	}

	if(parent!=null)	parent.getModelElements().remove(index);
	else if(container!=null) container.getVisualElements().remove(index);

	size1=child.getVisualElements().size();
	for(int i=0;i<size1;i++) {
		com =new ElementDeleteCommand((VisualElement)child.getVisualElements().get(0));	// store a copy of incoming & outgoing connections before proceeding 
		com.execute();
		commands.add(com);
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
	// add the child and reconnect its connections
	if(parent!=null)		
		parent.getModelElements().add(index,child);
	else if(container!=null)
		container.getVisualElements().add(index,child);

	// Inverto la lista dei comandi eseguiti
	for(int i=commands.size();i>0;i--)
		((Command) commands.get(i-1)).undo();
}
}
