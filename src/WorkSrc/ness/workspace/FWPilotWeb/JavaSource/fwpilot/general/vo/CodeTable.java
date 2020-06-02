
package fwpilot.general.vo;

import java.io.Serializable;

import com.ness.fw.bl.Identifiable;

public class CodeTable implements CodeTableVO, Serializable, Identifiable
{
	private Integer code; 
	private String name; 

	public CodeTable(Integer code, String name)
	{
		this.code = code;
		this.name = name;
	}

	public Integer getId()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public Integer getUID()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDeleted()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDirty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isNewlyCreated()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
