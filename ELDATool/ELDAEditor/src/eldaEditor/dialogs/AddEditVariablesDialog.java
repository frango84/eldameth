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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import dscDiagramModel.myDataTypes.TypeVariable;

/**
 * This class demonstrates how to create your own dialog classes. It allows
 * users to input a String
 */
public class AddEditVariablesDialog extends Dialog {

	// I valori delle proprietà della variabile
	private String superClassStr = "";
	private String nameStr = "";
	private String valueStr = "";

	// Un'istanza della transizione
	private TypeVariable variable;

	// Alcuni elementi del dialog
	Text text0 = null;
	Text text1 = null;
	Text text2 = null;
	Button ok;

	// Informazioni sulle scelete dell'utente
	private boolean cancelled = true; // ha premuto Cancel
	private boolean calledFromEvent = false;

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 */
	public AddEditVariablesDialog(Shell parent) {
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public AddEditVariablesDialog(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
		setText("Add Variable");
	}

	public AddEditVariablesDialog(Shell parent, int style, TypeVariable variable) {
		// Let users override the default styles
		super(parent, style);
		this.variable = variable;
		setText("Edit Variable");
	}

	public AddEditVariablesDialog(Shell parent, int style,
			TypeVariable variable, boolean calledFromEvent) {
		// Let users override the default styles
		super(parent, style);
		this.variable = variable;
		setText("Edit Variable");
		this.calledFromEvent = calledFromEvent;
	}

	public AddEditVariablesDialog(Shell parent, int style,
			boolean calledFromEvent) {
		// Let users override the default styles
		super(parent, style);
		setText("Add Parameters");
		this.calledFromEvent = calledFromEvent;
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public TypeVariable open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		if (calledFromEvent) {
			shell.setSize(300, 180);
		} else {
			shell.setSize(300, 270);
		}

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
		GridData label_data = new GridData();
		label_data.horizontalSpan = 2;

		// Setto lo stile per la casella di testo
		GridData text_data = new GridData(GridData.FILL_HORIZONTAL);
		text_data.horizontalSpan = 2;

		SashForm general_form = new SashForm(shell, SWT.VERTICAL);
		general_form.setLayout(new GridLayout(1, true));
		general_form.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Show the message for stereotype
		Label label0 = new Label(general_form, SWT.NONE);
		if (calledFromEvent) {
			label0.setText("Parameter Type:");
		} else {
			label0.setText("Variable Type:");
		}

		label0.setLayoutData(label_data);

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
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ChooseTypeDialog chooseType = new ChooseTypeDialog(shell);
				superClassStr = chooseType.open(shell);
				if (superClassStr != null)
					text0.setText(superClassStr);
			}
		});

		int[] pesiType = { 80, 20 };
		typeForm.setWeights(pesiType);

		// Show the message for name
		Label label1 = new Label(general_form, SWT.NONE);
		if (calledFromEvent) {
			label1.setText("Parameter Name:");
		} else {
			label1.setText("Variable Name:");
		}

		label1.setLayoutData(label_data);
		// Display the input box
		text1 = new Text(general_form, SWT.BORDER);
		text1.setLayoutData(text_data);
		// se il dialog è stato aperto in modalità edit
		if (variable != null)
			text1.setText(variable.getName());

		if (!calledFromEvent) {
			// Show the message for description
			Label label2 = new Label(general_form, SWT.NONE);
			label2.setText("Value:");
			GridData data2 = new GridData();
			data2.horizontalSpan = 2;
			label2.setLayoutData(label_data);
			// Display the input box
			text2 = new Text(general_form, SWT.BORDER);
			text2.setLayoutData(text_data);
			// se il dialog è stato aperto in modalità edit
			if (variable != null)
				text2.setText(variable.getValue());
		}

		SashForm buttonForm = new SashForm(general_form, SWT.HORIZONTAL);
		buttonForm.setLayout(new GridLayout(2, true));

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		ok = new Button(buttonForm, SWT.PUSH);
		ok.setText("OK");
		label_data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(label_data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				variable = new TypeVariable();
				variable.setType(text0.getText());
				variable.setName(text1.getText());
				if (text2 != null)
					variable.setValue(text2.getText());
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
		label_data = new GridData(GridData.FILL_HORIZONTAL);
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
		// text1.setFocus();
	}

	public boolean getCancelled() {
		return cancelled;
	}

	/**
	 * Sets the input
	 * 
	 * @param input
	 *            the new input
	 */

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public void setSuperClassStr(String superClassStr) {
		this.superClassStr = superClassStr;
	}

	/**
	 * Gets the input
	 * 
	 * @return String
	 */

	public String getSuperClassStr() {
		return superClassStr;
	}

	public String getValueStr() {
		return valueStr;
	}

	public String getNameStr() {
		return nameStr;
	}

}
