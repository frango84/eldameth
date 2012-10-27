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

package elda_multicoordination.eldaevent.management.timer;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event used to create a timer.
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventCreateTimer extends ELDAEventTimer {

	/**
	 * This variable indicates the number of milliseconds after which
	 * the timeout notification will be sent
	 */
	private long delay;
	
	/**
	 * This variable is true if a creation notification is required
	 */
	private boolean notify;
	
	/**
	 * Creation notification that will be sent back when the creation is successfully executed
	 * (it can be null if the variable "notify" is false)
	 */
	private ELDAEventCreateTimerNotify backEventCTN;
	
	/**
	 * Timeout notification that will be sent back
	 */
	private ELDAEventTimeoutNotify backEventT;

	/**
	 * Constructor with all parameters.
	 * @param source Sender of the event
	 * @param delay The number of milliseconds after which the timeout notification will be sent
	 * @param notify This parameter is true if a creation notification is required
	 * @param backEventCTN Creation notification that will be sent back when the creation is successfully executed (it can be null if the parameter "notify" is false)
	 * @param backEventT Timeout notification that will be sent back
	 */
 	public ELDAEventCreateTimer(AID source, long delay, boolean notify,
			ELDAEventCreateTimerNotify backEventCTN, ELDAEventTimeoutNotify backEventT){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
 		
  		this.delay = delay;
  		this.notify = notify;
  		this.backEventCTN = backEventCTN;
  		this.backEventT = backEventT;
	}
 	
	/**
	 * Constructor without creation notification.
	 * @param source Sender of the event
	 * @param delay The number of milliseconds after which the timeout notification will be sent
	 * @param backEventT Timeout notification that will be sent back
	 */
 	public ELDAEventCreateTimer(AID source, long delay, ELDAEventTimeoutNotify backEventT){
 		this(source, delay, false, null, backEventT);
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public ELDAEventCreateTimerNotify getBackEventCTN() {
		return backEventCTN;
	}

	public void setBackEventCTN(ELDAEventCreateTimerNotify backEventCTN) {
		this.backEventCTN = backEventCTN;
	}

	public ELDAEventTimeoutNotify getBackEventT() {
		return backEventT;
	}

	public void setBackEventT(ELDAEventTimeoutNotify backEventT) {
		this.backEventT = backEventT;
	}

}