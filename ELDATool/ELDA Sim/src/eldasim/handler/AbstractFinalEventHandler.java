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

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : AbstractFinalEventHandler.java
//  @ Date : 24/12/2007
//  @ Author : 
//
//

package eldasim.handler;

import java.util.Vector;

import eldasim.AgentServer;

public abstract class AbstractFinalEventHandler extends EventHandler {
	public AbstractFinalEventHandler(AgentServer as, String name, Vector<Class> v) {
		super(as, name, v);
	}

	@Override
	public final void addSuccessor(EventHandler eh) {
		// There isn't any successor!
		setThisHandlerAsPredecessorOf(eh);
	}

}
