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

//import java.util.Iterator;

import genericUMLDiagramEditor.figures.GenericFigure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.graphics.Color;

import dscDiagramModel.DSCState;


/**
 * Crea la vista associata all'elemento del modello State (ObjectFlow)
 * @author samuele
 *
 */
public class StateFigure extends GenericFigure {
	
	private static Color classColor = new Color(null,255,255,206);
	//new Color(null,255,255,255)); white
	/**	Stringa contenente le informazioni associate alle transizioni interne */	
	private String info ="";
	/**	Margini della figure */	
	private Rectangle r;
	/**	Elemento del modello associato alla figure */	
	private DSCState state;
	
	private FontRegistry fr;
	/**
	 * Costruttore
	 * @param state	Elemento del modello associato alla figure
	 */
	public StateFigure(DSCState state) {
		super(state);
		this.state=state;
//		this.refreshInfo();
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		fr= new FontRegistry ();
//		FontData fd =new FontData("Arial",10,SWT.NONE);
//		FontData fdBold=new FontData("Arial",10,SWT.BOLD);
//		fr.get("Arial");
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	public void paintFigure(Graphics g)
	  {
		String stereo_str="";
		if(getStereotype()!=null && getStereotype().trim().length()>0)
			stereo_str=getStereotype();
		
		String name_str="";
		if(getName()!=null && getName().trim().length()>0)
			name_str=getName();
		
//		String info_str="";
//		if(getInfo()!=null && getInfo().trim().length()>0)
//			info_str=getInfo();
	    
		r=new Rectangle(this.bounds);
		r.crop(new Insets(0,0,1,1));
		g.setBackgroundColor(classColor);
		g.fillRoundRectangle(r,30,30);
		g.drawRoundRectangle(r,30,30);

//		FontData fd =new FontData("Arial",10,SWT.NONE);
		g.setFont(fr.get("Arial"));

		int length_stereo = textLength(stereo_str,g.getFont());
		int x2=(r.x+(r.width-length_stereo)/2);
		int y2=(r.getTop().y+2);
		Point startStereo = new Point(x2,y2);
		g.drawText(stereo_str,startStereo);

//		fd =new FontData("Arial",10,SWT.BOLD);
		g.setFont(fr.getBold("Arial"));

		int length_name = textLength(name_str,g.getFont());
		int x1=(r.x+(r.width-length_name)/2);
		int y1=(r.getTop().y+14);
		Point startName = new Point(x1,y1);
		g.drawText(name_str,startName);
		
		//cambio la figure in base al tipo di stato
		if (state.isIsSimple()==false)
		g.drawLine(r.x,r.y+35,r.x+r.width,r.y+35);
		
		
//		int x3=(r.x+r.width*1/20);
//		int y3=(r.getTop().y+40);
//		Point startInfo = new Point(x3,y3);
//		fd =new FontData("Arial",10,SWT.NONE);
//		g.setFont(new Font(null,fd));
//		g.drawText(info_str,startInfo);
		
//		this.setChildrenOrientation(SWT.VERTICAL);
	  }

/**
 * Imposta le informazioni sulle transizioni interne
 * @param info Le informazioni sulle transizioni interne
 */
//public void refreshInfo() {
//	// Creo una riga per ogni transizione
//	this.info="";
//	Iterator i = state.getInternalTransition().iterator();
//	 while (i.hasNext()) {
//		Transition m =(Transition) i.next();
//		this.info += createInfo(m) + "\n";
//	}
//}

/**
 * Ritorna le informazioni sulle transizioni interne
 * @return	info
 */
public String getInfo() {
	return info;
}

//private String createInfo(Transition relationship) {
//		String event_str="", guard_str="", action_str="";
//		
//		if(relationship.getTrigger()!=null && relationship.getTrigger().getName()!=null && relationship.getTrigger().getName().trim().length()>0)
//			event_str=relationship.getTrigger().getName();
//		if(relationship.getGuard()!=null && relationship.getGuard().getName()!=null && relationship.getGuard().getName().trim().length()>0)
//			guard_str=" [" + relationship.getGuard().getName() + "]";
//		if(relationship.getEffect()!=null && relationship.getEffect().getName()!=null && relationship.getEffect().getName().trim().length()>0) {
//			action_str=" / " + relationship.getEffect().getName();
//		}
//		return  event_str + guard_str + action_str;
//}
}
