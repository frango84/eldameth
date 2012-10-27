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
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import dscDiagramModel.DSCDiagram;
import eldaEditor.codegen.jade.CodeGeneratorForJADE;
import eldaEditor.codegen.jade.ICodeGeneratorForJADE;

/**
 * @author S. Mascillaro, F. Rango
 */
public class CodeGeneratorForJADEAction extends Action {

	public static final String ID = "Code_Generator_For_JADE_Action";
	private DSCDiagram parent;

	public CodeGeneratorForJADEAction(){
		this.setId(ID);
		this.setText("To JADE");
	}

	protected boolean calculateEnabled(){
		return true;
	}

	public void run(){
		// TODO verificare che il diagramma sia salvato e salvarlo
		// TODO Dare gli alert di errori nella generazione tipo des non settato

		this.parent = (DSCDiagram) ((GenericDiagramEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor()).getContents();

		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
				ICodeGeneratorForJADE codeGenerator = new CodeGeneratorForJADE(parent);
				codeGenerator.start();
			}
		};

		try {
			operation.run(null);

		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
