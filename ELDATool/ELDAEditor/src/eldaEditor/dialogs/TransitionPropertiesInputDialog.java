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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import triggerModel.Trigger;
import triggerModel.TriggerList;
import actionGuardModel.AnElement;
import actionGuardModel.impl.AnElementListImpl;
import dscDiagramModel.DSCDiagram;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import eldaEditor.codegen.elda.CodeGenerator;
import eldaEditor.commands.ChangeTransitionPropertiesCommand;
import eldaEditor.dialogs.events.EventClassesDefinitionDialog;
import eldaEditor.dialogs.gora.ActionGuardDefinitionDialog;
import eldaEditor.util.OpenActionEditor;
import eldaEditor.util.OpenEventEditor;
import eldaEditor.wizards.ExtensionInterface;
import genericUMLDiagramEditor.commands.ChangeShowLabelCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

/**
 * Classe che gestisce le transizioni
 * 
 * @author S. Mascillaro, F. Rango
 */
public class TransitionPropertiesInputDialog extends Dialog implements
		ExtensionInterface {

	Button ok;

	// i nomi dei file da utilizzare
	private String eventFile = "";
	private String guardFile = "";
	private String actionFile = "";
	private String funcFile = "";

	// I valori delle proprietà della transizione
	private String eventName = "";
	private String guardName = "";
	private String actionName = "";
	private String transitionName = new String("");

	// Un'istanza della transizione
	private Transition connection = null;

	// parent del diagramma
	private DSCDiagram parent = null;

	// Informazioni sulle scelte dell'utente
	private boolean cancelled = true; // ha premuto Cancel
	private boolean show_properties; // vuole vedere le info della transizione
	private boolean show_identifier;
	private boolean isTriggeredByEvent;

	/**
	 * Sono le liste associate ai modelli
	 */
	private List eventList;
	private List guardList;
	private List actionList;

	/**
	 * Sono le variabili che puntano ai combo
	 */
	private Combo eventCombo;
	private Combo exceptionCombo;
	private Combo guardCombo;
	private Combo actionCombo;

	/**
	 * Sono le variabili che puntano alle risorse
	 */
	private Resource eventResource;
	private Resource guardResource;
	private Resource actionResource;

	private Button radioEvent;
	private Button radioException;
	private Button editEventButton;

	/**
	 * 
	 */
	TabItem triggerTab = null;

	/**
	 * Il file attualmente attivo
	 */
	private final IFile dscFile = ((FileEditorInput) PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
			.getEditorInput()).getFile();

	/**
	 * Il progetto che contiene il file attivo
	 */
	private final IProject project = dscFile.getProject();

	public TransitionPropertiesInputDialog(Shell parent, int style,
			Transition connection) {

		// Let users override the default styles
		super(parent, style);

		this.connection = connection;
		this.parent = (DSCDiagram) connection.getParent();
		this.show_properties = connection.getConnectionLabel().isIsVisible();

		// inizializzo gli Id della transizione
		this.eventName = connection.getEventID();
		this.guardName = connection.getGuardID();
		this.actionName = connection.getActionID();

		initializeFileNames();
		setText("Transition Properties");
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public String open() {
		// Create the dialog window
		final Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		// inizializzo il percorso dei file che indicano E G e A
		createContents(shell);
		shell.setSize(400, 230);
		shell.setLocation(250, 250);
		shell.open();
		Display display = getParent().getDisplay();

		// finchè la finestra non è stata chiusa
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				// per le transizioni striggerate da eventi
				if (!triggerTab.isDisposed()) {
					if (radioEvent.getSelection())
						if (eventCombo.getText().trim().equals(""))
							ok.setEnabled(false);
						else
							ok.setEnabled(true);
					else if (exceptionCombo.getText().trim().equals(""))
						ok.setEnabled(false);
					else
						ok.setEnabled(true);
				}

				// se è una transition triggerata da evento
				if (radioEvent.getSelection()) {
					eventCombo.setEnabled(true);
					editEventButton.setEnabled(true);
					exceptionCombo.setEnabled(false);
				} else {
					eventCombo.setEnabled(false);
					editEventButton.setEnabled(false);
					exceptionCombo.setEnabled(true);
				}
				display.sleep();
			}
		}
		// Return the entered value, or null
		return null;
	}

	/**
	 * Inizializzo le variabili che memorizzano il nome dei file
	 */
	private void initializeFileNames() {

		// recupero il diagramma e assegno i file
		this.eventFile = parent.getEventFile();
		this.guardFile = parent.getGuardFile();
		this.actionFile = parent.getActionFile();
		this.funcFile = parent.getFunctionFile();
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell
	 *            the dialog window
	 */

	private void createContents(final Shell shell) {

		shell.setLayout(new GridLayout(2, true));

		// Setto lo stile per la label
		GridData label_data = new GridData();
		label_data.horizontalSpan = 2;

		// Creo il dialog a tab
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);

		GridData folder_data = new GridData(GridData.FILL_BOTH);
		folder_data.verticalSpan = 2;
		folder_data.horizontalSpan = 3;
		tabFolder.setLayoutData(folder_data);

		// Creo tre tabItem
		triggerTab = new TabItem(tabFolder, SWT.NONE);
		triggerTab.setText("Trigger");
		TabItem guardTab = new TabItem(tabFolder, SWT.NONE);
		guardTab.setText("Guard");
		TabItem actionTab = new TabItem(tabFolder, SWT.NONE);
		actionTab.setText("Action");

		GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
		labelData.horizontalSpan = 1;

		// **********************************************************
		// **********************************************************
		// Trigger Tab
		// **********************************************************
		// **********************************************************
		SashForm triggerSash = new SashForm(tabFolder, SWT.HORIZONTAL);

		SashForm eventSash = new SashForm(triggerSash, SWT.NONE);

		Group eventGroup = new Group(eventSash, SWT.VERTICAL | SWT.FILL);
		eventGroup.setText("Event");
		eventGroup.setLayout(new GridLayout(1, true));
		eventGroup.setLayoutData(labelData);

		SashForm exceptionSash = new SashForm(triggerSash, SWT.NONE);

		Group exceptionGroup = new Group(exceptionSash, SWT.VERTICAL | SWT.FILL);
		exceptionGroup.setText("Exception");
		exceptionGroup.setLayout(new GridLayout(1, true));
		exceptionGroup.setLayoutData(labelData);

		isTriggeredByEvent = connection.isTriggeredByEvent();

		radioEvent = new Button(eventGroup, SWT.RADIO);
		radioEvent.setText("Event");
		radioEvent.setSelection(isTriggeredByEvent);

		radioException = new Button(exceptionGroup, SWT.RADIO);
		radioException.setText("Exception");
		radioException.setSelection(!isTriggeredByEvent);

		radioEvent.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (radioEvent.getSelection()) {
					radioException.setSelection(false);
					isTriggeredByEvent = true;
				} else {
					radioException.setSelection(true);
				}
			}
		});

		radioException.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (radioException.getSelection()) {
					radioEvent.setSelection(false);
					isTriggeredByEvent = false;
				} else {
					radioEvent.setSelection(true);
				}
			}
		});

		// spazio vuoto
		Label empty1 = new Label(eventGroup, SWT.NONE);

		//
		SashForm aa = new SashForm(eventGroup, SWT.HORIZONTAL | SWT.FILL);
		aa.setLayout(new GridLayout(1, true));
		aa.setLayoutData(labelData);

		eventCombo = new Combo(aa, SWT.READ_ONLY | SWT.DROP_DOWN | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);
		List eventUD = fillEventCombo(eventFile);
		eventCombo.addSelectionListener(new mySelectionListener(eventCombo,
				eventUD, eventFile));
		editEventButton = new Button(aa, SWT.PUSH | SWT.FILL);
		editEventButton.setText("Edit");
		editEventButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//URI.createFileURI(eventFile);
				// Creo il resourceSet
				ResourceSetImpl eventResourceSet = new ResourceSetImpl();

				// recupero il file associato alla baseDati
				IFile file = project.getFile(eventFile);

				// Get the URI of the model file.
				URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
						.toString());

				try {
					eventResourceSet.getResource(fileURI, true);
				}
				// gestisco l'eccezione se la risorsa non esiste
				catch (RuntimeException e) {
					WorkspaceModifyOperation operation = new OpenEventEditor(
							parent).createResourceOperationForEvent(eventFile);
					// createResourceForEventOperation(eventFile);
					try {
						operation.run(null);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					eventResourceSet.getResource(fileURI, true);
				}

				// Prelevo la risorsa
				eventResource = (Resource) eventResourceSet.getResources().get(
						0);

				EventClassesDefinitionDialog eventDialog = new EventClassesDefinitionDialog(
						shell, eventResource);
				eventDialog.open();
				reloadTriggers();
			}
		});

		int[] weights = { 75, 25 };
		aa.setWeights(weights);

		Label empty2 = new Label(exceptionGroup, SWT.NONE);
		SashForm bb = new SashForm(exceptionGroup, SWT.HORIZONTAL | SWT.FILL);
		bb.setLayout(new GridLayout(1, true));
		bb.setLayoutData(labelData);

		exceptionCombo = new Combo(bb, SWT.READ_ONLY | SWT.DROP_DOWN
				| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);
		List exceptionList = fillExceptionCombo(exceptionCombo);
		exceptionCombo.addSelectionListener(new mySelectionListener(
				exceptionCombo, exceptionList, null));

		// **********************************************************
		// **********************************************************
		// Guard Tab
		// **********************************************************
		// **********************************************************

		SashForm guardSash = new SashForm(tabFolder, SWT.HORIZONTAL);

		Group guardGroup = new Group(guardSash, SWT.HORIZONTAL | SWT.FILL);
		guardGroup.setText("Guard");
		guardGroup.setLayout(new GridLayout(1, true));
		guardGroup.setLayoutData(labelData);

		SashForm elementSash = new SashForm(guardGroup, SWT.HORIZONTAL
				| SWT.FILL);
		elementSash.setLayout(new GridLayout(1, true));
		elementSash.setLayoutData(labelData);

		SashForm nullSash = new SashForm(guardSash, SWT.HORIZONTAL);

		guardCombo = new Combo(elementSash, SWT.READ_ONLY | SWT.DROP_DOWN
				| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);

		List guardUD = fillGuardCombo(guardFile);
		guardCombo.addSelectionListener(new mySelectionListener(guardCombo,
				guardUD, guardFile));

		Button editGuardButton = new Button(elementSash, SWT.PUSH);
		editGuardButton.setText("Edit");
		editGuardButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// recupero il progetto e cerco la posizione della base dati
				ResourceSet guardResourceSet = new ResourceSetImpl();

				// recupero il file associato alla baseDati
				IFile file = project.getFile(guardFile);

				// Get the URI of the model file.
				URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
						.toString());
				// se la risorsa non esiste
				try {
					guardResourceSet.getResource(fileURI, true);
				}
				// gestisco l'eccezione
				catch (RuntimeException e) {
					WorkspaceModifyOperation operation = new OpenActionEditor(
							parent).createResourceOperationForGora(guardFile);
					try {
						operation.run(null);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					guardResourceSet.getResource(fileURI, true);
				}
				guardResource = (Resource) guardResourceSet.getResources().get(
						0);

				IFile funcIFile = project.getFile(funcFile);

				ActionGuardDefinitionDialog dialog = new ActionGuardDefinitionDialog(
						shell, guardResource, funcIFile, false, parent);
				dialog.open();
				reloadGuards();
			}
		});

		elementSash.setWeights(weights);

		// **********************************************************
		// **********************************************************
		// Action Tab
		// **********************************************************
		// **********************************************************
		SashForm actionSash = new SashForm(tabFolder, SWT.HORIZONTAL | SWT.FILL);

		Group actionGroup = new Group(actionSash, SWT.HORIZONTAL | SWT.FILL);
		actionGroup.setText("Action");
		actionGroup.setLayout(new GridLayout(1, true));
		actionGroup.setLayoutData(labelData);

		SashForm elementSash2 = new SashForm(actionGroup, SWT.HORIZONTAL
				| SWT.FILL);
		elementSash2.setLayout(new GridLayout(1, true));
		elementSash2.setLayoutData(labelData);

		SashForm nullSash2 = new SashForm(actionSash, SWT.HORIZONTAL);

		actionCombo = new Combo(elementSash2, SWT.READ_ONLY | SWT.DROP_DOWN
				| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FILL);
		List actionUD = fillActionCombo(actionFile);
		actionCombo.addSelectionListener(new mySelectionListener(actionCombo,
				actionUD, actionFile));
		Button editActionButton = new Button(elementSash2, SWT.PUSH | SWT.TOP
				| SWT.BEGINNING);
		editActionButton.setText("Edit");
		editActionButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// recupero il progetto e cerco la posizione della base dati
				ResourceSet actionResourceSet = new ResourceSetImpl();

				// recupero il file associato alla baseDati
				IFile file = project.getFile(actionFile);

				// Get the URI of the model file.
				URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
						.toString());
				// se la risorsa non esiste
				try {
					actionResourceSet.getResource(fileURI, true);
				}
				// gestisco l'eccezione
				catch (RuntimeException e) {
					WorkspaceModifyOperation operation = new OpenActionEditor(
							parent).createResourceOperationForGora(actionFile);
					try {
						operation.run(null);
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					actionResourceSet.getResource(fileURI, true);
				}
				actionResource = (Resource) actionResourceSet.getResources()
						.get(0);

				IFile funcIFile = project.getFile(funcFile);

				ActionGuardDefinitionDialog dialog = new ActionGuardDefinitionDialog(
						shell, actionResource, funcIFile, true, parent);
				dialog.open();
				reloadActions();
			}
		});
		elementSash2.setWeights(weights);

		actionTab.setControl(actionSash);
		triggerTab.setControl(triggerSash);
		guardTab.setControl(guardSash);

		// disabilitiamo i tab guard e trigger se la transizione parte dal
		// pallino nero iniziale o da un connettore di storia
		if ((connection instanceof Transition)
				&& ((connection.getSource() instanceof StartPoint)
						|| (connection.getSource() instanceof DeepHistory) || (connection
						.getSource() instanceof ShallowHistory))) {
			triggerTab.dispose();
			guardTab.dispose();
		}

		// **********************************************************
		// **********************************************************
		// show label check button
		// **********************************************************
		// **********************************************************

		SashForm aGroup = new SashForm(shell, SWT.HORIZONTAL);

		final Button checkIdentifier = new Button(aGroup, SWT.CHECK);
		final Text identifier = new Text(aGroup, SWT.BORDER);

		if (connection.getTransitionID() != null)
			identifier.setText(connection.getTransitionID());

		show_identifier = connection.isShowTransitionID();
		checkIdentifier.setSelection(show_identifier);
		identifier.setEnabled(show_identifier);

		checkIdentifier.setText("Show Identifier");
		checkIdentifier
				.setToolTipText("Check this button to show the identifier\n associated to this transition");
		checkIdentifier.setLayoutData(label_data);
		checkIdentifier.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				show_identifier = checkIdentifier.getSelection();
				if (show_identifier)
					identifier.setEnabled(true);
				else
					identifier.setEnabled(false);
			}
		});

		final Button checkProperties = new Button(shell, SWT.CHECK);
		checkProperties.setText("Show Properties");
		checkProperties
				.setToolTipText("Check this button to show all the inserted \n informations on the diagram");

		if (connection.getEventID() != null
				|| (connection.getSource() instanceof StartPoint)) {
			checkProperties.setSelection(show_properties);
			show_properties = checkProperties.getSelection();
		} else {
			checkProperties.setSelection(true);
			show_properties = checkProperties.getSelection();
		}
		checkProperties.setLayoutData(label_data);
		checkProperties.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				show_properties = checkProperties.getSelection();
			}
		});

		// **********************************************************
		// **********************************************************
		// Ok Cancel button
		// **********************************************************
		// **********************************************************

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value
		ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		label_data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(label_data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				// creo il command composto

				CompoundCommand command = new CompoundCommand();

				// modifico le proprietà della transizione
				ChangeTransitionPropertiesCommand changeTransCmd = new ChangeTransitionPropertiesCommand(
						connection, eventName, guardName, actionName,
						identifier.getText(), isTriggeredByEvent,
						show_identifier, show_properties);
				ChangeShowLabelCommand labelCmd = new ChangeShowLabelCommand(
						connection, show_identifier || show_properties);
				command.add(changeTransCmd);
				command.add(labelCmd);
				if (command != null
						&& ((GenericDiagramEditor) PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getActivePage()
								.getActiveEditor())
								.getAdapter(CommandStack.class) != null) {
					CommandStack cs = (CommandStack) ((GenericDiagramEditor) PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().getActiveEditor())
							.getAdapter(CommandStack.class);
					cs.execute(command);
				}

				cancelled = false;
				shell.close();
			}
		});

		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(shell, SWT.PUSH);
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
	}

	private List fillActionCombo(String actionFile) {
		List actionUD = getGoraList(actionFile);
		actionCombo.removeAll();
		actionCombo.add("");
		if (actionUD != null)
			for (int i = 0; i < actionUD.size(); i++)
				actionCombo.add(((AnElement) actionUD.get(i)).getName());
		return actionUD;
	}

	private List fillGuardCombo(String guardFile) {
		List guardUD = getGoraList(guardFile);
		guardCombo.removeAll();
		guardCombo.add("");
		if (guardUD != null)
			for (int i = 0; i < guardUD.size(); i++)
				guardCombo.add(((AnElement) guardUD.get(i)).getName());
		return guardUD;

	}

	private List fillEventCombo(String baseDati) {
		List eventUD = getEventTypeList(baseDati);

		eventCombo.removeAll();
		eventCombo.add("");
		if (eventUD != null)
			for (int i = 0; i < eventUD.size(); i++)
				eventCombo.add(((Trigger) eventUD.get(i)).getTriggerName());
		return eventUD;
	}

	private List getEventTypeList(String baseDati) {

		//URI.createFileURI(baseDati);
		// Creo il resourceSet
		ResourceSetImpl eventResourceSet = new ResourceSetImpl();

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
		eventResource = (Resource) eventResourceSet.getResources().get(0);

		// aggiungo alla lista
		List newList = new ArrayList();
		newList.addAll(((TriggerList) eventResource.getContents().get(0))
				.getTrigger());
		return newList;
	}

	private void reloadTriggers() {
		int index;
		fillEventCombo(eventFile);
		index = getIndex(connection, eventCombo, eventFile);
		eventCombo.select(index);
	}

	private void reloadGuards() {
		int index;
		fillCombo(guardCombo, guardFile);
		index = getIndex(connection, guardCombo, guardFile);
		guardCombo.select(index);
	}

	private void reloadActions() {
		int index;
		fillCombo(actionCombo, actionFile);
		index = getIndex(connection, actionCombo, actionFile);
		actionCombo.select(index);
	}

	private void fillCombo(Combo combo, String file) {
		List list = getGoraList(file);
		combo.removeAll();
		combo.add("");
		if (list != null)
			for (int i = 0; i < list.size(); i++)
				combo.add(((AnElement) list.get(i)).getName());
	}

	private List getGoraList(String baseDati) {
		//URI.createFileURI(baseDati);
		// Creo il resourceSet
		ResourceSet resourceSet = new ResourceSetImpl();

		// recupero il file associato alla baseDati
		IFile file = project.getFile(baseDati);

		// Get the URI of the model file.
		URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
				.toString());

		// se la risorsa non esiste
		try {
			resourceSet.getResource(fileURI, true);
		}
		// gestisco l'eccezione ed interrompo l'esecuzione del metodo ritornando
		// un valore nullo
		catch (RuntimeException e) {
			return null;
		}

		// Prelevo la risorsa
		List newList = new ArrayList();
		if (baseDati.endsWith(ACTION_EXTENSION)) {
			actionResource = (Resource) resourceSet.getResources().get(0);
			newList.addAll(((AnElementListImpl) actionResource.getContents()
					.get(0)).getAnElement());
		} else {
			guardResource = (Resource) resourceSet.getResources().get(0);
			newList.addAll(((AnElementListImpl) guardResource.getContents()
					.get(0)).getAnElement());
		}
		return newList;
	}

	private int getIndex(Transition connection, Combo combo, String baseDati) {
		String id = new String("");
		// inizializzo id opportunamente
		if (baseDati != null && baseDati.equals(actionFile))
			id = connection.getActionID();
		else if (baseDati != null && baseDati.equals(guardFile))
			id = connection.getGuardID();
		else
			id = connection.getEventID();

		// per tutti gli elementi del combo
		for (int i = 0; i < combo.getItemCount(); i++)
			// verifico che ne esista uno con lo stesso id e ne ritorno l'indice
			if (combo.getItem(i).equals(id))
				return i;
		// nessun elemento selezionato
		return -1;
	}

	private List fillExceptionCombo(Combo exceptionComnbo) {
		List exceptionList = new ArrayList<String>();
		// creo il javaProject
		IJavaProject jProject = JavaCore.create(project);
		try {
			// recupero il jar di actiware
			IPackageFragmentRoot jar = jProject
					.findPackageFragmentRoot(JavaCore
							.getResolvedVariablePath(JavaCore.newVariableEntry(
									CodeGenerator.frameworkPath, null, null)
									.getPath()));

			for (int i = 1; i < jar.getChildren().length; i++) {
				IPackageFragment jarFragment = (IPackageFragment) JavaCore
						.create(jar.getChildren()[i].getHandleIdentifier());
				if (jarFragment.getElementName().startsWith(
						"eldaframework.eldaevent.exception")) {
					IClassFile[] classArray = jarFragment.getClassFiles();
					for (int j = 0; j < classArray.length; j++) {
						String name = classArray[j].getElementName()
								.replaceAll(".class", "");
						exceptionComnbo.add(name);
						exceptionList.add(name);
					}
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exceptionList;
	}

	public boolean getCancelled() {
		return cancelled;
	}

	/**
	 * Crea l'operazione necessaria per la modifica del workspace qualora sia
	 * necessario creare la risorsa contenente gli eventi.
	 * 
	 * @param baseDati
	 * @return l'operazione
	 */

	private class mySelectionListener implements SelectionListener {
		protected Combo combo;
		protected List newList;
		protected String baseDati;

		public mySelectionListener(Combo combo, final List list, String baseDati) {
			this.combo = combo;
			this.newList = list;
			this.baseDati = baseDati;
			int index = getIndex(connection, combo, baseDati);
			combo.select(index);
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			int index = combo.getSelectionIndex();
			if (baseDati != null && baseDati.equals(actionFile))
				actionName = combo.getItem(index);
			else if (baseDati != null && baseDati.equals(guardFile))
				guardName = combo.getItem(index);
			else
				eventName = combo.getItem(index);
		}
	}

}
