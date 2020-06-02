/*
 * Created on 07/06/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.db;

import java.sql.Connection;
import java.util.Properties;

/**
 * @author P0006439
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class ConnectionManager {

	public abstract void init(Properties props) throws DbException;
	public abstract Connection getConnection() throws DbException;
}
