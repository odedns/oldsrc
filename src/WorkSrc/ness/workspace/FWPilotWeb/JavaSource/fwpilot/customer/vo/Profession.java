/*
 * Created on: 06/02/2005
 * @author: baruch hizkya
 * @version $Id: Profession.java,v 1.1 2005/02/21 17:07:22 baruch Exp $
 */

package fwpilot.customer.vo;

import fwpilot.general.vo.CodeTable;

/**
 * @author bhizkya
 *
 */
public class Profession extends CodeTable
{

	/**
	 * @param code
	 * @param name
	 */
	public Profession(Integer code, String name)
	{
		super(code, name);
	}


	public Profession(CodeTable codeTable)
	{
		super(codeTable.getId(), codeTable.getName());
	}

}
