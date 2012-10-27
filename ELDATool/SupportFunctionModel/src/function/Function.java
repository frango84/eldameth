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
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link function.Function#getFunctionList <em>Function List</em>}</li>
 *   <li>{@link function.Function#getName <em>Name</em>}</li>
 *   <li>{@link function.Function#getReturnedType <em>Returned Type</em>}</li>
 *   <li>{@link function.Function#getParameter <em>Parameter</em>}</li>
 *   <li>{@link function.Function#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see function.FunctionPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends EObject{
	/**
	 * Returns the value of the '<em><b>Function List</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link function.FunctionList#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function List</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function List</em>' container reference.
	 * @see #setFunctionList(FunctionList)
	 * @see function.FunctionPackage#getFunction_FunctionList()
	 * @see function.FunctionList#getFunction
	 * @model opposite="function" required="true"
	 * @generated
	 */
	FunctionList getFunctionList();

	/**
	 * Sets the value of the '{@link function.Function#getFunctionList <em>Function List</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function List</em>' container reference.
	 * @see #getFunctionList()
	 * @generated
	 */
	void setFunctionList(FunctionList value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see function.FunctionPackage#getFunction_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link function.Function#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Returned Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Returned Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Returned Type</em>' attribute.
	 * @see #setReturnedType(String)
	 * @see function.FunctionPackage#getFunction_ReturnedType()
	 * @model
	 * @generated
	 */
	String getReturnedType();

	/**
	 * Sets the value of the '{@link function.Function#getReturnedType <em>Returned Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Returned Type</em>' attribute.
	 * @see #getReturnedType()
	 * @generated
	 */
	void setReturnedType(String value);

	/**
	 * Returns the value of the '<em><b>Parameter</b></em>' attribute list.
	 * The list contents are of type {@link dscDiagramModel.myDataTypes.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter</em>' attribute list.
	 * @see function.FunctionPackage#getFunction_Parameter()
	 * @model type="dscDiagramModel.myDataTypes.TypeVariable" dataType="dscDiagramModel.Variable"
	 * @generated
	 */
	EList getParameter();

	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' attribute.
	 * @see #setBody(String)
	 * @see function.FunctionPackage#getFunction_Body()
	 * @model
	 * @generated
	 */
	String getBody();

	/**
	 * Sets the value of the '{@link function.Function#getBody <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' attribute.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(String value);

} // Function
