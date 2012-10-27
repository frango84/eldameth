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

import function.FunctionFactory;
import function.FunctionPackage;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;

import dscDiagramModel.DSCDiagram;
import eldaEditor.dialogs.function.FunctionNameDefinitionDialog;

/**
 * Apre l'editor delle funzioni di supporto 
 * @author samuele
 *
 */
public class SupportFunctionEditorAction extends Action {

	public static final String ID = "Support_Function_Editor_Action";
	private DSCDiagram parent; 



	public SupportFunctionEditorAction() {


		this.setId(ID);
		this.setText("Function Editor");

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

		OpenSupportFunctionEditor openFunctionEditor= new OpenSupportFunctionEditor(parent);
		openFunctionEditor.open();


	}
}

class OpenSupportFunctionEditor 

{
	private String functionFile=null;
	private Resource functionResource=null;
	private WorkspaceModifyOperation operation=null;

	private final IFile dscfile=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();

	public OpenSupportFunctionEditor(DSCDiagram parent) {
		// TODO Auto-generated constructor stub
		this.functionFile=parent.getFunctionFile();
		this.operation=new ResourceOperationForFunction(functionFile,dscfile).create();

	}

	public void open()
	{

		try
		{
			operation.run(null);
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		FunctionNameDefinitionDialog dialog=new FunctionNameDefinitionDialog(new Shell(), functionResource);
		dialog.open();
	}

	public class ResourceOperationForFunction {
		private FunctionPackage functionPackage;
		private FunctionFactory functionFactory;
		private String baseDati;
		private IFile dscfile;
		
		public ResourceOperationForFunction(String baseDati,IFile dscfile)
		{
			this.baseDati=baseDati;
			this.dscfile=dscfile;
//			istanze necessarie alla creazione di nuovi base dati non presenti
			functionPackage = FunctionPackage.eINSTANCE;
			functionFactory = functionPackage.getFunctionFactory();
			create();
		}


		private WorkspaceModifyOperation create() {




			//definisco l'operazione da effettuare
			WorkspaceModifyOperation operation =
				new WorkspaceModifyOperation() {
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						// Create a resource set
						//
						ResourceSet resourceSet = new ResourceSetImpl();

						// Get the URI of the model file.
						//
						URI fileURI = URI.createPlatformResourceURI(dscfile.getProject().getFullPath().append(baseDati).toString());

						// Create a resource for this file.
						//

						Resource resource=null;
						try
						{
							resource=resourceSet.getResource(fileURI,true);
						}
						//gestisco l'eccezione
						catch (RuntimeException e) 
						{
							System.err.println("Non esiste la risorsa "+ baseDati);
							resource= resourceSet.createResource(fileURI);
							EClass eClass = (EClass) functionPackage.getEClassifier("FunctionList");

							EObject rootObject = functionFactory.create(eClass);


							// Add the initial model object to the contents.

							if (rootObject != null) {
								resource.getContents().add(0,rootObject);
							}

							// Save the contents of the resource to the file system.
							//
							Map options = new HashMap();
							options.put(XMLResource.OPTION_ENCODING, "UTF-8");
							resource.save(options);

							resourceSet.getResource(fileURI,true);


						}
						functionResource=(Resource)resourceSet.getResources().get(0);
					}
					catch (Exception exception) {
//						GoraEditorPlugin.INSTANCE.log(exception);
					}
					finally {
						progressMonitor.done();
					}
				}
			};

			return operation;
		}

	}
}



