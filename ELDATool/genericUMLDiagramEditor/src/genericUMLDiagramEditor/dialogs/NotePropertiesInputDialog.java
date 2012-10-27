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
import genericUMLDiagramEditor.commands.ChangeCommentNoteCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramModel.Note;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;

import org.eclipse.jface.dialogs.MessageDialog;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.ui.PlatformUI;

/**
 * It allows users to input the properties for a Note
 * 
 *  @author marguu&zaza
 */
public class NotePropertiesInputDialog extends Dialog {
  
  private String input1; 
  private String input2;
  private Note note;
  
  private boolean cancelled = true;
  protected boolean intelligentResizing=false;
  
  private Button ok;
  private Text text;
	

  /**
   * Constructor
   * 
   * @param parent the parent
   */
  public NotePropertiesInputDialog(Shell parent, int style, Note note) {
    // Let users override the default styles
    super(parent, style);
    setText("Note Properties");
    this.note=note;
    this.setInput1(note.getComment());
    this.setInput2(note.getDescription());
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
    	  if(text.getText().trim().length()==0)
    		  ok.setEnabled(false);
    	  else
    		  ok.setEnabled(true);
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
    
    // Setto lo stile per la label
    GridData label_data = new GridData(GridData.FILL_HORIZONTAL);
    label_data.horizontalSpan = 2;
    // Setto lo stile per la casella di testo
    GridData text_data = new GridData(GridData.FILL_BOTH);
    text_data.horizontalSpan = 2;
    text_data.verticalSpan = 2;

    // Show the message for body    
    Label label1 = new Label(shell, SWT.NONE);
    label1.setText("Boby: ");
    label1.setLayoutData(label_data);
    //  Display the input box
    text = new Text(shell, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
    if(note.getComment()!=null)
    	text.setText(note.getComment());
    text.setLayoutData(text_data);
    
    // Show the message for description
    Label label2 = new Label(shell, SWT.NONE);
    label2.setText("Description:");
    label2.setLayoutData(label_data);
    //  Display the input box
    final Text text2 = new Text(shell, SWT.V_SCROLL|SWT.H_SCROLL|SWT.BORDER);
    if(note.getDescription()!=null)
    	text2.setText(note.getDescription());
    text2.setLayoutData(text_data);

    final Button check = new Button(shell, SWT.CHECK);
    check.setText("Automatic Resize on Text");
    check.setToolTipText("Check this button to set automatic resize to fit element size\n" +
    					"on text one.");
    check.setSelection(intelligentResizing);
    check.setLayoutData(label_data);
    check.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	  intelligentResizing=check.getSelection();
      }
    });

    // Create the OK button and add a handler
    // so that pressing it will set input
    // to the entered value
    ok = new Button(shell, SWT.PUSH);
    ok.setText("OK");
    text_data = new GridData(GridData.FILL_HORIZONTAL);
    ok.setLayoutData(text_data);
    ok.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
    	String input1_temp = text.getText().trim();
    	String input2_temp = text2.getText();

    	shell.close();

    	if(!input1_temp.equals(input1) || !input2_temp.equals(input2) || intelligentResizing ) {
    		
    		ChangeCommentNoteCommand nameCmd = new ChangeCommentNoteCommand (note, input1_temp);//,editPart,figure,intelligentResizing);
    		ChangeDescriptionCommand descrCmd = new ChangeDescriptionCommand(note, input2_temp);
    					
    		CompoundCommand command = new CompoundCommand();
    		command.add(nameCmd);
          	command.add(descrCmd);
          	if (intelligentResizing){
          	Dimension dimMin= getDimension(input1_temp);
          	
          	if(dimMin.width > note.getSizeDefault().width  && dimMin.height< note.getSizeDefault().height)
          		note.setSize(new Dimension (dimMin.width,note.getSizeDefault().height));
          	else if (dimMin.width < note.getSizeDefault().width  && dimMin.height> note.getSizeDefault().height)
          		note.setSize(new Dimension (note.getSizeDefault().width,dimMin.height));
          	else if (dimMin.width > note.getSizeDefault().width  && dimMin.height> note.getSizeDefault().height)
          		note.setSize(dimMin);
          	else
          		note.setSize(note.getSizeDefault());
          	}
          	
			if (command != null && ((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class) != null) {
				CommandStack cs=(CommandStack)((GenericDiagramEditor)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()).getAdapter(CommandStack.class);
			    cs.execute(command);
			}
			
	    	// Verifico se la nota non è vuota
	        if(input1_temp.trim().length()>0) {
	        	cancelled = false;
	            input1 = input1_temp;
	            input2 = input2_temp;
	        }
	        else
	        	MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"WARNING MESSAGE","Note can't be empty! Your operation has been cancelled.");
    	}
      }
    });
    
    text.addModifyListener(new ModifyListener() { 
        public void modifyText(ModifyEvent e){ 
        	 }
                }
        	);

    // Create the cancel button and add a handler
    // so that pressing it will set input to null
    Button cancel = new Button(shell, SWT.PUSH);
    cancel.setText("Cancel");
//    data = new GridData(GridData.FILL_HORIZONTAL);
    cancel.setLayoutData(text_data);
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
  
  public boolean getCancelled() {
	   return cancelled;
 }
  
  protected Dimension textSize(String text, Font f) {
		return FigureUtilities.getTextExtents(text,f);
	}
  private Dimension getDimension(String imput1_tmp)
  {
	  Dimension dim;
	// Creo il font della figura
  	FontData fd =new FontData("Arial",10,SWT.NONE);
  	Font font = new Font(null,fd);
  	dim=textSize(imput1_tmp,font);
  	dim.expand(0,10);
  	return dim;
	  
  }
} 
