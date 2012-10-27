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

package genericUMLDiagramEditor.editor;

import genericUMLDiagramEditor.icons.Icons;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

/**
 * This WizardPage can create an empty .generic file for the GenericDiagramEditor
 * 
 * @author marguu&zaza
 */
public class GenericDiagramCreationPage extends WizardNewFileCreationPage {
	protected String DEFAULT_EXTENSION = ".generic";
	protected String DEFAULT_NAME = "genericFile";
	protected final IWorkbench workbench;
	protected IStructuredSelection selection;
	
	/**
	 * Create a new wizard page instance.
	 * @param workbench the current workbench
	 * @param selection the current object selection
	 * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
	 */
	public GenericDiagramCreationPage(IWorkbench workbench, IStructuredSelection selection) {
		super("shapeCreationPage1", selection);
		this.workbench = workbench;
		this.selection = selection;
		setTitle("Create a new " + DEFAULT_EXTENSION + " file");
		setDescription("Create a new " + DEFAULT_EXTENSION + " resource.");
		setImageDescriptor(ImageDescriptor.createFromImage(Icons.IMAGE_WIZBAN));
	}
	
	/**
	 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		// Setto il nome di default
		String defaultModelBaseFilename = DEFAULT_NAME;
		String defaultModelFilenameExtension = DEFAULT_EXTENSION;
		String modelFilename = defaultModelBaseFilename + defaultModelFilenameExtension;
		
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
				catch (IOException e) {}
				IDE.openEditor(page, newFile, true);
				
			}
			catch (PartInitException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
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
	
	/**
	 * Return an empty GenericDiagram
	 */
	protected GenericDiagram createContent() {
		GenericUMLDiagramModelFactory factory = GenericUMLDiagramModelFactory.eINSTANCE;
		return factory.createGenericDiagram();
	}
}
