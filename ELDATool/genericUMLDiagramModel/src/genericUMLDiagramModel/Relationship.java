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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Relationship</b></em>'. <!-- end-user-doc --> <p> The following features are supported: <ul> <li> {@link genericUMLDiagramModel.Relationship#getSource  <em>Source</em>} </li> <li> {@link genericUMLDiagramModel.Relationship#getTarget  <em>Target</em>} </li> <li> {@link genericUMLDiagramModel.Relationship#getBendpoints  <em>Bendpoints</em>} </li> <li> {@link genericUMLDiagramModel.Relationship#getStereotype  <em>Stereotype</em>} </li> </ul> </p>
 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getRelationship()
 * @model  
 * @generated
 */
public interface Relationship extends ModelElement{

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.ModelElement#getSourceRelationships <em>Source Relationships</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(ModelElement)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getRelationship_Source()
	 * @see genericUMLDiagramModel.ModelElement#getSourceRelationships
	 * @model opposite="sourceRelationships" required="true"
	 * @generated
	 */
	ModelElement getSource();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.Relationship#getSource  <em>Source</em>} ' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(ModelElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.ModelElement#getTargetRelationships <em>Target Relationships</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ModelElement)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getRelationship_Target()
	 * @see genericUMLDiagramModel.ModelElement#getTargetRelationships
	 * @model opposite="targetRelationships" required="true"
	 * @generated
	 */
	ModelElement getTarget();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.Relationship#getTarget  <em>Target</em>} ' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ModelElement value);

	/**
	 * Returns the value of the '<em><b>Bendpoints</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.draw2d.AbsoluteBendpoint}.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Bendpoints</em>' attribute list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Bendpoints</em>' attribute list.
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getRelationship_Bendpoints()
	 * @model type="org.eclipse.draw2d.AbsoluteBendpoint" dataType="genericUMLDiagramModel.AbsoluteBendpoint"
	 * @generated
	 */
  EList getBendpoints();

	/**
	 * Returns the value of the '<em><b>Connection Label</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link genericUMLDiagramModel.ConnectionLabel#getRelationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Connection Label</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connection Label</em>' containment reference.
	 * @see #setConnectionLabel(ConnectionLabel)
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#getRelationship_ConnectionLabel()
	 * @see genericUMLDiagramModel.ConnectionLabel#getRelationship
	 * @model opposite="relationship" containment="true" required="true"
	 * @generated
	 */
	ConnectionLabel getConnectionLabel();

	/**
	 * Sets the value of the ' {@link genericUMLDiagramModel.Relationship#getConnectionLabel  <em>Connection Label</em>} ' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value  the new value of the '<em>Connection Label</em>' containment reference.
	 * @see #getConnectionLabel()
	 * @generated
	 */
	void setConnectionLabel(ConnectionLabel value);

} // Relationship
