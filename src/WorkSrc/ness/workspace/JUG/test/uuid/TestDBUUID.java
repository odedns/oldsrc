package test.uuid;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.ness.fw.persistence.uuid.UUIDProvider;

public class TestDBUUID {

	public static void main(String[] args) {
		TestDBUUID dbUuid = new TestDBUUID();
		dbUuid.doit(50);
	}

	private void doit(int nRecordsToInsert) {
		String connectionString = "jdbc:as400://10.99.244.61;naming=sql;libraries=fwgil;bidi string type=6;";
		String driver = "com.ibm.as400.access.AS400JDBCDriver";
		String dbUser = "hdbusr";
		String dbPassword = "hdbpwd";
		try
		{
			Class.forName(driver).newInstance();
			java.sql.Connection con = DriverManager.getConnection(connectionString,dbUser,dbPassword);

			PreparedStatement prpdStmt = con.prepareStatement("insert into ZZZZZ2 (id, desc) values(?,?)");

			for(int i = 0; i < nRecordsToInsert; i++){
				prpdStmt.setString(1, UUIDProvider.generateUUIDasHex());
				prpdStmt.setString(2, "UUID # " + (i + 1));
				prpdStmt.executeUpdate();
				if(((i + 1) % 1000) == 0){
					con.commit();
					System.out.println("done " + (i + 1) + " records.");
				}
			}
			
			prpdStmt.close();
			con.commit();
			con.close();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}		
	}

}
