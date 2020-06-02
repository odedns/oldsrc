/*
 * Created on: 31/08/2004
 * Author: yifat har-nof
 * @version $Id: ValueObjectListFactory.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.bl;

import java.util.List;

/**
 * A factory for creation of ValueObjectList implementation.
 */
public class ValueObjectListFactory
{

	/**
	 * Creates new ValueObjectList contains the given {@link ValueObject}s. 
	 * @param valueObjects A List of {@link ValueObject}s
	 * @return ValueObjectList
	 */
	public static ValueObjectList createValueObjectList(List valueObjects)
	{
		return new ValueObjectListImpl(valueObjects);
	}

}
