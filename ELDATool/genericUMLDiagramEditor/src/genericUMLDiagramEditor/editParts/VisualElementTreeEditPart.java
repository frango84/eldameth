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

import java.util.ArrayList;
import java.util.List;

import genericUMLDiagramModel.*;

import org.eclipse.emf.common.notify.Notification;

/**
 * EditPart used for VisualElement instances in the outline
 *
 * @author marguu&zaza
 * 
 */
public class VisualElementTreeEditPart extends GenericDiagramAbstractTreeEditPart {
/**
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null Shapes instance
 */
public VisualElementTreeEditPart(VisualElement model) {
	super(model);
}

/**
 * Return the VisualElement for this editPart
 */
protected VisualElement getCastedModel() {
	return (VisualElement) getModel();
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	if (getModel() instanceof VisualElement) {
		VisualElement elemento = getCastedModel();
		List children = new ArrayList();
		children.addAll(elemento.getSourceRelationships());
		children.addAll(elemento.getTargetRelationships());
		return children;
	}
	return super.getModelChildren();
}

/**
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
 */
protected String getText() {
	return getCastedModel().toString();
}

/**
 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification msg) {
	switch (msg.getFeatureID(VisualElement.class)) {
	case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__NAME:
		if (msg.getNotifier() == getModel())
			refreshVisuals();
		else
			// a supertype's name has changed
			refreshChildren();
	}
	switch (msg.getFeatureID(VisualElement.class)) {
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
		case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
			refreshChildren();
	}
}
}
