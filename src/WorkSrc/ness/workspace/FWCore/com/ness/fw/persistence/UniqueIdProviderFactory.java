/*
 * Created on: 27/03/2005
 * Author: yifat har-nof
 * @version $Id: UniqueIdProviderFactory.java,v 1.2 2005/03/27 15:02:54 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Factory for UniqueIdProvider objects, that managed locally the values of a 
 * specific numerator with big steps in the DB.
 */
public class UniqueIdProviderFactory
{
	public static final int DEFAULT_PROVIDER_STEP_SIZE = 1;

	/**
	 * 
	 */
	private UniqueIdProviderFactory()
	{
	}

	/**
	 * Creates new UniqueIdProvider object, that support local management of specific 
	 * numerator values that was declared with big steps in the DB. 
	 * Used for transactions with a lot of insert statements. 
	 * @param numeratorName The name of the numerator.
	 * @param connectionManagerName The name of the connection manager.
	 * @param providerStepSize The step size to allocate the numerator values 
	 * locally on the step buffer in the DB.
	 * @return UniqueIdProvider The numerator provider
	 * @throws PersistenceException
	 */
	public static UniqueIdProvider createUniqueIdProvider(
		String numeratorName,
		String connectionManagerName,
		int providerStepSize)
		throws PersistenceException
	{
		return new UniqueIdProvider(
			numeratorName,
			connectionManagerName,
			providerStepSize);
	}

	/**
	 * Creates new UniqueIdProvider object, that support local management of specific 
	 * numerator values that was declared with big steps in the DB. 
	 * Used for transactions with a lot of insert statements. 
	 * @param numeratorName The name of the numerator.
	 * @param providerStepSize The step size to allocate the numerator values 
	 * locally on the step buffer in the DB.
	 * @return UniqueIdProvider The numerator provider
	 * @throws PersistenceException
	 */
	public static UniqueIdProvider createUniqueIdProvider(
		String numeratorName,
		int providerStepSize)
		throws PersistenceException
	{
		return new UniqueIdProvider(numeratorName, null, providerStepSize);
	}

	/**
	 * Creates new UniqueIdProvider object, that support local management of specific 
	 * numerator values that was declared with big steps in the DB. 
	 * Used for transactions with a lot of insert statements. 
	 * @param numeratorName The name of the numerator.
	 * @return UniqueIdProvider The numerator provider
	 * @throws PersistenceException
	 */
	public static UniqueIdProvider createUniqueIdProvider(String numeratorName)
		throws PersistenceException
	{
		return new UniqueIdProvider(
			numeratorName,
			null,
			DEFAULT_PROVIDER_STEP_SIZE);
	}

}
