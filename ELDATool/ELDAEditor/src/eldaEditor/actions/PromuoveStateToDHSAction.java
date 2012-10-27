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

package eldaEditor.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import eldaEditor.editparts.DSCStateEditPart;
import genericUMLDiagramEditor.commands.ChangeStereotypeCommand;
import genericUMLDiagramModel.ModelElement;


/**
 * Promuove a state created into top level to Default Entrance State 
 * @author samuele
 *
 */
public class PromuoveStateToDHSAction extends SelectionAction {
	
	public static final String ID = "Des_Action";
	private DSCDiagram parent; 
	

	public PromuoveStateToDHSAction(IWorkbenchPart editor) {
		super(editor);
		this.setId(ID);
		this.setText("Promuove State to Default Entrance State");
		// TODO Auto-generated constructor stub
	}

	protected boolean calculateEnabled() {

		List selection= this.getSelectedObjects();
		//non posso promuovere più di uno stato
			if(selection.size()!=1)
				return false;
		//se l'oggetto selezionato non è uno state non abilito l'azione	
			if (!(selection.get(0)instanceof DSCStateEditPart))
				return false;
		//se l'oggetto selezionato non è a top level
			if (selection.get(0)instanceof DSCStateEditPart && 
				(((DSCState) ((DSCStateEditPart)selection.get(0)).getModel()).getParent()==null))
				return false;
		//se negli elementi contenuti a top level già qualcuno è di tipo des
			if (selection.get(0)instanceof DSCStateEditPart && 
				(((DSCState) ((DSCStateEditPart)selection.get(0)).getModel()).getParent()!=null))
				parent = (DSCDiagram)  ((DSCState) ((DSCStateEditPart)selection.get(0)).getModel()).getParent();
				
				Iterator i = parent.getModelElements().iterator();
				while (i.hasNext()) 
				{
		        ModelElement m =(ModelElement) i.next();
		        if (m instanceof DSCState && (((DSCState)m).getStereotype().equals("DHS")|| ((DSCState)m).getStereotype().equals("dhs")) )
		        	{
		        	return false;
		        	}
				}
		return true;
	}

public void run ()
{
	List selection = this.getSelectedObjects();
	Command com = new ChangeStereotypeCommand(((DSCState) ((DSCStateEditPart)selection.get(0)).getModel()),"DHS");
	execute(com);	
}
}
