package mataf.slika.operations;

import java.awt.Cursor;
import java.io.IOException;
import java.util.Vector;

import mataf.general.operations.MatafClientOp;
import mataf.services.chequereader.CRException;
import mataf.services.chequereader.CheckReaderLANDP;
import mataf.services.reftables.RefTables;
import mataf.services.reftables.RefTablesService;

//import mataf.services.chequereader.CheckReaderLANDP;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.desktop.Desktop;
import com.mataf.dse.appl.OpenDesktop;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ReadCheckDetailsClientOp extends MatafClientOp {

	public static final String CHECK_DETAILS_INSERTED_BY_READER = "0";
	
	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {				
		new Thread(){public void run(){
			
		try {
			System.out.println("Pre-Death State");
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
									
			CheckReaderLANDP crlService=(CheckReaderLANDP)getService("chequeReader");
			try
			{
				crlService.init();		
			}
			catch(CRException e)
			{
				System.out.println(e.getMessage());
				return;
			}
			
			try
			{
				String checkData = crlService.read();
				System.out.println("Returned from ChqReader: "+checkData);
	
				addCheckDataToContext(checkData);
				
				fireHandleOperationRepliedEvent(new OperationRepliedEvent(ReadCheckDetailsClientOp.this));
			}
			catch(CRException e)
			{
				switch(e.GetErrorCode())
				{
					case CRException.CRE_ERR_DOC_WRONGLY_FED:  //1 - בעיה בקריאת נתונים, הקש ידנית
															   addToErrorList("appMsgs","3000"); 
															   break;
															  
					case CRException.CRE_TIMEOUT: 			   //2 - לא הועבר שק
												  			   addToErrorList("appMsgs","3001"); 
												  			   break;
												  			   
					case CRException.CRE_READING_ABROTED:      //3 - הפעולה הופסקה ע"י מקש
															   addToErrorList("appMsgs","3002"); 
												  			   break;
												  			   
					case CRException.CRE_ERR_LOADING_OPTIONS:  //4 - תצורה לא מתאימה לקורא
															   addToErrorList("appMsgs","3003"); 
												  			   break;
												  			   
					case CRException.CRE_ERR_BAD_CHARACTER:    //5 - תו שגוי
															   addToErrorList("appMsgs","3004"); 
												  			   break;
												  			   
					case CRException.CRE_LANDP_OPEN_FAILED:    //6 - קורא שיקים אינו מחובר או תפוס
															   addToErrorList("appMsgs","3005"); 
												  			   break;
												  			   
					case CRException.CRE_ERR_FRAME_ERROR:      //7 - בעיה בקריאת נתוני השק, נסה שנית
															   addToErrorList("appMsgs","3006"); 
												  			   break;
												  			   
					case CRException.CRE_ERR_DOC_STUCK_INSIDE: //8 - מסמך תקוע, נא הוצא שק מהקורא
															   addToErrorList("appMsgs","3007"); 
												  			   break;
												  			   
					default: /*Problem reading the cheque*/  //בעיה בקריאת השק
															  addToErrorList("appMsgs","3008"); 
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();		
			}
			
			try
			{
				crlService.close();
			}
			catch(CRException e)
			{
				System.out.println(e.getMessage());
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}}}.start();
	}
	
	
	/**
	 * Method parses the check data and adds it to the table's 
	 * Indexed Collection.
	 * @param checkData The String representing the check data recieved from the check reader.
	 * @throws DSEException
	 */
	private void addCheckDataToContext(String checkData) throws DSEException
	{
		IndexedCollection iColl = 
			(IndexedCollection)getElementAt("CZSS_T110_LIST");
		
		KeyedCollection checkDataKColl = null;
		try
		{
			checkDataKColl = 
				(KeyedCollection)((KeyedCollection)iColl.getElements().get(0)).clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		
		// Fill KeyedCollection with the check data.
		insertCheckDataToKColl(checkDataKColl, checkData);
		
		// Overwrite last row with check's data. 
		com.ibm.dse.base.Vector iCollElements = (com.ibm.dse.base.Vector)iColl.getElements();
		iCollElements.setElementAt(checkDataKColl, iCollElements.size()-1);
	}
	
	/**
	 * Method fits the data recieved from the check reader into
	 * the right places in the KeyedCollection.
	 * 
	 * @param kColl - The KeyedCollection to be filled with the check data.
	 * @param checkData - The String representing the check data recieved from the check reader.
	 * @throws DSEException - Thrown if the KeyedCollection type is not compatiable.
	 */
	private void insertCheckDataToKColl(KeyedCollection kColl, String checkData) throws DSEException
	{
		// Lengths of the chunks of data to parse each iteration.
		int[] indexOffsets = new int[]{2,7,1,2,1,3,2,1,9};
		
		// Corresponding keys in the KeyedCollection to insert the data.
		String[] fields = new String[]{null,"CH_MISPAR_CHEQ",null,"CH_BANK_CHOTEM",
										null,"CH_SNIF_CHOTEM","CH_SNIF_S_B",null,"CH_CH_CHOTEM"};
		kColl.setValueAt("CH_SCHUM_CHEQ"," ");
		
		// Parse the check data into the KeyedCollection.
		for(int i=0,index=0;i<indexOffsets.length;i++)
		{
			if(fields[i]==null)
			{
				index+=indexOffsets[i];
				continue;
			}
			
			String value = checkData.substring(index,index+indexOffsets[i]);
			kColl.setValueAt(fields[i],value);
			index+=indexOffsets[i];
		}
	}
}
