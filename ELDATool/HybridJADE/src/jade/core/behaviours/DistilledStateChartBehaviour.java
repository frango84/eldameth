/*****************************************************************
"DistilledStateChartBehaviour" is a work based on the library "HSMBehaviour"
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

import jade.lang.acl.*;
import jade.util.leap.*;
import jade.core.*;

/** DistilledStateChartBehaviour: a DSC using behaviours as states.
 * <p>
 * It is a <code>CompositeBehaviour</code> that executes its children
 * behaviours according to a DSC defined by the user. More specifically
 * each child represents a state in the DSC.
 * The class provides methods to add states (sub-behaviours) and
 * transitions that defines how sub-behaviours will be scheduled.
 * <p> At a minimum, the following steps are needed in order to properly
 * define a <code>DistilledStateChartBehaviour</code>:
 * <ul>
 * <li> add a single behaviour as the initial state of the DSC by calling
 * the method <code>addInitialState</code>;
 * <li> add one or more behaviours as the final states of the DSC
 * by calling the method <code>addFinalState</code>;
 * <li> add one or more behaviours as the intermediate states of the DSC
 * by calling the method <code>addState</code>;
 * <li> for each state of the DSC (or from the DSC itself), add the transitions to the other
 * states by calling the method <code>addTransition</code>;
 * </ul>
 * A number of other methods are available in this class for generic tasks.
 *
 * @author G. Fortino, F. Rango
 */
public class DistilledStateChartBehaviour extends CompositeBehaviour {

	private Behaviour current = null; //stato corrente di questo DistilledStateChartBehaviour (serve anche per entrare sempre con storia profonda nel DistilledStateChartBehaviour, infatti se ogni DistilledStateChartBehaviour memorizza il suo stato corrente è possibile raggiungere sempre lo stato foglia corrente ogni volta che si entra nel DistilledStateChartBehaviour root)

	private Behaviour lastState = null; //ultimo stato visitato da questo DistilledStateChartBehaviour prima di uscire dal DistilledStateChartBehaviour (viene utilizzato per la storia)

	private Behaviour defaultShallowHistoryEntrance = null; //Default Shallow History Entrance di questo DistilledStateChartBehaviour, utilizzata quando entriamo in questo DistilledStateChartBehaviour con storia shallow e questo DistilledStateChartBehaviour non è stato mai visitato in precedenza
	
	private Behaviour defaultDeepHistoryEntrance = null; //Default Deep History Entrance di questo DistilledStateChartBehaviour, utilizzata quando entriamo in questo DistilledStateChartBehaviour con storia deep e questo DistilledStateChartBehaviour non è stato mai visitato in precedenza
	
	private Map states = new HashMap(); //maps between actual behaviour and wrapped behaviour.

	private List finalStates = new ArrayList(); //stati finali di questo DistilledStateChartBehaviour

	private Map transitions = new HashMap(); //mapping from state to list of transitions.

	private Behaviour initialStateBehaviour = null; //stato iniziale di tipo normale (cioè, diverso da H o H*) di questo DistilledStateChartBehaviour

	private int initialStateHistory = DistilledStateChartTransition.NO_HISTORY; //stato iniziale di tipo H o H* di questo DistilledStateChartBehaviour
	
	private int lastExitValue;

	private boolean putbackMessage = true; //utilizzato per la politica di gestione dei messaggi

	private MessageTemplate mainTemplate = null; //template tramite cui vengono ricevuti i messaggi nel DSC (utilizzato per la politica di gestione dei messaggi)

	/** Default constructor, does not set the owner agent.
	 * The owner agent has got to be set before much else is done (setAgent can be
	 * called to do this).
	 */
	public DistilledStateChartBehaviour() {
		super();
	}

	/** This constructor sets the owner agent.
	 * @param anAgent the agent this behaviour belongs to.
	 */
	public DistilledStateChartBehaviour(Agent anAgent) {
		super(anAgent);
	}

	/** This constructor sets the owner agent and name of the behaviour (state).
	 * @param anAgent the agent this behaviour belongs to.
	 * @param aName the name of the behaviour.
	 */
	public DistilledStateChartBehaviour(Agent anAgent, String aName) {
		super(anAgent);
		setBehaviourName(aName);
	}

	/** This constructor sets the owner agent, name and MessageTemplate.
	 * @param anAgent the agent this behaviour belongs to.
	 * @param aName the name of the behaviour.
	 * @param aTemplate the main template
	 */
	public DistilledStateChartBehaviour(Agent anAgent, String aName, MessageTemplate aTemplate) {
		super(anAgent);
		setBehaviourName(aName);
		setMainTemplate(aTemplate);
	}

	/** This constructor sets the owner agent, name and sets the putback mode.
	 * @param anAgent the agent this behaviour belongs to.
	 * @param aName the name of the behaviour.
	 * @param aPutback the putback mode
	 */
	public DistilledStateChartBehaviour(Agent anAgent, String aName, boolean aPutback) {
		super(anAgent);
		setBehaviourName(aName);
		setPutback(aPutback);
	}

