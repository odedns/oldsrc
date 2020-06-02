package fwpilot.common.bpo;

import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;
import fwpilot.common.bpc.PLPagingBPC;

public class PLPagingBPO extends BusinessProcess
{	
	public static void paging(PLPagingBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			BasicPagingService  pagingSrv = (BasicPagingService)container.getPagingSrv();
			int pagingOperation = container.getPagingOperation();

			//System.out.println("pagingOperation" + pagingOperation);
			Page page = null;
			if (pagingSrv != null)
			{
				seq = ConnectionSequence.beginSequence();
		
				if (pagingOperation == PagingService.GET_FIRST)
				{
					page = pagingSrv.firstPage(seq);
				}
				else if (pagingOperation == PagingService.GET_NEXT && pagingSrv.isOperationAllowed(SequentialPagingService.GET_NEXT))
				{
					page = pagingSrv.nextPage(seq);
				}
				else if (pagingOperation == PagingService.GET_PREVIOUS && pagingSrv.isOperationAllowed(SequentialPagingService.GET_PREVIOUS))
				{
					page = pagingSrv.previousPage(seq);
				}
				else if (pagingOperation == PagingService.GET_LAST)
				{
					page = pagingSrv.lastPage(seq);
				}
				
				//System.err.println("page=" + page);
			
				container.setPage(page);
				container.setPagingSrv(pagingSrv);
			}
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
