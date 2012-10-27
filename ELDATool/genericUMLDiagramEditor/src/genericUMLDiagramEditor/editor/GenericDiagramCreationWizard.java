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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;

import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Create new new .generic-file. 
 * Those files can be used with the GenericEditor (see plugin.xml).
 * 
 * @author marguu&zaza
 */
public class GenericDiagramCreationWizard extends Wizard implements INewWizard {

protected GenericDiagramCreationPage page1;

/**
 * @see org.eclipse.jface.wizard.IWizard#addPages()
 */
public void addPages() {
	// add pages to this wizard
	addPage(page1); 
}

/**
 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
 */
public void init(IWorkbench workbench, IStructuredSelection selection) {
	// create pages for this wizard
	page1 = new GenericDiagramCreationPage(workbench, selection);
}

/**
 * @see org.eclipse.jface.wizard.IWizard#performFinish()
 */
public boolean performFinish() {
	return page1.finish();
}
}
