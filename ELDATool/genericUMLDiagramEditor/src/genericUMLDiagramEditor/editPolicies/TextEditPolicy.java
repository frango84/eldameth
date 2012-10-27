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

import genericUMLDiagramModel.VisualElement;

import genericUMLDiagramEditor.commands.ChangeNameCommand;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

/**
 * Return the commands to manage the DirectEdit
 * 
 * @author marguu&zaza
 *
 */
public class TextEditPolicy extends DirectEditPolicy {
	
/**
 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
 */
protected Command getDirectEditCommand(DirectEditRequest edit) {
	String labelText = (String)edit.getCellEditor().getValue();
	ChangeNameCommand command = new ChangeNameCommand((VisualElement)getHost().getModel(),labelText);
	return command;
  }

/**
 * @see org.eclipse.gef.editpolicies.DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
 */
protected void showCurrentEditValue(DirectEditRequest request) {
  }
}
