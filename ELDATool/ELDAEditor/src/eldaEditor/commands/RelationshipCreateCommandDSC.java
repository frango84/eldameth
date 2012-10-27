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

package eldaEditor.commands;

import genericUMLDiagramEditor.commands.CreateFileName;
import genericUMLDiagramEditor.commands.RelationshipCreateCommand;
import genericUMLDiagramModel.Classifier;
import genericUMLDiagramModel.ConnectionLabel;
import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericUMLDiagramModelFactory;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.TextLabel;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;
import eldaEditor.dialogs.TransitionPropertiesInputDialog;

/**
 * Classe che permette la creazione di una relazione
 * @author samuele
 *
 */
public class RelationshipCreateCommandDSC extends RelationshipCreateCommand {
/**
 * Costruttore
 * @param conn		Istanza della relazione che si sta creando
 * @param source	Riferimento all’elemento sorgente della relazione
 */
public RelationshipCreateCommandDSC(Relationship conn, ModelElement source) {
	super(conn, source);
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#canExecute()
 */
public boolean canExecute() {

	if(connection instanceof Transition)
	{
		// non permetto che le transizioni in uscita da un history siano agganciate ad altre history
		if ((source instanceof DeepHistory || source instanceof ShallowHistory) &&
				(target instanceof DeepHistory || target instanceof ShallowHistory))
			return false;
		
		//se ci sono già transizioni in uscita non ne accetto
		if (source instanceof DeepHistory || source instanceof ShallowHistory) 
			{
			if (source.getSourceRelationships().size()==1)
				return false;
			
			}
		
		
		//Se è una transizione uscente da un'history
		if ((source instanceof DeepHistory) && target!=null )
		{
			if(((VisualElement)source).getContainer()==target) 
				return false;
			
			if (!EcoreUtil.isAncestor( ((VisualElement)source).getContainer(),target))
				return false;
			
		}
		//se è una transizione uscente da una history
		if(source instanceof ShallowHistory  && target instanceof VisualElement)
		{
			//se il target e source non hanno lo stesso container
			if(((VisualElement)source).getContainer()!=((VisualElement)target).getContainer()) 
				return false;
		}
//
//		
//		// Un top state non può essere target di una transition
//		if(target instanceof DSCState && parent!=null && isTopMost(target) && ((VisualElement)target).getContainer()==null)
//			return false;
		
		if(target instanceof Note || target instanceof Relationship || target instanceof TextLabel 
		   || target instanceof VerticalLineSeparator || target instanceof HorizontalLineSeparator)
			return false;

		if(!(target instanceof DSCState) && source.equals(target))
			return false;
	}
	if(connection instanceof AnchorNoteToItem)	
	{
		if(target instanceof TextLabel || target instanceof AnchorNoteToItem)
			return false;
		if(source instanceof Note && target instanceof Note)	
			return false;
		if(!(source instanceof Note)&&!(target instanceof Note))	
			return false;
		
		// Verifico se sto creando una doppia indicazione tra la stessa coppia di elementi
		if(target!=null) {
			List con1 = target.getSourceRelationships();
			List con2 = target.getTargetRelationships();
			for(int i=0; i<con1.size(); i++) {
				if(con1.get(i) instanceof AnchorNoteToItem) {
					AnchorNoteToItem item = (AnchorNoteToItem) con1.get(i);
					if(item.getTarget().equals(source))
						return false;
				}
			}
			for(int i=0; i<con2.size(); i++) {
				if(con2.get(i) instanceof AnchorNoteToItem) {
					AnchorNoteToItem item = (AnchorNoteToItem) con2.get(i);
					if(item.getSource().equals(source))
						return false;
				}
			}
		}

	}
	return true;
}

/* (non-Javadoc)
 * @see org.eclipse.gef.commands.Command#execute()
 */
public void execute() {
	
	TransitionPropertiesInputDialog dialog;
	
//	EcoreUtil.isAncestor( ((VisualElement)source).getContainer(),target);
	// Cerco il nome del file
	String strFile = CreateFileName.getName();

	// Setto l'identificativo del nuovo oggetto al più alto identificativo
	// degli oggetti contenuti dal padre incrementato di una unità
	if (connection.getID()==null) {
		// Setto l'identificativo
		connection.setID(strFile + (parent.getMaxID()+1));
		
		// Incremento il maxID del padre
		parent.setMaxID(parent.getMaxID()+1);
	}

	if(connection instanceof Transition) {
		GenericUMLDiagramModelFactory factory = GenericUMLDiagramModelFactory.eINSTANCE;
		ConnectionLabel label = factory.createConnectionLabel();
		label.setRelationship(connection);
		
		
		
		
		// Setto l'ID della ConnectionLabel
		if (label.getID()==null) {
			// Setto l'identificativo
			label.setID(strFile + (parent.getMaxID()+1));
		
			// Incremento il maxID del padre
			parent.setMaxID(parent.getMaxID()+1);
		}
	}
	
	connection.setSource(source);
	connection.setTarget(target);
	connection.setParent(parent);
	
	if(connection instanceof Transition ){
		dialog=new TransitionPropertiesInputDialog(new Shell(),SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL,(Transition)connection);
		dialog.open();
		if(dialog.getCancelled()){
			RelationshipDeleteCommand deleteCommand=new RelationshipDeleteCommand(connection);
			if(deleteCommand.canExecute())
				deleteCommand.execute();
		}
	}
	
	// Se è un'auto-transizione setto staticamente i bendpoint
	if(source.equals(target)) {
		VisualElement visual_source = (VisualElement) source;
		int x = visual_source.getLocation().x;
		int y = visual_source.getLocation().y;

		// Trasformo in globali le coordinate dello stato a cui è
		// agganciata le autotransizioni in esame
		VisualElement container = visual_source.getContainer(); 
		while(container!=null) {
			if(container instanceof DSCState) {
				x += container.getLocation().x;
				y += container.getLocation().y;
			}
			container = container.getContainer();
		}

		Point pos = new Point(x,y);
		Dimension dim = visual_source.getSize();
		
		// Conto in che posizione compare l'autotransizione attuale
		// nella lista delle relazioni dello stato da cui dipende
		List source_list = source.getSourceRelationships();
		List target_list = source.getTargetRelationships();
		int count=1;
		for(int i=0; i<source_list.indexOf(connection); i++)
			if(target_list.contains(source_list.get(i))) count++;
		
		int scala = count/4;
		switch(count%4) {
			case 1:
				AbsoluteBendpoint bend_E_0 = new AbsoluteBendpoint(pos.x+dim.width+30+10*scala,pos.y+dim.height/2+20+10*scala);
				AbsoluteBendpoint bend_E_1 = new AbsoluteBendpoint(pos.x+dim.width+30+10*scala,pos.y+dim.height/2-20-10*scala);
				connection.getBendpoints().clear();
				connection.getBendpoints().add(0,bend_E_0);
				connection.getBendpoints().add(1,bend_E_1);
				break;
			case 2:
				AbsoluteBendpoint bend_N_0 = new AbsoluteBendpoint(pos.x+dim.width/2+20+10*scala,pos.y-30-10*scala);
				AbsoluteBendpoint bend_N_1 = new AbsoluteBendpoint(pos.x+dim.width/2-20-10*scala,pos.y-30-10*scala);
				connection.getBendpoints().clear();
				connection.getBendpoints().add(0,bend_N_0);
				connection.getBendpoints().add(1,bend_N_1);
				break;
			case 3:
				AbsoluteBendpoint bend_W_0 = new AbsoluteBendpoint(pos.x-30-10*scala,pos.y+dim.height/2-20-10*scala);
				AbsoluteBendpoint bend_W_1 = new AbsoluteBendpoint(pos.x-30-10*scala,pos.y+dim.height/2+20+10*scala);
				connection.getBendpoints().clear();
				connection.getBendpoints().add(0,bend_W_0);
				connection.getBendpoints().add(1,bend_W_1);
				break;
			case 0:
				AbsoluteBendpoint bend_S_0 = new AbsoluteBendpoint(pos.x+dim.width/2-20-10*scala,pos.y+dim.height+30+10*scala);
				AbsoluteBendpoint bend_S_1 = new AbsoluteBendpoint(pos.x+dim.width/2+20+10*scala,pos.y+dim.height+30+10*scala);
				connection.getBendpoints().clear();
				connection.getBendpoints().add(0,bend_S_0);
				connection.getBendpoints().add(1,bend_S_1);
				break;
		}
	}
}

//protected boolean isTopMost(ModelElement newElement) {
//	// Un top state non può essere source o target di una transition
//	int count = 0;
//	if(newElement.getParent()!=null && ((VisualElement)newElement).getContainer()==null) {
//		for(int i=0; i<newElement.getParent().getModelElements().size(); i++) {
//			// Controlla quanti elementi hanno getContainer==null
//			if((newElement.getParent().getModelElements().get(i) instanceof Classifier || 
//					newElement.getParent().getModelElements().get(i) instanceof Container) 
//				&& ((VisualElement)newElement.getParent().getModelElements().get(i)).getContainer()==null)
//				count++;
//		}
//	}
//	return (count==1);
//}
}
