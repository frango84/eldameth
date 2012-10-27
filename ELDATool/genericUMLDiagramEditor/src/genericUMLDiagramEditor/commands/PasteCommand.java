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

package genericUMLDiagramEditor.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import genericUMLDiagramModel.Container;
import genericUMLDiagramModel.GenericDiagram;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VisualElement;

import genericUMLDiagramEditor.commands.RelationshipCreateCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

/**
 * A command to copy an element in the diagram
 * 
 * @author marguu&zaza
 *
 */
public class PasteCommand extends Command{
	
	protected GenericDiagramEditor editor;
	protected Object template;
	protected Command com;
	protected VisualElement new_obj;
	protected GenericDiagram oldDiagram, newDiagram;
	protected boolean canExecute;
	

	public PasteCommand(GenericDiagramEditor editor, Object template){
		this.editor = editor;
		this.template = template;
		
		if (template != null) {
			// Copio l'oggetto della clipboard
			Copier copier = new Copier();
			new_obj = (VisualElement) copier.copy((EObject)template);
//			copier.copyReferences();
			
			// Verifico se ho cambiato diagramma
			newDiagram = (GenericDiagram) editor.getGraphViewer().getContents().getModel();
			oldDiagram = (GenericDiagram) EcoreUtil.getRootContainer((VisualElement) template);
			
			// Memorizzo se posso incollare il nuovo elemento
			canExecute = (newDiagram.getClass().equals(oldDiagram.getClass()));
		}

	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	public boolean canExecute() {
		return canExecute;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute(){
		if (template != null) {
			// Se sto incollando un elemento nello stesso diagramma
			if(newDiagram.equals(oldDiagram)) {
				// Deseleziono il vecchio oggetto
				EditPart old_part = (EditPart) editor.getGraphViewer().getEditPartRegistry().get(template);
				old_part.setSelected(EditPart.SELECTED_NONE);
				
				// Traslo la copia di poco rispetto all'originale
				new_obj.setLocation(new_obj.getLocation().getTranslated(10,10));
				
				// Aggiungo il nuovo oggetto al diagramma
				VisualElement in = (VisualElement)template;
				if(in.getContainer()!=null)
					new_obj.setContainer(in.getContainer());
				if(in.getParent()!=null)
					new_obj.setParent(in.getParent());
			}
			// Se sto incollando un elemento in un nuovo diagramma
			else if(newDiagram.getClass().equals(oldDiagram.getClass())){
				// Inserisco il nuovo oggetto nel nuovo diagramma
				new_obj.setParent(newDiagram);
				
				// Calcolo la posizione di template nella lista presente nella clipboard
				List selection = (List) Clipboard.getDefault().getContents();
				int index = selection.indexOf(template);
				
				// Inserisco il nuovo oggetto sempre nello stesso punto
				((VisualElement)new_obj).setLocation(new Point(20+index*10,20+index*10));
			}
			
			// Setto il nuovo ID
			createAllID(new_obj);
			
			// Se sto copiando un contenitore, verifico se gli elementi
			// contenuti hanno relazioni tra di loro e le copio pure
			if(template instanceof Container) copyRelationships();
		}
	}

	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	public void redo() {
		if(com!=null) com.undo();
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		com = new ElementDeleteCommand(new_obj);
		com.execute();
	}
	
	/**
	 * Create the ID for the copied element
	 */
	protected void createID(ModelElement newObj) {
		// Setto il nuovo ID; comincio col cercare il nome del file
		String strFile = CreateFileName.getName();
		
		// Setto l'identificativo del nuovo oggetto al più alto identificativo
		// degli oggetti contenuti dal padre incrementato di una unità
		newObj.setID(strFile + (newDiagram.getMaxID()+1));
		
		// Incremento il maxID del padre
		newDiagram.setMaxID(newDiagram.getMaxID()+1);
	}
	
	/**
	 * Create the ID for the passed element and all its contained elements
	 * 
	 * @param newObj		the object to be marked
	 */
	protected void createAllID(ModelElement newObj) {
		createID(newObj);
		
		if(newObj instanceof Container) {
			List inner = innerElements((Container)newObj);
			for(int i=0; i<inner.size(); i++)
				createID((ModelElement)inner.get(i));
		}
	}
	
	/**
	 * Copy all the inner relatioships of a container
	 */
	protected void copyRelationships() {
		// Recupero l'array di tutte le relazioni contenute nel container copiato
		Map list = CrossReferencer.find(((Container) template).getVisualElements());
		Object[] array = list.values().toArray();
		

		
		// Verifico che esistano relazioni da copiare, altrimenti esco
		if(array.length<=0) return;
		
		// Creo la lista di tutte le relazioni contenute nel container copiato
		List conn = new ArrayList();
		for(int i=0; i<array.length; i++) {
			List list1 = (List) array[i];

			for(int j=0; j<list1.size(); j++) {

				List list2 = (List) list1.get(j);
				for(int k=0; k<list2.size(); k++) {
					if(list2.get(k) instanceof Relationship && !conn.contains(list2.get(k)))
						conn.add(list2.get(k));
				
			}
			}
		}
		
		// Recupero le informazioni sui container
		Container oldCont = (Container) template;
		Container newCont = (Container) new_obj;
		
		// Creo le liste degli elementi copiati
		List oldList = innerElements(oldCont);
		List newList = innerElements(newCont);
		
		for(int i=0; i<conn.size(); i++) {
			ModelElement oldSource = ((Relationship)conn.get(i)).getSource();
			ModelElement oldTarget = ((Relationship)conn.get(i)).getTarget();
			
			int srcIndex = oldList.indexOf(oldSource);
			int trgIndex = oldList.indexOf(oldTarget);

			if(srcIndex!=-1 && trgIndex!=-1) {
				ModelElement newSource = (ModelElement)newList.get(srcIndex);
				ModelElement newTarget = (ModelElement)newList.get(trgIndex);
				Relationship connection = (Relationship)conn.get(i);

				createConnection(connection, newSource, newTarget);
			}
		}
	}
	
	/**
	 * Create a copy of the passed connection in the model
	 * 
	 * @param oldConn	the connection to be copied
	 */
	protected Relationship createModelConnection(Relationship oldConn) {
		// Creo la nuova relazione (come oggetto del modello)
		EFactory factory = editor.getModelFactory();
		return (Relationship) factory.create(oldConn.eClass());
	}

	/**
	 * Create a copy of the passed connection
	 * 
	 * @param oldConn	the connection to be copied
	 * @param source	the source of the new connection
	 * @param target	the target of the new connection
	 */
	protected void createConnection(Relationship oldConn, ModelElement source, ModelElement target) {
		// Creo la nuova relazione (come oggetto del modello)
		Relationship conn = createModelConnection(oldConn);

		// Creo la nuova relazione
		RelationshipCreateCommand com = new RelationshipCreateCommand(conn, source);
		com.setTarget(target);
		com.execute();
		
		// Copio le proprietà della connessione
		copy(oldConn, conn);
	}
	
	/**
	 * Copy the properties of the copied connection
	 * 
	 * @param oldConn	the connection to be copied
	 * @param newConn	the copied connection
	 */
	protected void copy(Relationship oldConn, Relationship newConn) {
		// Copio le principali proprietà di una relazione
		newConn.setName(oldConn.getName());
		newConn.setStereotype(oldConn.getStereotype());
		newConn.setDescription(oldConn.getDescription());
		
		// Setto il GAP di quanto devo traslare i benpoint
		Dimension gap = new Dimension();
		if(newDiagram.equals(oldDiagram))
			gap = new Dimension(10,10);
		else {
			//Calcolo le coordinate glibali del source della relazione copiata
			Point global = egoToGlobal((VisualElement)oldConn.getSource());
			
			// Calcolo quanto dista il source della relazione copiata dal punto (20,20)
			gap = global.getDifference(new Point(20,20));
		}
		
		// Copio anche i bendpoints
		for(int i=0; i<oldConn.getBendpoints().size(); i++) {
			AbsoluteBendpoint oldBend = (AbsoluteBendpoint) oldConn.getBendpoints().get(i);
			AbsoluteBendpoint newBend;
			newBend = new AbsoluteBendpoint(oldBend.getTranslated(gap));
			newConn.getBendpoints().add(i,newBend);
		}
	}
	
	/**
	 * Calculate the list of all the inner elements in the passed container
	 * 
	 * @param container		the container to be studied
	 * @return				the calculated list
	 */
	protected List innerElements(Container container) {
		List output = new ArrayList();
		
		List list = container.getVisualElements();
		for(int i=0; i<list.size(); i++) {
			output.add(list.get(i));
			
			if(list.get(i) instanceof Container) {
				Container inner = (Container)list.get(i);
				output.addAll(innerElements(inner));
			}
		}
		
		return output;
	}
	
	/**
	 * Return the global coordinate of the passed element.
	 */
	protected Point egoToGlobal(VisualElement item) {
		// Trasformo in globali le coordinate dell'elemento passato
		int x = item.getLocation().x;
		int y = item.getLocation().y;
		
		Container container = (Container) item.getContainer(); 
		while(container!=null) {
			if(container instanceof Container) {
				x += container.getLocation().x;
				y += container.getLocation().y;
			}
			container = (Container) container.getContainer();
		}
		return new Point(x,y);
	}
}
