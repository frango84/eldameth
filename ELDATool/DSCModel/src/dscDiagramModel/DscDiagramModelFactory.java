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
package dscDiagramModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see dscDiagramModel.DscDiagramModelPackage
 * @generated
 */
public interface DscDiagramModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DscDiagramModelFactory eINSTANCE = new dscDiagramModel.impl.DscDiagramModelFactoryImpl();

	/**
	 * Returns a new object of class '<em>DSC Diagram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DSC Diagram</em>'.
	 * @generated
	 */
	DSCDiagram createDSCDiagram();

	/**
	 * Returns a new object of class '<em>Anchor Note To Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Anchor Note To Item</em>'.
	 * @generated
	 */
	AnchorNoteToItem createAnchorNoteToItem();

	/**
	 * Returns a new object of class '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Transition</em>'.
	 * @generated
	 */
	Transition createTransition();

	/**
	 * Returns a new object of class '<em>Start Point</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Point</em>'.
	 * @generated
	 */
	StartPoint createStartPoint();

	/**
	 * Returns a new object of class '<em>Shallow History</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Shallow History</em>'.
	 * @generated
	 */
	ShallowHistory createShallowHistory();

	/**
	 * Returns a new object of class '<em>Deep History</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deep History</em>'.
	 * @generated
	 */
	DeepHistory createDeepHistory();

	/**
	 * Returns a new object of class '<em>DSC State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>DSC State</em>'.
	 * @generated
	 */
	DSCState createDSCState();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DscDiagramModelPackage getDscDiagramModelPackage();

} //DscDiagramModelFactory
