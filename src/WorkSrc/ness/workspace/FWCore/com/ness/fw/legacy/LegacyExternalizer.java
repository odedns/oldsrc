/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyExternalizer.java,v 1.1 2005/02/21 15:07:17 baruch Exp $
 */
package com.ness.fw.legacy;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ness.fw.common.externalization.DOMList;
import com.ness.fw.common.externalization.DOMRepository;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.externalization.XMLUtil;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.persistence.StoredProcedureService;
import com.ness.fw.util.*;

/**
 * The externalizer of the legacy commands layer.  
 */
public class LegacyExternalizer
{
	/**
	 * The logger context
	 */
	private static final String LOGGER_CONTEXT = "LEGACY EXTERNALIZATION";

	/**
	 * The setter method prefix
	 */
	private static final String SETTER_PREFIX = "set";

	/**
	 * The getter method prefix
	 */
	private static final String GETTER_PREFIX = "get";

	/**
	 * The adder method prefix
	 */
	private static final String ADDER_PREFIX = "add";
	
	/**
	 * The records map
	 */
	private static Map records = new HashMap();
	
	/**
	 * The {@link Structure}s map
	 */
	private static Map structures = new HashMap();
	
	/**
	 * The {@link LegacyCommand}s map
	 */
	private static Map commands = new HashMap();

	/**
	 * Initialize the command definitions from the xml files. 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoots The paths of the business process configuration xml file.
	 * @throws LegacyCommandsExternalizationException
	 */
	public static void initialize (ArrayList confFilesRoots) throws LegacyCommandsExternalizationException
	{ 
		//creating DOM repository
		DOMRepository domRepository = new DOMRepository();
		
		try
		{
			domRepository.initialize(confFilesRoots);
		} 
		catch (ExternalizationException e)
		{
			throw new LegacyCommandsExternalizationException("Unable to initialize Legacy Commands DOM Repository", e);
		}

		synchronized (commands)
		{
			commands.clear();
			records.clear();
			structures.clear();

			Logger.debug(LOGGER_CONTEXT, "loading legacy records");
			//read records
			DOMList recDomList = domRepository.getDOMList(ExternalizerConstants.RECORD_TAG_NAME);
			if (recDomList != null)
			{
				for (int i = 0; i < recDomList.getDocumentCount(); i++)
				{
					Document doc = recDomList.getDocument(i);
	
					processRecordsDOM(doc);
				}
			}

			Logger.debug(LOGGER_CONTEXT, "loading legacy structures");
			//read structures
			DOMList structDomList = domRepository.getDOMList(ExternalizerConstants.STRUCTURE_TAG_NAME);
			if (structDomList != null)
			{
				for (int i = 0; i < structDomList.getDocumentCount(); i++)
				{
					Document doc = structDomList.getDocument(i);
	
					processStructuresDOM(doc);
				}
			}
	
			Logger.debug(LOGGER_CONTEXT, "loading legacy commands");
			//read commands
			DOMList commandDomList = domRepository.getDOMList(ExternalizerConstants.COMMAND_TAG_NAME);
			if (commandDomList != null)
			{
				for (int i = 0; i < commandDomList.getDocumentCount(); i++)
				{
					Document doc = commandDomList.getDocument(i);
	
					processCommandsDOM(doc);
				}
			}
		}
	}

	/**
	 * Initialize the command definitions from the xml files. 
	 * <b>
	 * Must be called in the scope of lock in the ControllerServlet.
	 * <b>
	 *
	 * @param confFilesRoot The path of the business process configuration xml file.
	 * @throws LegacyCommandsExternalizationException
	 */
	public static void initialize (String confFilesRoot) throws LegacyCommandsExternalizationException
	{
		ArrayList roots = new ArrayList();
		roots.add(confFilesRoot);
		initialize(roots);
	}
	
	/**
	 * Parsing all records in the specific Document
	 * @param doc
	 */
	private static void processRecordsDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.RECORD_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element recordElement = (Element) nodes.item(i);

