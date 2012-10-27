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

import genericUMLDiagramEditor.editor.GenericDiagramContextMenuProvider;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramEditor.editor.GenericDiagramOutlinePage;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.SelectionSynchronizer;

import eldaEditor.editparts.DSCDiagramTreeEditPartFactory;

/**
 * @author samuele
 *
 */
public class DSCDiagramOutlinePage extends GenericDiagramOutlinePage {
	static final int ID_OUTLINE  = 0;
	static final int ID_OVERVIEW = 1;

	public DSCDiagramOutlinePage(GenericDiagramEditor editor, EditPartViewer viewer,
		ActionRegistry registry, DefaultEditDomain editDomain,
		GraphicalViewer graphViewer, SelectionSynchronizer selSync) {

		super(editor,viewer,registry,editDomain,graphViewer,selSync);
	}

	protected void initializeOutlineViewer() {
		getViewer().setEditDomain(editDomain);
		getViewer().setEditPartFactory(new DSCDiagramTreeEditPartFactory());
		ContextMenuProvider provider = new GenericDiagramContextMenuProvider(registry, graphViewer);
		getViewer().setContextMenu(provider);
		getSite().registerContextMenu(
			"org.eclipse.gef.examples.logic.outline.contextmenu", //$NON-NLS-1$
			provider, getSite().getSelectionProvider());
	}
}
