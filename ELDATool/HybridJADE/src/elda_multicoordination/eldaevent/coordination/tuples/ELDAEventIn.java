/*****************************************************************
Multi-Coordination for JADE agents
Copyright (C) 2010 G. Fortino, F. Rango

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

package elda_multicoordination.eldaevent.coordination.tuples;

//ATTENZIONE: non cambiare il nome di questo package e di questa classe perchè vengono usati dal generatore di codice per JADE di ELDATool

import elda_multicoordination.eldaevent.ELDAEvent;
import jade.core.*;

/**
 * Event corresponding to the IN/INP primitives of TuCSoN (to remove respectively in blocking or
 * NON blocking way a tuple matching the specified tuple template from the tuple centre).
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventIn extends ELDAEventTuples {

 	/**
 	 * Template of the tuple to remove
 	 */
 	private String tupleMatch;
 	
 	/**
 	 * Modality of access to the tuple space (if this variable is true, the IN primitive is executed, otherwise the INP primitive is executed)
 	 */
 	private boolean sync;
 	
 	/**
 	 * Attended event that will be sent back (containing the removed tuple)
 	 */
 	private ELDAEventReturnTuple backEvent;

 	/**
 	 * Name of the tuple centre that contains the tuple
 	 */
 	private String tupleCentreName;
 	
 	/**
 	 * IP address of the tuple centre that contains the tuple
 	 */
 	private String tupleCentreIPAddress;

	/**
	 * Constructor of the event.
	 * @param source Sender of the event
	 * @param tupleMatch Template of the tuple to remove
	 * @param backEvent Attended event that will be sent back (containing the removed tuple)
	 * @param sync Modality of access to the tuple space (if this parameter is true, the IN primitive is executed, otherwise the INP primitive is executed)
	 * @param tupleCentreName Name of the tuple centre that contains the tuple
	 * @param tupleCentreIPAddress IP address of the tuple centre that contains the tuple
	 */
 	public ELDAEventIn(AID source, String tupleMatch, ELDAEventReturnTuple backEvent,
 			boolean sync, String tupleCentreName, String tupleCentreIPAddress){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);
  		
  		this.tupleMatch = tupleMatch;
  		this.sync = sync;
  		this.backEvent = backEvent;
  		this.tupleCentreName = tupleCentreName;
  		this.tupleCentreIPAddress = tupleCentreIPAddress;
 	}

	public String getTupleMatch() {
		return tupleMatch;
	}

	public void setTupleMatch(String tupleMatch) {
		this.tupleMatch = tupleMatch;
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

	public ELDAEventReturnTuple getBackEvent() {
		return backEvent;
	}

	public void setBackEvent(ELDAEventReturnTuple backEvent) {
		this.backEvent = backEvent;
	}

	public String getTupleCentreName() {
		return tupleCentreName;
	}

	public void setTupleCentreName(String tupleCentreName) {
		this.tupleCentreName = tupleCentreName;
	}

	public String getTupleCentreIPAddress() {
		return tupleCentreIPAddress;
	}

	public void setTupleCentreIPAddress(String tupleCentreIPAddress) {
		this.tupleCentreIPAddress = tupleCentreIPAddress;
	}

}