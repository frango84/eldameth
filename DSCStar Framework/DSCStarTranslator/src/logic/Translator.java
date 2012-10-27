/*****************************************************************
DSC* TRANSLATOR
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

package logic;

import java.io.*;
import java.util.*;
import org.eclipse.jdt.core.dom.*;
import com.mxgraph.layout.hierarchical.*;
import com.mxgraph.model.*;
import com.mxgraph.util.*;
import com.mxgraph.view.*;

/**
 * Main class of the DSC* translator.
 * 
 * @author G. Fortino, F. Rango
 */
public class Translator extends Observable {
	
	//costanti che indicano il tipo di traduzione da effettuare
	public static final int KEY_STATEMENTS_BASED = 1; //indica la traduzione basata sugli statement chiave
	public static final int FULL = 2; //indica la traduzione di tipo FULL
	
	//costanti usate per informare la GUI
	public static final int END = 3;
	public static final int ERROR = 4;

	//costanti che indicano le parole chiave da cercare nel file java di origine
	//quando si effettua la traduzione basata sulle parole chiave
	public static final String MOVE_KEY = "move();";
	public static final String CHECKPOINT_KEY = "checkpoint();";
	public static final String PERSISTENCE_KEY = "persistence();";
	public static final String CLONE_KEY = "clone();";
	
	//stili per il DSC* in forma grafica creato con JGraph
	private String stateStyle = "rounded=1;fillColor=white;fontColor=black;strokeColor=black;verticalAlign=top";
	private String transitionStyle = "endArrow=open;fontColor=black;strokeColor=black;labelBackgroundColor=white"; //ATTENZIONE: NON aggiungere la voce "edgeStyle=elbowEdgeStyle" nello stile della transizione, altrimenti si verifica il seguente problema: quando c'è una transizione <<R>> che va da uno stato composto al connettore di storia superficiale dello stesso stato composto, non compare la scroll bar per il DSC* grafico
	private String initStyle = "shape=ellipse;perimeter=ellipsePerimeter;fillColor=black;strokeColor=black";
	private String historyStyle = "shape=ellipse;perimeter=ellipsePerimeter;fillColor=white;fontColor=black;strokeColor=black";
	private String endPart1Style = "shape=ellipse;perimeter=ellipsePerimeter;fillColor=white;strokeColor=black";
	
	private int translationType; //tipo di traduzione
	private File sourceFile; //file java di origine
	private File destFile; //file java di destinazione
	private CompilationUnit unit; //rappresenta il file java da tradurre
	private int contTransitions; //contatore delle transizioni
	private int contSimpleStates; //contatore degli stati semplici
	private int actualState; //stato attuale da cui riprendere la traduzione
	private String codeOfDestFile; //codice da inserire nel file java di destinazione
	private String stringCreateSimpleStates; //stringa che contiene la creazione degli stati semplici
	private String stringCreateCompositeStates; //stringa che contiene la creazione degli stati composti
	private String stringAddSimpleStates; //stringa che contiene l'aggiunta degli stati semplici
	private String stringCreateTransitions; //stringa che contiene la creazione delle transizioni
	private String stringAddTransitions; //stringa che contiene l'aggiunta delle transizioni
	private String stringNotTranslatedMethods; //stringa che contiene i metodi non tradotti
	private ArrayList<String> starTransitionsList; //lista che memorizza le transizioni di tipo "*" create, per evitare di creare più volte la stessa transizione "*" e "<<R>>"
	private ArrayList<MethodDeclaration> translatedMethodsList; //lista dei metodi tradotti
	private mxGraph graph; //DSC* in forma grafica creato con JGraph
	
	/** Constructor.
	 */
	public Translator(){}
	
	/** This method starts the translation.
	 * @param translationType the translation type (Translator.KEY_STATEMENTS_BASED or Translator.FULL).
	 * @param sourceFile source file to translate.
	 * @param destFile destination file.
	 */
	public void translate(int translationType, File sourceFile, File destFile){
		//metodo che avvia la traduzione in base al tipo di traduzione scelta
		
		try {
			//inizializziamo tutte le variabili da utilizzare
			this.unit = null;
			this.codeOfDestFile = "";
			this.stringCreateSimpleStates = "";
			this.stringCreateCompositeStates = "";
			this.stringAddSimpleStates = "";
			this.stringCreateTransitions = "";
			this.stringAddTransitions = "";
			this.contTransitions = 0;
			this.contSimpleStates = 0;
			this.actualState = 0;
			this.stringNotTranslatedMethods = "";
			this.starTransitionsList = new ArrayList<String>();
			this.translatedMethodsList = new ArrayList<MethodDeclaration>();
			initGraph();
			
			//settiamo le variabili passate come parametro
			this.translationType = translationType;
			this.sourceFile = sourceFile;
			this.destFile = destFile;
			String destAgentName = destFile.getName().substring(0, destFile.getName().length() - 5); //ricaviamo il nome del nuovo agente eliminando l'estensione ".java" dal file di destinazione
			
			//ricaviamo il codice del file di origine da tradurre
			String codiceDaTradurre = extractSourceCode();
			
			//creiamo il parser per java 1.5 e ricaviamo la "CompilationUnit"
			//che rappresenta il file java da tradurre
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(codiceDaTradurre.toCharArray());
			parser.setResolveBindings(true);
			this.unit = (CompilationUnit) parser.createAST(null);
			
			//creiamo lo stato ACTIVE del DSC* grafico (PRIMA DI TRADURRE IL CORPO DEI METODI)
			createGraphicActiveState();

			//traduciamo il corpo dei metodi
			List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
			for(int i=0; i < classComponents.size(); i++){
				if(classComponents.get(i) instanceof MethodDeclaration){
					MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
					if(canTranslate(md)){
						//traduciamo il metodo corrente solo se la traduzione è FULL, oppure se
						//la traduzione è KEY_STATEMENTS_BASED ed il metodo contiene un key statement
						//o contiene un'invocazione ad un metodo che contiene un key statement
						
						//aggiungiamo il metodo corrente alla lista dei metodi tradotti
						this.translatedMethodsList.add(md);
						
						//creiamo lo stato composto corrispondente al metodo corrente
						createCompositeState(md);

						//traduciamo il corpo del metodo corrente
						Block body = md.getBody();
						translateBlock(body, md);
					}
					else{
						//se il metodo non è stato tradotto (in caso di traduzione KEY_STATEMENTS_BASED),
						//lo riportiamo così com'è nel file di destinazione
						this.stringNotTranslatedMethods += md.toString();
					}
				}
			}

			//DOPO AVER TRADOTTO IL CORPO DEI METODI:
			
			//creiamo l'intestazione del nuovo file java
			codeOfDestFile += createHeader(destAgentName);
			
			//creiamo le variabili degli stati composti
			codeOfDestFile += createCompositeStatesVars();
			
			//apriamo il metodo "setup" del nuovo agente
			codeOfDestFile += openSetupMethod();
			
			//creiamo gli stati composti
			codeOfDestFile += stringCreateCompositeStates;
			
			//creiamo gli stati semplici
			codeOfDestFile += stringCreateSimpleStates;
			
			//aggiungiamo i sotto-stati semplici negli stati composti
			codeOfDestFile += stringAddSimpleStates;
			
			//creiamo la root in accordo al DSC template (prima di aggiungere le transizioni)
			codeOfDestFile += createRoot();
			
			//creiamo le transizioni
			codeOfDestFile += stringCreateTransitions;
			
			//aggiungiamo le transizioni
			codeOfDestFile += stringAddTransitions;
			
			//attiviamo la root
			MethodDeclaration mainMethod = Util.getMethodDeclaration(this.unit, "main");
			if(this.translatedMethodsList.contains(mainMethod)){
				codeOfDestFile += "addBehaviour(root);\n";
			}
			
			//mandiamo l'evento CPE_MAIN per avviare la computazione della macchina a stati
			codeOfDestFile += sendCPEMain();

			//chiudiamo il metodo "setup" del nuovo agente
			codeOfDestFile += closeSetupMethod();
			
			//creiamo il metodo "afterMove" del nuovo agente
			codeOfDestFile += createAfterMoveMethod();

			//creiamo il metodo "afterClone" del nuovo agente
			codeOfDestFile += createAfterCloneMethod();
			
			//creiamo il metodo "postEvent(DSCStarEvent)"
			codeOfDestFile += createPostEventMethod();

			//creiamo i metodi delle parole chiave
			codeOfDestFile += createKeyStatementsMethods();
			
			//riportiamo nel file di destinazione i metodi del file originale non tradotti
			codeOfDestFile += stringNotTranslatedMethods;
			
			//creiamo le inner class degli oggetti variabili
			codeOfDestFile += createInnerClassesOfVarObjects();
			
			//creiamo le inner class degli eventi
			codeOfDestFile += createInnerClassesOfEvents();
			
			//creiamo l'inner class del behaviour degli stati semplici
			codeOfDestFile += createInnerClassesOfSimpleStatesBehaviour();

			//chiudiamo la classe
			codeOfDestFile += "}";
			
			//dopo aver tradotto tutto il codice del file di origine,
			//creiamo il file di destinazione contenente il codice tradotto
			createDestinationFile();
			
			//concludiamo la creazione del DSC* grafico
			finishGraph();

			//notifichiamo alla GUI che la traduzione è avvenuta con successo
			notifyChange(new Integer(END));
		}
		catch (Exception e) {
			e.printStackTrace();
			
			//notifichiamo alla GUI che c'è stato un errore nella traduzione
			notifyChange(new Integer(ERROR));
		}
	}

	public mxGraph getGraph() {
		return this.graph;
	}

	/** This method notifies the changes to the observers.
	 * @param i Integer that represents the change.
	 */
	private void notifyChange(Integer i) {
		setChanged();
		notifyObservers(i);
	}
	
	private void initGraph(){
		//metodo che inizializza il DSC* grafico
		
		this.graph = new mxGraph();
		
		//inizio update del grafo
		this.graph.getModel().beginUpdate();

		//this.graph.setCellsEditable(false); //disattiva l'editing di stati/transizioni
		//this.graph.setCellsDisconnectable(false); //disattiva la disconnessione delle transizioni
	}
	
	private void finishGraph(){
		//metodo che conclude la creazione del DSC* grafico
		
		//aggiusta la dimensione degli stati
		for(int i=1; i <= this.contSimpleStates; i++){
			Object s = ((mxGraphModel) graph.getModel()).getCell("S" + i);
			graph.updateCellSize(s);
		}
		for(int i=0; i < this.translatedMethodsList.size(); i++){
			MethodDeclaration m = this.translatedMethodsList.get(i);
			Object methodState = ((mxGraphModel) graph.getModel()).getCell(m.getName().toString().toUpperCase());
			graph.updateCellSize(methodState);
		}
		Object active = ((mxGraphModel) graph.getModel()).getCell("ACTIVE");
		graph.updateCellSize(active);
		
		//settaggio layout
		mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
		int border = 30; //bordo interno degli stati composti
		layout.setInterHierarchySpacing(100);
		layout.setInterRankCellSpacing(190);
		layout.setIntraCellSpacing(300);
		layout.setParallelEdgeSpacing(200);
		layout.setResizeParent(true);
		layout.setParentBorder(border);
		layout.setFineTuning(true);

		//per effettuare correttamente il layout dei vari connettori di storia superficiale,
		//creiamo per ciascuno una transizione fittizia che collega il pallino nero iniziale
		//dello stato al connettore di storia e rimuoviamo questa transizione dopo il layout
		ArrayList<Object> tempTransitions = new ArrayList<Object>();
		for(int i=0; i < this.translatedMethodsList.size(); i++){
			MethodDeclaration m = this.translatedMethodsList.get(i);
			String stateName = m.getName().toString().toUpperCase();
			Object methodState = ((mxGraphModel) graph.getModel()).getCell(stateName);
			Object init = ((mxGraphModel) graph.getModel()).getCell("INIT_" + stateName);
			Object shallowHistory = ((mxGraphModel) graph.getModel()).getCell("SHALLOW_HISTORY_" + stateName);
			Object t = graph.insertEdge(methodState, null, "", init, shallowHistory, this.transitionStyle);
			tempTransitions.add(t);
		}

		for(int i=0; i < this.translatedMethodsList.size(); i++){
			MethodDeclaration m = this.translatedMethodsList.get(i);
			Object methodState = ((mxGraphModel) graph.getModel()).getCell(m.getName().toString().toUpperCase());
			
			//eseguiamo il layout per lo stato composto corrente
			layout.execute(methodState);
			
			//se la larghezza attuale dello stato composto è inferiore alla larghezza
			//dell'etichetta dello stato composto (compreso il bordo), allarghiamo lo stato composto
			mxRectangle sizeLabel = graph.getPreferredSizeForCell(methodState);
			mxGeometry g = (mxGeometry)(graph.getModel().getGeometry(methodState)).clone();
			if(g.getWidth() < (sizeLabel.getWidth() + border * 2)){
				g.setWidth(sizeLabel.getWidth() + border * 2);
				graph.resizeCell(methodState, new mxRectangle(g));
			}
		}
		
		//eseguiamo il layout dello stato composto "ACTIVE"
		//dopo aver effettuato il layout degli altri stati composti
		layout.execute(active);
		
		//se la larghezza attuale dello stato "ACTIVE" è inferiore alla larghezza
		//dell'etichetta dello stato "ACTIVE" (compreso il bordo), allarghiamo lo stato "ACTIVE"
		mxRectangle sizeLabel = graph.getPreferredSizeForCell(active);
		mxGeometry g = (mxGeometry)(graph.getModel().getGeometry(active)).clone();
		if(g.getWidth() < (sizeLabel.getWidth() + border * 2)){
			g.setWidth(sizeLabel.getWidth() + border * 2);
			graph.resizeCell(active, new mxRectangle(g));
		}
		
		//rimuoviamo le transizioni fittizie create in precedenza
		for(int i=0; i < tempTransitions.size(); i++){
			Object t = tempTransitions.get(i);
			graph.removeCells(new Object[]{t});
		}

		//fine update del grafo
		graph.getModel().endUpdate();
	}
	
