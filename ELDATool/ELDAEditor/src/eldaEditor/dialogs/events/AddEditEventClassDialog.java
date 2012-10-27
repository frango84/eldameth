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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import triggerModel.Trigger;
import triggerModel.TriggerList;
import triggerModel.TriggerModelFactory;
import triggerModel.TriggerModelPackage;
import dscDiagramModel.DSCDiagram;

import dscDiagramModel.myDataTypes.TypeVariable;
import eldaEditor.dialogs.AddEditVariablesDialog;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

/**
 * @author S. Mascillaro, F. Rango
 */
public class AddEditEventClassDialog extends Dialog {

	// Informa se l'utente ha premuto Cancel
	private boolean cancelled = true;

	// Variabili globali
	private Table table;
	private TableItem item;
	private Button add, edit, remove, move_up, move_down;
	private Button inButton, udButton;
	private Text eventName;
	private Combo comboIN;
	private Combo comboUD;

	private boolean editMode = false;

	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	// Informa se l'utente ha modificato le variabili
	private boolean modEvent = false;

	final TriggerModelPackage eventPackage = TriggerModelPackage.eINSTANCE;
	final TriggerModelFactory eventFactory = eventPackage
			.getTriggerModelFactory();

	private Trigger eventTypeAdded;
	private TriggerList eventTypeList;

	private DSCDiagram parent;
	private IProject project;

	private Button ok, cancel;

