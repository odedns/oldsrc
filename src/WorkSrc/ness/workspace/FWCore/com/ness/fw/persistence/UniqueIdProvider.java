/*
 * Created on: 24/03/2005
 * Author: yifat har-nof
 * @version $Id: UniqueIdProvider.java,v 1.4 2005/04/04 09:36:18 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Support local management of specific numerator values that was declared with 
 * big steps in the DB. 
 * Used for transactions with a lot of insert statements. 
 */
public class UniqueIdProvider
{

	/**
	 * The name of the numerator.
	 */
	private String numeratorName;

	/**
	 * The name of the connection manager.
	 */
	private String connectionManagerName;

	/**
	 * The step size to allocate the numerator values locally on the step buffer in the DB. 
	 */
	private int providerStepSize;

	/**
	 * Contains the numerator details such as next value & step.
	 */
	private NumeratorDetails numeratorDetails;

	/**
	 * current next value.
	 */
	private long nextValue;

	/**
	 * Creates new UniqueIdProvider object.
	 * @param numeratorName The name of the numerator.
	 * @param connectionManagerName The name of the connection manager.
	 * @param providerStepSize The step size to allocate the numerator values 
	 * locally on the step buffer in the DB.
	 * @throws PersistenceException
	 */
	protected UniqueIdProvider(
		String numeratorName,
		String connectionManagerName,
		int providerStepSize)
		throws PersistenceException
	{
		this.numeratorName = numeratorName;
		this.connectionManagerName = connectionManagerName;
		this.providerStepSize = providerStepSize;
	}

	/**
	 * load the next range of values of the numerator from the DB.
	 * @throws PersistenceException
	 */
	private void loadNextValue() throws PersistenceException
	{
		numeratorDetails =
			Numerator.getNumeratorNextDetails(
				numeratorName,
				connectionManagerName);
		nextValue = numeratorDetails.getNextId();

		if (providerStepSize > numeratorDetails.getStep())
		{
			throw new PersistenceException(
				"The provider step size ["
					+ providerStepSize
					+ "] should be smaller then the numerator step size ["
					+ numeratorDetails.getStep()
					+ "].");
		}
	}

	/**
	 * Returns the next value of the numerator as a long.
	 * @return long next value
	 * @throws PersistenceException
	 */
	public long getNextLongValue() throws PersistenceException
	{
		if (numeratorDetails != null
			&& ((nextValue + providerStepSize)
				< (numeratorDetails.getNextId() + numeratorDetails.getStep())))
		{
			nextValue += providerStepSize;
		}
		else
		{
			loadNextValue();
		}
		return nextValue;
	}

	/**
	 * Returns the next value of the numerator as an int.
	 * @return int next value
	 * @throws PersistenceException
	 */
	public int getNextValue() throws PersistenceException
	{
		long value = getNextLongValue();
		Numerator.checkIntegerMaxValue(numeratorName, value);
		return (int) value;
	}

}