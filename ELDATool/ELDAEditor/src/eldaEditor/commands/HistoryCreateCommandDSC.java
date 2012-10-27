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



//import org.eclipse.emf.ecore.util.EcoreUtil;
import genericUMLDiagramEditor.commands.CreateFileName;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.DscDiagramModelFactory;
import dscDiagramModel.ShallowHistory;



/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */
public class HistoryCreateCommandDSC extends Command {
//	private GenericDiagramEditor editor;
	private DSCState compositeState;
	private String type;
	private DSCDiagram parent;
	
	public HistoryCreateCommandDSC(DSCState state,String type, DSCDiagram parent) {
//		this.editor=editor;
		this.compositeState=state;
		this.type=type;
		this.parent=parent;
		
		
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		
		int altezza=compositeState.getSize().height;
		
//		 Cerco il nome del file
		String strFile = CreateFileName.getName();
		
		DscDiagramModelFactory factory= DscDiagramModelFactory.eINSTANCE;
		if (type.equals("Deep"))
		{
		DeepHistory history =factory.createDeepHistory();
		history.setLocation(new Point(10,altezza - 30));
		(history).setCompositeState(compositeState);
		history.setSize(new Dimension (20,20));
		history.setContainer(compositeState);
		//	 Setto l'ID 
		if (history.getID()==null) {
			// Setto l'identificativo
			history.setID(strFile + (parent.getMaxID()+1));
		
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}
		}
		else
		{
			ShallowHistory 	history =factory.createShallowHistory();
			history.setLocation(new Point(10,altezza - 60));
			(history).setCompositeState(compositeState);
			history.setSize(new Dimension (20,20));
			history.setContainer(compositeState);
			//	 Setto l'ID 
			if (history.getID()==null) {
				// Setto l'identificativo
				history.setID(strFile + (parent.getMaxID()+1));
			
				// Incremento il maxID del padre
				parent.setMaxID(parent.getMaxID()+1);
			}
		}

		
	}
	public void undo(){
		if (type.equals("Deep"))
		{	
			compositeState.getDeepHistory().setContainer(null);
			compositeState.setDeepHistory(null);
		
		}
		else
		{
			compositeState.getShallowHistory().setContainer(null);
			compositeState.setShallowHistory(null);
			
		}
	}
	
	public void redo()
	{
		execute();
	}
		
}
