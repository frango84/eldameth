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

package eldasim.network;

import jaf.Actor;
import jaf.RandomGenerator;
import jaf.Timer;
import jaf.VirtualClock;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import eldaframework.eldaevent.coordination.direct.ELDAEventDirect;
import eldaframework.eldaevent.coordination.p_s.ELDAEventP_S;
import eldaframework.eldaevent.management.lifecycle.ELDAEventMove;
import eldasim.Msg;
import eldasim.lowlevelevent.LowLevelEvent;
import eldasim.statistics.VirtualNetworkParamsTracing;

public class VirtualNetwork {
	protected static double[][] net;
	protected static double[][] freeTime; // They are absolute!
	protected static long band;
	public static Hashtable mapping;
	protected static Hashtable logicalnodes;
	protected static boolean initialized;
	public static RandomGenerator rg = new RandomGenerator((int) System.currentTimeMillis());
	protected static final boolean debug = false;
	protected static long codeAgentDimension = 0;

	public static void init(long b, double[][] network) {
		net = network;
		freeTime = new double[net.length][net[0].length];
		band = b;
		mapping = new Hashtable();
		logicalnodes = new Hashtable();
		initialized = true;
	}

	public static void clear() {
		freeTime = new double[net.length][net[0].length];
	}

	public static void map(Actor as, int node) {
		mapping.put(as.getName(), new Integer(node));
		logicalnodes.put(as.getName(), as);
	}

	protected static void msgCounter(Msg msg) {
		Object cargo = msg.getCargo();
		boolean lowlevel = false;
		Class[] interfaces = msg.getCargo().getClass().getInterfaces();
		for (int i = 0; i < interfaces.length; i++)
			lowlevel = interfaces[i] == LowLevelEvent.class;
		if (!lowlevel)
			if (cargo instanceof ELDAEventDirect)
				VirtualNetworkParamsTracing.incrementMsgCounter();
			else if (cargo instanceof ELDAEventP_S)
				VirtualNetworkParamsTracing.incrementP_SCounter();
	}

	public static void send(Msg msg) {
		if (debug)
			System.out.println("Handled by " + new Exception().getStackTrace()[0].getMethodName() + " of Virtual Network");
		String targeturl = msg.getTargetURL();
		String sourceurl = msg.getSourceURL();
		if (targeturl == null)
			System.err.print("TARGERT URL NOT DEFINED");
		else {
			msgCounter(msg);
			int sourcenode = ((Integer) mapping.get(sourceurl)).intValue();
			int targetnode = ((Integer) mapping.get(targeturl)).intValue();
			msg.receiver = (Actor) logicalnodes.get(targeturl);
			double eed = net[sourcenode][targetnode];// end-to-end delay of one
			// message

			long latency = (long) rg.uniform(eed - eed / 10, eed + eed / 10); // plus
			// or
			// minus
			// 10%
			Object cargo = msg.getCargo();
			long stateDimension = determineNumByte(cargo);

			long codeDimension = 0;

			if (cargo instanceof ELDAEventMove)
				codeDimension = codeAgentDimension;
			else
				codeDimension = 500;

			long delay = calculateDelay(stateDimension, codeDimension, latency);

			// if the time of sending is bigger than the channel's freetime
			double now = VirtualClock.getSimulationTime();
			if (now >= freeTime[sourcenode][targetnode]) {
				freeTime[sourcenode][targetnode] = delay + now;
				new Timer().set(msg, delay);
			} else {
				double absoluteSendingTime = delay + freeTime[sourcenode][targetnode];
				new Timer().setAbsolute(msg, absoluteSendingTime);
				freeTime[sourcenode][targetnode] = absoluteSendingTime;
			}
		}
	}

	protected static long determineNumByte(Object data) {
		long numByte = 0;
		ByteArrayOutputStream f = new ByteArrayOutputStream();

		try {
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(data);
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		numByte = f.toByteArray().length;

		return numByte;
	}

	protected static long calculateDelay(long stateDimension, long codeDimension, long latency) {
		return (long) ((((stateDimension + codeDimension) / (double) band)) * 1000) + latency;
	}

	public static void setCodeDimension(long value) {
		codeAgentDimension = value;
	}
}
