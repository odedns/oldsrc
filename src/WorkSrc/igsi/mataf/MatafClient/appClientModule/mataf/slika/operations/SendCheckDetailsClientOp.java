package mataf.slika.operations;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.desktop.Desktop;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafClientOp;
import mataf.halbanathon.operationsteps.SetIshurMenahel;
import mataf.utils.GLogger;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SendCheckDetailsClientOp extends MatafClientOp {

	public static final int ROW_EMPTY = 1;
	public static final int REQUIRED_FIELDS_EMPTY = 2;
	public static final int ERRORS_IN_FIELDS = 3;
	public static final int ROW_IS_VALID = 4;
	
	private boolean atLeastOneRow = false;
	private boolean isChecksTableValid;
	
	/**
	 * @see mataf.general.operations.MatafClientOp#preSend2hostValidation()
	 */
	public boolean preSend2hostValidation() throws Exception 
	{
		if (isChecksRowsValid() && atLeastOneRow)
		{
			setChecksTableValid(true);
			return true;
		}
		else
		{
			setChecksTableValid(false);
			return false;
		}
	}
	
	private boolean isChecksRowsValid() throws DSEObjectNotFoundException {
		VisualDataField checkNumberField, bankField, snifField, sbField, accountNumberField, ammountField;
		IndexedCollection icoll = (IndexedCollection) getElementAt("CZSS_T110_LIST");
		for( int counter=0 ; counter<icoll.size() ; counter++ ) {
			checkNumberField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_MISPAR_CHEQ");
			bankField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_BANK_CHOTEM");
			snifField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_SNIF_CHOTEM");
			sbField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_SNIF_S_B");
			accountNumberField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_CH_CHOTEM");
			ammountField = (VisualDataField) getElementAt("CZSS_T110_LIST."+counter+".CH_SCHUM_CHEQ");
			
			int rowInd = checkRow(checkNumberField, bankField, snifField, sbField, accountNumberField, ammountField);
			if(rowInd==ROW_EMPTY) 
			{
				if(!atLeastOneRow)
					addToErrorList("טבלה ריקה");
				break;
			} 
			else 
				if(rowInd==REQUIRED_FIELDS_EMPTY) 
				{
					addToErrorList("שדות חובה חסרים");
					return false;
				} 
				else 
					if(rowInd==ERRORS_IN_FIELDS) 
					{
						addToErrorList("ערכים לא תקינים בטבלה");
						return false;
					}
			atLeastOneRow = true;			
		}
		return true;		
	}
	
	/** In future this method will b removed because only rows that shown in screen
	 *  will b in the IndexedCollection. 
	 *  Today, there is final size of 50 checks in the IndexedCollection.
	 */
	private int checkRow(VisualDataField checkNumberField, 
								 VisualDataField bankField, 
								 VisualDataField snifField, 
								 VisualDataField sbField, 
								 VisualDataField accountNumberField, 
								 VisualDataField ammountField) 
								 	throws DSEObjectNotFoundException {
				
		boolean isCheckNumberEmpty = ((String) checkNumberField.getValue()).trim().length()==0;
		boolean isBankEmpty = ((String) bankField.getValue()).trim().length()==0;
		boolean isSnifEmpty = ((String) snifField.getValue()).trim().length()==0;
		boolean isSbEmpty = ((String) sbField.getValue()).trim().length()==0;
		boolean isAccountNumberEmpty = accountNumberField.getValue().toString().trim().length()==0;
		String ammountStr = ((String) ammountField.getValue()).trim();
		boolean isAmmountEmpty = true;
		int ammount = 0;
		if(ammountStr.length()>0) 
		{
			try
			{
				ammount = Integer.parseInt(ammountStr);
			}
			catch(NumberFormatException e)
			{
				return ERRORS_IN_FIELDS;
			}
			isAmmountEmpty = ammount==0;
		}
						
		if(isCheckNumberEmpty && isBankEmpty && isSnifEmpty && isSbEmpty && isAccountNumberEmpty && isAmmountEmpty) {
			return ROW_EMPTY;
		}
		if((isCheckNumberEmpty && checkNumberField.isRequired()) ||
			(isBankEmpty && bankField.isRequired()) ||
			(isSnifEmpty && snifField.isRequired()) ||
			(isSbEmpty && sbField.isRequired()) ||
			(isAccountNumberEmpty && accountNumberField.isRequired()) ||
			(isAmmountEmpty &&  ammountField.isRequired()) ) {
			return REQUIRED_FIELDS_EMPTY;
		}
		if(checkNumberField.isInErrorFromServer() ||
			bankField.isInErrorFromServer() ||
			snifField.isInErrorFromServer() ||
			sbField.isInErrorFromServer() ||
			accountNumberField.isInErrorFromServer() ||
			ammountField.isInErrorFromServer() ) {
			return ERRORS_IN_FIELDS;
		}		
		
		return ROW_IS_VALID;
	}

	/**
	 * @return
	 */
	public boolean isChecksTableValid() {
		return isChecksTableValid;
	}

	/**
	 * @param b
	 */
	public void setChecksTableValid(boolean b) {
		isChecksTableValid = b;
	}

}
