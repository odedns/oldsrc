package mataf.globalmessages.handler;

import com.ibm.dse.base.Context;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IMessageClickHandler
{
		public int handleClick(int iRow);
		public Context getContext();
}
