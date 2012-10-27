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

package eldaframework.eldaevent.coordination.services;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;

public abstract class ELDAEventServiceInvoke extends ELDAEventServices implements Serializable{

	/**
	 * Url of the Web Service
	 */
	protected String serviceUrl;
	/**
	 * Eventual parameters to include in a SOAP Request for invoking the web service 
	 */
	protected Object [] params;
	/**
	 * Attended event that will be sent back
	 */
	protected ELDAEventServiceReturn backEvent;

 	public ELDAEventServiceInvoke(ELDAId source, ELDAId target, String serviceUrl,
                          Object [] params, ELDAEventServiceReturn backEvent){
  		super(source, target);
  		this.serviceUrl=serviceUrl;
  		this.params=params;
  		this.backEvent=backEvent;
 	}

 	public ELDAEventServiceInvoke(ELDAId source, String serviceUrl,
                          Object [] params, ELDAEventServiceReturn backEvent){
  		super(source, null);
  		this.serviceUrl=serviceUrl;
  		this.params=params;
  		this.backEvent=backEvent;
 	}

 	public String getserviceUrl(){ return serviceUrl; }

 	public Object [] getParams(){ return params; }

 	public ELDAEventServiceReturn getBackEvent(){ return backEvent; }

}
