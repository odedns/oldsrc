package fwpilot.agreement.vo;

import com.ness.fw.bl.ValueObject;

public interface Catalog extends ValueObject
{
	public static int MAIN_CATALOG = 1;
	
	public Integer getId();
	public String getName();
	public void setName(String name);
	public void setId(Integer id);
	
}
