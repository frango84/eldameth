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

package eldaframework.eldaevent.exception.management.console;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.exception.management.*;

public class ELDAConsoleException extends ELDAManagementException implements Serializable{

	public final int UNREACHABLE_CONSOLE=901;
	public final int UNREACHABLE_TARGET_AGENT=902;
	public final String nineHundredOne="The console is unreachable.";
	public final String nineHundredTwo="The target agent is unreachable.";
	
	public ELDAConsoleException(ELDAId source, ELDAId target, int code) {
		super(source, target, code);
		setDescr(code);
	}
	
	public ELDAConsoleException(ELDAId target, int code) {
		super(null, target, code);
		setDescr(code);
	}
	
	public void setDescr(int code) {
		switch(code){
			case 901: descr=nineHundredOne;
			case 902: descr=nineHundredTwo;
			default: 
		}
	}

}
