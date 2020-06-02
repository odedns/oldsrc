package fwpilot.general.bpc;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.persistence.Page;

public class PLQueueBPC extends BusinessProcessContainer
{

	private Page page;
	private int type;

	public PLQueueBPC()
	{
		super();
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
	 * @return type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(int type)
	{
		this.type = type;
	}

}
