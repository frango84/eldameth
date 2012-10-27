/*****************************************************************
"DSCStarBehaviour" is a work based on the library "HSMBehaviour"
(authors: G. Caire, R. Delucchi, M. Griss, R. Kessler, B. Remick).
Changed files: "HSMBehaviour.java", "HSMEvent.java", "HSMPerformativeTransition.java",
"HSMTemplateTransition.java", "HSMTransition.java".
Last change date: 18/06/2010
Copyright (C) 2010 G. Fortino, F. Rango

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

package jade.core.behaviours;

/**
 * DSCStarVariablesAndParameters: class that specifies variables and parameters
 * of the procedure represented by a composite state
 * 
 * @author G. Fortino, F. Rango
 */
public class DSCStarVariablesAndParameters implements Cloneable, java.io.Serializable {
	
	//Questa classe permette di specificare le variabili ed i parametri
	//della procedura rappresentata da uno stato composto:
	//le variabili ed i parametri devono essere specificati in una
	//classe che estende "DSCStarVariablesAndParameters".
	
	//Questa classe implementa "Serializable" perchè serve per la migrazione dell'agente.
	
	public DSCStarVariablesAndParameters(){}
	
	public Object clone() { //utilizzato per clonare questo oggetto, solo per gli stati composti diversi dalla root
		
		//NOTA:
		//le sottoclassi non devono ridefinire questo metodo,
		//anche se contengono delle variabili di tipo oggetto (che, in generale,
		//non vengono clonate automaticamente, come accade invece per i tipi di base),
		//perchè l'oggetto "DSCStarVariablesAndParameters" viene clonato quando le sue variabili
		//sono settate ai valori iniziali ed, in particolare, le variabili di tipo oggetto
		//sono settate a NULL (l'importante è che, nelle sottoclassi, le variabili
		//di tipo oggetto non vengono inizializzate a un valore diverso da NULL)
		
		DSCStarVariablesAndParameters result;
		try {
			result = (DSCStarVariablesAndParameters) super.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

}
