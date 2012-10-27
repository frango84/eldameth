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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Text Label</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.TextLabel#getText  <em>Text</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getTextLabel()
 * @model  
 * @generated
 */
public interface TextLabel extends AuxiliaryElement{

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Text</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getTextLabel_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.TextLabel#getText  <em>Text</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // TextLabel
