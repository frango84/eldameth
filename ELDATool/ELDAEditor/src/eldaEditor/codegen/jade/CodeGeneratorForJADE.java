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

package eldaEditor.codegen.jade;

import java.util.ArrayList;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import dscDiagramModel.DSCDiagram;

/**
 * Classe che genera la struttura del progetto che conterrà l'implementazione.
 * 
 * @author G. Fortino, F. Rango
 */
public class CodeGeneratorForJADE implements ICodeGeneratorForJADE {

	/**
	 * classe che fornisce alcune utility per la generazione del codice
	 */
	private CodeGeneratorUtilForJADE util;
	
	/**
	 * Variabile che punta al file JAR di JADE
	 */
	public static Path jadePath = new Path("ECLIPSE_HOME/plugins/HybridJADE.jar");

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

	public CodeGeneratorForJADE(DSCDiagram parent) {
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
		fileInputName = fileInput.getName().substring(0, fileInput.getName().lastIndexOf(fileInput.getFileExtension()) - 1).replaceAll(" ", "_").replaceAll("-", "_");
		dscProject = fileInput.getProject();
		dscProjectName = dscProject.getName().replaceAll(" ", "_").replaceAll("-", "_");
		agentPackageName = dscProjectName.toLowerCase() + "." + fileInputName.toLowerCase();
		eventsPackageName = dscProjectName.toLowerCase() + "." + "events";
		newProjName = dscProjectName + "_JADE_Implementation";

		util = new CodeGeneratorUtilForJADE(parent, fileInput);
	}

