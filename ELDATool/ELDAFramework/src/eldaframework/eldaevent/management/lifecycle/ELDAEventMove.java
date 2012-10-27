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
import eldaframework.eldaevent.ELDAEvent;

public class ELDAEventMove extends ELDAEventLifecycle implements Serializable{

 	/**
 	 * Migration destination
 	 */ 
 	protected Object dest;
 	
 	protected ELDAEvent reactivationEvent;

// 	public ELDAEventMove(ELDAId source, ELDAId target, Object dest){
//  		super(source, target);
//  		setIn();
//  		this.dest=dest;
//	}

 	public ELDAEventMove(ELDAId target, Object dest){
  		super(target, target);
  		setIn();
  		this.dest=dest;
	}
 	
 	public ELDAEventMove(ELDAId target, Object dest,ELDAEvent reactivationEvent){
  		super(target, target);
  		setIn();
  		this.dest=dest;
  		this.reactivationEvent=reactivationEvent;
	}

 	public Object getDestination(){ return dest; }

	public ELDAEvent getReactivationEvent() {
		return reactivationEvent;
	}

}
