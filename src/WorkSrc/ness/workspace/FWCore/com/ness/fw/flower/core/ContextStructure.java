/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ContextStructure.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import com.ness.fw.flower.factory.externalization.ExternalizerConstants;
import com.ness.fw.flower.util.*;
import com.ness.fw.shared.common.SystemUtil;
import com.ness.fw.shared.common.XIDataFactory;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;

import java.util.*;
import java.lang.reflect.*;

/**
 * Represents a special kind of field data - structure, that encapsulates:
 * <ol>
 * <li>structure definition
 * <li>fields data
 * <li>holding context
 * </ol>
 * 
 * Only fields of type class supported for structures. no nested structures allowed.
 */
public class ContextStructure
{

	public static final String LOGGER_CONTEXT = FLOWERConstants.LOGGER_CONTEXT + "STRUCTURE";
	
	/**
	 * A map contains the field’s data.
	 * Contains the data only for the fields that was initialized. 
	 */
	private HashMap data;

	/**
	 * The structure definition.
	 */
	private ContextStructureDefinition definition;
	
	/**
	 * The context that hold the structure.
	 */
	private Context holdingContext;

	/**
	 * create new ContextStructure object.
	 * initializeDefaultValues set to true.
	 *  
	 * @param definition The structure definition.
	 * @param holdingContext The context that hold the structure.
	 * @throws ContextException
	 */
	public ContextStructure(ContextStructureDefinition definition, Context holdingContext) throws ContextException, FatalException, AuthorizationException
	{
		this(definition, holdingContext, true);
	}

	/**
	 * create new ContextStructure object. 
	 * @param definition The structure definition.
	 * @param holdingContext The context that hold the structure.
	 * @param initializeDefaultValues Indicates whether to initialize the default 
	 * values for the fields after the structure creation. Set to false when copying the structure. 
	 * @throws ContextException
	 */
	private ContextStructure(ContextStructureDefinition definition, Context holdingContext, boolean initializeDefaultValues) throws ContextException, FatalException, AuthorizationException
	{
		this.definition = definition;
		this.holdingContext = holdingContext;
		this.data = new HashMap();

		if (initializeDefaultValues && definition.isDefaultValuesPresents())
		{
			processDefaultValues();
		}
	}

