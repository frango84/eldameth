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

import genericUMLDiagramModel.VisualElement;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

/**
 * Utility class for the figure in the GenericDiagram
 * 
 * @author marguu&zaza
 *
 */
public class GenericFigure extends Figure {
	
protected String name = "";
protected String stereotype = "";
protected String orig_stereotype = "";
protected Font font;
protected Rectangle r;
protected VisualElement generic;
protected int newWidth;

public GenericFigure(VisualElement classifier) {
	this.generic=classifier;
	
	 // Creo il font della figura
	FontData fd =new FontData("Arial",10,SWT.NONE);
	font = new Font(null,fd);
}
	
	
/**
 * @param name	the new value of name
 */
public void setName(String name) {
	if (name!=null) this.name = name;
	else 			this.name = generic.getName();
}

/**
 * Return	the value of name
 */
public String getName() {
	return name;
}

/**
 * @param name the new value of stereotype
 */
public void setStereotype(String name) {
	if (!name.equals(""))
		stereotype = "«"+name+"»";
	else
		stereotype = generic.getStereotype();
	orig_stereotype = name;
}

/**
 * Return	the value of stereotype
 */
public String getStereotype() {
	return stereotype;
}

/**
 * Return the bounds of the name
 */
public Rectangle getNameBounds() {
	Dimension dim = textSize(getName(),font);
	return new Rectangle(name_point(), dim);
}

/**
 * @param text  the string
 * Return		the length of the passed string with the defualt font
 */
public int textLength(String text) {
	return FigureUtilities.getTextWidth(text,font);
}

/**
 * @param text  the string
 * @param f  	the used font
 * Return		the length of the passed string
 */
public int textLength(String text, Font f) {
	return FigureUtilities.getTextWidth(text,f);
}

/**
 * @param text  the string
 * @param f  	the used font
 * Return		the size of the passed string
 */
protected Dimension textSize(String text, Font f) {
	return FigureUtilities.getTextExtents(text,f);
}

/**
 * @param stereo_str	the stereotype
 * Return				the point in which the stereotype string should start
 */
protected Point stereo_point(String stereo_str) {
	int length_stereo = textLength(stereo_str);
	int x1=(bounds.x+(bounds.width-length_stereo)/2);
	int y1=(bounds.getTop().y+4);
	
	return new Point(x1,y1);
}

/**
 * Return	the point in which the name string should start
 */
protected Point name_point() {
	String name_str = getName();
	int length_name = textLength(name_str);
	int x2=(bounds.x+(bounds.width-length_name)/2);
	int y2=(bounds.getTop().y+25);
	
	return new Point(x2,y2);
}

/**
 * Return the max size of the inner strings
 */
public int getDinamicWidth(){ 
	// Verifico che la stringa più lunga entri nella figure senza essere tagliata;
	// se così non è allargo lo stato
	// Cerco la dimensione massima del testo inserito come nome
	
	setName(generic.getName());
	setStereotype(generic.getStereotype());
	
	int name_length = textLength(name);
	int stereo_length = textLength(stereotype);
	int max_length = (Math.max(name_length,stereo_length))+30;
	
	// Se la lunghezza non è piccola
	int MIN_WIDTH = generic.getSizeDefault().width;
	
	if(max_length<MIN_WIDTH)
		max_length = MIN_WIDTH;
	
	return  max_length;
}

}
