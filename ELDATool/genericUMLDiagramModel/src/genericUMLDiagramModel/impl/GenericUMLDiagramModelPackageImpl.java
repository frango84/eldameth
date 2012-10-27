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
package genericUMLDiagramModel.impl;

import genericUMLDiagramModel.AuxiliaryElement;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.GenericUMLDiagramModelFactory;
import genericUMLDiagramModel.GenericUMLDiagramModelPackage;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.AbsoluteBendpoint;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericUMLDiagramModelPackageImpl extends EPackageImpl implements GenericUMLDiagramModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass modelElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass classifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationshipEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass genericDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass containerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass noteEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass visualElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass auxiliaryElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass textLabelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass horizontalLineSeparatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass verticalLineSeparatorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionLabelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType pointEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType dimensionEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType absoluteBendpointEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see genericUMLDiagramModel.GenericUMLDiagramModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private GenericUMLDiagramModelPackageImpl() {
		super(eNS_URI, GenericUMLDiagramModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static GenericUMLDiagramModelPackage init() {
		if (isInited) return (GenericUMLDiagramModelPackage)EPackage.Registry.INSTANCE.getEPackage(GenericUMLDiagramModelPackage.eNS_URI);

		// Obtain or create and register package
		GenericUMLDiagramModelPackageImpl theGenericUMLDiagramModelPackage = (GenericUMLDiagramModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof GenericUMLDiagramModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new GenericUMLDiagramModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theGenericUMLDiagramModelPackage.createPackageContents();

		// Initialize created meta-data
		theGenericUMLDiagramModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theGenericUMLDiagramModelPackage.freeze();

		return theGenericUMLDiagramModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getModelElement() {
		return modelElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_Name() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_Description() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_Parent() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_SourceRelationships() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getModelElement_TargetRelationships() {
		return (EReference)modelElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_ID() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getModelElement_Stereotype() {
		return (EAttribute)modelElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getClassifier() {
		return classifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRelationship() {
		return relationshipEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_Source() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_Target() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRelationship_Bendpoints() {
		return (EAttribute)relationshipEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRelationship_ConnectionLabel() {
		return (EReference)relationshipEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGenericDiagram() {
		return genericDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGenericDiagram_ModelElements() {
		return (EReference)genericDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGenericDiagram_MaxID() {
		return (EAttribute)genericDiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContainer() {
		return containerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContainer_VisualElements() {
		return (EReference)containerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNote() {
		return noteEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNote_Comment() {
		return (EAttribute)noteEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVisualElement() {
		return visualElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualElement_Location() {
		return (EAttribute)visualElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVisualElement_Size() {
		return (EAttribute)visualElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVisualElement_Container() {
		return (EReference)visualElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAuxiliaryElement() {
		return auxiliaryElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTextLabel() {
		return textLabelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTextLabel_Text() {
		return (EAttribute)textLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHorizontalLineSeparator() {
		return horizontalLineSeparatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVerticalLineSeparator() {
		return verticalLineSeparatorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConnectionLabel() {
		return connectionLabelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionLabel_Offset() {
		return (EAttribute)connectionLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConnectionLabel_Relationship() {
		return (EReference)connectionLabelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConnectionLabel_IsVisible() {
		return (EAttribute)connectionLabelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPoint() {
		return pointEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDimension() {
		return dimensionEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getAbsoluteBendpoint() {
		return absoluteBendpointEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericUMLDiagramModelFactory getGenericUMLDiagramModelFactory() {
		return (GenericUMLDiagramModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		modelElementEClass = createEClass(MODEL_ELEMENT);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__NAME);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__DESCRIPTION);
		createEReference(modelElementEClass, MODEL_ELEMENT__PARENT);
		createEReference(modelElementEClass, MODEL_ELEMENT__SOURCE_RELATIONSHIPS);
		createEReference(modelElementEClass, MODEL_ELEMENT__TARGET_RELATIONSHIPS);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__STEREOTYPE);
		createEAttribute(modelElementEClass, MODEL_ELEMENT__ID);

		classifierEClass = createEClass(CLASSIFIER);

		relationshipEClass = createEClass(RELATIONSHIP);
		createEReference(relationshipEClass, RELATIONSHIP__SOURCE);
		createEReference(relationshipEClass, RELATIONSHIP__TARGET);
		createEAttribute(relationshipEClass, RELATIONSHIP__BENDPOINTS);
		createEReference(relationshipEClass, RELATIONSHIP__CONNECTION_LABEL);

		genericDiagramEClass = createEClass(GENERIC_DIAGRAM);
		createEReference(genericDiagramEClass, GENERIC_DIAGRAM__MODEL_ELEMENTS);
		createEAttribute(genericDiagramEClass, GENERIC_DIAGRAM__MAX_ID);

		containerEClass = createEClass(CONTAINER);
		createEReference(containerEClass, CONTAINER__VISUAL_ELEMENTS);

		noteEClass = createEClass(NOTE);
		createEAttribute(noteEClass, NOTE__COMMENT);

		visualElementEClass = createEClass(VISUAL_ELEMENT);
		createEAttribute(visualElementEClass, VISUAL_ELEMENT__LOCATION);
		createEAttribute(visualElementEClass, VISUAL_ELEMENT__SIZE);
		createEReference(visualElementEClass, VISUAL_ELEMENT__CONTAINER);

		auxiliaryElementEClass = createEClass(AUXILIARY_ELEMENT);

		textLabelEClass = createEClass(TEXT_LABEL);
		createEAttribute(textLabelEClass, TEXT_LABEL__TEXT);

		horizontalLineSeparatorEClass = createEClass(HORIZONTAL_LINE_SEPARATOR);

		verticalLineSeparatorEClass = createEClass(VERTICAL_LINE_SEPARATOR);

		connectionLabelEClass = createEClass(CONNECTION_LABEL);
		createEAttribute(connectionLabelEClass, CONNECTION_LABEL__OFFSET);
		createEReference(connectionLabelEClass, CONNECTION_LABEL__RELATIONSHIP);
		createEAttribute(connectionLabelEClass, CONNECTION_LABEL__IS_VISIBLE);

		// Create data types
		pointEDataType = createEDataType(POINT);
		dimensionEDataType = createEDataType(DIMENSION);
		absoluteBendpointEDataType = createEDataType(ABSOLUTE_BENDPOINT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Add supertypes to classes
		classifierEClass.getESuperTypes().add(this.getVisualElement());
		relationshipEClass.getESuperTypes().add(this.getModelElement());
		containerEClass.getESuperTypes().add(this.getVisualElement());
		noteEClass.getESuperTypes().add(this.getVisualElement());
		visualElementEClass.getESuperTypes().add(this.getModelElement());
		auxiliaryElementEClass.getESuperTypes().add(this.getVisualElement());
		textLabelEClass.getESuperTypes().add(this.getAuxiliaryElement());
		horizontalLineSeparatorEClass.getESuperTypes().add(this.getAuxiliaryElement());
		verticalLineSeparatorEClass.getESuperTypes().add(this.getAuxiliaryElement());
		connectionLabelEClass.getESuperTypes().add(this.getTextLabel());

		// Initialize classes and features; add operations and parameters
		initEClass(modelElementEClass, ModelElement.class, "ModelElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getModelElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelElement_Description(), ecorePackage.getEString(), "description", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_Parent(), this.getGenericDiagram(), this.getGenericDiagram_ModelElements(), "parent", null, 1, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_SourceRelationships(), this.getRelationship(), this.getRelationship_Source(), "sourceRelationships", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getModelElement_TargetRelationships(), this.getRelationship(), this.getRelationship_Target(), "targetRelationships", null, 0, -1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelElement_Stereotype(), ecorePackage.getEString(), "stereotype", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getModelElement_ID(), ecorePackage.getEString(), "ID", null, 0, 1, ModelElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classifierEClass, Classifier.class, "Classifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(relationshipEClass, Relationship.class, "Relationship", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationship_Source(), this.getModelElement(), this.getModelElement_SourceRelationships(), "source", null, 1, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationship_Target(), this.getModelElement(), this.getModelElement_TargetRelationships(), "target", null, 1, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelationship_Bendpoints(), this.getAbsoluteBendpoint(), "bendpoints", null, 0, -1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRelationship_ConnectionLabel(), this.getConnectionLabel(), this.getConnectionLabel_Relationship(), "connectionLabel", null, 1, 1, Relationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(genericDiagramEClass, GenericDiagram.class, "GenericDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGenericDiagram_ModelElements(), this.getModelElement(), this.getModelElement_Parent(), "modelElements", null, 0, -1, GenericDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGenericDiagram_MaxID(), ecorePackage.getELong(), "maxID", null, 0, 1, GenericDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(containerEClass, Container.class, "Container", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContainer_VisualElements(), this.getVisualElement(), this.getVisualElement_Container(), "visualElements", null, 0, -1, Container.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(noteEClass, Note.class, "Note", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNote_Comment(), ecorePackage.getEString(), "comment", null, 0, 1, Note.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(visualElementEClass, VisualElement.class, "VisualElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVisualElement_Location(), this.getPoint(), "location", null, 0, 1, VisualElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVisualElement_Size(), this.getDimension(), "size", null, 0, 1, VisualElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVisualElement_Container(), this.getContainer(), this.getContainer_VisualElements(), "container", null, 0, 1, VisualElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(auxiliaryElementEClass, AuxiliaryElement.class, "AuxiliaryElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(textLabelEClass, TextLabel.class, "TextLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTextLabel_Text(), ecorePackage.getEString(), "text", null, 0, 1, TextLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(horizontalLineSeparatorEClass, HorizontalLineSeparator.class, "HorizontalLineSeparator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(verticalLineSeparatorEClass, VerticalLineSeparator.class, "VerticalLineSeparator", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(connectionLabelEClass, ConnectionLabel.class, "ConnectionLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConnectionLabel_Offset(), this.getPoint(), "offset", null, 0, 1, ConnectionLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnectionLabel_Relationship(), this.getRelationship(), this.getRelationship_ConnectionLabel(), "relationship", null, 1, 1, ConnectionLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getConnectionLabel_IsVisible(), ecorePackage.getEBoolean(), "isVisible", null, 0, 1, ConnectionLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(pointEDataType, Point.class, "Point", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(dimensionEDataType, Dimension.class, "Dimension", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(absoluteBendpointEDataType, AbsoluteBendpoint.class, "AbsoluteBendpoint", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //GenericUMLDiagramModelPackageImpl
