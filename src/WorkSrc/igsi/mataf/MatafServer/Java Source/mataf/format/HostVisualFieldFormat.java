package mataf.format;

import java.io.IOException;

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
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HostVisualFieldFormat extends FieldFormat
{

	/**
	 * Constructor for HostVisualFieldFormat.
	 */
	public HostVisualFieldFormat()
	{
		super();
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#formatField(DataField)
	 */
	public String formatField(DataField aDataField)
		throws DSEInvalidArgumentException, DSEInvalidClassException
	{
		try {
			return aDataField.getValue().toString();
		} catch(Exception ex) {
			System.err.println("Error while trying 2 format element named "+aDataField.getName());
			return "";
		}
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#unformatField(String, DataField)
	 */
	public DataField unformatField(String aString, DataField aDataField)
		throws DSEInvalidArgumentException
	{
		aDataField.setValue(aString);
		return aDataField;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException
	{
		setDataElementName("");
        for(int i = 0; i < aTag.getAttrList().size(); i++)
        {
            TagAttribute attribute = (TagAttribute)aTag.getAttrList().elementAt(i);
            if(attribute.getName().equals("dataName"))
                setDataElementName((String)attribute.getValue());
        }

        return ((FormatExternalizer)FormatElement.getExternalizer()).linkToDecorators(this, aTag);
	}

}
