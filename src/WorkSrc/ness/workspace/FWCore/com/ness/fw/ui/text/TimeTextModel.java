package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

import java.util.Date;

public class TimeTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setTime(java.sql.Time)
	 */
	public void setTime(Date timeData) 
	{
		setData(timeData);
	}
		
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getTime()
	 */
	public Date getTime() 
	{
		return (Date)data;
	}	
}
