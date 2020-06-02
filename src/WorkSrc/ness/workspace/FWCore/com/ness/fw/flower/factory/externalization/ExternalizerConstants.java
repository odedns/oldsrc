/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ExternalizerConstants.java,v 1.3 2005/03/29 17:44:54 yifat Exp $
 */
package com.ness.fw.flower.factory.externalization;

/**
 * The constants for XML parsing of flower files.
 */
public class ExternalizerConstants
{
	//common
	public static final String ATTR_NAME                            	=   "name";
	public static final String ATTR_CONTEXT                         	=   "context";
	public static final String ATTR_IN_FORMATTER                    	=   "inFormatter";
	public static final String ATTR_OUT_FORMATTER                   	=   "outFormatter";
	public static final String ATTR_COMPLEX_FORMATTER                   =   "complexFormatter";
	public static final String ATTR_VALIDATOR                       	=   "validator";
    public static final String ATTR_CLASS_NAME                      	=   "class";
	public static final String ATTR_IMPLEMENTATION                  	=   "implementation";
	public static final String ATTR_TYPE                            	=   "type";
	public static final String ATTR_VALUE                           	=   "value";
	public static final String ATTR_DEFAULT_VALUE                   	=   "defaultValue";
	public static final String ATTR_PAGE                            	=   "page";
	public static final String ATTR_FLOW_NAME                       	=   "flowName";
	public static final String ATTR_SUB_FLOW_IN_FORMATTER           	=   "inFormatter";
	public static final String ATTR_SUB_FLOW_OUT_FORMATTER          	=   "outFormatter";
	public static final String ATTR_SUB_FLOW_GOOD_FINAL_STATES      	=   "goodFinalStates";
	public static final String ATTR_MENU_NAME                       	=   "menu";
	public static final String ATTR_FIELD_NAME                      	=   "fieldName";
	public static final String ATTR_FIELD_SET_ERROR_STATE               =   "setFieldErrorState";
	public static final String ATTR_STOP_ON_ERROR                   	=   "stopOnError";
	public static final String ATTR_CAPTION                         	=   "caption";
	public static final String ATTR_METHOD_NAME                     	=   "method";
	public static final String ATTR_FROM_TYPE                       	=   "fromType";
	public static final String ATTR_AUTH_ID                         	=   "authId";

	public static final String PARAM_TAG_NAME                       	=   "param";
	public static final String ACTION_TAG_NAME                      	=   "action";
	public static final String SUB_FLOW_DATA_TAG_NAME               	=   "subFlowData";
	public static final String TAB_SYSTEM_DATA_TAG_NAME               	=   "tabSystemData";
	public static final String TAB_SYSTEM_ATTR_PAGE 					=   "page";

	public static final String TRUE                                 	=   "true";
	public static final String FALSE                                	=   "false";

	public static final String DEFAULT_IMPLEMENTATION               	=   "default";

	public static final String GOOD_FINAL_STATES_DELIMITER          	=   ",";

	//type definitions
    public static final String TYPE_DEFINITION_TAG_NAME             		=   "typeDefinition";
	public static final String TYPE_DEFINITION_TYPE_TAG_NAME        		=   "type";
	public static final String TYPE_DEFINITION_BUILDER_DEFINITION_TAG_NAME 	=   "builderTypeDefinition";
	public static final String TYPE_DEFINITION_BUILDER_TAG_NAME     		=   "builder";
	public static final String TYPE_DEFINITION_BUILDERS_TAG_NAME    		=   "builders";
	public static final String TYPE_DEFINITION_PRINTER_TAG_NAME     		=   "printer";
	public static final String BUILDER_DEFINITION_ATTR_BUILDER_TYPE   		=   "builderTypeName";
	public static final String TYPE_ATTR_IS_XI_TYPE        					=   "xiType";
	public static final String TYPE_ATTR_BASIC_XI_TYPE        				=   "basicXIType";

	
	//validators
	public static final String VALIDATOR_TAG_NAME                   		=   "validator";
	public static final String VALIDATION_CHECK_DEFINITION_TAG_NAME 		=   "validationCheckDefinition";
	public static final String VALIDATION_CHECK_PARAM_DEFINITION_TAG_NAME 	=   "checkParamDefinition";
	public static final String VALIDATION_CHECK_PARAM_ATTR_DEF_TYPE		 	=   "defaultType";
	public static final String VALIDATION_CHECK_TAG_NAME            		=   "validationCheck";
	public static final String VALIDATION_CHECK_SET_TAG_NAME        		=   "validationCheckSet";
	public static final String CHECK_SET_TAG_NAME                   		=   "checkSet";
	public static final String CHECK_TAG_NAME                       		=   "check";
	public static final String VALIDATION_CHECK_PARAM               		=   "checkParam";
	public static final String VALIDATION_PARAM_MULTITPICITY        		=   "multiplicity";
	public static final String VALIDATION_PARAM_MULTITPICITY_MANY   		=   "*";
	public static final String CHECK_ATTR_VALIDATION_CHECK_NAME        		=   "validationCheckName";
	public static final String CHECK_ATTR_VALIDATION_CHECK_DEF        		=   "validationCheckDef";
	public static final String CHECK_SET_ATTR_VAL_CHECK_SET_NAME            =   "validationCheckSetName";
	public static final String CHECK_PARAM_ATTR_VALUE_TYPE	        		=   "valueType";
	

