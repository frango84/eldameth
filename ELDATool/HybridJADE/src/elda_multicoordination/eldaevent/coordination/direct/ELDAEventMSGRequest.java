/*****************************************************************
Multi-Coordination for JADE agents
Copyright (C) 2010 G. Fortino, F. Rango

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

package elda_multicoordination.eldaevent.coordination.direct;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * This event formalizes a request for sending an asynchronous message
 * and contains the actual ELDAEventMSG to be sent.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventMSGRequest extends ELDAEventDirect {
	
	/**
	 * ELDAEventMSG to deliver
	 */
	private ELDAEventMSG msg;

	/**
	 * Constructor of the event.
	 * @param source Sender of the event
	 * @param msg ELDAEventMSG to deliver
	 */
	public ELDAEventMSGRequest(AID source, ELDAEventMSG msg){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
 		
  		this.msg = msg;
 	}

	public ELDAEventMSG getMsg() {
		return msg;
	}

	public void setMsg(ELDAEventMSG msg) {
		this.msg = msg;
	}

}