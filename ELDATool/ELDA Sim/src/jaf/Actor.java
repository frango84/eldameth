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

//file Actor.java

package jaf;

import java.util.Vector;

public abstract class Actor {

	static FilterSet FS; // Filter set

	static Vector TEQ = new Vector(); // Temporary Event Queue

	protected String name; // Actor name

	int status; // Actor status

	Vector DEQ = new Vector(); // Deferred Event Queue

	protected void become(int status) {
		this.status = status;
	}

	public static void send(Message m) {
		// FS.filter(m);
		TEQ.addElement(m);
	}

	protected void defer(Message m) {
		DEQ.addElement(m);
	}

	public int currentStatus() {
		return status;
	}

	protected abstract void handler(Message m);

	public static void setFilter(FilterSet filterSet) {
		FS = filterSet;
	};

	public String getName() {
		return name;
	}

};// Actor
