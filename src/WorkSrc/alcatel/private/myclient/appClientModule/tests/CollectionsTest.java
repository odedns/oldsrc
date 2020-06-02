/**
 * Date: 19/03/2007
 * File: CollectionsTest.java
 * Package: tests
 */
package tests;

import java.util.LinkedList;

/**
 * @author a73552
 *
 */
public class CollectionsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LinkedList<String> l1 = new LinkedList<String>();
		LinkedList<String> l2 = new LinkedList<String>();
		
		for(int i=0; i < 3; ++i) {
			l1.add("Sting-" + i);
		}
		for(int i=4; i < 7; ++i) {
			l2.add("Sting-" + i);
		}
		
		l1.addAll(l2);
		for(String s: l1){
			System.out.println(s);
		}
	}

}
