/**
 * Created on 16/01/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;
import org.hibernate.cfg.*;
import org.hibernate.*;
import java.net.URL;


/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestEmp
{

	public static void main(String[] args)
	{
		String fname = "c:/ibm/wks/dbproj/src/hibernate.cfg.xml";
		try {
											// com.ibm.db2j.jdbc.Db2jDriver
			Class c = Class.forName("com.ibm.db2j.jdbc.DB2jDriver");
			Configuration cfg  = new Configuration();			
			URL url = ClassLoader.getSystemResource("hibernate.cfg.xml"); 
			
			SessionFactory sessionFactory =	cfg.configure().buildSessionFactory();

			Session s = sessionFactory.openSession();
			
			Transaction trx = s.beginTransaction();
			Employee emp = new Employee();
			emp.setName("Wally");
			emp.setType(new Integer(Employee.EMP));
			emp.setSalary(new Float(111000));
			s.save(emp);
			Department dept = new Department();
			dept.setName("Software");
			dept.setType(new Integer(100));
			dept.addEmployee(emp);
			s.save(dept);						
			trx.commit();
			s.flush();
			s.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
