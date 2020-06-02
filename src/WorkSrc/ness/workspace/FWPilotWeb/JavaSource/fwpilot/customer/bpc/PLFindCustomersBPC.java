package fwpilot.customer.bpc;

import java.util.Map;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.PagingService;

public class PLFindCustomersBPC extends BusinessProcessContainer
{
	private Map criteriaFields;
	private Page page;
	private PagingService pagingService;
	private int totalPages;

	public PLFindCustomersBPC()
	{
		super();
	}

	public Map getCriteriaFields()
	{
		return criteriaFields;
	}

	public void setCriteriaFields(Map map)
	{
		criteriaFields = map;
	}

	public Page getPage()
	{
		return page;
	}

	public void setPage(Page page)
	{
		this.page = page;
	}

	public PagingService getPagingService()
	{
		return pagingService;
	}

	public void setPagingService(PagingService service)
	{
		pagingService = service;
	}

	/**
	 * @return
	 */
	public int getTotalPages()
	{
		return totalPages;
	}

	/**
	 * @param i
	 */
	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}

}
