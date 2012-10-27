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

import java.util.Iterator;

import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.VisualElement;
import genericUMLDiagramModel.Note;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Add an existing VisualElement to a Container
 * 
 * @author marguu&zaza
 *
 */
public class ContainerAddCommand extends Command
{
	protected VisualElement child;
	protected final GenericDiagram parent;
	protected int index;
	protected Container pack;
	protected final Rectangle newBounds;
	protected Rectangle oldBounds;
	protected Container oldContainer;
	protected GenericDiagram oldParent;
	
	// La distanza minima dal bordo sinistro o quello superiore
	protected final int MIN_DIST = 20;
	
	public ContainerAddCommand(VisualElement child,Container pack,GenericDiagram parent,Rectangle newBounds)
	{
		this.child = child;
		this.newBounds = newBounds.getCopy();
		this.pack = pack;
		this.parent = parent;
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldBounds = new Rectangle(child.getLocation(), child.getSize());
	    oldParent=child.getParent();
	    oldContainer=child.getContainer();	
	
	    if(oldParent!=null) {
	    	index = oldParent.getModelElements().indexOf(child);
	    	oldParent.getModelElements().remove(index);	
	    }
	
	    if(oldContainer!=null)
	    	oldContainer.getVisualElements().remove(child);
	    
		// Verifico che l'elemento non venga portato o troppo in alto o troppo a sx
		Point newPos = newBounds.getLocation();
		if(newPos.x<MIN_DIST) newPos.x = MIN_DIST;
		if(newPos.y<MIN_DIST) newPos.y = MIN_DIST;
		child.setLocation(newPos);

		// Verifico se sto creando duplicati nel container destinazione
		checkDuplicate();

		pack.getVisualElements().add(child);
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		if(pack instanceof Container && child instanceof Note )
			return false;
		else
			return true;
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
		pack.getVisualElements().remove(child);
		child.setLocation(oldBounds.getLocation());
		if(oldParent!=null )
			   child.setParent(oldParent);
		else if(oldContainer!=null)
			 child.setContainer(oldContainer);
	}
	
	/**
	 * Check if there are duplicate in the same container
	 */
	protected void checkDuplicate() {
		// Verifico se sto creando duplicati nel container destinazione
		if (child instanceof Classifier){
			Iterator i = pack.getVisualElements().iterator();
			 while (i.hasNext()) {
		        VisualElement m =(VisualElement) i.next();
		        if ((m instanceof Classifier)&&(child.getName().equals(m.getName())))
		        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attention, element: "+child.getName()+" already exists in this container!");
		    }
		}
		if (child instanceof Container){
			Iterator i = pack.getVisualElements().iterator();
			 while (i.hasNext()) {
		        VisualElement m =(VisualElement) i.next();
		        if ((m instanceof Container)&&(child.getName().equals(m.getName())))
		        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attention, container: "+child.getName()+" already exists in this container!");
		    }
		}
	}
}
