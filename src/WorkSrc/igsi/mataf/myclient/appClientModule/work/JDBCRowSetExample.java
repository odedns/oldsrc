
package work;
import java.sql.*;
import javax.sql.*;

public class JDBCRowSetExample{
	public static void main(String[] argv){
		String url = "jdbc:inetdae7:localhost:1433?database=LEDES";
		String login = "jod";
		String password = "jod";
		try {
			Class.forName("com.inet.tds.TdsDriver").newInstance();
			JDBCRowSet rowSet = new JDBCRowSet();
			//set url,login and password;
			rowSet.setUrl( url );
			rowSet.setUsername( login );
			rowSet.setPassword( password );
			//get the driver version
			DatabaseMetaData dbmd = rowSet.getConnection().getMetaData();
			System.out.println("Driver Name:\t" + dbmd.getDriverName());
			System.out.println("Driver Version:\t" + dbmd.getDriverVersion());
			//set the sql command
			rowSet.setCommand("SELECT ID,FName,LName,EMail FROM CONTACTS");
			//execute the command
			rowSet.execute();
			// read the data and put it to the console
			while (rowSet.next()){
				for(int j=1; j<=rowSet.getMetaData().getColumnCount(); j++){
						System.out.print( rowSet.getObject(j)+"\t");
				}
				System.out.println();
			}
			while (rowSet.previous()){
				for(int j=1; j<=rowSet.getMetaData().getColumnCount(); j++){
					System.out.print( rowSet.getObject(j)+"\t");
				}
				System.out.println();
			}
			rowSet.close();
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
}
