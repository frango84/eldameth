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

import java.util.Iterator;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DSCState;
import dscDiagramModel.myDataTypes.TypeVariable;
import eldaEditor.commands.HistoryCreateCommandDSC;
import eldaEditor.commands.HistoryDeleteCommandDSC;
import eldaEditor.commands.SetVariableCommandDSCState;
import eldaEditor.commands.StateSetConstraintCommand;
import genericUMLDiagramEditor.commands.ChangeDescriptionCommand;
import genericUMLDiagramEditor.commands.ChangeNameCommand;
import genericUMLDiagramEditor.commands.ChangeStereotypeCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;
import genericUMLDiagramModel.ModelElement;

/**
 * Classe per creare un finestra di dialogo, tramite la quale si possono
 * inserire le proprietà di un DSCState
 * 
 * @author samuele, F. Rango
 */
public class StatePropertiesInputDialog extends Dialog {

	/**
	 * E' il diagramma parent di tutto
	 */
	private DSCDiagram parent;
	/**
	 * Memorizza se deve essere creato uno shallow history
	 */
	private boolean shallowHystoryCreate = false;
	/**
	 * Memorizza se deve essere creato uno deep history
	 */
	private boolean deepHystoryCreate = false;

	/**
	 * Memorizza se deve essere creato uno shallow history
	 */
	private boolean oldShallowHystoryCreate = false;
	/**
	 * Memorizza se deve essere creato uno deep history
	 */
	private boolean oldDeepHystoryCreate = false;

	/** Stringa rappresentante il nome dello stato in cui si trova l'objectflow */
	private String stateName;
	/** Stringa rappresentante lo stereotipo dell'objectflow */
	private String stereotype;
	/** Stringa rappresentante la descrizione dell'objectflow */
	private String description;
	/**
	 * Istanza dell’elemento del modello che è associato alla finestra di
	 * dialogo e di cui si vogliono impostare e/o modificare le proprietà
	 */
	private DSCState state;

	// Informa se l'utente ha premuto Cancel
	private boolean cancelled = true;
	// Informa se l'utente ha modificato le transizioni interne
	private boolean modVariables = false;
	// Memorizza se l'utente vuole effettuare un resize automatico della figure
	private boolean automaticResize = false;
	private boolean initialeAutomaticResize = false;

	// Variabili globali
	private Table table;
	private TableItem item;
	private Button add, edit, remove, move_up, move_down;

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
	public StatePropertiesInputDialog(Shell parent, DSCState state,
			boolean automaticResize, DSCDiagram diagram) {
		// Let users override the default styles
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.setStateName(state.getName());
		this.setStereotype(state.getStereotype());
		this.setDescription(state.getDescription());
		this.state = state;
		this.automaticResize = automaticResize;
		this.initialeAutomaticResize = automaticResize;
		this.parent = diagram;
	}

	/**
	 * ritorna il nome dello stato in cui si trova l'objectflow
	 * 
	 * @return String stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * ritorna la descrizione dell'objectflow
	 * 
	 * @return String description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * ritorna lo stereotipo dell'objectflow
	 * 
	 * @return String description
	 */
	public String getStereotype() {
		return stereotype;
	}

	/**
	 * Imposta lo stato dell'objectflow
	 * 
	 * @param input
	 *            il nuovo stato dell'objectflow
	 */
	public void setStateName(String input) {
		this.stateName = input;
	}

	/**
	 * Imposta la descrizione dell'objectflow
	 * 
	 * @param input
	 *            la nuova descrizione
	 */
	public void setDescription(String input) {
		this.description = input;
	}

	/**
	 * Imposta lo stereotipo dell'objectflow
	 * 
	 * @param input
	 *            il nuovo stereotipo
	 */
	public void setStereotype(String input) {
		this.stereotype = input;
	}

