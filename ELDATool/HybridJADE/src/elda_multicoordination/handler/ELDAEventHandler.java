/*****************************************************************
Multi-Coordination for JADE agents
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

package elda_multicoordination.handler;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import jade.content.onto.basic.Action;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.proto.SimpleAchieveREInitiator;
import jade.core.messaging.TopicManagementHelper;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.mobility.CloneAction;
import jade.domain.mobility.MobileAgentDescription;
import jade.domain.mobility.MobilityOntology;
import jade.domain.mobility.MoveAction;
import alice.logictuple.LogicTuple;
import alice.tucson.api.Tucson;
import alice.tucson.api.TucsonContext;
import alice.tucson.api.TupleCentreId;
import elda_multicoordination.eldaevent.*;
import elda_multicoordination.eldaevent.coordination.tuples.*;
import elda_multicoordination.eldaevent.internal.*;
import elda_multicoordination.eldaevent.management.lifecycle.*;
import elda_multicoordination.eldaevent.management.timer.*;
import elda_multicoordination.eldaevent.coordination.direct.*;
import elda_multicoordination.eldaevent.coordination.p_s.*;

/**
 * Handler for ELDA OUT-events.
 * You must add this behaviour to your agent to handle properly the ELDAEvent of type OUT.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventHandler extends CyclicBehaviour {

	private Agent myAgent;
	
	private MessageTemplate mt;
	
	private int timerCount;
	
	private int eventCount; //contatore degli eventi gestiti dall'agente corrente
	
	private java.util.Hashtable<Integer, WakerBehaviour> timers; //memorizza i timer gestiti dall'agente corrente

	private java.util.ArrayList<AID> registeredTopics; //memorizza i topic a cui è registrato l'agente corrente
	
	public ELDAEventHandler(Agent myAgent) {
		super(myAgent);
		this.myAgent = myAgent;
		this.timers = new java.util.Hashtable<Integer, WakerBehaviour>();
		this.timerCount = 0;
		this.eventCount = 0;
		this.registeredTopics = new java.util.ArrayList<AID>();
		
		//Impostiamo il MessageTemplate adatto per la ricezione dei messaggi
		//all'interno di questo behaviour. Esso riceve soltanto messaggi con
		//campo language settato a "ELDAEvent.OUT". Questo permette di escludere
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
		this.mt = MessageTemplate.MatchLanguage(ELDAEvent.OUT);
	}

	/** 
	 * This method handles a received OUT-event.
	 */
	public void action() {
		//questo metodo permette di ricevere e di gestire una richiesta
		//espressa tramite un ELDAEvent di tipo OUT

		try {
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				java.io.Serializable content = msg.getContentObject();

				if ((content != null) && (content instanceof ELDAEventMSGRequest)) {
					ELDAEventMSGRequest evt = (ELDAEventMSGRequest) content;
					
					//inviamo l'ELDAEventMSG contenuto nell'ELDAEventMSGRequest corrente
					ELDAEventMSG message = evt.getMsg();
					generate(message);
				}
				else if ((content != null) && (content instanceof ELDAEventOut)) {
					ELDAEventOut evt = (ELDAEventOut) content;
					TucsonContext ctx = Tucson.enterDefaultContext();
					TupleCentreId tc = new TupleCentreId(evt.getTupleCentreName() + "@'" + evt.getTupleCentreIPAddress() + "'");
					LogicTuple logicTuple = LogicTuple.parse(evt.getTuple());

					//execute the OUT primitive of TuCSoN
					//(to put the specified tuple in the specified tuple centre)
					ctx.out(tc, logicTuple);
				}
				else if ((content != null) && (content instanceof ELDAEventRd)) {
					ELDAEventRd evt = (ELDAEventRd) content;
					TucsonContext ctx = Tucson.enterDefaultContext();
					TupleCentreId tc = new TupleCentreId(evt.getTupleCentreName() + "@'" + evt.getTupleCentreIPAddress() + "'");
					LogicTuple logicTuple = LogicTuple.parse(evt.getTupleMatch());
					LogicTuple read = null;
					if (evt.isSync()){
						//execute the RD primitive of TuCSoN
						//(to read in blocking way a tuple matching the specified
						//tuple template from the specified tuple centre)
						read = ctx.rd(tc, logicTuple);
					}
					else{
						//execute the RDP primitive of TuCSoN
						//(to read in NON blocking way a tuple (only if present)
						//matching the specified tuple template from the specified tuple centre)
						read = ctx.rdp(tc, logicTuple);
					}
					
					//send the result through the back event
					//(specifichiamo in questo momento il mittente ed il destinatario
					//di questo evento per inviarlo correttamente all'agente corrente
					//proprietario di questo behaviour, anche quando si tratta di
					//un clone di un altro agente)
					ELDAEventReturnTuple backEvent = evt.getBackEvent();
					if (read != null){
						backEvent.setTuple(read.toString());
					}
					backEvent.setSource(myAgent.getAID());
					java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
					target.add(myAgent.getAID());
					backEvent.setTarget(target);
					generate(backEvent);
				}
				else if ((content != null) && (content instanceof ELDAEventIn)) {
					ELDAEventIn evt = (ELDAEventIn) content;
					TucsonContext ctx = Tucson.enterDefaultContext();
					TupleCentreId tc = new TupleCentreId(evt.getTupleCentreName() + "@'" + evt.getTupleCentreIPAddress() + "'");
					LogicTuple logicTuple = LogicTuple.parse(evt.getTupleMatch());
					LogicTuple removed = null;
					if (evt.isSync()){
						//execute the IN primitive of TuCSoN
						//(to remove in blocking way a tuple matching the specified
						//tuple template from the specified tuple centre)
						removed = ctx.in(tc, logicTuple);
					}
					else{
						//execute the INP primitive of TuCSoN
						//(to remove in NON blocking way a tuple (only if present)
						//matching the specified tuple template from the specified tuple centre)
						removed = ctx.inp(tc, logicTuple);
					}

					//send the result through the back event
					//(specifichiamo in questo momento il mittente ed il destinatario
					//di questo evento per inviarlo correttamente all'agente corrente
					//proprietario di questo behaviour, anche quando si tratta di
					//un clone di un altro agente)
					ELDAEventReturnTuple backEvent = evt.getBackEvent();
					if (removed != null){
						backEvent.setTuple(removed.toString());
					}
					backEvent.setSource(myAgent.getAID());
					java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
					target.add(myAgent.getAID());
					backEvent.setTarget(target);
					generate(backEvent);
				}
				else if ((content != null) && (content instanceof ELDAEventSubscribe)) {
					final ELDAEventSubscribe evt = (ELDAEventSubscribe) content;

					//register the agent to receive notifications about the topic
					TopicManagementHelper topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
					AID topic = topicHelper.createTopic(evt.getTopic());
					topicHelper.register(topic);
					this.registeredTopics.add(topic);

					//add a behaviour to the agent to process the notifications about the topic
					final MessageTemplate template = MessageTemplate.MatchTopic(topic);
					myAgent.addBehaviour(new CyclicBehaviour(myAgent) {
						public void action() {
							ACLMessage msg = myAgent.receive(template);
							if (msg != null) {
								//inviamo l'evento di notifica
								//(specifichiamo in questo momento il mittente ed il destinatario
								//di questo evento per inviarlo correttamente all'agente corrente
								//proprietario di questo behaviour, anche quando si tratta di
								//un clone di un altro agente)
								ELDAEventEVTNotification backEvent = evt.getBackEvent();
								backEvent.setTopic(evt.getTopic());
								try {
									backEvent.setData(msg.getContentObject()); //the content of the ELDAEventEVTNotification is that published through a previous ELDAEventPublish
								}
								catch (Exception ex) {
									ex.printStackTrace();
								}
								backEvent.setSource(myAgent.getAID());
								java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
								target.add(myAgent.getAID());
								backEvent.setTarget(target);
								generate(backEvent);
							}
							else {
								block();
							}
						}
					});
				}
				else if ((content != null) && (content instanceof ELDAEventUnsubscribe)) {
					ELDAEventUnsubscribe evt = (ELDAEventUnsubscribe) content;
					
					//deregister the agent from the topic
					TopicManagementHelper topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
					AID topic = topicHelper.createTopic(evt.getTopic());
					topicHelper.deregister(topic);
					this.registeredTopics.remove(topic);
				}
				else if ((content != null) && (content instanceof ELDAEventPublish)) {
					ELDAEventPublish evt = (ELDAEventPublish) content;
					
					//do a publication about the topic
					TopicManagementHelper topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
					AID topic = topicHelper.createTopic(evt.getTopic());
					ACLMessage publish = new ACLMessage(ACLMessage.INFORM);
					publish.addReceiver(topic);
					publish.setContentObject(evt.getData());
					myAgent.send(publish);
				}
				else if ((content != null) && (content instanceof ELDAEventCreate)) {
					ELDAEventCreate evt = (ELDAEventCreate) content;
					
					//chiediamo all'AMS di creare l'agente indicato da questo evento
					CreateAgent ca = new CreateAgent();
					ca.setAgentName(evt.getAgentName());
					ca.setClassName(evt.getClassName());
					ca.setContainer(new jade.core.ContainerID(evt.getContainerName(), null));
					java.util.List<java.io.Serializable> arguments = evt.getArguments();
					if (arguments != null){
						for(int i = 0; i < arguments.size(); i++) {
							ca.addArguments(arguments.get(i));
						}
					}
					Action action = new Action();
					action.setActor(myAgent.getAMS());
					action.setAction(ca);
					ACLMessage request = getAMSRequest();
					request.setOntology(JADEManagementOntology.NAME);
					myAgent.getContentManager().fillContent(request, action);
					
					//ricaviamo l'eventuale notifica da inviare
					ELDAEventCreateNotify backEvent = null;
					if(evt.isNotify()){
						backEvent = evt.getBackEvent();
						backEvent.setCreatedAgentAID(new AID(evt.getAgentName(), AID.ISLOCALNAME));
					}

					//effettuiamo la richiesta all'AMS tramite un apposito protocollo che
					//permette di inviare la notifica se la richiesta è stata eseguita con successo
					myAgent.addBehaviour(new AMSRequesterBehaviour(myAgent, request, "CREATE_AGENT", backEvent));
				}
				else if ((content != null) && (content instanceof ELDAEventMoveRequest)) {
					ELDAEventMoveRequest evt = (ELDAEventMoveRequest) content;
					
					//chiediamo all'AMS di far migrare l'agente indicato da questo evento
					MoveAction moveAct = new MoveAction();
					MobileAgentDescription desc = new MobileAgentDescription();
					desc.setName(evt.getAgentToMove());
					desc.setDestination(evt.getDestination());
					moveAct.setMobileAgentDescription(desc);
					Action action = new Action();
					action.setActor(myAgent.getAMS());
					action.setAction(moveAct);
					ACLMessage request = getAMSRequest();
					request.setOntology(MobilityOntology.NAME);
					myAgent.getContentManager().fillContent(request, action);
					
					//ricaviamo l'eventuale evento da inviare se la migrazione va a buon fine
					ELDAEvent reactivationEvent = evt.getReactivationEvent();

					//effettuiamo la richiesta all'AMS tramite un apposito protocollo che
					//permette di inviare la notifica se la richiesta è stata eseguita con successo
					myAgent.addBehaviour(new AMSRequesterBehaviour(myAgent, request, "MOVE_AGENT", reactivationEvent));
				}
				else if ((content != null) && (content instanceof ELDAEventClone)) {
					ELDAEventClone evt = (ELDAEventClone) content;
					
					//chiediamo all'AMS di clonare l'agente indicato da questo evento
					CloneAction cloneAct = new CloneAction();
					MobileAgentDescription desc = new MobileAgentDescription();
					desc.setName(evt.getAgentToClone());
					desc.setDestination(evt.getDestination());
					cloneAct.setMobileAgentDescription(desc);
					cloneAct.setNewName(evt.getNewName());
					Action action = new Action();
					action.setActor(myAgent.getAMS());
					action.setAction(cloneAct);
					ACLMessage request = getAMSRequest();
					request.setOntology(MobilityOntology.NAME);
					myAgent.getContentManager().fillContent(request, action);
					
					//ricaviamo l'eventuale notifica da inviare
					ELDAEventCloneNotify backEvent = null;
					if(evt.isNotify()){
						backEvent = evt.getBackEvent();
						backEvent.setClonedAgentAID(new AID(evt.getNewName(), AID.ISLOCALNAME));
					}
						
					//effettuiamo la richiesta all'AMS tramite un apposito protocollo che
					//permette di inviare la notifica se la richiesta è stata eseguita con successo
					myAgent.addBehaviour(new AMSRequesterBehaviour(myAgent, request, "CLONE_AGENT", backEvent));
				}
				else if ((content != null) && (content instanceof ELDAEventSuspendRequest)) {
					//qualcuno ha inviato questo evento per richiedere che l'agente
					//proprietario di questo behaviour venga sospeso (questo evento
					//può essere inviato dall'agente stesso oppure da un altro agente)
					myAgent.doSuspend();
				}
				else if ((content != null) && (content instanceof ELDAEventResume)) {
					//qualcuno ha inviato questo evento per richiedere che l'agente
					//proprietario di questo behaviour venga riattivato (se attualmente
					//è sospeso); questo evento può essere inviato dall'agente
					//stesso oppure da un altro agente
					myAgent.doActivate();
				}
				else if ((content != null) && (content instanceof ELDAEventQuitRequest)) {
					//qualcuno ha inviato questo evento per richiedere che l'agente
					//proprietario di questo behaviour termini l'esecuzione (questo evento
					//può essere inviato dall'agente stesso oppure da un altro agente)
					myAgent.doDelete();
				}
				else if ((content != null) && (content instanceof ELDAEventWaitRequest)) {
					ELDAEventWaitRequest evt = (ELDAEventWaitRequest) content;
					
					//qualcuno ha inviato questo evento per richiedere che l'agente
					//proprietario di questo behaviour venga messo in attesa (questo evento
					//può essere inviato dall'agente stesso oppure da un altro agente)
					myAgent.doWait(evt.getTime());
				}
				else if ((content != null) && (content instanceof ELDAEventWakeUp)) {
					//qualcuno ha inviato questo evento per richiedere che l'agente
					//proprietario di questo behaviour venga risvegliato (se attualmente
					//si trova in attesa); questo evento può essere inviato dall'agente
					//stesso oppure da un altro agente
					myAgent.doWake();
				}
				else if ((content != null) && (content instanceof ELDAEventCreateTimer)) {
					final ELDAEventCreateTimer evt = (ELDAEventCreateTimer) content;
					
					//creiamo il timer richiesto per l'agente corrente (questo timer
					//non viene avviato adesso, perchè verrà avviato quando verrà
					//ricevuto l'apposito evento "ELDAEventStartTimer")
					this.timerCount++;
					WakerBehaviour timer = new WakerBehaviour(myAgent, evt.getDelay()){
						protected void onWake() {
							//allo scadere del timeout, inviamo un evento di tipo ELDAEventTimeoutNotify
							//(specifichiamo in questo momento il mittente ed il destinatario
							//di questo evento per inviarlo correttamente all'agente corrente
							//proprietario di questo behaviour, anche quando si tratta di
							//un clone di un altro agente)
							ELDAEventTimeoutNotify backEventT = evt.getBackEventT();
							backEventT.setTimerId(timerCount); //indichiamo che questo evento si riferisce al timer creato
							backEventT.setSource(myAgent.getAID());
							java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
							target.add(myAgent.getAID());
							backEventT.setTarget(target);
							generate(backEventT);
						}
					};
					this.timers.put(this.timerCount, timer);
					
					//se è stata richiesta una notifica di creazione del timer la inviamo adesso
					//(specifichiamo in questo momento il mittente ed il destinatario
					//di questo evento per inviarlo correttamente all'agente corrente
					//proprietario di questo behaviour, anche quando si tratta di
					//un clone di un altro agente)
					if(evt.isNotify()){
						ELDAEventCreateTimerNotify backEventCTN = evt.getBackEventCTN();
						backEventCTN.setTimerId(this.timerCount); //indichiamo che questo evento si riferisce al timer creato
						backEventCTN.setSource(myAgent.getAID());
						java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
						target.add(myAgent.getAID());
						backEventCTN.setTarget(target);
						generate(backEventCTN);
					}
				}
				else if ((content != null) && (content instanceof ELDAEventStartTimer)) {
					ELDAEventStartTimer evt = (ELDAEventStartTimer) content;
					
					//avviamo il timer indicato da questo evento
					WakerBehaviour timer = this.timers.get(evt.getTimerId());
					if(timer != null){
						myAgent.addBehaviour(timer);
					}
				}
				else if ((content != null) && (content instanceof ELDAEventResetTimer)) {
					ELDAEventResetTimer evt = (ELDAEventResetTimer) content;
					
					//resettiamo il timer indicato da questo evento
					WakerBehaviour timer = this.timers.get(evt.getTimerId());
					if(timer != null){
						timer.reset();
					}
				}
				else if ((content != null) && (content instanceof ELDAEventStopTimer)) {
					ELDAEventStopTimer evt = (ELDAEventStopTimer) content;
					
					//stoppiamo il timer indicato da questo evento
					WakerBehaviour timer = this.timers.get(evt.getTimerId());
					if(timer != null){
						timer.stop();
					}
				}
				else if ((content != null) && (content instanceof ELDAEventReleaseTimer)) {
					ELDAEventReleaseTimer evt = (ELDAEventReleaseTimer) content;
					
					//rilasciamo il timer indicato da questo evento
					WakerBehaviour timer = this.timers.get(evt.getTimerId());
					if(timer != null){
						myAgent.removeBehaviour(timer);
						this.timers.remove(evt.getTimerId());
					}
				}
			}
			else {
				block(); //sospende questo behaviour fino a quando non arriva un messaggio da processare
			}
		}
		catch (Exception ex) {			
			ex.printStackTrace();
		}
	}
	
	/** 
	 * This method must be used to restore the topic registrations after a clonation
	 * (you must call this method inside the "afterClone" method of your agent)
	 */
	public void restoreTopicRegistrations(){
		//quando si clona un agente, l'agente clonato non risulta registrato ai
		//topic a cui è registrato l'agente di partenza (mentre con la migrazione
		//si conservano le registrazioni ai topic);
		//quindi, questo metodo permette di registrare un agente clonato agli stessi
		//topic a cui è registrato l'agente di partenza (bisogna chiamare questo
		//metodo all'interno del metodo "afterClone" dell'agente)
		
		try {
			for(int i=0; i < this.registeredTopics.size(); i++){
				TopicManagementHelper topicHelper = (TopicManagementHelper) myAgent.getHelper(TopicManagementHelper.SERVICE_NAME);
				topicHelper.register(this.registeredTopics.get(i));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/** 
	 * This method encapsulates the ELDAEvent inside an ACLMessage and sends the resulting message.
	 * You can use this method to send your ELDAEvent.
	 * @param event the ELDAEvent to send.
	 */
	public void generate(ELDAEvent event) {
		try {
			AID source = event.getSource();
			java.util.List<AID> target = event.getTarget();
			String language = event.getLanguage();
			ACLMessage msg = new ACLMessage(ACLMessage.UNKNOWN);
	
			// set sender
			if (source != null) {
				msg.setSender(source);
			}
	
			// set receivers
			if (target != null) {
				for (int i = 0; i < target.size(); i++) {
					msg.addReceiver(target.get(i));
				}
			}
	
			// set language
			if (language != null) {
				msg.setLanguage(language);
			}
	
			// encapsulate the ELDAEvent inside the ACLMessage through the
			// "setContentObject" method
			msg.setContentObject(event);
	
			// send the ACLMessage that contains the ELDAEvent
			myAgent.send(msg); //ATTENZIONE: non usare il metodo "putBack(ACLMessage)" per inviare il messaggio, perchè ci sono problemi quando si invia il reactivationEvent relativo a un ELDAEventMoveRequest
			
			this.eventCount++;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Get the event count.
	 * @return The event count.
	 */
	public int getEventCount() {
		return this.eventCount;
	}
	
	/**
	 * Get the request message used to send a request to the AMS agent.
	 * @return The request ACL message.
	 */
	private ACLMessage getAMSRequest() {
		ACLMessage AMSRequest = new ACLMessage(ACLMessage.REQUEST);
		AMSRequest.setSender(myAgent.getAID());
		AMSRequest.addReceiver(myAgent.getAMS());
		AMSRequest.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		AMSRequest.setLanguage(FIPANames.ContentLanguage.FIPA_SL0);
		return AMSRequest;
	}
	
	/**
	 * This class is used to send a request to the AMS agent through
	 * the FIPA-Request interaction protocol, when a notification is required
	 */
	private class AMSRequesterBehaviour extends SimpleAchieveREInitiator {
		
		//questo behaviour viene utilizzato quando bisogna effettuare una
		//richiesta all'AMS e c'è la necessità di inviare una notifica quando
		//la richiesta è stata eseguita con successo
		
		private String actionName;
		
		private ELDAEvent notification; //notifica da inviare se la richiesta è stata effettuata correttamente
		
		public AMSRequesterBehaviour(Agent agent, ACLMessage request, String actionName, ELDAEvent notification) {
			super(agent, request);
			this.actionName = actionName;
			this.notification = notification;
		}

		protected void handleNotUnderstood(ACLMessage reply) {
			System.out.println(myAgent.getAID().getName() + " : NOT-UNDERSTOOD sent by AMS for " + actionName + " : " + reply);
		}
		
		protected void handleRefuse(ACLMessage reply) {
			System.out.println(myAgent.getAID().getName() + " : REFUSE sent by AMS for " + actionName + " : " + reply);
		}
		
		protected void handleFailure(ACLMessage reply) {
			System.out.println(myAgent.getAID().getName() + " : FAILURE sent by AMS for " + actionName + " : " + reply);
		}

		protected void handleInform(ACLMessage reply) {
			//questo metodo viene eseguito quando l'AMS risponde con un INFORM
			//alla nostra richiesta, cioè quando la richiesta è stata eseguita
			//con successo; quindi, se è stata richiesta, inviamo la notifica
			if(notification != null){
				if(actionName.equals("MOVE_AGENT") == false){
					//se non si tratta di una richiesta di migrazione, specifichiamo
					//in questo momento il mittente ed il destinatario della
					//notifica per inviarla correttamente all'agente corrente
					//proprietario di questo behaviour (anche quando si tratta di
					//un clone di un altro agente); se si tratta di una richiesta
					//di migrazione, la notifica da inviare è di tipo ELDAEvent
					//ed i suoi mittente e destinatari sono stati già indicati al
					//momento della creazione dell'evento
					notification.setSource(myAgent.getAID());
					java.util.ArrayList<AID> target = new java.util.ArrayList<AID>();
					target.add(myAgent.getAID());
					notification.setTarget(target);
				}
				generate(notification);
			}
		}
		
	}

}
