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

import genericUMLDiagramEditor.commands.PasteCommand;
import genericUMLDiagramEditor.editor.GenericDiagramEditor;

import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.Clipboard;

import dscDiagramModel.DSCState;
import dscDiagramModel.Transition;

/**
 * A command to copy an element in the diagram
 * 
 * @author samuele
 *
 */
public class PasteCommandDSC extends PasteCommand {
	private int dist = 0;
	
	public PasteCommandDSC(GenericDiagramEditor editor, Object template) {
		super(editor, template);
		
		// Calcolo la posizione di template nella lista presente nella clipboard
		List selection = (List) Clipboard.getDefault().getContents();
		int index = selection.indexOf(template);

		// Calcolo la distanza dal punto di riferimento in alto a sinistra
		dist = 20+index*10;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		super.execute();
		
		// Copio le eventuali autotransizioni di uno stato
		if(new_obj instanceof DSCState) {
			DSCState oldState = (DSCState) template;
			DSCState newState = (DSCState) new_obj;
			
			List source_list = oldState.getSourceRelationships();
			List target_list = oldState.getTargetRelationships();
			for(int i=0; i<source_list.size(); i++) {
				if(target_list.contains(source_list.get(i))) {
					// Ho trovato una autotransizione
					Transition oldConn = (Transition) source_list.get(i);
					// La copio
					Transition newConn = copySelfTransition(oldConn);
					// L'aggiungo nelle liste delle relazioni
					newState.getSourceRelationships().add(newConn);
					newState.getTargetRelationships().add(newConn);
				}
			}
			
			if(!(newDiagram.equals(oldDiagram))) {
				// Cerco il bendpoint più in alto quello più a sinistra
				int su=dist, sx=dist;
				
				List new_source_list = newState.getSourceRelationships();
				List old_target_list = newState.getTargetRelationships();
				for(int i=0; i<new_source_list.size(); i++) {
					if(old_target_list.contains(new_source_list.get(i))) {
						// Ho trovato una autotransizione
						Transition conn = (Transition) new_source_list.get(i);
						for(int j=0; j<conn.getBendpoints().size(); j++) {
							AbsoluteBendpoint bend = (AbsoluteBendpoint) conn.getBendpoints().get(j);
							if(bend.x<sx) sx = bend.x;
							if(bend.y<su) su = bend.y;
						}
					}
				}
				
				// Se il bendpoint si trova sopra o a sinistra dello stato, lo devo spostare
				Dimension gap = new Dimension();
				if(sx<dist) gap.width =dist-sx;
				if(su<dist) gap.height=dist-su;
				trasla(gap);
				
				// Aggiorno la vista
				EditPart part = (EditPart) editor.getGraphViewer().getEditPartRegistry().get(new_obj);
				part.refresh();
			}
		}
		
	}
	
	/**
	 * @param trans 	the transition to be copied
	 * @return			the copied transition
	 */
	protected Transition copySelfTransition(Transition trans) {
		// Copio la transition
		Copier copier = new Copier();
		Transition newTrans = (Transition) copier.copy(trans);
		
		// Aggiungo la transition al diagram
		newTrans.setParent(newDiagram);
		
		// Calcolo la posizione assoluta dello stato
		Point global = egoToGlobal((DSCState)trans.getSource());
		// Calcolo quanto dista questa dal punto di riferimento
		Dimension distance = global.getDifference(new Point(dist,dist));
		
		// Copio i bendpoint
		if(trans.getBendpoints()!=null) {
			newTrans.getBendpoints().clear();
			for(int i=0; i<trans.getBendpoints().size(); i++) {
				AbsoluteBendpoint oldBend = (AbsoluteBendpoint) trans.getBendpoints().get(i);
				AbsoluteBendpoint newBend;
				if(newDiagram.equals(oldDiagram))
					newBend = new AbsoluteBendpoint(oldBend.x+10,oldBend.y+10);
				else
					newBend = new AbsoluteBendpoint(oldBend.x-distance.width,oldBend.y-distance.height);
				newTrans.getBendpoints().add(i,newBend);
			}
		}

		return newTrans;
	}
	
	protected void trasla(Dimension gap) {
		DSCState state = (DSCState) new_obj;

		// Traslo le autotransizioni (ne sposto i bendpoint)
		List source_list = state.getSourceRelationships();
		List target_list = state.getTargetRelationships();
		for(int i=0; i<source_list.size(); i++) {
			if(target_list.contains(source_list.get(i))) {
				// Ho trovato una autotransizione
				Transition conn = (Transition) source_list.get(i);
				for(int j=0; j<conn.getBendpoints().size(); j++) {
					AbsoluteBendpoint bend = (AbsoluteBendpoint) conn.getBendpoints().get(j);
					bend.translate(gap);
				}
			}
		}
		
		// Traslo lo stato
		state.getLocation().translate(gap);
	}
	
	protected Point egoToGlobal(DSCState item) {
		// Trasformo in globali le coordinate dell'elemento passato
		int x = item.getLocation().x;
		int y = item.getLocation().y;
		
		DSCState container = (DSCState) item.getContainer(); 
		while(container!=null) {
			if(container instanceof DSCState) {
				x += container.getLocation().x;
				y += container.getLocation().y;
			}
			container = (DSCState) container.getContainer();
		}
		return new Point(x,y);
	}
}
