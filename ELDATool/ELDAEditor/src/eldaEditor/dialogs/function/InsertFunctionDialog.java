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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import dscDiagramModel.myDataTypes.TypeVariable;
import eldaEditor.dialogs.gora.InsertGoraDialog;
import function.Function;

public class InsertFunctionDialog extends InsertGoraDialog {
	private List list;
	private String name = new String("");
	private boolean cancelled = true;

	/**
	 * Costruttore relativo alle azione e alle guard
	 * 
	 * @param parent
	 * @param name
	 * @param list
	 */
	public InsertFunctionDialog(Shell parent, List list) {
		super(parent, "", list, false);
		this.list = list;
		this.setText("Select function");
	}

	/**
	 * Opens the dialog and returns the input
	 * 
	 * @return String
	 */
	public String open() {

		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.pack();

		shell.setLocation(600, 350);
		shell.setSize(300, 130);
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (!cancelled)
			return name;
		else
			return null;
	}

	public void createContents(final Shell shell) {

		shell.setLayout(new GridLayout(2, true));

		GridData gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		gd.horizontalSpan = 2;

		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		GridLayout gl = new GridLayout(2, true);
		sashForm.setLayout(gl);
		sashForm.setLayoutData(gd);

		Label label = new Label(sashForm, SWT.None);
		label.setLayoutData(gd);
		label.setText("The ID will be inserted in the cursor position\n"
				+ "or if the text area is disabled, at the text end .");
		final Combo combo = new Combo(sashForm, SWT.READ_ONLY | SWT.DROP_DOWN
				| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		List newList = new ArrayList();
		newList.addAll(list);

		for (int i = 0; i < newList.size(); i++) {
			combo.add(((Function) newList.get(i)).getName());
		}
		combo.select(0);
		// inizializzo il nome
		name = combo.getItem(combo.getSelectionIndex());
		combo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				name = combo.getItem(combo.getSelectionIndex());
			}

			public void widgetSelected(SelectionEvent e) {
				name = combo.getItem(combo.getSelectionIndex());
			}
		});

		combo.setLayoutData(gd);
		Label labelVuota = new Label(sashForm, SWT.None);

		SashForm okCancelForm = new SashForm(sashForm, SWT.HORIZONTAL);
		okCancelForm.setLayout(gl);
		okCancelForm.setLayoutData(gd);
		int[] weights = { 30, 20, 10, 25 };
		sashForm.setWeights(weights);

		Button insert = new Button(okCancelForm, SWT.PUSH);
		insert.setText("Insert");
		insert.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		insert.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				name = name + "( " + getActualParameters(name) + " ) ";
				cancelled = false;
				shell.close();
			}
		});

		Button cancel = new Button(okCancelForm, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				name = null;
				shell.close();

			}
		});
	}

	private String getActualParameters(String name) {
		Iterator i = list.iterator();
		StringBuffer stringParameters = new StringBuffer();
		while (i.hasNext()) {
			Function function = (Function) i.next();

			if (function.getName().equals(name)) {
				Iterator paramIterator = function.getParameter().iterator();
				while (paramIterator.hasNext()) {
					TypeVariable variable = (TypeVariable) paramIterator.next();
					stringParameters.append(variable.getName());
					if (paramIterator.hasNext()) {
						stringParameters.append(",");
						stringParameters.append(" ");
					}
				}
			}
		}
		return stringParameters.toString();
	}
}
