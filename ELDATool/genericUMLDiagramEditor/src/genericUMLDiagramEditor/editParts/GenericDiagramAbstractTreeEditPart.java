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

import genericUMLDiagramEditor.editPolicies.ElementComponentEditPolicy;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractTreeEditPart;

/**
 * Abstract EditPart for an element of a GenericDiagram instance
 * in the outline.
 * 
 * author marguu&zaza
 */
public abstract class GenericDiagramAbstractTreeEditPart extends AbstractTreeEditPart {

protected Adapter modelListener = new AdapterImpl() {
	/**
	 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification msg) {
		handlePropertyChanged(msg);
	}
};

/** 
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null ShapesDiagram instance
 */
public GenericDiagramAbstractTreeEditPart(Object model) {
	super(model);
}

/**
 * Upon activation, attach to the model element as a property change listener.
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
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
 */
protected void createEditPolicies() {
	// allow removal of the associated model element
	installEditPolicy(EditPolicy.COMPONENT_ROLE, new ElementComponentEditPolicy());
}

/**
 * Manage the model changes
 * 
 * @param notification	the notification of a change
 */
protected abstract void handlePropertyChanged(Notification notification);
}
