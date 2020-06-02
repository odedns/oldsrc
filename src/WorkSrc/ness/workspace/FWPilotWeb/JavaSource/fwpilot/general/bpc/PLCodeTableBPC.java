package fwpilot.general.bpc;

import java.util.List;

import com.ness.fw.bl.BusinessProcessContainer;

import fwpilot.general.vo.CodeTable;

public class PLCodeTableBPC extends BusinessProcessContainer
{
	String entity;
	Integer id;
	List values;
	CodeTable codeTable;

	public String getEntity()
	{
		return entity;
	}

	public void setEntity(String string)
	{
		entity = string;
	}

	public List getValues()
	{
		return values;
	}

	public void setValues(List list)
	{
		values = list;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public CodeTable getCodeTable()
	{
		return codeTable;
	}

	public void setCodeTable(CodeTable table)
	{
		codeTable = table;
	}

}
