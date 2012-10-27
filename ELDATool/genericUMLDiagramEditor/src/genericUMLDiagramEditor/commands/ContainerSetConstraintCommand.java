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
import java.util.List;
import java.util.Map;

import genericUMLDiagramEditor.editParts.ContainerEditPart;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;

import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;

import org.eclipse.gef.requests.ChangeBoundsRequest;


/**
 * A command to resize and/or move a VisualElement
 *
 * @author maguu&zaza
 * 
 */
public class ContainerSetConstraintCommand extends Command {
/** Stores the new size and location. */
protected final Rectangle newBounds;
/** Stores the old size and location. */
protected Rectangle oldBounds;
/** A request to move/resize an edit part. */
protected final ChangeBoundsRequest request;
/** Shape to manipulate. */
protected final VisualElement shape;
protected ContainerEditPart child;
protected Dimension gap;

//La distanza minima dal bordo sinistro o quello superiore
protected final int MIN_DIST = 20;

public ContainerSetConstraintCommand(VisualElement shape, ChangeBoundsRequest req, 
		Rectangle newBounds,ContainerEditPart child) {
	if (shape == null || req == null || newBounds == null) {
		throw new IllegalArgumentException();
	}
	this.shape = shape;
	this.request = req;
	this.newBounds = newBounds.getCopy();
	this.child=child;
	setLabel("move / resize");
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	Object type = request.getType();
	
	// make sure the Request is of a type we support:
	return (RequestConstants.REQ_MOVE.equals(type)
			|| RequestConstants.REQ_MOVE_CHILDREN.equals(type) 
			|| RequestConstants.REQ_RESIZE.equals(type)
			|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type)
			|| RequestConstants.REQ_ALIGN_CHILDREN.equals(type));
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	oldBounds = new Rectangle(shape.getLocation(), shape.getSize());
	
	// Verifico che l'elemento non vengo portato o troppo in alto o troppo a sx
	Point newPos = newBounds.getLocation();
	if(newPos.x<MIN_DIST) newPos.x = MIN_DIST;
	if(newPos.y<MIN_DIST) newPos.y = MIN_DIST;
	newBounds.setLocation(newPos);
	
	// Memorizzo l'entità dello spostamente
	gap = newPos.getDifference(shape.getLocation());
	
	redo();
}

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	shape.setLocation(newBounds.getLocation());
	shape.setSize(newBounds.getSize());
	
	// Muovo i bendpoint interni
	moveBendPoints();
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	shape.setSize(oldBounds.getSize());
	shape.setLocation(oldBounds.getLocation());
	
	// Inverto l'entità dello spostamento
	gap.negate();
	
	// Muovo i bendpoint interni
	moveBendPoints();
	
	// Ristabilisco l'entità dello spostamento
	gap.negate();
}

/**
 * Move the bendpoints of the inner relationships
 */
protected void moveBendPoints() {
	if(shape instanceof Container && shape!=null) {
		List connection = findInnerRelationship();
		
		if(connection==null) return;
		
		// Sposto anche i bendpoints di tutte le relazioni contenute
		for(int j=0; j<connection.size(); j++) {
			Relationship conn = (Relationship) connection.get(j);
			for(int i=0; i<conn.getBendpoints().size(); i++) {
				AbsoluteBendpoint bend = (AbsoluteBendpoint) conn.getBendpoints().get(i);
				bend.translate(gap);
			}
		}
	}
}

/**
 * Return the list of the inner relationship of the modified element
 */
protected List findInnerRelationship() {
	// Recupero l'array di tutte le relazioni contenute nel container copiato
	Map list = CrossReferencer.find(((Container) shape).getVisualElements());
	Object[] array = list.values().toArray();
	
	// Verifico che esistano relazioni da copiare, altrimenti esco
	if(array.length<=0) return null;
	
	// Creo la lista di tutte le relazioni contenute nel container copiato
	List conn = new ArrayList();
	for(int i=0; i<array.length; i++) {
		List list1 = (List) array[i];
		for(int j=0; j<list1.size(); j++) {
			if (list1.get(j) instanceof BasicEObjectImpl)
			{	
			List list2 = (List) list1.get(j);
			for(int k=0; k<list2.size(); k++) {
				if(!conn.contains(list2.get(k))) conn.add(list2.get(k));
			}
			}
		}
	}
	
	return conn;
}
}
