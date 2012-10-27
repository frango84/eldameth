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

/**
 * @author samuele
 *
 */

import genericUMLDiagramEditor.commands.GenericDiagramAddCommand;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import eldaEditor.editparts.DSCStateEditPart;


public class DSCDiagramAddCommand extends GenericDiagramAddCommand {
	// Variabile che contiene un riferimento all’edit part
	private AbstractGraphicalEditPart part;
	
	public DSCDiagramAddCommand(VisualElement child,Container pack,DSCDiagram parent,Rectangle newBounds, AbstractGraphicalEditPart part) {
		super(child,pack,parent,newBounds);
		
		this.part = part;
	}
	
	public void execute(){
		// Memorizzo la vecchia posizione
		Point oldPos = egoToGlobal(child);
		
		// Effettuo lo spostamento dell'oggetto
		super.execute();

		// Se devo, aggiorno le autotransizioni
		if(part instanceof DSCStateEditPart) {
			// Memorizzo la nuova posizione
			Point newPos = egoToGlobal(child);
			// Calcolo la differenza di posizione
			Dimension diff_pos = newPos.getDifference(oldPos);

			((DSCStateEditPart)part).refreshAutotransition(oldBounds.getCopy(), createOuterBounds(), oldPos, diff_pos,null);
		}
	}
	
	public void undo() {
		// Memorizzo la vecchia posizione
		Point oldPos = egoToGlobal(child);
		
		// Annullo lo spostamento dell'oggetto
		super.undo();

		// Se devo, aggiorno le autotransizioni
		if(part instanceof DSCStateEditPart) {
			// Memorizzo la nuova posizione
			Point newPos = egoToGlobal(child);
			// Calcolo la differenza di posizione
			Dimension diff_pos = newPos.getDifference(oldPos);
			
			((DSCStateEditPart) part).refreshAutotransition(newBounds.getCopy(), createInnerBounds(), oldPos, diff_pos,null);
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
		// agganciata l'autotransizione in esame
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
		if (child instanceof DSCState){
			Iterator i = parent.getModelElements().iterator();
			 while (i.hasNext()) {
		        ModelElement m =(ModelElement) i.next();
		        if ((m instanceof DSCState)&&(child.getName().equals(m.getName())))
		        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attention, state: "+child.getName()+" already exists in this diagram!");
		    }
		}		
	}
}
