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

package dscDiagramModel.myDataTypes;

import org.eclipse.emf.ecore.impl.*;
import org.eclipse.emf.ecore.EObject;

public class TypeVariable extends EObjectImpl implements EObject{

private String Type="";
private String Name="";
private String Value="";

public TypeVariable ()
{
	
}
public TypeVariable (String a, String b)
{
//	super();
	this.Type=a;
	this.Name=b;
	
}

public TypeVariable (String a, String b, String c)
{
//	super();
	this.Type=a;
	this.Name=b;
	this.Value=c;
	
}

public String getName() {
	return Name;
}
public void setName(String name) {
	Name = name;
}
public String getType() {
	return Type;
}
public void setType(String type) {
	Type = type;
}
public String getValue() {
	return Value;
}
public void setValue(String value) {
	Value = value;
}
public String toString()
{
	
	if (eIsProxy()) return super.toString();
		
	StringBuffer result = new StringBuffer();
	result.append("type=");
	result.append(this.getType());
	result.append(", name=");
	result.append(this.getName());
	if (!this.getValue().equals("") )   
			{
			result.append(", value=");
			result.append(this.getValue());
			}
	else 
		result.append(" ");
	return result.toString();

}
}
