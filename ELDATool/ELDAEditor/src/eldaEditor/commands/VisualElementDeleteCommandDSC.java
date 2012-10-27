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
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.gef.commands.Command;

/**
 * A command to remove a VisualElement from its parent
 * 
 * @author samuele
 * 
 */
public class VisualElementDeleteCommandDSC extends Command
{
	protected VisualElement child;
	private int size1,size2;
	/**
	 * 
	 * @uml.property name="list"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private List commands=new ArrayList();
	private GenericDiagram parent;
	private Container container;
	private Map sourceConnections;
	private Map targetConnections;
	private Command com; 
	
	private int oldIndex;

	public VisualElementDeleteCommandDSC(VisualElement child) {
		this.child = child;
		if(child.getParent()!=null)	this.parent = child.getParent();
		else if(child.getContainer()!=null)this.container=child.getContainer();
	}

	public boolean canExecute()
	{
//		if (child instanceof DeepHistory ||  child instanceof ShallowHistory || child instanceof StartPoint)
//		    return false;
		return true;
	}
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		if(parent!=null)
			oldIndex = parent.getModelElements().indexOf(child);
		else	if(container!=null)
			oldIndex = container.getVisualElements().indexOf(child);
		
		if (oldIndex == -1) return;

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
		
		if(parent!=null)			parent.getModelElements().remove(child);
		else if(container!=null)	container.getVisualElements().remove(child);
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
		if(parent!=null) {
			if(!parent.getModelElements().contains(child))
				parent.getModelElements().add(oldIndex,child);
		}
		else if(container!=null) {
			if(!container.getVisualElements().contains(child))
				container.getVisualElements().add(oldIndex,child);
		}
	    Iterator i = commands.iterator();
	    while (i.hasNext()) {
	        Command o =(Command) i.next();
	        o.undo();
	   };
	}
}
