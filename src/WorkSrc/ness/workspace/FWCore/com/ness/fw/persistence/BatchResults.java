/*
 * Author: yifat har-nof
 * @version $Id: BatchResults.java,v 1.2 2005/04/04 09:41:17 yifat Exp $
 */
package com.ness.fw.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.HashMap;

/**
 * Contains the execution results of the {@link Batch} object.
 */
public class BatchResults implements Serializable
{

	/** The number of records returned from the execution of the statements.
	 */
	private int[] numOfRecords;

	/** A map contains an IdentityKey/Value of the identity columns that was assigned 
	 *  in this batch.
	 */
	private HashMap identityKeys;

	/**
	 * Constructor for BatchResults.
	 */
	public BatchResults(int[] numOfRecords, HashMap identityKeys)
	{
		super();
		this.numOfRecords = numOfRecords;
		this.identityKeys = identityKeys;
	}

	/**
	 * Returns the identity Keys that was assigned in this batch.
	 * @return HashMap
	 */
	public HashMap getIdentityKeys()
	{
		return identityKeys;
	}

	/**
	 * Returns an identity key values according to the given key.
	 * @param key The key of the identity column.
	 * @return List The values assigned for the key.
	 */
	public List getIdentityKeyValues(String key)
	{
		return (List) identityKeys.get(key);
	}

	/**
	 * Returns the last identity key value according to the given key.
	 * @param key The key of the identity column.
	 * @return Integer The last value assigned for the key.
	 */
	public Integer getIdentityKeyLastValue(String key)
	{
		Integer value = null;
		List values = getIdentityKeyValues(key);
		if (values.size() > 0)
		{
			value = (Integer) values.get(values.size() - 1);
		}
		return value;
	}

	/**
	 * Returns the number of records returned from the execution of the statements.
	 * @return int[]
	 */
	public int[] getNumOfRecords()
	{
		return numOfRecords;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String output = "numOfRecords: ";

		for (int i = 0; i < numOfRecords.length; i++)
		{
			output += " stmt[" + i + "]=" + numOfRecords[i] + ", ";
		}

		output += " IdentityKeys= " + getIdentityKeys();

		return output;
	}

}
