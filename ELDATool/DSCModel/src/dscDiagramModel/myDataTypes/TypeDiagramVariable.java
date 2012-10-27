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

public class TypeDiagramVariable extends TypeVariable{

	private boolean bePresentIntoConstructor=false;
	
	
	public TypeDiagramVariable ()
	{
	}
	
	public TypeDiagramVariable (String a, String b)
	{
		super(a,b);
	}
	
	public TypeDiagramVariable (String a, String b, String c)
	{
		super(a,b,c);
	}
	
	public TypeDiagramVariable (String a, String b, String c, boolean d)
	{
		super(a,b,c);
		this.bePresentIntoConstructor=d;
	}

	public boolean isBePresentIntoConstructor() {
		return bePresentIntoConstructor;
	}

	public void setBePresentIntoConstructor(boolean bePresentIntoConstructor) {
		this.bePresentIntoConstructor = bePresentIntoConstructor;
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
		
		if(this.isBePresentIntoConstructor())
			result.append(", bePresentIntoConstructor=true");
		
		return result.toString();
	}
}
