package mataf.types.misc;

import mataf.format.VisualNumStringFormat;

import com.ibm.dse.base.DataField;
import com.ibm.dse.gui.Converter;



/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VisualFloatConverter extends Converter {	
	private String fieldSeparator = ".";
	private char fieldDecimalSeparator = ',';
	private int fieldMaxNumberOfDecimals = 2;
	private static final long serialVersionUID = 123456782;
	private transient VisualNumStringFormat format = new VisualNumStringFormat();
	/**
	 * This constructor creates a FloatConverter object. 
	 */
	public VisualFloatConverter() {
		super();
		initializeFormat(getDecimalSeparator(), getSeparator(), getMaxNumberOfDecimals());
	}
	/**
	 * This constructor creates a FloatConverter object. 
	 * @param text java.lang.String, the errorMessage
	 * @param sep java.lang.String, the thousands separator
	 * @param decSep char, the decimal separator
	 */
	public VisualFloatConverter(String text, String sep, char decSep) {
		super();
		setErrorMessage(text);
		setSeparator(sep);
		setDecimalSeparator(decSep);
		initializeFormat(decSep, sep, 0);
	}
	/**
	 * This constructor creates a FloatConverter object. 
	 * @param errorMessage java.lang.String, the error message
	 * @param sep java.lang.String, the thousands separator
	 * @param decSep char, the decimal separator
	 * @param decimals int, the number of decimals
	 */
	public VisualFloatConverter(String errorMessage, String sep, char decSep, int decimals) {
		this(errorMessage, sep, decSep);
		setMaxNumberOfDecimals(decimals);
		initializeFormat(decSep, sep, decimals);
	}
	/**
	 * This constructor creates a FloatConverter object. 
	 * @param errorMessage java.lang.String, the error message
	 * @param sep java.lang.String, the thousands separator
	 * @param decSep char, the decimal separator
	 * @param decimals int, the number of decimals
	 * @param convertible boolean, indicates if it is Euro-EMU convertible or not
	 */
	public VisualFloatConverter(String errorMessage, String sep, char decSep, int decimals, boolean convertible) {
		this(errorMessage, sep, decSep);
		setMaxNumberOfDecimals(decimals);
		setIsConvertible(convertible);
		initializeFormat(decSep, sep, decimals);
	}
	/**
	 * Converts a String into a Float. 
	 * @return Object - the result of converting the aString parameter into a Float
	 * @param text java.lang.String, the String to be converted
	 */
	public Object formatAsConversionType(String text) {

		Float result;

		if ((text == null) || (text.length() == 0)) {
			return null;
		}

		DataField d = new DataField();
		DataField d2 = new DataField();
		Object value;

		try {
			value = getFormat().unformatField(text, d2).getValue();
		} catch (Exception e) {
			return null;
		}

		if ((value.getClass()) == Double.class) {
			result = new Float(((Double) value).floatValue());
		} else {
			result = new Float(((Long) value).floatValue());
		}
		return result;

	}
	/**
	 * Converts a Float into a String. 
	 * @return String - the result of converting the input parameter into a String
	 * @param input Object, the float to be converted
	 */
	public String formatFromConversionType(Object input) {

		if (input == null) {
			return "";
		}

		DataField d = new DataField();
		d.setValue(input);
		return (getFormat().formatField(d));

	}
	/**
	 * Gets the decimalSeparator property (char) value.
	 * @return char - the decimalSeparator property value.
	 * @see #setDecimalSeparator
	 */
	public char getDecimalSeparator() {
		return fieldDecimalSeparator;
	}
	/**
	 * Gets the format property (NumericStringFormat) value.
	 * @return NumericStringFormat - the format property value.
	 * @see #setFormat
	 */
	public VisualNumStringFormat getFormat() {
		return format;
	}
	/**
	 * Gets the maxNumberOfDecimals property (int) value.
	 * @return int - the maxNumberOfDecimals property value.
	 * @see #setMaxNumberOfDecimals
	 */
	public int getMaxNumberOfDecimals() {
		return fieldMaxNumberOfDecimals;
	}
	/**
	 * Gets the separator property (java.lang.String) value.
	 * @return String - the separator property value.
	 * @see #setSeparator
	 */
	public String getSeparator() {
		return fieldSeparator;
	}
	/**
	 * Initializes a format with the decimal separator, thousands separator and number of decimals passed as arguments
	 */
	private void initializeFormat(char decSep, String thousSep, int nbOfDecimals) {
		format.setDecimalSeparator(decSep);
		if (thousSep.length() != 0) {
			format.setThousandsSeparator(thousSep.charAt(0));
		} else {
			format.setUseThousandsSeparator(false);
		}
		format.setDecimalPlaces(nbOfDecimals);
	}
	/**
	 * true if char is valid for floatFormatter
	 */
	private boolean isValidChar(char ch1) {
		char sep = 0, decSep;

		if ((getSeparator().length()) > 0) {
			sep = (getSeparator().charAt(0));
		};
		decSep = getDecimalSeparator();

		if ((ch1 == sep) || (ch1 == decSep) || (ch1 == '+') || (ch1 == '-'))
			return true;
		return false;
	}
	/**
	 * Sets the decimalSeparator property (char) value.
	 * @param decimalSeparator, the new value for the decimalSeparator property.
	 * @see #getDecimalSeparator
	 */
	public void setDecimalSeparator(char decimalSeparator) {
		char oldValue = fieldDecimalSeparator;
		fieldDecimalSeparator = decimalSeparator;
		firePropertyChange("decimalSeparator", new Character(oldValue), new Character(decimalSeparator));
	}
	/**
	 * Sets the format property (NumericStringFormat) value.
	 * @param format, the new value for the format property.
	 * @see #getFormat
	 */
	public void setFormat(VisualNumStringFormat value) {
		format = value;
	}
	/**
	 * Sets the maxNumberOfDecimals property (int) value.
	 * @param maxNumberOfDecimals, the new value for the maxNumberOfDecimals property.
	 * @see #getMaxNumberOfDecimals
	 */
	public void setMaxNumberOfDecimals(int maxNumberOfDecimals) {
		int oldValue = fieldMaxNumberOfDecimals;
		fieldMaxNumberOfDecimals = maxNumberOfDecimals;
		firePropertyChange("maxNumberOfDecimals", "" + oldValue, "" + maxNumberOfDecimals);
	}
	/**
	 * Sets the separator property (java.lang.String) value.
	 * @param separator, the new value for the separator property.
	 * @see #getSeparator
	 */
	public void setSeparator(String separator) {
		String oldValue = fieldSeparator;
		fieldSeparator = separator;
		firePropertyChange("separator", oldValue, separator);
	}
	/**
	 * Returns a boolean that indicates if the validation has been achieved successfully or not.
	 * @return boolean - the result of the validation
	 * @param userInput java.lang.String - the string to be validated
	 */
	public boolean validate(String userInput) {

		if (userInput == null)
			return true;

		String input = userInput.trim();

		if (input.equals(""))
			return true;

		if (input.charAt(0) == '-')
			input = input.substring(1);
		else if (input.charAt(0) == '+')
			input = input.substring(1);
		boolean allowDecimal = true;
		char[] aCharArray = input.toCharArray();
		for (int i = 0; i < aCharArray.length; i++) {
			char c = aCharArray[i];
			if (c == getDecimalSeparator()) {
				if (!allowDecimal)
					return false;
				else
					allowDecimal = false;
				continue;
			}
			if (!Character.isDigit(c)) {
				if (!input.substring(i, i + 1).equals(getSeparator())) //It is supposed that separators has just one character
					return false;
			}
		}
		return true;
	}
	/**
	 * Returns a boolean that indicates if the validation has been achieved successfully or not for this new key.
	 * @return boolean - the result of the validation.
	 * @param userInput java.lang.String - the string writed.
	 * @param keyEvent java.awt.event.KeyEvent - new key.
	 */
	public boolean validateKey(String userInput, java.awt.event.KeyEvent keyEvent) {
		char ch1, ch3 = 0, decSep;
		if ((getSeparator().length()) > 0) {
			ch3 = (getSeparator().charAt(0));
		};
		decSep = getDecimalSeparator();
		ch1 = keyEvent.getKeyChar();
		if (((ch1 == '+') || (ch1 == '-')) && (userInput != null) && (userInput.length() > 0)) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			return false;
		}
		if (((ch1 == ch3) || (ch1 == decSep)) && (userInput != null) && (userInput.indexOf(decSep) >= 0)) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			return false;
		}
		if (!(Character.isDigit(ch1) || Character.isISOControl(ch1) || isValidChar(ch1))) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			return false;
		}
		return true;
	}
}
