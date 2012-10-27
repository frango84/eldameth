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

package eldaEditor.codegen.elda;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import dscDiagramModel.DSCDiagram;
import eldaEditor.wizards.DSCDiagramCreationPage_ChooseName;

/**
 * Classe che genera la struttura del progetto che conterrà l'implementazione.
 * Inizalizza la classe util che viene utilizzata per la generazione.
 * 
 * @author S. Mascillaro, F. Rango
 */
public class CodeGenerator implements ICodeGenerator {

	/**
	 * classe che fornisce alcune utility oer la generazione del codice
	 */
	private CodeGeneratorUtil util;

	/**
	 * Variabile che punta all'ELDAFramework
	 */
	public static Path frameworkPath = new Path(
			"ECLIPSE_HOME/plugins/ELDAFramework.jar");

	private NullProgressMonitor monitor = new NullProgressMonitor();

	/**
	 * E' il file .dsc attivo al momento della generazione del codice
	 */
	private IFile fileInput;

	/**
	 * E' il progetto che contiene il file .dsc da cui si esegue la generazione
	 */
	private IProject dscProject;

	/**
	 * E' il nome del progetto senza gli eventuali spazi e trattini
	 */
	private String dscProjectName;

	/**
	 * Il DSCDiagram contenuto in fileInput
	 */
	private DSCDiagram parent;

	private String agentPackageName;
	private String eventsPackageName;

	/**
	 * relativo all'agente per cui si sta generando codice
	 */
	private String fileInputName;

	/**
	 * nome del progetto da creare
	 */
	private String newProjName;

	public CodeGenerator(DSCDiagram parent) {
		this.parent = parent;
		init();
	}

	/**
	 * inizializza opportunamente le variabili globali utili per la generazione
	 * del codice.
	 */
	public void init() {
		fileInput = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile();
		fileInputName = fileInput.getName()
				.substring(
						0,
						fileInput.getName().lastIndexOf(
								fileInput.getFileExtension()) - 1).replaceAll(
						" ", "_").replaceAll("-", "_");
		dscProject = fileInput.getProject();
		dscProjectName = dscProject.getName().replaceAll(" ", "_").replaceAll(
				"-", "_");
		agentPackageName = dscProjectName.toLowerCase() + "."
				+ fileInputName.toLowerCase();
		eventsPackageName = dscProjectName.toLowerCase() + "." + "events";
		newProjName = dscProjectName + "_ELDA_Implementation";

		util = new CodeGeneratorUtil(parent, fileInput);
	}

	public void start() {
		try {
			// Creo la struttura del nuovo progetto
			IFolder[] output = projectCreator();

			if (output != null) { // output = null quando il progetto da
									// generare esiste già e l'utente decide di
									// NON sovrascriverlo

				// Genero il codice relativo all'ADSC
				ADSCClassCodeGenerator activeStateCodeGenerator = new ADSCClassCodeGenerator(
						parent, fileInputName, dscProjectName,
						agentPackageName, output[0], util, fileInput);
				activeStateCodeGenerator.run();

				// Genero il codice relativo agli eventi
				EventsClassesCodeGenerator eventsClassesCodeGenerator = new EventsClassesCodeGenerator(
						eventsPackageName, output[1], util);
				eventsClassesCodeGenerator.run();

				MessageDialog.openInformation(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), "INFO",
						"Code generation executed successfully in \""
								+ newProjName + "\" project!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			MessageDialog.openError(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(), "ERROR MESSAGE",
					"ERROR: " + ex.toString());
		}
	}

