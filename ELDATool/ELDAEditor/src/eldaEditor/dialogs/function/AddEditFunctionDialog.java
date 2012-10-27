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

package eldaEditor.dialogs.function;

/**
 * @author samuele
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import dscDiagramModel.myDataTypes.TypeDiagramVariable;
import dscDiagramModel.myDataTypes.TypeVariable;
import eldaEditor.dialogs.AddEditVariablesDialog;
import eldaEditor.dialogs.ChooseTypeDialog;
import function.Function;
import function.FunctionFactory;
import function.FunctionList;
import function.FunctionPackage;

/**
 * This class demonstrates how to create your own dialog classes. It allows users
 * to input a String
 */
/**
 * @author samuele, F. Rango
 * 
 */
public class AddEditFunctionDialog extends Dialog {

	// I valori delle proprietà della variabile
	private String typeStr = "";

	// Alcuni elementi del dialog

	/**
	 * name of function
	 */
	Text text0 = null;

	/**
	 * type of function
	 */
	Text text1 = null;

	/**
	 * Text of function body
	 */
	Text textArea = null;

	// Combo combo=null;

	Button ok;

	// Informazioni sulle scelete dell'utente
	private boolean cancelled = true; // ha premuto Cancel
	private Resource functionResource;

	private Table table;
	private TableItem item;

	private Button add, edit, remove, move_up, move_down;

	private boolean modEvent = false;

	// L'indice dell'ultimo elemento della tabella selezionato
	private int selected = -1;

	final FunctionPackage functionPackage = FunctionPackage.eINSTANCE;
	final FunctionFactory functionFactory = functionPackage
			.getFunctionFactory();
	private FunctionList functionList = functionFactory.createFunctionList();

	private boolean editMode = false;

	private Function functionEdited = null;

