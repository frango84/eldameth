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

/**
 * @author samuele
 *
 */

import genericUMLDiagramEditor.editParts.ContainerTreeEditPart;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.VisualElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import eldaEditor.icons.OutlineIcons;

public class StateTreeEditPart extends ContainerTreeEditPart {
/**
 * Create a new instance of this edit part using the given model element.
 * @param model a non-null Shapes instance
 */
public StateTreeEditPart(VisualElement model) {
	super(model);
}

/* (non-Javadoc)
 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
 */
protected Image getImage() {
		return OutlineIcons.IMAGE_STATE;
}

protected List getModelChildren() {
	Container pack= (Container)getModel();
	List children = new ArrayList();
	children.addAll(pack.getVisualElements());
	children.addAll(pack.getSourceRelationships());
	
	for(int i=0; i<pack.getTargetRelationships().size(); i++) {
		// Le connessioni che sono già presenti come Source sono delle
		// autotransizioni quindi non devo inserirle due volte; tutte
		// le altre connessioni vanno inserite
		if(!pack.getSourceRelationships().contains(pack.getTargetRelationships().get(i)))
			children.add(pack.getTargetRelationships().get(i));
	}

	return children;
}
}
