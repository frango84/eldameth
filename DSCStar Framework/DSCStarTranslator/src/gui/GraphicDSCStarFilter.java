/*****************************************************************
DSC* TRANSLATOR
Copyright (C) 2010 G. Fortino, F. Rango

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

package gui;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

public class GraphicDSCStarFilter extends FileFilter {

	public static String [] formats = { "BMP", "GIF", "JPEG", "PNG", "XML" };
	private String description;
	private String extension;

	public GraphicDSCStarFilter(String extension, String description) {
		this.description = description;
		this.extension = extension;
	}

	public boolean accept(File file) {
		return file.isDirectory() || file.getName().toLowerCase().endsWith(this.extension.toLowerCase());
	}

	public String getDescription() {
		return this.description;
	}
	
	public String getExtension(){
		return this.extension;
	}

}
