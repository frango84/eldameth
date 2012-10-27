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

import genericUMLDiagramModel.*;

import genericUMLDiagramEditor.actions.CopyAction;
import genericUMLDiagramEditor.actions.PasteAction;
import genericUMLDiagramEditor.actions.ExportAction;
import genericUMLDiagramEditor.actions.ExportSelectionAction;
import genericUMLDiagramEditor.actions.RefreshAction;
import genericUMLDiagramEditor.actions.ShowAllAction;
import genericUMLDiagramEditor.actions.VisibilityAction;
import genericUMLDiagramEditor.commands.CreateFileName;
import genericUMLDiagramEditor.editParts.GenericDiagramEditPartFactory;
import genericUMLDiagramEditor.icons.Icons;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.*;

import org.eclipse.draw2d.PositionConstants;

import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.parts.*;
import org.eclipse.gef.ui.actions.*;
import org.eclipse.gef.palette.*;
import org.eclipse.gef.*;
import org.eclipse.gef.commands.*;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.*;
import org.eclipse.ui.views.contentoutline.*;
import org.eclipse.ui.IFileEditorInput;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.*;

/**
 * Create the main interface for the editor
 * 
 * @author marguu&zaza
 *
 */
public class GenericDiagramEditor  extends GraphicalEditorWithPalette 
{
	private PropertySheetPage propPage;
	private IContentOutlinePage outlinePage;
	private boolean saveAlreadyRequested;	
	private GenericDiagram content;
	private ResourceSet resourceSet = new ResourceSetImpl();
	private URI lastURI;

	public GenericDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	/**
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithPalette#getPaletteRoot()
	 */
	protected PaletteRoot getPaletteRoot() {
		PaletteRoot root = new PaletteRoot();
		PaletteGroup group = new PaletteGroup("Tools");

		// Add a selection tool to the group
		ToolEntry tool = new SelectionToolEntry();
		group.add(0,tool);
		root.setDefaultEntry(tool);
		
		// Add a marquee tool to the group
		ToolEntry marqueeToolEntry = new MarqueeToolEntry();
		marqueeToolEntry.setToolProperty(AbstractTool.PROPERTY_UNLOAD_WHEN_FINISHED, Boolean.TRUE);
		group.add(1,marqueeToolEntry);

		// Add a (unnamed) separator to the group
		group.add(2,new PaletteSeparator());
		
		root.add(group);
		
		CreationToolEntry createClassifierEntry =
			new CreationToolEntry(
				"Element",
				"Element tool to insert a new generic element",
				new GenericDiagramModelCreationFactory(Classifier.class),
				ImageDescriptor.createFromFile(Icons.class, "instance.gif"),
				ImageDescriptor.createFromFile(Icons.class, "instance_on.gif"));
		
		CreationToolEntry createContainerEntry =
			new CreationToolEntry(
				"Container",
				"Container tool to insert a new generic container",
				new GenericDiagramModelCreationFactory(Container.class),
				ImageDescriptor.createFromFile(Icons.class, "package.gif"),
				ImageDescriptor.createFromFile(Icons.class, "package_on.gif"));

		CreationToolEntry createNoteEntry =
			new CreationToolEntry(
				"Note",
				"Note tool to insert a new note element",
				new GenericDiagramModelCreationFactory(Note.class),
				ImageDescriptor.createFromFile(Icons.class, "note.gif"),
				ImageDescriptor.createFromFile(Icons.class, "note_on.gif"));
		
		CreationToolEntry createTextEntry =
			new CreationToolEntry(
				"Text",
				"Text tool to insert a new text label",
				new GenericDiagramModelCreationFactory(TextLabel.class),
				ImageDescriptor.createFromFile(Icons.class, "label_off.gif"),
				ImageDescriptor.createFromFile(Icons.class, "label_on.gif"));
		
		CreationToolEntry createHorizontalLineEntry =
			new CreationToolEntry(
				"Horizontal separator",
				"Horizontal separator tool to insert a new horizontal separator",
				new GenericDiagramModelCreationFactory(HorizontalLineSeparator.class),
				ImageDescriptor.createFromFile(Icons.class, "Horizontalseparator.gif"),
				ImageDescriptor.createFromFile(Icons.class, "Horizontalseparator.gif"));
		
		CreationToolEntry createVerticalLineEntry =
			new CreationToolEntry(
				"Vertical separator",
				"Vertical separator tool to insert a new vertical separator",
				new GenericDiagramModelCreationFactory(VerticalLineSeparator.class),
				ImageDescriptor.createFromFile(Icons.class, "Verticalseparator.gif"),
				ImageDescriptor.createFromFile(Icons.class, "Verticalseparator.gif"));
		
		ConnectionCreationToolEntry relationToolEntry =
			new ConnectionCreationToolEntry(
					"Relationship",
					"Relationship tool to insert a new relationship",
					new GenericDiagramModelCreationFactory(Relationship.class),
					ImageDescriptor.createFromFile(Icons.class, "communication.gif"),
					ImageDescriptor.createFromFile(Icons.class, "communication_on.gif"));
		Image img = Icons.IMAGE_MIRINO;
		ImageData imgData = img.getImageData();
		Cursor curs = new Cursor(null,imgData,0,0);
		relationToolEntry.setToolProperty("defaultCursor", curs);
		relationToolEntry.setToolProperty("disabledCursor", new Cursor(null,SWT.CURSOR_NO));
	
		group.add(3, createClassifierEntry);
		group.add(4, createContainerEntry);
		group.add(5,new PaletteSeparator());
		group.add(6, createNoteEntry);
		group.add(7, createTextEntry);
		group.add(8, createHorizontalLineEntry);
		group.add(9, createVerticalLineEntry);
		group.add(10,new PaletteSeparator());
		group.add(11, relationToolEntry);
		
		return root;
	}

