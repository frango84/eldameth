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

import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Generic Diagram</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.GenericDiagram#getModelElements  <em>Model Elements</em>} </li> <li> {@link genericUMLDiagramModel.GenericDiagram#getMaxID  <em>Max ID</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getGenericDiagram()
 * @model
 * @generated
 */
public interface GenericDiagram extends EObject{
	/**
	 * Returns the value of the '<em><b>Model Elements</b></em>' containment reference list.
	 * The list contents are of type {@link genericUMLDiagramModel.ModelElement}.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.ModelElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Model Elements</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Elements</em>' containment reference list.
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getGenericDiagram_ModelElements()
	 * @see genericUMLDiagramModel.ModelElement#getParent
	 * @model type="genericUMLDiagramModel.ModelElement" opposite="parent" containment="true"
	 * @generated
	 */
  EList getModelElements();

	/**
	 * Returns the value of the '<em><b>Max ID</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Max ID</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Max ID</em>' attribute.
	 * @see #setMaxID(long)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getGenericDiagram_MaxID()
	 * @model
	 * @generated
	 */
	long getMaxID();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.GenericDiagram#getMaxID  <em>Max ID</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Max ID</em>' attribute.
	 * @see #getMaxID()
	 * @generated
	 */
	void setMaxID(long value);

	public List getSortedModelElements();

} // GenericDiagram
