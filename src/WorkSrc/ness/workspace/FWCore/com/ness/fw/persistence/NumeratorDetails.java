/*
 * Created on: 27/03/2005
 * Author: yifat har-nof
 * @version $Id: NumeratorDetails.java,v 1.3 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

/**
 * Contains the numerator details such as next value & step.
 */
class NumeratorDetails
{

	/**
	 * The name of the numerator.
	 */
	private String numeratorName;

	/**
	 * The numerator next value.
	 */
	private long nextId;

	/**
	 * The numerator step size.
	 */
	private int step;

	/**
	 * 
	 * @param numeratorName  The name of the numerator.
	 * @param nextId The numerator next value.
	 * @param step The numerator step size.
	 */
	protected NumeratorDetails(String numeratorName, long nextId, int step)
	{
		this.numeratorName = numeratorName;
		this.nextId = nextId;
		this.step = step;
	}

	/**
	 * @return long The numerator next value.
	 */
	protected long getNextId()
	{
		return nextId;
	}

	/**
	 * @return String The name of the numerator.
	 */
	protected String getNumeratorName()
	{
		return numeratorName;
	}

	/**
	 * @return int The numerator step size.
	 */
	protected int getStep()
	{
		return step;
	}

}
