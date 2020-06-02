/*
 * Created on 26/11/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fwpilot.general.vo;

import java.io.Serializable;

/**
 * @author yharnof
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CodeTableValue implements Serializable
{
	private int code; 
	private String name; 
	private String tableId;

	/**
	 * 
	 */
	public CodeTableValue(int code, String name, String tableId)
	{
		this.code = code;
		this.name = name;
		this.tableId = tableId;
	}

	/**
	 * @return
	 */
	public int getCode()
	{
		return code;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getTableId()
	{
		return tableId;
	}

}
