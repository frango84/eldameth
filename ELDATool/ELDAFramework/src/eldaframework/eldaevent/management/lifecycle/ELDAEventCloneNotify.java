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


import java.io.Serializable;

import eldaframework.agent.ELDAId;

public abstract class  ELDAEventCloneNotify extends ELDAEventLifecycle implements Serializable{

 	/**
 	 * Name of created Agent's clone 
 	 */
	private ELDAId cmid;


	public ELDAEventCloneNotify(ELDAId target){
		super(target, target);
		setIn();
		this.cmid=null;
	}
	
 	public ELDAEventCloneNotify(ELDAId target, ELDAId cmid){
		super(target, target);
		setIn();
		this.cmid=cmid;
	}

 	public ELDAId getClonedELDAId(){ return cmid; }
 	
 	public void setClonedELDAIds(ELDAId cmid){ this.cmid=cmid; }

}

