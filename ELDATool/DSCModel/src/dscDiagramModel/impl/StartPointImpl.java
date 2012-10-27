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

import dscDiagramModel.DSCState;
import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.StartPoint;

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;

import genericUMLDiagramModel.impl.ClassifierImpl;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Start Point</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link dscDiagramModel.impl.StartPointImpl#getCompositeState <em>Composite State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StartPointImpl extends ClassifierImpl implements StartPoint {
	/**
	 * The cached value of the '{@link #getCompositeState() <em>Composite State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeState()
	 * @generated
	 * @ordered
	 */
	protected DSCState compositeState = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StartPointImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DscDiagramModelPackage.eINSTANCE.getStartPoint();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DSCState getCompositeState() {
		if (compositeState != null && compositeState.eIsProxy()) {
			DSCState oldCompositeState = compositeState;
			compositeState = (DSCState)eResolveProxy((InternalEObject)compositeState);
			if (compositeState != oldCompositeState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, oldCompositeState, compositeState));
			}
		}
		return compositeState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DSCState basicGetCompositeState() {
		return compositeState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeState(DSCState newCompositeState, NotificationChain msgs) {
		DSCState oldCompositeState = compositeState;
		compositeState = newCompositeState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, oldCompositeState, newCompositeState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeState(DSCState newCompositeState) {
		if (newCompositeState != compositeState) {
			NotificationChain msgs = null;
			if (compositeState != null)
				msgs = ((InternalEObject)compositeState).eInverseRemove(this, DscDiagramModelPackage.DSC_STATE__START_POINT, DSCState.class, msgs);
			if (newCompositeState != null)
				msgs = ((InternalEObject)newCompositeState).eInverseAdd(this, DscDiagramModelPackage.DSC_STATE__START_POINT, DSCState.class, msgs);
			msgs = basicSetCompositeState(newCompositeState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, newCompositeState, newCompositeState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.START_POINT__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.START_POINT__PARENT, msgs);
				case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.START_POINT__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.START_POINT__CONTAINER, msgs);
				case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
					if (compositeState != null)
						msgs = ((InternalEObject)compositeState).eInverseRemove(this, DscDiagramModelPackage.DSC_STATE__START_POINT, DSCState.class, msgs);
					return basicSetCompositeState((DSCState)otherEnd, msgs);
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
				case DscDiagramModelPackage.START_POINT__PARENT:
					return eBasicSetContainer(null, DscDiagramModelPackage.START_POINT__PARENT, msgs);
				case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.START_POINT__CONTAINER:
					return eBasicSetContainer(null, DscDiagramModelPackage.START_POINT__CONTAINER, msgs);
				case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
					return basicSetCompositeState(null, msgs);
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
				case DscDiagramModelPackage.START_POINT__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				case DscDiagramModelPackage.START_POINT__CONTAINER:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS, Container.class, msgs);
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
			case DscDiagramModelPackage.START_POINT__NAME:
				return getName();
			case DscDiagramModelPackage.START_POINT__DESCRIPTION:
				return getDescription();
			case DscDiagramModelPackage.START_POINT__PARENT:
				return getParent();
			case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case DscDiagramModelPackage.START_POINT__STEREOTYPE:
				return getStereotype();
			case DscDiagramModelPackage.START_POINT__ID:
				return getID();
			case DscDiagramModelPackage.START_POINT__LOCATION:
				return getLocation();
			case DscDiagramModelPackage.START_POINT__SIZE:
				return getSize();
			case DscDiagramModelPackage.START_POINT__CONTAINER:
				return getContainer();
			case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
				if (resolve) return getCompositeState();
				return basicGetCompositeState();
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
			case DscDiagramModelPackage.START_POINT__NAME:
				setName((String)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__ID:
				setID((String)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__LOCATION:
				setLocation((Point)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__SIZE:
				setSize((Dimension)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__CONTAINER:
				setContainer((Container)newValue);
				return;
			case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
				setCompositeState((DSCState)newValue);
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
			case DscDiagramModelPackage.START_POINT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__PARENT:
				setParent((GenericDiagram)null);
				return;
			case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case DscDiagramModelPackage.START_POINT__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__ID:
				setID(ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case DscDiagramModelPackage.START_POINT__CONTAINER:
				setContainer((Container)null);
				return;
			case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
				setCompositeState((DSCState)null);
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
			case DscDiagramModelPackage.START_POINT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DscDiagramModelPackage.START_POINT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case DscDiagramModelPackage.START_POINT__PARENT:
				return getParent() != null;
			case DscDiagramModelPackage.START_POINT__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case DscDiagramModelPackage.START_POINT__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case DscDiagramModelPackage.START_POINT__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case DscDiagramModelPackage.START_POINT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DscDiagramModelPackage.START_POINT__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case DscDiagramModelPackage.START_POINT__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case DscDiagramModelPackage.START_POINT__CONTAINER:
				return getContainer() != null;
			case DscDiagramModelPackage.START_POINT__COMPOSITE_STATE:
				return compositeState != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //StartPointImpl
