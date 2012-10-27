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

import genericUMLDiagramModel.GenericDiagram;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DSC Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link dscDiagramModel.DSCDiagram#getEventFile <em>Event File</em>}</li>
 *   <li>{@link dscDiagramModel.DSCDiagram#getGuardFile <em>Guard File</em>}</li>
 *   <li>{@link dscDiagramModel.DSCDiagram#getActionFile <em>Action File</em>}</li>
 *   <li>{@link dscDiagramModel.DSCDiagram#getDiagramVariables <em>Diagram Variables</em>}</li>
 *   <li>{@link dscDiagramModel.DSCDiagram#getFunctionFile <em>Function File</em>}</li>
 * </ul>
 * </p>
 *
 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram()
 * @model
 * @generated
 */
public interface DSCDiagram extends GenericDiagram {
	/**
	 * Returns the value of the '<em><b>Event File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event File</em>' attribute.
	 * @see #setEventFile(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram_EventFile()
	 * @model
	 * @generated
	 */
	String getEventFile();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCDiagram#getEventFile <em>Event File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event File</em>' attribute.
	 * @see #getEventFile()
	 * @generated
	 */
	void setEventFile(String value);

	/**
	 * Returns the value of the '<em><b>Guard File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard File</em>' attribute.
	 * @see #setGuardFile(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram_GuardFile()
	 * @model
	 * @generated
	 */
	String getGuardFile();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCDiagram#getGuardFile <em>Guard File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard File</em>' attribute.
	 * @see #getGuardFile()
	 * @generated
	 */
	void setGuardFile(String value);

	/**
	 * Returns the value of the '<em><b>Action File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action File</em>' attribute.
	 * @see #setActionFile(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram_ActionFile()
	 * @model
	 * @generated
	 */
	String getActionFile();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCDiagram#getActionFile <em>Action File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action File</em>' attribute.
	 * @see #getActionFile()
	 * @generated
	 */
	void setActionFile(String value);

	/**
	 * Returns the value of the '<em><b>Diagram Variables</b></em>' attribute list.
	 * The list contents are of type {@link dscDiagramModel.myDataTypes.TypeDiagramVariable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Variables</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Variables</em>' attribute list.
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram_DiagramVariables()
	 * @model type="dscDiagramModel.myDataTypes.TypeDiagramVariable" dataType="dscDiagramModel.DiagramVariable"
	 * @generated
	 */
	EList getDiagramVariables();

	/**
	 * Returns the value of the '<em><b>Function File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function File</em>' attribute.
	 * @see #setFunctionFile(String)
	 * @see dscDiagramModel.DscDiagramModelPackage#getDSCDiagram_FunctionFile()
	 * @model
	 * @generated
	 */
	String getFunctionFile();

	/**
	 * Sets the value of the '{@link dscDiagramModel.DSCDiagram#getFunctionFile <em>Function File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function File</em>' attribute.
	 * @see #getFunctionFile()
	 * @generated
	 */
	void setFunctionFile(String value);

} // DSCDiagram
