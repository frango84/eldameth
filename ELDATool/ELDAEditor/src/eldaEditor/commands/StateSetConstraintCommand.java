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

import genericUMLDiagramEditor.commands.ContainerSetConstraintCommand;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import dscDiagramModel.DSCState;
import eldaEditor.editparts.DSCStateEditPart;

/**
 *	Classe che permette lo spostamento
 * o il ridimensionamento di un elemento interno a un contenitore. 
 * @author samuele
 */
public class StateSetConstraintCommand extends ContainerSetConstraintCommand {
/**
 * Variabile che contiene un riferimento all’edit part del contenitore 
 * nel quale si trova l’elemento da modificare
 */
protected DSCStateEditPart child;

/**
 * Costruttore 
 * @param shape	Istanza dell’elemento che sarà spostato o ridimensionato
 * @param req		Variabile che contiene un riferimento alla richiesta di ridimensionamento o spostamento effettuata dall’utente
 * @param newBounds Margini dell’elemento dopo la sua modifica
 * @throws IllegalArgumentException se uno dei parametri è null
 */
public StateSetConstraintCommand(VisualElement shape, ChangeBoundsRequest req, 
		Rectangle newBounds,DSCStateEditPart child) {
	super(shape,req,newBounds,child);
	
	this.child = child;
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	// Memorizzo la vecchia posizione
	Point oldPos = egoToGlobal(shape);

	// Verifico di non aver rimpicciolito troppo lo stato
	final Dimension MIN_SIZE_DEFAULT = new Dimension(45, 40);
	Dimension dim = newBounds.getSize();
	if(dim.width<MIN_SIZE_DEFAULT.width) dim.width=MIN_SIZE_DEFAULT.width;
	if(dim.height<MIN_SIZE_DEFAULT.height) dim.height=MIN_SIZE_DEFAULT.height;
	newBounds.setSize(dim);
	
	shape.setSize(newBounds.getSize());
	
	// Verifico che il contaniner non vengo portato o troppo in alto o troppo a sx
	Point tempPos = newBounds.getLocation();
	if(tempPos.x<MIN_DIST) tempPos.x = MIN_DIST;
	if(tempPos.y<MIN_DIST) {
		if(shape.getContainer()!=null)  tempPos.y = 2*MIN_DIST;
		else 							tempPos.y = MIN_DIST;
	}
	shape.setLocation(tempPos);
	
	// Memorizzo la nuova posizione
	Point newPos = egoToGlobal(shape);
	// Calcolo la differenza di posizione
	Dimension diff_pos = newPos.getDifference(oldPos);

	if(child!=null)
		child.refreshAutotransition(oldBounds.getCopy(), newBounds.getCopy(), oldPos, diff_pos, null);
	
	// Muovo i bendpoint interni
	moveBendPoints();
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	// Memorizzo la vecchia posizione
	Point oldPos = egoToGlobal(shape);
	
	// Annullo la modifica del bound
	super.undo();
	
	// Memorizzo la nuova posizione
	Point newPos = egoToGlobal(shape);
	// Calcolo la differenza di posizione
	Dimension diff_pos = newPos.getDifference(oldPos);

	if(child!=null)
		child.refreshAutotransition(newBounds.getCopy(), oldBounds.getCopy(), oldPos, diff_pos,null);
}

/**
 * Return the list of the inner relationship of the modified element, execpt the autotransition
 */
protected List findInnerRelationship() {
	// Creo la lista di tutte le relazioni contenute nel container copiato
	List oldConnection = super.findInnerRelationship();
	List newConnection = super.findInnerRelationship();
	
	if(oldConnection==null) return null;
	
	// Elimino dalla lista trovata le autotransizioni
	for(int k=0; k<oldConnection.size(); k++) {
		Relationship conn = (Relationship) oldConnection.get(k);
		
		List source_list = conn.getSource().getSourceRelationships();
		List target_list = conn.getSource().getTargetRelationships();
		for(int i=0; i<source_list.size(); i++) {
			if(target_list.contains(source_list.get(i))) {
				// Elimino la autotransizione trovata
				newConnection.remove(source_list.get(i));
			}
		}
	}
	
	return newConnection;
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
}
