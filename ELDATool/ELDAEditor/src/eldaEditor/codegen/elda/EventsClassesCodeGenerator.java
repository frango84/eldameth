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

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
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
import eldaEditor.wizards.DSCDiagramCreationPage_ChooseName;

/**
 * @author S. Mascillaro, F. Rango
 */
public class EventsClassesCodeGenerator {

	private String eventsPackageName;

	private IFolder folder;

	private IProject project;

	private CodeGeneratorUtil util;

	/**
	 * Lista di eventi usati per triggerare le transizioni
	 */
	private List<String> triggeringEventsList;

	public EventsClassesCodeGenerator(String eventsPackageName, IFolder folder,
			CodeGeneratorUtil util) {
		this.folder = folder;
		this.project = folder.getProject();
		this.util = util;
		this.eventsPackageName = eventsPackageName;
		this.triggeringEventsList = util.getUsedEventIDList();
	}

	/**
	 * Verifica se la classe passata come parametro è presente tra quelle
	 * presenti nel package ELDAEvent Con la possibilità di scelta delle
	 * eccezioni dovrebbe verificare anche quello
	 * 
	 * @param nameClass
	 * @return
	 */
	private boolean belongsToFramework(String nameClass) {

		// creo il javaProject
		IJavaProject jProject = JavaCore.create(project);
		try {
			boolean trovato = false;
			// recupero il jar di actiware
			IPackageFragmentRoot jar = jProject
					.findPackageFragmentRoot(JavaCore
							.getResolvedVariablePath(JavaCore.newVariableEntry(
									CodeGenerator.frameworkPath, null, null)
									.getPath()));

			for (int i = 1; i < jar.getChildren().length & !trovato; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
						.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith(
						"eldaframework.eldaevent")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length & !trovato; j++)
						if (classArray[j].getElementName().equals(
								nameClass + ".class"))
							trovato = true;
				}
			}
			return trovato;
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * il metodo che inizia la generazione del codice relativa agli eventi
	 */
	public void run() {
		List<String> eventExtendingFrameworkList = new ArrayList<String>();
		List<String> eventToBeOrderedList = new ArrayList<String>();

		// per tutti gli eventi che triggerano le transizioni
		for (int i = 0; i < triggeringEventsList.size(); i++) {

			String eventTypeName = triggeringEventsList.get(i);
			// Se l'evento non appartiene al framework
			if (!belongsToFramework(eventTypeName)) {
				// Recupero l'oggetto che modella il tipo dell'evento
				Trigger eventType = util.getEventType(eventTypeName);
				// Recupero il nome del tipo esteso
				String extendedEventTypeName = eventType.getExtendedEvent();
				// se il tipo esteso appartiene al framework
				if (belongsToFramework(extendedEventTypeName)) {
					// li accodo allo stesso livello di priorità di generazione
					eventExtendingFrameworkList.add(eventTypeName);
				}
				// se il tipo esteso è esso stesso UD
				else {
					// eventi che successivamente devo ordinare nel corretto
					// ordine di generazione,
					// cioè rispettando la gerarchia di estensione elle classi
					eventToBeOrderedList.add(eventTypeName);
					// potrebbe estendere un classe non usata dall'agente
					// che va aggiunta agli eventi usati
					if (!belongsTo(extendedEventTypeName, triggeringEventsList))
						triggeringEventsList.add(extendedEventTypeName);
				}
			}

		}
		// genero il codice delle classi che estendono gli eventi del framework
		// in maniera da avere gia le classi a disposizione nelle successive
		// generazioni
		if (eventExtendingFrameworkList.size() != 0)
			for (int i = 0; i < eventExtendingFrameworkList.size(); i++) {
				String eventTypeName = eventExtendingFrameworkList.get(i);
				Trigger eventType = util.getEventType(eventTypeName);
				String code = createEventClassCode(eventType, true);
				IJavaElement javaFolder = JavaCore.create(folder);
				util.generateFileWithThisCode(eventTypeName + ".java", code,
						javaFolder);
			}

		// genero il codice delle classi che estendono eventi UD
		if (eventToBeOrderedList.size() != 0) {
			List orderedEvent = orderEvent(eventToBeOrderedList,
					eventExtendingFrameworkList, 0);
			for (int i = 0; i < orderedEvent.size(); i++) {
				String eventTypeName = (String) orderedEvent.get(i);
				Trigger eventType = util.getEventType(eventTypeName);
				String code = createEventClassCode(eventType, false);
				IJavaElement javaFolder = JavaCore.create(folder);
				util.generateFileWithThisCode(eventTypeName + ".java", code,
						javaFolder);
			}
		}
	}

