/*
 * Author: yifat har-nof
 * @version $Id: Identifiable.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import java.io.Serializable;

/**
 * Every business object that identified by one identity column with type int 
 * should implement this interface.
 */
public interface Identifiable extends Serializable, Cloneable
{
	/**
	 * Returns the identifier of the object.
	 * @return id The identifier of the object.
	 */
	public Integer getId();

}
