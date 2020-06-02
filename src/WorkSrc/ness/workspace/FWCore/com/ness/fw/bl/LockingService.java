/*
 * Author: yifat har-nof
 * @version $Id: LockingService.java,v 1.2 2005/04/12 13:41:56 baruch Exp $
 */
package com.ness.fw.bl;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.Query;
import com.ness.fw.persistence.SqlPropertiesFactory;
import com.ness.fw.persistence.SqlService;
import com.ness.fw.persistence.exceptions.LockException;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;
import com.ness.fw.util.Message;
import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.util.SystemProperties;

/**
 * A service for performing the locking of the LockableDAO's.
 */
public class LockingService
{
	/**
	 * The SELECT FOR UPDATE statement postfix to add to the key prefix of the 
	 * locking statements in the Properties file.
	 */
	public static final String LOCK_POSTFIX = ".lockingSelect";
	
	/**
	 * The UPDATE LOCK_ID + 1 statement postfix to add to the key prefix of the 
	 * locking statements in the Properties file.
	 */
	public static final String UPDATE_LOCK_POSTFIX = ".lockingUpdate";
	
	/**
	 * Performs lock to the business object according to the given lock parameters.
	 * 
	 * If the lock succeeded, compare the given original lock id from the lock parameters
	 * to the lock id that is current in the DB record.
	 * 
	 * If the lock id has changed in the db, throws LockException that another user has 
	 * changed the record.
	 * 
	 * If the readOnlyLocking is set to true, update the lock id + 1 in the DB record 
	 * and return the new lock id (to be set in the LockableDAO lock id field).
	 * @param parameters The LockParameters.
	 * @return The new lock id.
	 * @throws LockException Locking problems: The DB record is already locked or 
	 * the record was changed by another user.
	 * @throws ObjectNotFoundException The business object record wasn't found in 
	 * the DB Table according to the select for update statement.
	 * @throws PersistenceException Any other problems. 
	 */
	public static int lock(LockParameters parameters, boolean readOnlyLocking) throws ObjectNotFoundException, LockException, PersistenceException
	{
		if(parameters.getTransaction() == null || parameters.getOriginalLockId() == null)
		{
			throw new PersistenceException("Not enough parameters for locking.");
		}
		
		SystemProperties properties = SqlPropertiesFactory.getInstance().getProperties(parameters.getSqlPropertiesFileName());
		
		// get the current lockId while locking the record
		String lockStatement = properties.getProperty(parameters.getSqlPropertiesPrefix() + LOCK_POSTFIX);
		SqlService lockSqlService = new SqlService(lockStatement, parameters.getKeys());
		Page page = null; 
		try
		{
			page = Query.execute(lockSqlService, parameters.getTransaction());
		}
		// handle locked record in the db
		catch (LockException le)
		{
			Message msg = getMessage("GE0004", parameters, properties);
			throw new LockException(getDescription(parameters, properties)
				+ " is now locked by another user. keys: " 
				+ StringFormatterUtil.convertListToString(parameters.getKeys(), ","), 
				le.getCause(), msg);
		}
		
		if (!page.next())
		{
			Message msg = getMessage("GE0023", parameters, properties);
			throw new ObjectNotFoundException(getDescription(parameters, properties) 
				+ " does not exist. keys: " 
				+ StringFormatterUtil.convertListToString(parameters.getKeys(), ","), msg);
		}

		// compare the current lockId with the original lockId
		int currentLockId = page.getInt("LOCK_ID");
		if (currentLockId != parameters.getOriginalLockId().intValue())
		{
			Message msg = getMessage("GE0004", parameters, properties);
			throw new LockException(getDescription(parameters, properties)
				+ " has changed by another user . keys: " 
				+ StringFormatterUtil.convertListToString(parameters.getKeys(), ","), msg);
		}

		if(! readOnlyLocking)
		{
			// update lockId + 1 in the current transaction 
			currentLockId++;
			String updateLockStatement = properties.getProperty(parameters.getSqlPropertiesPrefix() + UPDATE_LOCK_POSTFIX);
			SqlService updateLockSqlService = new SqlService(updateLockStatement);
			updateLockSqlService.addParameter(new Integer(currentLockId));
			updateLockSqlService.addParameters(parameters.getKeys());
			parameters.getTransaction().execute(updateLockSqlService);
		}
		
		return currentLockId;
	}

	/**
	 * Returns the description of the business object from properties file. 
	 * @param parameters The LockParameters.
	 * @param properties The properties file.
	 * @return String business object description.
	 * @throws PersistenceException
	 */
	private static String getDescription (LockParameters parameters, SystemProperties properties) throws PersistenceException 
	{
//		String entityDescription = properties.getProperty(parameters.getBusinessObjectDescription());
//		if(StringFormatterUtil.isEmpty(entityDescription))
//			throw new PersistenceException 
//				("Property key " + parameters.getBusinessObjectDescription() + " not found in file " + properties.getFileName());
//		return entityDescription;
		return parameters.getBusinessObjectDescription(); 
	}

	/**
	 * Returns a Message object with the given messageId.
	 * @param messageId the message id to dispaly.
	 * @param parameters The LockParameters.
	 * @param properties The properties file.
	 * @return Message The message object to display.
	 * @throws PersistenceException
	 */
	private static Message getMessage (String messageId, LockParameters parameters, SystemProperties properties) throws PersistenceException 
	{
		Message msg = new Message(messageId,  Message.SEVERITY_ERROR);
		String description = getDescription(parameters, properties);
		msg.addLocalizableParameter(description);
		return msg;
	}

}
