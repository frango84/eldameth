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

package eldaframework.agent;

import java.io.*;

import eldaframework.dsc.CompositeState;
import eldaframework.dsc.ELDAActiveState;
import eldaframework.dsc.ELDAContext;
import eldaframework.dsc.SimpleState;
import eldaframework.dsc.AState;
import eldaframework.dsc.TopState;
import eldaframework.eldaevent.*;
import eldaframework.eldaevent.management.lifecycle.ELDAEventDestroy;
import eldaframework.eldaevent.management.lifecycle.ELDAEventExecute;
import eldaframework.eldaevent.management.lifecycle.ELDAEventInvoke;
import eldaframework.eldaevent.management.lifecycle.ELDAEventQuit;
import eldaframework.eldaevent.management.lifecycle.ELDAEventResume;
import eldaframework.eldaevent.management.lifecycle.ELDAEventWakeUp;


public class ELDAFIPATemplate extends TopState implements Serializable {



 public ELDAFIPATemplate(ELDABehavior mbeh, ELDAActiveState activeState){
    super(mbeh);
    addState("WAITING",   new FIPAWaitingState(this, mbeh));
    addState("SUSPENDED", new FIPASuspendedState(this, mbeh));
    addState("TRANSIT",   new FIPATransitState(this, mbeh));
    addState("INITIATED", new FIPAInitiatedState(this, mbeh));
    addState("ACTIVE", activeState);
    activeState.setBehavior(mbeh);
    activeState.setParent(this);
    activeState.constructSubActiveStates();
 }

 public void setADSCState(ELDAActiveState activeState){
    addState("ACTIVE", activeState);
    activeState.setBehavior((ELDABehavior)context);
    activeState.setParent(this);
    activeState.constructSubActiveStates();
 }

 public final AState getDefaultEntrance() { return getState("INITIATED"); }

 protected void init(){}

 protected class FIPAWaitingState extends SimpleState implements Serializable {

    protected FIPAWaitingState (AState parent, ELDAContext context) {
      super(parent, context);
    }

    public final int handler(ELDAEvent mevent) {
      if (mevent instanceof ELDAEventWakeUp){
		 ((CompositeState)parent).setActiveState(((CompositeState)parent).getState("ACTIVE"));
         changeState( ((ELDAActiveState)getState("ACTIVE")).deepHistory() );
         return 0;
      }
      return parent.handler(mevent);
    }
 }//class MAOWaitingState

 protected class FIPASuspendedState extends SimpleState implements Serializable{

    protected FIPASuspendedState(AState parent, ELDAContext context) {
      super(parent, context);
    }

    public final int handler(ELDAEvent mevent) {
      if (mevent instanceof ELDAEventResume){
		 ((CompositeState)parent).setActiveState(((CompositeState)parent).getState("ACTIVE"));
         changeState(((ELDAActiveState)getState("ACTIVE")).deepHistory());
         return 0;
      }
      return parent.handler(mevent);
    }
 }//class MAOSuspendedState

 protected class FIPATransitState extends SimpleState implements Serializable{

    protected FIPATransitState (AState parent, ELDAContext context) {
      super(parent, context);
    }

    public final int handler(ELDAEvent mevent) {
      if (mevent instanceof ELDAEventExecute){
		 ((CompositeState)parent).setActiveState(((CompositeState)parent).getState("ACTIVE"));
         changeState(((ELDAActiveState)getState("ACTIVE")).deepHistory());
         return 0;
      }
      return parent.handler(mevent);
    }
 }//class MAOTransitState

 protected class FIPAInitiatedState extends SimpleState implements Serializable{

    protected FIPAInitiatedState(AState parent, ELDAContext context) {
      super(parent, context);
    }

    public final int handler(ELDAEvent mevent) {
      if (mevent instanceof ELDAEventInvoke){
		 ((CompositeState)parent).setActiveState(((CompositeState)parent).getState("ACTIVE"));
         changeState(((ELDAActiveState)getState("ACTIVE")).deepHistory());
         return 0;
      }
      return parent.handler(mevent);
    }
 }//class MAOInitiatedState

 public final int handler(ELDAEvent mevent) {
  if (mevent instanceof ELDAEventDestroy){
	  setActiveState(null);
      changeState(null);
      return 0;
  }
  else if (mevent instanceof ELDAEventQuit){
	  setActiveState(null);
      changeState(null);
      return 0;
  }
  return -1;
 }

}//class MAOStateTemplate







