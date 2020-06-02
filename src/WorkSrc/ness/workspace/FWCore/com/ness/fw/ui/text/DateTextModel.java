package com.ness.fw.ui.text;

import java.util.Date;

import com.ness.fw.ui.TextModel;

public class DateTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setDate(java.util.Date)
	 */
	public void setDate(Date dateData) 
	{
		setData(dateData);
	}	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getDate()
	 */
	public Date getDate() 
	{
		return (Date)data;
	}	
}
