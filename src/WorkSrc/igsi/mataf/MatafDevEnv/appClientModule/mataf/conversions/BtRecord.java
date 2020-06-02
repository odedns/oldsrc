package mataf.conversions;

import mataf.utils.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtRecord {

	BtField m_fields[];
	/**
	 * Constructor for BtRecord.
	 */
	public BtRecord() 
	{
		super();
	}

	/**
	 * construct a BtRecord from an array of
	 * BtFields.
	 * @param fields the array of fields constructing
	 * the record.
	 */
	public BtRecord(BtField fields[]) 
	{
		super();
		m_fields = fields;
	}
	
	
	/**
	 * Parse a BtRecord from a String.
	 * Return the updated field array with the 
	 * value updated.
	 * @param record the String representing the record.
	 * @return BtField[] an array of updated BtFields.
	 */
	public BtField[] parseRecord(String record)
	{
		return(null);
	}
	
	/**
	 * Parse a BtRecord from a String.
	 * Return the updated field array with the 
	 * value updated.
  	 * @param record the String representing the record.
  	 * @param fields the input array of BtFields.
	 * @return BtField[] an array of updated BtFields.
	 */
	public static BtField[] parseRecord(String record, BtField fields[])
	{
		/**
		 * loop over all fields and get the value for 
		 * the field.
		 * return the field list with the values;
		 */
		int index = 0;
		int lastIndex =0;
		int recordLen = record.length();
		String currVal = null;
		System.out.println("record length = " + record.length());
		for(int i=0; i < fields.length; ++i) {			
			lastIndex = index + fields[i].getLength();
			if(lastIndex > recordLen) {
				lastIndex = recordLen;
			}
			if(lastIndex < index) {
				index = lastIndex;
			}
			
			//System.out.println("parseRecord: index =" + index + 
			//		" lastIndex=" + lastIndex);
			currVal = record.substring(index,lastIndex);
//			currVal.trim();
			currVal = StringUtils.trim(currVal);
			fields[i].setValue(currVal);
			/*
			System.out.println("fields[" + i + "]= " + fields[i].getName() + " len : " + 
			fields[i].getLength() + " value: " + currVal);
			*/

			index = lastIndex + 1;
		}
		return(fields);
	}

}
