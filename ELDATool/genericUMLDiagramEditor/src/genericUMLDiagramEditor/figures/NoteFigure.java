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

import genericUMLDiagramModel.Note;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Create the view of a Note
 * 
 * @author marguu&zaza
 *
 */
public class NoteFigure extends Label {
	private int cornerSize=20;
	private Note note;
	private Rectangle rect=Rectangle.SINGLETON;
	
	// Creo il font della figura
  	FontData fd =new FontData("Arial",10,SWT.NONE);
  	Font font = new Font(null,fd);
	
	public NoteFigure(String body, Note n) {	
		super();
	    super.setText(body);
	    this.note=n;
	    this.setFont(font);
	}
	
	/**
	 * @see org.eclipse.draw2d.IFigure#paint(org.eclipse.draw2d.Graphics)
	 */
	public void paint(Graphics g) {
		rect.setBounds(new Rectangle(this.bounds.getLocation().getTranslated(1,1),note.getSize()));
			
		//disegno la figure
		g.setForegroundColor(ColorConstants.black);
		g.setBackgroundColor(ColorConstants.white);
		
		g.translate(getLocation());
		
		PointList outline = new PointList();
		outline.addPoint(0, 0);
		outline.addPoint(rect.width - cornerSize, 0);
		outline.addPoint(rect.width - 1, cornerSize);
		outline.addPoint(rect.width - 1, rect.height - 1);
		outline.addPoint(0, rect.height - 1);
		g.fillPolygon(outline); 
		
		// draw the inner outline
		PointList innerLine = new PointList();
		innerLine.addPoint(rect.width - cornerSize - 1, 0);
		innerLine.addPoint(rect.width - cornerSize - 1, cornerSize);
		innerLine.addPoint(rect.width - 1, cornerSize);
		innerLine.addPoint(rect.width - cornerSize - 1, 0);
		innerLine.addPoint(0, 0);
		innerLine.addPoint(0, rect.height - 1);
		innerLine.addPoint(rect.width - 1, rect.height - 1);
		innerLine.addPoint(rect.width - 1, cornerSize);
		g.drawPolygon(innerLine);
		
		g.drawLine(rect.width - cornerSize - 1, 0, rect.width - 1, cornerSize);
		g.translate(getLocation().getNegated());

		g.drawText((super.getText()),rect.getTopLeft().x+5,rect.getTopLeft().y+ cornerSize);
   }

	/**
	 * @param body		the body of the note
	 */
	public void setComment(String body) {
		if (body!=null)
			super.setText(body);
		else
			super.setText("");
	}
}
