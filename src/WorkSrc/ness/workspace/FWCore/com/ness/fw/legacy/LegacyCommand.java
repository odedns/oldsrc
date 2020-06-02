/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyCommand.java,v 1.2 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.ness.fw.bl.BasicContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.ConnectionProvider;
import com.ness.fw.util.*;

/**
* The super class for all legacy command implementations.
*/
public abstract class LegacyCommand
{

	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "LEGACY COMMAND";
	
	/**
	 * The command name
	 */
	private String commandName;
	
	/**
	 * The legacy command execution type. (SP, OS400)
	 */
	private int commandType;
	
	/**
	 * The activity type of the actions performed in the legacy call.
	 * According to it the connection to the db will be transactional or read only. 
	 */
	private int activityType;
	
	/**
	 * The class name of the objects graph results.
	 */
	private String objectGraphClassName;

	/**
	 * The {@link CallArgument}s inside the legacy call. 
	 * @associates <{com.ness.fw.legacy.CallArgument}>
	 * @clientCardinality 1
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List callArguments = null;

	/**
	 * The {@link ArgumentStructureDefinition}s inside the legacy call. 
	 * @associates <{com.ness.fw.legacy.ArgumentStructureDefinition}>
	 * @clientCardinality 1..*
	 * @directed directed
	 * @supplierCardinality 0..*
	 */
	private List argumentStructures = null;

	/**
	 * Creates new LegacyCommand object.
	 * @param commandName The command name
	 * @param commandType The legacy command execution type. (SP, OS400)
	 * @param activityType The activity type of the actions performed in the legacy call.
	 * According to it the connection to the db will be transactional or read only.
	 * @param objectGraphClassName  The class name of the objects graph results.
	 * @param argumentStructures The {@link ArgumentStructureDefinition}s inside the legacy call.
	 * @param callArguments The {@link CallArgument}s inside the legacy call. 
	 */
	public LegacyCommand(String commandName, int commandType, int activityType, String objectGraphClassName, List argumentStructures, List callArguments)
	{
		this.commandName = commandName;
		this.commandType = commandType;
		this.activityType = activityType;
		this.objectGraphClassName = objectGraphClassName;
		this.argumentStructures = argumentStructures;
		this.callArguments = callArguments;
	}

	/**
	 * Returns the command name.
	 * @return String 
	 */
	public String getCommandName()
	{
		return commandName;
	}

	/**
	 * Returns the legacy command execution type (SP, OS400).
	 * @return int
	 */
	public int getCommandType()
	{
		return commandType;
	}

	/**
	 * Returns the class name of the objects graph results.
	 * @return String
	 */
	public String getObjectGraphClassName()
	{
		return objectGraphClassName;
	}

	/**
	 * Returns the count of the call arguments inside the command.
	 * @return int
	 */
	public int getCallArgumentsCount()
	{
		return callArguments == null ? 0 : callArguments.size();
	}

	/**
	 * Returns a specific CallArgument definition according to the index.  
	 * @param index The argument index in the list
	 * @return CallArgument
	 */
	public CallArgument getCallArgument(int index)
	{
		return (CallArgument)callArguments.get(index);
	}

	/**
	 * Returns the count of the argument structures inside the command.
	 * @return int
	 */
	public int getArgumentStructuresCount()
	{
		return argumentStructures == null ? 0 : argumentStructures.size();
	}

	/**
	 * Returns the activity type of the actions performed in the legacy call.
	 * According to it the connection to the db will be transactional or read only. 
	 * @return int
	 */
	public int getActivityType()
	{
		return activityType;
	}

	/**
	 * Returns a specific ArgumentStructureDefinition according to the index.  
	 * @param index The argument index in the list
	 * @return ArgumentStructureDefinition
	 */
	public ArgumentStructureDefinition getArgumentStructureDefinition(int index)
	{
		return (ArgumentStructureDefinition)argumentStructures.get(index);
	}

