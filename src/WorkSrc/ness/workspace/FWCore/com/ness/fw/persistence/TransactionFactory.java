/*
 * Created on: 30/08/2004
 * Author: yifat har-nof
 */
package com.ness.fw.persistence;

import com.ness.fw.common.auth.UserAuthData;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * 	A factory for transaction manager.
 */
public class TransactionFactory
{

	/**
	* Create a new Transaction object with the default connection manager.
	* Do not allow auto commit operations.
	 * @param userAuthData Contains the basic authorization data of the current user.
	*/
	public static Transaction createTransaction(UserAuthData userAuthData)
		throws PersistenceException
	{
		return new Transaction(userAuthData);
	}

	/**
	 * Create a new Transaction object based on a specific connection manager.
	 * Do not allow auto commit operations.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 * @param connectionManagerName The name of the connection manager.
	 */
	public static Transaction createTransaction(
		UserAuthData userAuthData,
		String connectionManagerName)
		throws PersistenceException
	{
		return new Transaction(userAuthData, connectionManagerName);
	}

	/** 
	 * Create a new Transaction object based on specific connection manager.
	 * @param userAuthData Contains the basic authorization data of the current user.
	 * @param connectionManagerName The name of connection manager to use.
	 * @param allowAutoCommit allow auto commit operations.
	 */
	public static Transaction createTransaction(
		UserAuthData userAuthData,
		String connectionManagerName,
		boolean allowAutoCommit)
		throws PersistenceException
	{
		return new Transaction(
			userAuthData,
			connectionManagerName,
			allowAutoCommit);
	}

}