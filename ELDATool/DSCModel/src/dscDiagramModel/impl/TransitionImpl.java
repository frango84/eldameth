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
package dscDiagramModel.impl;

import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.Transition;

import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.ModelElement;

import genericUMLDiagramModel.impl.RelationshipImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#getTransitionID <em>Transition ID</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#getEventID <em>Event ID</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#getGuardID <em>Guard ID</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#getActionID <em>Action ID</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#isShowProperties <em>Show Properties</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#isShowTransitionID <em>Show Transition ID</em>}</li>
 *   <li>{@link dscDiagramModel.impl.TransitionImpl#isTriggeredByEvent <em>Triggered By Event</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends RelationshipImpl implements Transition {
	/**
	 * The default value of the '{@link #getTransitionID() <em>Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionID()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSITION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransitionID() <em>Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitionID()
	 * @generated
	 * @ordered
	 */
	protected String transitionID = TRANSITION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getEventID() <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventID()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventID() <em>Event ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventID()
	 * @generated
	 * @ordered
	 */
	protected String eventID = EVENT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuardID() <em>Guard ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardID()
	 * @generated
	 * @ordered
	 */
	protected static final String GUARD_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuardID() <em>Guard ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardID()
	 * @generated
	 * @ordered
	 */
	protected String guardID = GUARD_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getActionID() <em>Action ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionID()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActionID() <em>Action ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionID()
	 * @generated
	 * @ordered
	 */
	protected String actionID = ACTION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowProperties() <em>Show Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowProperties()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_PROPERTIES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowProperties() <em>Show Properties</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowProperties()
	 * @generated
	 * @ordered
	 */
	protected boolean showProperties = SHOW_PROPERTIES_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowTransitionID() <em>Show Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowTransitionID()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_TRANSITION_ID_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowTransitionID() <em>Show Transition ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowTransitionID()
	 * @generated
	 * @ordered
	 */
	protected boolean showTransitionID = SHOW_TRANSITION_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isTriggeredByEvent() <em>Triggered By Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTriggeredByEvent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRIGGERED_BY_EVENT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isTriggeredByEvent() <em>Triggered By Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTriggeredByEvent()
	 * @generated
	 * @ordered
	 */
	protected boolean triggeredByEvent = TRIGGERED_BY_EVENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DscDiagramModelPackage.eINSTANCE.getTransition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTransitionID() {
		return transitionID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitionID(String newTransitionID) {
		String oldTransitionID = transitionID;
		transitionID = newTransitionID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__TRANSITION_ID, oldTransitionID, transitionID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventID() {
		return eventID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventID(String newEventID) {
		String oldEventID = eventID;
		eventID = newEventID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__EVENT_ID, oldEventID, eventID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGuardID() {
		return guardID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuardID(String newGuardID) {
		String oldGuardID = guardID;
		guardID = newGuardID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__GUARD_ID, oldGuardID, guardID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getActionID() {
		return actionID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionID(String newActionID) {
		String oldActionID = actionID;
		actionID = newActionID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__ACTION_ID, oldActionID, actionID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowProperties() {
		return showProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowProperties(boolean newShowProperties) {
		boolean oldShowProperties = showProperties;
		showProperties = newShowProperties;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES, oldShowProperties, showProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowTransitionID() {
		return showTransitionID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowTransitionID(boolean newShowTransitionID) {
		boolean oldShowTransitionID = showTransitionID;
		showTransitionID = newShowTransitionID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID, oldShowTransitionID, showTransitionID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTriggeredByEvent() {
		return triggeredByEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTriggeredByEvent(boolean newTriggeredByEvent) {
		boolean oldTriggeredByEvent = triggeredByEvent;
		triggeredByEvent = newTriggeredByEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT, oldTriggeredByEvent, triggeredByEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.TRANSITION__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.TRANSITION__PARENT, msgs);
				case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__SOURCE:
					if (source != null)
						msgs = ((InternalEObject)source).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__SOURCE_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetSource((ModelElement)otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__TARGET_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetTarget((ModelElement)otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
					if (connectionLabel != null)
						msgs = ((InternalEObject)connectionLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL, null, msgs);
					return basicSetConnectionLabel((ConnectionLabel)otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.TRANSITION__PARENT:
					return eBasicSetContainer(null, DscDiagramModelPackage.TRANSITION__PARENT, msgs);
				case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.TRANSITION__SOURCE:
					return basicSetSource(null, msgs);
				case DscDiagramModelPackage.TRANSITION__TARGET:
					return basicSetTarget(null, msgs);
				case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
					return basicSetConnectionLabel(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case DscDiagramModelPackage.TRANSITION__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DscDiagramModelPackage.TRANSITION__NAME:
				return getName();
			case DscDiagramModelPackage.TRANSITION__DESCRIPTION:
				return getDescription();
			case DscDiagramModelPackage.TRANSITION__PARENT:
				return getParent();
			case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case DscDiagramModelPackage.TRANSITION__STEREOTYPE:
				return getStereotype();
			case DscDiagramModelPackage.TRANSITION__ID:
				return getID();
			case DscDiagramModelPackage.TRANSITION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case DscDiagramModelPackage.TRANSITION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case DscDiagramModelPackage.TRANSITION__BENDPOINTS:
				return getBendpoints();
			case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
				return getConnectionLabel();
			case DscDiagramModelPackage.TRANSITION__TRANSITION_ID:
				return getTransitionID();
			case DscDiagramModelPackage.TRANSITION__EVENT_ID:
				return getEventID();
			case DscDiagramModelPackage.TRANSITION__GUARD_ID:
				return getGuardID();
			case DscDiagramModelPackage.TRANSITION__ACTION_ID:
				return getActionID();
			case DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES:
				return isShowProperties() ? Boolean.TRUE : Boolean.FALSE;
			case DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID:
				return isShowTransitionID() ? Boolean.TRUE : Boolean.FALSE;
			case DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT:
				return isTriggeredByEvent() ? Boolean.TRUE : Boolean.FALSE;
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DscDiagramModelPackage.TRANSITION__NAME:
				setName((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__ID:
				setID((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__SOURCE:
				setSource((ModelElement)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__TARGET:
				setTarget((ModelElement)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__BENDPOINTS:
				getBendpoints().clear();
				getBendpoints().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
				setConnectionLabel((ConnectionLabel)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__TRANSITION_ID:
				setTransitionID((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__EVENT_ID:
				setEventID((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__GUARD_ID:
				setGuardID((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__ACTION_ID:
				setActionID((String)newValue);
				return;
			case DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES:
				setShowProperties(((Boolean)newValue).booleanValue());
				return;
			case DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID:
				setShowTransitionID(((Boolean)newValue).booleanValue());
				return;
			case DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT:
				setTriggeredByEvent(((Boolean)newValue).booleanValue());
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DscDiagramModelPackage.TRANSITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__PARENT:
				setParent((GenericDiagram)null);
				return;
			case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case DscDiagramModelPackage.TRANSITION__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__ID:
				setID(ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__SOURCE:
				setSource((ModelElement)null);
				return;
			case DscDiagramModelPackage.TRANSITION__TARGET:
				setTarget((ModelElement)null);
				return;
			case DscDiagramModelPackage.TRANSITION__BENDPOINTS:
				getBendpoints().clear();
				return;
			case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
				setConnectionLabel((ConnectionLabel)null);
				return;
			case DscDiagramModelPackage.TRANSITION__TRANSITION_ID:
				setTransitionID(TRANSITION_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__EVENT_ID:
				setEventID(EVENT_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__GUARD_ID:
				setGuardID(GUARD_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__ACTION_ID:
				setActionID(ACTION_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES:
				setShowProperties(SHOW_PROPERTIES_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID:
				setShowTransitionID(SHOW_TRANSITION_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT:
				setTriggeredByEvent(TRIGGERED_BY_EVENT_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DscDiagramModelPackage.TRANSITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DscDiagramModelPackage.TRANSITION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case DscDiagramModelPackage.TRANSITION__PARENT:
				return getParent() != null;
			case DscDiagramModelPackage.TRANSITION__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case DscDiagramModelPackage.TRANSITION__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case DscDiagramModelPackage.TRANSITION__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case DscDiagramModelPackage.TRANSITION__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DscDiagramModelPackage.TRANSITION__SOURCE:
				return source != null;
			case DscDiagramModelPackage.TRANSITION__TARGET:
				return target != null;
			case DscDiagramModelPackage.TRANSITION__BENDPOINTS:
				return bendpoints != null && !bendpoints.isEmpty();
			case DscDiagramModelPackage.TRANSITION__CONNECTION_LABEL:
				return connectionLabel != null;
			case DscDiagramModelPackage.TRANSITION__TRANSITION_ID:
				return TRANSITION_ID_EDEFAULT == null ? transitionID != null : !TRANSITION_ID_EDEFAULT.equals(transitionID);
			case DscDiagramModelPackage.TRANSITION__EVENT_ID:
				return EVENT_ID_EDEFAULT == null ? eventID != null : !EVENT_ID_EDEFAULT.equals(eventID);
			case DscDiagramModelPackage.TRANSITION__GUARD_ID:
				return GUARD_ID_EDEFAULT == null ? guardID != null : !GUARD_ID_EDEFAULT.equals(guardID);
			case DscDiagramModelPackage.TRANSITION__ACTION_ID:
				return ACTION_ID_EDEFAULT == null ? actionID != null : !ACTION_ID_EDEFAULT.equals(actionID);
			case DscDiagramModelPackage.TRANSITION__SHOW_PROPERTIES:
				return showProperties != SHOW_PROPERTIES_EDEFAULT;
			case DscDiagramModelPackage.TRANSITION__SHOW_TRANSITION_ID:
				return showTransitionID != SHOW_TRANSITION_ID_EDEFAULT;
			case DscDiagramModelPackage.TRANSITION__TRIGGERED_BY_EVENT:
				return triggeredByEvent != TRIGGERED_BY_EVENT_EDEFAULT;
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (transitionID: ");
		result.append(transitionID);
		result.append(", eventID: ");
		result.append(eventID);
		result.append(", guardID: ");
		result.append(guardID);
		result.append(", actionID: ");
		result.append(actionID);
		result.append(", showProperties: ");
		result.append(showProperties);
		result.append(", showTransitionID: ");
		result.append(showTransitionID);
		result.append(", triggeredByEvent: ");
		result.append(triggeredByEvent);
		result.append(')');
		return result.toString();
	}

} //TransitionImpl
