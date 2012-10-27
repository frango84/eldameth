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

package eldasim.network;

import java.util.Vector;

public class Proxy {

	private boolean active;
	private boolean temporary;
	private Vector mailbox;
	private String forward;

	public Proxy(String forward, boolean active, boolean tempor) {
		this.forward = forward;
		this.active = active;
		this.temporary = tempor;
		mailbox = new Vector();
	}

	public boolean isActive() {
		return active;
	}

	public String getForward() {
		return forward;
	}

	public void setForwardingOn() {
		active = true;
	}

	public void setForwardingOff() {
		active = false;
	}

	public Vector getMailBox() {
		return mailbox;
	}

	public void insertMailBox(Object o) {
		mailbox.add(o);
	}

	public void resetMailBox() {
		mailbox = new Vector();
	}

	public boolean isTemporary() {
		return temporary;
	}
}