			try
			{
				readRecord(recordElement);
			} catch (Throwable ex)
			{
				ex.printStackTrace();
				Logger.error(LOGGER_CONTEXT, "Unable to initialize legacy Record. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Parsing all structures in the specific Document
	 * @param doc
	 */
	private static void processStructuresDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.STRUCTURE_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element structureElement = (Element) nodes.item(i);

			try
			{
				readStructure(structureElement);
			} catch (Throwable ex)
			{
				ex.printStackTrace();
				Logger.error(LOGGER_CONTEXT, "Unable to initialize legacy structure. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Parsing all commands in the specific Document
	 * @param doc
	 */
	private static void processCommandsDOM (Document doc)
	{
		Element rootElement = doc.getDocumentElement();

		NodeList nodes = XMLUtil.getElementsByTagName(rootElement, ExternalizerConstants.COMMAND_TAG_NAME);

		for (int i = 0; i < nodes.getLength(); i++)
		{
			Element commandElement = (Element) nodes.item(i);

			try
			{
				readCommand(commandElement);
			} catch (Throwable ex)
			{
				ex.printStackTrace();
				Logger.error(LOGGER_CONTEXT, "Unable to initialize legacy command. Continue with other.");
				Logger.error(LOGGER_CONTEXT, ex);
			}
		}
	}

	/**
	 * Parse specific command element.
	 * @param commandElement
	 */
	private static void readCommand(Element commandElement) throws LegacyCommandsExternalizationException
	{
		String commandName = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_NAME);
		if(commands.containsKey(commandName))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize legacy command [" + commandName + "]. A legacy command with that name is already defined.");
		}
		else
		{
			String type = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_TYPE);
			Logger.debug(LOGGER_CONTEXT, "loading legacy command [" + commandName + "] of type [" + type + "]");
			
			LegacyCommand command = null;
			
			if(type.equals(ExternalizerConstants.COMMAND_TYPE_SP))
			{
				command = readSPCommand(commandElement, commandName);
			}
			else if(type.equals(ExternalizerConstants.COMMAND_TYPE_OS400))
			{
				command = readOS400Command(commandElement, commandName);
			}
			else
			{
				throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command. Unsupported command type [" + type + "]");
			}
			
			commands.put(commandName, command);
		}
	}

	/**
	 * Read a <code>LegacyCommand</code> object of type OS400.
	 * @param commandElement The command element
	 * @param commandName The command name.
	 * @return LegacyCommand
	 * @throws LegacyCommandsExternalizationException
	 */
	private static LegacyCommand readOS400Command(Element commandElement, String commandName) throws LegacyCommandsExternalizationException
	{
		OS400LegacyCommand command = null;
		String libraryName  = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_LIBRARY_NAME);
		String programName = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_PROGRAM_NAME);
		int activityType = getActivityType(commandName, XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_ACTIVITY_TYPE));
		String objectGraphClassName = null;
		List callArguments = null;
		List argumentStructures = null;
		
		if(libraryName == null || programName == null)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "] of type " + ExternalizerConstants.COMMAND_TYPE_OS400 + ". One of the attributes [" + ExternalizerConstants.COMMAND_ATTR_LIBRARY_NAME + ","  + ExternalizerConstants.COMMAND_ATTR_PROGRAM_NAME + "] is missing."); 
		}
		
		callArguments = readCallArguments(commandElement, commandName);
		
		// get LegacyObjectGraph definition. Only 0 / 1 occurrences are allowed.
		Element objGraphElement = getObjectGraphElement(commandElement, commandName);
		
		if(objGraphElement != null)
		{
			objectGraphClassName  = XMLUtil.getAttribute(objGraphElement, ExternalizerConstants.OBJ_GRAPH_ATTR_CLASS);
			argumentStructures = readArgumentStructureDefinitions(objGraphElement, commandName, callArguments);
		}

		command = new OS400LegacyCommand(commandName, activityType, objectGraphClassName, libraryName, programName, argumentStructures, callArguments);
		
		return command;
	}

	/**
	 * Read a <code>LegacyCommand</code> object of type SP.
	 * @param commandElement The command element
	 * @param commandName The command name.
	 * @return LegacyCommand
	 * @throws LegacyCommandsExternalizationException
	 */
	private static LegacyCommand readSPCommand(Element commandElement, String commandName) throws LegacyCommandsExternalizationException
	{
		SPLegacyCommand command = null;
		
		String sqlStatement = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_SQL_STATEMENT);
		String connectionManagerName  = XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_CONNECTION_MANAGER);
		int activityType = getActivityType(commandName, XMLUtil.getAttribute(commandElement, ExternalizerConstants.COMMAND_ATTR_ACTIVITY_TYPE));
		String objectGraphClassName = null;
		List nodeRecords = null;
		List argumentStructures = null;
		List callArguments = null;
		
		if(sqlStatement == null)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "] of type " + ExternalizerConstants.COMMAND_TYPE_SP + ". The attribute [" + ExternalizerConstants.COMMAND_ATTR_SQL_STATEMENT + "] is missing."); 
		}
		
		callArguments = readCallArguments(commandElement, commandName);

		
		// get LegacyObjectGraph definition. Only 0 / 1 occurrences are allowed.
		Element objGraphElement = getObjectGraphElement(commandElement, commandName);
		
		if(objGraphElement != null)
		{
			objectGraphClassName  = XMLUtil.getAttribute(objGraphElement, ExternalizerConstants.OBJ_GRAPH_ATTR_CLASS);
			argumentStructures = readArgumentStructureDefinitions(objGraphElement, commandName, callArguments);
			nodeRecords = readNodeRecords(objGraphElement, commandName);
		}

		command = new SPLegacyCommand(commandName, activityType, objectGraphClassName, connectionManagerName, sqlStatement, argumentStructures, callArguments, nodeRecords);
		
		return command;
	}

	/**
	 * Returns the ObjectGraph Element
	 * @param commandElement The command element
	 * @param commandName The command name.
	 * @return Element
	 * @throws LegacyCommandsExternalizationException
	 */
	private static Element getObjectGraphElement (Element commandElement, String commandName) throws LegacyCommandsExternalizationException
	{
		Element objGraphElement = null;
		
		// get LegacyObjectGraph definition. Only 0 / 1 occurrences are allowed.
		NodeList nodeList = XMLUtil.getElementsByTagName(commandElement, ExternalizerConstants.OBJ_GRAPH_TAG_NAME);
		if(nodeList.getLength() > 1)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. The element [" + ExternalizerConstants.OBJ_GRAPH_TAG_NAME + "] should be declared only once for each command.");
		}
		else if(nodeList.getLength() == 1)
		{ 
			objGraphElement = (Element) nodeList.item(0);
		}
		
		return objGraphElement;
	}

	/**
	 * Returns a list with the {@link NodeRecord}s inside the containerElement. 
	 * @param containerElement
	 * @param commandName The command name.
	 * @return List
	 * @throws LegacyCommandsExternalizationException
	 */
	private static List readNodeRecords (Element containerElement, String commandName) throws LegacyCommandsExternalizationException
	{
		List nodeRecords = null;
		
		//list of NodeRecord nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(containerElement, ExternalizerConstants.NODE_REC_TAG_NAME);

		if(nodeList.getLength() > 0)
		{
			nodeRecords = new ArrayList();
			
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				//NodeRecord element
				Element nodeRecordElement = (Element) nodeList.item(i);
	
				String recordId = XMLUtil.getAttribute(nodeRecordElement, ExternalizerConstants.NODE_REC_ATTR_ID);
	
				Logger.debug(LOGGER_CONTEXT, "loading legacy " + ExternalizerConstants.NODE_REC_TAG_NAME + " [" + recordId + "]");
	
				int resultSetNumber = Integer.parseInt(XMLUtil.getAttribute(nodeRecordElement, ExternalizerConstants.NODE_REC_ATTR_RS_NUM));
				String parentIdAttribute = XMLUtil.getAttribute(nodeRecordElement, ExternalizerConstants.NODE_REC_ATTR_PARENT_ATTR);
				String adder = XMLUtil.getAttribute(nodeRecordElement, ExternalizerConstants.NODE_REC_ATTR_ADDER);
	
				//if necessary, calc adder method name 
				if(adder == null)
				{
					adder = getMethodName(ADDER_PREFIX, recordId);
				}
	
				// get related Record
				Record record = getRecord(recordId, commandName);

				int parentIdFieldIndex = record.getFieldIndex(parentIdAttribute);
	
				// get contained Node Records								
				List childNodeRecords = readNodeRecords(nodeRecordElement, commandName);
					
				// create NodeRecord 	
				NodeRecord nodeRecord = new NodeRecord(recordId, resultSetNumber, adder, parentIdFieldIndex, record, childNodeRecords);
				
				// add it to the list		
				nodeRecords.add(nodeRecord);
				
			}
		}	
		return nodeRecords;	
	}

	/**
	 * Returns a list with the {@link CallArgument}s inside the ObjectGraph Element.
	 * @param objGraphElement 
	 * @param commandName The command name.
	 * @return List
	 * @throws LegacyCommandsExternalizationException
	 */
	private static List readCallArguments (Element objGraphElement, String commandName) throws LegacyCommandsExternalizationException
	{
		List callArguments = null;
		
		//list of CallArgument nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(objGraphElement, ExternalizerConstants.CALL_ARG_TAG_NAME);

		if(nodeList.getLength() > 0)
		{
			callArguments = new ArrayList();
			
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				//CallArgument element
				Element callArgumentElement = (Element) nodeList.item(i);
	
				String argumentName = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_NAME);
				String inputType = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_INPUT_TYPE);
				String type = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_TYPE);
				String inputContainerGetter = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_GETTER);
				String simpleResultSetter = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_SIMPLE_SETTER);
				String resultAsStr = XMLUtil.getAttribute(callArgumentElement, ExternalizerConstants.CALL_ARG_ATTR_RESULT_AS);
				Logger.debug(LOGGER_CONTEXT, "loading legacy " + ExternalizerConstants.CALL_ARG_TAG_NAME + " [" + argumentName + "]");
				
				int resultAs = CallArgument.RESULT_AS_SIMPLE;
				if(resultAsStr != null)
				{
					if(resultAsStr.equals(ExternalizerConstants.CALL_ARG_RESULT_AS_STRUCTURE))
					{
						resultAs = CallArgument.RESULT_AS_STRUCTURE;
					}
					else if(! resultAsStr.equals(ExternalizerConstants.CALL_ARG_RESULT_AS_SIMPLE))
					{
						throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Unsupported argument " + ExternalizerConstants.CALL_ARG_ATTR_RESULT_AS + " value [" + resultAsStr + "]"); 
					}
				}

				//if necessary, calc adder method name 
				if(inputContainerGetter == null)
				{
					inputContainerGetter = getMethodName(GETTER_PREFIX, argumentName);
				}

				//if necessary, calc setter method name 
				if(simpleResultSetter == null)
				{
					simpleResultSetter = getMethodName(SETTER_PREFIX, argumentName);
				}
	
				// create ArgumentStructureDefinition 			
				CallArgument callArgument = new CallArgument(
						argumentName, 
						getSQLType(commandName, type), 
						getArgumntInputType(commandName, inputType), 
						inputContainerGetter, 
						resultAs, 
						simpleResultSetter,
						getTypeId(ExternalizerConstants.CALL_ARG_TAG_NAME, commandName, type).intValue());
				
				// add it to the argument list
				callArguments.add(callArgument);
			}
		}		
		return callArguments;		
	}

	/**
	 * Returns a list with the {@link ArgumentStructureDefinition}s inside the ObjectGraph Element.
	 * @param objGraphElement
	 * @param commandName The command name.
	 * @param callArguments A list contains the CallArguments inside the ObjectGraph Element.
	 * @return List
	 * @throws LegacyCommandsExternalizationException
	 */
	private static List readArgumentStructureDefinitions (Element objGraphElement, String commandName, List callArguments) throws LegacyCommandsExternalizationException
	{
		List argumentStructures = null;
		
		//list of ArgumentStructureDefinition nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(objGraphElement, ExternalizerConstants.ARGUMENT_STRUCT_TAG_NAME);

		if(nodeList.getLength() > 0)
		{
			argumentStructures = new ArrayList();
			
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				//ArgumentStructureDefinition element
				Element argumentStructureElement = (Element) nodeList.item(i);
	
				String argumentName = XMLUtil.getAttribute(argumentStructureElement, ExternalizerConstants.ARGUMENT_STRUCT_ATTR_NAME);
	
				Logger.debug(LOGGER_CONTEXT, "loading legacy " + ExternalizerConstants.ARGUMENT_STRUCT_TAG_NAME + " [" + argumentName + "]");
	
				int argumentIndex = getArgumentIndex(argumentName, callArguments, commandName);
				int structureIdLength = Integer.parseInt(XMLUtil.getAttribute(argumentStructureElement, ExternalizerConstants.ARGUMENT_STRUCT_ATTR_STR_ID_LENGTH));
				
				NodeStructure nodeStructure = null;
				NodeList nodeStructureList = XMLUtil.getElementsByTagName(argumentStructureElement, ExternalizerConstants.NODE_STRUCT_TAG_NAME);
				if(nodeStructureList.getLength() == 1)
				{
					Element nodeStructureElement = (Element) nodeStructureList.item(0);
					nodeStructure = readNodeStructure(nodeStructureElement, commandName);
				}
				else
				{
					throw new LegacyCommandsExternalizationException("Unable to load legacy command [" + commandName + "]. " + ExternalizerConstants.ARGUMENT_STRUCT_TAG_NAME + "[" + argumentName + "] must contain one " + ExternalizerConstants.NODE_STRUCT_TAG_NAME + "."); 
				}
				
				// create ArgumentStructureDefinition 			
				ArgumentStructureDefinition argumentStructureDefinition = new ArgumentStructureDefinition(argumentName, nodeStructure, structureIdLength, argumentIndex);
				
				// add it to the argument list
				argumentStructures.add(argumentStructureDefinition);
			}
		}		
		return argumentStructures;		
	}
	
	/**
	 * Returns the argumnet index according to the argument name.
	 * @param argumentName The name of the argument
	 * @param callArguments A list contains the CallArguments inside the ObjectGraph Element.
	 * @param commandName The command name.
	 * @return int argument index
	 * @throws LegacyCommandsExternalizationException
	 */
	private static int getArgumentIndex(String argumentName, List callArguments, String commandName) throws LegacyCommandsExternalizationException
	{
		CallArgument currentCallArgument;
		for (int index = 0 ; index < callArguments.size() ; index++)
		{
			currentCallArgument = (CallArgument)callArguments.get(index);
			if(currentCallArgument.getName().equals(argumentName))
			{
				return index;
			}
		}
		throw new LegacyCommandsExternalizationException("Unable to load legacy command [" + commandName + "]. Cannot find " + ExternalizerConstants.CALL_ARG_TAG_NAME + " with name [" + argumentName + "]."); 
	}

	/**
	 * Returns a map with the NodeStructures inside the containerElement. 
	 * @param containerElement
	 * @param commandName The command name.
	 * @return Map
	 * @throws LegacyCommandsExternalizationException
	 */
	private static Map readNodeStructures(Element containerElement, String commandName) throws LegacyCommandsExternalizationException
	{
		Map nodeStructures = null;
		//list of NodeStructures
		NodeList nodeList = XMLUtil.getElementsByTagName(containerElement, ExternalizerConstants.NODE_STRUCT_TAG_NAME);
		if(nodeList.getLength() > 0)
		{
			nodeStructures = new HashMap(nodeList.getLength());
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				//NodeStructure element
				Element nodeStructureElement = (Element) nodeList.item(i);
				
				NodeStructure nodeStructure = readNodeStructure(nodeStructureElement, commandName); 
				nodeStructures.put(nodeStructure.getId(), nodeStructure);
			}
		}
		return nodeStructures;
	}

	/**
	 * Read a single NodeStructure element.
	 * @param nodeStructureElement
	 * @param commandName The command name.
	 * @return NodeStructure
	 * @throws LegacyCommandsExternalizationException
	 */
	private static NodeStructure readNodeStructure(Element nodeStructureElement, String commandName) throws LegacyCommandsExternalizationException
	{
		String structureId = XMLUtil.getAttribute(nodeStructureElement, ExternalizerConstants.NODE_STRUCT_ATTR_ID);
		String adder = XMLUtil.getAttribute(nodeStructureElement, ExternalizerConstants.NODE_STRUCT_ATTR_ADDER);

		//if necessary, calc adder method name 
		if(adder == null)
		{
			adder = getMethodName(ADDER_PREFIX, structureId);
		}
				
		// get related Structure
		Structure structure = getStructure(structureId, commandName);

		// get contained Node Structures								
		Map childNodeStructures = readNodeStructures(nodeStructureElement, commandName);
				
		// create NodeStructure and add it to the nodeStructureContainer			
		NodeStructure nodeStructure = new NodeStructure(structureId, adder, structure, childNodeStructures);
		
		return nodeStructure;
	}


	/**
	 * Read a single Record element.
	 * @param recordElement
	 * @throws LegacyCommandsExternalizationException
	 */
	private static void readRecord(Element recordElement) throws LegacyCommandsExternalizationException
	{
		String recordId = XMLUtil.getAttribute(recordElement, ExternalizerConstants.RECORD_ATTR_ID);
		if(records.containsKey(recordId))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize legacy record [" + recordId + "]. A legacy record with that name is already defined.");
		}
		else
		{
			Logger.debug(LOGGER_CONTEXT, "loading legacy record [" + recordId + "]");

			String className = XMLUtil.getAttribute(recordElement, ExternalizerConstants.RECORD_ATTR_CLASS);
			List fields = readRecordFields(recordId, recordElement); 
			int uniqueFieldIndex = -1;
			for (int i = 0 ; i < fields.size() ; i++)
			{
				RecordField field = (RecordField)fields.get(i);
				if(field.isUniqueId())
				{
					uniqueFieldIndex = i;
					break;
				}
			}
			
			Record record = new Record(recordId, className, uniqueFieldIndex, fields);		
			records.put(recordId, record);
		}
	}

	/**
	 * Returns a List with the {@link RecordField}s of a specific Record element.
	 * @param recordId The record id.
	 * @param recordElement
	 * @return List 
	 * @throws LegacyCommandsExternalizationException
	 */
	private static List readRecordFields(String recordId, Element recordElement) throws LegacyCommandsExternalizationException
	{
		List fields = new ArrayList();

		//list of record field nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(recordElement, ExternalizerConstants.REC_FIELD_TAG_NAME);

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//record field element
			Element fieldElement = (Element) nodeList.item(i);

			String attributeId = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_ID);

			Logger.debug(LOGGER_CONTEXT, "loading legacy record field for attribute [" + attributeId + "]");

			String columnId = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.REC_FIELD_ATTR_COL_ID);
			String columnType = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.REC_FIELD_ATTR_COL_TYPE);
			String attributeType = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_TYPE);
			String setter = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_SETTER);
			String uniqueIdStr = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_UNIQUE);
			
			Integer attributeTypeId = getTypeId(ExternalizerConstants.RECORD_TAG_NAME, recordId, attributeType);
			Integer columnTypeId = getTypeId(ExternalizerConstants.RECORD_TAG_NAME, recordId, columnType);
			String fromMask = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_FROM_MASK);
			
			if(attributeType != null && columnType == null)
			{
				throw new LegacyCommandsExternalizationException("Unable to read record [" + recordId + "], Attribute " + ExternalizerConstants.FIELD_ATTR_TYPE + " should be supplied together with attribute " + ExternalizerConstants.REC_FIELD_ATTR_COL_TYPE + "."); 
			}
			
			boolean uniqueId = false;
			if(uniqueIdStr != null)
			{
				if(uniqueIdStr.equals(ExternalizerConstants.FIELD_UNIQUE_ID_TRUE))
				{
					uniqueId = true;
				}
				else if(! uniqueIdStr.equals(ExternalizerConstants.FIELD_UNIQUE_ID_FALSE))
				{
					throw new LegacyCommandsExternalizationException("Unable to read record [" + recordId + "]. Invalid " + ExternalizerConstants.FIELD_ATTR_UNIQUE + " [" + uniqueIdStr + "] for field [" + columnId + "]. "); 
				}
			}
			
			//if necessary, calc setter method name 
			if(setter == null)
			{
				setter = getMethodName(SETTER_PREFIX, attributeId);
			}
			
			RecordField recordField = new RecordField(attributeId, attributeTypeId, setter, uniqueId, columnId, columnTypeId, fromMask);
			fields.add(recordField);
		}		

		return fields;
	}

	/**
	 * Read a single Structure element.
	 * @param structureElement
	 * @throws LegacyCommandsExternalizationException
	 */
	private static void readStructure(Element structureElement) throws LegacyCommandsExternalizationException
	{
		String structureId = XMLUtil.getAttribute(structureElement, ExternalizerConstants.STRUCTURE_ATTR_ID);
		if(structures.containsKey(structureId))
		{
			Logger.warning(LOGGER_CONTEXT, "Unable to initialize legacy structure [" + structureId + "]. A legacy structure with that name is already defined.");
		}
		else
		{
			Logger.debug(LOGGER_CONTEXT, "loading legacy structure [" + structureId + "]");
			
			String className = XMLUtil.getAttribute(structureElement, ExternalizerConstants.STRUCTURE_ATTR_CLASS);
			int length = Integer.parseInt(XMLUtil.getAttribute(structureElement, ExternalizerConstants.STRUCTURE_ATTR_LENGTH));
			
			List fields = readStructureFields(structureId, structureElement); 

			Structure structure = new Structure(structureId, className, length, fields);		

			structures.put(structureId, structure);
		}
	}
	
	/**
	 * Returns a List with the {@link StructureField}s of a specific Structure element.
	 * @param structureId The Structure id
	 * @param structureElement
	 * @return List
	 * @throws LegacyCommandsExternalizationException
	 */
	private static List readStructureFields(String structureId, Element structureElement) throws LegacyCommandsExternalizationException
	{
		List fields = new ArrayList();

		//list of structure field nodes
		NodeList nodeList = XMLUtil.getElementsByTagName(structureElement, ExternalizerConstants.STRUCT_FIELD_TAG_NAME);

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			//structure field element
			Element fieldElement = (Element) nodeList.item(i);

			String attributeId = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_ID);

			Logger.debug(LOGGER_CONTEXT, "loading legacy structure field for attribute [" + attributeId + "]");

			int startPosition = Integer.parseInt(XMLUtil.getAttribute(fieldElement, ExternalizerConstants.STRUCT_FIELD_ATTR_START_POS));
			int length = Integer.parseInt(XMLUtil.getAttribute(fieldElement, ExternalizerConstants.STRUCT_FIELD_ATTR_LENGTH));
			String fromMask = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_FROM_MASK);
			
			String attributeType = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_TYPE);
			String setter = XMLUtil.getAttribute(fieldElement, ExternalizerConstants.FIELD_ATTR_SETTER);

			Integer attributeTypeId = getTypeId(ExternalizerConstants.STRUCTURE_TAG_NAME, structureId, attributeType);
						
			//if necessary, calc setter method name 
			if(setter == null)
			{
				setter = getMethodName(SETTER_PREFIX, attributeId);
			}
			
			StructureField structureField = new StructureField(attributeId, attributeTypeId, setter, startPosition, length, fromMask);
			fields.add(structureField);
		}		

		return fields;
	}
	/**
	 * Returns the LegacyCommand definition according to the given command name.
	 * @param commandName The name of the command to return.
	 * @return LegacyCommand
	 * @throws LegacyCommandNotFoundException
	 */
	protected static LegacyCommand getCommand (String commandName) throws LegacyCommandNotFoundException
	{
		LegacyCommand command = null;
		synchronized (commands)
		{
			command = (LegacyCommand) commands.get(commandName);
		}	
		
		if(command == null)
		{
			throw new LegacyCommandNotFoundException("Unable to execute legacy command [" + commandName + "]. The command was not been defined.");
		}
		
		return command;
	}

	/**
	 * Returns the Structure definition according to the given structure name.
	 * @param structureName The name of the structure to return.
	 * @param commandName The name of the command.
	 * @return Structure
	 * @throws LegacyCommandsExternalizationException
	 */
	private static Structure getStructure (String structureName, String commandName) throws LegacyCommandsExternalizationException
	{
		Structure structure = (Structure) structures.get(structureName);
		
		if(structure == null)
		{
			throw new LegacyCommandsExternalizationException("Unable to load legacy command [" + commandName + "]. The Structure [" + structureName + "] was not been defined.");
		}
		
		return structure;
	}

	/**
	 * Returns the Record definition according to the given record name.
	 * @param recordName The name of the record to return.
	 * @param commandName The name of the command.
	 * @return Record
	 * @throws LegacyCommandsExternalizationException
	 */
	private static Record getRecord (String recordName, String commandName) throws LegacyCommandsExternalizationException
	{
		Record record = (Record) records.get(recordName);
		
		if(record == null)
		{
			throw new LegacyCommandsExternalizationException("Unable to load legacy command [" + commandName + "]. The Record [" + recordName + "] was not been defined.");
		}
		
		return record;
	}

	/**
	 * Returns the activity type according to the String.
	 * @param commandName The command name.
	 * @param activityTypeStr A String contains the activity type.
	 * @return int The activity type numeric constant.
	 * @throws LegacyCommandsExternalizationException
	 */
	private static int getActivityType(String commandName, String activityTypeStr) throws LegacyCommandsExternalizationException
	{
		int activityType = LegacyConstants.ACTIVITY_TYPE_READ_WRITE;
		if(activityTypeStr != null)
		{
			if(activityTypeStr.equals(ExternalizerConstants.COMMAND_ACTIVITY_TYPE_READ_ONLY))
			{
				activityType = LegacyConstants.ACTIVITY_TYPE_READ_ONLY;
			}
			else if(! activityTypeStr.equals(ExternalizerConstants.COMMAND_ACTIVITY_TYPE_READ_WRITE))
			{
				throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Unsupported activity type[" + activityTypeStr + "]"); 
			}
		}
		return activityType;
	}

	/**
	 * Returns the argument input type according to the String.
	 * @param commandName The command name.
	 * @param inputTypeStr 
	 * @return int The input type numeric constant.
	 * @throws LegacyCommandsExternalizationException
	 */
	private static int getArgumntInputType (String commandName, String inputTypeStr) throws LegacyCommandsExternalizationException
	{
		if(inputTypeStr == null)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Argument input type should be supplied."); 
		}
		int inputType;
		if(inputTypeStr.equals(ExternalizerConstants.CALL_ARG_INPUT_TYPE_IN))
		{
			inputType = CallArgument.INPUT_TYPE_AS_INPUT;
		}
		else if(inputTypeStr.equals(ExternalizerConstants.CALL_ARG_INPUT_TYPE_INOUT))
		{
			inputType = CallArgument.INPUT_TYPE_AS_INPUT_OUTPUT;
		}
		else if(inputTypeStr.equals(ExternalizerConstants.CALL_ARG_INPUT_TYPE_OUT))
		{
			inputType = CallArgument.INPUT_TYPE_AS_OUTPUT;
		}
		else
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Unsupported argument input type [" + inputTypeStr + "]"); 
		}
		return inputType;
				
	}

	/**
	 * Returns the sql type according to the String.
	 * @param commandName The command name.
	 * @param type
	 * @return int The sql type numeric constant.
	 * @throws LegacyCommandsExternalizationException
	 */
	private static int getSQLType (String commandName, String type) throws LegacyCommandsExternalizationException
	{
		// TODO check if should support boolean type 
		if(type == null)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Output argument type should be supplied."); 
		}
		int dbType;
		if(type.equals(ExternalizerConstants.TYPE_DECIMAL))
		{
			dbType = StoredProcedureService.DECIMAL;
		}
		else if(type.equals(ExternalizerConstants.TYPE_INTEGER))
		{
			dbType = StoredProcedureService.INTEGER;
		}
		else if(type.equals(ExternalizerConstants.TYPE_LONG))
		{
			dbType = StoredProcedureService.LONG;
		}
		else if(type.equals(ExternalizerConstants.TYPE_DOUBLE))
		{
			dbType = StoredProcedureService.DOUBLE;
		}
		else if(type.equals(ExternalizerConstants.TYPE_FLOAT))
		{
			dbType = StoredProcedureService.FLOAT;
		}
		else if(type.equals(ExternalizerConstants.TYPE_STRING))
		{
			dbType = StoredProcedureService.STRING;
		}
		else if(type.equals(ExternalizerConstants.TYPE_DATE))
		{
			dbType = StoredProcedureService.DATE;
		}
		else if(type.equals(ExternalizerConstants.TYPE_TIMESTAMP))
		{
			dbType = StoredProcedureService.TIMESTAMP;
		}
		else if(type.equals(ExternalizerConstants.TYPE_TIME))
		{
			dbType = StoredProcedureService.TIME;
		}
		else
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize legacy command [" + commandName + "]. Unsupported Output argument type [" + type + "]"); 
		}
		return dbType;
	}

	/**
	 * Returns the class name according to the given type.
	 * @param elementType The type of the element.
	 * @param elementName The name of the element.
	 * @param type The type the get the class name.
	 * @return String type class name.
	 * @throws LegacyCommandsExternalizationException
	 */
	private static String getTypeClassName (String elementType, String elementName, String type) throws LegacyCommandsExternalizationException
	{
		if(type == null)
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize " + elementType + " [" + elementName + "]. The type should be supplied."); 
		}
		
		String typeClassName;
		if(type.equals(ExternalizerConstants.TYPE_DECIMAL))
		{
			typeClassName = BigDecimal.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_INTEGER))
		{
			typeClassName = Integer.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_LONG))
		{
			typeClassName = Long.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_DOUBLE))
		{
			typeClassName = Double.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_FLOAT))
		{
			typeClassName = Float.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_STRING))
		{
			typeClassName = String.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_DATE))
		{
			typeClassName = Date.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_TIMESTAMP))
		{
			typeClassName = Timestamp.class.getName();
		}
		else if(type.equals(ExternalizerConstants.TYPE_TIME))
		{
			typeClassName = Time.class.getName();
		}
		else
		{
			throw new LegacyCommandsExternalizationException ("Unable to initialize " + elementType + " [" + elementName + "]. Unsupported type [" + type + "]"); 
		}
		return typeClassName;
	}

	/**
	 * Returns the id of the type according to the String.
	 * @param elementType The type of the element.
	 * @param elementName The name of the element.
	 * @param type The type the get the class name.
	 * @return Integer The id of the type.
	 * @throws LegacyCommandsExternalizationException
	 */
	private static Integer getTypeId (String elementType, String elementName, String type) throws LegacyCommandsExternalizationException
	{
		int typeId;
		if(type != null)
		{
			if(type.equals(ExternalizerConstants.TYPE_DECIMAL))
			{
				typeId = TypeConverter.TYPE_BIG_DECIMAL;
			}
			else if(type.equals(ExternalizerConstants.TYPE_INTEGER))
			{
				typeId = TypeConverter.TYPE_INTEGER;
			}
			else if(type.equals(ExternalizerConstants.TYPE_LONG))
			{
				typeId = TypeConverter.TYPE_LONG;
			}
			else if(type.equals(ExternalizerConstants.TYPE_DOUBLE))
			{
				typeId = TypeConverter.TYPE_DOUBLE;
			}
			else if(type.equals(ExternalizerConstants.TYPE_FLOAT))
			{
				typeId = TypeConverter.TYPE_FLOAT;
			}
			else if(type.equals(ExternalizerConstants.TYPE_STRING))
			{
				typeId = TypeConverter.TYPE_STRING;
			}
			else if(type.equals(ExternalizerConstants.TYPE_DATE))
			{
				typeId = TypeConverter.TYPE_DATE;
			}
			else if(type.equals(ExternalizerConstants.TYPE_TIMESTAMP))
			{
				typeId = TypeConverter.TYPE_TIMESTAMP;
			}
			else if(type.equals(ExternalizerConstants.TYPE_TIME))
			{
				typeId = TypeConverter.TYPE_TIME;
			}
			else
			{
				throw new LegacyCommandsExternalizationException ("Unable to initialize " + elementType + " [" + elementName + "]. Unsupported type [" + type + "]"); 
			}
			return new Integer(typeId);
		}
		return null;
	}

	/**
	 * Returns the method name of the attribute according to the method prefix   
	 * @param prefix
	 * @param attributeId
	 * @return String method name
	 */
	private static String getMethodName (String prefix, String attributeId) 
	{
		return prefix + attributeId.substring(0, 1).toUpperCase() + attributeId.substring(1);
	}


}
