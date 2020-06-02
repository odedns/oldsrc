package tests.operations;

import javax.naming.SizeLimitExceededException;

import mataf.data.VisualDataField;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Settings;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckSemelDivuachUploadOpStepTest extends TestCase {

	// The server operation to test
	private DSEServerOperation serverOperation;
	
	/**
	 * Constructor for CheckSemelDivuachUploadOpStepTest.
	 * @param arg0
	 */
	public CheckSemelDivuachUploadOpStepTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		
		junit.textui.TestRunner.run(CheckSemelDivuachUploadOpStepTest.class);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		init();		
		serverOperation = (DSEServerOperation) DSEOperation.readObject("checkTotalAmountSlServerOp");
	}
	
	private void init() throws Exception {
		KeyedCollection settingsKcoll = Settings.getSettings();
		if(settingsKcoll==null) {
			Context.reset();
			Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
		}
	}
	
	public void testExecuteOp1() {
		try {	
			
			initFieldsForTestExecuteOp("105", "мъ", "410", "T", "41000", Boolean.FALSE);
						
			serverOperation.execute();
			
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(TL_DIVUACH_HOST, 98, "");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public void testExecuteOp2() {
		try {	
			
			initFieldsForTestExecuteOp("105", "мъ", "410", "T", "39999", Boolean.FALSE);
			
			serverOperation.execute();
				
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(TL_DIVUACH_HOST, 99, "0337");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public void testExecuteOp3() {
		try {	
			
			initFieldsForTestExecuteOp("105", "р", "410", "T", "41000", Boolean.FALSE);
			
			serverOperation.execute();
			
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(!TL_DIVUACH_HOST, 0, "");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public void testExecuteOp4() {
		try {	
			
			initFieldsForTestExecuteOp("106", "ц", "410", "T", "41000", Boolean.FALSE);
			
			serverOperation.execute();
			
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(!TL_DIVUACH_HOST, 0, "");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public void testExecuteOp5() {
		try {	
			
			initFieldsForTestExecuteOp("105", "ц", "051", "T", "41000", Boolean.FALSE);
						
			serverOperation.execute();
			
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(TL_DIVUACH_HOST, 98, "");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	public void testExecuteOp6() {
		try {	
			
			initFieldsForTestExecuteOp("105", "ц", "051", "T", "39999", Boolean.FALSE);
			
			serverOperation.execute();
				
			boolean TL_DIVUACH_HOST = ((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).isEnabled();
			validateFeedbackValues(TL_DIVUACH_HOST, 99, "0437");
			
		} catch(DSEObjectNotFoundException ex) {
			fail(ex.getMessage());
		} catch(Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	private void initFieldsForTestExecuteOp(String accountType, 
											 String kodIfyun,
											 String kodPeula,
											 String nosePeula,
											 String totalAmount,
											 Boolean isDivuachHostEnabled) 
											 	throws DSEInvalidArgumentException, DSEObjectNotFoundException {
		// Account type that should enable/disable to insert semel divuach
		serverOperation.setValueAt("AccountType", accountType);
		// Account property that should enable/disable to insert semel divuach 
		serverOperation.setValueAt("AccountBalanceHostReplyData.GKSG_IFYUN", kodIfyun);
		// SugPeula - zchut/chova
		serverOperation.setValueAt("taskAttributes.trxId", kodPeula);
		serverOperation.setValueAt("KodNosePeulaEn", nosePeula);
		// Total amount that is more/less then the amount in the table TLST_PALTASH in field TL_SCHUM_GVUL1
		serverOperation.setValueAt("TotalAmount", totalAmount);
		// Set the default property for the VisualDataField named TL_DIVUACH_HOST			
		((VisualDataField) serverOperation.getElementAt("TL_DIVUACH_HOST")).setIsEnabled(isDivuachHostEnabled);
	}
	
	private void validateFeedbackValues(boolean expectedEnabledDivuachHost,
										 int expectedLengthOfList,
										 String expectedValueOfSemelDivuach) 
										 	throws DSEObjectNotFoundException {
		// 1st check
		assertTrue("Visual field named TL_DIVUACH_HOST is not enabled or disabled where it should be.", expectedEnabledDivuachHost);
		
		// 2nd check
		int lengthOfList = ((IndexedCollection) serverOperation.getElementAt("SemelDivuachList")).size();
		assertEquals("Length of SemelDivuachList is not as it should be. ", lengthOfList, expectedLengthOfList);
		
		// 3rd check
		String defaultValueOfSemelDivuach = (String) serverOperation.getValueAt("TL_DIVUACH_HOST");
		assertEquals("Semel divuach value is not as it should be.", defaultValueOfSemelDivuach, expectedValueOfSemelDivuach);
		
		// check for general errors
		IndexedCollection businessMessagesList = (IndexedCollection) serverOperation.getElementAt("BusinessMessagesList");
		if(businessMessagesList.size()>0) {
			fail((String) ((KeyedCollection)businessMessagesList.getElementAt(0)).getValueAt("BusinessMessage"));
		}
	}
		
}
