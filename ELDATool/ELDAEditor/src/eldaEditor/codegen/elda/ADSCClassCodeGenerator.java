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

//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.core.search.JavaWorkspaceScope;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.FolderSelectionDialog;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.impl.DSCStateImpl;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import dscDiagramModel.myDataTypes.TypeVariable;

public class ADSCClassCodeGenerator {

	private StringBuffer result = new StringBuffer();
	private DSCDiagram parent;
	private IFile fileInput;

	private String fileName;
	String fileNameUpperCase;
	private String packageName;
	private IFolder folder;
	private CodeGeneratorUtil util;
	private String dscProjectName;

	private static StringBuffer INIT_JAVADOC_BUFFER = new StringBuffer(
			"\n/**\n* @name ");
	private static StringBuffer END_JAVADOC_BUFFER = new StringBuffer(
			"\n*\n* @generated\n*/\n\n");

	public ADSCClassCodeGenerator(DSCDiagram parent, String fileName,
			String dscProjectName, String packageName, IFolder folder,
			CodeGeneratorUtil util, IFile fileInput) {
		this.fileInput = fileInput;
		this.parent = parent;
		this.fileName = fileName;
		this.dscProjectName = dscProjectName;
		this.packageName = packageName;
		this.util = util;
		this.folder = folder;

	}

	public void run() {

		result.append(createTopIntestation());
		result.append(createDiagramVariableDefinition());
		result.append(createConstructor());
		result.append(createConstructSubActiveStatesMethod());
		result.append(createInnerClass());
		result.append(createGetDefaultEntranceMethod());
		result.append(createADSCHandlerMethod());
		result.append(util.createActionDefinitionIntoADSC());
		result.append(util.createGuardDefinitionIntoADSC());
		result.append(util.createFunctionDefinitionIntoADSC());
		result.append("}");

		this.createADSCClass();
	}

	/**
	 * Crea il package, gli import e la definizione della classe
	 * 
	 * @return
	 */
	private StringBuffer createTopIntestation() {

		StringBuffer intestation = new StringBuffer();

		IPath path = fileInput.getProjectRelativePath();
		path = path.removeLastSegments(1);
		intestation.append("package \t");
		intestation.append(packageName);
		// while (!path.isEmpty())
		// {
		// intestation.append("."+path.segment(0));
		// path=path.removeFirstSegments(1);
		// }
		intestation.append("; \n\n");

		StringBuffer imports = new StringBuffer();
		imports.append("import eldaframework.components.*;\n");
		imports.append("import eldaframework.agent.*;\n");
		imports.append("import eldaframework.dsc.*;\n");
		imports.append("import eldaframework.eldaevent.*; \n");
		imports.append("import eldaframework.eldaevent.coordination.*; \n");
		imports
				.append("import eldaframework.eldaevent.coordination.direct.*; \n");
		imports.append("import eldaframework.eldaevent.coordination.p_s.*; \n");
		imports
				.append("import eldaframework.eldaevent.coordination.services.*; \n");
		imports
				.append("import eldaframework.eldaevent.coordination.tuples.*; \n");

		imports.append("import eldaframework.eldaevent.exception.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.coordination.*;  \n");
		imports
				.append("import eldaframework.eldaevent.exception.coordination.direct.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.coordination.p_s.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.coordination.services.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.coordination.tuples.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.internal.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.management.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.management.lifecycle.*; \n");
		imports
				.append("import eldaframework.eldaevent.exception.management.timer.*; \n");
		imports.append("import eldaframework.eldaevent.internal.*; \n");
		imports.append("import eldaframework.eldaevent.management.*; \n");
		imports
				.append("import eldaframework.eldaevent.management.lifecycle.*; \n");
		imports.append("import eldaframework.eldaevent.management.timer.*; \n");
		imports.append("import eldaframework.eldaevent.simulated.*; \n");
		imports.append("import eldaframework.maaf.*;\n");
		imports.append("import java.io.*;\n");

		imports.append("import " + dscProjectName.toLowerCase()
				+ ".events.*; \n");

		util.generateImportsFromClasspath(imports);
		util.generateImportsFromVariables(imports);
		intestation.append(imports);

		StringBuffer nameClass = new StringBuffer();
		fileNameUpperCase = fileName.toUpperCase().substring(0, 1).concat(
				fileName.substring(1));
		nameClass.append("\n\npublic class " + fileNameUpperCase
				+ "ActiveState"
				+ " extends ELDAActiveState implements Serializable {\n");

		intestation.append(nameClass);

		return intestation;
	}

