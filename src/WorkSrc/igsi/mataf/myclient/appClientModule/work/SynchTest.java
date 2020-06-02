package work;
import java.io.InterruptedIOException;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

class SynObject {
	synchronized void foo()
	{
		System.out.println("in foo");
		try {
			Thread.currentThread().sleep(4000);
		} catch(InterruptedException ie) {
			ie.printStackTrace();	
		}
	
		System.out.println("exiting foo");
	}
	
	synchronized void bar()
	{
		System.out.println("in bar");
		try {
			Thread.currentThread().sleep(4000);
		} catch(InterruptedException ie) {
			ie.printStackTrace();	
		}
	}
	

	void unsynched()
	{
		System.out.println("in unsynched");	
	}	
	
}
public class SynchTest extends Thread {
	SynObject o;
	int id;
	SynchTest(SynObject so, int i)
	{
		o = so;	
		id = i;
	}
	public void run()
	{
	
		if(id > 0) {
			o.unsynched();				
		} else {	
			o.foo();
		}
		o.bar();
		o.unsynched();	
	}
	
	public static void main(String[] args) {
		
		SynObject o = new SynObject();
		for(int i=0; i < 2; ++i) {
			SynchTest st = new SynchTest(o,i);				
			
			st.start();
		}
	}
}
