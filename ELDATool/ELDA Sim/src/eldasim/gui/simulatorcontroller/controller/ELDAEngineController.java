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

package eldasim.gui.simulatorcontroller.controller;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import eldasim.gui.simulatorcontroller.exception.SimulatorInitException;
import eldasim.gui.util.ExceptionDialog;
import eldasim.setup.MASSimulation;

class ELDAEngineController {
	public static final int READY = 0;
	public static final int PLAY = 1;
	public static final int PAUSE = 2;
	public static final int STOP = 3;

	private Thread TucsonService;
	private Thread ELDAEngine;
	private int ELDAEngineStatus;

	private Lock pauseStatusLock;
	private Condition waitingPauseCondition;

	public void init(String projectHomePath, String simulationClassName, String configurationFileName) throws SimulatorInitException {
		Runtime.getRuntime().gc();

		pauseStatusLock = new ReentrantLock();
		waitingPauseCondition = pauseStatusLock.newCondition();

		TucsonService = new Thread(new TucsonDaemon());
		TucsonService.setDaemon(true);

		ELDAEngine = new Thread(new ELDAEngineControllerDaemon(projectHomePath, simulationClassName, configurationFileName));
		ELDAEngineStatus = READY;
	}

	public void play() {
		if (ELDAEngineStatus == READY) {
			TucsonService.start();

			synchronized (this) {
				try {
					wait(1000);
				} catch (InterruptedException e) {
				}
			}

			ELDAEngine.start();
			ELDAEngineStatus = PLAY;
		} else if (ELDAEngineStatus == PAUSE) {
			ELDAEngine.resume();
			ELDAEngineStatus = PLAY;

			pauseStatusLock.lock();
			waitingPauseCondition.signalAll();
			pauseStatusLock.unlock();
		}
	}

	public void pause() {
		if (ELDAEngineStatus == PLAY) {
			ELDAEngine.suspend();
			ELDAEngineStatus = PAUSE;
		} else if (ELDAEngineStatus == PAUSE)
			play();
	}

	public void stop() {
		if (ELDAEngineStatus == PLAY || ELDAEngineStatus == PAUSE) {
			ELDAEngine.stop();
			ELDAEngineStatus = STOP;

			if (ELDAEngineStatus == PAUSE) {
				pauseStatusLock.lock();
				waitingPauseCondition.signalAll();
				pauseStatusLock.unlock();
			}
		}
	}

	public int getCurrentStatus() {
		return ELDAEngineStatus;
	}

	public void waitingPauseCondition() {
		pauseStatusLock.lock();

		try {
			while (ELDAEngineStatus == PAUSE)
				waitingPauseCondition.await();
		} catch (InterruptedException e) {
		} finally {
			pauseStatusLock.unlock();
		}
	}

	public int getCurrentSimulationRun() {
		return MASSimulation.currentRun;
	}

	public int getSimulationRun() {
		return MASSimulation.nRun;
	}

	private class ELDAEngineControllerDaemon implements Runnable {
		private MASSimulation simulation;
		private XMLTree configuration;

		public ELDAEngineControllerDaemon(String projectHomePath, String simulationClassName, String configurationFileName)
				throws SimulatorInitException {
			simulation = SimulatorInitializer.initialize(projectHomePath + "\\bin", simulationClassName);
			configuration = SimulatorInitializer.loadConfiguration(configurationFileName);

			try {
				simulation.initSimulator(configuration);
				MASSimulation.projectHomePath = projectHomePath;
			} catch (Exception ex) {
				throw new SimulatorInitException(ex);
			}
		}

		public void run() {
			try {
				simulation.setupAndStartSimulator();
			} catch (Exception ex) {
				new ExceptionDialog().showExceptionMessage(null, ex, "ELDASim Controller: Exception");
			} finally {
				ELDAEngineStatus = STOP;
			}
		}
	}

	private class TucsonDaemon implements Runnable {
		private String extractJarFile;
		private String startTucsonDaemon;
		private String killTucsonDaemon;
		private String delJarFile;

		private String path;
		private String pluginsFileName;

		public TucsonDaemon() throws SimulatorInitException {
			try {
				Bundle plugins = Platform.getBundle("ELDASim");
				pluginsFileName = FileLocator.getBundleFile(plugins).getAbsolutePath();
				path = FileLocator.getBundleFile(plugins).getParent();

				// debug only!!
				// pluginsFileName="C:\\Program Files\\Eclipse\\plugins\\ELDASim_1.0.0.jar";

				extractJarFile = "CMD /C START /D \"" + path + "\" /B JAR xf \"" + pluginsFileName + "\" lib/Tucson.jar";
				startTucsonDaemon = "CMD /C START \"TUCSON_SERVICE_DAEMON\" /MIN JAVA -cp \"" + path
						+ "\\lib\\Tucson.jar\" alice.tucson.runtime.Node";
				killTucsonDaemon = "CMD /C TASKKILL /T /FI \"WINDOWTITLE eq TUCSON_SERVICE_DAEMON\"";
				delJarFile = "CMD /C RMDIR \"" + path + "\\lib\" /S /Q";

				Runtime.getRuntime().exec(delJarFile);
				Runtime.getRuntime().exec(extractJarFile);
			} catch (Exception ex) {
				throw new SimulatorInitException(ex);
			}
		}

		public void run() {
			try {
				Runtime.getRuntime().exec(startTucsonDaemon);

				// wait simulation ending
				while (getCurrentStatus() != STOP) {
					synchronized (this) {
						try {
							wait(250);
						} catch (InterruptedException e) {
						}
					}
				}

				Runtime.getRuntime().exec(killTucsonDaemon);

				// wait for Tucson process termination
				synchronized (this) {
					try {
						wait(500);
					} catch (InterruptedException e) {
					}
				}

				Runtime.getRuntime().exec(delJarFile);
			} catch (Exception ex) {
				new ExceptionDialog().showExceptionMessage(null, ex, "ELDASim Controller: Exception");
				stop();
			}
		}
	}
}
