/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: NodeRecord.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

import java.util.List;

/**
 * Contains the definition of a node in the objects graph, which should be constructed 
 * according to a ResultSet (page) that was returned from the Stored procedure call.  
 */
public class NodeRecord
{
	
	/**
	 * The id of the {@link Record} that describe the record to contruct.
	 */
	private String id;
	
	/**
	 * The number of the ResultSet to get the data. 
	 * (from the ResultSets that was returned from the SP call).
	 */
	private int resultSetNumber;
	
	/**
	 * The name of the add method in the resulted objects graph.  
	 */
	private String adder;
	
	/**
	 * The index of the RecordField definition in the record taht represent the parent
	 * id, to attach the current record to him.
	 */
	private int parentIdFieldIndex;
	
	/**
	 * The Record object that describe the record structure.
	 * @clientCardinality 1..*
	 * @supplierCardinality 1
	 */
	private Record record;

	/**
	 * The {@link NodeRecord} definitions inside the current node record.
	 * @associates <{com.ness.fw.legacy.NodeRecord}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List nodeRecords = null;

	/**
	 * 
	 * @param id The id of the {@link Record} that describe the record to contruct.
	 * @param resultSetNumber The number of the ResultSet to get the data. 
	 * @param adder The name of the add method in the resulted objects graph.
	 * @param parentIdColumn The index of the RecordField definition in the record taht represent the parent
	 * id, to attach the current record to him.
	 * @param record The Record object that describe the record structure.
	 * @param nodeRecords The {@link NodeRecord} definitions inside the current node record.
	 */
	public NodeRecord(String id, int resultSetNumber, String adder, int parentIdFieldIndex, Record record, List nodeRecords)
	{
		this.id = id;
		this.resultSetNumber = resultSetNumber; 
		this.adder = adder; 
		this.parentIdFieldIndex = parentIdFieldIndex; 
		this.record = record; 
		this.nodeRecords = nodeRecords; 
	}


	/**
	 * The name of the add method in the resulted objects graph.
	 * @return
	 */
	public String getAdder()
	{
		return adder;
	}

	/**
	 * The id of the {@link Record} that describe the record to contruct.
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * The index of the RecordField definition in the record taht represent the parent
	 * id, to attach the current record to him.
	 * @return
	 */
	public int getParentIdFieldIndex()
	{
		return parentIdFieldIndex;
	}

	/**
	 * The Record object that describe the record structure.
	 * @return
	 */
	public Record getRecord()
	{
		return record;
	}

	/**
	 * The number of the ResultSet to get the data. 
	 * @return
	 */
	public int getResultSetNumber()
	{
		return resultSetNumber;
	}

	/**
	 * The {@link NodeRecord} definitions inside the current node record.
	 * @return
	 */
	public java.util.List getNodeRecords()
	{
		return nodeRecords;
	}

	/**
	 * returns the count of the {@link NodeRecord}s inside.
	 * @return int count
	 */
	public int getNodeRecordsCount()
	{
		return nodeRecords == null ? 0 : nodeRecords.size();
	}

}
