package mataf.format;

import java.io.IOException;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.FormatExternalizer;
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
public class StringWithCharSetFormat extends FormatElement {

	/**
	 * Constructor for TestFormatElement.
	 */
	public StringWithCharSetFormat() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.FormatElement#format(DataElement)
	 */
	public String format(DataElement aDataElement) throws DSEInvalidClassException, DSEInvalidRequestException, DSEInvalidArgumentException {
		return (String) aDataElement.getValue();
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException {
		for (int i=0; i<aTag.getAttrList().size(); i++) 
		{
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);
			if (attribute.getName().equals(com.ibm.dse.base.Constants.DataName))
				setDataElementName( (String) (attribute.getValue()) );
		}
		return this;
	}

	/**
	 * @see com.ibm.dse.base.FormatElement#unformat(String, DataElement)
	 */
	public DataElement unformat(String aString, DataElement aDataElement) throws DSEInvalidRequestException, DSEInvalidArgumentException {
		aDataElement.setValue(aString);
		return aDataElement;
	}

}
