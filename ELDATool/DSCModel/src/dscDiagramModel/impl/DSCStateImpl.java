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
import dscDiagramModel.DeepHistory;
import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;

import dscDiagramModel.myDataTypes.TypeVariable;

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;

import genericUMLDiagramModel.impl.ContainerImpl;

import java.util.Collection;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>DSC State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link dscDiagramModel.impl.DSCStateImpl#getStartPoint <em>Start Point</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCStateImpl#getShallowHistory <em>Shallow History</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCStateImpl#getDeepHistory <em>Deep History</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCStateImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCStateImpl#isIsSimple <em>Is Simple</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DSCStateImpl extends ContainerImpl implements DSCState {
	/**
	 * The cached value of the '{@link #getStartPoint() <em>Start Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPoint()
	 * @generated
	 * @ordered
	 */
	protected StartPoint startPoint = null;

	/**
	 * The cached value of the '{@link #getShallowHistory() <em>Shallow History</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShallowHistory()
	 * @generated
	 * @ordered
	 */
	protected ShallowHistory shallowHistory = null;

	/**
	 * The cached value of the '{@link #getDeepHistory() <em>Deep History</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeepHistory()
	 * @generated
	 * @ordered
	 */
	protected DeepHistory deepHistory = null;

	/**
	 * The cached value of the '{@link #getVariables() <em>Variables</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVariables()
	 * @generated
	 * @ordered
	 */
	protected EList variables = null;

	/**
	 * The default value of the '{@link #isIsSimple() <em>Is Simple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSimple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_SIMPLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsSimple() <em>Is Simple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsSimple()
	 * @generated
	 * @ordered
	 */
	protected boolean isSimple = IS_SIMPLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DSCStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DscDiagramModelPackage.eINSTANCE.getDSCState();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPoint getStartPoint() {
		if (startPoint != null && startPoint.eIsProxy()) {
			StartPoint oldStartPoint = startPoint;
			startPoint = (StartPoint)eResolveProxy((InternalEObject)startPoint);
			if (startPoint != oldStartPoint) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DscDiagramModelPackage.DSC_STATE__START_POINT, oldStartPoint, startPoint));
			}
		}
		return startPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPoint basicGetStartPoint() {
		return startPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStartPoint(StartPoint newStartPoint, NotificationChain msgs) {
		StartPoint oldStartPoint = startPoint;
		startPoint = newStartPoint;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__START_POINT, oldStartPoint, newStartPoint);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPoint(StartPoint newStartPoint) {
		if (newStartPoint != startPoint) {
			NotificationChain msgs = null;
			if (startPoint != null)
				msgs = ((InternalEObject)startPoint).eInverseRemove(this, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, StartPoint.class, msgs);
			if (newStartPoint != null)
				msgs = ((InternalEObject)newStartPoint).eInverseAdd(this, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, StartPoint.class, msgs);
			msgs = basicSetStartPoint(newStartPoint, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__START_POINT, newStartPoint, newStartPoint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShallowHistory getShallowHistory() {
		if (shallowHistory != null && shallowHistory.eIsProxy()) {
			ShallowHistory oldShallowHistory = shallowHistory;
			shallowHistory = (ShallowHistory)eResolveProxy((InternalEObject)shallowHistory);
			if (shallowHistory != oldShallowHistory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY, oldShallowHistory, shallowHistory));
			}
		}
		return shallowHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShallowHistory basicGetShallowHistory() {
		return shallowHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetShallowHistory(ShallowHistory newShallowHistory, NotificationChain msgs) {
		ShallowHistory oldShallowHistory = shallowHistory;
		shallowHistory = newShallowHistory;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY, oldShallowHistory, newShallowHistory);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShallowHistory(ShallowHistory newShallowHistory) {
		if (newShallowHistory != shallowHistory) {
			NotificationChain msgs = null;
			if (shallowHistory != null)
				msgs = ((InternalEObject)shallowHistory).eInverseRemove(this, DscDiagramModelPackage.SHALLOW_HISTORY__COMPOSITE_STATE, ShallowHistory.class, msgs);
			if (newShallowHistory != null)
				msgs = ((InternalEObject)newShallowHistory).eInverseAdd(this, DscDiagramModelPackage.SHALLOW_HISTORY__COMPOSITE_STATE, ShallowHistory.class, msgs);
			msgs = basicSetShallowHistory(newShallowHistory, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY, newShallowHistory, newShallowHistory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeepHistory getDeepHistory() {
		if (deepHistory != null && deepHistory.eIsProxy()) {
			DeepHistory oldDeepHistory = deepHistory;
			deepHistory = (DeepHistory)eResolveProxy((InternalEObject)deepHistory);
			if (deepHistory != oldDeepHistory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY, oldDeepHistory, deepHistory));
			}
		}
		return deepHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeepHistory basicGetDeepHistory() {
		return deepHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeepHistory(DeepHistory newDeepHistory, NotificationChain msgs) {
		DeepHistory oldDeepHistory = deepHistory;
		deepHistory = newDeepHistory;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY, oldDeepHistory, newDeepHistory);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeepHistory(DeepHistory newDeepHistory) {
		if (newDeepHistory != deepHistory) {
			NotificationChain msgs = null;
			if (deepHistory != null)
				msgs = ((InternalEObject)deepHistory).eInverseRemove(this, DscDiagramModelPackage.DEEP_HISTORY__COMPOSITE_STATE, DeepHistory.class, msgs);
			if (newDeepHistory != null)
				msgs = ((InternalEObject)newDeepHistory).eInverseAdd(this, DscDiagramModelPackage.DEEP_HISTORY__COMPOSITE_STATE, DeepHistory.class, msgs);
			msgs = basicSetDeepHistory(newDeepHistory, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY, newDeepHistory, newDeepHistory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getVariables() {
		if (variables == null) {
			variables = new EDataTypeUniqueEList(TypeVariable.class, this, DscDiagramModelPackage.DSC_STATE__VARIABLES);
		}
		return variables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsSimple() {
		return isSimple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsSimple(boolean newIsSimple) {
		boolean oldIsSimple = isSimple;
		isSimple = newIsSimple;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_STATE__IS_SIMPLE, oldIsSimple, isSimple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.DSC_STATE__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.DSC_STATE__PARENT, msgs);
				case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.DSC_STATE__CONTAINER, msgs);
				case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
					return ((InternalEList)getVisualElements()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__START_POINT:
					if (startPoint != null)
						msgs = ((InternalEObject)startPoint).eInverseRemove(this, DscDiagramModelPackage.START_POINT__COMPOSITE_STATE, StartPoint.class, msgs);
					return basicSetStartPoint((StartPoint)otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
					if (shallowHistory != null)
						msgs = ((InternalEObject)shallowHistory).eInverseRemove(this, DscDiagramModelPackage.SHALLOW_HISTORY__COMPOSITE_STATE, ShallowHistory.class, msgs);
					return basicSetShallowHistory((ShallowHistory)otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
					if (deepHistory != null)
						msgs = ((InternalEObject)deepHistory).eInverseRemove(this, DscDiagramModelPackage.DEEP_HISTORY__COMPOSITE_STATE, DeepHistory.class, msgs);
					return basicSetDeepHistory((DeepHistory)otherEnd, msgs);
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
				case DscDiagramModelPackage.DSC_STATE__PARENT:
					return eBasicSetContainer(null, DscDiagramModelPackage.DSC_STATE__PARENT, msgs);
				case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__CONTAINER:
					return eBasicSetContainer(null, DscDiagramModelPackage.DSC_STATE__CONTAINER, msgs);
				case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
					return ((InternalEList)getVisualElements()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.DSC_STATE__START_POINT:
					return basicSetStartPoint(null, msgs);
				case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
					return basicSetShallowHistory(null, msgs);
				case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
					return basicSetDeepHistory(null, msgs);
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
				case DscDiagramModelPackage.DSC_STATE__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				case DscDiagramModelPackage.DSC_STATE__CONTAINER:
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
			case DscDiagramModelPackage.DSC_STATE__NAME:
				return getName();
			case DscDiagramModelPackage.DSC_STATE__DESCRIPTION:
				return getDescription();
			case DscDiagramModelPackage.DSC_STATE__PARENT:
				return getParent();
			case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case DscDiagramModelPackage.DSC_STATE__STEREOTYPE:
				return getStereotype();
			case DscDiagramModelPackage.DSC_STATE__ID:
				return getID();
			case DscDiagramModelPackage.DSC_STATE__LOCATION:
				return getLocation();
			case DscDiagramModelPackage.DSC_STATE__SIZE:
				return getSize();
			case DscDiagramModelPackage.DSC_STATE__CONTAINER:
				return getContainer();
			case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
				return getVisualElements();
			case DscDiagramModelPackage.DSC_STATE__START_POINT:
				if (resolve) return getStartPoint();
				return basicGetStartPoint();
			case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
				if (resolve) return getShallowHistory();
				return basicGetShallowHistory();
			case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
				if (resolve) return getDeepHistory();
				return basicGetDeepHistory();
			case DscDiagramModelPackage.DSC_STATE__VARIABLES:
				return getVariables();
			case DscDiagramModelPackage.DSC_STATE__IS_SIMPLE:
				return isIsSimple() ? Boolean.TRUE : Boolean.FALSE;
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
			case DscDiagramModelPackage.DSC_STATE__NAME:
				setName((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__ID:
				setID((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__LOCATION:
				setLocation((Point)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__SIZE:
				setSize((Dimension)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__CONTAINER:
				setContainer((Container)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
				getVisualElements().clear();
				getVisualElements().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__START_POINT:
				setStartPoint((StartPoint)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
				setShallowHistory((ShallowHistory)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
				setDeepHistory((DeepHistory)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__VARIABLES:
				getVariables().clear();
				getVariables().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_STATE__IS_SIMPLE:
				setIsSimple(((Boolean)newValue).booleanValue());
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
			case DscDiagramModelPackage.DSC_STATE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__PARENT:
				setParent((GenericDiagram)null);
				return;
			case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case DscDiagramModelPackage.DSC_STATE__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__ID:
				setID(ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_STATE__CONTAINER:
				setContainer((Container)null);
				return;
			case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
				getVisualElements().clear();
				return;
			case DscDiagramModelPackage.DSC_STATE__START_POINT:
				setStartPoint((StartPoint)null);
				return;
			case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
				setShallowHistory((ShallowHistory)null);
				return;
			case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
				setDeepHistory((DeepHistory)null);
				return;
			case DscDiagramModelPackage.DSC_STATE__VARIABLES:
				getVariables().clear();
				return;
			case DscDiagramModelPackage.DSC_STATE__IS_SIMPLE:
				setIsSimple(IS_SIMPLE_EDEFAULT);
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
			case DscDiagramModelPackage.DSC_STATE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DscDiagramModelPackage.DSC_STATE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case DscDiagramModelPackage.DSC_STATE__PARENT:
				return getParent() != null;
			case DscDiagramModelPackage.DSC_STATE__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case DscDiagramModelPackage.DSC_STATE__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case DscDiagramModelPackage.DSC_STATE__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case DscDiagramModelPackage.DSC_STATE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DscDiagramModelPackage.DSC_STATE__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case DscDiagramModelPackage.DSC_STATE__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case DscDiagramModelPackage.DSC_STATE__CONTAINER:
				return getContainer() != null;
			case DscDiagramModelPackage.DSC_STATE__VISUAL_ELEMENTS:
				return visualElements != null && !visualElements.isEmpty();
			case DscDiagramModelPackage.DSC_STATE__START_POINT:
				return startPoint != null;
			case DscDiagramModelPackage.DSC_STATE__SHALLOW_HISTORY:
				return shallowHistory != null;
			case DscDiagramModelPackage.DSC_STATE__DEEP_HISTORY:
				return deepHistory != null;
			case DscDiagramModelPackage.DSC_STATE__VARIABLES:
				return variables != null && !variables.isEmpty();
			case DscDiagramModelPackage.DSC_STATE__IS_SIMPLE:
				return isSimple != IS_SIMPLE_EDEFAULT;
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
		result.append(" (Variables: ");
		result.append(variables);
		result.append(", isSimple: ");
		result.append(isSimple);
		result.append(')');
		return result.toString();
	}

} //DSCStateImpl
