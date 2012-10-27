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
package genericUMLDiagramModel.impl;

import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.Relationship;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link genericUMLDiagramModel.impl.GenericDiagramImpl#getModelElements <em>Model Elements</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.GenericDiagramImpl#getMaxID <em>Max ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenericDiagramImpl extends EObjectImpl implements GenericDiagram
{
	/**
	 * The cached value of the '{@link #getModelElements() <em>Model Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getModelElements()
	 * @generated
	 * @ordered
	 */
  protected EList modelElements = null;

	/**
	 * The default value of the '{@link #getMaxID() <em>Max ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxID()
	 * @generated
	 * @ordered
	 */
	protected static final long MAX_ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMaxID() <em>Max ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxID()
	 * @generated
	 * @ordered
	 */
	protected long maxID = MAX_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected GenericDiagramImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass() {
		return GenericUMLDiagramModelPackage.eINSTANCE.getGenericDiagram();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getModelElements() {
		if (modelElements == null) {
			modelElements = new EObjectContainmentWithInverseEList(ModelElement.class, this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericUMLDiagramModelPackage.MODEL_ELEMENT__PARENT);
		}
		return modelElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMaxID() {
		return maxID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxID(long newMaxID) {
		long oldMaxID = maxID;
		maxID = newMaxID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID, oldMaxID, maxID));
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
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
				case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
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
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
				return getModelElements();
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID:
				return new Long(getMaxID());
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
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
				getModelElements().clear();
				getModelElements().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID:
				setMaxID(((Long)newValue).longValue());
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
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
				getModelElements().clear();
				return;
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID:
				setMaxID(MAX_ID_EDEFAULT);
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
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS:
				return modelElements != null && !modelElements.isEmpty();
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID:
				return maxID != MAX_ID_EDEFAULT;
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
		result.append(" (maxID: ");
		result.append(maxID);
		result.append(')');
		return result.toString();
	}

	public List getSortedModelElements() {
		List modelElements = getModelElements();
		// calcolo la lunghezza della lista degli elementi
		int length = modelElements.size();
			
		List list = new ArrayList();
		//ordino prima tutti gli elementi diversi da nota e container
		for(int i=0; i<length; i++) {
			if(!(modelElements.get(i) instanceof Note)
				&& !(modelElements.get(i) instanceof Container)
				&& !(modelElements.get(i) instanceof Relationship)
//				&& !(modelElements.get(i) instanceof ConnectionLabel)
				)
				list.add(modelElements.get(i));
		}
		//ordino dunque i container
		for(int i=0; i<length; i++) {
			if(modelElements.get(i) instanceof Container)
				list.add(modelElements.get(i));
		}
		//ordino dunque le note
		for(int i=0; i<length; i++) {
			if(modelElements.get(i) instanceof Note)
				list.add(modelElements.get(i));
		}

		return list;
	}

} //GenericDiagramImpl
