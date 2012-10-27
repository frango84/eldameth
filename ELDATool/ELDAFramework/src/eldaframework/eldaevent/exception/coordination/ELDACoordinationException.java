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

package eldaframework.eldaevent.exception.coordination;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.exception.*;

public abstract class ELDACoordinationException extends ELDAException implements Serializable{
	
	public final int DENIED_OPERATION=201;
	public final int VIOLATED_POLITICS_OF_MANAGEMENT=202;
	public final int NOT_RECEIVED_BACK_EVENT=203;
	public final int NOT_FOUND_CM=204;
	public final String twoHundredOne="The demanded operation has been denied.";
	public final String twoHundredTwo="The demanded operation violates politics of management.";
	public final String twoHundredThree="The attended back event has not been received.";
	public final String twoHundredFour="The coordination medium has not been found.";

	public ELDACoordinationException(ELDAId source, ELDAId target, int code){
 		super(source, target, code);
 		setDescr(code);
 	}
	
	public ELDACoordinationException(ELDAId target, int code){
 		super(null, target, code);
 		setDescr(code);
 	}
	
	public void setDescr(int code) {
		switch(code){
			case 201: descr=twoHundredOne;
			case 202: descr=twoHundredTwo;
			case 203: descr=twoHundredThree;
			case 204: descr=twoHundredFour;
			default: 
		}
	}
	
}
