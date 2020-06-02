package mataf.conversions;

import com.ibm.dse.base.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import java.io.*;
import java.util.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FldHandler extends WBBasicHandler {

	String lenAfterDot = null;

	/**
	 * Constructor for FldHandler.
	 * @param eInfo
	 */
	public FldHandler() 
	{
		super();
	}

	public void setEntityInfo(BtEntityInfo eInfo) 
	{
		super.setEntityInfo(eInfo);
	}
	/**
	 *
	 */
	public void handleAttributes(Entity ent, Instance ins, String name, String value)
	{
	
	
		
		if(name.equals("lenAfterDot")) {
			lenAfterDot = value;	
			return;
		}
		/* handle length */
		if(name.equals("lenBeforeDot")) {
			String len=null;
			name = "length";
			if(lenAfterDot != null) {
				len = value + "." + lenAfterDot;
				value = len;		
			}	
		}
		/**
		 * convert magic types to WB types
		 * according to hash table.
		 */
		if(name.equals("type")) {
			// convert types;	
			String type = ValueConversions.getFieldType(value.trim());
			if(null == type) {
				type = value;	
			}
			value = type;

		}				
		System.out.println("FldHandler name=" + name + " value=" + value);
		ins.setValue(ent.getAttribute(name),value);

		
	}



}
