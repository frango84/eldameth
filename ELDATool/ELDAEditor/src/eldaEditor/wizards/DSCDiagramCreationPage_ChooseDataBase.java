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

//import org.eclipse.core.resources.IFile;
//import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;

import eldaEditor.icons.OutlineIcons;


/**
 * This WizardPage  .
 * @author samuele
 */

public class DSCDiagramCreationPage_ChooseDataBase extends WizardPage implements IWizardPage,ExtensionInterface{
	protected final IWorkbench workbench;
	
	protected IStructuredSelection selection;
	
	private Text eventText;
	private Text guardText;
	private Text actionText;
	private Text functionText;
	
	private String nameProject;
	private String nameFile;

	/**
	 * Create a new wizard page instance.
	 * @param workbench the current workbench
	 * @param selection the current object selection
	 * @see ShapesCreationWizard#init(IWorkbench, IStructuredSelection)
	 */
	DSCDiagramCreationPage_ChooseDataBase(IWorkbench workbench, IStructuredSelection selection) {
		super("dataBasePage");
		this.workbench = workbench;
		this.selection = selection;
		this.setTitle("Linking events, guards, actions and functions definition file.");

		
		setImageDescriptor(ImageDescriptor.createFromImage(OutlineIcons.IMAGE_WIZBAN));
		setDescription("You must select the file containing informations about events, guards, actions and functions\n" + "which would be used in transitions definition.\n" + "They must be into the same project.");

	}
	
	public void createControl (Composite parent)
	{
		
//		recupero il workspace
//		IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
//		final String nameProject=((IJavaProject) selection.toList().get(0)).getProject().getName();
		
		
		
			
		int [] weigths= {80,20};
		
		
		SashForm layoutForm= new SashForm(parent,SWT.VERTICAL);
		layoutForm.setLayout(new GridLayout(1,true));
		
		   // Setto lo stile per la label
	    GridData label_data = new GridData();
	    label_data.horizontalSpan = 1;
	    
		   // Setto lo stile per la label
	    GridData sash_data = new GridData();
	    sash_data.horizontalSpan = 1;
		
		//sezione riguardante gli event
	    
	    Label eventLabel =new Label(layoutForm,SWT.FILL);
		eventLabel.setText("Select the file containing events");
		eventLabel.setLayoutData(label_data);
		
		SashForm eventSash= new SashForm(layoutForm,SWT.HORIZONTAL); 
		eventSash.setLayout(new GridLayout(1,false));
		eventSash.setLayoutData(sash_data);
		
		SashForm emptySash= new SashForm(layoutForm,SWT.HORIZONTAL); 
		emptySash.setLayout(new GridLayout(2,false));
		
		eventText= new Text(eventSash,SWT.BORDER);
//		eventText.setText(nameProject + "_events.event");
		eventText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {

				validatePage();
				
			}});
		
		
		Button buttonBrowse=new Button(eventSash,SWT.PUSH);
		buttonBrowse.setText("Browse..");
		
		buttonBrowse.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {

				
						
				// TODO Auto-generated method stub
				FileDialog fileDialog=new FileDialog(new Shell(), SWT.OPEN);
				IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				fileDialog.setFilterPath(wsroot.getLocation().toOSString().concat("\\" + nameProject));
				String [] extensions={"*"+EVENT_EXTENSION}; 
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
//				if (!fileDialog.getFileName().equals(""))
//					eventText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals(""))
				{
					String relativePath=fileDialog.getFilterPath().toString();
					relativePath=relativePath.replace(wsroot.getLocation().toOSString().toString()+"\\"+nameProject, "");
					eventText.setText(relativePath +"\\"+fileDialog.getFileName());
				}

				
			}
			
		});
		
		eventSash.setWeights(weigths);
		
		//sezione riguardante le guard	
		Label guardLabel =new Label(layoutForm,SWT.None);
		guardLabel.setText("Select the file containing guards");
		
		SashForm guardSash= new SashForm(layoutForm,SWT.HORIZONTAL); 
		guardSash.setLayout(new GridLayout(2,false));
		
		
		
		guardText= new Text(guardSash,SWT.BORDER);
//		guardText.setText(nameFile + "_guards.gora");
		guardText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {

				validatePage();
				}});
		
		
		
		Button buttonBrowse2=new Button(guardSash,SWT.PUSH| SWT.BEGINNING);
		buttonBrowse2.setText("Browse..");
		
		buttonBrowse2.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
//				recupero il workspace
//				final IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
					
				
				
				FileDialog fileDialog=new FileDialog(new Shell(), SWT.OPEN);
				
				IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				fileDialog.setFilterPath(wsroot.getLocation().toOSString().concat("\\" + nameProject));
				String [] extensions={"*"+GUARD_EXTENSION}; 
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
//				if (!fileDialog.getFileName().equals(""))
//					guardText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals(""))
				{
					String relativePath=fileDialog.getFilterPath().toString();
					relativePath=relativePath.replace(wsroot.getLocation().toOSString().toString()+"\\"+nameProject, "");
					guardText.setText(relativePath +"\\"+fileDialog.getFileName());
				}

				
			}
			
		});
		
		guardSash.setWeights(weigths);
		
		SashForm emptySash2= new SashForm(layoutForm,SWT.HORIZONTAL); 
		emptySash2.setLayout(new GridLayout(2,false));
		
		//sezione riguardante le action
		
		Label actionLabel =new Label(layoutForm,SWT.None);
		actionLabel.setText("Select the file containing actions");
		
		SashForm actionSash= new SashForm(layoutForm,SWT.HORIZONTAL); 
		actionSash.setLayout(new GridLayout(2,false));
		
	
		
		
		actionText= new Text(actionSash,SWT.BORDER);