	/**
	 * Set a value into a specific structure field.
	 * When the type of the given data is not as the same type of the field in the context,
	 * use a builder object to convert the given value to the type of the field in the context. 
	 * @param contextFieldName The field name to set the data.
	 * @param fieldData The data to set.
	 * @param allowNonUITypes Indicates whether to allow setting data to non ui data types. 
	 * @throws ContextException
	 */
	public void setField(String fieldName, Object fieldData, boolean allowNonUITypes) throws ContextException, FatalException, AuthorizationException
	{
		// get the definition of the field.
		ContextStructureField field = definition.getField(fieldName);
		Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. given data [" + fieldData + "]");

		if (field == null)
		{
			throw new ContextException("Unable to set field [" + fieldName + "] to context structure [" + definition.getStructureName() + "]. the field is not defined");
		}

		// when the value of the field is null, remove the field from the data map.  
		if (fieldData == null)
		{
			Object currentData = data.get(fieldName);
			if(field.getType().isXIType() && currentData != null)
			{
				boolean setData = XIDataFactory.setDataToXIObject(field.getType().getTypeName(), currentData, fieldData);
				if(!setData)
				{
					data.remove(fieldName);
					Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. actual data [null]");
				}
			}
			else
			{
				data.remove(fieldName);
				Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. actual data [null]");
			}
		}
		// When the given data has the same class of the type in the context, 
		// set the data as is, without converting it.
		else if (((ClassContextFieldType)field.getType()).getTypeClass().isInstance(fieldData))
		{
			data.put(fieldName, fieldData);
			Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. actual data [" + fieldData + "]");
		}
		// When the type of the given data is not as the same type of the field in the context,
		// use a builder object to convert the given value to the type of the field in the context. 
		else
		{
			try
			{
				Object newFieldData = null;

				ContextFieldBuilderBundle builderBundle = ((ClassContextFieldType)field.getType()).getBuilderBundle(fieldData);
				if(builderBundle != null)
				{
					// convert the object data from the given type to the type of the field in the context.
					Object currentFieldData = data.get(fieldName);
					
					if(!allowNonUITypes && !field.isXIType())
					{
						Logger.error(LOGGER_CONTEXT, "Unable to set parameter [" + fieldName + "] into context structure [" + definition.getStructureName() + "]. Redundant parameter that is not " + ExternalizerConstants.TYPE_ATTR_IS_XI_TYPE + "=true");
					}
					else
					{
						if(currentFieldData == null && field.getType().isXIType())
						{
							currentFieldData = XIDataFactory.createXIData(field.getType().getTypeName());
						}
					}
					
					ContextFieldBuilderParams builderParams = new ContextFieldBuilderParams(
						ApplicationUtil.getGlobalMessageContainer(holdingContext), 
						ApplicationUtil.getLocalizedResources(holdingContext), 
						!allowNonUITypes);
					
					newFieldData = builderBundle.build(builderParams, currentFieldData, fieldData);
				}
				else
				{
					throw new IncompatibleFieldTypeException("Unable to set field. The type  [" + ((ClassContextFieldType)field.getType()).getTypeClass().getName() + "] of field with name [" + fieldName + "] in context structure [" + definition.getStructureName() + "] is incompatible with [" + fieldData.getClass().getName() + "]");
				}


				// when the value of the field is null, remove the field from the data map.  
				if (newFieldData == null)
				{
					data.remove(fieldName);
					Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. actual data [null]");
				}
				else
				{
					// set the field value in the data map.  
					data.put(fieldName, newFieldData);
					Logger.debug(LOGGER_CONTEXT, "set data into field [" + fieldName + "] in structure [" + definition.getStructureName() + "]. actual data [" + newFieldData + "]");
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
					throw new FatalException("Unable to construct field. The constructor of structure field with name [" + fieldName + "] in structure [" + definition.getStructureName() + "]" + SystemUtil.convertThrowable2String(ex.getTargetException()));
				}
			}
			catch (IllegalAccessException ex)
			{
				throw new FatalException("Unable to construct field. The constructor of structure field with name [" + fieldName + "] in structure [" + definition.getStructureName() + "]" + SystemUtil.convertThrowable2String(ex));
			}
			catch (ContextException ex)
			{
				throw ex;
			}
		}
	}

	/**
	 * Returns the value of a specific structure field, according to the field name.
	 * @param fieldName The field name 
	 * @param formatted Indicates whether to format the value of the field. 
	 * @param maskKey The key of the mask to format the value. The mask is taken from a localizable properties
	 * file according to the given key.
	 * @return Object The field’s data. When the "formatted" parameter is set to true, the returned value 
	 * will be formatted according to the format of the parameter "maskKey".
	 * @throws ContextException
	 */
	public Object getField(String fieldName, boolean formatted, String maskKey) throws ContextException
	{
		// get the current data of the field.
		Object result = data.get(fieldName);

		// get the field definition
		ContextStructureField fieldDefinition = definition.getField(fieldName);
		
		if(fieldDefinition == null)
		{
			throw new ContextException ("Unable to get field from context structure [" + definition.getStructureName() + "]. Field with name [" + fieldName + "] not found in the structure."); 
		}

		if(result == null)
		{
			if(fieldDefinition.isXIType())
			{
				result = XIDataFactory.createXIData(fieldDefinition.getType().getTypeName());
				Logger.debug(LOGGER_CONTEXT, "create new XIData " + result + " to field " + fieldName + " in structure " + definition.getStructureName());
				data.put(fieldName, result);
			}
		}


		if (formatted) // should format the value of the field
		{
			
			// get the printer defined for the field type
			ContextFieldPrinterBundle printerBundle = null; 
			if(fieldDefinition != null)
			{
				printerBundle = ((ClassContextFieldType)fieldDefinition.getType()).getPrinterBundle(); 
			}
			
			if (printerBundle != null)
			{
				// format the value according to the mask.
				try
				{
					LocalizedResources localizable = ApplicationUtil.getLocalizedResources(holdingContext);
					return printerBundle.print(localizable, result, maskKey);
				} catch (Throwable ex)
				{
					throw new ContextException("Unable to get formatted value for field [" + fieldName + "] from structure [" + definition.getStructureName() + "].", ex);
				}
			}
			else
			{
				return result == null ? result : result.toString();
			}
		}
		else
		{ // get the value without formatting
			if (result == null)
			{
				if (fieldDefinition == null)
				{
					throw new ContextException("Unable to get field [" + fieldName + "] from structure [" + definition.getStructureName() + "]. The field in not defined");
				}
				else
				{
					return result;
				}
			}
			else
			{
				return result;
			}
		}
	}

