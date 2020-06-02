/* Created on 11/09/2006 */
package jmonitor;

import onjlib.utils.GWrappingException;

/**
 * 
 * @author Odedn
 */
public class JadminException extends GWrappingException {

	/**
	 * @param s
	 */
	public JadminException(String s)
	{
		super(s);
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param s
	 * @param th
	 */
	public JadminException(String s, Throwable th)
	{
		super(s, th);
		// TODO Auto-generated constructor stub
	}

}
