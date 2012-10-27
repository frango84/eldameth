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

package eldaframework.agent;

import java.util.Vector;

import java.io.*;

import eldaframework.eldaevent.*;


public class ELDAQueue implements Serializable{

	//Data Structure --> PriorityQueue

	private final boolean DEBUG = true;

	Vector queue=null;

	public ELDAQueue(){
		//create queue
		queue = new Vector();
	}

	public synchronized ELDAEvent dequeue(){
		if( queue.size()==0 )
			try { wait(); }catch (InterruptedException ignored) {}
			ELDAEvent mevent = (ELDAEvent)queue.remove(0);
			if (DEBUG) System.out.println("Dequeue:"+mevent);
			notifyAll(); return mevent;
	}

	public synchronized void enqueue(ELDAEvent mevent){
		if (DEBUG) System.out.println("Insert:"+mevent);
		queue.addElement(mevent);
		notifyAll();
	}

	public synchronized void putInHead(ELDAEvent mevent){
		if (DEBUG) System.out.println("Insert:"+mevent);
		queue.add(0,mevent);
		notifyAll();
	}
}