	/**
	 * Creates new <code>ContextStructure</code> instance and copy all current structure fields to 
	 * the newly created structure. 
	 * @param newHoldingContext The {@link Context} that should contain the new structure.   
	 * @return ContextStructure The new <code>ContextStructure</code> instance. 
	 * @throws ContextException
	 */
	public ContextStructure copy(Context newHoldingContext) throws ContextException, FatalException, AuthorizationException
	{
		ContextStructure result = new ContextStructure(this.definition, newHoldingContext, false);

		Iterator keyIt = data.keySet().iterator();
		while (keyIt.hasNext())
		{
			String key = (String) keyIt.next();
			result.setField(key, data.get(key), true);
		}

		return result;
	}

	/**
	 * Called while instantiating the <code>ContextStructure</code> instance to 
	 * initialize the default values.
	 * @throws ContextException
	 */
	private void processDefaultValues() throws ContextException, FatalException, AuthorizationException
	{
		// run over the list of fields defined in the structure 
		// and construct the default values for the field.
		Iterator it = definition.getFieldNames();
		while (it.hasNext())
		{
			String fieldName = (String) it.next();
			ContextStructureField field = definition.getField(fieldName);

			if (field.getDefaultValue() != null)
			{
				setField(fieldName, field.getDefaultValue(), true);
			}
		}
	}

	/**
	 * Indicates whether the type is XI (external interface) type and contains model that 
	 * check the perfmissions to set the data into the field from the request parameters. 
	 * @param fieldName The field name  to check
	 * @return boolean
	 */
	public boolean isXIType(String fieldName)
	{
		// get the field definition
		ContextStructureField fieldDefinition = definition.getField(fieldName);
		return fieldDefinition.isXIType();
	}


	/**
	 * Used by <code>Context</code>'s <code>toString</code> method
	 *
	 * @param tabs The number of tabs to append.
	 * @return String 
	 */
	String printAll(int tabs)
	{
		StringBuffer sb = new StringBuffer(3072);
		Iterator keyIt = data.keySet().iterator();

		while (keyIt.hasNext())
		{
			String key = (String) keyIt.next();
			appendTabs(sb, tabs);
			ContextStructureField field = definition.getField(key);

			sb.append("STR FIELD: NAME -> [" + key + "]");

			if (field != null)
			{
				if (field.getType().getType() == ContextFieldType.CLASS_TYPE)
				{
					sb.append(" TYPE -> [");
					sb.append(((ClassContextFieldType)field.getType()).getTypeClass() + "]");
				}
				sb.append(" XI_TYPE -> [");
				sb.append(field.getType().isXIType() + "]");
			}

			Object fieldData = data.get(key);
			sb.append(" VALUE -> [" + fieldData.toString() + "]\n");
		}

		return sb.toString();
	}

	/**
	 * append tabs in the StringBuffer.
	 * @param sb The {@link StringBuffer} to append the tabs 
	 * @param tabs The number of tabs to append.
	 */
	private void appendTabs(StringBuffer sb, int tabs)
	{
		for (int i = 0; i < tabs; i++)
		{
			sb.append("\t");
		}
	}
}