	/**
	 * Questo metodo ordina gli eventi in ordine di generazione
	 * 
	 * @param eventToBeOrderedList
	 * @param referringList
	 * @return la lista ordinata
	 */
	private List orderEvent(List eventToBeOrderedList, List referringList,
			int level) {
		// creo una lista di riferimento vuota
		List newReferringList = new ArrayList();
		// System.out.println("level: "+ level + "	eventToBeOrderedList:	"+
		// eventToBeOrderedList.toString() + "	referringList:"+
		// referringList.toString());
		// per tutti gli eventi da ordinare
		int size = eventToBeOrderedList.size();
		List copyList = new ArrayList();
		copyList.addAll(eventToBeOrderedList);
		for (int i = 0; i < size; i++) {
			String eventTypeName = (String) eventToBeOrderedList.get(i);
			Trigger eventType = util.getEventType(eventTypeName);
			// se il tipo estende un evento della lista di riferimento ricevuta
			// come parametro
			if (belongsTo(eventType.getExtendedEvent(), referringList)) {
				// lo aggiungo nella nuova lista di riferimento
				// e lo tolgo da quelli da riordinare
				newReferringList.add(eventTypeName);
				copyList.remove(eventTypeName);
			}
		}
		// se la lista degli eventi da riordinare non è vuota
		// richiamo il metodo ricorsivamente aggiungendo il risultato
		// alla nuova lista di riferimento creata localmente

		if (copyList.size() != 0)
			newReferringList.addAll(orderEvent(copyList, newReferringList,
					++level));
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
	 * Crea la stringa che contiene il codice java dell'evento
	 * 
	 * @param eventType
	 * @param extendsFramework
	 * @return
	 */
	private String createEventClassCode(Trigger eventType,
			boolean extendsFramework) {

		StringBuffer result = new StringBuffer();

		result.append("package " + eventsPackageName + ";\n\n");
		result.append("import eldaframework.agent.ELDAId; \n");
		result.append("import eldaframework.eldaevent.*; \n");
		result.append("import eldaframework.eldaevent.coordination.*; \n");
		result
				.append("import eldaframework.eldaevent.coordination.direct.*; \n");
		result.append("import eldaframework.eldaevent.coordination.p_s.*; \n");
		result
				.append("import eldaframework.eldaevent.coordination.tuples.*; \n");
		result
				.append("import eldaframework.eldaevent.coordination.services.*; \n");
		result.append("import eldaframework.eldaevent.internal.*; \n");
		result.append("import eldaframework.eldaevent.management.*; \n");

		result
				.append("import eldaframework.eldaevent.management.lifecycle.*; \n");
		result
				.append("import eldaframework.eldaevent.management.resource.*; \n");
		result.append("import eldaframework.eldaevent.management.timer.*; \n");
		result.append("import java.util.Vector; \n\n");

		util.generateImportsFromClasspath(result);
		util.generateImportsFromVariables(result);

		result.append("public class " + eventType.getTriggerName());
		result.append(" extends " + eventType.getExtendedEvent() + " {");

		StringBuffer additionalParameters = new StringBuffer();
		StringBuffer assignementForAdditionalParameters = new StringBuffer();
		StringBuffer localVariableForAdditionalParameters = new StringBuffer();
		StringBuffer setterGetterBuffer = new StringBuffer();

		if (eventType.getParameters().size() == 0)
			additionalParameters.append("");
		else {
			for (int i = 0; i < eventType.getParameters().size(); i++) {

				TypeVariable parameter = (TypeVariable) eventType
						.getParameters().get(i);
				additionalParameters.append(parameter.getType() + " "
						+ parameter.getName());

				localVariableForAdditionalParameters.append("\n protected "
						+ parameter.getType() + " " + parameter.getName()
						+ ";\n");
				assignementForAdditionalParameters.append("\n this."
						+ parameter.getName() + "=" + parameter.getName()
						+ ";\n");
				setterGetterBuffer.append("public void set"
						+ translateFirstToUpper(parameter.getName()) + "("
						+ parameter.getType() + " " + parameter.getName()
						+ ") \n { \n" + " this." + parameter.getName() + "="
						+ parameter.getName() + ";\n } \n");
				setterGetterBuffer.append("public " + parameter.getType()
						+ " get" + translateFirstToUpper(parameter.getName())
						+ "() \n { \n" + " return " + parameter.getName()
						+ ";\n } \n");
				if (i < eventType.getParameters().size() - 1) {
					additionalParameters.append(", ");
				}
			}
		}
		result.append(localVariableForAdditionalParameters);

		// se l'evento estende uno appartenente al framework
		Vector<IMethod> constructors = null;
		if (extendsFramework)
			constructors = getConstructor4EventExtendingFramework(eventType);
		// altrimenti
		else
			constructors = getConstructor4EventExtendingUD(eventType);

		for (int j = 0; j < constructors.size(); j++) {
			List superParameters = getSuperConstructorParameters(constructors
					.get(j));
			StringBuffer constructorString = new StringBuffer();
			constructorString.append("\n\npublic " + eventType.getTriggerName()
					+ "(");

			StringBuffer superDeclarationParameters = createDeclarationParameters(superParameters);
			constructorString.append(superDeclarationParameters);

			if (superDeclarationParameters.toString().equals("") == false
					&& additionalParameters.toString().equals("") == false) {
				constructorString.append(", ");
			}

			constructorString.append(additionalParameters);

			constructorString.append(")\n {\n super ("
					+ createCallParameters(superParameters) + "); \n"
					+ assignementForAdditionalParameters + " }\n");
			result.append(constructorString);
		}
		result.append(setterGetterBuffer);
		result.append("}");
		return result.toString();
	}

	private Vector<IMethod> getConstructor4EventExtendingUD(Trigger eventType) {

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
			// recupero la classe dell'evento esteso classe dell'evento esteso
			classType = jProject.findType(packageString + "."
					+ eventType.getExtendedEvent());
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<IMethod> constructor = getConstructor(classType);
		return constructor;
	}

	/**
	 * Ritorna il costruttore della classe passata come paramentro
	 * 
	 * @param classType
	 */
	private Vector<IMethod> getConstructor(IType classType) {
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
	 * Metodo che dato un costruttore ritorna una lista di parametri (Tipo &
	 * Nome)
	 * 
	 * @param constructor
	 * @return
	 */
	private List getSuperConstructorParameters(IMethod constructor) {
		int num = constructor.getNumberOfParameters();
		List result = new ArrayList(num);
		for (int i = 0; i < num; i++) {
			ArrayList object = new ArrayList(2);
			object.add(0, Signature.getSignatureSimpleName(constructor
					.getParameterTypes()[i]));
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

	/**
	 * @param eventType
	 * @return
	 */
	private Vector<IMethod> getConstructor4EventExtendingFramework(
			Trigger eventType) {

		// creo il javaProject
		IJavaProject jProject = JavaCore.create(project);
		IClassFile classeEstesa = null;
		try {
			// recupero il jar di actiware
			IPackageFragmentRoot jar = jProject
					.findPackageFragmentRoot(JavaCore
							.getResolvedVariablePath(JavaCore.newVariableEntry(
									CodeGenerator.frameworkPath, null, null)
									.getPath()));
			// recupero ELDAEvent
			for (int i = 1; i < jar.getChildren().length & classeEstesa == null; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
						.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith(
						"eldaframework.eldaevent")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length
							& classeEstesa == null; j++)
						if (classArray[j].getElementName().equals(
								eventType.getExtendedEvent() + ".class"))
							classeEstesa = classArray[j];
				}
			}
			// IPackageFragment
			// jarFragment=(IPackageFragment)JavaCore.create(jar.getChildren()[1].getHandleIdentifier());
			// IClassFile[] classArray =jarFragment.getClassFiles();

			// for (int i=0; i<classArray.length & classeEstesa==null; i++)
			// if
			// (classArray[i].getElementName().equals(eventType.getExtendedType()+".class"))
			// classeEstesa=classArray[i];
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Recupero il costruttore
		Vector<IMethod> constructor = null;
		constructor = getConstructor(classeEstesa.getType());
		return constructor;
	}

	// StringBuffer result=new StringBuffer();

	// List superParameters=getSuperConstructorParameters(constructor);

	// StringBuffer additionalParameters=new StringBuffer();
	// StringBuffer localVariableForAdditionalParameters= new StringBuffer();
	// StringBuffer assignementForAdditionalParameters= new StringBuffer();
	// StringBuffer setterGetterBuffer= new StringBuffer();

	// if(eventType.getParameters().size()==0)
	// additionalParameters.append("");
	// else
	// additionalParameters.append(",");

	// for (int i=0; i<eventType.getParameters().size(); i++)
	// {

	// TypeVariable parameter= (TypeVariable)eventType.getParameters().get(i);
	// additionalParameters.append(parameter.getType()+" "+
	// parameter.getName());
	// localVariableForAdditionalParameters.append("\n protected
	// "+parameter.getType()+" "+ parameter.getName()+";\n");
	// assignementForAdditionalParameters.append("\n
	// this."+parameter.getName()+"="+parameter.getName()+";\n" );
	// setterGetterBuffer.append("public void set" +
	// translateFirstToUpper(parameter.getName()) + "(" + parameter.getType()+ "
	// " + parameter.getName()+ ") \n { \n" + " this." + parameter.getName()+
	// "=" + parameter.getName()+";\n } \n" );
	// setterGetterBuffer.append("public "+ parameter.getType() + " get" +
	// translateFirstToUpper(parameter.getName()) + "() \n { \n" + " return " +
	// parameter.getName() + ";\n } \n" );
	// if(i<eventType.getParameters().size()-1)
	// {
	// additionalParameters.append(", ");
	// }
	// }
	// result.append(localVariableForAdditionalParameters);
	// result.append("\n\npublic "+ eventType.getEventTypeName() + "(");
	// result.append(createDeclarationParameters(superParameters));
	// result.append(additionalParameters);

	// result.append(")\n {\n super ("+createCallParameters(superParameters)+");
	// \n" +assignementForAdditionalParameters +" }\n");
	// result.append(setterGetterBuffer);

	// return result;
	// }

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
				result.append(",");
		}
		return result;
	}

	private StringBuffer createDeclarationParameters(List superParameters) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < superParameters.size(); i++) {
			result.append(((ArrayList) superParameters.get(i)).get(0));
			result.append(" ");
			result.append(((ArrayList) superParameters.get(i)).get(1));
			if (i < superParameters.size() - 1) {
				result.append(",");
			}
		}
		return result;
	}
}
