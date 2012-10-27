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

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;
import genericUMLDiagramModel.ConnectionLabel;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts.
 *
 * @author marguu&zaza
 */
public class GenericDiagramEditPartFactory implements EditPartFactory {

/**
 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
 */
public EditPart createEditPart(EditPart context, Object modelElement) {
	// get EditPart for model element
	EditPart part = getPartForElement(modelElement);
	// store model element in EditPart
	part.setModel(modelElement);
	return part;
}

/**
 * Maps an object to an EditPart. 
 * @throws RuntimeException if no match was found (programming error)
 */
protected EditPart getPartForElement(Object modelElement) {
	if (modelElement instanceof GenericDiagram)
		return new GenericDiagramEditPart();
	if (modelElement instanceof Container)
		return new ContainerEditPart();
	if (modelElement instanceof ConnectionLabel)
		return new ConnLabelEditPart();
	if (modelElement instanceof VisualElement)
		return new VisualElementEditPart();
	if (modelElement instanceof Relationship)
		return new RelationshipEditPart();
	throw new RuntimeException(
			"Can't create part for model element: "
			+ ((modelElement != null) ? modelElement.getClass().getName() : "null"));
}

}