	/** This constructor sets the owner agent, name, MessageTemplate and the putback mode.
	 * @param anAgent the agent this behaviour belongs to.
	 * @param aName the name of the behaviour.
	 * @param aTemplate the main template
	 * @param aPutback the putback mode
	 */
	public DistilledStateChartBehaviour(Agent anAgent, String aName, MessageTemplate aTemplate, boolean aPutback) {
		super(anAgent);
		setBehaviourName(aName);
		setMainTemplate(aTemplate);
		setPutback(aPutback);
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

	/** Add a <code>Behaviour</code> as a state of this <code>DistilledStateChartBehaviour</code>.
	 * When the DSC reaches this state the added <code>Behaviour</code> will be
	 * executed. If the added state is a DistilledStateChartBehaviour, then we check to see if it
	 * has any transitions and take them out of the state and put them into our own DSC parent.
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
		
		if (states.containsKey(state)) {
			// We are already wrapped and added.
			return;
		}
		if (!(state instanceof DistilledStateChartBehaviour)) {
			state.setParent(this); //settiamo il parent
			Behaviour wrappedState = new Wrapper(state, myAgent);
			wrappedState.setBehaviourName(state.getBehaviourName() + "[Wrapped]");
			states.put(state, wrappedState);
			//debugPrint(1, " added state: " + wrappedState.getBehaviourName());
		}
		else {
			DistilledStateChartBehaviour dsc = (DistilledStateChartBehaviour) state;
			states.put(state, state);
			dsc.setPutback(putbackMessage);
			dsc.setMainTemplate(mainTemplate);
			
			// We have to take all of the transitions that may already exist in the
			// state that we are adding and stuff them into the root. This way if we
			// add an already existing DSC, all of the transitions will end up in the
			// root.
			
			Collection stateTransitions = dsc.getStatesWithTransitions(); //preleviamo tutte le eventuali transizioni presenti nello stato da aggiungere
																		//(da notare che questo stato contiene delle transizioni soltanto se è una root)
			Iterator states = stateTransitions.iterator();
			while (states.hasNext()) {
				Behaviour source = (Behaviour) states.next();
				Iterator theTransitions = dsc.getTransitions(source);
				while (theTransitions.hasNext()) {
					DistilledStateChartTransition trans = (DistilledStateChartTransition) theTransitions.next();
					addTransition(trans, source); //aggiungiamo la transizione corrente in questo (this) DistilledStateChartBehaviour
				}
			}
			dsc.removeAllTransitions();	//eliminiamo tutte le eventuali transizioni presenti nello stato da aggiungere
										//(da notare che questo stato contiene delle transizioni soltanto se è una root)
			
			//debugPrint(1, " added state: " + state.getBehaviourName());

			/* IT IS VERY IMPORTANT THAT WE DO THIS setParent LAST!!!!  The reason is that the code
			 * above for adding in an existing DistilledStateChartBehaviour, must grab all of the existing
			 * transitions and stuff them into our root.  To do this, we call the getTransitions
			 * code.  Well, getTransitions traverses to the root to find the match.  If we
			 * have already grafted the state into the tree, then we will skip over the ones
			 * that are stored in the argument DSC.  So, to get around this, what we do is
			 * first grab all of the transitions, store them in the root, and then graft the
			 * state in.  This should work just fine, but DO NOT do the setParent until after
			 * this work has been done!!!! */
			state.setParent(this);
		}
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

	/** Add a <code>Behaviour</code> as the initial state of this <code>DistilledStateChartBehaviour</code>.
	 * @param state The <code>Behaviour</code> representing the state
	 */
	public void addInitialState(Behaviour state) {
		
		//azzeriamo l'eventuale stato iniziale di tipo H o H* presente
		initialStateHistory = DistilledStateChartTransition.NO_HISTORY;
		
		//settiamo lo stato iniziale di tipo normale
		initialStateBehaviour = state;
		addState(state);
	}
	
	/** Add a history state as the initial state of this <code>DistilledStateChartBehaviour</code>.
	 * @param history The history type: DistilledStateChartTransition.SHALLOW_HISTORY or DistilledStateChartTransition.DEEP_HISTORY
	 */
	public void addInitialState(int history) {
		
		//azzeriamo l'eventuale stato iniziale di tipo normale presente
		initialStateBehaviour = null;
		
		//settiamo lo stato iniziale di tipo H o H*
		initialStateHistory = history;
	}

	/** Returns the initial state of this <code>DistilledStateChartBehaviour</code>
	 * (the initial state returned is a Behaviour or an Integer that represents an history state
	 * of type DistilledStateChartTransition.SHALLOW_HISTORY or DistilledStateChartTransition.DEEP_HISTORY).
	 * @return the initial state of this DistilledStateChartBehaviour.
	 */
	public Object getInitialState() {
		if(initialStateBehaviour != null){
			//stato iniziale di tipo normale
			return initialStateBehaviour;
		}
		
		//stato iniziale di tipo H o H*
		return new Integer(initialStateHistory);
	}

	/** Add a <code>Behaviour</code> as a final state of this <code>DistilledStateChartBehaviour</code>.
	 * When the DSC reaches this state
	 * the added <code>Behaviour</code> will be executed and,
	 * when completed, the <code>DistilledStateChartBehaviour</code> will terminate too.
	 * @param state The <code>Behaviour</code> representing the state
	 */
	public void addFinalState(Behaviour state) {
		addState(state);
		if (!finalStates.contains(state)) {
			finalStates.add(state);
		}
	}

	/** Returns the final states assocated with this <code>DistilledStateChartBehaviour</code>.
	 * @return An iterator of all of the final states of this DSC.
	 */
	public Iterator getFinalStates() {
		return finalStates.iterator();
	}

