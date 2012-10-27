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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author samuele
 * 
 */
public class ChooseTypeDialog extends Dialog {
	private String superClass;

	// private Text project;

	public ChooseTypeDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	public String open(Shell shell) {
		try {
			IJavaProject project = JavaCore.create(getProject());

			SelectionDialog dialog = JavaUI
					.createTypeDialog(
							shell,
							new ProgressMonitorDialog(shell),
							SearchEngine
									.createJavaSearchScope(new IJavaElement[] { project }),
							IJavaElementSearchConstants.CONSIDER_CLASSES, false);
			dialog.setTitle("Choose the variable type");

			if (dialog.open() == SelectionDialog.OK) {
				Object[] result = dialog.getResult();
				superClass = ((IType) result[0]).getFullyQualifiedName();
				return superClass;
			}
		} catch (Exception ex) {}
		return null;
	}

	private IProject getProject() {
		try {
			// recupero il workspace
			// IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();

			IFile file = ((FileEditorInput) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor().getEditorInput()).getFile();
			// String strFile = file.getFullPath().toString();

			// Elimino dal path trovato il nome del progetto
			// strFile =
			// strFile.replaceAll(file.getProject().getFullPath()+"/","");

			// ResourcesPlugin.getWorkspace().getRoot()
			IProject project = file.getProject();
			if (project.hasNature(JavaCore.NATURE_ID)) {
				return project;
			}
		} catch (Exception ex) {
		}
		return null;
	}

}
