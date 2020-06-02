/* Created on 25/05/2006 */
package test;

import java.io.PrintWriter;

/**
 * 
 * @author Odedn
 */
public class InsertAction {

	PrintWriter pw = null;
	
	public void doAction(PrintWriter pw)
	{
		this.pw = pw;
		pw.println("<p>InsertAction.doAction()");
		try {
			getConnection(1000);
			runInsert(4000);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public void getConnection(long millis) throws InterruptedException
	{		
		pw.println("<br>getConnection() sleep: " + millis);
		Thread.sleep(millis);
	}
	
	public void runInsert(long millis)throws InterruptedException
	{
		pw.println("<br>runInsert() sleep: " + millis);
		Thread.sleep(millis);
	}
	
	
		
}
