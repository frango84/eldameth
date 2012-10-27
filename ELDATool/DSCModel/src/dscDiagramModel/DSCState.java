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

import genericUMLDiagramModel.Container;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DSC State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link dscDiagramModel.DSCState#getStartPoint <em>Start Point</em>}</li>
 *   <li>{@link dscDiagramModel.DSCState#getShallowHistory <em>Shallow History</em>}</li>
 *   <li>{@link dscDiagramModel.DSCState#getDeepHistory <em>Deep History</em>}</li>
 *   <li>{@link dscDiagramModel.DSCState#getVariables <em>Variables</em>}</li>
 *   <li>{@link dscDiagramModel.DSCState#isIsSimple <em>Is Simple</em>}</li>
 * </ul>
 * </p>
 *
 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState()
 * @model
 * @generated
 */
public interface DSCState extends Container {
	/**
	 * Returns the value of the '<em><b>Start Point</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dscDiagramModel.StartPoint#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Point</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Point</em>' reference.
	 * @see #setStartPoint(StartPoint)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState_StartPoint()
	 * @see dscDiagramModel.StartPoint#getCompositeState
	 * @model opposite="compositeState" required="true"
	 * @generated
	 */
	StartPoint getStartPoint();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCState#getStartPoint <em>Start Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Point</em>' reference.
	 * @see #getStartPoint()
	 * @generated
	 */
	void setStartPoint(StartPoint value);

	/**
	 * Returns the value of the '<em><b>Shallow History</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dscDiagramModel.ShallowHistory#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shallow History</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shallow History</em>' reference.
	 * @see #setShallowHistory(ShallowHistory)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState_ShallowHistory()
	 * @see dscDiagramModel.ShallowHistory#getCompositeState
	 * @model opposite="compositeState"
	 * @generated
	 */
	ShallowHistory getShallowHistory();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCState#getShallowHistory <em>Shallow History</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shallow History</em>' reference.
	 * @see #getShallowHistory()
	 * @generated
	 */
	void setShallowHistory(ShallowHistory value);

	/**
	 * Returns the value of the '<em><b>Deep History</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link dscDiagramModel.DeepHistory#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deep History</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deep History</em>' reference.
	 * @see #setDeepHistory(DeepHistory)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState_DeepHistory()
	 * @see dscDiagramModel.DeepHistory#getCompositeState
	 * @model opposite="compositeState"
	 * @generated
	 */
	DeepHistory getDeepHistory();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCState#getDeepHistory <em>Deep History</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deep History</em>' reference.
	 * @see #getDeepHistory()
	 * @generated
	 */
	void setDeepHistory(DeepHistory value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' attribute list.
	 * The list contents are of type {@link dscDiagramModel.myDataTypes.TypeVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' attribute list.
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState_Variables()
	 * @model type="dscDiagramModel.myDataTypes.TypeVariable" dataType="dscDiagramModel.Variable"
	 * @generated
	 */
	EList getVariables();

	/**
	 * Returns the value of the '<em><b>Is Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Simple</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Simple</em>' attribute.
	 * @see #setIsSimple(boolean)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCState_IsSimple()
	 * @model
	 * @generated
	 */
	boolean isIsSimple();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCState#isIsSimple <em>Is Simple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Simple</em>' attribute.
	 * @see #isIsSimple()
	 * @generated
	 */
	void setIsSimple(boolean value);

} // DSCState
