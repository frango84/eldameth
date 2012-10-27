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

package eldaframework.eldaevent.management.timer;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.management.ELDAEventManagement;

public abstract class ELDAEventTimeoutNotify extends ELDAEventManagement implements Serializable{

	/**
 	 * A Timer reference
 	 */
 	protected Object timer;

 	public ELDAEventTimeoutNotify(ELDAId target, Object timer){
  		super(target, target);
  		this.timer=timer;
  		setIn();
	}
 	
 	public ELDAEventTimeoutNotify(ELDAId target){
  		super(target, target);
  		setIn();
	}

 	public Object getTimer(){ return timer; }

 	public void setTimer(Object timer){ this.timer=timer; }

}
