/*
 * Author: yifat har-nof
 * @version $Id: ValueObject.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;

/**
 * Represent a state managed value object.
 */
public interface ValueObject extends Serializable, Cloneable
{
	
	/**
	 * Returns the unique id generated for the current object.
	 * @return Long 
	 */
	public Integer getUID();

	/** Check if the object was deleted.
	 * @return boolean Return true if the object was deleted.
	 */
	public boolean isDeleted();

	/** Check if the object contains changes that not made to the db.
	 * @return boolean Return true if the object is dirty.
	 */
	public boolean isDirty();

	/** Check if the object was newly created.
	 * @return boolean Return true if the object was newly created.
	 */
	public boolean isNewlyCreated();
	
}
