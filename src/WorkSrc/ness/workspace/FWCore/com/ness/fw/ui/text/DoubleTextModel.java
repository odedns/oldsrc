package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

public class DoubleTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setDouble(java.lang.Double)
	 */
	public void setDouble(Double doubleData) 
	{
		setData(doubleData);
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getDouble()
	 */
	public Double getDouble() 
	{
		return (Double)data;
	}

}
