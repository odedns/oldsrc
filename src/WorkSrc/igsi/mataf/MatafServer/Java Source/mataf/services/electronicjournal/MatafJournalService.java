package mataf.services.electronicjournal;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Enumeration;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInternalErrorException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.services.jdbc.DSESQLException;
import com.ibm.dse.services.jdbc.JDBCJournal;

/**
 * @author yossid
 *
 * Additional to the inherited class JDBCJournal, this class is:
 * 1.  Increase the journal counter for in the global records.
 * 2.  Increase/Decrease the counter for records that should be inquired
 * 	   (Every transaction commited in the system is writen in the journal
 *     in one or more records. The inquireCounter holds the transaction 
 *     that should be inquire later because an error was returned from the CICS
 *     while trying to commit them)
 * 
 * It's very important to add to the declaration of the service in the dsesrvce.xml
 * the 4 parameters: counterFieldName, inquireCounterFieldName, counterDecimalFormat 
 * & inquireCounterDecimalFormat that will be set as members
 * in this Service (see the notes for those members). 
 */
public class MatafJournalService extends JDBCJournal {

	/**
	 * Indicate that the counter should be increase
	 */
	public static final String DECREASE = "dec";
	
	/**
	 * Indicate that the counter should be decrease
	 */
	public static final String INCREASE = "inc";
	
	/**
	 * Attribute name of the counter field name as should be 
	 * added in the configuration of this service in dsesrvce.xml
	 */
	public static final String COUNTER_FIELD_NAME_ATT_NAME = "counterFieldName";
	
	/**
	 * Attribute name of the inquire counter field name as should be 
	 * added in the configuration of this service in dsesrvce.xml
	 */
	public static final String INQUIRE_COUNTER_FIELD_NAME_ATT_NAME = "inquireCounterFieldName";
	
	/**
	 * Attribute name of the counter decimal format as should be 
	 * added in the configuration of this service in dsesrvce.xml
	 */
	public static final String COUNTER_DECIMAL_FORMAT_ATT_NAME = "counterDecimalFormat";
	
	/**
	 * Attribute name of the inquire counter decimal format as should be 
	 * added in the configuration of this service in dsesrvce.xml
	 */
	public static final String INQUIRE_COUNTER_DECIMAL_FORMAT_ATT_NAME = "inquireCounterDecimalFormat";
	
	/** 
	 * holding the CURRENT generaition of the journal. 
	 */
	public static final int CURRENT_GENERATION = 0;
	
	/** 
	 * This member holding the name of the journal counter.
	 * it dosn't holding the counter value itself because it can be
	 * set to zero by the runtime
	 */
	private String counterFieldName = null;
	
	/** 
	 * This member holding the name of the transactions to inquire counter.
	 */
	private String inquireCounterFieldName = null;
	
	/** 
	 * This member holding the TextFormat of the counter, namely,
	 * the way that this counter should be set in the context.
	 */
	private String counterDecimalFormat = null;	
	
	/** 
	 * This member holding the TextFormat of the inquire counter, namely,
	 * the way that this counter should be set in the context.
	 */
	private String inquireCounterDecimalFormat = null;
	
