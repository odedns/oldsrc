package mataf.decorator;

import java.io.IOException;

import mataf.utils.HebrewConverter;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.FormatDecorator;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.Vector;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HebrewDecorator extends FormatDecorator {

	/**
	 * Constructor for HebrewDecorator.
	 */
	public HebrewDecorator() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#addDecoration(String)
	 */
	public String addDecoration(String aString) throws DSEInvalidArgumentException {
		// should implement this...
		return null;
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#removeDecoration(String)
	 */
	public String removeDecoration(String aString) throws DSEInvalidArgumentException {
		return HebrewConverter.convert2Hebrew(aString);
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#toStrings()
	 */
	public Vector toStrings() {
		Vector v = new Vector();
		if (getDecorated()!=null) 
			v.add(getDecorated().toStrings());
		String s="<"+getTagName()+">";
		v.addElement(s);
		return v;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag arg0) throws IOException, DSEException {
		return this;
	}

}
