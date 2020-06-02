/*
 * Created on 18/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package work;

import onjlib.db.DbService;
import onjlib.db.Page;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestDb {

	/**
	 * 
	 */
	public TestDb() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main(String args[]) {
		
		try { 
			System.out.println("TestDb");
			DbService db = new DbService();
			Page p  = db.executeQueryEx("SELECT * FROM ON_PK_GENERATOR");
			
			if(p.next()) {
				int id = p.getInt(1);
				int numerator = p.getInt(2);
				String desc = p.getString(3);
				System.out.println("id=" + id + "\tnum=" + numerator + "\tdesc=" + desc);
				
			}
			
			db.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
