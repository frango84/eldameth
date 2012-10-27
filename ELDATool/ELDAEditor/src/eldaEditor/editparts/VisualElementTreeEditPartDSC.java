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

import genericUMLDiagramEditor.editParts.VisualElementTreeEditPart;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.swt.graphics.Image;

import dscDiagramModel.StartPoint;
import eldaEditor.icons.OutlineIcons;

/**
 * @author samuele
 *
 */
public class VisualElementTreeEditPartDSC extends VisualElementTreeEditPart {

/**
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null Shapes instance
 */
public VisualElementTreeEditPartDSC(VisualElement model) {
	super(model);
}

/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
 */
protected Image getImage() {
	
	if(getCastedModel() instanceof Note )
		return OutlineIcons.IMAGE_NOTE;
	else if(getCastedModel() instanceof TextLabel)
		return OutlineIcons.IMAGE_TEXTLABEL;
	else if(getCastedModel() instanceof HorizontalLineSeparator)
		return OutlineIcons.IMAGE_HORIZONTAL_SEPARATOR;
	else if(getCastedModel() instanceof VerticalLineSeparator)
		return OutlineIcons.IMAGE_VERTICAL_SEPARATOR;
	else if(getCastedModel() instanceof StartPoint)
		return OutlineIcons.IMAGE_START_POINT;

	else return null;
}
}
