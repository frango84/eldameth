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

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage
 * @generated
 */
public class GenericUMLDiagramModelSwitch {

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static GenericUMLDiagramModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public GenericUMLDiagramModelSwitch() {
		if (modelPackage == null) {
			modelPackage = GenericUMLDiagramModelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
  public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
  protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
  protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case GenericUMLDiagramModelPackage.MODEL_ELEMENT: {
				ModelElement modelElement = (ModelElement)theEObject;
				Object result = caseModelElement(modelElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.CLASSIFIER: {
				Classifier classifier = (Classifier)theEObject;
				Object result = caseClassifier(classifier);
				if (result == null) result = caseVisualElement(classifier);
				if (result == null) result = caseModelElement(classifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.RELATIONSHIP: {
				Relationship relationship = (Relationship)theEObject;
				Object result = caseRelationship(relationship);
				if (result == null) result = caseModelElement(relationship);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM: {
				GenericDiagram genericDiagram = (GenericDiagram)theEObject;
				Object result = caseGenericDiagram(genericDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.CONTAINER: {
				Container container = (Container)theEObject;
				Object result = caseContainer(container);
				if (result == null) result = caseVisualElement(container);
				if (result == null) result = caseModelElement(container);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.NOTE: {
				Note note = (Note)theEObject;
				Object result = caseNote(note);
				if (result == null) result = caseVisualElement(note);
				if (result == null) result = caseModelElement(note);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT: {
				VisualElement visualElement = (VisualElement)theEObject;
				Object result = caseVisualElement(visualElement);
				if (result == null) result = caseModelElement(visualElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.AUXILIARY_ELEMENT: {
				AuxiliaryElement auxiliaryElement = (AuxiliaryElement)theEObject;
				Object result = caseAuxiliaryElement(auxiliaryElement);
				if (result == null) result = caseVisualElement(auxiliaryElement);
				if (result == null) result = caseModelElement(auxiliaryElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.TEXT_LABEL: {
				TextLabel textLabel = (TextLabel)theEObject;
				Object result = caseTextLabel(textLabel);
				if (result == null) result = caseAuxiliaryElement(textLabel);
				if (result == null) result = caseVisualElement(textLabel);
				if (result == null) result = caseModelElement(textLabel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.HORIZONTAL_LINE_SEPARATOR: {
				HorizontalLineSeparator horizontalLineSeparator = (HorizontalLineSeparator)theEObject;
				Object result = caseHorizontalLineSeparator(horizontalLineSeparator);
				if (result == null) result = caseAuxiliaryElement(horizontalLineSeparator);
				if (result == null) result = caseVisualElement(horizontalLineSeparator);
				if (result == null) result = caseModelElement(horizontalLineSeparator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR: {
				VerticalLineSeparator verticalLineSeparator = (VerticalLineSeparator)theEObject;
				Object result = caseVerticalLineSeparator(verticalLineSeparator);
				if (result == null) result = caseAuxiliaryElement(verticalLineSeparator);
				if (result == null) result = caseVisualElement(verticalLineSeparator);
				if (result == null) result = caseModelElement(verticalLineSeparator);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL: {
				ConnectionLabel connectionLabel = (ConnectionLabel)theEObject;
				Object result = caseConnectionLabel(connectionLabel);
				if (result == null) result = caseTextLabel(connectionLabel);
				if (result == null) result = caseAuxiliaryElement(connectionLabel);
				if (result == null) result = caseVisualElement(connectionLabel);
				if (result == null) result = caseModelElement(connectionLabel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Model Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseModelElement(ModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Classifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseClassifier(Classifier object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Relationship</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseRelationship(Relationship object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Generic Diagram</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Generic Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseGenericDiagram(GenericDiagram object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Container</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseContainer(Container object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Note</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Note</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseNote(Note object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Visual Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Visual Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseVisualElement(VisualElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Auxiliary Element</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Auxiliary Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseAuxiliaryElement(AuxiliaryElement object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Text Label</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Text Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseTextLabel(TextLabel object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Horizontal Line Separator</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Horizontal Line Separator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseHorizontalLineSeparator(HorizontalLineSeparator object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Vertical Line Separator</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Vertical Line Separator</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
  public Object caseVerticalLineSeparator(VerticalLineSeparator object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Connection Label</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Connection Label</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public Object caseConnectionLabel(ConnectionLabel object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
  public Object defaultCase(EObject object) {
		return null;
	}

} //GenericUMLDiagramModelSwitch
