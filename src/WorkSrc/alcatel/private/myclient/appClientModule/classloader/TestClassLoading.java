package classloader;

import java.util.ArrayList;
import java.lang.reflect.*;


class Foo {
	
	Foo()
	{
		System.out.println("Foo()");	
	}		
	
	void exec()
	{
		ArrayList ar = new ArrayList();
		ar.add("foo");
		System.out.println("exec()");		
	}
	
	public static void main(String argv[])
	{
		System.out.println("in Foo main");
		Foo f = new Foo();
		f.exec();	
	}
	
	
	
}
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestClassLoading {

	public static void main(String[] argv) {
		
		try {
			MyClassLoader loader = new MyClassLoader(MyClassLoader.class.getClassLoader());
			Thread.currentThread().setContextClassLoader(loader);
			Class c = loader.loadClass("classloader.Foo");
			
			Method mainMethod = c.getMethod("main", new Class [] {String [].class});
			Object args[] = new Object[1];
	    	mainMethod.invoke(null, args); 
			/*
			Foo f = (Foo)c.newInstance();
			f.exec();
			*/
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
		
}