	/**
	 * InputDialog constructor
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public AddEditFunctionDialog(Shell parent, int style,
			Resource functionResource) {
		// Let users override the default styles
		super(parent, style);
		this.functionResource = functionResource;
		this.functionList = ((FunctionList) functionResource.getContents().get(
				0));
		setText("Add Function Definition");
	}

	public AddEditFunctionDialog(Shell parent, int style, Function function,
			Resource functionResource) {
		// Let users override the default styles
		super(parent, style);
		this.functionEdited = function;
		this.functionResource = functionResource;
		this.functionList = ((FunctionList) functionResource.getContents().get(
				0));
		this.editMode = true;
		setText("Edit Function Definition");
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public Function open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.setSize(400, 400);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				if (text0.getText().trim().equals(""))
					ok.setEnabled(false);
				else
					ok.setEnabled(true);
				display.sleep();
			}
		}
		// Return the entered value, or null
		return functionEdited;
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
		label0.setText("Function Name:");
		label0.setLayoutData(labelData);

		// Display the input box
		text0 = new Text(generalForm, SWT.BORDER);
		text0.setLayoutData(text_data);
		// se il dialog è stato aperto in modalità edit

		Label labelVuota = new Label(generalForm, SWT.NONE);
		// Show the message for name

		// Display the input box

		Label label1 = new Label(generalForm, SWT.NONE);
		label1.setText("Returned Type:");
		label1.setLayoutData(labelData);

		SashForm typeForm = new SashForm(generalForm, SWT.HORIZONTAL);
		typeForm.setLayout(new GridLayout(2, false));

		text1 = new Text(typeForm, SWT.BORDER);
		text1.setLayoutData(text_data);

		Button browse = new Button(typeForm, SWT.PUSH);
		browse.setText("Browse..");
		browse.setLayoutData(new GridData(1));
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ChooseTypeDialog chooseType = new ChooseTypeDialog(shell);
				typeStr = chooseType.open(shell);
				if (typeStr != null)
					text1.setText(typeStr);
			}
		});

		int[] typeWeights = { 80, 20 };
		typeForm.setWeights(typeWeights);

		if (editMode) {
			text0.setText(functionEdited.getName());
			if (functionEdited.getReturnedType() != null)
				text1.setText(functionEdited.getReturnedType());
			else
				text1.setText("void");
		}
		// combo=new Combo(generalForm,SWT.READ_ONLY | SWT.DROP_DOWN | SWT.MULTI
		// |
		// SWT.V_SCROLL | SWT.H_SCROLL);

		Label labelVuota1 = new Label(generalForm, SWT.NONE);
		// fillCombo();

		// se il dialog è stato aperto in modalità edit
		// if (nameTypeEvent[1]!=null)
		// {
		// combo.select(getIndex(combo,nameTypeEvent[1]));
		// }

		// combo.addSelectionListener(new SelectionListener(){

		// public void widgetDefaultSelected(SelectionEvent e) {

		// }
		// public void widgetSelected(SelectionEvent e) {

		// }});

		// Button browse= new Button(typeForm,SWT.PUSH);
		// browse.setText("Add..");
		// browse.setLayoutData(new GridData (1));
		// browse.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent event) {
		// EventsTypeDefinitionDialog eventsTypeDialog=new
		// EventsTypeDefinitionDialog(shell,eventResource);
		// String newEventType=eventsTypeDialog.open();
		// eventTypeList=((EventTypeList)eventResource.getContents().get(1));

		// fillCombo();
		// int i=getIndex(combo, newEventType);
		// if (i!=-1)
		// combo.select(i);
		// else
		// combo.select(getIndex(combo, nameTypeEvent[1]));

		// }
		// });

		// sezione con i parametri della funzione
		SashForm parameterForm = new SashForm(generalForm, SWT.VERTICAL);

		parameterForm.setLayout(new GridLayout());

		Label label5 = new Label(parameterForm, SWT.NONE);
		label5.setText("Additional Parameters");
		label5.setLayoutData(labelData);

		SashForm tableForm = new SashForm(parameterForm, SWT.HORIZONTAL);
		tableForm.setLayout(new GridLayout(2, true));

		GridData tableFormData = new GridData(GridData.FILL_HORIZONTAL);
		tableFormData.horizontalSpan = 2;

		tableForm.setLayoutData(tableFormData);

		// Tabella con i parametri della funzione
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
		if (editMode && functionEdited.getParameter() != null) {
			Iterator iterator = functionEdited.getParameter().iterator();

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
						TableItem item = new TableItem(table, SWT.NULL);
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

		int[] advancedWeights = { 10, 80 };
		parameterForm.setWeights(advancedWeights);

		item1.setControl(generalForm);

		// tab relativo al body

		TabItem item2 = new TabItem(tabFolder, SWT.NONE);
		item2.setText("Body");

		SashForm bodyForm = new SashForm(tabFolder, SWT.VERTICAL);
		bodyForm.setLayout(new GridLayout(1, true));
		bodyForm.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label6 = new Label(bodyForm, SWT.NONE);
		label6.setText("Put below the function body");
		label6.setLayoutData(labelData);

		textArea = new Text(bodyForm, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		textArea.setBackground(new Color(null, 255, 255, 255));

		textArea.setVisible(true);

		if (editMode && functionEdited.getBody() != null)
			textArea.setText(functionEdited.getBody());

		int[] bodyWeights = { 10, 80 };
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

				functionEdited = functionFactory.createFunction();

				functionEdited.setName(text0.getText());
				functionEdited.setReturnedType(text1.getText());
				functionEdited.setBody(textArea.getText());
				List parameters = getParametersList();
				functionEdited.getParameter().addAll(parameters);

				shell.close();

				cancelled = false;
			}

			private List getParametersList() {
				List variableList = new ArrayList();
				// Creo una nuova variabile interna per ogni item della tabella
				for (int i = 0; i < table.getItemCount(); i++) {

					TypeDiagramVariable variable = new TypeDiagramVariable();
					variable.setType(table.getItem(i).getText(0));
					variable.setName(table.getItem(i).getText(1));
					variableList.add(variable);
				}
				return variableList;

			}

		});

		text0.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
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

		// Setto il cursore lampeggiante
		// text1.setFocus();

		int[] pesiGeneralForm = { 4, 5, 2, 3, 5, 4, 40 };
		generalForm.setWeights(pesiGeneralForm);
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

	// public void setValueStr(String valueStr) {
	// this.valueStr = valueStr;
	// }
	// public void setNameStr(String nameStr) {
	// this.nameStr = nameStr;
	// }
	// public void setSuperClassStr(String superClassStr) {
	// this.superClassStr = superClassStr;
	// }
	/**
	 * Gets the input
	 * 
	 * @return String
	 */

