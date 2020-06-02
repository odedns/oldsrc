package tests;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.services.jdbc.DSESQLException;
import com.ibm.dse.services.jdbc.JDBCJournal;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Myjournal extends JDBCJournal {

	/**
	 * @see com.ibm.dse.services.jdbc.DatabaseConnect#disconnect()
	 */
	public void disconnect() throws DSESQLException, DSEException {
		super.disconnect();
	}
	
	/**
	 * @see com.ibm.dse.services.jdbc.Journal#today()
	 */
	protected String today() {
		String date = "";
		int wrapNum= -1;
		try {
			wrapNum = currentWrapNumber();
			if(wrapNum == 0) {
				System.out.println("calling super.today()");
				return(super.today());	
			}
			date = currentGenerationDate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("wrap num = " + wrapNum + "today date = " + date);
		return(date);
	}

}
