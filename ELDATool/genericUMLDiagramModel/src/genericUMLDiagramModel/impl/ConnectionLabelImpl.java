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

import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.Relationship;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link genericUMLDiagramModel.impl.ConnectionLabelImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.ConnectionLabelImpl#getRelationship <em>Relationship</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.ConnectionLabelImpl#isIsVisible <em>Is Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionLabelImpl extends TextLabelImpl implements ConnectionLabel {
	private PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	protected static final String TEXT_EDEFAULT = new String("");
	private String text = TEXT_EDEFAULT;
	
	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @
	 * @ordered
	 */
	protected static final Point OFFSET_EDEFAULT = new Point(); //null;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected Point offset = OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsVisible() <em>Is Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsVisible() <em>Is Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean isVisible = IS_VISIBLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return GenericUMLDiagramModelPackage.eINSTANCE.getConnectionLabel();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(Point newOffset) {
		Point oldOffset = offset;
		offset = newOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.CONNECTION_LABEL__OFFSET, oldOffset, offset));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Relationship getRelationship() {
		if (eContainerFeatureID != GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP) return null;
		return (Relationship)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelationship(Relationship newRelationship) {
		if (newRelationship != eContainer || (eContainerFeatureID != GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP && newRelationship != null)) {
			if (EcoreUtil.isAncestor(this, newRelationship))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newRelationship != null)
				msgs = ((InternalEObject)newRelationship).eInverseAdd(this, GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL, Relationship.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newRelationship, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, newRelationship, newRelationship));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsVisible() {
		return isVisible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsVisible(boolean newIsVisible) {
		boolean oldIsVisible = isVisible;
		isVisible = newIsVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.CONNECTION_LABEL__IS_VISIBLE, oldIsVisible, isVisible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, msgs);
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
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, msgs);
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
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS, Container.class, msgs);
				case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL, Relationship.class, msgs);
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
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__NAME:
				return getName();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__DESCRIPTION:
				return getDescription();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
				return getParent();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__STEREOTYPE:
				return getStereotype();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__ID:
				return getID();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__LOCATION:
				return getLocation();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SIZE:
				return getSize();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
				return getContainer();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TEXT:
				return getText();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__OFFSET:
				return getOffset();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
				return getRelationship();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__IS_VISIBLE:
				return isIsVisible() ? Boolean.TRUE : Boolean.FALSE;
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
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__NAME:
				setName((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__ID:
				setID((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__LOCATION:
				setLocation((Point)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SIZE:
				setSize((Dimension)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
				setContainer((Container)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TEXT:
				setText((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__OFFSET:
				setOffset((Point)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
				setRelationship((Relationship)newValue);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__IS_VISIBLE:
				setIsVisible(((Boolean)newValue).booleanValue());
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
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
				setParent((GenericDiagram)null);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__ID:
				setID(ID_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
				setContainer((Container)null);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__OFFSET:
				setOffset(OFFSET_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
				setRelationship((Relationship)null);
				return;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__IS_VISIBLE:
				setIsVisible(IS_VISIBLE_EDEFAULT);
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
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__PARENT:
				return getParent() != null;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__CONTAINER:
				return getContainer() != null;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__OFFSET:
				return OFFSET_EDEFAULT == null ? offset != null : !OFFSET_EDEFAULT.equals(offset);
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP:
				return getRelationship() != null;
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL__IS_VISIBLE:
				return isVisible != IS_VISIBLE_EDEFAULT;
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
		result.append(" (offset: ");
		result.append(offset);
		result.append(", isVisible: ");
		result.append(isVisible);
		result.append(')');
		return result.toString();
	}

	  public void addPropertyChangeListener(PropertyChangeListener l){
		    listeners.addPropertyChangeListener(l);
	  }
	  
	  public void removePropertyChangeListener(PropertyChangeListener l){
		listeners.removePropertyChangeListener(l);
	  }
} //ConnectionLabelImpl
