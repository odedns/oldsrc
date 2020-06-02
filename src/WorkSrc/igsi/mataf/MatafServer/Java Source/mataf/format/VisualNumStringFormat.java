package mataf.format;

import mataf.logger.GLogger;

import com.ibm.dse.base.DataField;
import com.ibm.dse.base.NumericStringFormat;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VisualNumStringFormat extends NumericStringFormat {
	/**
	 * @see com.ibm.dse.base.NumericStringFormat#formatField(DataField)
	 */
	String aNumString = null;
	String result = null;
	public String formatField(DataField aDataField) {
		try {
			Object number = aDataField.getValue();
			if (number.equals("")) {
				return null;
			} else if (number instanceof Number) {
				result = formatter.format(number);
			} else if (number instanceof String){
				result = formatter.format(formatter.parse((String)number));
			}
		} catch (Exception e) {
			String msg = "VisualNumStringFormat can not parse the String " + aNumString + " to a Number";
			GLogger.error(this.getClass(), null, msg, e, false);
		}
			
		//	Rest of the code was copied from NumericStringFormat:
		//	After obtain the formatted text, the decimal separator is deleted if there are decimals digits and
		//	if showDecimalSep setting is set to no.
		if (!isShowDecimalsSep() && getDecimalPlaces() > 0) {
			int posDecimalSeparator = result.indexOf(getDecimalSeparator());
			result = result.substring(0, posDecimalSeparator) + result.substring(posDecimalSeparator + 1);
		}
		return result;

	}
}
