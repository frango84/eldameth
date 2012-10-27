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

package eldaframework.eldaevent.exception.coordination.direct;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.exception.coordination.*;

public class ELDADirectException extends ELDACoordinationException implements Serializable{

	public final int UNREACHABLE_TARGET_AGENT=401;
	public final int UNREACHABLE_REMOTE_OBJECT=402;
	public final int NOT_AVAILABLE_REMOTE_METHOD=403;
	public final String fourHundredOne="The target agent is unreachable.";
	public final String fourHundredTwo="The remote object is unreachable.";
	public final String fourHundredThree="The remote method is temporary not avaible.";	
	
	public ELDADirectException(ELDAId source, ELDAId target, int code){
 		super(source, target, code);
 		setDescr(code);
 	}
	
	public ELDADirectException(ELDAId target, int code){
 		super(null, target, code);
 		setDescr(code);
 	}
	
	public void setDescr(int code) {
		switch(code){
			case 401: descr=fourHundredOne;
			case 402: descr=fourHundredTwo;
			case 403: descr=fourHundredThree;
			default: 
		}
	}
	
}
