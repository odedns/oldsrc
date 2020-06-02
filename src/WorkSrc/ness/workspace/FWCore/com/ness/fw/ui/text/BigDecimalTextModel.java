package com.ness.fw.ui.text;

import java.math.BigDecimal;

import com.ness.fw.ui.TextModel;

public class BigDecimalTextModel extends TextModel 
{
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#setBigDecimal(java.math.BigDecimal)
	 */
	public void setBigDecimal(BigDecimal bigDecimalData) 
	{
		setData(bigDecimalData);	
	}
		
	/* (non-Javadoc)
	 * @see com.ness.fw.shared.common.XIData#getBigDecimal()
	 */
	public BigDecimal getBigDecimal() 
	{
		return (BigDecimal)data;
	}	
}
