/*
 * Created on: 07/11/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

/**
 * 
 */
public class Env2
{

	private Integer id;
	private String desc;
	private Integer x1;
	private Integer x2;
	private Integer x3;

	public String toString()
	{
		return "Env2:  id=" + id 
		+ " desc=" + desc + " x1 = " + x1 + " x2 = " + x2 + " x3= " + x3;
	}

	/**
	 * @return
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param string
	 */
	public void setDesc(String string)
	{
		desc = string;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer)
	{
		id = integer;
	}

	/**
	 * @return
	 */
	public Integer getX1()
	{
		return x1;
	}

	/**
	 * @return
	 */
	public Integer getX2()
	{
		return x2;
	}

	/**
	 * @return
	 */
	public Integer getX3()
	{
		return x3;
	}

	/**
	 * @param integer
	 */
	public void setX1(Integer integer)
	{
		x1 = integer;
	}

	/**
	 * @param integer
	 */
	public void setX2(Integer integer)
	{
		x2 = integer;
	}

	/**
	 * @param integer
	 */
	public void setX3(Integer integer)
	{
		x3 = integer;
	}

}
