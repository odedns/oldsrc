package com.mataf.operations;

import mataf.services.chequereader.ChequeMateReader;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.FormatElement;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ChequeReadClientOp extends DSEClientOperation 
{
	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception 
	{
		
		//super.execute();
		ChequeMateReader cmr=(ChequeMateReader)getService("chequeReader");
		char c1=0x2;
		char c2=0x3;
		
		cmr.simpleWriter.write(c1+"A"+c2);
//		String message = cmr.readAndWait();
		
		cmr.readAndUnformat(getContext(),(FormatElement)getFormat("ChequeReaderFMT"));
		System.out.println(getKeyedCollection());
		
	}


}
