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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import actionGuardModel.ActionGuardModelFactory;
import actionGuardModel.ActionGuardModelPackage;
import actionGuardModel.AnElement;
import actionGuardModel.AnElementList;
import dscDiagramModel.DSCDiagram;

/**
 * Classe per creare un finestra di dialogo, tramite la quale si possono
 * visualizzare le proprietà delle azione e le guard condition definite
 * dall'utente.
 * 
 * @author samuele, F. Rango
 */
public class ActionGuardDefinitionDialog extends Dialog {

	// Informa se l'utente ha premuto Cancel
	private boolean cancelled = true;

	// Informa se l'utente ha modificato le variabili
	private boolean modEvent = false;

	private boolean isActionDialog = false;

	// Variabili globali
	private Table table;
	private TableItem item;
	private Button add, edit, remove, move_up, move_down;

	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	// gestione delle risorse
	private Resource goraResource;

	private IFile funcFile;

	final ActionGuardModelPackage goraPackage = ActionGuardModelPackage.eINSTANCE;
	final ActionGuardModelFactory goraFactory = goraPackage
			.getActionGuardModelFactory();

	private AnElementList goraList = null;

	private String returnValue = "";

	private DSCDiagram parent;

	/**
	 * 
	 * Costruttore
	 * 
	 * @param shell
	 * @param eventResource
	 */

	public ActionGuardDefinitionDialog(Shell shell, Resource goraResource,
			IFile funcFile, boolean isActionDialog, DSCDiagram parent) {
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.isActionDialog = isActionDialog;
		this.goraResource = goraResource;
		this.funcFile = funcFile;
		if (goraResource != null)
			this.goraList = ((AnElementList) goraResource.getContents().get(0));
		this.parent = parent;

		if (isActionDialog)
			setText("Actions Dialog");
		else
			setText("Guard Conditions Dialog");
	}