	public AddEditEventClassDialog(Shell shell, TriggerList eventTypeList) {
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.eventTypeList = eventTypeList;
		this.parent = (DSCDiagram) ((GenericDiagramEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor()).getContents();
		setText("Add Event Class Dialog");
	}

	public AddEditEventClassDialog(Shell shell, Trigger eventType,
			TriggerList eventTypeList) {
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.eventTypeList = eventTypeList;
		this.eventTypeAdded = eventType;
		this.editMode = true;
		this.parent = (DSCDiagram) ((GenericDiagramEditor) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor()).getContents();

		setText("Edit Event Class Dialog");

	}

	public Trigger open() {

		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(400, 250);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				if (eventName.getText().trim().equals("")
						|| (comboIN.getSelectionIndex() == -1 && comboUD
								.getSelectionIndex() == -1))
					ok.setEnabled(false);
				else
					ok.setEnabled(true);

				if (udButton.getSelection()) {
					comboUD.setEnabled(true);
					comboIN.setEnabled(false);
				} else {
					comboIN.setEnabled(true);
					comboUD.setEnabled(false);
				}
				display.sleep();
			}
		}
		return eventTypeAdded;
	}

	private void createContents(final Shell shell) {

		// GridData generalData=new GridData();
		// generalData.horizontalSpan=2;

		shell.setLayout(new GridLayout(2, true));

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);

		folder_data.horizontalSpan = 2;
		tabFolder.setLayout(new GridLayout(2, true));
		tabFolder.setLayoutData(folder_data);

		// Creo due tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");

		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("Advanced");

		// Setto lo stile per la label
		GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
		labelData.horizontalSpan = 1;
		labelData.verticalSpan = 1;

		// ********************************************//
		// ********************************************//
		// ********** General Tab ********************//
		// ********************************************//
		// ********************************************//

		SashForm generalForm = new SashForm(tabFolder, SWT.VERTICAL);
		generalForm.setLayout(new GridLayout(2, true));

		SashForm labelForm = new SashForm(generalForm, SWT.VERTICAL);
		labelForm.setLayout(new GridLayout(2, true));
		Label label1 = new Label(labelForm, SWT.NONE);
		label1.setText("Event Class");
		label1.setLayoutData(labelData);

		SashForm emptyForm = new SashForm(labelForm, SWT.NONE);
		eventName = new Text(emptyForm, SWT.BORDER);
		eventName.setLayoutData(new GridData(GridData.BEGINNING));

		Label empty = new Label(emptyForm, SWT.HORIZONTAL);

		Label empty2 = new Label(labelForm, SWT.HORIZONTAL);

		int[] pesi = { 30, 35, 15 };
		labelForm.setWeights(pesi);

		Group group = new Group(generalForm, SWT.HORIZONTAL);
		group.setText("Event Super Class");
		group.setLayout(new GridLayout(2, true));
		group.setLayoutData(labelData);

		inButton = new Button(group, SWT.RADIO | SWT.FILL_EVEN_ODD);
		inButton.setText("IN Events");
		inButton.setLayoutData(labelData);

		udButton = new Button(group, SWT.RADIO);
		udButton.setText("User Defined Events");
		udButton.setLayoutData(labelData);

		comboIN = new Combo(group, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);
		comboIN.setLayoutData(labelData);

		comboUD = new Combo(group, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);
		comboUD.setLayoutData(labelData);

		int[] vertWeights = { 40, 60 };
		generalForm.setWeights(vertWeights);
		item1.setControl(generalForm);

		if (editMode)
			eventName.setText(eventTypeAdded.getTriggerName());

		fillComboIN();
		fillComboUD();

		if (editMode) {
			udButton.setSelection(eventTypeAdded.isExtendUDEvent());
			inButton.setSelection(!udButton.getSelection());
			if (eventTypeAdded.isExtendUDEvent())
				comboUD.select(getIndex(comboUD, eventTypeAdded
						.getExtendedEvent()));
			else
				comboIN.select(getIndex(comboIN, eventTypeAdded
						.getExtendedEvent()));

		} else {
			inButton.setSelection(true);
			udButton.setSelection(false);
		}

		if (udButton.getSelection()) {
			comboUD.setEnabled(true);
			comboIN.setEnabled(false);
		} else {
			comboIN.setEnabled(true);
			comboUD.setEnabled(false);
		}

		// ********************************************//
		// ********************************************//
		// ********** Advanced Tab *******************//
		// ********************************************//
		// ********************************************//

		SashForm advancedForm = new SashForm(tabFolder, SWT.VERTICAL);
		advancedForm.setLayout(new GridLayout(1, true));

		Label label5 = new Label(advancedForm, SWT.NONE);
		label5.setText("Additional Parameters");
		label5.setLayoutData(labelData);

		SashForm tableForm = new SashForm(advancedForm, SWT.HORIZONTAL);
		tableForm.setLayout(new GridLayout(2, true));

		GridData tableFormData = new GridData(GridData.FILL_HORIZONTAL);
		tableFormData.horizontalSpan = 2;

		tableForm.setLayoutData(tableFormData);

		table = new Table(tableForm, SWT.FULL_SELECTION | SWT.BORDER);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setText("Type");
		column.setWidth(150);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("Name");
		column.setWidth(150);

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
		if (editMode) {
			Iterator iterator = eventTypeAdded.getParameters().iterator();
			while (iterator.hasNext()) {
				TypeVariable variable = (TypeVariable) iterator.next();
				item = new TableItem(table, SWT.NULL);
				if (variable.getType() != null)
					item.setText(0, variable.getType());
				else
					item.setText(0, "");
				if (!variable.getName().equals(""))
					item.setText(1, variable.getName());
				else
					item.setText(1, "");

			}
		}

		// Inserisco un form contenente tutti i bottoni di controllo
		SashForm button_form = new SashForm(tableForm, SWT.VERTICAL);
		int[] weights = { 90, 20 };
		tableForm.setWeights(weights);

		add = new Button(button_form, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddEditVariablesDialog dialog = new AddEditVariablesDialog(
						new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
						true);
				TypeVariable variable = dialog.open();
				if (!dialog.getCancelled()) {
					if (!variable.getType().equals("")
							|| !variable.getName().equals("")) {
						item = new TableItem(table, SWT.NULL);
						if (!variable.getType().equals(""))
							item.setText(0, variable.getType());
						else
							item.setText(0, "");
						if (!variable.getName().equals(""))
							item.setText(1, variable.getName());
						else
							item.setText(1, "");

						modEvent = true;
					}
					selected = table.getItemCount() - 1;
				} else
					selected = table.getItemCount();
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

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				selected = table.getSelectionIndex();
				reset();
			}
		});

		int[] advancedWeights = { 10, 90 };
		advancedForm.setWeights(advancedWeights);

		item2.setControl(advancedForm);
		// ********************************************//
		// ********************************************//

		ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);

		ok.setLayoutData(buttonData);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				eventTypeAdded = eventFactory.createTrigger();
				eventTypeAdded.setTriggerName(eventName.getText());
				eventTypeAdded.setExtendUDEvent(udButton.getSelection());
				if (udButton.getSelection())
					eventTypeAdded.setExtendedEvent(comboUD.getItem(comboUD
							.getSelectionIndex()));
				else
					eventTypeAdded.setExtendedEvent(comboIN.getItem(comboIN
							.getSelectionIndex()));
				eventTypeAdded.getParameters().addAll(getVariableList());
				cancelled = false;
				shell.close();
			}
		});

		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(buttonData);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
				cancelled = true;
			}
		});

		reset();
	}

	private void fillComboUD() {
		String baseDati = parent.getEventFile();
		List eventUD = getEventTypeList(baseDati);
		comboUD.add("");
		if (eventUD != null)
			for (int i = 0; i < eventUD.size(); i++) {
				String name = ((Trigger) eventUD.get(i)).getTriggerName();
				if (!name.equals(eventName.getText()))
					comboUD.add(name);
			}
	}

	private List getEventTypeList(String baseDati) {

		//URI.createFileURI(baseDati);
		// Creo il resourceSet
		ResourceSetImpl eventResourceSet = new ResourceSetImpl();
		IProject project = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile().getProject();
		// recupero il file associato alla baseDati
		IFile file = project.getFile(baseDati);

		// Get the URI of the model file.
		URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
				.toString());

		try {
			eventResourceSet.getResource(fileURI, true);
		}
		// gestisco l'eccezione se la risorsa non esiste
		catch (RuntimeException e) {
			return null;
		}

		// Prelevo la risorsa
		Resource eventResource = (Resource) eventResourceSet.getResources()
				.get(0);

		// aggiungo alla lista
		List newList = new ArrayList();
		newList.addAll(((TriggerList) eventResource.getContents().get(0))
				.getTrigger());
		return newList;
	}

	private void fillComboIN() {
		// Path frameworkPath = new Path(
		// "ECLIPSE_HOME/plugins/ELDAFramework.jar");
		//		
		// project=((IFileEditorInput)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput()).getFile().getProject();
		//
		// // creo il javaProject
		// IJavaProject jProject = JavaCore.create(project);
		// try {
		// // recupero il jar di actiware
		// IPackageFragmentRoot jar = jProject
		// .findPackageFragmentRoot(JavaCore
		// .getResolvedVariablePath(JavaCore.newVariableEntry(
		// frameworkPath, null, null).getPath()));
		//
		// for (int i = 1; i < jar.getChildren().length; i++) {
		// IPackageFragment jarFragment = (IPackageFragment) JavaCore
		// .create(jar.getChildren()[i].getHandleIdentifier());
		// if (jarFragment.getElementName().startsWith(
		// "eldaframework.eldaevent")) {
		// IClassFile[] classArray = jarFragment.getClassFiles();
		// for (int j = 0; j < classArray.length; j++){
		// comboIN.add(classArray[j].getElementName());
		// }
		// }
		// }
		//
		// } catch (JavaModelException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// internal
		comboIN.add("ELDAEventInternal");

		// direct coordination
		comboIN.add("ELDAEventMSG");
		comboIN.add("ELDAEventRPCResult");

		// publish-subscribe coordination
		comboIN.add("ELDAEventEVTNotification");

		// tuples coordination
		comboIN.add("ELDAEventReturnTuple");

		// lifecycle management
		comboIN.add("ELDAEventCreateNotify");
		comboIN.add("ELDAEventCloneNotify");

		// timer management
		comboIN.add("ELDAEventCreateTimerNotify");
		comboIN.add("ELDAEventTimeoutNotify");

		// resource management
		comboIN.add("ELDAEventDBNotify");
		comboIN.add("ELDAEventDBInput");
		comboIN.add("ELDAEventConsoleInput");

		// information management
		comboIN.add("ELDAEventRegisteredAgent");
	}

	private void edit_button_execute() {
		selected = table.getSelectionIndex();
		if (table.getSelectionIndex() >= 0
				&& table.getSelectionIndex() < table.getItemCount()) {
			TableItem item = table.getItem(table.getSelectionIndex());
			// Setto le proprietà della variabile da modificare
			TypeVariable variable = new TypeVariable(table.getItem(
					table.getSelectionIndex()).getText(0), table.getItem(
					table.getSelectionIndex()).getText(1), "");

			AddEditVariablesDialog dialog = new AddEditVariablesDialog(
					new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
					variable, true);
			variable = dialog.open();

			// sovrascrivo gli stessi campi dell'item che sto modificando
			if (!dialog.getCancelled()) {

				// Verifico se sono cambiate le proprietà della transizione
				if (variable.getType() != item.getText(0)
						|| variable.getName() != item.getText(1)
						|| variable.getValue() != item.getText(2)) {
					if (variable.getType() != null)
						item.setText(0, variable.getType());
					else
						item.setText(0, "");
					if (variable.getName() != null)
						item.setText(1, variable.getName());
					else
						item.setText(1, "");

					modEvent = true;
				}

			}
			
			/*else
				modEvent = false;*/
			
			reset();
		}
	}

	public boolean isCancelled() {
		return cancelled;
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

	private List getVariableList() {
		List variableList = new ArrayList();
		// Creo una nuova variabile interna per ogni item della tabella
		for (int i = 0; i < table.getItemCount(); i++) {
			TypeVariable variable = new TypeVariable();
			variable.setType(table.getItem(i).getText(0));
			variable.setName(table.getItem(i).getText(1));
			variableList.add(variable);
		}
		return variableList;
	}

	private int getIndex(Combo combo, String id) {
		// per tutti gli elementi del combo
		for (int i = 0; i < combo.getItemCount(); i++)
			// verifico che ne esista uno con lo stesso id e ne ritorno l'indice
			if (combo.getItem(i).equals(id))
				return i;
		// nessun elemento selezionato
		return -1;
	}
}
