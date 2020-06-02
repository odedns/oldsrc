/*
 * Created on: 15/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: WFWorkList.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.workflow;

/**
 * Wraps the name and ID of the pre-defined filter.
 */
public class WFWorkList
{
	/**
	 * Name of the pre-defined filter.
	 */
	private String name;
	
	/**
	 * Id of the pre-defined filter.
	 */
	private String id;
	
	/**
	 * Constructor
	 * @param name Name of the pre-defined filter.
	 * @param id Id of the pre-defined filter.
	 */
	public WFWorkList(String name, String id)
	{
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Sets name of the pre-defined filter.
	 * @param name
	 */
	private void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Sets id of the pre-defined filter.
	 * @param id
	 */
	private void setId(String id)
	{
		this.id = id;
	}
	
	/**
	 * Returns name of the pre-defined filter.
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns id of the pre-defined filter.
	 * @return id
	 */
	public String getId()
	{
		return id;
	}
}
