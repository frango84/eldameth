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

package eldaframework.eldaevent.coordination.tuples;


import java.io.*;

import eldaframework.agent.ELDAId;
import eldaframework.dsc.*;

public abstract class ELDAEventReturnTuple extends ELDAEventTuples implements Serializable{

 	/**
 	 * Tuple that matchs on the proposed template
 	 */
 	protected Object content;
//
// 	public ELDAEventReturnTuple(ELDAId source, ELDAId target, Object content){
//  		super(source, target);
//  		this.content=content;
//	}

 	public ELDAEventReturnTuple(ELDAId target, Object content){
  		super(target, target);
  		this.content=content;
  		setIn();
	}
 	
 	public ELDAEventReturnTuple(ELDAId target){
  		super(target, target);
  		this.content=content;
  		setIn();
	}

 	public Object getContent(){ return content; }

 	public void setContent(Object content){ this.content=content; }

}
