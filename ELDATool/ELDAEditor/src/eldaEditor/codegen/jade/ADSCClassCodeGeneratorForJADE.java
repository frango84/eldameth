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
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;

/**
 * Genera il codice relativo al DistilledStateChartBehaviour root
 * 
 * @author G. Fortino, F. Rango
 */
public class ADSCClassCodeGeneratorForJADE {

	private DSCDiagram parent;
	private IFile fileInput;
	private String fileName;
	private String agentName;
	private String packageName;
	private IFolder folder;
	private CodeGeneratorUtilForJADE util;
	private String dscProjectName;
	
	public static int contTransitions; //contatore generale delle transizioni

	public ADSCClassCodeGeneratorForJADE(DSCDiagram parent, String fileName,
			String dscProjectName, String packageName, IFolder folder,
			CodeGeneratorUtilForJADE util, IFile fileInput) {
		this.parent = parent;
		this.dscProjectName = dscProjectName;
		this.packageName = packageName;
		this.util = util;
		this.fileInput = fileInput;
		this.folder = folder;
		this.fileName = fileName;
		this.agentName = this.fileName.toUpperCase().substring(0, 1).concat(this.fileName.substring(1));
		contTransitions = 0;
	}

	public StringBuffer run(){
		StringBuffer result = new StringBuffer();

		//creiamo l'intestazione della classe principale
		StringBuffer stringBufferTopIntestation = createTopIntestation();
		
		//creiamo le variabili definite dall'utente
		StringBuffer stringBufferDiagramVariable = createDiagramVariableDefinition();

		//dichiariamo i sotto-stati tramite delle apposite variabili
		StringBuffer stringBufferVariabiliStati = util.creaVariabiliStati(parent.getModelElements());
		
		//creiamo la prima parte del costruttore della classe principale
		StringBuffer stringBufferCostruttoreParte1 = creaPrimaParteCostruttore();
		
		//creiamo le inner class dei sotto-stati
		StringBuffer stringBufferInnerClass = createInnerClass();
		
		//creiamo le variabili delle transizioni DOPO tutte le operazioni precedenti,
		//perchè le operazioni precedenti modificano il valore del contatore delle transizioni da utilizzare
		StringBuffer stringBufferVariabiliTransizioni = creaVariabiliTransizioni();
		
		//creiamo la seconda parte del costruttore della classe principale con le varie chiamate ai
		//metodi "createT..." e "addTransition". NOTA: creiamo le chiamate ai metodi "createT..."
		//e "addTransition" DOPO tutte le operazioni precedenti, perchè queste chiamate vengono
		//generate durante le operazioni precedenti
		StringBuffer stringBufferCostruttoreParte2 = creaSecondaParteCostruttore();
		
		//creiamo i metodi relativi alle azioni, alle guardie ed alle funzioni da inserire nella root
		StringBuffer stringBufferAction = util.createActionDefinitionIntoRoot();
		StringBuffer stringBufferGuard = util.createGuardDefinitionIntoRoot();
		StringBuffer stringBufferFunction = util.createFunctionDefinitionIntoRoot();
		
		//componiamo il risultato mettendo in ordine i vari componenti
		result.append(stringBufferTopIntestation);
		result.append(stringBufferDiagramVariable);
		result.append(stringBufferVariabiliTransizioni);
		result.append(stringBufferVariabiliStati);
		result.append(stringBufferCostruttoreParte1);
		result.append(stringBufferCostruttoreParte2);
		result.append(stringBufferAction);
		result.append(stringBufferGuard);
		result.append(stringBufferFunction);
		result.append(stringBufferInnerClass);
		result.append("}"); //chiudiamo la classe principale
		
		return result;
	}

	/**
	 * Crea la definizione della classe principale
	 * 
	 * @return
	 */
	private StringBuffer createTopIntestation() {
		StringBuffer intestation = new StringBuffer();
		intestation.append("private class " + agentName + "DSCBehaviour" + " extends DistilledStateChartBehaviour {\n");
		intestation.append("private " + agentName + " myAgent;\n");
		return intestation;
	}

