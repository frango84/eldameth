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

package eldaEditor.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontRegistry;

/**
 * Crea la vista associata all'elemento del modello StartPoint
 * @author samuele
 *
 */
public class ShallowHistoryFigure extends Figure {
    /**	Margini della figure */	
	Rectangle r;
	
	private FontRegistry fr;
	
	/**
	 * Costruttore
	 */
	public ShallowHistoryFigure() {
		fr= new FontRegistry ();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.IFigure#paint(org.eclipse.draw2d.Graphics)
	 */
	public void paint(Graphics g)
	  {
		r=new Rectangle(bounds);
		r.crop(new Insets(1,1,1,1));
		g.setBackgroundColor(ColorConstants.white);
		g.setForegroundColor(ColorConstants.black);
		g.fillOval(r);
		g.drawOval(r);
		
		g.setFont(fr.getBold("Arial"));
		g.drawText("H", r.getLocation().getTranslated(6,4));
	  }
}
