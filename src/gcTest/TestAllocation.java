package gcTest;

import java.lang.*;

public class TestAllocation {

	public static void main(String[] args) throws Throwable {
		
		TestAllocation taAllocation=new TestAllocation();
		taAllocation.testAllocation();
	}
	
private int _1MB=1024*1024;

/*
 * VM参数 ：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public void  testAllocation() {
	
//	括号内：3324K-＞152K（3712K）”含义是“GC前该内存区域已使用容量-＞GC后该内存区域已使用容量（该内存区域总容量）”
//	括号外：3324K-> 152K（11904K） 含义是“GC前Java堆已使用容量 -> GC后Java堆已使用容量（Java堆总容量）”

	byte[] allocation1,allocation2,allocation3,allocation4;
	allocation1 = new byte[10 * _1MB];
	allocation2 = new byte[2 * _1MB];
	allocation3 = new byte[2 * _1MB];
	allocation4 = new byte[4 * _1MB];
}

}
