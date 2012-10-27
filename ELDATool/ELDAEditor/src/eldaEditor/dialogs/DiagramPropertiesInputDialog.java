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

import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import genericUMLDiagramEditor.commands.ChangeStereotypeCommand;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import eldaEditor.commands.ChangeDiagramFileDatabaseCommand;
import eldaEditor.commands.SetVariableCommandDSCDiagram;
import eldaEditor.wizards.ExtensionInterface;

/**
 * Classe per creare un finestra di dialogo, tramite la quale si possono
 * inserire le proprietà
 * 
 * @author samuele, F. Rango
 */
public class DiagramPropertiesInputDialog extends Dialog implements
		ExtensionInterface {

	/**
	 * E' il diagramma parent di tutto
	 */
	private DSCDiagram parent;

	// Informa se l'utente ha premuto Cancel
	private boolean cancelled = true;

	// Informa se l'utente ha modificato le variabili
	private boolean modVariables = false;

	// informa se ha modificato i file
	private boolean modFileDatabase = false;
	// Variabili globali
	private Table table;
	private TableItem item;
	private Button add, edit, remove, move_up, move_down;

	// variabili golabali relativi al tab advanced
	private Text eventText;
	private Text guardText;
	private Text actionText;
	private Text functionText;
	private String nameProject;

	// work space
	private IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
	// gestione del DES
	private String selection;
	private String oldSelection;
	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	/**
	 * Costruttore
	 * 
	 * @param parent
	 *            La shell
	 * @param style
	 *            Lo stile
	 * @param state
	 *            Istanza dell'objectflow di cui si vogliono impostare e/o
	 *            modificare le proprietà
	 */
	public DiagramPropertiesInputDialog(Shell parent, DSCDiagram diagram) {
		// Let users override the default styles
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.parent = diagram;
	}

	/**
	 * Apre la finestra di dialogo
	 */
	public void open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText("Diagram Properties");
		createContents(shell);
		shell.setSize(450, 400);
		shell.setLocation(200, 200);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Inizializza la finestra di dialogo
	 * 
	 * @param shell
	 *            La finestra di dialogo
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(3, true));

		// Setto lo stile per la label
		GridData label_data = new GridData();
		label_data.horizontalSpan = 3;
		// Setto lo stile per la casella di testo
		GridData text_data = new GridData(GridData.FILL_HORIZONTAL);
		text_data.horizontalSpan = 3;

		// Setto lo stile per la label
		GridData sash_data = new GridData();
		sash_data.horizontalSpan = 1;

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 4;
		tabFolder.setLayoutData(folder_data);

		// Creo due tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");
		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("Advanced");

		// *********************************************************
		// *********************************************************
		// *********************************************************
		// Creo il contenuto del tab General
		// *********************************************************
		// *********************************************************

		SashForm general_form = new SashForm(tabFolder, SWT.VERTICAL);
		general_form.setLayout(new GridLayout(1, true));

		// recupero il workspace

		IFile file = ((FileEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getFile();
		nameProject = file.getProject().getName();

		int[] weigths = { 80, 20 };

		Label eventLabel = new Label(general_form, SWT.FILL);
		eventLabel.setText("Select the file containing events");
		eventLabel.setLayoutData(label_data);

		SashForm eventSash = new SashForm(general_form, SWT.HORIZONTAL);
		eventSash.setLayout(new GridLayout(1, false));
		eventSash.setLayoutData(sash_data);

		SashForm emptySash = new SashForm(general_form, SWT.HORIZONTAL);
		emptySash.setLayout(new GridLayout(2, false));

		eventText = new Text(eventSash, SWT.BORDER);
		eventText.setText(parent.getEventFile());
		eventText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validatePage();
				modFileDatabase = true;
			}
		});

		Button buttonBrowse = new Button(eventSash, SWT.PUSH);
		buttonBrowse.setText("Browse..");
		buttonBrowse.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell(), SWT.OPEN);
				String projectPath = wsroot.getLocation().toOSString().concat(
						"\\" + nameProject);
				fileDialog.setFilterPath(projectPath);
				String[] extensions = { "*" + EVENT_EXTENSION };
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
				// if (!fileDialog.getFileName().equals(""))
				// eventText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals("")) {
					String relativePath = fileDialog.getFilterPath().toString();
					if (relativePath.equals(projectPath))
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject, "");
					else
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject + "\\", "");

					if (!relativePath.equals(""))
						eventText.setText(relativePath + "\\"
								+ fileDialog.getFileName());
					else
						eventText.setText(fileDialog.getFileName());
				}
			}
		});

		eventSash.setWeights(weigths);

		// guard section
		Label guardLabel = new Label(general_form, SWT.FILL);
		guardLabel.setText("Select the file containing guards");
		guardLabel.setLayoutData(label_data);

		SashForm guardSash = new SashForm(general_form, SWT.HORIZONTAL);
		guardSash.setLayout(new GridLayout(1, false));
		guardSash.setLayoutData(sash_data);

		SashForm emptySash2 = new SashForm(general_form, SWT.HORIZONTAL);
		emptySash.setLayout(new GridLayout(2, false));

		guardText = new Text(guardSash, SWT.BORDER);
		guardText.setText(parent.getGuardFile());
		guardText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validatePage();
				modFileDatabase = true;
			}
		});

		Button buttonBrowse2 = new Button(guardSash, SWT.PUSH);
		buttonBrowse2.setText("Browse..");
		buttonBrowse2.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell(), SWT.OPEN);
				String projectPath = wsroot.getLocation().toOSString().concat(
						"\\" + nameProject);
				fileDialog.setFilterPath(projectPath);
				String[] extensions = { "*" + GUARD_EXTENSION };
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
				// if (!fileDialog.getFileName().equals(""))
				// guardText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals("")) {
					String relativePath = fileDialog.getFilterPath().toString();
					if (relativePath.equals(projectPath))
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject, "");
					else
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject + "\\", "");

					if (!relativePath.equals(""))
						guardText.setText(relativePath + "\\"
								+ fileDialog.getFileName());
					else
						guardText.setText(fileDialog.getFileName());

				}
			}
		});

		guardSash.setWeights(weigths);

		// action section
		Label actionLabel = new Label(general_form, SWT.FILL);
		actionLabel.setText("Select the file containing actions");
		actionLabel.setLayoutData(label_data);

		SashForm actionSash = new SashForm(general_form, SWT.HORIZONTAL);
		actionSash.setLayout(new GridLayout(1, false));
		actionSash.setLayoutData(sash_data);

		SashForm emptySash3 = new SashForm(general_form, SWT.HORIZONTAL);
		emptySash.setLayout(new GridLayout(2, false));

		actionText = new Text(actionSash, SWT.BORDER);
		actionText.setText(parent.getActionFile());
		actionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validatePage();
				modFileDatabase = true;
			}
		});

		Button buttonBrowse3 = new Button(actionSash, SWT.PUSH);
		buttonBrowse3.setText("Browse..");
		buttonBrowse3.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell(), SWT.OPEN);
				String projectPath = wsroot.getLocation().toOSString().concat(
						"\\" + nameProject);
				fileDialog.setFilterPath(projectPath);
				String[] extensions = { "*" + ACTION_EXTENSION };
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
				if (!fileDialog.getFileName().equals("")) {
					String relativePath = fileDialog.getFilterPath().toString();
					if (relativePath.equals(projectPath))
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject, "");
					else
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject + "\\", "");

					if (!relativePath.equals(""))
						actionText.setText(relativePath + "\\"
								+ fileDialog.getFileName());
					else
						actionText.setText(fileDialog.getFileName());

				}
			}
		});

		actionSash.setWeights(weigths);

		// support functions
		Label supportLabel = new Label(general_form, SWT.FILL);
		supportLabel.setText("Select the file containing support functions");
		supportLabel.setLayoutData(label_data);

		SashForm functionSash = new SashForm(general_form, SWT.HORIZONTAL);
		functionSash.setLayout(new GridLayout(1, false));
		functionSash.setLayoutData(sash_data);

		SashForm emptySash4 = new SashForm(general_form, SWT.HORIZONTAL);
		emptySash.setLayout(new GridLayout(2, false));

		functionText = new Text(functionSash, SWT.BORDER);
		functionText.setText(parent.getFunctionFile());
		functionText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				validatePage();
				modFileDatabase = true;
			}
		});

		Button buttonBrowse4 = new Button(functionSash, SWT.PUSH);
		buttonBrowse4.setText("Browse..");
		buttonBrowse4.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(new Shell(), SWT.OPEN);
				String projectPath = wsroot.getLocation().toOSString().concat(
						"\\" + nameProject);
				fileDialog.setFilterPath(projectPath);
				String[] extensions = { "*" + FUNCTION_EXTENSION };
				fileDialog.setFilterExtensions(extensions);
				fileDialog.open();
				// if (!fileDialog.getFileName().equals(""))
				// functionText.setText(fileDialog.getFileName());
				if (!fileDialog.getFileName().equals("")) {
					String relativePath = fileDialog.getFilterPath().toString();
					if (relativePath.equals(projectPath))
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject, "");
					else
						relativePath = relativePath.replace(wsroot
								.getLocation().toOSString().toString()
								+ "\\" + nameProject + "\\", "");

					if (!relativePath.equals(""))
						functionText.setText(relativePath + "\\"
								+ fileDialog.getFileName());
					else
						functionText.setText(fileDialog.getFileName());

				}
			}
		});

		functionSash.setWeights(weigths);

		item1.setControl(general_form);

		// *********************************************************
		// *********************************************************
		// *********************************************************
		// Creo il contenuto del tab Advanced
		// *********************************************************
		// *********************************************************

		SashForm advanced_form = new SashForm(tabFolder, SWT.VERTICAL);
		advanced_form.setLayout(new GridLayout(3, true));

		Label label1 = new Label(advanced_form, SWT.NONE);
		label1.setText("Default History Entrance");
		label1.setLayoutData(text_data);

		SashForm comboForm = new SashForm(advanced_form, SWT.HORIZONTAL);

		final Combo desCombo = new Combo(comboForm, SWT.READ_ONLY
				| SWT.DROP_DOWN | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		int count = parent.getModelElements().size();

		// riempo il combo con gli state a top level
		for (int i = 0; i < count; i++) {
			if (parent.getModelElements().get(i) instanceof DSCState) {
				desCombo.add(((DSCState) parent.getModelElements().get(i))
						.getName());

				if ((((DSCState) parent.getModelElements().get(i))
						.getStereotype().equals("dhs"))
						|| (((DSCState) parent.getModelElements().get(i))
								.getStereotype().equals("DHS"))) {
					oldSelection = ((DSCState) parent.getModelElements().get(i))
							.getName();
				}
			}
		}
		// selezione l'elemento del combo che ha lo stereotipo fissato a default
		// entrance history
		for (int j = 0; j < desCombo.getItemCount(); j++) {
			if (desCombo.getItem(j).equals(oldSelection))
				desCombo.select(j);
		}

		desCombo.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(SelectionEvent e) {
				int index = desCombo.getSelectionIndex();
				selection = desCombo.getItem(index);
				modVariables = true;

			}

		});

		Label label2 = new Label(advanced_form, SWT.NONE);
		label2.setText("Internal Variables:");
		label2.setLayoutData(text_data);

		SashForm table_form = new SashForm(advanced_form, SWT.HORIZONTAL);
		GridData table_form_data = new GridData(GridData.FILL_HORIZONTAL);
		table_form_data.horizontalSpan = 3;
		table_form.setLayoutData(table_form_data);

		table = new Table(table_form, SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);

		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setText("Type");
		column.setWidth(91);
		table.setHeaderVisible(true);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("Name");
		column.setWidth(91);
		table.setHeaderVisible(true);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("Value");
		column.setWidth(91);
		table.setHeaderVisible(true);

		column = new TableColumn(table, SWT.LEFT);
		column.setText("PIC");
		column
				.setToolTipText("This field indicates if this value is present into the constructor");
		column.setWidth(40);
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

		// Riempio la tabella con i dati provenienti dalle variabili interne
		// dello stato
		Iterator i = parent.getDiagramVariables().iterator();
		while (i.hasNext()) {
			TypeDiagramVariable var = (TypeDiagramVariable) i.next();
			item = new TableItem(table, SWT.NULL);
			if (var.getType() != null)
				item.setText(0, var.getType());
			else
				item.setText(0, "");
			if (var.getName() != null)
				item.setText(1, var.getName());
			else
				item.setText(1, "");
			if (var.getValue() != null)
				item.setText(2, var.getValue());
			else
				item.setText(2, "");
			if (var.isBePresentIntoConstructor())
				item.setText(3, "T");
			else
				item.setText(3, "F");
		}

		// Inserisco un form contenente tutti i bottoni di controllo
		SashForm button_form = new SashForm(table_form, SWT.VERTICAL);

		add = new Button(button_form, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddEditDiagramVariablesDialog dialog = new AddEditDiagramVariablesDialog(
						new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				TypeDiagramVariable variable = dialog.open();
				if (!dialog.getCancelled()) {
					if (variable.getType() != "" || variable.getName() != ""
							|| variable.getValue() != "") {
						item = new TableItem(table, SWT.NULL);
						if (variable.getType() != null)
							item.setText(0, variable.getType());
						else
							item.setText(0, "");
						if (variable.getName() != null)
							item.setText(1, variable.getName());
						else
							item.setText(1, "");
						if (variable.getValue() != null)
							item.setText(2, variable.getValue());
						else
							item.setText(2, "");
						if (variable.isBePresentIntoConstructor())
							item.setText(3, "T");
						else
							item.setText(3, "F");
									  		
						modVariables = true;
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
				modVariables = true;
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
				modVariables = true;
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
				modVariables = true;
			}
		});

		// // Inserisco degli spazi vuoti per far salire i bottoni di modifica
		// delle transizioni
		// Label spazio1 = new Label(button_form, SWT.PUSH);
		// Label spazio2 = new Label(button_form, SWT.PUSH);

		// Fisso le proporzioni tra gli elementi del form della tabella
		int[] table_pesi = { 300, 100 };
		table_form.setWeights(table_pesi);

		item2.setControl(advanced_form);
		int[] advanced_pesi = { 20, 20, 20, 200 };// ,65,50,65,50,65};//,65,50};
		advanced_form.setWeights(advanced_pesi);

		// // Aggiungo i bottoni di controllo del dialog; prima inserisco
		// // una label vuota per farli spostare sulla destra
		Label spazio3 = new Label(shell, SWT.PUSH);

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		GridData button_data = new GridData(GridData.FILL_HORIZONTAL);

		ok.setLayoutData(button_data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				// Memorizzo in variabili temporanee i dati contenuti nei vari
				// tab del dialog
				String eventFile = eventText.getText();
				String guardFile = guardText.getText();
				String actionFile = actionText.getText();
				String funcFile = functionText.getText();

				CompoundCommand command = new CompoundCommand();

				if (modVariables || modFileDatabase) {
					// recupero gli indici nel combo della vecchia e della nuova
					// selezione se esistenti
					int index = getIndex(selection);
					int oldIndex = getIndex(oldSelection);
					SetVariableCommandDSCDiagram varCmd = new SetVariableCommandDSCDiagram(
							parent, table);
					ChangeDiagramFileDatabaseCommand fileCmd = new ChangeDiagramFileDatabaseCommand(
							parent, eventFile, guardFile, actionFile, funcFile);

					// se il dhs era assegnato e adesso viene scelto un altro
					// elemento
					if (oldSelection != null && index != -1) {
						ChangeStereotypeCommand strCmd2 = new ChangeStereotypeCommand(
								(DSCState) parent.getModelElements().get(
										oldIndex), "");
						command.add(strCmd2);
					}
					if (selection != null) {
						ChangeStereotypeCommand strCmd = new ChangeStereotypeCommand(
								(DSCState) parent.getModelElements().get(index),
								"DHS");
						command.add(strCmd);
					}

					command.add(varCmd);
					command.add(fileCmd);

					if (command != null
							&& ((GenericDiagramEditor) PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage().getActiveEditor())
									.getAdapter(CommandStack.class) != null) {
						CommandStack cs = (CommandStack) ((GenericDiagramEditor) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().getActiveEditor())
								.getAdapter(CommandStack.class);
						cs.execute(command);
					}

				}

				cancelled = false;
				shell.close();
			}

		}

		);

		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(shell, SWT.PUSH);
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

		// Setto il cursore lampeggiante nella casella del nome dello stato
		// rendendola attiva
		// text1.setFocus();
		// text1.selectAll();
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
			//    		
			TableItem item = table.getItem(table.getSelectionIndex());

			boolean pic;
			if (item.getText(3).equals("T"))
				pic = true;
			else
				pic = false;

			// Setto le proprietà della variabile da modificare
			TypeDiagramVariable variable = new TypeDiagramVariable(table
					.getItem(table.getSelectionIndex()).getText(0), table
					.getItem(table.getSelectionIndex()).getText(1), table
					.getItem(table.getSelectionIndex()).getText(2), pic);

			AddEditDiagramVariablesDialog dialog = new AddEditDiagramVariablesDialog(
					new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL,
					variable);
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
					if (variable.getValue() != null)
						item.setText(2, variable.getValue());
					else
						item.setText(2, "");
					if (variable.isBePresentIntoConstructor())
						item.setText(3, "T");
					else
						item.setText(3, "F");
						  		
					modVariables = true;
				}

			}
			
			/*else
				this.modVariables = false;*/
			
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

	public void validatePage() {
		// TODO
		if (!(guardText.getText().endsWith("gora"))
				|| (!(actionText.getText().endsWith("gora")))) {
			// setErrorMessage("The file must be finish with *.gora extension");
			// setPageComplete(false);
			return;
		}
		if (!(eventText.getText().endsWith("event"))) {
			// setErrorMessage("The file must be finish with *.event extension");
			// setPageComplete(false);
			return;
		}
		// setErrorMessage(null);
		// setPageComplete(true);

	}

	private int getIndex(String sel) {
		for (int i = 0; i < parent.getModelElements().size(); i++) {
			if (parent.getModelElements().get(i) instanceof DSCState
					&& ((DSCState) parent.getModelElements().get(i)).getName()
							.equals(sel))
				return i;
		}
		return -1;
	}
}
