/*
 * Created on 17/11/2004
 *
 */
package com.ness.fw.workflow.mdb;

/**
 * @author Amit Mendelson
 *
 */
public class UPESConstants
{

	/**
	 * INSERT_TO_DB
	 * Key for insertion of the pair (ProcessID, CorrelID) to DB.
	 */
	public static final String INSERT_TO_DB = "WorkFlow.UPES.InsertToDB";
	
	/**
	 * SELECT_FROM_DB
	 * Key for retrieval of the CorrelIDs from the DB.
	 */
	public static final String SELECT_FROM_DB = "WorkFlow.UPES.RetrieveFromDB";

	/**
	 * DELETE_FROM_DB
	 * Key for removal of the pair (CorrelID, ProcessID) from DB.
	 */
	public static final String DELETE_FROM_DB = "WorkFlow.UPES.DeleteFromDB";

	/**
	 * IIOP_PROVIDER_URL
	 */
	public static final String IIOP_PROVIDER_URL = "WorkFlow.UPES.IIOPProviderUrl";

	/**
	 * RESPONSE_MESSAGE
	 */
	public static final String RESPONSE_MESSAGE = "WorkFlow.UPES.ResponseMessage";

	/**
	 * SHORT_WAIT
	 */
	public static final String SHORT_WAIT = "WorkFlow.UPES.ShortWait";

	/**
	 * LONG_WAIT
	 */
	public static final String LONG_WAIT = "WorkFlow.UPES.LongWait";

//	/**
//	 * UPES_HOST_NAME
//	 */
//	public static final String UPES_HOST_NAME = "WorkFlow.UPES.HostName";
//
//	/**
//	 * UPES_USER_ID
//	 */
//	public static final String UPES_USER_ID	= "WorkFlow.UPES.UserID";

	/**
	 * UPES_WF_USER_ID
	 */
	public static final String UPES_WF_USER_ID	= "WorkFlow.UPES.WFUserId";

	/**
	 * UPES_WF_APP_USER_ID
	 */
	public static final String UPES_WF_APP_USER_ID	= "WorkFlow.UPES.APPUserId";

	/**
	 * INITIAL_CONTEXT_FACTORY_MAPPING
	 */		
	public static final String INITIAL_CONTEXT_FACTORY_MAPPING =
		"WorkFlow.UPES.InitialContextFactoryMapping";
	//	"com.ibm.websphere.naming.WsnInitialContextFactory";

	
//	/**
//	 * PUBLISH
//	 * Code for performing a publish command from the MDB.
//	 */
//	public static final int PUBLISH = 10;
//
//	/**
//	 * DELETE_PUBLICATION
//	 * Code for performing a delete publication command from the MDB.
//	 */
//	public static final int DELETE_PUBLICATION = 11;
//
//	/**
//	 * REGISTER_SUBSCRIBER
//	 * Code for performing a register subscriber command from the MDB.
//	 */
//	public static final int REGISTER_SUBSCRIBER = 12;
//
//	/**
//	 * DEREGISTER_SUBSCRIBER
//	 * Code for performing a deregister subscriber command from the MDB.
//	 */
//	public static final int DEREGISTER_SUBSCRIBER = 13;
//
//	/**
//	 * REGISTER_PUBLISHER
//	 * Code for performing a register publisher command from the MDB.
//	 */
//	public static final int REGISTER_PUBLISHER = 14;
//
//	/**
//	 * DEREGISTER_PUBLISHER
//	 * Code for performing a deregister publisher command from the MDB.
//	 */
//	public static final int DEREGISTER_PUBLISHER = 15;

	/**
	 * WFMESSAGE_PREFIX
	 */
	public static final String WFMESSAGE_PREFIX = "<WfMessage>";
	
	/**
	 * WFMESSAGE_SUFFIX
	 */
	public static final String WFMESSAGE_SUFFIX = "</WfMessage>";

	/**
	 * MESSAGE_HEADER_PREFIX
	 */
	public static final String MESSAGE_HEADER_PREFIX =
		"<WfMessageHeader><ResponseRequired>";
	
