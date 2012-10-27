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

package genericUMLDiagramEditor.editPolicies;

import genericUMLDiagramEditor.commands.ElementDeleteCommand;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * This edit policy enables the removal of a ModelElement instance from its container. 
 *
 * @author marguu&zaza
 */
public class ElementComponentEditPolicy extends ComponentEditPolicy {
private Command com;

/**
 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
 */
protected Command createDeleteCommand(GroupRequest deleteRequest) {
	Object child = getHost().getModel();
	
	if(child instanceof VisualElement) {
		com = new ElementDeleteCommand((VisualElement)child); 
		return com;
	}

	return super.createDeleteCommand(deleteRequest);
}
}
