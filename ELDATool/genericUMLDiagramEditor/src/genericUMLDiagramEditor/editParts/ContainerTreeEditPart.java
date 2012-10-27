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
import org.eclipse.swt.graphics.Image;

/**
 * EditPart used for Container instances in the outline
 * 
 * @author marguu&zaza
 *
 */
public class ContainerTreeEditPart extends GenericDiagramAbstractTreeEditPart {
/**
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null Container instance
 */
public ContainerTreeEditPart(VisualElement model) {
	super(model);
}

/**
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
 */
protected Image getImage() {
	return null;
}

/**
 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
 */
protected List getModelChildren() {
	if (getModel() instanceof Container) {
		Container pack= (Container)getModel();
		List children = new ArrayList();
		children.addAll(pack.getVisualElements());
		children.addAll(pack.getSourceRelationships());
		children.addAll(pack.getTargetRelationships());
		return children;
	}
		
	return super.getModelChildren();
}

/**
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getText()
 */
protected String getText() {
	return getModel().toString();
}

/**
 * @see genericUMLDiagramEditor.editParts.GenericDiagramAbstractTreeEditPart#handlePropertyChanged(org.eclipse.emf.common.notify.Notification)
 */
protected void handlePropertyChanged(Notification msg) {
	switch (msg.getFeatureID(Container.class)) {
	case GenericUMLDiagramModelPackage.CONTAINER__NAME:
			refreshVisuals();
	case GenericUMLDiagramModelPackage.CONTAINER__SOURCE_RELATIONSHIPS:
	case GenericUMLDiagramModelPackage.CONTAINER__TARGET_RELATIONSHIPS:
	case GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS:
		refreshChildren();
	}
}
}