	/**
	 * Crea la definizione delle variabili del diagramma
	 * 
	 * @return
	 */
	private StringBuffer createDiagramVariableDefinition() {
		StringBuffer variableBuffer = new StringBuffer();
		StringBuffer finalVariableBuffer = new StringBuffer();

		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent
					.getDiagramVariables().get(i);
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
			finalVariableBuffer.append(INIT_JAVADOC_BUFFER);
			finalVariableBuffer.append("variables section");
			finalVariableBuffer.append(END_JAVADOC_BUFFER);
			finalVariableBuffer.append(variableBuffer);

		}
		return finalVariableBuffer;

	}

	/**
	 * Crea il costruttore dell'ADSC
	 * 
	 * @return
	 */

	private StringBuffer createConstructor() {

		StringBuffer constructor = new StringBuffer();

		constructor.append(INIT_JAVADOC_BUFFER);
		constructor.append("constructor ADSC");
		constructor.append(END_JAVADOC_BUFFER);

		constructor.append("public " + fileNameUpperCase + "ActiveState (");
		List<String> array = new ArrayList<String>();

		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent
					.getDiagramVariables().get(i);

			if (var.isBePresentIntoConstructor()) {

				if (i != parent.getDiagramVariables().size()) {
					int index = var.getType().lastIndexOf(".");
					if (index != -1)
						array.add(var.getType().substring(index + 1) + " "
								+ var.getName());
					else
						array.add(var.getType() + " " + var.getName());
				}
			}
		}
		for (int i = 0; i < array.size(); i++) {
			constructor.append(array.get(i));
			if (i < array.size() - 1)
				constructor.append(",");
		}

		constructor.append(") {\nsuper(); \n");

		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent
					.getDiagramVariables().get(i);
			if (var.isBePresentIntoConstructor()) {
				constructor.append("this." + var.getName() + "="
						+ var.getName() + ";\n");
			}
		}

		constructor.append("}\n");

		return constructor;

	}

	/**
	 * Crea il metodo ConstructSubActiveStates
	 * 
	 * @return
	 */
	private StringBuffer createConstructSubActiveStatesMethod() {

		StringBuffer constructSubActiveStatesBuffer = new StringBuffer();

		constructSubActiveStatesBuffer.append(INIT_JAVADOC_BUFFER);
		constructSubActiveStatesBuffer
				.append("constructSubActiveStates Method");
		constructSubActiveStatesBuffer.append(END_JAVADOC_BUFFER);

		constructSubActiveStatesBuffer
				.append("public void constructSubActiveStates(){ \n");
		constructSubActiveStatesBuffer.append(util.createAddStateMethod(parent
				.getModelElements()));
		constructSubActiveStatesBuffer.append("}");
		return constructSubActiveStatesBuffer;
	}

	/**
	 * Crea il codice relativo alle classi interne
	 * 
	 * @return
	 */
	private StringBuffer createInnerClass() {

		StringBuffer innerClassBuffer = new StringBuffer();
		for (int i = 0; i < parent.getModelElements().size(); i++) {

			if (parent.getModelElements().get(i) instanceof DSCState) {
				DSCState state = (DSCState) parent.getModelElements().get(i);
				DSCClassCodeGenerator stateCodeGenerator = new DSCClassCodeGenerator(
						state, parent, util);
				innerClassBuffer.append(stateCodeGenerator.run());
			}
		}

		return innerClassBuffer;
	}

	/**
	 * Crea il metodo GetDefaultEntrance
	 * 
	 * @return
	 */
	private StringBuffer createGetDefaultEntranceMethod() {

		StringBuffer getDefaultEntranceMethodBuffer = new StringBuffer();

		getDefaultEntranceMethodBuffer.append(INIT_JAVADOC_BUFFER);
		getDefaultEntranceMethodBuffer.append("getDefaultEntrance Method");
		getDefaultEntranceMethodBuffer.append(END_JAVADOC_BUFFER);

		getDefaultEntranceMethodBuffer
				.append("protected AState getDefaultEntrance() { \n"
						+ "return getState(\"");

		for (int i = 0; i < parent.getModelElements().size(); i++) {
			if ((parent.getModelElements().get(i) instanceof DSCState)
					&& ((((DSCState) parent.getModelElements().get(i))
							.getStereotype().equals("dhs")) || (((DSCState) parent
							.getModelElements().get(i)).getStereotype()
							.equals("DHS")))) {
				getDefaultEntranceMethodBuffer.append(((DSCState) parent
						.getModelElements().get(i)).getName().toUpperCase());

			}
		}
		getDefaultEntranceMethodBuffer.append("\"); \n } \n");

		return getDefaultEntranceMethodBuffer;
	}

	/**
	 * Crea il metodo Handler
	 * 
	 * @return
	 */
	private StringBuffer createADSCHandlerMethod() {
		StringBuffer handlerBuffer = new StringBuffer();

		handlerBuffer.append(INIT_JAVADOC_BUFFER);
		handlerBuffer.append("handler method");
		handlerBuffer.append(END_JAVADOC_BUFFER);

		handlerBuffer
				.append("\npublic final int handler (ELDAEvent mevent) { \n "
						+ "return super.handler(mevent);\n } \n");
		return handlerBuffer;
	}

	private void createADSCClass() {
		// IFile
		// fileOutput=folder.getFile(fileNameUpperCase+"ActiveState.java");
		IJavaElement javaFolder = JavaCore.create(folder);
		util.generateFileWithThisCode(fileNameUpperCase + "ActiveState.java",
				result.toString(), javaFolder);
	}

}
