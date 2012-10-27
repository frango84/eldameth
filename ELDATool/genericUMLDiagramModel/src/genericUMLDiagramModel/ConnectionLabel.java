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

import java.beans.PropertyChangeListener;
import org.eclipse.draw2d.geometry.Point;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Connection Label</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.ConnectionLabel#getOffset  <em>Offset</em>} </li> <li> {@link genericUMLDiagramModel.ConnectionLabel#getRelationship  <em>Relationship</em>} </li> <li> {@link genericUMLDiagramModel.ConnectionLabel#isIsVisible  <em>Is Visible</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getConnectionLabel()
 * @model
 * @generated
 */
public interface ConnectionLabel extends TextLabel{
	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Offset</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(Point)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getConnectionLabel_Offset()
	 * @model  dataType="genericUMLDiagramModel.Point"
	 * @generated
	 */
	Point getOffset();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ConnectionLabel#getOffset  <em>Offset</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(Point value);

	/**
	 * Returns the value of the '<em><b>Relationship</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.Relationship#getConnectionLabel <em>Connection Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Relationship</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relationship</em>' container reference.
	 * @see #setRelationship(Relationship)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getConnectionLabel_Relationship()
	 * @see genericUMLDiagramModel.Relationship#getConnectionLabel
	 * @model opposite="connectionLabel" required="true"
	 * @generated
	 */
	Relationship getRelationship();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ConnectionLabel#getRelationship  <em>Relationship</em>} ' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Relationship</em>' container reference.
	 * @see #getRelationship()
	 * @generated
	 */
	void setRelationship(Relationship value);

	/**
	 * Returns the value of the '<em><b>Is Visible</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Is Visible</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Is Visible</em>' attribute.
	 * @see #setIsVisible(boolean)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getConnectionLabel_IsVisible()
	 * @model
	 * @generated
	 */
	boolean isIsVisible();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ConnectionLabel#isIsVisible  <em>Is Visible</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Is Visible</em>' attribute.
	 * @see #isIsVisible()
	 * @generated
	 */
	void setIsVisible(boolean value);

	void addPropertyChangeListener(PropertyChangeListener l);
	void removePropertyChangeListener(PropertyChangeListener l);

} // ConnectionLabel
