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

import org.eclipse.gef.commands.Command;

/**
 * Change the name of the passed ModelElement
 * 
 * @author marguu&zaza
 *
 */
public class ChangeNameCommand extends Command {

  protected String newName;
  protected String oldName;
  protected ModelElement model;
  
  public ChangeNameCommand(ModelElement model, String string) {
	this.model = model;
	if (string != null)	  newName = filter(string);
	else				  newName = "";
	
	setLabel("change properties");
  }

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
  	oldName = getText();
	setText(newName);
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
  	setText(oldName);
  }
  
/**
 * Set the new value
 * 
 * @param newText
 */
protected void setText(String newText) {
	  model.setName(newText);
  }
  
/**
 * Return the value
 */
protected String getText() {
	  return model.getName();
  }
  
/**
 * Repalce the symbols à è ì ò ù with the symbols a' e' i' o' u'
 * 
 * @param text	the text to be modified
 * Return		the modified text
 */
protected String filter(String text) {
      // Controllo se nella stringa passata sono presenti lettere accentate
	  text = text.replaceAll("à","a'");
	  text = text.replaceAll("è","e'");
	  text = text.replaceAll("ì","i'");
	  text = text.replaceAll("ò","o'");
	  text = text.replaceAll("ù","u'");
      
      return text;
  }
}
