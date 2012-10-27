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

package eldaEditor.commands;

import genericUMLDiagramEditor.commands.CreateFileName;
import genericUMLDiagramEditor.commands.VisualElementCreateCommand;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.StartPoint;
import eldaEditor.dialogs.StatePropertiesInputDialog;
import eldaEditor.editparts.DSCStateEditPart;

/**
 * A command to add a Shape to a ShapeDiagram.
 * The command can be undone or redone.
 *
 *@author samuele
 */
public class VisualElementCreateCommandDSC extends VisualElementCreateCommand {
private EditPart part;
private boolean automaticResize=false;

/**
 * Create a command that will add a new VisualElement to a ShapesDiagram.
 * @param parent the GenericDiagram that will hold the new element
 * @param req     a request to create a new VisualElement
 * @throws IllegalArgumentException if any parameter is null, or the request
 * 						  does not provide a new Shape instance
 */
public VisualElementCreateCommandDSC(GenericDiagram parent, CreateRequest req, IFigure fig, EditPart part) {
	super(parent, req, fig);
	
	this.part = part;
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	// Obtain the new Shape instance from the request.
	// This causes the factory stored in the request to create a new instance.
	// The factory is supplied in the palette-tool-entry
	newShape = (VisualElement) request.getNewObject();
	// Get desired location and size from the request
	if (newShape instanceof HorizontalLineSeparator || newShape instanceof VerticalLineSeparator ||
			newShape instanceof Note || newShape instanceof TextLabel) {
		super.execute();
		// Tali elementi vengono gestiti dal GenericEditor quindi è come
		// se non li mettessi dallo StatechartEditor
		cancelled = true;
	}
//	else if (newShape instanceof Join) {
//		if(((Join)newShape).isIsHorizontal())
//			newShape.setSize(new Dimension(60,6));
//		else
//			newShape.setSize(new Dimension(6,60));
//	}
//	else if (newShape instanceof Fork) {
//		if(((Fork)newShape).isIsHorizontal())
//			newShape.setSize(new Dimension(60,6));
//		else
//			newShape.setSize(new Dimension(6,60));
//	}
	else if (newShape instanceof StartPoint)
		newShape.setSize(new Dimension(25,25));
//	else if (newShape instanceof EndPoint)
//		newShape.setSize(new Dimension(27,27));
	else
		newShape.setSize(request.getSize()); // might be null!

	if(!cancelled) {
		fig.translateToRelative(request.getLocation());
		newShape.setLocation(request.getLocation());
		if (newShape instanceof DSCState){
			// Verifico di non aver rimpicciolito troppo lo stato
			final Dimension MIN_SIZE_DEFAULT = new Dimension(75, 55);
			Dimension dim = newShape.getSize();
			if(dim.width<MIN_SIZE_DEFAULT.width) dim.width=MIN_SIZE_DEFAULT.width;
			if(dim.height<MIN_SIZE_DEFAULT.height) dim.height=MIN_SIZE_DEFAULT.height;
			newShape.setSize(dim);

			StatePropertiesInputDialog dialog=new StatePropertiesInputDialog(new Shell(),(DSCState)newShape,true,(DSCDiagram) parent);
			dialog.open();
			cancelled = dialog.getCancelled();
			if(!cancelled) {
				automaticResize = dialog.getAutomaticResize();

				Iterator i = parent.getModelElements().iterator();
			    while (i.hasNext()) {
			        ModelElement m =(ModelElement) i.next();
			        if ((m instanceof DSCState)&&(((DSCState)newShape).getName().equals(m.getName()))) {
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attention, state: "+((DSCState)newShape).getName()+"already exists in this diagram!");
			        	break;
			        }
			    }
			}
		}

		if(!cancelled) {
			// Cerco il nome del file
			String strFile = CreateFileName.getName();

			// Setto l'identificativo del nuovo oggetto al più alto identificativo
			// degli oggetti contenuti dal padre incrementato di una unità
			newShape.setID(strFile + (parent.getMaxID()+1));
			
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
			
			shapeAdded=true;
			redo();
		}
	}
}

public void redo() {
	super.redo();
	
	if(newShape instanceof DSCState) {
		// Setto la scelta dell'utente riguardo l'automaticResize
		DSCStateEditPart statePart = (DSCStateEditPart) part.getViewer().getEditPartRegistry().get(newShape);
		statePart.setAutomaticResize(automaticResize);
	}
}
}
