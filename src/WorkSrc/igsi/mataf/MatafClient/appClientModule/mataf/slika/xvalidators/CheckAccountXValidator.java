package mataf.slika.xvalidators;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEXValidate;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.OperationXValidate;
import com.ibm.dse.base.types.DSETypeException;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckAccountXValidator implements OperationXValidate {

	/**
	 * @see com.ibm.dse.base.OperationXValidate#xValidate(Context)
	 */
	public String[] xValidate(Context ctx) {
		return null;
	}

	/**
	 * @see com.ibm.dse.base.OperationXValidate#validate(String, DataField, Context)
	 */
	public void validate(String fullyQualifiedName, DataField df, Context ctxt) throws DSETypeException {
	}

}
