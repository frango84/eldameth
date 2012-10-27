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

package eldaframework.eldaevent.coordination.tuples;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;

public class ELDAEventRd extends ELDAEventTuples implements Serializable{

 	/**
 	 * Template of the tuple that will be searched
 	 */
 	protected Object tupleMatch; 
 	
 	/**
 	 * Modality of access to the tuples space (The flag value will be true for synchronous wait for the back event)
 	 */
 	protected boolean sync;
 	
 	/**
 	 * Attended event that will be sent back
 	 */
 	protected ELDAEventReturnTuple backEvent; 	

// 	public ELDAEventRd(ELDAId source, ELDAId target, Object name, boolean sync, ELDAEventReturnTuple eventBack){
//  		super(source, target);
//  		this.tupleMatch=name;
//  		this.sync=sync;
//  		this.eventBack=eventBack;
//	}

 	public ELDAEventRd(ELDAId source, Object name, ELDAEventReturnTuple eventBack, boolean sync){
  		super(source, source);
  		this.tupleMatch=name;
  		this.sync=sync;
  		this.backEvent=eventBack;
	}

	public Object getTupleMatch() { return tupleMatch; }
	
	public boolean isSync(){ return sync; }
	
 	public ELDAEventReturnTuple getBackEvent(){ return backEvent; }

 }
