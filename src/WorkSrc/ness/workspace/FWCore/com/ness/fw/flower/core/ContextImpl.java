/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextImpl.java,v 1.3 2005/04/12 13:43:59 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.factory.*;
import com.ness.fw.flower.factory.externalization.ContextExternalizer;
import com.ness.fw.flower.factory.externalization.ExternalizerConstants;
import com.ness.fw.flower.factory.externalization.ExternalizerInitializationException;
import com.ness.fw.flower.factory.externalization.ExternalizerNotInitializedException;
import com.ness.fw.flower.util.*;
import com.ness.fw.shared.common.SystemUtil;
import com.ness.fw.shared.common.XIData;
import com.ness.fw.shared.common.XIDataFactory;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.*;

import java.util.*; 
import java.lang.reflect.*;
import java.math.BigDecimal;

/**
 * Implementation for {@link Context} interface.
 */
public class ContextImpl extends Context
{
	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "CONTEXT";
	public static final String GLOBAL_PARAMETERS_PREFIX = "global_";
	private static final String DYNAMIC_GLOBALS = "dynamicGlobals";

	ContextPeer peer;

	/**
	 * Hash for all fields data
	 */
	private HashMap data;

	/**
	 * Holds the <code>Context</code> dynamic fields & services declarations.
	 */
	private ContextMetaData dynamicMetaData = null;

	/**
	 * parent context
	 */
	private Context parent;

	/**
	 * the name of the context
	 */
	private String name;

	/**
	 * Hash for all services
	 */
	private HashMap services;

	/**
	 * A reference to the first dynamic context
	 */
	private Context firstDynamicContext;
	
	/**
	 * Used to synchronize all acces to data fields
	 */
	private Object syncObject = new Object();

	public ContextImpl(ContextPeer peer) throws ContextException, FatalException, AuthorizationException
	{
		this(peer, null, null, null);
	}

	public ContextImpl(ContextPeer peer, Context parent, Context firstDynamicContext, DynamicGlobals dynamicGlobals) throws ContextException, FatalException, AuthorizationException
	{
		this.peer = peer;
		this.name = peer.getContextName();
		this.parent = parent;
		this.firstDynamicContext = firstDynamicContext;
		if(firstDynamicContext == null && parent != null && parent.isStatic())
		{
			this.firstDynamicContext = this;
		}

		data = new HashMap();
		services = new HashMap();

		if(dynamicGlobals != null)
		{
			setDynamicGlobals(dynamicGlobals);
		}

		//if peer contains default values definition - process default values
		if (peer.isDefaultValuesPresents())
		{
			processDefaultValues();
		}
		Logger.debug(LOGGER_CONTEXT, toString());

		try
		{
			//creating services instances
			initServices(peer.getMetaData());
		} 
		catch (Throwable ex)
		{
			throw new ContextException("Unable to initialize services for context [" + name + "]", ex);
		}
	}

	public void destroy()
	{
		destroyServices();
	}

	

	/**
	 * Used by framework to chain parent <code>Context</code> to the current <code>Context</code>.
	 * @param parentContext Parent <code>Context</code>.
	 */
	public void setParent(Context parentContext)
	{
		this.parent = parentContext;
	}

	/**
	 * Used by framework to determine if any specific <code>Context</code> is defined for the
	 * <code>Context</code>. <br>If this method return <code>null</code> usually the 
	 * <code>Context</code> of the parent entity (eg. Flow or State) will be assigned as parent 
	 * <code>Context</code>.<br>
	 * If this method does not return <code>null</code>, a static <code>Context</code> will be
	 * retrieved from factory and assigned as parent <code>Context</code>.
	 * @return String Parent static context name.
	 */
	public String getParentContextName()
	{
		return peer.getParentContextName();
	}

	/**
	 * Retruns the reference to the first dynamic context.
	 * @return Context
	 */
	public Context getFirstDynamicContext()
	{
		return firstDynamicContext;
	}

