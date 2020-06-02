package fwpilot.agreement.bpc;

import java.util.Map;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.persistence.PagingService;
import com.ness.fw.persistence.Page;

public class PLFindAgreementsBPC extends BusinessProcessContainer
{
	private Map criteriaFields;
	private Page page;
	private PagingService service;

	public PLFindAgreementsBPC()
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
	/**
	 * @return
	 */
	public PagingService getPagingService()
	{
		return service;
	}

	/**
	 * @param service
	 */
	public void setPagingService(PagingService service)
	{
		this.service = service;
	}

}
