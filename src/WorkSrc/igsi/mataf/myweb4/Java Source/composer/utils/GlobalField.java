package composer.utils;

import com.ibm.dse.base.*;

/**
 * @author Oded Nissan 22/07/2003
 * This class represents a global field, which will
 * be synchronized with the RT system.
 * The field contains a code that identifies it in the 
 * proxy request sent from the RT.
 * When synching data from the composer to the RT, we will
 * use the field's code to identify it in the proxy request
 * sent to the RT system.
 */
public class GlobalField extends DataField {
	int m_code;
	
	/**
	 * Initializes an instance of GlobalField with its attributes.
	 * @return Object
	 * @param aTag Tag
	 * @exception java.io.IOException
	 */
	public Object initializeFrom(Tag aTag) throws java.io.IOException 
	{
		super.initializeFrom(aTag);
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			if (attribute.getName().equals("code")) {
				String value = (String) attribute.getValue();
				int code = Integer.parseInt(value);
				setCode(code);
			} //if
		}	// for
		return this;
	}
		
	/**
	 * set the fields code id.
	 * @param code the field code.
	 */
	public void setCode(int code)
	{
		m_code = code;
	}
		
	/**
	 * get the field code.
	 * @return int the field code.
	 */
	public int getCode()
	{
		return(m_code);
	}

}
