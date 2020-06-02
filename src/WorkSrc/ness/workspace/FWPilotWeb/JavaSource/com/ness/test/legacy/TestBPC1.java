/*
 * Created on: 14/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy;

import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.common.auth.UserAuthData;

/**
 * 
 */
public class TestBPC1 extends BusinessProcessContainer
{
	private Integer arg1Value;

	/**
	 * @param userId
	 * @param legacyCommandName
	 */
	public TestBPC1(UserAuthData userAuthData)
	{
		super(userAuthData);
	}

	public TestBPC1()
	{
	}


	/**
	 * @return
	 */
	public Integer getArg1Value()
	{
		return arg1Value;
	}

	/**
	 * @param integer
	 */
	public void setArg1Value(Integer integer)
	{
		arg1Value = integer;
	}

}
