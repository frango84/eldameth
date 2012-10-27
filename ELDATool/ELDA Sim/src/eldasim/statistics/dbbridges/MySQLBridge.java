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

package eldasim.statistics.dbbridges;

import java.sql.DriverManager;
import java.sql.SQLException;

import eldasim.statistics.DBBridge;

public class MySQLBridge extends DBBridge {
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	public MySQLBridge(String dbPath) {
		super(dbPath);
	}

	@Override
	protected void createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql:" + dbPath + "?user=" + USER + "&password=" + PASSWORD);
	}
}
