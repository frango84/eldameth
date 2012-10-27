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
import java.util.Vector;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import triggerModel.Trigger;
import dscDiagramModel.myDataTypes.TypeVariable;
import eldaEditor.codegen.elda.CodeGenerator;

/**
 * Genera il codice relativo agli eventi
 * 
 * @author G. Fortino, F. Rango
 */
public class EventsClassesCodeGeneratorForJADE {

	private String eventsPackageName;

	private IFolder folder;

	private IProject project;

	private CodeGeneratorUtilForJADE util;

	/**
	 * Lista di eventi usati per triggerare le transizioni
	 */
	private List<String> triggeringEventsList;

	public EventsClassesCodeGeneratorForJADE(String eventsPackageName,
			IFolder folder, CodeGeneratorUtilForJADE util) {
		this.folder = folder;
		this.project = folder.getProject();
		this.eventsPackageName = eventsPackageName;
		this.util = util;
		this.triggeringEventsList = this.util.getUsedEventIDList();
	}

	/**
	 * il metodo che inizia la generazione del codice relativa agli eventi
	 */
	public void run() throws InvalidEventException {
		List<String> eventExtendingFrameworkList = new ArrayList<String>();
		List<String> eventToBeOrderedList = new ArrayList<String>();

		// per tutti gli eventi che triggerano le transizioni
		for (int i = 0; i < triggeringEventsList.size(); i++) {
			String eventTypeName = triggeringEventsList.get(i);

			//controlliamo se l'evento è valido, altrimenti lanciamo un errore
			if(validEvent(eventTypeName) == false){
				throw new InvalidEventException(eventTypeName);
			}

			//se l'evento non appartiene al framework di JADE
			if (!belongsToJADEFramework(eventTypeName)) {
				
				//recupero l'oggetto che modella il tipo dell'evento
				Trigger eventType = util.getEventType(eventTypeName);

				//recupero il nome del tipo esteso
				String extendedEventTypeName = eventType.getExtendedEvent();

				//controlliamo se l'evento esteso è valido, altrimenti lanciamo un errore
				if(validEvent(extendedEventTypeName) == false){
					throw new InvalidEventException(extendedEventTypeName);
				}
				
				// se il tipo esteso appartiene al framework di JADE
				if (belongsToJADEFramework(extendedEventTypeName)) {
					
					// li accodo allo stesso livello di priorità di generazione
					eventExtendingFrameworkList.add(eventTypeName);
				}
				else {
					// eventi che successivamente devo ordinare nel corretto
					// ordine di generazione,
					// cioè rispettando la gerarchia di estensione delle classi
					eventToBeOrderedList.add(eventTypeName);
					
					// potrebbe estendere un classe non usata dall'agente
					// che va aggiunta agli eventi usati
					if (!belongsTo(extendedEventTypeName, triggeringEventsList)){
						triggeringEventsList.add(extendedEventTypeName);
					}
				}
			}
		}
		
		// genero il codice delle classi che estendono gli eventi del framework
		// in maniera da avere le classi già a disposizione nelle successive generazioni
		if (eventExtendingFrameworkList.size() != 0){
			for (int i = 0; i < eventExtendingFrameworkList.size(); i++) {
				String eventTypeName = eventExtendingFrameworkList.get(i);
				Trigger eventType = util.getEventType(eventTypeName);
				
				//generiamo il codice per l'evento corrente
				String code = createEventClassCode(eventType, true);
				
				IJavaElement javaFolder = JavaCore.create(folder);
				util.generateFileWithThisCode(eventTypeName + ".java", code, javaFolder);
			}
		}

		// genero il codice delle classi che estendono eventi definiti dall'utente
		if (eventToBeOrderedList.size() != 0) {
			List orderedEvent = orderEvent(eventToBeOrderedList, eventExtendingFrameworkList, 0);
			for (int i = 0; i < orderedEvent.size(); i++) {
				String eventTypeName = (String) orderedEvent.get(i);
				Trigger eventType = util.getEventType(eventTypeName);
				
				//generiamo il codice per l'evento corrente
				String code = createEventClassCode(eventType, false);
				
				IJavaElement javaFolder = JavaCore.create(folder);
				util.generateFileWithThisCode(eventTypeName + ".java", code, javaFolder);
			}
		}
	}

