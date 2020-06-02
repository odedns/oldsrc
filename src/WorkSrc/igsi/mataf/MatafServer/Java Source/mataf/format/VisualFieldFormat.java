package mataf.format;

import java.awt.Color;
import java.io.IOException;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.FieldFormat;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.FormatExternalizer;
import com.ibm.dse.base.Operation;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VisualFieldFormat extends FormatElement {

	public static final String RGB_COLOR_DELIM = ",";
	public static final String CLASS_MEMBERS_DELIM = "$";

	/**
	 * Constructor for VisualFieldFormat.
	 */
	public VisualFieldFormat() {
		super();
	}

	public VisualFieldFormat(String aName) throws IOException {
		setName(aName);
		readExternal();
	}

	/**
	 * @see com.ibm.dse.base.FormatElement#format(Context)
	 */
	public String format(Context aContext) throws DSEInvalidClassException, DSEInvalidRequestException, DSEInvalidArgumentException {
		DataElement retElem = aContext.tryGetElementAt(getDataElementName());
		if (retElem != null)
			return formatField((VisualDataField) retElem);
		else
			throw new DSEInvalidRequestException(
				DSEException.harmless,
				getClass().getName(),
				"Invalid Request: (format: "
					+ getDataElementName()
					+ " not found in Context "
					+ aContext.getName()
					+ ")"
					+ ". Format: "
					+ getName()
					+ ", DataElement: "
					+ getDataElementName()
					+ ", Process: format");

	}

	/**
	 * @see com.ibm.dse.base.FormatElement#format(DataElement)
	 */
	public String format(DataElement aDataElement) throws DSEInvalidClassException, DSEInvalidRequestException, DSEInvalidArgumentException {
		if (aDataElement instanceof VisualDataField) {
			String formattedData = null;
			try {
				formattedData = formatField((VisualDataField) aDataElement);
			} catch (DSEException e) {
				String message = "Error formatting field '" + aDataElement.getName() + "': " + e;
				throw new DSEInvalidArgumentException(DSEException.harmless, aDataElement.getName(), message);
			}
			return formattedData;
		} else {
			throw new DSEInvalidArgumentException(
				DSEException.harmless,
				aDataElement.getName(),
				"InvalidArgument: (formatField: "
					+ aDataElement.getName()
					+ " is not a DataField)"
					+ ". Format: "
					+ getName()
					+ ", DataElement: "
					+ getDataElementName()
					+ ", Process: format");
		}
	}
	
	/**
	 * @see com.ibm.dse.base.FieldFormat#formatField(DataField)
	 */
	public String formatField(DataField aDataField) throws DSEInvalidArgumentException, DSEInvalidClassException {

		Boolean hasFocus, isVisible, isEnable, isRequired, inError;
		String foregroundColor, errorMessage;

		try {
			VisualDataField visualField = (VisualDataField) aDataField;
			hasFocus = visualField.getShouldRequestFocus();
			isVisible = visualField.getIsVisible();
			isEnable = visualField.getIsEnabled();
			isRequired = visualField.getIsRequired();
			inError = visualField.getInErrorFromServer();
			errorMessage = visualField.getErrorMessage();
			foregroundColor = formatColor(visualField.getForeground());
		} catch (ClassCastException e) { // aDataField class doesn't extend VisualDataField
			String msg = "Can't format field named '" + aDataField.getName() + "'. Initializing default values..."; 
			GLogger.error(this.getClass(), null, msg, e, false);
			hasFocus = Boolean.FALSE;
			isVisible = Boolean.TRUE;
			isEnable = Boolean.TRUE;
			isRequired = Boolean.TRUE;
			inError = Boolean.FALSE;
			errorMessage = " ";
			foregroundColor = formatColor((Color) Settings.getValueAt("mandatoryForegroundColor"));
		}

		StringBuffer bfr = new StringBuffer();
					
		bfr.append(hasFocus).append(CLASS_MEMBERS_DELIM);
		bfr.append(isVisible).append(CLASS_MEMBERS_DELIM);
		bfr.append(isEnable).append(CLASS_MEMBERS_DELIM);
		bfr.append(isRequired).append(CLASS_MEMBERS_DELIM);
		bfr.append(inError).append(CLASS_MEMBERS_DELIM);
		bfr.append(errorMessage);
		bfr.append(CLASS_MEMBERS_DELIM);
		bfr.append(foregroundColor);
		bfr.append(CLASS_MEMBERS_DELIM);
		bfr.append(aDataField.getValue());

		return bfr.toString();
	}

	private String formatColor(Color color2format) {

		StringBuffer str2return = new StringBuffer();
		str2return.append(color2format.getRed());
		str2return.append(RGB_COLOR_DELIM);
		str2return.append(color2format.getGreen());
		str2return.append(RGB_COLOR_DELIM);
		str2return.append(color2format.getBlue());
		return str2return.toString();
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#unformatField(String, DataField)
	 */
	public DataField unformatField(String aString, VisualDataField aDataField) throws DSEInvalidArgumentException {

		VisualDataField visualField = null;
		StringTokenizer tokenizer = null;

		try {
			visualField = (VisualDataField) aDataField;
			tokenizer = new StringTokenizer(aString, CLASS_MEMBERS_DELIM);
			visualField.setShouldRequestFocus(new Boolean(tokenizer.nextToken()));
			visualField.setIsVisible(new Boolean(tokenizer.nextToken()));
			visualField.setIsEnabled(new Boolean(tokenizer.nextToken()));
			visualField.setIsRequired(new Boolean(tokenizer.nextToken()));
			visualField.setInErrorFromServer(new Boolean(tokenizer.nextToken())); // <--It's ok, ignore the deprecation warning.
			visualField.setErrorMessage(new String(tokenizer.nextToken()));
			visualField.setForeground(unformatColor(tokenizer.nextToken()));
			if (tokenizer.hasMoreTokens()) {
				visualField.setValue(tokenizer.nextToken());
			} else {
				visualField.setValue(new String(""));
			}
		} catch (Exception e) {
			String msg = "Can't unformat String "+aString+" to set VisualDataField named "+aDataField.getName();
			GLogger.error(this.getClass(), null, msg, e, false);
			tokenizer = new StringTokenizer(aString, CLASS_MEMBERS_DELIM);
			for (int counter = 0; counter < 7; counter++)
				tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				aDataField.setValue(tokenizer.nextToken());
			} else {
				aDataField.setValue(new String(""));
			}
		}

		return aDataField;
	}

	private Color unformatColor(String formatedColor) {
		StringTokenizer tokenizer = null;
		int red = 0, green = 0, blue = 0;

		try {
			tokenizer = new StringTokenizer(formatedColor, RGB_COLOR_DELIM);
			red = Integer.parseInt(tokenizer.nextToken());
			green = Integer.parseInt(tokenizer.nextToken());
			blue = Integer.parseInt(tokenizer.nextToken());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new Color(red, green, blue);
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException {

		setDataElementName("");
		for (int i = 0; i < aTag.getAttrList().size(); i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);
			if (attribute.getName().equals("dataName"))
				setDataElementName((String) attribute.getValue());
		}

		return ((FormatExternalizer) FormatElement.getExternalizer()).linkToDecorators(this, aTag);
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#unformat(String, Context)
	 */
	public DataElement unformat(String aString, Context aContext) throws DSEInvalidRequestException, DSEInvalidArgumentException {
		DataElement retElem = aContext.tryGetElementAt(getDataElementName());
		if (retElem != null)
			return unformatField(aString, (VisualDataField) retElem);
		String tempString = aString;
		if (aString.length() > 15)
			tempString = aString.substring(0, 15) + "...";
		throw new DSEInvalidRequestException(
			DSEException.harmless,
			getClass().getName(),
			"Invalid Request: (unformat: "
				+ getDataElementName()
				+ " not found in Context "
				+ aContext.getName()
				+ ")"
				+ ". Format: "
				+ getName()
				+ ", DataElement: "
				+ getDataElementName()
				+ ", Process: unformat"
				+ ", String: "
				+ tempString);
	}

	/**
	 * @see com.ibm.dse.base.FieldFormat#unformat(String, DataElement)
	 */
	public DataElement unformat(String aString, DataElement aDataElement) throws DSEInvalidArgumentException {
		if (aDataElement instanceof VisualDataField) {
			VisualDataField unformattedData = null;
			try {
				unformattedData = (VisualDataField) unformatField(aString, (VisualDataField) aDataElement);
			} catch (DSEException e) {
				String message = "Error unformatting field '" + aDataElement.getName() + "': " + e;
				throw new DSEInvalidArgumentException(DSEException.harmless, aDataElement.getName(), message);
			}
			return unformattedData;
		}
		String tempString = aString;
        if(aString.length() > 15)
            tempString = aString.substring(0, 15) + "...";
        throw new DSEInvalidArgumentException(DSEException.harmless, aDataElement.getName(), "InvalidArgument: (formatField: " + aDataElement.getName() + " is not a DataField)" + ". Format: " + getName() + ", DataElement: " + getDataElementName() + ", Process: unformat" + ", String: " + tempString);

	}

}
