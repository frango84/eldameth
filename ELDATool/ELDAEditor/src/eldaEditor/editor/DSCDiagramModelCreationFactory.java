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


package eldaEditor.editor;


import org.eclipse.gef.requests.CreationFactory;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCState;
import dscDiagramModel.DscDiagramModelFactory;
import dscDiagramModel.Transition;

/**
 * @author samuele
 *
 */
public class DSCDiagramModelCreationFactory implements CreationFactory {
	private Class targetClass;
	private DscDiagramModelFactory factory=DscDiagramModelFactory.eINSTANCE;
	private boolean isSimple;
	
	public DSCDiagramModelCreationFactory( Class targetClass ) {
		this.targetClass = targetClass;
	}
	
	public DSCDiagramModelCreationFactory( Class targetClass, boolean isSimple) {
		this.targetClass = targetClass;
		this.isSimple = isSimple;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.requests.CreationFactory#getNewObject()
	 */
	public Object getNewObject() {
		Object	result = null;
//		if( targetClass.equals( StartPoint.class ) )
	//		result = factory.createStartPoint();
//		else if( targetClass.equals( EndPoint.class ) )
//			result = factory.createEndPoint();
//		else if( targetClass.equals( Join.class ) ) {
//			if(isHorizontal) {
//				result = factory.createJoin();
//				((Join) result).setIsHorizontal(true);
//			}
//			else {
//				result = factory.createJoin();
//				((Join) result).setIsHorizontal(false);
//			}
//		}
//		else if( targetClass.equals( Fork.class ) ) {
//			if(isHorizontal) {
//				result = factory.createFork();
//				((Fork) result).setIsHorizontal(true);
//			}
//			else {
//				result = factory.createFork();
//				((Fork) result).setIsHorizontal(false);
//			}
//		}
		 if (targetClass.equals(DSCState.class))
			{
			 result = factory.createDSCState();
			 ((DSCState)result).setIsSimple(isSimple);
			}
		else if (targetClass.equals(DSCState.class))
			{
			result=factory.createDSCState();
			((DSCState)result).setIsSimple(isSimple);
			}
		else if( targetClass.equals( AnchorNoteToItem.class ) ) 
			result = factory.createAnchorNoteToItem();
		else if( targetClass.equals( Transition.class ) )
				result = factory.createTransition();
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
	 */
	public Object getObjectType() {
		return targetClass;
	}
}
