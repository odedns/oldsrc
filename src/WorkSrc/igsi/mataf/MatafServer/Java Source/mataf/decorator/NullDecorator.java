package mataf.decorator;

import java.io.IOException;

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
public class NullDecorator extends FormatDecorator {

	/**
	 * Constructor for NullDecorator.
	 */
	public NullDecorator() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#addDecoration(String)
	 */
	public String addDecoration(String arg0) throws DSEInvalidArgumentException {
		return String.valueOf((char)255);
	}

	/**
	 * @see com.ibm.dse.base.FormatDecorator#removeDecoration(String)
	 */
	public String removeDecoration(String arg0) throws DSEInvalidArgumentException {
		return arg0;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#toStrings()
	 */
	public Vector toStrings() {
		return null;
	}

	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag arg0) throws IOException, DSEException {
		return this;
	}

}