	/** This method extracts the code from the source file.
	 * @return a string that contains the code.
	 */
	private String extractSourceCode() throws IOException{
		//metodo che ricava il codice del file di origine sottoforma di stringa
		
		String codice = "";
		FileReader reader = new FileReader(this.sourceFile);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()){
			codice += (in.nextLine() + "\n");
		}
		reader.close();
		return codice;
	}
	
	/** This method creates the destination file.
	 */
	private void createDestinationFile() throws IOException{
		//metodo che crea il file java di destinazione contenente il codice tradotto
		
		FileWriter fileWriter = new FileWriter(this.destFile);
		PrintWriter pw = new PrintWriter(fileWriter);
		pw.println(this.codeOfDestFile);
		pw.close();
	}
	
	/** This method creates the header of the new agent.
	 * @return result string.
	 */
	private String createHeader(String name){ //metodo che crea l'intestazione del nuovo file java
		String result = "";
		
		//ricaviamo gli import del file java di origine
		List imports = unit.imports();
		for(int i=0; i < imports.size(); i++){
			result += imports.get(i).toString() + "\n";
		}
		
		//importiamo le classi di JADE necessarie
		result += "import jade.core.*;\n";
		result += "import jade.core.behaviours.*;\n";
		result += "import jade.lang.acl.*;\n";
		result += "import jade.content.*;\n";
		result += "import jade.content.lang.*;\n";
		result += "import jade.content.lang.sl.*;\n";
		result += "import jade.content.onto.*;\n";
		result += "import jade.content.onto.basic.*;\n";
		result += "import jade.domain.*;\n";
		result += "import jade.domain.FIPAAgentManagement.*;\n";
		result += "import jade.domain.JADEAgentManagement.*;\n";
		result += "import jade.domain.mobility.*;\n";
		result += "import jade.wrapper.*;\n";

		//apriamo la nuova classe
		result += "public class " + name + " extends Agent{\n";
		return result;
	}

	/** This method opens the "setup" method of the new agent.
	 * @return result string.
	 */
	private String openSetupMethod(){ //metodo che apre il metodo "setup" del nuovo agente e crea le operazioni iniziali
		String result = "";
		result += "protected void setup(){\n";
		result += "try{\n";
		result += "getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);\n";
		result += "getContentManager().registerOntology(MobilityOntology.getInstance());\n";
		return result;
	}
	
	/** This method closes the "setup" method of the new agent.
	 * @return result string.
	 */
	private String closeSetupMethod(){ //metodo che crea le operazioni finali del metodo "setup" del nuovo agente e chiude questo metodo
		String result = "";
		result += "}\n";
		result += "catch (Exception e) {\n";
		result += "e.printStackTrace();\n";
		result += "doDelete();\n";
		result += "}\n";
		result += "}\n";
		return result;
	}
	
	/** This method creates the "afterMove" method of the new agent.
	 * @return result string.
	 */
	private String createAfterMoveMethod(){ //metodo che crea il metodo "afterMove" del nuovo agente
		String result = "";
		result += "protected void afterMove(){\n";
		result += "getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);\n";
		result += "getContentManager().registerOntology(MobilityOntology.getInstance());\n";
		result += "}\n";
		return result;
	}

	/** This method creates the "afterClone" method of the new agent.
	 * @return result string.
	 */
	private String createAfterCloneMethod(){ //metodo che crea il metodo "afterClone" del nuovo agente
		String result = "";
		result += "protected void afterClone(){\n";
		result += "getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);\n";
		result += "getContentManager().registerOntology(MobilityOntology.getInstance());\n";
		result += "}\n";
		return result;
	}

	/** This method sends the CPE_MAIN event.
	 * @return result string.
	 */
	private String sendCPEMain(){
		//metodo che manda l'evento CPE_MAIN, inserendo in questo evento
		//i parametri ricevuti dall'agente da passare allo stato composto MAIN
		//per avviare la computazione della macchina a stati
		
		MethodDeclaration mainMethod = Util.getMethodDeclaration(this.unit, "main");
		String result = "";
		if(this.translatedMethodsList.contains(mainMethod)){
			if(mainMethod.parameters().size() > 0){
				//se il main ha dei parametri, l'evento "CPE_MAIN" contiene questi parametri
				//(che vengono estratti dagli argomenti passati all'agente)
				result += "Object args[] = getArguments();\n";
				String methodNameUpperCase = mainMethod.getName().toString().toUpperCase();
				result += "CPE_" + methodNameUpperCase + " cpeMain = new CPE_" + methodNameUpperCase + "();\n";
				for(int j=0; j < mainMethod.parameters().size(); j++){
					if(mainMethod.parameters().get(j) instanceof SingleVariableDeclaration){
						SingleVariableDeclaration par = (SingleVariableDeclaration) mainMethod.parameters().get(j);
						result += "cpeMain." + par.getName().toString() + " = (" + Util.convertPrimitiveTypeToObject(par.getType().toString()) + ") args[" + j + "];\n";
					}
				}
				result += "postEvent(cpeMain);\n";
			}
			else{
				//se il main non ha nemmeno un parametro,
				//l'evento "CPE_MAIN" non contiene nessun parametro
				result += "DSCStarEvent cpeMain = new DSCStarEvent(DSCStarEvent.CPE, main);\n";
				result += "postEvent(cpeMain);\n";
			}
		}
		return result;
	}

	/** This method creates the key statements methods of the new agent.
	 * @return result string.
	 */
	private String createKeyStatementsMethods(){
		//metodo che crea i metodi delle parole chiave
		
		String result = "";
		
		//metodo per la migrazione
		result += "private void " + MOVE_KEY.replace(";", "") + "{}\n"; //per il momento, lasciamo vuoto il metodo per la migrazione, perchè non si sa la locazione in cui spostarsi
		
		//metodo per la clonazione
		result += "private void " + CLONE_KEY.replace(";", "") + "{}\n"; //per il momento, lasciamo vuoto il metodo per la clonazione
		
		//metodo per il checkpoint
		result += "private void " + CHECKPOINT_KEY.replace(";", "") + "{}\n"; //per il momento, lasciamo vuoto il metodo per il checkpoint
		
		//metodo per la persistenza
		result += "private void " + PERSISTENCE_KEY.replace(";", "") + "{}\n"; //per il momento, lasciamo vuoto il metodo per la persistenza
		
		return result;
	}

	/** This method creates the root DSCStarBehaviour of the new agent.
	 * @return result string.
	 */
	private String createRoot(){
		//metodo che crea la root in accordo al DSC template
		
		String result = "";
		MethodDeclaration mainMethod = Util.getMethodDeclaration(this.unit, "main");
		if(this.translatedMethodsList.contains(mainMethod)){
			result += "java.util.ArrayList<DSCStarBehaviour> otherStates = new java.util.ArrayList<DSCStarBehaviour>();\n";
			
			List classComponents = ((TypeDeclaration) this.unit.types().get(0)).bodyDeclarations();
			for(int i=0; i < classComponents.size(); i++){
				if(classComponents.get(i) instanceof MethodDeclaration){
					MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
					if((md.getName().toString()).equalsIgnoreCase("main") == false && this.translatedMethodsList.contains(md)){
						result += "otherStates.add(" + md.getName().toString() + ");\n";
					}
				}
			}
			
			int contVarRoot = Util.contVarsOfRoot(this.unit);
			String oggVar = "";
			if(contVarRoot > 0){
				oggVar = "new VarRoot()";
			}
			else{
				oggVar = "null";
			}
			
			result += "DSCStarBehaviour root = DSCStarBehaviour.createRootForDSCStar(main, otherStates, " + oggVar + ");\n";
		
			//aggiorniamo lo stato ACTIVE del DSC* grafico settando il suo stato iniziale e finale
			Object active = ((mxGraphModel) graph.getModel()).getCell("ACTIVE");
			String mainStateName = mainMethod.getName().toString().toUpperCase();
			Object mainState = ((mxGraphModel) graph.getModel()).getCell(mainStateName);
			Object init = graph.insertVertex(active, "INIT_ACTIVE", "", 1, 1, 16, 16, this.initStyle); //crea il pallino nero iniziale
			Object deepHistory = graph.insertVertex(active, "DEEP_HISTORY_ACTIVE", "H*", 1, 1, 20, 20, this.historyStyle); //crea il connettore di storia profonda
			Object endPart1 = graph.insertVertex(active, "END_ACTIVE", "", 1, 1, 16, 16, this.endPart1Style); //prima parte del pallino terminale (NON CAMBIARE I VALORI NUMERICI)
			mxGeometry geo = new mxGeometry(0.5, 0.5, 6, 6);
			geo.setOffset(new mxPoint(-3, -3));
			geo.setRelative(true);
			mxCell endPart2 = new mxCell(null, geo, this.initStyle); //seconda parte del pallino terminale (NON CAMBIARE I VALORI NUMERICI)
			endPart2.setVertex(true);
			graph.addCell(endPart2, endPart1);
			graph.insertEdge(active, null, "", mainState, endPart1, this.transitionStyle);
			graph.insertEdge(active, null, "", init, deepHistory, this.transitionStyle);
			graph.insertEdge(active, null, "", deepHistory, mainState, this.transitionStyle);
		}
		return result;
	}
	
	private void createGraphicActiveState(){
		//metodo che crea lo stato ACTIVE del DSC* grafico
		
		//troviamo le variabili da inserire nell'intestazione dello stato ACTIVE
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		int contVarRoot = 0;
		String stringVarRoot = "V(ACTIVE) = { ";
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof FieldDeclaration){
				FieldDeclaration fd = (FieldDeclaration) classComponents.get(i);
				for(int j=0; j < fd.fragments().size(); j++){
					if(fd.fragments().get(j) instanceof VariableDeclarationFragment){
						contVarRoot++;
						VariableDeclarationFragment v = (VariableDeclarationFragment) fd.fragments().get(j);
						stringVarRoot += v.getName().toString() + ":" + fd.getType().toString() + ", ";
					}
				}
			}
		}
		String stateName = "ACTIVE";
		if(contVarRoot > 0){
			stringVarRoot = stringVarRoot.substring(0, stringVarRoot.length() - 2) + " }";
			stateName += "   " + stringVarRoot;
		}
		
		//creiamo lo stato ACTIVE del DSC* grafico
		this.graph.insertVertex(this.graph.getDefaultParent(), "ACTIVE", stateName, 1, 1, 10, 10, this.stateStyle);
	}
	
	private void createGraphicCompositeState(MethodDeclaration md){
		//metodo che crea uno stato composto del DSC* grafico
		
		String stateName = md.getName().toString().toUpperCase();
		int contPar = 0;
		int contVar = 0;
		String stringPar = "P(" + stateName + ") = { ";
		String stringVar = "V(" + stateName + ") = { ";
	
		//ricaviamo i parametri del metodo corrente
		for(int j=0; j < md.parameters().size(); j++){
			if(md.parameters().get(j) instanceof SingleVariableDeclaration){
				contPar++;
				SingleVariableDeclaration par = (SingleVariableDeclaration) md.parameters().get(j);
				stringPar += par.getName().toString() + ":" + par.getType().toString() + ", ";
			}
		}

		//troviamo le variabili contenute nel corpo del metodo corrente
		Block body = md.getBody();
		for(int j=0; j < body.statements().size(); j++){
			if(body.statements().get(j) instanceof VariableDeclarationStatement){
				VariableDeclarationStatement vd = (VariableDeclarationStatement) body.statements().get(j);
				for(int z=0; z < vd.fragments().size(); z++){
					if(vd.fragments().get(z) instanceof VariableDeclarationFragment){
						contVar++;
						VariableDeclarationFragment v = (VariableDeclarationFragment) vd.fragments().get(z);
						stringVar += v.getName().toString() + ":" + vd.getType().toString() + ", ";
						
					}
				}
			}
		}

		if(contPar > 0 && contVar > 0){
			stringPar = stringPar.substring(0, stringPar.length() - 2) + " }";
			stringVar = stringVar.substring(0, stringVar.length() - 2) + " }";
			stateName += "   " + stringPar + ", " + stringVar;
		}
		else if(contPar > 0){
			stringPar = stringPar.substring(0, stringPar.length() - 2) + " }";
			stateName += "   " + stringPar;
		}
		else if(contVar > 0){
			stringVar = stringVar.substring(0, stringVar.length() - 2) + " }";
			stateName += "   " + stringVar;
		}
		
		//creiamo lo stato composto nel DSC* grafico
		this.graph.insertVertex(((mxGraphModel) this.graph.getModel()).getCell("ACTIVE"), md.getName().toString().toUpperCase(), stateName, 1, 1, 10, 10, this.stateStyle);
	}

	/** This method creates the inner classes of variable objects of the new agent.
	 * @return result string.
	 */
	private String createInnerClassesOfVarObjects(){
		//metodo che crea le inner class degli oggetti variabili
		
		String result = "";
		
		//creiamo l'oggetto variabili della root
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		int contVarRoot = 0;
		String stringVarRoot = "private class VarRoot extends DSCStarVariablesAndParameters{\n";
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof FieldDeclaration){
				FieldDeclaration fd = (FieldDeclaration) classComponents.get(i);
				for(int j=0; j < fd.fragments().size(); j++){
					if(fd.fragments().get(j) instanceof VariableDeclarationFragment){
						contVarRoot++;
						VariableDeclarationFragment v = (VariableDeclarationFragment) fd.fragments().get(j);
						stringVarRoot += "public " + fd.getType().toString() + " " + v.getName().toString();
						if(v.getInitializer() != null){
							//se la variabile globale corrente è inizializzata a un valore,
							//inizializziamo la variabile anche all'interno dell'oggetto variabili
							//della root (che non viene clonato)
							stringVarRoot += " = " + v.getInitializer().toString();
						}
						stringVarRoot += ";\n";
					}
				}
			}
		}
		stringVarRoot += "public VarRoot(){}\n";
		stringVarRoot += "}\n";
		if(contVarRoot > 0){
			result += stringVarRoot;
		}

		//creiamo gli oggetti variabili degli altri stati composti
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof MethodDeclaration){
				MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
				if(this.translatedMethodsList.contains(md)){
					int contVarMethod = 0;
					String methodName = Util.firstToUpper(md.getName().toString());
					String stringVarMethod = "private class Var" + methodName + " extends DSCStarVariablesAndParameters{\n";
				
					//ricaviamo i parametri del metodo corrente
					for(int j=0; j < md.parameters().size(); j++){
						if(md.parameters().get(j) instanceof SingleVariableDeclaration){
							contVarMethod++;
							SingleVariableDeclaration par = (SingleVariableDeclaration) md.parameters().get(j);
							stringVarMethod += ("public " + par.getType().toString() + " " + par.getName().toString() + ";\n");
						}
					}
					
					//troviamo le altre variabili contenute nel corpo del metodo corrente
					Block body = md.getBody();
					for(int j=0; j < body.statements().size(); j++){
						if(body.statements().get(j) instanceof VariableDeclarationStatement){
							VariableDeclarationStatement vd = (VariableDeclarationStatement) body.statements().get(j);
							for(int z=0; z < vd.fragments().size(); z++){
								if(vd.fragments().get(z) instanceof VariableDeclarationFragment){
									contVarMethod++;
									VariableDeclarationFragment v = (VariableDeclarationFragment) vd.fragments().get(z);
									stringVarMethod += ("public " + vd.getType().toString() + " " + v.getName().toString() + ";\n");
									
									//NOTA: anche se la variabile corrente è inizializzata a un valore,
									//NON inizializziamo la variabile all'interno dell'oggetto variabili
									//dello stato composto, perchè deve essere inizializzata adeguatamente
									//durante il processo di traduzione del metodo corrente, in quanto
									//gli oggetti variabili degli stati composti diversi dalla root
									//vengono clonati
								}
							}
						}
					}

					stringVarMethod += "public Var" + methodName + "(){}\n";
					stringVarMethod += "}\n";
					if(contVarMethod > 0){
						result += stringVarMethod;
					}
				}
			}
		}
		
		return result;
	}

	/** This method creates the inner classes of events of the new agent.
	 * @return result string.
	 */
	private String createInnerClassesOfEvents(){
		//metodo che crea le inner class degli eventi
		
		String result = "";
		
		//creiamo gli eventi CPE e RPE per i vari metodi
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof MethodDeclaration){
				MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
				if(this.translatedMethodsList.contains(md)){
					String methodNameUpperCase = md.getName().toString().toUpperCase();
					
					//se il metodo corrente ha almeno un parametro,
					//creiamo l'evento di CPE per questo metodo
					if(md.parameters().size() > 0){
						result += "private class CPE_" + methodNameUpperCase + " extends DSCStarEvent{\n";
						for(int j=0; j < md.parameters().size(); j++){
							if(md.parameters().get(j) instanceof SingleVariableDeclaration){
								SingleVariableDeclaration par = (SingleVariableDeclaration) md.parameters().get(j);
								result += ("public " + par.getType().toString() + " " + par.getName().toString() + ";\n");
							}
						}
						result += "public CPE_" + methodNameUpperCase + "(){\n";
						result += "super(DSCStarEvent.CPE, " + md.getName().toString() + ");\n";
						result += "}\n";
						result += "}\n";
					}
					
					//se il metodo corrente ha un ritorno diverso da "void",
					//creiamo l'evento RPE per questo metodo
					if((md.getReturnType2().toString()).equalsIgnoreCase("void") == false){
						result += "private class RPE_" + methodNameUpperCase + " extends DSCStarEvent{\n";
						result += "public " + md.getReturnType2().toString() + " result;\n"; //la variabile che contiene il valore da ritornare si chiama "result"
						result += "public RPE_" + methodNameUpperCase + "(){\n";
						result += "super(DSCStarEvent.RPE, " + md.getName().toString() + ");\n";
						result += "}\n";
						result += "}\n";
					}
				}
			}
		}

		return result;
	}

	/** This method creates the composite states variables of the new agent.
	 * @return result string.
	 */
	private String createCompositeStatesVars(){
		//metodo che crea le variabili degli stati composti
		
		String result = "";
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof MethodDeclaration){
				MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
				if(this.translatedMethodsList.contains(md)){
					result += "private DSCStarBehaviour " + md.getName().toString() + ";\n";
				}
			}
		}
		return result;
	}
	
	/** This method creates the simple state behaviour of the new agent.
	 * @return result string.
	 */
	private String createInnerClassesOfSimpleStatesBehaviour(){
		//metodo che crea l'inner class del behaviour degli stati semplici
		
		String result = "";
		result += "private class SimpleStateBehaviour extends Behaviour {\n";
		result += "public SimpleStateBehaviour(Agent anAgent, String aName) {\n";
		result += "super(anAgent);\n";
		result += "this.myAgent = anAgent;\n";
		result += "setBehaviourName(aName);\n";
		result += "}\n";
		result += "public void action() {}\n";
		result += "public boolean done() {\n";
		result += "return true;\n";
		result += "}\n";
		result += "}\n";
		return result;
	}
	
	/** This method creates the "postEvent(DSCStarEvent)" method of the new agent.
	 * @return result string.
	 */
	private String createPostEventMethod(){
		//creiamo il metodo "postEvent(DSCStarEvent)", che permette di settare in maniera adeguata il
		//campo conversation-id dell'evento da mandare; inoltre, per mandare l'evento viene utilizzato
		//il metodo "postMessage"; perchè, quando viene utilizzato il metodo "send" per mandare un evento
		//che estende la classe ACLMessage (come questo evento) e la piattaforma è distribuita (cioè,
		//costituita da più container), l'evento giunge a destinazione come un semplice ACLMessage e non
		//risulta più come istanza della classe originaria che estende ACLMessage.
		
		String result = "";
		result += "public void postEvent(DSCStarEvent msg) {\n";
		result += "msg.setLanguage(\"DSC_STAR\");\n";
		result += "postMessage(msg);\n";
		result += "}\n";
		return result;
	}
	
	/** This method generates the event "E".
	 * @return result string.
	 */
	private String createE(){
		//metodo che crea l'evento "E"
		
		String result = "";
		result += "DSCStarEvent e = new DSCStarEvent(DSCStarEvent.E, null);\n";
		result += "postEvent(e);\n";
		return result;
	}
	
	/** This method creates a trigger based only on event "E".
	 * @return result string.
	 */
	private String createTriggerE(){
		//metodo che crea il trigger di una transizione basato
		//soltanto sul controllo che l'evento sia di tipo "E"
		
		String result = "";
		result += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		result += "return true;\n";
		result += "}\n";
		result += "return false;\n";
		return result;
	}

	/** This method creates a composite state.
	 * @param md the method that represents the composite state.
	 */
	private void createCompositeState(MethodDeclaration md){
		//metodo che crea uno stato composto
		
		//creiamo lo stato composto, passandogli il suo oggetto variabili
		int contVarMethod = Util.contVarsOfMethod(md);
		String oggVar = "";
		if(contVarMethod > 0){
			oggVar = "new Var" + Util.firstToUpper(md.getName().toString()) + "()";
		}
		else{
			oggVar = "null";
		}
		String methodNameUpperCase = md.getName().toString().toUpperCase();
		this.stringCreateCompositeStates += md.getName().toString() + " = new DSCStarBehaviour(this, \"" + methodNameUpperCase + "\", MessageTemplate.MatchLanguage(\"DSC_STAR\"), false, " + oggVar + ");\n";
		
		//se non è stato già creato, creiamo lo stato composto del DSC* grafico
		if(((mxGraphModel) graph.getModel()).getCell(md.getName().toString().toUpperCase()) == null){
			createGraphicCompositeState(md);
		}

		//creiamo lo stato semplice iniziale
		this.contSimpleStates++;
		createInitialSimpleState(this.contSimpleStates, md);
	
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = this.contSimpleStates;

		//creiamo il secondo stato semplice e la transizione che va in questo stato

		//creiamo il trigger: controlla l'evento CPE
		String trigger2 = "";
		trigger2 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getName().equalsIgnoreCase(\"CPE_" + methodNameUpperCase + "\"))){\n";
		trigger2 += "return true;\n";
		trigger2 += "}\n";
		trigger2 += "return false;\n";
		
		String graphicTrigger = "CPE_" + methodNameUpperCase;
		
		//creiamo l'azione
		String action2 = "";
		String graphicAction = "";
		if(md.parameters().size() > 0){
			//se il metodo ha dei parametri, trasferiamo i parametri ricevuti
			//nell'evento CPE allo stato composto che rappresenta il metodo

			action2 += "CPE_" + methodNameUpperCase + " cpe = (CPE_" + methodNameUpperCase + ") msg;\n";
			for(int j=0; j < md.parameters().size(); j++){
				if(md.parameters().get(j) instanceof SingleVariableDeclaration){
					SingleVariableDeclaration par = (SingleVariableDeclaration) md.parameters().get(j);
					String parName = par.getName().toString();
					action2 += translateVars(par.getName(), md) + " = cpe." + parName + ";\n";
					graphicAction += parName + ", ";
				}
			}
			if(graphicAction.endsWith(", ")){
				graphicAction = graphicAction.substring(0, graphicAction.length() - 2);
			}
			graphicAction += " = extract parameters\n";
			graphicAction += "contained in CPE_" + methodNameUpperCase + ";\n";
		}
		action2 += createE();
		graphicAction += "postEvent(E);";

		this.contSimpleStates++;
		int targetState2 = this.contSimpleStates; //prossimo stato
		
		//creiamo lo stato semplice target
		createIntermediateSimpleState(targetState2, md);
			
		//creiamo la transizione normale
		createNormalTransition(this.actualState, targetState2, md, trigger2, action2, graphicTrigger, graphicAction);
			
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = targetState2;
	}
	
	/** This method creates an intermediate simple state (not initial and not final).
	 * @param state the simple state number.
	 * @param md the method that contains this simple state.
	 */
	private void createIntermediateSimpleState(int state, MethodDeclaration md){
		//metodo che crea uno stato semplice intermedio (non iniziale e non finale)
		
		this.stringCreateSimpleStates += "Behaviour s" + state + " = new SimpleStateBehaviour(this, \"S" + state + "\");\n";
		this.stringAddSimpleStates += md.getName().toString() + ".addState(s" + state + ");\n";
		
		//creiamo lo stato intermedio nello stato composto del DSC* grafico
		String stateName = md.getName().toString().toUpperCase();
		Object methodState = ((mxGraphModel) graph.getModel()).getCell(stateName);
		graph.insertVertex(methodState, "S" + state, "S" + state, 1, 1, 10, 10, this.stateStyle);
	}
	
	/** This method creates an initial simple state.
	 * @param state the simple state number.
	 * @param md the method that contains this simple state.
	 */
	private void createInitialSimpleState(int state, MethodDeclaration md){
		//metodo che crea uno stato semplice iniziale
		
		this.stringCreateSimpleStates += "Behaviour s" + state + " = new SimpleStateBehaviour(this, \"S" + state + "\");\n";
		this.stringAddSimpleStates += md.getName().toString() + ".addInitialState(s" + state + ");\n";
		
		//creiamo lo stato iniziale dello stato composto del DSC* grafico
		String stateName = md.getName().toString().toUpperCase();
		Object methodState = ((mxGraphModel) graph.getModel()).getCell(stateName);
		Object init = graph.insertVertex(methodState, "INIT_" + stateName, "", 1, 1, 16, 16, this.initStyle); //crea il pallino nero iniziale
		Object initialState = graph.insertVertex(methodState, "S" + state, "S" + state, 1, 1, 10, 10, this.stateStyle); //crea lo stato iniziale
		graph.insertEdge(methodState, null, "", init, initialState, this.transitionStyle); //crea la transizione necessaria
	}

	/** This method creates a final simple state.
	 * @param state the simple state number.
	 * @param md the method that contains this simple state.
	 */
	private void createFinalSimpleState(int state, MethodDeclaration md){
		//metodo che crea uno stato semplice finale
		
		this.stringCreateSimpleStates += "Behaviour s" + state + " = new SimpleStateBehaviour(this, \"S" + state + "\");\n";
		this.stringAddSimpleStates += md.getName().toString() + ".addFinalState(s" + state + ");\n";
		
		//creiamo lo stato finale dello stato composto del DSC* grafico
		String stateName = md.getName().toString().toUpperCase();
		Object methodState = ((mxGraphModel) graph.getModel()).getCell(stateName);
		Object finalState = graph.insertVertex(methodState, "S" + state, "S" + state, 1, 1, 10, 10, this.stateStyle); //crea lo stato finale
		Object endPart1 = graph.insertVertex(methodState, "END_" + stateName, "", 1, 1, 16, 16, this.endPart1Style); //prima parte del pallino terminale (NON CAMBIARE I VALORI NUMERICI)
		mxGeometry geo = new mxGeometry(0.5, 0.5, 6, 6);
		geo.setOffset(new mxPoint(-3, -3));
		geo.setRelative(true);
		mxCell endPart2 = new mxCell(null, geo, this.initStyle); //seconda parte del pallino terminale (NON CAMBIARE I VALORI NUMERICI)
		endPart2.setVertex(true);
		graph.addCell(endPart2, endPart1);
		graph.insertEdge(methodState, null, "", finalState, endPart1, this.transitionStyle); //crea la transizione necessaria
	}

	/** This method creates a normal transition.
	 * @param sourceState the source state number.
	 * @param targetState the target state number.
	 * @param md the method under translation.
	 * @param trigger the real trigger of the transition.
	 * @param action the real action of the transition.
	 * @param graphicTrigger the trigger to insert into the graphic DSC*.
	 * @param graphicAction the action to insert into the graphic DSC*.
	 */
	private void createNormalTransition(int sourceState, int targetState, MethodDeclaration md, String trigger, String action, String graphicTrigger, String graphicAction){
		//metodo che crea una transizione normale
		
		this.contTransitions++;
		String methodName = Util.firstToUpper(md.getName().toString());
		int contVarRoot = Util.contVarsOfRoot(unit);
		int contVarMethod = Util.contVarsOfMethod(md);
		
		//creiamo la transizione
		this.stringCreateTransitions += "DSCStarNormalTransition t" + this.contTransitions + " = new DSCStarNormalTransition(\"T" + this.contTransitions + "\", s" + sourceState + ", s" + targetState + "){\n";
		
		//creiamo il trigger
		if(trigger.equals("") == false){
			this.stringCreateTransitions += "public boolean trigger(Behaviour source, ACLMessage msg, DSCStarVariablesAndParameters sourceVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters){\n";
			if(contVarRoot > 0){
				this.stringCreateTransitions += "VarRoot varRoot = (VarRoot) rootVariablesAndParameters;\n";
			}
			if(contVarMethod > 0){
				this.stringCreateTransitions += "Var" + methodName + " var" + methodName + " = (Var" + methodName + ") sourceVariablesAndParameters;\n";
			}
			this.stringCreateTransitions += trigger;
			this.stringCreateTransitions += "}\n";	
		}
		
		//creiamo l'azione
		if(action.equals("") == false){
			this.stringCreateTransitions += "public void action(ACLMessage msg, DSCStarVariablesAndParameters sourceVariablesAndParameters, DSCStarVariablesAndParameters rootVariablesAndParameters){\n";
			if(contVarRoot > 0){
				this.stringCreateTransitions += "VarRoot varRoot = (VarRoot) rootVariablesAndParameters;\n";
			}
			if(contVarMethod > 0){
				this.stringCreateTransitions += "Var" + methodName + " var" + methodName + " = (Var" + methodName + ") sourceVariablesAndParameters;\n";
			}
			this.stringCreateTransitions += action;
			this.stringCreateTransitions += "}\n";
		}
		
		this.stringCreateTransitions += "};\n";
		
		//aggiungiamo la transizione
		this.stringAddTransitions += "root.addTransition(t" + this.contTransitions + ");\n";
		
		//creiamo la transizione nel DSC* grafico
		String stateName = md.getName().toString().toUpperCase();
		Object methodState = ((mxGraphModel) graph.getModel()).getCell(stateName);
		Object source = ((mxGraphModel) graph.getModel()).getCell("S" + sourceState);
		Object target = ((mxGraphModel) graph.getModel()).getCell("S" + targetState);
		graph.insertEdge(methodState, null, graphicTrigger + "\n/" + graphicAction, source, target, this.transitionStyle);
	}

	/** This method creates a star transition.
	 * @param currentMethod the method under translation.
	 * @param calledMethod the called method.
	 */
	private void createStarTransition(MethodDeclaration currentMethod, MethodDeclaration calledMethod){
		//metodo che crea una transizione di tipo "*"

		String transition = currentMethod.getName().toString() + "->" + calledMethod.getName().toString();
		if(!(this.starTransitionsList.contains(transition))){
			//creiamo la transizione soltanto se non è stata già creata in precedenza
			this.contTransitions++;
			this.starTransitionsList.add(transition);

			//creiamo la transizione
			this.stringCreateTransitions += "DSCStarStarTransition t" + this.contTransitions + " = new DSCStarStarTransition(" + currentMethod.getName().toString() + ", " + calledMethod.getName().toString() + ");\n";

			//aggiungiamo la transizione
			this.stringAddTransitions += "root.addTransition(t" + this.contTransitions + ");\n";
			
			//creiamo le transizioni */<<R>> nel DSC* grafico
			
			//se non è stato già creato, creiamo lo stato composto di destinazione nel DSC* grafico
			String calledMethodStateName = calledMethod.getName().toString().toUpperCase();
			if(((mxGraphModel) graph.getModel()).getCell(calledMethodStateName) == null){
				createGraphicCompositeState(calledMethod);
			}
			
			String currentMethodStateName = currentMethod.getName().toString().toUpperCase();
			Object activeState = ((mxGraphModel) graph.getModel()).getCell("ACTIVE");
			Object currentMethodState = ((mxGraphModel) graph.getModel()).getCell(currentMethodStateName);
			Object calledMethodState = ((mxGraphModel) graph.getModel()).getCell(calledMethodStateName);
			graph.insertEdge(activeState, null, "Call" + calledMethodStateName + " (*)", currentMethodState, calledMethodState, this.transitionStyle); //transizione *
			if(((mxGraphModel) graph.getModel()).getCell("SHALLOW_HISTORY_" + currentMethodStateName) == null){
				graph.insertVertex(currentMethodState, "SHALLOW_HISTORY_" + currentMethodStateName, "H", 1, 1, 20, 20, this.historyStyle); //crea il connettore di storia superficiale
			}
			Object shallowHistory = ((mxGraphModel) graph.getModel()).getCell("SHALLOW_HISTORY_" + currentMethodStateName);
			graph.insertEdge(activeState, null, "ReturnFrom" + calledMethodStateName + " (<<R>>)", calledMethodState, shallowHistory, this.transitionStyle); //transizione <<R>>
		}
	}

	/** This method translates the node passed as parameter to use
	 * the variable object of the method passed as parameter or of the root.
	 * @param node the node to translate.
	 * @param method the method.
	 */
	private String translateVars(ASTNode node, MethodDeclaration method){
		//Metodo che permette di tradurre il nodo passato come parametro
		//in modo da usare l'oggetto variabili necessario
		//(ad esempio: l'istruzione "i = x + y;" deve essere tradotta in
		//"OGG_VAR.i = OGG_VAR.x + OGG_VAR.y", dove "OGG_VAR" rappresenta
		//l'oggetto variabili da utilizzare).
		//Il parametro "MethodDeclaration" indica che bisogna usare l'oggetto
		//variabili di questo metodo o quello della root (non quello di qualche
		//altro metodo).
		
		//troviamo tutti i "SimpleName" del nodo passato come parametro
		ArrayList<SimpleName> simpleNamesList = null;
		if(node instanceof SimpleName){
			simpleNamesList = new ArrayList<SimpleName>();
			simpleNamesList.add((SimpleName) node);
		}
		else{
			//utilizziamo l'apposito visitor per trovare tutti i "SimpleName"
			//del nodo passato come parametro e dei suoi sotto-nodi
			SimpleNameVisitor visitor = new SimpleNameVisitor();
			node.accept(visitor);
			simpleNamesList = visitor.getSimpleNamesList();
		}
		
		//ricaviamo l'istruzione da tradurre
		String istruzione = "";
		if(node instanceof VariableDeclarationStatement){
			//se il nodo passato come parametro rappresenta la dichiarazione
			//di una variabile, dobbiamo eliminare il tipo della variabile a
			//sinistra dell'istruzione; ad esempio, se abbiamo questa istruzione:
			// int x = y + z;
			//eliminiamo "int" dalla parte sinistra e l'istruzione diventa:
			// OGG_VAR.x = OGG_VAR.y + OGG_VAR.z;
			VariableDeclarationStatement vd = (VariableDeclarationStatement) node;
			for(int i = 0; i < vd.fragments().size(); i++){
				if(vd.fragments().get(i) instanceof VariableDeclarationFragment){
					VariableDeclarationFragment v = (VariableDeclarationFragment) vd.fragments().get(i);
					if(v.getInitializer() != null){
						//le dichiarazioni di variabili senza assegnazione di valore
						//(cioè, quelle che hanno "getInitializer() = null", come ad
						//esempio: "int x;") non vanno tradotte; traduciamo solo
						//quelle che assegnano un valore alle variabili (es.: "int x = 1;")
						istruzione += v.toString() + ";";
					}
				}
			}
		}
		else{
			istruzione = node.toString(); //contiene l'istruzione rappresentata dal nodo passato come parametro
		}

		if(istruzione.equals("") == false){
			//prima di manipolare la stringa che rappresenta l'istruzione,
			//sostituiamo il testo tra virgolette (che non deve essere
			//modificato) con il carattere speciale "@"; poi, al termine
			//delle modifiche, ogni carattere speciale "@" verrà sostituito
			//con il testo tra virgolette originale
			ArrayList<String> stringheTraVirgolette = new ArrayList<String>();
			ArrayList<Integer> indiciVirgolette = new ArrayList<Integer>();
			
			for(int i=0; i < istruzione.length(); i++){
				char carattereCorr = istruzione.charAt(i);
				if( ((i == 0) && (carattereCorr == '\"')) ||
					((i > 0) && (carattereCorr == '\"') && (istruzione.charAt(i - 1) != '\\')) ){
					//se abbiamo trovato il carattere " e questo carattere
					//non è preceduto dal carattere \ (per ignorare la rappresentazione
					//di una virgoletta all'interno di un testo tra virgolette),
					//memorizziamo l'indice attuale
					indiciVirgolette.add(i);
				}
			}
			
			//memorizziamo il testo tra virgolette
			int k = 0;
			while(k < (indiciVirgolette.size() - 1)){
				String sottostringa = istruzione.substring(indiciVirgolette.get(k), indiciVirgolette.get(k + 1) + 1);
				stringheTraVirgolette.add(sottostringa);
				k = k + 2;
			}
			
			//sostituiamo il testo tra virgolette con il carattere
			//speciale "@" seguito da un numero che permette di identificare
			//successivamente quale sottostringa è stata sostituita
			for(int i=0; i < stringheTraVirgolette.size(); i++){
				istruzione = istruzione.replace(stringheTraVirgolette.get(i), "@" + i);
			}

			//separiamo con il carattere speciale "#" i vari componenti dell'istruzione
			//per effettuare successivamente in maniera corretta la sostituzione
			//dei nomi delle variabili (ogni nome di variabile è preceduto e seguito
			//dal carattere speciale "#" o da uno spazio vuoto)
			istruzione = "#" + istruzione + "#";
			istruzione = istruzione.replace("+", "#+#");
			istruzione = istruzione.replace("-", "#-#");
			istruzione = istruzione.replace("~", "#~#");
			istruzione = istruzione.replace("!", "#!#");
			istruzione = istruzione.replace("*", "#*#");
			istruzione = istruzione.replace("/", "#/#");
			istruzione = istruzione.replace("%", "#%#");
			istruzione = istruzione.replace("<", "#<#");
			istruzione = istruzione.replace(">", "#>#");
			istruzione = istruzione.replace("=", "#=#");
			istruzione = istruzione.replace("&", "#&#");
			istruzione = istruzione.replace("^", "#^#");
			istruzione = istruzione.replace("|", "#|#");
			istruzione = istruzione.replace("?", "#?#");
			istruzione = istruzione.replace(":", "#:#");
			istruzione = istruzione.replace("(", "#(#");
			istruzione = istruzione.replace(")", "#)#");
			istruzione = istruzione.replace("[", "#[#");
			istruzione = istruzione.replace("]", "#]#");
			istruzione = istruzione.replace("{", "#{#");
			istruzione = istruzione.replace("}", "#}#");
			istruzione = istruzione.replace(",", "#,#");
			istruzione = istruzione.replace(".", "#.#");
			istruzione = istruzione.replace(";", "#;#");
						
			//per ogni "SimpleName" trovato, vediamo quale variabile rappresenta
			//e scegliamo di conseguenza l'oggetto variabili da utilizzare.
			//NOTA: per l'oggetto variabili della root utilizziamo il nome "varRoot",
			//mentre per gli oggetti variabili degli stati composti utilizziamo il
			//formato "varXxx", dove "xxx" è il nome del metodo
			for(int i = 0; i < simpleNamesList.size(); i++){
				SimpleName sn = simpleNamesList.get(i);
				
				//cerchiamo la variabile con il nome corrente
				//e riceviamo l'oggetto variabili da utilizzare
				String oggVar = Util.findVar(sn, method, this.unit);
				
				if(oggVar != null){
					//se "oggVar" è diverso da NULL, significa che la
					//variabile è stata trovata, quindi anteponiamo il
					//nome dell'oggetto variabili al nome della variabile
					//all'interno dell'istruzione
					istruzione = istruzione.replace("#" + sn.toString() + "#", "#" + oggVar + "." + sn.toString() + "#");
					istruzione = istruzione.replace("#" + sn.toString() + " ", "#" + oggVar + "." + sn.toString() + " ");
					istruzione = istruzione.replace(" " + sn.toString() + "#", " " + oggVar + "." + sn.toString() + "#");
					istruzione = istruzione.replace(" " + sn.toString() + " ", " " + oggVar + "." + sn.toString() + " ");
				}
			}
			
			//eliminiamo i caratteri speciali "#"
			istruzione = istruzione.replace("#", "");
			
			//eliminiamo i caratteri speciali "@", sostituendo ad
			//ogni carattere "@" il testo tra virgolette originale
			for(int i=0; i < stringheTraVirgolette.size(); i++){
				istruzione = istruzione.replace("@" + i, stringheTraVirgolette.get(i));
			}
		}

		return istruzione;
	}
	
	/** This method handles "special" statements.
	 * @param currentStatement current statement to check.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 * @return a boolean that indicates if the statement is "special" (true) or not (false).
	 */
	private boolean handleSpecialStatement(ASTNode currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che gestisce gli statement "speciali";
		//restituisce un booleano che indica se lo statement è "speciale" (TRUE) o no (FALSE)
		
		if((currentStatement instanceof ExpressionStatement) &&
				(((ExpressionStatement) currentStatement).toString().replace("\n", "").equals(MOVE_KEY) ||
				((ExpressionStatement) currentStatement).toString().replace("\n", "").equals(CLONE_KEY) ||
				((ExpressionStatement) currentStatement).toString().replace("\n", "").equals(CHECKPOINT_KEY) ||
				((ExpressionStatement) currentStatement).toString().replace("\n", "").equals(PERSISTENCE_KEY))){
			//trovato uno STATEMENT CHIAVE (MOVE, CLONE, CHECKPOINT o PERSISTENCE)
			
			//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
			//passandogli la lista attuale degli statement da gestire
			translateKeyStatement((ExpressionStatement) currentStatement, md, normalStatementsList);
			return true;
		}
		else if(currentStatement instanceof IfStatement && canTranslate(currentStatement)){
			//trovato un IF

			//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
			//passandogli la lista attuale degli statement da gestire
			translateIfStatement((IfStatement) currentStatement, md, normalStatementsList);
			return true;
		}
		else if(currentStatement instanceof WhileStatement && canTranslate(currentStatement)){
			//trovato un WHILE

			//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
			//passandogli la lista attuale degli statement da gestire
			translateWhileStatement((WhileStatement) currentStatement, md, normalStatementsList);
			return true;
		}
		else if(currentStatement instanceof ForStatement && canTranslate(currentStatement)){
			//trovato un FOR
			
			//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
			//passandogli la lista attuale degli statement da gestire
			translateForStatement((ForStatement) currentStatement, md, normalStatementsList);
			return true;
		}
		else if((currentStatement instanceof VariableDeclarationStatement) || 
					(currentStatement instanceof ExpressionStatement)){
			//controlliamo se si tratta di un'invocazione di un metodo da tradurre in maniera "speciale"
			
			String calledMethodName = "";
			if(currentStatement instanceof VariableDeclarationStatement){
				VariableDeclarationFragment v = (VariableDeclarationFragment)(((VariableDeclarationStatement) currentStatement).fragments().get(0));
				Expression initializer = v.getInitializer();
				if((initializer != null) && (initializer instanceof MethodInvocation)){
					calledMethodName = ((MethodInvocation) initializer).getName().toString();
				}
			}
			else if(currentStatement instanceof ExpressionStatement){
				Expression ex = ((ExpressionStatement) currentStatement).getExpression();
				if(ex != null){
					MethodInvocation mi = null;
					if(ex instanceof MethodInvocation){
						mi = (MethodInvocation) ex;
					}
					else if(ex instanceof Assignment){
						Expression rightSide = ((Assignment) ex).getRightHandSide();
						if(rightSide instanceof MethodInvocation){
							mi = (MethodInvocation) rightSide;
						}
					}
					if(mi != null){
						calledMethodName = mi.getName().toString();
					}
				}
			}
			
			//controlliamo se il metodo invocato appartiene al file java di origine
			//e se questo metodo verrà tradotto, altrimenti l'invocazione di questo
			//metodo NON deve essere tradotta in maniera "speciale" (ad esempio,
			//la chiamata "System.out.println" NON deve essere tradotta in maniera "speciale")
			MethodDeclaration calledMethod = Util.getMethodDeclaration(this.unit, calledMethodName);
			if(calledMethod != null && canTranslate(calledMethod)){
				//si tratta di un'invocazione di un metodo "speciale", quindi
				//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
				//passandogli la lista attuale degli statement da gestire
				translateMethodInvocation(currentStatement, md, normalStatementsList);
				return true;
			}
			else{
				//si tratta di uno statement "normale"
				return false;
			}
		}
		else if(currentStatement instanceof ReturnStatement){
			//trovato un RETURN

			//chiamiamo il metodo addetto alla traduzione di questo statement "speciale",
			//passandogli la lista attuale degli statement da gestire
			translateReturnStatement((ReturnStatement) currentStatement, md, normalStatementsList);
			return true;
		}
		
		return false;
	}
	
	/** This method indicates if the node passed as parameter must be translated or not.
	 * @param node the node.
	 * @return a boolean that indicates if the node must be translated (true) or not (false).
	 */
	private boolean canTranslate(ASTNode node){
		//Metodo utilizzato per stabilire se il nodo "speciale" (= if/while/for/metodo) passato
		//come parametro deve essere tradotto o meno.
		//Il metodo restituisce TRUE quando il nodo deve essere tradotto, altrimenti restituisce FALSE.
		
		if(this.translationType == Translator.FULL){
			//Se la traduzione è FULL, il nodo deve essere tradotto sempre
			return true;
		}
		else if(this.translationType == Translator.KEY_STATEMENTS_BASED){
			//Se la traduzione è KEY_STATEMENTS_BASED, il nodo "speciale" (= if/while/for/metodo)
			//deve essere tradotto se:
			//- contiene un key statement;
			//- contiene un'invocazione ad un metodo che contiene un key statement;
			//- contiene un return, ma il nodo non è un metodo.
			//Altrimenti, il nodo viene riportato così com'è nel file di destinazione.
			if(node instanceof MethodDeclaration){
				//il nodo è un metodo: cerchiamo al suo interno un key statement
				//o un'invocazione ad un metodo che contiene un key statement
				return findKeyStatement(node);
			}
			else if(node instanceof IfStatement || node instanceof WhileStatement ||
					node instanceof ForStatement){
				//il nodo è un if/while/for: cerchiamo al suo interno un key statement
				//oppure un'invocazione ad un metodo che contiene un key statement oppure un return
				KeyStatementsBasedVisitor visitor = new KeyStatementsBasedVisitor(this.unit);
				node.accept(visitor);
				if(findKeyStatement(node) || visitor.isReturnFound()){
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** This method indicates if the node passed as parameter contains a key statement or
	 * a method invocation that contains a key statement.
	 * @param node the node.
	 * @return a boolean that indicates if the node contains a key statement or
	 * a method invocation that contains a key statement.
	 */
	private boolean findKeyStatement(ASTNode node){
		//questo metodo cerca all'interno del nodo passato come parametro
		//un key statement o un'invocazione ad un metodo che contiene un key statement
		
		KeyStatementsBasedVisitor visitor = new KeyStatementsBasedVisitor(this.unit);
		node.accept(visitor);
		if(visitor.isKeyStatementFound()){
			//il nodo contiene un key statement
			return true;
		}
		else{
			//cerchiamo un'invocazione ad un metodo che contiene un key statement
			ArrayList<MethodDeclaration> methodsToCheck = new ArrayList<MethodDeclaration>(); //lista dei metodi invocati da controllare
			ArrayList<MethodDeclaration> invokedMethods = visitor.getInvokedMethodsList();
			for(int i=0; i < invokedMethods.size(); i++){
				//aggiungiamo alla lista dei metodi invocati da controllare
				//i metodi invocati dal nodo corrente (se non sono già presenti)
				MethodDeclaration m = invokedMethods.get(i);
				if(!methodsToCheck.contains(m)){
					methodsToCheck.add(m);
				}
			}
			for(int i=0; i < methodsToCheck.size(); i++){
				MethodDeclaration currentInvokedMethod = methodsToCheck.get(i);
				KeyStatementsBasedVisitor visitor2 = new KeyStatementsBasedVisitor(this.unit);
				currentInvokedMethod.accept(visitor2);
				if(visitor2.isKeyStatementFound()){
					//il metodo invocato corrente contiene un key statement
					return true;
				}
				else{
					//aggiungiamo alla lista dei metodi invocati da controllare
					//i metodi invocati dal metodo invocato corrente (se non sono già presenti)
					ArrayList<MethodDeclaration> invokedMethods2 = visitor2.getInvokedMethodsList();
					for(int j=0; j < invokedMethods2.size(); j++){
						MethodDeclaration m = invokedMethods2.get(j);
						if(!methodsToCheck.contains(m)){
							methodsToCheck.add(m);
						}
					}
				}
			}
		}
		return false;
	}

	/** This method translates a code block.
	 * @param block the code block.
	 * @param md the method under translation.
	 * @return a boolean that indicates if the translation was interrupted (true) or not (false).
	 */
	private boolean translateBlock(Block block, MethodDeclaration md){
		//metodo che traduce un blocco di codice:
		//restituisce un booleano che indica se la traduzione del blocco
		//è stata interrotta perchè è stato trovato un "return"

		LinkedList<ASTNode> normalStatementsList = new LinkedList<ASTNode>(); //lista degli statement "normali" in attesa di essere gestiti
		
		for(int i = 0; i < block.statements().size(); i++){
			ASTNode currentStatement = (ASTNode) block.statements().get(i); //ricaviamo lo statement corrente

			if(this.translationType == KEY_STATEMENTS_BASED){
				
				//se la traduzione è basata sulle parole chiave,
				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli la lista attuale degli statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement, md, normalStatementsList);
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//lo aggiungiamo nella lista degli statement "normali" da gestire
					normalStatementsList.addLast(currentStatement);
				}
				else{
					//invece, se lo statement corrente è uno statement "speciale",
					//svuotiamo la lista degli statement da gestire (perchè sono stati 
					//gestiti dal metodo addetto alla traduzione dello statement "speciale")
					normalStatementsList.clear();
				}
			}
			else if(this.translationType == FULL){
				
				//se la traduzione è di tipo "FULL",
				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli una lista vuota di statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement, md, new LinkedList<ASTNode>());
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//chiamiamo il metodo per tradurre gli statement "normali"
					//in caso di traduzione "FULL"
					translateNormalStatementForFullTranslation(currentStatement, md);
				}
			}

			if(currentStatement instanceof ReturnStatement){
				//è stato trovato un "return" all'interno di questo blocco,
				//quindi interrompiamo la traduzione del blocco
				return true; //indichiamo che la traduzione del blocco è stata interrotta perchè è stato trovato un "return"
			}
			else if(i == (block.statements().size() - 1)){

				if(this.translationType == KEY_STATEMENTS_BASED){
					//siamo arrivati alla fine del blocco, quindi creiamo una transizione che va
					//nell'ultimo stato del blocco utilizzando gli statement da gestire rimasti
	
					//creiamo l'azione
					String action = "";
					String graphicAction = "";
					for(int j=0; j < normalStatementsList.size(); j++){
						String s = translateVars(normalStatementsList.get(j), md);
						if(s.equals("") == false){
							action += Util.appendNewLine(s);
							graphicAction += Util.appendNewLine(normalStatementsList.get(j).toString());
						}
					}

					if( ((block.getParent()).equals(md)) &&
							((md.getReturnType2().toString()).equalsIgnoreCase("void")) ){
						//se siamo giunti alla fine del metodo e quest'ultimo
						//ha un ritorno di tipo "void", creiamo gli eventi "ReturnFrom" e "RPE"
						action += "DSCStarEvent returnFrom = new DSCStarEvent(DSCStarEvent.RETURN_FROM, " + md.getName().toString() + ");\n";
						action += "postEvent(returnFrom);\n";
						action += "DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, " + md.getName().toString() + ");\n";
						action += "postEvent(rpe);\n";
						graphicAction += "postEvent(ReturnFrom" + md.getName().toString().toUpperCase() + ");\n";
						graphicAction += "postEvent(RPE_" + md.getName().toString().toUpperCase() + ");";
					}
					else if(action.equals("") == false){
						//se fino a questo punto l'azione non è vuota, inviamo l'evento "E"
						action += createE();
						graphicAction += "postEvent(E);";
					}
					
					if( (action.equals("") == false) ||
							( ((block.getParent()).equals(md)) &&
							((md.getName().toString()).equalsIgnoreCase("main")) )
								){
						//se fino a questo punto l'azione non è vuota, oppure se
						//siamo giunti alla fine del metodo "main", procediamo
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();
						String graphicTrigger = "E";

						this.contSimpleStates++;
						int lastState = this.contSimpleStates; //ultimo stato del blocco
						
						//creiamo l'ultimo stato del blocco
						if( ((block.getParent()).equals(md)) &&
								((md.getName().toString()).equalsIgnoreCase("main")) ){
							//se siamo giunti alla fine del metodo "main",
							//creiamo un sotto-stato finale 
							createFinalSimpleState(lastState, md);
						}
						else{
							//altrimenti, creiamo un sotto-stato intermedio
							createIntermediateSimpleState(lastState, md);
						}
						
						//creiamo la transizione che va dallo stato attuale all'ultimo stato del blocco
						createNormalTransition(this.actualState, lastState, md, trigger, action, graphicTrigger, graphicAction);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = lastState;
					}
				}
				else if(this.translationType == FULL){
					//siamo arrivati alla fine del blocco
					
					if( ((block.getParent()).equals(md)) &&
							((md.getReturnType2().toString()).equalsIgnoreCase("void")) ){
						//se siamo giunti alla fine del metodo e quest'ultimo
						//ha un ritorno di tipo "void", creiamo l'ultimo stato
						//del blocco e la transizione che va in questo stato
						
						//creiamo l'azione
						String action = "";
						String graphicAction = "";
						
						//creiamo gli eventi "ReturnFrom" e "RPE"
						action += "DSCStarEvent returnFrom = new DSCStarEvent(DSCStarEvent.RETURN_FROM, " + md.getName().toString() + ");\n";
						action += "postEvent(returnFrom);\n";
						action += "DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, " + md.getName().toString() + ");\n";
						action += "postEvent(rpe);\n";
						graphicAction += "postEvent(ReturnFrom" + md.getName().toString().toUpperCase() + ");\n";
						graphicAction += "postEvent(RPE_" + md.getName().toString().toUpperCase() + ");";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();
						String graphicTrigger = "E";

						this.contSimpleStates++;
						int lastState = this.contSimpleStates; //ultimo stato del blocco
						
						//creiamo l'ultimo stato del blocco
						if( ((block.getParent()).equals(md)) &&
								((md.getName().toString()).equalsIgnoreCase("main")) ){
							//se siamo giunti alla fine del metodo "main",
							//creiamo un sotto-stato finale
							createFinalSimpleState(lastState, md);
						}
						else{
							//altrimenti, creiamo un sotto-stato intermedio
							createIntermediateSimpleState(lastState, md);
						}
						
						//creiamo la transizione che va dallo stato attuale all'ultimo stato del blocco
						createNormalTransition(this.actualState, lastState, md, trigger, action, graphicTrigger, graphicAction);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = lastState;
					}
				}	
			}
		}
		
		//indichiamo che la traduzione del blocco NON è stata interrotta
		//perchè NON è stato trovato un "return"
		return false;
	}
	
	/** This method translates a key statement.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateKeyStatement(ExpressionStatement currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce uno statement chiave
			
		// creiamo il trigger: controlla solo che l'evento sia di tipo "E"
		String trigger = createTriggerE();
		String graphicTrigger = "E";

		// creiamo l'azione
		String action = "";
		String graphicAction = "";
		for (int i = 0; i < normalStatementsList.size(); i++) {
			String s = translateVars(normalStatementsList.get(i), md);
			if (s.equals("") == false) {
				action += Util.appendNewLine(s);
				graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
			}
		}
		action += Util.appendNewLine(currentStatement.toString());
		graphicAction += Util.appendNewLine(currentStatement.toString());
		action += createE();
		graphicAction += "postEvent(E);";

		this.contSimpleStates++;
		int targetState = this.contSimpleStates; // prossimo stato

		// creiamo lo stato semplice target
		createIntermediateSimpleState(targetState, md);
		
		// creiamo la transizione normale
		createNormalTransition(this.actualState, targetState, md, trigger, action, graphicTrigger, graphicAction);

		// aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = targetState;
	}
	
	/** This method translates an IF statement.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateIfStatement(IfStatement currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce un IF
		
		//creiamo lo stato che rappresenta l'ingresso nell'IF

		// creiamo l'azione
		String a = "";
		String graphicAction = "";
		for (int i = 0; i < normalStatementsList.size(); i++) {
			String s = translateVars(normalStatementsList.get(i), md);
			if (s.equals("") == false) {
				a += Util.appendNewLine(s);
				graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
			}
		}

		if (a.equals("") == false) {
			// se fino a questo punto l'azione non è vuota, creiamo la nuova
			// transizione, altrimenti lo stato che rappresenta l'ingresso
			// nell'IF è lo stato attuale

			a += createE();
			graphicAction += "postEvent(E);";

			// creiamo il trigger: controlla solo che l'evento sia di tipo "E"
			String trigger = createTriggerE();

			this.contSimpleStates++;
			int targetState = this.contSimpleStates; //prossimo stato (stato che rappresenta l'ingresso nell'IF)

			// creiamo lo stato semplice target
			createIntermediateSimpleState(targetState, md);
			
			// creiamo la transizione normale
			createNormalTransition(this.actualState, targetState, md, trigger, a, "E", graphicAction);

			// aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = targetState;
		}

		//A partire dallo stato che rappresenta l'ingresso nell'IF, creiamo due transizioni:
		// - una che parte dallo stato che rappresenta l'ingresso nell'IF e,
		//   rispettando la condizione dell'IF, va nello stato semplice iniziale del blocco IF
		//   (stato_ingresso_if ---- E[condizione_if]/postMessage(E) ---> stato_iniziale_blocco_if);
		// - l'altra che parte dallo stato che rappresenta l'ingresso nell'IF e,
		//   NON rispettando la condizione dell'IF, va nello stato semplice iniziale del blocco ELSE
		//   (stato_ingresso_if ---- E[!condizione_if]/postMessage(E) ---> stato_iniziale_blocco_else).
		//Queste due transizioni hanno come azione soltanto l'invio dell'evento "E" e specificano
		//il trigger, altrimenti successivamente non è possibile specificarlo.
		//Poi, la traduzione del blocco IF partirà dallo stato semplice "stato_iniziale_blocco_if",
		//mentre la traduzione del blocco ELSE partirà dallo stato semplice "stato_iniziale_blocco_else".
		
		//ricaviamo lo stato che rappresenta l'ingresso nell'IF (creato in precedenza)
		int stato_ingresso_if = this.actualState;
		
		//creiamo la transizione:
		//stato_ingresso_if ---- E[condizione_if]/postMessage(E) ---> stato_iniziale_blocco_if
		
		//creiamo il trigger
		String trigger1 = "";
		trigger1 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger1 += "if (" + translateVars(currentStatement.getExpression(), md) + "){\n"; //traduciamo la condizione dell'IF
		trigger1 += "return true;\n";
		trigger1 += "}\n";
		trigger1 += "}\n";
		trigger1 += "return false;\n";
		String graphicTrigger1 = "E[" + currentStatement.getExpression() + "]";

		//creiamo l'azione
		String action1 = createE();
		
		//creiamo lo stato iniziale del blocco IF
		this.contSimpleStates++;
		int stato_iniziale_blocco_if = this.contSimpleStates;
		createIntermediateSimpleState(stato_iniziale_blocco_if, md);
		
		//creiamo la transizione da "stato_ingresso_if" a "stato_iniziale_blocco_if"
		createNormalTransition(stato_ingresso_if, stato_iniziale_blocco_if, md, trigger1, action1, graphicTrigger1, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_iniziale_blocco_if;

		//traduciamo il blocco IF
		boolean traduzioneBloccoIfInterrotta = false; //indica se la traduzione è stata interrotta perchè è stato trovato un "return"
		if(currentStatement.getThenStatement() != null){
			//siamo nel caso in cui: if(...){ c'è qualche istruzione }
			
			if(currentStatement.getThenStatement() instanceof Block){
				//siamo nel caso in cui: if(...){ più istruzioni }
				traduzioneBloccoIfInterrotta = translateBlock((Block) currentStatement.getThenStatement(), md);
			}
			else{
				//siamo nel caso in cui: if(...) una istruzione
				
				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli una lista vuota di statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement.getThenStatement(), md, new LinkedList<ASTNode>());
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//lo gestiamo adesso creando un'apposita transizione
					Statement thenStat = currentStatement.getThenStatement();

					//creiamo l'azione
					String action = "";
					String graphicAction2 = "";
					String s = translateVars(thenStat, md);
					
					if(s.equals("") == false){
						//se l'istruzione attuale non è vuota, procediamo

						action += Util.appendNewLine(s);
						graphicAction2 += Util.appendNewLine(thenStat.toString());
						action += createE();
						graphicAction2 += "postEvent(E);";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();

						//creiamo lo stato semplice target
						this.contSimpleStates++;
						int targetState = this.contSimpleStates;
						createIntermediateSimpleState(targetState, md);
						
						//creiamo la transizione che va dallo stato iniziale del blocco IF al nuovo stato target
						createNormalTransition(stato_iniziale_blocco_if, targetState, md, trigger, action, "E", graphicAction2);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = targetState;
					}
				}
				else if(currentStatement.getThenStatement() instanceof ReturnStatement){
					//indichiamo che è stato trovato un "return" nell'unica istruzione dell'IF
					traduzioneBloccoIfInterrotta = true;
				}
			}
		}

		//ricaviamo lo stato finale del blocco IF
		int stato_finale_blocco_if = this.actualState;

		//creiamo la transizione:
		//stato_ingresso_if ---- E[!condizione_if]/postMessage(E) ---> stato_iniziale_blocco_else
		
		//creiamo il trigger
		String trigger2 = "";
		trigger2 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger2 += "if (!(" + translateVars(currentStatement.getExpression(), md) + ")){\n"; //traduciamo la condizione dell'IF
		trigger2 += "return true;\n";
		trigger2 += "}\n";
		trigger2 += "}\n";
		trigger2 += "return false;\n";
		String graphicTrigger2 = "E[!(" + currentStatement.getExpression() + ")]";

		//creiamo l'azione
		String action2 = createE();
		
		//creiamo lo stato iniziale del blocco ELSE
		this.contSimpleStates++;
		int stato_iniziale_blocco_else = this.contSimpleStates;
		createIntermediateSimpleState(stato_iniziale_blocco_else, md);
		
		//creiamo la transizione da "stato_ingresso_if" a "stato_iniziale_blocco_else"
		createNormalTransition(stato_ingresso_if, stato_iniziale_blocco_else, md, trigger2, action2, graphicTrigger2, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_iniziale_blocco_else;

		//traduciamo il blocco ELSE
		boolean traduzioneBloccoElseInterrotta = false; //indica se la traduzione è stata interrotta perchè è stato trovato un "return"
		if(currentStatement.getElseStatement() != null){
			//siamo nel caso in cui: else{ c'è qualche istruzione }
			
			if(currentStatement.getElseStatement() instanceof Block){
				//siamo nel caso in cui: else{ più istruzioni }
				traduzioneBloccoElseInterrotta = translateBlock((Block) currentStatement.getElseStatement(), md);
			}
			else{
				//siamo nel caso in cui: else una istruzione
				
				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli una lista vuota di statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement.getElseStatement(), md, new LinkedList<ASTNode>());
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//lo gestiamo adesso creando un'apposita transizione
					Statement elseStat = currentStatement.getElseStatement();
					
					//creiamo l'azione
					String action = "";
					String graphicAct = "";
					String s = translateVars(elseStat, md);
					
					if(s.equals("") == false){
						//se l'istruzione attuale non è vuota, procediamo
						
						action += Util.appendNewLine(s);
						graphicAct += Util.appendNewLine(elseStat.toString());
						action += createE();
						graphicAct += "postEvent(E);";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();

						//creiamo lo stato semplice target
						this.contSimpleStates++;
						int targetState = this.contSimpleStates;
						createIntermediateSimpleState(targetState, md);
						
						//creiamo la transizione che va dallo stato iniziale del blocco ELSE al nuovo stato target
						createNormalTransition(stato_iniziale_blocco_else, targetState, md, trigger, action, "E", graphicAct);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = targetState;
					}
				}
				else if(currentStatement.getElseStatement() instanceof ReturnStatement){
					//indichiamo che è stato trovato un "return" nell'unica istruzione dell'ELSE
					traduzioneBloccoElseInterrotta = true;
				}
			}
		}
		
		//ricaviamo lo stato finale del blocco ELSE
		int stato_finale_blocco_else = this.actualState;

		if(traduzioneBloccoIfInterrotta == false && traduzioneBloccoElseInterrotta == false){
			//se la traduzione del blocco IF e del blocco ELSE non è stata interrotta
			//(cioè, se non è stato trovato un "return" nel blocco IF e nel blocco ELSE),
			//dopo aver tradotto i blocchi dell'IF e dell'ELSE, creiamo un nuovo stato semplice
			//"stato_fin" e due transizioni:
			// - una che parte dallo stato finale del blocco IF e va nel nuovo stato semplice "stato_fin"
			//   (stato_finale_blocco_if ---- E/postMessage(E) ---> stato_fin);
			// - l'altra parte dallo stato finale del blocco ELSE e va nel nuovo stato semplice "stato_fin"
			//   (stato_finale_blocco_else ---- E/postMessage(E) ---> stato_fin);
			//Queste due transizioni permettono di eseguire le istruzioni successive all'IF/ELSE
			//presenti nel blocco di codice parent, sia se abbiamo eseguito l'IF che se abbiamo
			//eseguito l'ELSE. Infatti, quando riprenderà la traduzione nel blocco di codice
			//parent dell'IF/ELSE (nel metodo "translateBlock"), si ripartirà dallo stato attuale
			//"stato_fin" e le operazioni presenti dopo l'IF/ELSE nel blocco di codice parent
			//potranno essere eseguite sia quando viene eseguito l'IF che quando viene eseguito l'ELSE.
			
			//creiamo lo stato semplice "stato_fin"
			this.contSimpleStates++;
			int stato_fin = this.contSimpleStates;
			createIntermediateSimpleState(stato_fin, md);
			
			//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
			String trigger3 = createTriggerE();
			
			//creiamo l'azione
			String action3 = createE();
			
			//creiamo la transizione:
			//stato_finale_blocco_if ---- E/postMessage(E) ---> stato_fin
			createNormalTransition(stato_finale_blocco_if, stato_fin, md, trigger3, action3, "E", "postEvent(E);");
			
			//creiamo la transizione:
			//stato_finale_blocco_else ---- E/postMessage(E) ---> stato_fin
			createNormalTransition(stato_finale_blocco_else, stato_fin, md, trigger3, action3, "E", "postEvent(E);");
			
			//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = stato_fin;
		}
		else if(traduzioneBloccoIfInterrotta == false){
			//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = stato_finale_blocco_if;
		}
		else if(traduzioneBloccoElseInterrotta == false){
			//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = stato_finale_blocco_else;
		}
	}
	
	/** This method translates a WHILE statement.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateWhileStatement(WhileStatement currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce un WHILE

		//creiamo lo stato che rappresenta l'ingresso nel WHILE

		//creiamo l'azione
		String a = "";
		String graphicAction = "";
		for (int i = 0; i < normalStatementsList.size(); i++) {
			String s = translateVars(normalStatementsList.get(i), md);
			if (s.equals("") == false) {
				a += Util.appendNewLine(s);
				graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
			}
		}

		if (a.equals("") == false) {
			// se fino a questo punto l'azione non è vuota, creiamo la nuova
			// transizione, altrimenti lo stato che rappresenta l'ingresso nel
			// WHILE è lo stato attuale

			a += createE();
			graphicAction += "postEvent(E);";

			// creiamo il trigger: controlla solo che l'evento sia di tipo "E"
			String trigger = createTriggerE();

			this.contSimpleStates++;
			int targetState = this.contSimpleStates; // prossimo stato (stato che rappresenta l'ingresso nel WHILE)

			// creiamo lo stato semplice target
			createIntermediateSimpleState(targetState, md);
			
			// creiamo la transizione normale
			createNormalTransition(this.actualState, targetState, md, trigger, a, "E", graphicAction);

			// aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = targetState;
		}
		
		//ricaviamo lo stato che rappresenta l'ingresso nel WHILE (creato in precedenza)
		int stato_ingresso_while = this.actualState;

		//creiamo la transizione:
		//stato_ingresso_while ---- E[condizione_while]/postMessage(E) ---> stato_iniziale_blocco_while
		
		//creiamo il trigger
		String trigger1 = "";
		trigger1 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger1 += "if (" + translateVars(currentStatement.getExpression(), md) + "){\n"; //traduciamo la condizione del WHILE
		trigger1 += "return true;\n";
		trigger1 += "}\n";
		trigger1 += "}\n";
		trigger1 += "return false;\n";
		String graphicTrigger1 = "E[" + currentStatement.getExpression() + "]";

		//creiamo l'azione
		String action1 = createE();
		
		//creiamo lo stato iniziale del blocco WHILE
		this.contSimpleStates++;
		int stato_iniziale_blocco_while = this.contSimpleStates;
		createIntermediateSimpleState(stato_iniziale_blocco_while, md);
		
		//creiamo la transizione da "stato_ingresso_while" a "stato_iniziale_blocco_while"
		createNormalTransition(stato_ingresso_while, stato_iniziale_blocco_while, md, trigger1, action1, graphicTrigger1, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_iniziale_blocco_while;

		//traduciamo il blocco WHILE
		boolean traduzioneBloccoWhileInterrotta = false; //indica se la traduzione è stata interrotta perchè è stato trovato un "return"
		if(currentStatement.getBody() != null){
			//siamo nel caso in cui: while(...){ c'è qualche istruzione }
			
			if(currentStatement.getBody() instanceof Block){
				//siamo nel caso in cui: while(...){ più istruzioni }
				traduzioneBloccoWhileInterrotta = translateBlock((Block) currentStatement.getBody(), md);
			}
			else{
				//siamo nel caso in cui: while(...) una istruzione

				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli una lista vuota di statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement.getBody(), md, new LinkedList<ASTNode>());
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//lo gestiamo adesso creando un'apposita transizione
					Statement bodyStat = currentStatement.getBody();

					//creiamo l'azione
					String action = "";
					String graphicAct = "";
					String s = translateVars(bodyStat, md);
					
					if(s.equals("") == false){
						//se l'istruzione attuale non è vuota, procediamo
						
						action += Util.appendNewLine(s);
						graphicAct += Util.appendNewLine(bodyStat.toString());
						action += createE();
						graphicAct += "postEvent(E);";

						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();

						//creiamo lo stato semplice target
						this.contSimpleStates++;
						int targetState = this.contSimpleStates;
						createIntermediateSimpleState(targetState, md);
						
						//creiamo la transizione che va dallo stato iniziale del blocco WHILE al nuovo stato target
						createNormalTransition(stato_iniziale_blocco_while, targetState, md, trigger, action, "E", graphicAct);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = targetState;
					}
				}
				else if(currentStatement.getBody() instanceof ReturnStatement){
					//indichiamo che è stato trovato un "return" nell'unica istruzione del WHILE
					traduzioneBloccoWhileInterrotta = true;
				}
			}
		}

		//ricaviamo lo stato finale del blocco WHILE
		int stato_finale_blocco_while = this.actualState;

		if(traduzioneBloccoWhileInterrotta == false){
			//se la traduzione del blocco WHILE non è stata interrotta
			//(cioè, se non è stato trovato un "return" nel blocco WHILE),
			//creiamo la transizione:
			//stato_finale_blocco_while ---- E/postMessage(E) ---> stato_ingresso_while
			
			//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
			String trigger2 = createTriggerE();
			
			//creiamo l'azione
			String action2 = createE();
			
			//creiamo la transizione che va da "stato_finale_blocco_while" a "stato_ingresso_while"
			createNormalTransition(stato_finale_blocco_while, stato_ingresso_while, md, trigger2, action2, "E", "postEvent(E);");
		}

		//creiamo la transizione:
		//stato_ingresso_while ---- E[!condizione_while]/postMessage(E) ---> stato_fin
		
		//creiamo il trigger
		String trigger3 = "";
		trigger3 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger3 += "if (!(" + translateVars(currentStatement.getExpression(), md) + ")){\n"; //traduciamo la condizione del WHILE
		trigger3 += "return true;\n";
		trigger3 += "}\n";
		trigger3 += "}\n";
		trigger3 += "return false;\n";
		String graphicTrigger3 = "E[!(" + currentStatement.getExpression() + ")]";

		//creiamo l'azione
		String action3 = createE();
		
		//creiamo lo stato semplice "stato_fin"
		this.contSimpleStates++;
		int stato_fin = this.contSimpleStates;
		createIntermediateSimpleState(stato_fin, md);
		
		//creiamo la transizione da "stato_ingresso_while" a "stato_fin"
		createNormalTransition(stato_ingresso_while, stato_fin, md, trigger3, action3, graphicTrigger3, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_fin;
	}
	
	/** This method translates a FOR statement.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateForStatement(ForStatement currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce un FOR

		//creiamo lo stato che rappresenta l'ingresso nel FOR

		if(this.translationType == KEY_STATEMENTS_BASED){
			// creiamo l'azione
			String action = "";
			String graphicAction = "";
			for (int i = 0; i < normalStatementsList.size(); i++) {
				String s = translateVars(normalStatementsList.get(i), md);
				if (s.equals("") == false) {
					action += Util.appendNewLine(s);
					graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
				}
			}

			// inseriamo in questa azione l'inizializzazione degli indici del
			// FOR (ad esempio: for(i = 0; ... ))
			for (int i = 0; i < currentStatement.initializers().size(); i++) {
				String s = translateVars((ASTNode) currentStatement.initializers().get(i), md);
				if (s.equals("") == false) {
					action += s + ";\n";
					graphicAction += currentStatement.initializers().get(i) + ";\n";
				}
			}

			if (action.equals("") == false) {
				// se fino a questo punto l'azione non è vuota, creiamo la nuova
				// transizione, altrimenti lo stato che rappresenta l'ingresso
				// nel FOR è lo stato attuale

				action += createE();
				graphicAction += "postEvent(E);";

				// creiamo il trigger: controlla solo che l'evento sia di tipo "E"
				String trigger = createTriggerE();

				this.contSimpleStates++;
				int targetState = this.contSimpleStates; // prossimo stato (stato che rappresenta l'ingresso nel FOR)

				// creiamo lo stato semplice target
				createIntermediateSimpleState(targetState, md);
				
				// creiamo la transizione normale
				createNormalTransition(this.actualState, targetState, md, trigger, action, "E", graphicAction);

				// aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
				this.actualState = targetState;
			}
		}
		else if(this.translationType == FULL){
			//per ogni inizializzazione di un indice del FOR, creiamo una transizione
			for(int i=0; i < currentStatement.initializers().size(); i++){
				//inseriamo in questa azione l'inizializzazione dell'indice del FOR corrente
				//(ad esempio: for(i = 0; ... ))
				String s = translateVars((ASTNode) currentStatement.initializers().get(i), md);
				if(s.equals("") == false){	
					//creiamo l'azione
					String action = "";
					String graphicAction = "";
					action += s + ";\n";
					graphicAction += currentStatement.initializers().get(i) + ";\n";
					action += createE();
					graphicAction += "postEvent(E);";
						
					//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
					String trigger = createTriggerE();
						
					this.contSimpleStates++;
					int targetState = this.contSimpleStates; //prossimo stato (stato che rappresenta l'ingresso nel FOR)
						
					//creiamo lo stato semplice target
					createIntermediateSimpleState(targetState, md);
					
					//creiamo la transizione normale
					createNormalTransition(this.actualState, targetState, md, trigger, action, "E", graphicAction);
						
					//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
					this.actualState = targetState;
				}
			}
		}

		//ricaviamo lo stato che rappresenta l'ingresso nel FOR (creato in precedenza)
		int stato_ingresso_for = this.actualState;

		//creiamo la transizione:
		//stato_ingresso_for ---- E[condizione_for]/postMessage(E) ---> stato_iniziale_blocco_for
		
		//creiamo il trigger
		String trigger1 = "";
		trigger1 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger1 += "if (" + translateVars(currentStatement.getExpression(), md) + "){\n"; //traduciamo la condizione del FOR
		trigger1 += "return true;\n";
		trigger1 += "}\n";
		trigger1 += "}\n";
		trigger1 += "return false;\n";
		String graphicTrigger1 = "E[" + currentStatement.getExpression() + "]";

		//creiamo l'azione
		String action1 = createE();
		
		//creiamo lo stato iniziale del blocco FOR
		this.contSimpleStates++;
		int stato_iniziale_blocco_for = this.contSimpleStates;
		createIntermediateSimpleState(stato_iniziale_blocco_for, md);
		
		//creiamo la transizione da "stato_ingresso_for" a "stato_iniziale_blocco_for"
		createNormalTransition(stato_ingresso_for, stato_iniziale_blocco_for, md, trigger1, action1, graphicTrigger1, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_iniziale_blocco_for;

		//traduciamo il blocco FOR
		boolean traduzioneBloccoForInterrotta = false; //indica se la traduzione è stata interrotta perchè è stato trovato un "return"
		if(currentStatement.getBody() != null){
			//siamo nel caso in cui: for(...){ c'è qualche istruzione }
			
			if(currentStatement.getBody() instanceof Block){
				//siamo nel caso in cui: for(...){ più istruzioni }
				traduzioneBloccoForInterrotta = translateBlock((Block) currentStatement.getBody(), md);
			}
			else{
				//siamo nel caso in cui: for(...) una istruzione

				//chiamiamo il metodo incaricato di gestire gli statement "speciali",
				//passandogli una lista vuota di statement "normali" da gestire
				boolean specialStatement = handleSpecialStatement(currentStatement.getBody(), md, new LinkedList<ASTNode>());
				if(specialStatement == false){
					//se lo statement corrente non è uno statement "speciale",
					//lo gestiamo adesso creando un'apposita transizione
					Statement bodyStat = currentStatement.getBody();
					
					//creiamo l'azione
					String action = "";
					String graphicAct = "";
					String s = translateVars(bodyStat, md);
					
					if(s.equals("") == false){
						//se l'istruzione attuale non è vuota, procediamo
						
						action += Util.appendNewLine(s);
						graphicAct += Util.appendNewLine(bodyStat.toString());
						action += createE();
						graphicAct += "postEvent(E);";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();

						//creiamo lo stato semplice target
						this.contSimpleStates++;
						int targetState = this.contSimpleStates;
						createIntermediateSimpleState(targetState, md);
						
						//creiamo la transizione che va dallo stato iniziale del blocco FOR al nuovo stato target
						createNormalTransition(stato_iniziale_blocco_for, targetState, md, trigger, action, "E", graphicAct);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = targetState;
					}
				}
				else if(currentStatement.getBody() instanceof ReturnStatement){
					//indichiamo che è stato trovato un "return" nell'unica istruzione del FOR
					traduzioneBloccoForInterrotta = true;
				}
			}
		}

		//ricaviamo lo stato finale del blocco FOR
		int stato_finale_blocco_for = this.actualState;

		if(this.translationType == KEY_STATEMENTS_BASED){
			if(traduzioneBloccoForInterrotta == false){
				//se la traduzione del blocco FOR non è stata interrotta
				//(cioè, se non è stato trovato un "return" nel blocco FOR),
				//creiamo la transizione:
				//stato_finale_blocco_for ---- E / aggiornamento_indici_for; postMessage(E) ---> stato_ingresso_for
				
				//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
				String trigger = createTriggerE();
				
				//creiamo l'azione
				String action = "";
				String graphicAct = "";
				
				//inseriamo in questa azione l'aggiornamento degli indici del FOR
				//(ad esempio: for( ... ; ... ; i++))
				for(int i=0; i < currentStatement.updaters().size(); i++){
					String s = translateVars((ASTNode) currentStatement.updaters().get(i), md);
					if(s.equals("") == false){
						action += s + ";\n";
						graphicAct += currentStatement.updaters().get(i) + ";\n";
					}
				}
				
				action += createE();
				graphicAct += "postEvent(E);";
	
				//creiamo la transizione da "stato_finale_blocco_for" a "stato_ingresso_for"
				createNormalTransition(stato_finale_blocco_for, stato_ingresso_for, md, trigger, action, "E", graphicAct);
			}
		}
		else if(this.translationType == FULL){
			if(traduzioneBloccoForInterrotta == false){
				//se la traduzione del blocco FOR non è stata interrotta
				//(cioè, se non è stato trovato un "return" nel blocco FOR),
				//creiamo le transizioni che permettono di aggiornare gli indici
				//del FOR e di tornare in "stato_ingresso_for"
				
				//per ogni aggiornamento di un indice del FOR, creiamo una transizione
				for(int i=0; i < currentStatement.updaters().size(); i++){
					
					//inseriamo in questa azione l'aggiornamento dell'indice del FOR corrente
					//(ad esempio: for( ... ; ... ; i++))
					String s = translateVars((ASTNode) currentStatement.updaters().get(i), md);
					
					if(i == (currentStatement.updaters().size() - 1)){
						//creiamo la transizione che va dallo stato attuale
						//allo stato "stato_ingresso_for" 
						
						//creiamo l'azione
						String action = "";
						String graphicAct = "";
						if(s.equals("") == false){
							action += s + ";\n";
							graphicAct += currentStatement.updaters().get(i) + ";\n";
						}
						action += createE();
						graphicAct += "postEvent(E);";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();
							
						//creiamo la transizione che va dallo stato attuale a "stato_ingresso_for"
						createNormalTransition(this.actualState, stato_ingresso_for, md, trigger, action, "E", graphicAct);
					}
					else if(s.equals("") == false){
						
						//creiamo l'azione
						String action = "";
						String graphicAct = "";
						action += s + ";\n";
						graphicAct += currentStatement.updaters().get(i) + ";\n";
						action += createE();
						graphicAct += "postEvent(E);";
						
						//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
						String trigger = createTriggerE();
						
						this.contSimpleStates++;
						int targetState = this.contSimpleStates; //prossimo stato
							
						//creiamo lo stato semplice target
						createIntermediateSimpleState(targetState, md);
						
						//creiamo la transizione normale
						createNormalTransition(this.actualState, targetState, md, trigger, action, "E", graphicAct);
						
						//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
						this.actualState = targetState;
					}
				}
			}
		}

		//creiamo la transizione:
		//stato_ingresso_for ---- E[!condizione_for]/postMessage(E) ---> stato_fin
		
		//creiamo il trigger
		String trigger3 = "";
		trigger3 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getType() == DSCStarEvent.E)){\n";
		trigger3 += "if (!(" + translateVars(currentStatement.getExpression(), md) + ")){\n"; //traduciamo la condizione del FOR
		trigger3 += "return true;\n";
		trigger3 += "}\n";
		trigger3 += "}\n";
		trigger3 += "return false;\n";
		String graphicTrigger3 = "E[!(" + currentStatement.getExpression() + ")]";

		//creiamo l'azione
		String action3 = createE();
		
		//creiamo lo stato semplice "stato_fin"
		this.contSimpleStates++;
		int stato_fin = this.contSimpleStates;
		createIntermediateSimpleState(stato_fin, md);
		
		//creiamo la transizione da "stato_ingresso_for" a "stato_fin"
		createNormalTransition(stato_ingresso_for, stato_fin, md, trigger3, action3, graphicTrigger3, "postEvent(E);");
		
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = stato_fin;
	}
	
	/** This method translates a method invocation.
	 * @param currentStatement the current statement.
	 * @param currentMethod the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateMethodInvocation(ASTNode currentStatement, MethodDeclaration currentMethod, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce l'invocazione di un metodo
		
		//ricaviamo le informazioni necessarie sull'invocazione del metodo
		MethodInvocation methodInvocation = null;
		if(currentStatement instanceof VariableDeclarationStatement){
			VariableDeclarationFragment v = (VariableDeclarationFragment)(((VariableDeclarationStatement) currentStatement).fragments().get(0));
			methodInvocation = (MethodInvocation) v.getInitializer();
		}
		else if(currentStatement instanceof ExpressionStatement){
			Expression ex = ((ExpressionStatement) currentStatement).getExpression();
			if(ex instanceof MethodInvocation){
				methodInvocation = (MethodInvocation) ex;
			}
			else if(ex instanceof Assignment){
				Expression rightSide = ((Assignment) ex).getRightHandSide();
				methodInvocation = (MethodInvocation) rightSide;
			}
		}
		String nomeMetodoChiamato = methodInvocation.getName().toString();
		MethodDeclaration calledMethod = Util.getMethodDeclaration(this.unit, nomeMetodoChiamato);
		List parametriPassati = methodInvocation.arguments();
		String calledMethodNameUpperCase = calledMethod.getName().toString().toUpperCase();

		//creiamo l'azione della prima transizione normale
		String action = "";
		String graphicAction = "";
		for(int i=0; i < normalStatementsList.size(); i++){
			String s = translateVars(normalStatementsList.get(i), currentMethod);
			if(s.equals("") == false){
				action += Util.appendNewLine(s);
				graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
			}
		}

		//inviamo l'evento di "Call" (il metodo da chiamare è quello
		//ricavato tramite l'oggetto "MethodInvocation")
		action += "DSCStarEvent call = new DSCStarEvent(DSCStarEvent.CALL, " + nomeMetodoChiamato + ");\n";
		action += "postEvent(call);\n";
		graphicAction += "postEvent(Call" + calledMethodNameUpperCase + ");\n";

		//inviamo l'evento di "CPE"
		if(calledMethod.parameters().size() > 0){
			//se il metodo da chiamare ha dei parametri,
			//l'evento "CPE" contiene questi parametri
			action += "CPE_" + calledMethodNameUpperCase + " cpe = new CPE_" + calledMethodNameUpperCase + "();\n";
			graphicAction += "postEvent(CPE_" + calledMethodNameUpperCase + "{";
			
			//scorriamo i parametri del metodo da chiamare ed assegniamo i valori
			//alle corrispondenti variabili dell'evento "CPE"
			for(int j=0; j < calledMethod.parameters().size(); j++){
				if(calledMethod.parameters().get(j) instanceof SingleVariableDeclaration){
					SingleVariableDeclaration par = (SingleVariableDeclaration) calledMethod.parameters().get(j);
					action += "cpe." + par.getName().toString() + " = " + translateVars((ASTNode) parametriPassati.get(j), currentMethod) + ";\n";
					graphicAction += parametriPassati.get(j) + ", ";
				}
			}
			
			if(graphicAction.endsWith(", ")){
				graphicAction = graphicAction.substring(0, graphicAction.length() - 2);
			}
			graphicAction += "});";
			action += "postEvent(cpe);\n";
		}
		else{
			//se il metodo da chiamare non ha nemmeno un parametro,
			//l'evento "CPE" non contiene nessun parametro
			action += "DSCStarEvent cpe = new DSCStarEvent(DSCStarEvent.CPE, " + nomeMetodoChiamato + ");\n";
			action += "postEvent(cpe);\n";
			graphicAction += "postEvent(CPE_" + calledMethodNameUpperCase + ");";
		}

		//creiamo il primo stato semplice e la transizione che va in questo stato

		//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
		String trigger = createTriggerE();

		this.contSimpleStates++;
		int targetState = this.contSimpleStates; //prossimo stato
		
		//creiamo lo stato semplice target
		createIntermediateSimpleState(targetState, currentMethod);
			
		//creiamo la transizione normale
		createNormalTransition(this.actualState, targetState, currentMethod, trigger, action, "E", graphicAction);
			
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = targetState;

		//creiamo il secondo stato semplice e la transizione che va in questo stato

		//creiamo il trigger: controlla l'evento RPE
		String trigger2 = "";
		trigger2 += "if((msg != null) && (msg instanceof DSCStarEvent) && (((DSCStarEvent) msg).getName().equalsIgnoreCase(\"RPE_" + calledMethodNameUpperCase + "\"))){\n";
		trigger2 += "return true;\n";
		trigger2 += "}\n";
		trigger2 += "return false;\n";
		String graphicTrigger2 = "RPE_" + calledMethodNameUpperCase;
		
		//creiamo l'azione
		String action2 = "";
		String graphicAction2 = "";
		if( (currentStatement instanceof VariableDeclarationStatement) ||
				( (currentStatement instanceof ExpressionStatement) &&
				(((ExpressionStatement) currentStatement).getExpression() instanceof Assignment) ) ){
			//siamo nel caso in cui il risultato del metodo chiamato deve essere trasferito
			//ad una variabile del metodo chiamante o della root (ad esempio: int ris = metodoChiamato();)
			
			ASTNode variabileRitorno = null; //è la variabile che riceve il risultato del metodo chiamato (ad esempio, nell'istruzione "int ris = metodoChiamato();" è la variabile "ris")
			if(currentStatement instanceof VariableDeclarationStatement){
				VariableDeclarationFragment v = (VariableDeclarationFragment)(((VariableDeclarationStatement) currentStatement).fragments().get(0));
				variabileRitorno = v.getName();
			}
			else{
				Expression ex = ((ExpressionStatement) currentStatement).getExpression();
				variabileRitorno = ((Assignment) ex).getLeftHandSide();
			}

			action2 += "RPE_" + calledMethodNameUpperCase + " rpe = (RPE_" + calledMethodNameUpperCase + ") msg;\n";
			action2 += translateVars(variabileRitorno, currentMethod) + " = rpe.result;\n"; //la variabile dell'evento che contiene il valore da ritornare si chiama "result"
			graphicAction2 += variabileRitorno + " = extract result\n";
			graphicAction2 += "contained in RPE_" + calledMethodNameUpperCase + ";\n";
		}
		action2 += createE();
		graphicAction2 += "postEvent(E);";

		this.contSimpleStates++;
		int targetState2 = this.contSimpleStates; //prossimo stato
		
		//creiamo lo stato semplice target
		createIntermediateSimpleState(targetState2, currentMethod);
			
		//creiamo la transizione normale
		createNormalTransition(this.actualState, targetState2, currentMethod, trigger2, action2, graphicTrigger2, graphicAction2);
			
		//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
		this.actualState = targetState2;

		//creiamo la transizione "*"
		createStarTransition(currentMethod, calledMethod);
	}

	/** This method translates a RETURN statement.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 * @param normalStatementsList list of actual "normal" statements to handle.
	 */
	private void translateReturnStatement(ReturnStatement currentStatement, MethodDeclaration md, LinkedList<ASTNode> normalStatementsList){
		//metodo che traduce un RETURN

		//creiamo l'azione
		String action = "";
		String graphicAction = "";
		for(int i=0; i < normalStatementsList.size(); i++){
			String s = translateVars(normalStatementsList.get(i), md);
			if(s.equals("") == false){
				action += Util.appendNewLine(s);
				graphicAction += Util.appendNewLine(normalStatementsList.get(i).toString());
			}
		}
		
		//inviamo l'evento di "ReturnFrom" (il metodo da cui bisogna ritornare
		//è quello che si sta traducendo, cioè "md")
		action += "DSCStarEvent returnFrom = new DSCStarEvent(DSCStarEvent.RETURN_FROM, " + md.getName().toString() + ");\n";
		action += "postEvent(returnFrom);\n";
		graphicAction += "postEvent(ReturnFrom" + md.getName().toString().toUpperCase() + ");\n";
		
		if((md.getReturnType2().toString()).equalsIgnoreCase("void")){
			//se il metodo ha un ritorno di tipo "void", l'evento RPE non contiene nessun parametro
			action += "DSCStarEvent rpe = new DSCStarEvent(DSCStarEvent.RPE, " + md.getName().toString() + ");\n";
			action += "postEvent(rpe);\n";
			graphicAction += "postEvent(RPE_" + md.getName().toString().toUpperCase() + ");";
		}
		else{
			//se il metodo ha un ritorno diverso da "void", l'evento RPE contiene qualche parametro
			String methodNameUpperCase = md.getName().toString().toUpperCase();
			action += "RPE_" + methodNameUpperCase + " rpe = new RPE_" + methodNameUpperCase + "();\n";
			action += "rpe.result = " + translateVars(currentStatement.getExpression(), md) + ";\n"; //la variabile dell'evento che contiene il valore da ritornare si chiama "result"
			action += "postEvent(rpe);\n";
			graphicAction += "postEvent(RPE_" + md.getName().toString().toUpperCase() + "{" + currentStatement.getExpression() + "});";
		}
			
		//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
		String trigger = createTriggerE();

		this.contSimpleStates++;
		int targetState = this.contSimpleStates; //prossimo stato

		//creiamo lo stato semplice target
		if( (md.getName().toString()).equalsIgnoreCase("main") ){
			//se siamo nel metodo "main", creiamo un sotto-stato finale:
			//così, se il "main" è stato invocato da qualche altro
			//metodo, gli eventi "ReturnFrom" e "RPE" creati prima
			//vengono utilizzati per tornare al metodo chiamante e
			//riprendere l'esecuzione; altrimenti, se il "main" non
			//è stato invocato da nessuno, gli eventi "ReturnFrom"
			//e "RPE" vengono scartati (perchè non ci sono delle transizioni
			//che partono dal sotto-stato finale e tramite l'evento "RPE"
			//o "ReturnFrom" permettono di spostarsi) e l'esecuzione termina 
			createFinalSimpleState(targetState, md);
		}
		else{
			//altrimenti, creiamo un sotto-stato intermedio
			createIntermediateSimpleState(targetState, md);
		}
		
		//creiamo la transizione normale
		createNormalTransition(this.actualState, targetState, md, trigger, action, "E", graphicAction);
			
		//aggiorniamo lo stato attuale
		this.actualState = targetState;

		//NOTA: successivamente, a partire dallo stato semplice attuale
		//(cioè, quello che viene raggiunto dalla transizione che genera
		//l'evento di "ReturnFrom") non viene creata nessuna transizione,
		//perchè non devono esistere delle transizioni che partono da questo
		//stato e permettono di spostarsi in un altro stato
	}
	
	/** This method translates a "normal" statement only for FULL translation.
	 * @param currentStatement the current statement.
	 * @param md the method under translation.
	 */
	private void translateNormalStatementForFullTranslation(ASTNode currentStatement, MethodDeclaration md){
		//metodo che traduce uno statement "normale" in caso di traduzione FULL

		String s = translateVars(currentStatement, md);
		if(s.equals("") == false){
			//se l'istruzione attuale non è vuota, procediamo
				
			//creiamo l'azione
			String action = "";
			String graphicAction = "";
			action += Util.appendNewLine(s);
			graphicAction += Util.appendNewLine(currentStatement.toString());
			action += createE();
			graphicAction += "postEvent(E);";

			//creiamo il trigger: controlla solo che l'evento sia di tipo "E"
			String trigger = createTriggerE();
				
			this.contSimpleStates++;
			int targetState = this.contSimpleStates; //prossimo stato
			
			//creiamo lo stato semplice target
			createIntermediateSimpleState(targetState, md);
				
			//creiamo la transizione normale
			createNormalTransition(this.actualState, targetState, md, trigger, action, "E", graphicAction);
				
			//aggiorniamo lo stato attuale, da cui verrà ripresa la traduzione
			this.actualState = targetState;
		}
	}

}
