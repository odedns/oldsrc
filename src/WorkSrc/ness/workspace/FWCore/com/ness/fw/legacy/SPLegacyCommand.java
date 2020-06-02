/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: SPLegacyCommand.java,v 1.2 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;

import java.util.List;

import com.ness.fw.bl.BasicContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.persistence.ConnectionProvider;
import com.ness.fw.persistence.Page;
import com.ness.fw.persistence.StoredProcedureResults;
import com.ness.fw.persistence.StoredProcedureService;
import com.ness.fw.util.*;

/**
 * An implementation of a legacy command that calls to a stored procedure in the DB. 
 */
public class SPLegacyCommand extends LegacyCommand 
{
	/**
	 * The name of the connection manager.
	 */
	private String connectionManager;
	
	/**
	 * The sql statement to execute with the call command. 
	 */
	private String sqlStatement;

	/**
	 * The {@link NodeRecord}s inside the legacy call. 
	 * @associates <{com.ness.fw.legacy.NodeRecord}>
	 * @clientCardinality 1..*
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List nodeRecords = null;
	
	/**
	 * Creates new LegacyCommand object.
	 * @param commandName The command name
	 * @param activityType The activity type of the actions performed in the legacy call.
	 * According to it the connection to the db will be transactional or read only.
	 * @param objectGraphClassName  The class name of the objects graph results.
	 * @param connectionManager The name of the connection manager.
	 * @param sqlStatement The sql statement to execute with the call command. 
	 * @param argumentStructures The {@link ArgumentStructureDefinition}s inside the legacy call.
	 * @param callArguments The {@link CallArgument}s inside the legacy call. 
	 * @param nodeRecords The {@link NodeRecord}s inside the legacy call. 
	 */ 
	public SPLegacyCommand(
		String commandName,
		int activityType,
		String objectGraphClassName,
		String connectionManager,
		String sqlStatement,
		List argumentStructures,
		List callArguments,
		List nodeRecords)
	{
		super(commandName, LegacyConstants.COMMAND_TYPE_SP, activityType, objectGraphClassName, argumentStructures, callArguments);
		this.connectionManager = connectionManager;
		this.sqlStatement = sqlStatement;
		this.nodeRecords = nodeRecords;
	}
	
	/**
	 * Retruns the name of the connection manager.
	 * @return String
	 */
	protected String getConnectionManager()
	{
		return connectionManager;
	}

	/**
	 * Returns the sql statement to execute with the call command. 
	 * @return String
	 */
	protected String getSqlStatement()
	{
		return sqlStatement;
	}

	/**
	 * Returns the count of the node records inside the command.
	 * @return int
	 */
	public int getNodeRecordsCount()
	{
		return nodeRecords == null ? 0 : nodeRecords.size();
	}

	/**
	 * Returns the node records inside the command.
	 * @return List
	 */
	protected List getNodeRecords()
	{
		return nodeRecords;
	}
	