	public static final String VALIDATOR_TYPE_CUSTOM                	=   "custom";
	public static final String VALIDATOR_TYPE_COMPLEX               	=   "complex";

	//context
	public static final String CONTEXT_TAG_NAME                     	=   "context";
	public static final String CONTEXT_STRUCTURE_TAG_NAME           	=   "structureDefinition";
    public static final String CONTEXT_ATTR_PARENT                  	=   "parent";
	public static final String CONTEXT_ATTR_CHILD_ACCESS                =   "childAccess";
	public static final String CONTEXT_ATTR_ADD_ON_DEMAND           	=   "addOnDemand";

	public static final String CONTEXT_TYPE_DYNAMIC                 	=   "dynamic";
	public static final String CONTEXT_TYPE_STATIC                  	=   "static";

	public static final String CONTEXT_FIELD_TAG_NAME               	=   "field";
	public static final String CONTEXT_STRUCTURE_FIELD_TAG_NAME     	=   "structureField";
	public static final String CONTEXT_SERVICE_TAG_NAME             	=   "service";
	public static final String CONTEXT_SERVICE_DEF_NAME					=   "serviceDefName";
	public static final String CONTEXT_ELEMENT_SET_DEFINITION_TAG_NAME  =   "contextElementSetDefinition";
	public static final String CONTEXT_ELEMENT_SET_TAG_NAME             =   "contextElementSet";
	public static final String CONTEXT_ELEMENT_SET_ATTR_DEF_NAME        =   "contextElementSetDefName";
	
	

	public static final String CONTEXT_FIELD_ACCESS_FULL          		=   "full";
	public static final String CONTEXT_FIELD_ACCESS_READ_ONLY         	=   "readOnly";
	public static final String CONTEXT_FIELD_ACCESS_NONE	         	=   "none";

	//service
	public static final String SERVICE_TAG_NAME                     	=   "serviceDefinition";
	public static final String SERVICE_ATTR_DEFAULT_METHOD_CONTEXT		=   "defaultMethodContext";
	
	
	// service method
	public static final String SERVICE_METHOD_TAG_NAME              	=   "serviceMethod";
	public static final String SERVICE_METHOD_ATTR_EXECUTION_METHOD 	=   "executionMethod";
		
	//operation
	public static final String OPERATION_TAG_NAME                   	=   "operationDefinition";

	//formatter
	public static final String FORMATTER_TAG_NAME                   	=   "formatterDefinition";
	public static final String FORMATTER_FORMAT_TAG_NAME            	=   "format";

	public static final String IN_FORMATTER_TAG_NAME                	=   "inputFormatter";
	public static final String OUT_FORMATTER_TAG_NAME               	=   "outputFormatter";

	// Formatter attributes
	public static final String FORMATTER_ATTR_FROM_NAME             	=   "fromName";
	public static final String FORMATTER_ATTR_FROM_VALUE             	=   "fromValue";
	public static final String FORMATTER_ATTR_TO_NAME               	=   "toName";

	// Formatter types
    public static final String FORMATTER_TYPE_SIMPLE                	=   "simple";
	public static final String FORMATTER_TYPE_CUSTOM                	=   "custom";
	public static final String FORMATTER_TYPE_COMPLEX                	=   "complex";

	public static final String STATE_TAG_NAME                       	=   "state";
	public static final String ACTION_LIST_TAG_NAME                	    =   "actionList";
	public static final String ENTRY_ACTIONS_TAG_NAME               	=   "entryActions";
	public static final String EXIT_ACTIONS_TAG_NAME                	=   "exitActions";
	public static final String ACTIONS_TAG_NAME                     	=   "actions";
	public static final String STATE_TRANSITION_TAG_NAME            	=   "transition";

	// State types
	public static final String STATE_TYPE_COMPLEX                      	=   "complex";
	public static final String STATE_TYPE_FINAL                     	=   "final";
	public static final String STATE_TYPE_SIMPLE                		=   "simple";


	// State attributes
	public static final String STATE_ATTR_INTERRUPTIBLE             	=   "interruptible";
	public static final String STATE_ATTR_REACHABLE_BY_FLOW_ONLY    	=   "reachableByFlowOnly";
	public static final String STATE_ATTR_INITIAL                   	=   "initial";
	public static final String STATE_ATTR_ACTIVITY                  	=   "activityName";
	public static final String STATE_ATTR_BUTTONSET                 	=   "buttonSet";
	public static final String STATE_ATTR_WAITING_FOR_EVENT            	=   "waitingForEvent";
	public static final String STATE_ATTR_VISIBLE         				=   "visible";
	
	

