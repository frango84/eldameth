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
package actionGuardModel.impl;

import actionGuardModel.ActionGuardModelPackage;
import actionGuardModel.AnElement;
import actionGuardModel.AnElementList;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>An Element List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link actionGuardModel.impl.AnElementListImpl#getAnElement <em>An Element</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AnElementListImpl extends EObjectImpl implements AnElementList {
	/**
	 * The cached value of the '{@link #getAnElement() <em>An Element</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnElement()
	 * @generated
	 * @ordered
	 */
	protected EList anElement = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnElementListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ActionGuardModelPackage.Literals.AN_ELEMENT_LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getAnElement() {
		if (anElement == null) {
			anElement = new EObjectContainmentWithInverseEList(AnElement.class, this, ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT, ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST);
		}
		return anElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				return ((InternalEList)getAnElement()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				return ((InternalEList)getAnElement()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				return getAnElement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				getAnElement().clear();
				getAnElement().addAll((Collection)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				getAnElement().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT:
				return anElement != null && !anElement.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AnElementListImpl