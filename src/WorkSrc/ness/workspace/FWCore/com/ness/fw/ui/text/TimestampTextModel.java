package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

import java.util.Date;

public class TimestampTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setTimeStamp(java.sql.TimestampTextModel)
	 */
	public void setTimestamp(Date timestampData) 
	{
		setData(timestampData);
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getTimeStamp()
	 */
	public Date getTimestamp() 
	{
		return (Date)data;
	}		
}
