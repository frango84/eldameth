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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent <ul> <li>each class,</li> <li>each feature of each class,</li> <li>each enum,</li> <li>and each data type</li> </ul> <!-- end-user-doc -->
 * @see genericUMLDiagramModel.GenericUMLDiagramModelFactory
 * @model  kind="package"
 * @generated
 */
public interface GenericUMLDiagramModelPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "genericUMLDiagramModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://genericUMLDiagramModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "genericUMLDiagramModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericUMLDiagramModelPackage eINSTANCE = genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.ModelElementImpl <em>Model Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.ModelElementImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getModelElement()
	 * @generated
	 */
	int MODEL_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__PARENT = 2;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__SOURCE_RELATIONSHIPS = 3;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__TARGET_RELATIONSHIPS = 4;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__STEREOTYPE = 5;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT__ID = 6;

	/**
	 * The number of structural features of the the '<em>Model Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_ELEMENT_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.VisualElementImpl <em>Visual Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.VisualElementImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getVisualElement()
	 * @generated
	 */
	int VISUAL_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__NAME = MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__DESCRIPTION = MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__PARENT = MODEL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__SOURCE_RELATIONSHIPS = MODEL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__TARGET_RELATIONSHIPS = MODEL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__STEREOTYPE = MODEL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__ID = MODEL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__LOCATION = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__SIZE = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT__CONTAINER = MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the the '<em>Visual Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VISUAL_ELEMENT_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.ClassifierImpl <em>Classifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.ClassifierImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getClassifier()
	 * @generated
	 */
	int CLASSIFIER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__NAME = VISUAL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__DESCRIPTION = VISUAL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__PARENT = VISUAL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__SOURCE_RELATIONSHIPS = VISUAL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__TARGET_RELATIONSHIPS = VISUAL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__STEREOTYPE = VISUAL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__ID = VISUAL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__LOCATION = VISUAL_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__SIZE = VISUAL_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER__CONTAINER = VISUAL_ELEMENT__CONTAINER;

	/**
	 * The number of structural features of the the '<em>Classifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASSIFIER_FEATURE_COUNT = VISUAL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.RelationshipImpl <em>Relationship</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.RelationshipImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getRelationship()
	 * @generated
	 */
	int RELATIONSHIP = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__NAME = MODEL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__DESCRIPTION = MODEL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__PARENT = MODEL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__SOURCE_RELATIONSHIPS = MODEL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__TARGET_RELATIONSHIPS = MODEL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__STEREOTYPE = MODEL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__ID = MODEL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__SOURCE = MODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__TARGET = MODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Bendpoints</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__BENDPOINTS = MODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Connection Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP__CONNECTION_LABEL = MODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the the '<em>Relationship</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATIONSHIP_FEATURE_COUNT = MODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.GenericDiagramImpl <em>Generic Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.GenericDiagramImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getGenericDiagram()
	 * @generated
	 */
	int GENERIC_DIAGRAM = 3;

	/**
	 * The feature id for the '<em><b>Model Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DIAGRAM__MODEL_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Max ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DIAGRAM__MAX_ID = 1;

	/**
	 * The number of structural features of the the '<em>Generic Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_DIAGRAM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.ContainerImpl <em>Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.ContainerImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getContainer()
	 * @generated
	 */
	int CONTAINER = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__NAME = VISUAL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__DESCRIPTION = VISUAL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__PARENT = VISUAL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__SOURCE_RELATIONSHIPS = VISUAL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__TARGET_RELATIONSHIPS = VISUAL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__STEREOTYPE = VISUAL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__ID = VISUAL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__LOCATION = VISUAL_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__SIZE = VISUAL_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__CONTAINER = VISUAL_ELEMENT__CONTAINER;

	/**
	 * The feature id for the '<em><b>Visual Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER__VISUAL_ELEMENTS = VISUAL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTAINER_FEATURE_COUNT = VISUAL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.NoteImpl <em>Note</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.NoteImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getNote()
	 * @generated
	 */
	int NOTE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__NAME = VISUAL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__DESCRIPTION = VISUAL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__PARENT = VISUAL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__SOURCE_RELATIONSHIPS = VISUAL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__TARGET_RELATIONSHIPS = VISUAL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__STEREOTYPE = VISUAL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__ID = VISUAL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__LOCATION = VISUAL_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__SIZE = VISUAL_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__CONTAINER = VISUAL_ELEMENT__CONTAINER;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE__COMMENT = VISUAL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Note</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOTE_FEATURE_COUNT = VISUAL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.AuxiliaryElementImpl <em>Auxiliary Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.AuxiliaryElementImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getAuxiliaryElement()
	 * @generated
	 */
	int AUXILIARY_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__NAME = VISUAL_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__DESCRIPTION = VISUAL_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__PARENT = VISUAL_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__SOURCE_RELATIONSHIPS = VISUAL_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__TARGET_RELATIONSHIPS = VISUAL_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__STEREOTYPE = VISUAL_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__ID = VISUAL_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__LOCATION = VISUAL_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__SIZE = VISUAL_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT__CONTAINER = VISUAL_ELEMENT__CONTAINER;

	/**
	 * The number of structural features of the the '<em>Auxiliary Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUXILIARY_ELEMENT_FEATURE_COUNT = VISUAL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.TextLabelImpl <em>Text Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.TextLabelImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getTextLabel()
	 * @generated
	 */
	int TEXT_LABEL = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__NAME = AUXILIARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__DESCRIPTION = AUXILIARY_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__PARENT = AUXILIARY_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__SOURCE_RELATIONSHIPS = AUXILIARY_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__TARGET_RELATIONSHIPS = AUXILIARY_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__STEREOTYPE = AUXILIARY_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__ID = AUXILIARY_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__LOCATION = AUXILIARY_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__SIZE = AUXILIARY_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__CONTAINER = AUXILIARY_ELEMENT__CONTAINER;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL__TEXT = AUXILIARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Text Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LABEL_FEATURE_COUNT = AUXILIARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.HorizontalLineSeparatorImpl <em>Horizontal Line Separator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.HorizontalLineSeparatorImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getHorizontalLineSeparator()
	 * @generated
	 */
	int HORIZONTAL_LINE_SEPARATOR = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__NAME = AUXILIARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__DESCRIPTION = AUXILIARY_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__PARENT = AUXILIARY_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS = AUXILIARY_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS = AUXILIARY_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__STEREOTYPE = AUXILIARY_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__ID = AUXILIARY_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__LOCATION = AUXILIARY_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__SIZE = AUXILIARY_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR__CONTAINER = AUXILIARY_ELEMENT__CONTAINER;

	/**
	 * The number of structural features of the the '<em>Horizontal Line Separator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HORIZONTAL_LINE_SEPARATOR_FEATURE_COUNT = AUXILIARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.VerticalLineSeparatorImpl <em>Vertical Line Separator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.VerticalLineSeparatorImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getVerticalLineSeparator()
	 * @generated
	 */
	int VERTICAL_LINE_SEPARATOR = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__NAME = AUXILIARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__DESCRIPTION = AUXILIARY_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__PARENT = AUXILIARY_ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__SOURCE_RELATIONSHIPS = AUXILIARY_ELEMENT__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__TARGET_RELATIONSHIPS = AUXILIARY_ELEMENT__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__STEREOTYPE = AUXILIARY_ELEMENT__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__ID = AUXILIARY_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__LOCATION = AUXILIARY_ELEMENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__SIZE = AUXILIARY_ELEMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR__CONTAINER = AUXILIARY_ELEMENT__CONTAINER;

	/**
	 * The number of structural features of the the '<em>Vertical Line Separator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERTICAL_LINE_SEPARATOR_FEATURE_COUNT = AUXILIARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link genericUMLDiagramModel.impl.ConnectionLabelImpl <em>Connection Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see genericUMLDiagramModel.impl.ConnectionLabelImpl
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getConnectionLabel()
	 * @generated
	 */
	int CONNECTION_LABEL = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__NAME = TEXT_LABEL__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__DESCRIPTION = TEXT_LABEL__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__PARENT = TEXT_LABEL__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__SOURCE_RELATIONSHIPS = TEXT_LABEL__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__TARGET_RELATIONSHIPS = TEXT_LABEL__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__STEREOTYPE = TEXT_LABEL__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__ID = TEXT_LABEL__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__LOCATION = TEXT_LABEL__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__SIZE = TEXT_LABEL__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__CONTAINER = TEXT_LABEL__CONTAINER;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__TEXT = TEXT_LABEL__TEXT;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__OFFSET = TEXT_LABEL_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Relationship</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__RELATIONSHIP = TEXT_LABEL_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Is Visible</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL__IS_VISIBLE = TEXT_LABEL_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the the '<em>Connection Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_LABEL_FEATURE_COUNT = TEXT_LABEL_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '<em>Point</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.draw2d.geometry.Point
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 12;

	/**
	 * The meta object id for the '<em>Dimension</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.draw2d.geometry.Dimension
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 13;

	/**
	 * The meta object id for the '<em>Absolute Bendpoint</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.draw2d.AbsoluteBendpoint
	 * @see genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl#getAbsoluteBendpoint()
	 * @generated
	 */
	int ABSOLUTE_BENDPOINT = 14;


	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.ModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Element</em>'.
	 * @see genericUMLDiagramModel.ModelElement
	 * @generated
	 */
	EClass getModelElement();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ModelElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getName()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ModelElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getDescription()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Description();

	/**
	 * Returns the meta object for the container reference '{@link genericUMLDiagramModel.ModelElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getParent()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_Parent();

	/**
	 * Returns the meta object for the reference list '{@link genericUMLDiagramModel.ModelElement#getSourceRelationships <em>Source Relationships</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source Relationships</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getSourceRelationships()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_SourceRelationships();

	/**
	 * Returns the meta object for the reference list '{@link genericUMLDiagramModel.ModelElement#getTargetRelationships <em>Target Relationships</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Target Relationships</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getTargetRelationships()
	 * @see #getModelElement()
	 * @generated
	 */
	EReference getModelElement_TargetRelationships();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ModelElement#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getID()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_ID();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ModelElement#getStereotype <em>Stereotype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stereotype</em>'.
	 * @see genericUMLDiagramModel.ModelElement#getStereotype()
	 * @see #getModelElement()
	 * @generated
	 */
	EAttribute getModelElement_Stereotype();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.Classifier <em>Classifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Classifier</em>'.
	 * @see genericUMLDiagramModel.Classifier
	 * @generated
	 */
	EClass getClassifier();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.Relationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relationship</em>'.
	 * @see genericUMLDiagramModel.Relationship
	 * @generated
	 */
	EClass getRelationship();

	/**
	 * Returns the meta object for the reference '{@link genericUMLDiagramModel.Relationship#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see genericUMLDiagramModel.Relationship#getSource()
	 * @see #getRelationship()
	 * @generated
	 */
	EReference getRelationship_Source();

	/**
	 * Returns the meta object for the reference '{@link genericUMLDiagramModel.Relationship#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see genericUMLDiagramModel.Relationship#getTarget()
	 * @see #getRelationship()
	 * @generated
	 */
	EReference getRelationship_Target();

	/**
	 * Returns the meta object for the attribute list '{@link genericUMLDiagramModel.Relationship#getBendpoints <em>Bendpoints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Bendpoints</em>'.
	 * @see genericUMLDiagramModel.Relationship#getBendpoints()
	 * @see #getRelationship()
	 * @generated
	 */
	EAttribute getRelationship_Bendpoints();

	/**
	 * Returns the meta object for the containment reference '{@link genericUMLDiagramModel.Relationship#getConnectionLabel <em>Connection Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection Label</em>'.
	 * @see genericUMLDiagramModel.Relationship#getConnectionLabel()
	 * @see #getRelationship()
	 * @generated
	 */
	EReference getRelationship_ConnectionLabel();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.GenericDiagram <em>Generic Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Diagram</em>'.
	 * @see genericUMLDiagramModel.GenericDiagram
	 * @generated
	 */
	EClass getGenericDiagram();

	/**
	 * Returns the meta object for the containment reference list '{@link genericUMLDiagramModel.GenericDiagram#getModelElements <em>Model Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Model Elements</em>'.
	 * @see genericUMLDiagramModel.GenericDiagram#getModelElements()
	 * @see #getGenericDiagram()
	 * @generated
	 */
	EReference getGenericDiagram_ModelElements();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.GenericDiagram#getMaxID <em>Max ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max ID</em>'.
	 * @see genericUMLDiagramModel.GenericDiagram#getMaxID()
	 * @see #getGenericDiagram()
	 * @generated
	 */
	EAttribute getGenericDiagram_MaxID();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.Container <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Container</em>'.
	 * @see genericUMLDiagramModel.Container
	 * @generated
	 */
	EClass getContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link genericUMLDiagramModel.Container#getVisualElements <em>Visual Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Visual Elements</em>'.
	 * @see genericUMLDiagramModel.Container#getVisualElements()
	 * @see #getContainer()
	 * @generated
	 */
	EReference getContainer_VisualElements();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.Note <em>Note</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Note</em>'.
	 * @see genericUMLDiagramModel.Note
	 * @generated
	 */
	EClass getNote();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.Note#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see genericUMLDiagramModel.Note#getComment()
	 * @see #getNote()
	 * @generated
	 */
	EAttribute getNote_Comment();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.VisualElement <em>Visual Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Visual Element</em>'.
	 * @see genericUMLDiagramModel.VisualElement
	 * @generated
	 */
	EClass getVisualElement();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.VisualElement#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see genericUMLDiagramModel.VisualElement#getLocation()
	 * @see #getVisualElement()
	 * @generated
	 */
	EAttribute getVisualElement_Location();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.VisualElement#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see genericUMLDiagramModel.VisualElement#getSize()
	 * @see #getVisualElement()
	 * @generated
	 */
	EAttribute getVisualElement_Size();

	/**
	 * Returns the meta object for the container reference '{@link genericUMLDiagramModel.VisualElement#getContainer <em>Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Container</em>'.
	 * @see genericUMLDiagramModel.VisualElement#getContainer()
	 * @see #getVisualElement()
	 * @generated
	 */
	EReference getVisualElement_Container();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.AuxiliaryElement <em>Auxiliary Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Auxiliary Element</em>'.
	 * @see genericUMLDiagramModel.AuxiliaryElement
	 * @generated
	 */
	EClass getAuxiliaryElement();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.TextLabel <em>Text Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Label</em>'.
	 * @see genericUMLDiagramModel.TextLabel
	 * @generated
	 */
	EClass getTextLabel();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.TextLabel#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see genericUMLDiagramModel.TextLabel#getText()
	 * @see #getTextLabel()
	 * @generated
	 */
	EAttribute getTextLabel_Text();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.HorizontalLineSeparator <em>Horizontal Line Separator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Horizontal Line Separator</em>'.
	 * @see genericUMLDiagramModel.HorizontalLineSeparator
	 * @generated
	 */
	EClass getHorizontalLineSeparator();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.VerticalLineSeparator <em>Vertical Line Separator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vertical Line Separator</em>'.
	 * @see genericUMLDiagramModel.VerticalLineSeparator
	 * @generated
	 */
	EClass getVerticalLineSeparator();

	/**
	 * Returns the meta object for class '{@link genericUMLDiagramModel.ConnectionLabel <em>Connection Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Label</em>'.
	 * @see genericUMLDiagramModel.ConnectionLabel
	 * @generated
	 */
	EClass getConnectionLabel();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ConnectionLabel#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see genericUMLDiagramModel.ConnectionLabel#getOffset()
	 * @see #getConnectionLabel()
	 * @generated
	 */
	EAttribute getConnectionLabel_Offset();

	/**
	 * Returns the meta object for the container reference '{@link genericUMLDiagramModel.ConnectionLabel#getRelationship <em>Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Relationship</em>'.
	 * @see genericUMLDiagramModel.ConnectionLabel#getRelationship()
	 * @see #getConnectionLabel()
	 * @generated
	 */
	EReference getConnectionLabel_Relationship();

	/**
	 * Returns the meta object for the attribute '{@link genericUMLDiagramModel.ConnectionLabel#isIsVisible <em>Is Visible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Visible</em>'.
	 * @see genericUMLDiagramModel.ConnectionLabel#isIsVisible()
	 * @see #getConnectionLabel()
	 * @generated
	 */
	EAttribute getConnectionLabel_IsVisible();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.draw2d.geometry.Point <em>Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Point</em>'.
	 * @see org.eclipse.draw2d.geometry.Point
	 * @model instanceClass="org.eclipse.draw2d.geometry.Point"
	 * @generated
	 */
	EDataType getPoint();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.draw2d.geometry.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Dimension</em>'.
	 * @see org.eclipse.draw2d.geometry.Dimension
	 * @model instanceClass="org.eclipse.draw2d.geometry.Dimension"
	 * @generated
	 */
	EDataType getDimension();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.draw2d.AbsoluteBendpoint <em>Absolute Bendpoint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Absolute Bendpoint</em>'.
	 * @see org.eclipse.draw2d.AbsoluteBendpoint
	 * @model instanceClass="org.eclipse.draw2d.AbsoluteBendpoint"
	 * @generated
	 */
	EDataType getAbsoluteBendpoint();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GenericUMLDiagramModelFactory getGenericUMLDiagramModelFactory();

} //GenericUMLDiagramModelPackage
