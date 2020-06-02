package mataf.format;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.FieldFormat;
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
public class DateStringFormat extends FieldFormat {

	private SimpleDateFormat sourcePattern = null;
	private SimpleDateFormat targetPattern = null;
	
	/**
	 * Constructor for DateStringFormat.
	 */
	public DateStringFormat() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#formatField(DataField)
	 */
	public String formatField(DataField aDataField) throws DSEInvalidArgumentException, DSEInvalidClassException {
		String dateAsString  = null;
		Date date = null;
		String formatedDate = null;
		try {
			dateAsString  = (String) aDataField.getValue();
			date = sourcePattern.parse(dateAsString);
			formatedDate = targetPattern.format(date);
		} catch(ParseException ex) {
			String errMsg = "Exception while tring 2 format date: dateAsString='"+
								dateAsString+"' , formatedDate='"+formatedDate+"'";
			ex.printStackTrace();
			throw new DSEInvalidArgumentException(null, null, errMsg);
		}
		return formatedDate;
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#unformatField(String, DataField)
	 */
	public DataField unformatField(String dateAsString, DataField aDataField) throws DSEInvalidArgumentException {
		Date date = null;
		String formatedDate = null;
		try {
			date = sourcePattern.parse(dateAsString);
			formatedDate = targetPattern.format(date);
			aDataField.setValue(formatedDate);
		} catch(ParseException ex) {
			String errMsg = "Exception while tring 2 format date: dateAsString='"+
								dateAsString+"' , formatedDate='"+formatedDate+"'";
			ex.printStackTrace();
			throw new DSEInvalidArgumentException(null, null, errMsg);
		}
		return aDataField;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException {
		setDataElementName("");
        for(int i = 0; i < aTag.getAttrList().size(); i++)
        {
            TagAttribute attribute = (TagAttribute)aTag.getAttrList().elementAt(i);
            if(attribute.getName().equals("dataName"))
                setDataElementName((String)attribute.getValue());
            if(attribute.getName().equals("sourcePattern"))
                sourcePattern = new SimpleDateFormat((String)attribute.getValue());
            if(attribute.getName().equals("targetPattern"))
                targetPattern = new SimpleDateFormat((String)attribute.getValue());
        }

        return ((FormatExternalizer)FormatElement.getExternalizer()).linkToDecorators(this, aTag);
	}

}
