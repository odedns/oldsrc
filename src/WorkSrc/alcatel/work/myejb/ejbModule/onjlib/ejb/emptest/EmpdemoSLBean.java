
package onjlib.ejb.emptest;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.*;
import java.util.*;
import java.sql.*;
import javax.naming.*;

public class EmpdemoSLBean implements SessionBean {
	private SessionContext m_ctx;
	int m_id;
	String m_name;
	int m_dept;
	boolean modify = false;



  /**
   * Required by the EJB specification, this method is not used
   * by this example.
   *
   */
  public void ejbActivate() {
	System.out.println("ejbActivate ");
  }

  /**
   * Sets the EntityContext for the EJBean.
   *
   * @param ctx               EntityContext
   */
  public void setSessionContext(SessionContext ctx)
	throws RemoteException {
	  System.out.println("setEntityContext ");
	m_ctx = ctx;
  }


  /**
   * This method is required by the EJB Specification,
   * but is not used by this example.
   *
   */
  public void ejbPassivate() {
	  System.out.println("ejbPassivate ");
  }

  public Connection getConnection() throws Exception
  {
	  Context ctx = null;

	  // Get a context for the JNDI look up
	  ctx = new InitialContext();
	  javax.sql.DataSource ds
		  = (javax.sql.DataSource) ctx.lookup ("jndi.accessPool");
	  return(ds.getConnection());


  }


  public Empinfo getEmp(int empid)
  {
	  System.out.println("getEmp()");
	  Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  int id;
	  int dept;
	  String name;
	  Empinfo e = null;
	  try {
		conn = getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select * from empdemo where empid =" + empid);
		System.out.println("select * from empdemo where empid=" + empid);
		if(rs.next()) {
			id = rs.getInt("empid");
			name = rs.getString("name");
			dept = rs.getInt("dept");
			System.out.println("found id = " + empid);
			e = new Empinfo(id, name,dept);
		}

	 } catch (Exception ex) {
		ex.printStackTrace();
	  }

	  finally {
		try {
			st.close();
					conn.close();
		} catch(Exception ex2) {
		}
	}

	  return(e);
  }
  public Empinfo[] getAll()
  {
	  System.out.println("getAll()");
	  Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  int id;
	  int dept;
	  String name;
	  ArrayList ar = new ArrayList(300);
	  try {
		conn = getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select * from empdemo e, dept where e.dept =deptno" );
		System.out.println("select * from empdemo e, dept where e.dept =deptno" );
		while(rs.next()) {
			id = rs.getInt("empid");
			name = rs.getString("name");
			dept = rs.getInt("dept");
			System.out.println("found id = " + id);
			ar.add(new Empinfo(id, name,dept));
		}

	 } catch (Exception e) {
		e.printStackTrace();
	  }

	  finally {
		try {
			st.close();
					conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	  Empinfo ei[] = new Empinfo[ar.size()];
	  ei = (Empinfo[]) ar.toArray(ei);

	  return(ei);
  }


  public void ejbRemove() { }
  public void ejbCreate() { }


}
