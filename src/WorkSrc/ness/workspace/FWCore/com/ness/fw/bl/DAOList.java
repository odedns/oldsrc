/*
 * Author: yifat har-nof
 * @version $Id: DAOList.java,v 1.6 2005/05/01 10:33:21 yifat Exp $
 */
package com.ness.fw.bl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.Batch;
import com.ness.fw.persistence.StateManagedList;
import com.ness.fw.persistence.Transaction;
import com.ness.fw.util.TypesUtil;

/**
 * A List of <code>DAO</code> objects.
 */
public class DAOList extends ValueObjectListImpl implements StateManagedList
{
	
	/**
	 * Create new DAOList object.
	 */
	public DAOList()
	{
		super(new ArrayList());
	}

	/**
	 * Create new DAOList object with a DAOList to add into the new list.
	 * @param daoList The DAO's to add the new list.
	 */
	public DAOList (DAOList daoList)
	{
		super(new ArrayList(daoList.size()));
		for(int i = 0; i < daoList.size() ; i++) 
		{
			add(daoList.get(i));
		}
	}

	/**
	 * Create new DAOList object with a DAO[] to add into the new list.
	 * @param daoArray The DAO's to add the new list.
	 */
	public DAOList (DAO[] daoArray)
	{
		super(new ArrayList(daoArray.length));
		for(int i = 0; i < daoArray.length ; i++) 
		{
			add(daoArray[i]);
		}
	}


	/**
	 * Add a <code>DAO</code> object to the DAO objects list.
	 * @param dao The DAO object to add.
	 */
	public final void add (DAO dao)
	{
		objectList.add(dao);
	}

	/**
	 * Returns <code>DAO</code> object according to the given index.
	 * @param index The index of the DAO object to return.
	 * @return DAO
	 */
	public final DAO get (int index)
	{
		DAO result = null;
		if(index < size()) 
		{
			result = (DAO) objectList.get(index);	
		}
		return result;
	}

	/**
	 * Sets the <code>DAO</code> object in the given index.
	 * @param index The index of the DAO object to return.
	 * @param dao The DAO object to set.
	 */
	public final void set (int index, DAO dao)
	{
		objectList.set(index, dao);	
	}
	
	/**
	 * Removes the <code>DAO</code> object.
	 * @param dao The <code>DAO</code> to remove.
	 * @return boolean Returns true when the DAO object was found and removed.
	 */
	public final boolean remove (DAO dao)
	{
		return objectList.remove(dao);
	}


	/** 
	 * A helper method for activates the save operation 
	 * on the {@link DAO} objects.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @param batch The {@link Batch} object to add the statements to him.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	public final void save (Transaction transaction, Map keys, Batch batch) throws BusinessLogicException, PersistenceException, FatalException
	{
		// register the object to the transaction, 
		// for removing after commit the deleted / deleted newly created objects.
		transaction.registerStateManagedList(this);
		
		int count = size();
		for(int index = 0 ; index < count ; index++)
		{
			DAO dao = (DAO) objectList.get(index);
			if (dao != null)
			{
				dao.save(transaction, keys, batch);
			}
		}
	}

	/**
	 * Returns the index of the given DAO.
	 * @param dao DAO to find
	 * @return int The DAO index
	 */
	public final int indexOf (DAO dao)
	{
		return objectList.indexOf(dao);
	}

	/**
	 * Returns an array of the DAO objects inside the list.
	 * @return Object[]
	 */
	public final Object[] toArray ()
	{
		return objectList.toArray();
	}

	/**
	 * Returns the DAO according the unique id generated for the object.
	 * @param UId The unique id generated for the object to find.
	 * @return DAO The selected DAO.
	 */
	public final DAO getByUID (Integer UId) 
	{
		DAO selectedDAO = null;
		int size = objectList.size();
		for (int i = 0 ; i < size ; i++)
		{
			DAO current = get(i);
			if (current.getUID()!= null && current.getUID().equals(UId))
			{
				selectedDAO = current;
				break;
			}
		}
		return selectedDAO;
	}

	/**
	 * A helper method for handling selection changes in lists with multiple selection.
	 * The Objects in the current DAOList should implement {@link RelatedIdentifiable} interface, 
	 * and they represent the old selected values from the business object.
	 * The selected Ids represent the new selected values.
	 * This method mark as deleted the RelatedIdentifiables that was removed from the list 
	 * and does not exists in the selectedIds list.
	 * This method also returns a List with the new selected Ids, and the business object should create 
	 * a new {@link RelatedIdentifiable} object for each of them.
	 * @param selectedIds A list of String Ids of the NEW selected values. 
	 * @return newIdsList A List with new selected Ids to create for them new DAO Objects.
	 */
	public final List setSelectedRelatedIdentifiables(
		List selectedIds)
	{
		List newIds = new ArrayList(0);
		int currentObjectsCount = size();
		int selectedCount = selectedIds.size();
		boolean[] selectedIdExists = new boolean[selectedCount];

		List numericSelectedIds = new ArrayList(selectedCount);
		for (int i = 0; i < selectedCount; i++)
		{
			Integer current = TypesUtil.convertStringToInteger(selectedIds.get(i));
			numericSelectedIds.add(current);
		}

		//	check if should remove all items
		if (selectedCount == 0 && currentObjectsCount > 0)
		{
			for (int i = 0; i < currentObjectsCount; i++)
			{
				DAO current = get(i);
				current.setStateAsDeleted();
			}
		}
		else if (selectedCount > 0 && currentObjectsCount == 0)
		{
			// all the selected Ids is new 
			newIds.addAll(numericSelectedIds);
		}
		else 
		{
			int index;
			// Loop on the current objects in the DAOList
			for (int i = 0; i < currentObjectsCount; i++)
			{
				RelatedIdentifiable current = (RelatedIdentifiable) objectList.get(i);

				// check if the current Identifiable is still selected  
				index = numericSelectedIds.indexOf(current.getRelatedId());
				if (index != -1)
				{ // mark as already exist in the list
					selectedIdExists[index] = true;
				}
				else
				{ // item removed from the selected list
					((DAO) current).setStateAsDeleted();
				}
			}

			for (int i = 0; i < selectedCount; i++)
			{
				if(! selectedIdExists[i])
				{
					newIds.add(numericSelectedIds.get(i));
				}
			}			
		}
		return newIds;
	}

	/**
	 * Clears the objects in status DELETED / DELETE_NEWLEY_CREATED from the list.
	 */
	public void clearObjectsState () throws FatalException
	{
		int count = objectList.size();
		List daosToRemove = null;
		for (int index = 0 ; index < count ; index++)
		{
			DAO dao = get(index);
			if(dao.ignoreObject())
			{
				if(daosToRemove == null)
				{
					daosToRemove = new ArrayList();
				}
				daosToRemove.add(dao);
			}
		}
		
		if(daosToRemove != null)
		{
			for (int index = 0 ; index < daosToRemove.size() ; index++)
			{
				remove ((DAO)daosToRemove.get(index));
			}
		}
	}

	/** 
	 * A helper method for activates the setStateAsDeleted operation 
	 * on the {@link DAO} objects.
	 */
	public final void setAllAsDeleted ()
	{
		int count = size();
		for(int index = 0 ; index < count ; index++)
		{
			DAO dao = (DAO) objectList.get(index);
			dao.setStateAsDeleted();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() throws CloneNotSupportedException
	{
		DAOList clonedList = new DAOList();
		int count = objectList.size();
		for (int index = 0 ; index < count ; index++)
		{
			DAO clonedDao = (DAO)get(index).clone();
			clonedList.add(clonedDao);			
		}
		return clonedList;
	}

}
