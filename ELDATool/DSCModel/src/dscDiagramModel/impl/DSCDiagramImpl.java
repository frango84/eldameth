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

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DscDiagramModelPackage;

import dscDiagramModel.myDataTypes.TypeDiagramVariable;

import genericUMLDiagramModel.impl.GenericDiagramImpl;

import java.util.Collection;

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
 * An implementation of the model object '<em><b>DSC Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link dscDiagramModel.impl.DSCDiagramImpl#getEventFile <em>Event File</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCDiagramImpl#getGuardFile <em>Guard File</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCDiagramImpl#getActionFile <em>Action File</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCDiagramImpl#getDiagramVariables <em>Diagram Variables</em>}</li>
 *   <li>{@link dscDiagramModel.impl.DSCDiagramImpl#getFunctionFile <em>Function File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DSCDiagramImpl extends GenericDiagramImpl implements DSCDiagram {
	/**
	 * The default value of the '{@link #getEventFile() <em>Event File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventFile()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventFile() <em>Event File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventFile()
	 * @generated
	 * @ordered
	 */
	protected String eventFile = EVENT_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuardFile() <em>Guard File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardFile()
	 * @generated
	 * @ordered
	 */
	protected static final String GUARD_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuardFile() <em>Guard File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuardFile()
	 * @generated
	 * @ordered
	 */
	protected String guardFile = GUARD_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getActionFile() <em>Action File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionFile()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActionFile() <em>Action File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionFile()
	 * @generated
	 * @ordered
	 */
	protected String actionFile = ACTION_FILE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDiagramVariables() <em>Diagram Variables</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDiagramVariables()
	 * @generated
	 * @ordered
	 */
	protected EList diagramVariables = null;

	/**
	 * The default value of the '{@link #getFunctionFile() <em>Function File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FUNCTION_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunctionFile() <em>Function File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionFile()
	 * @generated
	 * @ordered
	 */
	protected String functionFile = FUNCTION_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DSCDiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DscDiagramModelPackage.eINSTANCE.getDSCDiagram();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventFile() {
		return eventFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventFile(String newEventFile) {
		String oldEventFile = eventFile;
		eventFile = newEventFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_DIAGRAM__EVENT_FILE, oldEventFile, eventFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGuardFile() {
		return guardFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuardFile(String newGuardFile) {
		String oldGuardFile = guardFile;
		guardFile = newGuardFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_DIAGRAM__GUARD_FILE, oldGuardFile, guardFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getActionFile() {
		return actionFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionFile(String newActionFile) {
		String oldActionFile = actionFile;
		actionFile = newActionFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_DIAGRAM__ACTION_FILE, oldActionFile, actionFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getDiagramVariables() {
		if (diagramVariables == null) {
			diagramVariables = new EDataTypeUniqueEList(TypeDiagramVariable.class, this, DscDiagramModelPackage.DSC_DIAGRAM__DIAGRAM_VARIABLES);
		}
		return diagramVariables;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunctionFile() {
		return functionFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionFile(String newFunctionFile) {
		String oldFunctionFile = functionFile;
		functionFile = newFunctionFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DscDiagramModelPackage.DSC_DIAGRAM__FUNCTION_FILE, oldFunctionFile, functionFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
					return ((InternalEList)getModelElements()).basicAdd(otherEnd, msgs);
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
				case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
					return ((InternalEList)getModelElements()).basicRemove(otherEnd, msgs);
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
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
				return getModelElements();
			case DscDiagramModelPackage.DSC_DIAGRAM__MAX_ID:
				return new Long(getMaxID());
			case DscDiagramModelPackage.DSC_DIAGRAM__EVENT_FILE:
				return getEventFile();
			case DscDiagramModelPackage.DSC_DIAGRAM__GUARD_FILE:
				return getGuardFile();
			case DscDiagramModelPackage.DSC_DIAGRAM__ACTION_FILE:
				return getActionFile();
			case DscDiagramModelPackage.DSC_DIAGRAM__DIAGRAM_VARIABLES:
				return getDiagramVariables();
			case DscDiagramModelPackage.DSC_DIAGRAM__FUNCTION_FILE:
				return getFunctionFile();
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
			case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
				getModelElements().clear();
				getModelElements().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__MAX_ID:
				setMaxID(((Long)newValue).longValue());
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__EVENT_FILE:
				setEventFile((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__GUARD_FILE:
				setGuardFile((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__ACTION_FILE:
				setActionFile((String)newValue);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__DIAGRAM_VARIABLES:
				getDiagramVariables().clear();
				getDiagramVariables().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__FUNCTION_FILE:
				setFunctionFile((String)newValue);
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
			case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
				getModelElements().clear();
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__MAX_ID:
				setMaxID(MAX_ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__EVENT_FILE:
				setEventFile(EVENT_FILE_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__GUARD_FILE:
				setGuardFile(GUARD_FILE_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__ACTION_FILE:
				setActionFile(ACTION_FILE_EDEFAULT);
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__DIAGRAM_VARIABLES:
				getDiagramVariables().clear();
				return;
			case DscDiagramModelPackage.DSC_DIAGRAM__FUNCTION_FILE:
				setFunctionFile(FUNCTION_FILE_EDEFAULT);
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
			case DscDiagramModelPackage.DSC_DIAGRAM__MODEL_ELEMENTS:
				return modelElements != null && !modelElements.isEmpty();
			case DscDiagramModelPackage.DSC_DIAGRAM__MAX_ID:
				return maxID != MAX_ID_EDEFAULT;
			case DscDiagramModelPackage.DSC_DIAGRAM__EVENT_FILE:
				return EVENT_FILE_EDEFAULT == null ? eventFile != null : !EVENT_FILE_EDEFAULT.equals(eventFile);
			case DscDiagramModelPackage.DSC_DIAGRAM__GUARD_FILE:
				return GUARD_FILE_EDEFAULT == null ? guardFile != null : !GUARD_FILE_EDEFAULT.equals(guardFile);
			case DscDiagramModelPackage.DSC_DIAGRAM__ACTION_FILE:
				return ACTION_FILE_EDEFAULT == null ? actionFile != null : !ACTION_FILE_EDEFAULT.equals(actionFile);
			case DscDiagramModelPackage.DSC_DIAGRAM__DIAGRAM_VARIABLES:
				return diagramVariables != null && !diagramVariables.isEmpty();
			case DscDiagramModelPackage.DSC_DIAGRAM__FUNCTION_FILE:
				return FUNCTION_FILE_EDEFAULT == null ? functionFile != null : !FUNCTION_FILE_EDEFAULT.equals(functionFile);
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
		result.append(" (eventFile: ");
		result.append(eventFile);
		result.append(", guardFile: ");
		result.append(guardFile);
		result.append(", actionFile: ");
		result.append(actionFile);
		result.append(", diagramVariables: ");
		result.append(diagramVariables);
		result.append(", functionFile: ");
		result.append(functionFile);
		result.append(')');
		return result.toString();
	}

} //DSCDiagramImpl