	/**
	 * Apre la finestra di dialogo
	 */
	public void open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		// shell.setText(stateName + ": State Properties");
		createContents(shell);
		shell.setSize(400, 400);
		shell.setLocation(200, 200);
		shell.open();
		Display display = getParent().getDisplay();
		// state.getVariables().
		while (!shell.isDisposed()) {
			shell.setText(stateName + ": State Properties");
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

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 3;
		tabFolder.setLayoutData(folder_data);

		// Creo tre tab
		TabItem item1 = new TabItem(tabFolder, SWT.NONE);
		item1.setText("General");
		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("Advanced");
		TabItem item3 = new TabItem(tabFolder, SWT.NONE);
		item3.setText("Visual Properties");

		// *********************************************************
		// *********************************************************
		// *********************************************************
		// Creo il contenuto del tab General
		// *********************************************************
		// *********************************************************
		SashForm general_form = new SashForm(tabFolder, SWT.VERTICAL);
		general_form.setLayout(new GridLayout(3, true));

		// Show the message for stereotype
		Label label0 = new Label(general_form, SWT.NONE);
		label0.setText("Stereotype:");
		label0.setLayoutData(label_data);
		// Display the input box
		final Text text0 = new Text(general_form, SWT.BORDER);
		if (state.getStereotype() != null)
			text0.setText(state.getStereotype());
		text0.setLayoutData(text_data);

		// Show the message for name
		Label label1 = new Label(general_form, SWT.NONE);
		label1.setText("State Name:");
		label1.setLayoutData(label_data);
		// Display the input box
		final Text text1 = new Text(general_form, SWT.BORDER);
		if (state.getName() != null)
			text1.setText(state.getName());
		text1.setLayoutData(text_data);

		// Show the message for description
		Label label3 = new Label(general_form, SWT.NONE);
		label3.setText("Description:");
		GridData data3 = new GridData();
		data3.horizontalSpan = 3;
		label3.setLayoutData(data3);
		// Display the input box
		final Text text2 = new Text(general_form, SWT.V_SCROLL | SWT.H_SCROLL
				| SWT.BORDER);
		data3 = new GridData(GridData.FILL_BOTH);
		if (state.getDescription() != null)
			text2.setText(state.getDescription());
		data3.horizontalSpan = 3;
		data3.verticalSpan = 2;
		text2.setLayoutData(data3);

		// Aggiungo il contenuto creato al tab General; prima fisso
		// le proporzioni tra gli elementi del form
		int[] general_pesi = { 50, 65, 50, 65, 50, 500 };
		general_form.setWeights(general_pesi);
		item1.setControl(general_form);

		// *********************************************************
		// *********************************************************
		// *********************************************************
		// Creo il contenuto del tab Advanced
		// *********************************************************
		// *********************************************************

		SashForm advanced_form = new SashForm(tabFolder, SWT.VERTICAL);
		advanced_form.setLayout(new GridLayout(3, true));

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
		Iterator i = state.getVariables().iterator();
		while (i.hasNext()) {
			TypeVariable var = (TypeVariable) i.next();
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
		}

		// Inserisco un form contenente tutti i bottoni di controllo
		SashForm button_form = new SashForm(table_form, SWT.VERTICAL);

		add = new Button(button_form, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				AddEditVariablesDialog dialog = new AddEditVariablesDialog(
						new Shell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				TypeVariable variable = dialog.open();
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
					table.remove(table.getSelectionIndex() + 1);
				}
				selected = selected + 1;
				reset();
				modVariables = true;
			}
		});

		// Fisso le proporzioni tra gli elementi del form della tabella
		int[] table_pesi = { 300, 100 };
		table_form.setWeights(table_pesi);

		final Button createShallow = new Button(advanced_form, SWT.CHECK);
		createShallow.setText("Create a shallow history");
		createShallow.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {
				shallowHystoryCreate = createShallow.getSelection();

			}
		});

		final Button createDeep = new Button(advanced_form, SWT.CHECK);
		createDeep.setText("Create a deep history");
		createDeep.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				deepHystoryCreate = createDeep.getSelection();

			}
		});

		if (state.isIsSimple() == true) {
			createDeep.setEnabled(false);
			createShallow.setEnabled(false);
		} else
		// se lo stato è composto
		{
			// se
			if (state.getShallowHistory() != null) {
				createShallow.setSelection(true);
				oldShallowHystoryCreate = createShallow.getSelection();
				shallowHystoryCreate = oldShallowHystoryCreate;
			}
			if (state.getDeepHistory() != null) {
				createDeep.setSelection(true);
				oldDeepHystoryCreate = createDeep.getSelection();
				deepHystoryCreate = oldDeepHystoryCreate;
			}

		}
		item2.setControl(advanced_form);
		int[] advanced_pesi = { 20, 200, 20, 20 };// ,65,50,65,50,65};//,65,50};
		advanced_form.setWeights(advanced_pesi);

		// Creo il contenuto del tab Advanced
		SashForm visual_form = new SashForm(tabFolder, SWT.VERTICAL);
		visual_form.setLayout(new GridLayout(2, true));

		Label position = new Label(visual_form, SWT.NONE);
		position.setText("Position:");

		Label posX = new Label(visual_form, SWT.NONE);
		posX.setText("x:");
		final Spinner pos_x = new Spinner(visual_form, SWT.BORDER);

		Label posY = new Label(visual_form, SWT.NONE);
		posY.setText("y:");
		final Spinner pos_y = new Spinner(visual_form, SWT.BORDER);

		// Label spazio = new Label(advanced_form, SWT.PUSH);

		Label size = new Label(visual_form, SWT.NONE);
		size.setText("Size:");
		GridData sizeData = new GridData();
		sizeData.verticalIndent = 3;
		size.setLayoutData(sizeData);

		Label posW = new Label(visual_form, SWT.NONE);
		posW.setText("width:");
		final Spinner size_w = new Spinner(visual_form, SWT.BORDER);

		Label posH = new Label(visual_form, SWT.NONE);
		posH.setText("height:");
		final Spinner size_h = new Spinner(visual_form, SWT.BORDER);

		// Label spazio0 = new Label(advanced_form, SWT.PUSH);

		// Create the Show Info check-button and add a handler
		// so that pressing it will set input to null
		final Button check = new Button(visual_form, SWT.CHECK);
		check.setText("Automatic resize");
		check
				.setToolTipText("Check this button to resize the figure dynamically.");
		check.setSelection(automaticResize);
		check.setLayoutData(text_data);
		check.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				automaticResize = check.getSelection();
			}
		});

		// Fisso i valori massimi degli spinner
		final int MAX_SPIN = 10000;
		pos_x.setMaximum(MAX_SPIN);
		pos_y.setMaximum(MAX_SPIN);
		size_w.setMaximum(MAX_SPIN);
		size_h.setMaximum(MAX_SPIN);

		if (state != null) {
			if (state.getLocation() != null) {
				pos_x.setSelection(state.getLocation().x);
				pos_y.setSelection(state.getLocation().y);
			}
			if (state.getSize() != null) {
				size_w.setSelection(state.getSize().width);
				size_h.setSelection(state.getSize().height);
			}
		}

		// Label advanced_spazio = new Label(advanced_form, SWT.PUSH);

		// Aggiungo il contenuto creato al tab Advanced; prima fisso
		// le proporzioni tra gli elementi del form
		int[] visual_pesi = { 30, 25, 25, 25, 25, 30, 25, 25, 25, 25, 20 };
		visual_form.setWeights(visual_pesi);
		item3.setControl(visual_form);

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
				String stereotype_temp = text0.getText();
				String stateName_temp = text1.getText();
				String description_temp = text2.getText();
				int pos_x_temp = pos_x.getSelection();
				int pos_y_temp = pos_y.getSelection();
				int size_w_temp = size_w.getSelection();
				int size_h_temp = size_h.getSelection();

				// Cerco la dimensione massima del testo inserito come nome
				FontData fd = new FontData("Arial", 10, SWT.BOLD);
				Font f = new Font(null, fd);
				int length = textLength(stateName_temp, f);

				if (!stereotype_temp.equals(stereotype)
						|| !stateName_temp.equals(stateName)
						|| !description_temp.equals(description)
						|| initialeAutomaticResize != automaticResize
						|| pos_x_temp != state.getLocation().x
						|| pos_y_temp != state.getLocation().y
						|| size_w_temp != state.getSize().width
						|| size_h_temp != state.getSize().height
						|| shallowHystoryCreate != oldShallowHystoryCreate
						|| deepHystoryCreate != oldDeepHystoryCreate
						|| modVariables) {
					// se il nome è cambiato
					if (!stateName_temp.equals(stateName)) {
						Iterator i = parent.getModelElements().iterator();
						while (i.hasNext()) {
							ModelElement m = (ModelElement) i.next();
							if ((m instanceof DSCState)
									&& (stateName_temp.equals(m.getName()))) {
								MessageDialog
										.openError(
												PlatformUI
														.getWorkbench()
														.getActiveWorkbenchWindow()
														.getShell(),
												"ERROR MESSAGE",
												"Attention, state: "
														+ stateName_temp
														+ " already exists in this diagram!");
								text1.setFocus();
								text1.selectAll();
								return;
							}
						}
					}

					// se lo stereotipo è cambiato
					if (!stereotype_temp.equals(stereotype)) {
						if (stereotype_temp.equals("DHS")
								|| stereotype_temp.equals("dhs")) {
							Iterator i = parent.getModelElements().iterator();
							while (i.hasNext()) {
								ModelElement m = (ModelElement) i.next();
								if ((m instanceof DSCState)
										&& (m.getStereotype().equals("DHS") || m
												.getStereotype().equals("dhs"))) {
									MessageDialog
											.openError(PlatformUI
													.getWorkbench()
													.getActiveWorkbenchWindow()
													.getShell(),
													"ERROR MESSAGE",
													"Attention, Default History Entrance already exists in this diagram!");
									text0.setFocus();
									text0.selectAll();
									return;
								}
							}
						}
					}

					stereotype = stereotype_temp;
					stateName = stateName_temp;
					description = description_temp;

					// Creo i parametri necessari al command per la modifica dei
					// constaint sullo stato
					Point newPos = new Point(pos_x_temp, pos_y_temp);
					Dimension newSize = new Dimension(size_w_temp, size_h_temp);

					// Verifico che la stringa più lunga entri nello stato senza
					// essere tagliata;
					// se così non è allargo lo stato
					if (state.getSize().width < length)
						newSize = new Dimension(length + 20,
								state.getSize().height);

					// Creo la request
					ChangeBoundsRequest req = new ChangeBoundsRequest();
					req.setType(RequestConstants.REQ_RESIZE_CHILDREN);

					// Creo i nuoni margini
					Rectangle newBounds = new Rectangle(newPos, newSize);

					// Creo il command
					StateSetConstraintCommand stateCmd = new StateSetConstraintCommand(
							state, req, newBounds, null);

					// Creo il comando composto da eseguire
					CompoundCommand command = new CompoundCommand();
					ChangeStereotypeCommand stereoCmd = new ChangeStereotypeCommand(
							state, stereotype);
					ChangeNameCommand nameCmd = new ChangeNameCommand(state,
							stateName);
					ChangeDescriptionCommand descrCmd = new ChangeDescriptionCommand(
							state, description);
					SetVariableCommandDSCState varCmd = new SetVariableCommandDSCState(
							state, table);
					command.add(stereoCmd);
					command.add(nameCmd);
					command.add(descrCmd);
					command.add(varCmd);
					command.add(stateCmd);

					// creo il command opportuno per la gestione degli history

					// se è stato fleggato shallow history
					if (shallowHystoryCreate) {
						// ed non era stato creato prima lo creo
						if (oldShallowHystoryCreate == false) {
							HistoryCreateCommandDSC historyCreateCommandDSC = new HistoryCreateCommandDSC(
									state, "Shallow", parent);
							command.add(historyCreateCommandDSC);
						}
					}
					// se non è stato fleggato
					else {
						// e prima era stato creato
						if (state.getShallowHistory() != null
								&& oldShallowHystoryCreate == true) {
							HistoryDeleteCommandDSC com = new HistoryDeleteCommandDSC(
									state.getShallowHistory());
							command.add(com);

						}
					}
					if (deepHystoryCreate) {
						if (oldDeepHystoryCreate == false) {
							HistoryCreateCommandDSC historyCreateCommandDSC = new HistoryCreateCommandDSC(
									state, "Deep", parent);
							command.add(historyCreateCommandDSC);
						}
					} else {
						if (state.getDeepHistory() != null
								&& oldDeepHystoryCreate == true) {
							HistoryDeleteCommandDSC com = new HistoryDeleteCommandDSC(
									state.getDeepHistory());
							command.add(com);
						}
					}
					// if(automaticResize) command.add(stateCmd);

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
		text1.setFocus();
		text1.selectAll();
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

			// Setto le proprietà della variabile da modificare
			TypeVariable variable = new TypeVariable(table.getItem(
					table.getSelectionIndex()).getText(0), table.getItem(
					table.getSelectionIndex()).getText(1), table.getItem(
					table.getSelectionIndex()).getText(2));

			AddEditVariablesDialog dialog = new AddEditVariablesDialog(
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

	// private String createInfo(Transition relationship) {
	// String event_str="", guard_str="", action_str="";
	//		
	// if(relationship.getTrigger()!=null &&
	// relationship.getTrigger().getName()!=null &&
	// relationship.getTrigger().getName().trim().length()>0)
	// event_str=relationship.getTrigger().getName();
	// if(relationship.getGuard()!=null &&
	// relationship.getGuard().getName()!=null &&
	// relationship.getGuard().getName().trim().length()>0)
	// guard_str=" [" + relationship.getGuard().getName() + "]";
	// if(relationship.getEffect()!=null &&
	// relationship.getEffect().getName()!=null &&
	// relationship.getEffect().getName().trim().length()>0) {
	// action_str=" / " + relationship.getEffect().getName();
	// }
	// return event_str + guard_str + action_str;
	// }

	public boolean getAutomaticResize() {
		return automaticResize;
	}

}
