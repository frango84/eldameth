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
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;

import java.util.Collection;

import org.eclipse.draw2d.AbsoluteBendpoint;

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
 * An implementation of the model object '<em><b>Relationship</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link genericUMLDiagramModel.impl.RelationshipImpl#getSource <em>Source</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.RelationshipImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.RelationshipImpl#getBendpoints <em>Bendpoints</em>}</li>
 *   <li>{@link genericUMLDiagramModel.impl.RelationshipImpl#getStereotype <em>Stereotype</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RelationshipImpl extends ModelElementImpl implements Relationship
{

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected ModelElement source = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected ModelElement target = null;

	/**
	 * The cached value of the '{@link #getBendpoints() <em>Bendpoints</em>}' attribute list.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBendpoints()
	 * @generated
	 * @ordered
	 */
  protected EList bendpoints = null;

	/**
	 * The cached value of the '{@link #getConnectionLabel() <em>Connection Label</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnectionLabel()
	 * @generated
	 * @ordered
	 */
	protected ConnectionLabel connectionLabel = null;

  /**
   * The default value of the '{@link #getStereotype() <em>Stereotype</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStereotype()
   * 
   * @ordered
   */
  protected static final String STEREOTYPE_EDEFAULT = new String("");

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected RelationshipImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected EClass eStaticClass() {
		return GenericUMLDiagramModelPackage.eINSTANCE.getRelationship();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ModelElement getSource() {
		if (source != null && source.eIsProxy()) {
			ModelElement oldSource = source;
			source = (ModelElement)eResolveProxy((InternalEObject)source);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(ModelElement newSource, NotificationChain msgs) {
		ModelElement oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setSource(ModelElement newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__SOURCE_RELATIONSHIPS, ModelElement.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__SOURCE_RELATIONSHIPS, ModelElement.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement getTarget() {
		if (target != null && target.eIsProxy()) {
			ModelElement oldTarget = target;
			target = (ModelElement)eResolveProxy((InternalEObject)target);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElement basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(ModelElement newTarget, NotificationChain msgs) {
		ModelElement oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(ModelElement newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__TARGET_RELATIONSHIPS, ModelElement.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__TARGET_RELATIONSHIPS, ModelElement.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getBendpoints() {
		if (bendpoints == null) {
			bendpoints = new EDataTypeUniqueEList(AbsoluteBendpoint.class, this, GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS);
		}
		return bendpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionLabel getConnectionLabel() {
		return connectionLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnectionLabel(ConnectionLabel newConnectionLabel, NotificationChain msgs) {
		ConnectionLabel oldConnectionLabel = connectionLabel;
		connectionLabel = newConnectionLabel;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL, oldConnectionLabel, newConnectionLabel);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnectionLabel(ConnectionLabel newConnectionLabel) {
		if (newConnectionLabel != connectionLabel) {
			NotificationChain msgs = null;
			if (connectionLabel != null)
				msgs = ((InternalEObject)connectionLabel).eInverseRemove(this, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, ConnectionLabel.class, msgs);
			if (newConnectionLabel != null)
				msgs = ((InternalEObject)newConnectionLabel).eInverseAdd(this, GenericUMLDiagramModelPackage.CONNECTION_LABEL__RELATIONSHIP, ConnectionLabel.class, msgs);
			msgs = basicSetConnectionLabel(newConnectionLabel, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL, newConnectionLabel, newConnectionLabel));
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicAdd(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
					if (source != null)
						msgs = ((InternalEObject)source).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__SOURCE_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetSource((ModelElement)otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
					if (target != null)
						msgs = ((InternalEObject)target).eInverseRemove(this, GenericUMLDiagramModelPackage.MODEL_ELEMENT__TARGET_RELATIONSHIPS, ModelElement.class, msgs);
					return basicSetTarget((ModelElement)otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
					if (connectionLabel != null)
						msgs = ((InternalEObject)connectionLabel).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL, null, msgs);
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
				case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
					return eBasicSetContainer(null, GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
					return ((InternalEList)getSourceRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
					return ((InternalEList)getTargetRelationships()).basicRemove(otherEnd, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
					return basicSetSource(null, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
					return basicSetTarget(null, msgs);
				case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
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
				case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
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
			case GenericUMLDiagramModelPackage.RELATIONSHIP__NAME:
				return getName();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION:
				return getDescription();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
				return getParent();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
				return getSourceRelationships();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
				return getTargetRelationships();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE:
				return getStereotype();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__ID:
				return getID();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS:
				return getBendpoints();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
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
			case GenericUMLDiagramModelPackage.RELATIONSHIP__NAME:
				setName((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
				setParent((GenericDiagram)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				getSourceRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				getTargetRelationships().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE:
				setStereotype((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__ID:
				setID((String)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
				setSource((ModelElement)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
				setTarget((ModelElement)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS:
				getBendpoints().clear();
				getBendpoints().addAll((Collection)newValue);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
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
			case GenericUMLDiagramModelPackage.RELATIONSHIP__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
				setParent((GenericDiagram)null);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
				getSourceRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
				getTargetRelationships().clear();
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE:
				setStereotype(STEREOTYPE_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__ID:
				setID(ID_EDEFAULT);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
				setSource((ModelElement)null);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
				setTarget((ModelElement)null);
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS:
				getBendpoints().clear();
				return;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
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
			case GenericUMLDiagramModelPackage.RELATIONSHIP__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT:
				return getParent() != null;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS:
				return sourceRelationships != null && !sourceRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS:
				return targetRelationships != null && !targetRelationships.isEmpty();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE:
				return STEREOTYPE_EDEFAULT == null ? stereotype != null : !STEREOTYPE_EDEFAULT.equals(stereotype);
			case GenericUMLDiagramModelPackage.RELATIONSHIP__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE:
				return source != null;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET:
				return target != null;
			case GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS:
				return bendpoints != null && !bendpoints.isEmpty();
			case GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL:
				return connectionLabel != null;
		}
		return eDynamicIsSet(eFeature);
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   */
  public String toString()
  {
 	 if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer();
    if (stereotype!=null && !stereotype.equals(""))
    	result.append("Relationship «"+stereotype+"»");

    return result.toString();
  }


} //RelationshipImpl