	/** Returns the number of final states associated with this <code>DistilledStateChartBehaviour</code>.
	 * @return How many final states there are in this DSC.
	 */
	public int getFinalStateCount() {
		return finalStates.size();
	}

	/** Returns the final state at the given index.
	 * @param index The index of one of the final states in this DSC.
	 * @return Given an index, returns the specific final state.
	 */
	public Behaviour getFinalState(int index) {
		return (Behaviour) finalStates.get(index);
	}

	/** Delete the argument state from this DSC.
	 * If the state is an initial state or a final state, we remove it too.  Note -
	 * we currently do not delete any transitions associated with this state.  We
	 * could and probably should at least delete those that are sourced by this state.
	 * But then again, it is up to the user to take care of this.
	 * @param state the behaviour to delete.
	 * @exception RuntimeException if the state that we attempt to remove is not defined in the DSC.
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
			throw new RuntimeException("ERROR: State " + localName(state)+ " not removed! It is not in DSC!");
		}
	}
	
	/** Delete the initial history state (H or H*) from this DSC.
	 */
	public void removeInitialStateHistory(){
		initialStateHistory = DistilledStateChartTransition.NO_HISTORY;
	}

	/** Adds the argument transition to the list of transitions in the root <code>DistilledStateChartBehaviour</code>.
	 * Calls "addTransition" with the "this" as the state argument.
	 * @param trans the transition that we are adding.
	 */
	public void addTransition(DistilledStateChartTransition trans) {
		addTransition(trans, this);
	}

	/** Adds the argument transition to the list of transitions in the root <code>DistilledStateChartBehaviour</code>.
	 * @param trans the transition that we are adding
	 * @param source the source of the transition
	 */
	public void addTransition(DistilledStateChartTransition trans, Behaviour source) {
		if (!rootP()) {
			((DistilledStateChartBehaviour) getParent()).addTransition(trans, source);
		}
		else {
			// Well, if I'm the source, then there is no possible problem with the source.
			// If I'm not the source, then we really need to know if the source is actually
			// in the DSC. But that forces that the users to addState first. Needs some decision.

			//debugPrint(1, "Transition: added " + getBehaviourName() + "." + trans.getTransitionName() + " (" + source.getBehaviourName() + "->" + trans.getTarget().getBehaviourName() + ")");
			List tList = null;
			if (transitions.containsKey(source)) {
				tList = (List) transitions.get(source);
			}
			else {
				tList = new ArrayList();
			}
			tList.add(trans);
			transitions.put(source, tList);

			//System.out.println("TRANSIZIONE " + trans.getTransitionName() + " aggiunta in " + this.getBehaviourName());

		}
	}

	/** Determines if the given transition is already added.
	 * @param aTransition Tests to see if this argument is a transition already added.
	 * @param source source of the transition.
	 * @return True if it is present, false otherwise.
	 */
	public boolean containsTransition(DistilledStateChartTransition aTransition, Behaviour source) {
		if (!rootP())
			return ((DistilledStateChartBehaviour) getParent()).containsTransition(aTransition, source);

		if (transitions.containsKey(source)) {
			List tList = (List) transitions.get(source);
			if (tList.contains(aTransition))
				return true;
		}

		return false;
	}

	/** Remove the argument transition from the set of known transitions by calling
	 * the specific removeTransition on this behaviour.
	 * @param aTransition the transition to remove.
	 * @exception RuntimeException is thrown if the argument transition isn't in the set of transitions.
	 */
	public void removeTransition(DistilledStateChartTransition aTransition) {
		removeTransition(aTransition, this);
	}

	/** Remove the argument transition from the set of known transitions.
	 * @param aTransition the transition to remove.
	 * @param source The source state that may have the transition.
	 * @exception RuntimeException is thrown if the argument transition isn't in the set of transitions.
	 */
	public void removeTransition(DistilledStateChartTransition aTransition, Behaviour source) {
		if (!rootP()){
			((DistilledStateChartBehaviour) getParent()).removeTransition(aTransition, source);
		}
		else{
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
		}
	}
	
