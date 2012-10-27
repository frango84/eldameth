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

package eldaframework.eldaevent.exception;

import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;
import eldaframework.eldaevent.*;

public abstract class ELDAException extends ELDAEvent implements Serializable{

	/**
	 * Code that identifies the exception
	 */
	protected int code=-1;
	/**
	 * String that describes the exception
	 */
	protected String descr=null;

	public ELDAException(ELDAId source, ELDAId target, int code){
		super (source,target);
	}

	public ELDAException(ELDAId target, int code){
		super (null,target);
	}


	public int getCode(){ return code; }

	public String getDescr(){ return descr; }

	public void setCode(int code){ this.code=code;}

	public void setDescr(int code){ 
		switch(code){
			default: 
		}		
	}

}

