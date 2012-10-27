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
package genericUMLDiagramModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of the model. <!-- end-user-doc -->
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage
 * @generated
 */
public interface GenericUMLDiagramModelFactory extends EFactory{

	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericUMLDiagramModelFactory eINSTANCE = new genericUMLDiagramModel.impl.GenericUMLDiagramModelFactoryImpl();

	/**
	 * Returns a new object of class '<em>Model Element</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Model Element</em>'.
	 * @generated
	 */
  ModelElement createModelElement();

	/**
	 * Returns a new object of class '<em>Classifier</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Classifier</em>'.
	 * @generated
	 */
  Classifier createClassifier();

	/**
	 * Returns a new object of class '<em>Relationship</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Relationship</em>'.
	 * @generated
	 */
  Relationship createRelationship();

	/**
	 * Returns a new object of class '<em>Generic Diagram</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Diagram</em>'.
	 * @generated
	 */
  GenericDiagram createGenericDiagram();

	/**
	 * Returns a new object of class '<em>Container</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Container</em>'.
	 * @generated
	 */
  Container createContainer();

	/**
	 * Returns a new object of class '<em>Note</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Note</em>'.
	 * @generated
	 */
  Note createNote();

	/**
	 * Returns a new object of class '<em>Visual Element</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Visual Element</em>'.
	 * @generated
	 */
  VisualElement createVisualElement();

	/**
	 * Returns a new object of class '<em>Auxiliary Element</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Auxiliary Element</em>'.
	 * @generated
	 */
  AuxiliaryElement createAuxiliaryElement();

	/**
	 * Returns a new object of class '<em>Text Label</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Text Label</em>'.
	 * @generated
	 */
  TextLabel createTextLabel();

	/**
	 * Returns a new object of class '<em>Horizontal Line Separator</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Horizontal Line Separator</em>'.
	 * @generated
	 */
  HorizontalLineSeparator createHorizontalLineSeparator();

	/**
	 * Returns a new object of class '<em>Vertical Line Separator</em>'.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vertical Line Separator</em>'.
	 * @generated
	 */
  VerticalLineSeparator createVerticalLineSeparator();

	/**
	 * Returns a new object of class '<em>Connection Label</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection Label</em>'.
	 * @generated
	 */
	ConnectionLabel createConnectionLabel();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
  GenericUMLDiagramModelPackage getGenericUMLDiagramModelPackage();

} //GenericUMLDiagramModelFactory
