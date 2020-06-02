package mataf.decorator;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Vector;
import com.ibm.dse.base.*;

/**
 * @author Tibi Glazer
 *
 * Class: BlankWhenZeroDecorator.java
 * Description: Return blanks if element value is zero.
 * Created at: Dec 16, 2003
 * 
 */
public class BlankWhenZeroDecorator extends FormatDecorator {

	public static final char BLANK_CHAR = ' ';
    private DecimalFormat formatter;

	public BlankWhenZeroDecorator() {
		formatter = new DecimalFormat();
	}

	public BlankWhenZeroDecorator(String aName) throws IOException {
		setName(aName);
		readExternal();
	}

	public String addDecoration(String aString) {
		try {
			Number number = formatter.parse(aString.trim());
			if ((number != null) && (number.doubleValue() == 0.0D)) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < aString.length(); i++) 
				{				
					sb.append(BLANK_CHAR);
				}
				return sb.toString();
			}
		}
		catch (ParseException e) {
		}
		return aString;
	}

	public Object initializeFrom(Tag aTag) {
		return this;
	}

	public String removeDecoration(String aString) {
		return aString;
	}

	public com.ibm.dse.base.Vector toStrings() {
		com.ibm.dse.base.Vector v = new com.ibm.dse.base.Vector();
		if (getDecorated() != null)
			v.add(getDecorated().toStrings());
		String s = "<" + getTagName() + " " + "/" + ">";
		v.addElement(s);
		return v;
	}
}