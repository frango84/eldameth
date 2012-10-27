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
import genericUMLDiagramModel.Note;

/**
 * Change the body of the passed Note
 * 
 * @author marguu&zaza
 *
 */
public class ChangeCommentNoteCommand extends ChangeNameCommand {

  public ChangeCommentNoteCommand(ModelElement model, String string) {
	super(model,string);
  }

  
/**
 * @see genericUMLDiagramEditor.commands.ChangeNameCommand#setText(java.lang.String)
 */
protected void setText(String newText) {
	  ((Note)model).setComment(newText);
  }
  
  /**
 * @see genericUMLDiagramEditor.commands.ChangeNameCommand#getText()
 */
protected String getText() {
	  return ((Note)model).getComment();
  }
}
