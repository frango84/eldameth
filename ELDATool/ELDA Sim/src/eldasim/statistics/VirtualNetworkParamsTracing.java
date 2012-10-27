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

import java.sql.SQLException;

import eldasim.setup.MASSimulation;

public class VirtualNetworkParamsTracing extends PerformanceParamsTracing {
	static int msg = 0;
	static int tuple = 0;
	static int p_s = 0;

	/**
	 * Method invoked by the VirtualNetwork for some kinds of messages
	 */
	public static void incrementMsgCounter() {
		msg++;
	}

	/**
	 * Method invoked by the VirtualNetwork for some kinds of messages
	 */
	public static void incrementP_SCounter() {
		p_s++;
	}

	public static void reset() {
		msg = 0;
		tuple = 0;
		p_s = 0;
	}

	public static void createTable() throws SQLException {
		String SQLRemove = "DROP TABLE VIRTUAL_NETWORK_PARAMETERS;";
		String SQLCreate = "CREATE TABLE VIRTUAL_NETWORK_PARAMETERS "
				+ "(RUN INTEGER, TYPE VARCHAR (15), DIRECT LONG NOT NULL, TUPLE LONG NOT NULL, P_S LONG NOT NULL, PRIMARY KEY (RUN, TYPE));";

		try {
			execute(SQLRemove);
		} catch (SQLException e) {
		}

		execute(SQLCreate);
	}

	public static void writeVirtualNetworkParameters() throws SQLException {
		String SQLInsert = "INSERT INTO VIRTUAL_NETWORK_PARAMETERS (RUN, TYPE, DIRECT, TUPLE, P_S	) VALUES ( " + MASSimulation.currentRun
				+ ", " + "'" + MASSimulation.typeSolution + "', " + msg + ", " + tuple + ", " + p_s + ");";

		execute(SQLInsert);
	}
}
