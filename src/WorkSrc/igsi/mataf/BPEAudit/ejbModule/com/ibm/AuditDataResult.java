package com.ibm;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author dk92351
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AuditDataResult implements Serializable {
	public static String[][] AUDIT = { { "ALID", "getString" }, 
												{"EVENT_TIME", "getString" }, 
												{"AUDIT_EVENT", "getInt" },
												{"PTID", "getString" },
												{"PIID", "getString" },
												{"PROCESS_TEMPL_NAME", "getString" },
												{"PROCESS_INST_NAME", "getString" }, 
												{"TOP_LEVEL_PI_NAME", "getString" }, 
												{"TOP_LEVEL_PIID", "getString" }, 
												{"PARENT_PI_NAME", "getString" }, 
												{"PARENT_PIID", "getString" }, 
												{"VALID_FROM", "getString" }, 
												{"ACTIVITY_NAME", "getString" }, 
												{"ACTIVITY_KIND", "getInt" }, 
												{"ACTIVITY_STATE", "getInt" }, 
												{"CONTROL_LINK_NAME", "getString" }, 												
												{"IMPL_NAME", "getString" }, 
												{"PRINCIPAL", "getString" }, 
												{"TERMINAL_NAME", "getString" }, 
												{"EXCEPTION_TEXT", "getString" }
	};
								
	
	private Vector theResult=new Vector();
	
	public static String getSelect()
	{
		String theSelect="";
		String theColumnName=null;
		for (int i = 0; i < AUDIT.length; i++) {

			if (i>0)
				theSelect+=",";
				
			theSelect+=AUDIT[i][0];
		}
		
		return theSelect;
	}
	
	public int rowCount()
	{
		return theResult.size();
	}
	
	public Hashtable getRow(int index) {
		return (Hashtable) theResult.get(index);
	}
	public AuditDataResult(ResultSet aSQLResult)
	{
		try {
			while (aSQLResult.next())
			{
				Class[] parameterType = {String.class};
				Object[] theParameters = new Object[1];
				Hashtable aRow = new Hashtable(AUDIT.length);
				for (int theColumn = 0; theColumn < AUDIT.length; theColumn++) {
					Method theGetMethod = aSQLResult.getClass().getDeclaredMethod(AUDIT[theColumn][1], parameterType);					
					theParameters[0] = AUDIT[theColumn][0];
					Object theResult = theGetMethod.invoke(aSQLResult, theParameters);
					if (theResult != null)
						aRow.put(AUDIT[theColumn][0], theGetMethod.invoke(aSQLResult, theParameters));
				}
				theResult.add(aRow);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
