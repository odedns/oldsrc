package classloader;

import java.lang.reflect.Method;
import java.util.*;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.Proxy;


class Bar {
	
	void bar()
	{
		System.out.println("void Bar.bar()");
	}
}

class SimpleInvocationHandler implements InvocationHandler {
    Object o = null;
    public SimpleInvocationHandler(Object o) {
        this.o = o;
    }
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        System.out.println("invoking " + m + " on " + o + " with " + args);
        Object r = m.invoke(o, args);
        System.out.println("done: " + m + " on " + o + " with " + args + ", result is " + r);
        return r;
    }
 }

class MyInterceptor implements MethodInterceptor {

	public Object intercept(Object o, Method m, Object[] args, MethodProxy mp) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("MyInterceptor: " + o.getClass().getName() + "\t" + m);
		//return (m.invoke(o, args));
		return(mp.invokeSuper(o, args));
	}
	
	
	
}
public class CglibTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			testIsProxyClass();
			testEnhancer();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testIsProxyClass() throws Exception {
        HashMap map = new HashMap();
        map.put("test", "test");
        InvocationHandler handler = new SimpleInvocationHandler(map);
        Map proxyMap = (Map) Proxy.newProxyInstance(CglibTest.class.getClassLoader(), new Class[] { Map.class }, handler);
        proxyMap.put("test", "test");
        
	}
	
	private static void testEnhancer() throws Exception
	{
		MyInterceptor mi = new MyInterceptor();
		//Map proxyMap = (Map) Enhancer.create(HashMap.class, mi);
		//proxyMap.put("test", "test");
		Bar b = (Bar) Enhancer.create(Bar.class, mi);
		b.bar();
	}

}
