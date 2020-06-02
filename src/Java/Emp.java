
import java.sql.*;


public class Emp {
  
  public static void main(String argv[])
       throws Exception
  {

    Class.forName("COM.cloudscape.core.JDBCDriver").newInstance();
      
    Connection conn = DriverManager.getConnection("jdbc:cloudscape:c:/bea/wlserver/samples/eval/cloudscape/data/demo");
    
    Statement stmt = conn.createStatement();
    
    
    
    ResultSet rs = stmt.executeQuery("select * from emp");
    while (rs.next()) {
	System.out.println("=============================");
	System.out.println("EMPNO: " + rs.getInt("EMPNO"));
	System.out.println("ENAME: " + rs.getString("ENAME"));
	System.out.println("JOB: " + rs.getString("JOB"));
	System.out.println("MGR: " + rs.getInt("MGR"));
	System.out.println("HIREDATE : " + rs.getDate("HIREDATE"));
	System.out.println("SAL: " + rs.getFloat("SAL"));
	System.out.println("COMM: " + rs.getFloat("COMM"));
	System.out.println("COMM: " + rs.getFloat("COMM"));
	System.out.println("DEPTNO: " + rs.getInt("DEPTNO"));
    }
    
    try {
	    rs.close();
    } catch(Exception rse) { }
    try {
	    stmt.close();
    } catch(Exception ste) { }
    try {
	    conn.close();
    } catch(Exception cse) { }
   }
}

