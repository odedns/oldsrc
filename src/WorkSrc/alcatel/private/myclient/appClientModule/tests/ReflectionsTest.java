package tests;
import java.lang.reflect.*;

import onjlib.utils.GTimer;




class Foo2 {
	
	public void foo(String s) 
	{
		//System.out.println("this is foo param:" + s);	
		char c;
		for(int i=0; i < s.length(); ++i) {
			c = s.charAt(i);			
		}
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
public class ReflectionsTest {

	public static void main(String[] args) {
		try {
			GTimer t = new GTimer();
			t.start();		
			for(int i=0; i < 500; ++i) {	
				Class c = Class.forName("work.Foo2");
				Foo2 myfoo = (Foo2) c.newInstance();
				String s = " param999";
				Class params[] = new Class[1];
				params[0] = s.getClass();
				Object p[] = new Object[1];
				p[0] = s;			
				Method m = c.getMethod("foo",params);
				m.invoke(myfoo,p);
			}
			t.stop();
			System.out.println("reflections took : " + t.getElapsedTime());
			t.start();
			for(int i=0; i < 500; ++i) {
				Foo2 mfoo = new Foo2();
				String s = "params0000";
				mfoo.foo(s);
			}
			t.stop();
			System.out.println("regular call took : " + t.getElapsedTime());
			
		} catch(Exception e) {
			
			e.printStackTrace();	
		}		
	}
}