	/**
	 * @see org.eclipse.gef.commands.CommandStackListener#commandStackChanged(java.util.EventObject)
	 */
	public void commandStackChanged(EventObject event) {
		super.commandStackChanged(event);
		if (isDirty() && !saveAlreadyRequested) {
			saveAlreadyRequested = true;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
		else {
			saveAlreadyRequested = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}
	
	/**
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		getGraphicalViewer().setContextMenu(new GenericDiagramContextMenuProvider(getActionRegistry(), getGraphicalViewer()));

		ScalableRootEditPart root = new ScalableRootEditPart();

		List zoomLevels = new ArrayList(3);
		zoomLevels.add(ZoomManager.FIT_ALL);
		zoomLevels.add(ZoomManager.FIT_WIDTH);
		zoomLevels.add(ZoomManager.FIT_HEIGHT);
		root.getZoomManager().setZoomLevelContributions(zoomLevels);

		IAction zoomIn = new ZoomInAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomIn);
		getSite().getKeyBindingService().registerAction(zoomIn);

		IAction zoomOut = new ZoomOutAction(root.getZoomManager());
		getActionRegistry().registerAction(zoomOut);
		getSite().getKeyBindingService().registerAction(zoomOut);

		getGraphicalViewer().setRootEditPart(root);

		//keyhandler:
		getGraphicalViewer().setKeyHandler(new GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(createKeyHandler()));
	}
	
	/**
	 * Return the KeyHandler
	 */
	protected KeyHandler createKeyHandler() {
		KeyHandler keyHandler = new KeyHandler();

		String id = ActionFactory.DELETE.getId();
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), getActionRegistry().getAction(id));
		
		id = ExportSelectionAction.ID;
		keyHandler.put(KeyStroke.getPressed(SWT.F3, 0), getActionRegistry().getAction(id));

		id = ExportAction.ID;
		keyHandler.put(KeyStroke.getPressed(SWT.F4, 0), getActionRegistry().getAction(id));

		id = RefreshAction.ID;
		keyHandler.put(KeyStroke.getPressed(SWT.F5, 0), getActionRegistry().getAction(id));

		return keyHandler;
	}
	
	/**
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#initializeGraphicalViewer()
	 */
	protected void initializeGraphicalViewer() {
		getGraphicalViewer().setEditPartFactory(new GenericDiagramEditPartFactory());
		getGraphicalViewer().setContents(getContents());
	}
	
	/**
	 * Return the associated GraphicalViewer
	 */
	public GraphicalViewer getGraphViewer() {
		return getGraphicalViewer();
	}
	
	/**
	 * Return the associated ModelFactory
	 */
	public EFactory getModelFactory() {
		return GenericUMLDiagramModelFactory.eINSTANCE;
	}

	/**
	 * Return the instance of the GenericDiagram
	 */
	public GenericDiagram getContents() {
		return content;
	}
	
	/**
	 * Return a new istance of the GenericDiagram
	 */
	protected GenericDiagram createContents() {
	    GenericUMLDiagramModelFactory factory= GenericUMLDiagramModelFactory.eINSTANCE;
		GenericDiagram diagram=factory.createGenericDiagram();
		
	    return diagram;
	}

