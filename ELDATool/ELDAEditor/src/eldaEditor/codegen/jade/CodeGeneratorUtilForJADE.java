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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import triggerModel.Trigger;
import triggerModel.TriggerList;
import actionGuardModel.AnElement;
import actionGuardModel.AnElementList;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import dscDiagramModel.impl.DSCStateImpl;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import dscDiagramModel.myDataTypes.TypeVariable;
import function.Function;
import function.FunctionList;

/**
 * Classe di utility per la generazione del codice.
 * 
 * @author G. Fortino, F. Rango
 */
public class CodeGeneratorUtilForJADE {

	// Il DSCDiagram che rappresenta il behaviour dell'agente di cui si sta
	// generando l'implementazione
	private DSCDiagram parent;

	//le mappe che contengono la corretta posizione di dichiarazione
	private HashMap<String, Object> guardsMap = new HashMap<String, Object>();

	private HashMap<String, Object> actionsMap = new HashMap<String, Object>();

	private HashMap<String, String> eventsMap = new HashMap<String, String>();

	private HashMap<String, String> functionsMap = new HashMap<String, String>();

	// La lista che contiene gli elementi definiti nei vari modelli
	// complementari al DSC
	private AnElementList actionsList;

	private AnElementList guardsList;

	private FunctionList functionsList;

	private TriggerList triggerList;

	// Oggetto che serve a manipolare le risorse
	private ResourceSet resourceSet = new ResourceSetImpl();
	
	private ArrayList<String> listaChiamateCreateT; //lista che contiene tutte le chiamate ai metodi "createT..." necessarie per creare le transizioni
	private ArrayList<String> listaChiamateAddTransition; //lista che contiene tutte le chiamate ai metodi "addTransition" necessarie per aggiungere le transizioni

	private IFile fileInput;
	
	/**
	 * 
	 * Inizializza la classe che fornisce le utility
	 * 
	 * @param parent il diagramma sul quale è stata chiamata la generazione
	 * 
	 */
	public CodeGeneratorUtilForJADE(DSCDiagram parent, IFile fileInput){
		this.parent = parent;
		this.fileInput = fileInput;
		
		listaChiamateCreateT = new ArrayList<String>();
		listaChiamateAddTransition = new ArrayList<String>();

		//carico in memoria tutti i modelli
		actionsList = loadActionOrGuardResource(parent.getActionFile());
		guardsList = loadActionOrGuardResource(parent.getGuardFile());
		triggerList = loadEventTypeList(parent.getEventFile());
		functionsList = loadFunctionResource(parent.getFunctionFile());

		//inizializzo le mappe
		guardsMap.clear();
		actionsMap.clear();
		functionsMap.clear();

		//calcolo la posizione di definizione di funzioni, azioni e guardie
		findFAGDeclarationsPosition();
	}

