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

import genericUMLDiagramModel.ModelElement;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * A command to show all the elements in the diagram
 * 
 * @author marguu&zaza
 *
 */
public class ShowAllCommand extends Command {

  protected List selected;
  
  public ShowAllCommand(List selected) {
	this.selected = selected;
	
	setLabel("show all");
  }

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	  for(int i=0; i<selected.size(); i++) {
		  AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) selected.get(i);
		  if(part.getModel() instanceof ModelElement) {
			  part.getFigure().setVisible(true);
			  
			  // Effettuo il refresh del diagramma
			  ((AbstractGraphicalEditPart)part.getParent()).getFigure().repaint();
		  }
	  }
  }
  
/**
 * @see org.eclipse.gef.commands.Command#redo()
 */
public void redo() {
  	execute();
  }

/**
 * @see org.eclipse.gef.commands.Command#undo()
 */
public void undo() {
	  	execute();
  }
}