	/**
	 * Execute the legacy command and as result returns the objects graph built 
	 * according to the node structures & records that was defined in the command.  
	 * @param bpc The input container.
	 * @param connectionProvider The {@link ConnectionProvider} to use its connection.
	 * @return LegacyObjectGraph
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	protected abstract LegacyObjectGraph execute(BasicContainer bpc, ConnectionProvider connectionProvider) throws LegacyCommandException, BusinessLogicException;
	
	/**
	 * Executes a method without arguments in the given object. 
	 * @param methodName The name of the method to execute.
	 * @param objectToExecuteMethod The object to execute the method on.
	 * @return Object The method execution results.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	protected Object executeMethod (String methodName, Object objectToExecuteMethod) throws LegacyCommandException, BusinessLogicException
	{
		return executeMethod(methodName, objectToExecuteMethod, null, null);
	}

	/**
	 * Executes a method with arguments in the given object. 
	 * @param methodName The name of the method to execute.
	 * @param objectToExecuteMethod The object to execute the method on.
	 * @param argumentData The argument value. (could be null value).
	 * @param argumentClass The argument Class.
	 * @return Object The method execution results.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 */
	protected Object executeMethod (String methodName, Object objectToExecuteMethod, Object argumentData, Class argumentClass) throws LegacyCommandException, BusinessLogicException
	{
		Class[] argumentTypes; 
		Object[] argumentsData;
		if(argumentClass != null)
		{
			argumentTypes = new Class[] {argumentClass};
			argumentsData = new Object[] {argumentData};
		}
		else
		{
			argumentTypes = new Class[0];
			argumentsData = new Object[0];
		}
		
		// retrieving method
		Object results = null;
		Method method;
		try
		{
			method = objectToExecuteMethod.getClass().getMethod(methodName, argumentTypes);
		} 
		catch (Throwable ex)
		{
			throw new LegacyCommandException("Unable to execute legacy command [" +commandName + "]. Invalid method name [" + methodName + "] in class " + objectToExecuteMethod.getClass().getName(), ex);
		}

		// executing the method
		try
		{
			results = method.invoke(objectToExecuteMethod, argumentsData);
		}
		catch (InvocationTargetException ex)
		{
			if (ex.getTargetException() instanceof BusinessLogicException)
			{
				Logger.error(LOGGER_CONTEXT, ex.getTargetException());
				throw (BusinessLogicException)ex.getTargetException();
			}
			throw new LegacyCommandException("Unable to execute legacy command [" +commandName  + "]. Execution method name [" + methodName + "] in class " + objectToExecuteMethod.getClass().getName(), ex.getTargetException());
		} 
		catch (Throwable ex)
		{
			Logger.error(LOGGER_CONTEXT, ex);
			throw new LegacyCommandException("Unable to execute legacy command [" +commandName  + "]. Execution method name [" + methodName + "] in class " + objectToExecuteMethod.getClass().getName(), ex);
		}
		
		return results;
	}

	/**
	 * Instanciates a class according to his name.
	 * @param className The class name to instanciate. 
	 * @return Object
	 * @throws LegacyCommandException
	 */
	protected Object instanciateObject (String className) throws LegacyCommandException
	{
		try
		{
			return Class.forName(className).newInstance();
		}
		catch (Throwable e)
		{
			throw new LegacyCommandException("Unable to execute command " + getCommandName() + " Cannot instanciate class [" + className + "]");
		}
	}

	/**
	 * set the value into the value object attribute.
	 * @param value Teh value of the field
	 * @param fieldDef The field definition
	 * @param valueObject The value object to set the value.
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 * @throws ClassNotFoundException
	 */
	protected void setFieldValue (Object value, Field fieldDef, Object valueObject, Object recordId) throws LegacyCommandException, BusinessLogicException, ClassNotFoundException 
	{
//		Logger.debug(LOGGER_CONTEXT, "setValue [" + value + "] to field [" + fieldDef.getAttributeId() + "] in record [" + recordId + "]" );
		
		// handle convertion of data types between the column type & the attribute type
		if(value != null 
		   && fieldDef.getAttributeType() != null 
		   && fieldDef.getValueType() != null
		   && (! fieldDef.getValueType().equals(fieldDef.getAttributeType())))
		{
			try
			{
				value = TypeConverter.convertValueType(value, 
								fieldDef.getValueType().intValue(), 
								fieldDef.getAttributeType().intValue(),
								fieldDef.getFromMask());
			}
			catch (TypeConvertionException e)
			{
				throw new LegacyCommandException ("Error converting value [" + value + "] of field [" + fieldDef.getAttributeId() + "] in record [" + recordId + "] from type [" + fieldDef.getValueType() + "] to type [" + fieldDef.getAttributeType() + "]", e);
			}
		}
		
		Class valueClass = null;
		if(fieldDef.getAttributeType() != null)
		{
			valueClass = Class.forName(TypeConverter.getTypeClassName(fieldDef.getAttributeType().intValue()));
		}
		else if(value != null)
		{
			valueClass = value.getClass();
		}

		if(valueClass != null)
		{
			executeMethod(fieldDef.getSetter(), valueObject, value, valueClass);
		}
	}	

