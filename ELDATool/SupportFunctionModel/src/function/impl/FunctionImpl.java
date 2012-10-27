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
package function.impl;

import dscDiagramModel.myDataTypes.TypeVariable;

import function.Function;
import function.FunctionList;
import function.FunctionPackage;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link function.impl.FunctionImpl#getFunctionList <em>Function List</em>}</li>
 *   <li>{@link function.impl.FunctionImpl#getName <em>Name</em>}</li>
 *   <li>{@link function.impl.FunctionImpl#getReturnedType <em>Returned Type</em>}</li>
 *   <li>{@link function.impl.FunctionImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link function.impl.FunctionImpl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionImpl extends EObjectImpl implements Function {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getReturnedType() <em>Returned Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnedType()
	 * @generated
	 * @ordered
	 */
	protected static final String RETURNED_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnedType() <em>Returned Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnedType()
	 * @generated
	 * @ordered
	 */
	protected String returnedType = RETURNED_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList parameter = null;

	/**
	 * The default value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected static final String BODY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
	protected String body = BODY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return FunctionPackage.eINSTANCE.getFunction();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionList getFunctionList() {
		if (eContainerFeatureID != FunctionPackage.FUNCTION__FUNCTION_LIST) return null;
		return (FunctionList)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionList(FunctionList newFunctionList) {
		if (newFunctionList != eContainer || (eContainerFeatureID != FunctionPackage.FUNCTION__FUNCTION_LIST && newFunctionList != null)) {
			if (EcoreUtil.isAncestor(this, newFunctionList))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFunctionList != null)
				msgs = ((InternalEObject)newFunctionList).eInverseAdd(this, FunctionPackage.FUNCTION_LIST__FUNCTION, FunctionList.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newFunctionList, FunctionPackage.FUNCTION__FUNCTION_LIST, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionPackage.FUNCTION__FUNCTION_LIST, newFunctionList, newFunctionList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionPackage.FUNCTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnedType() {
		return returnedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnedType(String newReturnedType) {
		String oldReturnedType = returnedType;
		returnedType = newReturnedType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionPackage.FUNCTION__RETURNED_TYPE, oldReturnedType, returnedType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getParameter() {
		if (parameter == null) {
			parameter = new EDataTypeUniqueEList(TypeVariable.class, this, FunctionPackage.FUNCTION__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBody() {
		return body;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBody(String newBody) {
		String oldBody = body;
		body = newBody;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FunctionPackage.FUNCTION__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case FunctionPackage.FUNCTION__FUNCTION_LIST:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, FunctionPackage.FUNCTION__FUNCTION_LIST, msgs);
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
				case FunctionPackage.FUNCTION__FUNCTION_LIST:
					return eBasicSetContainer(null, FunctionPackage.FUNCTION__FUNCTION_LIST, msgs);
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
				case FunctionPackage.FUNCTION__FUNCTION_LIST:
					return eContainer.eInverseRemove(this, FunctionPackage.FUNCTION_LIST__FUNCTION, FunctionList.class, msgs);
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
			case FunctionPackage.FUNCTION__FUNCTION_LIST:
				return getFunctionList();
			case FunctionPackage.FUNCTION__NAME:
				return getName();
			case FunctionPackage.FUNCTION__RETURNED_TYPE:
				return getReturnedType();
			case FunctionPackage.FUNCTION__PARAMETER:
				return getParameter();
			case FunctionPackage.FUNCTION__BODY:
				return getBody();
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
			case FunctionPackage.FUNCTION__FUNCTION_LIST:
				setFunctionList((FunctionList)newValue);
				return;
			case FunctionPackage.FUNCTION__NAME:
				setName((String)newValue);
				return;
			case FunctionPackage.FUNCTION__RETURNED_TYPE:
				setReturnedType((String)newValue);
				return;
			case FunctionPackage.FUNCTION__PARAMETER:
				getParameter().clear();
				getParameter().addAll((Collection)newValue);
				return;
			case FunctionPackage.FUNCTION__BODY:
				setBody((String)newValue);
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
			case FunctionPackage.FUNCTION__FUNCTION_LIST:
				setFunctionList((FunctionList)null);
				return;
			case FunctionPackage.FUNCTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case FunctionPackage.FUNCTION__RETURNED_TYPE:
				setReturnedType(RETURNED_TYPE_EDEFAULT);
				return;
			case FunctionPackage.FUNCTION__PARAMETER:
				getParameter().clear();
				return;
			case FunctionPackage.FUNCTION__BODY:
				setBody(BODY_EDEFAULT);
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
			case FunctionPackage.FUNCTION__FUNCTION_LIST:
				return getFunctionList() != null;
			case FunctionPackage.FUNCTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case FunctionPackage.FUNCTION__RETURNED_TYPE:
				return RETURNED_TYPE_EDEFAULT == null ? returnedType != null : !RETURNED_TYPE_EDEFAULT.equals(returnedType);
			case FunctionPackage.FUNCTION__PARAMETER:
				return parameter != null && !parameter.isEmpty();
			case FunctionPackage.FUNCTION__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", returnedType: ");
		result.append(returnedType);
		result.append(", parameter: ");
		result.append(parameter);
		result.append(", body: ");
		result.append(body);
		result.append(')');
		return result.toString();
	}

} //FunctionImpl
