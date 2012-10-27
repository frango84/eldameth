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

//file Simulation.java

package jaf;

import java.util.Vector;

public class Simulation {
	static Vector EQ;
	static int oldstatus;
	static Message dispatch;
	static boolean initialized = false;

	public static void init() {
		EQ = new Vector();
		VirtualClock.simulationTime = 0;
		CurrentTime.clock = new VirtualClock();
		initialized = true;
	}

	public static void Scheduler(double simPeriod) {
		if (!initialized)
			throw new SchedulerNotInitializedException();
		while (true) {
			// schedule Actor TEQ "just sent messages" into EQ
			while (Actor.TEQ.size() != 0) {
				EQ.addElement(Actor.TEQ.firstElement());
				Actor.TEQ.removeElementAt(0);
			}
			if (EQ.size() == 0 && Timer.TQ.size() == 0)
				break;
			if (VirtualClock.simulationTime > simPeriod)
				break;
			if (EQ.size() == 0) { // force firing of a timer
				Timer t = (Timer) Timer.TQ.firstElement();
				VirtualClock.simulationTime = t.firetime;
				Timer.fire();
				dispatch = t.timeout;
				Timer.MRFT = null;
			} else {
				dispatch = (Message) EQ.firstElement();
				EQ.removeElementAt(0);
			}
			oldstatus = dispatch.receiver.status;
			dispatch.receiver.handler(dispatch);
			if (dispatch.receiver.status != oldstatus)
				// catenate DEQ events in front of EQ
				while (dispatch.receiver.DEQ.size() != 0) {
					EQ.insertElementAt(dispatch.receiver.DEQ.lastElement(), 0);
					dispatch.receiver.DEQ.removeElementAt(dispatch.receiver.DEQ.size() - 1);
				}
		}
		if ((EQ.size() == 0) && (VirtualClock.simulationTime < simPeriod))
			System.out.println("Simulation deadlock!");
	}

	public static Vector getQueue() {
		return EQ;
	}

};// Simulation

