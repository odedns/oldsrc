package com.ness.fw.shared.common;

import com.ness.fw.flower.common.FlowerTypeDefinitionConstants;

public interface XIData 
{
	public static final String XI_INTEGER_TYPE_DEF = FlowerTypeDefinitionConstants.XI_INTEGER;
	public static final String XI_DOUBLE_TYPE_DEF = FlowerTypeDefinitionConstants.XI_DOUBLE;
	public static final String XI_LONG_TYPE_DEF = FlowerTypeDefinitionConstants.XI_LONG;
	public static final String XI_DATE_TYPE_DEF = FlowerTypeDefinitionConstants.XI_DATE;
	public static final String XI_TIME_TYPE_DEF = FlowerTypeDefinitionConstants.XI_TIME;
	public static final String XI_TIMESTAMP_TYPE_DEF = FlowerTypeDefinitionConstants.XI_TIMESTAMP;
	public static final String XI_BIG_DECIMAL_TYPE_DEF = FlowerTypeDefinitionConstants.XI_BIG_DECIMAL;
	public static final String XI_STRING_TYPE_DEF = FlowerTypeDefinitionConstants.XI_STRING;
	
	public Object getData();
	public void setData(Object data);
}
