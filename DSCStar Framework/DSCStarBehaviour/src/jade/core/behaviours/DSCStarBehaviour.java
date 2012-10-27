/*****************************************************************
"DSCStarBehaviour" is a work based on the library "HSMBehaviour"
(authors: G. Caire, R. Delucchi, M. Griss, R. Kessler, B. Remick).
Changed files: "HSMBehaviour.java", "HSMEvent.java", "HSMPerformativeTransition.java",
"HSMTemplateTransition.java", "HSMTransition.java".
Last change date: 18/06/2010
Copyright (C) 2010 G. Fortino, F. Rango

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

package jade.core.behaviours;

import java.io.Serializable;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.*;

/**
 * DSCStarBehaviour: a behaviour that represents a DSC*.
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCStarBehaviour extends CompositeBehaviour {
	
	private java.util.LinkedList<DSCStarStarTransition> executedStarTransition = new java.util.LinkedList<DSCStarStarTransition>(); //lista delle transizioni di tipo "*" eseguite (viene memorizzata nella root)
	
	private java.util.LinkedList<VirtualInstance> instancesList; //Questa lista permette di simulare le varie istanze di questo stato composto,
																//memorizzando ordinatamente degli oggetti di tipo "VirtualInstance" che rappresentano
																//ognuno un'istanza virtuale di questo stato composto. L'ultima istanza virtuale viene
																//aggiunta sempre in coda all'interno della lista ed è la prima ad essere prelevata.

	private boolean active; //booleano che indica se lo stato composto è attualmente "attivo" (TRUE = attivo; FALSE = disattivo),
							//cioè se siamo già entrati nello stato composto e stiamo eseguendo la procedura rappresentata da questo stato composto
							//(nei DSC*, l'entrata in uno stato composto può avvenire soltanto in  due modi:
							//o tramite una transizione di tipo "*" o, se è uno stato iniziale, automaticamente)
	
	private DSCStarVariablesAndParameters dscStarVariablesAndParameters; //oggetto che contiene le variabili ed i parametri di questo stato composto
	
	private Behaviour defaultShallowHistoryEntrance = null; //Default Shallow History Entrance di questo DSCStarBehaviour, utilizzata quando entriamo in questo DSCStarBehaviour con storia shallow e questo DSCStarBehaviour non è stato mai visitato in precedenza
	
	private Behaviour defaultDeepHistoryEntrance = null; //Default Deep History Entrance di questo DSCStarBehaviour, utilizzata quando entriamo in questo DSCStarBehaviour con storia deep e questo DSCStarBehaviour non è stato mai visitato in precedenza
	
	private Map states = new HashMap(); //maps between actual behaviour and wrapped behaviour.

	private List finalStates = new ArrayList(); //stati finali di questo DSCStarBehaviour

	private Map transitions = new HashMap(); //mapping from state to list of transitions.

	private Behaviour initialStateBehaviour = null; //stato iniziale di tipo normale (cioè, diverso da H o H*) di questo DSCStarBehaviour

	private int initialStateHistory = DistilledStateChartTransition.NO_HISTORY; //stato iniziale di tipo H o H* di questo DSCStarBehaviour
	
	private int lastExitValue;

	private boolean putbackMessage = true; //utilizzato per la politica di gestione dei messaggi

	private MessageTemplate mainTemplate = null; //template tramite cui vengono ricevuti i messaggi nel DSC* (utilizzato per la politica di gestione dei messaggi)

	/** This constructor sets the owner agent, name, MessageTemplate and the putback mode.
	 * @param anAgent the agent this behaviour belongs to.
	 * @param aName the name of the behaviour.
	 * @param aTemplate the main template
	 * @param aPutback the putback mode
	 * @param dscStarVariablesAndParameters object that contains variables and parameters of this composite state
	 */
	public DSCStarBehaviour(Agent anAgent, String aName, MessageTemplate aTemplate, boolean aPutback, DSCStarVariablesAndParameters dscStarVariablesAndParameters) {
		super(anAgent);
		setBehaviourName(aName);
		setMainTemplate(aTemplate);
		setPutback(aPutback);
		this.instancesList = new java.util.LinkedList<VirtualInstance>();
		this.dscStarVariablesAndParameters = dscStarVariablesAndParameters;
		
		//aggiungiamo in coda i dati della prima istanza virtuale dello stato composto
		createNewInstance();
		
		this.active = false; //il booleano che indica se lo stato composto è attivo/disattivo viene inizialmente settato a FALSE (= DISATTIVO)
	}

	/** Create new virtual instance of this composite state.
	 */
	private void createNewInstance(){
		//questo metodo permette di creare una nuova istanza virtuale per questo stato composto
		VirtualInstance instance = new VirtualInstance();
		this.instancesList.addLast(instance); //aggiungiamo in coda la nuova istanza dello stato composto
	
		//System.out.println("CREATA UNA NUOVA ISTANZA PER LO STATO " + getBehaviourName());
		//System.out.println("NUM. ISTANZE DELLO STATO " + getBehaviourName() + " = " + this.instancesList.size());
		
	}
	
	/** Remove last virtual instance of this composite state.
	 */
	private void removeLastInstance(){
		//questo metodo permette di eliminare l'ultima istanza virtuale di questo stato composto
		this.instancesList.removeLast();
	
		//System.out.println("ELIMINATA L'ULTIMA ISTANZA DELLO STATO " + getBehaviourName());
		//System.out.println("NUM. ISTANZE DELLO STATO " + getBehaviourName() + " = " + this.instancesList.size());
		
	}
	
	/** Get size of instances list of this composite state.
	 */
	private int getInstancesListSize(){
		return this.instancesList.size();
	}

	/** Get the current state of last virtual instance of this composite state.
	 * @return The behaviour that is current.
	 * @see jade.core.behaviours.CompositeBehaviour#getCurrent
	 */
	protected Behaviour getCurrent() {
		return this.instancesList.getLast().getCurrentState(); //restituiamo lo stato corrente dell'ultima istanza virtuale dello stato composto
	}
	
	/** Set the current state of last virtual instance of this composite state.
	 * @param state The state to set the current to.
	 */
	protected void setCurrent(Behaviour state) {
		//aggiorniamo lo stato corrente dell'ultima istanza virtuale dello stato composto
		if(state != null){
			VirtualInstance instance = this.instancesList.getLast(); //preleviamo l'ultima istanza virtuale dello stato composto
			instance.setCurrentState((Behaviour) states.get(state)); //settiamo il nuovo stato corrente
			
			//System.out.println("STATO CORRENTE DELL'ULTIMA ISTANZA DELLO STATO " + getBehaviourName() + " = " + state.getBehaviourName());
			
		}
		else{
			VirtualInstance instance = this.instancesList.getLast(); //preleviamo l'ultima istanza virtuale dello stato composto
			instance.setCurrentState(null); //settiamo il nuovo stato corrente
			
			//System.out.println("STATO CORRENTE DELL'ULTIMA ISTANZA DELLO STATO " + getBehaviourName() + " = NULL");
			
		}
	}

	/** Get last sub-state visited by last virtual instance of this composite state.
	 * @return Behaviour that represents the state.
	 */
	private Behaviour getLastState() {
		return this.instancesList.getLast().getLastVisitedSubState(); //restituiamo l'ultimo sotto-stato visitato dall'ultima istanza virtuale dello stato composto
	}

	/** Set last sub-state visited by last virtual instance of this composite state.
	 * @param lastState Behaviour that represents the state.
	 */
	private void setLastState(Behaviour lastState) {
		//aggiorniamo l'ultimo sotto-stato visitato dall'ultima istanza virtuale dello stato composto
		VirtualInstance instance = this.instancesList.getLast(); //preleviamo l'ultima istanza virtuale dello stato composto
		instance.setLastVisitedSubState(lastState); //settiamo il nuovo ultimo sotto-stato visitato
		
		//System.out.println("ULTIMO SOTTO-STATO VISITATO DALL'ULTIMA ISTANZA DELLO STATO " + getBehaviourName() + " = " + lastState.getBehaviourName());
		
	}

	/** Get the object that contains variables and parameters of last virtual instance of this composite state.
	 * @return the object that contains variables and parameters of last virtual instance of this composite state.
	 */
	private DSCStarVariablesAndParameters getActualVariablesAndParameters() {
		if(rootP()){
			//se lo stato composto è la root, restituiamo il suo unico oggetto variabili,
			//che non va clonato
			return dscStarVariablesAndParameters;
		}
		
		//se lo stato composto non è una root, restituiamo l'oggetto che contiene
		//le variabili ed i parametri dell'ultima istanza virtuale di questo stato composto
		return this.instancesList.getLast().getVariablesAndParameters();
	}

	/** Get a boolean that indicates if this composite state is active or not.
	 * @return true = active; false = not active.
	 */
	private boolean isActive() {
		return this.active; //restituiamo il booleano che indica se lo stato composto è attivo/disattivo
	}

	/** Set a boolean that indicates if this composite state is active or not.
	 * @param b true = active; false = not active.
	 */
	private void setActive(boolean b) {
		//aggiorniamo il booleano che indica se lo stato composto è attivo/disattivo
		this.active = b;
		
		/*if(b == true){
			System.out.println("LO STATO COMPOSTO " + getBehaviourName() + " E' ATTIVO");
		}	
		else{
			System.out.println("LO STATO COMPOSTO " + getBehaviourName() + " E' DISATTIVO");
		}*/
		
	}
	
	/**
	 * Prepare the first child for execution.
	 * @see jade.core.behaviours.CompositeBehaviour#scheduleFirst
	 */
	protected void scheduleFirst() {
		if (getCurrent() == null) {
			initialOperations();
		}
	}

	/** This method schedules the next child to be executed.
	 * Basically this code does nothing. That is because
	 * the new current is already set in the transition processing code.
	 * @param currentDone a flag indicating whether the just executed
	 * child has completed or not.
	 * @param currentResult the termination value (as returned by
	 * <code>onEnd()</code>) of the just executed child in the case this
	 * child has completed (otherwise this parameter is meaningless)
	 * @see jade.core.behaviours.CompositeBehaviour#scheduleNext(boolean, int)
	 */
	protected void scheduleNext(boolean currentDone, int currentResult) {
		if (getCurrent() == null) {
			initialOperations();
		}

		// We don't have to do anything because the transition already sets current to be the
		// correct state.
	}

	/** This method makes the initial operations:
	 * updates current to the initial state and makes the initial action.
	 */
	private void initialOperations(){
		
		if(initialStateBehaviour != null){
			//stato iniziale di tipo normale
			
			//aggiorniamo tutti i current a partire dal DSCStarBehaviour parent
			//dello stato iniziale fino ad arrivare al DSCStarBehaviour root
			updateCurrentTot(initialStateBehaviour);
		}
		else{
			//stato iniziale di tipo H o H*

			Behaviour dest = null; //stato di destinazione
			if(initialStateHistory == DistilledStateChartTransition.SHALLOW_HISTORY){
				if(getLastState() != null){
					dest = getLastState(); //preleva l'ultimo sotto-stato visitato prima di uscire
				}
				else{
					if(getDefaultShallowHistoryEntrance() != null){
						dest = getDefaultShallowHistoryEntrance();
					}
					else{
						//se lo stato iniziale è un connettore di storia superficiale (H)
						//e manca la memoria e NON è stata definita la Default Shallow History Entrance,
						//lanciamo un'eccezione
						throw new RuntimeException("ERROR: Initial History State hasn't memory and hasn't a Default Shallow History Entrance: please, set a Default Shallow History Entrance!");
					}
				}
				
				//System.out.println("STATO DI DESTINAZIONE SHALLOW HISTORY: " + dest.getBehaviourName());
				
			}
			else if(initialStateHistory == DistilledStateChartTransition.DEEP_HISTORY){
				if(getLastState() != null){
					dest = this;
					while(dest instanceof DSCStarBehaviour){ //usciamo dal ciclo se abbiamo raggiunto uno stato foglia
						dest = ((DSCStarBehaviour) dest).getLastState(); //preleva l'ultimo sotto-stato visitato dallo stato precedente
					}
				}
				else{
					if(getDefaultDeepHistoryEntrance() != null){
						dest = getDefaultDeepHistoryEntrance();
					}
					else{
						//se lo stato iniziale è un connettore di storia profonda (H*)
						//e manca la memoria e NON è stata definita la Default Deep History Entrance,
						//lanciamo un'eccezione
						throw new RuntimeException("ERROR: Initial History State hasn't memory and hasn't a Default Deep History Entrance: please, set a Default Deep History Entrance!");
					}
				}
				
				//System.out.println("STATO DI DESTINAZIONE DEEP HISTORY: " + dest.getBehaviourName());
				
			}
			
			//aggiorniamo tutti i current a partire dal DSCStarBehaviour parent di "dest"
			//fino ad arrivare al DSCStarBehaviour root
			updateCurrentTot(dest);
		}
		
		//eseguiamo l'azione iniziale (se è stata definita) passandogli l'oggetto
		//che contiene le variabili ed i parametri attuali di questo DSCStarBehaviour
		//e l'oggetto che contiene le variabili ed i parametri globali della root
		DSCStarBehaviour corr = this;
		while(!rootP(corr)){
			corr = (DSCStarBehaviour) corr.getParent();
		}
		DSCStarBehaviour root = corr;
		initialAction(getActualVariablesAndParameters(), root.getActualVariablesAndParameters());
	}
	
	/** This method is normally overridden and indicates the action related to the initial state
	 * of the DSC* to execute when we enter the DSC* and we have to leave again from the initial state
	 * (it represents the existing action on the transition that connects the black circle to the
	 * initial state in the DSC*). If this action isn't defined, it isn't executed.
	 * @param compositeStateVariablesAndParameters object that contains actual variables and parameters of this DSCStarBehaviour.
	 * @param rootVariablesAndParameters object that contains global variables and parameters of root DSCStarBehaviour.
	 */
	public void initialAction(DSCStarVariablesAndParameters compositeStateVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters){
		//Questo metodo viene normalmente ridefinito ed indica l'azione associata allo stato iniziale
		//del DSCStarBehaviour da eseguire quando si entra nel DSCStarBehaviour
		//e bisogna ripartire dallo stato iniziale (rappresenta l'azione presente sulla transizione che
		//collega il cerchietto nero allo stato iniziale nel DSC*). Questa azione NON deve essere definita
		//per forza: se non viene definita, non viene eseguita.
		
		//Il parametro "compositeStateVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri
		//attuali di questo DSCStarBehaviour.
		//Il parametro "rootVariablesAndParameters" è l'oggetto che contiene le variabili ed i parametri globali
		//del DSCStarBehaviour root.
	}

	/** Check for transitions and fire.
	 * Essentially this code goes through the hierarchy looking for transitions
	 * to see if any return true. If they do, then we fire the transition and
	 * set up current to point to the new state.
	 * @return an ArrayList that contains the transition that we fired (position 0),
	 * the corresponding message (position 1), the object with variables and
	 * parameters of source DSCStarBehaviour (position 2) and the object with variables
	 * and parameters of the root (position 3), or null if we haven't fired any transition.
	 */
	private java.util.ArrayList findAndFireTransition() {

		//System.out.println("verifica delle transizioni avviata (chiamato metodo findAndFireTransition) ...");

		//get an event and test the transitions.
		ACLMessage msg = myAgent.receive(mainTemplate);	// Se il "mainTemplate" è stato assegnato, il DSC* riceve ed utilizza soltanto i messaggi che rispettano il
														// "mainTemplate" assegnato; cioè, il DSC* utilizza soltanto quei messaggi inviati appositamente per lui ed ignora
														// tutti gli altri messaggi inviati per essere utilizzati da qualche altro behaviour dell'agente corrente.
														// Mentre, se il "mainTemplate" è uguale a NULL, il DSC* riceve ed utilizza tutti i messaggi inviati all'agente corrente
														// senza alcun filtro.
		if (msg != null){
			java.util.ArrayList searchResult = searchThenFireTransition(((Wrapper) getCurrent()).getBehaviour(), msg);
			if (searchResult != null){
				java.util.ArrayList list = new java.util.ArrayList();
				list.add(searchResult.get(0)); //la transizione effettuata viene inserita nella posizione 0 dell'ArrayList da restituire
				list.add(msg); //il messaggio viene inserito nella posizione 1 dell'ArrayList da restituire
				list.add(searchResult.get(1)); //l'oggetto che contiene le variabili ed i parametri del DSCStarBehaviour di origine della transizione viene inserito nella posizione 2 dell'ArrayList da restituire
				list.add(searchResult.get(2)); //l'oggetto che contiene le variabili ed i parametri della root viene inserito nella posizione 3 dell'ArrayList da restituire
				return list;
			}
			else {
				if (putbackMessage) {
					myAgent.putBack(msg); 	// Se la transizione non è stata effettuata e se si è scelto
											// di utilizzare la politica di gestione dei messaggi con
											// "putbackMessage = TRUE", allora il messaggio ricevuto
											// viene rimesso all'inizio della coda dei messaggi e
											// sarà il primo messaggio ad essere ricevuto tramite la
											// prossima chiamata "receive".
				}
			}
		}
		
		return null; //NON abbiamo trovato nessuna transizione effettuabile
	}

	/** Search for a transition on the current message argument and fire if you can.
	 * @param state the Behaviour that we are to look for a match. Note: initially
	 * the state is this, but as we traverse up the tree it changes to parent DSC*.
	 * @param msg the message to attempt to match.
	 * @return an ArrayList that contains the transition fired, the object with
	 * variables and parameters of source DSCStarBehaviour and the object with
	 * variables and parameters of the root, or null otherwise.
	 */
	private java.util.ArrayList searchThenFireTransition(Behaviour state, ACLMessage msg) {
		
		//System.out.println("invocato metodo searchThenFireTransition ...");
		
		Iterator it = getTransitions(state);
		int tranCount = getTransitionCount(state);
		if (tranCount != 0) {
			while (it.hasNext()) {
				DSCStarNormalTransition trans = (DSCStarNormalTransition) it.next();
				boolean canFire = false;
				try {
					
					//ricaviamo l'oggetto che contiene le variabili ed i parametri
					//globali del DSCStarBehaviour root
					DSCStarBehaviour corr = this;
					while(!rootP(corr)){
						corr = (DSCStarBehaviour) corr.getParent();
					}
					DSCStarBehaviour root = corr;
					DSCStarVariablesAndParameters rootVariablesAndParameters = root.getActualVariablesAndParameters();
					
					//ATTENZIONE: NON SPOSTARE QUESTO BLOCCO DI CODICE --> [INIZIO]
					//Prima di eseguire il metodo "fireTransition",
					//ricaviamo l'oggetto che contiene le variabili ed i parametri
					//dell'ultima istanza attuale del DSCStarBehaviour di origine
					//della transizione (se lo stato sorgente della transizione è uno
					//stato semplice, il DSCStarBehaviour di origine della transizione
					//è rappresentato dallo stato composto che contiene lo stato
					//sorgente della transizione). Questo oggetto viene ricavato
					//prima dell'esecuzione del metodo "fireTransition", perchè durante
					//l'esecuzione di questo metodo potrebbe essere aggiunta
					//o rimossa un'istanza del DSCStarBehaviour di origine della
					//transizione e, per quanto riguarda il DSCStarBehaviour di origine,
					//non bisogna tenere conto di queste modifiche per utilizzare
					//l'ultima istanza esistente prima di eventuali aggiunte o rimozioni.
					DSCStarVariablesAndParameters sourceVariablesAndParameters;
					if(trans.getSourceState() instanceof DSCStarBehaviour){
						sourceVariablesAndParameters = ((DSCStarBehaviour) trans.getSourceState()).getActualVariablesAndParameters();
					}
					else{
						sourceVariablesAndParameters = ((DSCStarBehaviour) trans.getSourceState().parent).getActualVariablesAndParameters();
					}
					//<-- [FINE]

					canFire = trans.trigger(state, msg, sourceVariablesAndParameters, rootVariablesAndParameters);
					if ((canFire && !(trans instanceof DSCStarReturnTransition)) ||
							(canFire && (trans instanceof DSCStarReturnTransition) &&
							(getLastExecutedStarTransition() != null) &&
							(((DSCStarReturnTransition) trans).equals(getLastExecutedStarTransition().getDscStarReturnTransition())))) {
						
						//La transizione corrente può essere eseguita se:
						// 1) il suo metodo "trigger" restituisce TRUE;
						// 2) la transizione NON è di tipo "<<R>>".
						//Oppure se:
						// 1) il suo metodo "trigger" restituisce TRUE;
						// 2) la transizione è di tipo "<<R>>";
						// 3) la transizione è associata all'ultima transizione di tipo "*" eseguita (se esiste).
						//Questo permette di eseguire una transizione "<<R>>" soltanto dopo l'esecuzione della
						//transizione "*" corrispondente. Inoltre, basandoci soltanto sull'esito del metodo "trigger"
						//delle transizioni "<<R>>", potrebbe accadere di avere più transizioni "<<R>>" che,
						//a partire dallo stesso stato sorgente "X", potrebbero essere eseguite tutte tramite
						//lo stesso evento "ReturnFromX" (perchè lo stato sorgente "X" è lo stesso, ma lo stato
						//di destinazione potrebbe cambiare); invece, tramite i controlli effettuati, eseguiamo
						//soltanto la transizione "<<R>>" che è associata all'ultima transizione di tipo "*"
						//eseguita (se esiste) ed, anche se esistono delle altre transizioni "<<R>>" che
						//potrebbero essere eseguite tramite lo stesso evento, vengono ignorate. 
						
						fireTransition(msg, state, trans); //ATTENZIONE: NON SPOSTARE

						//restituiamo un ArrayList che contiene la transizione effettuata,
						//l'oggetto contenente le variabili ed i parametri del DSCStarBehaviour
						//di origine della transizione, l'oggetto contenente le variabili ed
						//i parametri della root
						java.util.ArrayList list = new java.util.ArrayList();
						list.add(trans); //posizione 0
						list.add(sourceVariablesAndParameters); //posizione 1
						list.add(rootVariablesAndParameters); //posizione 2
						return list;
					}
					
				}
				catch (java.lang.NullPointerException e) {
					e.printStackTrace();
					System.out.println("ERROR: Your trigger code in " + trans.getName() + " failed with a null pointer!");
				}
			}
		}
		if (rootP(state)) {
			return null;
		}
		return searchThenFireTransition(state.parent, msg);
	}

	/** Actually do the work to fire a transition.
	 * @param msg the message that we are to fire on.
	 * @param source the source of the transition.
	 * @param trans the transition that we are to fire.
	 */
	private void fireTransition(ACLMessage msg, Behaviour source, DSCStarNormalTransition trans) {
		
		//System.out.println("invocato metodo fireTransition ...");

		//prima di effettuare la transizione, memorizziamo la storia:
		//memorizziamo gli ultimi sotto-stati visitati dalle ultime istanze degli stati composti
		Behaviour corr = ((Wrapper) getCurrent()).getBehaviour();
		String storia = corr.getBehaviourName();
		while(rootP(corr) == false){
			((DSCStarBehaviour) corr.parent).setLastState(corr);
			storia = corr.parent.getBehaviourName() + " -> " + storia;
			corr = corr.parent;
		}
		storia = "STORIA MEMORIZZATA PRIMA DELLA TRANSIZIONE: " + storia;
		//System.out.println(storia);

		Behaviour currentLeafState = ((Wrapper) getCurrent()).getBehaviour(); //NON SPOSTARE (la chiamata "getCurrent" va fatta qui, perchè deve essere relativa all'ultima istanza attuale dello stato composto)
		
		//impostiamo tutti i current a NULL, partendo dal DSCStarBehaviour parent dello
		//stato foglia attuale fino ad arrivare al DSCStarBehaviour root, in modo che quando
		//ritorniamo eventualmente in questi DSCStarBehaviour senza storia, riprendiamo dal
		//loro stato iniziale
		updateCurrentTot(null); //NON SPOSTARE (perchè effettua la chiamata "getCurrent", che va fatta qui, perchè deve essere relativa all'ultima istanza attuale dello stato composto)

		Behaviour dest = null; //stato di destinazione
		if(!(trans instanceof DSCStarStarTransition) && !(trans instanceof DSCStarReturnTransition)){
			//se la transizione NON è di tipo "*" o "<<R>>"
			dest = trans.getTargetState();
			
			//NOTA: nei DSC* le transizioni normali (cioè, NON di tipo "*"/"<<R>>") NON possono
			//avere uno stato di destinazione di tipo composto, quindi NON possono utilizzare la storia.
			
		}
		else if(trans instanceof DSCStarStarTransition){
			//se la transizione è di tipo "*"
						
			DSCStarBehaviour target = (DSCStarBehaviour) trans.getTargetState();
			if(target.isActive() == true){
				//se lo stato composto di destinazione è attivo, significa che
				//siamo già entrati in questo stato composto e stiamo eseguendo la
				//procedura rappresentata da questo stato composto; quindi, dobbiamo
				//creare una nuova istanza dello stato composto e dobbiamo
				//ripartire dal suo stato iniziale (cioè, dalla default entrance)
				target.createNewInstance();
				dest = target;
			}
			else{
				//se lo stato composto di destinazione NON è attivo, significa che
				//stiamo entrando per la prima volta in questo stato composto;
				//quindi, dobbiamo utilizzare la prima istanza dello stato composto,
				//senza creare una nuova istanza
				dest = target;
			}
			
			//aggiungiamo nell'apposita lista presente nella root l'ultima transizione di tipo "*" eseguita
			addLastExecutedStarTransition((DSCStarStarTransition) trans);
		}
		else if(trans instanceof DSCStarReturnTransition){
			//se la transizione è di tipo "<<R>>"
			
			DSCStarBehaviour sourceState = (DSCStarBehaviour)(((DSCStarReturnTransition) trans).getSourceState());
			if(sourceState.getInstancesListSize() > 1){
				//se lo stato sorgente della transizione possiede più istanze,
				//dobbiamo eliminare la sua ultima istanza (per passare automaticamente
				//all'istanza precedente)
				sourceState.removeLastInstance();
			}
			else{
				//se lo stato sorgente della transizione ha una sola istanza,
				//indichiamo che questo stato non è attivo (per indicare che, quando
				//rientriamo in questo stato è come se entrassimo per la prima volta):
				//nei DSC*, gli stati composti diversi dalla root rappresentano delle procedure
				//da invocare, quindi NON possono contenere altri stati composti e possono
				//contenere soltanto degli stati semplici; per questo motivo, basta indicare
				//semplicemente che lo stato composto sorgente della transizione non è attivo,
				//senza indicare che eventualmente anche gli stati composti inferiori a questo
				//stato composto non sono attivi, perchè NON esistono degli stati composti inferiori
				sourceState.setActive(false);
				
				//eliminiamo e ricreiamo la prima istanza dello stato stato sorgente della
				//transizione per resettare l'istanza ai valori iniziali (in questo modo,
				//viene resettato ai valori iniziali anche l'oggetto che contiene le
				//variabili ed i parametri dell'istanza)
				sourceState.removeLastInstance();
				sourceState.createNewInstance();
			}
			
			//Andiamo con storia superficiale nello stato target della transizione
			//(per ritornare nell'ultimo sotto-stato foglia visitato dall'ultima
			//istanza dello stato target della transizione);
			//NOTA1: per ritornare nell'ultimo sotto-stato foglia visitato dall'ultima
			//istanza dello stato target della transizione, basta usare la storia superficiale
			//e NON quella profonda, perchè nei DSC* gli stati composti diversi dalla root
			//possono contenere soltanto degli stati semplici e NON possono contenere
			//degli stati composti;
			//NOTA2: il meccanismo funziona anche quando lo stato sorgente e lo stato
			//target della transizione coincidono, perchè l'eventuale rimozione
			//dell'ultima istanza dello stato sorgente viene eseguita prima di
			//effettuare le operazioni sullo stato target

			DSCStarBehaviour targetState = (DSCStarBehaviour) trans.getTargetState();
			if(targetState.getLastState() != null){
				dest = targetState.getLastState();
			}
			else{
				if(targetState.getDefaultShallowHistoryEntrance() != null){
					dest = targetState.getDefaultShallowHistoryEntrance();
				}
				else{
					dest = targetState;
				}
			}
			
			//System.out.println("STATO DI DESTINAZIONE SHALLOW HISTORY: " + dest.getBehaviourName());

			//eliminiamo dall'apposita lista presente nella root l'ultima transizione di tipo "*" eseguita
			//(perchè tramite la transizione "<<R>>" corrente abbiamo concluso la chiamata alla procedura,
			//quindi successivamente bisogna passare a controllare la transizione "*" precedente)
			removeLastExecutedStarTransition();
		}

		cleanUpToCommonParent(currentLeafState, dest);
		
		//aggiorniamo tutti i current a partire dal DSCStarBehaviour parent di "dest"
		//fino ad arrivare al DSCStarBehaviour root.
		updateCurrentTot(dest);
		
		//NOTA: il metodo "action" della transizione viene chiamato all'interno del metodo "action" del Wrapper
		//al termine di tutte le altre operazioni.
	}
	
	/** Call "onEnd" and "reset" for the current behaviour up to the common parent of the target.
	 * This code basically walks up the parent tree of the source asking if the parent equals
	 * any parent of the target (so, we set the source static and scan the target parent
	 * list). While we work our way up the source tree, we call "onEnd" and "reset" of each state.
	 * This makes sure that we have properly exited each state. If on the way up we never find
	 * the matching parent, then a RuntimeException is thrown. This should never happen, but if
	 * it does, then that means that the state machine is malformed.
	 * @param source The behaviour that we are transitioning from.
	 * @param target The behaviour that we are transitioning too, used to find the common parent.
	 * @throws RuntimeException when there is no parent that matches.
	 */
	private void cleanUpToCommonParent(Behaviour source, Behaviour target) {
		//debugPrint(4, "cleanUpToCommonParent: Enter Source: " + source.getBehaviourName() + " Target: "+ target.getBehaviourName());
		if (source instanceof DSCStarBehaviour) {
			source.onEnd();
			source.reset();
		}
		else {
			Behaviour wrapped = ((DSCStarBehaviour) source.parent).getWrappedState(source);
			wrapped.onEnd();
			wrapped.reset();
		}
		if (source.parent instanceof DSCStarBehaviour) {
			if (sourceMatchesAnyParent(source.parent, target)) {
				//debugPrint(4, "cleanUpToCommonParent: parent match found for: " + source.getBehaviourName() + " and " + target.getBehaviourName());
				return;
			}
			cleanUpToCommonParent(source.parent, target);
		}
		else {
			// Ok, this is really weird, this means that there is no common parent.
			// I am pretty sure that this has to be an error.  There has to be at least
			// one common parent, else you are transitioning to something wierd.  Let's
			// throw an error.
			throw new RuntimeException("ERROR: Illegal DSC*! No Common Parent Found!");
		}
	}

	/** Tests if the source parent matches any parent up the tree of the target.
	 * Eventually if we reach where there is no DSCStarBehaviour as a parent, then we
	 * return false. If along the way we find a match, then we return true.
	 * @param sourcesParent The DSCStarBehaviour parent that we are trying to match.
	 * @param target The behaviour that we scan.
	 * @return True if it found a match some place and false otherwise.
	 */
	private boolean sourceMatchesAnyParent(Behaviour sourcesParent, Behaviour target) {
		if (target.parent instanceof DSCStarBehaviour) {
			if (sourcesParent == target.parent) {
				return true;
			}
			else {
				return sourceMatchesAnyParent(sourcesParent, target.parent);
			}
		}
		else {
			return false;
		}
	}

	/** This method creates the root DSCStarBehaviour using
	 * the composite states passed as parameter,
	 * in agreement to the DSC template with initial deep history state.
	 * @param main composite state that represents the main procedure.
	 * @param otherStates list of other composite states that represent the other procedures.
	 * @param rootVariablesAndParameters global variables of the root
	 */
	public static DSCStarBehaviour createRootForDSCStar(DSCStarBehaviour main, java.util.ArrayList<DSCStarBehaviour> otherStates, DSCStarVariablesAndParameters rootVariablesAndParameters){

		//Questo metodo statico permette di creare il DSCStarBehaviour root
		//in accordo al template DSC per i DSC*: riceve come parametri il
		//DSCStarBehaviour "main", che rappresenta la procedura "main", gli
		//altri DSCStarBehaviour, che rappresentano le altre procedure, all'interno
		//di una lista e l'oggetto che contiene le variabili globali da assegnare alla root;
		//così, genera la root che ha come stato iniziale il connettore di
		//storia profonda presente nello stato "ACTIVE" del template DSC e come
		//Default Deep History Entrance il DSCStarBehaviour "main" passato come parametro.
		
		//NOTA: Bisogna creare prima la root tramite questo metodo e poi aggiungere le
		//transizioni (tramite il metodo "addTransition") nella root generata (cioè,
		//gli stati passati come parametro a questo metodo NON devono contenere transizioni).
		
		//per NON clonare l'oggetto variabili della root, creiamo prima la root con un oggetto
		//variabili uguale a NULL e poi settiamo la variabile "dscStarVariablesAndParameters",
		//che è quella che viene utilizzata sempre per accedere all'oggetto variabili della root
		//(che non viene clonato) 
		DSCStarBehaviour root = new DSCStarBehaviour(main.myAgent, "DSC*_ROOT", main.getMainTemplate(), main.getPutback(), null);
		root.dscStarVariablesAndParameters = rootVariablesAndParameters;
		
		//aggiungiamo gli stati e settiamo la Default Deep History Entrance nella root
		root.addInitialState(DistilledStateChartTransition.DEEP_HISTORY);
		root.addCompositeStateInRoot(main, true); //il "main" è uno stato finale
		for(int i=0; i < otherStates.size(); i++){
			root.addCompositeStateInRoot(otherStates.get(i), false);
		}
		root.setDefaultDeepHistoryEntrance(main);
		return root;
	}

	/** Add the last executed DSCStarStarTransition.
	 * @param trans the last executed DSCStarStarTransition.
	 */
	private void addLastExecutedStarTransition(DSCStarStarTransition trans){
		//aggiunge in coda (nella lista presente nella root) l'ultima transizione di tipo "*" eseguita
		DSCStarBehaviour corr = this;
		while(!rootP(corr)){
			corr = (DSCStarBehaviour) corr.getParent();
		}
		DSCStarBehaviour root = corr;
		root.executedStarTransition.addLast(trans);
		
		//System.out.println("AGGIUNTA UNA TRANSIZIONE * NELLA LISTA (DIMENSIONE LISTA = " + root.executedStarTransition.size() + ")");
		
	}
	
	/** Get the last executed DSCStarStarTransition.
	 * @return the last executed DSCStarStarTransition.
	 */
	private DSCStarStarTransition getLastExecutedStarTransition(){
		//restituisce l'ultima transizione di tipo "*" eseguita (tramite la lista memorizzata nella root)
		DSCStarBehaviour corr = this;
		while(!rootP(corr)){
			corr = (DSCStarBehaviour) corr.getParent();
		}
		DSCStarBehaviour root = corr;
		
		if(root.executedStarTransition.size() > 0){
			return root.executedStarTransition.getLast();
		}
		return null;
	}
	
	/** Remove the last executed DSCStarStarTransition.
	 */
	private void removeLastExecutedStarTransition(){
		//eliminiamo dalla lista presente nella root l'ultima transizione di tipo "*" eseguita
		DSCStarBehaviour corr = this;
		while(!rootP(corr)){
			corr = (DSCStarBehaviour) corr.getParent();
		}
		DSCStarBehaviour root = corr;
		
		if(root.executedStarTransition.size() > 0){
			root.executedStarTransition.removeLast();
			
			//System.out.println("ELIMINATA UNA TRANSIZIONE * DALLA LISTA (DIMENSIONE LISTA = " + root.executedStarTransition.size() + ")");
			
		}		
	}

	/** Test to see if I am a root.
	 * @return true if a root, false otherwise.
	 */
	private boolean rootP() {
		return (getParent() == null) || !(getParent() instanceof DSCStarBehaviour);
	}

	/** Test to see if argument is a root.
	 * @param state The state to check.
	 * @return true if a root, false otherwise.
	 */
	private boolean rootP(Behaviour state) {
		return (state.parent == null) || !(state.parent instanceof DSCStarBehaviour);
	}
	
	/** Adds the argument transition to the list of transitions in the root.
	 * @param trans the transition that we are adding
	 */
	public void addTransition(DSCStarNormalTransition trans) {
		if(!(trans instanceof DSCStarStarTransition) && !(trans instanceof DSCStarReturnTransition) && ((trans.getTargetState() instanceof DSCStarBehaviour) || (trans.getSourceState() instanceof DSCStarBehaviour))){
			//se la transizione non è di tipo "*" e neanche di tipo "<<R>>", ma il suo stato sorgente o quello di destinazione è uno stato composto,
			//viene lanciata un'eccezione, perchè le transizioni che possono coinvolgere stati composti nei DSC* sono solo di tipo "*" e "<<R>>"
			throw new RuntimeException("ERROR: Only DSCStarStarTransition/DSCStarReturnTransition can have a target/source COMPOSITE state!");
		}
		else if(!(trans instanceof DSCStarStarTransition) && !(trans instanceof DSCStarReturnTransition) && !((trans.getSourceState().parent).equals(trans.getTargetState().parent))){
			//se la transizione non è di tipo "*" e neanche di tipo "<<R>>" ed il suo stato sorgente e quello di destinazione non sono contenuti
			//nello stesso stato composto, viene lanciata un'eccezione, perchè le transizioni normali (cioè, non di tipo "*"/"<<R>>") non possono
			//far spostare in stati composti diversi
			throw new RuntimeException("ERROR: Found a transition that has target/source state with different parent state!");
		}
		
		if (!rootP()) {
			((DSCStarBehaviour) getParent()).addTransition(trans);
		}
		else {
			Behaviour source = trans.getSourceState();
			List tList = null;
			if (transitions.containsKey(source)) {
				tList = (List) transitions.get(source);
			}
			else {
				tList = new ArrayList();
			}
			tList.add(trans);
			transitions.put(source, tList);

			//System.out.println("TRANSIZIONE " + trans.getName() + " aggiunta in " + getBehaviourName());
			
			//se la transione è di tipo "*", aggiungiamo automaticamente la transizione "<<R>>" corrispondente
			if(trans instanceof DSCStarStarTransition){
				
				//lo stato sorgente della transizione "<<R>>" è lo stato composto di destinazione della transizione "*"
				DSCStarBehaviour sourceReturnTransition = (DSCStarBehaviour) trans.getTargetState();
				
				//lo stato di destinazione della transizione "<<R>>" è lo stato composto di origine della transizione "*"
				DSCStarBehaviour targetReturnTransition = (DSCStarBehaviour) source;

				DSCStarReturnTransition returnTrans = new DSCStarReturnTransition(sourceReturnTransition, targetReturnTransition);
				List tList2 = null;
				if (transitions.containsKey(sourceReturnTransition)) {
					tList2 = (List) transitions.get(sourceReturnTransition);
				}
				else {
					tList2 = new ArrayList();
				}
				tList2.add(returnTrans);
				transitions.put(sourceReturnTransition, tList2);
				
				//associamo la DSCStarReturnTransition alla DSCStarStarTransition attuale
				((DSCStarStarTransition) trans).setDscStarReturnTransition(returnTrans);

				//System.out.println("TRANSIZIONE " + returnTrans.getName() + " aggiunta in " + getBehaviourName());
				
			}
		}
	}
	
	/** Add a <code>Behaviour</code> as a state of this <code>DSCStarBehaviour</code>.
	 * @param state The <code>Behaviour</code> representing the state
	 */
	public void addState(Behaviour state) {
		if(initialStateBehaviour == null && initialStateHistory == DistilledStateChartTransition.NO_HISTORY){
			//Se lo stato iniziale non è stato settato, lanciamo un'eccezione.
			//Questo obbliga ad aggiungere prima lo stato iniziale e poi gli altri stati.
			//Così, è possibile assicurare che ogni macchina a stati abbia uno stato iniziale,
			//che è necessario per il corretto funzionamento delle transizioni gerarchiche e della storia.
			throw new RuntimeException("ERROR: for each state machine, add first initial state and then other states!");
		}
		else if(state instanceof DSCStarBehaviour){
			//se lo stato da aggiungere è uno stato composto, lanciamo un'eccezione, perchè
			//nei DSC* gli stati composti diversi dalla root rappresentano delle procedure
			//da invocare, quindi NON possono contenere altri stati composti e possono
			//contenere soltanto degli stati semplici
			throw new RuntimeException("ERROR: in DSC*, you can't add a composite state to another composite state!");
		}
		
		if (states.containsKey(state)) {
			// We are already wrapped and added.
			return;
		}
		
		state.setParent(this); //settiamo il parent
		Behaviour wrappedState = new Wrapper(state, myAgent);
		wrappedState.setBehaviourName(state.getBehaviourName() + "[Wrapped]");
		states.put(state, wrappedState);
	}

	/** Add a composite state to this <code>DSCStarBehaviour</code> (this method is used internally by the root).
	 * @param state The <code>DSCStarBehaviour</code> representing the composite state
	 * @param finalState Indicates if the state that we are adding is a final state (TRUE) or not (FALSE)
	 */
	private void addCompositeStateInRoot(DSCStarBehaviour state, boolean finalState) {
		
		//Questo metodo viene utilizzato dalla root generata in accordo al DSC template
		//per aggiungere i suoi sotto-stati composti
		
		if(initialStateBehaviour == null && initialStateHistory == DistilledStateChartTransition.NO_HISTORY){
			//Se lo stato iniziale non è stato settato, lanciamo un'eccezione.
			//Questo obbliga ad aggiungere prima lo stato iniziale e poi gli altri stati.
			//Così, è possibile assicurare che ogni macchina a stati abbia uno stato iniziale,
			//che è necessario per il corretto funzionamento delle transizioni gerarchiche e della storia.
			throw new RuntimeException("ERROR: for each state machine, add first initial state and then other states!");
		}
		
		if (states.containsKey(state)) {
			// We are already wrapped and added.
			return;
		}
		
		states.put(state, state);
		state.setPutback(putbackMessage);
		state.setMainTemplate(mainTemplate);
			
		// We have to take all of the transitions that may already exist in the
		// state that we are adding and stuff them into the root. This way if we
		// add an already existing DSC*, all of the transitions will end up in the
		// root.
			
		Collection stateTransitions = state.getStatesWithTransitions(); //preleviamo tutte le eventuali transizioni presenti nello stato da aggiungere
																		//(da notare che questo stato contiene delle transizioni soltanto se è una root)
		Iterator states = stateTransitions.iterator();
		while (states.hasNext()) {
			Behaviour source = (Behaviour) states.next();
			Iterator theTransitions = state.getTransitions(source);
			while (theTransitions.hasNext()) {
				DSCStarNormalTransition trans = (DSCStarNormalTransition) theTransitions.next();
					
				if(!(trans instanceof DSCStarReturnTransition)){
					//se la transizione non è di tipo "DSCStarReturnTransition",
					//aggiungiamo la transizione corrente in questo (this) DSCStarBehaviour;
					//infatti, le transizioni di tipo "DSCStarReturnTransition" vengono
					//aggiunte automaticamente all'interno del metodo "addTransition" quando
					//aggiungiamo le transizioni "DSCStarStarTransition" corrispondenti
					addTransition(trans);
				}
			}
		}
		state.removeAllTransitions();	//eliminiamo tutte le eventuali transizioni presenti nello stato da aggiungere
										//(da notare che questo stato contiene delle transizioni soltanto se è una root)

		/* IT IS VERY IMPORTANT THAT WE DO THIS setParent LAST!!!!  The reason is that the code
		 * above for adding in an existing DSCStarBehaviour, must grab all of the existing
		 * transitions and stuff them into our root.  To do this, we call the getTransitions
		 * code.  Well, getTransitions traverses to the root to find the match.  If we
		 * have already grafted the state into the tree, then we will skip over the ones
		 * that are stored in the argument DSC*.  So, to get around this, what we do is
		 * first grab all of the transitions, store them in the root, and then graft the
		 * state in.  This should work just fine, but DO NOT do the setParent until after
		 * this work has been done!!!! */
		state.setParent(this);
		
		if(finalState == true){
			//lo stato da aggiungere è uno stato finale
			if (!finalStates.contains(state)) {
				finalStates.add(state);
			}
		}
	}

	/** Remove the argument transition from the set of known transitions.
	 * @param aTransition the transition to remove.
	 * @exception RuntimeException is thrown if the argument transition isn't in the set of transitions.
	 */
	public void removeTransition(DSCStarNormalTransition aTransition) {
		if (!rootP()){
			((DSCStarBehaviour) getParent()).removeTransition(aTransition);
		}
		else{
			Behaviour source = aTransition.getSourceState();
			if (transitions.containsKey(source)) {
				List tList = (List) transitions.get(source);
				if (tList.contains(aTransition)) {
					tList.remove(aTransition);
					transitions.put(source, tList);
				}
				else {
					throw new RuntimeException("ERROR: Transition not present!");
				}
			}
			else {
				throw new RuntimeException("ERROR: No transitions for source!");
			}
			
			//se la transione è di tipo "*", eliminiamo automaticamente la transizione "<<R>>" corrispondente
			if(aTransition instanceof DSCStarStarTransition){
				DSCStarReturnTransition returnTransition = ((DSCStarStarTransition) aTransition).getDscStarReturnTransition();
				DSCStarBehaviour sourceReturnTransition = (DSCStarBehaviour) returnTransition.getSourceState();
				if (transitions.containsKey(sourceReturnTransition)) {
					List tList = (List) transitions.get(sourceReturnTransition);
					if (tList.contains(returnTransition)) {
						tList.remove(returnTransition);
						transitions.put(sourceReturnTransition, tList);
					}
					else {
						throw new RuntimeException("ERROR: Transition not present!");
					}
				}
				else {
					throw new RuntimeException("ERROR: No transitions for source!");
				}
			}
		}
	}

	/** To set the putback mode.
	 * @param aPutback boolean to set the putback mode
	 */
	public void setPutback(boolean aPutback) {
		putbackMessage = aPutback;
	}

	/** To set the MessageTemplate.
	 * Null means not filtering.
	 * @param aTemplate the filtering MessageTemplate
	 */
	public void setMainTemplate(MessageTemplate aTemplate) {
		mainTemplate = aTemplate;
	}

	/** Get the putback mode.
	 */
	public boolean getPutback() {
		return putbackMessage;
	}

	/** Get the MessageTemplate.
	 */
	public MessageTemplate getMainTemplate() {
		return mainTemplate;
	}

	/* Convenience name printer */
	private String localName(Behaviour b) {
		if (b == null) {
			return "null";
		}
		else {
			return b.getBehaviourName();
		}
	}

	/** Returns the parent behaviour of this behaviour.
	 * @return the parent behaviour of this behaviour.
	 */
	public CompositeBehaviour getParent() {
		return this.parent;
	}

	/** Determines if the given state is already associated with this state.
	 * @param b Tests to see if this argument is a state inside of me.
	 * @return True if it is present, false otherwise.
	 */
	public boolean containsState(Behaviour b) {
		return states.containsKey(b);
	}

	/** Returns the number of states contained within this state.
	 * @return The number of states within me.
	 */
	public int getStateCount() {
		return states.size();
	}

	/** Add a <code>Behaviour</code> as the initial state of this <code>DSCStarBehaviour</code>.
	 * @param state The <code>Behaviour</code> representing the state
	 */
	public void addInitialState(Behaviour state) {
		
		//azzeriamo l'eventuale stato iniziale di tipo H o H* presente
		initialStateHistory = DistilledStateChartTransition.NO_HISTORY;
		
		//settiamo lo stato iniziale di tipo normale
		initialStateBehaviour = state;
		addState(state);
	}
	
	/** Add a history state as the initial state of this <code>DSCStarBehaviour</code>.
	 * @param history The history type: DistilledStateChartTransition.SHALLOW_HISTORY or DistilledStateChartTransition.DEEP_HISTORY
	 */
	public void addInitialState(int history) {
		
		//azzeriamo l'eventuale stato iniziale di tipo normale presente
		initialStateBehaviour = null;
		
		//settiamo lo stato iniziale di tipo H o H*
		initialStateHistory = history;
	}

	/** Returns the initial state of this <code>DSCStarBehaviour</code>
	 * (the initial state returned is a Behaviour or an Integer that represents an history state
	 * of type DistilledStateChartTransition.SHALLOW_HISTORY or DistilledStateChartTransition.DEEP_HISTORY).
	 * @return the initial state of this DSCStarBehaviour.
	 */
	public Object getInitialState() {
		if(initialStateBehaviour != null){
			//stato iniziale di tipo normale
			return initialStateBehaviour;
		}
		
		//stato iniziale di tipo H o H*
		return new Integer(initialStateHistory);
	}

	/** Add a <code>Behaviour</code> as a final state of this <code>DSCStarBehaviour</code>.
	 * When the DSC* reaches this state
	 * the added <code>Behaviour</code> will be executed and,
	 * when completed, the <code>DSCStarBehaviour</code> will terminate too.
	 * @param state The <code>Behaviour</code> representing the state
	 */
	public void addFinalState(Behaviour state) {
		addState(state);
		if (!finalStates.contains(state)) {
			finalStates.add(state);
		}
	}

	/** Returns the final states assocated with this <code>DSCStarBehaviour</code>.
	 * @return An iterator of all of the final states of this DSC*.
	 */
	public Iterator getFinalStates() {
		return finalStates.iterator();
	}

	/** Returns the number of final states associated with this <code>DSCStarBehaviour</code>.
	 * @return How many final states there are in this DSC*.
	 */
	public int getFinalStateCount() {
		return finalStates.size();
	}

	/** Returns the final state at the given index.
	 * @param index The index of one of the final states in this DSC*.
	 * @return Given an index, returns the specific final state.
	 */
	public Behaviour getFinalState(int index) {
		return (Behaviour) finalStates.get(index);
	}

	/** Delete the argument state from this DSC*.
	 * If the state is an initial state or a final state, we remove it too.  Note -
	 * we currently do not delete any transitions associated with this state.  We
	 * could and probably should at least delete those that are sourced by this state.
	 * But then again, it is up to the user to take care of this.
	 * @param state the behaviour to delete.
	 * @exception RuntimeException if the state that we attempt to remove is not defined in the DSC*.
	 */
	public void removeState(Behaviour state) {
		if (states.containsKey(state)) {
			Behaviour wrappedState = (Behaviour) states.get(state);
			states.remove(state);
			if (initialStateBehaviour == state) {
				initialStateBehaviour = null;
			}
			if (finalStates.contains(state)) {
				finalStates.remove(state);
			}
		}
		else {
			throw new RuntimeException("ERROR: State " + localName(state)+ " not removed! It is not in DSC*!");
		}
	}
	
	/** Delete the initial history state (H or H*) from this DSC*.
	 */
	public void removeInitialStateHistory(){
		initialStateHistory = DistilledStateChartTransition.NO_HISTORY;
	}

	/** Determines if the given transition is already added.
	 * @param aTransition Tests to see if this argument is a transition already added.
	 * @return True if it is present, false otherwise.
	 */
	public boolean containsTransition(DSCStarNormalTransition aTransition) {
		if (!rootP())
			return ((DSCStarBehaviour) getParent()).containsTransition(aTransition);

		Behaviour source = aTransition.getSourceState();
		if (transitions.containsKey(source)) {
			List tList = (List) transitions.get(source);
			if (tList.contains(aTransition))
				return true;
		}

		return false;
	}
	
	/** Remove all the transitions.
	 */
	public void removeAllTransitions(){
		if (!rootP()) {
			((DSCStarBehaviour) getParent()).removeAllTransitions();
		}
		else {
			transitions.clear();
		}
	}

	/**
	 * Get the exit value of the last executed state.
	 * @return the exit value of the last executed state.
	 */
	public int getLastExitValue() {
		return lastExitValue;
	}

	/**
	 * Check whether this <code>DSCStarBehaviour</code> must terminate.
	 * Note: this happens only when the last child is done and it is a final state.
	 * @param currentDone tells if the behaviour that just ran set its done flag to true.
	 * @param currentResult is the result that the behaviour that just ran returned.
	 * @return true when the last child has terminated and it represents a final state, false otherwise.
	 * @see jade.core.behaviours.CompositeBehaviour#checkTermination
	 */
	protected boolean checkTermination(boolean currentDone, int currentResult) {
		//debugPrint(4, "checkTermination: Enter - current: " + (current == null ? "null" : current.getBehaviourName()) + " cd: " + currentDone);
		if (currentDone) {
			lastExitValue = currentResult;
			Behaviour unwrapped = getCurrent();
			if (getCurrent() instanceof Wrapper) {
				unwrapped = ((Wrapper) getCurrent()).getBehaviour();
			}
			boolean result = finalStates.contains(unwrapped);
			//debugPrint(4, "checkTermination: Exit - result: " + result);
			return result;
		}
		//debugPrint(4, "checkTermination: Exit - result: false");
		return false;
	}

	/** Helper to extract the wrapped state of the argument.
	 * @param aState the state that we are looking up.
	 * @return Either the wrapped state or null if it isn't wrapped yet.
	 */
	public Behaviour getWrappedState(Behaviour aState) {
		if (states.containsKey(aState)) {
			return (Behaviour) states.get(aState);
		}
		else {
			return null;
		}
	}

	/** Updates all the current starting from the <code>DSCStarBehaviour</code> parent
	 * of the state passed as parameter up to the root <code>DSCStarBehaviour</code>.
	 * If the state passed as parameter is null, all the current are setted to null,
	 * starting from the <code>DSCStarBehaviour</code> parent of the actual leaf state
	 * up to the root <code>DSCStarBehaviour</code>.
	 * @param state The state to set the current to, updating also the current of superior behaviours.
	 */
	private void updateCurrentTot(Behaviour state){
		
		//Questo metodo permette di aggiornare tutti i current a partire dal DSCStarBehaviour
		//parent dello stato passato come parametro fino ad arrivare al DSCStarBehaviour root.
		//Se lo stato passato come parametro è uguale a NULL, tutti i current vengono
		//settati a NULL, partendo dal DSCStarBehaviour parent dello
		//stato foglia attuale fino ad arrivare al DSCStarBehaviour root.
		
		if(state == null){
			Behaviour behaviour = ((Wrapper) getCurrent()).getBehaviour();
			while(rootP(behaviour) == false){
				((DSCStarBehaviour) behaviour.parent).setCurrent(null);
				behaviour = behaviour.parent;
			}
		}
		else{
			Behaviour behaviour = state;
			while(rootP(behaviour) == false){
				((DSCStarBehaviour) behaviour.parent).setCurrent(behaviour);
				behaviour = behaviour.parent;
			}
		}
	}
	
	/** Get Default Shallow History Entrance of this <code>DSCStarBehaviour</code>.
	 * @return Behaviour that represents Default Shallow History Entrance.
	 */
	public Behaviour getDefaultShallowHistoryEntrance() {
		return defaultShallowHistoryEntrance;
	}

	/** Set Default Shallow History Entrance of this <code>DSCStarBehaviour</code>.
	 * @param state Behaviour that represents Default Shallow History Entrance.
	 */
	public void setDefaultShallowHistoryEntrance(Behaviour state) {
		if (states.containsKey(state)) {
			this.defaultShallowHistoryEntrance = state;
		}
		else{
			//se lo stato passato come parametro NON è un sotto-stato di questo DSCStarBehaviour, lanciamo un'eccezione
			throw new RuntimeException("ERROR: chosen Default Shallow History Entrance isn't a state of corresponding DSCStarBehaviour, add first states to DSCStarBehaviour and then select its Default Shallow History Entrance!");
		}
	}
	
	/** Get Default Deep History Entrance of this <code>DSCStarBehaviour</code>.
	 * @return Behaviour that represents Default Deep History Entrance.
	 */
	public Behaviour getDefaultDeepHistoryEntrance() {
		return defaultDeepHistoryEntrance;
	}

	/** Set Default Deep History Entrance of this <code>DSCStarBehaviour</code>.
	 * @param state Behaviour that represents Default Deep History Entrance.
	 */
	public void setDefaultDeepHistoryEntrance(Behaviour state) {
		if (states.containsKey(state)) {
			this.defaultDeepHistoryEntrance = state;
		}
		else{
			//se lo stato passato come parametro NON è un sotto-stato di questo DSCStarBehaviour, lanciamo un'eccezione
			throw new RuntimeException("ERROR: chosen Default Deep History Entrance isn't a state of corresponding DSCStarBehaviour, add first states to DSCStarBehaviour and then select its Default Deep History Entrance!");
		}
	}

	/** Return a Collection view of the children behaviours of this <code>DSCStarBehaviour</code>.
	 * @return The collection.
	 * @see jade.core.behaviours.CompositeBehaviour#getChildren
	 */
	public Collection getChildren() {
		return states.keySet();
	}

	/** Return an Iterator of the transitions with the given source state.
	 * @param source The source state of the transitions.
	 * @return The Iterator.
	 */
	public Iterator getTransitions(Behaviour source) {
		if (!rootP()) {
			return ((DSCStarBehaviour) getParent()).getTransitions(source);
		}
		List tList = new ArrayList();
		if (transitions.containsKey(source)) {
			tList = (List) transitions.get(source);
		}
		return tList.iterator();
	}

	/** Returns the number of transitions with the given source state.
	 * @param source The source state of the transitions.
	 * @return The number of transitions.
	 */
	public int getTransitionCount(Behaviour source) {
		if (!rootP()) {
			return ((DSCStarBehaviour) getParent()).getTransitionCount(source);
		}
		if (transitions.containsKey(source)) {
			return ((List) transitions.get(source)).size();
		}
		else {
			return 0;
		}
	}

	/** Returns the transition at the given index.
	 * @param index The index of the transition that we are looking up.
	 * @param source The source state of the transition.
	 * @return The transition.
	 */
	public DSCStarNormalTransition getTransition(int index, Behaviour source) {
		if (!rootP()) {
			return ((DSCStarBehaviour) getParent()).getTransition(index, source);
		}
		
		List tList = new ArrayList();
		if (transitions.containsKey(source)) {
			tList = (List) transitions.get(source);
		}
		
		return (DSCStarNormalTransition) tList.get(index);
	}

	/** Returns the collection of only the transitions that I know about
	 * (this method has to work in this way! don't modify!).
	 * @return The collection of transitions.
	 */
	public Collection getStatesWithTransitions() {
		return transitions.keySet();
	}

	/** Reset this <code>DSCStarBehaviour</code>.
	 */
	public void reset() {
		super.reset();
	}

	/** Overridden from the base class to make sure that the Wrapper
	 * objects have their agent pointers set as well.
	 * @param a The agent that you are setting for this behaviour.
	 */
	public void setAgent(Agent a) {
		super.setAgent(a);
		Iterator iter = states.values().iterator();
		while (iter.hasNext())
			((Behaviour) iter.next()).setAgent(a);
	}

	/** Handle block/restart notifications.
	 * A <code>DSCStarBehaviour</code> is blocked only when
	 * its currently active child is blocked, and becomes ready again
	 * when its current child is ready. This method takes care of the
	 * various possibilities.
	 * @param rce The event to handle.
	 */
	protected void handle(RunnableChangedEvent rce) {

		//Questo metodo permette di gestire gli eventi di BLOCK e RESTART dei behaviour coinvolti.
		//ATTENZIONE: L'intero DSC* risulta bloccato soltanto quando il behaviour del suo stato corrente è bloccato,
		//e si riattiva quando il behaviour del suo stato corrente si riattiva.

		if (rce.isUpwards()) {
			// Upwards notification
			if (rce.getSource() == this) {
				// If the event is from this behaviour, set the new
				// runnable state and notify upwords.
				super.handle(rce);
			}
			else if ((rce.getSource() == getCurrent()) || ((getCurrent() != null) && (getCurrent() instanceof Wrapper) && (rce.getSource() == ((Wrapper) getCurrent()).getBehaviour()))) {
				// se l'evento è da parte del behaviour dello stato corrente (di tipo DSCStarBehaviour, Wrapper, o behaviour associato al Wrapper),
				// crea un nuovo evento, setta il nuovo runnable state e notify upwords.
				myEvent.init(rce.isRunnable(), NOTIFY_UP);
				super.handle(myEvent);
			}
			else {
				// If the event is from another child, just ignore it
			}
		}
		else {
			// Downwards notifications
			// Copy the state and pass it downwords only to the
			// current child
			setRunnable(rce.isRunnable());
			if (getCurrent() != null) {
				getCurrent().handle(rce);
			}
		}
	}
	
	/** "onEnd" method for this DSC*.
	 * "onStart" and "onEnd" represent the entry/exit codes defined in the UML state
	 * machine model. They are called in a hierarchical fashion as per that model.
	 * @return the last exit value.
	 */
	public int onEnd() {
		//debugPrint(2, ".onEnd executed");
		return getLastExitValue();
	}

	/** "onStart" method for this DSC*.
	 * "onStart" and "onEnd" represent the entry/exit codes defined in the UML state
	 * machine model. They are called in a hierarchical fashion as per that model.
	 */
	public void onStart() {
		//debugPrint(2, ".onStart executed");
	}

	/** This class wraps behaviours that are used as states. The purpose is to give us a hook into the action.
	 * The problem is that "actionWrapper" method of "Behaviour" class calls "onStart" (the first time) and then
	 * immediately calls "action". We wish to check to see if any transitions can fire before calling "action".
	 * So, this class wraps the behaviour and lets us insert our own code to do the checking.
	 */
	private class Wrapper extends Behaviour {

		private Behaviour behaviour; //behaviour associato
		private boolean firstActionInvocation; //indica se è la prima volta che viene invocato il metodo "action" del behaviour associato
		private boolean transitionExecuted; //indica se è stata effettuata una transizione
		
		public Wrapper(Behaviour b, Agent anAgent) {
			super(anAgent);
			behaviour = b;
			
			/*if (b.parent == null) {
				System.out.println("Parent of " + b.getBehaviourName() + " = NULL !!!");
			}
			else {
				System.out.println("Parent of " + b.getBehaviourName() + " = " + b.parent.getBehaviourName());
			}*/
			
			setParent(b.parent);

			firstActionInvocation = true;
			transitionExecuted = false;
		}

		public void action() {
			//NOTA: all'interno del metodo "action" del Wrapper,
			//eseguiamo il metodo "action" del behaviour associato o
			//il metodo "action" della transizione effettuata
			//come ultima operazione prima di uscire dal metodo "action" del Wrapper,
			//per fare in modo che eventuali operazioni come "doMove" contenute
			//all'interno del metodo "action" del behaviour associato o all'interno
			//del metodo "action" della transizione effettuata vengano eseguite
			//al termine di tutte le altre operazioni.
			
			//indichiamo che lo stato composto superiore allo stato foglia corrente è attivo:
			//nei DSC*, gli stati composti diversi dalla root rappresentano delle procedure
			//da invocare, quindi NON possono contenere altri stati composti e possono
			//contenere soltanto degli stati semplici; per questo motivo, basta indicare
			//semplicemente che lo stato composto superiore allo stato foglia corrente è attivo,
			//senza indicare che anche gli stati superiori a questo stato composto sono attivi,
			//perchè l'unico stato superiore a questo stato composto è la root, che non ha bisogno
			//di essere segnata come "attiva", perchè non è coinvolta nelle transizioni "*"/"<<R>>"
			((DSCStarBehaviour) behaviour.parent).setActive(true);
			
			//se il metodo "done" del behaviour associato restituisce TRUE
			//ed è stato invocato almeno una volta il suo metodo "action",
			//significa che il behaviour associato ha terminato, quindi
			//si verifica soltanto se è possibile effettuare una transizione
			//e, se attualmente non può essere eseguita nessuna transizione,
			//il DSC* viene messo in attesa che arrivi un messaggio per
			//controllare nuovamente se è possibile effettuare una transizione
			if (behaviour.done() == true && firstActionInvocation == false) {
				java.util.ArrayList list = findAndFireTransition();
				if(list == null){ //non è stata effettuata nessuna transizione
					transitionExecuted = false;
					
					//System.out.println("DSC* in attesa di un evento ...");
					
					//mettiamo in attesa il DSC* fino a quando non arriva un messaggio da processare,
					//che permette di verificare nuovamente (alla prossima invocazione del metodo
					//"action" del Wrapper) se è possibile effettuare una transizione
					block();
				}
				else{ //è stata effettuata una transizione
					transitionExecuted = true;

					//eseguiamo il metodo "action" della transizione effettuata, giusto prima di uscire dal metodo "action" del Wrapper
					DSCStarNormalTransition transition = (DSCStarNormalTransition) list.get(0);
					ACLMessage msg = (ACLMessage) list.get(1);
					DSCStarVariablesAndParameters sourceVariablesAndParameters = (DSCStarVariablesAndParameters) list.get(2);
					DSCStarVariablesAndParameters rootVariablesAndParameters = (DSCStarVariablesAndParameters) list.get(3);
					transition.action(msg, sourceVariablesAndParameters, rootVariablesAndParameters);
				}
			}
			else {
				//altrimenti, si verifica se è possibile effettuare delle transizioni
				//ed eventualmente si chiama ancora il metodo "action" del behaviour associato
				java.util.ArrayList list = findAndFireTransition();
				if(list == null){
					transitionExecuted = false;
					if (firstActionInvocation == true) {
						firstActionInvocation = false; //indichiamo che il metodo "action" del behaviour associato è stato invocato almeno una volta
					}
					
					//eseguiamo il metodo "action" del behaviour associato, giusto prima di uscire dal metodo "action" del Wrapper
					behaviour.action();
				}
				else{
					transitionExecuted = true;

					//eseguiamo il metodo "action" della transizione effettuata, giusto prima di uscire dal metodo "action" del Wrapper
					DSCStarNormalTransition transition = (DSCStarNormalTransition) list.get(0);
					ACLMessage msg = (ACLMessage) list.get(1);
					DSCStarVariablesAndParameters sourceVariablesAndParameters = (DSCStarVariablesAndParameters) list.get(2);
					DSCStarVariablesAndParameters rootVariablesAndParameters = (DSCStarVariablesAndParameters) list.get(3);
					transition.action(msg, sourceVariablesAndParameters, rootVariablesAndParameters);
				}
			}
		}

		public boolean done() {
			if (behaviour.done() == true && finalStates.contains(behaviour) == false) {
				//se il metodo "done" del behaviour associato restituisce TRUE
				//ed il behaviour associato NON è uno stato finale,
				//il metodo "done" del wrapper restituisce FALSE,
				//in modo che successivamente sarà possibile chiamare
				//ancora il metodo "action" del wrapper, il quale
				//provvederà soltanto a verificare se è possibile
				//effettuare delle transizioni, senza chiamare ancora
				//il metodo "action" del behaviour associato (che ha
				//già terminato)
				return false;
			}
			else if (finalStates.contains(behaviour) == true && transitionExecuted == true) {
				//se il behaviour associato è uno stato finale e
				//nel metodo "action" del wrapper è stata eseguita una transizione,
				//il metodo "done" del wrapper restituisce FALSE,
				//così non viene chiamato di nuovo il metodo "onEnd" (che viene
				//già chiamato nel metodo incaricato di effettuare la transizione)
				return false;
			}
			//altrimenti, si restituisce il valore restituito dal
			//metodo "done" del behaviour associato
			return behaviour.done();
		}

		public void onStart() {
			behaviour.onStart();
		}

		public int onEnd() {
			int result = behaviour.onEnd();
			return result;
		}

		public void reset() {
			// We have to reset ourselves so when we come around to execute the state
			// again, we fire the onStart properly.
			super.reset();
			// Now we reset the behaviour for the same reason.
			behaviour.reset(); //ATTENZIONE: per rieseguire dall'inizio il behaviour associato quando entriamo di nuovo in questo stato, il metodo "reset" del behaviour associato deve essere implementato in modo tale da azzerare tutte le variabili che devono riprendere dal valore iniziale (come se l'oggetto venisse ricreato da capo) e bisogna chiamare al suo interno il metodo "super.reset()" della classe "Behaviour"

			//resettiamo anche il valore delle variabili booleane di Wrapper
			firstActionInvocation = true;
			transitionExecuted = false;
		}

		public Behaviour getBehaviour() {
			return behaviour; //restituisce il behaviour associato
		}

		public void setAgent(Agent a) {
			super.setAgent(a);
			behaviour.setAgent(a);
		}
	}
	
	private class VirtualInstance implements Serializable { //questa inner class permette di rappresentare un'istanza virtuale di uno stato composto (implementa "Serializable" perchè serve per la migrazione dell'agente)
		
		private Behaviour currentState; //stato corrente dell'istanza
		private Behaviour lastVisitedSubState; //ultimo sotto-stato visitato dall'istanza (serve per utilizzare la storia)
		private DSCStarVariablesAndParameters variablesAndParameters; //oggetto che contiene le variabili ed i parametri di questa istanza
		
		public VirtualInstance(){
			this.currentState = null; //lo stato corrente dell'istanza è inizialmente uguale a NULL
			this.lastVisitedSubState = null; //l'ultimo sotto-stato visitato dall'istanza è inizialmente uguale a NULL
			if(dscStarVariablesAndParameters != null){
				//se lo stato composto è diverso dalla root, copiamo l'oggetto che contiene
				//le variabili ed i parametri (con i valori iniziali) dello stato composto
				this.variablesAndParameters = (DSCStarVariablesAndParameters) dscStarVariablesAndParameters.clone();
				
				//NOTA: se lo stato composto è la root, non bisogna copiare l'oggetto che contiene
				//le variabili ed i parametri della root, perchè si tratta delle variabili
				//globali e bisogna utilizzare sempre l'ultimo valore di queste variabili;
				//quindi, per evitare di copiare questo oggetto, all'atto della creazione
				//della prima e unica istanza della root, abbiamo che la variabile
				//"dscStarVariablesAndParameters" è uguale a NULL; successivamente, la variabile
				//"dscStarVariablesAndParameters" viene settata con l'oggetto variabili della root
				//e si utilizza sempre questa variabile. Non possiamo fare un controllo qui
				//per vedere se lo stato composto è una root, perchè all'atto della creazione
				//della prima istanza (nel costruttore dello stato composto) gli stati composti
				//risultano momentaneamente tutti root.
			}
		}

		protected Behaviour getCurrentState() {
			return currentState;
		}

		protected void setCurrentState(Behaviour currentState) {
			this.currentState = currentState;
		}

		protected Behaviour getLastVisitedSubState() {
			return lastVisitedSubState;
		}

		protected void setLastVisitedSubState(Behaviour lastVisitedSubState) {
			this.lastVisitedSubState = lastVisitedSubState;
		}

		protected DSCStarVariablesAndParameters getVariablesAndParameters() {
			return variablesAndParameters;
		}
		
	}

}
