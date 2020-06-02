/*
 * Created on: 27/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: WFLastHandlerOfCustomer.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.workflow;

/**
 * This class is used as the return value from MQWorkFlow service
 * getLastHandlerOfCustomer().
 */
public class WFLastHandlerOfCustomer
{
	
	/**
	 * lastHandler of customer.
	 */
	private String lastHandler = null;
	
	/**
	 * organization of customer.
	 */
	private String organization = null;

	/**
	 * Constructor.
	 * @param lastHandler
	 * @param organization
	 */
	public WFLastHandlerOfCustomer(String lastHandler, String organization)
	{
		this.lastHandler = lastHandler;
		this.organization = organization;		
	}
	
	/**
	 * Return the name of the last handler of the customer.
	 * @return String LastHandler
	 */
	public String getLastHandler()
	{
		return lastHandler;
	}
	
	/**
	 * Returns the organization of the last handler.
	 * @return String Organization
	 */
	public String getOrganization()
	{
		return organization;
	}
}
