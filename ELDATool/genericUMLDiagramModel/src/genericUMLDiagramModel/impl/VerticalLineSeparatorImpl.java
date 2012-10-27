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
import genericUMLDiagramModel.VerticalLineSeparator;
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
 * An implementation of the model object '<em><b>Vertical Line Separator</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class VerticalLineSeparatorImpl extends AuxiliaryElementImpl implements VerticalLineSeparator
{
	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected VerticalLineSeparatorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass() {
		return GenericUMLDiagramModelPackage.eINSTANCE.getVerticalLineSeparator();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER, msgs);
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
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER, msgs);
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
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
				case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
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
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__NAME:
				return getName();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__DESCRIPTION:
				return getDescription();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
				return getParent();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__STEREOTYPE:
				return getStereotype();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__ID:
				return getID();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__LOCATION:
				return getLocation();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SIZE:
				return getSize();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
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
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__NAME:
				setName((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__ID:
				setID((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__LOCATION:
				setLocation((Point)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SIZE:
				setSize((Dimension)newValue);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
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
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
				setParent((GenericDiagram)null);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__ID:
				setID(ID_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SIZE:
				setSize(SIZE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
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
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__PARENT:
				return getParent() != null;
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__SIZE:
				return SIZE_EDEFAULT == null ? size != null : !SIZE_EDEFAULT.equals(size);
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR__CONTAINER:
				return getContainer() != null;
		}
		return eDynamicIsSet(eFeature);
	}

  /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 */
	public void setSize(Dimension newSize) {
		Dimension oldSize = size;
		if (newSize != null)
			size = newSize;
		size.width=10;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this,Notification.SET,GenericUMLDiagramModelPackage.VISUAL_ELEMENT__SIZE,oldSize,size));
	}
public String toString()
 {
	 if (eIsProxy()) return super.toString();

   StringBuffer result = new StringBuffer();
   result.append("Separatore verticale");
  
   return result.toString();
 }

} //VerticalLineSeparatorImpl