	/**
	 * Apre la finestra di dialogo
	 */
	public String open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(450, 300);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return returnValue;
	}

	/**
	 * Inizializza la finestra di dialogo
	 * 
	 * @param shell
	 *            La finestra di dialogo
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(1, true));

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 4;
		tabFolder.setLayoutData(folder_data);

		// Creo il tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");

		// Setto lo stile per la label
		GridData labelData = new GridData();
		labelData.horizontalSpan = 1;

		SashForm dialogForm = new SashForm(tabFolder, SWT.VERTICAL);
		dialogForm.setLayout(new GridLayout(2, true));

		Label label2 = new Label(dialogForm, SWT.NONE);
		if (isActionDialog)
			label2.setText("User defined actions.");
		else
			label2.setText("User defined guard conditions.");

		label2.setLayoutData(labelData);

		SashForm tableForm = new SashForm(dialogForm, SWT.HORIZONTAL);
		tableForm.setLayout(new GridLayout(2, true));

		GridData tableFormData = new GridData(GridData.FILL_HORIZONTAL);
		tableFormData.horizontalSpan = 2;

		tableForm.setLayoutData(tableFormData);

		// tabella che mostra nome, commento e corpo
		table = new Table(tableForm, SWT.FULL_SELECTION | SWT.BORDER);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		if (isActionDialog)
			column.setText("Action Name");
		else
			column.setText("Guard Name");

		column.setWidth(100);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("Comment");
		column.setWidth(100);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("Body");
		column.setWidth(100);

		table.setHeaderVisible(true);

		table.setLinesVisible(true);

		table.setHeaderVisible(true);

		table.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
				edit_button_execute();
			}

			public void mouseUp(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}
		});

		// riempo la tabella
		Iterator i = goraList.eAllContents();
		while (i.hasNext()) {
			AnElement element = (AnElement) i.next();
			item = new TableItem(table, SWT.NULL);
			if (element.getName() != null)
				item.setText(0, element.getName());
			else
				item.setText(0, "");
			if (element.getComment() != null)
				item.setText(1, element.getComment());
			else
				item.setText(1, "");

			if (element.getBody() != null)
				item.setText(2, element.getBody());
			else
				item.setText(2, "");

		}

		// Inserisco un form contenente tutti i bottoni di controllo
		SashForm button_form = new SashForm(tableForm, SWT.VERTICAL);
		int[] weights = { 90, 20 };
		tableForm.setWeights(weights);

		add = new Button(button_form, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddEditActionGuardDialog dialog = null;
				if (isActionDialog)
					dialog = new AddEditActionGuardDialog(new Shell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
							createListFromTable(), funcFile, true, parent);
				else
					dialog = new AddEditActionGuardDialog(new Shell(),
							SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
							createListFromTable(), funcFile, false, parent);
				AnElement element = dialog.open();
				if (!dialog.getCancelled()) {
					item = new TableItem(table, SWT.NULL);
					if (element.getName() != "") {
						item.setText(0, element.getName());
						modEvent = true;
					}
					if (element.getComment() != "") {
						item.setText(1, element.getComment());
						modEvent = true;
					}
					if (element.getBody() != "") {
						item.setText(2, element.getBody());
						modEvent = true;
					}
					selected = table.getItemCount() - 1;
				} else {
					selected = table.getItemCount() - 1;
					reset();
				}
			}
		});

		remove = new Button(button_form, SWT.PUSH);
		remove.setText("Remove");
		remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (table.getSelectionIndex() >= 0
						&& table.getSelectionIndex() < table.getItemCount()) {
					selected = table.getSelectionIndex();
					table.remove(table.getSelectionIndex());
				}

				reset();
				modEvent = true;
			}
		});

		edit = new Button(button_form, SWT.PUSH);
		edit.setText("Edit");
		edit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				edit_button_execute();
			}
		});

		move_up = new Button(button_form, SWT.PUSH);
		move_up.setText("Move Up");
		move_up.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				selected = table.getSelectionIndex();
				if (table.getSelectionIndex() != 0 && table.getItemCount() > 0) {
					TableItem new_item = new TableItem(table, SWT.NULL, table
							.getSelectionIndex() - 1);
					item = table.getItem(table.getSelectionIndex());
					new_item.setText(0, item.getText(0));
					new_item.setText(1, item.getText(1));
					new_item.setText(2, item.getText(2));
					new_item.setText(3, item.getText(3));

					table.remove(table.getSelectionIndex());
				}
				selected = selected - 1;
				reset();
				modEvent = true;
			}
		});

		move_down = new Button(button_form, SWT.PUSH);
		move_down.setText("Move Down");
		move_down.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				selected = table.getSelectionIndex();
				if (table.getSelectionIndex() != table.getItemCount() - 1) {
					TableItem new_item = new TableItem(table, SWT.NULL, table
							.getSelectionIndex());
					item = table.getItem(table.getSelectionIndex() + 1);
					new_item.setText(0, item.getText(0));
					new_item.setText(1, item.getText(1));
					new_item.setText(2, item.getText(2));
					new_item.setText(3, item.getText(3));

					table.remove(table.getSelectionIndex() + 1);
				}
				selected = selected + 1;
				reset();
				modEvent = true;
			}
		});

		SashForm okCancelForm = new SashForm(dialogForm, SWT.HORIZONTAL);

		Button ok = new Button(okCancelForm, SWT.PUSH);
		ok.setText("OK");
		GridData button_data = new GridData(GridData.FILL_HORIZONTAL);

		ok.setLayoutData(button_data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				if (modEvent) {
					saveGoraList();
				}
				cancelled = false;
				if (table.getSelectionIndex() != -1) {
					returnValue = table.getItem(table.getSelectionIndex())
							.getText(0);
				}
				shell.close();
			}
		});

		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(okCancelForm, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(button_data);
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

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				selected = table.getSelectionIndex();
				reset();
			}
		});

		reset();
		int[] vertWeigths = { 10, 60, 10 };
		dialogForm.setWeights(vertWeigths);
		item1.setControl(dialogForm);
	}

	public int textLength(String text, Font f) {
		return FigureUtilities.getTextWidth(text, f);
	}

	public boolean getCancelled() {
		return cancelled;
	}

	private void edit_button_execute() {
		selected = table.getSelectionIndex();
		if (table.getSelectionIndex() >= 0
				&& table.getSelectionIndex() < table.getItemCount()) {

			TableItem item = table.getItem(table.getSelectionIndex());

			// Setto le proprietà della variabile da modificare
			AnElement element = goraFactory.createAnElement();

			element.setName(item.getText(0));
			element.setComment(item.getText(1));
			element.setBody(item.getText(2));

			AddEditActionGuardDialog dialog = null;
			if (isActionDialog)
				dialog = new AddEditActionGuardDialog(new Shell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, element,
						createListFromTable(), funcFile, true, parent);
			else
				dialog = new AddEditActionGuardDialog(new Shell(),
						SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL, element,
						createListFromTable(), funcFile, false, parent);

			element = dialog.open();

			// sovrascrivo gli stessi campi dell'item che sto modificando
			if (!dialog.getCancelled()) {

				// Verifico se sono cambiate le proprietà
				if (element.getName() != item.getText(0)
						|| element.getComment() != item.getText(1)) {
					item.setText(0, element.getName());
					item.setText(1, element.getComment());
					item.setText(2, element.getBody());
					modEvent = true;
				}
			}

			/*
			 * else modEvent = false;
			 */

			reset();
		}
	}

	private void reset() {
		all_on();

		// Se l'item selezionato è il primo in alto non posso ulteriormente
		// spostarlo verso l'alto
		if (selected == 0)
			move_up.setEnabled(false);

		// Se l'item selezionato è l'ultimo in basso non posso ulteriormente
		// spostarlo verso il basso
		if (selected == table.getItemCount() - 1)
			move_down.setEnabled(false);

		// Se l'item selezionato non è valido, posso solo aggiungere un nuovo
		// item
		if (selected < 0 || selected > table.getItemCount()) {
			edit.setEnabled(false);
			remove.setEnabled(false);
			move_up.setEnabled(false);
			move_down.setEnabled(false);
		}

		// Seleziono il nuovo item
		table.setSelection(selected);
		table.setFocus();
	}

	private void all_on() {
		// Abilito tutti i bottoni
		add.setEnabled(true);
		edit.setEnabled(true);
		remove.setEnabled(true);
		move_up.setEnabled(true);
		move_down.setEnabled(true);
	}

	private void saveGoraList() {

		AnElementList goraList = createListFromTable();
		goraResource.getContents().remove(0);
		goraResource.getContents().add(0, goraList);

		// Save the contents of the resource to the file system.
		Map options = new HashMap();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			goraResource.save(options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private AnElementList createListFromTable() {

		AnElement element = null;
		AnElementList goraList = goraFactory.createAnElementList();

		for (int i = 0; i < table.getItemCount(); i++) {
			element = goraFactory.createAnElement();
			item = table.getItem(i);
			element.setName(item.getText(0));
			element.setComment(item.getText(1));
			element.setBody(item.getText(2));

			goraList.getAnElement().add(element);
		}
		return goraList;

	}
}
