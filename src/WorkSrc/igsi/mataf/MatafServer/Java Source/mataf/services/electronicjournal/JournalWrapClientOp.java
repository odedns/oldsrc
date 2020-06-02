package mataf.services.electronicjournal;

import mataf.logger.*;
import mataf.operations.general.*;
import mataf.utils.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalWrapClientOp extends BasicClientOp {

/**
	 * execute the operation.
	 * wrap the slika journal.
	 */
	public void execute() throws Exception
	{
		GLogger.debug("int JournalWrapClientOp  ...");
		sendReceive(getContext());		
	}
}
