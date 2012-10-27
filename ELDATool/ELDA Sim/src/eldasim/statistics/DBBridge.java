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

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import eldasim.setup.MASSimulation;

public abstract class DBBridge {
	protected String dbPath;
	protected Connection connection;

	protected DBBridge(String dbPath) {
		this.dbPath = dbPath;
	}

	protected abstract void createConnection() throws SQLException, ClassNotFoundException;

	public void openConnection() throws SQLException, ClassNotFoundException {
		if (connection == null || (connection != null && connection.isClosed()))
			createConnection();
	}

	public void closeConnection() throws SQLException {
		connection.close();
	}

	public void execute(String query) throws SQLException {
		connection.createStatement().execute(query);
	}

	public ResultSet executeQuery(String query) throws SQLException {
		return connection.createStatement().executeQuery(query);
	}

	public int executeUpdate(String query) throws SQLException {
		return connection.createStatement().executeUpdate(query);
	}

	public static DBBridge getNewBridge() throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return (DBBridge) MASSimulation.dbBridge.getConstructor(new Class[] { String.class })
				.newInstance(new Object[] { MASSimulation.dbPath });
	}
}
