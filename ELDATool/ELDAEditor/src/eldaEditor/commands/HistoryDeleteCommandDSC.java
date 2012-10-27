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

import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import genericUMLDiagramModel.VisualElement;

/**
 * A command to remove a VisualElement from its parent
 * 
 * @author samuele
 * 
 */
public class HistoryDeleteCommandDSC extends VisualElementDeleteCommandDSC
{
	private DSCState state;
	
	public HistoryDeleteCommandDSC(VisualElement child) {
		super((VisualElement)child);
		this.state=(DSCState)child.getContainer();
			
	}


	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		super.execute();
		if (child instanceof ShallowHistory)
		{
			((ShallowHistory)child).getCompositeState().setShallowHistory(null);
		}
		else
		{
			((DeepHistory)child).getCompositeState().setDeepHistory(null);
		}
			
	}

	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		execute();
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		super.undo();
		if (child instanceof ShallowHistory)
		{
			state.setShallowHistory((ShallowHistory)child);
		}
		else
		{
			state.setDeepHistory( (DeepHistory)child );
		}
			
	}
		
	}
