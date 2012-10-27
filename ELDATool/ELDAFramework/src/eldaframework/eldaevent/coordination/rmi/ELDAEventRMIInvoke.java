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

package eldaframework.eldaevent.coordination.rmi;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.coordination.direct.ELDAEventDirect;


public class ELDAEventRMIInvoke extends ELDAEventDirect implements Serializable{

	/**
	 * Url of the Remote Object
	 */
	protected String idEntity;
	
	/**
	 * Method name to invoke on the Remote Object
	 */
	protected String methodName;
	
	/**
	 * Eventual parameters of the method name
	 */
	protected Object [] params;
	/**
	 * Attended event that will be sent back
	 */
	protected ELDAEventRMIReturn backEvent;

	public ELDAEventRMIInvoke(ELDAId source, ELDAId target, String idEntity, String methodName,
                          Object [] params, ELDAEventRMIReturn backEvent){
  		super(source, target);
		this.idEntity=idEntity;
  		this.methodName=methodName;
  		this.params=params;
  		this.backEvent=backEvent;
	}

 	public ELDAEventRMIInvoke(ELDAId source, String idEntity, String methodName,
                          Object [] params, ELDAEventRMIReturn backEvent){
  		super(source, null);
 		this.idEntity=idEntity;
  		this.methodName=methodName;
  		this.params=params;
  		this.backEvent=backEvent;
 	}

 	public String getIdEntity(){ return idEntity; }

 	public String getMethodName(){ return methodName; }

 	public Object [] getParams(){ return params; }

 	public ELDAEventRMIReturn getBackEvent(){ return backEvent; }

}
