/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: NodeStructure.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import java.util.Map;

/**
 * Contains the definition of a node in the objects graph, which should be constructed 
 * according to an output parameter result data and the Structure definition that 
 * describe it.  
 */
public class NodeStructure 
{

	/**
	 * The id of the {@link Structure} that describe the record to contruct.
	 */
	private String id;
	
	/**
	 * The name of the add method in the resulted objects graph.  
	 */
	private String adder;

	/**
	 * The Structure that describe the record.
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 */
	private Structure structure;

	/**
	 * The {@link NodeStructure} definitions inside the current node structure.
	 * @associates <{com.ness.fw.legacy.NodeStructure}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private Map nodeStructures = null;

	/**
	 * Creates new NodeStructure object.
	 * @param id The id of the {@link Structure} that describe the record to contruct.
	 * @param adder The name of the add method in the resulted objects graph. 
	 * @param structure  The Structure that describe the record.
	 * @param nodeStructures The {@link NodeStructure} definitions inside the current node structure.
	 */
	public NodeStructure(String id, String adder, Structure structure, Map nodeStructures)
	{
		this.id = id;
		this.adder = adder;
		this.structure = structure;
		this.nodeStructures = nodeStructures;
	}

	/**
	 * Returns the name of the add method in the resulted objects graph.
	 * @return String
	 */
	public String getAdder()
	{
		return adder;
	}

	/**
	 * Returns the id of the {@link Structure} that describe the record to contruct.
	 * @return String
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Returns the Structure that describe the record.
	 * @return Structure
	 */
	public Structure getStructure()
	{
		return structure;
	}
		
	/**
	 * Returns the child NodeStructure according to the structureId.  
	 * @param structureId The id of the {@link NodeStructure} to return.
	 * @return NodeStructure
	 */
	protected NodeStructure getNodeStructure(String structureId)
	{
		return (NodeStructure)nodeStructures.get(structureId);
	}
	
	/**
	 * returns the count of the {@link NodeStructure}s inside.
	 * @return int count
	 */
	public int getNodeStructuresCount()
	{
		return nodeStructures == null ? 0 : nodeStructures.size();
	}
	
}
