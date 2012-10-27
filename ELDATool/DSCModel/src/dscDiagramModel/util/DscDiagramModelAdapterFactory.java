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
package dscDiagramModel.util;

import dscDiagramModel.*;

import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see dscDiagramModel.DscDiagramModelPackage
 * @generated
 */
public class DscDiagramModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DscDiagramModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DscDiagramModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DscDiagramModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch the delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DscDiagramModelSwitch modelSwitch =
		new DscDiagramModelSwitch() {
			public Object caseDSCDiagram(DSCDiagram object) {
				return createDSCDiagramAdapter();
			}
			public Object caseAnchorNoteToItem(AnchorNoteToItem object) {
				return createAnchorNoteToItemAdapter();
			}
			public Object caseTransition(Transition object) {
				return createTransitionAdapter();
			}
			public Object caseStartPoint(StartPoint object) {
				return createStartPointAdapter();
			}
			public Object caseShallowHistory(ShallowHistory object) {
				return createShallowHistoryAdapter();
			}
			public Object caseDeepHistory(DeepHistory object) {
				return createDeepHistoryAdapter();
			}
			public Object caseDSCState(DSCState object) {
				return createDSCStateAdapter();
			}
			public Object caseGenericDiagram(GenericDiagram object) {
				return createGenericDiagramAdapter();
			}
			public Object caseModelElement(ModelElement object) {
				return createModelElementAdapter();
			}
			public Object caseRelationship(Relationship object) {
				return createRelationshipAdapter();
			}
			public Object caseVisualElement(VisualElement object) {
				return createVisualElementAdapter();
			}
			public Object caseClassifier(Classifier object) {
				return createClassifierAdapter();
			}
			public Object caseContainer(Container object) {
				return createContainerAdapter();
			}
			public Object defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	public Adapter createAdapter(Notifier target) {
		return (Adapter)modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.DSCDiagram <em>DSC Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.DSCDiagram
	 * @generated
	 */
	public Adapter createDSCDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.AnchorNoteToItem <em>Anchor Note To Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.AnchorNoteToItem
	 * @generated
	 */
	public Adapter createAnchorNoteToItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.Transition
	 * @generated
	 */
	public Adapter createTransitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.StartPoint <em>Start Point</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.StartPoint
	 * @generated
	 */
	public Adapter createStartPointAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.ShallowHistory <em>Shallow History</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.ShallowHistory
	 * @generated
	 */
	public Adapter createShallowHistoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.DeepHistory <em>Deep History</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.DeepHistory
	 * @generated
	 */
	public Adapter createDeepHistoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link dscDiagramModel.DSCState <em>DSC State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see dscDiagramModel.DSCState
	 * @generated
	 */
	public Adapter createDSCStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.GenericDiagram <em>Generic Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.GenericDiagram
	 * @generated
	 */
	public Adapter createGenericDiagramAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.ModelElement
	 * @generated
	 */
	public Adapter createModelElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.Relationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.Relationship
	 * @generated
	 */
	public Adapter createRelationshipAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.VisualElement <em>Visual Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.VisualElement
	 * @generated
	 */
	public Adapter createVisualElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.Classifier
	 * @generated
	 */
	public Adapter createClassifierAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.Container
	 * @generated
	 */
	public Adapter createContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DscDiagramModelAdapterFactory
