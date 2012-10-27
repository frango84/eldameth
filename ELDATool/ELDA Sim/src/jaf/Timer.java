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

//file Timer.java

package jaf;

import java.util.Vector;

public class Timer {
	Message timeout;
	double firetime;
	static Vector TQ = new Vector(); // ranked Timer Queue
	static Timer MRFT;

	public void setMaxPriority(Message timeout) {
		this.timeout = timeout;
		this.firetime = CurrentTime.now();
		int index = TQ.indexOf(this);
		if (index != -1)
			TQ.removeElementAt(index);
		TQ.insertElementAt(this, 0);
	}

	public void set(Message timeout, long firetime) {
		this.timeout = timeout;
		this.firetime = firetime + CurrentTime.now();
		int index = TQ.indexOf(this);
		if (index != -1)
			TQ.removeElementAt(index);
		int pos = 0;
		while (pos < TQ.size() && ((Timer) TQ.elementAt(pos)).firetime <= this.firetime)
			pos++;
		TQ.insertElementAt(this, pos);
	};// set

	public void setAbsolute(Message timeout, double absFiretime) {
		this.timeout = timeout;
		this.firetime = absFiretime;
		int pos = 0;
		while (pos < TQ.size() && ((Timer) TQ.elementAt(pos)).firetime <= this.firetime)
			pos++;
		TQ.insertElementAt(this, pos);
	};// set

	public void reset() {
		if (MRFT == this)
			MRFT = null;
		else {
			int index = TQ.indexOf(this);
			if (index != -1)
				TQ.removeElementAt(index);
		}
	};// reset

	public static void deSchedule(Class msgtype) {
		for (int i = 0; i < TQ.size(); i++)
			if (((Timer) TQ.elementAt(i)).timeout.getClass() == msgtype) {
				TQ.removeElementAt(i);
				System.out.println(msgtype.getName());
			}
	}

	public double timeRemaining() {
		return firetime - CurrentTime.now();
	};// timeRemaining

	static void fire() {
		MRFT = null;
		if (TQ.size() != 0) {
			Timer t = (Timer) TQ.firstElement();
			if (t.firetime <= CurrentTime.now()) {
				MRFT = t;
				TQ.removeElementAt(0);
			}
		}
	}
};// Timer

