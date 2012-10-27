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

import genericUMLDiagramModel.Classifier;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Create the view of a Classifier 
 * 
 * @author marguu&zaza
 *
 */
public class ClassifierFigure extends GenericFigure {
	

	Classifier classifier;
	
	public ClassifierFigure(Classifier classifier) {
		super(classifier);
		this.classifier=classifier;
		
		 // Creo il font della figura
		FontData fd =new FontData("Arial",10,SWT.NONE);
		font = new Font(null,fd);
	}
	
	/**
	 * @see org.eclipse.draw2d.IFigure#paint(org.eclipse.draw2d.Graphics)
	 */
	public void paint(Graphics g) {
		// Setto il font del graphics
		g.setFont(font);
		g.setBackgroundColor(this.getBackgroundColor());
		
		setName(classifier.getName());
		setStereotype(classifier.getStereotype());

		String name_str="";
		if(getName()!=null && getName().trim().length()>0)
			name_str=getName();
		String stereo_str="";
		if(getStereotype()!=null && getStereotype().trim().length()>0)
			stereo_str=getStereotype();
		
		int name_length = textLength(name);
		int stereo_length = textLength(stereotype);
		int max_length = (Math.max(name_length,stereo_length))+30;
		
		int MIN_DIM = 50;
		
		// Se la lunghezza non è piccola
		if(max_length<MIN_DIM) max_length = MIN_DIM;
		
		if (max_length>classifier.getSize().width){
			classifier.setSize(new Dimension(max_length,classifier.getSize().height));
			this.bounds.width=max_length;
		}
		if (classifier.getSize().height<MIN_DIM){
			classifier.setSize(new Dimension(classifier.getSize().width,MIN_DIM));
			this.bounds.height=MIN_DIM;
		}

//		int  newWidth=super.getDinamicWidth();
//		
//		if (newWidth>classifier.getSize().width){
//			classifier.setSize(new Dimension(newWidth,classifier.getSize().height));
//			this.bounds.width=newWidth;
//		}

		r=new Rectangle(this.bounds);
		r.crop(new Insets(2,2,2,2));
		g.setClip(r.getExpanded(new Insets(1,1,1,1)));
		
		g.fillRectangle(r);
		g.drawRectangle(r);

		g.fillText(stereo_str,stereo_point(stereo_str));
		g.fillText(name_str,name_point());
	  }
}
