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

package eldaEditor.util;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;

import actionGuardModel.ActionGuardModelFactory;
import actionGuardModel.ActionGuardModelPackage;
import dscDiagramModel.DSCDiagram;
import eldaEditor.dialogs.gora.ActionGuardDefinitionDialog;
import function.FunctionFactory;
import function.FunctionPackage;

public class OpenActionEditor {


	protected String goraFile,funcFile;
	protected DSCDiagram parent; 
	protected Resource goraResource=null;
	protected IFile funcIFile=null;
	
	protected final IFile dscfile=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();
	
	public OpenActionEditor(DSCDiagram parent) {
		// TODO Auto-generated constructor stub
		this.parent=parent;
		this.goraFile=parent.getActionFile();
		this.funcFile=parent.getFunctionFile();

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
		
		funcIFile=dscfile.getProject().getFile(funcFile);
		ActionGuardDefinitionDialog dialog=new ActionGuardDefinitionDialog(new Shell(), goraResource,funcIFile,true,parent);
		dialog.open();
	}

	
	public WorkspaceModifyOperation createResourceOperationForGora(final String baseDati) {

//		istanze necessarie alla creazione di nuovi base dati non presenti
		final  ActionGuardModelPackage goraPackage = ActionGuardModelPackage.eINSTANCE;
		final  ActionGuardModelFactory goraFactory = goraPackage.getActionGuardModelFactory();

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
							EClass eClass = (EClass) goraPackage.getEClassifier("AnElementList");
						
							EObject rootObject = goraFactory.create(eClass);
					
						
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
						goraResource=(Resource)resourceSet.getResources().get(0);
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
	
	public WorkspaceModifyOperation createResourceOperationForFunction(final String baseDati) {

//		istanze necessarie alla creazione di nuovi base dati non presenti
		final  FunctionPackage funcPackage = FunctionPackage.eINSTANCE;
		final FunctionFactory funcFactory = funcPackage.getFunctionFactory();

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
							EClass eClass = (EClass) funcPackage.getEClassifier("FunctionList");
						
							EObject rootObject = funcFactory.create(eClass);
					
						
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
						Resource funcResource=(Resource)resourceSet.getResources().get(0);
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
