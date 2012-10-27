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

import genericUMLDiagramEditor.commands.NullCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramModel.TextLabel;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.PlatformUI;

/**
 * It allows users to input the properties for a TextLabel
 * 
 *  @author marguu&zaza
 */
public class TextLabelPropertiesInputDialog extends Dialog {
  
  private String input1; 
  private TextLabel textlabel;
  
  // Informa se l'utente ha premuto Cancel
  private boolean cancelled = true;

  /**
   * Constructor
   * 
   * @param parent the parent
   */
  public TextLabelPropertiesInputDialog(Shell parent) {
    // Pass the default styles here
    this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
  }

  /**
   * Constructor
   * 
   * @param parent the parent
   * @param style  the style
   */
  public TextLabelPropertiesInputDialog(Shell parent, int style) {
    // Let users override the default styles
    super(parent, style);
    setText("Text Label");
  }

  /**
   * Constructor
   * 
   * @param parent 		the parent
   * @param style 		the style
   * @param textlabel 	the label
   */
  public TextLabelPropertiesInputDialog(Shell parent, int style, TextLabel textlabel) {
    // Let users override the default styles
    super(parent, style);
    setText("Text Label");
    this.textlabel=textlabel;
    this.setInput1(textlabel.getText());
  }

  /**
   * Sets the input
   * 
   * @param input the new input
   */
  public void setInput1(String input) {
    this.input1 = input;
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

    //  Display the input box for the body
    final  Text text = new Text(shell, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
    GridData data = new GridData(GridData.FILL_BOTH);
    if(textlabel.getText()!=null)
    	text.setText(textlabel.getText());
    data.horizontalSpan = 2;
    data.verticalSpan= 2;
    text.setLayoutData(data);

    // Create the OK button and add a handler
    // so that pressing it will set input
    // to the entered value
    Button ok = new Button(shell, SWT.PUSH);
    ok.setText("OK");
    data = new GridData(GridData.FILL_HORIZONTAL);
    ok.setLayoutData(data);
    ok.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	String input1_temp = text.getText().trim();
    	
        // Controllo se nel testo della label sono presenti lettere accentate
        input1 = input1.replaceAll("à","a'");
        input1 = input1.replaceAll("è","e'");
        input1 = input1.replaceAll("ì","i'");
        input1 = input1.replaceAll("ò","o'");
        input1 = input1.replaceAll("ù","u'");

    	shell.close();
    	
    	if(!input1_temp.equals(input1)) {
			Command command=new NullCommand();
			if (command != null && ((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class) != null) {
				CommandStack cs=(CommandStack)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class);
			    cs.execute(command);
			}
    	}
	    	
	    	// Verifico se la label non è vuota
	        if(input1_temp.trim().length()>0) {
	        	cancelled = false;
	        	input1 = input1_temp;
	        }
	        else
	        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Label can't be empty! Your operation has been cancelled.");
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
  }
  
  /**
   * Return the value of cancelled
   */
  public boolean getCancelled() {
	   return cancelled;
  }
}
