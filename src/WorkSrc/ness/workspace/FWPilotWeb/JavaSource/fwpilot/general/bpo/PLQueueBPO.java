package fwpilot.general.bpo;

import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;

import fwpilot.general.bpc.PLQueueBPC;
import fwpilot.general.dao.WorkQueueItemDAO;

public class PLQueueBPO extends BusinessProcess
{

			
	public static void loadQueue(PLQueueBPC container) throws PersistenceException 
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		Page page = WorkQueueItemDAO.getQueueByType(container.getType(),1, seq);
		container.setPage(page);		
		seq.end();					
	}
		
}