	// public String getSuperClassStr() {
	// return superClassStr;
	// }
	// public String getValueStr() {
	// return valueStr;
	// }
	// public String getNameStr() {
	// return nameStr;
	// }
	// private List getAvalaibleEventType()
	// {
	// List<String> list=new ArrayList();
	// Iterator iter= eventTypeList.eContents().iterator();
	// while (iter.hasNext())
	// {
	// EventType eventType=(EventType)iter.next();
	// list.add(eventType.getEventTypeName());
	// }
	// list.add("<-- Actiware Events -->");
	// list.add(ELDAEVENT);
	// list.add(ELDAEVENTCLONE);
	// list.add(ELDAEVENTCLONENOTIFY);
	// list.add(ELDAEVENTCONSOLE);
	// list.add(ELDAEVENTCOORDINATION);
	// list.add(ELDAEVENTCREATE);
	// list.add(ELDAEVENTCREATENOTIFY);
	// list.add(ELDAEVENTDESTROY);
	// list.add(ELDAEVENTDIRECT);
	// list.add(ELDAEVENTEVTNOTIFICATION);
	// list.add(ELDAEVENTEXECUTE);
	// list.add(ELDAEVENTIN);
	// list.add(ELDAEVENTINTERNAL);
	// list.add(ELDAEVENTINVOKE);
	// list.add(ELDAEVENTMANAGEMENT);
	// list.add(ELDAEVENTMOVE);
	// list.add(ELDAEVENTMOVEREQUEST);
	// list.add(ELDAEVENTMSG);
	// list.add(ELDAEVENTMSGREQUEST);
	// list.add(ELDAEVENTOUT);
	// list.add(ELDAEVENTP_S);
	// list.add(ELDAEVENTPUBLISH);
	// list.add(ELDAEVENTQUIT);
	// list.add(ELDAEVENTRD);
	// list.add(ELDAEVENTRESUME);
	// list.add(ELDAEVENTRETURNTUPLE);
	// list.add(ELDAEVENTRPCINVOKE);
	// list.add(ELDAEVENTRPCREQUEST);
	// list.add(ELDAEVENTRPCRESULT);
	// list.add(ELDAEVENTRPCRETURN);
	// list.add(ELDAEVENTSIGNAL);
	// list.add(ELDAEVENTSUBSCRIBE);
	// list.add(ELDAEVENTSUSPEND);
	// list.add(ELDAEVENTTIMER);
	// list.add(ELDAEVENTTUPLES);
	// list.add(ELDAEVENTUNSUBSCRIBE);
	// list.add(ELDAEVENTWAIT);
	// list.add(ELDAEVENTWAKEUP);
	// return list;
	// }
	// private void fillCombo()
	// {
	// List avalaibleEventType= getAvalaibleEventType();
	// combo.removeAll();
	// for (int i=0; i<avalaibleEventType.size();i++)
	// {
	// combo.add((String)avalaibleEventType.get(i));
	// }
	// }
	private void edit_button_execute() {
		selected = table.getSelectionIndex();
		if (table.getSelectionIndex() >= 0
				&& table.getSelectionIndex() < table.getItemCount()) {
			TableItem item = table.getItem(table.getSelectionIndex());
			// Setto le proprietà della variabile da modificare
			TypeVariable variable = new TypeDiagramVariable(table.getItem(
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

	// private int getIndex(Combo combo, String id)
	// {
	// //per tutti gli elementi del combo
	// for (int i=0; i<combo.getItemCount(); i++)
	// {
	// //verifico che ne esista uno con lo stesso id e ne ritorno l'indice
	// if (combo.getItem(i).equals(id)) return i;
	// }
	//
	// //nessun elemento selezionato
	// return -1;
	// }

}
