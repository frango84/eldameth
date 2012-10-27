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

import genericUMLDiagramEditor.editParts.GenericDiagramEditPartFactory;
import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.gef.EditPart;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;

/**
 * Factory that maps model elements to edit parts.
 *@author samuele
 */
public class DSCDiagramEditPartFactory extends GenericDiagramEditPartFactory {

/**
 * Maps an object to an EditPart. 
 * @throws RuntimeException if no match was found (programming error)
 */
protected EditPart getPartForElement(Object modelElement) {
	if (modelElement instanceof DSCDiagram)
		return new DSCDiagramEditPart();

	if (modelElement instanceof DSCState)
		return new DSCStateEditPart();
	if (modelElement instanceof ConnectionLabel)
		  return new ConnLabelEditPartDSC();
	if (modelElement instanceof VisualElement)
		return new VisualElementEditPartDSC();
	if (modelElement instanceof Relationship)
		return new RelationshipEditPartDSC();
	throw new RuntimeException(
			"Can't create part for model element: "
			+ ((modelElement != null) ? modelElement.getClass().getName() : "null"));
}
}
