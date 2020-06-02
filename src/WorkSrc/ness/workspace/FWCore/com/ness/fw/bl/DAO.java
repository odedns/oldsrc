/*
 * Author: yifat har-nof
 * @version $Id: DAO.java,v 1.10 2005/05/09 06:51:27 yifat Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;
import java.util.*;

import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.LockException;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.logger.Logger;

/**
 * Generic Business Object class.
 * 
 * <p>
 * A Business Object must inherit this class so that it can participate
 * in this package transactions scheme.
 * </p>
 */
public abstract class DAO 
	implements Serializable, Cloneable, StateManaged
{

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "BL: DAO";

	/** A constant indicates a newly created row that not exist in the db
	 *  and the action of insertion was not performed yet.
	 */
	protected static final int NEWLY_CREATED = 1;
	private static final String STATE_NAME_NEWLY_CREATED = "NEWLY_CREATED";

	/** A constant indicates a row that was originally existed in the db, 
	 *  which contain changes that not made to the db 
	 *  and the action of update was not performed yet.
	 */
	protected static final int EXISTING_DIRTY = 2;
	private static final String STATE_NAME_EXISTING_DIRTY = "EXISTING_DIRTY";

	/** A constant indicates a row that was originally existed in the db, 
	 *  and don't contain changes that not made to the db. 
	 */
	protected static final int EXISTING_NON_DIRTY = 3;
	private static final String STATE_NAME_EXISTING_NON_DIRTY = "EXISTING_NON_DIRTY";

	/** A constant indicates a deleted row that was originally existed in the db 
	 *  and the action of deletion was not performed yet.
	 */
	protected static final int DELETE_EXISTING = 4;
	private static final String STATE_NAME_DELETE_EXISTING = "DELETE_EXISTING";

	/** A constant indicates a deleted row that was originally newly created 
	 *  and didn't exist in the db.
	 */
	protected static final int DELETE_NEWLY_CREATED = 5;
	private static final String STATE_NAME_DELETE_NEWLY_CREATED = "DELETE_NEWLY_CREATED";

	/** A constant indicates a deleted row that was originally existed in the db 
	 *  and the action of deletion was already performed.
	 */
	protected static final int DELETED = 6;
	private static final String STATE_NAME_DELETED = "DELETED";

	/** The state of the row in the DB.
	 */
	private int objectState = NEWLY_CREATED;

	/** The unique id generated for the current object.
	 */
	private Integer uID = null;

	/** The Batch object to add the statements.
	 */
	private Batch currentBatch = null;

	/**
	 * Default constructor
	 */
	public DAO()
	{
		super();
	}

	/**
	 * Check if should handle the object (not deleted, unmarked etc.)
	 * @return boolean Return true if the object should be ignored.
	 */
	protected boolean ignoreObject()
	{
		return objectState == DELETE_NEWLY_CREATED
			|| objectState == DELETED;
	}

	/** 
	 * Should be implemented by the business objects.
	 * Activate the save operation on the contained objects.
	 * can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	}

	/** 
	 * Should be implemented by the business objects.
	 * 
	 * Perform delete to the contained Objects.
	 * Could mark them for deletion and perform save to the DAOList 
	 * or 
	 * call to static method in the contained object class that perform 
	 * deletion to his contained objects and then to himself. 
	 * 
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected void deleteContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	}

	/** 
	 * To perform activities after the contained objects save themselves.
	 * Could be implemented by the business objects.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected void postSave(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	}

	/** 
	 * To perform activities before the saving of the current object.
	 * Could be implemented by the business objects.
	 * Can use the methods <code>isDeleted</code>, <code>isNewlyCreated</code> & 
	 * <code>isDirty</code> to check the object state.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected void checkBeforeSave(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	}

	/** 
	 * To perform activities after the checking & before the saving of the current object.
	 * Could be implemented by the business objects.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected void preSave(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	}


	/** 
	 * Save the current object & his contained objects into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @return The keys of the object (if the object was newly created.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	public final Map save(Transaction transaction)
		throws BusinessLogicException, PersistenceException, FatalException
	{
		return save(transaction, null, null);
	}


	/** 
	 * Save the current object & his contained objects into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param Batch The {@link Batch} object to add the statements to him.
	 * @return The keys of the object (if the object was newly created.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	public final Map save(Transaction transaction, Batch batch) throws BusinessLogicException, PersistenceException, FatalException
	{
		return save(transaction, null, batch);
	}

	/** 
	 * Save the current object & his contained objects into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @param Batch The {@link Batch} object to add the statements to him.
	 * @return The keys of the object (if the object was newly created.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	public final Map save(Transaction transaction, Map keys, Batch batch) throws BusinessLogicException, PersistenceException, FatalException
	{
		Logger.debug(LOGGER_CONTEXT, "Execute save in DAO [" + this.getClass().getName() + "]");

		Map localKeys = new HashMap();
		if(keys != null)
			localKeys.putAll(keys);

		if (!ignoreObject())
		{
			try
			{
				// perform checks before the saving of the object
				checkBeforeSave(transaction, keys);

				if (objectState != EXISTING_NON_DIRTY)
				{
					// register the dao in the transaction.
					((Transaction)transaction).registerStateManaged(this);
				}

				// perform actions before the saving of the object 
				preSave(transaction, localKeys);
				
				// initialize the current batch object
				currentBatch = batch;

				// get the keys of the current object
				if (objectState != NEWLY_CREATED)
				{
					setKeyValues(localKeys);
				}

				// first save the contained objects in case of deleted object.
				if (objectState == DELETE_EXISTING)
				{
					// activate the delete operation on the contained objects. 
					deleteContainedObjects(transaction, localKeys);
				}

				// save the current object 
				if (objectState != EXISTING_NON_DIRTY)
				{
					if (objectState == NEWLY_CREATED)
					{
						insert(transaction, localKeys);
						setKeyValues(localKeys);
					}
					else if (objectState == EXISTING_DIRTY)
					{
						update(transaction, localKeys);
					}
					else if (objectState == DELETE_EXISTING)
					{
						delete(transaction, localKeys);
					}
				}

				if (objectState != DELETE_EXISTING)
				{
					// activate the save operation on the contained objects. 
					saveContainedObjects(transaction, localKeys);
				}

				// perform actions after the contained objects saved 
				postSave(transaction, localKeys);
				
			} 
			finally
			{
				// clear current batch object
				currentBatch = null;
			}
		}
		return localKeys;
	}

	//-------------------- //
	// Persistable methods //
	//-------------------- //

	/** 
	 * Insert this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies the parent object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	protected abstract void insert(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException;

	/** 
	 * Update this object into the database. 
	 * In case of Lockable objects, don't update the field LockId, 
	 * because it must be updated only by the LockManager.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies the parent object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	protected abstract void update(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException;

	/** 
	 * Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies the parent object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	protected abstract void delete(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException;

	/** 
	 * Add the keys of the current object to the keys of his parent object.
	 * @param keys The keys of the parent object to add the keys of the current object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected abstract void setKeyValues(Map keys);


	/**
	 * Returns the unique id generated for the current object.
	 * @return Long 
	 */
	public Integer getUID()
	{
		if (isNewlyCreated() && uID == null)
		{
			uID = new Integer(this.hashCode());
		}
		return uID;
	}

	/**
	 * Clears the unique id generated for the current object.
	 */
	public void clearUID()
	{
		uID = null;
	}

	/**
	 * Returns the object state in the DB.
	 * @return int The object state.
	 */
	public int getObjectState()
	{
		return objectState;
	}
	
	/**
	 * Returns the Batch object to add the statements.
	 * @return Batch
	 */
	protected Batch getCurrentBatch()
	{
		return currentBatch;
	}

	/**
	 * To perform activities after committing the changes to the db, 
	 * before changing the state of the object.
	 * Could be implemented by the business objects.
	 * @throws FatalException
	 */
	protected void beforeClearObjectState()
		throws FatalException
	{
	}


	/**
	 * Clears the object state to non-dirty for the current object.
	 * Can be overridden for cleaning of the persistable children’s.
	 * @return boolean Return true if the object can be set to non dirty.
	 */
	public void clearObjectState() throws FatalException 
	{
		beforeClearObjectState();
		
		if (ignoreObject())
			return;

		if (objectState == DELETE_EXISTING)
		{
			objectState = DELETED;
		}
		else if (objectState == EXISTING_DIRTY || objectState == NEWLY_CREATED)
		{
			objectState = EXISTING_NON_DIRTY;
		}
	}

	/** Check if the object was deleted.
	 * @return boolean Return true if the object was deleted.
	 */
	public final boolean isDeleted()
	{
		return objectState == DELETE_EXISTING
			|| objectState == DELETE_NEWLY_CREATED
			|| objectState == DELETED;
	}

	/** Check if the object was deleted and was before newly created.
	 * @return boolean Return true if the object was deleted newly created.
	 */
	public final boolean isDeletedNewlyCreated()
	{
		return objectState == DELETE_NEWLY_CREATED;
	}

	/** Check if the object was newly created.
	 * @return boolean Return true if the object was newly created.
	 */
	public final boolean isNewlyCreated()
	{
		return objectState == NEWLY_CREATED;
	}

	/** Check if the object contains changes that not made to the db.
	 * @return boolean Return true if the object is dirty.
	 */
	public final boolean isDirty()
	{
		return objectState == NEWLY_CREATED
			|| objectState == EXISTING_DIRTY
			|| objectState == DELETE_EXISTING;
	}

	/**
	 * Sets row state to dirty.
	 * @return boolean Return true if the object can be set to dirty.
	 */
	public final boolean setStateAsDirty()
	{
		if (objectState == EXISTING_NON_DIRTY)
		{
			objectState = EXISTING_DIRTY;
			return true;
		}
		return false;
	}

	/**
	 * Sets row state to dirty, only when the oldValue is not equals to the newValue.
	 * @param oldValue The old value of the field
	 * @param newValue The new value of the field
	 * @return boolean Return true if the field value has changed.
	 */
	public final boolean setStateAsDirty(Object oldValue, Object newValue)
	{
		boolean valueChanged = true; 
		if(oldValue == null && newValue == null)
		{
			valueChanged = false; 
		}
		else if(oldValue != null && newValue != null && oldValue.equals(newValue))
		{
			valueChanged = false;
		}
		
		if(valueChanged)
		{
			setStateAsDirty();
		}
		
		return valueChanged;
	}

	/**
	 * Sets row state to delete.
	 * @return boolean Return true if the object can be set to deleted.
	 */
	public final boolean setStateAsDeleted()
	{
		boolean rc = true;
		
		if (objectState == EXISTING_NON_DIRTY
			|| objectState == EXISTING_DIRTY)
		{
			objectState = DELETE_EXISTING;
		}
		else if (objectState == DELETED)
		{
			rc = false;
		}
		else if (objectState == NEWLY_CREATED)
		{
			objectState = DELETE_NEWLY_CREATED;
		}
		return rc;
	}
	
	/**
	 * Sets row state to existing non dirty.
	 */
	public final void setStateAsNonDirty()
	{
		objectState = EXISTING_NON_DIRTY;
	}

	/**
	 * Sets row state to newly created.
	 */
	protected final void setStateAsNewlyCreated()
	{
		objectState = NEWLY_CREATED;
	}

	/**
	 * Returns the name of the identity key to be set in the batch results.
	 * @return String
	 */
	private String getIdentityKeyName ()
	{
		return getClass().getName();
	}

	/**
	 * Execute the sql statement with the parameters.
	 * If the Batch parameter is not null, add the statement to the batch. 
	 * Otherwise execute the statement (using SqlService) within the transaction.

	 * Used for static methods that should perform db operations without a 
	 * DAO instance, like ChildDAO.deleteByParentId. 
	 *  
	 * @param sqlStatement The base Sql statement to execute
	 * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	 * @param transaction A {@link  Transaction} object to execute the statement. 
	 * @param batch The batch object to add the statement (optional).
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected static final void executeStatement(
		String sqlStatement,
		List parameters,
		Transaction transaction,
		Batch batch)
		throws PersistenceException
	{
		executeStatementInternal(sqlStatement, parameters, transaction, batch, false, null);
	}

	/**
	 * Execute the sql statement with the parameters.
	 * If the save operation contains Batch object, add the statement to the batch. 
	 * Otherwise execute the statement (using SqlService) within the transaction.
	 * @param sqlStatement The base Sql statement to execute
	 * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	 * @param transaction A {@link  Transaction} object to execute the statement. 
	 * @param batch The batch object to add the statement (optional).
	 * @param autoId Indicates whether the key generated automatically in this INSERT statement.
	 * @param identityKeyClassName The class name of the DAO that manage the statement execution. 
	 * used for relating the automatic generated key.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private static final void executeStatementInternal(
		String sqlStatement,
		List parameters,
		Transaction transaction,
		Batch batch,
		boolean autoId,
		String identityKeyClassName)
		throws PersistenceException
	{
		// execute the statement
		if (batch == null)
		{
			SqlService sqlService = new SqlService(sqlStatement, parameters);
			transaction.execute(sqlService);
		}
		else
		{
			// add the statement to the batch
			// if the statement contains insert with identity key, 
			// should execute the batch and get the identity key value.
			String identityKey = null;
			if(autoId)
			{
				identityKey = identityKeyClassName;
			}
			
			int index =	batch.addStatementString(sqlStatement, identityKey);
			batch.addStatementExecution(index, parameters);
		}
	}


	/**
	 * Execute the sql statement with the parameters.
	 * If the save operation contains Batch object, add the statement to the batch. 
	 * Otherwise execute the statement (using SqlService) within the transaction.
	 * @param sqlStatement The base Sql statement to execute
	 * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	 * @param transaction A {@link  Transaction} object to execute the statement.
	 * @param autoId Indicates whether the key generated automatically in this INSERT statement.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	private final void executeStatementInternal(
		String sqlStatement,
		List parameters,
		Transaction transaction,
		boolean autoId)
		throws PersistenceException
	{

		// add the statement to the batch
		// if the statement contains insert with identity key, 
		// should execute the batch and get the identity key value.
		String identityKey = null;
		if(currentBatch != null && autoId)
		{
			identityKey = getIdentityKeyName();
		}
		executeStatementInternal(sqlStatement, parameters, transaction, currentBatch, autoId, identityKey);
	}

	/**
	 * Execute the sql statement with the parameters.
	 * If the save operation contains Batch object, add the statement to the batch. 
	 * Otherwise execute the statement (using SqlService) within the transaction.
	 * @param sqlStatement The base Sql statement to execute
	 * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	 * @param transaction A {@link  Transaction} object to execute the statement. 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final void executeStatement(
		String sqlStatement,
		List parameters,
		Transaction transaction)
		throws PersistenceException
	{
		executeStatementInternal(sqlStatement, parameters, transaction, false);
	}

	/**
	 * Execute the insert sql statement with the parameters.
	 * If the save operation contains Batch object, add the statement to the batch. 
	 * Otherwise execute the statement (using SqlService) within the transaction.
	 * Support getting the value of identity key after insert statements. 
	 * @param sqlStatement The base Sql statement to execute
	 * @param parameters The parameters to use replacing the <B>"?"</B> placeholders.
	 * @param transaction A {@link  Transaction} object to execute the statement. 
	 * @param autoId Indicates whether the key generated automatically in this INSERT statement.
	 * @return Integer The assigned identity key value. 
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final Integer executeInsertStatement(
		String sqlStatement,
		List parameters,
		Transaction transaction,
		boolean autoId)
		throws PersistenceException
	{
		Integer identityKeyValue = null;
				
		// execute the statement
		executeStatementInternal(sqlStatement, parameters, transaction, autoId);	
		
		if (autoId)
		{
			if (currentBatch == null)
			{
				identityKeyValue = new Integer(transaction.getLastIdentityKey());
			}
			else
			{
				BatchResults results = transaction.execute(currentBatch);
				identityKeyValue = results.getIdentityKeyLastValue(getIdentityKeyName());
			}
		}

		return identityKeyValue;
	}

	/**
	 * Init the DAOLIst in new DAOList hwne the given list is null and the 
	 * current DAO is in state of newly created and cannot contain child dao's
	 * in the DB.
	 * @param childList The DAOList of the childs to check.
	 * @return The child DAOList after initialization, if necessary. 
	 */
	protected DAOList initDAOListIfNewlyCreated(DAOList childList)
	{
		if (isNewlyCreated() && childList == null)
		{
			childList = new DAOList();
		}
		return childList;
	}

	/**
	 * Execute the query according to the SqlService within the given connection.
	 * @param sqlService The SqlService contains the query sql statement and parameters
	 * @param cp The ConnectionProvider to use its connection.
	 * @param maxRowsInPage The maximum number of rows to return in the page.
	 * @return Page The query results as a {@link Page} object.
	 * @throws LockException
	 * @throws PersistenceException
	 */
	protected static final Page executeQuery(
		SqlService sqlService,
		ConnectionProvider cp,
		int maxRowsInPage)
		throws LockException, PersistenceException
	{
		return Query.execute(sqlService, cp, maxRowsInPage);
	}

	/**
	 * Execute the query according to the given sql statement & parameters, 
	 * within the given connection.
	 * @param sqlStatement The sql statement to execute.
	 * @param parameters The List of parameters to set in the statement.
	 * @param cp The ConnectionProvider to use its connection.
	 * @param maxRowsInPage The maximum number of rows to return in the page.
	 * @return Page The query results as a {@link Page} object.
	 * @throws LockException
	 * @throws PersistenceException
	 */
	protected static final Page executeQuery(
		String sqlStatement,
		List parameters,
		ConnectionProvider cp,
		int maxRowsInPage)
		throws LockException, PersistenceException
	{
		SqlService sqlService = new SqlService(sqlStatement, parameters);
		return executeQuery(sqlService, cp, maxRowsInPage);
	}

	/**
	 * Execute the query with the default limit of rows returned, 
	 * according to the given sql statement & parameters, within the given connection.
	 * @param sqlStatement The sql statement to execute.
	 * @param parameters The List of parameters to set in the statement.
	 * @param cp The ConnectionProvider to use its connection.
	 * @return Page The query results as a {@link Page} object.
	 * @throws LockException
	 * @throws PersistenceException
	 */
	protected static final Page executeQuery(
		String sqlStatement,
		List parameters,
		ConnectionProvider cp)
		throws LockException, PersistenceException
	{
		SqlService sqlService = new SqlService(sqlStatement, parameters);
		return executeQuery(sqlService, cp, 0);
	}

	/**
	 * Call to save method in the <code>DAOList</code>.
	 * To be used inside saveContainedObjects method implementation.
	 * @param list DAOList
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 * @throws FatalException Any FatalException that may occur.
	 */
	protected final void saveDAOList (DAOList list, Transaction transaction, Map keys) throws BusinessLogicException, PersistenceException, FatalException
	{
		if(list != null) 
			list.save(transaction, keys, currentBatch);
	}

	/**
	 * Call to save method in the given child <code>DAO</code>.
	 * To be used inside saveContainedObjects method implementation.
	 * @param childDAO The child DAO to save.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected final void saveChildDAO (DAO childDAO, Transaction transaction, Map keys) throws BusinessLogicException, PersistenceException, FatalException  
	{
		if(childDAO != null) 
			childDAO.save(transaction, keys, currentBatch);
	}
	
	protected final String getObjectStateName ()
	{
		String stateName = null;
		
		switch (objectState)
		{
			case NEWLY_CREATED :
			{
				stateName = STATE_NAME_NEWLY_CREATED;
				break;
			}
			case EXISTING_NON_DIRTY :
			{
				stateName = STATE_NAME_EXISTING_NON_DIRTY;
				break;
			}
			case EXISTING_DIRTY :
			{
				stateName = STATE_NAME_EXISTING_DIRTY;
				break;
			}
			case DELETE_EXISTING :
			{
				stateName = STATE_NAME_DELETE_EXISTING;
				break;
			}
			case DELETE_NEWLY_CREATED :
			{
				stateName = STATE_NAME_DELETE_NEWLY_CREATED;
				break;
			}
			case DELETED :
			{
				stateName = STATE_NAME_DELETED;
				break;
			}
		}
		
		return stateName;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer(256);
		
		stringBuffer.append("DAO: className [");
		stringBuffer.append(getClass().getName());
		stringBuffer.append("], objectState [");
		stringBuffer.append(getObjectStateName());
		stringBuffer.append("], UID [");
		stringBuffer.append(uID);
		
		if(this instanceof Identifiable)
		{
			stringBuffer.append("], ID [");
			stringBuffer.append(((Identifiable)this).getId());
		}

		if(this instanceof RelatedIdentifiable)
		{
			stringBuffer.append("], RelatedID [");
			stringBuffer.append(((RelatedIdentifiable)this).getRelatedId());
		}

		stringBuffer.append("]\n");
		
		return stringBuffer.toString();
	}	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException
	{
		DAO clonedDao = (DAO)super.clone();
		clonedDao.setStateAsNewlyCreated();
		clonedDao.clearUID();
		return clonedDao;
	}

}