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

package genericUMLDiagramEditor.figures;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;

/**
 * Create the view of an VerticalLineSeparator
 * 
 * @author marguu&zaza
 *
 */
public class VerticalLineFigure extends Figure {
	
	public VerticalLineFigure() {}
	
	/**
	 * @see org.eclipse.draw2d.IFigure#paint(org.eclipse.draw2d.Graphics)
	 */
	public void paint(Graphics g)
	{		
		g.setLineStyle(2);
		g.setLineWidth(1);
		
		g.drawLine(this.bounds.getTop(),this.bounds.getBottom());
	}
}
