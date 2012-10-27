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

package eldaframework.eldaevent.management.lifecycle;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.management.ELDAEventManagement;

public class ELDAEventClone extends ELDAEventLifecycle implements Serializable{

	/**
	 * Locations where Agent will be cloned
	 */
	protected Object location=null;
	/**
	 * The flag value will be true if the Agent requests a clonation notification
	 */ 	
	protected boolean notify;
	/**
	 * Attended event that will be sent back
	 */
	protected ELDAEventCloneNotify backEvent;


	public ELDAEventClone(ELDAId source){
		super(source, source);
	}
	
	public ELDAEventClone(ELDAId source, boolean notify, ELDAEventCloneNotify backEvent){
		super(source, source);
		this.location=null;
		this.notify=notify;
		this.backEvent=backEvent;
	}
	
	public ELDAEventClone(ELDAId source, Object location){
		super(source, source);
		this.location=location;
		this.notify=false;
		this.backEvent=null;
	}

	public ELDAEventClone(ELDAId source, Object location, boolean notify, ELDAEventCloneNotify backEvent){
		super(source, source);
		this.location=location;
		this.notify=notify;
		this.backEvent=backEvent;
	}

	public Object getLocation(){ return location; }

	public boolean getNotify(){ return notify; }

	public ELDAEventCloneNotify getBackEvent(){ return backEvent; }

}