	/**
	 * Build the value objects from the value buffer data.  
	 * @param valueBuffer The buffer contains the data.
	 * @param bufferPosition The buffer position to start from.
	 * @param valueObjectsContainer The container to add the built value objects. 
	 * @param argStrDef The ArgumentStructureDefinition that contains the definition to analyze the buffer.  
	 * @param currentNodeStructure The current NodeStructure to read.
	 * @return int The next buffer position
	 * @throws LegacyCommandException
	 * @throws BusinessLogicException
	 * @throws ClassNotFoundException
	 */
	protected int readNodeStructuresValueObjects (String valueBuffer, int bufferPosition, Object valueObjectsContainer, ArgumentStructureDefinition argStrDef, NodeStructure currentNodeStructure) throws LegacyCommandException, BusinessLogicException, ClassNotFoundException
	{
		String currentStructureData;
		StructureField currentField;
		Object currentValueObject;
		String fieldValue;
		NodeStructure childNodeStructure;

		Structure currentStructure = currentNodeStructure.getStructure();
		// get the current structure id
		String currentStructureId = valueBuffer.substring(bufferPosition, bufferPosition + argStrDef.getStructureIdLength());
		
		int recordPosition;
		int bufferLength = valueBuffer.length();
		
		// loop over the records of the same type
		while (bufferPosition < (bufferLength - 1) 
				&& currentStructureId.equals(currentStructure.getId()))
		{ 
		
			// handle current record
			//-----------------------
			recordPosition = bufferPosition + argStrDef.getStructureIdLength();
		
			if(recordPosition + currentStructure.getLength() > bufferLength - 1)
			{
				throw new LegacyCommandException ("Problem analyzing buffer structure [" + argStrDef.getName() + "] last record is too short. \n current buffer position [" + bufferPosition + "].");
			}
			
			currentStructureData = valueBuffer.substring(recordPosition, recordPosition + currentStructure.getLength());
	
			// create a new value object for the db record
			currentValueObject = instanciateObject (currentStructure.getClassName());
	
			// iterate over the record fields & set their value into the value object		
			for (int fieldIndex = 0 ; fieldIndex < currentStructure.getFieldsCount() ; fieldIndex++)
			{
				currentField = currentStructure.getField(fieldIndex);
				fieldValue = currentStructureData.substring(currentField.getStartPosition(), currentField.getStartPosition() + currentField.getLength());
				setFieldValue(fieldValue, currentField, currentValueObject, currentStructureId);
			}
	
			// add the value object to the value objects container
			executeMethod(currentNodeStructure.getAdder(), valueObjectsContainer, currentValueObject, currentValueObject.getClass());	
	
			// change the buffer position to the next record
			bufferPosition = recordPosition + currentStructure.getLength();

			if((bufferPosition + argStrDef.getStructureIdLength()) < valueBuffer.length())
			{
				// get the next structure id
				currentStructureId = valueBuffer.substring(bufferPosition, bufferPosition + argStrDef.getStructureIdLength());
				
				if(currentNodeStructure.getNodeStructuresCount() > 0)
				{ 
					childNodeStructure = currentNodeStructure.getNodeStructure(currentStructureId);

					// if the node contains inner Structure Nodes, read their Value Objects of the structures related to 
					// the current value object according to the records order.
					while (childNodeStructure != null 
						   && bufferPosition < (valueBuffer.length() - 1))
					{
					
						bufferPosition = readNodeStructuresValueObjects(valueBuffer, bufferPosition, currentValueObject, argStrDef, childNodeStructure);
						
						// get the next structure id
						if((bufferPosition + argStrDef.getStructureIdLength()) < valueBuffer.length())
						{
							currentStructureId = valueBuffer.substring(bufferPosition, bufferPosition + argStrDef.getStructureIdLength());
							childNodeStructure = currentNodeStructure.getNodeStructure(currentStructureId);
						}
					}
				}
			}
		}
	
		return bufferPosition;			
	}

	/** Print the parsed SQL string to stdout.
	 */
	protected void printBufferToLog(String buffer, String bufferName)
	{
		Logger.debug(LOGGER_CONTEXT, "Start printing buffer [" + bufferName + "] in command [" +  getCommandName() + "] buffer length [" + buffer.length() + "]:\r\n" + "--------------------------------------------------------------------");
		int pos = 0;
		while(pos < buffer.length()){
			int posLen = pos + 68;
			if(posLen > buffer.length()){
				posLen = buffer.length();
			}
			String part = buffer.substring(pos, posLen);
			Logger.debug(LOGGER_CONTEXT, part);
			pos = posLen;
		}
		Logger.debug(LOGGER_CONTEXT, "--------------------------------------------------------------------\r\n");
	}

}