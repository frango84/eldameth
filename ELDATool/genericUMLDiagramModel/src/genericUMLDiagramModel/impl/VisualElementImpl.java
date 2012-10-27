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

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.VisualElement;
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
 * An implementation of the model object '<em><b>Visual Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link genericUMLDiagramModel.impl.VisualElementImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.VisualElementImpl#getSize <em>Size</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.VisualElementImpl#getContainer <em>Container</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VisualElementImpl extends ModelElementImpl implements VisualElement
{
	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
  protected static final Point LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
  protected Point location = LOCATION_EDEFAULT;

  /**
   * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSize()
   * 
   * @ordered
   */
  protected static Dimension SIZE_EDEFAULT = new Dimension(150,100);

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
  protected Dimension size = SIZE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected VisualElementImpl() {
		super();
	}
  
	public Dimension getSizeDefault()
	{
		return SIZE_EDEFAULT;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass() {
		return GenericUMLDiagramModelPackage.eINSTANCE.getVisualElement();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(Point newLocation) {
		Point oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dimension getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setSize(Dimension newSize) {
		Dimension oldSize = size;
		if (newSize != null)
			size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(
				this,
				Notification.SET,
				GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE,
				oldSize,
				size));
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Container getContainer() {
		if (eContainerFeatureID != GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER) return null;
		return (Container)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setContainer(Container newContainer) {
		if (newContainer != eContainer || (eContainerFeatureID != GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS, Container.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newContainer, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER, newContainer, newContainer));
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER, msgs);
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
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER, msgs);
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
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
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
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__NAME:
				return getName();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__DESCRIPTION:
				return getDescription();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
				return getParent();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__STEREOTYPE:
				return getStereotype();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__ID:
				return getID();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION:
				return getLocation();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE:
				return getSize();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
				return getContainer();
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
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__ID:
				setID((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION:
				setLocation((Point)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE:
				setSize((Dimension)newValue);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
				setContainer((Container)newValue);
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
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
				setParent((GenericDiagram)null);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__ID:
				setID(ID_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
				setContainer((Container)null);
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
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__PARENT:
				return getParent() != null;
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT__CONTAINER:
				return getContainer() != null;
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
		result.append(" (location: ");
		result.append(location);
		result.append(", size: ");
		result.append(size);
		result.append(')');
		return result.toString();
	}

} //VisualElementImpl
