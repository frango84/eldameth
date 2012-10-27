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

import genericUMLDiagramModel.Relationship;

import org.eclipse.gef.commands.Command;

/**
 * Change the ShowLabel property of the passed Relationship
 * 
 * @author marguu&zaza
 *
 */
public class ChangeShowLabelCommand extends Command {

  protected boolean newValue;
  protected boolean oldValue;
  protected Relationship model;
  
  public ChangeShowLabelCommand(Relationship model, boolean newValue) {
	this.model = model;
	this.newValue = newValue;
	
	setLabel("change properties");
  }

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
  	oldValue = getValue();
	setValue(newValue);
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
  	setValue(oldValue);
  }
  
/**
 * set the new value
 * 
 * @param newValue
 */
protected void setValue(boolean newValue) {
	  model.getConnectionLabel().setIsVisible(newValue);
  }
  
/**
 * Get the new value
 * 
 * Return
 */
protected boolean getValue() {
	  return model.getConnectionLabel().isIsVisible();
  }
}