	/**
	 * 
	 * @return il primo valore dell'array è il package che conterrà il codice
	 *         degli ADSC, mentre il secondo è il package che conterrà il codice
	 *         degli eventi.
	 */
	private IFolder[] projectCreator() {
		// dichiaro il nuovo progetto
		IProject newProject = null;

		// Serve a contenere l'eventuale cartella src del progetto dsc
		IFolder dscSrcFolder = null;

		// Faccio il retrieve di tutti i progetti presenti nel workspace
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();

		// Verifico che non è gia presente un progetto con le stesso nome
		boolean found = false;
		for (int i = 0; i < projects.length & !found; i++) {
			// se è gia stato precedentement generato
			if (projects[i].getName().equals(newProjName)) {
				found = true;
				newProject = projects[i];
			}
		}

		if (found) {
			boolean result = MessageDialog.openConfirm(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"CONFIRM", "ATTENTION: A project named \"" + newProjName
							+ "\" already exists: overwrite it ?");
			if (result == false) {
				return null; // se l'utente decide di non sovrascrivere il
								// progetto esistente, restituiamo NULL
			}
		}

		// se non esisteva nel workspace un progetto omonimo
		if (!found) {
			// get project from workspace
			newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
					newProjName);
			// and create it
			try {
				newProject.create(monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			// open it
			try {
				newProject.open(monitor);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// add the java nature to the project
			IProjectDescription description = newProject.getWorkspace()
					.newProjectDescription(newProjName);

			String[] currentNatures = description.getNatureIds();

			String[] newNatures = new String[currentNatures.length + 1];

			System.arraycopy(currentNatures, 0, newNatures, 0,
					currentNatures.length);
			newNatures[newNatures.length - 1] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			try {
				newProject.setDescription(description, monitor);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// creo la cartella src nel nuovo progetto
			IFolder srcFolder = newProject.getFolder("src");
			try {
				srcFolder.create(true, true, monitor);
			} catch (CoreException e2) {
				// TODO gestire il caso in cui la cartella src già esiste
				e2.printStackTrace();
			}
		}

		// indipendentemente dalla eventuale precedente presenza del progetto
		// se dentro la cartella src del nuovo progetto è già presente la
		// cartella relativa
		IFolder projectFolder = newProject.getFolder("src").getFolder(
				dscProjectName.toLowerCase());
		try {
			if (!projectFolder.exists()) {
				projectFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			// TODO gestire il caso in cui la cartella nameProject già esiste
			e1.printStackTrace();
		}

		// se il progetto contiene la cartella fileName
		IFolder diagramFolder = projectFolder.getFolder(fileInputName
				.toLowerCase());
		try {
			if (!diagramFolder.exists()) {
				diagramFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IFolder eventsFolder = projectFolder.getFolder("events");
		try {
			if (!eventsFolder.exists()) {
				eventsFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		IJavaElement jElement = JavaCore.create((IResource) newProject);
		IJavaProject newJProject = jElement.getJavaProject();

		// solo se non avevo trovato il progetto
		if (!found) {
			// recupero le classpath entry originali
			IClasspathEntry[] dscEntries = null;
			try {
				dscEntries = JavaCore.create((IResource) dscProject)
						.getJavaProject().getRawClasspath();
			} catch (JavaModelException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}

			// verifico se esiste l'entry associata ad Actiware e alla cartella
			// src

			boolean actiwareEntryExists = false;
			boolean dscSrcFolderExists = false;
			int dscSrcEntryIndex = -1;

			for (int i = 0; i < dscEntries.length; i++) {

				// se actiware era gia stato aggiunto al progetto originario
				if (dscEntries[i].equals(JavaCore.newVariableEntry(
						frameworkPath, null, null)))
					actiwareEntryExists = true;

				// da gestire
				// se actiware era stato aggiunto come variabile
				if (dscEntries[i].getEntryKind() == IClasspathEntry.CPE_VARIABLE) {

				}

				// se esisteva una cartella di tipo src
				if (dscEntries[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					dscSrcFolderExists = true;
					dscSrcEntryIndex = i;
					dscSrcFolder = dscProject.getFolder(dscEntries[i].getPath()
							.removeFirstSegments(1));
				}
			}

			// istanzio le entry del nuovo progetto
			IClasspathEntry[] newEntries = null;

			// se sono entrambe presenti
			if (actiwareEntryExists && dscSrcFolderExists)
				newEntries = new IClasspathEntry[dscEntries.length];
			// se sono entrambi assenti
			else if (!actiwareEntryExists && !dscSrcFolderExists)
				newEntries = new IClasspathEntry[dscEntries.length + 2];
			// se solo una è assente
			else
				newEntries = new IClasspathEntry[dscEntries.length + 1];

			// associo alla cartella src del nuovo progetto una source entry
			IClasspathEntry srcEntry = JavaCore.newSourceEntry(newProject
					.getFolder("src").getFullPath());

			IClasspathEntry actiwareEntry = null;
			if (!actiwareEntryExists) {
				// actiware.jar entry using variable
				actiwareEntry = JavaCore.newVariableEntry(frameworkPath, null,
						null);
			}

			// copio ogni entry del progetto originale in quello nuovo
			for (int i = 0; i < dscEntries.length; i++)
				if (dscSrcFolderExists)
					newEntries[i] = dscEntries[i];
				else
					newEntries[i + 1] = dscEntries[i];

			// se esiste srcEntry
			if (dscSrcFolderExists) {
				newEntries[dscSrcEntryIndex] = srcEntry;
				// se non esiste Actiware entry
				if (!actiwareEntryExists)
					newEntries[newEntries.length - 1] = actiwareEntry;
			}
			// Se non esiste srcEntry
			else {
				newEntries[0] = srcEntry;
				// se non esiste Actiware Entry
				if (!actiwareEntryExists) {
					newEntries[newEntries.length - 1] = actiwareEntry;
				}
			}

			try {
				if (dscSrcFolderExists) {
					IResource[] members = dscSrcFolder.members();
					for (int i = 0; i < members.length; i++) {
						members[i].copy(newProject.getFolder("src").getFolder(
								members[i].getName()).getFullPath(), true,
								monitor);
					}
				}
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				newJProject.setRawClasspath(newEntries, monitor);
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IFolder[] results = { diagramFolder, eventsFolder };

		return results;

	}

}
