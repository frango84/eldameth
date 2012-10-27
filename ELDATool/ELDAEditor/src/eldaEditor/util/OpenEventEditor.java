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

import triggerModel.TriggerModelFactory;
import triggerModel.TriggerModelPackage;
import dscDiagramModel.DSCDiagram;
import eldaEditor.dialogs.events.EventClassesDefinitionDialog;

public class OpenEventEditor {


	protected String eventFile;
	protected Resource eventResource=null;
	
	
	protected final IFile dscfile=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();
	
	public OpenEventEditor(DSCDiagram parent) {
		// TODO Auto-generated constructor stub
		this.eventFile=parent.getEventFile();
		
	}
	
	public void open()
	{

		WorkspaceModifyOperation operation=createResourceOperationForEvent(eventFile);
		
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
		
		
		EventClassesDefinitionDialog dialog=new EventClassesDefinitionDialog(new Shell(), eventResource);
		dialog.open();
	}

	
	public WorkspaceModifyOperation createResourceOperationForEvent(final String baseDati) {

//		istanze necessarie alla creazione di nuovi base dati non presenti
		final TriggerModelPackage eventPackage = TriggerModelPackage.eINSTANCE;
		final TriggerModelFactory eventFactory = eventPackage.getTriggerModelFactory();

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
							EClass eClass = (EClass) eventPackage.getEClassifier("TriggerList");
						
							EObject rootObject = eventFactory.create(eClass);
					
						
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
						eventResource=(Resource)resourceSet.getResources().get(0);
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
