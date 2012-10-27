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

public class ELDAEventWSDLRequest extends ELDAEventServices implements Serializable{

	/**
	 * Url of WSDL file
	 */
	protected String url;

	/**
	 * Attended event that will be sent back 
	 */
	protected ELDAEventWSDLReturn backEvent;

 	public ELDAEventWSDLRequest(ELDAId source, ELDAId target, String url, ELDAEventWSDLReturn backEvent){
  		super(source, target);
  		this.url=url;
  		this.backEvent=backEvent;
 	}

 	public ELDAEventWSDLRequest(ELDAId source, String url, ELDAEventWSDLReturn backEvent){
  		super(source, null);
  		this.url=url;
  		this.backEvent=backEvent;
 	}

 	public String getUrl(){ return url; }

 	public ELDAEventWSDLReturn getBackEvent(){ return backEvent; }


}
