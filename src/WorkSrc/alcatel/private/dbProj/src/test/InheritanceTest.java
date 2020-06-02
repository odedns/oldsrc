/**
 * Created on 28/12/2004
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;
import org.hibernate.cfg.*;
import org.hibernate.*;

import java.sql.*;
import java.util.*;
import java.io.*;


/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InheritanceTest
{

	/**
	 * 
	 */
	public InheritanceTest()
	{
		super();
		// TODO Auto-generated constructor stub
	}


	static void addManager(Session s) throws Exception
	{
		Transaction trx = s.beginTransaction();
		Manager mng = new Manager();
		mng.setName("Some Boss");
		mng.setSalary(new Float(100000));
		mng.setNumEmps(new Integer(1));
		s.save(mng);
		trx.commit();
		
	}
	
	public static void main(String[] args)
	{
		try {
						// com.ibm.db2j.jdbc.Db2jDriver
					File dir1 = new File (".");
					System.out.println("dir = " + dir1.getCanonicalPath());
					Class c = Class.forName("com.ibm.db2j.jdbc.DB2jDriver");
					Configuration cfg  = new Configuration();
					cfg.setProperty("hibernate.show_sql", "true");
					cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
					cfg.addFile("c:/ibm/wks/dbproj/src/team.hbm.xml");
					
					SessionFactory sessionFactory = cfg.buildSessionFactory();
					
					Connection conn = java.sql.DriverManager.getConnection("jdbc:db2j:c:/ibm/cloudscape/mydb;create=false");
					Session s = sessionFactory.openSession(conn);
					//addVehicle(s);
					
					List l = findAllManagers(s);
					Iterator iter = l.iterator();
					while(iter.hasNext()) {
						Employee emp = (Employee) iter.next();
						System.out.println("name=" + emp.getName());
						if(emp instanceof Manager) {
							System.out.println("is manager");
						}
					}
												
					s.flush();
					s.close();
					System.out.println("done ...");
				} catch(Exception e){
					e.printStackTrace();
				}
		
		
		
	}
	
	
	static List findAll(Session s) throws Exception
	{
		Query q  = s.createQuery("from test.Employee emp");
		List l = q.list();
		return(l);
		
	}
	
	static List findAllManagers(Session s) throws Exception
	{
		Query q  = s.createQuery("from test.Employee emp where emp.type=2");
		List l = q.list();
		return(l);
	}
	
	
	static void addVehicle(Session s) throws Exception
	{
		Car c = new Car();
		c.setDesc("my car");
		c.setModel("Ibiza");
		c.setYear(new Integer(1998));
		Transaction trx = s.beginTransaction();
		s.save(c);
		Truck t = new Truck();
		t.setDesc("My truck");
		t.setHorsePower(new Integer(40000));
		t.setMake("Volvo");
		s.save(t);
		trx.commit();
		
		System.out.println("saved car...");
		
	}
	
	
	static List findAllVehicles(Session s) throws Exception
	{
		return(null);
	}
}
