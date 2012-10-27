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

package eldaEditor.editparts;

import genericUMLDiagramEditor.editParts.GenericDiagramTreeEditPart;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;


/**
 * @author samuele
 *
 */

public class DSCDiagramTreeEditPartFactory implements EditPartFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof DSCState)
			return new StateTreeEditPart((DSCState) model);
		if (model instanceof VisualElement)
			return new VisualElementTreeEditPartDSC((VisualElement) model);
		if (model instanceof DSCDiagram)
			return new GenericDiagramTreeEditPart((GenericDiagram) model);
		if (model instanceof Relationship)
			return new RelationshipTreeEditPartDSC((Relationship) model);
		return null; // will not show an entry for the corresponding model instance
	}
}
