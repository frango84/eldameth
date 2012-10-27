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

package genericUMLDiagramEditor.anchor;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

/**
 * Create the anchor to connect a relationship to another relationship
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramAnchor extends ChopboxAnchor {

	private PolylineConnection figure;

public GenericDiagramAnchor(PolylineConnection fig){
	super(fig);
	this.figure = fig;
}

/**
 * @see org.eclipse.draw2d.ConnectionAnchor#getLocation(org.eclipse.draw2d.geometry.Point)
 */
public Point getLocation(Point reference){
	PointList list = figure.getPoints();
	Point center = list.getMidpoint();
	getOwner().translateToAbsolute(center);
	return center;
}
}
