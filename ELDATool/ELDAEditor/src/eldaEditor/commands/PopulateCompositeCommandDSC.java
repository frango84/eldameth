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



import genericUMLDiagramEditor.commands.CreateFileName;
import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.GenericUMLDiagramModelFactory;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DscDiagramModelFactory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;


/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */
public class PopulateCompositeCommandDSC extends Command {
//	private GenericDiagramEditor editor;
	private DSCState compositeState;
//	private DSCState container;
	private DSCDiagram parent;
	StartPoint startPoint;
	DSCState simpleState;
	Transition startTransition;
	
	

	/**
	 * Costruttore chiamato in caso di creazione a top level
	 * @param template
	 * @param parent
	 */
	public PopulateCompositeCommandDSC(DSCState template, DSCDiagram parent) {
//		this.editor=editor;
		this.compositeState=template;
		this.parent=parent;
		
	}
	
//	public PopulateCompositeCommandDSC(DSCState template, DSCState container,DSCDiagram parent) {
////		this.editor=editor;
//		this.compositeState=template;
//		this.container=container;
//		this.parent=parent;
//	}
	
	
	
	
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		
		
//		 Cerco il nome del file
		String strFile = CreateFileName.getName();
		
		DscDiagramModelFactory factory= DscDiagramModelFactory.eINSTANCE;
		
		startPoint =factory.createStartPoint();
		startPoint.setLocation(new Point(10,40));
		startPoint.setSize(new Dimension (20,20));
//		compositeState.setStartPoint(startPoint);
		startPoint.setContainer(compositeState);
		

		//	 Setto l'ID 
		if (startPoint.getID()==null) {
			// Setto l'identificativo
			startPoint.setID(strFile + (parent.getMaxID()+1));
		
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}
		
		simpleState=factory.createDSCState();
		simpleState.setName("DefaultName");
		simpleState.setIsSimple(true);
		simpleState.setLocation(new Point(50,40));
		simpleState.setSize(new Dimension (85,55));
		simpleState.setContainer(compositeState);
		
//		
		//	 Setto l'ID 
		if (simpleState.getID()==null) {
			// Setto l'identificativo
			simpleState.setID(strFile + (parent.getMaxID()+1));
		
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}
//		
		startTransition=factory.createTransition();
		
		GenericUMLDiagramModelFactory factoryGeneric = GenericUMLDiagramModelFactory.eINSTANCE;
		ConnectionLabel label = factoryGeneric.createConnectionLabel();
		label.setRelationship(startTransition);
		
		// Setto l'ID della ConnectionLabel
		if (label.getID()==null) {
			// Setto l'identificativo
			label.setID(strFile + (parent.getMaxID()+1));
		
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}
		
		startTransition.setSource(startPoint);
		startTransition.setTarget(simpleState);
		
		startTransition.setParent(parent);
		
		
		//setto l'ID
		if (startTransition.getID()==null) {
			// Setto l'identificativo
			startTransition.setID(strFile + (parent.getMaxID()+1));
			
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}

		
		
		
	}
		
	public void undo()
	{
		startPoint.setContainer(null);
		simpleState.setContainer(null);
		startTransition.setParent(null);
		
	}
	
	public void redo()
	{
		execute();
	}
		
}
