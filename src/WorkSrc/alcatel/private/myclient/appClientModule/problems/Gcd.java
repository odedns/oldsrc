/**
 * Date: 20/03/2007
 * File: Gcd.java
 * Package: problems
 */
package problems;

/**
 * @author a73552
 *
 */
public class Gcd {

	
	public static long gcd(long x, long y)
	{
		if((x % y) == 0) {
			return(y);
		}
		if(y == 0) {
			return(y);
		}
		return(gcd(y, x %y));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		long x = 34398;
		long y = 2132;
		long n = gcd(x,y);
		System.out.println("gcd(" + x +"," + y + ")=" + n);
	}

}
