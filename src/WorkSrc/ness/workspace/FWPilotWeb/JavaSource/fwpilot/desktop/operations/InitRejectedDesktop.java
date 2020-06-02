package fwpilot.desktop.operations;

import fwpilot.general.dao.WorkQueueItemDAO;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InitRejectedDesktop extends InitDesktop
{
	protected int getType()
	{
		return WorkQueueItemDAO.TYPE_REJECTED;
	}
}