	/**
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
	 */
	public void createActions()	{
		super.createActions();
		
		ActionRegistry registry = getActionRegistry();
		IAction action;
		
		action = new VisibilityAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ExportAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new CopyAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new PasteAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ExportSelectionAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart)this, PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		// Aggiungo l'azione per il refresh del diagramma
		action = new RefreshAction((IWorkbenchPart)this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
		
		
		// Aggiungo l'azione per tornare a visualizzare tutti gli elementi del diagrammi
		action = new ShowAllAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(Composite parent)
	{
		super.createPartControl(parent);

		IEditorSite pageSite = getEditorSite();
		ActionRegistry registry = getActionRegistry();
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
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class aClass) {
		if (aClass == CommandStack.class)
			return getCommandStack();
		else if (aClass == IPropertySheetPage.class)
			return getPropertySheetPage();
		else if (aClass == IContentOutlinePage.class) {
			if (outlinePage == null) {
				outlinePage = new GenericDiagramOutlinePage(this, new TreeViewer(),
						getActionRegistry(), getEditDomain(),
						getGraphicalViewer(), getSelectionSynchronizer());
			  }
			return outlinePage;
		}
		else if (aClass == ZoomManager.class)
			return getGraphicalViewer().getProperty(ZoomManager.class.toString());
		else
			return super.getAdapter(aClass);
	}

	/**
	 * Return the PropertySheetPage value
	 */
	public PropertySheetPage getPropertySheetPage()
	{
		if (propPage == null)
		{
			propPage = new PropertySheetPage();
			propPage.setRootEntry(new UndoablePropertySheetEntry(getCommandStack()));
		}
		return propPage;
	}
	
	/**
	 * Dispose the outline view 
	 */
	protected void disposeOutlinePage() {
		outlinePage = null;
	}
	
	/**
	 * Mark for the serialization all the elements contained with its own id
	 *  
	 * @param pack		the container
	 * @param resource	the resource to save
	 */
	protected void markContainer(Container pack, XMIResource resource) {
		// Setto gli id di tutti gli elementi contenuti in un container
		for(int i=0; i<pack.getVisualElements().size(); i++) {
			ModelElement model = (ModelElement) pack.getVisualElements().get(i);
			resource.setID(model, model.getID());
			
			if(model instanceof Relationship) {
				ConnectionLabel label = ((Relationship)model).getConnectionLabel();
				if(label!=null)	resource.setID(label, label.getID());
			}
			
			if(model instanceof Container) markContainer((Container) model, resource);
		}
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void doSave(IProgressMonitor monitor)
	{
		XMIResource XMIresource = new XMIResourceImpl(lastURI);
	    XMIresource.getContents().add(content);

		// Cerco il nome del file
	    String strFile = CreateFileName.getName();

		// Assegno l'ID al diagram
		XMIresource.setID(content,strFile);

		// Adssegno l'ID a tutti i ModelElement contenuti nel diagram
		for(int i=0; i<content.getModelElements().size(); i++) {
			ModelElement model = (ModelElement) content.getModelElements().get(i);
			XMIresource.setID(model, model.getID());

			if(model instanceof Relationship) {
				ConnectionLabel label = ((Relationship)model).getConnectionLabel();
				if(label!=null)	XMIresource.setID(label, label.getID());
			}
			
			if(model instanceof Container) markContainer((Container) model, XMIresource);
		}

	    try {
			XMIresource.save(null);
		    getCommandStack().markSaveLocation();
		} catch (IOException ioe) {
			String error = "The resource cannot be saved while there are errors:\n\n"
				+ ioe.getLocalizedMessage();
			System.out.println(error);
		}
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	public void doSaveAs() {
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		
		IPath path = dialog.getResult();
		if (path != null) {
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			try {
					new ProgressMonitorDialog(shell).run(
						false, 
						false, 
						new WorkspaceModifyOperation() {
							public void execute(final IProgressMonitor monitor) {
								URI fileURI = URI.createURI(file.getFullPath().toString());
								
								XMIResource newresource = new XMIResourceImpl(fileURI);
							    newresource.getContents().add(content);
							    
								// Cerco il nome del file
							    String strFile = CreateFileName.getName();
								
								// Assegno l'ID al diagram
								newresource.setID(content,strFile);

								// Adssegno l'ID a tutti i ModelElement contenuti nel diagram
								for(int i=0; i<content.getModelElements().size(); i++) {
									ModelElement model = (ModelElement) content.getModelElements().get(i);
									newresource.setID(model, model.getID());

									if(model instanceof Relationship) {
										ConnectionLabel label = ((Relationship)model).getConnectionLabel();
										if(label!=null)	newresource.setID(label, label.getID());
									}
									
									if(model instanceof Container) markContainer((Container) model, newresource);
								}
								
								try {
								  	newresource.save(Collections.EMPTY_MAP);
								}
								catch (IOException e) {}
								getCommandStack().markSaveLocation();
								setInput(new FileEditorInput(file));
							}
						});
			}
			
			catch (InterruptedException ie) {
	  			// should not happen, since the monitor dialog is not cancelable
				ie.printStackTrace(); 
			}
			catch (InvocationTargetException ite) { 
				ite.printStackTrace(); 
			}
		}
	}

	/**
	 * @see org.eclipse.ui.ISaveablePart#isDirty()
	 */
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}
	
	/**
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	/**
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	public void setInput(IEditorInput input) {
		super.setInput(input);

		IFile file = ((IFileEditorInput)input).getFile();
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString());
		lastURI=URI.createPlatformResourceURI(file.getFullPath().toString());

		Resource resource = resourceSet.getResource(uri, true);

		EList l = resource.getContents();
	    Iterator i = l.iterator();
	    while (i.hasNext()) {
	        Object o = i.next();
	        if (o instanceof GenericDiagram)
	            content = (GenericDiagram) o;
	    };
	    
		setPartName(file.getName());
	}
}