	/**
	 * Costruisce le HashMap che contengono le informazioni sulla posizione
	 * della definizione di Function, Action e Guard
	 */
	private void findFAGDeclarationsPosition() {

		//carico in memoria tutti gli oggetti che sono nel diagramma a qualunque livello
		TreeIterator o = parent.eAllContents();

		//li scorro tutti
		while (o.hasNext()) {
			Object object = o.next();

			//solamente per gli stati
			if (object instanceof DSCStateImpl) {
				DSCState state = (DSCState) object;

				//per tutte le transizioni in uscita
				for (int i = 0; i < state.getSourceRelationships().size(); i++) {
					if (state.getSourceRelationships().get(i) instanceof Transition) {
						Transition transition = (Transition) state.getSourceRelationships().get(i);
						String actionID = transition.getActionID();
						String guardID = transition.getGuardID();
						String eventID = transition.getEventID();
						StringTokenizer bodyTokenizer = null;
						
						if (actionsList != null) {
							
							//inserisco nella mappa le azioni definite nella transition
							smartInsertIntoHashMap(actionsMap, actionID, state);

							//inserisco nella mappa le azioni definite nei body delle action
							bodyTokenizer = new StringTokenizer(getActionOrGuardBody(
									actionsList.getAnElement(), actionID, "").toString(), ";");
							smartInsertIntoHashMap(actionsMap,
									getCalledElements(bodyTokenizer, "<--", "-->"), state);

							//inserisco nella mappa le funzioni definite nei body delle action
							bodyTokenizer = new StringTokenizer(getActionOrGuardBody(
									actionsList.getAnElement(), actionID, "").toString(), ";");
							smartInsertIntoHashMap(functionsMap,
									getCalledElements(bodyTokenizer, "<-f-", "("), state);
						}

						if (guardsList != null) {
							
							//inserisco nella mappa le guard definite a top level
							smartInsertIntoHashMap(guardsMap, guardID, state);

							//inserisco nella mappa le guard definite nei body delle guard
							bodyTokenizer = new StringTokenizer(getActionOrGuardBody(
									guardsList.getAnElement(), guardID, "").toString(), ";");
							smartInsertIntoHashMap(guardsMap,
									getCalledElements(bodyTokenizer, "<--", "-->"), state);

							//inserisco nella mappa le funzioni definite nei body delle guard
							bodyTokenizer = new StringTokenizer(getActionOrGuardBody(
									guardsList.getAnElement(), guardID, "").toString(), ";");
							smartInsertIntoHashMap(functionsMap,
									getCalledElements(bodyTokenizer, "<-f-", "("), state);
						}

						//inserisco nella mappa tutti gli eventi
						if (eventID != null && !eventID.equals(""))
							if (!eventsMap.containsKey(eventID))
								eventsMap.put(eventID, "");
					}
				}

				if (!state.isIsSimple()) {
					
					//gestisco la start transition solamente per gli stati composti
					Transition startTransition = getStartTransition(state);
					String actionIDST = startTransition.getActionID();
					StringTokenizer bodyTokenizerST = null;
					if (actionsList != null) {
						//inserisco nella mappa le azioni definite nella startTransition
						smartInsertIntoHashMap(actionsMap, actionIDST, state);

						//inserisco nella mappa le azioni definite nei body delle action
						bodyTokenizerST = new StringTokenizer(getActionOrGuardBody(
								actionsList.getAnElement(), actionIDST, "").toString(), ";");
						smartInsertIntoHashMap(actionsMap, getCalledElements(
								bodyTokenizerST, "<--", "-->"), state);

						//inserisco nella mappa le funzioni definite nei body delle action
						bodyTokenizerST = new StringTokenizer(getActionOrGuardBody(
								actionsList.getAnElement(), actionIDST, "").toString(), ";");
						smartInsertIntoHashMap(functionsMap, getCalledElements(
								bodyTokenizerST, "<-f-", "("), state);
					}
					
					//gestiamo l'azione associata alla Default Deep/Shallow History Entrance
					if (state.getDeepHistory() != null){
						for (int i=0; i<state.getDeepHistory().getSourceRelationships().size(); i++){
							if(state.getDeepHistory().getSourceRelationships().get(i) instanceof Transition){
								Transition transition = (Transition) state.getDeepHistory().getSourceRelationships().get(i);
								String actionID = transition.getActionID();
								StringTokenizer bodyTokenizer = null;
								if (actionsList!=null){
									//inserisco nella mappa le azioni definite nella transizione
									smartInsertIntoHashMap(actionsMap, actionID, state);

									//inserisco nella mappa le azioni definite nei body delle action
									bodyTokenizer=new StringTokenizer(getActionOrGuardBody(actionsList.getAnElement(), actionID,"").toString(),";");
									smartInsertIntoHashMap(actionsMap, getCalledElements(bodyTokenizer, "<--", "-->"), state);

									//inserisco nella mappa le funzioni definite a nei body delle action
									bodyTokenizer=new StringTokenizer(getActionOrGuardBody(actionsList.getAnElement(), actionID,"").toString(),";");
									smartInsertIntoHashMap(functionsMap, getCalledElements(bodyTokenizer, "<-f-", "("), state);
								}
							}
						}
					}
					if (state.getShallowHistory() != null){
						for (int i=0; i<state.getShallowHistory().getSourceRelationships().size(); i++){
							if(state.getShallowHistory().getSourceRelationships().get(i) instanceof Transition){
								Transition transition = (Transition) state.getShallowHistory().getSourceRelationships().get(i);
								String actionID = transition.getActionID();
								StringTokenizer bodyTokenizer = null;
								if (actionsList!=null){
									//inserisco nella mappa le azioni definite nella transizione
									smartInsertIntoHashMap(actionsMap, actionID, state);

									//inserisco nella mappa le azioni definite nei body delle action
									bodyTokenizer=new StringTokenizer(getActionOrGuardBody(actionsList.getAnElement(), actionID,"").toString(),";");
									smartInsertIntoHashMap(actionsMap, getCalledElements(bodyTokenizer, "<--", "-->"), state);

									//inserisco nella mappa le funzioni definite a nei body delle action
									bodyTokenizer=new StringTokenizer(getActionOrGuardBody(actionsList.getAnElement(), actionID,"").toString(),";");
									smartInsertIntoHashMap(functionsMap, getCalledElements(bodyTokenizer, "<-f-", "("), state);
								}
							}
						}
					}
					
				}

			}
		}
	}
	
