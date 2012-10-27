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

import genericUMLDiagramModel.*;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

/**
 * Factory that maps model elements to edit parts fot the outline.
 *
 * @author marguu&zaza
 */
public class GenericDiagramTreeEditPartFactory implements EditPartFactory {

	/**
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Container) {
			return new ContainerTreeEditPart((Container) model);
		}
		if (model instanceof VisualElement) {
			return new VisualElementTreeEditPart((VisualElement) model);
		}
		if (model instanceof GenericDiagram) {
			return new GenericDiagramTreeEditPart((GenericDiagram) model);
		}
		if (model instanceof Relationship) {
			return new RelationshipTreeEditPart((Relationship) model);
		}
		return null; // will not show an entry for the corresponding model instance
	}
}
