package mataf.decorator;

import java.io.IOException;

import com.ibm.connector2.sna.util.JavaExtensions;
import com.ibm.dse.base.CodeSetTranslator;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.FormatDecorator;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Vector;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FloatDecorator extends MataFixLengthDecorator {

	public static final int DEFAULT_FLOATING_POINT = 2;
	public static final char DEFAULT_EMPTY_PREFIX = 0;
	public static final boolean DEFAULT_SIGNED = true;
		
	private int flotingPoint = DEFAULT_FLOATING_POINT;
	private char emptyPrefix = DEFAULT_EMPTY_PREFIX;
	private boolean signed = DEFAULT_SIGNED;
	
	private boolean isNegative = false;
	
	/**
	 * Constructor for FloatDecorator.
	 */
	public FloatDecorator() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#addDecoration(String)
	 */
	public String addDecoration(String aString) {
		try {
			StringBuffer strBfr = new StringBuffer(aString);
			
			if(strBfr.length()>0) {
				if(strBfr.charAt(0)=='-') {
					isNegative = true;
					strBfr.deleteCharAt(0);
				} else if(strBfr.charAt(0)=='+') {
					strBfr.deleteCharAt(0);
				}
				
				int indexOfPoint = aString.indexOf(".");
				return getDecoratedFloat(indexOfPoint, strBfr);
			} else { // handel null value
				fixLength(strBfr, getNullPad(), getLength(), true);
				if(getEmptyPrefix()!=DEFAULT_EMPTY_PREFIX) {
					byte[] stringBytes = String.valueOf(getEmptyPrefix()).getBytes(getToCodeSet());
					strBfr.insert(0, new String(stringBytes, getFromCodeSet()));
				}
				return strBfr.toString();
			}
		} catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private String getDecoratedFloat(int indexOfFloatingPoint, StringBuffer strBfr) throws IOException {
		if(indexOfFloatingPoint != -1) {
			strBfr.deleteCharAt(indexOfFloatingPoint);
			fixLength(strBfr, '0', indexOfFloatingPoint+getFlotingPoint(), true);
		} else {
			fixLength(strBfr, '0', strBfr.length()+getFlotingPoint(), true);
		}
		fixLength(strBfr, getPadCharacter(), getLength(), false);
		trySetSign(strBfr);
		byte[] stringBytes = strBfr.toString().getBytes(getToCodeSet());
		return new String(stringBytes, getFromCodeSet());
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#removeDecoration(String)
	 */
	public String removeDecoration(String aString) throws DSEInvalidArgumentException {
		StringBuffer strBfr = new StringBuffer(aString);
		if(aString.length()>flotingPoint) {			
			int index2putPoint = aString.length()-flotingPoint;
			strBfr.insert(index2putPoint, '.');
		}
		
		return strBfr.toString();
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#toStrings()
	 */
	public Vector toStrings() {
		Vector v = new Vector();
		if (getDecorated()!=null) 
			v.add(getDecorated().toStrings());
		String s="<"+getTagName()+" ";
		s=JavaExtensions.addAttribute(s,"floatingPoint",String.valueOf(getFlotingPoint()));
		s=JavaExtensions.addAttribute(s,"signed",String.valueOf(isSigned()));
		s=s+">";
		v.addElement(s);
		return v;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) {
		super.initializeFrom(aTag);
		
		for (int i=0; i<aTag.getAttrList().size(); i++)
		{
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i); 
			if (attribute.getName().equals("floatingPoint")) 
				setFlotingPoint(Integer.parseInt((String) attribute.getValue()));
			if (attribute.getName().equals("signed")) 
				setSigned(Boolean.valueOf((String) attribute.getValue()).booleanValue());
			if (attribute.getName().equals("emptyPrefix")) 
				setEmptyPrefix(((String) attribute.getValue()).charAt(0));
		}
		
		return this;
	}

	/**
	 * Returns the flotingPoint.
	 * @return int
	 */
	public int getFlotingPoint() {
		return flotingPoint;
	}

	/**
	 * Sets the flotingPoint.
	 * @param flotingPoint The flotingPoint to set
	 */
	public void setFlotingPoint(int flotingPoint) {
		this.flotingPoint = flotingPoint;
	}

	/**
	 * Returns the signed.
	 * @return boolean
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * Sets the signed.
	 * @param signed The signed to set
	 */
	public void setSigned(boolean signed) {
		this.signed = signed;
	}
	
	/**
	 * Returns the emptyPrefix.
	 * @return char
	 */
	public char getEmptyPrefix() {
		return emptyPrefix;
	}

	/**
	 * Sets the emptyPrefix.
	 * @param emptyPrefix The emptyPrefix to set
	 */
	public void setEmptyPrefix(char emptyPrefix) {
		this.emptyPrefix = emptyPrefix;
	}

	private void trySetSign(StringBuffer strBfr) {
		if(strBfr.length()!=0) {
			if(isSigned()) {
				if(isNegative) {
					strBfr.insert(0, "-");
				} else {
					strBfr.insert(0, "+");
				}			
			}
		}
	}
	
	private void fixLength(StringBuffer strBfr, char padChar, int lengthParam, boolean justifyRight) {
		// sub chars
		if(strBfr.length()>lengthParam) {
			if(justifyRight) {
				strBfr.delete(lengthParam, strBfr.length());
			} else {
				strBfr.delete(0, strBfr.length()-lengthParam);
			}
		} else { // append chars
			int chars2append = lengthParam-strBfr.length();
			for(int counter=0 ; counter<chars2append ; counter++ ) {
				if(justifyRight) 
				{
						strBfr.append(padChar);
				} else {
					strBfr.insert(0, padChar);
				}
			}
		}
	}
	
	/**
	 * Returns the isNegative.
	 * @return boolean
	 */
	public boolean isNegative() {
		return isNegative;
	}

	/**
	 * Sets the isNegative.
	 * @param isNegative The isNegative to set
	 */
	public void setIsNegative(boolean isNegative) {
		this.isNegative = isNegative;
	}

}
