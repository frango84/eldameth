/*****************************************************************
Multi-Coordination for JADE agents
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

package elda_multicoordination.starter;

import jade.content.lang.sl.SLCodec;
import jade.core.*;
import jade.wrapper.*;

/**
 * Starter for agents that use the Multi-Coordination of ELDA.
 * This class allows to start JADE with the specified agents and
 * all the services needed for the Multi-Coordination.
 * See the parameters to pass to the "start" method of this class
 * to start correctly the agents.
 * 
 * @author G. Fortino, F. Rango
 */
public class Starter {

	private static final String tucsonTitle = "TUCSON_PROCESS";

	/**
	 * Starter method.
	 * @param mainContainer If this parameter is true, a main container is started, otherwise a normal container (not main) is started
	 * @param mainHost The host name where the main container to register with is running
	 * @param mainPort The port number where the main container to register with is running
	 * @param gui If this parameter is true, the RMA (Remote Monitoring Agent) GUI of JADE is launched
	 * @param tucson If this parameter is true, the TuCSoN service (used for tuple-based coordination) is launched
	 * @param agentsNames The names of the agents to start
	 * @param agentsClasses The fully qualified names of the Java classes implementing the agents to start (respecting the order of the "agentsNames")
	 * @param agentsArguments The arguments to pass to the agents to start (respecting the order of the "agentsNames"): for each agent you can specify an array of Object to pass to this agent
	 */
	public static void start(boolean mainContainer, String mainHost, String mainPort,
			boolean gui, boolean tucson, String [] agentsNames, String [] agentsClasses,
			java.util.List<Object[]> agentsArguments) {
		
		//check for errors in parameters
		if((agentsNames != null && agentsClasses == null) ||
				(agentsNames == null && agentsClasses != null)){
			throw new RuntimeException("ERROR: \"agentsNames\" and \"agentsClasses\" parameters must be both null or not null!");
		}
		else if(agentsNames != null && agentsClasses != null && agentsArguments != null){
			if((agentsNames.length != agentsClasses.length) ||
					(agentsNames.length != agentsArguments.size()) ||
					(agentsClasses.length != agentsArguments.size())){
				throw new RuntimeException("ERROR: \"agentsNames\", \"agentsClasses\" and \"agentsArguments\" parameters must have same dimension!");
			}
		}
		else if(mainContainer == false && (mainHost == null || mainPort == null)){
			throw new RuntimeException("ERROR: If you want to start a normal container (not main), \"mainHost\" and \"mainPort\" parameters must be not null!");
		}
		
		try {
			if(tucson == true){
				//start TuCSoN (with the actual CLASSPATH, that contains the TuCSoN classes)
				java.lang.Runtime.getRuntime().exec("cmd /c start \"" + tucsonTitle + "\" java -classpath \"" + System.getProperty("java.class.path") + "\" alice.tucson.runtime.Node");

				//close TuCSoN when terminate the JADE execution
				Thread closeTucson = new Thread() {
					public void run() {
						try {
							java.lang.Runtime.getRuntime().exec("CMD /C TASKKILL /FI \"WINDOWTITLE eq " + tucsonTitle + "\"");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};
				java.lang.Runtime.getRuntime().addShutdownHook(closeTucson);
			}
			
			//settiamo questa property per indicare che la codifica dei messaggi
			//deve conservare i tipi primitivi di Java (es.: int), altrimenti i
			//numeri vengono visti tutti come long e ci possono essere problemi
			//(es.: quando richiediamo la creazione di un agente all'AMS e vogliamo
			//passargli un parametro di tipo Integer)
			System.setProperty(SLCodec.PRESERVE_JAVA_TYPES, "true");

			jade.core.Runtime rt = jade.core.Runtime.instance();
			ContainerController cc = null;
			if(mainContainer == true){
				//create the main container
				Profile p1 = new ProfileImpl(true);
				p1.setParameter(Profile.DETECT_MAIN, "false");
				p1.setParameter(Profile.SERVICES, Profile.DEFAULT_SERVICES + ";jade.core.messaging.TopicManagementService"); //for Publish/Subscribe service
				cc = rt.createMainContainer(p1);
			}
			else{
				//create a normal container
				Profile p2 = new ProfileImpl(false);
				p2.setParameter(Profile.MAIN_HOST, mainHost);
				p2.setParameter(Profile.MAIN_PORT, mainPort);
				p2.setParameter(Profile.SERVICES, Profile.DEFAULT_SERVICES + ";jade.core.messaging.TopicManagementService"); //for Publish/Subscribe service
				cc = rt.createAgentContainer(p2);
			}
			
			if(gui == true){
				//start RMA agent
				AgentController ac = cc.createNewAgent("RMA", "jade.tools.rma.rma", null);
				ac.start();
			}

			//start the specified agents
			if(agentsNames != null && agentsClasses != null){
				for(int i = 0; i < agentsNames.length; i++){
					Object [] args = null;
					if(agentsArguments != null){
						args = agentsArguments.get(i);	
					}
					AgentController ac = cc.createNewAgent(agentsNames[i], agentsClasses[i], args);
					ac.start();
				}	
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}