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

import genericUMLDiagramModel.ConnectionLabel;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * Define the locator for the ConnectionLabel
 * 
 * @author marguu&zaza
 *
 */
public class ConnLabelConstraint implements Locator {
	
	private ConnectionLabel label;
	private PolylineConnection connFigure;
	private String text;
	
	public ConnLabelConstraint(String text, ConnectionLabel label,PolylineConnection connFigure) {
	  this.text = text;
	  this.label = label;
	  this.connFigure = connFigure;
	}
	
	/**
	 * @see org.eclipse.draw2d.Locator#relocate(org.eclipse.draw2d.IFigure)
	 */
	public void relocate(IFigure figure) {
	  Dimension minimum = FigureUtilities.getTextExtents(text,figure.getFont());
	  figure.setSize(minimum);

	  Point location = connFigure.getPoints().getMidpoint();
	  Point offsetCopy = label.getOffset().getCopy();
	  offsetCopy.translate(location);
	  figure.setLocation(offsetCopy); 
	  
	  if(label.isIsVisible())
		  ((Label)figure).setText(text);
	  else
		  ((Label)figure).setText("");
	}
}
