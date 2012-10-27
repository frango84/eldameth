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

import genericUMLDiagramEditor.editParts.RelationshipTreeEditPart;
import genericUMLDiagramModel.Relationship;

import org.eclipse.swt.graphics.Image;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.Transition;
import eldaEditor.icons.OutlineIcons;

/**
 * EditPart associato all'elemento relazione presente nell'outline
 * @author samuele
 *
 */
public class RelationshipTreeEditPartDSC extends RelationshipTreeEditPart {

	/**
	 * Costruttore
	 * @param model elemento associato all'editPart
	 */
	public RelationshipTreeEditPartDSC(Relationship model) {
		super(model);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	public Image getImage() {
	if(getModel() instanceof Transition)
		return OutlineIcons.IMAGE_TRANSITION;
	if(getModel() instanceof AnchorNoteToItem)
		return OutlineIcons.IMAGE_INDICATION;
	else
		return null;
	}
}
