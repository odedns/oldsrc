/**
 * Date: 29/04/2007
 * File: ExceptionTest.java
 * Package: tests
 */
package tests;

/**
 * @author Oded Nissan.
 *
 */
class GException extends Exception {
	GException(String s){
		super(s);
	}
}

class SpecialException extends GException {
	SpecialException(String s){
		super(s);
	}
}

public class ExceptionTest {

	
	public static void foo() throws GException
	{
		System.out.println("in foo()");
		
	}
	
	
	public static void bar(boolean b) throws GException, SpecialException
	{
		System.out.println("in foo()");
		if(b) {
			throw new GException("GException in bar()");
		} else {
			throw new SpecialException("SpecialException in bar()");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			ExceptionTest.bar(false);			
		} catch(SpecialException e) {
			e.printStackTrace();
		} catch(GException ge){
			ge.printStackTrace();
		}
		try {
			ExceptionTest.bar(false);
		} catch(GException ge){
			ge.printStackTrace();
		}

	}

}