	/**
	 * Verifica se la classe passata come parametro è presente nel file JAR di JADE
	 * 
	 * @param nameClass
	 * @return
	 */
	private boolean belongsToJADEFramework(String nameClass) {
		IJavaProject jProject = JavaCore.create(project);
		try {
			boolean trovato = false;
			
			//recupero il file JAR di JADE
			IPackageFragmentRoot jar = jProject
					.findPackageFragmentRoot(JavaCore
							.getResolvedVariablePath(JavaCore.newVariableEntry(
									CodeGeneratorForJADE.jadePath, null, null).getPath()));
			
			for (int i = 1; i < jar.getChildren().length & !trovato; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
						.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith("elda_multicoordination.eldaevent")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length & !trovato; j++)
						if (classArray[j].getElementName().equals(nameClass + ".class"))
							trovato = true;
				}
			}
			return trovato;
		}
		catch (JavaModelException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Verifica se la classe passata come parametro è presente in ELDAFramework
	 * 
	 * @param nameClass
	 * @return
	 */
	private boolean belongsToELDAFramework(String nameClass) {
		IJavaProject jProject = JavaCore.create(project);
		try {
			boolean trovato = false;
			
			//recupero il jar di ELDAFramework
			IPackageFragmentRoot jar = jProject.findPackageFragmentRoot(
					JavaCore.getResolvedVariablePath(JavaCore.newVariableEntry(
							CodeGenerator.frameworkPath, null, null).getPath()));

			for (int i = 1; i < jar.getChildren().length & !trovato; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
				.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith("eldaframework.eldaevent")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length & !trovato; j++)
						if (classArray[j].getElementName().equals(
								nameClass + ".class"))
							trovato = true;
				}
			}
			return trovato;
		}
		catch (JavaModelException e) {
			e.printStackTrace();
		}
		return false;
	}

	//gli eventi scelti per essere tradotti in codice JADE possono essere
	//soltanto di alcuni tipi, quindi questo metodo permette di effettuare questa verifica
	private boolean validEvent(String eventTypeName){
		if(belongsToELDAFramework(eventTypeName) && !belongsToJADEFramework(eventTypeName)){
			return false; //se il tipo di evento appartiene all'ELDAFramework
						//ma non al file JAR di JADE, l'evento NON è valido
						//(da notare che se il tipo di evento è una classe definita
						//dall'utente, l'evento è considerato valido)
		}
		return true;
	}

	/**
	 * Questo metodo ordina gli eventi in ordine di generazione
	 * 
	 * @param eventToBeOrderedList
	 * @param referringList
	 * @return la lista ordinata
	 */
	private List orderEvent(List eventToBeOrderedList, List referringList, int level) {
		
		//creo una lista di riferimento vuota
		List newReferringList = new ArrayList();
		
		//per tutti gli eventi da ordinare
		int size = eventToBeOrderedList.size();
		List copyList = new ArrayList();
		copyList.addAll(eventToBeOrderedList);
		for (int i = 0; i < size; i++) {
			String eventTypeName = (String) eventToBeOrderedList.get(i);
			Trigger eventType = util.getEventType(eventTypeName);
			
			//se il tipo estende un evento della lista di riferimento ricevuta come parametro
			if (belongsTo(eventType.getExtendedEvent(), referringList)) {
				
				//lo aggiungo nella nuova lista di riferimento
				//e lo tolgo da quelli da riordinare
				newReferringList.add(eventTypeName);
				copyList.remove(eventTypeName);
			}
		}
		
		// se la lista degli eventi da riordinare non è vuota
		// richiamo il metodo ricorsivamente aggiungendo il risultato
		// alla nuova lista di riferimento creata localmente
		if (copyList.size() != 0)
			newReferringList.addAll(orderEvent(copyList, newReferringList, ++level));
		return newReferringList;
	}

	/**
	 * Verifica che l'evento sia presente nella lista
	 * 
	 * @param extendedType
	 * @param referringList
	 * @return true in caso positivo, false altrimenti
	 */
	private boolean belongsTo(String extendedType, List referringList) {
		boolean trovato = false;
		for (int i = 0; i < referringList.size() & !trovato; i++)
			if (extendedType.equals(referringList.get(i)))
				trovato = true;
		return trovato;
	}

	/**
	 * Crea la stringa contenente il codice dell'evento
	 * 
	 * @param eventType
	 * @param extendsFramework
	 * @return
	 */
	private String createEventClassCode(Trigger eventType, boolean extendsFramework) {
		StringBuffer result = new StringBuffer();

		result.append("package " + eventsPackageName + ";\n");
		
		//creiamo gli imports
		util.generateJADEImports(result);
		util.generateImportsFromClasspath(result);
		util.generateImportsFromVariables(result);

		result.append("public class " + eventType.getTriggerName());
		result.append(" extends " + eventType.getExtendedEvent() + " {\n");

		StringBuffer additionalParameters = new StringBuffer();
		StringBuffer assignementForAdditionalParameters = new StringBuffer();
		StringBuffer localVariableForAdditionalParameters = new StringBuffer();
		StringBuffer setterGetterBuffer = new StringBuffer();

		if (eventType.getParameters().size() == 0)
			additionalParameters.append("");
		else {
			for(int i = 0; i < eventType.getParameters().size(); i++) {
				TypeVariable parameter = (TypeVariable) eventType.getParameters().get(i);
				
				additionalParameters.append(parameter.getType() + " " + parameter.getName());

				localVariableForAdditionalParameters.append("protected "
						+ parameter.getType() + " " + parameter.getName() + ";\n");
				
				assignementForAdditionalParameters.append("this."
						+ parameter.getName() + " = " + parameter.getName()+ ";\n");
				
				setterGetterBuffer.append("public void set"
						+ translateFirstToUpper(parameter.getName()) + "("
						+ parameter.getType() + " " + parameter.getName()
						+ "){\n" + "this." + parameter.getName() + " = "
						+ parameter.getName() + ";\n}\n");
				
				setterGetterBuffer.append("public " + parameter.getType()
						+ " get" + translateFirstToUpper(parameter.getName())
						+ "(){\n" + "return " + parameter.getName()
						+ ";\n}\n");
				
				if (i < eventType.getParameters().size() - 1) {
					additionalParameters.append(", ");
				}
			}
		}
		result.append(localVariableForAdditionalParameters);

		Vector<IMethod> constructors = null;
		if (extendsFramework){
			//se l'evento estende una classe di evento appartenente al framework,
			//preleviamo i costruttori di questa classe
			constructors = getConstructors4EventExtendingFramework(eventType);
		}
		else{
			//altrimenti, preleviamo i costruttori della classe estesa definita dall'utente
			constructors = getConstructors4EventExtendingUD(eventType);
		}	

		for (int j = 0; j < constructors.size(); j++){
			
			//preleviamo i parametri del costruttore corrente della classe estesa
			List superParameters = getSuperConstructorParameters(constructors.get(j));
			
			StringBuffer constructorString = new StringBuffer();
			constructorString.append("public " + eventType.getTriggerName()+ "(");

			//aggiungiamo le dichiarazioni dei parametri della classe estesa
			StringBuffer superDeclarationParameters = createDeclarationParameters(superParameters);
			constructorString.append(superDeclarationParameters);

			if(superDeclarationParameters.toString().equals("") == false &&
					additionalParameters.toString().equals("") == false){
				constructorString.append(", ");
			}

			//aggiungiamo le dichiarazioni dei parametri dell'evento corrente
			constructorString.append(additionalParameters);

			constructorString.append("){\n");

			//chiamiamo il costruttore della classe estesa
			constructorString.append("super(" + createCallParameters(superParameters) + ");\n");
			
			//aggiungiamo gli assegnamenti dei parametri dell'evento corrente
			constructorString.append(assignementForAdditionalParameters);
			
			//chiudiamo il costruttore corrente
			constructorString.append("}\n");

			result.append(constructorString);
		}
		
		//aggiungiamo i metodi "get" e "set" per i parametri dell'evento corrente
		result.append(setterGetterBuffer);
		
		//chiudiamo la classe dell'evento corrente
		result.append("}");
		
		return result.toString();
	}
	
	/**
	 * Ritorna i costruttori della classe estesa appartenente al framework
	 * 
	 * @param eventType
	 */
	private Vector<IMethod> getConstructors4EventExtendingFramework(Trigger eventType){
		IJavaProject jProject = JavaCore.create(project);
		IClassFile classeEstesa = null;
		try {
			//recupero il file JAR di JADE
			IPackageFragmentRoot jar = jProject
					.findPackageFragmentRoot(JavaCore
							.getResolvedVariablePath(JavaCore.newVariableEntry(
									CodeGeneratorForJADE.jadePath, null, null).getPath()));
			
			//recupero l'evento
			for (int i = 1; i < jar.getChildren().length & classeEstesa == null; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
						.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith("elda_multicoordination.eldaevent")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length & classeEstesa == null; j++)
						if (classArray[j].getElementName().equals(
								eventType.getExtendedEvent() + ".class"))
							classeEstesa = classArray[j];
				}
			}
		}
		catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//recupero i costruttori della classe estesa
		Vector<IMethod> constructor = getConstructors(classeEstesa.getType());
		return constructor;
	}

	/**
	 * Ritorna i costruttori della classe estesa definita dall'utente
	 * 
	 * @param eventType
	 */
	private Vector<IMethod> getConstructors4EventExtendingUD(Trigger eventType) {
		
		// recupero il progetto
		IProject project = folder.getProject();
		
		// creo il javaProject
		IJavaProject jProject = JavaCore.create(project);
		
		// trovo il package del progetto dove sono state generate le classi
		// degli eventi precedenti
		String packageString = folder.getProjectRelativePath()
			.removeFirstSegments(1).toString().replace('/', '.');
		IType classType = null;
		try {
			// recupero la classe dell'evento esteso
			classType = jProject.findType(packageString + "." + eventType.getExtendedEvent());
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		//recuperiamo i costruttori della classe estesa
		Vector<IMethod> constructors = getConstructors(classType);
		return constructors;
	}

	/**
	 * Ritorna i costruttori della classe passata come paramentro
	 * 
	 * @param classType
	 */
	private Vector<IMethod> getConstructors(IType classType) {
		IMethod[] methods = null;
		try {
			methods = classType.getMethods();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<IMethod> constructor = new Vector<IMethod>();

		for (int i = 0; i < methods.length; i++)
			try {
				if (methods[i].isConstructor())
					constructor.add(methods[i]);
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return constructor;
	}

	/**
	 * Metodo che dato un costruttore ritorna una lista di parametri (tipo e nome)
	 * 
	 * @param constructor
	 * @return
	 */
	private List getSuperConstructorParameters(IMethod constructor) {
		int num = constructor.getNumberOfParameters();
		List result = new ArrayList(num);
		for (int i = 0; i < num; i++) {
			ArrayList object = new ArrayList(2);
			object.add(0, Signature.getSignatureSimpleName(constructor.getParameterTypes()[i]));
			try {
				object.add(1, constructor.getParameterNames()[i]);
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(object);
		}
		return result;
	}

	private String translateFirstToUpper(String arg) {
		String result = new String();
		CharSequence start = arg.subSequence(0, 1);
		CharSequence end = arg.subSequence(1, arg.length());
		result = result.concat(start.toString().toUpperCase());
		result = result.concat(end.toString());
		return result;
	}

	private StringBuffer createCallParameters(List superParameters) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < superParameters.size(); i++) {
			result.append(((ArrayList) superParameters.get(i)).get(1));
			if (i < superParameters.size() - 1)
				result.append(", ");
		}
		return result;
	}

	//metodo che permette di creare le dichiarazioni dei parametri
	private StringBuffer createDeclarationParameters(List superParameters) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < superParameters.size(); i++){
			String tipo = (String)((ArrayList) superParameters.get(i)).get(0);
			String nome = (String)((ArrayList) superParameters.get(i)).get(1);
			result.append(tipo);
			result.append(" ");
			result.append(nome);
			if (i < superParameters.size() - 1) {
				result.append(", ");
			}
		}
		return result;
	}
	
}
