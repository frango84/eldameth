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

package genericUMLDiagramEditor.editParts;

import genericUMLDiagramModel.ModelElement;

import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * Abstract EditPart for an element of a GenericDiagram instance.
 * 
 * author marguu&zaza
 */
public abstract class GenericDiagramAbstractEditPart extends AbstractGraphicalEditPart implements NodeEditPart {

protected Adapter modelListener = new AdapterImpl() {
	/**
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification msg) {
		handlePropertyChanged(msg);
	}
};
	
/**
 * Upon activation, attach to the model element as a property change listener.
 * 
 */
public void activate() {
	if (!isActive()) {
		super.activate();
		((Notifier) getModel()).eAdapters().add(modelListener);
	}
}

/**
 * Upon deactivation, detach from the model element as a property change listener.
 */
public void deactivate() {
	if (isActive()) {
		super.deactivate();
		((Notifier) getModel()).eAdapters().remove(modelListener);
	}
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
 */
protected IFigure createFigure() {
	IFigure f =  createFigureForModel();
	return f;
}

/**
 * Return a new figure for the model
 */
protected IFigure createFigureForModel() {
	return null;
}

/**
 * Return the ModelElement associated to the editPart
 */
private ModelElement getCastedModel() {
	return (ModelElement) getModel();
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections()
 */
protected List getModelSourceConnections() {
	if(getModel() instanceof ModelElement) 
		return getCastedModel().getSourceRelationships();
	
	return super.getModelSourceConnections();
}

/**
 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections()
 */
protected List getModelTargetConnections() {
	if(getModel() instanceof ModelElement)
		return getCastedModel().getTargetRelationships();

	return super.getModelTargetConnections();
}

/**
 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.RelationshipEditPart)
 */
public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
	return new ChopboxAnchor(getFigure());
}

/*
 * (non-Javadoc)
 * @see org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef.Request)
 */
public ConnectionAnchor getSourceConnectionAnchor(Request request) {
	return new ChopboxAnchor(getFigure());
}

/**
 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.RelationshipEditPart)
 */
public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
	return new ChopboxAnchor(getFigure());
}

/**
 * @see org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef.Request)
 */
public ConnectionAnchor getTargetConnectionAnchor(Request request) {
	return new ChopboxAnchor(getFigure());
}

/**
 * Create the CellEditor for the DirectEdit
 * 
 * @param bounds	the bounds for the CellEditor
 * @param name		the initial text for the CellEditor
 */
protected void performDirectEdit(Rectangle bounds, String name){
	DirectEditManager manager = new TextEditManager((GraphicalEditPart) this, 
			  TextCellEditor.class, 
			  new TextCellEditorLocator(name,bounds));
			    ((TextEditManager) manager).setInitame(name);
	manager.show();
}

/**
 * Manage the model changes
 * 
 * @param notification	the notification of a change
 */
protected abstract void handlePropertyChanged(Notification notification);
}
