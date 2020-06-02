/*
 * Created on 15/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.db;

import onjlib.utils.GWrappingException;

/**
 * @author P0006439
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DbException extends GWrappingException {

	/**
	 * @param s
	 */
	public DbException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public DbException(String s, Exception e) {
		super(s,e);
		// TODO Auto-generated constructor stub
	}
}
