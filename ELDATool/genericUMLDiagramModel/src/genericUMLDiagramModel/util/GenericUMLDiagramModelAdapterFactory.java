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
package genericUMLDiagramModel.util;

import genericUMLDiagramModel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage
 * @generated
 */
public class GenericUMLDiagramModelAdapterFactory extends AdapterFactoryImpl
{

	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenericUMLDiagramModelPackage modelPackage;


	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public GenericUMLDiagramModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = GenericUMLDiagramModelPackage.eINSTANCE;
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
	protected GenericUMLDiagramModelSwitch modelSwitch =
		new GenericUMLDiagramModelSwitch() {
			public Object caseModelElement(ModelElement object) {
				return createModelElementAdapter();
			}
			public Object caseClassifier(Classifier object) {
				return createClassifierAdapter();
			}
			public Object caseRelationship(Relationship object) {
				return createRelationshipAdapter();
			}
			public Object caseGenericDiagram(GenericDiagram object) {
				return createGenericDiagramAdapter();
			}
			public Object caseContainer(Container object) {
				return createContainerAdapter();
			}
			public Object caseNote(Note object) {
				return createNoteAdapter();
			}
			public Object caseVisualElement(VisualElement object) {
				return createVisualElementAdapter();
			}
			public Object caseAuxiliaryElement(AuxiliaryElement object) {
				return createAuxiliaryElementAdapter();
			}
			public Object caseTextLabel(TextLabel object) {
				return createTextLabelAdapter();
			}
			public Object caseHorizontalLineSeparator(HorizontalLineSeparator object) {
				return createHorizontalLineSeparatorAdapter();
			}
			public Object caseVerticalLineSeparator(VerticalLineSeparator object) {
				return createVerticalLineSeparatorAdapter();
			}
			public Object caseConnectionLabel(ConnectionLabel object) {
				return createConnectionLabelAdapter();
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
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.Note <em>Note</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.Note
	 * @generated
	 */
  public Adapter createNoteAdapter() {
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
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.AuxiliaryElement <em>Auxiliary Element</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.AuxiliaryElement
	 * @generated
	 */
  public Adapter createAuxiliaryElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.TextLabel <em>Text Label</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.TextLabel
	 * @generated
	 */
  public Adapter createTextLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.HorizontalLineSeparator <em>Horizontal Line Separator</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.HorizontalLineSeparator
	 * @generated
	 */
  public Adapter createHorizontalLineSeparatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.VerticalLineSeparator <em>Vertical Line Separator</em>}'.
	 * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.VerticalLineSeparator
	 * @generated
	 */
  public Adapter createVerticalLineSeparatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link genericUMLDiagramModel.ConnectionLabel <em>Connection Label</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see genericUMLDiagramModel.ConnectionLabel
	 * @generated
	 */
	public Adapter createConnectionLabelAdapter() {
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

} //GenericUMLDiagramModelAdapterFactory
