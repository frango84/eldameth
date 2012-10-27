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

import genericUMLDiagramModel.VisualElement;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Text;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

/**
 * Define the manager for the DirectEdit
 * 
 * @author marguu&zaza
 *
 */
public class TextEditManager extends DirectEditManager {

  private double cachedZoom = -1.0;
  private Font scaledFont;
  private String init_name;
  private ZoomListener zoomListener = new ZoomListener() {
		public void zoomChanged(double newZoom) {
			updateScaledFont(newZoom);
		}
  };

  public TextEditManager(GraphicalEditPart source,Class editorType,CellEditorLocator locator) {
	super(source, editorType, locator);
  }

/**
 * Dispose the font
 */
private void disposeScaledFont() {
		if (scaledFont != null) {
			scaledFont.dispose();
			scaledFont = null;
		}
	}

/**
 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
 */
protected void bringDown() {
	ZoomManager zoomMgr = (ZoomManager)getEditPart().getViewer().getProperty(ZoomManager.class.toString());
	if (zoomMgr != null)
		zoomMgr.removeZoomListener(zoomListener);

	super.bringDown();

	// dispose any scaled fonts that might have been created
	disposeScaledFont();
  }
  
/**
 * Sets the initial text for the DirectEdit
 * 
 * @param str		the initial string
 */
public void setInitame(String str) {
	  init_name = str;
  }

/**
 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
 */
protected void initCellEditor() {
	// update text
	VisualElement model = (VisualElement)((GraphicalEditPart)getEditPart()).getModel();
	String name = init_name;

	Label label = new Label(name);
	String initialLabelText = label.getText();
	getCellEditor().setValue(initialLabelText);
	Text text = (Text)getCellEditor().getControl();

	// update font
	AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) ((GraphicalEditPart)getEditPart()).getViewer().getEditPartRegistry().get(model);
	IFigure figure = part.getFigure();
	ZoomManager zoomMgr = (ZoomManager)getEditPart().getViewer().getProperty(ZoomManager.class.toString());
	if (zoomMgr != null) {
		// this will force the font to be set
		cachedZoom = -1.0;
		updateScaledFont(zoomMgr.getZoom());
		zoomMgr.addZoomListener(zoomListener);
	}
	else
		text.setFont(figure.getFont());
	text.selectAll();
  }
  
/**
 * Update the zoom factor
 * 
 * @param zoom  the new zoom factor
 */
private void updateScaledFont(double zoom) {
		if (cachedZoom == zoom)
			return;
		
		Text text = (Text)getCellEditor().getControl();
		Font font = getEditPart().getFigure().getFont();
		
		disposeScaledFont();
		cachedZoom = zoom;
		if (zoom == 1.0)
			text.setFont(font);
		else {
			FontData fd = font.getFontData()[0];
			fd.setHeight((int)(fd.getHeight() * zoom));
			text.setFont(scaledFont = new Font(null, fd));
		}
	}
}
