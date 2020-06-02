/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: ComplexFormatter.java,v 1.1 2005/02/21 15:07:10 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Implements couple of input and output formatters
 */
public class ComplexFormatter
{
	private Formatter inFormatter;
	private Formatter outFormatter;
	private String name;

	public ComplexFormatter(String name, Formatter inFormatter, Formatter outFormatter)
	{
		this.name = name;
		this.inFormatter = inFormatter;
		this.outFormatter = outFormatter;
	}

	public String getName()
	{
		return name;
	}

	public Formatter getInFormatter()
	{
		return inFormatter;
	}

	public Formatter getOutFormatter()
	{
		return outFormatter;
	}
}