	/**
	 * Used to set field into context
	 *
	 * @param contextFieldName the name of the field to set data
	 * @param fieldData the data to be set
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	public void setField(String fieldName, Object fieldData) throws ContextException, FatalException, AuthorizationException
	{
		setField(fieldName, fieldData, true);
	}

	/**
	 * Used to set field into context
	 *
	 * @param contextFieldName the name of the field to set data
	 * @param fieldData the data to be set
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException thrown when field is not defined for the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	protected void setField(String fieldName, Object fieldData, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "] given data [" + (fieldData == null ? fieldData : fieldData.toString()) + "]");

		synchronized (syncObject)
		{
			if (fieldName.indexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR) != -1)
			{
				setFieldToStructure(fieldName, fieldData, true, allowNonUITypes);
			}
			else
			{
				setFieldPlain(fieldName, fieldData, true, allowNonUITypes);
			}
		}
	}

	/**
	 * Used to set field to structure
	 *
	 * @param contextFieldName the name of field to set (including the <code>ContextStructureField.STRUCTURE_NAME_SEPARATOR</code>)
	 * @param fieldData data of field
	 * @param ignoreAccess if called from parent context this parameter should be set to false
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException
	 */
	private void setFieldToStructure(String fieldName, Object fieldData, boolean ignoreAccess, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		int indexOfDelim = fieldName.indexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR);
		if (indexOfDelim != fieldName.lastIndexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR))
		{
			throw new IncompatibleFieldTypeException("Unable to set field [" + fieldName + "] to context [" + name + "]. Nested structures are not supported");
		}

		//the name of field in the structure
		String structureFieldName = fieldName.substring(indexOfDelim + 1);

		//the name of field in the context
		fieldName = fieldName.substring(0, indexOfDelim);

		setFieldToStructure(fieldName, structureFieldName, fieldData, ignoreAccess, allowNonUITypes);
	}

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
	void setFieldToStructure(String fieldName, String structureFieldName, Object fieldData, boolean ignoreAccess, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		ContextField field = peer.getField(fieldName, dynamicMetaData);
		if (field == null)
		{
			ContextStructure structure = (ContextStructure) data.get(fieldName);
			if (structure != null)
			{
				structure.setField(structureFieldName, fieldData, allowNonUITypes);
			}
			else if (parent != null)
			{
				Logger.debug(LOGGER_CONTEXT, "Set pass to context [" + parent.getName() + "] field [" + fieldName + "] value [" + (fieldData == null ? fieldData : fieldData.toString()) + "]");
				parent.setFieldToStructure(fieldName, structureFieldName, fieldData, false, allowNonUITypes);
				return;
			}
			else
			{
				throw new FieldNotFoundException("Unable to set field. Field with name [" + fieldName + "] not found in context [" + name + "] and parent context is null");
			}
		}
		else
		{
			if (!ignoreAccess && field.getAccess() != ContextField.CHILD_ACCESS_FULL)
			{
				throw new FieldAccessViolationException("Unable to set field. The field with name [" + fieldName + "] in parent context does not have access modifier [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_FULL + "]");
			}

			if (field.getType().getType() != ContextFieldType.STRUCTURE_TYPE)
			{
				throw new FieldNotFoundException ("Unable to set field. Field with name [" + fieldName + "]to context [" + name + "]. Attempt to assign structure element to non structure field");
			}

			ContextStructure structure = (ContextStructure) data.get(fieldName);

			if (structure == null)
			{
				structure = new ContextStructure(((StructureContextFieldType)field.getType()).getStructureDefinition(), this);
				data.put(fieldName, structure);
			}
			
			structure.setField(structureFieldName, fieldData, allowNonUITypes);
		}
	}

	/**
	 * Used to set data into a plain field (not of structure type).
	 * For internal use only.
	 * @param contextFieldName The name of the field to set the data.
	 * @param fieldData The value to be set in the field.
	 * @param ignoreAccess If called from parent context, this parameter should be set to false.
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	void setFieldPlain(String fieldName, Object fieldData, boolean ignoreAccess, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		ContextField field = peer.getField(fieldName, dynamicMetaData);
		if (field == null) //no field with such name defined in the context
		{
			if (peer.isAddOnDemand())
			{
				//if add on demand is allowed the field will be added anyway
				if (fieldData == null)
				{
					data.remove(fieldName);
				}
				else
				{
					Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "] actual data [" + fieldData + "]");
					data.put(fieldName, fieldData);
				}
			}
			else
			{
				//pass the set method to parent context (if exists)
				if (parent != null)
				{
					Logger.debug(LOGGER_CONTEXT, "Set pass to context [" + parent.getName() + "] field [" + fieldName + "] value [" + (fieldData == null ? fieldData : fieldData.toString()) + "]");
					try
					{
						parent.setFieldPlain(fieldName, fieldData, false, allowNonUITypes);
					}
					catch (FieldAccessViolationException ex)
					{
						throw new FieldAccessViolationException("Unable to set field. The field with name [" + fieldName + "] in parent context does not have access modifier [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_FULL + "]", ex);
					}
					catch (FieldNotFoundException ex)
					{
						throw new FieldNotFoundException("Unable to set field to context [" + name + "]. Field with name [" + fieldName + "] not found in context [" + name + "] and nor in parent context hierarchy. \n The context hierarchy is :" + getContextHierarchy());
					}
					catch (IncompatibleFieldTypeException ex)
					{
						throw new IncompatibleFieldTypeException("Unable to set field to context [" + name + "]. The type [" + (fieldData == null ? "unknown" : fieldData.getClass().getName()) + "] for field with name [" + fieldName + "] in parent context is incompatible." ,ex);
					}
				}
				else
				{
					throw new FieldNotFoundException("Unable to set field. Field with name [" + fieldName + "] not found in context [" + name + "] and parent context is null");
				}
			}
		}
		else //setting field data
		{
			//security modifyer checking (if readOnly - can not be changed by child)
			if (ignoreAccess || field.getAccess() == ContextField.CHILD_ACCESS_FULL)
			{
				if(!allowNonUITypes && !field.isXIType())
				{
					Logger.error(LOGGER_CONTEXT, "Unable to set parameter [" + fieldName + "] into context. Redundant parameter that is not " + ExternalizerConstants.TYPE_ATTR_IS_XI_TYPE + "=true");
				}
				else
				{
					if(data.get(fieldName) == null && field.isXIType())
					{
						//XIData xiData = XIDataFactory.createXIData(field.getType().getTypeName());
						// baruch 
						XIData xiData = XIDataFactory.createXIData(field.getType().getBasicXITypeName());
						data.put(fieldName, xiData);
					}
					performSet(field, fieldData, fieldName, allowNonUITypes);
				}
			}
			else
			{
				throw new FieldAccessViolationException("Unable to set field. The field with name [" + fieldName + "] in context [" + name + "] does not have access modifier [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_FULL + "]");
			}
		}
	}

	/**
	 * Used to perform actual set of field data
	 *
	 * @param contextFieldName the name of field to set
	 * @param fieldData data of field
	 * @param ignoreAccess if called from parent context this parameter should be set to false
	 * @param allowNonUITypes
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> or the data type
	 * does not match or security violation
	 */
	private void performSet(ContextField field, Object fieldData, String fieldName, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		ContextFieldType fieldType = field.getType();
		Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in context [" + getName() + "]. given data [" + fieldData + "]");
		if (fieldData == null)
		{
			Object currentData = data.get(fieldName);
			if(fieldType.isXIType() && currentData != null)
			{
				boolean setData = XIDataFactory.setDataToXIObject(fieldType.getTypeName(), currentData, fieldData);
				if(!setData)
				{
					data.remove(fieldName);
					Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "]. actual data [null]");
				}
			}
			else
			{
				data.remove(fieldName);
			}
		}
		else if (((fieldType.getType() == ContextFieldType.CLASS_TYPE) && ((ClassContextFieldType)field.getType()).getTypeClass().isInstance(fieldData)))
		{
			data.put(fieldName, fieldData);
			Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "]. actual data [" + fieldData + "]");
		}
		else if (fieldType.getType() == ContextFieldType.STRUCTURE_TYPE)
		{
			data.put(fieldName, ((ContextStructure)fieldData).copy(this));
			Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "]. actual data [" + fieldData + "]");
		}
		else
		{
			try
			{
				Class typeClass = ((ClassContextFieldType)fieldType).getTypeClass();
				Object fieldObject = null;
				Class componentClass = typeClass.getComponentType();
				Object componentObject;
				if (componentClass != null)
				{
					if (componentClass.isInstance(fieldData))
					{
						componentObject = fieldData;
					}
					else
					{
						throw new IncompatibleFieldTypeException("Unable to set field. The type  [" + ((ClassContextFieldType)field.getType()).getTypeClass().getName() + "] of field with name [" + fieldName + "] in context [" + name + "] is incompatible with [" + (fieldData == null ? "unknown" : fieldData.getClass().getName()) + "]");
					}

					fieldObject = Array.newInstance(typeClass.getComponentType(), 1);
					Array.set(fieldObject, 0, componentObject);

				}
				else
				{

					ContextFieldBuilderBundle builderBundle = ((ClassContextFieldType)fieldType).getBuilderBundle(fieldData);
					if(builderBundle != null)
					{
						Object currentFieldData = data.get(fieldName);
						if(currentFieldData == null && fieldType.isXIType())
						{
							//currentFieldData = XIDataFactory.createXIData(fieldType.getTypeName());
							// baruch
							currentFieldData = XIDataFactory.createXIData(fieldType.getBasicXITypeName());
						}
						
						ContextFieldBuilderParams builderParams = new ContextFieldBuilderParams(
							ApplicationUtil.getGlobalMessageContainer(this), 
							ApplicationUtil.getLocalizedResources(this), 
							!allowNonUITypes);
						fieldObject = builderBundle.build(builderParams, currentFieldData, fieldData);
					}
					else
					{
						throw new IncompatibleFieldTypeException("Unable to set field. The type  [" + ((ClassContextFieldType)field.getType()).getTypeClass().getName() + "] of field with name [" + fieldName + "] in context [" + name + "] is incompatible with [" + (fieldData == null ? "unknown" : fieldData.getClass().getName()) + "] and no appropriate builder was found");
					}
				}

				if (fieldObject == null)
				{
					data.remove(fieldName);
					Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "]. actual data [null]");
				}
				else
				{
					data.put(fieldName,  fieldObject);
					Logger.debug(LOGGER_CONTEXT, "Set to context [" + getName() + "] field [" + fieldName + "]. actual data [" + fieldData + "]");
				}
			}
			catch (InvocationTargetException ex)
			{
				if (ex.getTargetException() instanceof ContextFieldConstructionException)
				{
					throw (ContextFieldConstructionException)ex.getTargetException();
				}
				else if (ex.getTargetException() instanceof AuthorizationException)
				{
					throw (AuthorizationException)ex.getTargetException();
				}
				else
				{
					throw new FatalException("Unable to construct field. The constructor of field with name [" + fieldName + "] in context [" + name + "]" + SystemUtil.convertThrowable2String(ex.getTargetException()));
				}
			}
			catch (IllegalAccessException ex)
			{
				throw new FatalException("Unable to construct field. The constructor of field with name [" + fieldName + "] in context [" + name + "]" + SystemUtil.convertThrowable2String(ex));
			}
			catch (ContextException ex)
			{
				throw ex;
			}
		}
	}

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
	public String getFieldValue(String fieldName, String maskKey) throws ContextException
	{
		String formattedValue = null;
		
		if(maskKey != null)
		{
			formattedValue = getFormattedFieldValue(fieldName, maskKey);			
		}
		else
		{
			Object fieldValue = getField(fieldName);
			if(fieldValue != null && fieldValue instanceof XIData)
			{
				fieldValue = ((XIData)fieldValue).getData();
			}
			
			if(fieldValue != null)
			{
				formattedValue = fieldValue.toString();
			}
		}
		
		// baruch 30/11/04. callers will do that check
		//		if(formattedValue == null)
		//		{
		//			formattedValue = "";
		//		}
		
		return formattedValue;
	}


	/**
	 * Used to retrieve field data.
	 *
	 * @param fieldName The name of the field to retrieve the data.
	 * @return Object The field data
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	public Object getField(String fieldName) throws ContextException
	{
		return getField(fieldName, true);
	}
	
	/**
	 * Used to retrieve field data.
	 *
	 * @param contextFieldName name of field tht's data to be retrieved
	 * @return the field data
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	private Object getField(String fieldName, boolean ignoreAccess) throws ContextException
	{
	 	synchronized(syncObject)
	 	{
			 if (fieldName.indexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR) != -1)
			 {
				 return getFieldFromStructure(fieldName, false, null, ignoreAccess);
			 }
			 else
			 {
				 return getFieldPlain(fieldName, false, null, ignoreAccess);
			 }
		 }
	}

	/**
	 * Used to retrieve formatted value of field data using <code>FlowerType</code> interface. If field data does not implement such interface
	 * <code>toString</code> methos of field data is used.
	 *
	 * @param contextFieldName name of field
	 * @param mask optionally mask to format the field data. If <code>null</code> default mask will be used
	 * @return formatted string representation of the field data
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	public String getFormattedFieldValue(String fieldName, String mask) throws ContextException
	{
		return getFormattedFieldValue(fieldName, mask, true);
	}

	/**
	 * Used to retrieve formatted value of field data using <code>FlowerType</code> interface. If field data does not implement such interface
	 * <code>toString</code> methos of field data is used.
	 *
	 * @param contextFieldName name of field
	 * @param mask optionally mask to format the field data. If <code>null</code> default mask will be used
	 * @return formatted string representation of the field data
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	private String getFormattedFieldValue(String fieldName, String mask, boolean ignoreAccess) throws ContextException
	{
		String result = null;
		synchronized(syncObject)
		{
			if (fieldName.indexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR) != -1)
			{
				result = (String) getFieldFromStructure(fieldName, true, mask, ignoreAccess);
			}
			else
			{
				result = (String) getFieldPlain(fieldName, true, mask, ignoreAccess);
			}
		}
		
		if(result == null)
		{
			result = "";
		}
		return result;
	}

	/**
	 * Used to retrieve formatted value of field data using <code>FlowerType</code> interface. If field data does not implement such interface
	 * <code>toString</code> methos of field data is used. Passes <code>null</code> as mask
	 *
	 * @param contextFieldName name of field
	 * @return formatted string representation of the field data
	 * @throws ContextException thrown when field is not defined in the <code>Context</code> 
	 * or security violation
	 */
	public String getFormattedFieldValue(String fieldName) throws ContextException
	{
		return getFormattedFieldValue(fieldName,  null);
	}

	/**
	 * For internal use only
	 */
	Object getFieldFromStructure(String fieldName, boolean formatted, String formatParam, boolean ignoreAccess) throws ContextException
	{
		int indexOfDelim = fieldName.indexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR);
		if (indexOfDelim != fieldName.lastIndexOf(ContextStructureField.STRUCTURE_NAME_SEPARATOR))
		{
			throw new IncompatibleFieldTypeException("Unable to get field [" + fieldName + "] from context [" + name + "]. Nested structures are not supported");
		}

		//the name of field in the structure
		String structureFieldName = fieldName.substring(indexOfDelim + 1);

		//the name of field in the context
		fieldName = fieldName.substring(0, indexOfDelim);

		return getFieldFromStructure(fieldName, structureFieldName, formatted, formatParam, ignoreAccess);
	}

	/**
	 * For internal use only
	 */
	Object getFieldFromStructure(String fieldName, String structureFieldName, boolean formatted, String formatParam, boolean ignoreAccess) throws ContextException
	{
		ContextStructure structure = null;
		
		//if the field data not set - checking is field defined for the context
		ContextField field = peer.getField(fieldName, dynamicMetaData);
		
		if(!ignoreAccess)
		{
			if(field != null && field.getAccess() == ContextField.CHILD_ACCESS_NONE)
			{
				throw new FieldAccessViolationException("Unable to get structure field value. Field with name [" + fieldName + "] in parent context [" + name + "] has child access [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE + "]");				
			}
		}
		
		try{
			structure = (ContextStructure) data.get(fieldName);
		}catch (ClassCastException ex)
		{
			throw new FieldNotFoundException ("Unable to get field. Field with name [" + fieldName + "] from context [" + name + "]. Attempt to get structure element from non structure field");
		}

		if (structure == null)
		{
			if (field == null)
			{
				if (parent != null)
				{
					return parent.getFieldFromStructure(fieldName, structureFieldName, formatted, formatParam, false);
				}
				else
				{
					throw new FieldNotFoundException("Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "] and parent context is null");
				}
			}
			else
			{
				return null;
			}
		}
		else
		{
			return structure.getField(structureFieldName, formatted, formatParam);
		}
	}


	/**
	 * For internal use only
	 */
	Object getFieldPlain(String fieldName, boolean formatted, String maskKey, boolean ignoreAccess) throws ContextException
	{
		//if the field data not set - checking is field defined for the context
		ContextField field = peer.getField(fieldName, dynamicMetaData);
		
		if(!ignoreAccess)
		{
			if(field != null && field.getAccess() == ContextField.CHILD_ACCESS_NONE)
			{
				throw new FieldAccessViolationException("Unable to get field value. Field with name [" + fieldName + "] in parent context [" + name + "] has child access [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE + "]");				
			}
		}
		
		
		//if field data already set - return it
		Object fieldData = data.get(fieldName);
		if (fieldData != null)
		{
			if (formatted)
			{
				ContextFieldPrinterBundle bundle = field == null ? null : ((ClassContextFieldType)field.getType()).getPrinterBundle();
				if (bundle != null)
				{
					try
					{
						return bundle.print(ApplicationUtil.getLocalizedResources(this), fieldData, maskKey);
					} catch (Throwable ex)
					{
						throw new ContextException("Unable to get formatted value for field. Field with name [" + fieldName + "] in context [" + name + "]", ex);
					}
				}
				else
				{
					return fieldData.toString();
				}
			}
			else
			{
				return fieldData;
			}
		}
		else
		{

			if (field == null)
			{
				//passing get to parent
				if (parent != null)
				{
					try{
						return parent.getFieldPlain(fieldName, formatted, maskKey, false);
					}catch (FieldAccessViolationException ex)
					{
						throw new FieldAccessViolationException("Unable to get field. The field with name [" + fieldName + "] in parent context has access modifier [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE + "]", ex);
					}catch (ContextException ex)
					{
						if (peer.isAddOnDemand())
						{
							return null;
						}
						else
						{
							Logger.debug(LOGGER_CONTEXT, "Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "], parent context is [" + parent.getName() + "]");
							throw new ContextException("Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "] and nor in parent context");
						}
					}
				}
				else
				{
					if (peer.isAddOnDemand())
					{
						return null;
					}
					else
					{
						Logger.debug(LOGGER_CONTEXT, "Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "], parent context is null");
						throw new ContextException("Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "] and parent context is null");
					}
				}
			}
			else
			{
				if(field.isXIType())
				{
					//fieldData = XIDataFactory.createXIData(field.getType().getTypeName());
					// baruch
					fieldData = XIDataFactory.createXIData(field.getType().getBasicXITypeName());
					Logger.debug(LOGGER_CONTEXT, "create new XIData " + fieldData + " to field " + fieldName);
					data.put(fieldName, fieldData);
				}
				
				//if field is defined for context but not set - return null
				if (formatted)
				{
					ContextFieldPrinterBundle bundle = field == null ? null : ((ClassContextFieldType)field.getType()).getPrinterBundle();
					if (bundle != null)
					{
						try
						{
							return bundle.print(ApplicationUtil.getLocalizedResources(this), fieldData, maskKey);
						} catch (Throwable ex)
						{
							Logger.debug(LOGGER_CONTEXT, "Unable to get formatted value for field. Field with name [" + fieldName + "] in context [" + name + "]");
							throw new ContextException("Unable to get formatted value for field. Field with name [" + fieldName + "] in context [" + name + "]");
						}
					}
					else
					{
						return fieldData;
					}
				}
				else
				{
					return fieldData;
				}
			}
		}
	}

	public String getName()
	{
		return name;
	}

	/**
	 * Execute a service method.
	 * The service should be declared iun the current context or in one of his parnet contexts. 
	 * @param methodExecutionData Data for executing specific service method.  
	 * @throws ServiceException
	 * @throws ContextException
	 * @throws ValidationException
	 */
	public void executeServiceMethod(ServiceMethodExecutionData methodExecutionData) throws ServiceException, ContextException, ValidationException
	{
		try
		{
			Service service = getService(methodExecutionData.getServiceName());
			service.executeMethod(this, methodExecutionData);
		}
		catch (ContextException e)
		{
			throw e;
		}
		catch (ServiceException e)
		{
			throw e;
		}
		catch (ValidationException e)
		{
			throw e;
		}
		catch (GeneralException e)
		{
			throw new ServiceException("service method execution failed. serviceName [" + methodExecutionData.getServiceName() + "] methodName [" + methodExecutionData.getMethodName() + "]", e);
		}
	}


	/**
	 * Used to retrieve service instance defined in the <code>Context</code>
	 *
	 * @param serviceName name of service instance in the <code>Context</code>
	 * @return <code>Service</code> instance
	 * @throws ContextException thrown when service is not defined in the <code>Context</code>
	 */
	protected Service getService(String serviceName) throws ContextException, FatalException
	{
		Service service;

		synchronized (syncObject)
		{
			service = (Service) services.get(serviceName);
		}

		//if service is not defined for the context try to find such definition at parent context
		if (service == null)
		{
			if (parent != null)
			{
				service = parent.getService(serviceName);
			}
			else
			{
				throw new ContextException("Unable to supply service. No service found for name [" + serviceName + "]");
			}
		}

        return service;
	}

	/**
	 * Returns the parent <code>Context</code> instance.
	 * @return Context Parent <code>Context</code>.
	 */
	public Context getParentContext()
	{
		return parent;
	}

	/**
	 * Returns the root <code>Context</code> of the whole hierarchy.
	 * @return Context Root <code>Context</code>.
	 */
	public Context getRootContext()
	{
		//run recurceively bottom up over all context's hierarchy and return the topmost context

		if (parent == null)
		{
			return this;
		}

		return parent.getRootContext();
	}

	public void addService(Service service, String serviceName)
	{
		Logger.debug(LOGGER_CONTEXT, "Adding service [" + serviceName + "] dynamically into context [" + peer.getContextName() + "]");
		
		synchronized (syncObject)
		{
			services.put(serviceName, service);
		}
	}

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
	public void setDynamicGlobalParameter(String fieldName, Object value)
	{
		firstDynamicContext.setDynamicGlobalParameterInner(fieldName, value);
//		if (parent.isStatic())
//		{
//			data.put(GLOBAL_PARAMETERS_PREFIX + fieldName, value);
//		}
//		else
//		{
//			parent.setDynamicGlobalParameter(fieldName, value);
//		}
	}

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
	protected void setDynamicGlobalParameterInner(String fieldName, Object value)
	{
		data.put(GLOBAL_PARAMETERS_PREFIX + fieldName, value);
	}


	/**
	 * Used to determine is the context defined as static
	 *
	 * @return true if the context defined as static.
	 */
	public boolean isStatic()
	{
		return peer.getContextType() == Context.CONTEXT_TYPE_STATIC;
	}

	/**
	 * Usefull method to retrieve context field data as <code>java.util.Date</code>
	 *
	 * @param contextFieldName of field to retrieve
	 * @return <code>java.util.Date</code> object
	 * @throws ContextException
	 */
	public Date getDate(String fieldName) throws ContextException
	{
		return (Date) getField(fieldName);
	}

	/**
	 * Usefull method to retrieve context field data as <code>java.lang.String</code>
	 *
	 * @param contextFieldName of field to retrieve
	 * @return <code>java.lang.String</code> object
	 * @throws ContextException
	 */
	public String getString(String fieldName) throws ContextException
	{
		return (String) getField(fieldName);
	}

	public int getInt(String fieldName) throws ContextException
	{
		return ((Number)getField(fieldName)).intValue();
	}

	public void setField(String fieldName, int fieldData) throws ContextException, FatalException, AuthorizationException
	{
		setField(fieldName, new Integer(fieldData));
	}

	public Boolean getBoolean(String fieldName) throws ContextException
	{
		return (Boolean)getField(fieldName);
	}

	public void setField(String fieldName, Boolean fieldData) throws ContextException, FatalException, AuthorizationException
	{
		setField(fieldName, (Object)fieldData);
	}

	/**
	 * Usefull method to retrieve context field data as <code>java.lang.Integer</code>
	 *
	 * @param contextFieldName of field to retrieve
	 * @return <code>java.lang.Integer</code> object
	 * @throws ContextException
	 */
	public Integer getInteger(String fieldName) throws ContextException
	{
		Object o = getField(fieldName);
		if (o != null)
		{
			return new Integer(((Number) o).intValue());
		}
		else
		{
			return null;
		}
	}

	/**
	 * Usefull method to retrieve context field data as <code>java.lang.Long</code>
	 *
	 * @param contextFieldName of field to retrieve
	 * @return <code>java.lang.Integer</code> object
	 * @throws ContextException
	 */
	public Long getLong(String fieldName) throws ContextException
	{
		Object o = getField(fieldName);
		if (o != null)
		{
			return new Long(((Number) o).longValue());
		}
		else
		{
			return null;
		}
	}

	/* 
	 * Start: XIData interface methods 
	 * for types that recieve data from external source i.e Http event, Web service
	 */

	/**
	 * Returns the data of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Object The value of the field as an Object. 
	 */
	public Object getXIData(String fieldName) throws ContextException
	{
		XIData data = (XIData)getField(fieldName);
		if(data == null)
			return null;
		return data.getData();
	}

	/**
	 * Sets an Object value into field of type XIData. 
	 * @param fieldName The field name to set.
	 * @param Object The field value to set.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public void setXIData(String fieldName, Object fieldValue) throws ContextException, FatalException, AuthorizationException
	{
		setField(fieldName, fieldValue, true);
	}


	private Object getXIFieldData (String fieldName) throws ContextException
	{
		Object fieldData = null;
		XIData data = (XIData)getField(fieldName);
		if(data != null)
			fieldData = data.getData();
		return fieldData;
	}

	/**
	 * Returns the Integer value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Integer The value of the field as an Integer. 
	 */
	public Integer getXIInteger(String fieldName) throws ContextException
	{
		return (Integer) getXIFieldData(fieldName);
	}

	/**
	 * Returns the Double value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Double The value of the field as an Double. 
	 */
	public Double getXIDouble(String fieldName) throws ContextException
	{
		return (Double) getXIFieldData(fieldName);
	}

	/**
	 * Returns the Long value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Long The value of the field as an Long. 
	 */
	public Long getXILong(String fieldName) throws ContextException
	{
		return (Long) getXIFieldData(fieldName);
	}


	/**
	 * Returns the BigDecimal value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return BigDecimal The value of the field as an BigDecimal. 
	 */
	public BigDecimal getXIBigDecimal(String fieldName) throws ContextException
	{
		return (BigDecimal) getXIFieldData(fieldName);
	}


	/**
	 * Returns the Date value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return Date The value of the field as an Date. 
	 */
	public Date getXIDate(String fieldName) throws ContextException
	{
		return (Date) getXIFieldData(fieldName);
	}
	
	/**
	 * Returns the String value of a specific XIData field.
	 * @param fieldName The field name to get.
	 * @return String The value of the field as an String. 
	 */
	public String getXIString(String fieldName) throws ContextException
	{
		return (String) getXIFieldData(fieldName);
	}
	
	/* 
	 * End: XIData interface methods 
	 */

	/**
	 * Used to retrieve parameters stored by <code>setDynamicGlobalParameter</code>
	 * @param contextFieldName the name of field to retrieve
	 * @return the value of parameter or <code>null</code> if not set.
	 */
	public Object getDynamicGlobalParameter(String fieldName)
	{
		return firstDynamicContext.getDynamicGlobalParameterInner(fieldName);
//		if (parent.isStatic())
//		{
//			return data.get(GLOBAL_PARAMETERS_PREFIX + fieldName);
//		}
//		else
//		{
//			return parent.getDynamicGlobalParameter(fieldName);
//		}
	}

	/**
	 * Returns the value of a global parameter stored by <code>setDynamicGlobalParameter</code> method.
	 * @param contextFieldName The name of field.
	 * @return Object The value of the parameter or <code>null</code> if the parameter was not set.
	 */
	protected Object getDynamicGlobalParameterInner(String fieldName)
	{
		return data.get(GLOBAL_PARAMETERS_PREFIX + fieldName);
	}


	/**
	 * Used to remove parameters stored by <code>setDynamicGlobalParameter</code>
	 * @param contextFieldName the name of field to remove
	 * @return the value of parameter or <code>null</code> if not set.
	 */
	public Object removeDynamicGlobalParameter(String fieldName)
	{
		return firstDynamicContext.removeDynamicGlobalParameterInner(fieldName);
//		if (parent.isStatic())
//		{
//			return data.remove(GLOBAL_PARAMETERS_PREFIX + fieldName);
//		}
//		else
//		{
//			return parent.removeDynamicGlobalParameter(fieldName);
//		}
	}

	/**
	 * Used to remove parameters stored by <code>setDynamicGlobalParameter</code>
	 * @param contextFieldName the name of field to remove
	 * @return the value of parameter or <code>null</code> if not set.
	 */
	protected Object removeDynamicGlobalParameterInner(String fieldName)
	{
		return data.remove(GLOBAL_PARAMETERS_PREFIX + fieldName);
	}

	/**
	 * Used to create <code>Iterator</code> of existing context fields names.
	 *
	 * @return <code>Iterator</code>
	 */
	public Iterator getFieldNamesIterator()
	{
		return data.keySet().iterator();
	}

	/**
	 * Returns the DynamicGlobals object from the upper dynamic context.
	 * @return DynamicGlobals
	 */
	public DynamicGlobals getDynamicGlobals ()
	{
		return (DynamicGlobals) getDynamicGlobalParameter(DYNAMIC_GLOBALS);
	}
	
	/**
	 * Sets the DynamicGlobals object in the upper dynamic context.
	 * @param DynamicGlobals 
	 */
	public void setDynamicGlobals (DynamicGlobals dynamicGlobals)
	{
		if(getDynamicGlobalParameter(DYNAMIC_GLOBALS) == null)
			setDynamicGlobalParameter(DYNAMIC_GLOBALS, dynamicGlobals);
	}

	/**
	 * get the desription of the current object.
	 * @return String
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer(5120);
		printAll(sb);

		return sb.toString();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Called while service instantiation to create services instances and initialize them
	 */
	private void initServices(ContextMetaData metaData) throws FlowElementsFactoryException, ServiceException
	{
		//run over all services definitions of the peer and create services instances.
		int count = metaData.getServicesCount();
        for (int i = 0; i < count; i++)
        {
			ServiceData serviceData = metaData.getServiceData(i);
			Logger.debug(LOGGER_CONTEXT, "init service [" + serviceData.getServiceName() + "] in context [" + peer.getContextName() + "]");
			
			Service service = createService(serviceData);
			
			//storing service in the local service map by service name
			services.put(serviceData.getServiceName(), service);
        }
	}

	private Service createService (ServiceData serviceData) throws FlowElementsFactoryException, ServiceException
	{
		//create service instance
		Service service = FlowElementsFactory.getInstance().createServiceInstance(serviceData.getServiceRefName());

		//initializing service with list of parameters
		service.initialize(serviceData.getParameterList());

		return service;
	}

	private void destroyServices()
	{
		Iterator it = services.keySet().iterator();
		while (it.hasNext())
		{
			String serviceKey = (String) it.next();

			Service service = (Service) services.get(serviceKey);

			service.destroy();
		}
	}

	/**
	 * Called while context instance creation to initialize default fields
	 */
	private void processDefaultValues() throws ContextException, FatalException, AuthorizationException
	{
		//run over list of default values defined in peer and setting context fields
        Iterator it = peer.getFieldNames();
		while (it.hasNext())
		{
			String fieldName = (String) it.next();
			proccessFieldDefaultValue(peer.getField(fieldName));
		}
	}

	/**
	 * Proccess field default value.
	 * @param field The field
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	private void proccessFieldDefaultValue(ContextField field) throws ContextException, FatalException, AuthorizationException
	{
		if (field.getDefaultValue() != null)
		{
			performSet(field, field.getDefaultValue(), field.getName(), false);
		}
		else
		{
			if (field.getType().getType() == ContextFieldType.STRUCTURE_TYPE)
			{
				if (((StructureContextFieldType)field.getType()).isDefaultValuesPresents())
				{
					ContextStructure structure = new ContextStructure(((StructureContextFieldType)field.getType()).getStructureDefinition(), this);
					data.put(field.getName(), structure);
				}
			}
		}
	}

	/**
	 * Called by toString() method to create string representation of all context hierarchy (from this context and up to root (application context))
	 *
	 * @param sb <code>StringBuffer</code> to place the string representation to
	 * @return print tabulation index (used by itself while recursion to make more comfortable string representation)
	 */
	private int printAll(StringBuffer sb)
	{
		int tabs = 0;
		if (parent != null && parent instanceof ContextImpl)
		{
			tabs = ((ContextImpl)parent).printAll(sb);
		}

		sb.append("\n**** CONTEXT START **************************************************************************\n");
		appendTabs(sb, tabs);
		sb.append("NAME     -> [" + getName() + "]\n");
		appendTabs(sb, tabs);
		sb.append("PARENT   -> [" + (getParentContext() == null ? null : getParentContext().getName()) + "]\n");
		appendTabs(sb, tabs);
		sb.append("FIELDS   -> \n");

		Iterator keyIt = data.keySet().iterator();
		while (keyIt.hasNext())
		{
			String key = (String) keyIt.next();
			ContextField field = peer.getField(key, dynamicMetaData);

			appendTabs(sb, tabs + 1);

			sb.append("FIELD: NAME -> [" + key + "]");

			if (field != null)
			{
				String childAccess;
				if (field.getAccess() == ContextField.CHILD_ACCESS_READ_ONLY)
					childAccess = ExternalizerConstants.CONTEXT_FIELD_ACCESS_READ_ONLY;
				else if (field.getAccess() == ContextField.CHILD_ACCESS_FULL)
					childAccess = ExternalizerConstants.CONTEXT_FIELD_ACCESS_FULL;
				else
					childAccess = ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE;
				
				sb.append(" ACCESS -> [" + childAccess + "]");
				if (field.getType().getType() == ContextFieldType.CLASS_TYPE)
				{
					sb.append(" TYPE -> [");
					sb.append(((ClassContextFieldType)field.getType()).getTypeClass().getName() + "]");
					sb.append(" PRINTER -> [");
					sb.append(((ClassContextFieldType)field.getType()).getPrinterBundle() + "]");
					sb.append(" BUILDER -> [");
					sb.append(((ClassContextFieldType)field.getType()).getBuilderBundleList() + "]");
					sb.append(" XI_TYPE -> [");
					sb.append(field.isXIType() + "]");
				}
				else if (field.getType().getType() == ContextFieldType.STRUCTURE_TYPE)
				{
					sb.append(" TYPE -> [");
					sb.append("structure -> [" + ((StructureContextFieldType)field.getType()).getStructureDefinition().getStructureName() + "]]");
					sb.append(" XI_TYPE -> [");
					sb.append(field.isXIType() + "]");
				}
			}

			Object fieldData = data.get(key);
			if (fieldData instanceof ContextStructure)
			{
				ContextStructure structure = (ContextStructure) fieldData;
				sb.append("\n" + structure.printAll(tabs + 2));
			}
			else
			{
				sb.append(" VALUE -> [" + fieldData + "]\n");
			}
		}

		sb.append("**** CONTEXT END ****************************************************************************");

		return tabs + 1;
	}

	/**
	 * Used by <code>printAll</code> method. Adds tab (\t) character to given <code>StringBuffer</code>
	 */
	private void appendTabs(StringBuffer sb, int tabs)
	{
		for (int i = 0; i < tabs; i++)
		{
			sb.append("\t");
		}
	}

	/**
	 * Check if the field name is already exists in the conetxt peer, 
	 * before adding a dynamic field to the context.
	 * @param fieldName The field name
	 * @throws ContextException
	 */
	private void checkDuplicateFieldName(String fieldName) throws ContextException
	{
		if(peer.getField(fieldName, dynamicMetaData) != null)
		{
			throw new ContextException("Unable to add dynamic field into context [" + getName() + "]. The field with name [" + fieldName + "] is already exists.");			
		}
	}

	/**
	 * Initialize the dynamicMetaData object, if necessary.
	 */
	private void initDynamicMetaData()
	{
		if(dynamicMetaData == null)
		{
			dynamicMetaData = new ContextMetaData();
		}
	}

	/**
	 * Check if the conetxt is of type dynamic, for dynamic insertion of fields into the context.
	 */
	private void checkContextTypeForDynamicFields() throws ContextException
	{
		if(isStatic())
		{
			throw new ContextException("Unable to add dynamic field into context [" + getName() + "]. The context is of type static and could not be added with dynamic fields.");			
		}
	}


	/**
	 * Used to add a new field dynamically to the context.
	 * @param DynamicField The dynamic field attributes
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public void addDynamicField (DynamicField dynamicField) throws ContextException, FatalException, AuthorizationException
	{
		//Check if the conetxt is of type dynamic, for dynamic insertion of fields into the context.
		checkContextTypeForDynamicFields();
		
		//Check if the field name is already exists in the conetxt peer.
		checkDuplicateFieldName(dynamicField.getFieldName());
		
		//Initialize the dynamicMetaData object.
		initDynamicMetaData();

		Logger.debug(LOGGER_CONTEXT, "Adding field [" + dynamicField.getFieldName() + "] dynamically into context [" + peer.getContextName() + "]");
		
		synchronized (syncObject)
		{
		
			ContextField field;
			try
			{
				ContextExternalizer externalizer = ContextExternalizer.getInstance();
				
				ContextFieldType contextFieldType = externalizer.getContextFieldType(
						dynamicField.getFieldName(),
						dynamicField.getFieldType(), 
						peer.getContextName());
				
				field =	externalizer.createField(
						dynamicField.getFieldName(),
						contextFieldType,
						dynamicField.getChildAccess(),
						dynamicField.getDefaultValue(),
						dynamicField.getCaption());
						
				dynamicMetaData.addField(field);
						
			}
			catch (ExternalizerInitializationException e)
			{
				throw new ContextException("Error adding dynamic Field", e); 
			}
			catch (ExternalizerNotInitializedException e)
			{
				throw new ContextException("Error adding dynamic Field", e); 
			}
			
			if(dynamicField.getDefaultValue() != null)
			{
				performSet(field, dynamicField.getDefaultValue(), dynamicField.getFieldName(), false);		
			}
		}
	}

	/**
	 * Used to add a new structure field dynamically to the context.
	 * The Structure definition is declared in the DynamicStructure and not in the XML files.
	 * @param dynamicStructure The dynamic structure field attributes
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 */
	public void addDynamicStructure (DynamicStructure dynamicStructure) throws ContextException, FatalException, AuthorizationException
	{
		//Check if the conetxt is of type dynamic, for dynamic insertion of fields into the context.
		checkContextTypeForDynamicFields();
		
		//Check if the field name is already exists in the conetxt peer.
		checkDuplicateFieldName(dynamicStructure.getContextFieldName());

		//Initialize the dynamicMetaData object.
		initDynamicMetaData();

		Logger.debug(LOGGER_CONTEXT, "Adding structure [" + dynamicStructure.getStructureName() + "] context field name [" + dynamicStructure.getContextFieldName() + "] dynamically into context [" + peer.getContextName() + "]");
		
		synchronized (syncObject)
		{
		
			try
			{
				ContextExternalizer externalizer = ContextExternalizer.getInstance();
				
				//creating structure definition instance
				ContextStructureDefinition structureDefinition = new ContextStructureDefinition(dynamicStructure.getStructureName());
			
				for (int i = 0 ; i < dynamicStructure.getFieldsCount() ; i++)
				{
					DynamicField field = dynamicStructure.getField(i);
					externalizer.addFieldToStructureDefinition(field.getFieldName(), field.getFieldType(), field.getDefaultValue(), structureDefinition);
				}
				
				ContextFieldType contextFieldType = new StructureContextFieldType(structureDefinition, dynamicStructure.getStructureName());
				
				ContextField field = externalizer.createField(dynamicStructure.getContextFieldName(), contextFieldType, dynamicStructure.getChildAccess(), null, null);
	
				dynamicMetaData.addField(field);
	
				if(structureDefinition.isDefaultValuesPresents())
				{
					data.put(field.getName(), new ContextStructure(structureDefinition, this));
				}
				
			}
			catch (ExternalizerInitializationException e)
			{
				throw new ContextException("Error adding dynamic Structure", e); 
			}
			catch (ExternalizerNotInitializedException e)
			{
				throw new ContextException("Error adding dynamic Structure", e); 
			}
		}

	}

	/**
	 * Used to add a new ContextElementSet dynamically to the context.
	 * @param contextElementSetDefName The ContextElementSet defintion name.
	 * @throws ContextException
	 * @throws FatalException
	 * @throws AuthorizationException
	 * @throws FlowElementsFactoryException
	 * @throws ServiceException
	 */
	public void addDynamicContextElementSet (String contextElementSetDefName) throws ContextException, FatalException, AuthorizationException, FlowElementsFactoryException, ServiceException
	{
		//Check if the conetxt is of type dynamic, for dynamic insertion of fields into the context.
		checkContextTypeForDynamicFields();
		
		//Initialize the dynamicMetaData object.
		initDynamicMetaData();
		
		Logger.debug(LOGGER_CONTEXT, "Adding ContextElementSet [" + contextElementSetDefName + "] dynamically into context [" + peer.getContextName() + "]");

		synchronized (syncObject)
		{
		
			try
			{
				ContextExternalizer externalizer = ContextExternalizer.getInstance();
				
				// copy the meta data into the current dynamic meta data.
				ContextMetaData setMetaData = externalizer.copyContextElementSetDefinitions(peer, contextElementSetDefName, dynamicMetaData);
				
				// proccess the fields default values
				Iterator fieldsIterator = setMetaData.getFieldNames();
				while (fieldsIterator.hasNext())
				{
					proccessFieldDefaultValue(setMetaData.getField((String)fieldsIterator.next()));
				}
				
				initServices(setMetaData);
				
				
			}
			catch (ExternalizerInitializationException e)
			{
				throw new ContextException("Error adding dynamic ContextElementSet", e); 
			}
			catch (ExternalizerNotInitializedException e)
			{
				throw new ContextException("Error adding dynamic ContextElementSet", e); 
			}
		}		
	}

	/**
	 * Returns the field definition.
	 * @param fieldName
	 * @return ContextField
	 * @throws ContextException
	 */
	protected ContextField getFieldDefinition(String fieldName) throws ContextException
	{
		return getFieldDefinition(fieldName, true);
	}
	/**
	 * Returns the field definition.
	 * @param fieldName
	 * @param ignoreAccess
	 * @return ContextField
	 * @throws ContextException
	 */
	protected ContextField getFieldDefinition(String fieldName, boolean ignoreAccess) throws ContextException
	{
		//if the field data not set - checking is field defined for the context
		ContextField field = peer.getField(fieldName, dynamicMetaData);
		
		if(!ignoreAccess)
		{
			if(field != null && field.getAccess() == ContextField.CHILD_ACCESS_NONE)
			{
				throw new FieldAccessViolationException("Unable to get field value. Field with name [" + fieldName + "] in parent context [" + name + "] has child access [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE + "]");				
			}
		}
		
		
		if (field == null)
		{ 
			// the field is not defined in the current context
			
			if (parent != null)
			{
				try
				{
					//	passing get to parent
					return parent.getFieldDefinition(fieldName, false);
				}
				catch (FieldAccessViolationException ex)
				{
					throw new FieldAccessViolationException("Unable to get field. The field with name [" + fieldName + "] in parent context has access modifier [" + ExternalizerConstants.CONTEXT_FIELD_ACCESS_NONE + "]", ex);
				}
				catch (ContextException ex)
				{
					if (peer.isAddOnDemand())
					{
						return null;
					}
					else
					{
						Logger.debug(LOGGER_CONTEXT, "Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "], parent context is [" + parent.getName() + "]");
						throw new ContextException("Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "] and nor in parent context");
					}
				}
			}
			else
			{
				if (peer.isAddOnDemand())
				{
					return null;
				}
				else
				{
					Logger.debug(LOGGER_CONTEXT, "Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "], parent context is null");
					throw new ContextException("Unable to get field. Field with name [" + fieldName + "] not found in context [" + name + "] and parent context is null");
				}
			}
		}
		
		return field;
	}
	
	private String getContextHierarchy()
	{
		Context ctx = this;
		StringBuffer hierarchy = new StringBuffer(1024);
		while (ctx != null)
		{
			hierarchy.append(ctx.getName()).append("--->");
			ctx = ctx.getParentContext();		
		}
		
		return hierarchy.toString();
	}

}
