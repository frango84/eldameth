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

import genericUMLDiagramModel.ModelElement;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.TargetDataLine;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.Transition;
import dscDiagramModel.myDataTypes.TypeVariable;

public class DSCClassCodeGenerator {

	private DSCState state;
	private String nameUpperCase;
	private CodeGeneratorUtil util;
	private DSCDiagram parent;

	private StringBuffer initJavadocBuffer = new StringBuffer("\n/**\n* @name ");
	private StringBuffer endJavadocBuffer = new StringBuffer(
			"\n*\n* @generated\n*/\n\n");

	public DSCClassCodeGenerator(DSCState state, DSCDiagram parent,
			CodeGeneratorUtil util) {
		super();
		this.state = state;
		this.parent = parent;
		this.util = util;
		if (state.getName().toUpperCase().equals(""))
			this.nameUpperCase = "DEFAULT_NAME";
		else
			this.nameUpperCase = state.getName().toUpperCase().replaceAll(" ",
					"_");
	}

	public StringBuffer run() {

		StringBuffer stateCode = new StringBuffer();
		stateCode.append("\n // " + nameUpperCase + "State Inner Class \n");
		stateCode.append(createTopIntestation());
		stateCode.append(createVariableDefinition());
		stateCode.append(createConstructor());
		if (!state.isIsSimple()) {
			stateCode.append(createInnerClass());
			stateCode.append(createGetDefaultEntranceMethod());
		}
		stateCode.append(createHandlerMethod());
		stateCode.append(util.createActionDefinition(state));
		stateCode.append(util.createGuardDefinition(state));
		stateCode.append(util.createFunctionDefinition(state));

		if (!state.isIsSimple())
			stateCode.append(createCodeForHistoryMethods());

		stateCode.append("\n}");
		stateCode.append("\n // END " + nameUpperCase + "State Inner Class \n");

		return stateCode;

	}

	/**
	 * Crea le intestazioni della classe interna
	 * 
	 * @return
	 */
	private StringBuffer createTopIntestation() {

		StringBuffer topIntestationBuffer = new StringBuffer();

		topIntestationBuffer.append("public class " + nameUpperCase
				+ "State extends ");
		if (state.isIsSimple())
			topIntestationBuffer.append("SimpleState");
		else
			topIntestationBuffer.append("CompositeState");

		topIntestationBuffer.append(" implements Serializable { \n");

		return topIntestationBuffer;
	}

	/**
	 * Crea le definizioni delle variabili dello stato
	 * 
	 * @return
	 */
	private StringBuffer createVariableDefinition() {

		StringBuffer variableBuffer = new StringBuffer();
		StringBuffer finalVariableBuffer = new StringBuffer();

		for (int i = 0; i < state.getVariables().size(); i++) {
			TypeVariable var = (TypeVariable) state.getVariables().get(i);

			int index = var.getType().lastIndexOf(".");
			if (index != -1)
				variableBuffer.append(var.getType().substring(index + 1));
			else
				variableBuffer.append(var.getType());
			variableBuffer.append(" " + var.getName());
			if (!var.getValue().equals(""))
				variableBuffer.append("=" + var.getValue());
			variableBuffer.append(";\n");

		}
		if (variableBuffer.length() != 0) {
			finalVariableBuffer.append(initJavadocBuffer);
			finalVariableBuffer.append("variables section");
			finalVariableBuffer.append(endJavadocBuffer);
			finalVariableBuffer.append(variableBuffer);
		}
		return finalVariableBuffer;
	}

	/**
	 * Crea il costruttore dello stato in esame
	 * 
	 * @return
	 */
	private StringBuffer createConstructor() {

		StringBuffer constructor = new StringBuffer();

		constructor.append(initJavadocBuffer);
		constructor.append("constructor ADSC");
		constructor.append(endJavadocBuffer);

		constructor.append("public " + nameUpperCase
				+ "State ( AState parent, ELDABehavior ebeh) {\n");
		constructor.append("super(parent,ebeh); \n");
		constructor
				.append(util.createAddStateMethod(state.getVisualElements()));
		constructor.append("} \n");

		return constructor;
	}

