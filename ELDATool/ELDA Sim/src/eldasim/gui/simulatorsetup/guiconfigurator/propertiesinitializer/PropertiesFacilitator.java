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

package eldasim.gui.simulatorsetup.guiconfigurator.propertiesinitializer;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesFacilitator {
	private Properties properties;
	private static PropertiesFacilitator instance;

	public static void loadProperties(String filePath) throws Exception {
		instance = new PropertiesFacilitator();
		instance.properties = new Properties();

		try {
			instance.properties.loadFromXML(new FileInputStream(filePath));
		} catch (Exception e) {
			instance = null;

			throw new Exception(e);
		}
	}

	public static String getProperty(String parameter) {
		if (instance == null)
			return "";

		String value = instance.properties.getProperty(parameter);

		return (value != null) ? value : "";
	}

	public static void addProperty(String key, String value) {
		if (instance == null)
			return;

		instance.properties.setProperty(key, value);
	}
}
