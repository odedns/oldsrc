/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: Context.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.math.BigDecimal;
import java.util.*;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.factory.FlowElementsFactoryException;
import com.ness.fw.flower.util.DynamicGlobals;


/**
 * <p>
 * Instances of <code>Context</code> interface implementations used to keep all business
 * data collected or generated during business process and {@link Service}s definitions.
 * </p>
 *
 * <p>
 * Instances of context interface implementations chained hierarchically to their parent context.
 * The parent context could be of type dynamic (the framework chain them while context construction according to the current flow/state/ transition context) or of type static, as declared in the context configuration file.
 * So usually every context has reference to other context as its parent, besides the root context (that must be of type static).
 * </p>
 *
 * <p>
 * There are two kinds of <code>Context</code> with same implementation -
 * <ol>
 * <li>Dynamic <code>Context</code>
 * <li>Static <code>Context</code>
 * </ol>
 * </p>
 */
public abstract class Context
{
	/**
	 * Constant for static <code>Context</code> type
	 */
	public static final int CONTEXT_TYPE_STATIC = 1;

	/**
	 * Constant for dynamic <code>Context</code> type
	 */
	public static final int CONTEXT_TYPE_DYNAMIC = 2;

	/**
	 * Used by framework to chain parent <code>Context</code> to the current <code>Context</code>.
	 * @param parentContext Parent <code>Context</code>.
	 */
	public abstract void setParent(Context parentContext);

	/**
	 * Used by framework to determine if any specific <code>Context</code> is defined for the
	 * <code>Context</code>. <br>If this method return <code>null</code> usually the 
	 * <code>Context</code> of the parent entity (eg. Flow or State) will be assigned as parent 
	 * <code>Context</code>.<br>
	 * If this method does not return <code>null</code>, a static <code>Context</code> will be
	 * retrieved from factory and assigned as parent <code>Context</code>.
	 * @return String Parent static context name.
	 */
	public abstract String getParentContextName();

	/**
	 * Returns the parent <code>Context</code> instance.
	 * @return Context Parent <code>Context</code>.
	 */
	public abstract Context getParentContext();

	/**
	 * Returns the root <code>Context</code> of the whole hierarchy.
	 * @return Context Root <code>Context</code>.
	 */
	public abstract Context getRootContext();

	/**
	 * Retruns the reference to the first dynamic context.
	 * @return Context
	 */
	public abstract Context getFirstDynamicContext();


