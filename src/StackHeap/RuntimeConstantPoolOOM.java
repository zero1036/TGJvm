package StackHeap;

import java.util.ArrayList;
import java.util.List;

public class RuntimeConstantPoolOOM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ʹ��List�����ų��������ã�����Full GC���ճ�������Ϊ
		List<String> list = new ArrayList<String>();
		
		//10M��PermSize��Integet��Χ���㹻����OOM��
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}

}
