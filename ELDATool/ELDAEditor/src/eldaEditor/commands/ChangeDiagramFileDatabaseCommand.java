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

import org.eclipse.gef.commands.Command;

import dscDiagramModel.DSCDiagram;

/**
 * Change the ShowLabel property of the passed Relationship
 * 
 * @author samuele
 */
public class ChangeDiagramFileDatabaseCommand extends Command {


  protected DSCDiagram parent;
  private String actionFile;
  private String guardFile;
  private String  eventFile;
  private String  funcFile;
  private String oldActionFile;
  private String oldGuardFile;
  private String  oldEventFile;
  private String  oldFuncFile;
  
  public ChangeDiagramFileDatabaseCommand(DSCDiagram parent, String eventFile,String guardFile,String actionFile, String functionFile ) {
	this.parent = parent;
	this.oldActionFile=parent.getActionFile();
	this.oldGuardFile=parent.getGuardFile();
	this.oldEventFile=parent.getEventFile();
	this.oldFuncFile=parent.getFunctionFile();
	
	this.actionFile=actionFile;
	this.eventFile=eventFile;
	this.guardFile=guardFile;
	this.funcFile=functionFile;
	
	setLabel("change properties");
  }

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
  	parent.setActionFile(actionFile);
  	parent.setGuardFile(guardFile);
  	parent.setEventFile(eventFile);
  	parent.setFunctionFile(funcFile);
  	
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
	parent.setActionFile(oldActionFile);
	parent.setEventFile(oldEventFile);
	parent.setGuardFile(oldGuardFile);
	parent.setFunctionFile(funcFile);
	
  }
}
