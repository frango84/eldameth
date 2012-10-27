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

package eldaEditor.dialogs.gora;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import actionGuardModel.ActionGuardModelFactory;
import actionGuardModel.ActionGuardModelPackage;
import actionGuardModel.AnElement;
import actionGuardModel.AnElementList;
import dscDiagramModel.DSCDiagram;
import eldaEditor.dialogs.function.InsertFunctionDialog;
import eldaEditor.util.OpenActionEditor;
import function.FunctionFactory;
import function.FunctionList;
import function.FunctionPackage;

/**
 * @author samuele, F. Rango
 */
public class AddEditActionGuardDialog extends Dialog {

	// I valori delle proprietà della variabile
	private String typeStr = "";

	// Alcuni elementi del dialog

	/**
	 * name
	 */
	Text text0 = null;

	/**
	 * comment
	 */
	Text textAreaComment = null;

	/**
	 * body
	 */
	Text textAreaBody = null;

	Button ok;

	private Resource goraResource;

	private IFile funcIFile;

	// Informazioni sulle scelte dell'utente

	private boolean cancelled = true;
	private boolean editMode = false;

	private boolean isActionDialog = false;

	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	final ActionGuardModelPackage goraPackage = ActionGuardModelPackage.eINSTANCE;
	final ActionGuardModelFactory goraFactory = goraPackage
			.getActionGuardModelFactory();

	final FunctionPackage functionPackage = FunctionPackage.eINSTANCE;
	final FunctionFactory functionFactory = functionPackage
			.getFunctionFactory();

	private AnElementList anElementList = goraFactory.createAnElementList();
	private FunctionList functionList = functionFactory.createFunctionList();

	private AnElement elementEdited = null;

	private DSCDiagram parent;

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public AddEditActionGuardDialog(Shell parent, int style,
			AnElementList goraList, IFile funcIFile, boolean isActionDialog,
			DSCDiagram dsc) {
		// Let users override the default styles
		super(parent, style);
		this.funcIFile = funcIFile;
		this.parent = dsc;
		this.anElementList = goraList;
		this.isActionDialog = isActionDialog;
		if (isActionDialog)
			setText("Add Action");
		else
			setText("Add Guard Condition");
	}

