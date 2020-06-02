package tests;

import com.ibm.dse.base.*;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BlobTest {
	
	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	
	static Connection getConnection() throws Exception
	{	
	  String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";	
	  String url = "jdbc:microsoft:sqlserver://10.11.103.216:1433";
      Class.forName(driver).newInstance();
      Properties props = new Properties();
      props.put("DatabaseName","MATAFDB");            
      props.put("User","mataf");      
      props.put("Password","mataf");            
      Connection c =java.sql.DriverManager.getConnection(url,props);
      return(c);	
	}


	static int writeCtx(Context ctx) throws Exception
	{
		Connection conn = null;
		PreparedStatement pst = null;
		int pk = -1;
		try {
			conn = getConnection();
			pst = conn.prepareStatement("INSERT INTO BIN_TBL VALUES (?, ?)");
			pk = ctx.hashCode();			
			pst.setInt(1,pk);
			KeyedCollection kc = ctx.getKeyedCollection();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			kc.writeExternal(os);
			os.flush();
			os.close();
			byte b[] = bos.toByteArray();
			System.out.println("wrote len = " + b.length);
			pst.setBytes(2,b);
			pst.execute();
			
		} finally {
			if(pst != null) {
				pst.close();	
			}
	
			if(conn != null) {
				conn.close();	
			}
			
		}
		return(pk);
		
	}
	
	
	static Context readContext(int pk) throws Exception
	{
		
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT CTX FROM BIN_TBL WHERE PK=" + pk;
		
		try {
			System.out.println("sql = " + sql);
			conn = getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				byte b[] = rs.getBytes(1);
				System.out.println("got len = " + b.length);
				ByteArrayInputStream bis = new ByteArrayInputStream(b);
				ObjectInputStream is = new ObjectInputStream(bis);
				KeyedCollection kc = new KeyedCollection();
				kc.readExternal(is);							
				is.close();			
				System.out.println("kc = " + kc.toString());
			}			
			
			
		} finally {
			if(rs != null) {
				rs.close();
			}
			if(st != null) {
				st.close();	
			}
	
			if(conn != null) {
				conn.close();	
			}
			
		}
		return(null);
		
		
	}
	public static void main(String[] args) {
		
		try {
			init();
			System.out.println("BlobTest : after init");
			Context ctx = (Context) Context.readObject("overrideCtx");
			ctx.setValueAt("mgrUserId","111111");
			ctx.setValueAt("tellerUserId","foo");
			ctx.setValueAt("trxId","106");
			ctx.setValueAt("trxName","סריקת צקים");
			int pk = writeCtx(ctx);			
			System.out.println("wrote ctx pk = " + pk);
			readContext(pk);
			System.out.println("blob test done ...");
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
		
	}
}
