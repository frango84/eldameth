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
package actionGuardModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>An Element List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link actionGuardModel.AnElementList#getAnElement <em>An Element</em>}</li>
 * </ul>
 * </p>
 *
 * @see actionGuardModel.ActionGuardModelPackage#getAnElementList()
 * @model
 * @generated
 */
public interface AnElementList extends EObject {
	/**
	 * Returns the value of the '<em><b>An Element</b></em>' containment reference list.
	 * The list contents are of type {@link actionGuardModel.AnElement}.
	 * It is bidirectional and its opposite is '{@link actionGuardModel.AnElement#getAnElementList <em>An Element List</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>An Element</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>An Element</em>' containment reference list.
	 * @see actionGuardModel.ActionGuardModelPackage#getAnElementList_AnElement()
	 * @see actionGuardModel.AnElement#getAnElementList
	 * @model type="actionGuardModel.AnElement" opposite="anElementList" containment="true"
	 * @generated
	 */
	EList getAnElement();

} // AnElementList