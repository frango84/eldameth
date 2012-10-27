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

package eldaframework.eldaevent.exception.coordination.services;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.exception.coordination.*;

public class ELDAServicesException extends ELDACoordinationException implements Serializable{

	public final int UNREACHABLE_UDDI=701;
	public final int UNREACHABLE_WEB_SERVICE=702;
	public final int NOT_AVAIBLE_WSDL=703;
	public final String sevenHundredOne="The UDDI is unreachable.";
	public final String sevenHundredTwo="The web services is unreachable.";
	public final String sevenHundredThree="The WSDL file is temporary not avaible.";
	
	public ELDAServicesException(ELDAId source, ELDAId target, int code){
 		super(source, target, code);
 		setDescr(code);
 	}
	
	public ELDAServicesException(ELDAId target, int code){
 		super(null, target, code);
 		setDescr(code);
 	}
	
	public void setDescr(int code) {
		switch(code){
			case 701: descr=sevenHundredOne;
			case 702: descr=sevenHundredTwo;
			case 703: descr=sevenHundredThree;
			default: 
		}
	}
	
}