	/**
	 * Execute the legacy command and as result returns the objects graph built 
	 * according to the node structures & records that was defined in the command.  
	 * @param bpc The input container.
	 * @param connectionProvider The {@link ConnectionProvider} to use its connection.
	 */
	protected LegacyObjectGraph execute(BasicContainer bpc, ConnectionProvider connectionProvider) throws LegacyCommandException, BusinessLogicException
	{
		LegacyObjectGraph results = null;
		
		try
		{
			// create sp service
			StoredProcedureService spService = new StoredProcedureService(getSqlStatement(), getConnectionManager());
			// set the sp arguments into the sp service
			setServiceArguments(spService, bpc);
			// execute the sp
			StoredProcedureResults spResults = spService.execute(connectionProvider);
			// analyze the sp results
			results = createLegacyObjectGraph(spResults, spService, bpc);
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (LegacyCommandException e)
		{
			throw e;
		} 
		catch (Throwable e)
		{
			throw new LegacyCommandException("Unable to execute legacy command [" +getCommandName()  + "].", e);
		} 
		
		return results;
	}	
	
	/**
	 * Sets the argument to the {@link StoredProcedureService}.
	 * @param spService The StoredProcedureService.
	 * @param bpc The container with the input values.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	private void setServiceArguments (StoredProcedureService spService, BasicContainer bpc) throws LegacyCommandException, BusinessLogicException
	{
		CallArgument currentArgument;

		Object argumentValue;
		for (int i = 0 ; i < getCallArgumentsCount() ; i++)
		{
			currentArgument = getCallArgument(i);
			
			// get argumnet value 
			if (currentArgument.getInputType() != CallArgument.INPUT_TYPE_AS_OUTPUT)
			{
				argumentValue = executeMethod(currentArgument.getInputContainerGetter(), bpc);
			}
			else
			{
				argumentValue = null;
			}
			
			// add the argument to the sp service according to the input type
			if (currentArgument.getInputType() == CallArgument.INPUT_TYPE_AS_INPUT)
			{
				spService.addInputParameter(argumentValue);
			}			
			else if (currentArgument.getInputType() == CallArgument.INPUT_TYPE_AS_INPUT_OUTPUT)
			{
				spService.addInputOutputParameter(argumentValue);
			}			
			else if (currentArgument.getInputType() == CallArgument.INPUT_TYPE_AS_OUTPUT)
			{
				spService.addOutputParameter(currentArgument.getSQLType());
			}			
			
		}
	}

	/**
	 * Creates the LegacyObjectGraph according to the node structures & records 
	 * definition with the stored procedure results.
	 * @param spResults The StoredProcedureResults.
	 * @param spService The StoredProcedureService. 
	 * @param bpc The input container.
	 * @return LegacyObjectGraph
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	protected LegacyObjectGraph createLegacyObjectGraph (StoredProcedureResults spResults, StoredProcedureService spService, BasicContainer bpc) throws LegacyCommandException, BusinessLogicException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		LegacyObjectGraph results = (LegacyObjectGraph)instanciateObject(getObjectGraphClassName());
		
		// Sets the value of the output arguments that was defined result as simple.
		readSimpleOutputArguments(spResults, results);
		
		// loop on the main node records inside the object graph.
		if(getNodeRecordsCount() > 0)
		{
			readNodeRecordsValueObjects(spResults, results, null, getNodeRecords());
		}

		// loop on the main node records inside the object graph.
		if(getArgumentStructuresCount() > 0)
		{
			readNodeStructuresValueObjects(spResults, results);
		}
		
		return results;
	}


	/**
	 * Sets the value of the output arguments that was defined result as simple. 
	 * set the value as returned from the sp. 
	 * @param spResults
	 * @param results
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	protected void readSimpleOutputArguments (StoredProcedureResults spResults, LegacyObjectGraph results) throws LegacyCommandException, BusinessLogicException
	{
		CallArgument argument;
		Object outputValue;
		for (int i = 0 ; i < getCallArgumentsCount() ; i++)
		{
			argument = getCallArgument(i);
			if(argument.isSimpleOutputArgument())
			{
				outputValue = spResults.getOutputValue(i);
				Class valueClass = TypeConverter.getTypeClass(argument.getAttributeType());
				if(valueClass == null && outputValue != null)
				{
					valueClass = outputValue.getClass();
				}
				executeMethod(argument.getSimpleResultSetter(), results, outputValue, valueClass);
			}
		}
	}

	/**
	 * Read the value objects according to the node record definitions from the 
	 * ResultSets returned from the SP. 
	 * @param spResults
	 * @param valueObjectsContainer
	 * @param parentId
	 * @param nodeRecords
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	private void readNodeRecordsValueObjects (StoredProcedureResults spResults, Object valueObjectsContainer, Object parentId, List nodeRecords) throws LegacyCommandException, BusinessLogicException, ClassNotFoundException 
	{
		NodeRecord currentNodeRecord = null;
		Page currentRecordPage = null;
		Object currentValueObject = null;
		Record currentRecord = null;
		RecordField currentField = null;
		Object currentRecordId = null;
		Object currentParentId = null;
		
		for (int nodeIndex = 0 ; nodeIndex < nodeRecords.size() ; nodeIndex++)
		{
			currentNodeRecord = (NodeRecord)nodeRecords.get(nodeIndex);
			currentRecord = currentNodeRecord.getRecord();
			
			// get DB records page of the node record according to the ResultSet number.
			currentRecordPage = spResults.getResultPage(currentNodeRecord.getResultSetNumber() - 1);

			// iterate over the db records of the ResultSet 				
			while (currentRecordPage != null && currentRecordPage.next())
			{
				// get record unique id
				currentRecordId = getRecordFieldValue(currentRecord.getUniqueIdFieldIndex(), currentRecord, currentRecordPage);
				
				//get record parent id
				currentParentId = getRecordFieldValue(currentNodeRecord.getParentIdFieldIndex(), currentRecord, currentRecordPage);

				// check if the record is a child record of the given parentId 
				if(parentId != null)
				{ 
					if(! parentId.equals(currentParentId))
					{
						currentRecordPage.previous();
						break;
					}
				}

				// create a new value object for the db record
				currentValueObject = instanciateObject (currentRecord.getClassName());

				// iterate over the record fields & set their value into the value object		
				for (int fieldIndex = 0 ; fieldIndex < currentRecord.getFieldsCount() ; fieldIndex++)
				{
					currentField = currentRecord.getField(fieldIndex);
					Object value = currentRecordPage.getObject(currentField.getColumnId());
					setFieldValue(value, currentField, currentValueObject, currentRecordId);
				}

				// add the value object to the value objects container
				executeMethod(currentNodeRecord.getAdder(), valueObjectsContainer, currentValueObject, currentValueObject.getClass());	

				// if the node contains inner Record Nodes, read their Value Objects of the records related to 
				// the current value object according to the parent id.
				if(currentNodeRecord.getNodeRecordsCount() > 0)
				{
					readNodeRecordsValueObjects(spResults, currentValueObject, currentRecordId, currentNodeRecord.getNodeRecords());
				}
				
			}
		}
	}

	/**
	 * Returns the value if a  record field from the resulted page.
	 * @param recordPage
	 * @param fieldIndex
	 * @param record
	 * @return Object record field value
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	private Object getRecordFieldValue (int fieldIndex, Record record, Page recordPage) throws LegacyCommandException, BusinessLogicException 
	{
		Object fieldValue = null;
		RecordField recordField = record.getField(fieldIndex); 
		if(recordField != null)
		{
			fieldValue = recordPage.getObject(recordField.getColumnId()); 
		}
		return fieldValue;
	}

	/**
	 * Read the value objects according to the node structure definitions from the 
	 * output arguments returned from the SP. 
	 * @param spResults
	 * @param valueObjectsContainer
	 * @param parentId
	 * @param nodeRecords
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	private void readNodeStructuresValueObjects (StoredProcedureResults spResults, Object valueObjectsContainer) throws LegacyCommandException, BusinessLogicException, ClassNotFoundException 
	{
		// TODO handle bidi string type
		ArgumentStructureDefinition currentArgDefinition;
		CallArgument currentCallArgument;
		Object outputValue;
		int bufferPosition = 0;
		String buffer;
		for (int index = 0 ; index < getArgumentStructuresCount() ; index++)
		{
			currentArgDefinition = getArgumentStructureDefinition(index);
			currentCallArgument = getCallArgument(currentArgDefinition.getArgumentIndex());
			if(currentCallArgument.isOutputParameter())
			{
				outputValue = spResults.getOutputValue(currentArgDefinition.getArgumentIndex());
				if(outputValue != null)
				{
					buffer = outputValue.toString();
					printBufferToLog(buffer, currentArgDefinition.getName());
					bufferPosition = readNodeStructuresValueObjects(buffer, 0, valueObjectsContainer, currentArgDefinition, currentArgDefinition.getNodeStructure());
					if(bufferPosition < buffer.length())
					{
						throw new LegacyCommandException("Problem analyzing buffer structure [" + currentArgDefinition.getName() + "] \n current buffer position [" + bufferPosition + "] buffer length [" + buffer.length() + "] \n buffer data [" + buffer + "].");
					}
				}
			}
		}
	}

}
