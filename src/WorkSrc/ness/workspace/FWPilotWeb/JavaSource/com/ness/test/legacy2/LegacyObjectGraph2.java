/*
 * Created on: 17/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.legacy.LegacyObjectGraph;

/**
 * 
 */
public class LegacyObjectGraph2 extends LegacyObjectGraph
{

	private List customers = new ArrayList();
	private List envelopes = new ArrayList();
	
	private Integer outArg;
	

	public void addEnv (Env2 env2)
	{
		envelopes.add(env2);
	}

	public Env2 getEnv(int index)
	{
		return (Env2)envelopes.get(index);
	}


	public void addCustomer (Customer2 customer)
	{
		customers.add(customer);
	}

	/**
	 * @return
	 */
	public Customer2 getCustomer(int index)
	{
		return (Customer2)customers.get(index);
	}

	public int getEnvelopesCount()
	{
		return envelopes.size();
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
