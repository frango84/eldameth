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



import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Table;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;


/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */
public class SetVariableCommandDSCDiagram extends Command {
	private Table table;
	private DSCDiagram diagram;
	private List listVariable;
	private List newListVariable=new ArrayList();
	
	public SetVariableCommandDSCDiagram(DSCDiagram template, Table table) {
		this.table=table;
		this.diagram=template;
		this.listVariable=diagram.getDiagramVariables();
		
		
		
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		
        // Rimuovo tutte le precedenti variabili  associate a questo stato
		diagram.getDiagramVariables().clear();
		
        // Creo una nuova variabile interna per ogni item della tabella
        for(int i=0; i<table.getItemCount(); i++) {
        	
        	TypeDiagramVariable variable=new TypeDiagramVariable();
        	variable.setType(table.getItem(i).getText(0));
        	variable.setName(table.getItem(i).getText(1));
        	variable.setValue(table.getItem(i).getText(2));
        	if (table.getItem(i).getText(3).equals("T"))
        		variable.setBePresentIntoConstructor(true);
        	else
        		variable.setBePresentIntoConstructor(false);
        	
        	diagram.getDiagramVariables().add(variable);
        }
        this.newListVariable.addAll(diagram.getDiagramVariables());
	}
	
	
	public void undo(){
		
		diagram.getDiagramVariables().clear();
		diagram.getDiagramVariables().addAll(listVariable);
	}
	
	public void redo(){
		diagram.getDiagramVariables().clear();
		diagram.getDiagramVariables().addAll(newListVariable);
		
	}
}
