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

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.ModelElement;

import genericUMLDiagramEditor.editParts.GenericDiagramAbstractEditPart;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.util.ArrayList;
import java.util.List;

import java.io.ByteArrayOutputStream;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.editparts.LayerManager;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Let users export the selected items in the diagram as image.
 * If the source and the target of a connection are selected, the
 * connection itself will be automatically selected. Those unseleceted
 * elements will be hide automatically.
 *  
 * 
 * @author marguu&zaza
 *
 */
public class ExportSelectionAction extends ExportAction {

	public static final String ID = "Export_selection_action";
	private List selected = new ArrayList();

	public ExportSelectionAction(GenericDiagramEditor part) {
		super(part);
		setText("Export selection as image     F3");
		setId(ID);
		setToolTipText("Exports the selected elements as an image");

		// Setto la nuova estensione di default
		super.ext = "_part.jpg";
	}

	/**
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		// Tolgo dagli elementi selezionati il GenericDiagram se presente
		selected.clear();
		for(int i=0; i<getSelectedObjects().size(); i++) {
			if(getSelectedObjects().get(i) instanceof AbstractGraphicalEditPart) {
				AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) getSelectedObjects().get(i);
				if(!(part.getModel() instanceof GenericDiagram))
					selected.add(getSelectedObjects().get(i));
			}
			else if(getSelectedObjects().get(i) instanceof AbstractTreeEditPart) {
				AbstractTreeEditPart partTree = (AbstractTreeEditPart) getSelectedObjects().get(i);
				AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) editor.getGraphViewer().getEditPartRegistry().get(partTree.getModel());
				if(!(part.getModel() instanceof GenericDiagram))
					selected.add(part);
			}
		}

		return selected.size()>0;
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

		Device device = this.editor.getGraphViewer().getControl().getDisplay();
		LayerManager lm = (LayerManager) this.editor.getGraphViewer()
				.getEditPartRegistry().get(LayerManager.ID);
		IFigure figure = lm.getLayer(LayerConstants.PRINTABLE_LAYERS);
		
		// Determino il rettangolo contenente tutti gli oggetti selezionati
		AbstractGraphicalEditPart part0 = (AbstractGraphicalEditPart) selected.get(0);
		IFigure fig0 = (IFigure)part0.getFigure();
		Rectangle r = fig0.getBounds().getCopy();
		
		for(int i=1; i<selected.size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) selected.get(i);
			IFigure fig = (IFigure)part.getFigure();
			r.union(fig.getBounds().getCopy());
		}
		
		// Creo le liste degli oggetti e delle connessioni da nascondere
		List hide = new ArrayList();
		List connHide = new ArrayList();

		// Cerco l'editPart del GenericDiagram
		AbstractGraphicalEditPart root = (AbstractGraphicalEditPart) part0.getViewer().getEditPartRegistry().get(EcoreUtil.getRootContainer((ModelElement)part0.getModel()));

		// Nascondo gli oggetti che non sono selezionati
		for(int i=0; i<root.getChildren().size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) root.getChildren().get(i);
			if(!selected.contains(part) && part instanceof GenericDiagramAbstractEditPart) {
				// Verifico se non è un progenitore di uno degli oggetti selezionati
				boolean noop = false; // NoOperation: Memorizza se non devo effettuare altre operazioni 
				for(int j=0; j<selected.size(); j++) {
					AbstractGraphicalEditPart selPart = (AbstractGraphicalEditPart) selected.get(j);
					if(EcoreUtil.isAncestor((ModelElement)part.getModel(), (ModelElement)selPart.getModel())) {
						noop = true;
						break;
					}
				}
				
				if(!noop) {
					part.getFigure().setVisible(false);
					hide.add(part);
					connHide.addAll(part.getSourceConnections());
					connHide.addAll(part.getTargetConnections());
					
					// Se è un container controllo rimuovo anche le connessioni
					// degli elementi contenuti
					if(part.getModel() instanceof Container)
						connHide.addAll(hideConnection((Container) part.getModel(), part));
				}
			}
		}
		
		// Nascondo le relazioni degli oggetti non selezionati
		for(int i=0; i<connHide.size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) connHide.get(i);
			part.getFigure().setVisible(false);
		}
		hide.addAll(connHide);

		Image image = null;
		GC gc = null;
		Graphics g = null;
		try {
			image = new Image(device, r.width, r.height);
			gc = new GC(image);
			g = new SWTGraphics(gc);
			g.translate(r.x * -1, r.y * -1);

			figure.paint(g);

			ImageLoader imageLoader = new ImageLoader();
			imageLoader.data = new ImageData[] { image.getImageData() };
			imageLoader.save(result, format);
		} finally {
			if (g != null) {
				g.dispose();
			}
			if (gc != null) {
				gc.dispose();
			}
			if (image != null) {
				image.dispose();
			}
		}
		
		// Rivisualizzo tutte le figure
		for(int i=0; i<hide.size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) hide.get(i);
			part.getFigure().setVisible(true);
		}
		
		return result.toByteArray();
	}

	/**
	 * @param model		the model element
	 * @param root		the root editPart
	 * Return			the list of the automatically selected connections
	 */
	private List hideConnection(Container model, AbstractGraphicalEditPart root) {
		List connHide = new ArrayList();
		for(int i=0; i<model.getVisualElements().size(); i++) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) root.getViewer()
				.getEditPartRegistry().get(model.getVisualElements().get(i));
			connHide.addAll(part.getSourceConnections());
			connHide.addAll(part.getTargetConnections());
			
			// Se è un container controllo rimuovo anche le connessioni
			// degli elementi contenuti
			if(part.getModel() instanceof Container)
				connHide.addAll(hideConnection((Container) part.getModel(), part));			
		}
		
		return connHide;
	}
}
