/*
 * Created on 26/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tests;



class MyContainer {
	private static ThreadLocal m_obj = new ThreadLocal() {
		
		protected synchronized Object initialValue() {
            return new String("foo");
        }
	};
	
	public static String getString() {
		return((String) m_obj.get());
	}
	
	public static void setString(String s) {
		m_obj.set(s);
	}
}

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ThreadLocalTest  extends Thread{

	
	
	/**
	 * 
	 */
	public ThreadLocalTest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
		
		System.out.println("running thread : " + getName());
		
		MyContainer.setString(getName());
		System.out.println("name = " + getName() + "\tgot : " + MyContainer.getString());
	}
	

	public static void main(String[] args) {
		
		for(int i=0; i < 5; ++i) {
			ThreadLocalTest th = new ThreadLocalTest();
			th.setName("foo-" + i);
			th.start();
		}
		
	}
}
