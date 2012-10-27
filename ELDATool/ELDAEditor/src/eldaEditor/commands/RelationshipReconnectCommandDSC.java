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

import genericUMLDiagramEditor.commands.RelationshipReconnectCommand;
import genericUMLDiagramModel.HorizontalLineSeparator;
import genericUMLDiagramModel.ModelElement;
import genericUMLDiagramModel.Note;
import genericUMLDiagramModel.Relationship;
import genericUMLDiagramModel.VerticalLineSeparator;
import genericUMLDiagramModel.VisualElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.util.EcoreUtil;

import dscDiagramModel.AnchorNoteToItem;
import dscDiagramModel.DSCState;
import dscDiagramModel.DeepHistory;
import dscDiagramModel.ShallowHistory;
import dscDiagramModel.StartPoint;
import dscDiagramModel.Transition;

/**
 * @author samuele
 *
 */
public class RelationshipReconnectCommandDSC extends RelationshipReconnectCommand {
/** Riferimento ai vecchi endPoint della relazione */
private ModelElement oldSource;
private ModelElement oldTarget;

private List bendPoints = new ArrayList();

/**
 * Costruttore
 * @param conn			Istanza della relazione che si sta creando
 * @param newElement	Istanza del nuovo terminale della relazione
 * @param isSource		Variabile booleana che indica se l’elemento che è cambiato nella connessione è l’elemento sorgente
 */
public RelationshipReconnectCommandDSC(Relationship conn,ModelElement newElement,boolean isSource ) {
		super(conn, newElement, isSource);
		this.oldSource=conn.getSource();
		this.oldTarget=conn.getTarget();
}

public boolean canExecute() {

//	se è una transition non permetto il reconnect del source verso un history che ha gia transizioni in ingresso
	if (connection instanceof Transition )
	{
		if (isSource && (newElement instanceof ShallowHistory || newElement instanceof DeepHistory ) &&
			newElement.getSourceRelationships().size()==1)
			return false;
	}
	
	//se è una start transition non permetto il reconnect del source
	if (connection instanceof Transition && connection.getSource() instanceof StartPoint)
	{
		if (isSource)
			return false;
	}
	
	//se è una start transition non permetto il reconnect del target verso uno stato che è ad un diverso livello
	if (connection instanceof Transition && connection.getSource() instanceof StartPoint)
	{
		if(!(isSource))
		{
		if (((VisualElement)newElement).getContainer()!=((StartPoint)connection.getSource()).getContainer())
		return false;
		}
	}
	
	// se è una transition in uscita da un history permetto il reconnect del target solo se l'oggetto ha trai suoi contenitori
	//quello che contiene l'history
	if (connection instanceof Transition && (connection.getSource() instanceof DeepHistory || connection.getSource() instanceof ShallowHistory))
	{
		//Se è una transizione uscente da un'history
		if ((connection.getSource() instanceof DeepHistory || connection.getSource() instanceof ShallowHistory) )
		{
			//se provo a riconnettere il source
			if (isSource)
			{
			//impedisco il reconnect
				return false;
			}
			// se provo a riconnettere il target
			else
			{
				if(((VisualElement)connection.getSource()).getContainer()==newElement) 
					return false;
				
				if (!EcoreUtil.isAncestor( ((VisualElement)connection.getSource()).getContainer(),newElement))
					return false;
			}
			
		}
		
	}
	
	return ((isSource && checkSourceReconnection()) || (!isSource && checkTargetReconnection()));
	

		
}

public void execute() {
	super.execute();

	// Se è una autotransizione e ho meno di 2 bendpoint li creo io (memorizzo anche la vecchia lista)
	if(connection.getSource()==connection.getTarget() && connection.getBendpoints().size()<2) {
		for(int i=0; i<connection.getBendpoints().size(); i++)
			bendPoints.add(connection.getBendpoints().get(i));
		connection = setBendPoint(connection);
	}
}

public Relationship setBendPoint(Relationship conn) {
	Transition connection = (Transition) conn;
	ModelElement source = connection.getSource();
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
	
	return connection;
}

public void undo() {
	super.undo();
	
	// Se ho creato un'autotransizione elimino tutti i bendpoint e ripristino i vecchi
	connection.getBendpoints().clear();
	connection.getBendpoints().addAll(bendPoints);
	
	if(connection.getSource()==connection.getTarget())
		connection = this.setBendPoint(connection);
}

/**
 * Ritorna true, se il nuovo elemento scelto è valido come elemento sorgente della relazione */
private boolean checkSourceReconnection() {
	if(connection instanceof Transition ) {
		if(newElement instanceof HorizontalLineSeparator || newElement instanceof VerticalLineSeparator
		    || newElement instanceof Relationship ||newElement instanceof Note )
			return false;
		if(newElement instanceof StartPoint) {
			// Se ci sono altre transizioni in cui lo StartPoint è source non continuo
			for(int i=0; i<newElement.getSourceRelationships().size(); i++) {
				if(newElement.getSourceRelationships().get(i) instanceof Transition)
					return false;
			}
		}
	}
	if(connection instanceof AnchorNoteToItem ) {
		if(newElement instanceof AnchorNoteToItem)
			return false;
		if(newElement instanceof Note && oldTarget instanceof Note)
			return false;
		if(!(newElement instanceof Note) && !(oldTarget instanceof Note))
			return false;
		
		// Verifico se sto creando una doppia indicazione tra la stessa coppia di elementi
		if(newElement!=null) {
			List con1 = newElement.getSourceRelationships();
			List con2 = newElement.getTargetRelationships();
			for(int i=0; i<con1.size(); i++) {
				AnchorNoteToItem item = (AnchorNoteToItem) con1.get(i);
				if(item.getTarget().equals(oldTarget))
					return false;
			}
			for(int i=0; i<con2.size(); i++) {
				AnchorNoteToItem item = (AnchorNoteToItem) con2.get(i);
				if(item.getSource().equals(oldTarget))
					return false;
			}
		}
	}
	return true;
}

/**
 * Ritorna true, se il nuovo elemento scelto è valido come elemento destinazione della relazione */
private boolean checkTargetReconnection() {
	if(connection instanceof Transition)
	{
		if(newElement instanceof StartPoint || newElement instanceof Relationship 
			||	newElement instanceof Note || newElement instanceof HorizontalLineSeparator
			|| newElement instanceof VerticalLineSeparator)
			return false;
	}	
	if(connection instanceof AnchorNoteToItem) {
		if(newElement instanceof AnchorNoteToItem)
			return false;
		if(newElement instanceof Note && oldSource instanceof Note)
			return false;
		if(!(newElement instanceof Note) && !(oldSource instanceof Note))
			return false;
		
		// Verifico se sto creando una doppia indicazione tra la stessa coppia di elementi
		if(newElement!=null) {
			List con1 = newElement.getSourceRelationships();
			List con2 = newElement.getTargetRelationships();
			for(int i=0; i<con1.size(); i++) {
				AnchorNoteToItem item = (AnchorNoteToItem) con1.get(i);
				if(item.getTarget().equals(oldSource))
					return false;
			}
			for(int i=0; i<con2.size(); i++) {
				AnchorNoteToItem item = (AnchorNoteToItem) con2.get(i);
				if(item.getSource().equals(oldSource))
					return false;
			}
		}
	}
	return true;
}
}
