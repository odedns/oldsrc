package fwpilot.common.bpc;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;

public class PLPagingBPC extends BusinessProcessContainer
{

	PagingService pagingSrv;
	int pagingOperation;
	int numPages;
	Page page;

	public PLPagingBPC()
	{
		super();
	}

	public PagingService getPagingSrv()
	{
		return pagingSrv;
	}

	public void setPagingSrv(PagingService service)
	{
		pagingSrv = service;
	}

	public int getPagingOperation()
	{
		return pagingOperation;
	}

	public void setPagingOperation(int pagingOperation)
	{
		this.pagingOperation = pagingOperation;
	}

	public Page getPage()
	{
		return page;
	}

	public void setPage(Page page)
	{
		this.page = page;
	}

	public int getNumPages()
	{
		return numPages;
	}

	public void setNumPages(int numPages)
	{
		this.numPages = numPages;
	}

}
