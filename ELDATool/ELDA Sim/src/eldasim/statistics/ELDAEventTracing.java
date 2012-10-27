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

package eldasim.statistics;

import jaf.VirtualClock;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import eldaframework.eldaevent.ELDAEvent;
import eldasim.Msg;
import eldasim.setup.MASSimulation;

public class ELDAEventTracing {
	private static DBBridge dbBridge;

	public static void createTable(int i) throws SQLException {
		String SQLRemove = "DROP TABLE GENERATED_EVENTS_" + MASSimulation.typeSolution + "_" + i + ";";
		String SQLCreate = "CREATE TABLE GENERATED_EVENTS_" + MASSimulation.typeSolution + "_" + i + " (" + "EVENT_ID COUNTER PRIMARY KEY,"
				+ "AGENT_SERVER VARCHAR(30) NOT NULL," + "SIMULATION_TIME LONG NOT NULL," + "SOURCE VARCHAR(30) NOT NULL,"
				+ "TIPO_EVENTO VARCHAR(50) NOT NULL" + ");";

		try {
			execute(SQLRemove);
		} catch (SQLException e) {
		}

		execute(SQLCreate);

		SQLRemove = "DROP TABLE DELIVERED_EVENTS_" + MASSimulation.typeSolution + "_" + i + ";";
		SQLCreate = "CREATE TABLE DELIVERED_EVENTS_" + MASSimulation.typeSolution + "_" + i + " (" + "EVENT_ID COUNTER PRIMARY KEY,"
				+ "AGENT_SERVER VARCHAR(30) NOT NULL," + "SIMULATION_TIME LONG NOT NULL," + "SOURCE VARCHAR(30) NOT NULL,"
				+ "TARGET VARCHAR(30) NOT NULL," + "TIPO_EVENTO VARCHAR(50) NOT NULL" + ");";

		try {
			execute(SQLRemove);
		} catch (SQLException e) {
		}

		execute(SQLCreate);
	}

	public static void traceGeneratedEvent(ELDAEvent evt, String ASName) throws SQLException {
		String callingAction = new Exception().getStackTrace()[4].getMethodName();
		int point = evt.getClass().getName().lastIndexOf(".") + 1;

		String SQLInsert = "INSERT INTO GENERATED_EVENTS_" + MASSimulation.typeSolution + "_" + MASSimulation.currentRun + " "
				+ "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TIPO_EVENTO) " + "VALUES " + "(" + "'" + ASName + "'" + ","
				+ VirtualClock.getSimulationTime() + "," + "'" + evt.getSource().getUID() + "'," + "'" + callingAction + ": "
				+ evt.getClass().getName().substring(point) + "'" + ");";

		execute(SQLInsert);
	}

	public static void traceDeliveredEvent(ELDAEvent evt, String prefix, String ASName) throws SQLException {
		int point = evt.getClass().getName().lastIndexOf(".") + 1;

		String SQLInsert = "INSERT INTO DELIVERED_EVENTS_" + MASSimulation.typeSolution + "_" + MASSimulation.currentRun + " "
				+ "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TARGET, TIPO_EVENTO) " + "VALUES " + "(" + "'" + ASName + "'" + ","
				+ VirtualClock.getSimulationTime() + "," + "'" + evt.getSource().getUID() + "@" + evt.getSource().getCurrLocation() + "'," + "'"
				+ evt.getTarget().getUID() + "@" + evt.getTarget().getCurrLocation() + "'," + "'" + prefix + ": "
				+ evt.getClass().getName().substring(point) + "'" + ");";

		execute(SQLInsert);
	}

	public static void traceLowLevelEvent(Msg msg, String arg, String ASName) throws SQLException {
		ELDAEvent evt = (ELDAEvent) msg.getCargo();
		int point = evt.getClass().getName().lastIndexOf(".") + 1;

		String SQLInsert = "INSERT INTO DELIVERED_EVENTS_" + MASSimulation.typeSolution + "_" + MASSimulation.currentRun + " "
				+ "(AGENT_SERVER, SIMULATION_TIME, SOURCE, TARGET, TIPO_EVENTO) " + "VALUES " + "(" + "'" + ASName + "'" + ","
				+ VirtualClock.getSimulationTime() + "," + "'" + evt.getSource().getUID() + "@" + evt.getSource().getCurrLocation() + "'," + "'"
				+ arg + "'," + "'" + evt.getClass().getName().substring(point) + "'" + ");";

		execute(SQLInsert);
	}

	private static void execute(String query) throws SQLException {
		dbBridge.execute(query);
	}

	public static void openConnection() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SQLException, ClassNotFoundException {
		if (dbBridge == null)
			dbBridge = DBBridge.getNewBridge();

		dbBridge.openConnection();
	}

	public static void closeConnection() throws SQLException {
		dbBridge.closeConnection();
	}
}
