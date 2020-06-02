/*
 * Created on: 06/02/2005
 * @author: baruch hizkya
 * @version $Id: Status.java,v 1.1 2005/02/21 17:07:23 baruch Exp $
 */

package fwpilot.agreement.vo;

import fwpilot.general.vo.CodeTable;

/**
 * @author bhizkya
 *
 */
public class Status extends CodeTable
{

	/**
	 * @param code
	 * @param name
	 */
	public Status(Integer code, String name)
	{
		super(code, name);
	}
	
	public Status(CodeTable codeTable)
	{
		super(codeTable.getId(), codeTable.getName());
	}

}
