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

package genericUMLDiagramEditor.commands;

import java.util.Iterator;

import genericUMLDiagramEditor.dialogs.ContainerPropertiesInputDialog;
import genericUMLDiagramEditor.dialogs.DiagramVisualElementPropertiesInputDialog;
import genericUMLDiagramEditor.dialogs.NotePropertiesInputDialog;
import genericUMLDiagramEditor.dialogs.TextLabelPropertiesInputDialog;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.HorizontalLineSeparator;

import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * A command to create a new VisualElement into a Container
 * 
 * @author marguu&zaza
 */
public class ContainerCreateInCommand extends Command {
/** The new shape. */ 
protected VisualElement newShape;
protected final Rectangle newBounds;
/** ShapeDiagram to add to. */
protected final Container parent;
/** A request to create a new Shape. */
protected final CreateRequest request;
/** True, if newShape was added to parent. */
protected boolean shapeAdded;
protected IFigure fig;

protected final GenericDiagram diagram;
protected boolean cancelled = false;

public ContainerCreateInCommand(Container parent, CreateRequest req,Rectangle newBounds,IFigure fig) {
	if (parent == null || req == null || !(req.getNewObject() instanceof VisualElement)) {
		throw new IllegalArgumentException();
	}
	
	this.fig=fig;
	this.parent = parent;
	this.diagram = (GenericDiagram) EcoreUtil.getRootContainer(parent);
	this.request = req;
	this.newBounds = newBounds;
	setLabel("element creation");
}

/**
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {
	if(parent instanceof Container && request.getNewObject() instanceof Note )
		return false;
	else
		return true;
}

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	// Obtain the new Shape instance from the request.
	// This causes the factory stored in the request to create a new instance.
	// The factory is supplied in the palette-tool-entry, see
	newShape = (VisualElement) request.getNewObject();	
	// Get desired location and size from the request
	if (newShape instanceof HorizontalLineSeparator)
		newShape.setSize(new Dimension(200,10)); 
	if (newShape instanceof TextLabel)
		newShape.setSize(new Dimension(100,20)); 
	if (newShape instanceof Container)
		newShape.setSize(new Dimension(300,200)); 
	if (newShape instanceof VerticalLineSeparator)
		newShape.setSize(new Dimension(10,200)); 
	else
		newShape.setSize(request.getSize()); // might be null!
	
	fig.translateToRelative(request.getLocation());
	Point p=new Point(request.getLocation().x-newBounds.getLocation().x,request.getLocation().y-newBounds.getLocation().y);
	newShape.setLocation(p);
	
	if (newShape instanceof Classifier) {
		DiagramVisualElementPropertiesInputDialog dialog=new DiagramVisualElementPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,(Classifier)newShape);
		dialog.open();
		if(!dialog.getCancelled()) {
			Iterator i = parent.getVisualElements().iterator();
		    while (i.hasNext()) {
		        VisualElement m =(VisualElement) i.next();
		        if ((m instanceof Classifier)&&(newShape.getName().equals(m.getName())))
		        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, elemento: "+newShape.getName()+" già esistente nel contenitore!");
		    }
			cancelled = false;
		}
		else
			cancelled = true;			
		}
		else if (newShape instanceof Container){
			ContainerPropertiesInputDialog dialog=new ContainerPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,(Container)newShape);
			dialog.open();
			if(!dialog.getCancelled()) {
				Iterator i = parent.getVisualElements().iterator();
			    while (i.hasNext()) {
			        VisualElement m =(VisualElement) i.next();
			        if ((m instanceof Container)&&(newShape.getName().equals(m.getName())))
			        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Attenzione, contenitore: "+newShape.getName()+" già esistente nel contenitore!");
			    }
				cancelled = false;
			}
			else
				cancelled = true;
		}
		else if (newShape instanceof Note){
			NotePropertiesInputDialog dialog=new NotePropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,(Note)newShape);
			dialog.open();
			if(!dialog.getCancelled()) {
				cancelled = false;
			}
			else
				cancelled=true;
		}
		else if (newShape instanceof TextLabel){
			TextLabelPropertiesInputDialog dialog=new TextLabelPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,(TextLabel)newShape);
			dialog.open();
			if(!dialog.getCancelled())
				cancelled = false;
			else
				cancelled=true;
		}

	if(!cancelled) {
		// Cerco il nome del file
		String strFile = CreateFileName.getName();

		// Setto l'identificativo del nuovo oggetto al più alto identificativo
		// degli oggetti contenuti dal padre incrementato di una unità
		newShape.setID(strFile + (diagram.getMaxID()+1));
		
		// Incremento il maxID del padre
		diagram.setMaxID(diagram.getMaxID()+1);
		
		shapeAdded=true;
		redo();
	}
}

/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
	newShape.setContainer(parent);
}

/**
 * @see org.eclipse.gef.commands.Command#canUndo()
 */
public boolean canUndo() {
	return shapeAdded;
}

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	parent.getVisualElements().remove(newShape);
}
}
