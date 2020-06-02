/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: Structure.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import java.util.List;

/**
 * Contains the structure definition of a string buffer.
 * According to the definition the framework analyzes the buffer 
 * and creates the LegacyObjectGraph.
 */
public class Structure
{
	/**
	 * The structure id
	 */
	private String id;
	
	/**
	 * The class name to instansiate for each record of that structure.
	 */
	private String className;
	
	/**
	 * The length of each record to read from the buffer.
	 */
	private int length;

	/**
	 * The {@link StructureField} definitions inside the structure.
	 * @associates <{com.ness.fw.legacy.StructureField}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List fields = null;

	/**
	 * Creates new Structure object
	 * @param id The structure id
	 * @param className The class name to instansiate for each record of that structure.
	 * @param length The length of each record to read from the buffer.
	 * @param fields The {@link StructureField} definitions inside the structure.
	 */
	public Structure(String id, String className, int length, List fields)
	{
		this.id = id;
		this.className = className;
		this.length = length;
		this.fields = fields;
	}

	/**
	 * Returns the class name to instansiate for each record of that structure.
	 * @return String 
	 */
	public String getClassName()
	{
		return className;
	}

	/**
	 * Returns the structure id
	 * @return String
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Returns the length of each record to read from the buffer.
	 * @return int
	 */
	public int getLength()
	{
		return length;
	}

	/**
	 * Returns a specific field definition according to the index.  
	 * @param index The field index in the list
	 * @return StructureField
	 */
	public StructureField getField(int index)
	{
		return (StructureField) fields.get(index);
	}

	/**
	 * Returns the count of the fields inside the structure.
	 * @return int
	 */
	public int getFieldsCount()
	{
		return fields == null ? 0 : fields.size();
	}

}
