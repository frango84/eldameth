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

package eldaEditor.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DscDiagramModelFactory;
import eldaEditor.codegen.elda.CodeGenerator;
import eldaEditor.codegen.jade.CodeGeneratorForJADE;
import eldaEditor.icons.OutlineIcons;
import genericUMLDiagramModel.GenericDiagram;

/**
 * This WizardPage can create an empty .statechart file for the StatechartEditor.
 * @author S. Mascillaro, F. Rango
 */
public class DSCDiagramCreationPage_ChooseName extends WizardNewFileCreationPage {
	protected String DEFAULT_NAME = "DSCFile";
	protected String DEFAULT_EXTENSION = ".dsc";
	protected final IWorkbench workbench;
	protected IStructuredSelection selection;

	private String eventFile;
	private String guardFile;
	private String actionFile;
	private String functionFile;

	private String modelFilename; 

	/**
	 * Create a new wizard page instance.
	 * @param workbench the current workbench
	 * @param selection the current object selection
	 * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
	 */
	DSCDiagramCreationPage_ChooseName(IWorkbench workbench, IStructuredSelection selection) {
		super("ChoosePage", selection);
		this.workbench = workbench;
		this.selection = selection;
		setTitle("Create a new " + DEFAULT_EXTENSION + " file");
		setDescription("Create a new " + DEFAULT_EXTENSION + " resource.");
		setImageDescriptor(ImageDescriptor.createFromImage(OutlineIcons.IMAGE_WIZBAN));

	}
	/**
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);

		// Setto il nome di default
		String defaultModelBaseFilename = DEFAULT_NAME;
		String defaultModelFilenameExtension = DEFAULT_EXTENSION;
		modelFilename = defaultModelBaseFilename + defaultModelFilenameExtension;

		if (selection != null && !selection.isEmpty()) {
			// Get the resource...
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				IResource selectedResource = (IResource)selectedElement;
				if (selectedResource.getType() == IResource.FILE)
					selectedResource = selectedResource.getParent();

				// This gives us a directory...
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					for (int i = 1; ((IContainer)selectedResource).findMember(modelFilename)!= null; ++i)
						modelFilename = defaultModelBaseFilename + i + defaultModelFilenameExtension;
				}
			}
			if (selectedElement instanceof IJavaProject ){
				IJavaProject selectedResource= (IJavaProject) selectedElement;
//				Set this for the container.
				setContainerFullPath(selectedResource.getPath());

//				Make up a unique new name here.
				try {
					for (int i = 1; ((IContainer)(selectedResource).getCorrespondingResource()).findMember(modelFilename)!= null; ++i)
						modelFilename = defaultModelBaseFilename + i + defaultModelFilenameExtension;
				} catch (JavaModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		setFileName(modelFilename);
		setPageComplete(validatePage());
	}

	/**
	 * This method will be invoked, when the "Finish" button is pressed.
	 * @see ShapesCreationWizard#performFinish()
	 */
	public boolean finish() {
		// create a new file, result != null if successful

		// Get the URI of the model file.
		IFile newFile = createNewFile();
		// open newly created file in the editor
		IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		if (newFile != null && page != null) {
			try {
				URI fileURI = URI.createURI(newFile.getFullPath().toString());
				ResourceSet resourceSet = new ResourceSetImpl();

				// Create a resource for this file.
				Resource resource = resourceSet.createResource(fileURI);

				resource.getContents().add(createContent());
				try {
					resource.save(Collections.EMPTY_MAP);
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				IDE.openEditor(page, newFile, true);

			}
			catch (PartInitException e) {
				e.printStackTrace();
				return false;
			}
			modifyProject(newFile);
		}
		return true;
	}

	private void modifyProject(IFile newFile){

		IProject project=newFile.getProject();
		IProjectDescription description;
		try {
			description =project.getDescription();

			String[] currentNatures = description.getNatureIds();

			String[] newNatures = new String[currentNatures.length+1];
			System.arraycopy(currentNatures, 0, newNatures, 0, currentNatures.length);
			newNatures[newNatures.length-1] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			try {
				project.setDescription(description, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IJavaProject javaProj = JavaCore.create(project);
			IFolder binDir = project.getFolder("bin");
			IPath binPath = binDir.getFullPath();
			javaProj.setOutputLocation(binPath, null);

			IClasspathEntry cpeFramework = JavaCore.newVariableEntry(CodeGenerator.frameworkPath, null, null);
			IClasspathEntry cpeJade = JavaCore.newVariableEntry(CodeGeneratorForJADE.jadePath, null, null);
			IClasspathEntry cpeJava = JavaRuntime.getDefaultJREContainerEntry();

			//recupero le classpath entry originali
			IClasspathEntry[] dscEntries=null;
			try {
				dscEntries= javaProj.getRawClasspath();
				
			} catch (JavaModelException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			boolean frameworkEntryExists = false;
			boolean jadeEntryExists = false;
			boolean javaEntryExists = false;
			
			for (int i=0; i<dscEntries.length; i++){
				
				//vedo se ELDAFramework è già stato aggiunto al progetto originario
				if(dscEntries[i].equals(JavaCore.newVariableEntry(CodeGenerator.frameworkPath, null, null)))
					frameworkEntryExists = true;
				
				//vedo se JADE è già stato aggiunto al progetto originario
				if(dscEntries[i].equals(JavaCore.newVariableEntry(CodeGeneratorForJADE.jadePath, null, null)))
					jadeEntryExists = true;
				
				//vedo se Java è già stato aggiunto al progetto originario
				if(dscEntries[i].equals(JavaRuntime.getDefaultJREContainerEntry()))
					javaEntryExists = true;
			}
			
			IClasspathEntry[] newEntries = null;
				
			if (frameworkEntryExists && javaEntryExists && jadeEntryExists){
				newEntries = new IClasspathEntry[dscEntries.length];
				for(int i=0; i<dscEntries.length; i++)
					newEntries[i] = dscEntries[i];
			}
			else{
				ArrayList<IClasspathEntry> list = new ArrayList<IClasspathEntry>();
				if(!frameworkEntryExists){
					list.add(cpeFramework);
				}
				if(!javaEntryExists){
					list.add(cpeJava);
				}
				if(!jadeEntryExists){
					list.add(cpeJade);
				}
				
				newEntries = new IClasspathEntry[dscEntries.length + list.size()];
				for(int i=0; i < dscEntries.length; i++){
					newEntries[i] = dscEntries[i];
				}
				for(int i=0; i < list.size(); i++){
					newEntries[dscEntries.length + i] = list.get(i);
				}
			}

			javaProj.setRawClasspath(newEntries, new NullProgressMonitor());
		}
		catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
	 */
	protected InputStream getInitialContents() {
		ByteArrayInputStream bais = null;
		return bais;
	}

	/**
	 * Return true, if the file name entered in this page is valid.
	 */
	protected boolean validateFilename() {
		if (getFileName() != null && getFileName().endsWith(DEFAULT_EXTENSION)) {
			return true;
		}
		setErrorMessage("The 'file' name must end with " + DEFAULT_EXTENSION);
		return false;
	}

	/**
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
	 */
	protected boolean validatePage() {
		return super.validatePage() && validateFilename();
	}

	protected GenericDiagram createContent() {
		DscDiagramModelFactory factory = DscDiagramModelFactory.eINSTANCE;
		DSCDiagram dscDiagram=factory.createDSCDiagram(); 

		if(!(eventFile.trim().equals("")))
			dscDiagram.setEventFile(eventFile);

		if(!(guardFile.trim().equals("")))
			dscDiagram.setGuardFile(guardFile);

		if(!(actionFile.trim().equals("")))
			dscDiagram.setActionFile(actionFile);

		if(!(functionFile.trim().equals("")))
			dscDiagram.setFunctionFile(functionFile);

		return dscDiagram;
	}
	
	protected void setFile(String eventFile,String  guardFile, String  actionFile,String  functionFile){
		this.eventFile=eventFile;
		this.guardFile=guardFile;
		this.actionFile=actionFile;
		this.functionFile=functionFile;
	}

}
