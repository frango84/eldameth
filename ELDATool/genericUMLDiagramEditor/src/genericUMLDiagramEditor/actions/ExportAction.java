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

package genericUMLDiagramEditor.actions;

import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;

import org.eclipse.swt.dnd.Clipboard;

/**
 * Let users export the whole diagram as image
 * 
 * @author marguu&zaza
 *
 */
public class ExportAction extends SelectionAction {

	public static final String ID = "Export_action";
	protected String ext = ".jpg";
	protected IPath path;

	protected GenericDiagramEditor editor;

	public ExportAction(GenericDiagramEditor part) {
		super(part);
		this.editor = part;
		setText("Export diagram  as image     F4");
		setId(ID);
		setToolTipText("Exports the diagram as an image");
	}

	/**
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	public void run() {
		SaveAsDialog dialog = new SaveAsDialog(getWorkbenchPart().getSite().getShell());
		IFile editorFile=((FileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile();
		String fileName = editorFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		
		dialog.setOriginalFile(editorFile.getProject().getFile(fileName + ext));
		dialog.create();
		dialog.setMessage("Specify a name and location for the image. Either enter .jpg, .bmp, .gif or .png  extension.");
		dialog.open();

		path = dialog.getResult();
		if (path != null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IFile file = workspace.getRoot().getFile(path);
			String ext = file.getFileExtension();
			if (ext == null
					|| ext.length() == 0
					|| !(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("bmp")
					|| ext.equalsIgnoreCase("gif") || ext.equalsIgnoreCase("png"))) {
				System.out.println("Error during processing of export");
			} else {
				saveImage(file, (ext.equalsIgnoreCase("jpg") ? SWT.IMAGE_JPEG
						: SWT.IMAGE_BMP));
			}
		}
	}

	/**
	 * Saves an encoded image from this viewer.
	 * 
	 * @param format
	 *            one of SWT.IMAGE_BMP, SWT.IMAGE_BMP_RLE, SWT.IMAGE_GIF
	 *            SWT.IMAGE_ICO, SWT.IMAGE_JPEG or SWT.IMAGE_PNG
	 * Return the bytes of an encoded image for the specified viewer
	 */
	public void saveImage(final IFile file, final int format) {
		WorkspaceModifyOperation op = new WorkspaceModifyOperation() {

			public void execute(final IProgressMonitor monitor)
					throws CoreException {
				try {
					if (file.exists()) {
						file.setContents(new ByteArrayInputStream(
								createImage(format)), true, false, monitor);
					} else {
						file.create(new ByteArrayInputStream(
								createImage(format)), true, monitor);
					}
				} catch (CoreException e) {
					ErrorDialog.openError(getWorkbenchPart().getSite()
							.getShell(), "Error",
							"Error during processing of export", e.getStatus());
				}
			}
		};

		try {
			Shell shell = getWorkbenchPart().getSite().getWorkbenchWindow().getShell();
			new ProgressMonitorDialog(shell).run(false, true, op);
		} catch (InvocationTargetException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		return true;
	}

	/**
	 * Returns the bytes of an encoded image from this viewer.
	 * 
	 * @param format
	 *            one of SWT.IMAGE_BMP, SWT.IMAGE_BMP_RLE, SWT.IMAGE_GIF
	 *            SWT.IMAGE_ICO, SWT.IMAGE_JPEG or SWT.IMAGE_PNG
	 * Return the bytes of an encoded image for the specified viewer
	 */
	public byte[] createImage(int format) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		Device device = editor.getGraphViewer().getControl().getDisplay();
		LayerManager lm = (LayerManager) this.editor.getGraphViewer()
				.getEditPartRegistry().get(LayerManager.ID);
		IFigure figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
		Rectangle r = figure.getClientArea();

		Image image = null;
		GC gc = null;
		Graphics g = null;
		Clipboard clip = null;
		try {
			image = new Image(device, r.width, r.height);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			g.translate(r.x * -1, r.y * -1);

			figure.paint(g);

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(result, format);
		} 
		finally {
			if (g != null) {
				g.dispose();
			}
			if (gc != null) {
				gc.dispose();
			}
			if (image != null) {
				image.dispose();
			}
			if (clip != null) {
				clip.dispose();
			}
		}
		return result.toByteArray();
	}

}
