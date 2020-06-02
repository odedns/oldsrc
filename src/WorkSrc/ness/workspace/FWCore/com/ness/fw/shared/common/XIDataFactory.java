package com.ness.fw.shared.common;

import com.ness.fw.ui.text.*;

public class XIDataFactory 
{
	public static XIData createXIData(String type)
	{
		if (type.equals(XIData.XI_STRING_TYPE_DEF))
		{
			return new StringTextModel();
		}
		else if (type.equals(XIData.XI_INTEGER_TYPE_DEF))
		{
			return new IntegerTextModel();
		}
		else if (type.equals(XIData.XI_DOUBLE_TYPE_DEF))
		{
			return new DoubleTextModel();
		}
		else if (type.equals(XIData.XI_LONG_TYPE_DEF))
		{
			return new LongTextModel();
		}
		else if (type.equals(XIData.XI_DATE_TYPE_DEF))
		{
			return new DateTextModel();
		}
		else if (type.equals(XIData.XI_TIMESTAMP_TYPE_DEF))
		{
			return new TimestampTextModel();
		}		
		else if (type.equals(XIData.XI_TIME_TYPE_DEF))
		{
			return new TimeTextModel();
		}		
		else if (type.equals(XIData.XI_BIG_DECIMAL_TYPE_DEF))
		{
			return new BigDecimalTextModel();
		}
		else
		{
			return null;		
		}
	}

	public static XIData createXIData(String type,Object data)
	{
		if (type.equals(XIData.XI_STRING_TYPE_DEF))
		{
			StringTextModel stringTextModel = new StringTextModel();
			stringTextModel.setData(data);
			return stringTextModel;
		}
		else if (type.equals(XIData.XI_INTEGER_TYPE_DEF))
		{
			IntegerTextModel integerTextModel = new IntegerTextModel();
			integerTextModel.setData(data);
			return integerTextModel;
		}
		else if (type.equals(XIData.XI_DOUBLE_TYPE_DEF))
		{
			DoubleTextModel doubleTextModel = new DoubleTextModel();
			doubleTextModel.setData(data);
			return doubleTextModel;
		}
		else if (type.equals(XIData.XI_LONG_TYPE_DEF))
		{
			LongTextModel longTextModel = new LongTextModel();
			longTextModel.setData(data);
			return longTextModel;
		}
		else if (type.equals(XIData.XI_DATE_TYPE_DEF))
		{
			DateTextModel dateTextModel = new DateTextModel();
			dateTextModel.setData(data);
			return dateTextModel;
		}
		else if (type.equals(XIData.XI_TIMESTAMP_TYPE_DEF))
		{
			TimestampTextModel timestampTextModel = new TimestampTextModel();
			timestampTextModel.setData(data);
			return timestampTextModel;
		}		
		else if (type.equals(XIData.XI_TIME_TYPE_DEF))
		{
			TimeTextModel timeTextModel = new TimeTextModel();
			timeTextModel.setData(data);
			return timeTextModel;
		}		
		else if (type.equals(XIData.XI_BIG_DECIMAL_TYPE_DEF))
		{
			BigDecimalTextModel bigDecimalTextModel = new BigDecimalTextModel();
			bigDecimalTextModel.setData(data);
			return bigDecimalTextModel;
		}
		else
		{
			return null;		
		}
	}
	
	public static boolean setDataToXIObject(String fieldType, Object currentData, Object newData)
	{
		if (fieldType.equals(XIData.XI_STRING_TYPE_DEF) || 
			fieldType.equals(XIData.XI_INTEGER_TYPE_DEF) || fieldType.equals(XIData.XI_LONG_TYPE_DEF) || fieldType.equals(XIData.XI_DOUBLE_TYPE_DEF) ||
			fieldType.equals(XIData.XI_DATE_TYPE_DEF) || fieldType.equals(XIData.XI_TIMESTAMP_TYPE_DEF) || fieldType.equals(XIData.XI_TIME_TYPE_DEF) ||
			fieldType.equals(XIData.XI_BIG_DECIMAL_TYPE_DEF))
		{
			((XIData)currentData).setData(newData);
			return true;
			
		}
		return false;
	}
}