	/**
	 * Set a value into a specific field.
	 * @param contextFieldName The name of the field to set the data.
	 * @param fieldData The data to be set.
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or the data does not match.
	 */
	public abstract void setField(String fieldName, Object fieldData) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Used to set field into context
	 *
	 * @param contextFieldName the name of the field to set data
	 * @param fieldData the data to be set
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	protected abstract void setField(String fieldName, Object fieldData, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException;


	/**
	 * Used to retrieve field data. 
	 * If a mask was supplied, return the value formatted, otherwise return the value 
	 * of the field as an unformatted string. 
	 *
	 * @param fieldName The name of the field to retrieve the data.
	 * @param mask optionally mask to format the field data. If <code>null</code>, 
	 * unformatted value will be returned.
	 * @return Object The field data.
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	public abstract String getFieldValue(String fieldName, String maskKey) throws ContextException;


	/**
	 * Returns the value of a specific field from the context as is, without formatting the value.
	 * @param contextFieldName The name of the field.
	 * @return Object The field data.
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	public abstract Object getField(String fieldName) throws ContextException;

	/**
	 * Returns the value of a specific field from the context, after formatting the value 
	 * according to the default mask declared for the type.
	 * Uses <code>getFormattedFieldValue(String contextFieldName, String mask)</code> with <code>null</code> 
	 * for the mask.
	 * @param contextFieldName The name of the field.
	 * @return String The formatted field data.
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> 
	 * or could not format the value
	 */
	public abstract String getFormattedFieldValue(String fieldName) throws ContextException;

	/**
	 * Returns the value of a specific field from the context, after formatting the value 
	 * according to the given mask.
	 * Uses of <code>getFormattedValue</code> method of <code>FlowerType</code> interface
	 * with specific mask (fully dependent of field data implementation).
	 * If field data does not implement such interface - <code>toString</code> method of 
	 * field data object is used.
	 *
	 * @param contextFieldName The name of the field.
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return String The formatted field data.
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> 
	 * or could not format the value
	 */
	public abstract String getFormattedFieldValue(String fieldName, String maskKey) throws ContextException;

	/**
	 * Returns the name of the <code>Context</code>.
	 * @return String name
	 */
	public abstract String getName();

	/**
	 * Returns the service instance defined in the <code>Context</code> according to the service name.
	 * @param serviceName The name of the service in the <code>Context</code>
	 * @return Service instance
	 * @throws ContextException thrown when service is not defined in the <code>Context</code>
	 */
    protected abstract Service getService(String serviceName) throws ContextException, FatalException;

	/**
	 * Execute a service method.
	 * The service should be declared iun the current context or in one of his parnet contexts. 
	 * @param serviceName The name of service instance in the <code>Context</code>.
	 * @param methodName The method to execute in the service. 
	 * @throws ServiceException
	 * @throws ContextException
	 * @throws ValidationException
	 */
	public abstract void executeServiceMethod(ServiceMethodExecutionData methodExceutionData) throws ServiceException, ContextException, ValidationException;

	/**
	 * Used to add a {@link Service} definition to the current <code>Context</code> at runtime.
	 * @param Service The service to be added.
	 * @param serviceName The name that will be associated with the {@link Service} instance.
	 */
    public abstract void addService(Service service, String serviceName);

	/**
	 * Set parameter into the context for global (user wide) use. 
	 * Specific prefix is added to the field name to distinguish between global 
	 * parameters and regular fields. <br>
	 * Global parameters can be retrieved using <code>getDynamicGlobalParameter</code> method.
	 * <br>
	 * Physically such parameters are stored in the upper dynamic context.
	 *
	 * @param contextFieldName The name of the field in the context.
	 * @param value The value of the field to set in the context.
	 */
	public abstract void setDynamicGlobalParameter(String fieldName, Object value);

	/**
	 * Used to set parameters into context for global (user wide) use. Specific preffix is added to each field name
	 * to distinguish between global parameters and regular fields.
	 * <br>
	 * Global parameters can be retrieved using <code>getDynamicGlobalParameter</code> method.
	 * <br>
	 * Physically such parameters are stored in the upper dynamic context.
	 *
	 * @param contextFieldName the name of the field in the context
	 * @param value the value of the field in the context
	 */
	protected abstract void setDynamicGlobalParameterInner(String fieldName, Object value);

	/**
	 * Returns the value of a global parameter stored by <code>setDynamicGlobalParameter</code> method.
	 * @param contextFieldName The name of field.
	 * @return Object The value of the parameter or <code>null</code> if the parameter was not set.
	 */
	public abstract Object getDynamicGlobalParameter(String fieldName);

	/**
	 * Returns the value of a global parameter stored by <code>setDynamicGlobalParameter</code> method.
	 * @param contextFieldName The name of field.
	 * @return Object The value of the parameter or <code>null</code> if the parameter was not set.
	 */
	protected abstract Object getDynamicGlobalParameterInner(String fieldName);

	/**
	 * Removes the global parameter stored by <code>setDynamicGlobalParameter</code> method.
	 * @param contextFieldName The name of the field to remove.
	 * @return Object The previous value of the parameter or <code>null</code> if the parameter was not set.
	 */
	public abstract Object removeDynamicGlobalParameter(String fieldName);

	/**
	 * Used to remove parameters stored by <code>setDynamicGlobalParameter</code>
	 * @param contextFieldName the name of field to remove
	 * @return the value of parameter or <code>null</code> if not set.
	 */
	protected abstract Object removeDynamicGlobalParameterInner(String fieldName);

	/**
	 * Indicates if the context defined as static.
	 * @return boolean True if the context defined as static.
	 */
	public abstract boolean isStatic();

	/**
	 * Returns the value of a specific context field as a <code>java.util.Date</code>.
	 * @param contextFieldName The name of the field.
	 * @return java.util.Date Field data.
	 * @throws ContextException
	 */
	public abstract Date getDate(String fieldName) throws ContextException;

	/**
	 * Returns the value of a specific context field as a <code>java.lang.String</code>.
	 * @param contextFieldName The name of the field.
	 * @return String Field data.
	 * @throws ContextException
	 */
	public abstract String getString(String fieldName) throws ContextException;
	
	/**
	 * Returns the value of a specific context field as a <code>int</code>.
	 * @param contextFieldName The name of the field.
	 * @return int Field data.
	 * @throws ContextException
	 */
	public abstract int getInt(String fieldName) throws ContextException;
	
	/**
	 * Returns the value of a specific context field as a <code>java.lang.Boolean</code>.
	 * @param contextFieldName The name of the field.
	 * @return Boolean Field data.
	 * @throws ContextException
	 */
	public abstract Boolean getBoolean(String fieldName) throws ContextException;

	/**
	 * Returns the value of a specific context field as a <code>java.util.Integer</code>.
	 * @param contextFieldName The name of the field.
	 * @return Integer Field data.
	 * @throws ContextException
	 */
	public abstract Integer getInteger(String fieldName) throws ContextException;

	/**
	 * Returns the value of a specific context field as a <code>java.util.Long</code>.
	 * @param contextFieldName The name of the field.
	 * @return Long Field data.
	 * @throws ContextException
	 */
	public abstract Long getLong(String fieldName) throws ContextException;

	/**
	 * Set an <code>int</code> value into a specific field.
	 * @param contextFieldName The name of the field to set the data.
	 * @param fieldData The <code>int</code> value to be set in the field.
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> 
	 * or the data does not match.
	 */
	public abstract void setField(String fieldName, int fieldData) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Set an <code>java.lang.Boolean</code> value into a specific field.
	 * @param contextFieldName The name of the field to set the data.
	 * @param fieldData The <code>java.lang.Boolean</code> value to be set in the field.
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> 
	 * or the data does not match.
	 */
	public abstract void setField(String fieldName, Boolean fieldData) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Used to create {@link Iterator} of the existing context field’s names, that was initialized in the context.
	 * @return Iterator
	 */
	public abstract Iterator getFieldNamesIterator();


	/**
	 * Used to set data into a plain field (not of structure type).
	 * For internal use only.
	 * @param contextFieldName The name of the field to set the data.
	 * @param fieldData The value to be set in the field.
	 * @param ignoreAccess If called from parent context, this parameter should be set to false.
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	abstract void setFieldPlain(String fieldName, Object fieldData, boolean ignoreAccess, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Used to set data into a structure field.
	 * For internal use only.
	 * @param contextFieldName The name of the structure field.
	 * @param structureFieldName The name of the field inside the structure.
	 * @param fieldData The value to be set in the field.
	 * @param ignoreAccess If called from parent context, this parameter should be set to false.
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	abstract void setFieldToStructure(String fieldName, String structureFieldName, Object fieldData, boolean ignoreAccess, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Returns the data of a plain field (not of structure type).
	 * When the "formatted" parameter will set to true, the returned value will be 
	 * formatted according to maskKey parameter .
	 * For internal use only.
	 * @param contextFieldName The name of the field.
	 * @param formatted Indicates if the returned value should be formatted.  
	 * @param maskKey The key of the mask used to format the data. The mask will be taken from the {@link LocalizedResources} object.
	 * @return Object The Field data.   
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	abstract Object getFieldPlain(String fieldName, boolean formatted, String maskKey, boolean ignoreAccess) throws ContextException;

	/**
	 * Returns the data of a structure field.
	 * When the "formatted" parameter will set to true, the returned value will be 
	 * formatted according to maskKey parameter .
	 * For internal use only.
	 * @param contextFieldName The name of the structure field.
	 * @param structureFieldName The name of the field inside the structure.
	 * @param formatted Indicates if the returned value should be formatted.  
	 * @param maskKey The key of the mask used to format the data. The mask will be taken from the {@link LocalizedResources} object.
	 * @return Object The Field data.   
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	abstract Object getFieldFromStructure(String fieldName, String structureFieldName, boolean formatted, String maskKey, boolean ignoreAccess) throws ContextException;

	/**
	 * Returns the DynamicGlobals object from the upper dynamic context.
	 * @return DynamicGlobals
	 */
	public abstract DynamicGlobals  getDynamicGlobals ();
	
	/**
	 * Sets the DynamicGlobals object in the upper dynamic context.
	 * @param DynamicGlobals 
	 */
	public abstract void setDynamicGlobals (DynamicGlobals dynamicGlobals);

	/**
	 * Used to add a new field dynamically to the context.
	 * @param DynamicField The dynamic field attributes
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public abstract void addDynamicField (DynamicField dynamicField) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Used to add a new structure field dynamically to the context.
	 * The Structure definition is declared in the DynamicStructure and not in the XML files.
	 * @param dynamicStructure The dynamic structure field attributes
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public abstract void addDynamicStructure (DynamicStructure dynamicStructure) throws ContextException, FatalException, AuthorizationException;


	/**
	 * Used to add a new ContextElementSet dynamically to the context.
	 * @param contextElementSetDefName The ContextElementSet defintion name.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 * @throws FlowElementsFactoryException
	 * @throws ServiceException
	 */
	public abstract void addDynamicContextElementSet (String contextElementSetDefName) throws ContextException, FatalException, AuthorizationException, FlowElementsFactoryException, ServiceException;

	/* 
	 * Start: XIData interface methods 
	 * for types that recieve data from external source i.e Http event, Web service
	 */

	/**
	 * Returns the data of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Object The value of the field as an Object. 
	 */
	public abstract Object getXIData(String fieldName) throws ContextException;

	/**
	 * Sets an Object value into field of type XIData. 
	 * @param fieldName The field name to set.
	 * @param Object The field value to set.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public abstract void setXIData(String fieldName, Object fieldValue) throws ContextException, FatalException, AuthorizationException;

	/**
	 * Returns the Integer value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Integer The value of the field as an Integer. 
	 */
	public abstract Integer getXIInteger(String fieldName) throws ContextException;

	/**
	 * Returns the Double value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Double The value of the field as an Double. 
	 */
	public abstract Double getXIDouble(String fieldName) throws ContextException;

	/**
	 * Returns the Long value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Long The value of the field as an Long. 
	 */
	public abstract Long getXILong(String fieldName) throws ContextException;

	/**
	 * Returns the BigDecimal value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return BigDecimal The value of the field as an BigDecimal. 
	 */
	public abstract BigDecimal getXIBigDecimal(String fieldName) throws ContextException;

	/**
	 * Returns the Date value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Date The value of the field as an Date. 
	 */
	public abstract Date getXIDate(String fieldName) throws ContextException;

	/**
	 * Returns the String value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return String The value of the field as an String. 
	 */
	public abstract String getXIString(String fieldName) throws ContextException;

	/* 
	 * End: XIData interface methods 
	 */

	/**
	 * Returns the field definition.
	 * @param fieldName
	 * @param ignoreAccess
	 * @return ContextField
	 * @throws ContextException
	 */
	protected abstract ContextField getFieldDefinition(String fieldName, boolean ignoreAccess) throws ContextException;

	/**
	 * Returns the field definition.
	 * @param fieldName
	 * @return ContextField
	 * @throws ContextException
	 */
	protected abstract ContextField getFieldDefinition(String fieldName) throws ContextException;

	/**
	 * Release the resources.
	 */
	abstract void destroy();
}
