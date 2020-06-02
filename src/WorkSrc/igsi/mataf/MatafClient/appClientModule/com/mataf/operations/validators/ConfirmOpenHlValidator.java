package com.mataf.operations.validators;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.OperationXValidate;
import com.ibm.dse.base.types.DSETypeException;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConfirmOpenHlValidator implements OperationXValidate {

	/**
	 * @see com.ibm.dse.base.OperationXValidate#xValidate(Context)
	 */
	public String[] xValidate(Context ctx) 
	{
		//System.out.println("in xValidate");
		//System.out.println(ctx.getKeyedCollection());
		String[] errorMessages = new String[5];
		String str = null;
			
		try
		{
			str = (String)ctx.getValueAt("cheshbon");
			if(str==null)
			{
				errorMessages[0] = "חובה להקליד ערך בשדה חשבון.";
				return errorMessages;
			}
		}
		catch(DSEObjectNotFoundException e)
		{
			e.printStackTrace();
		}
		
		int total = 0;
		int length = str.length();			
		
		for (int i = length-1; i >= 0; i--)
		   total += Integer.parseInt(""+str.charAt(i)) * (length - i);
		
		int iRet = total % 11;
		
		if(iRet!=0) 
		{
			errorMessages[0] = "ספרת ביקורת שגויה בחשבון";
			return errorMessages;
		}
		
		return null;
	}

	/**
	 * @see com.ibm.dse.base.OperationXValidate#validate(String, DataField, Context)
	 */
	public void validate(String arg0, DataField arg1, Context arg2) throws DSETypeException {
	}

}
