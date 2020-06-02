/*
 * Author: yifat har-nof
 * @version $Id: LockParameters.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;
import java.util.List;

import com.ness.fw.persistence.Transaction;

/**
 *	This object contains the parameters for the {@link LockingService}.
 */
public class LockParameters implements Serializable, Cloneable
{
	/**
	 * The Transaction that the locking should be performed in his boundaries.
	 */
	private Transaction transaction;
	
	/**
	 * The name of the Properties file.
	 */
	private String sqlPropertiesFileName;
	
	/**
	 * The key prefix of the locking statements in the Properties file.
	 */
	private String sqlPropertiesPrefix;

	/**
	 * The name of the business object to translate from Localizable Properties 
	 * file and for logging.
	 */
	private String businessObjectDescription;
	
	/**
	 * The keys that identifies the business object, 
	 * in order to set in the locking statements in the given order. 
	 */
	private List keys;

	/**
	 * The value of the lockId field from the last refresh of the business object. 
	 */
	private Integer originalLockId;

	/**
	 * Short constructor with the basic values for the business object.
	 * The rest of the values should be set in the lock method every time.  
	 * @param keys The keys that identifies the business object, 
	 * in order to set in the locking statements in the given order. 
	 * @param sqlPropertiesFileName The name of the Properties file.
	 * @param sqlPropertiesKey The key prefix of the locking statements 
	 * in the Properties file.
	 */
	public LockParameters(List keys, String sqlPropertiesFileName, String sqlPropertiesPrefix, String businessObjectDescription)
	{
		this(null, keys, sqlPropertiesFileName, sqlPropertiesPrefix, businessObjectDescription, null);
	}
	
	/**
	 * A constructor with all the parameters required for the LockingService.
	 * @param originalLockId The value of the lockId field from the last refresh of the business object.
	 * @param keys The keys that identifies the business object, 
	 * in order to set in the locking statements in the given order. 
	 * @param sqlPropertiesFileName The name of the Properties file.
	 * @param sqlPropertiesKey The key prefix of the locking statements 
	 * in the Properties file.
	 * @param transaction The Transaction that the locking should be performed in his boundaries.
	 */
	public LockParameters(Integer originalLockId, List keys, String sqlPropertiesFileName, String sqlPropertiesPrefix, String businessObjectDescription, Transaction transaction)
	{
		this.originalLockId = originalLockId;
		this.keys = keys;
		this.sqlPropertiesFileName = sqlPropertiesFileName;
		this.sqlPropertiesPrefix = sqlPropertiesPrefix;
		this.businessObjectDescription = businessObjectDescription;
		this.transaction = transaction;		
	}

	/**
	 * @return Integer The value of the lockId field from the last refresh 
	 * of the business object.
	 */
	public Integer getOriginalLockId()
	{
		return originalLockId;
	}

	/**
	 * @return String The name of the Properties file.
	 */
	public String getSqlPropertiesFileName()
	{
		return sqlPropertiesFileName;
	}

	/**
	 * @return String The key prefix of the locking statements 
	 * in the Properties file.
	 */
	public String getSqlPropertiesPrefix()
	{
		return sqlPropertiesPrefix;
	}

	/**
	 * @return {@link Transaction} The Transaction that the locking should be performed in his boundaries. 
	 */
	public Transaction getTransaction()
	{
		return transaction;
	}

	/**
	 * @return keys A List of the keys that identifies the business object, 
	 * in order to set in the locking statements in the given order.
	 */
	public List getKeys()
	{
		return keys;
	}

	/**
	 * 
	 * @param originalLockId The value of the lockId field from the last refresh of the business object.
	 * @param transaction The {@link Transaction} that the locking should be performed in his boundaries.
	 */
	public void setCurrentLockingValues(int originalLockId, Transaction transaction)
	{
		this.originalLockId = new Integer(originalLockId);
		this.transaction = transaction;
	}

	/**
	 * Clear the values of the fields that should be set every time the lock action performed.
	 * (Transaction, originalLockId). 
	 */
	public void clearCurrentLockingValues()
	{
		originalLockId = null;
		transaction = null;
	}

	/**
	 * @param sqlPropertiesFileName The name of the Properties file.
	 */
	public void setSqlPropertiesFileName(String sqlPropertiesFileName)
	{
		this.sqlPropertiesFileName = sqlPropertiesFileName;
	}

	/**
	 * @param sqlPropertiesKey The key prefix of the locking statements 
	 * in the Properties file.
	 */
	public void setSqlPropertiesPrefix(String sqlPropertiesPrefix)
	{
		this.sqlPropertiesPrefix = sqlPropertiesPrefix;
	}

	/**
	 * @param keys A List of the keys that identifies the business object, 
	 * in order to set in the locking statements in the given order. 
	 */
	public void setKeys(List keys)
	{
		this.keys = keys;
	}

	/**
	 * Returns the name of the business object to translate from Localizable 
	 * Properties file and for logging.
	 * @return String businessObjectDescription
	 */
	public String getBusinessObjectDescription()
	{
		return businessObjectDescription;
	}

	/**
	 * Sets the name of the business object to translate from Localizable 
	 * Properties file and for logging.
	 * @param String businessObjectDescription
	 */
	public void setBusinessObjectDescription(String businessObjectDescription)
	{
		this.businessObjectDescription = businessObjectDescription;
	}

}
