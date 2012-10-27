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

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.tools.CellEditorLocator;

import org.eclipse.jface.viewers.CellEditor;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

/**
 * Define the locator for the CellEditor
 * 
 * @author marguu&zaza
 *
 */
final public class TextCellEditorLocator implements CellEditorLocator {

  private Rectangle bounds;

  public TextCellEditorLocator(String text, Rectangle bounds) {
	this.bounds = bounds;
  }

/**
 * @see org.eclipse.gef.tools.CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
 */
public void relocate(CellEditor celleditor) {
	Text text = (Text)celleditor.getControl();
	Point sel = text.getSelection();
	Point pref = text.computeSize(-1, -1);
	
	// Creo un nuovo rettangolo in cui fisso solo la posizione facendola coincidere
	// con quella del nome dell'elemento che voglio modificare
	Rectangle rect = bounds;
	text.setBounds(rect.x-4, rect.y-1, pref.x+1, pref.y+1);	
	text.setSelection(0);
	text.setSelection(sel);
  }
}
