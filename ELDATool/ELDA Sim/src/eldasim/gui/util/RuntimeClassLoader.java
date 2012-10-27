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

package eldasim.gui.util;

import java.io.RandomAccessFile;
import java.util.StringTokenizer;

import eldasim.gui.util.exception.RuntimeClassLoaderNotInitializedException;

public class RuntimeClassLoader extends ClassLoader {
	private static RuntimeClassLoader classLoader;
	private String classPath;

	private RuntimeClassLoader(ClassLoader cl, String classPath) {
		super(cl);
		this.classPath = classPath;
	}

	public Class findClass(String className) {
		byte[] b = loadClassData(className);
		return defineClass(className, b, 0, b.length);
	}

	private byte[] loadClassData(String className) {
		try {
			RandomAccessFile file = new RandomAccessFile(JavaPathToOSPath(className), "r");
			byte data[] = new byte[(int) file.length()];

			file.readFully(data);
			file.close();

			return data;
		} catch (Exception ex) {
		}

		return null;
	}

	private String JavaPathToOSPath(String javaClassName) {
		StringBuffer str = new StringBuffer();

		StringTokenizer token = new StringTokenizer(javaClassName, ".");
		while (token.hasMoreTokens()) {
			str.append("\\" + token.nextToken());
		}

		return classPath + str.toString() + ".class";
	}

	public static void initializeClassLoader(String classPath) {
		initializeClassLoader(RuntimeClassLoader.class.getClassLoader(), classPath);
	}

	public static void initializeClassLoader(ClassLoader cl, String classPath) {
		if (classLoader == null)
			classLoader = new RuntimeClassLoader(cl, classPath);
	}

	public static RuntimeClassLoader getClassLoader() throws RuntimeClassLoaderNotInitializedException {
		if (classLoader == null)
			throw new RuntimeClassLoaderNotInitializedException();

		return classLoader;
	}
}
