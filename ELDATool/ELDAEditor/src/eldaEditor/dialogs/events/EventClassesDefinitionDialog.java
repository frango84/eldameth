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

package eldaEditor.dialogs.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

import triggerModel.*;

/**
 * @author S. Mascillaro, F. Rango
 */
public class EventClassesDefinitionDialog extends Dialog {

	// Informa se l'utente ha premuto Cancel
	private boolean cancelled = true;

	// Informa se l'utente ha modificato le variabili
	private boolean modEvent = false;

	// Variabili globali
	private Table table;
	private TableItem item;
	private Button add, edit, remove, move_up, move_down;

	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	private Resource eventResource;

	private TriggerList eventTypeList;
	private Trigger eventTypeAdded;

	final TriggerModelPackage eventPackage = TriggerModelPackage.eINSTANCE;
	final TriggerModelFactory eventFactory = eventPackage
			.getTriggerModelFactory();

	private String eventType = "";

	public EventClassesDefinitionDialog(Shell parent, Resource eventResource) {// ,Resource
																				// eventTypeResource)
																				// {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.eventResource = eventResource;
		this.eventTypeList = (TriggerList) eventResource.getContents().get(0);
		setText("Event Classes Dialog");

	}

	public String open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(320, 300);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return eventType;
	}

	private void createContents(final Shell shell) {

		shell.setLayout(new GridLayout(1, true));

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 4;
		tabFolder.setLayoutData(folder_data);

		// Creo due tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");

		// Setto lo stile per la label
		GridData label_data = new GridData();
		label_data.horizontalSpan = 1;

		SashForm dialogForm = new SashForm(tabFolder, SWT.VERTICAL);
		dialogForm.setLayout(new GridLayout(2, true));

		Label label2 = new Label(dialogForm, SWT.NONE);
		label2.setText("Event classes defined by user.");
		label2.setLayoutData(label_data);

		SashForm tableForm = new SashForm(dialogForm, SWT.HORIZONTAL);
		tableForm.setLayout(new GridLayout(2, true));

		GridData tableFormData = new GridData(GridData.FILL_HORIZONTAL);
		tableFormData.horizontalSpan = 1;

		tableForm.setLayoutData(tableFormData);

		table = new Table(tableForm, SWT.FULL_SELECTION | SWT.BORDER);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setText("Event Classes");
		column.setWidth(180);

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

		Iterator i = eventTypeList.eAllContents();
		while (i.hasNext()) {
			Trigger eventType = (Trigger) i.next();
			item = new TableItem(table, SWT.NULL);
			if (!eventType.getTriggerName().equals(""))
				item.setText(0, eventType.getTriggerName());
		}

		// Inserisco un form contenente tutti i bottoni di controllo
		SashForm button_form = new SashForm(tableForm, SWT.VERTICAL);
		int[] weights = { 70, 30 };
		tableForm.setWeights(weights);

		add = new Button(button_form, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddEditEventClassDialog dialog = new AddEditEventClassDialog(
						new Shell(), eventTypeList);
				eventTypeAdded = dialog.open();
				if (!dialog.isCancelled()) {
					if (eventTypeAdded != null
							&& !eventTypeAdded.getTriggerName().equals("")) {
						item = new TableItem(table, SWT.NULL);
						item.setText(0, eventTypeAdded.getTriggerName());
						modEvent = true;
						selected = table.getItemCount() - 1;
						eventTypeAdded.setTriggerList(eventTypeList);
						// eventTypeList.eContents().add(eventTypeAdded);
					}
				} else {
					selected = table.getItemCount();
				}

				reset();
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
					saveEventTypeList();
				}
				cancelled = false;

				if (table.getSelection() != null
						&& table.getSelectionIndex() != -1)
					eventType = table.getItem(table.getSelectionIndex())
							.getText(0);
				shell.close();
			}

		}

		);

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

		reset();

	}

	private void edit_button_execute() {
		selected = table.getSelectionIndex();
		if (table.getSelectionIndex() >= 0
				&& table.getSelectionIndex() < table.getItemCount()) {

			TableItem item = table.getItem(table.getSelectionIndex());

			// Setto le proprietà della eventType da modificare da modificare
			Trigger eventType = getEventType(table.getItem(
					table.getSelectionIndex()).getText(0));

			AddEditEventClassDialog dialog = new AddEditEventClassDialog(
					new Shell(), eventType, eventTypeList);
			eventTypeAdded = dialog.open();

			// sovrascrivo gli stessi campi dell'item che sto modificando
			if (!dialog.isCancelled()) {

				item.setText(0, eventTypeAdded.getTriggerName());
				int index = eventTypeList.eContents().indexOf(eventType);

				eventTypeList.getTrigger().remove(index);
				eventTypeList.getTrigger().add(index, eventTypeAdded);
				modEvent = true;
			}
			
			/*else
				this.modEvent = false;*/
			
			reset();
		}
	}

	private Trigger getEventType(String text) {

		Iterator iterator = eventTypeList.eContents().iterator();
		while (iterator.hasNext()) {
			Trigger eventType = (Trigger) iterator.next();
			if (eventType.getTriggerName().equals(text))
				return eventType;
		}

		return null;
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

	private void saveEventTypeList() {
		Trigger eventType = null;
		TriggerList newEventTypeList = eventFactory.createTriggerList();

		for (int i = 0; i < table.getItemCount(); i++) {
			String eventTypeName = table.getItem(i).getText(0);
			eventType = getEventType(eventTypeName);
			eventType.setTriggerList(newEventTypeList);

		}

		eventResource.getContents().remove(0);
		eventResource.getContents().add(0, newEventTypeList);

		// Save the contents of the resource to the file system.
		//
		Map options = new HashMap();
		options.put(XMLResource.OPTION_ENCODING, "UTF-8");
		try {
			eventResource.save(options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
