package com.ness.fw.shared.builders.ui;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.externalization.ExternalizationException;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.flower.core.ClassContextFieldType;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.core.ContextFieldBuilder;
import com.ness.fw.flower.core.ContextFieldBuilderBundle;
import com.ness.fw.flower.core.ContextFieldBuilderParams;
import com.ness.fw.flower.core.ContextFieldConstructionException;
import com.ness.fw.flower.core.ContextFieldPrinter;
import com.ness.fw.flower.core.ContextFieldPrinterBundle;
import com.ness.fw.flower.core.ParameterList;
import com.ness.fw.flower.factory.externalization.ExternalizerNotInitializedException;
import com.ness.fw.flower.factory.externalization.TypeDefinitionExternalizer;
import com.ness.fw.ui.AbstractModel;
import com.ness.fw.ui.TextModel;
import com.ness.fw.ui.text.*;

public class UITextModelBuilder implements ContextFieldBuilder,ContextFieldPrinter
{
	private static final String FLOWER_DATA_TYPE_NAME_PARAM = "flowerDataTypeName";
	private static final String TYPE_PARAM = "type";
	private static final String TYPE_PARAM_INTEGER_VALUE = "integer";
	private static final String TYPE_PARAM_DOUBLE_VALUE = "double";
	private static final String TYPE_PARAM_LONG_VALUE = "long";
	private static final String TYPE_PARAM_DATE_VALUE = "date";
	private static final String TYPE_PARAM_TIME_VALUE = "time";
	private static final String TYPE_PARAM_TIMESTAMP_VALUE = "timestamp";
	private static final String TYPE_PARAM_BIG_DECIMAL_VALUE = "bigDecimal";
	private static final String TYPE_PARAM_String_VALUE = "string";
		
	private ClassContextFieldType classContextFieldType;

	private String flowerDataTypeNameParameter;
	private String typeParameter;

	/* (non-Javadoc)
	 * @see com.ness.fw.flower.core.ContextFieldBuilder#initialize(com.ness.fw.flower.core.ParameterList)
	 */
	public void initialize(ParameterList parameterList) throws Exception 
	{
		flowerDataTypeNameParameter = parameterList.getParameter(FLOWER_DATA_TYPE_NAME_PARAM).getValue().toString();
		typeParameter = parameterList.getParameter(TYPE_PARAM).getValue().toString();
	}
	
	private void checkAuthorization(TextModel textModel,boolean checkAuthorization) throws UIException, AuthorizationException
	{
		textModel.checkAutorization(checkAuthorization);
	}
	
	public Integer buildIntegerFromXIInteger(ContextFieldBuilderParams builderParams,Integer modelData,IntegerTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getInteger();
	}

