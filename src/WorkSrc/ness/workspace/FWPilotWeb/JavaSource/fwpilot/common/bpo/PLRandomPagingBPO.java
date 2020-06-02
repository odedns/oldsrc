package fwpilot.common.bpo;

import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;
import fwpilot.common.bpc.PLPagingBPC;

public class PLRandomPagingBPO extends BusinessProcess
{	
	public static void paging(PLPagingBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			RandomPagingService  pagingSrv = (RandomPagingService)container.getPagingSrv();
			int pageNumber = container.getPagingOperation();

			//System.out.println("pageNumber" + pageNumber);
			Page page = null;
			if (pagingSrv != null)
			{
				seq = ConnectionSequence.beginSequence();		
				page = pagingSrv.getPage(pageNumber,seq);
			}
				
			container.setPage(page);
		}

		catch (PersistenceException pe)
		{
			throw new FatalException("persistance", pe);					
		}
	
		// Handling a problem in the DB - closing connections
		finally
		{
			if (seq != null)
			{
				try 
				{
					seq.end();
				} 
				catch (PersistenceException e) 
				{
					throw new FatalException("persistance",e);		
				}
			}
		}
	}
}
