/*
 * Created on: 14/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.auth.UserAuthData;

/**
 * 
 */
public class TestBPC2 extends BusinessProcessContainer
{
	private Integer arg1Value;

	/**
	 * @param userId
	 * @param legacyCommandName
	 */
	public TestBPC2(UserAuthData userAuthData)
	{
		super(userAuthData);
	}

	public TestBPC2()
	{
	}



	/**
	 * @return
	 */
	public Integer getArg1Value()
	{
		return arg1Value;
	}

	public Integer getVAR02()
	{
		return new Integer(12);
	}
	
	
	public Integer getVAR03()
	{
		return new Integer(35);
	}

	/**
	 * @param integer
	 */
	public void setArg1Value(Integer integer)
	{
		arg1Value = integer;
	}

}
