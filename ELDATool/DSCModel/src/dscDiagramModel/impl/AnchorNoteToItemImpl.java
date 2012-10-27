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

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DscDiagramModelPackage;

import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.ModelElement;

import genericUMLDiagramModel.impl.RelationshipImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Anchor Note To Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AnchorNoteToItemImpl extends RelationshipImpl implements AnchorNoteToItem {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnchorNoteToItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return DscDiagramModelPackage.eINSTANCE.getAnchorNoteToItem();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
					if (source != null)
						msgs = ((InternalEObject)source).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__SOURCE_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetSource((ModelElement)otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__TARGET_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetTarget((ModelElement)otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
					if (connectionLabel != null)
						msgs = ((InternalEObject)connectionLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL, null, msgs);
					return basicSetConnectionLabel((ConnectionLabel)otherEnd, msgs);
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
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
					return eBasicSetContainer(null, DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
					return basicSetSource(null, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
					return basicSetTarget(null, msgs);
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
					return basicSetConnectionLabel(null, msgs);
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
				case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
					return eContainer.eInverseRemove(this, GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS, GenericDiagram.class, msgs);
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
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__NAME:
				return getName();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__DESCRIPTION:
				return getDescription();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
				return getParent();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__STEREOTYPE:
				return getStereotype();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__ID:
				return getID();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__BENDPOINTS:
				return getBendpoints();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
				return getConnectionLabel();
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
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__NAME:
				setName((String)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__ID:
				setID((String)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
				setSource((ModelElement)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
				setTarget((ModelElement)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__BENDPOINTS:
				getBendpoints().clear();
				getBendpoints().addAll((Collection)newValue);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
				setConnectionLabel((ConnectionLabel)newValue);
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
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
				setParent((GenericDiagram)null);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__ID:
				setID(ID_EDEFAULT);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
				setSource((ModelElement)null);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
				setTarget((ModelElement)null);
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__BENDPOINTS:
				getBendpoints().clear();
				return;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
				setConnectionLabel((ConnectionLabel)null);
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
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__PARENT:
				return getParent() != null;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__SOURCE:
				return source != null;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__TARGET:
				return target != null;
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__BENDPOINTS:
				return bendpoints != null && !bendpoints.isEmpty();
			case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL:
				return connectionLabel != null;
		}
		return eDynamicIsSet(eFeature);
	}

} //AnchorNoteToItemImpl
