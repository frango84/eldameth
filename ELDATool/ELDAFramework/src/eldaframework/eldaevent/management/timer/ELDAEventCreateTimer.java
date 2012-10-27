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

public class ELDAEventCreateTimer extends ELDAEventManagement implements Serializable{

	/**
	 * Delay in milliseconds before task is to be executed
	 */
	protected long delay;
	/**
	 * Time in milliseconds between successive task executions (optional parameter)
	 */
	protected long period;
	/**
	 * The flag value will be true if the Agent requests a creation notification
	 */
	protected boolean notify;
	/**
	 * Attended event that will be sent back (creation notification)
	 */
	protected ELDAEventCreateTimerNotify backEventCTN;
	/**
	 * Attended event that will be sent back (timeout notification)
	 */
	protected ELDAEventTimeoutNotify backEventT;

	public ELDAEventCreateTimer(ELDAId source, ELDAId target, long delay, boolean notify,
			ELDAEventCreateTimerNotify backEventCTN, ELDAEventTimeoutNotify backEventT){
		super(source, target);
		this.delay=delay;
		this.notify=notify;
		this.backEventCTN=backEventCTN;
		this.backEventT=backEventT;
	}

	public ELDAEventCreateTimer(ELDAId source, long delay, boolean notify,
			ELDAEventCreateTimerNotify backEventCTN, ELDAEventTimeoutNotify backEventT){
		super(source, null);
		this.delay=delay;
		this.notify=notify;
		this.backEventCTN=backEventCTN;
		this.backEventT=backEventT;
	}

	public ELDAEventCreateTimer(ELDAId source, long delay, ELDAEventTimeoutNotify backEventT){
		super(source, null);
		this.delay=delay;
		this.notify=false;
		this.backEventCTN=null;
		this.backEventT=backEventT;
	}

	public ELDAEventCreateTimer(ELDAId source, ELDAId target, long delay, long period, ELDAEventTimeoutNotify backEventT){
		super(source, target);
		this.delay=delay;
		this.period=period;
		this.notify=false;
		this.backEventCTN=null;
		this.backEventT=backEventT;
	}

	public ELDAEventCreateTimer(ELDAId source, ELDAId target, long delay, long period, boolean notify,
			ELDAEventCreateTimerNotify backEventCTN, ELDAEventTimeoutNotify backEventT){
		super(source, target);
		this.delay=delay;
		this.period=period;
		this.notify=notify;
		this.backEventCTN=backEventCTN;
		this.backEventT=backEventT;
	}

	public ELDAEventCreateTimer(ELDAId source, long delay, long period, boolean notify,
			ELDAEventCreateTimerNotify backEventCTN, ELDAEventTimeoutNotify backEventT){
		super(source, source);
		this.delay=delay;
		this.period=period;
		this.notify=notify;
		this.backEventCTN=backEventCTN;
		this.backEventT=backEventT;
	}

	public long getDelay(){
		return delay;
	}

	public long getPeriod(){
		return period;
	}

	public boolean getNotify(){ return notify; }

	public ELDAEventCreateTimerNotify getBackEventCTN(){ return backEventCTN; }

	public ELDAEventTimeoutNotify getBackEventT(){ return backEventT; }

}
