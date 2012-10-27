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

import eldaframework.components.*;
import eldaframework.dsc.ELDAActiveState;
import eldaframework.dsc.ELDAContext;
import eldaframework.eldaevent.*;
import eldaframework.maaf.*;


public class ELDABehavior extends ELDAContext implements Serializable {

	//for MAAF
	private IMobileAgentAdapter imaa=null;

	//for ActiWare
	transient private ITLH tlh=null;
	private ELDAId mid=null;

	private ELDAFIPATemplate mst;

	public ELDABehavior(ELDAActiveState activeState) {
		mst = new ELDAFIPATemplate(this, activeState);
		changeState(mst);
	}

	public void setIMobileAgentAdapter(IMobileAgentAdapter imaa){
		this.imaa=imaa;
	}

	public void generate(ELDAEvent mevent) {
		if (imaa!=null) imaa.generate(mevent);
		else tlh.generate(mevent);
	}

	public ELDAId self() {
		if (imaa!=null) return imaa.getMAOId();
		else return mid;
	}

	// Hook to TLH
	public void setTLH(ITLH tlh){
		this.tlh=tlh;
	}

	public void setMID(ELDAId mid){
		this.mid=mid;
	}

}
