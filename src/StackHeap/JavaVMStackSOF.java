package StackHeap;

import java.util.*;
import StackHeap.HeapOOM.OOMObject;

public class JavaVMStackSOF {

	private int stackLength = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable eThrowable) {
			// TODO: handle exception
			System.out.println("stack length:" + oom.stackLength);
			throw eThrowable;
		}
	}

	private void stackLeak() {
		stackLength++;
		stackLeak();
	}

}
