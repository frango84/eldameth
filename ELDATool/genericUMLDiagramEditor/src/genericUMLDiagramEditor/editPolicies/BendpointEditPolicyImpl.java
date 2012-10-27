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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.*;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.*;

import genericUMLDiagramEditor.commands.*;
import genericUMLDiagramModel.*;

/**
 * Return the commands to manage the bendpoints of a connection
 * 
 * @author marguu&zaza
 *
 */
public class BendpointEditPolicyImpl extends BendpointEditPolicy
{

	/**
	 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getCreateBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
	 */
	protected Command getCreateBendpointCommand(BendpointRequest request)
	{
		int index = request.getIndex();
		Point loc = request.getLocation();
		IFigure fig=getHostFigure();
		Relationship model = (Relationship)getHost().getModel();
		return new BendpointCreateCommand(model, index, loc, fig);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getDeleteBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
	 */
	protected Command getDeleteBendpointCommand(BendpointRequest request)
	{
		int index = request.getIndex();
		Relationship model = (Relationship)getHost().getModel();
		return new BendpointDeleteCommand(model, index);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.BendpointEditPolicy#getMoveBendpointCommand(org.eclipse.gef.requests.BendpointRequest)
	 */
	protected Command getMoveBendpointCommand(BendpointRequest request)
	{
		int index = request.getIndex();
		Point loc = request.getLocation();
		IFigure fig=getHostFigure();
		Relationship model = (Relationship)getHost().getModel();
		return new BendpointMoveCommand(model, index, loc, fig);
	}
}
