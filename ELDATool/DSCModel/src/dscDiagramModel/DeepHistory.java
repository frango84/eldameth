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

import genericUMLDiagramModel.Classifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Deep History</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link dscDiagramModel.DeepHistory#getCompositeState <em>Composite State</em>}</li>
 * </ul>
 * </p>
 *
 * @see dscDiagramModel.DscDiagramModelPackage#getDeepHistory()
 * @model
 * @generated
 */
public interface DeepHistory extends Classifier {
	/**
	 * Returns the value of the '<em><b>Composite State</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dscDiagramModel.DSCState#getDeepHistory <em>Deep History</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite State</em>' reference.
	 * @see #setCompositeState(DSCState)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDeepHistory_CompositeState()
	 * @see dscDiagramModel.DSCState#getDeepHistory
	 * @model opposite="deepHistory" required="true"
	 * @generated
	 */
	DSCState getCompositeState();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DeepHistory#getCompositeState <em>Composite State</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite State</em>' reference.
	 * @see #getCompositeState()
	 * @generated
	 */
	void setCompositeState(DSCState value);

} // DeepHistory
