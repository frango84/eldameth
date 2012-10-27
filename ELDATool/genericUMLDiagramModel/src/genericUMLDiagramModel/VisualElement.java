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

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Visual Element</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.VisualElement#getLocation  <em>Location</em>} </li> <li> {@link genericUMLDiagramModel.VisualElement#getSize  <em>Size</em>} </li> <li> {@link genericUMLDiagramModel.VisualElement#getContainer  <em>Container</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getVisualElement()
 * @model  
 * @generated
 */
public interface VisualElement extends ModelElement{

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Location</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Location</em>' attribute.
	 * @see #setLocation(Point)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getVisualElement_Location()
	 * @model  dataType="genericUMLDiagramModel.Point"
	 * @generated
	 */
	Point getLocation();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.VisualElement#getLocation  <em>Location</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Location</em>' attribute.
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Point value);

	/**
	 * Returns the value of the '<em><b>Size</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Size</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Size</em>' attribute.
	 * @see #setSize(Dimension)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getVisualElement_Size()
	 * @model  dataType="genericUMLDiagramModel.Dimension"
	 * @generated
	 */
	Dimension getSize();
	
	Dimension getSizeDefault();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.VisualElement#getSize  <em>Size</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Size</em>' attribute.
	 * @see #getSize()
	 * @generated
	 */
	void setSize(Dimension value);

	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.Container#getVisualElements <em>Visual Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Container</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(Container)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getVisualElement_Container()
	 * @see genericUMLDiagramModel.Container#getVisualElements
	 * @model opposite="visualElements"
	 * @generated
	 */
	Container getContainer();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.VisualElement#getContainer  <em>Container</em>} ' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(Container value);

} // VisualElement
