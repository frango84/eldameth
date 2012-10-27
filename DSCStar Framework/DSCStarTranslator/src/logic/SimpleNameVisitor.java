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
 * Visitor for SimpleName.
 * 
 * @author G. Fortino, F. Rango
 */
public class SimpleNameVisitor extends ASTVisitor {
	
	//Questo visitor trova tutti i "SimpleName" presenti nel nodo
	//di partenza e nei suoi sotto-nodi.
	//Un "SimpleName" è un identificatore diverso dalle keywords di Java,
	//dai booleani "true" e "false" e dalla parola "null".

	private ArrayList<SimpleName> simpleNamesList;
		
	public SimpleNameVisitor() {
		super(false); //FALSE = non visita i commenti
		simpleNamesList = new ArrayList<SimpleName>();
	}
	
	/**
	 * Visits the given type-specific AST node.
	 * 
	 * @param node the node to visit
	 * @return <code>true</code> if the children of this node should be
	 * visited, and <code>false</code> if the children of this node should
	 * be skipped
	 */
	public boolean visit(SimpleName node) {
		simpleNamesList.add(node);
		return true;
	}

	public ArrayList<SimpleName> getSimpleNamesList() {
		return simpleNamesList;
	}
	
}