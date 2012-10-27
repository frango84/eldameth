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

package eldaEditor.actions.eldatools;


import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCDiagram;
import eldaEditor.util.OpenActionEditor;

/**
 * Apre l'editor delle funzioni di supporto 
 * @author samuele
 *
 */
public class ActionEditorAction extends Action {

	public static final String ID = "Action_Editor_Action";
	private DSCDiagram parent; 



	public ActionEditorAction() {


		this.setId(ID);
		this.setText("Action Editor");

		// TODO Auto-generated constructor stub
	}

	protected boolean calculateEnabled() {
		return true;
	}

	public void run ()
	{
		//TODO verificare che il diagramma sia salvato e salvarlo
		//TODO Dare gli alert di errori nella generazione tipo des non settato

		this.parent=(DSCDiagram)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getContents();

		OpenActionEditor openActionEditor= new OpenActionEditor(parent);
		openActionEditor.open();
	}
}