	/** Remove all the transitions.
	 */
	public void removeAllTransitions(){
		if (!rootP()) {
			((DistilledStateChartBehaviour) getParent()).removeAllTransitions();
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
	 * Prepare the first child for execution.
	 * @see jade.core.behaviours.CompositeBehaviour#scheduleFirst
	 */
	protected void scheduleFirst() {
		//debugPrint(4, "scheduleFirst: Enter");
		
		if (getCurrent() == null) {
			initialOperations();
		}
		
		//debugPrint(4, "scheduleFirst: Exit");
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
		//debugPrint(4, "scheduleNext: Enter - currentDone: " + currentDone);
		
		if (getCurrent() == null) {
			initialOperations();
		}

		// We don't have to do anything because the transition already sets current to be the
		// correct state.
		
		//debugPrint(4, "scheduleNext: Exit");
	}

	/** This method makes the initial operations:
	 * updates current to the initial state and makes the initial action.
	 */
	private void initialOperations(){
		
		//eseguiamo l'azione iniziale, se è stata definita
		initialAction();
		
		if(initialStateBehaviour != null){
			//stato iniziale di tipo normale
			
			//aggiorniamo tutti i current a partire dal DistilledStateChartBehaviour parent
			//dello stato iniziale fino ad arrivare al DistilledStateChartBehaviour root
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
						
						//eseguiamo l'azione associata alla Default Shallow History Entrance
						defaultShallowHistoryEntranceAction();
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
					while(dest instanceof DistilledStateChartBehaviour){ //usciamo dal ciclo se abbiamo raggiunto uno stato foglia
						dest = ((DistilledStateChartBehaviour) dest).getLastState(); //preleva l'ultimo sotto-stato visitato dallo stato precedente
					}
				}
				else{
					if(getDefaultDeepHistoryEntrance() != null){
						dest = getDefaultDeepHistoryEntrance();
						
						//eseguiamo l'azione associata alla Default Deep History Entrance
						defaultDeepHistoryEntranceAction();
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
			
			//aggiorniamo tutti i current a partire dal DistilledStateChartBehaviour parent di "dest"
			//fino ad arrivare al DistilledStateChartBehaviour root
			updateCurrentTot(dest);
		}
	}
	
	/** This method is normally overridden and indicates the action related to the initial state
	 * of the DSC to execute when we enter the DSC and we have to leave again from the initial state
	 * (it represents the existing action on the transition that connects the black circle to the
	 * initial state in the DSC). If this action isn't defined, it isn't executed.
	 */
	public void initialAction(){
		//Questo metodo viene normalmente ridefinito ed indica l'azione associata allo stato iniziale
		//del DistilledStateChartBehaviour da eseguire quando si entra nel DistilledStateChartBehaviour
		//e bisogna ripartire dallo stato iniziale (rappresenta l'azione presente sulla transizione che
		//collega il cerchietto nero allo stato iniziale nel DSC). Questa azione NON deve essere definita
		//per forza: se non viene definita, non viene eseguita.
	}

	/** This method is normally overridden and indicates the action related to the
	 * Default Shallow History Entrance of the DSC. If this action isn't defined, it isn't executed.
	 */
	public void defaultShallowHistoryEntranceAction(){
		//Questo metodo viene normalmente ridefinito ed indica l'azione associata alla
		//Default Shallow History Entrance del DistilledStateChartBehaviour. Questa azione
		//NON deve essere definita per forza: se non viene definita, non viene eseguita.
	}
	
	/** This method is normally overridden and indicates the action related to the
	 * Default Deep History Entrance of the DSC. If this action isn't defined, it isn't executed.
	 */
	public void defaultDeepHistoryEntranceAction(){
		//Questo metodo viene normalmente ridefinito ed indica l'azione associata alla
		//Default Deep History Entrance del DistilledStateChartBehaviour. Questa azione
		//NON deve essere definita per forza: se non viene definita, non viene eseguita.
	}

	/** This method creates the root DistilledStateChartBehaviour using
	 * the DistilledStateChartBehaviour passed as parameter,
	 * in agreement to the DSC template with initial deep history state.
	 */
	public static DistilledStateChartBehaviour createRootForDSCTemplate(DistilledStateChartBehaviour dsc){

		//questo metodo statico permette di creare il DistilledStateChartBehaviour root
		//in accordo al template DSC, cioè riceve come parametro il DistilledStateChartBehaviour
		//creato a proprio piacimento (con i vari stati e le transizioni) e genera la root
		//che ha come stato iniziale il connettore di storia profonda presente nello stato "ACTIVE" del
		//template DSC e come Default Deep History Entrance il DistilledStateChartBehaviour passato come parametro
		
		//creiamo la root con le stesse caratteristiche del DistilledStateChartBehaviour passato come parametro
		DistilledStateChartBehaviour root = new DistilledStateChartBehaviour(dsc.myAgent, "dsc_root", dsc.getMainTemplate(), dsc.getPutback());
		
		//aggiungiamo lo stato iniziale di tipo H*
		root.addInitialState(DistilledStateChartTransition.DEEP_HISTORY);
		
		//aggiungiamo il DistilledStateChartBehaviour passato come parametro
		//(le transizioni esistenti nel DistilledStateChartBehaviour passato
		//come parametro verranno spostate nella root)
		root.addState(dsc);
		
		//settiamo la Default Deep History Entrance al DistilledStateChartBehaviour passato come parametro
		root.setDefaultDeepHistoryEntrance(dsc);
		
		return root;
	}
	
	/** Test to see if I am a root.
	 * @return true if a root, false otherwise.
	 */
	private boolean rootP() {
		return (getParent() == null) || !(getParent() instanceof DistilledStateChartBehaviour);
	}

	/** Test to see if argument is a root.
	 * @param state The state to check.
	 * @return true if a root, false otherwise.
	 */
	private boolean rootP(Behaviour state) {
		return (state.parent == null) || !(state.parent instanceof DistilledStateChartBehaviour);
	}

	/** Check for transitions and fire.
	 * Essentially this code goes through the hierarchy looking for transitions
	 * to see if any return true. If they do, then we fire the transition and
	 * set up current to point to the new state.
	 * @return an ArrayList that contains the transition that we fired (position 0),
	 * the corresponding message (position 1) and String that indicates if we have to
	 * execute the action associated to the Default Deep/Shallow History Entrance (position 2),
	 * or null if we haven't fired any transition.
	 */
	private java.util.ArrayList findAndFireTransition() {

		//System.out.println("verifica delle transizioni avviata (chiamato metodo findAndFireTransition) ...");

		//get an event and test the transitions.
		ACLMessage msg = myAgent.receive(mainTemplate);	// Se il "mainTemplate" è stato assegnato, il DSC riceve ed utilizza soltanto i messaggi che rispettano il
														// "mainTemplate" assegnato; cioè, il DSC utilizza soltanto quei messaggi inviati appositamente per lui ed ignora
														// tutti gli altri messaggi inviati per essere utilizzati da qualche altro behaviour dell'agente corrente.
														// Mentre, se il "mainTemplate" è uguale a NULL, il DSC riceve ed utilizza tutti i messaggi inviati all'agente corrente
														// senza alcun filtro.
		if (msg != null){
			java.util.ArrayList l = searchThenFireTransition(((Wrapper) getCurrent()).getBehaviour(), msg);
			if (l != null){
				java.util.ArrayList list = new java.util.ArrayList();
				list.add(l.get(0)); //transition fired = position 0 of ArrayList
				list.add(msg); //message = position 1 of ArrayList
				list.add(l.get(1)); //String that indicates if we have to execute the action associated to the Default Deep/Shallow History Entrance = position 2 of ArrayList
				return list;
			}
			else {
				//debugPrint(4, "findAndFireTransition: putBack");
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
		
		//debugPrint(4, "findAndFireTransition: Exit - no transition fired");
		return null; //NON abbiamo trovato nessuna transizione effettuabile
	}

	/** Search for a transition on the current message argument and fire if you can.
	 * @param state the Behaviour that we are to look for a match. Note: initially
	 * the state is this, but as we traverse up the tree it changes to parent DSCs.
	 * @param msg the message to attempt to match.
	 * @return an ArrayList that contains the transition that we fired (position 0)
	 * and the String that indicates if we have to execute the action associated to the
	 * Default Deep/Shallow History Entrance (position 1), or null if we haven't fired any transition.
	 */
	private java.util.ArrayList searchThenFireTransition(Behaviour state, ACLMessage msg) {
		
		//System.out.println("invocato metodo searchThenFireTransition ...");
		
		Iterator it = getTransitions(state);
		int tranCount = getTransitionCount(state);
		//debugPrint(4, "searchThenFireTransition: Enter - State: "+ state.getBehaviourName() + " Trans count: " + tranCount);
		if (tranCount != 0) {
			while (it.hasNext()) {
				DistilledStateChartTransition trans = (DistilledStateChartTransition) it.next();
				boolean canFire = false;
				try {
					//debugPrint(4, "searchThenFireTransition: Triggering: " + trans.getTransitionName());
					canFire = trans.trigger(state, msg);
					//debugPrint(4, "searchThenFireTransition: Trigger " + (canFire ? "succeeded" : "failed") + " " + trans.getTransitionName());
					if (canFire) {
						String c = fireTransition(msg, state, trans);
						java.util.ArrayList list = new java.util.ArrayList();
						list.add(trans); //pos. 0
						list.add(c); //pos. 1
						return list;
					}
				}
				catch (java.lang.NullPointerException e) {
					e.printStackTrace();
					System.out.println("ERROR: Your trigger code in " + trans.getTransitionName() + " failed with a null pointer!");
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
	 * @return a String that indicates if we have to execute
	 * the action associated to the Default Deep/Shallow History Entrance.
	 */
	private String fireTransition(ACLMessage msg, Behaviour source, DistilledStateChartTransition trans) {
		
		//System.out.println("invocato metodo fireTransition ...");
		
		String c = "N"; //stringa che viene restituita per indicare se bisogna eseguire l'azione associata alla Default Deep/Shallow History Entrance

		//prima di effettuare la transizione, cioè prima di uscire dai vari stati coinvolti,
		//settiamo gli ultimi stati visitati partendo dal DistilledStateChartBehaviour parent dello
		//stato foglia attuale fino ad arrivare al DistilledStateChartBehaviour root
		//(gli ultimi stati visitati vengono utilizzati per la storia)
		Behaviour corr = ((Wrapper) getCurrent()).getBehaviour();
		String storia = corr.getBehaviourName();
		while(rootP(corr) == false){
			((DistilledStateChartBehaviour) corr.parent).setLastState(corr);
			storia = corr.parent.getBehaviourName() + " -> " + storia;
			corr = corr.parent;
		}
		storia = "STORIA MEMORIZZATA PRIMA DELLA TRANSIZIONE: " + storia;
		//System.out.println(storia);

		Behaviour dest = null; //stato di destinazione
		if(trans.getHistory() == DistilledStateChartTransition.NO_HISTORY){
			//se la transizione NON utilizza la storia, lo stato di destinazione da utilizzare è
			//quello memorizzato nella transizione
			dest = trans.getTarget();
		}
		else if(trans.getHistory() == DistilledStateChartTransition.SHALLOW_HISTORY){
			//se la transizione utilizza la storia superficiale, lo stato di destinazione da utilizzare è
			//l'ultimo sotto-stato visitato dallo stato target della transizione prima di uscire,
			//senza ripristinare ricorsivamente i sotto-stati di quest'ultimo sotto-stato
			
			DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) trans.getTarget(); //quando la transizione utilizza la storia, il suo stato target è sempre un DistilledStateChartBehaviour
			if(target.getLastState() != null){
				dest = target.getLastState(); //preleva l'ultimo sotto-stato visitato dallo stato target della transizione prima di uscire
			}
			else{
				//se "target.getLastState() == NULL" (cioè, se lo stato target non ha memorizzato
				//l'ultimo sotto-stato visitato perchè non ci siamo mai entrati in precedenza),
				//bisogna raggiungere uno stato di default e ci possono essere due casi:
				//	1) 	se la "Default Shallow History Entrance" dello stato target è stata definita,
				//		utilizziamo la "Default Shallow History Entrance" dello stato target e riprendiamo
				//		l'esecuzione dal sotto-stato iniziale della "Default Shallow History Entrance"
				//	2)	se la "Default Shallow History Entrance" dello stato target NON è stata definita,
				//		utilizziamo come default direttamente lo stato target e, di conseguenza,
				//		il suo sotto-stato iniziale
				
				if(target.getDefaultShallowHistoryEntrance() != null){
					dest = target.getDefaultShallowHistoryEntrance();
					
					//indichiamo che bisogna eseguire l'azione associata alla Default Shallow History Entrance
					c = "S";
				}
				else{
					dest = target;
				}
			}
			
			//System.out.println("STATO DI DESTINAZIONE SHALLOW HISTORY: " + dest.getBehaviourName());
			
		}
		else if(trans.getHistory() == DistilledStateChartTransition.DEEP_HISTORY){
			//se la transizione utilizza la storia profonda, lo stato di destinazione da utilizzare è
			//l'ultimo sotto-stato visitato dallo stato target della transizione prima di uscire,
			//ripristinando anche ricorsivamente tutti i sotto-stati di quest'ultimo sotto-stato
			//(fino a quando viene raggiunto un sotto-stato foglia)

			DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) trans.getTarget(); //quando la transizione utilizza la storia, il suo stato target è sempre un DistilledStateChartBehaviour
			if(target.getLastState() != null){
				dest = target;
				while(dest instanceof DistilledStateChartBehaviour){ //usciamo dal ciclo se abbiamo raggiunto uno stato foglia
					dest = ((DistilledStateChartBehaviour) dest).getLastState(); //preleva l'ultimo sotto-stato visitato dallo stato precedente
				}
			}
			else{
				//se "target.getLastState() == NULL" significa che lo stato target non ha memorizzato
				//l'ultimo sotto-stato visitato, perchè non ci siamo mai entrati in precedenza e,
				//di conseguenza, questo significa anche che sicuramente nessuno dei sotto-stati dello
				//stato target è stato visitato in precedenza e, per questo, sicuramente nessuno dei
				//sotto-stati dello stato target ha memorizzato l'ultimo sotto-stato visitato
				//(infatti, quando usciamo da uno stato qualsiasi, vengono memorizzati tutti gli
				//ultimi stati visitati partendo dal DistilledStateChartBehaviour parent dello
				//stato foglia attuale fino ad arrivare al DistilledStateChartBehaviour root).
				//Quindi, in queste condizioni, bisogna raggiungere uno stato di default e ci possono
				//essere due casi:
				//	1) 	se la "Default Deep History Entrance" dello stato target è stata definita,
				//		utilizziamo la "Default Deep History Entrance" dello stato target e riprendiamo
				//		l'esecuzione dal sotto-stato iniziale della "Default Deep History Entrance"
				//	2)	se la "Default Deep History Entrance" dello stato target NON è stata definita,
				//		utilizziamo come default direttamente lo stato target e, di conseguenza,
				//		il suo sotto-stato iniziale
				
				if(target.getDefaultDeepHistoryEntrance() != null){
					dest = target.getDefaultDeepHistoryEntrance();
					
					//indichiamo che bisogna eseguire l'azione associata alla Default Deep History Entrance
					c = "D";
				}
				else{
					dest = target;
				}
			}
			
			//System.out.println("STATO DI DESTINAZIONE DEEP HISTORY: " + dest.getBehaviourName());
			
		}

		cleanUpToCommonParent(((Wrapper) getCurrent()).getBehaviour(), dest);
		
		//impostiamo tutti i current a NULL, partendo dal DistilledStateChartBehaviour parent dello
		//stato foglia attuale fino ad arrivare al DistilledStateChartBehaviour root, in modo che quando
		//ritorniamo eventualmente in questi DistilledStateChartBehaviour senza storia, riprendiamo dal
		//loro stato iniziale
		updateCurrentTot(null);
		
		//aggiorniamo tutti i current a partire dal DistilledStateChartBehaviour parent di "dest"
		//fino ad arrivare al DistilledStateChartBehaviour root.
		updateCurrentTot(dest);
		
		return c;
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
		if (source instanceof DistilledStateChartBehaviour) {
			source.onEnd();
			source.reset();
		}
		else {
			Behaviour wrapped = ((DistilledStateChartBehaviour) source.parent).getWrappedState(source);
			wrapped.onEnd();
			wrapped.reset();
		}
		if (source.parent instanceof DistilledStateChartBehaviour) {
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
			throw new RuntimeException("ERROR: Illegal DSC! No Common Parent Found!");
		}
	}

	/** Tests if the source parent matches any parent up the tree of the target.
	 * Eventually if we reach where there is no DistilledStateChartBehaviour as a parent, then we
	 * return false. If along the way we find a match, then we return true.
	 * @param sourcesParent The DistilledStateChartBehaviour parent that we are trying to match.
	 * @param target The behaviour that we scan.
	 * @return True if it found a match some place and false otherwise.
	 */
	private boolean sourceMatchesAnyParent(Behaviour sourcesParent, Behaviour target) {
		if (target.parent instanceof DistilledStateChartBehaviour) {
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

	/**
	 * Check whether this <code>DistilledStateChartBehaviour</code> must terminate.
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
	
	/** Get the current child.
	 * @return The behaviour that is current.
	 * @see jade.core.behaviours.CompositeBehaviour#getCurrent
	 */
	protected Behaviour getCurrent() {
		return this.current;
	}
	
	/** Establish who is the current state.
	 * @param state The state to set the current to.
	 */
	protected void setCurrent(Behaviour state) {
		if(state != null){
			this.current = (Behaviour) states.get(state);
		}
		else{
			this.current = null;
		}
	}

	/** Updates all the current starting from the <code>DistilledStateChartBehaviour</code> parent
	 * of the state passed as parameter up to the root <code>DistilledStateChartBehaviour</code>.
	 * If the state passed as parameter is null, all the current are setted to null,
	 * starting from the <code>DistilledStateChartBehaviour</code> parent of the actual leaf state
	 * up to the root <code>DistilledStateChartBehaviour</code>.
	 * @param state The state to set the current to, updating also the current of superior behaviours.
	 */
	private void updateCurrentTot(Behaviour state){
		
		//Questo metodo permette di aggiornare tutti i current a partire dal DistilledStateChartBehaviour
		//parent dello stato passato come parametro fino ad arrivare al DistilledStateChartBehaviour root.
		//Se lo stato passato come parametro è uguale a NULL, tutti i current vengono
		//settati a NULL, partendo dal DistilledStateChartBehaviour parent dello
		//stato foglia attuale fino ad arrivare al DistilledStateChartBehaviour root.
		
		if(state == null){
			Behaviour behaviour = ((Wrapper) getCurrent()).getBehaviour();
			while(rootP(behaviour) == false){
				((DistilledStateChartBehaviour) behaviour.parent).setCurrent(null);
				behaviour = behaviour.parent;
			}
		}
		else{
			Behaviour behaviour = state;
			while(rootP(behaviour) == false){
				((DistilledStateChartBehaviour) behaviour.parent).setCurrent(behaviour);
				behaviour = behaviour.parent;
			}
		}
	}

	/** Get last state visited by this <code>DistilledStateChartBehaviour</code>
	 * before going out of the <code>DistilledStateChartBehaviour</code> (it's used for history).
	 * @return Behaviour that represents last state.
	 */
	private Behaviour getLastState() {
		return lastState;
	}

	/** Set last state visited by this <code>DistilledStateChartBehaviour</code>
	 * before going out of the <code>DistilledStateChartBehaviour</code> (it's used for history).
	 * @param lastState Behaviour that represents last state.
	 */
	private void setLastState(Behaviour lastState) {
		this.lastState = lastState;
	}
	
	/** Get Default Shallow History Entrance of this <code>DistilledStateChartBehaviour</code>.
	 * @return Behaviour that represents Default Shallow History Entrance.
	 */
	public Behaviour getDefaultShallowHistoryEntrance() {
		return defaultShallowHistoryEntrance;
	}

	/** Set Default Shallow History Entrance of this <code>DistilledStateChartBehaviour</code>.
	 * @param state Behaviour that represents Default Shallow History Entrance.
	 */
	public void setDefaultShallowHistoryEntrance(Behaviour state) {
		if (states.containsKey(state)) {
			this.defaultShallowHistoryEntrance = state;
		}
		else{
			//se lo stato passato come parametro NON è un sotto-stato di questo DistilledStateChartBehaviour, lanciamo un'eccezione
			throw new RuntimeException("ERROR: chosen Default Shallow History Entrance isn't a state of corresponding DistilledStateChartBehaviour, add first states to DistilledStateChartBehaviour and then select its Default Shallow History Entrance!");
		}
	}
	
	/** Get Default Deep History Entrance of this <code>DistilledStateChartBehaviour</code>.
	 * @return Behaviour that represents Default Deep History Entrance.
	 */
	public Behaviour getDefaultDeepHistoryEntrance() {
		return defaultDeepHistoryEntrance;
	}

	/** Set Default Deep History Entrance of this <code>DistilledStateChartBehaviour</code>.
	 * @param state Behaviour that represents Default Deep History Entrance.
	 */
	public void setDefaultDeepHistoryEntrance(Behaviour state) {
		if (states.containsKey(state)) {
			this.defaultDeepHistoryEntrance = state;
		}
		else{
			//se lo stato passato come parametro NON è un sotto-stato di questo DistilledStateChartBehaviour, lanciamo un'eccezione
			throw new RuntimeException("ERROR: chosen Default Deep History Entrance isn't a state of corresponding DistilledStateChartBehaviour, add first states to DistilledStateChartBehaviour and then select its Default Deep History Entrance!");
		}
	}

	/** Return a Collection view of the children behaviours of this <code>DistilledStateChartBehaviour</code>.
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
			return ((DistilledStateChartBehaviour) getParent()).getTransitions(source);
		}
		List tList = new ArrayList();
		if (transitions.containsKey(source)) {
			tList = (List) transitions.get(source);
		}
		return tList.iterator();
	}

	/** Returns the source behaviour of the given transition. */
	public Behaviour getTransitionSource(DistilledStateChartTransition t) {
		if (!rootP())
			return ((DistilledStateChartBehaviour) getParent()).getTransitionSource(t);

		Iterator keys = transitions.keySet().iterator();
		while (keys.hasNext()) {
			Behaviour currentSrc = (Behaviour) keys.next();
			List transList = (List) transitions.get(currentSrc);
			if (transList.contains(t))
				return currentSrc;
		}

		return null;
	}

	/** Returns the number of transitions with the given source state.
	 * @param source The source state of the transitions.
	 * @return The number of transitions.
	 */
	public int getTransitionCount(Behaviour source) {
		if (!rootP()) {
			return ((DistilledStateChartBehaviour) getParent()).getTransitionCount(source);
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
	public DistilledStateChartTransition getTransition(int index, Behaviour source) {
		if (!rootP()) {
			return ((DistilledStateChartBehaviour) getParent()).getTransition(index, source);
		}
		
		List tList = new ArrayList();
		if (transitions.containsKey(source)) {
			tList = (List) transitions.get(source);
		}
		
		return (DistilledStateChartTransition) tList.get(index);
	}

	/** Returns the collection of only the transitions that I know about
	 * (this method has to work in this way! don't modify!).
	 * @return The collection of transitions.
	 */
	public Collection getStatesWithTransitions() {
		return transitions.keySet();
	}

	/** Reset this <code>DistilledStateChartBehaviour</code>.
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
	 * A <code>DistilledStateChartBehaviour</code> is blocked only when
	 * its currently active child is blocked, and becomes ready again
	 * when its current child is ready. This method takes care of the
	 * various possibilities.
	 * @param rce The event to handle.
	 */
	protected void handle(RunnableChangedEvent rce) {

		//Questo metodo permette di gestire gli eventi di BLOCK e RESTART dei behaviour coinvolti.
		//ATTENZIONE: L'intero DSC risulta bloccato soltanto quando il behaviour del suo stato corrente è bloccato,
		//e si riattiva quando il behaviour del suo stato corrente si riattiva.

		if (rce.isUpwards()) {
			// Upwards notification
			if (rce.getSource() == this) {
				// If the event is from this behaviour, set the new
				// runnable state and notify upwords.
				super.handle(rce);
			}
			else if ((rce.getSource() == getCurrent()) || ((getCurrent() != null) && (getCurrent() instanceof Wrapper) && (rce.getSource() == ((Wrapper) getCurrent()).getBehaviour()))) {
				// se l'evento è da parte del behaviour dello stato corrente (di tipo DistilledStateChartBehaviour, Wrapper, o behaviour associato al Wrapper),
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
	
	/** "onEnd" method for this DSC.
	 * "onStart" and "onEnd" represent the entry/exit codes defined in the UML state
	 * machine model. They are called in a hierarchical fashion as per that model.
	 * @return the last exit value.
	 */
	public int onEnd() {
		//debugPrint(2, ".onEnd executed");
		return getLastExitValue();
	}

	/** "onStart" method for this DSC.
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
			//poco prima di uscire dal metodo "action" del Wrapper,
			//per fare in modo che eventuali operazioni come "doMove" contenute
			//all'interno del metodo "action" del behaviour associato o all'interno
			//del metodo "action" della transizione effettuata vengano eseguite
			//al termine di tutte le operazioni.
			
			//se il metodo "done" del behaviour associato restituisce TRUE
			//ed è stato invocato almeno una volta il suo metodo "action",
			//significa che il behaviour associato ha terminato, quindi
			//si verifica soltanto se è possibile effettuare una transizione
			//e, se attualmente non può essere eseguita nessuna transizione,
			//il DSC viene messo in attesa che arrivi un messaggio per
			//controllare nuovamente se è possibile effettuare una transizione
			if (behaviour.done() == true && firstActionInvocation == false) {
				java.util.ArrayList list = findAndFireTransition();
				if(list == null){ //non è stata effettuata nessuna transizione
					transitionExecuted = false;
					
					//System.out.println("DSC in attesa di un evento ...");
					
					//mettiamo in attesa il DSC fino a quando non arriva un messaggio da processare,
					//che permette di verificare nuovamente (alla prossima invocazione del metodo
					//"action" del Wrapper) se è possibile effettuare una transizione
					block();
				}
				else{ //è stata effettuata una transizione
					transitionExecuted = true;
					
					DistilledStateChartTransition transition = (DistilledStateChartTransition) list.get(0);
					ACLMessage msg = (ACLMessage) list.get(1);
					String c = (String) list.get(2);
					
					//prima eseguiamo il metodo "action" della transizione effettuata
					transition.action(msg);
					
					//poi eseguiamo eventualmente l'azione associata alla Default Deep/Shallow History Entrance
					if(c.equals("S")){
						DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) transition.getTarget();
						target.defaultShallowHistoryEntranceAction();
					}
					else if(c.equals("D")){
						DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) transition.getTarget();
						target.defaultDeepHistoryEntranceAction();
					}
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

					DistilledStateChartTransition transition = (DistilledStateChartTransition) list.get(0);
					ACLMessage msg = (ACLMessage) list.get(1);
					String c = (String) list.get(2);
					
					//prima eseguiamo il metodo "action" della transizione effettuata
					transition.action(msg);
					
					//poi eseguiamo eventualmente l'azione associata alla Default Deep/Shallow History Entrance
					if(c.equals("S")){
						DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) transition.getTarget();
						target.defaultShallowHistoryEntranceAction();
					}
					else if(c.equals("D")){
						DistilledStateChartBehaviour target = (DistilledStateChartBehaviour) transition.getTarget();
						target.defaultDeepHistoryEntranceAction();
					}
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
	
}