	/**
	 * MESSAGE_HEADER_SUFFIX
	 */
	public static final String MESSAGE_HEADER_SUFFIX =
		"</ResponseRequired></WfMessageHeader>";
	
	/**
	 * ACTIVITY_IMPL_INVOKE_PREFIX
	 */
	public static final String ACTIVITY_IMPL_INVOKE_RESP_PREFIX = 
		"<ActivityImplInvokeResponse>";
	
	/**
	 * ACTIVITY_IMPL_INVOKE_SUFFIX
	 */
	public static final String ACTIVITY_IMPL_INVOKE_RESP_SUFFIX = 
		"</ActivityImplInvokeResponse>";
	
	/**
	 * PROGRAM_NAME
	 */
	public static final String PROGRAM_NAME = "ProgramName";

	/**
	 * PROCESS_NAME
	 */
	public static final String PROCESS_NAME = "_PROCESS";

	/**
	 * PROCESS_TEMPLATE_NAME
	 */
	public static final String PROCESS_TEMPLATE_NAME = "_PROCESS_MODEL";

	/**
	 * WAIT_FOR_DOCUMENTS
	 */
	public static final String WAIT_FOR_DOCUMENTS = "WaitForDocuments";

	/**
	 * TRUE
	 */
	public static final String TRUE = "true";

	/**
	 * PROGRAM_INPUT_DATA
	 * ProgramInputData section name in the XML message.
	 */
	public static final String PROGRAM_INPUT_DATA = "ProgramInputData";

	/**
	 * PROGRAM_OUTPUT_DATA_DEFAULTS
	 */
	public static final String PROGRAM_OUTPUT_DATA_DEFAULTS = 
		"ProgramOutputDataDefaults";

	/**
	 * PROGRAM_OUTPUT_DATA
	 */
	public static final String PROGRAM_OUTPUT_DATA =
		"ProgramOutputData";

	/**
	 * PROGRAM_OUTPUT_DATA_PREFIX
	 */
	public static final String PROGRAM_OUTPUT_DATA_PREFIX = 
		"<ProgramOutputData>";
	
	/**
	 * PROGRAM_OUTPUT_DATA_SUFFIX
	 */
	public static final String PROGRAM_OUTPUT_DATA_SUFFIX = 
		"</ProgramOutputData>";
	
	/**
	 * ACT_IMPL_CORREL_ID_PREFIX
	 */
	public static final String ACT_IMPL_CORREL_ID_PREFIX =
		"<ActImplCorrelID>";
	
	/**
	 * ACT_IMPL_CORREL_ID_SUFFIX
	 */
	public static final String ACT_IMPL_CORREL_ID_SUFFIX =
		"</ActImplCorrelID>";
	/**
	 * ACT_IMPL_CORREL_ID
	 */
	public static final String ACT_IMPL_CORREL_ID = "ActImplCorrelID";
	
	/**
	 * PROGRAM_RC_PREFIX
	 */
	public static final String PROGRAM_RC_PREFIX = "<ProgramRC>";
	
	/**
	 * PROGRAM_RC_SUFFIX
	 */
	public static final String PROGRAM_RC_SUFFIX = "</ProgramRC>";

	/**
	 * PREDEFINED_PROGRAM_RC
	 */
	public static final String PREDEFINED_PROGRAM_RC = 
		"<ProgramRC>0</ProgramRC>";	

	/**
	 * DELIMITER
	 */
	public static final String DELIMITER = "/";
	
	/**
	 * UPES_CONTEXT
	 */
	public static final String UPES_CONTEXT = "WorkFlow.UPES";
	
	/**
	 * MDB_MESSAGE_NULL
	 */
	public static final String MDB_MESSAGE_NULL = "Expected MDB message is null";
	
	/**
	 * MDB_PROGRAM_TERMINATED
	 */
	public static final String MDB_PROGRAM_TERMINATED = "MDB Program is terminated";

	/**
	 * MDB_ACTIVITY_EXPIRED
	 */
	public static final String MDB_ACTIVITY_EXPIRED = "MDB Activity expired";

	/**
	 * BAD_TREE_FORMAT
	 */
	public static final String BAD_TREE_FORMAT = "Bad tree format";
	