	//metodo per creare gli stati
	public StringBuffer creaStati(EList list) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof DSCState) {
				String nameState;
				if (((DSCState) list.get(i)).getName().toUpperCase().equals("")){
					nameState = "DEFAULT_NAME";
				}
				else {
					nameState = ((DSCState) list.get(i)).getName().toUpperCase().replaceAll(" ", "_");
				}

				result.append(nameState.toLowerCase() + " = new " + nameState + "(agent, \"" + nameState + "\");" + "\n"); //es.: dsc1 = new DSC1(anAgent, "DSC1");
				
			}
		}
		return result;
	}
	
	//metodo per creare le variabili degli stati
	public StringBuffer creaVariabiliStati(EList list) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof DSCState) {
				String nameState;
				if (((DSCState) list.get(i)).getName().toUpperCase().equals("")){
					nameState = "DEFAULT_NAME";
				}
				else {
					nameState = ((DSCState) list.get(i)).getName().toUpperCase().replaceAll(" ", "_");
				}

				result.append("public " + nameState + " " + nameState.toLowerCase() + ";" + "\n"); //es.: public DSC1 dsc1;
				
			}
		}
		return result;
	}
	
	//metodo per aggiungere una chiamata "createT..." nell'apposita lista
	public void aggiungiChiamataCreateT(String s){
		listaChiamateCreateT.add(s);
	}
	
	//metodo per aggiungere una chiamata "addTransition" nell'apposita lista
	public void aggiungiChiamataAddTransition(String s){
		listaChiamateAddTransition.add(s);
	}
	
	//metodo per restituire le chiamate ai metodi "createT..."
	public StringBuffer getChiamateCreateT(){
		StringBuffer result = new StringBuffer();
		for(int i=0; i<listaChiamateCreateT.size(); i++){
			result.append(listaChiamateCreateT.get(i));
		}
		return result;
	}
	
	//metodo per restituire le chiamate ai metodi "addTransition"
	public StringBuffer getChiamateAddTransition(){
		StringBuffer result = new StringBuffer();
		for(int i=0; i<listaChiamateAddTransition.size(); i++){
			result.append(listaChiamateAddTransition.get(i));
		}
		return result;
	}

	/**
	 * Crea la definizione delle azioni relative alla root
	 * 
	 * @return
	 */
	public StringBuffer createActionDefinitionIntoRoot() {
		StringBuffer result = new StringBuffer();
		Iterator i = actionsMap.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();
			if (entry.getValue().equals(parent)){
				result.append(replaceFunctionCalled(replaceActionCalled(getActionOrGuardBody(
								actionsList.getAnElement(), entry.getKey(), "void"))));
			}
		}
		return result;
	}

	/**
	 * Crea la definizione delle azioni relative allo stato passato come parametro
	 * 
	 * @return
	 */
	public StringBuffer createActionDefinition(DSCState state) {
		StringBuffer result = new StringBuffer();

		//recupero tutte le definizioni delle azione e le metto in un iteratore
		Iterator i = actionsMap.entrySet().iterator();

		//per ogni elemento della map
		while (i.hasNext()) {
			//estraggo l'entry
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();

			//se l'azione è associata allo stato in esame la dichiaro di seguito allo stato
			if (entry.getValue().equals(state)) {
				result.append(replaceFunctionCalled(replaceActionCalled(getActionOrGuardBody(
								actionsList.getAnElement(), entry.getKey(), "void"))));
			}
		}
		return result;
	}
	
	/**
	 * Crea la definizione delle guardie relative alla root
	 * 
	 * @return
	 */
	public StringBuffer createGuardDefinitionIntoRoot() {
		StringBuffer result = new StringBuffer();
		Iterator i = guardsMap.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();
			if (entry.getValue().equals(parent)){
				result.append(replaceFunctionCalled(replaceGuardCalled(getActionOrGuardBody(
								guardsList.getAnElement(), entry.getKey(), "boolean"))));
			}
		}
		return result;
	}

	/**
	 * Crea la definizione delle guardie relative allo stato passato come parametro
	 * 
	 * @return
	 */
	public StringBuffer createGuardDefinition(DSCState state) {
		StringBuffer result = new StringBuffer();
		Iterator i = guardsMap.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();
			if (entry.getValue().equals(state)){
				result.append(replaceFunctionCalled(replaceGuardCalled(getActionOrGuardBody(
								guardsList.getAnElement(), entry.getKey(), "boolean"))));
			}
		}
		return result;
	}
	
	/**
	 * Crea la definizione delle funzioni relative alla root
	 * 
	 * @return
	 */
	public StringBuffer createFunctionDefinitionIntoRoot() {
		StringBuffer result = new StringBuffer();
		Iterator i = functionsMap.entrySet().iterator();
		while (i.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();
			if (entry.getValue().equals(parent)) {
				result.append(replaceFunctionCalled((getFunctionBody(
						functionsList.getFunction(), entry.getKey()))));
			}
		}
		return result;
	}

	/**
	 * Crea la definizione delle funzioni relative allo stato passato come parametro
	 * 
	 * @return
	 */
	public StringBuffer createFunctionDefinition(DSCState state) {
		StringBuffer result = new StringBuffer();
		Iterator i = functionsMap.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) i.next();
			if (entry.getValue().equals(state)){
				result.append(replaceFunctionCalled(getFunctionBody(
						functionsList.getFunction(), entry.getKey())));
			}
		}
		return result;
	}

	/**
	 * Ritorna il corpo della action o guard passata come parametro
	 * 
	 * @param list
	 *            la lista in cui cercare
	 * @param key
	 *            l'ID dell'action/guard
	 * @param type
	 *            il tipo ritornato
	 * @return il corpo
	 */
	private StringBuffer getActionOrGuardBody(List list, String key, String type) {
		StringBuffer result = new StringBuffer();
		result.append("private " + type + " " + key + " (ELDAEvent e){\n");
		for (int i = 0; i < list.size(); i++){
			if (((AnElement) list.get(i)).getName().equals(key)) {
				if (((AnElement) list.get(i)).getBody() == null)
					result.append("");
				else
					result.append(((AnElement) list.get(i)).getBody());
			}
		}
		result.append("\n}\n");
		return result;
	}

	/**
	 * Ritorna il corpo della funzione passata come parametro
	 */
	private StringBuffer getFunctionBody(List list, String key) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < list.size(); i++){
			if (((Function) list.get(i)).getName().equals(key.trim())) {
				String returnedType = "void";
				if (!((Function) list.get(i)).getReturnedType().equals(""))
					returnedType = ((Function) list.get(i)).getReturnedType();
				result.append("private " + returnedType + " "
						+ ((Function) list.get(i)).getName() + "("
						+ getStringParameters(((Function) list.get(i)))
						+ "){\n");
				if (((Function) list.get(i)).getBody() == null)
					result.append("");
				else
					result.append(((Function) list.get(i)).getBody());
			}
		}
		result.append("\n}\n");
		return result;
	}

	/**
	 * Costruisce una stringa contenente la definizione dei parametri della
	 * funzione passata come parametro
	 * @param function
	 * @return restituisce una stringa contenente la definizione dei parametri
	 */
	private StringBuffer getStringParameters(Function function){
		StringBuffer stringParameters = new StringBuffer();
		Iterator paramIterator = function.getParameter().iterator();
		while (paramIterator.hasNext()){
			TypeVariable variable = (TypeVariable) paramIterator.next();
			stringParameters.append(variable.getType());
			stringParameters.append(" ");
			stringParameters.append(variable.getName());
			if (paramIterator.hasNext()){
				stringParameters.append(", ");
			}
		}
		return stringParameters;
	}
	
	//metodo per calcolare il percorso completo di un certo stato
	//sottoforma di stringa separata da punti (es.: dsc1.dsc2.dsc3.s1)
	public String getPercorsoStato(EObject stato){
		if (parent.equals(stato)){
			//lo stato passato come parametro è la root
			return "";
		}
		String percorso = "";
		if (stato instanceof DSCState) {
			String nameState = "";
			if (((DSCState) stato).getName().toUpperCase().equals("")){
				nameState = "DEFAULT_NAME";
			}
			else {
				nameState = ((DSCState) stato).getName().toUpperCase().replaceAll(" ", "_");
			}
			percorso = nameState.toLowerCase();
		}
		boolean continua = true;
		while (continua){
			if (parent.equals(stato.eContainer())) {
				continua = false;
			}
			else {
				stato = (EObject) stato.eContainer();
				if (stato instanceof DSCState) {	
					String nameState = "";
					if (((DSCState) stato).getName().toUpperCase().equals("")){
						nameState = "DEFAULT_NAME";
					}
					else {
						nameState = ((DSCState) stato).getName().toUpperCase().replaceAll(" ", "_");
					}
					percorso = (nameState.toLowerCase() + "." + percorso);
				}
			}
		}
		return percorso;
	}

	/**
	 * Ritorna l'ancestor tra i due elementi del modello
	 * 
	 * @param state1
	 * @param state2
	 * @return
	 */
	public EObject getAncestor(EObject state1, EObject state2) {
		EObject ancestor;

		int depthState1 = findDepth(parent, state1);
		int depthState2 = findDepth(parent, state2);

		if (depthState1 > depthState2)
			ancestor = calculateAncestor(state1, state2);
		else
			ancestor = calculateAncestor(state2, state1);

		return ancestor;
	}

	/**
	 * Calcola la profondità di state rispetto a rif
	 * 
	 * @param rif
	 * @param state
	 * @return 0 se immediatamente contenuto.<br>
	 *         -1 se sono allo stesso livello
	 * 
	 */
	public int findDepth(EObject rif, EObject state) {
		int depth = 0;
		// se il riferimento e lo stato sono uguali
		if (rif.equals(state))
			return -1;
		boolean cont = true;

		while (cont) {
			if (rif.equals(state.eContainer())) {
				cont = false;
			} else {
				state = (EObject) state.eContainer();
				depth++;
			}
		}
		return depth;
	}

	/**
	 * Ritorna l'ancestor tra state1 e state2, se il primo è più profondo del secondo
	 * 
	 * @param state1
	 * @param state2
	 * @return
	 */
	private EObject calculateAncestor(EObject state1, EObject state2) {
		boolean cont = true;
		while (cont) {
			if (EcoreUtil.isAncestor(state1, state2)) {
				cont = false;
			}
			else {
				if (state1.eContainer() instanceof DSCState) {
					state1 = (DSCState) state1.eContainer();
				}
				else {
					return parent;
				}
			}
		}
		return state1;
	}

	/**
	 * Serve per caricare in memoria le risorse necessarie alla generazione del
	 * codice relativa a guard ed action
	 * 
	 * @param fileName
	 * @return
	 * 
	 * @author samuele
	 */
	private AnElementList loadActionOrGuardResource(String fileName) {
		AnElementList content = null;
		
		IFile diagramFile = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile();
		IProject project = diagramFile.getProject();
		// serve per gestire la posizione relativa del file
		// TODO
		// IPath relativePath=file.getProjectRelativePath();
		IFile resourceFile = project.getFile(fileName);

		URI uri = URI.createPlatformResourceURI(resourceFile.getFullPath().toString());

		Resource resource = null;
		try {
			resource = resourceSet.getResource(uri, true);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			if (e.getCause().getClass().getSimpleName().equals(
					"ResourceException"))
				return null;
		}

		EList l = resource.getContents();
		if (l != null) {
			Iterator i = l.iterator();
			while (i.hasNext()) {
				Object o = i.next();
				if (o instanceof AnElementList)
					content = (AnElementList) o;
			}
		}
		return content;
	}

	public Transition getStartTransition(DSCState state) {
		StartPoint startPoint = null;
		for (int i = 0; i < state.getVisualElements().size(); i++) {
			if (state.getVisualElements().get(i) instanceof StartPoint) {
				startPoint = (StartPoint) state.getVisualElements().get(i);
				break;
			}
		}

		for (int i = 0; i < startPoint.getSourceRelationships().size(); i++) {
			if (startPoint.getSourceRelationships().get(i) instanceof Transition) {
				return (Transition) startPoint.getSourceRelationships().get(i);
			}
		}
		return null;
	}

	private TriggerList loadEventTypeList(String eventFile) {
		IFile diagramFile = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile();

		IProject project = diagramFile.getProject();

		IFile resourceFile = project.getFile(eventFile);

		URI uri = URI.createPlatformResourceURI(resourceFile.getFullPath()
				.toString());

		Resource eventResource = null;
		try {
			eventResource = resourceSet.getResource(uri, true);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			if (e.getCause().getClass().getSimpleName().equals(
					"ResourceException"))
				return null;

		}

		return (TriggerList) eventResource.getContents().get(0);
	}

	private FunctionList loadFunctionResource(String functionFile) {

		IFile diagramFile = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile();

		IProject project = diagramFile.getProject();

		IFile resourceFile = project.getFile(functionFile);

		URI uri = URI.createPlatformResourceURI(resourceFile.getFullPath().toString());

		Resource functionResource = null;
		try {
			functionResource = resourceSet.getResource(uri, true);
		}
		catch (RuntimeException e) {
			// TODO Auto-generated catch block
			if (e.getCause().getClass().getSimpleName().equals(
					"ResourceException"))
				return null;
		}

		return (FunctionList) functionResource.getContents().get(0);
	}

	public void generateFileWithThisCode(String name, String code, IJavaElement folder) {
		IPackageFragment ipf = (IPackageFragment) folder;

		//System.out.println(getClass().getName() + ":  folder = " + folder + " ; ipf = " + ipf);

		try {
			ipf.createCompilationUnit(name, code, true, new NullProgressMonitor()); //se il file da creare esiste già, viene sostituito
		}
		catch (JavaModelException e) {
			e.printStackTrace();
		}
	}

	public List getUsedEventIDList() {
		eventsMap.entrySet().toArray();
		Iterator i = eventsMap.entrySet().iterator();
		List<String> list = new ArrayList();
		while (i.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
			list.add(entry.getKey());
		}
		return list;
	}

	public TriggerList getEventTypeList() {
		return triggerList;
	}

	/**
	 * Ritorna l'elemento del modello con il nome passato come parametro
	 * 
	 * @param eventTypeName
	 * @return
	 */
	public Trigger getEventType(String eventTypeName) {
		for (int i = 0; i < triggerList.eContents().size(); i++) {
			if (((Trigger) (triggerList.eContents().get(i))).getTriggerName().equals(eventTypeName)) {
				return ((Trigger) (triggerList.eContents().get(i)));
			}
		}
		return null;
	}

	private List<String> getCalledElements(StringTokenizer bodyTokenizer,
			String startDel, String endDel) {
		List<String> result = new ArrayList<String>();
		// StringTokenizer bodyTokenizer=new StringTokenizer(getGoraBody(list,
		// actionId,"").toString(),";");

		while (bodyTokenizer.hasMoreTokens()) {
			String body = bodyTokenizer.nextToken();
			int start = body.indexOf(startDel);
			int end = body.indexOf(endDel, start);
			if (start != -1 && end != -1)
				result.add(body.substring(start + startDel.length(), end));
		}
		return result;
	}

	private void smartInsertIntoHashMap(HashMap map, String actionID, DSCState state) {
		if (actionID != null && !actionID.equals("")) {
			if (!map.containsKey(actionID)) {
				map.put(actionID, state);
			}
			else {
				Object ancestor = getAncestor((DSCState) map.get(actionID), state);
				map.put(actionID, ancestor);
			}
		}
	}

	private void smartInsertIntoHashMap(HashMap map, List actionInnerList, DSCState state) {
		Iterator<String> i = actionInnerList.iterator();
		while (i.hasNext()) {
			String actionID = i.next().trim();
			if (actionID != null && !actionID.equals("")) {
				if (!map.containsKey(actionID)) {
					map.put(actionID, state);
				}
				else {
					Object ancestor = getAncestor((DSCState) map.get(actionID), state);
					map.put(actionID, ancestor);
				}
			}
		}
	}

	private StringBuffer replaceActionCalled(StringBuffer result) {
		String io = result.toString();
		io = io.replaceAll("<--", "");
		io = io.replaceAll("-->", "(e)");
		return new StringBuffer(io);
	}

	private StringBuffer replaceGuardCalled(StringBuffer result) {
		String io = result.toString();
		io = io.replaceAll("<--", "");
		io = io.replaceAll("-->", "(e)");
		return new StringBuffer(io);
	}

	private StringBuffer replaceFunctionCalled(StringBuffer result) {
		String io = result.toString();
		io = io.replaceAll("<-f-", "");
		io = io.replaceAll("-f->", "");
		return new StringBuffer(io);
	}
	
	public void generateJADEImports(StringBuffer result){
		//genera gli import per le classi di JADE e gli eventi relativi alla multi-coordination
		result.append("import jade.core.*;\n");
		result.append("import jade.core.behaviours.*;\n");
		result.append("import jade.lang.acl.*;\n");
		result.append("import jade.content.*;\n");
		result.append("import jade.content.lang.*;\n");
		result.append("import jade.content.lang.sl.*;\n");
		result.append("import jade.content.onto.*;\n");
		result.append("import jade.content.onto.basic.*;\n");
		result.append("import jade.domain.*;\n");
		result.append("import jade.domain.FIPAAgentManagement.*;\n");
		result.append("import jade.domain.JADEAgentManagement.*;\n");
		result.append("import jade.domain.mobility.*;\n");
		result.append("import jade.wrapper.*;\n");		
		result.append("import java.io.*;\n");
		result.append("import java.util.*;\n");
		result.append("import elda_multicoordination.handler.*;\n");
		result.append("import elda_multicoordination.eldaevent.*;\n");
		result.append("import elda_multicoordination.eldaevent.internal.*;\n");
		result.append("import elda_multicoordination.eldaevent.coordination.*;\n");
		result.append("import elda_multicoordination.eldaevent.coordination.direct.*;\n");
		result.append("import elda_multicoordination.eldaevent.coordination.tuples.*;\n");
		result.append("import elda_multicoordination.eldaevent.coordination.p_s.*;\n");
		result.append("import elda_multicoordination.eldaevent.management.*;\n");
		result.append("import elda_multicoordination.eldaevent.management.lifecycle.*;\n");
		result.append("import elda_multicoordination.eldaevent.management.timer.*;\n");
	}
	
	public void generateImportsFromClasspath(StringBuffer buffer) {
		IJavaElement jElement = JavaCore.create((IResource) fileInput.getProject());
		IJavaProject newJProject = jElement.getJavaProject();
		try {
			// per tutte le entry del classpath
			for (int i = 0; i < newJProject.getRawClasspath().length; i++) {
				// per la entry di tipo source
				if (newJProject.getRawClasspath()[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					IFolder folder = fileInput.getProject().getFolder(
							newJProject.getRawClasspath()[i].getPath()
									.removeFirstSegments(1));
					try {
						for (int j = 0; j < folder.members().length; j++) {
							String currentImport = "import " + folder.members()[j]
													.getProjectRelativePath()
													.removeFirstSegments(1).toString()
													+ ".*;\n";
							if(buffer.toString().contains(currentImport) == false){
								buffer.append(currentImport);
							}
						}
					}
					catch (CoreException e) {
						e.printStackTrace();
					}
				}
				if (newJProject.getRawClasspath()[i].getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
					IPackageFragmentRoot root = newJProject
							.getPackageFragmentRoot(newJProject
									.getRawClasspath()[i].toString());
					// root.open(null);
					// root.getChildren();
					// SelectionDialog dialog=JavaUI.createPackageDialog(new
					// Shell(), root);
					// dialog.open();

				}
			}

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void generateImportsFromVariables(StringBuffer imports) {
		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			String type = ((TypeDiagramVariable) parent.getDiagramVariables().get(i)).getType();
			generateImportForType(type, imports);
		}

		TreeIterator o = parent.eAllContents();
		while (o.hasNext()) {
			Object object = o.next();
			if (object instanceof DSCStateImpl) {
				DSCState state = (DSCState) object;
				for (int i = 0; i < state.getVariables().size(); i++) {
					String type = ((TypeVariable) state.getVariables().get(i)).getType();
					generateImportForType(type, imports);
				}
			}
		}
		
		List<String> triggeringEventsList = getUsedEventIDList();
		for (int i = 0; i < triggeringEventsList.size(); i++) {
			String eventTypeName = triggeringEventsList.get(i);
			Trigger eventType = getEventType(eventTypeName);
			for(int j = 0; j < eventType.getParameters().size(); j++) {
				TypeVariable parameter = (TypeVariable) eventType.getParameters().get(j);
				String type = parameter.getType();
				generateImportForType(type, imports);
			}
		}
	}
	
	private void generateImportForType(String type, StringBuffer imports) {
		if (type.contains(".")) {
			type = type.replace('[', ' ');
			type = type.replace(']', ' ');
			
			if(type.contains("<")){
				type = type.substring(0, type.indexOf("<"));
			}
			
			String currentImport = "import " + type + ";\n";
			if(imports.toString().contains(currentImport) == false){
				imports.append(currentImport);
			}
		}
	}
	
	/** This method translates the primitive types to the corresponding object.
	 * @param type the type.
	 * @return the corresponding object.
	 */
	public String convertPrimitiveTypeToObject(String type) {
		//metodo che trasforma i tipi primitivi
		//nel corrispondente oggetto (es.: "int" in "Integer")
		
		if(type.equals("byte")){
			return "Byte";
		}
		else if(type.equals("short")){
			return "Short";
		}
		else if(type.equals("int")){
			return "Integer";
		}
		else if(type.equals("long")){
			return "Long";
		}
		else if(type.equals("float")){
			return "Float";
		}
		else if(type.equals("double")){
			return "Double";
		}
		else if(type.equals("boolean")){
			return "Boolean";
		}
		else if(type.equals("char")){
			return "Character";
		}
		return type; //se il tipo non è stato tradotto, restituiamo il tipo stesso
	}

}
