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

package eldaEditor.editpolicies;

import genericUMLDiagramEditor.commands.NullCommand;
import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import eldaEditor.commands.DSCStateCreateInCommand;
import eldaEditor.commands.StateAddCommand;
import eldaEditor.commands.StateSetConstraintCommand;
import eldaEditor.commands.VisualElementSetConstraintCommandDSC;
import eldaEditor.editparts.DSCStateEditPart;
import eldaEditor.editparts.VisualElementEditPartDSC;
import eldaEditor.figures.StateFigure;

/**
 * Classe che ritorna i comandi  necessari alla gestione 
 * degli elementi che si trovano all’interno di un contenitore
 * @author samuele
 */
public class CompositeStateXYLayoutEditPolicy extends XYLayoutEditPolicy {
	/**
	 * Costruttore
	 * @param layout Layout associato al contenitore
	 */
	public	CompositeStateXYLayoutEditPolicy(XYLayout layout) {
		if (layout == null) {
			throw new IllegalArgumentException();
		}
		setXyLayout(layout);
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		//non posson spostare gli elementi indicati
		if (getHost() instanceof DSCStateEditPart && 
				(child.getModel() instanceof StartPoint ||
				child.getModel() instanceof ShallowHistory  ||
				child.getModel() instanceof DeepHistory
				) 
				 ) {
			return null;
		}
		if (getHost() instanceof DSCStateEditPart && child.getModel() instanceof ConnectionLabel )
		{
			return new NullCommand();
		}
		//non sposto gli state che sono connessi con uno start point
		if (getHost() instanceof DSCStateEditPart && child.getModel() instanceof DSCState )
		{	
			DSCState model= (DSCState ) child.getModel();
			//per tutte le relazioni in cui l'elemento è 
			for (int i=0; i<model.getTargetRelationships().size(); i++)
			{
				if (model.getTargetRelationships().get(i) instanceof Transition &&
					((Transition)model.getTargetRelationships().get(i)).getSource() instanceof StartPoint)
					return null;
			}
		}
		//non sposto gli state che sono connessi con uno start point
		if (getHost() instanceof DSCStateEditPart && child.getModel() instanceof DSCState )
		{	
			DSCState model= (DSCState ) child.getModel();
			//per tutte le relazioni in cui l'elemento è 
			for (int i=0; i<model.getTargetRelationships().size(); i++)
			{
				if (model.getTargetRelationships().get(i) instanceof Transition &&
					(((Transition)model.getTargetRelationships().get(i)).getSource() instanceof DeepHistory||
					((Transition)model.getTargetRelationships().get(i)).getSource() instanceof ShallowHistory))
					return null;
			}
		}
		if ((getHost() instanceof DSCStateEditPart) && child.getModel() instanceof VisualElement ) {
			VisualElement childModel = (VisualElement)child.getModel();
			Container pack = (Container) getHost().getModel();
			AbstractGraphicalEditPart pack_part = (AbstractGraphicalEditPart) child.getViewer().getEditPartRegistry().get(child.getModel());
			return new StateAddCommand(childModel, pack,(Rectangle) constraint, pack_part);
		}
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request,
			EditPart child, Object constraint) {
		if (child instanceof VisualElementEditPartDSC&& constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new VisualElementSetConstraintCommandDSC(
					(VisualElement) child.getModel(), request, (Rectangle) constraint,(VisualElementEditPartDSC) child);
		}
		if (child instanceof DSCStateEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new StateSetConstraintCommand(
					(VisualElement) child.getModel(), request, (Rectangle) constraint,(DSCStateEditPart) child);
		}
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		// not used in this example
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Object childClass = request.getNewObjectType();
		DSCStateEditPart host = (DSCStateEditPart)getHost();
		IFigure fig=getHostFigure();
		if (childClass == DSCState.class 
			|| childClass == HorizontalLineSeparator.class || childClass == VerticalLineSeparator.class
			|| childClass == TextLabel.class ||  childClass == Note.class
			||childClass == StartPoint.class  
			||childClass == Transition.class || childClass == AnchorNoteToItem.class)
		{
			Rectangle r;
			r=((StateFigure)host.getFigure()).getBounds();
			return new DSCStateCreateInCommand((DSCState)host.getModel(), request,r,fig,getHost());
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
	 */
	protected Command getDeleteDependantCommand(Request request) {
		// not used in this example
		return null;
	}
}
