/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: ExternalizerConstants.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

/**
 * The constants for XML parsing of legacy command definition files.
 */
public class ExternalizerConstants
{

	/*
	 * LegacyCommand element definitions
	 */
	// tag name
	public static final String COMMAND_TAG_NAME = "LegacyCommand";
	// attributes
	public static final String COMMAND_ATTR_NAME = "commandName";
	public static final String COMMAND_ATTR_TYPE = "commandType";
	public static final String COMMAND_ATTR_ACTIVITY_TYPE = "activityType";
	public static final String COMMAND_ATTR_SQL_STATEMENT = "sqlStatement";	
	public static final String COMMAND_ATTR_CONNECTION_MANAGER = "connectionManagerName";
	public static final String COMMAND_ATTR_LIBRARY_NAME = "libraryName";
	public static final String COMMAND_ATTR_PROGRAM_NAME = "programName";
	
	
	// command type values
	public static final String COMMAND_TYPE_SP = "SP";
	public static final String COMMAND_TYPE_OS400 = "OS400";

	// command activity type values
	public static final String COMMAND_ACTIVITY_TYPE_READ_WRITE = "readWrite";
	public static final String COMMAND_ACTIVITY_TYPE_READ_ONLY = "readOnly";
	
	/*
	 * CallArgument element definitions 
	 */ 
	// tag name
	public static final String CALL_ARG_TAG_NAME = "CallArgument";
	// attributes
	public static final String CALL_ARG_ATTR_NAME = "name";
	public static final String CALL_ARG_ATTR_TYPE = "type";
	public static final String CALL_ARG_ATTR_INPUT_TYPE = "inputType";
	public static final String CALL_ARG_ATTR_GETTER = "inputContainerGetter";
	public static final String CALL_ARG_ATTR_RESULT_AS = "resultAs";
	public static final String CALL_ARG_ATTR_SIMPLE_SETTER = "simpleResultSetter";
	// input type values
	public static final String CALL_ARG_INPUT_TYPE_IN = "in";
	public static final String CALL_ARG_INPUT_TYPE_OUT = "out";
	public static final String CALL_ARG_INPUT_TYPE_INOUT = "inout";
	// argument type values
	public static final String TYPE_DECIMAL = "decimal";
	public static final String TYPE_INTEGER = "integer";
	public static final String TYPE_LONG = "long";
	public static final String TYPE_DOUBLE = "double";
	public static final String TYPE_FLOAT = "float";
	public static final String TYPE_STRING = "string";
	public static final String TYPE_DATE = "date";
	public static final String TYPE_TIMESTAMP = "timestamp";
	public static final String TYPE_TIME = "time";
	// result as values
	public static final String CALL_ARG_RESULT_AS_SIMPLE = "simple";
	public static final String CALL_ARG_RESULT_AS_STRUCTURE = "structure";

	/*
	 * LegacyObjectGraph element definitions 
	 */ 
	// tag name
	public static final String OBJ_GRAPH_TAG_NAME = "LegacyObjectGraph";
	// attributes
	public static final String OBJ_GRAPH_ATTR_CLASS = "className";

	/*
	 * NodeRecord element definitions 
	 */ 
	// tag name
	public static final String NODE_REC_TAG_NAME = "NodeRecord";
	// attributes
	public static final String NODE_REC_ATTR_ID = "id";
	public static final String NODE_REC_ATTR_RS_NUM = "resultSetNumber";
	public static final String NODE_REC_ATTR_ADDER = "adder";
	public static final String NODE_REC_ATTR_PARENT_ATTR = "parentIdAttribute";

	/*
	 * ArgumentStructureDefinition element definitions 
	 */ 
	// tag name
	public static final String ARGUMENT_STRUCT_TAG_NAME = "ArgumentStructureDefinition";
	// attributes
	public static final String ARGUMENT_STRUCT_ATTR_NAME = "name";
	public static final String ARGUMENT_STRUCT_ATTR_STR_ID_LENGTH = "structureIdLength";

	/*
	 * NodeStructure element definitions 
	 */ 
	// tag name
	public static final String NODE_STRUCT_TAG_NAME = "NodeStructure";
	// attributes
	public static final String NODE_STRUCT_ATTR_ID = "id";
	public static final String NODE_STRUCT_ATTR_ADDER = "adder";

	/*
	 * common field element definitions 
	 */ 
	// attributes
	public static final String FIELD_ATTR_ID = "attributeId";
	public static final String FIELD_ATTR_TYPE = "attributeType";
	public static final String FIELD_ATTR_UNIQUE = "uniqueId";
	public static final String FIELD_ATTR_SETTER = "setter";
	public static final String FIELD_ATTR_FROM_MASK = "fromMask";
	// unique id values
	public static final String FIELD_UNIQUE_ID_TRUE = "true";
	public static final String FIELD_UNIQUE_ID_FALSE = "false";


	/*
	 * Record element definitions 
	 */ 
	// tag name
	public static final String RECORD_TAG_NAME = "Record";
	// attributes
	public static final String RECORD_ATTR_ID = "id";
	public static final String RECORD_ATTR_CLASS = "className";

	/*
	 * RecordField element definitions 
	 */ 
	// tag name
	public static final String REC_FIELD_TAG_NAME = "RecordField";
	// attributes
	public static final String REC_FIELD_ATTR_COL_ID = "columnId";
	public static final String REC_FIELD_ATTR_COL_TYPE = "columnType";

	/*
	 * Structure element definitions 
	 */ 
	// tag name
	public static final String STRUCTURE_TAG_NAME = "Structure";
	// attributes
	public static final String STRUCTURE_ATTR_ID = "id";
	public static final String STRUCTURE_ATTR_CLASS = "className";
	public static final String STRUCTURE_ATTR_LENGTH = "length";

	/*
	 * StructureField element definitions 
	 */ 
	// tag name
	public static final String STRUCT_FIELD_TAG_NAME = "StructureField";
	// attributes
	public static final String STRUCT_FIELD_ATTR_START_POS = "startPosition";
	public static final String STRUCT_FIELD_ATTR_LENGTH = "length";

}