	/**
	 * Crea il codice relatvo agli stati interni
	 * 
	 * @return
	 */
	private StringBuffer createInnerClass() {

		StringBuffer codeInnerClass = new StringBuffer();
		for (int i = 0; i < state.getVisualElements().size(); i++) {
			if (state.getVisualElements().get(i) instanceof DSCState) {
				DSCClassCodeGenerator stateCodeGenerator = new DSCClassCodeGenerator(
						(DSCState) state.getVisualElements().get(i), parent,
						util);
				codeInnerClass.append(stateCodeGenerator.run());
			}
		}
		return codeInnerClass;
	}

	/**
	 * Crea il metodo getDefaultEntrance
	 * 
	 * @return
	 */
	private StringBuffer createGetDefaultEntranceMethod() {
		StringBuffer getDefaultEntranceBuffer = new StringBuffer();

		getDefaultEntranceBuffer.append(initJavadocBuffer);
		getDefaultEntranceBuffer.append("getDefaultEntrance Method");
		getDefaultEntranceBuffer.append(endJavadocBuffer);

		getDefaultEntranceBuffer
				.append("protected AState getDefaultEntrance () { \n");
		Transition startTransition = util.getStartTransition(state);
		if (startTransition.getActionID() != null
				&& !startTransition.getActionID().equals(""))
			getDefaultEntranceBuffer.append(startTransition.getActionID()
					+ "(null); \n");
		if (startTransition.getTarget() instanceof DeepHistory
				|| startTransition.getTarget() instanceof ShallowHistory) {
			getDefaultEntranceBuffer
					.append("return null; //State entered by history \n }");
		} else
			getDefaultEntranceBuffer.append("return getState (\""
					+ startTransition.getTarget().getName().toUpperCase()
							.replaceAll(" ", "_") + "\"); \n }");
		return getDefaultEntranceBuffer;
	}

	/**
	 * Crea il metodo handler
	 * 
	 * @return
	 */
	private StringBuffer createHandlerMethod() {
		EList outcomingRelationships = state.getSourceRelationships();

		// verifico se esiste una transizione in uscita dallo stato considerato
		boolean exist = false;
		StringBuffer handlerBuffer = new StringBuffer();

		handlerBuffer.append(initJavadocBuffer);
		handlerBuffer.append("handler method");
		handlerBuffer.append(endJavadocBuffer);

		handlerBuffer.append("\npublic final int handler(ELDAEvent eevent){\n");

		// per tutte le transizioni in uscita
		for (int i = 0; i < outcomingRelationships.size(); i++) {

			if (outcomingRelationships.get(i) instanceof Transition) {
				exist = true;
				Transition trans = (Transition) outcomingRelationships.get(i);
				if (i != 0)
					handlerBuffer.append("else ");
				handlerBuffer.append("if (eevent instanceof "
						+ trans.getEventID());
				if (trans.getGuardID() != null
						&& !trans.getGuardID().equals(""))
					handlerBuffer.append(" && " + trans.getGuardID()
							+ "(eevent)");
				handlerBuffer.append("){\n");
				if (trans.getActionID() != null
						&& !trans.getActionID().equals(""))
					handlerBuffer.append("\n" + trans.getActionID()
							+ "(eevent);\n");

				// se il target è diverso dal source genero i metodi per gestire
				// la transizione in esame
				if (!trans.getTarget().equals(trans.getSource())) {
					List results = createMethodsForTransition(trans);
					// appendo il metodo setActiveState generato
					handlerBuffer.append(results.get(0));
					// appendo il metodo changeState generato
					handlerBuffer.append(results.get(1));
				} else if (trans.getTarget().equals(trans.getSource())
						&& !((DSCState) trans.getSource()).isIsSimple()) {
					handlerBuffer.append("changeState(this);\n");
				}

				handlerBuffer.append("return 0; \n}\n");

			}
		}
		if (exist)
			handlerBuffer.append("\nelse return parent.handler(eevent);\n}");
		else
			handlerBuffer.append("\nreturn parent.handler(eevent);\n}");

		return handlerBuffer;
	}

