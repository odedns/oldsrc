package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

public class LongTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setLong(java.lang.Long)
	 */
	public void setLong(Long longData) 
	{
		setData(longData);
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getLong()
	 */
	public Long getLong() 
	{
		return (Long)data;
	}
}
