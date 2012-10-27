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
package triggerModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Trigger List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link triggerModel.TriggerList#getTrigger <em>Trigger</em>}</li>
 * </ul>
 * </p>
 *
 * @see triggerModel.TriggerModelPackage#getTriggerList()
 * @model
 * @generated
 */
public interface TriggerList extends EObject {
	/**
	 * Returns the value of the '<em><b>Trigger</b></em>' containment reference list.
	 * The list contents are of type {@link triggerModel.Trigger}.
	 * It is bidirectional and its opposite is '{@link triggerModel.Trigger#getTriggerList <em>Trigger List</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trigger</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Trigger</em>' containment reference list.
	 * @see triggerModel.TriggerModelPackage#getTriggerList_Trigger()
	 * @see triggerModel.Trigger#getTriggerList
	 * @model type="triggerModel.Trigger" opposite="triggerList" containment="true"
	 * @generated
	 */
	EList getTrigger();

} // TriggerList
