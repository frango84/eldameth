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

package eldaEditor.wizards;

/**
 * @author samuele
 *
 */

//import genericUMLDiagramEditor.editor.GenericDiagramCreationPage;
//import genericUMLDiagramEditor.editor.GenericDiagramCreationWizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Create new new .shape-file. 
 * Those files can be used with the ShapesEditor (see plugin.xml).
 * @author samuele
 */
public class DSCDiagramCreationWizard extends Wizard implements INewWizard {
	
	protected DSCDiagramCreationPage_Fipa page1;
	protected DSCDiagramCreationPage_ChooseName page2;
	protected DSCDiagramCreationPage_ChooseDataBase page3;
	

	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// create pages for this wizard
		
		//pagina che mostra il template fipa
		page1 = new DSCDiagramCreationPage_Fipa(workbench, selection);
		
		//pagina che permette di scegliere nome e posizione del file da creare
		page2 = new DSCDiagramCreationPage_ChooseName(workbench, selection);

//		pagina che permette di scegliere nome e posizione del file da creare
		page3 = new DSCDiagramCreationPage_ChooseDataBase(workbench, selection);
		setWindowTitle("DSC Creation Wizard");

		
	}
	
	public void addPages()
	{
		addPage(page1);
		addPage(page2);
		addPage(page3);
		
	}
	
	public boolean performFinish() {
		page2.setFile(page3.eventFile(),page3.guardFile(), page3.actionFile(),page3.functionFile());
		return page2.finish();
		
	}

	public boolean canFinish()
	{
		return (this.getContainer().getCurrentPage().equals(page2)||
				(this.getContainer().getCurrentPage().equals(page3)&& 
						page3.isPageComplete()));
	}
	
	public IWizardPage getNextPage(IWizardPage page)
	{
		IWizardPage nextPage=super.getNextPage(page);
		if (nextPage instanceof DSCDiagramCreationPage_ChooseDataBase)
			((DSCDiagramCreationPage_ChooseDataBase) nextPage).refreshField();
		return nextPage;
	}
	
}
