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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see function.FunctionFactory
 * @model kind="package"
 * @generated
 */
public interface FunctionPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "function";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://function";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "function";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	FunctionPackage eINSTANCE = function.impl.FunctionPackageImpl.init();

	/**
	 * The meta object id for the '{@link function.impl.FunctionListImpl <em>List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see function.impl.FunctionListImpl
	 * @see function.impl.FunctionPackageImpl#getFunctionList()
	 * @generated
	 */
	int FUNCTION_LIST = 0;

	/**
	 * The feature id for the '<em><b>Function</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_LIST__FUNCTION = 0;

	/**
	 * The number of structural features of the the '<em>List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_LIST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link function.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see function.impl.FunctionImpl
	 * @see function.impl.FunctionPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 1;

	/**
	 * The feature id for the '<em><b>Function List</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__FUNCTION_LIST = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Returned Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__RETURNED_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__PARAMETER = 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__BODY = 4;

	/**
	 * The number of structural features of the the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link function.FunctionList <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List</em>'.
	 * @see function.FunctionList
	 * @generated
	 */
	EClass getFunctionList();

	/**
	 * Returns the meta object for the containment reference list '{@link function.FunctionList#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Function</em>'.
	 * @see function.FunctionList#getFunction()
	 * @see #getFunctionList()
	 * @generated
	 */
	EReference getFunctionList_Function();

	/**
	 * Returns the meta object for class '{@link function.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see function.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the container reference '{@link function.Function#getFunctionList <em>Function List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Function List</em>'.
	 * @see function.Function#getFunctionList()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_FunctionList();

	/**
	 * Returns the meta object for the attribute '{@link function.Function#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see function.Function#getName()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Name();

	/**
	 * Returns the meta object for the attribute '{@link function.Function#getReturnedType <em>Returned Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Returned Type</em>'.
	 * @see function.Function#getReturnedType()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_ReturnedType();

	/**
	 * Returns the meta object for the attribute list '{@link function.Function#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameter</em>'.
	 * @see function.Function#getParameter()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link function.Function#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see function.Function#getBody()
	 * @see #getFunction()
	 * @generated
	 */
	EAttribute getFunction_Body();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	FunctionFactory getFunctionFactory();

} //FunctionPackage
