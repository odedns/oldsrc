/*
 * Created on: 07/11/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

/**
 * 
 */
public class CustomerProfession2
{

	private Integer customerId;
	private Integer professionId;

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
	public Integer getProfessionId()
	{
		return professionId;
	}

	/**
	 * @param integer
	 */
	public void setCustomerId(Integer integer)
	{
		customerId = integer;
	}

	/**
	 * @param integer
	 */
	public void setProfessionId(Integer integer)
	{
		professionId = integer;
	}
	public String toString()
	{
		return "CustomerProfession:  customerId=" + customerId 
			+ " professionId=" + professionId;
	}

}
