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
package dscDiagramModel;

import genericUMLDiagramModel.Relationship;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link dscDiagramModel.Transition#getTransitionID <em>Transition ID</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#getEventID <em>Event ID</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#getGuardID <em>Guard ID</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#getActionID <em>Action ID</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#isShowProperties <em>Show Properties</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#isShowTransitionID <em>Show Transition ID</em>}</li>
 *   <li>{@link dscDiagramModel.Transition#isTriggeredByEvent <em>Triggered By Event</em>}</li>
 * </ul>
 * </p>
 *
 * @see dscDiagramModel.DscDiagramModelPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends Relationship{
	/**
	 * Returns the value of the '<em><b>Transition ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transition ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transition ID</em>' attribute.
	 * @see #setTransitionID(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_TransitionID()
	 * @model
	 * @generated
	 */
	String getTransitionID();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#getTransitionID <em>Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transition ID</em>' attribute.
	 * @see #getTransitionID()
	 * @generated
	 */
	void setTransitionID(String value);

	/**
	 * Returns the value of the '<em><b>Event ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event ID</em>' attribute.
	 * @see #setEventID(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_EventID()
	 * @model
	 * @generated
	 */
	String getEventID();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#getEventID <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event ID</em>' attribute.
	 * @see #getEventID()
	 * @generated
	 */
	void setEventID(String value);

	/**
	 * Returns the value of the '<em><b>Guard ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard ID</em>' attribute.
	 * @see #setGuardID(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_GuardID()
	 * @model
	 * @generated
	 */
	String getGuardID();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#getGuardID <em>Guard ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard ID</em>' attribute.
	 * @see #getGuardID()
	 * @generated
	 */
	void setGuardID(String value);

	/**
	 * Returns the value of the '<em><b>Action ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action ID</em>' attribute.
	 * @see #setActionID(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_ActionID()
	 * @model
	 * @generated
	 */
	String getActionID();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#getActionID <em>Action ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action ID</em>' attribute.
	 * @see #getActionID()
	 * @generated
	 */
	void setActionID(String value);

	/**
	 * Returns the value of the '<em><b>Show Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Properties</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Properties</em>' attribute.
	 * @see #setShowProperties(boolean)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_ShowProperties()
	 * @model
	 * @generated
	 */
	boolean isShowProperties();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#isShowProperties <em>Show Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Properties</em>' attribute.
	 * @see #isShowProperties()
	 * @generated
	 */
	void setShowProperties(boolean value);

	/**
	 * Returns the value of the '<em><b>Show Transition ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Show Transition ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Show Transition ID</em>' attribute.
	 * @see #setShowTransitionID(boolean)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_ShowTransitionID()
	 * @model
	 * @generated
	 */
	boolean isShowTransitionID();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#isShowTransitionID <em>Show Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show Transition ID</em>' attribute.
	 * @see #isShowTransitionID()
	 * @generated
	 */
	void setShowTransitionID(boolean value);

	/**
	 * Returns the value of the '<em><b>Triggered By Event</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Triggered By Event</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Triggered By Event</em>' attribute.
	 * @see #setTriggeredByEvent(boolean)
	 * @see dscDiagramModel.DscDiagramModelPackage#getTransition_TriggeredByEvent()
	 * @model default="true"
	 * @generated
	 */
	boolean isTriggeredByEvent();

	/**
	 * Sets the value of the '{@link dscDiagramModel.Transition#isTriggeredByEvent <em>Triggered By Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Triggered By Event</em>' attribute.
	 * @see #isTriggeredByEvent()
	 * @generated
	 */
	void setTriggeredByEvent(boolean value);

} // Transition
