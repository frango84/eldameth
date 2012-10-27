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

//import genericUMLDiagramModel.GenericDiagram;
//import org.eclipse.swt.graphics.Image;

//import genericUMLDiagramEditor.editor.GenericDiagramCreationPage;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import eldaEditor.icons.OutlineIcons;

/**
 * This WizardPage  .
 * @author samuele
 */
public class DSCDiagramCreationPage_Fipa extends WizardPage implements IWizardPage{
	protected final IWorkbench workbench;
	protected IStructuredSelection selection;
	
	/**
	 * Create a new wizard page instance.
	 * @param workbench the current workbench
	 * @param selection the current object selection
	 * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
	 */
	DSCDiagramCreationPage_Fipa(IWorkbench workbench, IStructuredSelection selection) {
//		super("fipaPage", selection);
		super("fipaPage");
		this.workbench = workbench;
		this.selection = selection;
		
		this.setTitle("Create a new ADSC diagram");
		setImageDescriptor(ImageDescriptor.createFromImage(OutlineIcons.IMAGE_WIZBAN));
		this.setDescription("You must only model the Active Distilled Statechart into FIPA template \n");
		
	}
	
	public void createControl (Composite parent)
	{
//		super.createControl(parent);
		Button button=new Button (parent,SWT.PUSH| SWT.FILL);
		button.setImage(OutlineIcons.IMAGE_FIPA);
		setControl(button);	
	}
	
	public boolean canFlipToNextPage()
	{
		return true;
	}

}