//		actionText.setText(nameFile + "_actions.gora");
		actionText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				validatePage();
				}});
		
		Button buttonBrowse3=new Button(actionSash,SWT.PUSH);
		buttonBrowse3.setText("Browse..");
		
		buttonBrowse3.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
//				recupero il workspace
//				final IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				
				//TODO gestire i casi in cui il progetto non è di tipo java
				IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				FileDialog fileDialog=new FileDialog(new Shell(), SWT.OPEN);
				fileDialog.setFilterPath(wsroot.getLocation().toOSString().concat("\\" + nameProject));
				String [] extensions={"*"+ACTION_EXTENSION}; 
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
//				if (!fileDialog.getFileName().equals(""))
//					actionText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals(""))
				{
					String relativePath=fileDialog.getFilterPath().toString();
					relativePath=relativePath.replace(wsroot.getLocation().toOSString().toString()+"\\"+nameProject, "");
					actionText.setText(relativePath +"\\"+fileDialog.getFileName());
				}
			}
			
		});
		
		actionSash.setWeights(weigths);
		
		SashForm emptySash3= new SashForm(layoutForm,SWT.HORIZONTAL); 
		emptySash3.setLayout(new GridLayout(2,false));

		//function support
		Label functionLabel =new Label(layoutForm,SWT.None);
		functionLabel.setText("Select the file containing functions");
		
		SashForm functionSash= new SashForm(layoutForm,SWT.HORIZONTAL); 
		functionSash.setLayout(new GridLayout(2,false));
		
	
		
		
		functionText= new Text(functionSash,SWT.BORDER);
//		actionText.setText(nameFile + "_actions.gora");
		functionText.addModifyListener(new ModifyListener(){

			public void modifyText(ModifyEvent e) {
				validatePage();
				}});
		
		Button buttonBrowse4=new Button(functionSash,SWT.PUSH);
		buttonBrowse4.setText("Browse..");
		
		buttonBrowse4.addSelectionListener(new SelectionListener(){

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void widgetSelected(SelectionEvent e) {
//				recupero il workspace
//				final IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				
				//TODO gestire i casi in cui il progetto non è di tipo java
				
				FileDialog fileDialog=new FileDialog(new Shell(), SWT.OPEN);
				IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
				fileDialog.setFilterPath(wsroot.getLocation().toOSString().concat("\\" + nameProject));
				String [] extensions={"*"+FUNCTION_EXTENSION}; 
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
//				if (!fileDialog.getFileName().equals(""))
//					functionText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals(""))
				{
					String relativePath=fileDialog.getFilterPath().toString();
					relativePath=relativePath.replace(wsroot.getLocation().toOSString().toString()+"\\"+nameProject, "");
					functionText.setText(relativePath +"\\"+fileDialog.getFileName());
				}
				
			}
			
		});
		
		functionSash.setWeights(weigths);

		
		SashForm emptySash4= new SashForm(layoutForm,SWT.HORIZONTAL); 
		emptySash4.setLayout(new GridLayout(2,false));
		
		int [] weigthsVert={5,4,10,5,4,10,5,4,10,5,4,10};
		layoutForm.setWeights(weigthsVert);
		
		this.setControl(layoutForm);
	
}

	public void  validatePage()
	{
		if (!(eventText.getText().endsWith(EVENT_EXTENSION)))
		{
		setErrorMessage("The file must be finish with "+EVENT_EXTENSION+" extension");	
		setPageComplete(false);
		return ;
		}
		if (!(guardText.getText().endsWith(GUARD_EXTENSION)))
			{
			setErrorMessage("The file must be finish with "+GUARD_EXTENSION +" extension");
			setPageComplete(false);
			return;		
			}
		if( (!(actionText.getText().endsWith(ACTION_EXTENSION))))
		{
			setErrorMessage("The file must be finish with "+ACTION_EXTENSION+" extension");
			setPageComplete(false);
			return;		
		}
		if( (!(functionText.getText().endsWith(FUNCTION_EXTENSION))))
		{
			setErrorMessage("The file must be finish with "+FUNCTION_EXTENSION+" extension");
			setPageComplete(false);
			return;		
		}

		setErrorMessage(null);
		setPageComplete(true);
		
	}
	
	public String eventFile()
	{
		return eventText.getText();
	}
	
	public String guardFile()
	{
		return guardText.getText();
	}
	
	public String actionFile()
	{
		return actionText.getText();
	}

	public String functionFile()
	{
		return functionText.getText();
	}



	public void refreshField() {
		String completeNameFile=((DSCDiagramCreationPage_ChooseName) getWizard().getPage("ChoosePage")).getFileName();
		int end=completeNameFile.lastIndexOf("dsc");
		this.nameFile=completeNameFile.substring(0, end-1);
		String pathSelected=((DSCDiagramCreationPage_ChooseName) getWizard().getPage("ChoosePage")).getContainerFullPath().removeFirstSegments(1).toOSString();
		this.nameProject=((DSCDiagramCreationPage_ChooseName) getWizard().getPage("ChoosePage")).getContainerFullPath().segment(0);
		
		eventText.setText(nameProject + "_events" + EVENT_EXTENSION);
		guardText.setText(pathSelected+"\\"+nameFile + "_guards"+ GUARD_EXTENSION);
		actionText.setText(pathSelected+"\\"+nameFile + "_actions" + ACTION_EXTENSION);
		functionText.setText(pathSelected+"\\"+nameFile + "_functions" + FUNCTION_EXTENSION);
		
		
	}

	
}
