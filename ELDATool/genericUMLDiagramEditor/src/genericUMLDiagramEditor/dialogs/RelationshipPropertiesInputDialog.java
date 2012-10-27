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
import genericUMLDiagramEditor.commands.ChangeShowLabelCommand;
import genericUMLDiagramEditor.commands.ChangeStereotypeCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramModel.Relationship;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.PlatformUI;

/**
 * It allows users to input the properties for a Relationship
 * 
 *  @author marguu&zaza
 */
public class RelationshipPropertiesInputDialog extends Dialog {
  
  private String input1; 
  private String input2;
  private String input3;
  private boolean show_label=false;
  private Relationship connection;
  
  // Informa se l'utente ha premuto Cancel
  private boolean cancelled = true;

  /**
   * Constructor
   * 
   * @param parent the parent
   */
  public RelationshipPropertiesInputDialog(Shell parent, int style, Relationship connection) {
    // Let users override the default styles
    super(parent, style);
    setText("Relationship Properties");
    this.connection=connection;
    this.show_label = connection.getConnectionLabel().isIsVisible();
    this.setInput1(connection.getName());
    this.setInput2(connection.getDescription());
    this.setInput3(connection.getStereotype());
  }
  
  /**
   * Constructor
   * 
   * @param parent 				the parent
   * @param style  				the style
   * @param parent 				the parent
   * @param connection 			the connection
   * @param useConnectionLabel 	true if the connectionLabel is visible
   */
  public RelationshipPropertiesInputDialog(Shell parent, int style, Relationship connection,boolean useConnectionLabel) {
    // Let users override the default styles
    super(parent, style);
    setText("Relationship Properties");
    this.connection=connection;
    this.setInput1(connection.getName());
    this.setInput2(connection.getDescription());
    this.setInput3(connection.getStereotype());
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
    shell.setSize(400,400);
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
    title1.setText("Properties of the relationship");
    GridData dat = new GridData();
    dat.horizontalSpan = 2;
    title1.setLayoutData(dat);
    
    Label label3 = new Label(shell, SWT.NONE);
    label3.setText("Stereotype:");
   
    GridData data3 = new GridData();
    data3.horizontalSpan = 2;
    label3.setLayoutData(data3);
   
    //  Display the input box
    final Text text3 = new Text(shell, SWT.BORDER);
    data3 = new GridData(GridData.FILL_HORIZONTAL);
    if(connection.getStereotype()!=null)
      text3.setText(connection.getStereotype());
    data3.horizontalSpan = 2;
    text3.setLayoutData(data3);
    
    Label label1 = new Label(shell, SWT.NONE);
    label1.setText("Name:");
    GridData data = new GridData();
    data.horizontalSpan = 2;
    label1.setLayoutData(data);

    //  Display the input box
    final Text text = new Text(shell, SWT.BORDER);
    data = new GridData(GridData.FILL_HORIZONTAL);
    if(connection.getName()!=null)
      text.setText(connection.getName());
    data.horizontalSpan = 2;
    text.setLayoutData(data);
    
    Label label2 = new Label(shell, SWT.NONE);
    label2.setText("Description: ");
    GridData data2 = new GridData();
    data2.horizontalSpan = 2;
    label2.setLayoutData(data2);   

    //  Display the input box
    final  Text text2 = new Text(shell, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
    
    data2 = new GridData(GridData.FILL_BOTH);
    if(connection.getDescription()!=null)
    	text2.setText(connection.getDescription());
    data2.horizontalSpan = 2;
    data2.verticalSpan= 2;
    text2.setLayoutData(data2);
    
    // Create the Show Info check-button and add a handler
    // so that pressing it will set input to null
    final Button check = new Button(shell, SWT.CHECK);
    check.setText("Show Label");
    check.setToolTipText("Check this button to show all the inserted \n informations on the diagram; otherwise \n don't check this button.");
    check.setSelection(show_label);
    check.setLayoutData(data);
    check.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	  show_label=check.getSelection();
      }
    });

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

        shell.close();
        if(!input3.equals(connection.getStereotype()) || !input1.equals(connection.getName()) ||
        	!input2.equals(connection.getDescription()) || show_label!=connection.getConnectionLabel().isIsVisible()) {

        	CompoundCommand command = new CompoundCommand();
        	ChangeStereotypeCommand stereoCmd = new ChangeStereotypeCommand(connection, input3);
        	ChangeNameCommand nameCmd = new ChangeNameCommand(connection, input1);
        	ChangeDescriptionCommand descrCmd = new ChangeDescriptionCommand(connection, input2);
        	ChangeShowLabelCommand labelCmd = new ChangeShowLabelCommand(connection, show_label);
        	command.add(stereoCmd);
        	command.add(nameCmd);
        	command.add(descrCmd);
        	command.add(labelCmd);

        	if (command != null && ((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class) != null) {
				CommandStack cs=(CommandStack)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class);
			    cs.execute(command);
			}
        }

		cancelled=false;
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
        shell.close();
        cancelled=true;
      }
    });

    // Set the OK button as the default, so
    // user can type input and press Enter
    // to dismiss
    shell.setDefaultButton(ok);
    
    // Setto il cursore lampeggiante 
    text.setFocus();
  }
  
  /**
   * Return the value of cancelled
   */
  public boolean getCancelled() {
	   return cancelled;
 }
}
