/*
 * Created on: 18/01/2005
 * @author: baruch hizkya
 * @version $Id: EJBContainer.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

import java.io.Serializable;

import com.ness.fw.bl.BusinessProcessContainer;

/**
 * A class that use as a container for BusinessProcessContainer and Object result
 */
public class EJBContainer implements Serializable
{
	private BusinessProcessContainer bpc;
	private Object result;

	/**
	 * @return bpc
	 */
	public BusinessProcessContainer getBpc()
	{
		return bpc;
	}

	/**
	 * @return result
	 */
	public Object getResult()
	{
		return result;
	}

	/**
	 * @param container
	 */
	public void setBPC(BusinessProcessContainer bpc)
	{
		this.bpc = bpc;
	}

	/**
	 * @param object
	 */
	public void setResult(Object object)
	{
		result = object;
	}

}
