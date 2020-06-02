/*
 * Author: yifat har-nof
 * @version $Id: ValueObjectList.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a List of <code>ValueObject</code>s.
 */
public interface ValueObjectList extends Serializable, Cloneable
{

	/**
	 * Returns <code>ValueObject</code> object according to the given index.
	 * @param index The index of the ValueObject object to return.
	 * @return ValueObject
	 */
	public ValueObject getValueObject (int index);

	/**
	 * Returns the Identifiable object according the given id.
	 * @param id The id of the object to find.
	 * @return Identifiable The selected Identifiable.
	 */
	public Identifiable getIdentifiableById (Integer id); 

	/**
	 * returns the value objects count.
	 * @return int
	 */
	public int size();
	
	/**
	 * Returns the objects count that is not marked for delete.
	 * @return int
	 */
	public int getNonDeletedObjectsCount();

	/**
	 * Returns the keys of the RelatedIdentifiable that is not marked as deleted.
	 * @return ArrayList selected keys
	 */
	public ArrayList getRelatedIdentifiableSelectedKeys ();
	
	
}
