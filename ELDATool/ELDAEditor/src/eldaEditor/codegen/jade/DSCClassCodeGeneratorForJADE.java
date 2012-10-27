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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.Transition;
import dscDiagramModel.myDataTypes.TypeVariable;

/**
 * Genera il codice relativo ad uno stato
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCClassCodeGeneratorForJADE {

	private DSCState state;
	private String nameUpperCase;
	private CodeGeneratorUtilForJADE util;
	private DSCDiagram parent;
	private String agentName;

	public DSCClassCodeGeneratorForJADE(DSCState state, DSCDiagram parent, CodeGeneratorUtilForJADE util, String agentName) {
		this.state = state;
		this.parent = parent;
		this.util = util;
		this.agentName = agentName;
		if (state.getName().toUpperCase().equals(""))
			this.nameUpperCase = "DEFAULT_NAME";
		else
			this.nameUpperCase = state.getName().toUpperCase().replaceAll(" ", "_");
	}

	public StringBuffer run(){
		StringBuffer stateCode = new StringBuffer();
		
		//creiamo l'intestazione della classe
		stateCode.append(createTopIntestation());
		
		//creiamo le variabili
		stateCode.append(createVariableDefinition());
		
		if (state.isIsSimple() == false){ //stato composto
			//dichiariamo i sotto-stati tramite delle apposite variabili
			stateCode.append(util.creaVariabiliStati(state.getVisualElements()));
		}
		
		//creiamo il costruttore
		stateCode.append(createConstructor());

		if (state.isIsSimple() == true){ //stato semplice
			//inseriamo i metodi necessari "action" e "done" nel Behaviour semplice
			stateCode.append("public void action(){}\n");
			stateCode.append("public boolean done(){return true;}\n"); //il Behaviour semplice termina subito, perchè non deve fare nulla nel suo metodo "action"
		}
		
		if (state.isIsSimple() == false){ //stato composto
			
			//creiamo l'eventuale initial action
			stateCode.append(createInitialAction());
			
			//creiamo l'eventuale azione associata alla Default Deep History Entrance
			stateCode.append(createDefaultDeepHistoryEntranceAction());
			
			//creiamo l'eventuale azione associata alla Default Shallow History Entrance
			stateCode.append(createDefaultShallowHistoryEntranceAction());
		}
		
		//creiamo i metodi "createT..." utilizzati per creare le transizioni
		stateCode.append(creaMetodiCreateT());

		//creiamo i metodi relativi alle azioni, alle guardie ed alle funzioni utilizzate per lo stato corrente
		stateCode.append(util.createActionDefinition(state));
		stateCode.append(util.createGuardDefinition(state));
		stateCode.append(util.createFunctionDefinition(state));
		
		if (state.isIsSimple() == false){ //stato composto
			
			//creiamo le inner class dei sotto-stati
			stateCode.append(createInnerClass());
		}

		//chiudiamo la classe di questo stato
		stateCode.append("}\n");

		return stateCode;
	}

	/**
	 * Crea l'intestazione della classe dello stato
	 * 
	 * @return
	 */
	private StringBuffer createTopIntestation() {
		StringBuffer topIntestationBuffer = new StringBuffer ();
		topIntestationBuffer.append("private class " + nameUpperCase + " extends ");
		if (state.isIsSimple()){
			topIntestationBuffer.append("Behaviour"); //stato semplice
		}
		else{
			topIntestationBuffer.append("DistilledStateChartBehaviour"); //stato composto
		}
		topIntestationBuffer.append(" {\n");
		topIntestationBuffer.append("private " + agentName + " myAgent;\n");
		return topIntestationBuffer;
	}

	/**
	 * Crea le definizioni delle variabili dello stato
	 * 
	 * @return
	 */
	private StringBuffer createVariableDefinition() {
		StringBuffer variableBuffer = new StringBuffer ();
		for (int i = 0; i < state.getVariables().size(); i++){
			TypeVariable var = (TypeVariable) state.getVariables().get(i);
			int index = var.getType().lastIndexOf(".");
			if (index != -1)
				variableBuffer.append(var.getType().substring(index+1));
			else
				variableBuffer.append(var.getType());
			variableBuffer.append(" " +var.getName());
			if (!var.getValue().equals(""))
				variableBuffer.append(" = " +var.getValue());
			variableBuffer.append(";\n");
		}
		return variableBuffer;
	}

	/**
	 * Crea il costruttore dello stato
	 * 
	 * @return
	 */
	private StringBuffer createConstructor() {
		StringBuffer constructor = new StringBuffer ();
		constructor.append("public " + nameUpperCase + "(" + agentName + " agent, String name){\n");

		if (state.isIsSimple()){ //stato semplice
			
			//inseriamo le operazioni di inizializzazione dello stato semplice
			constructor.append("super(agent);\n");
			constructor.append("this.myAgent = agent;\n");
			constructor.append("setBehaviourName(name);\n");
		}
		else{ //stato composto
			
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
			constructor.append("super(agent, name, MessageTemplate.MatchLanguage(ELDAEvent.IN), false);\n");
		
			constructor.append("this.myAgent = agent;\n");
			
			//creiamo i sotto-stati
			constructor.append(util.creaStati(state.getVisualElements()));
			
			//aggiungiamo i sotto-stati
			constructor.append(aggiungiStati());

			//settiamo le eventuali Default Deep/Shallow History Entrance
			constructor.append(aggiungiDefaultHistoryEntrance());
		}

		//chiudiamo il costruttore
		constructor.append("}\n");

		return constructor;
	}

	/**
	 * Crea il codice relativo alle inner class, cioè ai sotto-stati dello stato corrente
	 * 
	 * @return
	 */
	private StringBuffer createInnerClass() {
		StringBuffer codeInnerClass = new StringBuffer();
		for (int i=0; i<state.getVisualElements().size(); i++){
			if (state.getVisualElements().get(i) instanceof DSCState){
				DSCClassCodeGeneratorForJADE stateCodeGenerator = new DSCClassCodeGeneratorForJADE((DSCState) state.getVisualElements().get(i), parent, util, agentName);
				codeInnerClass.append(stateCodeGenerator.run());
			}
		}
		return codeInnerClass;
	}
	
	//metodo per creare l'initial action, in caso di stato composto
	private StringBuffer createInitialAction() {
		StringBuffer stringBuffer = new StringBuffer();
		Transition startTransition = util.getStartTransition(state);
		if (startTransition.getActionID() != null && !startTransition.getActionID().equals("")){
			stringBuffer.append("public void initialAction(){\n");
			stringBuffer.append(startTransition.getActionID() + "(null);\n"); //chiamiamo il metodo dell'azione associata, passandogli un evento di tipo NULL (perchè l'initial action non deve lavorare su un evento)
			stringBuffer.append("}\n");
		}
		return stringBuffer;
	}
	
	//metodo per creare l'azione associata alla Default Deep History Entrance, in caso di stato composto
	private StringBuffer createDefaultDeepHistoryEntranceAction() {
		StringBuffer stringBuffer = new StringBuffer();
		if (state.getDeepHistory() != null){
			for (int i=0; i<state.getDeepHistory().getSourceRelationships().size(); i++){
				if(state.getDeepHistory().getSourceRelationships().get(i) instanceof Transition){
					Transition transition = (Transition) state.getDeepHistory().getSourceRelationships().get(i);
					if (transition.getActionID() != null && !transition.getActionID().equals("")){
						stringBuffer.append("public void defaultDeepHistoryEntranceAction(){\n");
						stringBuffer.append(transition.getActionID() + "(null);\n");
						stringBuffer.append("}\n");
					}
				}
			}
		}
		return stringBuffer;	
	}
	
	//metodo per creare l'azione associata alla Default Shallow History Entrance, in caso di stato composto
	private StringBuffer createDefaultShallowHistoryEntranceAction() {
		StringBuffer stringBuffer = new StringBuffer();
		if (state.getShallowHistory() != null){
			for (int i=0; i<state.getShallowHistory().getSourceRelationships().size(); i++){
				if(state.getShallowHistory().getSourceRelationships().get(i) instanceof Transition){
					Transition transition = (Transition) state.getShallowHistory().getSourceRelationships().get(i);
					if (transition.getActionID() != null && !transition.getActionID().equals("")){
						stringBuffer.append("public void defaultShallowHistoryEntranceAction(){\n");
						stringBuffer.append(transition.getActionID() + "(null);\n");
						stringBuffer.append("}\n");
					}
				}
			}
		}
		return stringBuffer;	
	}
	
	//metodo per aggiungere i sotto-stati, in caso di stato composto
	private StringBuffer aggiungiStati() {
		EList list = state.getVisualElements();
		StringBuffer result = new StringBuffer();
		Transition startTransition = util.getStartTransition(state);
		String nomeStatoIniziale = "";
		String statoIniziale = "";
		String statiNonIniziali = "";
		
		boolean statoInizialeHistory = false;
		if(startTransition.getTarget() instanceof DeepHistory){
			//stato iniziale di tipo H*
			statoInizialeHistory = true;
			statoIniziale += ("addInitialState(DistilledStateChartTransition.DEEP_HISTORY);\n");
		}
		else if(startTransition.getTarget() instanceof ShallowHistory){
			//stato iniziale di tipo H
			statoInizialeHistory = true;
			statoIniziale += ("addInitialState(DistilledStateChartTransition.SHALLOW_HISTORY);\n");
		}
		
		if(statoInizialeHistory == false){
			nomeStatoIniziale = startTransition.getTarget().getName().toUpperCase().replaceAll(" ", "_");
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof DSCState) {
				String nameState;
				if (((DSCState) list.get(i)).getName().toUpperCase().equals("")){
					nameState = "DEFAULT_NAME";
				}
				else {
					nameState = ((DSCState) list.get(i)).getName().toUpperCase().replaceAll(" ", "_");
				}

				if(statoInizialeHistory == false && nomeStatoIniziale.equals(nameState)){
					//stato iniziale di tipo normale (diverso da H o H*)
					statoIniziale += ("addInitialState(" + nameState.toLowerCase() +");\n"); //es.: addInitialState(dsc1);
				}
				else{
					//stato NON iniziale
					statiNonIniziali += ("addState(" + nameState.toLowerCase() +");\n"); //es.: addState(dsc1);
				}
			}
		}
		
		result.append(statoIniziale + statiNonIniziali); //aggiungiamo prima lo stato iniziale e poi gli altri stati
		return result;
	}

	//metodo per settare le eventuali Default Deep/Shallow History Entrance, in caso di stato composto
	private StringBuffer aggiungiDefaultHistoryEntrance() {
		StringBuffer stringBuffer = new StringBuffer();
		if (state.isIsSimple() == false){
			if (state.getDeepHistory() != null){
				for (int i=0; i<state.getDeepHistory().getSourceRelationships().size(); i++){
					if(state.getDeepHistory().getSourceRelationships().get(i) instanceof Transition){
						Transition transition = (Transition) state.getDeepHistory().getSourceRelationships().get(i);
						String nameState = "";
						if (transition.getTarget().getName().toUpperCase().equals("")){
							nameState = "DEFAULT_NAME";
						}
						else {
							nameState = transition.getTarget().getName().toUpperCase().replaceAll(" ", "_");
						}
						stringBuffer.append("setDefaultDeepHistoryEntrance(" + nameState.toLowerCase() +");\n"); //es.: setDefaultDeepHistoryEntrance(dsc1);
					}
				}
			}

			if (state.getShallowHistory() != null){
				for (int i=0; i<state.getShallowHistory().getSourceRelationships().size(); i++){
					if(state.getShallowHistory().getSourceRelationships().get(i) instanceof Transition){
						Transition transition = (Transition) state.getShallowHistory().getSourceRelationships().get(i);
						String nameState = "";
						if (transition.getTarget().getName().toUpperCase().equals("")){
							nameState = "DEFAULT_NAME";
						}
						else {
							nameState = transition.getTarget().getName().toUpperCase().replaceAll(" ", "_");
						}
						stringBuffer.append("setDefaultShallowHistoryEntrance(" + nameState.toLowerCase() +");\n"); //es.: setDefaultShallowHistoryEntrance(dsc1);
					}
				}
			}
		}

		return stringBuffer;
	}
	
	//metodo per creare i metodi "createT...", utilizzati per creare le transizioni
	private StringBuffer creaMetodiCreateT(){
		EList outcomingRelationships = state.getSourceRelationships();
		StringBuffer stringBuffer = new StringBuffer();

		//creiamo un metodo "createT..." per ciascuna transizione in uscita dallo stato corrente
		for (int i = 0; i < outcomingRelationships.size(); i++) {
			if (outcomingRelationships.get(i) instanceof Transition) {
				Transition trans = (Transition) outcomingRelationships.get(i);
				EObject target = (EObject) trans.getTarget();
				
				ADSCClassCodeGeneratorForJADE.contTransitions++; //incrementiamo il contatore totale delle transizioni
				stringBuffer.append("public void createT" + ADSCClassCodeGeneratorForJADE.contTransitions + "(){\n"); //creiamo il metodo "createT..." per la transizione corrente
				
				//creiamo la classe della transizione
				if((target instanceof DeepHistory) == false && (target instanceof ShallowHistory) == false){
					//il target della transizione corrente è uno stato normale (diverso da H o H*)
					stringBuffer.append("t" + ADSCClassCodeGeneratorForJADE.contTransitions + " = new DistilledStateChartTransition(\"T" + ADSCClassCodeGeneratorForJADE.contTransitions + "\", " + util.getPercorsoStato(target) + "){\n");
				}
				else if(target instanceof DeepHistory){
					//il target della transizione corrente è un connettore H*
					EObject statoContenenteH = (EObject) target.eContainer(); //preleviamo lo stato che contiene il connettore H*
					stringBuffer.append("t" + ADSCClassCodeGeneratorForJADE.contTransitions + " = new DistilledStateChartTransition(\"T" + ADSCClassCodeGeneratorForJADE.contTransitions + "\", " + util.getPercorsoStato(statoContenenteH) + ", DistilledStateChartTransition.DEEP_HISTORY){\n");
				}
				else if(target instanceof ShallowHistory){
					//il target della transizione corrente è un connettore H
					EObject statoContenenteH = (EObject) target.eContainer(); //preleviamo lo stato che contiene il connettore H
					stringBuffer.append("t" + ADSCClassCodeGeneratorForJADE.contTransitions + " = new DistilledStateChartTransition(\"T" + ADSCClassCodeGeneratorForJADE.contTransitions + "\", " + util.getPercorsoStato(statoContenenteH) + ", DistilledStateChartTransition.SHALLOW_HISTORY){\n");
				}

				stringBuffer.append("public boolean trigger(Behaviour sourceState, ACLMessage event){\n"); //apriamo il metodo "trigger"
				stringBuffer.append("java.io.Serializable content = null;\n");
				stringBuffer.append("try {content = event.getContentObject();}\n"); //estraiamo l'ELDAEvent dall'ACLMessage corrente tramite il metodo "getContentObject"
				stringBuffer.append("catch (Exception e){e.printStackTrace();}\n");
				stringBuffer.append("if ((content != null) && (content instanceof " + trans.getEventID() + ")){\n"); //controlliamo il tipo di evento
				if (trans.getGuardID() != null && !trans.getGuardID().equals("")){
					stringBuffer.append("return " + trans.getGuardID() + "((" + trans.getEventID() + ") content);\n"); //se l'evento ricevuto è del tipo indicato sulla transizione ed è stata definita una guardia, chiamiamo il metodo relativo alla guardia
				}
				else{
					stringBuffer.append("return true;\n"); //se l'evento ricevuto è del tipo indicato sulla transizione e NON è stata definita una guardia, il metodo "trigger" restituisce TRUE
				}
				stringBuffer.append("}\n");
				stringBuffer.append("return false;\n"); //se l'evento ricevuto NON è del tipo voluto, il metodo "trigger" restituisce FALSE
				stringBuffer.append("}\n"); //chiudiamo il metodo "trigger"

				stringBuffer.append("public void action(ACLMessage event){\n"); //apriamo il metodo "action"
				if (trans.getActionID() != null && !trans.getActionID().equals("")){
					stringBuffer.append("try {" + trans.getActionID() + "((" + trans.getEventID() + ") event.getContentObject());}\n"); //se è stato definito, chiamiamo il metodo dell'azione associata
					stringBuffer.append("catch (Exception e){e.printStackTrace();}\n");
				}
				stringBuffer.append("}\n"); //chiudiamo il metodo "action"

				stringBuffer.append("};\n"); //chiudiamo la classe della transizione
				stringBuffer.append("}\n"); //chiudiamo il metodo "createT..."
				
				//creiamo la chiamata al metodo "createT..." da utilizzare nella root
				util.aggiungiChiamataCreateT(util.getPercorsoStato(this.state) + ".createT" + ADSCClassCodeGeneratorForJADE.contTransitions + "();\n");
				
				//creiamo la chiamata al metodo "addTransition" da utilizzare nella root
				util.aggiungiChiamataAddTransition("addTransition(t" + ADSCClassCodeGeneratorForJADE.contTransitions + ", " + util.getPercorsoStato(this.state) + ");\n");
			}
		}

		return stringBuffer;
	}
	
}
