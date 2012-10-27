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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import dscDiagramModel.DSCDiagram;
import eldaEditor.dialogs.gora.ActionGuardDefinitionDialog;
import eldaEditor.util.OpenActionEditor;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;


/**
 * Apre l'editor delle funzioni di supporto 
 * @author samuele
 *
 */
public class GuardEditorAction extends ActionEditorAction {
	
	public static final String ID = "Guard_Editor_Action";
	private DSCDiagram parent; 
	
	

	public GuardEditorAction() {
		
		
		this.setId(ID);
		this.setText("Guard Editor");
	
		// TODO Auto-generated constructor stub
	}


public void run ()
{
	
	this.parent=(DSCDiagram)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getContents();
	
	OpenGuardEditor openGuardEditor= new OpenGuardEditor(parent);
	openGuardEditor.open();
		
	}
}

class OpenGuardEditor extends OpenActionEditor{
	
	
//	private final IFile dscfile=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();
//	private Resource goraResource=null;
	
	public OpenGuardEditor(DSCDiagram parent) {
		super(parent);
		// TODO Auto-generated constructor stub
		this.goraFile=parent.getGuardFile();

	}
	
	public void open()
	{

		WorkspaceModifyOperation operation=createResourceOperationForGora(goraFile);

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
		
		WorkspaceModifyOperation operation2=createResourceOperationForFunction(funcFile);
		try
		{
			operation2.run(null);
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		funcIFile=dscfile.getProject().getFile(funcFile);
		ActionGuardDefinitionDialog dialog=new ActionGuardDefinitionDialog(new Shell(), goraResource, funcIFile,false,parent);
		dialog.open();
	}

	
//	protected WorkspaceModifyOperation createResourceOperationForGora(final String baseDati) {
//
////		istanze necessarie alla creazione di nuovi base dati non presenti
//		final  ActionGuardModelPackage goraPackage = ActionGuardModelPackage.eINSTANCE;
//		final  ActionGuardModelFactory goraFactory = goraPackage.getActionGuardModelFactory();
//
//		//definisco l'operazione da effettuare
//		WorkspaceModifyOperation operation =
//			new WorkspaceModifyOperation() {
//				protected void execute(IProgressMonitor progressMonitor) {
//					try {
//						// Create a resource set
//						//
//						ResourceSet resourceSet = new ResourceSetImpl();
//
//						// Get the URI of the model file.
//						//
//						URI fileURI = URI.createPlatformResourceURI(dscfile.getProject().getFullPath().append(baseDati).toString());
//
//						// Create a resource for this file.
//						//
//						
//						Resource resource=null;
//						try
//						{
//							resource=resourceSet.getResource(fileURI,true);
//						}
//						//gestisco l'eccezione
//						catch (RuntimeException e) 
//						{
//							System.err.println("Non esiste la risorsa "+ baseDati);
//							resource= resourceSet.createResource(fileURI);
//							EClass eClass = (EClass) goraPackage.getEClassifier("AnElementList");
//						
//							EObject rootObject = goraFactory.create(eClass);
//					
//						
//							// Add the initial model object to the contents.
//								
//							if (rootObject != null) {
//								resource.getContents().add(0,rootObject);
//						}
//						
//						// Save the contents of the resource to the file system.
//						//
//						Map options = new HashMap();
//						options.put(XMLResource.OPTION_ENCODING, "UTF-8");
//						resource.save(options);
//						
//						resourceSet.getResource(fileURI,true);
//						
//						
//						}
//						goraResource=(Resource)resourceSet.getResources().get(0);
//					}
//					catch (Exception exception) {
////						GoraEditorPlugin.INSTANCE.log(exception);
//					}
//					finally {
//						progressMonitor.done();
//					}
//				}
//			};
//			
//			return operation;
//	}

	
}

