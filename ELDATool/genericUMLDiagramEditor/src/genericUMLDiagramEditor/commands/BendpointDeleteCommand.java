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

package genericUMLDiagramEditor.commands;

import org.eclipse.gef.commands.*;
import genericUMLDiagramModel.*;
import org.eclipse.draw2d.*;

/**
 * Delete the passed bendpoint for the passed relatioship
 * 
 * @author marguu&zaza
 *
 */
public class BendpointDeleteCommand extends Command
{
	private int index;
	private Relationship model;
	private Bendpoint bendpoint;
	
	public BendpointDeleteCommand(Relationship model, int index)
	{
		this.model = model;
		this.index = index;	
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute()
	{
		bendpoint =(Bendpoint) model.getBendpoints().remove(index);
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo()
	{
		//model.removeBendpoint(index);
		model.getBendpoints().remove(index);
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo()
	{
		model.getBendpoints().add(index,bendpoint);
	}
}
