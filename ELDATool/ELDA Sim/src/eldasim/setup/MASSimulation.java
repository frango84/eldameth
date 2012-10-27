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

package eldasim.setup;

import jaf.Simulation;

import java.util.Vector;

import eldasim.AgentServer;
import eldasim.agent.SimELDA;
import eldasim.gui.simulatorcontroller.controller.XMLTree;
import eldasim.network.VirtualNetwork;
import eldasim.statistics.ELDAEventTracing;
import eldasim.statistics.PerformanceParamsTracing;
import eldasim.statistics.VirtualNetworkParamsTracing;
import eldasim.statistics.dbbridges.AccessBridge;

public abstract class MASSimulation {
	public static String projectHomePath;

	public static Class dbBridge;
	public static String dbPath;

	public static boolean debug;
	public static boolean traceELDAEvent;
	public static boolean tooMuchAS;

	public static long band;
	public static double endToEndLatency;

	public static int nAS;
	public static int nRun;
	public static int currentRun;

	public static String typeSolution;

	protected static Vector<AgentServer> ASs;
	protected static Vector<SimELDA> ELDAs;
	protected static AgentServer publish_subscribe;

	public final void initSimulator(XMLTree configuration) throws Exception {
		resetParams();
		loadParams(configuration);
	}

	private void resetParams() {
		projectHomePath = "";

		dbBridge = AccessBridge.class;
		dbPath = "";

		debug = false;
		traceELDAEvent = false;
		tooMuchAS = false;

		band = -1;
		endToEndLatency = -1;

		nAS = -1;
		nRun = -1;
		currentRun = -1;

		typeSolution = "";

		ASs = new Vector<AgentServer>();
		ELDAs = new Vector<SimELDA>();
		publish_subscribe = null;

		resetSimulationParams();
	}

	protected abstract void resetSimulationParams();

	protected abstract void loadParams(XMLTree configuration) throws Exception;

	public final void setupAndStartSimulator() throws Exception {
		setupAS();
		setupVN();

		try {
			openDBConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setupAndStartCustomSimulation();

		try {
			closeDBConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected abstract void setupAS() throws Exception;

	protected void setupVN() throws Exception {
		System.out.print("Setup Network............");
		double net[][] = new double[ASs.size()][ASs.size()];
		for (int i = 0; i < net.length; i++)
			for (int j = i; j < net[0].length; j++)
				if (i == j)
					net[i][j] = 0;
				else
					net[i][j] = net[j][i] = endToEndLatency;

		VirtualNetwork.init(band, net);

		for (int i = 0; i < ASs.size(); i++)
			VirtualNetwork.map(ASs.get(i), i);

		System.out.println("DONE\n");
	}

	private void openDBConnection() throws Exception {
		PerformanceParamsTracing.openConnection();

		if (traceELDAEvent)
			ELDAEventTracing.openConnection();

		createPerformanceParamsTabs();
	}

	private void createPerformanceParamsTabs() throws Exception {
		VirtualNetworkParamsTracing.createTable();
		createSimPerformanceParamsTabs();
	}

	protected abstract void createSimPerformanceParamsTabs() throws Exception;

	protected abstract void setupAndStartCustomSimulation() throws Exception;

	private void closeDBConnection() throws Exception {
		PerformanceParamsTracing.closeConnection();

		if (traceELDAEvent)
			ELDAEventTracing.closeConnection();
	}

	protected final void startSimulator(String type) throws Exception {
		MASSimulation.typeSolution = type;

		if (traceELDAEvent)
			try {
				traceELDAEvent();
			} catch (Exception e) {
				e.printStackTrace();
			}

		// setup and register agent
		setupAgent();
		setAgentCodeDimension();
		registerAgent();

		// init and start agents and simulator
		Simulation.init();
		startAgent();
		Simulation.Scheduler(Long.MAX_VALUE);

		try {
			tracePerformanceParams();
		} catch (Exception e) {
			e.printStackTrace();
		}

		clearSimulator();
	}

	private void traceELDAEvent() throws Exception {
		ELDAEventTracing.createTable(currentRun);
	}

	protected abstract void setupAgent() throws Exception;

	protected abstract void setAgentCodeDimension() throws Exception;

	protected void registerAgent() {
		for (int i = 0; i < ELDAs.size(); i++) {
			AgentServer as = ELDAs.get(i).getAs();
			ELDAs.get(i).getELDAId().setHomeLocation(as.getName());
			as.registerAgent(ELDAs.get(i));
		}
	}

	protected abstract void startAgent() throws Exception;

	private void tracePerformanceParams() throws Exception {
		VirtualNetworkParamsTracing.writeVirtualNetworkParameters();
		traceSimPerformanceParams();
	}

	protected abstract void traceSimPerformanceParams() throws Exception;

	private void clearSimulator() {
		VirtualNetwork.clear();
		clearAS();
		ELDAs.clear();

		resetPerformanceParams();
	}

	protected abstract void clearAS();

	private void resetPerformanceParams() {
		VirtualNetworkParamsTracing.reset();
		resetSimPerformanceParams();
	}

	protected abstract void resetSimPerformanceParams();

	protected static final void setPublishSubscribe(int i) {
		publish_subscribe = ASs.get(i);
	}

	public static final AgentServer getPublish_subscribe() {
		return publish_subscribe;
	}
}
