/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: StructureContextFieldType.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * The class represents the type of {@link ContextField} that is of Structure type. 
 */
public class StructureContextFieldType extends ContextFieldType
{
	/**
	 * The structure definition.
	 */
	private ContextStructureDefinition structureDefinition;

	/**
	 * create new StructureContextFieldType Object.
	 * 
	 * @param structureDefinition The {@link ContextStructureDefinition} object.
	 * @param typeName The name of the type definition.
	 */
	public StructureContextFieldType(ContextStructureDefinition structureDefinition, String typeName)
	{
		// baruch
		// basicXITtype is null because it is not an xiType
		super(STRUCTURE_TYPE, typeName, false, null);
		this.structureDefinition = structureDefinition;
	}

	/**
	 * Returns The structure definition.
	 * @return ContextStructureDefinition
	 */
	public ContextStructureDefinition getStructureDefinition()
	{
		return structureDefinition;
	}
	
	/**
	 * Indicates if at least one of the fields contains default value.
	 * @return boolean
	 */
	public boolean isDefaultValuesPresents ()
	{
		return structureDefinition.isDefaultValuesPresents();
	}
	
}