	//invocare questo metodo per avviare la generazione del codice
	public void start(){
		try{
			//creiamo la struttura del nuovo progetto
			IFolder [] output = projectCreator();
			
			if(output != null){ //output = null quando il progetto da generare esiste già e l'utente decide di NON sovrascriverlo
				
				//generiamo il codice relativo all'agente
				AgentClassCodeGeneratorForJADE agentCodeGenerator = new AgentClassCodeGeneratorForJADE(
						parent, fileInputName, dscProjectName, agentPackageName, output[0], util, fileInput);
				agentCodeGenerator.run();

				//generiamo il codice relativo agli eventi
				EventsClassesCodeGeneratorForJADE eventsClassesCodeGenerator = new EventsClassesCodeGeneratorForJADE(
						eventsPackageName, output[1], util);
				eventsClassesCodeGenerator.run();

				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "INFO", "Code generation executed successfully in \"" + newProjName + "\" project!");
			}
		}
		catch(InvalidEventException e1){
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ERROR MESSAGE","ERROR: Found an event type not allowed for JADE code generation: " + e1.getMessage());
		}
		catch(Exception e2){
			e2.printStackTrace();
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ERROR MESSAGE","ERROR: " + e2.toString());
		}
	}

	/**
	 * Crea il progetto.
	 * 
	 * @return il primo valore dell'array è il package che conterrà il codice
	 *         dell'agente, mentre il secondo è il package
	 *         che conterrà il codice degli eventi.
	 */
	private IFolder [] projectCreator() {
		
		//dichiaro il nuovo progetto
		IProject newProject = null;

		IFolder dscSrcFolder = null; //serve a contenere l'eventuale cartella "src"

		//preleviamo tutti i progetti presenti nel workspace
		IProject [] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();

		//verifichiamo se nel workspace è già presente un progetto con le stesso nome del progetto da creare
		boolean found = false;
		for (int i = 0; i < projects.length & !found; i++) {
			// se è gia stato precedentemente generato
			if (projects[i].getName().equals(newProjName)) {
				found = true;
				newProject = projects[i];
			}
		}

		if(found){
			boolean result = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "CONFIRM", "ATTENTION: A project named \"" + newProjName + "\" already exists: overwrite it ?");
			if(result == false){
				return null; //se l'utente decide di non sovrascrivere il progetto esistente, restituiamo NULL
			}
		}

		if(!found){
			//nel workspace non esiste già un progetto con lo stesso nome,
			//quindi effettuiamo le operazioni di inizializzazione del progetto
			
			//get project from workspace
			newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(newProjName);

			//create it
			try {
				newProject.create(monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}

			//open it
			try {
				newProject.open(monitor);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}

			//add the java nature to the project
			IProjectDescription description = newProject.getWorkspace().newProjectDescription(newProjName);
			String[] currentNatures = description.getNatureIds();
			String[] newNatures = new String[currentNatures.length + 1];
			System.arraycopy(currentNatures, 0, newNatures, 0, currentNatures.length);
			newNatures[newNatures.length - 1] = JavaCore.NATURE_ID;
			description.setNatureIds(newNatures);
			try {
				newProject.setDescription(description, monitor);
			} catch (CoreException e) {
				e.printStackTrace();
			}

			//creo la cartella "src" nel nuovo progetto
			IFolder srcFolder = newProject.getFolder("src");
			try {
				srcFolder.create(true, true, monitor);
			} catch (CoreException e2) {
				e2.printStackTrace();
			}	
		}

		//creo la cartella "dscProjectName" nella cartella "src"
		IFolder projectFolder = newProject.getFolder("src").getFolder(dscProjectName.toLowerCase());
		try {
			if(!projectFolder.exists()){
				projectFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		//creo la cartella "fileInputName"
		IFolder diagramFolder = projectFolder.getFolder(fileInputName.toLowerCase());
		try {
			if(!diagramFolder.exists()){
				diagramFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		//creo la cartella degli eventi
		IFolder eventsFolder = projectFolder.getFolder("events");
		try {
			if(!eventsFolder.exists()){
				eventsFolder.create(true, true, monitor);
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		IJavaElement jElement = JavaCore.create((IResource) newProject);
		IJavaProject newJProject = jElement.getJavaProject();

		if(!found){ //solo se il progetto non esiste già ...
			
			//recupero le classpath entry del progetto originale "dscProject"
			IClasspathEntry[] dscEntries = null;
			try {
				dscEntries = JavaCore.create((IResource) dscProject).getJavaProject().getRawClasspath();
			} catch (JavaModelException e4) {
				e4.printStackTrace();
			}

			//verifico se nel progetto originale "dscProject" esiste l'entry associata alla
			//variabile del file JAR di JADE e alla cartella "src"
			boolean variableEntryExists = false;
			boolean dscSrcFolderExists = false;
			int dscSrcEntryIndex = -1;
			for (int i = 0; i < dscEntries.length; i++) {
				if (dscEntries[i].equals(JavaCore.newVariableEntry(jadePath, null, null)))
					variableEntryExists = true;
				
				if (dscEntries[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					dscSrcFolderExists = true;
					dscSrcEntryIndex = i;
					dscSrcFolder = dscProject.getFolder(dscEntries[i].getPath().removeFirstSegments(1));
				}
			}

			//istanzio le entry del nuovo progetto
			IClasspathEntry[] newEntries = null;
			if (variableEntryExists && dscSrcFolderExists)
				newEntries = new IClasspathEntry[dscEntries.length];
			else if (!variableEntryExists && !dscSrcFolderExists)
				newEntries = new IClasspathEntry[dscEntries.length + 2];
			else
				newEntries = new IClasspathEntry[dscEntries.length + 1];

			//associo alla cartella "src" del nuovo progetto una source entry
			IClasspathEntry srcEntry = JavaCore.newSourceEntry(newProject.getFolder("src").getFullPath());

			IClasspathEntry variableEntry = null;
			if (!variableEntryExists) {
				//creiamo la variabile che punta al file JAR di JADE
				variableEntry = JavaCore.newVariableEntry(jadePath, null, null);
			}

			//copio ogni entry del progetto originale "dscProject" in quello nuovo
			for (int i = 0; i < dscEntries.length; i++){
				if (dscSrcFolderExists)
					newEntries[i] = dscEntries[i];
				else
					newEntries[i + 1] = dscEntries[i];
			}
			if (dscSrcFolderExists) {
				newEntries[dscSrcEntryIndex] = srcEntry;
				if (!variableEntryExists)
					newEntries[newEntries.length - 1] = variableEntry;
			}
			else {
				newEntries[0] = srcEntry;
				if (!variableEntryExists) {
					newEntries[newEntries.length - 1] = variableEntry;
				}
			}
			try {
				if (dscSrcFolderExists) {
					IResource[] members = dscSrcFolder.members();
					for (int i = 0; i < members.length; i++) {
						members[i].copy(newProject.getFolder("src").getFolder(
								members[i].getName()).getFullPath(), true, monitor);
					}
				}
			}
			catch (CoreException e1) {
				e1.printStackTrace();
			}
			try {
				newJProject.setRawClasspath(newEntries, monitor);
			}
			catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		//creiamo il risultato da restituire:
		// - il 1° valore dell'array è il package che conterrà il codice dell'agente
		// - il 2° valore dell'array è il package che conterrà il codice degli eventi
		IFolder[] results = { diagramFolder, eventsFolder };
		return results;
	}

}
