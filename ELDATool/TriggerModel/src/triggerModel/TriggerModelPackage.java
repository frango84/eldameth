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
package triggerModel;

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
 * @see triggerModel.TriggerModelFactory
 * @model kind="package"
 * @generated
 */
public interface TriggerModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "triggerModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://event";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "event";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TriggerModelPackage eINSTANCE = triggerModel.impl.TriggerModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link triggerModel.impl.TriggerListImpl <em>Trigger List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see triggerModel.impl.TriggerListImpl
	 * @see triggerModel.impl.TriggerModelPackageImpl#getTriggerList()
	 * @generated
	 */
	int TRIGGER_LIST = 0;

	/**
	 * The feature id for the '<em><b>Trigger</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER_LIST__TRIGGER = 0;

	/**
	 * The number of structural features of the the '<em>Trigger List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER_LIST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link triggerModel.impl.TriggerImpl <em>Trigger</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see triggerModel.impl.TriggerImpl
	 * @see triggerModel.impl.TriggerModelPackageImpl#getTrigger()
	 * @generated
	 */
	int TRIGGER = 1;

	/**
	 * The feature id for the '<em><b>Trigger List</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER__TRIGGER_LIST = 0;

	/**
	 * The feature id for the '<em><b>Extend UD Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER__EXTEND_UD_EVENT = 1;

	/**
	 * The feature id for the '<em><b>Extended Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER__EXTENDED_EVENT = 2;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER__PARAMETERS = 3;

	/**
	 * The feature id for the '<em><b>Trigger Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER__TRIGGER_NAME = 4;

	/**
	 * The number of structural features of the the '<em>Trigger</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIGGER_FEATURE_COUNT = 5;


	/**
	 * Returns the meta object for class '{@link triggerModel.TriggerList <em>Trigger List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trigger List</em>'.
	 * @see triggerModel.TriggerList
	 * @generated
	 */
	EClass getTriggerList();

	/**
	 * Returns the meta object for the containment reference list '{@link triggerModel.TriggerList#getTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Trigger</em>'.
	 * @see triggerModel.TriggerList#getTrigger()
	 * @see #getTriggerList()
	 * @generated
	 */
	EReference getTriggerList_Trigger();

	/**
	 * Returns the meta object for class '{@link triggerModel.Trigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trigger</em>'.
	 * @see triggerModel.Trigger
	 * @generated
	 */
	EClass getTrigger();

	/**
	 * Returns the meta object for the container reference '{@link triggerModel.Trigger#getTriggerList <em>Trigger List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Trigger List</em>'.
	 * @see triggerModel.Trigger#getTriggerList()
	 * @see #getTrigger()
	 * @generated
	 */
	EReference getTrigger_TriggerList();

	/**
	 * Returns the meta object for the attribute '{@link triggerModel.Trigger#isExtendUDEvent <em>Extend UD Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extend UD Event</em>'.
	 * @see triggerModel.Trigger#isExtendUDEvent()
	 * @see #getTrigger()
	 * @generated
	 */
	EAttribute getTrigger_ExtendUDEvent();

	/**
	 * Returns the meta object for the attribute '{@link triggerModel.Trigger#getExtendedEvent <em>Extended Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extended Event</em>'.
	 * @see triggerModel.Trigger#getExtendedEvent()
	 * @see #getTrigger()
	 * @generated
	 */
	EAttribute getTrigger_ExtendedEvent();

	/**
	 * Returns the meta object for the attribute list '{@link triggerModel.Trigger#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see triggerModel.Trigger#getParameters()
	 * @see #getTrigger()
	 * @generated
	 */
	EAttribute getTrigger_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link triggerModel.Trigger#getTriggerName <em>Trigger Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trigger Name</em>'.
	 * @see triggerModel.Trigger#getTriggerName()
	 * @see #getTrigger()
	 * @generated
	 */
	EAttribute getTrigger_TriggerName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TriggerModelFactory getTriggerModelFactory();

} //TriggerModelPackage
