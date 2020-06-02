/* Created on 25/05/2006 */
package test;

import java.io.PrintWriter;

/**
 * 
 * @author Odedn
 */
public class SelectAction {
	PrintWriter pw = null;
	
	
	
	
	public void doAction(PrintWriter pw)
	{
		this.pw = pw;
		pw.println("<p>SelectAction.doAction()");
		try {
			getConnection(1000);
			runSelect(2000);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void getConnection(long millis) throws InterruptedException
	{		
		pw.println("<br>getConnection() sleep: " + millis);
		Thread.sleep(millis);
	}
	
	public void runSelect(long millis)throws InterruptedException
	{
		pw.println("<br>runSelect() sleep: " + millis);
		Thread.sleep(millis);
	}
}
