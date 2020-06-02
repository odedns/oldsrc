/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationCheckWrapper.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.*;
import com.ness.fw.shared.common.XIData;
import com.ness.fw.util.*;

import java.util.*;
import java.lang.reflect.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationCheckWrapper
{
	private static final int EXTRA_PARAMS  =   1;

	private ArrayList paramList;
	private ValidationCheck validationCheck;
	private Method validationCheckMethod;
	private String name;
	private ValidationCheckFieldData fieldDatas[];

	public ValidationCheckWrapper(String name, ValidationCheck validationCheck, Method validatorCheckMethod)
	{
		this.validationCheck = validationCheck;
		this.validationCheckMethod = validatorCheckMethod;
		this.name = name;
		paramList = new ArrayList();
	}

	public void addParam(String name, Object value, boolean fromContext)
	{
		paramList.add(new ValidationParam(name, value, fromContext, value instanceof ArrayList));
	}

	public boolean doCheck(Context ctx) throws ContextException, InvocationTargetException, IllegalAccessException
	{
		Object params[] = new Object[paramList.size() + EXTRA_PARAMS];
		ValidationCheckBundle bundle = new ValidationCheckBundle(ApplicationUtil.getGlobalMessageContainer(ctx), fieldDatas, Message.SEVERITY_ERROR);
		params[0] = bundle;		
		for (int i = 0; i < paramList.size(); i++)
		{
			ValidationParam param = (ValidationParam) paramList.get(i);
			if (param.isList())
			{
				ArrayList subParams = (ArrayList)param.getValue();

				if (subParams.size() == 0)
				{
					params[i + EXTRA_PARAMS] = null;
				}
				else
				{
					Object arr[] = (Object[])Array.newInstance(((ValidationParam)subParams.get(0)).getValue().getClass(), subParams.size());
					for (int j = 0; j < subParams.size(); j++)
					{
						ValidationParam subParam = (ValidationParam)subParams.get(j);
						if (subParam.isFromContext())
						{
							arr[j] = getContextFieldValue(ctx, (String)subParam.getValue());
						}
						else
						{
							arr[j] = subParam.getValue();
						}
					}

					params[i + EXTRA_PARAMS] = arr;
				}
			}
			else
			{
				if (param.isFromContext())
				{
					params[i + EXTRA_PARAMS] = getContextFieldValue(ctx, (String)param.getValue());
				}
				else
				{
					params[i + EXTRA_PARAMS] = param.getValue();
				}
			}
		}

		return ((Boolean)validationCheckMethod.invoke(validationCheck, params)).booleanValue();
	}
	
	private Object getContextFieldValue (Context ctx, String fieldName) throws ContextException
	{
		Object fieldData = ctx.getField(fieldName); 
		if(fieldData != null && fieldData instanceof XIData)
		{
			fieldData = ((XIData)fieldData).getData();
		}
		return fieldData;
	}

	public String getName()
	{
		return name;
	}

	public ValidationCheckFieldData[] getFieldDatas()
	{
		return fieldDatas;
	}

	public void setFieldDatas(ValidationCheckFieldData[] fieldDatas)
	{
		this.fieldDatas = fieldDatas;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// Private methods
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////
	public static class ValidationParam
	{
		private String name;
		private Object value;
		private boolean fromContext;
		private boolean list;

		public ValidationParam(String name, Object value, boolean fromContext, boolean list)
		{
			this.name = name;
			this.value = value;
			this.fromContext = fromContext;
			this.list = list;
		}

		public String getName()
		{
			return name;
		}

		public Object getValue()
		{
			return value;
		}

		public boolean isFromContext()
		{
			return fromContext;
		}

		public boolean isList()
		{
			return list;
		}
	}
}
