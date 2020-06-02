/**
 * Date: 21/03/2007
 * File: LoopTests.java
 * Package: tests
 */
package tests;

/**
 * @author a73552
 *
 */
public class LoopTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String s = "My String";
		
		for(int i: s.getBytes()){
			System.out.println("charAt[" + i + "]=" + s.charAt(i));
		}
	}

}
