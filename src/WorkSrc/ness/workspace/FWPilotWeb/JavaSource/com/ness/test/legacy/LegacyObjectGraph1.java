/*
 * Created on: 17/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.legacy.LegacyObjectGraph;

/**
 * 
 */
public class LegacyObjectGraph1 extends LegacyObjectGraph
{

	private List customers = new ArrayList();
	
	private Integer outArg;
	
	public void addCustomer (Customer1 customer)
	{
		customers.add(customer);
	}

	/**
	 * @return
	 */
	public Customer1 getCustomer(int index)
	{
		return (Customer1)customers.get(index);
	}

	public int getCustomerCount()
	{
		return customers.size();
	}

	public void setOutArg1(Integer outArg)
	{
		this.outArg = outArg;
	}

	/**
	 * @return
	 */
	public Integer getOutArg()
	{
		return outArg;
	}

}
