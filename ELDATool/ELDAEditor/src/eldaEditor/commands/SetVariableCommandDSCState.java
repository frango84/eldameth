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

import dscDiagramModel.DSCState;
import dscDiagramModel.myDataTypes.TypeVariable;


/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */
public class SetVariableCommandDSCState extends Command {
	private Table table;
	private DSCState state;
	private List listVariable;
	private List newListVariable=new ArrayList();
	
	public SetVariableCommandDSCState(DSCState template, Table table) {
		this.table=table;
		this.state=template;
		this.listVariable=state.getVariables();
		
		
		
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		
        // Rimuovo tutte le precedenti transizioni associate a questo stato
        state.getVariables().clear();
		
        // Creo una nuova variabile interna per ogni item della tabella
        for(int i=0; i<table.getItemCount(); i++) {
        	
        	TypeVariable variable=new TypeVariable();
        	variable.setType(table.getItem(i).getText(0));
        	variable.setName(table.getItem(i).getText(1));
        	variable.setValue(table.getItem(i).getText(2));
        	state.getVariables().add(variable);
        }
        this.newListVariable.addAll(state.getVariables());
	}
	
	
	public void undo(){
		
		state.getVariables().clear();
		state.getVariables().addAll(listVariable);
	}
	
	public void redo(){
		state.getVariables().clear();
		state.getVariables().addAll(newListVariable);
		
	}
}