	/**
	 * Increase all the counters while adding record to the journal.
	 */
	public void increaseCounters(Context context) throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		setCounter(context, counterFieldName, counterDecimalFormat, INCREASE);
		setCounter(context, inquireCounterFieldName, inquireCounterDecimalFormat, INCREASE);
	}
	
	/**
	 * This method can be in use by any counter type.
	 * It's actually increase a counter and updating the runtime with this counter.
	 * Parameters:
	 * 		context - the context to set the counter field from
	 * 		counterFieldNameParam - the counter field name in hte context
	 * 		counterDecimalFormatParam - the format the the counter should be set
	 * Throws:
	 * 		DSEObjectNotFoundException - if the counter field could not be retrieve from the context.
	 * 		DSEInvalidArgumentException - if the counter field could not be set in the context.
	 */
	private void setCounter(Context context, String counterFieldNameParam, String counterDecimalFormatParam, String action) 
									throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		// get the journal counter
		String counterStr = (String) context.getValueAt(counterFieldNameParam);
		int counter = Integer.parseInt(counterStr);
		
		if(action.equals(INCREASE)) // increase the counter
			counter++;
		else // decrease the counter
			counter--;
		
		// format the counter in a way it should be in the context
		DecimalFormat decimalFormat = new DecimalFormat(counterDecimalFormatParam);
		String formatedCounter = decimalFormat.format(counter);
		
		// set it in the context
		context.setValueAt(counterFieldNameParam, formatedCounter);	
	}
		

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException {
		super.initializeFrom(aTag);
		
		for(Enumeration enumeration = aTag.getAttrList().elements(); enumeration.hasMoreElements();)
        {
            TagAttribute tagAttribute = (TagAttribute)enumeration.nextElement();
            if(tagAttribute.getName().equals(COUNTER_FIELD_NAME_ATT_NAME))
                setCounterFieldName((String)tagAttribute.getValue());
            else if(tagAttribute.getName().equals(INQUIRE_COUNTER_FIELD_NAME_ATT_NAME))
                setInquireCounterFieldName((String)tagAttribute.getValue());
            else if(tagAttribute.getName().equals(COUNTER_DECIMAL_FORMAT_ATT_NAME))
                setCounterDecimalFormat((String)tagAttribute.getValue());
            else if(tagAttribute.getName().equals(INQUIRE_COUNTER_DECIMAL_FORMAT_ATT_NAME))
                setInquireCounterDecimalFormat((String)tagAttribute.getValue());
        }
        
        return this;
	}

	/**
	 * Returns the counterDecimalFormat.
	 * @return String
	 */
	public String getCounterDecimalFormat() {
		return counterDecimalFormat;
	}

	/**
	 * Returns the counterFieldName.
	 * @return String
	 */
	public String getCounterFieldName() {
		return counterFieldName;
	}

	/**
	 * Returns the inquireCounterDecimalFormat.
	 * @return String
	 */
	public String getInquireCounterDecimalFormat() {
		return inquireCounterDecimalFormat;
	}

	/**
	 * Returns the inquireCounterFieldName.
	 * @return String
	 */
	public String getInquireCounterFieldName() {
		return inquireCounterFieldName;
	}

	/**
	 * Sets the counterDecimalFormat.
	 * @param counterDecimalFormat The counterDecimalFormat to set
	 */
	public void setCounterDecimalFormat(String counterDecimalFormat) {
		this.counterDecimalFormat = counterDecimalFormat;
	}

	/**
	 * Sets the counterFieldName.
	 * @param counterFieldName The counterFieldName to set
	 */
	public void setCounterFieldName(String counterFieldName) {
		this.counterFieldName = counterFieldName;
	}

	/**
	 * Sets the inquireCounterDecimalFormat.
	 * @param inquireCounterDecimalFormat The inquireCounterDecimalFormat to set
	 */
	public void setInquireCounterDecimalFormat(String inquireCounterDecimalFormat) {
		this.inquireCounterDecimalFormat = inquireCounterDecimalFormat;
	}

	/**
	 * Sets the inquireCounterFieldName.
	 * @param inquireCounterFieldName The inquireCounterFieldName to set
	 */
	public void setInquireCounterFieldName(String inquireCounterFieldName) {
		this.inquireCounterFieldName = inquireCounterFieldName;
	}
	
	public void decreaseInquireCounter(Context context) throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		setCounter(context, inquireCounterFieldName, inquireCounterDecimalFormat, DECREASE);
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
				return(super.today());	
			}
			date = currentGenerationDate();
		} catch(Exception e) {
			GLogger.error(this.getClass(), null, null, e, false);
		}
		GLogger.debug("today : " + date);
		return(date);
	}
	
	public int getPreviousGenerationNumber(int numberOfPreviousGeneration) throws DSESQLException, DSEInternalErrorException{
		int currentGenerationNumber = getWrapNumber();
		int previousGenerationNumber = currentGenerationNumber - numberOfPreviousGeneration;
		if (previousGenerationNumber > 0) {
			return previousGenerationNumber;
		} else if (previousGenerationNumber == 0) {
			return getNumberOfGenerations();
		} else if (previousGenerationNumber < 0) {
			return getNumberOfGenerations()+previousGenerationNumber;
		}
		return -1;
	}

}
