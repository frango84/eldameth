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

package genericUMLDiagramEditor.editor;

import genericUMLDiagramModel.*;

import org.eclipse.gef.requests.CreationFactory;

/**
 * Create the instances of the objects of the model
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramModelCreationFactory implements CreationFactory {
	
	protected Class targetClass;
	protected GenericUMLDiagramModelFactory factory;
	
	public GenericDiagramModelCreationFactory( Class targetClass ) {
		this.targetClass = targetClass;
		factory=GenericUMLDiagramModelFactory.eINSTANCE;
	}
	
	/**
	 * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
	 */
	public Object getNewObject() {
		Object		result = null;
		if( targetClass.equals( Classifier.class ) )
			result = factory.createClassifier();
		else if (targetClass.equals(Note.class))
			result = factory.createNote();
		else if (targetClass.equals(Container.class))
			result = factory.createContainer();
		else if( targetClass.equals( TextLabel.class ) ) 
			result = factory.createTextLabel();
		else if( targetClass.equals( HorizontalLineSeparator.class ) ) 
			result = factory.createHorizontalLineSeparator();
		else if( targetClass.equals( VerticalLineSeparator.class ) ) 
			result = factory.createVerticalLineSeparator();
		else if( targetClass.equals( Relationship.class ) ) 
			result = factory.createRelationship();	
			
		return result;
	}

	/**
	 * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
	 */
	public Object getObjectType() {
		return targetClass;
	}
}
