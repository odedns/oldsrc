/*
 * Author: yifat har-nof
 * @version $Id: RelatedIdentifiable.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;

/**
 * Every business object that represent a relational object and the relation 
 * identify by one identity column with type int should implement this interface
 */
public interface RelatedIdentifiable extends Serializable, Cloneable
{
	/**
	 * Returns the identifier of the object.
	 * @return id The identifier of the object.
	 */
	public Integer getRelatedId();
	
	/** Check if the object was deleted.
	 * @return boolean Return true if the object was deleted.
	 */
	public boolean isDeleted();
	

}
