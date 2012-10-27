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
import java.sql.SQLException;

public class PerformanceParamsTracing {
	private static DBBridge dbBridge;

	protected static void execute(String query) throws SQLException {
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