	public AbstractModel buildFromInteger(ContextFieldBuilderParams builderParams, IntegerTextModel textModel, Integer modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_INTEGER_VALUE))
		{
			textModel.setInteger(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}
	
	public Double buildDoubleFromXIDouble(ContextFieldBuilderParams builderParams,Double modelData,DoubleTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getDouble();
	}
	
	public AbstractModel buildFromDouble(ContextFieldBuilderParams builderParams, DoubleTextModel textModel, Double modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_DOUBLE_VALUE))
		{
			textModel.setDouble(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}
	
	public Long buildLongFromXILong(ContextFieldBuilderParams builderParams,Long modelData,LongTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getLong();
	}
		
	public AbstractModel buildFromLong(ContextFieldBuilderParams builderParams, LongTextModel textModel, Long modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_LONG_VALUE))
		{
			textModel.setLong(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}
	
	public Date buildDateFromXIDate(ContextFieldBuilderParams builderParams,Date modelData,DateTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getDate();
	}	
	
	public AbstractModel buildFromDate(ContextFieldBuilderParams builderParams, DateTextModel textModel, Date modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_DATE_VALUE))
		{
			textModel.setDate(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}

	public Date buildTimestampFromXITimestamp(ContextFieldBuilderParams builderParams,Date modelData,TimestampTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getTimestamp();
	}
		
	public AbstractModel buildFromTimestamp(ContextFieldBuilderParams builderParams, TimestampTextModel textModel, Date modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_TIMESTAMP_VALUE))
		{
			textModel.setTimestamp(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}
	
	public Date buildTimeFromXITime(ContextFieldBuilderParams builderParams,Date modelData,TimeTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getTime();
	}
		
	public AbstractModel buildFromTime(ContextFieldBuilderParams builderParams, TimeTextModel textModel,Date modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_TIME_VALUE))
		{
			textModel.setTime(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}	
	
	public BigDecimal buildBigDecimalFromXIBigDecimal(ContextFieldBuilderParams builderParams,BigDecimal modelData,BigDecimalTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getBigDecimal();
	}
	
	public AbstractModel buildFromBigDecimal(ContextFieldBuilderParams builderParams, BigDecimalTextModel textModel, BigDecimal modelData) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		if (typeParameter.equals(TYPE_PARAM_BIG_DECIMAL_VALUE))
		{
			textModel.setBigDecimal(modelData);
			return textModel;
		}
		else
		{
			throw new ContextException("Cannot construct field of type " + typeParameter + " with value of type " + modelData.getClass().getName());
		}
	}					
	
	public String buildStringFromXIString (ContextFieldBuilderParams builderParams,String modelData,StringTextModel textModel) throws AuthorizationException,UIException,ContextException,ContextFieldConstructionException,FatalException
	{
		return textModel.getString();
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,IntegerTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}

	public AbstractModel build(ContextFieldBuilderParams builderParams,DoubleTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,LongTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,DateTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,TimestampTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,TimeTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,BigDecimalTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}
	
	public AbstractModel build(ContextFieldBuilderParams builderParams,StringTextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		return buildTextModel(builderParams,textModel,modelData);	
	}	
	
	private AbstractModel buildTextModel(ContextFieldBuilderParams builderParams,TextModel textModel, String modelData) throws AuthorizationException,UIException,ContextFieldConstructionException,FatalException
	{
		checkAuthorization(textModel,builderParams.isCheckAuthorization());
		try 
		{
			if (classContextFieldType == null)
			{
				classContextFieldType =	TypeDefinitionExternalizer.getInstance().getType(flowerDataTypeNameParameter);
			}	
			ContextFieldBuilderBundle contextFieldBuilderBundle = classContextFieldType.getBuilderBundle("java.lang.String");
			if (contextFieldBuilderBundle == null)
			{
				textModel.setData(modelData);
			}
			else
			{
				textModel.setData(contextFieldBuilderBundle.build(builderParams,null,modelData));
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
				  throw new FatalException("Unable to construct TextModel field with data " + modelData, ex.getTargetException());
			}
		}
		catch (IllegalAccessException e) 
		{
			throw new FatalException("Unable to construct TextModel field with data " + modelData, e);
		} 
		catch (IllegalArgumentException e) 
		{
			throw new FatalException("Unable to construct TextModel field with data " + modelData, e);
		} 
		catch (ExternalizerNotInitializedException e) 
		{
			throw new FatalException("Unable to construct TextModel field with data " + modelData, e);
		} 
		catch (ExternalizationException e) 
		{
			throw new FatalException("Unable to construct TextModel field with data " + modelData, e);
		}
		return textModel;
	}
	
	public String print(LocalizedResources localizable, IntegerTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}
	
	public String print(LocalizedResources localizable, DoubleTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}

	public String print(LocalizedResources localizable, LongTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}
			
	public String print(LocalizedResources localizable, DateTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}
	
	public String print(LocalizedResources localizable, TimestampTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}	
		
	public String print(LocalizedResources localizable, TimeTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}
					
	public String print(LocalizedResources localizable, BigDecimalTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}

	public String print(LocalizedResources localizable, StringTextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		return printTextModel(localizable,contextFieldData,maskKey);
	}
							
	public String printTextModel(LocalizedResources localizable, TextModel contextFieldData, String maskKey) throws ResourceException,Throwable
	{
		if (classContextFieldType == null)
		{
			classContextFieldType =	TypeDefinitionExternalizer.getInstance().getType(flowerDataTypeNameParameter);
		}
		ContextFieldPrinterBundle contextFieldPrinterBundle = classContextFieldType.getPrinterBundle();
		if (contextFieldPrinterBundle == null)
		{
			String data;
			if (contextFieldData != null && contextFieldData.getData() != null)
				data = contextFieldData.getData().toString();
			else
				data = "";
			
			return data;
		}
		else
		{
			return contextFieldPrinterBundle.print(localizable,contextFieldData.getData(),maskKey);
		}	
	}
}