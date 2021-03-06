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

package genericUMLDiagramEditor.editPolicies;

import genericUMLDiagramEditor.commands.ContainerAddCommand;
import genericUMLDiagramEditor.commands.ContainerCreateInCommand;
import genericUMLDiagramEditor.commands.ContainerSetConstraintCommand;
import genericUMLDiagramEditor.commands.VisualElementSetConstraintCommand;
import genericUMLDiagramEditor.editParts.ContainerEditPart;
import genericUMLDiagramEditor.editParts.VisualElementEditPart;
import genericUMLDiagramEditor.figures.ContainerFigure;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.VerticalLineSeparator;

import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

/**
 * Return the commands to manage the elements contained by a Container
 * 
 * @author marguu&zaza
 *
 */
public class ContainerXYLayoutEditPolicy extends XYLayoutEditPolicy {
	/** 
	 * Create a new instance of this edit policy.
	 * @param layout a non-null XYLayout instance. This should be the layout of the editpart's 
	 *              figure where this instance is installed.
	 * @throws IllegalArgumentException if layout is null 
	 * @see ActivityDiagramEditPart#createEditPolicies()
	 */
	public	ContainerXYLayoutEditPolicy(XYLayout layout) {
		if (layout == null) {
			throw new IllegalArgumentException();
		}
		setXyLayout(layout);
	}
		
	/**
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createAddCommand(EditPart child, Object constraint) {
		if (getHost() instanceof ContainerEditPart && child.getModel() instanceof VisualElement ) {
			VisualElement childModel = (VisualElement)child.getModel();
			Container pack = (Container) getHost().getModel();
			GenericDiagram diagram = pack.getParent();
			
			return new ContainerAddCommand(childModel, pack, diagram,(Rectangle) constraint);
		}
		else
			// not used in this example
			return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest, org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(ChangeBoundsRequest request, EditPart child, Object constraint) {
		if (child instanceof VisualElementEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new VisualElementSetConstraintCommand(
					(VisualElement) child.getModel(), request, (Rectangle) constraint,(VisualElementEditPart) child);
		}
		if (child instanceof ContainerEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new ContainerSetConstraintCommand(
					(VisualElement) child.getModel(), request, (Rectangle) constraint,(ContainerEditPart) child);
		}
		
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	/**
	 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		// not used in this example
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	protected Command getCreateCommand(CreateRequest request) {
		Object childClass = request.getNewObjectType();
		ContainerEditPart host = (ContainerEditPart)getHost();
		IFigure fig=getHostFigure();
		if (childClass == Classifier.class ||childClass == Container.class || childClass == HorizontalLineSeparator.class ||
			childClass == VerticalLineSeparator.class || childClass == TextLabel.class ||  childClass == Note.class) 
		{
			Rectangle r=((ContainerFigure)host.getFigure()).getBounds();
			return new ContainerCreateInCommand((Container)host.getModel(), request,r,fig);
		}
		return null;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
	 */
	protected Command getDeleteDependantCommand(Request request) {
		// not used in this example
		return null;
	}
}
