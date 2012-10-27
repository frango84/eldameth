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

package genericUMLDiagramEditor.editParts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.tools.DragEditPartsTracker;

/**
 * Define the tracker for the ConnectionLabel
 * 
 * @author Angelo
 *
 */
public class ConnTextTracker extends DragEditPartsTracker {

	RelationshipEditPart connection;

  public ConnTextTracker(EditPart source,RelationshipEditPart connection)  {
  	super(source);
  	this.connection = connection;
  }

/**
 * @see org.eclipse.gef.tools.TargetingTool#getTargetEditPart()
 */
protected EditPart getTargetEditPart(){
	return connection;
  }
}
