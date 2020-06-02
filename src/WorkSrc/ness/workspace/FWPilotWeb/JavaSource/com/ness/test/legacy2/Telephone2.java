/*
 * Created on: 07/11/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

/**
 * 
 */
public class Telephone2
{

	private Integer customerId;
	private String telephone;
	private Integer fax;

	public String toString()
	{
		return "Telephone:  customerId=" + customerId 
		+ " telephone=" + telephone
		+ " fax=" + fax;
	}

	/**
	 * @return
	 */
	public Integer getCustomerId()
	{
		return customerId;
	}

	/**
	 * @return
	 */
	public Integer getFax()
	{
		return fax;
	}

	/**
	 * @return
	 */
	public String getTelephone()
	{
		return telephone;
	}

	/**
	 * @param integer
	 */
	public void setCustomerId(Integer integer)
	{
		customerId = integer;
	}

	/**
	 * @param string
	 */
	public void setFax(Integer fax)
	{
		this.fax = fax;
	}

	/**
	 * @param string
	 */
	public void setTelephone(String string)
	{
		telephone = string;
	}

}
