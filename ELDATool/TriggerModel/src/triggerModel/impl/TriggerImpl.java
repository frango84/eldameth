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
package triggerModel.impl;

import dscDiagramModel.myDataTypes.TypeVariable;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import triggerModel.Trigger;
import triggerModel.TriggerList;
import triggerModel.TriggerModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trigger</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link triggerModel.impl.TriggerImpl#getTriggerList <em>Trigger List</em>}</li>
 *   <li>{@link triggerModel.impl.TriggerImpl#isExtendUDEvent <em>Extend UD Event</em>}</li>
 *   <li>{@link triggerModel.impl.TriggerImpl#getExtendedEvent <em>Extended Event</em>}</li>
 *   <li>{@link triggerModel.impl.TriggerImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link triggerModel.impl.TriggerImpl#getTriggerName <em>Trigger Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TriggerImpl extends EObjectImpl implements Trigger {
	/**
	 * The default value of the '{@link #isExtendUDEvent() <em>Extend UD Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtendUDEvent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTEND_UD_EVENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExtendUDEvent() <em>Extend UD Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtendUDEvent()
	 * @generated
	 * @ordered
	 */
	protected boolean extendUDEvent = EXTEND_UD_EVENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getExtendedEvent() <em>Extended Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedEvent()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENDED_EVENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtendedEvent() <em>Extended Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedEvent()
	 * @generated
	 * @ordered
	 */
	protected String extendedEvent = EXTENDED_EVENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList parameters = null;

	/**
	 * The default value of the '{@link #getTriggerName() <em>Trigger Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTriggerName()
	 * @generated
	 * @ordered
	 */
	protected static final String TRIGGER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTriggerName() <em>Trigger Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTriggerName()
	 * @generated
	 * @ordered
	 */
	protected String triggerName = TRIGGER_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TriggerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return TriggerModelPackage.eINSTANCE.getTrigger();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TriggerList getTriggerList() {
		if (eContainerFeatureID != TriggerModelPackage.TRIGGER__TRIGGER_LIST) return null;
		return (TriggerList)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTriggerList(TriggerList newTriggerList) {
		if (newTriggerList != eContainer || (eContainerFeatureID != TriggerModelPackage.TRIGGER__TRIGGER_LIST && newTriggerList != null)) {
			if (EcoreUtil.isAncestor(this, newTriggerList))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newTriggerList != null)
				msgs = ((InternalEObject)newTriggerList).eInverseAdd(this, TriggerModelPackage.TRIGGER_LIST__TRIGGER, TriggerList.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newTriggerList, TriggerModelPackage.TRIGGER__TRIGGER_LIST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TriggerModelPackage.TRIGGER__TRIGGER_LIST, newTriggerList, newTriggerList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isExtendUDEvent() {
		return extendUDEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendUDEvent(boolean newExtendUDEvent) {
		boolean oldExtendUDEvent = extendUDEvent;
		extendUDEvent = newExtendUDEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TriggerModelPackage.TRIGGER__EXTEND_UD_EVENT, oldExtendUDEvent, extendUDEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtendedEvent() {
		return extendedEvent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedEvent(String newExtendedEvent) {
		String oldExtendedEvent = extendedEvent;
		extendedEvent = newExtendedEvent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TriggerModelPackage.TRIGGER__EXTENDED_EVENT, oldExtendedEvent, extendedEvent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParameters() {
		if (parameters == null) {
			parameters = new EDataTypeUniqueEList(TypeVariable.class, this, TriggerModelPackage.TRIGGER__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTriggerName(String newTriggerName) {
		String oldTriggerName = triggerName;
		triggerName = newTriggerName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TriggerModelPackage.TRIGGER__TRIGGER_NAME, oldTriggerName, triggerName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, TriggerModelPackage.TRIGGER__TRIGGER_LIST, msgs);
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
				case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
					return eBasicSetContainer(null, TriggerModelPackage.TRIGGER__TRIGGER_LIST, msgs);
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
				case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
					return eContainer.eInverseRemove(this, TriggerModelPackage.TRIGGER_LIST__TRIGGER, TriggerList.class, msgs);
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
			case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
				return getTriggerList();
			case TriggerModelPackage.TRIGGER__EXTEND_UD_EVENT:
				return isExtendUDEvent() ? Boolean.TRUE : Boolean.FALSE;
			case TriggerModelPackage.TRIGGER__EXTENDED_EVENT:
				return getExtendedEvent();
			case TriggerModelPackage.TRIGGER__PARAMETERS:
				return getParameters();
			case TriggerModelPackage.TRIGGER__TRIGGER_NAME:
				return getTriggerName();
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
			case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
				setTriggerList((TriggerList)newValue);
				return;
			case TriggerModelPackage.TRIGGER__EXTEND_UD_EVENT:
				setExtendUDEvent(((Boolean)newValue).booleanValue());
				return;
			case TriggerModelPackage.TRIGGER__EXTENDED_EVENT:
				setExtendedEvent((String)newValue);
				return;
			case TriggerModelPackage.TRIGGER__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection)newValue);
				return;
			case TriggerModelPackage.TRIGGER__TRIGGER_NAME:
				setTriggerName((String)newValue);
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
			case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
				setTriggerList((TriggerList)null);
				return;
			case TriggerModelPackage.TRIGGER__EXTEND_UD_EVENT:
				setExtendUDEvent(EXTEND_UD_EVENT_EDEFAULT);
				return;
			case TriggerModelPackage.TRIGGER__EXTENDED_EVENT:
				setExtendedEvent(EXTENDED_EVENT_EDEFAULT);
				return;
			case TriggerModelPackage.TRIGGER__PARAMETERS:
				getParameters().clear();
				return;
			case TriggerModelPackage.TRIGGER__TRIGGER_NAME:
				setTriggerName(TRIGGER_NAME_EDEFAULT);
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
			case TriggerModelPackage.TRIGGER__TRIGGER_LIST:
				return getTriggerList() != null;
			case TriggerModelPackage.TRIGGER__EXTEND_UD_EVENT:
				return extendUDEvent != EXTEND_UD_EVENT_EDEFAULT;
			case TriggerModelPackage.TRIGGER__EXTENDED_EVENT:
				return EXTENDED_EVENT_EDEFAULT == null ? extendedEvent != null : !EXTENDED_EVENT_EDEFAULT.equals(extendedEvent);
			case TriggerModelPackage.TRIGGER__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case TriggerModelPackage.TRIGGER__TRIGGER_NAME:
				return TRIGGER_NAME_EDEFAULT == null ? triggerName != null : !TRIGGER_NAME_EDEFAULT.equals(triggerName);
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
		result.append(" (extendUDEvent: ");
		result.append(extendUDEvent);
		result.append(", extendedEvent: ");
		result.append(extendedEvent);
		result.append(", parameters: ");
		result.append(parameters);
		result.append(", triggerName: ");
		result.append(triggerName);
		result.append(')');
		return result.toString();
	}

} //TriggerImpl
