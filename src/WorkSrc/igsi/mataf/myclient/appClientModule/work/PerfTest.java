package work;

import java.util.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */


public class PerfTest {

	public static void main(String[] args) {
			HashMap hm = new HashMap();
			//Map hm = Collections.synchronizedMap(new HashMap());
			Hashtable ht = new Hashtable();
			
			
			GTimer t = new GTimer();
			
			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				hm.put(s + "_key", s );
			}
			t.stop();
			System.out.println("hm.put = " + t.getElapsedTime());

			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				hm.get(s + "_key");
			}
			t.stop();
			System.out.println("hm.get = " + t.getElapsedTime());


						
			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				ht.put(s + "_key", s );
			}
			t.stop();
			System.out.println("ht.put = " + t.getElapsedTime());
			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				ht.get(s + "_key");
			}
			t.stop();
			System.out.println("ht.get = " + t.getElapsedTime());			

			
			
			
			
			
			
			// test array list and vector.			
			ArrayList ar = new ArrayList();
			Vector v = new Vector();

			
			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				ar.add(s);
			}
			t.stop();
			System.out.println("ar.add = " + t.getElapsedTime());			
			
			t.start();
			for(int i=0; i < 10000; ++i) {
				String s = new Integer(i).toString();
				v.add(s);
			}
			t.stop();
			System.out.println("v.add = " + t.getElapsedTime());	
			
			
	}
}
