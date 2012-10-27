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

import org.eclipse.jdt.core.dom.*;

import java.util.*;

/**
 * Visitor for key statements based translation.
 * 
 * @author G. Fortino, F. Rango
 */
public class KeyStatementsBasedVisitor extends ASTVisitor {
	
	//Questo visitor cerca nel nodo di partenza e nei suoi sotto-nodi:
	//- un key statement
	//- un'invocazione ad un metodo
	//- un return
	
	private boolean returnFound; //indica se è stato trovato o meno un return
	private boolean keyStatementFound; //indica se è stato trovato un key statement
	private ArrayList<MethodDeclaration> invokedMethodsList; //lista dei metodi invocati
	private CompilationUnit unit;
	
	public KeyStatementsBasedVisitor(CompilationUnit unit) {
		super(false); //FALSE = non visita i commenti
		this.unit = unit;
		this.returnFound = false;
		this.keyStatementFound = false;
		this.invokedMethodsList = new ArrayList<MethodDeclaration>();
	}
	
	public ArrayList<MethodDeclaration> getInvokedMethodsList() {
		return this.invokedMethodsList;
	}

	public boolean isReturnFound() {
		return this.returnFound;
	}

	public boolean isKeyStatementFound() {
		return this.keyStatementFound;
	}
	
	/**
	 * Visits the given type-specific AST node.
	 * 
	 * @param node the node to visit
	 * @return <code>true</code> if the children of this node should be
	 * visited, and <code>false</code> if the children of this node should
	 * be skipped
	 */
	public boolean visit(ReturnStatement node) {
		//indichiamo che è stato trovato un return
		this.returnFound = true;
		
		return true;
	}
	
	/**
	 * Visits the given type-specific AST node.
	 * 
	 * @param node the node to visit
	 * @return <code>true</code> if the children of this node should be
	 * visited, and <code>false</code> if the children of this node should
	 * be skipped
	 */
	public boolean visit(ExpressionStatement node) {
		if(node.toString().replace("\n", "").equals(Translator.MOVE_KEY) ||
				node.toString().replace("\n", "").equals(Translator.CHECKPOINT_KEY) ||
				node.toString().replace("\n", "").equals(Translator.PERSISTENCE_KEY)||
				node.toString().replace("\n", "").equals(Translator.CLONE_KEY)){
			//indichiamo che è stato trovato un key statement
			this.keyStatementFound = true;
		}
		else{
			//cerchiamo un'invocazione ad un metodo
			Expression ex = node.getExpression();
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
					String calledMethodName = mi.getName().toString();
					MethodDeclaration calledMethod = Util.getMethodDeclaration(this.unit, calledMethodName);
					if(calledMethod != null){
						this.invokedMethodsList.add(calledMethod);
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Visits the given type-specific AST node.
	 * 
	 * @param node the node to visit
	 * @return <code>true</code> if the children of this node should be
	 * visited, and <code>false</code> if the children of this node should
	 * be skipped
	 */
	public boolean visit(VariableDeclarationStatement node) {
		//cerchiamo un'invocazione ad un metodo
		VariableDeclarationFragment v = (VariableDeclarationFragment)(node.fragments().get(0));
		Expression initializer = v.getInitializer();
		if((initializer != null) && (initializer instanceof MethodInvocation)){
			String calledMethodName = ((MethodInvocation) initializer).getName().toString();
			MethodDeclaration calledMethod = Util.getMethodDeclaration(this.unit, calledMethodName);
			if(calledMethod != null){
				this.invokedMethodsList.add(calledMethod);
			}
		}
		return true;
	}

}
