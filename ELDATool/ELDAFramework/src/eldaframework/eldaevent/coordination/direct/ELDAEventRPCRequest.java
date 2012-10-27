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

public class ELDAEventRPCRequest extends ELDAEventDirect implements Serializable{

 	/**
 	 * Event that contains the message to deliver
 	 */
 	protected ELDAEventMSG msg;
 	
 	/**
 	 * Attended event that will be sent back
 	 */
 	protected ELDAEventRPCResult backEvent;


// 	public ELDAEventRPCRequest(ELDAId source, ELDAId target, ELDAEventMSG msg, ELDAEventRPCResult backEvent){
//  		super(source, target);
//  		this.msg=msg;
//  		this.backEvent=backEvent;
// 	}
 	
 	public ELDAEventRPCRequest(ELDAId source,  ELDAEventMSG msg, ELDAEventRPCResult backEvent){
  		super(source, source);
  		this.msg=msg;
  		this.backEvent=backEvent;
 	}

 	public ELDAEventMSG getMSG(){ return msg; }

 	public ELDAEventRPCResult getBackEvent(){ return backEvent; }

}
