package mataf.dwplugin;
import com.ibm.dse.dw.cm.WorkspaceConnection;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.cm.*;
import java.sql.*;
import com.ibm.dse.dw.plugin.DevelopmentWorkbenchPlugin;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpenEditionManager {

	static final String OE_SELECT = "SELECT OWNER FROM MATAF_OE WHERE INSTANCE=";
	static final String OE_INSERT = "INSERT INTO MATAF_OE VALUES (";
	static final String OE_DELETE = "DELETE FROM MATAF_OE WHERE INSTANCE=";
	/**
	 * Private constructor - this class
	 * cannot be instantiated.
	 */
	private OpenEditionManager()
	{
	}

	
	/**
	 * get the workspace owner
	 * from the active workspace connection.
	 * @return String the name of the workspace owner.
	 */
	private static String getOwner()
	{
		ConnectionManager cm = DevelopmentWorkbenchPlugin.getDefault().getConnectionManager();
		WorkspaceConnection wksConn = (WorkspaceConnection) cm.getActiveWorkspaceConnection();
		String owner = wksConn.getApplicationUserName();
		return(owner);
	}
	
	
	
	/**
	 * Check is the instance already has
	 * an open edition.
	 * @param ins the Instance to check.
	 * @return boolean true if the instance has an open edition.
	 * false otherwise.
	 */
	public static String hasOpenEditionOwner(Instance ins) 
	{
		String wksOwner = getOwner();
		String owner = hasOpenEdition(ins);
		   /**
	    * if open edition owner is equal to wks owner
	    * then we overwrite the open edition since the current
	    * user wants to discard his own changes.
	    */
	   if(wksOwner.equals(owner)) {
	   		LogFile.log("currOwner : " + wksOwner + " is equal to owner- overwrite!");
	   		owner = null;
	   }
		return(owner);
	}
	/**
	 * Check is the instance already has
	 * an open edition.
	 * @param ins the Instance to check.
	 * @return boolean true if the instance has an open edition.
	 * false otherwise.
	 */
	public static String hasOpenEdition(Instance ins) 
	{
		String iPath = ins.getPath().toString();
		String sql = OE_SELECT + "'" + iPath + "'";		
		LogFile.getInstance().log("sql = " + sql);
		String owner = null;
		Statement st = null;
		try { 
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);				
			if(rs.next()) {
				owner = rs.getString(1);
			}							
		} catch (Exception e) {
			try {
				if(null != st) {
					st.close();					
				}
			} catch(SQLException sq) {
				
			}
			String s = LogFile.getStackTrace(e);
			LogFile.getInstance().log(s);
			
		}
	   LogFile.getInstance().log("hasOpenEdition after executeQuery");		
		return(owner);
	}
	
	
	/**
	 * Check is the instance already has
	 * an open edition.
	 * @param ins the Instance to check.
	 * @return boolean true if the instance has an open edition.
	 * false otherwise.
	 */
	public static String hasOpenEditionOwner(Domain d) 
	{
		String wksOwner = getOwner();
		String owner = hasOpenEdition(d);
		   /**
	    * if open edition owner is equal to wks owner
	    * then we overwrite the open edition since the current
	    * user wants to discard his own changes.
	    */
	   if(wksOwner.equals(owner)) {
	   		LogFile.log("currOwner : " + wksOwner + " is equal to owner- overwrite!");
	   		owner = null;
	   }
		return(owner);
	}
	/**
	 * Check is the instance already has
	 * an open edition.
	 * @param ins the Instance to check.
	 * @return boolean true if the instance has an open edition.
	 * false otherwise.
	 */
	public static String hasOpenEdition(Domain  d) 
	{
		String iPath = d.getName();
		String sql = OE_SELECT + "'" + iPath + "'";		
		LogFile.getInstance().log("sql = " + sql);
		String owner = null;
		Statement st = null;
		try { 
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);				
			if(rs.next()) {
				owner = rs.getString(1);
			}							
		} catch (Exception e) {
			try {
				if(null != st) {
					st.close();					
				}
			} catch(SQLException sq) {
				
			}
			String s = LogFile.getStackTrace(e);
			LogFile.getInstance().log(s);
			
		}
	   LogFile.getInstance().log("hasOpenEdition after executeQuery");		
		return(owner);
	}
	
	/**
	 * Create an open edition for the current Instance
	 * by creating an entry in the open editions table
	 * mataf_oe in the repository.
	 */
	public static void createOpenEdition(Domain d)
	{
		String name = d.getName();
		String owner = getOwner();
		createDBEntry(name,owner);
		LogFile.getInstance().log("createOpenEdition after executeUpdate");
		
	}
	
	/**
	 * Create an open edition for the current Instance
	 * by creating an entry in the open editions table
	 * mataf_oe in the repository.
	 */
	public static void createOpenEdition(Instance ins)
	{
		String iPath = ins.getPath().toString();
		String owner = getOwner();
		createDBEntry(iPath,owner);
		LogFile.getInstance().log("createOpenEdition after executeUpdate");
		
	}
	
	/**
	 * create a DB entry for the Instance or Domain to be locked.
	 */
	private static void createDBEntry(String name, String owner)
	{
		Timestamp t = new Timestamp(System.currentTimeMillis());
		String sql = OE_INSERT + "'" + name + "','" + owner + "','" + t + "')";		
		LogFile.getInstance().log("sql = " + sql);		
		Statement st = null;
		try {
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);		
		} catch(Exception e) {
			try {
				if(null != st) {
					st.close();					
				}
			} catch(SQLException sq) {
				
			}
			String s = LogFile.getStackTrace(e);
			LogFile.getInstance().log(s);
		}
	
		
	}
	/**
	 * Close an open eidtion by deleting the entry
	 * from the mataf_oe table in the repository.
	 */
	public static void closeOpenEdition(Group grp) 
	{
		GroupComposition gc = grp.getComposition();
		Instance ins[] = gc.getInstances();
		for(int i=0; i < ins.length; ++i) {
			LogFile.getInstance().log("closing edition for : " + ins[i].getName());
			closeOpenEdition(ins[i]);
		}
		Group g[] = gc.getGroups();
		for(int i=0; i < g.length; ++i) {
			closeOpenEdition(g[i]);
		}
		
	}
	
	/**
	 * Close an open edition by deleting the entry
	 * from the mataf_oe table in the repository.
	 */
	public static void closeOpenEdition(Domain d) 
	{
		String name = d.getName();
		deleteDBEntry(name);
		LogFile.getInstance().log("closeOpenEdition after executeUpdate");		
	} // closeOpenEditions
	/**
	 * Close an open eidtion by deleting the entry
	 * from the mataf_oe table in the repository.
	 */
	public static void closeOpenEdition(Instance ins) 
	{
		String iPath = ins.getPath().toString();
		deleteDBEntry(iPath);
		LogFile.getInstance().log("closeOpenEdition after executeUpdate");		
	} // closeOpenEditions
	
	/**
	 * delete the open edition entry
	 * from the database.
	 */
	private static void deleteDBEntry(String name)
	{
		String sql = OE_DELETE + "'" + name + "'";		
		LogFile.getInstance().log("sql = " + sql);
		Statement st = null;
		try {
			java.sql.Connection conn = RepositoryDBConnection.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch(Exception e) {
			try {
				if(null != st) {
					st.close();					
				}
			} catch(SQLException sq) {
				
			}
			String s = LogFile.getStackTrace(e);
			LogFile.getInstance().log(s);		
			
	   } // try
		
	}
}
