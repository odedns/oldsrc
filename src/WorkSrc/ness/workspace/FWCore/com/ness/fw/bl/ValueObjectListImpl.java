/*
 * Created on: 31/08/2004
 * Author: yifat har-nof
 * @version $Id: ValueObjectListImpl.java,v 1.2 2005/04/14 14:01:45 yifat Exp $
 */
package com.ness.fw.bl;

import java.util.ArrayList;
import java.util.List;

/**
 * A List of <code>ValueObject</code>s, for read only usage.
 */
public class ValueObjectListImpl implements ValueObjectList
{

	/**
	 * The List of <code>ValueObject</code>s.
	 */
	protected List objectList;
	
	/**
	 * Create new ValueObjectListImpl object.
	 * @param valueObjects A List of <code>ValueObject</code>s.
	 */
	protected ValueObjectListImpl(List valueObjects)
	{
		this.objectList = valueObjects;
	}

	/**
	 * Returns the objects count that is not marked for delete.
	 * @return int
	 */
	public int getNonDeletedObjectsCount()
	{
		int count = 0;
		
		if(objectList != null)
		{
			for (int i = 0 ; i < objectList.size() ; i++)
			{
				if(! ((ValueObject)objectList.get(i)).isDeleted())
				{
					count++;
				}
			}
		}
		
		return count;
	}

	/**
	 * Returns the keys of the RelatedIdentifiable that is not marked as deleted.
	 * @return ArrayList selected keys
	 */
	public ArrayList getRelatedIdentifiableSelectedKeys () 
	{
		ArrayList selectedKeys = new ArrayList();
		if(objectList != null)
		{
			for (int i=0 ; i < objectList.size() ; i++)
			{
				RelatedIdentifiable current = (RelatedIdentifiable)objectList.get(i);
				if(!current.isDeleted()) 
				{
					selectedKeys.add(String.valueOf(current.getRelatedId()));
				}
			}
		}
		return selectedKeys;
	}

	/**
	 * Returns <code>ValueObject</code> object according to the given index.
	 * @param index The index of the ValueObject object to return.
	 * @return ValueObject
	 */
	public ValueObject getValueObject(int index)
	{
		ValueObject result = null;
		if(objectList != null)
		{
			if(index < size()) 
			{
				result = (ValueObject) objectList.get(index);	
			}
		}
		return result;
	}

	/**
	 * Returns the Identifiable object according the given id.
	 * @param id The id of the object to find.
	 * @return Identifiable The selected Identifiable.
	 */
	public final Identifiable getIdentifiableById (Integer id) 
	{
		Identifiable selectedIdentifiable = null;
		if(objectList != null)
		{
			int size = objectList.size();
			for (int i = 0 ; i < size ; i++)
			{
				Identifiable current =	(Identifiable) objectList.get(i);
				if (current.getId()!= null && current.getId().equals(id))
				{
					selectedIdentifiable = current;
					break;
				}
			}
		}
		return selectedIdentifiable;
	}


	/**
	 * returns the value objects count.
	 * @return int
	 */
	public int size()
	{
		return objectList == null ? 0 : objectList.size();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer(256);
		
		int count = size();
		stringBuffer.append("className [");
		stringBuffer.append(getClass().getName());
		stringBuffer.append("], number of items is [");
		stringBuffer.append(count);
		stringBuffer.append("], number of non deleted items is [");
		stringBuffer.append(getNonDeletedObjectsCount());
		stringBuffer.append("],\n contains the objects: ");
		
		for (int index = 0 ; index < count ; index++)
		{
			stringBuffer.append("\n Obj[");
			stringBuffer.append(index);
			stringBuffer.append("]:\n");
			stringBuffer.append(getValueObject(index).toString());
		}
		stringBuffer.append("\n");
		
		return stringBuffer.toString();
	}

}
