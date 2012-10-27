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

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package function;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link function.FunctionList#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see function.FunctionPackage#getFunctionList()
 * @model
 * @generated
 */
public interface FunctionList extends EObject {
	/**
	 * Returns the value of the '<em><b>Function</b></em>' containment reference list.
	 * The list contents are of type {@link function.Function}.
	 * It is bidirectional and its opposite is '{@link function.Function#getFunctionList <em>Function List</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' containment reference list.
	 * @see function.FunctionPackage#getFunctionList_Function()
	 * @see function.Function#getFunctionList
	 * @model type="function.Function" opposite="functionList" containment="true"
	 * @generated
	 */
	EList getFunction();

} // FunctionList
