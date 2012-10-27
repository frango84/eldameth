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



import genericUMLDiagramEditor.commands.ContainerCreateInCommand;
import genericUMLDiagramEditor.commands.CreateFileName;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import eldaEditor.dialogs.StatePropertiesInputDialog;
import eldaEditor.editparts.DSCStateEditPart;

/**
 *	Classe che permette la creazione di un elemento direttamente all’interno di un contenitore
 * @author samuele
 */

public class DSCStateCreateInCommand extends ContainerCreateInCommand {
private EditPart part;
private boolean automaticResize=false;
private boolean exactName=false;
private DSCDiagram diagram;

/**
 * Costruttore
 * @param parent	Variabile che contiene un riferimento al contenitore nel quale sarà creato l’elemento
 * @param req		Variabile che contiene un riferimento alla richiesta di creazione effettuata dall’utente
 * @param newBounds	Margini dell’elemento da creare nel contenitore
 * @param fig		Figura dell'elemento (contenitore o diagramma) in cui è contenuto il contenitore 
 * in cui si sta creando l'elemento
 */
public DSCStateCreateInCommand(DSCState container, CreateRequest req, Rectangle newBounds,IFigure fig, EditPart part) {
	super(container,req,newBounds,fig);
	
	this.part = part;
	this.diagram= (DSCDiagram) EcoreUtil.getRootContainer(container);
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	newShape = (VisualElement) request.getNewObject();
	
	// Per gli elementi che eredito da GenericEditor
	// richiamo il metodo execute di quest'editor
	if (newShape instanceof HorizontalLineSeparator || newShape instanceof VerticalLineSeparator || newShape instanceof Note || newShape instanceof TextLabel) {
		super.execute();
		// Tali elementi vengono gestiti dal GenericEditor quindi è come
		// se non li mettessi dallo StatechartEditor
		cancelled = true;
	}
	else
		newShape.setSize(request.getSize()); // might be null!

	if(!cancelled) {
		fig.translateToRelative(request.getLocation());
		Point p=new Point(request.getLocation().x-newBounds.getLocation().x,request.getLocation().y-newBounds.getLocation().y);
		newShape.setLocation(p);
		if (newShape instanceof DSCState){
			// Verifico di non aver rimpicciolito troppo lo stato
			final Dimension MIN_SIZE_DEFAULT = new Dimension(75, 55);
			Dimension dim = newShape.getSize();
			if(dim.width<MIN_SIZE_DEFAULT.width) dim.width=MIN_SIZE_DEFAULT.width;
			if(dim.height<MIN_SIZE_DEFAULT.height) dim.height=MIN_SIZE_DEFAULT.height;
			newShape.setSize(dim);

			DSCState state = (DSCState)newShape;
			while (!exactName)
			{
			exactName=true;
			StatePropertiesInputDialog dialog=new StatePropertiesInputDialog(new Shell(),(DSCState)newShape,true,diagram);
			dialog.open();
			if(!dialog.getCancelled()) {
				if(dialog.getStateName()!=null)    		state.setName(dialog.getStateName());
				else		state.setName("");
				if(dialog.getStereotype()!=null)   		state.setStereotype(dialog.getStereotype());
				else		state.setStereotype("");
				newShape.setDescription(dialog.getDescription());

				Iterator i = parent.getVisualElements().iterator();
			    while (i.hasNext()) {
			    	VisualElement m =(VisualElement) i.next();
			        if ((m instanceof DSCState)&&(state.getName().equals(((DSCState)m).getName()))) {
			        	MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"ERROR MESSAGE","Attention, state: "+state.getName()+" already exists in this diagram!");
			        	exactName=false;
			        	break;
			        }
			    }
			    
				cancelled = false;
			}
			else
				cancelled = true;
		}
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
			if (newShape instanceof DSCState && ((DSCState)newShape).isIsSimple()==false)
			{
			PopulateCompositeCommandDSC populateCompositeState= new PopulateCompositeCommandDSC ((DSCState) newShape, diagram);
			populateCompositeState.execute();
			}
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

public void undo() {
	parent.getVisualElements().remove(newShape);
	diagram.getModelElements().remove(newShape);
}
}
