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
package actionGuardModel;

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
 * @see actionGuardModel.ActionGuardModelFactory
 * @model kind="package"
 * @generated
 */
public interface ActionGuardModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "actionGuardModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://actionGuardModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "actionGuardModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ActionGuardModelPackage eINSTANCE = actionGuardModel.impl.ActionGuardModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link actionGuardModel.impl.AnElementListImpl <em>An Element List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see actionGuardModel.impl.AnElementListImpl
	 * @see actionGuardModel.impl.ActionGuardModelPackageImpl#getAnElementList()
	 * @generated
	 */
	int AN_ELEMENT_LIST = 0;

	/**
	 * The feature id for the '<em><b>An Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT_LIST__AN_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>An Element List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT_LIST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link actionGuardModel.impl.AnElementImpl <em>An Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see actionGuardModel.impl.AnElementImpl
	 * @see actionGuardModel.impl.ActionGuardModelPackageImpl#getAnElement()
	 * @generated
	 */
	int AN_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>An Element List</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT__AN_ELEMENT_LIST = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT__BODY = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT__COMMENT = 3;

	/**
	 * The number of structural features of the '<em>An Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AN_ELEMENT_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link actionGuardModel.AnElementList <em>An Element List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>An Element List</em>'.
	 * @see actionGuardModel.AnElementList
	 * @generated
	 */
	EClass getAnElementList();

	/**
	 * Returns the meta object for the containment reference list '{@link actionGuardModel.AnElementList#getAnElement <em>An Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>An Element</em>'.
	 * @see actionGuardModel.AnElementList#getAnElement()
	 * @see #getAnElementList()
	 * @generated
	 */
	EReference getAnElementList_AnElement();

	/**
	 * Returns the meta object for class '{@link actionGuardModel.AnElement <em>An Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>An Element</em>'.
	 * @see actionGuardModel.AnElement
	 * @generated
	 */
	EClass getAnElement();

	/**
	 * Returns the meta object for the container reference '{@link actionGuardModel.AnElement#getAnElementList <em>An Element List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>An Element List</em>'.
	 * @see actionGuardModel.AnElement#getAnElementList()
	 * @see #getAnElement()
	 * @generated
	 */
	EReference getAnElement_AnElementList();

	/**
	 * Returns the meta object for the attribute '{@link actionGuardModel.AnElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see actionGuardModel.AnElement#getName()
	 * @see #getAnElement()
	 * @generated
	 */
	EAttribute getAnElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link actionGuardModel.AnElement#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see actionGuardModel.AnElement#getBody()
	 * @see #getAnElement()
	 * @generated
	 */
	EAttribute getAnElement_Body();

	/**
	 * Returns the meta object for the attribute '{@link actionGuardModel.AnElement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see actionGuardModel.AnElement#getComment()
	 * @see #getAnElement()
	 * @generated
	 */
	EAttribute getAnElement_Comment();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ActionGuardModelFactory getActionGuardModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link actionGuardModel.impl.AnElementListImpl <em>An Element List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see actionGuardModel.impl.AnElementListImpl
		 * @see actionGuardModel.impl.ActionGuardModelPackageImpl#getAnElementList()
		 * @generated
		 */
		EClass AN_ELEMENT_LIST = eINSTANCE.getAnElementList();

		/**
		 * The meta object literal for the '<em><b>An Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AN_ELEMENT_LIST__AN_ELEMENT = eINSTANCE.getAnElementList_AnElement();

		/**
		 * The meta object literal for the '{@link actionGuardModel.impl.AnElementImpl <em>An Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see actionGuardModel.impl.AnElementImpl
		 * @see actionGuardModel.impl.ActionGuardModelPackageImpl#getAnElement()
		 * @generated
		 */
		EClass AN_ELEMENT = eINSTANCE.getAnElement();

		/**
		 * The meta object literal for the '<em><b>An Element List</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AN_ELEMENT__AN_ELEMENT_LIST = eINSTANCE.getAnElement_AnElementList();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AN_ELEMENT__NAME = eINSTANCE.getAnElement_Name();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AN_ELEMENT__BODY = eINSTANCE.getAnElement_Body();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AN_ELEMENT__COMMENT = eINSTANCE.getAnElement_Comment();

	}

} //ActionGuardModelPackage
