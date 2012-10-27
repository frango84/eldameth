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

package eldaEditor.editparts;

import genericUMLDiagramEditor.editParts.ConnLabelEditPart;
import dscDiagramModel.Transition;

/**
 * @author samuele
 *
 */
public class ConnLabelEditPartDSC extends ConnLabelEditPart {
	public String createText() {
		// Ricavo le informazioni su stereotipo e info
		String stereotype = getCastedModel().getRelationship().getStereotype();
		String info = createInfo();
		// Costruisco la label da stampare
		StringBuffer text = new StringBuffer();
		if(stereotype!=null && stereotype.trim().length()>0)
			text.append("«" + stereotype + "»");
		if((stereotype!=null && stereotype.trim().length()>0) 
				&& (info!=null && info.trim().length()>0))
			text.append("\n");
		if(info!=null && info.trim().length()>0)
			text.append(info);

		return text.toString();
	}

	private String createInfo() {
		// Ricavo le informazioni sulla Transition
		Transition transition = (Transition) getCastedModel().getRelationship();

		StringBuffer result= new StringBuffer("");
		if (transition.isShowTransitionID())
			result.append(transition.getTransitionID());
		if (transition.isShowTransitionID() && transition.isShowProperties())
			result.append(": ");
		if (transition.isShowProperties()){
			// Costruisco la label da stampare		
			String event_str="", guard_str="", action_str="";
			if(transition.getEventID()!=null && transition.getEventID().trim().length()>0)
				event_str=transition.getEventID()+ " ";
			if(transition.getGuardID()!=null && transition.getGuardID().trim().length()>0)
				guard_str="[" + transition.getGuardID()+ "] ";
			if(transition.getActionID()!=null && transition.getActionID().trim().length()>0) 
				action_str="/ " + transition.getActionID();
			result.append(event_str+ guard_str+action_str);
		}
		return result.toString();

	}
}
