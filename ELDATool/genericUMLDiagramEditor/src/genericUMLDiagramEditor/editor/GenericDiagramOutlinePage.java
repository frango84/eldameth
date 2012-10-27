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

package genericUMLDiagramEditor.editor;

import genericUMLDiagramEditor.editParts.GenericDiagramTreeEditPartFactory;
import genericUMLDiagramEditor.icons.Icons;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.PageBook;

/**
 * Create the content for the Outline View
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramOutlinePage extends ContentOutlinePage implements IAdaptable
{
	private final GenericDiagramEditor editor;
	private PageBook pageBook;
	private Control outline;
	private Canvas overview;
	private IAction showOutlineAction, showOverviewAction;
	static final int ID_OUTLINE  = 0;
	static final int ID_OVERVIEW = 1;
	private Thumbnail thumbnail;
	private DisposeListener disposeListener;
	
	protected ActionRegistry registry;
	protected DefaultEditDomain editDomain;
	protected GraphicalViewer graphViewer;
	protected SelectionSynchronizer selSync;

	public GenericDiagramOutlinePage(GenericDiagramEditor editor, EditPartViewer viewer,
		ActionRegistry registry, DefaultEditDomain editDomain,
		GraphicalViewer graphViewer, SelectionSynchronizer selSync) {

		super(viewer);
		
		if (editor == null) {
			throw new IllegalArgumentException();
		}
		
		this.editor = editor;
		this.registry = registry;
		this.editDomain = editDomain;
		this.graphViewer = graphViewer;
		this.selSync = selSync;
	}

	/**
	 * @see org.eclipse.ui.part.IPageBookViewPage#init(org.eclipse.ui.part.IPageSite)
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		IActionBars bars = pageSite.getActionBars();
		String id = ActionFactory.UNDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.REDO.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		id = ActionFactory.DELETE.getId();
		bars.setGlobalActionHandler(id, registry.getAction(id));
		bars.updateActionBars();
	}

	/**
	 * Initialize the outline viewer
	 */
	protected void initializeOutlineViewer() {
		getViewer().setEditDomain(editDomain);
		getViewer().setEditPartFactory(new GenericDiagramTreeEditPartFactory());
		ContextMenuProvider provider = new GenericDiagramContextMenuProvider(registry, graphViewer);
		getViewer().setContextMenu(provider);
		getSite().registerContextMenu(
			"org.eclipse.gef.examples.logic.outline.contextmenu", //$NON-NLS-1$
			provider, getSite().getSelectionProvider());
	}
	
	/**
	 * Configure the outline viewer
	 */
	protected void configureOutlineViewer(){
		// Effettuo le inizializzazioni di base
		initializeOutlineViewer();
		
		// Permette la cancellazione degli elementi premendo il tasto canc
		KeyHandler keyHandler = new KeyHandler();
		String id = ActionFactory.DELETE.getId();
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), registry.getAction(id));
		getViewer().setKeyHandler(new GraphicalViewerKeyHandler(graphViewer).setParent(keyHandler));
	
		IToolBarManager tbm = getSite().getActionBars().getToolBarManager();
		showOutlineAction = new Action() {
			public void run() {
				showPage(ID_OUTLINE);
			}
		};
	
		showOutlineAction.setImageDescriptor(ImageDescriptor.createFromImage(Icons.IMAGE_OUTLINE));
		tbm.add(showOutlineAction);
	
		showOverviewAction = new Action() {
			public void run() {
				showPage(ID_OVERVIEW);
			}
		};
		
		showOverviewAction.setImageDescriptor(ImageDescriptor.createFromImage(Icons.IMAGE_OVERVIEW));
		tbm.add(showOverviewAction);
	
		showPage(ID_OUTLINE);
	}

	/**
	 * @see org.eclipse.ui.part.IPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent){
		pageBook = new PageBook(parent, SWT.NONE);
		outline = getViewer().createControl(pageBook);
		overview = new Canvas(pageBook, SWT.NONE);
		pageBook.showPage(outline);
		configureOutlineViewer();
		hookOutlineViewer();
		setContents(editor.getContents());
	}

	/**
	 * @see org.eclipse.ui.part.IPage#dispose()
	 */
	public void dispose(){
		unhookOutlineViewer();
		if (thumbnail != null) {
			thumbnail.deactivate();
			thumbnail = null;
		}
		super.dispose();
		editor.disposeOutlinePage();
	}

	/**
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class)
			return graphViewer.getProperty(ZoomManager.class.toString());
		return null;
	}

	/**
	 * @see org.eclipse.ui.part.IPage#getControl()
	 */
	public Control getControl() {
		return pageBook;
	}

	/**
	 * Hook the outline viewer
	 */
	protected void hookOutlineViewer(){
		selSync.addViewer(getViewer());
	}

	/**
	 * Initialize the overview
	 */
	protected void initializeOverview() {
		LightweightSystem lws = new LightweightSystem(overview);
		RootEditPart rep = graphViewer.getRootEditPart();
		if (rep instanceof ScalableRootEditPart) {
			ScalableRootEditPart root = (ScalableRootEditPart)rep;
			thumbnail = new ScrollableThumbnail((Viewport)root.getFigure());
			thumbnail.setBorder(new MarginBorder(3));
			thumbnail.setSource(root.getLayer(LayerConstants.PRINTABLE_LAYERS));
			lws.setContents(thumbnail);
			disposeListener = new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			graphViewer.getControl().addDisposeListener(disposeListener);
		}
	}

	/**
	 * Add  the passad object to the viewer
	 * 
	 * @param contents the object to add
	 */
	public void setContents(Object contents) {
		getViewer().setContents(contents);
	}

	/**
	 * Show the selected type for the outline
	 * 
	 * @param id  the type for the outline
	 */
	protected void showPage(int id) {
		if (id == ID_OUTLINE) {
			showOutlineAction.setChecked(true);
			showOverviewAction.setChecked(false);
			pageBook.showPage(outline);
			if (thumbnail != null)
				thumbnail.setVisible(false);
		} else if (id == ID_OVERVIEW) {
			if (thumbnail == null)
				initializeOverview();
			showOutlineAction.setChecked(false);
			showOverviewAction.setChecked(true);
			pageBook.showPage(overview);
			thumbnail.setVisible(true);
		}
	}

	/**
	 * Unhook the outline viewer
	 */
	protected void unhookOutlineViewer(){
		selSync.removeViewer(getViewer());
		if (disposeListener != null && graphViewer.getControl() != null && !graphViewer.getControl().isDisposed())
			graphViewer.getControl().removeDisposeListener(disposeListener);
	}
}
