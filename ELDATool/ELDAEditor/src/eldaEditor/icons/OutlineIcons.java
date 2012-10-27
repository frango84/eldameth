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

package eldaEditor.icons;

import org.eclipse.swt.graphics.Image;


/**
 * @author samuele
 * 
 */
public class OutlineIcons {
	public static final Image 
	IMAGE_START_POINT =	new Image(null, OutlineIcons.class.getResourceAsStream("initialState.gif")),
	IMAGE_END_POINT =new Image(null, OutlineIcons.class.getResourceAsStream("finalState.gif")),
	IMAGE_JOIN_H =new Image(null, OutlineIcons.class.getResourceAsStream("join_h.gif")),
	IMAGE_JOIN_V =new Image(null, OutlineIcons.class.getResourceAsStream("join_v.gif")),
	IMAGE_FORK_H =new Image(null, OutlineIcons.class.getResourceAsStream("fork_h.gif")),
	IMAGE_FORK_V =new Image(null, OutlineIcons.class.getResourceAsStream("fork_v.gif")),
	IMAGE_STATE =new Image(null, OutlineIcons.class.getResourceAsStream("state1.gif")),
	IMAGE_NOTE =new Image(null, OutlineIcons.class.getResourceAsStream("note_on.gif")),
	IMAGE_TRANSITION =new Image(null, OutlineIcons.class.getResourceAsStream("transition.gif")),
	IMAGE_INDICATION =new Image(null, OutlineIcons.class.getResourceAsStream("indication.gif")),
	IMAGE_TEXTLABEL =new Image(null, OutlineIcons.class.getResourceAsStream("label_off.gif")),
	IMAGE_HORIZONTAL_SEPARATOR =new Image(null, OutlineIcons.class.getResourceAsStream("Horizontalseparator.gif")),
	IMAGE_VERTICAL_SEPARATOR =new Image(null, OutlineIcons.class.getResourceAsStream("Verticalseparator.gif")),
	IMAGE_WIZBAN =new Image(null, OutlineIcons.class.getResourceAsStream("statechartWizban.gif")),
	IMAGE_FIPA= new Image(null, OutlineIcons.class.getResourceAsStream("fipa.gif"));
}
