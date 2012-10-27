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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;

/**
 * Genera il codice relativo alla classe dell'agente
 * 
 * @author G. Fortino, F. Rango
 */
public class AgentClassCodeGeneratorForJADE {
	
	private StringBuffer result;
	private DSCDiagram parent;
	private IFile fileInput;
	private String fileName;
	private String agentName;
	private String packageName;
	private IFolder folder;
	private CodeGeneratorUtilForJADE util;
	private String dscProjectName;
	
	public AgentClassCodeGeneratorForJADE(DSCDiagram parent, String fileName,
			String dscProjectName, String packageName, IFolder folder,
			CodeGeneratorUtilForJADE util, IFile fileInput) {
		this.parent = parent;
		this.dscProjectName = dscProjectName;
		this.packageName = packageName;
		this.util = util;
		this.fileInput = fileInput;
		this.folder = folder;
		this.result = new StringBuffer();
		this.fileName = fileName;
		this.agentName = this.fileName.toUpperCase().substring(0, 1).concat(this.fileName.substring(1));
	}
	
	public void run(){
		
		//creiamo la dichiarazione del package
		result.append("package " + packageName + ";\n");
		
		//creiamo gli imports
		util.generateJADEImports(result);
		result.append("import " + dscProjectName.toLowerCase() + ".events.*;\n");
		util.generateImportsFromClasspath(result);
		util.generateImportsFromVariables(result);

		//creiamo l'intestazione della classe
		result.append("public class " + agentName + " extends Agent {\n");
		
		//creiamo la variabile per l'handler degli ELDAEvent
		result.append("private ELDAEventHandler eldaEventHandler;\n");
		
		//apriamo il metodo "setup"
		result.append("protected void setup(){\n");
		result.append("try{\n");
		String registrations = "";
		registrations += "getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);\n";
		registrations += "getContentManager().registerOntology(MobilityOntology.getInstance());\n";
		registrations += "getContentManager().registerOntology(JADEManagementOntology.getInstance());\n";
		result.append(registrations);
		
		//creiamo l'handler degli ELDAEvent
		result.append("eldaEventHandler = new ELDAEventHandler(this);\n");
		result.append("addBehaviour(eldaEventHandler);\n");
		
		//aggiungiamo il DistilledStateChartBehaviour root (se il costruttore di questo
		//behaviour possiede delle variabili aggiuntive definite dall'utente, vengono
		//prelevate dai parametri passati all'agente tramite il metodo "getArguments")
		String behaviour = agentName + "DSCBehaviour b = new " + agentName + "DSCBehaviour(this, \"" + agentName + "DSCBehaviour\"";
		List<String> paramTypes = new ArrayList<String>();
		for (int i = 0; i < parent.getDiagramVariables().size(); i++) {
			TypeDiagramVariable var = (TypeDiagramVariable) parent.getDiagramVariables().get(i);
			if (var.isBePresentIntoConstructor()) {
				int index = var.getType().lastIndexOf(".");
				if (index != -1){
					paramTypes.add(var.getType().substring(index + 1));
				}
				else{
					paramTypes.add(var.getType());
				}
			}
		}
		if(paramTypes.size() != 0){
			result.append("Object [] args = getArguments();\n");
			result.append(behaviour);
			for(int i=0; i < paramTypes.size(); i++){
				result.append(", (" + util.convertPrimitiveTypeToObject(paramTypes.get(i)) + ") args[" + i + "]");
			}
			result.append(");\n");
		}
		else{
			result.append(behaviour + ");\n");
		}
		result.append("addBehaviour(b);\n");
		
		//chiudiamo il metodo "setup"
		result.append("}\n");
		result.append("catch (Exception ex) {\n");
		result.append("ex.printStackTrace();\n");
		result.append("doDelete();\n");
		result.append("}\n");
		result.append("}\n");

		//creiamo il metodo "generate(ELDAEvent)", che permette di inviare un ELDAEvent tramite l'apposito handler
		result.append("private void generate(ELDAEvent event){ eldaEventHandler.generate(event); }\n");

		//creiamo il metodo "self()", che permette di ottenere l'AID dell'agente corrente
		result.append("private AID self(){ return getAID(); }\n");
		
		//creiamo il metodo "getEventCount()", che permette di ottenere il contatore degli eventi gestiti dall'agente corrente tramite l'apposito handler
		result.append("private int getEventCount(){ return eldaEventHandler.getEventCount(); }\n");

		//creiamo il metodo "afterMove"
		result.append("protected void afterMove(){\n");
		result.append(registrations);
		result.append("}\n");
		
		//creiamo il metodo "afterClone"
		result.append("protected void afterClone(){\n");
		result.append(registrations);
		result.append("eldaEventHandler.restoreTopicRegistrations();\n");
		result.append("}\n");

		//creiamo il DistilledStateChartBehaviour root
		ADSCClassCodeGeneratorForJADE activeStateCodeGenerator = new ADSCClassCodeGeneratorForJADE(this.parent,
				this.fileName, this.dscProjectName, this.packageName, this.folder, this.util, this.fileInput);
		result.append(activeStateCodeGenerator.run());

		//chiudiamo la classe principale
		result.append("}");
		
		//creiamo il file con il codice generato
		IJavaElement javaFolder = JavaCore.create(folder);
		util.generateFileWithThisCode(agentName + ".java", result.toString(), javaFolder);
	}

}
