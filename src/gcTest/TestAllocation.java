package gcTest;

import java.lang.*;

public class TestAllocation {

	public static void main(String[] args) throws Throwable {
		
		TestAllocation taAllocation=new TestAllocation();
		taAllocation.testAllocation();
	}
	
private int _1MB=1024*1024;

/*
 * VM���� ��-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 */
public void  testAllocation() {
	
//	�����ڣ�3324K-��152K��3712K���������ǡ�GCǰ���ڴ�������ʹ������-��GC����ڴ�������ʹ�����������ڴ���������������
//	�����⣺3324K-> 152K��11904K�� �����ǡ�GCǰJava����ʹ������ -> GC��Java����ʹ��������Java������������

	byte[] allocation1,allocation2,allocation3,allocation4;
	allocation1 = new byte[10 * _1MB];
	allocation2 = new byte[2 * _1MB];
	allocation3 = new byte[2 * _1MB];
	allocation4 = new byte[4 * _1MB];
}

}