	/**
	 * Crea le chiamate al metodo setActiveState relativo alla transizione in
	 * esame
	 * 
	 * @param trans
	 * @return
	 */
	private List createMethodsForTransition(Transition trans) {

		// booleano che implica la creazione del metodo setActiveState
		boolean CreateSetActiveState = true;
		// E' la lista di StringBuffer che restituisce
		List<StringBuffer> results = new ArrayList<StringBuffer>(2);

		StringBuffer setActiveStateLineBuffer = new StringBuffer();

		EObject source = (EObject) trans.getSource();
		EObject target = (EObject) trans.getTarget();

		// se il target è il proprio deep o il proprio shallow non creo il
		// metodo setActiveState
		if (target.equals(state.getDeepHistory())
				|| target.equals(state.getShallowHistory()))
			CreateSetActiveState = false;

		EObject ancestor = util.getAncestor(source, target);
		int sourceDepth = util.findDepth(ancestor, source);
		int targetDepth = util.findDepth(ancestor, target);

		StringBuffer changeStateBuffer = new StringBuffer();

		changeStateBuffer.append("changeState(");

		System.out.println(targetDepth);
		// E' la stringa che permette di accedere dallo stato source
		// all'ancestor
		// di source e target della transizione
		StringBuffer ancestorBuffer = getAncestorBuffer(sourceDepth);

		// E' la lista dei parent del target fino all'ancestor
		List<String> containerTargetList = getContainerTargetList(target,
				targetDepth);

		// recupero lo stato attivo a questo livello
		ModelElement activeState = (ModelElement) target;

		for (int i = 0; i <= targetDepth; i++) {
			StringBuffer partial = new StringBuffer();
			partial.append(createCasting(i, targetDepth));
			partial.append(ancestorBuffer);
			partial.append(createGetCalls(i, containerTargetList, activeState));

			if (activeState instanceof DSCState) {
				setActiveStateLineBuffer.append(partial);
				setActiveStateLineBuffer.append(".setActiveState(");
				setActiveStateLineBuffer.append(partial);
			}

			if (i == 0)
				changeStateBuffer.append(partial);

			if (activeState instanceof DSCState) {
				if (i == 0)
					changeStateBuffer.append(".getActiveState()");

				if (!activeState.getName().equals(""))
					setActiveStateLineBuffer.append(".getState(\""
							+ activeState.getName().toUpperCase().replaceAll(
									" ", "_") + "\"));\n");
				else
					setActiveStateLineBuffer
							.append(".getState(\"DEFAULT_NAME\"));\n");
			} else if (activeState instanceof DeepHistory) {
				if (i == 0)
					changeStateBuffer.append(".deepHistory()");
			} else if ((activeState instanceof ShallowHistory)) {
				if (i == 0)
					changeStateBuffer.append(".shallowHistory()");
			}

			if (!(activeState.eContainer() instanceof DSCDiagram))
				activeState = (ModelElement) activeState.eContainer();
		}
		if (CreateSetActiveState)
			results.add(0, setActiveStateLineBuffer);
		else {
			results.add(0, new StringBuffer());
		}
		changeStateBuffer.append(");\n");
		results.add(1, changeStateBuffer);
		return results;
	}

	private List<String> getContainerTargetList(EObject target, int targetDepth) {

		// aggiunto per le transizioni aventi come target l'ancestor
		if (targetDepth == -1) {
			List<String> containerTargetList = new ArrayList<String>(1);
			containerTargetList.add(((DSCState) target).getName());
			return containerTargetList;
		}

		List<String> containerTargetList = new ArrayList<String>(targetDepth);
		if (target.eContainer() instanceof ModelElement) {
			ModelElement cont = (ModelElement) target.eContainer();
			for (int i = 0; i < targetDepth; i++) {

				if (cont.getName().equals(""))
					containerTargetList.add("DEFAULT_NAME");
				else
					containerTargetList.add(cont.getName());
				if (cont.eContainer() instanceof ModelElement)
					cont = (ModelElement) cont.eContainer();
			}
		}

		return containerTargetList;
	}

