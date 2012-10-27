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
package dscDiagramModel.impl;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.DscDiagramModelFactory;
import dscDiagramModel.DscDiagramModelPackage;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;

import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import dscDiagramModel.myDataTypes.TypeVariable;

import genericUMLDiagramModel.GenericUMLDiagramModelPackage;

import genericUMLDiagramModel.impl.GenericUMLDiagramModelPackageImpl;

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
public class DscDiagramModelPackageImpl extends EPackageImpl implements DscDiagramModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dscDiagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass anchorNoteToItemEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass transitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startPointEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass shallowHistoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deepHistoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dscStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType variableEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType diagramVariableEDataType = null;

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
	 * @see dscDiagramModel.DscDiagramModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DscDiagramModelPackageImpl() {
		super(eNS_URI, DscDiagramModelFactory.eINSTANCE);
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
	public static DscDiagramModelPackage init() {
		if (isInited) return (DscDiagramModelPackage)EPackage.Registry.INSTANCE.getEPackage(DscDiagramModelPackage.eNS_URI);

		// Obtain or create and register package
		DscDiagramModelPackageImpl theDscDiagramModelPackage = (DscDiagramModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DscDiagramModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DscDiagramModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		GenericUMLDiagramModelPackageImpl.init();

		// Create package meta-data objects
		theDscDiagramModelPackage.createPackageContents();

		// Initialize created meta-data
		theDscDiagramModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDscDiagramModelPackage.freeze();

		return theDscDiagramModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDSCDiagram() {
		return dscDiagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCDiagram_EventFile() {
		return (EAttribute)dscDiagramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCDiagram_GuardFile() {
		return (EAttribute)dscDiagramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCDiagram_ActionFile() {
		return (EAttribute)dscDiagramEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCDiagram_DiagramVariables() {
		return (EAttribute)dscDiagramEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCDiagram_FunctionFile() {
		return (EAttribute)dscDiagramEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnchorNoteToItem() {
		return anchorNoteToItemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTransition() {
		return transitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_TransitionID() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_EventID() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_GuardID() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_ActionID() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_ShowProperties() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_ShowTransitionID() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTransition_TriggeredByEvent() {
		return (EAttribute)transitionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartPoint() {
		return startPointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStartPoint_CompositeState() {
		return (EReference)startPointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getShallowHistory() {
		return shallowHistoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getShallowHistory_CompositeState() {
		return (EReference)shallowHistoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeepHistory() {
		return deepHistoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDeepHistory_CompositeState() {
		return (EReference)deepHistoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDSCState() {
		return dscStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDSCState_StartPoint() {
		return (EReference)dscStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDSCState_ShallowHistory() {
		return (EReference)dscStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDSCState_DeepHistory() {
		return (EReference)dscStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCState_Variables() {
		return (EAttribute)dscStateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDSCState_IsSimple() {
		return (EAttribute)dscStateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVariable() {
		return variableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getDiagramVariable() {
		return diagramVariableEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DscDiagramModelFactory getDscDiagramModelFactory() {
		return (DscDiagramModelFactory)getEFactoryInstance();
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
		dscDiagramEClass = createEClass(DSC_DIAGRAM);
		createEAttribute(dscDiagramEClass, DSC_DIAGRAM__EVENT_FILE);
		createEAttribute(dscDiagramEClass, DSC_DIAGRAM__GUARD_FILE);
		createEAttribute(dscDiagramEClass, DSC_DIAGRAM__ACTION_FILE);
		createEAttribute(dscDiagramEClass, DSC_DIAGRAM__DIAGRAM_VARIABLES);
		createEAttribute(dscDiagramEClass, DSC_DIAGRAM__FUNCTION_FILE);

		anchorNoteToItemEClass = createEClass(ANCHOR_NOTE_TO_ITEM);

		transitionEClass = createEClass(TRANSITION);
		createEAttribute(transitionEClass, TRANSITION__TRANSITION_ID);
		createEAttribute(transitionEClass, TRANSITION__EVENT_ID);
		createEAttribute(transitionEClass, TRANSITION__GUARD_ID);
		createEAttribute(transitionEClass, TRANSITION__ACTION_ID);
		createEAttribute(transitionEClass, TRANSITION__SHOW_PROPERTIES);
		createEAttribute(transitionEClass, TRANSITION__SHOW_TRANSITION_ID);
		createEAttribute(transitionEClass, TRANSITION__TRIGGERED_BY_EVENT);

		startPointEClass = createEClass(START_POINT);
		createEReference(startPointEClass, START_POINT__COMPOSITE_STATE);

		shallowHistoryEClass = createEClass(SHALLOW_HISTORY);
		createEReference(shallowHistoryEClass, SHALLOW_HISTORY__COMPOSITE_STATE);

		deepHistoryEClass = createEClass(DEEP_HISTORY);
		createEReference(deepHistoryEClass, DEEP_HISTORY__COMPOSITE_STATE);

		dscStateEClass = createEClass(DSC_STATE);
		createEReference(dscStateEClass, DSC_STATE__START_POINT);
		createEReference(dscStateEClass, DSC_STATE__SHALLOW_HISTORY);
		createEReference(dscStateEClass, DSC_STATE__DEEP_HISTORY);
		createEAttribute(dscStateEClass, DSC_STATE__VARIABLES);
		createEAttribute(dscStateEClass, DSC_STATE__IS_SIMPLE);

		// Create data types
		variableEDataType = createEDataType(VARIABLE);
		diagramVariableEDataType = createEDataType(DIAGRAM_VARIABLE);
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

		// Obtain other dependent packages
		GenericUMLDiagramModelPackageImpl theGenericUMLDiagramModelPackage = (GenericUMLDiagramModelPackageImpl)EPackage.Registry.INSTANCE.getEPackage(GenericUMLDiagramModelPackage.eNS_URI);

		// Add supertypes to classes
		dscDiagramEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getGenericDiagram());
		anchorNoteToItemEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getRelationship());
		transitionEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getRelationship());
		startPointEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getClassifier());
		shallowHistoryEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getClassifier());
		deepHistoryEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getClassifier());
		dscStateEClass.getESuperTypes().add(theGenericUMLDiagramModelPackage.getContainer());

		// Initialize classes and features; add operations and parameters
		initEClass(dscDiagramEClass, DSCDiagram.class, "DSCDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDSCDiagram_EventFile(), ecorePackage.getEString(), "eventFile", null, 0, 1, DSCDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCDiagram_GuardFile(), ecorePackage.getEString(), "guardFile", null, 0, 1, DSCDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCDiagram_ActionFile(), ecorePackage.getEString(), "actionFile", null, 0, 1, DSCDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCDiagram_DiagramVariables(), this.getDiagramVariable(), "diagramVariables", null, 0, -1, DSCDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCDiagram_FunctionFile(), ecorePackage.getEString(), "functionFile", null, 0, 1, DSCDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(anchorNoteToItemEClass, AnchorNoteToItem.class, "AnchorNoteToItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(transitionEClass, Transition.class, "Transition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTransition_TransitionID(), ecorePackage.getEString(), "transitionID", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_EventID(), ecorePackage.getEString(), "eventID", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_GuardID(), ecorePackage.getEString(), "guardID", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_ActionID(), ecorePackage.getEString(), "actionID", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_ShowProperties(), ecorePackage.getEBoolean(), "showProperties", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_ShowTransitionID(), ecorePackage.getEBoolean(), "showTransitionID", null, 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTransition_TriggeredByEvent(), ecorePackage.getEBoolean(), "triggeredByEvent", "true", 0, 1, Transition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(startPointEClass, StartPoint.class, "StartPoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStartPoint_CompositeState(), this.getDSCState(), this.getDSCState_StartPoint(), "compositeState", null, 1, 1, StartPoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(shallowHistoryEClass, ShallowHistory.class, "ShallowHistory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getShallowHistory_CompositeState(), this.getDSCState(), this.getDSCState_ShallowHistory(), "compositeState", null, 1, 1, ShallowHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(deepHistoryEClass, DeepHistory.class, "DeepHistory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDeepHistory_CompositeState(), this.getDSCState(), this.getDSCState_DeepHistory(), "compositeState", null, 1, 1, DeepHistory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dscStateEClass, DSCState.class, "DSCState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDSCState_StartPoint(), this.getStartPoint(), this.getStartPoint_CompositeState(), "startPoint", null, 1, 1, DSCState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDSCState_ShallowHistory(), this.getShallowHistory(), this.getShallowHistory_CompositeState(), "shallowHistory", null, 0, 1, DSCState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDSCState_DeepHistory(), this.getDeepHistory(), this.getDeepHistory_CompositeState(), "deepHistory", null, 0, 1, DSCState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCState_Variables(), this.getVariable(), "Variables", null, 0, -1, DSCState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDSCState_IsSimple(), ecorePackage.getEBoolean(), "isSimple", null, 0, 1, DSCState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize data types
		initEDataType(variableEDataType, TypeVariable.class, "Variable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(diagramVariableEDataType, TypeDiagramVariable.class, "DiagramVariable", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //DscDiagramModelPackageImpl