	public AddEditActionGuardDialog(Shell parent, int style,
			AnElement elementEdited, AnElementList goraList, IFile funcIFile,
			boolean isActionDialog, DSCDiagram dsc) {
		// Let users override the default styles
		super(parent, style);
		this.funcIFile = funcIFile;
		this.parent = dsc;

		this.anElementList = goraList;
		this.isActionDialog = isActionDialog;
		if (isActionDialog)
			setText("Edit Action");
		else
			setText("Edit Guard Condition");
		this.editMode = true;
		this.elementEdited = elementEdited;
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return AnElement
	 */
	public AnElement open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(400, 300);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				String nameDialog = getText();
				shell.setText(text0.getText() + ": " + nameDialog);
				if (text0.getText().trim().equals(""))
					ok.setEnabled(false);
				else
					ok.setEnabled(true);
				display.sleep();
			}
		}
		// Return the entered value, or null
		return elementEdited;
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
		GridData labelData = new GridData();
		labelData.horizontalSpan = 2;

		// Setto lo stile per la casella di testo
		GridData text_data = new GridData(GridData.FILL_HORIZONTAL);
		text_data.horizontalSpan = 2;

		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 4;
		tabFolder.setLayoutData(folder_data);

		// Creo due tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");

		//	
		SashForm generalForm = new SashForm(tabFolder, SWT.VERTICAL);
		generalForm.setLayout(new GridLayout(1, true));
		generalForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Show the message for stereotype
		Label label0 = new Label(generalForm, SWT.NONE);
		if (isActionDialog)
			label0.setText("Action Name:");
		else
			label0.setText("Guard Condition Name:");
		label0.setLayoutData(labelData);

		// Display the input box
		text0 = new Text(generalForm, SWT.BORDER);
		text0.setLayoutData(text_data);
		text0.setFocus();
		text0.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			}
		});

		Label labelVuota = new Label(generalForm, SWT.NONE);
		// Show the message for name

		// Display the input box

		Label label1 = new Label(generalForm, SWT.NONE);
		label1.setText("Comment:");
		label1.setLayoutData(labelData);

		textAreaComment = new Text(generalForm, SWT.MULTI | SWT.WRAP
				| SWT.V_SCROLL | SWT.BORDER);
		textAreaComment.setBackground(new Color(null, 255, 255, 255));

		textAreaComment.setVisible(true);

		int[] pesiGeneralForm = { 8, 10, 5, 8, 40 };
		generalForm.setWeights(pesiGeneralForm);

		item1.setControl(generalForm);

		// tab relativo al body

		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("Body");

		SashForm bodyForm = new SashForm(tabFolder, SWT.VERTICAL);
		bodyForm.setLayout(new GridLayout(1, true));
		bodyForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label2 = new Label(bodyForm, SWT.NONE);

		if (isActionDialog)
			label2.setText("Body of Action");
		else
			label2.setText("Body of Guard Condition");

		label2.setLayoutData(labelData);

		textAreaBody = new Text(bodyForm, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		textAreaBody.setBackground(new Color(null, 255, 255, 255));
		textAreaBody.setVisible(true);

		SashForm insertForm = new SashForm(bodyForm, SWT.HORIZONTAL);
		insertForm.setLayout(new GridLayout(1, true));
		insertForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button insertElement = new Button(insertForm, SWT.PUSH);
		if (isActionDialog)
			insertElement.setText("Insert Action");
		else
			insertElement.setText("Insert Guard Condition");

		insertElement.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				InsertGoraDialog insertDialog = new InsertGoraDialog(
						new Shell(), text0.getText(), anElementList
								.getAnElement(), isActionDialog);
				String elementInserted = insertDialog.open();
				if (elementInserted != null)
					textAreaBody.insert(" <--" + elementInserted + "--> ; ");
			}
		});
		if (anElementList.getAnElement().size() == 0)
			insertElement.setEnabled(false);

		Button insertFunction = new Button(insertForm, SWT.PUSH);
		insertFunction.setText("Insert Function");

		// Get the URI of the model file.
		URI fileURI = URI.createPlatformResourceURI(funcIFile.getFullPath()
				.toString());
		ResourceSet funcResourceSet = new ResourceSetImpl();
		// se la risorsa non esiste
		try {
			funcResourceSet.getResource(fileURI, true);
		}
		// gestisco l'eccezione
		catch (RuntimeException e) {

			WorkspaceModifyOperation operation = new OpenActionEditor(parent)
					.createResourceOperationForFunction(funcIFile
							.getProjectRelativePath().toString());
			try {
				operation.run(null);
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			funcResourceSet.getResource(fileURI, true);
		}

		Resource funcResource = (Resource) funcResourceSet.getResources()
				.get(0);

		functionList = (FunctionList) funcResource.getContents().get(0);
		insertFunction.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				InsertFunctionDialog insertDialog = new InsertFunctionDialog(
						new Shell(), functionList.getFunction());
				String elementInserted = insertDialog.open();
				if (elementInserted != null)
					textAreaBody.insert(" <-f-" + elementInserted + "-f-> ");
			}
		});
		if (functionList.getFunction().size() == 0)
			insertFunction.setEnabled(false);

		// riempo i campi del dialog
		if (editMode) {
			text0.setText(elementEdited.getName());

			if (elementEdited.getComment() != null)
				textAreaComment.setText(elementEdited.getComment());
			else
				textAreaComment.setText("");
			if (elementEdited.getBody() != null)
				textAreaBody.setText(elementEdited.getBody());
			else
				textAreaBody.setText("");

		}

		int[] bodyWeights = { 10, 80, 13 };
		bodyForm.setWeights(bodyWeights);

		item2.setControl(bodyForm);

		SashForm buttonForm = new SashForm(shell, SWT.HORIZONTAL | SWT.FILL);
		buttonForm.setLayout(new GridLayout(2, true));
		buttonForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		ok = new Button(buttonForm, SWT.PUSH);
		ok.setText("OK");
		labelData = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(labelData);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				elementEdited = goraFactory.createAnElement();

				elementEdited.setName(text0.getText());
				elementEdited.setComment(textAreaComment.getText());
				elementEdited.setBody(textAreaBody.getText());

				shell.close();
				cancelled = false;
			}
		});

		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(buttonForm, SWT.PUSH);
		cancel.setText("Cancel");
		labelData = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(labelData);
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

	}

	public boolean getCancelled() {
		return cancelled;
	}

	public boolean isCancelled() {
		return cancelled;
	}

}
