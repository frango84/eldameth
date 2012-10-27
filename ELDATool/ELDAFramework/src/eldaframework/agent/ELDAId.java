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

public class ELDAId implements Serializable{

	private String hlName;
//	private Object llName;
	private String homeLocation;
	private String currentLocation;

//	public ELDAId(String hlName, Object llName, String homeLocation){
//
//		this.hlName=hlName;
//		this.llName=llName;
//		this.homeLocation=homeLocation;
//		this.currentLocation=homeLocation;
//	}

	public ELDAId(String hlName){
		this.hlName=hlName;
	}
	
	public ELDAId(String hlName, String homeLocation){
		this.hlName=hlName;
		this.homeLocation=homeLocation;
		this.currentLocation=homeLocation;
	}
	
	public ELDAId(ELDAId eldaId){
		this.hlName=eldaId.getHLName()!=null? new String(eldaId.getHLName()): "" ;
		this.homeLocation=eldaId.getHomeLocation()!=null? new String(eldaId.getHomeLocation()):"";
		this.currentLocation=eldaId.getCurrLocation()!=null? new String(eldaId.getCurrLocation()):"";
	}


//	public Object getLLName(){
//		return llName;
//	}
//
//	public void setLLName(Object llName){
//		this.llName=llName;
//	}
	public String getHLName(){
		return hlName;
	}
	
	public String getUID(){
		return hlName+ "/"+homeLocation;
	}
	public String getHomeLocation(){
		return homeLocation;
	}

	public void setHomeLocation(String homeLocation) {
		this.homeLocation = homeLocation;
	}

	public String getCurrLocation(){
		return currentLocation;
	}

	public void setCurrLocation(String currentLocation){
		this.currentLocation=currentLocation;
	}

	public boolean equals(Object o){
		return ((hlName.equals(((ELDAId)o).hlName)) && (homeLocation.equals(((ELDAId)o).homeLocation)));
	}

	public int hashCode(){
		return (hlName+homeLocation).hashCode(); //changed 15/2/06
	}

	public String toString(){
		return hlName+"/"+homeLocation+"@"+currentLocation;
	}



}
