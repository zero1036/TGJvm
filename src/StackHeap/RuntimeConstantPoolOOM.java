package StackHeap;

import java.util.ArrayList;
import java.util.List;

public class RuntimeConstantPoolOOM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 使用List保持着常量池引用，比喵Full GC回收常量池行为
		List<String> list = new ArrayList<String>();
		
		//10M的PermSize在Integet范围内足够产生OOM了
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}

}
