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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link triggerModel.Trigger#getTriggerList <em>Trigger List</em>}</li>
 *   <li>{@link triggerModel.Trigger#isExtendUDEvent <em>Extend UD Event</em>}</li>
 *   <li>{@link triggerModel.Trigger#getExtendedEvent <em>Extended Event</em>}</li>
 *   <li>{@link triggerModel.Trigger#getParameters <em>Parameters</em>}</li>
 *   <li>{@link triggerModel.Trigger#getTriggerName <em>Trigger Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see triggerModel.TriggerModelPackage#getTrigger()
 * @model
 * @generated
 */
public interface Trigger extends EObject {
	/**
	 * Returns the value of the '<em><b>Trigger List</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link triggerModel.TriggerList#getTrigger <em>Trigger</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trigger List</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trigger List</em>' container reference.
	 * @see #setTriggerList(TriggerList)
	 * @see triggerModel.TriggerModelPackage#getTrigger_TriggerList()
	 * @see triggerModel.TriggerList#getTrigger
	 * @model opposite="trigger" required="true"
	 * @generated
	 */
	TriggerList getTriggerList();

	/**
	 * Sets the value of the '{@link triggerModel.Trigger#getTriggerList <em>Trigger List</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trigger List</em>' container reference.
	 * @see #getTriggerList()
	 * @generated
	 */
	void setTriggerList(TriggerList value);

	/**
	 * Returns the value of the '<em><b>Extend UD Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extend UD Event</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extend UD Event</em>' attribute.
	 * @see #setExtendUDEvent(boolean)
	 * @see triggerModel.TriggerModelPackage#getTrigger_ExtendUDEvent()
	 * @model
	 * @generated
	 */
	boolean isExtendUDEvent();

	/**
	 * Sets the value of the '{@link triggerModel.Trigger#isExtendUDEvent <em>Extend UD Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extend UD Event</em>' attribute.
	 * @see #isExtendUDEvent()
	 * @generated
	 */
	void setExtendUDEvent(boolean value);

	/**
	 * Returns the value of the '<em><b>Extended Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Event</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Event</em>' attribute.
	 * @see #setExtendedEvent(String)
	 * @see triggerModel.TriggerModelPackage#getTrigger_ExtendedEvent()
	 * @model
	 * @generated
	 */
	String getExtendedEvent();

	/**
	 * Sets the value of the '{@link triggerModel.Trigger#getExtendedEvent <em>Extended Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Event</em>' attribute.
	 * @see #getExtendedEvent()
	 * @generated
	 */
	void setExtendedEvent(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute list.
	 * The list contents are of type {@link dscDiagramModel.myDataTypes.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute list.
	 * @see triggerModel.TriggerModelPackage#getTrigger_Parameters()
	 * @model type="dscDiagramModel.myDataTypes.TypeVariable" dataType="dscDiagramModel.Variable"
	 * @generated
	 */
	EList getParameters();

	/**
	 * Returns the value of the '<em><b>Trigger Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trigger Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trigger Name</em>' attribute.
	 * @see #setTriggerName(String)
	 * @see triggerModel.TriggerModelPackage#getTrigger_TriggerName()
	 * @model
	 * @generated
	 */
	String getTriggerName();

	/**
	 * Sets the value of the '{@link triggerModel.Trigger#getTriggerName <em>Trigger Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trigger Name</em>' attribute.
	 * @see #getTriggerName()
	 * @generated
	 */
	void setTriggerName(String value);

} // Trigger
