package com.ness.fw.ui.text;

import com.ness.fw.ui.TextModel;

public class StringTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setString(java.lang.String)
	 */
	public void setString(String stringData) 
	{
		setData(stringData);
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getString()
	 */
	public String getString() 
	{
		return (String)data;
	}	
}
