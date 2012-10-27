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

package eldaEditor.commands;


import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import eldaEditor.editparts.DSCStateEditPart;


/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */

public class PromuoveCommandDSC extends Command {
//	private GenericDiagramEditor editor;
	private DSCState state;
	private Dimension oldDim;
	PopulateCompositeCommandDSC populateCompositeCommand;
	
	public PromuoveCommandDSC(Object template) {
//		this.editor=editor;
		this.state=(DSCState)((DSCStateEditPart)template).getModel();
		this.oldDim= state.getSize();
		
		
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		DSCDiagram parent= (DSCDiagram) EcoreUtil.getRootContainer(state);
		state.setIsSimple(false);
		state.setSize(new Dimension (150,135));
		populateCompositeCommand= new PopulateCompositeCommandDSC(state,parent);
		populateCompositeCommand.execute();
		}
	
	public void undo(){
		populateCompositeCommand.undo();
		state.setIsSimple(true);
		state.setSize(oldDim);
		
	}
	
	public void redo()
	{
		execute();
	}
}
