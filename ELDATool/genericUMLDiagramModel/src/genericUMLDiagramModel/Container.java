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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link genericUMLDiagramModel.Container#getVisualElements <em>Visual Elements</em>}</li>
 *   <li>{@link genericUMLDiagramModel.Container#getStereotype <em>Stereotype</em>}</li>
 * </ul>
 * </p>
 * 
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getContainer()
 * @model 
 * @generated
 */
public interface Container extends VisualElement{
	/**
	 * Returns the value of the '<em><b>Visual Elements</b></em>' containment reference list.
	 * The list contents are of type {@link genericUMLDiagramModel.VisualElement}.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.VisualElement#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Visual Elements</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Visual Elements</em>' containment reference list.
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getContainer_VisualElements()
	 * @see genericUMLDiagramModel.VisualElement#getContainer
	 * @model type="genericUMLDiagramModel.VisualElement" opposite="container" containment="true"
	 * @generated
	 */
  EList getVisualElements();

} // Container
