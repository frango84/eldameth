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

import dscDiagramModel.Transition;

/**
 * Change the ShowLabel property of the passed Relationship
 * 
 * @author samuele
 */
public class ChangeTransitionPropertiesCommand extends Command {


  protected Transition model;
  
  private String actionID;
  private String guardID;
  private String  eventID;
  private String transitionID;
  private boolean isTriggeredByEvent, isShowIdentifier, isShowProperties;
  
  private String oldActionID;
  private String oldGuardID;
  private String  oldEventID;
  private String oldTransitionID;
  private boolean oldIsTriggeredByEvent,  oldIsShowIdentifier, oldIsShowProperties;
  
  public ChangeTransitionPropertiesCommand(Transition connection, String eventID,String guardID,String actionID, String transitionID, boolean isTriggeredByEvent, boolean isShowIdentifier, boolean isShowProperties ) {
	this.model = connection;
	
	this.oldActionID=model.getActionID();
	this.oldGuardID=model.getGuardID();
	this.oldEventID=model.getEventID();
	this.oldTransitionID=model.getTransitionID();
	this.oldIsTriggeredByEvent=model.isTriggeredByEvent();
	this.oldIsShowIdentifier=model.isShowTransitionID();
	this.oldIsShowProperties=model.isShowProperties();
	
	this.actionID=actionID;
	this.eventID=eventID;
	this.guardID=guardID;
	this.transitionID=transitionID;
	this.isTriggeredByEvent=isTriggeredByEvent;
	this.isShowIdentifier=isShowIdentifier;
	this.isShowProperties=isShowProperties;
	
	setLabel("change properties");
  }

/**
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
  	model.setActionID(actionID);
  	model.setGuardID(guardID);
  	model.setEventID(eventID);
  	model.setTransitionID(transitionID);
  	model.setTriggeredByEvent(isTriggeredByEvent);
  	model.setShowTransitionID(isShowIdentifier);
  	model.setShowProperties(isShowProperties);
  	
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
  	model.setActionID(oldActionID);
  	model.setEventID(oldEventID);
  	model.setGuardID(oldGuardID);
  	model.setTransitionID(oldTransitionID);
	model.setTriggeredByEvent(oldIsTriggeredByEvent);
	model.setShowTransitionID(oldIsShowIdentifier);
  	model.setShowProperties(oldIsShowProperties);
  }
}
