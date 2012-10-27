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

//import genericUMLDiagramModel.HorizontalLineSeparator;
//import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
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

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import eldaEditor.commands.DSCDiagramAddCommand;
import eldaEditor.commands.DSCStateCreateCommand;
import eldaEditor.commands.StateSetConstraintCommand;
import eldaEditor.commands.VisualElementCreateCommandDSC;
import eldaEditor.commands.VisualElementSetConstraintCommandDSC;
import eldaEditor.editparts.DSCDiagramEditPart;
import eldaEditor.editparts.DSCStateEditPart;
import eldaEditor.editparts.VisualElementEditPartDSC;

/**
 * @author samuele
 *
 */
public class DSCDiagramXYLayoutEditPolicy extends XYLayoutEditPolicy {
	/** 
	 * Create a new instance of this edit policy.
	 * @param layout a non-null XYLayout instance. This should be the layout of the editpart's 
	 *              figure where this instance is installed.
	 * @throws IllegalArgumentException if layout is null 
	 * @see ActivityDiagramEditPart#createEditPolicies()
	 */
	public DSCDiagramXYLayoutEditPolicy(XYLayout layout) {
		if (layout == null) {
			throw new IllegalArgumentException();
		}
		setXyLayout(layout);
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		
		if (getHost() instanceof DSCDiagramEditPart && (child.getModel() instanceof StartPoint ||
				child.getModel() instanceof ShallowHistory  ||child.getModel() instanceof DeepHistory) ) {
			return null;
		}
		
		//non sposto gli state che sono connessi con uno start point
		if (getHost() instanceof DSCDiagramEditPart && child.getModel() instanceof DSCState )
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
		
//		non sposto gli state che sono connessi con un history
		if (getHost() instanceof DSCDiagramEditPart && child.getModel() instanceof DSCState )
		{	
			DSCState model= (DSCState ) child.getModel();
			//per tutte le relazioni in cui l'elemento è 
			for (int i=0; i<model.getTargetRelationships().size(); i++)
			{
				if (model.getTargetRelationships().get(i) instanceof Transition &&
					(((Transition)model.getTargetRelationships().get(i)).getSource() instanceof DeepHistory ||
							((Transition)model.getTargetRelationships().get(i)).getSource() instanceof ShallowHistory))
					return null;
			}
		}
		
		if (getHost() instanceof DSCDiagramEditPart && child.getModel() instanceof VisualElement) {
			VisualElement childModel = (VisualElement)child.getModel();
			DSCDiagram diagram = (DSCDiagram) getHost().getModel();
			DSCState pack = (DSCState ) childModel.getContainer();
			AbstractGraphicalEditPart pack_part = (AbstractGraphicalEditPart) child.getViewer().getEditPartRegistry().get(child.getModel());
			
			return new DSCDiagramAddCommand(childModel, pack, diagram,(Rectangle) constraint, pack_part);
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
			return new StateSetConstraintCommand((VisualElement) child.getModel(), request, (Rectangle) constraint,(DSCStateEditPart) child);
		}
		
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		// not used in this example
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		IFigure fig=getHostFigure();
		Object childClass = request.getNewObjectType();
		DSCDiagram diagram=(DSCDiagram) getHost().getModel();

		if (childClass == TextLabel.class || childClass == Note.class)
			return new VisualElementCreateCommandDSC(diagram, request,fig,getHost());
		else if (childClass == DSCState.class )
		{
		 return new DSCStateCreateCommand(diagram, request,fig,getHost());
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

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.XYLayoutEditPolicy#showSizeOnDropFeedback(org.eclipse.gef.requests.CreateRequest)
	 */
	protected void showSizeOnDropFeedback(CreateRequest request) {
		super.showSizeOnDropFeedback(request);
	}
}
