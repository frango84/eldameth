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

import genericUMLDiagramEditor.commands.ContainerAddCommand;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCState;
import eldaEditor.editparts.DSCStateEditPart;

/**
 * Classe che aggiunge ad un contenitore un elemento 
 * già presente nel modello e nel diagramma.
 * @author samuele
 *
 */
public class StateAddCommand extends ContainerAddCommand {
	// Variabile che contiene un riferimento all’edit part 
	private AbstractGraphicalEditPart part;
	
	/**
	 *	Costruttore
	 * @param child Elemento da inserire nel contenitore
	 * @param pack	Contenitore a cui si aggiunge l'elemento
	 * @param newBounds	Dimensioni dell'elemento da inserire nel contenitore
	 */
	public StateAddCommand(VisualElement child,Container pack,Rectangle newBounds,AbstractGraphicalEditPart part) {
		super(child,pack,child.getParent(),newBounds);
		this.setLabel("add element to state");
		
		this.part = part;
	}
	
	public boolean canExecute()
	{
		
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {	
		if(canExecute()) {
			// Memorizzo la vecchia posizione
			Point oldPos = egoToGlobal(child);
			
			// Effettuo lo spostamento dell'oggetto
			super.execute();
			
			// Verifico che il contaniner non venga portato o troppo in alto o troppo a sx
			Point tempPos = child.getLocation();
			if(tempPos.x<MIN_DIST) tempPos.x = MIN_DIST;
			if(tempPos.y<2*MIN_DIST) tempPos.y = 2*MIN_DIST;
			child.setLocation(tempPos);

			// Se devo, aggiorno le autotransizioni
			if(part instanceof DSCStateEditPart) {
				// Memorizzo le nuove posizione e dimensione
				Point newPos = egoToGlobal(child);
				// Calcolo la differenza di posizione
				Dimension diff_pos = newPos.getDifference(oldPos);
				
				((DSCStateEditPart)part).refreshAutotransition(oldBounds.getCopy(), createInnerBounds(), oldPos, diff_pos,null);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		// Memorizzo la vecchia posizione
		Point oldPos = egoToGlobal(child);
		
		// Effettuo l'undo dello spostamento
		super.undo();
		
		// Se devo, aggiorno le autotransizioni
		if(part instanceof DSCStateEditPart) {
			// Memorizzo la nuova posizione
			Point newPos = egoToGlobal(child);
			// Calcolo la differenza di posizione
			Dimension diff_pos = newPos.getDifference(oldPos);
			
			((DSCStateEditPart)part).refreshAutotransition(newBounds.getCopy(), createOuterBounds(), oldPos, diff_pos,null);
		}
	}
	
	protected Point egoToGlobal(VisualElement item) {
		// Trasformo in globali le coordinate dell'elemento passato
		int x = item.getLocation().x;
		int y = item.getLocation().y;
		
		VisualElement container = item.getContainer(); 
		while(container!=null) {
			if(container instanceof DSCState) {
				x += container.getLocation().x;
				y += container.getLocation().y;
			}
			container = container.getContainer();
		}
		return new Point(x,y);
	}
	
	protected Rectangle createInnerBounds() {
		// Trasformo in globali le coordinate dello stato a cui è
		// agganciata l'autotransizione in esame
		int x = child.getLocation().x;
		int y = child.getLocation().y;
		
		VisualElement container = pack; 
		while(container!=null) {
			if(container instanceof DSCState) {
				x += container.getLocation().x;
				y += container.getLocation().y;
			}
			container = container.getContainer();
		}
		return new Rectangle(new Point(x,y),newBounds.getSize());
	}
	
	protected Rectangle createOuterBounds() {
		// Trasformo in globali le coordinate dello stato a cui è
		// agganciata le autotransizioni in esame
		int x = child.getLocation().x;
		int y = child.getLocation().y;
		
		VisualElement container = pack; 
		while(container!=null) {
			if(container instanceof DSCState) {
				x -= container.getLocation().x;
				y -= container.getLocation().y;
			}
			container = container.getContainer();
		}
		return new Rectangle(new Point(x,y),newBounds.getSize());
	}
	
	protected void checkDuplicate() {
		// Verifico se sto creando duplicati nel container destinazione
		if (child instanceof DSCState) {
			Iterator i = pack.getVisualElements().iterator();
			while (i.hasNext())  {
		     	VisualElement m =(VisualElement) i.next();
		     	if ((m instanceof DSCState)&&(child.getName().equals(m.getName())))
		     	    MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attention, state: "+child.getName()+" already exists in this diagram!");
		 	}
		}
	}
}
