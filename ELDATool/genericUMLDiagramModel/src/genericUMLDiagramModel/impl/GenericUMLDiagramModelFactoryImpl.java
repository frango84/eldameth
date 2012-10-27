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

import java.util.StringTokenizer;

import genericUMLDiagramModel.*;

import org.eclipse.draw2d.AbsoluteBendpoint;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

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
public class GenericUMLDiagramModelFactoryImpl extends EFactoryImpl implements GenericUMLDiagramModelFactory
{
	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public GenericUMLDiagramModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GenericUMLDiagramModelPackage.MODEL_ELEMENT: return createModelElement();
			case GenericUMLDiagramModelPackage.CLASSIFIER: return createClassifier();
			case GenericUMLDiagramModelPackage.RELATIONSHIP: return createRelationship();
			case GenericUMLDiagramModelPackage.GENERIC_DIAGRAM: return createGenericDiagram();
			case GenericUMLDiagramModelPackage.CONTAINER: return createContainer();
			case GenericUMLDiagramModelPackage.NOTE: return createNote();
			case GenericUMLDiagramModelPackage.VISUAL_ELEMENT: return createVisualElement();
			case GenericUMLDiagramModelPackage.AUXILIARY_ELEMENT: return createAuxiliaryElement();
			case GenericUMLDiagramModelPackage.TEXT_LABEL: return createTextLabel();
			case GenericUMLDiagramModelPackage.HORIZONTAL_LINE_SEPARATOR: return createHorizontalLineSeparator();
			case GenericUMLDiagramModelPackage.VERTICAL_LINE_SEPARATOR: return createVerticalLineSeparator();
			case GenericUMLDiagramModelPackage.CONNECTION_LABEL: return createConnectionLabel();
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
			case GenericUMLDiagramModelPackage.POINT:
				return createPointFromString(eDataType, initialValue);
			case GenericUMLDiagramModelPackage.DIMENSION:
				return createDimensionFromString(eDataType, initialValue);
			case GenericUMLDiagramModelPackage.ABSOLUTE_BENDPOINT:
				return createAbsoluteBendpointFromString(eDataType, initialValue);
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
			case GenericUMLDiagramModelPackage.POINT:
				return convertPointToString(eDataType, instanceValue);
			case GenericUMLDiagramModelPackage.DIMENSION:
				return convertDimensionToString(eDataType, instanceValue);
			case GenericUMLDiagramModelPackage.ABSOLUTE_BENDPOINT:
				return convertAbsoluteBendpointToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public ModelElement createModelElement() {
		ModelElementImpl modelElement = new ModelElementImpl();
		return modelElement;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Classifier createClassifier() {
		ClassifierImpl classifier = new ClassifierImpl();
		return classifier;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Relationship createRelationship() {
		RelationshipImpl relationship = new RelationshipImpl();
		return relationship;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public GenericDiagram createGenericDiagram() {
		GenericDiagramImpl genericDiagram = new GenericDiagramImpl();
		return genericDiagram;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Container createContainer() {
		ContainerImpl container = new ContainerImpl();
		return container;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Note createNote() {
		NoteImpl note = new NoteImpl();
		return note;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public VisualElement createVisualElement() {
		VisualElementImpl visualElement = new VisualElementImpl();
		return visualElement;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public AuxiliaryElement createAuxiliaryElement() {
		AuxiliaryElementImpl auxiliaryElement = new AuxiliaryElementImpl();
		return auxiliaryElement;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public TextLabel createTextLabel() {
		TextLabelImpl textLabel = new TextLabelImpl();
		return textLabel;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public HorizontalLineSeparator createHorizontalLineSeparator() {
		HorizontalLineSeparatorImpl horizontalLineSeparator = new HorizontalLineSeparatorImpl();
		return horizontalLineSeparator;
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public VerticalLineSeparator createVerticalLineSeparator() {
		VerticalLineSeparatorImpl verticalLineSeparator = new VerticalLineSeparatorImpl();
		return verticalLineSeparator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConnectionLabel createConnectionLabel() {
		ConnectionLabelImpl connectionLabel = new ConnectionLabelImpl();
		return connectionLabel;
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   */
  public Point createPointFromString(EDataType eDataType, String initialValue)
  {
    //return (Point)super.createFromString(eDataType, initialValue);
    return stringToPoint(initialValue);
  }

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertPointToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   */
  public Dimension createDimensionFromString(EDataType eDataType, String initialValue)
  {
   // return (Dimension)super.createFromString(eDataType, initialValue);
    return stringToDimension(initialValue);
  }

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertDimensionToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * 
   */
  public AbsoluteBendpoint createAbsoluteBendpointFromString(EDataType eDataType, String initialValue)
  {
    return stringToBendpoint(initialValue);
  	//return (AbsoluteBendpoint)super.createFromString(eDataType, initialValue);
  }

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String convertAbsoluteBendpointToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public GenericUMLDiagramModelPackage getGenericUMLDiagramModelPackage() {
		return (GenericUMLDiagramModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
  public static GenericUMLDiagramModelPackage getPackage() {
		return GenericUMLDiagramModelPackage.eINSTANCE;
	}

  public static Point stringToPoint (String initialValue){
    StringTokenizer st = new StringTokenizer(initialValue,",");
  	String first =st.nextToken();
  	first=first.substring(6);
  	String second = st.nextToken();
  	second=second.substring(1,second.length()-1);
  	Integer x = new Integer(first);
  	Integer y = new Integer(second);
  	return new Point(x.intValue(),y.intValue());
  	//	return("POINT:\nfirst = "+first.substring(6)+"\nsecond ="+second.substring(0,second.length()-1));
  	}
    public static Dimension stringToDimension (String initialValue){
      StringTokenizer st = new StringTokenizer(initialValue,",");
    	String first =st.nextToken();
    	first=first.substring(10);
  	String second = st.nextToken();
    	second=second.substring(1,second.length()-1);
  	Integer weight = new Integer(first);
  	Integer height = new Integer(second);
  	return new Dimension(weight.intValue(),height.intValue());
  	//	return("DIMENSION:\nfirst = "+first.substring(10)+"\nsecond ="+second.substring(0,second.length()-1));
    	}
    
    public static AbsoluteBendpoint stringToBendpoint (String initialValue){
        StringTokenizer st = new StringTokenizer(initialValue,",");
      	String first =st.nextToken();
      	first=first.substring(6);
      	String second = st.nextToken();
      	second=second.substring(1,second.length()-1);
      	Integer x = new Integer(first);
      	Integer y = new Integer(second);
      	return new AbsoluteBendpoint(x.intValue(),y.intValue());
      	//	return("POINT:\nfirst = "+first.substring(6)+"\nsecond ="+second.substring(0,second.length()-1));
      	}

} //GenericUMLDiagramModelFactoryImpl
