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

package genericUMLDiagramEditor.dialogs;

import genericUMLDiagramEditor.commands.ChangeDescriptionCommand;
import genericUMLDiagramEditor.commands.ChangeNameCommand;
import genericUMLDiagramEditor.commands.ChangeStereotypeCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramModel.Classifier;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.PlatformUI;

/**
 * It allows users to input the properties for a Classifier
 * 
 *  @author marguu&zaza
 */
public class DiagramVisualElementPropertiesInputDialog extends Dialog {
  
  protected String input1; 
  protected String input2;
  protected String input3;
  protected Classifier task;

  protected boolean cancelled = true;
  
  /**
   * Constructor
   * 
   * @param parent the parent
   */
  public DiagramVisualElementPropertiesInputDialog(Shell parent) {
    // Pass the default styles here
    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
  }

  /**
   * Constructor
   * 
   * @param parent the parent
   * @param style  the style
   */
  public DiagramVisualElementPropertiesInputDialog(Shell parent, int style) {
    // Let users override the default styles
    super(parent, style);
    setText("Input Dialog");
  }

  /**
   * Constructor
   * 
   * @param parent the parent
   * @param style  the style
   * @param task   the classifier
   */
  public DiagramVisualElementPropertiesInputDialog(Shell parent, int style, Classifier task) {
    // Let users override the default styles
    super(parent, style);
    setText("Generic Element Properties");
    this.task=task;
    this.setInput1(task.getStereotype());
    this.setInput2(task.getName());
    this.setInput3(task.getDescription());
  }
  
  /**
   * Constructor
   * 
   * @param parent the parent
   * @param task   the classifier
   */
  public DiagramVisualElementPropertiesInputDialog(Shell parent, Classifier task) {
    // Let users override the default styles
    super(parent);
    setText("Generic Element Properties");
    this.task=task;
    this.setInput1(task.getStereotype());
    this.setInput2(task.getName());
    this.setInput3(task.getDescription());
  }

  /**
   * Sets the input
   * 
   * @param input the new input
   */
  public void setInput1(String input) {
    this.input1 = input;
  }
  public void setInput2(String input) {
    this.input2 = input;
  }
  public void setInput3(String input) {
    this.input3 = input;
  }
  
  /**
   * Opens the dialog and returns the input
   * 
   * Return String
   */
  public String open() {
    // Create the dialog window
    Shell shell = new Shell(getParent(), getStyle());
    shell.setText(getText());
    createContents(shell);
    shell.setSize(400,370);
    shell.setLocation(200,200);
    shell.open();
    Display display = getParent().getDisplay();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    // Return the entered value, or null
    return input1;
  }

  /**
   * Creates the dialog's contents
   * 
   * @param shell the dialog window
   */
  private void createContents(final Shell shell) {
    shell.setLayout(new GridLayout(2, true));

    // Show the message
    Label title1 = new Label(shell, SWT.NONE);
    title1.setText("Properties of the generic element");
    GridData dat = new GridData();
    dat.horizontalSpan = 2;
    title1.setLayoutData(dat);
   
    Label label1 = new Label(shell, SWT.NONE);
    label1.setText("Stereotype:");
   
    GridData data = new GridData();
    data.horizontalSpan = 2;
    label1.setLayoutData(data);

    //  Display the input box
    final Text text = new Text(shell, SWT.BORDER);
    data = new GridData(GridData.FILL_HORIZONTAL);
    if(task.getStereotype()!=null)
      text.setText(task.getStereotype());
    data.horizontalSpan = 2;
    text.setLayoutData(data);
    
    Label label2 = new Label(shell, SWT.NONE);
    label2.setText("Name:");
   
    GridData data2 = new GridData();
    data2.horizontalSpan = 2;
    label2.setLayoutData(data2);

    //  Display the input box
    final Text text2 = new Text(shell, SWT.BORDER);
    data2 = new GridData(GridData.FILL_HORIZONTAL);
    if(task.getName()!=null)
      text2.setText(task.getName());
    data2.horizontalSpan = 2;
    text2.setLayoutData(data2);
    
    Label label3 = new Label(shell, SWT.NONE);
    label3.setText("Description:");
   
    GridData data3 = new GridData();
    data3.horizontalSpan = 2;
    label3.setLayoutData(data3);

    //  Display the input box
    final  Text text3 = new Text(shell, SWT.H_SCROLL|SWT.V_SCROLL|SWT.BORDER);
    
    data3 = new GridData(GridData.FILL_BOTH);
    if(task.getDescription()!=null)
    	text3.setText(task.getDescription());
    data3.horizontalSpan = 2;
    data3.verticalSpan= 2;
    text3.setLayoutData(data3);
  
    // Create the OK button and add a handler
    // so that pressing it will set input
    // to the entered value
    Button ok = new Button(shell, SWT.PUSH);
    ok.setText("OK");
    data = new GridData(GridData.FILL_HORIZONTAL);
    ok.setLayoutData(data);
    ok.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        input1 = text.getText();
        input2 = text2.getText();
        input3= text3.getText();

        cancelled = false;
        shell.close();
        
        if(!input1.equals(task.getStereotype()) || !input2.equals(task.getName()) || !input3.equals(task.getDescription())) {
        	
        	CompoundCommand command = new CompoundCommand();
        	ChangeStereotypeCommand stereoCmd = new ChangeStereotypeCommand(task, input1);
        	ChangeNameCommand nameCmd = new ChangeNameCommand(task, input2);
        	ChangeDescriptionCommand descrCmd = new ChangeDescriptionCommand(task, input3);
        	command.add(stereoCmd);
        	command.add(nameCmd);
        	command.add(descrCmd);

			if (command != null && ((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class) != null) {
				CommandStack cs=(CommandStack)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class);
			    cs.execute(command);
			}
        }
      }
    });

    // Create the cancel button and add a handler
    // so that pressing it will set input to null
    Button cancel = new Button(shell, SWT.PUSH);
    cancel.setText("Cancel");
    data = new GridData(GridData.FILL_HORIZONTAL);
    cancel.setLayoutData(data);
    cancel.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
   	    cancelled=true;
        shell.close();
      }
    });
    
    // Set the OK button as the default, so
    // user can type input and press Enter
    // to dismiss
    shell.setDefaultButton(ok);
    
    // Setto il cursore lampeggiante 
    text2.setFocus();
  }
  
  /**
   * Return the value of cancelled
   */
  public boolean getCancelled() {
	   return cancelled;
}
}
