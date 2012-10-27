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

package eldaframework.eldaevent.exception.management;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.exception.*;

public abstract class ELDAManagementException extends ELDAException implements Serializable{

	public final int DENIED_OPERATION=301;
	public final int VIOLATED_POLITICS_OF_MANAGEMENT=302;
	public final int NOT_RECEIVED_BACK_EVENT=303; 
	public final String threeHundredOne="The demanded operation has been denied.";
	public final String threeHundredTwo="The demanded operation violates politics of management.";
	public final String threeHundredThree="The attended back event has not been received.";	
	
	public ELDAManagementException(ELDAId source, ELDAId target, int code){
 		super(source, target, code);
 		setDescr(code);
 	}
	
	public ELDAManagementException(ELDAId target, int code){
 		super(null, target, code);
 		setDescr(code);
 	}
	
	public void setDescr(int code) {
		switch(code){
			case 301: descr=threeHundredOne;
			case 302: descr=threeHundredTwo;
			case 303: descr=threeHundredThree;
			default: 
		}
	}
	
}
