package StackHeap;

import java.util.*;
import java.lang.reflect.Method;
//import StackHeap.HeapOOM.OOMObject;
//import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.MethodInterceptor;

public class JavaMethodAreaOOM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while (true) {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(OOMObject.class);
			enhancer.setUseCache(false);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object obj, Method method,
						Object[] args, MethodProxy proxy) throws Throwable {
					// TODO Auto-generated method stub
					return proxy.invokeSuper(obj, args);
				}
			});
			enhancer.create();
		}
	}

	static class OOMObject {
	}

}
