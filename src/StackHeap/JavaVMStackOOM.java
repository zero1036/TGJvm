package StackHeap;

import java.*;
import java.util.*;
import java.lang.*;

public class JavaVMStackOOM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					dontStop();
				}
			});
			thread.start();
		}

	}

	private void dontStop() {
		while (true) {
		}
	}
}
