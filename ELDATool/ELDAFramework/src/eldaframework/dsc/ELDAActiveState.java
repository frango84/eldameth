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

import eldaframework.agent.ELDABehavior;
import eldaframework.agent.ELDAId;
import eldaframework.eldaevent.*;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldaframework.eldaevent.management.lifecycle.ELDAEventSuspend;
import eldaframework.eldaevent.management.lifecycle.ELDAEventWait;


public abstract class ELDAActiveState extends CompositeState implements Serializable {


	public static boolean debug=false;

	public ELDAActiveState (AState parent, ELDABehavior mbeh) {
		super(parent, mbeh);
	}

	public ELDAActiveState () {super(null,null);}

	public void setBehavior(ELDABehavior mbeh){context=mbeh;}

	public abstract void constructSubActiveStates();

	public void generate(ELDAEvent mevent){
		((ELDABehavior)context).generate(mevent);
	}

	public ELDAId self(){
		return ((ELDABehavior)context).self();
	}

	public int handler(ELDAEvent mevent){
		if (debug) System.out.println("Handling event in ActiveState " + mevent);
		if (mevent instanceof ELDAEventWait){
			((CompositeState)parent).setActiveState(((CompositeState)parent).getState("WAITING"));
			changeState(((CompositeState)parent).getState("WAITING"));
			return 0;
		}
		else if (mevent instanceof ELDAEventMove){
			((CompositeState)parent).setActiveState(((CompositeState)parent).getState("TRANSIT"));
			changeState(((CompositeState)parent).getState("TRANSIT"));
			return 0;
		}
		else if (mevent instanceof ELDAEventSuspend){
			((CompositeState)parent).setActiveState(((CompositeState)parent).getState("SUSPENDED"));
			changeState(((CompositeState)parent).getState("SUSPENDED"));
			return 0;
		}
		return parent.handler(mevent);
	}

}//class MAOActiveState
