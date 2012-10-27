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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>An Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link actionGuardModel.impl.AnElementImpl#getAnElementList <em>An Element List</em>}</li>
 *   <li>{@link actionGuardModel.impl.AnElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link actionGuardModel.impl.AnElementImpl#getBody <em>Body</em>}</li>
 *   <li>{@link actionGuardModel.impl.AnElementImpl#getComment <em>Comment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AnElementImpl extends EObjectImpl implements AnElement {
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
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected String comment = COMMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return ActionGuardModelPackage.Literals.AN_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnElementList getAnElementList() {
		if (eContainerFeatureID != ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST) return null;
		return (AnElementList)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAnElementList(AnElementList newAnElementList, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAnElementList, ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnElementList(AnElementList newAnElementList) {
		if (newAnElementList != eInternalContainer() || (eContainerFeatureID != ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST && newAnElementList != null)) {
			if (EcoreUtil.isAncestor(this, newAnElementList))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAnElementList != null)
				msgs = ((InternalEObject)newAnElementList).eInverseAdd(this, ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT, AnElementList.class, msgs);
			msgs = basicSetAnElementList(newAnElementList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST, newAnElementList, newAnElementList));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ActionGuardModelPackage.AN_ELEMENT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ActionGuardModelPackage.AN_ELEMENT__BODY, oldBody, body));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		String oldComment = comment;
		comment = newComment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ActionGuardModelPackage.AN_ELEMENT__COMMENT, oldComment, comment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAnElementList((AnElementList)otherEnd, msgs);
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
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				return basicSetAnElementList(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				return eInternalContainer().eInverseRemove(this, ActionGuardModelPackage.AN_ELEMENT_LIST__AN_ELEMENT, AnElementList.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				return getAnElementList();
			case ActionGuardModelPackage.AN_ELEMENT__NAME:
				return getName();
			case ActionGuardModelPackage.AN_ELEMENT__BODY:
				return getBody();
			case ActionGuardModelPackage.AN_ELEMENT__COMMENT:
				return getComment();
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
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				setAnElementList((AnElementList)newValue);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__BODY:
				setBody((String)newValue);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__COMMENT:
				setComment((String)newValue);
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
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				setAnElementList((AnElementList)null);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__BODY:
				setBody(BODY_EDEFAULT);
				return;
			case ActionGuardModelPackage.AN_ELEMENT__COMMENT:
				setComment(COMMENT_EDEFAULT);
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
			case ActionGuardModelPackage.AN_ELEMENT__AN_ELEMENT_LIST:
				return getAnElementList() != null;
			case ActionGuardModelPackage.AN_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ActionGuardModelPackage.AN_ELEMENT__BODY:
				return BODY_EDEFAULT == null ? body != null : !BODY_EDEFAULT.equals(body);
			case ActionGuardModelPackage.AN_ELEMENT__COMMENT:
				return COMMENT_EDEFAULT == null ? comment != null : !COMMENT_EDEFAULT.equals(comment);
		}
		return super.eIsSet(featureID);
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
		result.append(", body: ");
		result.append(body);
		result.append(", comment: ");
		result.append(comment);
		result.append(')');
		return result.toString();
	}

} //AnElementImpl