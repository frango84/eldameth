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

package eldaframework.eldaevent.coordination.direct;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;

public abstract class ELDAEventRPCResult extends ELDAEventDirect implements Serializable{

	/**
	 * Returned Value
	 */
	
	protected Object returnValue;

	public ELDAEventRPCResult(ELDAId source, ELDAId target, Object returnValue){
  		super(source, target);
  		this.returnValue=returnValue;
 	}

	public ELDAEventRPCResult(ELDAId target, Object returnValue){
  		super(target, target);
  		this.returnValue=returnValue;
 	}

 	public Object getReturnValue(){ return returnValue; }

 	public void setReturnValue(Object returnValue){ this.returnValue=returnValue; }

}
