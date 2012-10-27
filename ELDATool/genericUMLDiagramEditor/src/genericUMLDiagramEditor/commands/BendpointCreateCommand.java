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

import org.eclipse.draw2d.geometry.*;
import org.eclipse.draw2d.*;

/**
 * Create a new bendpoint in the passed point for the passed relatioship
 * 
 * @author marguu&zaza
 *
 */
public class BendpointCreateCommand extends Command
{
	private Point location;
	private int index;
	private Relationship model;
	private AbsoluteBendpoint bendpoint;
	private IFigure fig;
	
	public BendpointCreateCommand(Relationship model, int index, Point location,IFigure fig)
	{
		this.fig = fig;
		this.model = model;
		this.index = index;
		this.location = location;	
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute()
	{
		fig.translateToRelative(location);
		bendpoint = new AbsoluteBendpoint(location);
		bendpoint.setLocation(location);
		model.getBendpoints().add(index,bendpoint);
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo()
	{
		model.getBendpoints().add(index,bendpoint);
	}
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo()
	{
		model.getBendpoints().remove(bendpoint);
	}
}
