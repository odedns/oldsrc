package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

public class IntegerTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setInteger(java.lang.Integer)
	 */
	public void setInteger(Integer intData) 
	{
		setData(intData);	
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getInteger()
	 */
	public Integer getInteger() 
	{
		return (Integer)data;
	}	
}
