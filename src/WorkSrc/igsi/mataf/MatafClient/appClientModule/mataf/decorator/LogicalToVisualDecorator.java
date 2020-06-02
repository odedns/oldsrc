package mataf.decorator;

import java.io.IOException;
import java.text.Bidi;

import com.ibm.dse.base.FormatDecorator;
import com.ibm.dse.base.Tag;

/**
 * @author Tibi Glazer
 *
 * Class: LogicalToVisualDecorator.java
 * Description: Converts hebrew text from logical to visual representation.
 * Created at: Jan 6, 2004
 * 
 */
public class LogicalToVisualDecorator extends FormatDecorator {

	public LogicalToVisualDecorator() {
	}

	public LogicalToVisualDecorator(String aName) throws IOException {
		setName(aName);
		readExternal();
	}

	public String addDecoration(String aString) {
		if (!Bidi.requiresBidi(aString.toCharArray(), 1, aString.length())) {
			return aString;
		}
		Bidi bd = new Bidi(aString, Bidi.DIRECTION_LEFT_TO_RIGHT);
		StringBuffer[] tokens = new StringBuffer[bd.getRunCount()];
		byte[] levels = new byte[bd.getRunCount()];
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = new StringBuffer(aString.substring(bd.getRunStart(i), bd.getRunLimit(i)));
			levels[i] = (byte) bd.getRunLevel(i);
		}
		Bidi.reorderVisually(levels, 0, tokens, 0, tokens.length);
		StringBuffer aResult = new StringBuffer();
		for (int i = 0; i < tokens.length; i++) {
			/*
			 * Original Bidi code claims that odd run levels are right to left, 
			 * but my tests were not concludent so I had to use requiresBidi.
			 */
			if (Bidi.requiresBidi(new String(tokens[i]).toCharArray(), 1, tokens[i].length())) {
				aResult.append(tokens[i].reverse());
			}
			else {
				aResult.append(tokens[i]);
			}
		}
		return new String(aResult);
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