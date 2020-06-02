/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: ArgumentStructureDefinition.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

/**
 * Contains the name of the output argument to analayze his value.
 * According to the definition the framework analyzes the buffer 
 * and creates the LegacyObjectGraph.
 */
public class ArgumentStructureDefinition 
{
	
	/**
	 * The name of the argument to take the value.
	 */
	private String name;
	
	/**
	 * The main {@link NodeStructure} definition related to the output argument.
	 * @associates <{com.ness.fw.legacy.NodeStructure}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private NodeStructure nodeStructure = null;
	
	/**
	 * The index of the argument.
	 */
	private int argumentIndex;

	/**
	 * The length of each structureId in the buffer. 
	 */
	private int structureIdLength;
	
	/**
	 * Creates new ArgumentStructureDefinition object.
	 * @param name The name of the argument to take the value.
	 * @param nodeStructure The main NodeStructure definition related to the output argument.
	 * @param structureIdLength The length of each structureId in the buffer.
	 * @param argumentIndex The index of the argument.
	 */
	public ArgumentStructureDefinition(String name, NodeStructure nodeStructure, int structureIdLength, int argumentIndex)
	{
		this.name = name;
		this.nodeStructure = nodeStructure;
		this.structureIdLength = structureIdLength;
		this.argumentIndex = argumentIndex;
	}

	/**
	 * Returns the name of the argument to take the value.
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the index of the argument.
	 * @return int
	 */
	public int getArgumentIndex()
	{
		return argumentIndex;
	}

	/**
	 * Returns the main NodeStructure definition related to the output argument.
	 * @return NodeStructure
	 */
	public NodeStructure getNodeStructure()
	{
		return nodeStructure;
	}

	/**
	 * Returns the length of each structureId in the buffer.
	 * @return int
	 */
	public int getStructureIdLength()
	{
		return structureIdLength;
	}

}
