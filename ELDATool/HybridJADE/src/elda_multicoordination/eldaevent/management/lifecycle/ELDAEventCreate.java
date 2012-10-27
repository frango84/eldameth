/*****************************************************************
Multi-Coordination for JADE agents
Copyright (C) 2010 G. Fortino, F. Rango

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

package elda_multicoordination.eldaevent.management.lifecycle;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event used to create an agent.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventCreate extends ELDAEventLifecycle {
	
	/**
	 * Name of the agent to create
	 */
    private String agentName;
    
	/**
	 * The Java class implementing the agent to create (specifying also the package)
	 */
    private String className;
    
	/**
	 * Arguments to pass to the agent to create
	 */
    private java.util.List<java.io.Serializable> arguments;
    
	/**
	 * Name of the container where the agent is to be created
	 */
    private String containerName;
    
	/**
	 * This variable is true if a creation notification is required
	 */
	private boolean notify;
	
	/**
	 * Creation notification that will be sent back when the creation is successfully executed
	 * (it can be null if the variable "notify" is false)
	 */
	private ELDAEventCreateNotify backEvent;

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param agentName Name of the agent to create
	 * @param className The Java class implementing the agent to create (specifying also the package)
	 * @param arguments Arguments to pass to the agent to create
	 * @param containerName Name of the container where the agent is to be created
	 * @param notify This parameter is true if a creation notification is required
	 * @param backEvent Creation notification that will be sent back when the creation is successfully executed (it can be null if the variable "notify" is false)
	 */
	public ELDAEventCreate(AID source, String agentName, String className,
			java.util.List<java.io.Serializable> arguments, String containerName,
			boolean notify, ELDAEventCreateNotify backEvent){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
 		
 		this.agentName = agentName;
 		this.className = className;
 		this.arguments = arguments;
 		this.containerName = containerName;
 		this.notify = notify;
 		this.backEvent = backEvent;
	}
	
	/**
	 * Constructor without creation notification.
	 * @param source Sender of the event
	 * @param agentName Name of the agent to create
	 * @param className The Java class implementing the agent to create (specifying also the package)
	 * @param arguments Arguments to pass to the agent to create
	 * @param containerName Name of the container where the agent is to be created
	 */
	public ELDAEventCreate(AID source, String agentName, String className,
			java.util.List<java.io.Serializable> arguments, String containerName){
		this(source, agentName, className, arguments, containerName, false, null);
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

	public java.util.List<java.io.Serializable> getArguments() {
		return arguments;
	}

	public void setArguments(java.util.List<java.io.Serializable> arguments) {
		this.arguments = arguments;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public ELDAEventCreateNotify getBackEvent() {
		return backEvent;
	}

	public void setBackEvent(ELDAEventCreateNotify backEvent) {
		this.backEvent = backEvent;
	}

}