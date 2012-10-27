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

import java.util.*;
import org.eclipse.jdt.core.dom.*;

/**
 * Utility class.
 * 
 * @author G. Fortino, F. Rango
 */
public class Util {
	
	/** This method translates to upper case the first character of the string passed as parameter.
	 * @param arg string to translate.
	 * @return result string.
	 */
	public static String firstToUpper(String arg) {
		//metodo che trasforma in maiuscolo il primo carattere della stringa passata come parametro 
		
		String result = new String();
		CharSequence start = arg.subSequence(0, 1);
		CharSequence end = arg.subSequence(1, arg.length());
		result = result.concat(start.toString().toUpperCase());
		result = result.concat(end.toString());
		return result;
	}
	
	/** This method counts the variables of the method passed as parameter.
	 * @param md the method.
	 * @return the number of variables.
	 */
	public static int contVarsOfMethod(MethodDeclaration md){
		//metodo che conta le variabili del metodo passato come parametro
		
		int contVarMethod = 0;
		
		//contiamo i parametri del metodo corrente
		for(int j=0; j < md.parameters().size(); j++){
			if(md.parameters().get(j) instanceof SingleVariableDeclaration){
				contVarMethod++;
			}
		}
		
		//contiamo le altre variabili contenute nel corpo del metodo corrente
		Block body = md.getBody();
		for(int j=0; j < body.statements().size(); j++){
			if(body.statements().get(j) instanceof VariableDeclarationStatement){
				VariableDeclarationStatement vd = (VariableDeclarationStatement) body.statements().get(j);
				for(int z=0; z < vd.fragments().size(); z++){
					if(vd.fragments().get(z) instanceof VariableDeclarationFragment){
						contVarMethod++;
					}
				}
			}
		}
		
		return contVarMethod;
	}
	
	/** This method counts the variables of the root.
	 * @param unit the CompilationUnit.
	 * @return the number of variables.
	 */
	public static int contVarsOfRoot(CompilationUnit unit){
		//metodo che conta le variabili della root
		
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		int contVarRoot = 0;
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof FieldDeclaration){
				FieldDeclaration fd = (FieldDeclaration) classComponents.get(i);
				for(int j=0; j < fd.fragments().size(); j++){
					if(fd.fragments().get(j) instanceof VariableDeclarationFragment){
						contVarRoot++;
					}
				}
			}
		}
		
		return contVarRoot;
	}
	
	/** This method searches the method passed as parameter in the CompilationUnit passed as parameter.
	 * @param unit the CompilationUnit.
	 * @param methodName name of the searched method.
	 * @return a boolean that indicates if we have found the method (true) or not (false).
	 */
	public static boolean findMethod(CompilationUnit unit, String methodName){
		//cerca il metodo passato come parametro all'interno della CompilationUnit passata come parametro
		
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof MethodDeclaration){
				MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
				if((md.getName().toString()).equals(methodName)){
					return true;
				}
			}
		}
		return false;
	}
	
	/** This method returns the method declaration of the method passed as parameter.
	 * @param unit the CompilationUnit.
	 * @param methodName name of the searched method.
	 * @return the MethodDeclaration or null.
	 */
	public static MethodDeclaration getMethodDeclaration(CompilationUnit unit, String methodName){
		//restituisce la dichiarazione del metodo indicato dal parametro
		
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for(int i=0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof MethodDeclaration){
				MethodDeclaration md = (MethodDeclaration) classComponents.get(i);
				if((md.getName().toString()).equals(methodName)){
					return md;
				}
			}
		}
		return null;
	}

	/** This method searches the variable name passed as parameter in the method
	 * passed as parameter or in the root.
	 * @param name the name of the searched variable.
	 * @param method the method.
	 * @param unit the CompilationUnit.
	 * @return a string that represents the variable object to use or null.
	 */
	public static String findVar(SimpleName name, MethodDeclaration method, CompilationUnit unit){
		//trova la variabile che ha un nome uguale a quello passato come parametro
		//e restituisce l'oggetto variabili da utilizzare (quello del metodo passato
		//come parametro o quello della root)
		
		//cerchiamo la variabile nel metodo passato come parametro:

		//cerchiamo nei parametri del metodo corrente
		for(int j = 0; j < method.parameters().size(); j++){
			if(method.parameters().get(j) instanceof SingleVariableDeclaration){
				SingleVariableDeclaration par = (SingleVariableDeclaration) method.parameters().get(j);
				if((par.getName().toString()).equals(name.toString())){
					return "var" + Util.firstToUpper(method.getName().toString()); //restituiamo l'oggetto variabili del metodo corrente (nel formato "varXxx", dove "xxx" è il nome del metodo)
				}
			}
		}

		//cerchiamo nelle variabili contenute nel corpo del metodo corrente
		Block body = method.getBody();
		for(int j = 0; j < body.statements().size(); j++){
			if(body.statements().get(j) instanceof VariableDeclarationStatement){
				VariableDeclarationStatement vd = (VariableDeclarationStatement) body.statements().get(j);
				for(int z = 0; z < vd.fragments().size(); z++){
					if(vd.fragments().get(z) instanceof VariableDeclarationFragment){
						VariableDeclarationFragment v = (VariableDeclarationFragment) vd.fragments().get(z);
						if((v.getName().toString()).equals(name.toString())){
							return "var" + Util.firstToUpper(method.getName().toString()); //restituiamo l'oggetto variabili del metodo corrente (nel formato "varXxx", dove "xxx" è il nome del metodo)
						}
					}
				}
			}
		}

		//cerchiamo la variabile nella root:
		List classComponents = ((TypeDeclaration) unit.types().get(0)).bodyDeclarations();
		for(int i = 0; i < classComponents.size(); i++){
			if(classComponents.get(i) instanceof FieldDeclaration){
				FieldDeclaration fd = (FieldDeclaration) classComponents.get(i);
				for(int j = 0; j < fd.fragments().size(); j++){
					if(fd.fragments().get(j) instanceof VariableDeclarationFragment){
						VariableDeclarationFragment v = (VariableDeclarationFragment) fd.fragments().get(j);
						if((v.getName().toString()).equals(name.toString())){
							return "varRoot"; //restituiamo l'oggetto variabili della root
						}
					}
				}
			}
		}

		//se non abbiamo trovato la variabile, restituiamo NULL
		return null;
	}
	
	/** This method translates the primitive types to the corresponding object.
	 * @param type the type.
	 * @return the corresponding object.
	 */
	public static String convertPrimitiveTypeToObject(String type) {
		//metodo che trasforma i tipi primitivi
		//nel corrispondente oggetto (es.: "int" in "Integer")
		
		if(type.equals("byte")){
			return "Byte";
		}
		else if(type.equals("short")){
			return "Short";
		}
		else if(type.equals("int")){
			return "Integer";
		}
		else if(type.equals("long")){
			return "Long";
		}
		else if(type.equals("float")){
			return "Float";
		}
		else if(type.equals("double")){
			return "Double";
		}
		else if(type.equals("boolean")){
			return "Boolean";
		}
		else if(type.equals("char")){
			return "Character";
		}
		return type; //se il tipo non è stato tradotto, restituiamo il tipo stesso
	}
	
	public static String appendNewLine(String s){
		//questo metodo appende alla stringa passata come parametro
		//il carattere "\n", se non esiste
		if(!s.endsWith("\n")){
			s += "\n";
		}
		return s;
	}

}