	/**
	 * Crea la definizione delle variabili della root
	 * 
	 * @return
	 */
	private StringBuffer createDiagramVariableDefinition() {
		StringBuffer variableBuffer = new StringBuffer();
		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent.getDiagramVariables().get(i);
			int index = var.getType().lastIndexOf(".");
			if (index != -1)
				variableBuffer.append(var.getType().substring(index + 1));
			else
				variableBuffer.append(var.getType());
			variableBuffer.append(" " + var.getName());
			if (!var.getValue().equals(""))
				variableBuffer.append(" = " + var.getValue());
			variableBuffer.append(";\n");
		}
		return variableBuffer;
	}

	/**
	 * Crea la prima parte del costruttore della classe principale
	 */
	private StringBuffer creaPrimaParteCostruttore() {
		StringBuffer constructor = new StringBuffer();
		constructor.append("public " + agentName + "DSCBehaviour(" + agentName + " agent, String name");
		
		List<String> array = new ArrayList<String>();
		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent.getDiagramVariables().get(i);
			if (var.isBePresentIntoConstructor()) {
				int index = var.getType().lastIndexOf(".");
				if (index != -1)
					array.add(var.getType().substring(index + 1) + " " + var.getName());
				else
					array.add(var.getType() + " " + var.getName());
			}
		}
		
		if(array.size() != 0){
			constructor.append(", ");
		}
		
		for (int i = 0; i < array.size(); i++) {
			constructor.append(array.get(i));
			if (i < array.size() - 1)
				constructor.append(", ");
		}
		
		//settiamo la politica di gestione dei messaggi con "putbackMessage = FALSE" e
		//"mainTemplate" assegnato. Il "mainTemplate" viene settato in modo che vengono
		//ricevuti solo i messaggi con campo language uguale a "ELDAEvent.IN" (cioè solo
		//gli eventi di tipo IN). Questo permette di escludere
		//anche i messaggi inviati dall'AMS, che alcune volte risponde a una
		//richiesta con un messaggio contenente un'ontologia all'interno
		//del campo ContentObject e NON possiamo utilizzare il metodo
		//"getContentObject" (come accade in questo behaviour) per prelevare
		//il contenuto del messaggio, altrimenti viene sollevata un'eccezione.
		//Viene controllato il campo language anzichè il campo conversation-id,
		//perchè alcune volte l'AMS setta il conversation-id in maniera identica
		//ad un messaggio da lui ricevuto (es.: quando inviamo un messaggio ad
		//un destinatario inesistente, l'AMS risponde con un messaggio di errore
		//che ha lo stesso conversation-id del messaggio da noi inviato).
		constructor.append("){\n" + "super(agent, name, MessageTemplate.MatchLanguage(ELDAEvent.IN), false);\n");

		constructor.append("this.myAgent = agent;\n");
		
		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent.getDiagramVariables().get(i);
			if (var.isBePresentIntoConstructor()) {
				constructor.append("this." + var.getName() + " = " + var.getName() + ";\n");
			}
		}
		
		//creiamo i sotto-stati della root
		constructor.append(util.creaStati(parent.getModelElements()));
		
		//aggiungiamo i sotto-stati e settiamo la Default Deep History Entrance della root
		constructor.append(aggiungiStatiEDefaultDeepHistoryEntrance());

		return constructor;
	}
	
	/**
	 * Crea la seconda parte del costruttore della classe principale
	 */
	private StringBuffer creaSecondaParteCostruttore(){
		StringBuffer constructor = new StringBuffer();

		//creiamo le chiamate ai metodi "createT..."
		constructor.append(util.getChiamateCreateT());
		
		//creiamo le chiamate ai metodi "addTransition"
		constructor.append(util.getChiamateAddTransition());

		constructor.append("}\n");

		return constructor;
	}

	/**
	 * Crea il codice relativo alle inner class, cioè ai sotto-stati
	 * 
	 * @return
	 */
	private StringBuffer createInnerClass(){
		StringBuffer innerClassBuffer = new StringBuffer();
		for (int i = 0; i < parent.getModelElements().size(); i++){
			if (parent.getModelElements().get(i) instanceof DSCState){
				DSCState state = (DSCState) parent.getModelElements().get(i);
				DSCClassCodeGeneratorForJADE stateCodeGenerator = new DSCClassCodeGeneratorForJADE(state, parent, util, agentName);
				innerClassBuffer.append(stateCodeGenerator.run());
			}
		}
		return innerClassBuffer;
	}

	//metodo per aggiungere i sotto-stati e settare la Default Deep History Entrance della root,
	//in accordo al template DSC (aggiungendo come stato iniziale il connettore di storia profonda
	//presente nello stato "ACTIVE" del template DSC)
	private StringBuffer aggiungiStatiEDefaultDeepHistoryEntrance() {
		EList list = parent.getModelElements();
		StringBuffer result = new StringBuffer();
		String defaultDeepHistoryEntrance = "";
		String statiNonIniziali = "";
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof DSCState) {
				String nameState;
				if (((DSCState) list.get(i)).getName().toUpperCase().equals("")){
					nameState = "DEFAULT_NAME";
				}
				else {
					nameState = ((DSCState) list.get(i)).getName().toUpperCase().replaceAll(" ", "_");
				}

				if(((DSCState) list.get(i)).getStereotype().equalsIgnoreCase("DHS")){
					
					//la Default Deep History Entrance della root è lo stato che ha stereotipo "DHS"
					defaultDeepHistoryEntrance += ("setDefaultDeepHistoryEntrance(" + nameState.toLowerCase() +");\n"); //es.: setDefaultDeepHistoryEntrance(dsc1);
					
					//stato NON iniziale
					statiNonIniziali += ("addState(" + nameState.toLowerCase() +");\n"); //es.: addState(dsc1);
				}
				else{
					//stato NON iniziale
					statiNonIniziali += ("addState(" + nameState.toLowerCase() +");\n"); //es.: addState(dsc1);
				}
			}
		}
		
		//aggiungiamo lo stato iniziale di tipo H* nella root, in accordo al template DSC
		String statoIniziale = ("addInitialState(DistilledStateChartTransition.DEEP_HISTORY);\n");
		
		result.append(statoIniziale + statiNonIniziali + defaultDeepHistoryEntrance); //aggiungiamo prima lo stato iniziale e poi gli altri stati
		return result;
	}
	
	//metodo per creare le variabili delle transizioni
	private StringBuffer creaVariabiliTransizioni(){
		StringBuffer result = new StringBuffer();
		for(int i=1; i<=contTransitions; i++){ //NOTA: il valore di "contTransitions" deve essere quello definitivo, ottenuto dopo la creazione di tutti i metodi "createT..."
			result.append("public DistilledStateChartTransition t" + i + ";\n");
		}
		return result;
	}

}