	// Transition attributes
	public static final String TRANSITION_ATTR_EVENT_NAME           	=   "event";
	public static final String TRANSITION_ATTR_GUARD                	=   "guard";
	public static final String TRANSITION_ATTR_CUSTOM_GUARD         	=   "customGuardName";
	public static final String TRANSITION_ATTR_VALIDATION_GUARD     	=   "validationGuard";
	public static final String TRANSITION_ATTR_TARGET_STATE         	=   "targetState";
	public static final String TRANSITION_ATTR_ALTERNATIVE_TARGET_STATE =   "alternativeTargetState";
	public static final String TRANSITION_ATTR_HANDLE_VALIDATION_ERROR  =   "handleValidationError";
	public static final String TRANSITION_ATTR_INTERNAL             	=   "internal";
	public static final String TRANSITION_ATTR_DEFAULT              	=   "default";
	public static final String TRANSITION_ATTR_TRAVERSE              	=   "traverseEvent";
	public static final String TRANSITION_ACTIONS_TAG_NAME          	=   "actions";
	public static final String TRANSITION_VALIDATION_EXC_ACTIONS_TAG_NAME   =   "validationExceptionActions";
	public static final String TRANSITION_VALIDATION_ACTIONS_TAG_NAME   =   "validationActions";

	//flow
	public static final String FLOW_TAG_NAME                        	=   "flow";
	public static final String FLOW_IMPLEMENTATIONS_TAG_NAME        	=   "flowImplementations";
	public static final String IMPLEMENTATION_TAG_NAME              	=   "implementation";

	public static final String FLOW_ATTR_FORMAT_CONTEXT_ON_INTERRUPT    =   "formatContextOnInterrupt";
	public static final String FLOW_ATTR_INDEPENDENT                    =   "independent";
	public static final String FLOW_ATTR_DEFAULT_INTERNAL_TRANSITIONS   =   "defaultInternalTransitions";
	public static final String FLOW_TYPE_GENERIC                        =   "generic";
	public static final String FLOW_TYPE_TAB                            =   "tabSystem";

	public static final String ACTIVITY_TAG_NAME                        =   "activityDefinition";


	//action
    public static final String ACTION_TYPE_SERVICE                  	=   "service";
	public static final String ACTION_TYPE_OPERATION                	=   "operation";
	public static final String ACTION_TYPE_ACTION_LIST             	    =   "actionList";

	public static final String ACTION_ATTR_REF_NAME           			=   "refName";
	public static final String ACTION_ATTR_METHOD_NAME					=   "methodName";

	//validationAction
	public static final String VALIDATION_ACTION_TAG_NAME               =   "validationAction";
	public static final String VALIDATION_ACTION_ATTR_STOP_ON_ERROR     =   "stopOnError";
	

	//guard
	public static final String GUARD_DEFINITION_TAG_NAME            	=   "guardDefinition";

	//buttons
	public static final String BUTTON_SET_TAG                       	=   "buttonSet";
	public static final String BUTTON_TAG                           	=   "button";
	
	public static final String BUTTON_ATTR_GROUP                    	=   "group";
	public static final String BUTTON_ATTR_EVENT_NAME                  	=   "eventName";
	public static final String BUTTON_ATTR_ACTIVITY_TYPE               	=   "activityType";
	public static final String BUTTON_ATTR_OPEN_WINDOW_TYPE         	=   "openWindowType";
	public static final String BUTTON_ATTR_WINDOW_EXTRA_PARAMS     		=   "windowExtraParams";
	public static final String BUTTON_ATTR_CHECK_DIRTY            		=   "checkDirtyFlag";
	public static final String BUTTON_ATTR_CHECK_WARNINGS          		=   "checkWarnings";
	public static final String BUTTON_ATTR_CHECK_DIRTY_IDS         		=   "checkDirtyModelIds";
	public static final String BUTTON_ATTR_ALLOW_EVENT_ON_DIRTY_IDS     =   "allowEventOnDirtyIds";
	public static final String BUTTON_ATTR_CONFIRM_MESSAGE_ID     		=   "confirmMessageId";
	public static final String BUTTON_ATTR_STATE_MODEL_ID         		=   "stateModelId";
	public static final String BUTTON_ATTR_SHORT_CUT_KEY         		=   "shortCutKey";
	

	// button types
	public static final String BUTTON_TYPE_BUTTON               		=   "button";
	public static final String BUTTON_TYPE_SPACER               		=   "spacer";

	// button openWindowType  
	public static final String BUTTON_OPEN_WINDOW_NORMAL          		=   "normal";
	public static final String BUTTON_OPEN_WINDOW_DIALOG          		=   "dialog";
	public static final String BUTTON_OPEN_WINDOW_POPUP          		=   "popup";
	public static final String BUTTON_OPEN_WINDOW_CLOSE_DIALOG     		=   "closeDialog";
	public static final String BUTTON_OPEN_WINDOW_CLOSE_POPUP          	=   "closePopup";
	

	// activity type for operation / service / activity
	public static final String ATTR_ACTIVITY_TYPE               		=   "activityType";

	// event / activity types
	public static final String ACTIVITY_TYPE_READONLY          			=   "readOnly";
	public static final String ACTIVITY_TYPE_READWRITE         			=   "readWrite";

	

}
