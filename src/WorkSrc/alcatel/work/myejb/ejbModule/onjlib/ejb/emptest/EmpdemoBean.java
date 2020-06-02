package onjlib.ejb.emptest;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.ejb.*;
import java.util.*;
import java.sql.*;
import javax.naming.*;

public class EmpdemoBean implements EntityBean {
	private EntityContext m_ctx;
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
	System.out.println("ejbActivate (" + getId() + ")");
  }

  /**
   * Sets the EntityContext for the EJBean.
   *
   * @param ctx               EntityContext
   */
  public void setEntityContext(EntityContext ctx)
	throws RemoteException {
	  System.out.println("setEntityContext ");
	m_ctx = ctx;
  }

  /**
   * Unsets the EntityContext for the EJBean.
   *
   */
  public void unsetEntityContext()
	throws RemoteException {
	System.out.println("unsetEntityContext ");
	m_ctx = null;
  }

  /**
   * This method is required by the EJB Specification,
   * but is not used by this example.
   *
   */
  public void ejbPassivate() {
	  System.out.println("ejbPassivate (" + getId() + ")");
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
  /**
   * Refreshes the EJBean from the persistent storage.
   *
   * @exception               java.rmi.RemoteException
   *                          if there is a communications or systems failure
   */
  public void ejbLoad()
	throws RemoteException
  {
		 System.out.println("ejbLoad: (" + getId() +  ")");
		 Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  try {
		Integer i = (Integer) m_ctx.getPrimaryKey();
		conn = getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select * from empdemo e, dept where empid =" + i.intValue() + " and e.dept=deptno" );
		System.out.println("select * from empdemo e, dept where empid =" + i.intValue() + " and e.dept=deptno" );
		if(rs.next()) {
				m_id =  rs.getInt("empid");
				m_name = rs.getString("name");
				m_dept =  rs.getInt("dept");
		} else {
			throw new RemoteException("ejbLoad: data cannot be found : " + i);
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

  }

  /**
   * Given a Primary Key, refreshes the EJBean from
   * the persistent storage.
   *
   * @param pk                AccountPK Primary Key
   * @return                  AccountPK Primary Key
   * @exception               javax.ejb.FinderException
   *                          thrown if the EJBean cannot be found
   * @exception               java.rmi.RemoteException
   *                          if there is a communications or systems failure
   */
  public  Integer ejbFindByPrimaryKey(Integer id)
	throws FinderException
  {
	  System.out.println("ejbFindByPrimaryKey( " + id + " )");
	  Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  try {
		conn = getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select * from empdemo where empid =" + id.intValue());
		System.out.println("select * from empdemo where empid=" + id.intValue());
		if(rs.next()) {
			m_id = rs.getInt("empid");
			System.out.println("found id = " + m_id);
		} else {
			throw new FinderException("not found: " + id);
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
	  return(new Integer(m_id));


  }

  public Collection ejbFindAll()
  {
	  System.out.println("ejbFindAll()");
	  Connection conn=null;
	  Statement st=null;
	  ResultSet rs=null;
	  ArrayList ar = new ArrayList(300);
	  Integer pk;
	  int i;
	  try {
		conn = getConnection();
		st = conn.createStatement();
		rs = st.executeQuery("select empid from empdemo" );
		System.out.println("select empid  from empdemo ");
		while(rs.next()) {
			i = rs.getInt("empid");
			System.out.println("found id = " + i);
			ar.add(new Integer(i));
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
	  return(ar);
  }

  /**
   * Stores the EJBean in the persistent storage.
   *
   * @exception               java.rmi.RemoteException
   *                          if there is a communications or systems failure
   */
  public void ejbStore() throws RemoteException {

		System.out.println("ejbStore() called ");
	Connection conn=null;
	Statement st=null;

	if(!isModified()) {
		return;
	}
	try {
		Integer i = (Integer) m_ctx.getPrimaryKey();
		conn = getConnection();
		st = conn.createStatement();
		System.out.println("update  empdemo set name ='"  + m_name +
		"', dept= " + m_dept + " where empid =" + i.intValue());
		st.executeUpdate("update  empdemo set name ='"  + m_name +
		"', dept= " + m_dept + " where empid =" + i.intValue());
		modify = false;
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
  }

  /**
   * Deletes the EJBean from the persistent storage.
   *
   * @exception               javax.ejb.RemoveException
   *                          if the EJBean does not allow removing the EJBean
   * @exception               java.rmi.RemoteException
   *                          if there is a communications or systems failure
   */
  public void ejbRemove()
	throws RemoveException
  {
	  System.out.println("ejbRemove (" + m_id + ")");
	  Connection conn=null;
	Statement st=null;
	try {
		Integer i = (Integer) m_ctx.getPrimaryKey();
		conn = getConnection();
		st = conn.createStatement();
		st.executeUpdate("delete from  empdemo where empid =" + i.intValue());
		System.out.println("delete from  empdemo where empid =" + i.intValue());
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
  }

  public Integer ejbCreate(int id, String name, int dept)
	throws CreateException,
		   RemoteException
  {
	  m_name = name;
	  m_id=id;
	  m_dept = dept;
	  Connection conn=null;
	  Statement st=null;
	  System.out.println("ejbCreate (" + id + ")");
	  try {
		conn = getConnection();
		st = conn.createStatement();
		st.executeUpdate("insert into empdemo values (" + id + ",'" + name + "'," + dept + ", 64000 )" );
		System.out.println("insert into empdemo values (" + id + ",'" + name + "'," + dept + ",64000 )" );
	 } catch (Exception e) {
		e.printStackTrace();

	  }
	try {
		System.out.println("closing connection ..");
		st.close();
				   conn.close();
	} catch(Exception e2) {
		System.out.println(" Exception " + e2);
		e2.printStackTrace();
	}


	  return(new Integer(id));
  }

  /**
   * Required by the EJB specification, this method is not used
   * by this example.
   *
   * @param accountID         String Account Identification
   * @param initialBalance    double Initial Balance
   */
  public void ejbPostCreate(int id,String name, int dept)
 {
	  System.out.println("ejbPostCreate (" + id + ")");
  }




  public String getName()
  {
	return(m_name);
  }

  public void setName(String name)
  {
	m_name = name;
	modify = true;
  }

  public int getId()
  {
	return(m_id);
  }
  public void setDept(int dept)
  {
	  m_dept = dept;
	  modify = true;
  }

  public int getDept()
  {
	  return(m_dept);
  }



  public boolean isModified()
  {
	  return(modify);
  }

}
