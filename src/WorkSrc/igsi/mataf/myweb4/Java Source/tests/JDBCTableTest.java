package tests;
import java.sql.Timestamp;
import java.util.Enumeration;

import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JDBCTableTest {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	public static void main(String[] args) {
		
		
		try {
			init();
			JDBCTable tblSvc = (JDBCTable) Service.readObject("matafOverrideTable");
			Context ctx = (Context) Context.readObject("matafOverrideTableCtx");
			HashtableFormat fmt = (HashtableFormat) FormatElement.readObject("matafOverrideTableFmt");			
			
			System.out.println("ctx = " + ctx.toString());
			ctx.setValueAt("IDNUMBER",new Integer(106));
			ctx.setValueAt("TRXID",new Integer(1));
			ctx.setValueAt("STATUS",new Integer(1));
			ctx.setValueAt("REASON","some fucking reason !");
			ctx.setValueAt("OR_DATE", new java.sql.Date(System.currentTimeMillis()));
			Hashtable ht = new Hashtable();
			ht = fmt.format(ctx);
			/*
			System.out.println("adding record ht = " + ht.toString());
			tblSvc.addRecord(ht);
			System.out.println("record added...");
			*/
			
			Vector v = tblSvc.retrieveRecordsMatching("IDNUMBER =105");
			System.out.println("got v = " + v.toString());
			if(!v.isEmpty()) {
				Enumeration enum = v.elements();
				ht = (Hashtable) enum.nextElement();
				fmt.unformat(ht,ctx);
				System.out.println("ctx = " + ctx.toString());
				ctx.setValueAt("STATUS", new Integer(0));
				tblSvc.updateRecordsMatching("IDNUMBER=105",ctx,fmt);
				System.out.println("update succeeded..");
			}
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
