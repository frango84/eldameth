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

package eldaframework.dsc;

import java.io.*;

import eldaframework.eldaevent.*;


public abstract class ELDAContext implements Serializable {

	protected static boolean debug=false;
	private AState currentState;

	public ELDAContext(){}

	public int dispatch(ELDAEvent event) {
		if (currentState.handler(event)==-1) {
			System.err.println("Event:"+event+" targeting"+event.getTarget()+ "not handled");
			return -1;
		}
		else return 0;
	}

	public void changeState(AState state) {
		if (debug)System.out.println("Changing state to " + state);
		if (state!=null) currentState = state.start();
		else currentState = null;
		if (debug)System.out.println("Current state is " + currentState);
	}

	public AState getCurrentState(){
		return currentState;
	}

}
