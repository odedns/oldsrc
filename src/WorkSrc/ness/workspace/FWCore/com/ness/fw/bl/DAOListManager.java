/*
 * Created on: 26/10/2004
 * Author: yifat har-nof
 * @version $Id: DAOListManager.java,v 1.5 2005/04/28 14:56:36 baruch Exp $
 */
package com.ness.fw.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.Batch;
import com.ness.fw.persistence.Lockable;
import com.ness.fw.persistence.Transaction;

/**
 * A manager for DAO list without a specific parent DAO to manage them.
 * The users of this class should fill the list with Dao's according to the 
 * records count in the DB, because it should reflect the state of the list in 
 * the DB. Otherwise the managing of the list will not be reliable. 
 */
public abstract class DAOListManager
{
	/**
	 * The DAO list
	 */
	private DAOList daoList = null;
	
	/**
	 * A list of LockableDao's to lock before the save.
	 */
	private List lockableList = null;

	/**
	 * Creates new DAOListManager with a specified DAOList.
	 * @param daoList
	 */
	public DAOListManager(DAOList daoList)
	{
		this.daoList = daoList;
	}

	/**
	 * Creates new DAOListManager without DAOList.
	 */	
	public DAOListManager()
	{
	}

	/**
	 * Initialize the DAOList attribute, when it is null.
	 */
	protected void initList()
	{
		if (daoList == null) 
		{
			daoList = new DAOList();
		}		
	}

	/**
	 * Returns the list as ValueObjectList.
	 * @return ValueObjectList
	 */
	protected ValueObjectList getValueObjectList()
	{
		initList();
		return daoList;
	}

	/**
	 * Returns the count of the objects in the list.
	 * @return int
	 */
	protected int getCount()
	{
		return daoList == null ? 0 : daoList.size();
	}

	/**
	 * Returns the DAO according to his id.
	 * The DAO should implement Identifiable interface.
	 * @param id The id of the DAO.
	 * @return DAO
	 */
	protected DAO getById(Integer id)
	{
		initList();
		return (DAO)daoList.getIdentifiableById(id);		
	}

	/**
	 * Returns the DAO according to his UID (unique id).
	 * @param uID The unique id of the DAO (produce by the method getUID of DAO).
	 * @return DAO
	 */
	protected DAO getByUID(Integer uID)
	{
		initList();
		return daoList.getByUID(uID);		
	}

	/**
	 * Add a DAO object to the DAO objects list.
	 * @param dao The DAO object.
	 */
	protected void add(DAO dao)
	{
		initList();
		daoList.add(dao);
	}

	/**
	 * Mark the dao for deletion.
	 * @param dao The DAO to remove.
	 * @return boolean Return true if the object can be set to deleted.
	 */
	protected boolean remove(DAO dao)
	{
		return dao.setStateAsDeleted();
	}

	/**
	 * Returns the index of the given DAO.
	 * @param dao DAO to find
	 * @return int The DAO index
	 */
	protected final int indexOf (DAO dao)
	{
		return daoList.indexOf(dao);
	}

	/**
	 * Returns an array of the DAO objects inside the list.
	 * @return Object[]
	 */
	protected final Object[] toArray ()
	{
		return daoList.toArray();
	}

	/** 
	 * Activates the save operation on the {@link DAO} objects in the list.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @param batch The {@link Batch} object to add the statements to him.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	public final void save (Transaction transaction, Map keys, Batch batch) throws BusinessLogicException, PersistenceException, FatalException
	{
		daoList.save(transaction, keys, batch);		
	}

	/** 
	 * Activates the save operation on the {@link DAO} objects in the list.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	public final void save (Transaction transaction, Map keys) throws BusinessLogicException, PersistenceException, FatalException
	{
		daoList.save(transaction, keys, null);		
	}

	/**
	 * Returns the count of the lockable objects in the list.
	 * @return int
	 */
	protected int getLockablesCount()
	{
		return lockableList == null ? 0 : lockableList.size();
	}
	
	/**
	 * Add a LockableDAO to the end of the Dao list to lock 
	 * before the save.
	 * @param lockableDAO
	 */
	protected final void addLockable (LockableDAO lockableDAO)
	{
		if(lockableList == null)
		{
			lockableList = new ArrayList(1);
		}
		
		if(! lockableList.contains(lockableDAO))
		{
			lockableList.add(lockableDAO);
		}
	}
	
	/**
	 * Returns <code>Lockable</code> object according to the given index.
	 * @param index The index of the Lockable object to return.
	 * @return Lockable
	 */
	public Lockable getLockable(int index)
	{
		Lockable result = null;
		if(lockableList != null)
		{
			if(index < getLockablesCount()) 
			{
				result = (Lockable) lockableList.get(index);	
			}
		}
		return result;
	}
	
	/**
	 * Returns the list LockableDAO's to lock before the save.
	 * @return List
	 */
	protected final List getLockablesList ()
	{
		return lockableList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer(256);
		
		if (daoList != null)
		{
			stringBuffer.append(daoList.toString());
		}
		
		int lockCount = getLockablesCount(); 

		stringBuffer.append("\n, number of lockable items is [");
		stringBuffer.append(lockCount);
		stringBuffer.append("], contains the lockable objects: ");
		
		for (int index = 0 ; index < lockCount ; index++)
		{
			stringBuffer.append("\n Lockable[");
			stringBuffer.append(index);
			stringBuffer.append("]:\n");
			stringBuffer.append(getLockable(index).toString());
		}
		stringBuffer.append("\n");
		
		return stringBuffer.toString();
	}	
}
