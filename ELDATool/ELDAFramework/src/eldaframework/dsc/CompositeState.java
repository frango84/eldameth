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
import java.util.*;


public abstract class CompositeState extends AState implements Serializable {
	
	private static boolean debug=false;
	//private Hashtable<String, AState> componentStates;
	private Hashtable componentStates;

	protected AState activeState;

	public CompositeState(AState parent, ELDAContext cxt){
		super(parent, cxt);
//		componentStates = new Hashtable<String, AState>();//?
		componentStates = new Hashtable();//?
		activeState = null;
//		componentStates.//?
	}

	public void setActiveState(AState state) {
		activeState = state;
	}

	public AState getActiveState() { return activeState; }

	public void addState(String tag, AState state) {
		componentStates.put(tag, state);
	}

	public AState getState(String tag) {
		return (AState)componentStates.get(tag);
	}

	public AState start() {
		activeState=getDefaultEntrance();
		if (activeState!=null) return activeState.start();
		return this;
	}

	protected abstract AState getDefaultEntrance();

	protected AState getDefaultShallowHistoryEntrance(){return getDefaultEntrance();};

	protected AState getDefaultDeepHistoryEntrance(){return getDefaultEntrance();}

	public AState shallowHistory() {
		if (activeState!=null) return activeState;
		else {
			activeState=getDefaultShallowHistoryEntrance();
			return activeState;
		}
	}

	public AState deepHistory() {
		if (debug) System.out.println("Deep");
		if (activeState!=null)
			if (activeState instanceof CompositeState)
				return ((CompositeState)activeState).deepHistory();
			else
				return activeState;

		if (debug) System.out.println(this);
		activeState=getDefaultDeepHistoryEntrance();
		return activeState;
	}

}
