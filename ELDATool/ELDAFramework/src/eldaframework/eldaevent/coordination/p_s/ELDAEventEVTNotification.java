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

public abstract class ELDAEventEVTNotification extends ELDAEventP_S implements Serializable{
	/**
	 * Class name of the event
	 */
	protected Object eventTopic;

	/**
	 *  Eventual data of the event
	 */
	protected Object data;

//	public ELDAEventEVTNotification(ELDAId source, ELDAId target, Object data, Object eventTopic){
//		super(source, target);
//		this.eventTopic=eventTopic;
//		this.data=data;
//	}

	public ELDAEventEVTNotification(ELDAId target,Object eventTopic, Object data){
		super(target, target);
		this.eventTopic=eventTopic;
		this.data=data;
		setIn();
	}
	
	public ELDAEventEVTNotification(ELDAId target){
		super(target, target);
		setIn();
	}

	public Object getEventTopic(){ return eventTopic; }

	public Object getData(){ return data; }

	public void setData(Object data){ this.data=data; }

	public void setEventTopic(Object eventTopic) {
		this.eventTopic = eventTopic;
	}

}
