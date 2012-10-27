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
 * Event corresponding to the OUT primitive of TuCSoN (to put a tuple in the tuple centre).
 * 
 * @author G. Fortino, F. Rango
 */
public class ELDAEventOut extends ELDAEventTuples {

 	/**
 	 * Tuple to insert in the specified tuple centre
 	 */
 	private String tuple;
 	
 	/**
 	 * Name of the tuple centre that will contain the tuple
 	 */
 	private String tupleCentreName;
 	
 	/**
 	 * IP address of the tuple centre that will contain the tuple
 	 */
 	private String tupleCentreIPAddress;

	/**
	 * Constructor of the event.
	 * @param source Sender of the event
	 * @param tuple Tuple to insert in the specified tuple centre
	 * @param tupleCentreName Name of the tuple centre that will contain the tuple
	 * @param tupleCentreIPAddress IP address of the tuple centre that will contain the tuple
	 */
 	public ELDAEventOut(AID source, String tuple, String tupleCentreName, String tupleCentreIPAddress){
  		super(source, null); //sender = current agent
  		
 		//set receiver = current agent
 		java.util.List<AID> target = new java.util.ArrayList<AID>();
 		target.add(source);
 		setTarget(target);
 		
 		setLanguage(ELDAEvent.OUT);

  		this.tuple = tuple;
  		this.tupleCentreName = tupleCentreName;
  		this.tupleCentreIPAddress = tupleCentreIPAddress;
 	}

	public String getTuple() {
		return tuple;
	}

	public void setTuple(String tuple) {
		this.tuple = tuple;
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