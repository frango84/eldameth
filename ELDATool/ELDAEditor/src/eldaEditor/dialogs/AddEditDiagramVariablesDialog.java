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

package eldaEditor.dialogs;

/**
 * @author samuele
 *
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dscDiagramModel.myDataTypes.TypeDiagramVariable;

/**
 * This class demonstrates how to create your own dialog classes. It allows
 * users to input a String
 */
public class AddEditDiagramVariablesDialog extends Dialog {

	// I valori delle proprietà della variabile
	private String superClassStr = "";

	// Un'istanza della transizione
	private TypeDiagramVariable variable;

	// Alcuni elementi del dialog
	Text text0 = null;
	Text text1 = null;
	Button ok;

	// Informazioni sulle scelete dell'utente
	private boolean cancelled = true; // ha premuto Cancel

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 */
	public AddEditDiagramVariablesDialog(Shell parent) {
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public AddEditDiagramVariablesDialog(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
		setText("Add Variable");
	}

	public AddEditDiagramVariablesDialog(Shell parent, int style,
			TypeDiagramVariable variable) {
		// Let users override the default styles
		super(parent, style);
		this.variable = variable;
		setText("Edit Variable");
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public TypeDiagramVariable open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(300, 230);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				if (text0.getText().trim().equals("")
						|| text1.getText().trim().equals(""))
					ok.setEnabled(false);
				else
					ok.setEnabled(true);
				display.sleep();
			}
		}
		// Return the entered value, or null
		return variable;
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell
	 *            the dialog window
	 */
	private void createContents(final Shell shell) {

		shell.setLayout(new GridLayout(1, true));

		// Setto lo stile per la label
		GridData label_data = new GridData(GridData.FILL_HORIZONTAL);

		// Setto lo stile per la casella di testo
		GridData text_data = new GridData(GridData.FILL_HORIZONTAL);

		SashForm general_form = new SashForm(shell, SWT.VERTICAL);
		general_form.setLayout(new GridLayout(2, true));
		general_form.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Show the message for stereotype
		Label label0 = new Label(general_form, SWT.NONE);
		label0.setText("Type:");
		// label0.setLayoutData(label_data);

		SashForm typeForm = new SashForm(general_form, SWT.HORIZONTAL);
		typeForm.setLayout(new GridLayout(2, false));

		// Display the input box
		text0 = new Text(typeForm, SWT.BORDER);
		text0.setLayoutData(text_data);
		// se il dialog è stato aperto in modalità edit
		if (variable != null)
			text0.setText(variable.getType());

		Button browse = new Button(typeForm, SWT.PUSH);
		browse.setText("Browse..");
		browse.setLayoutData(new GridData(1));
		browse.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				ChooseTypeDialog chooseType = new ChooseTypeDialog(shell);
				superClassStr = chooseType.open(shell);
				if (superClassStr != null)
					text0.setText(superClassStr);
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		int[] pesiType = { 80, 20 };
		typeForm.setWeights(pesiType);

		// Show the message for name
		Label label1 = new Label(general_form, SWT.NONE);
		label1.setText("Variable Name:");
		label1.setLayoutData(label_data);
		// Display the input box
		text1 = new Text(general_form, SWT.BORDER);
		text1.setLayoutData(text_data);
		// se il dialog è stato aperto in modalità edit
		if (variable != null)
			text1.setText(variable.getName());

		// Show the message for description
		Label label2 = new Label(general_form, SWT.NONE);
		label2.setText("Value:");
		GridData data2 = new GridData();
		data2.horizontalSpan = 2;
		label2.setLayoutData(label_data);
		// Display the input box
		final Text text2 = new Text(general_form, SWT.BORDER);
		text2.setLayoutData(text_data);
		// se il dialog è stato aperto in modalità edit
		if (variable != null)
			text2.setText(variable.getValue());

		SashForm checkForm = new SashForm(general_form, SWT.HORIZONTAL);
		checkForm.setLayout(new GridLayout(2, false));

		final Button checkButton = new Button(checkForm, SWT.CHECK);
		checkButton.setLayoutData(label_data);
		checkButton.setText("Present into the constructor");

		if (variable != null)
			checkButton.setSelection(variable.isBePresentIntoConstructor());

		checkButton.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
			}
		});

		SashForm buttonForm = new SashForm(general_form, SWT.HORIZONTAL);
		buttonForm.setLayout(new GridLayout(2, true));

		int[] weights = { 15, 25, 15, 25, 15, 25, 30, 25 };
		general_form.setWeights(weights);

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		ok = new Button(buttonForm, SWT.PUSH);
		ok.setText("OK");
		label_data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(label_data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				variable = new TypeDiagramVariable();
				variable.setType(text0.getText());
				variable.setName(text1.getText());
				variable.setValue(text2.getText());
				variable
						.setBePresentIntoConstructor(checkButton.getSelection());
				shell.close();
				cancelled = false;
			}
		});

		text0.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			}
		});

		text1.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			}
		});
		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(buttonForm, SWT.PUSH);
		cancel.setText("Cancel");

		cancel.setLayoutData(label_data);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
				cancelled = true;
			}
		});

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		shell.setDefaultButton(ok);

		// Setto il cursore lampeggiante
		text1.setFocus();
	}

	public boolean getCancelled() {
		return cancelled;
	}

}
