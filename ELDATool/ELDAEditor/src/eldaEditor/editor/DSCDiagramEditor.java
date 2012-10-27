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

package eldaEditor.editor;

import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramEditor.editor.GenericDiagramModelCreationFactory;
import genericUMLDiagramEditor.icons.Icons;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.TextLabel;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCState;
import dscDiagramModel.DscDiagramModelFactory;
import dscDiagramModel.Transition;
import eldaEditor.actions.PasteActionDSC;
import eldaEditor.actions.PromuoveSimpleToCompositeAction;
import eldaEditor.actions.PromuoveStateToDHSAction;
import eldaEditor.actions.eldatools.CodeGeneratorAction;
import eldaEditor.editparts.DSCDiagramEditPartFactory;
import eldaEditor.icons.OutlineIcons;

/**
 * @author samuele
 *
 */
public class DSCDiagramEditor extends GenericDiagramEditor {
	private IContentOutlinePage outlinePage;
	ResourceSet resourceSet = new ResourceSetImpl();
	URI lastURI;
//	private final boolean isHorizontal = true;
//	private final boolean isVertical = false;

	public DSCDiagramEditor()
	{
		setEditDomain(new DefaultEditDomain(this));

	}

	protected PaletteRoot getPaletteRoot()
	{
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

		CreationToolEntry createCompositeStateEntry =
			new CreationToolEntry(
					"Composite State",
					"State tool to insert  a new composite state element",
					new DSCDiagramModelCreationFactory(DSCState.class, false),
					ImageDescriptor.createFromFile(OutlineIcons.class, "state1.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "state1_on.gif"));

		CreationToolEntry createSimpleStateEntry =
			new CreationToolEntry(
					"Simple State",
					"State tool to insert  a new state element",
					new DSCDiagramModelCreationFactory(DSCState.class, true),
					ImageDescriptor.createFromFile(OutlineIcons.class, "state1.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "state1_on.gif"));
		
		ConnectionCreationToolEntry transitionToolEntry =
			new ConnectionCreationToolEntry(
					"Transition",
					"Transition tool to insert  a new transition",
					new DSCDiagramModelCreationFactory(Transition.class),
					ImageDescriptor.createFromFile(OutlineIcons.class, "transition.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "transition_on.gif"));

//		transitionToolEntry.setToolProperty("defaultCursor", curs);
		transitionToolEntry.setToolProperty("defaultCursor", new Cursor(null,SWT.CURSOR_UPARROW));
		transitionToolEntry.setToolProperty("disabledCursor", new Cursor(null,SWT.CURSOR_NO));

		CreationToolEntry createNoteEntry =
			new CreationToolEntry(
					"Note",
					"Note tool to insert a new note element",
					new GenericDiagramModelCreationFactory(Note.class),
					ImageDescriptor.createFromFile(OutlineIcons.class, "note.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "note_on.gif"));
		createNoteEntry.setToolProperty(AbstractTool.PROPERTY_UNLOAD_WHEN_FINISHED, Boolean.TRUE);

		CreationToolEntry createTextEntry =
			new CreationToolEntry(
					"Text",
					"Text tool to insert a new text label",
					new GenericDiagramModelCreationFactory(TextLabel.class),
					ImageDescriptor.createFromFile(OutlineIcons.class, "label_off.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "label_on.gif"));




		ConnectionCreationToolEntry anchorNoteToItemToolEntry =
			new ConnectionCreationToolEntry(
					"Anchor note to item",
					"AnchorNoteToItem tool to insert a new note relationship",
					new DSCDiagramModelCreationFactory(AnchorNoteToItem.class),
					ImageDescriptor.createFromFile(OutlineIcons.class, "indication.gif"),
					ImageDescriptor.createFromFile(OutlineIcons.class, "indication_on.gif"));
		anchorNoteToItemToolEntry.setToolProperty(AbstractTool.PROPERTY_UNLOAD_WHEN_FINISHED, Boolean.TRUE);

		Image img = Icons.IMAGE_MIRINO;
		ImageData imgData = img.getImageData();
		Cursor curs = new Cursor(null,imgData,0,0);

		anchorNoteToItemToolEntry.setToolProperty("defaultCursor", curs);
		anchorNoteToItemToolEntry.setToolProperty("disabledCursor", new Cursor(null,SWT.CURSOR_NO));

		group.add(3, createSimpleStateEntry);
		group.add(4, createCompositeStateEntry);
		group.add(5, transitionToolEntry);
		group.add(6, new PaletteSeparator());
//		group.add(5, createStartPointEntry);
//		group.add(6, createEndPointEntry);
//		group.add(7, createHorizontalJoinEntry);
//		group.add(8, createVerticalJoinEntry);
//		group.add(9, createHorizontalForkEntry);
//		group.add(10, createVerticalForkEntry);
		group.add(7,new PaletteSeparator());
		group.add(8, createTextEntry);
		group.add(9, createNoteEntry);
		group.add(10, anchorNoteToItemToolEntry);
//		group.add(9, createHorizontalLineEntry);
//		group.add(15, createVerticalLineEntry);
//		group.add(9,new PaletteSeparator());
		
		

		return root;
	}

	protected void initializeGraphicalViewer()
	{
		getGraphicalViewer().setEditPartFactory(new DSCDiagramEditPartFactory());
		getGraphicalViewer().setContents(getContents());

	}

	public void configureGraphicalViewer()
	{
		super.configureGraphicalViewer();
		//redefined context menu
		getGraphicalViewer().setContextMenu(new DSCDiagramContextMenuProvider(getActionRegistry(), getGraphicalViewer()));
		//redefined hot key
		getGraphicalViewer().setKeyHandler(new GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(createKeyHandler()));
	}



	/**
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createActions()
	 */
	public void createActions()	{
		super.createActions();

		// Aggiungo l'azione per incollare gli elementi
		ActionRegistry registry = getActionRegistry();
		IAction action;
		action = new PasteActionDSC(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		//aggiungo l'azione per promuovere i simple state a composiste state

		action = new PromuoveSimpleToCompositeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		//Aggiungo l'azione per promuovere gli state a DES

		action= new PromuoveStateToDHSAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action=new CodeGeneratorAction();
		registry.registerAction(action);
		getSelectionActions().add(action.getId());
	}
	protected KeyHandler createKeyHandler() {
		KeyHandler keyHandler=super.createKeyHandler();

		String id =CodeGeneratorAction.ID;
		keyHandler.put(KeyStroke.getPressed(SWT.F6, 0), getActionRegistry().getAction(id));

		return keyHandler;
	}

	public Object getAdapter(Class aClass)
	{
		if (aClass == IContentOutlinePage.class) {
			if (outlinePage == null)
				outlinePage = new DSCDiagramOutlinePage(this, new TreeViewer(),
						getActionRegistry(), getEditDomain(),
						getGraphicalViewer(), getSelectionSynchronizer());
			return outlinePage;
		}
		else
			return super.getAdapter(aClass);
	}

	/**
	 * Return the associated ModelFactory
	 */
	public EFactory getModelFactory() {
		return DscDiagramModelFactory.eINSTANCE;
	}
}
