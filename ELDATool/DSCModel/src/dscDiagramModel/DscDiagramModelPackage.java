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

import genericUMLDiagramModel.GenericUMLDiagramModelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see dscDiagramModel.DscDiagramModelFactory
 * @model kind="package"
 * @generated
 */
public interface DscDiagramModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dscDiagramModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://dscDiagramModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dscDiagramModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DscDiagramModelPackage eINSTANCE = dscDiagramModel.impl.DscDiagramModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.DSCDiagramImpl <em>DSC Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.DSCDiagramImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getDSCDiagram()
	 * @generated
	 */
	int DSC_DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Model Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__MODEL_ELEMENTS = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MODEL_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Max ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__MAX_ID = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM__MAX_ID;

	/**
	 * The feature id for the '<em><b>Event File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__EVENT_FILE = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Guard File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__GUARD_FILE = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Action File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__ACTION_FILE = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Diagram Variables</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__DIAGRAM_VARIABLES = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Function File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM__FUNCTION_FILE = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the the '<em>DSC Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_DIAGRAM_FEATURE_COUNT = GenericUMLDiagramModelPackage.GENERIC_DIAGRAM_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.AnchorNoteToItemImpl <em>Anchor Note To Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.AnchorNoteToItemImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getAnchorNoteToItem()
	 * @generated
	 */
	int ANCHOR_NOTE_TO_ITEM = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__NAME = GenericUMLDiagramModelPackage.RELATIONSHIP__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__DESCRIPTION = GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__PARENT = GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__STEREOTYPE = GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__ID = GenericUMLDiagramModelPackage.RELATIONSHIP__ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__SOURCE = GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__TARGET = GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET;

	/**
	 * The feature id for the '<em><b>Bendpoints</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__BENDPOINTS = GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS;

	/**
	 * The feature id for the '<em><b>Connection Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM__CONNECTION_LABEL = GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL;

	/**
	 * The number of structural features of the the '<em>Anchor Note To Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANCHOR_NOTE_TO_ITEM_FEATURE_COUNT = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.TransitionImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = GenericUMLDiagramModelPackage.RELATIONSHIP__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__DESCRIPTION = GenericUMLDiagramModelPackage.RELATIONSHIP__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PARENT = GenericUMLDiagramModelPackage.RELATIONSHIP__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__STEREOTYPE = GenericUMLDiagramModelPackage.RELATIONSHIP__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ID = GenericUMLDiagramModelPackage.RELATIONSHIP__ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SOURCE = GenericUMLDiagramModelPackage.RELATIONSHIP__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TARGET = GenericUMLDiagramModelPackage.RELATIONSHIP__TARGET;

	/**
	 * The feature id for the '<em><b>Bendpoints</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__BENDPOINTS = GenericUMLDiagramModelPackage.RELATIONSHIP__BENDPOINTS;

	/**
	 * The feature id for the '<em><b>Connection Label</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__CONNECTION_LABEL = GenericUMLDiagramModelPackage.RELATIONSHIP__CONNECTION_LABEL;

	/**
	 * The feature id for the '<em><b>Transition ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TRANSITION_ID = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Event ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__EVENT_ID = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guard ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__GUARD_ID = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Action ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ACTION_ID = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Show Properties</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SHOW_PROPERTIES = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Show Transition ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SHOW_TRANSITION_ID = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Triggered By Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TRIGGERED_BY_EVENT = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = GenericUMLDiagramModelPackage.RELATIONSHIP_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.StartPointImpl <em>Start Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.StartPointImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getStartPoint()
	 * @generated
	 */
	int START_POINT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__NAME = GenericUMLDiagramModelPackage.CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__DESCRIPTION = GenericUMLDiagramModelPackage.CLASSIFIER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__PARENT = GenericUMLDiagramModelPackage.CLASSIFIER__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__STEREOTYPE = GenericUMLDiagramModelPackage.CLASSIFIER__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__ID = GenericUMLDiagramModelPackage.CLASSIFIER__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__LOCATION = GenericUMLDiagramModelPackage.CLASSIFIER__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__SIZE = GenericUMLDiagramModelPackage.CLASSIFIER__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__CONTAINER = GenericUMLDiagramModelPackage.CLASSIFIER__CONTAINER;

	/**
	 * The feature id for the '<em><b>Composite State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT__COMPOSITE_STATE = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Start Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_POINT_FEATURE_COUNT = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.ShallowHistoryImpl <em>Shallow History</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.ShallowHistoryImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getShallowHistory()
	 * @generated
	 */
	int SHALLOW_HISTORY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__NAME = GenericUMLDiagramModelPackage.CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__DESCRIPTION = GenericUMLDiagramModelPackage.CLASSIFIER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__PARENT = GenericUMLDiagramModelPackage.CLASSIFIER__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__STEREOTYPE = GenericUMLDiagramModelPackage.CLASSIFIER__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__ID = GenericUMLDiagramModelPackage.CLASSIFIER__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__LOCATION = GenericUMLDiagramModelPackage.CLASSIFIER__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__SIZE = GenericUMLDiagramModelPackage.CLASSIFIER__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__CONTAINER = GenericUMLDiagramModelPackage.CLASSIFIER__CONTAINER;

	/**
	 * The feature id for the '<em><b>Composite State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY__COMPOSITE_STATE = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Shallow History</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHALLOW_HISTORY_FEATURE_COUNT = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.DeepHistoryImpl <em>Deep History</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.DeepHistoryImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getDeepHistory()
	 * @generated
	 */
	int DEEP_HISTORY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__NAME = GenericUMLDiagramModelPackage.CLASSIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__DESCRIPTION = GenericUMLDiagramModelPackage.CLASSIFIER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__PARENT = GenericUMLDiagramModelPackage.CLASSIFIER__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.CLASSIFIER__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__STEREOTYPE = GenericUMLDiagramModelPackage.CLASSIFIER__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__ID = GenericUMLDiagramModelPackage.CLASSIFIER__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__LOCATION = GenericUMLDiagramModelPackage.CLASSIFIER__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__SIZE = GenericUMLDiagramModelPackage.CLASSIFIER__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__CONTAINER = GenericUMLDiagramModelPackage.CLASSIFIER__CONTAINER;

	/**
	 * The feature id for the '<em><b>Composite State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY__COMPOSITE_STATE = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the the '<em>Deep History</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEEP_HISTORY_FEATURE_COUNT = GenericUMLDiagramModelPackage.CLASSIFIER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link dscDiagramModel.impl.DSCStateImpl <em>DSC State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.impl.DSCStateImpl
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getDSCState()
	 * @generated
	 */
	int DSC_STATE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__NAME = GenericUMLDiagramModelPackage.CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__DESCRIPTION = GenericUMLDiagramModelPackage.CONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__PARENT = GenericUMLDiagramModelPackage.CONTAINER__PARENT;

	/**
	 * The feature id for the '<em><b>Source Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__SOURCE_RELATIONSHIPS = GenericUMLDiagramModelPackage.CONTAINER__SOURCE_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Target Relationships</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__TARGET_RELATIONSHIPS = GenericUMLDiagramModelPackage.CONTAINER__TARGET_RELATIONSHIPS;

	/**
	 * The feature id for the '<em><b>Stereotype</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__STEREOTYPE = GenericUMLDiagramModelPackage.CONTAINER__STEREOTYPE;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__ID = GenericUMLDiagramModelPackage.CONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__LOCATION = GenericUMLDiagramModelPackage.CONTAINER__LOCATION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__SIZE = GenericUMLDiagramModelPackage.CONTAINER__SIZE;

	/**
	 * The feature id for the '<em><b>Container</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__CONTAINER = GenericUMLDiagramModelPackage.CONTAINER__CONTAINER;

	/**
	 * The feature id for the '<em><b>Visual Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__VISUAL_ELEMENTS = GenericUMLDiagramModelPackage.CONTAINER__VISUAL_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Start Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__START_POINT = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shallow History</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__SHALLOW_HISTORY = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Deep History</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__DEEP_HISTORY = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__VARIABLES = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Is Simple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE__IS_SIMPLE = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the the '<em>DSC State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DSC_STATE_FEATURE_COUNT = GenericUMLDiagramModelPackage.CONTAINER_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '<em>Variable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.myDataTypes.TypeVariable
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 7;

	/**
	 * The meta object id for the '<em>Diagram Variable</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see dscDiagramModel.myDataTypes.TypeDiagramVariable
	 * @see dscDiagramModel.impl.DscDiagramModelPackageImpl#getDiagramVariable()
	 * @generated
	 */
	int DIAGRAM_VARIABLE = 8;


	/**
	 * Returns the meta object for class '{@link dscDiagramModel.DSCDiagram <em>DSC Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DSC Diagram</em>'.
	 * @see dscDiagramModel.DSCDiagram
	 * @generated
	 */
	EClass getDSCDiagram();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.DSCDiagram#getEventFile <em>Event File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event File</em>'.
	 * @see dscDiagramModel.DSCDiagram#getEventFile()
	 * @see #getDSCDiagram()
	 * @generated
	 */
	EAttribute getDSCDiagram_EventFile();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.DSCDiagram#getGuardFile <em>Guard File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guard File</em>'.
	 * @see dscDiagramModel.DSCDiagram#getGuardFile()
	 * @see #getDSCDiagram()
	 * @generated
	 */
	EAttribute getDSCDiagram_GuardFile();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.DSCDiagram#getActionFile <em>Action File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action File</em>'.
	 * @see dscDiagramModel.DSCDiagram#getActionFile()
	 * @see #getDSCDiagram()
	 * @generated
	 */
	EAttribute getDSCDiagram_ActionFile();

	/**
	 * Returns the meta object for the attribute list '{@link dscDiagramModel.DSCDiagram#getDiagramVariables <em>Diagram Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Diagram Variables</em>'.
	 * @see dscDiagramModel.DSCDiagram#getDiagramVariables()
	 * @see #getDSCDiagram()
	 * @generated
	 */
	EAttribute getDSCDiagram_DiagramVariables();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.DSCDiagram#getFunctionFile <em>Function File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function File</em>'.
	 * @see dscDiagramModel.DSCDiagram#getFunctionFile()
	 * @see #getDSCDiagram()
	 * @generated
	 */
	EAttribute getDSCDiagram_FunctionFile();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.AnchorNoteToItem <em>Anchor Note To Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Anchor Note To Item</em>'.
	 * @see dscDiagramModel.AnchorNoteToItem
	 * @generated
	 */
	EClass getAnchorNoteToItem();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see dscDiagramModel.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#getTransitionID <em>Transition ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transition ID</em>'.
	 * @see dscDiagramModel.Transition#getTransitionID()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_TransitionID();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#getEventID <em>Event ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event ID</em>'.
	 * @see dscDiagramModel.Transition#getEventID()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_EventID();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#getGuardID <em>Guard ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Guard ID</em>'.
	 * @see dscDiagramModel.Transition#getGuardID()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_GuardID();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#getActionID <em>Action ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Action ID</em>'.
	 * @see dscDiagramModel.Transition#getActionID()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ActionID();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#isShowProperties <em>Show Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Properties</em>'.
	 * @see dscDiagramModel.Transition#isShowProperties()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ShowProperties();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#isShowTransitionID <em>Show Transition ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Show Transition ID</em>'.
	 * @see dscDiagramModel.Transition#isShowTransitionID()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_ShowTransitionID();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.Transition#isTriggeredByEvent <em>Triggered By Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Triggered By Event</em>'.
	 * @see dscDiagramModel.Transition#isTriggeredByEvent()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_TriggeredByEvent();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.StartPoint <em>Start Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Point</em>'.
	 * @see dscDiagramModel.StartPoint
	 * @generated
	 */
	EClass getStartPoint();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.StartPoint#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite State</em>'.
	 * @see dscDiagramModel.StartPoint#getCompositeState()
	 * @see #getStartPoint()
	 * @generated
	 */
	EReference getStartPoint_CompositeState();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.ShallowHistory <em>Shallow History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shallow History</em>'.
	 * @see dscDiagramModel.ShallowHistory
	 * @generated
	 */
	EClass getShallowHistory();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.ShallowHistory#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite State</em>'.
	 * @see dscDiagramModel.ShallowHistory#getCompositeState()
	 * @see #getShallowHistory()
	 * @generated
	 */
	EReference getShallowHistory_CompositeState();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.DeepHistory <em>Deep History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deep History</em>'.
	 * @see dscDiagramModel.DeepHistory
	 * @generated
	 */
	EClass getDeepHistory();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.DeepHistory#getCompositeState <em>Composite State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Composite State</em>'.
	 * @see dscDiagramModel.DeepHistory#getCompositeState()
	 * @see #getDeepHistory()
	 * @generated
	 */
	EReference getDeepHistory_CompositeState();

	/**
	 * Returns the meta object for class '{@link dscDiagramModel.DSCState <em>DSC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DSC State</em>'.
	 * @see dscDiagramModel.DSCState
	 * @generated
	 */
	EClass getDSCState();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.DSCState#getStartPoint <em>Start Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start Point</em>'.
	 * @see dscDiagramModel.DSCState#getStartPoint()
	 * @see #getDSCState()
	 * @generated
	 */
	EReference getDSCState_StartPoint();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.DSCState#getShallowHistory <em>Shallow History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Shallow History</em>'.
	 * @see dscDiagramModel.DSCState#getShallowHistory()
	 * @see #getDSCState()
	 * @generated
	 */
	EReference getDSCState_ShallowHistory();

	/**
	 * Returns the meta object for the reference '{@link dscDiagramModel.DSCState#getDeepHistory <em>Deep History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Deep History</em>'.
	 * @see dscDiagramModel.DSCState#getDeepHistory()
	 * @see #getDSCState()
	 * @generated
	 */
	EReference getDSCState_DeepHistory();

	/**
	 * Returns the meta object for the attribute list '{@link dscDiagramModel.DSCState#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Variables</em>'.
	 * @see dscDiagramModel.DSCState#getVariables()
	 * @see #getDSCState()
	 * @generated
	 */
	EAttribute getDSCState_Variables();

	/**
	 * Returns the meta object for the attribute '{@link dscDiagramModel.DSCState#isIsSimple <em>Is Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Simple</em>'.
	 * @see dscDiagramModel.DSCState#isIsSimple()
	 * @see #getDSCState()
	 * @generated
	 */
	EAttribute getDSCState_IsSimple();

	/**
	 * Returns the meta object for data type '{@link dscDiagramModel.myDataTypes.TypeVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Variable</em>'.
	 * @see dscDiagramModel.myDataTypes.TypeVariable
	 * @model instanceClass="dscDiagramModel.myDataTypes.TypeVariable"
	 * @generated
	 */
	EDataType getVariable();

	/**
	 * Returns the meta object for data type '{@link dscDiagramModel.myDataTypes.TypeDiagramVariable <em>Diagram Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Diagram Variable</em>'.
	 * @see dscDiagramModel.myDataTypes.TypeDiagramVariable
	 * @model instanceClass="dscDiagramModel.myDataTypes.TypeDiagramVariable"
	 * @generated
	 */
	EDataType getDiagramVariable();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DscDiagramModelFactory getDscDiagramModelFactory();

} //DscDiagramModelPackage