	private StringBuffer getAncestorBuffer(int sourceDepth) {
		StringBuffer ancestorBuffer = new StringBuffer();
		if (sourceDepth == -1)
			return ancestorBuffer.append("this)");
		ancestorBuffer.append("parent");
		for (int i = 0; i < sourceDepth; i++) {
			ancestorBuffer.append(".getParent()");
		}
		ancestorBuffer.append(")");
		return ancestorBuffer;
	}

	private StringBuffer createGetCalls(int init,
			List<String> containerTargetList, ModelElement target) {

		StringBuffer result = new StringBuffer();
		int size = containerTargetList.size();
		List<String> ReverseContainerTargetList = new ArrayList<String>(size);

		for (int i = 0; i < size; i++) {
			ReverseContainerTargetList.add(i, containerTargetList.get(size - i
					- 1));
		}

		for (int i = 0; i < size - init; i++) {
			result.append(".getState(\"");
			result.append(ReverseContainerTargetList.get(i).toUpperCase()
					.replaceAll(" ", "_"));
			result.append("\"))");
		}
		return result;
	}

	private StringBuffer createCasting(int init, int targetDepth) {
		final String COMP = "((CompositeState) ";
		StringBuffer result = new StringBuffer();
		result.append(COMP);
		for (int i = init; i < targetDepth; i++)
			result.append(COMP);
		return result;

	}

	/**
	 * Crea il codice relativo agli history
	 * 
	 * @return
	 */
	private StringBuffer createCodeForHistoryMethods() {

		StringBuffer codeForHistoryBuffer = new StringBuffer();
		if (!state.isIsSimple()) {
			if (state.getDeepHistory() != null) {
				for (int i = 0; i < state.getDeepHistory()
						.getSourceRelationships().size(); i++) {
					if (state.getDeepHistory().getSourceRelationships().get(i) instanceof Transition) {
						Transition transition = (Transition) state
								.getDeepHistory().getSourceRelationships().get(
										i);
						codeForHistoryBuffer.append(initJavadocBuffer);
						codeForHistoryBuffer
								.append("getDefaultDeepHistoryEntrance");
						codeForHistoryBuffer.append(endJavadocBuffer);
						codeForHistoryBuffer
								.append("\nprotected AState getDefaultDeepHistoryEntrance() {\n");
						if (transition.getActionID() != null
								&& !transition.getActionID().equals(""))
							codeForHistoryBuffer.append(transition
									.getActionID()
									+ "(null);\n");
						codeForHistoryBuffer.append("return (AState) "
								+ createLinkToTarget(transition) + "\n}");
					}
				}
			}

			if (state.getShallowHistory() != null) {
				for (int i = 0; i < state.getShallowHistory()
						.getSourceRelationships().size(); i++) {
					if (state.getShallowHistory().getSourceRelationships().get(
							i) instanceof Transition) {
						Transition transition = (Transition) state
								.getShallowHistory().getSourceRelationships()
								.get(i);
						codeForHistoryBuffer.append(initJavadocBuffer);
						codeForHistoryBuffer
								.append("getDefaultShallowHistoryEntrance");
						codeForHistoryBuffer.append(endJavadocBuffer);
						codeForHistoryBuffer
								.append("\nprotected AState getDefaultShallowHistoryEntrance() {\n");
						if (transition.getActionID() != null
								&& !transition.getActionID().equals(""))
							codeForHistoryBuffer.append(transition
									.getActionID()
									+ "(null);\n");
						codeForHistoryBuffer
								.append("return (AState) this.getState(\""
										+ transition.getTarget().getName()
												.toUpperCase() + "\");\n}");
					}
				}
			}
		}

		return codeForHistoryBuffer;
	}

	private StringBuffer createLinkToTarget(Transition transition) {
		StringBuffer result = new StringBuffer();
		DSCState target = (DSCState) transition.getTarget();
		int targetDepth = util.findDepth(transition.getSource().eContainer(),
				target);
		result.append(this.createCasting(0, targetDepth));
		result.append("this)");
		List<String> containerTargetList = this.getContainerTargetList(target,
				targetDepth);
		result.append(this.createGetCalls(0, containerTargetList, target));
		result.append(".getState(\"" + target.getName().toUpperCase() + "\");");

		return result;
	}
}
