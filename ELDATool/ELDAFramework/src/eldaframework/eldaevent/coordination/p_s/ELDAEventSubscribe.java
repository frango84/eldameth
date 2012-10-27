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

package eldaframework.eldaevent.coordination.p_s;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;

public class ELDAEventSubscribe extends ELDAEventP_S implements Serializable{

	/**
	 * Class name of the event
	 */
	protected Object eventTopic;
	
	/**
	 * Attended event that will be sent back
	 */
 	protected ELDAEventEVTNotification backEvent;

// 	public ELDAEventSubscribe(ELDAId source, ELDAId target, Object eventTopic,
//                          ELDAEventEVTNotification backEvent){
//  		super(source, target);
//  		this.eventTopic=eventTopic;
//  		this.backEvent=backEvent;
// 	}

 	public ELDAEventSubscribe(ELDAId source, Object eventTopic,
                          ELDAEventEVTNotification backEvent){
  		super(source, source);
  		this.eventTopic=eventTopic;
  		this.backEvent=backEvent;
	}

 	public Object getEventTopic(){ return eventTopic; }
 	
 	public ELDAEventEVTNotification getBackEvent(){ return backEvent; }

}
