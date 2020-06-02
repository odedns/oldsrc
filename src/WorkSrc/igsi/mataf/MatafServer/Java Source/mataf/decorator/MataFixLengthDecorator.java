package mataf.decorator;

import java.io.IOException;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.FixedLength;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MataFixLengthDecorator extends FixedLength {

	public static final String DEFAULT_TO_CODE_SET_TRANS = com.ibm.dse.base.Constants.Cp037;
	public static final String DEFAULT_FROM_CODE_SET_TRANS = com.ibm.dse.base.Constants.N8859_1;
	public static final char DEFAULT_NULL_PAD_CHAR = 255; // 0xFF
	public static final boolean DEFAULT_TRANS_NULL_PAD = false;
	
	private char nullPad = DEFAULT_NULL_PAD_CHAR;
	private boolean transNullPad = DEFAULT_TRANS_NULL_PAD;
	private String toCodeSet = DEFAULT_TO_CODE_SET_TRANS;
	private String fromCodeSet = DEFAULT_FROM_CODE_SET_TRANS;
	
	/**
	 * Constructor for MataFixLengthDecorator.
	 */
	public MataFixLengthDecorator() {
		super();
	}

	/**
	 * Constructor for MataFixLengthDecorator.
	 * @param arg0
	 * @throws IOException
	 */
	public MataFixLengthDecorator(String arg0) throws IOException {
		super(arg0);
	}
	
	/**
	 * @see com.ibm.dse.base.FormatDecorator#addDecoration(String)
	 */
	public String addDecoration(String aString) {
		try {
			if(aString.length()==0) {
				StringBuffer strBfr = new StringBuffer();
				for(int counter=0 ; counter<getLength() ; counter++ ) {
					strBfr.append(nullPad);
				}
				
				if(isTransNullPad()) {
					byte[] stringBytes = strBfr.toString().getBytes(getToCodeSet());
					return new String(stringBytes, getFromCodeSet());
				} else { // sent the data AS IS without translating it 2 EBCIDIC
					return strBfr.toString();			
				}
			} else {
				byte[] stringBytes = super.addDecoration(aString).getBytes(getToCodeSet());
				return new String(stringBytes, getFromCodeSet());
			}
		} catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) {
		super.initializeFrom(aTag);
		for (int i=0; i<aTag.getAttrList().size(); i++)
		{
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i); 
			if (attribute.getName().equals("nullPad"))
				setNullPad((char)Integer.parseInt((String) attribute.getValue()));
			if (attribute.getName().equals("transNullPad"))
				setTransNullPad(Boolean.valueOf((String) attribute.getValue()).booleanValue());
			
		}
		return this;
	}

	/**
	 * Returns the nullPad.
	 * @return char
	 */
	public char getNullPad() {
		return nullPad;
	}

	/**
	 * Sets the nullPad.
	 * @param nullpad The nullPad to set
	 */
	public void setNullPad(char nullPad) {
		this.nullPad = nullPad;
	}

	/**
	 * Returns the transNullPad.
	 * @return boolean
	 */
	public boolean isTransNullPad() {
		return transNullPad;
	}

	/**
	 * Sets the transNullPad.
	 * @param transNullPad The transNullPad to set
	 */
	public void setTransNullPad(boolean transNullPad) {
		this.transNullPad = transNullPad;
	}

	/**
	 * Returns the fromCodeSet.
	 * @return String
	 */
	public String getFromCodeSet() {
		return fromCodeSet;
	}

	/**
	 * Returns the toCodeSet.
	 * @return String
	 */
	public String getToCodeSet() {
		return toCodeSet;
	}

	/**
	 * Sets the fromCodeSet.
	 * @param fromCodeSet The fromCodeSet to set
	 */
	public void setFromCodeSet(String fromCodeSet) {
		this.fromCodeSet = fromCodeSet;
	}

	/**
	 * Sets the toCodeSet.
	 * @param toCodeSet The toCodeSet to set
	 */
	public void setToCodeSet(String toCodeSet) {
		this.toCodeSet = toCodeSet;
	}

}