	/**
	 * TERMINATE_PROGRAM
	 */
	public static final String TERMINATE_PROGRAM = "TerminateProgram";
	
	/**
	 * ACTIVITY_EXPIRED
	 */
	public static final String ACTIVITY_EXPIRED = "ActivityExpired";
	
	/**
	 * YES
	 */
	public static final String YES = "Yes";
	
	/**
	 * IF_ERROR
	 */
	public static final String IF_ERROR = "IfError";

	/**
	 * QUEUE_CONNECTION_FACTORY_LOOKUP
	 */
	public static final String QUEUE_CONNECTION_FACTORY_LOOKUP = 
		"WorkFlow.UPES.WFQCF";
//	public static final String QCF_LOOKUP = "WFQCF";
	
	/**
	 * Q_LOOKUP
	 */
	public static final String QUEUE_LOOKUP = "WorkFlow.UPES.OutputQueue";
	//public static final String Q_LOOKUP = "MDBOUTPUTQ";
	
	/**
	 * THROW
	 */
	public static final String THROW = "throw";
	
	/**
	 * CORREL_ID_STRING
	 */
	public static final String CORREL_ID_STRING = "_CORREL_ID_STRING_";

	/**
	 * PROGRAM_OUTPUT_DATA_STRING
	 */	
	public static final String PROGRAM_OUTPUT_DATA_STRING = 
		"PROGRAM_OUTPUT_DATA_STRING";
	
	/**
	 * PROCESS_NAMES_ARRAY
	 * Is used during generation of the SQL query for retrieving CorrelIDs.
	 */	
	public static final String PROCESS_NAMES_ARRAY = "PROCESS_NAMES_ARRAY";
	
	/**
	 * CORREL_ID
	 */
	public static final String CORREL_ID = "CORREL_ID";
	
	/**
	 * PROCESS_NAMES_EMPTY
	 */
	public static final String PROCESS_NAMES_EMPTY =
		"Process names are empty";
	
	/**
	 * PROBLEM_WITH_SYSTEM_RESOURCES
	 */	
	public static final String PROBLEM_WITH_SYSTEM_RESOURCES = 
		"Problem with SystemResources";

	/**
	 * PROCESS_NAME_IS_NULL
	 */
	public static final String PROCESS_NAME_IS_NULL = "Process name is null";
	
	/**
	 * CORREL_ID_IS_NULL
	 */
	public static final String CORREL_ID_IS_NULL = "CorrelId is null";
	
//	/**
//	 * PROCESS_ID_START_TAG
//	 */
//	public static final String PROCESS_ID_START_TAG = "<_PROCESS>";
//
//	/**
//	 * PROCESS_ID_END_TAG
//	 */
//	public static final String PROCESS_ID_END_TAG = "</_PROCESS>";
//	
//	/**
//	 * ILLEGAL_MESSAGE_NO_PROCESS_ID
//	 */
//	public static final String ILLEGAL_MESSAGE_NO_PROCESS_ID = 
//		"Illegal XML string; Can't found the ProcessID";

	/**
	 * SYSTEM_CONFIGURATION_FILES
	 */
	public static final String SYSTEM_CONFIGURATION_FILES = "SystemConfigurationFiles";
	
//	/**
//	 * LOGGER_CONFIG_PATH
//	 */
//	public static final String LOGGER_CONFIG_PATH = "loggerConfigPath";
//
//	/**
//	 * SYSTEM_INIT_LOCATION
//	 */
//	public static final String SYSTEM_INIT_LOCATION = "config/systemInit.xml";
//	
//	/**
//	 * SERVERS_CONFIG_PATH
//	 */
//	public static final String SERVERS_CONFIG_PATH = "serversConfigPath";
	
	/**
	 * MDB_INITIALIZATION_PROBLEM
	 */
	public static final String MDB_INITIALIZATION_PROBLEM = 
		"problem with MDB initialization";
	
	/**
	 * ERROR_ACTIVATING_UPES_PREFIX
	 */
	public static final String ERROR_ACTIVATING_UPES_PREFIX =
		"error activating upes [";

}