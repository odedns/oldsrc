package work;

import java.lang.reflect.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */


interface Foo {
	public abstract void foo();	
}


class FooImpl implements Foo {
	
	public void foo()
	{
		System.out.println("in FooImp,foo()");	
	}	
	
}


class Myhandler implements InvocationHandler {
		Object realObj;

		Myhandler(Object o) 		
		{			
			realObj = o;
		}
		
		public Object invoke(Object proxy, Method method,
                     Object[] args)
              throws Throwable
        {
        	System.out.println("in invoke");
        	return(method.invoke(realObj,args));
        	
        }
}

public class DProxy {

	public static void main(String[] args) {
				
		InvocationHandler handler = new Myhandler(new FooImpl());
		 Foo f = (Foo) Proxy.newProxyInstance(Foo.class.getClassLoader(),
                                          new Class[] { Foo.class },
                                          handler);
                                          
		f.foo();                                          
	}
}
