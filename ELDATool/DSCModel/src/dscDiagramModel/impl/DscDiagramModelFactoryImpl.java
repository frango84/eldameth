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

import java.util.StringTokenizer;

import dscDiagramModel.*;

import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import dscDiagramModel.myDataTypes.TypeVariable;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DscDiagramModelFactoryImpl extends EFactoryImpl implements DscDiagramModelFactory {
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DscDiagramModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case DscDiagramModelPackage.DSC_DIAGRAM: return createDSCDiagram();
		case DscDiagramModelPackage.ANCHOR_NOTE_TO_ITEM: return createAnchorNoteToItem();
		case DscDiagramModelPackage.TRANSITION: return createTransition();
		case DscDiagramModelPackage.START_POINT: return createStartPoint();
		case DscDiagramModelPackage.SHALLOW_HISTORY: return createShallowHistory();
		case DscDiagramModelPackage.DEEP_HISTORY: return createDeepHistory();
		case DscDiagramModelPackage.DSC_STATE: return createDSCState();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case DscDiagramModelPackage.VARIABLE:
			return createVariableFromString(eDataType, initialValue);
		case DscDiagramModelPackage.DIAGRAM_VARIABLE:
			return createDiagramVariableFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case DscDiagramModelPackage.VARIABLE:
			return convertVariableToString(eDataType, instanceValue);
		case DscDiagramModelPackage.DIAGRAM_VARIABLE:
			return convertDiagramVariableToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DSCDiagram createDSCDiagram() {
		DSCDiagramImpl dscDiagram = new DSCDiagramImpl();
		return dscDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnchorNoteToItem createAnchorNoteToItem() {
		AnchorNoteToItemImpl anchorNoteToItem = new AnchorNoteToItemImpl();
		return anchorNoteToItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartPoint createStartPoint() {
		StartPointImpl startPoint = new StartPointImpl();
		return startPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ShallowHistory createShallowHistory() {
		ShallowHistoryImpl shallowHistory = new ShallowHistoryImpl();
		return shallowHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeepHistory createDeepHistory() {
		DeepHistoryImpl deepHistory = new DeepHistoryImpl();
		return deepHistory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DSCState createDSCState() {
		DSCStateImpl dscState = new DSCStateImpl();
		return dscState;
	}



	public TypeVariable createVariableFromString(EDataType eDataType, String initialValue) {
//		return (TypeVariable)super.createFromString(eDataType, initialValue);
		return (TypeVariable) myCreateFromString(eDataType, initialValue);
	}



	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVariableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	public TypeDiagramVariable createDiagramVariableFromString(EDataType eDataType, String initialValue) {
//		return (TypeDiagramVariable)super.createFromString(eDataType, initialValue);
		return (TypeDiagramVariable) myCreateFromString2(eDataType, initialValue);	
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDiagramVariableToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DscDiagramModelPackage getDscDiagramModelPackage() {
		return (DscDiagramModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	public static DscDiagramModelPackage getPackage() {
		return DscDiagramModelPackage.eINSTANCE;
	}

	public TypeVariable myCreateFromString(EDataType eDataType, String initialValue)
	{
		StringTokenizer st = new StringTokenizer(initialValue,",");
		String first =st.nextToken();
		first=first.substring(first.indexOf('=')+1);
		String second = st.nextToken();
		second=second.substring(second.indexOf('=')+1);

		if (st.hasMoreTokens())
		{
			String third=st.nextToken();
			third=third.substring(third.indexOf('=')+1);
			return new TypeVariable(first, second,third);
		}     

		return new TypeVariable(first, second);

	}

	public TypeDiagramVariable myCreateFromString2(EDataType eDataType, String initialValue)
	{
		StringTokenizer st = new StringTokenizer(initialValue,",");
		String first =st.nextToken();
		first=first.substring(first.indexOf('=')+1);

		String second = st.nextToken();
		second=second.substring(second.indexOf('=')+1);



		if (st.hasMoreTokens())
		{

			String thirdComplete=st.nextToken();
			String third=thirdComplete.substring(thirdComplete.indexOf('=')+1);

			if (thirdComplete.startsWith(" bePresent"))
				return new TypeDiagramVariable(first, second,"", true);
			else if (st.hasMoreTokens())
			{
				return new TypeDiagramVariable(first, second,third, true);
			}
			return new TypeDiagramVariable(first, second,third, false);
		}
		return new TypeDiagramVariable(first, second);
	}

} //DscDiagramModelFactoryImpl
