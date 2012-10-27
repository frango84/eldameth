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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Model Element</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.ModelElement#getName  <em>Name</em>} </li> <li> {@link genericUMLDiagramModel.ModelElement#getDescription  <em>Description</em>} </li> <li> {@link genericUMLDiagramModel.ModelElement#getParent  <em>Parent</em>} </li> <li> {@link genericUMLDiagramModel.ModelElement#getSourceRelationships  <em>Source Relationships</em>} </li> <li> {@link genericUMLDiagramModel.ModelElement#getTargetRelationships  <em>Target Relationships</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement()
 * @model  
 * @generated
 */
public interface ModelElement extends EObject{

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ModelElement#getName  <em>Name</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ModelElement#getDescription  <em>Description</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.GenericDiagram#getModelElements <em>Model Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(GenericDiagram)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_Parent()
	 * @see genericUMLDiagramModel.GenericDiagram#getModelElements
	 * @model opposite="modelElements" required="true"
	 * @generated
	 */
	GenericDiagram getParent();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ModelElement#getParent  <em>Parent</em>} ' container reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(GenericDiagram value);

	/**
	 * Returns the value of the '<em><b>Source Relationships</b></em>' reference list.
	 * The list contents are of type {@link genericUMLDiagramModel.Relationship}.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.Relationship#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Source Relationships</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Relationships</em>' reference list.
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_SourceRelationships()
	 * @see genericUMLDiagramModel.Relationship#getSource
	 * @model type="genericUMLDiagramModel.Relationship" opposite="source"
	 * @generated
	 */
  EList getSourceRelationships();

	/**
	 * Returns the value of the '<em><b>Target Relationships</b></em>' reference list.
	 * The list contents are of type {@link genericUMLDiagramModel.Relationship}.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.Relationship#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target Relationships</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Relationships</em>' reference list.
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_TargetRelationships()
	 * @see genericUMLDiagramModel.Relationship#getTarget
	 * @model type="genericUMLDiagramModel.Relationship" opposite="target"
	 * @generated
	 */
  EList getTargetRelationships();

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>ID</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_ID()
	 * @model
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ModelElement#getID  <em>ID</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Stereotype</b></em>' attribute. <!-- begin-user-doc --> <p> If the meaning of the '<em>Stereotype</em>' attribute isn't clear, there really should be more of a description here... </p> <!-- end-user-doc -->
	 * @return  the value of the '<em>Stereotype</em>' attribute.
	 * @see #setStereotype(String)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getModelElement_Stereotype()
	 * @model
	 * @generated
	 */
	String getStereotype();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.ModelElement#getStereotype  <em>Stereotype</em>} ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Stereotype</em>' attribute.
	 * @see #getStereotype()
	 * @generated
	 */
	void setStereotype(String value);

} // ModelElement
