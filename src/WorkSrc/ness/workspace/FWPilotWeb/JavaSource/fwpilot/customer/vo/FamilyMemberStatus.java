/*
 * Created on: 06/02/2005
 * @author: baruch hizkya
 * @version $Id: FamilyMemberStatus.java,v 1.1 2005/02/21 17:07:22 baruch Exp $
 */

package fwpilot.customer.vo;

import fwpilot.general.vo.CodeTable;

/**
 * @author bhizkya
 *
 */
public class FamilyMemberStatus extends CodeTable
{

	/**
	 * @param code
	 * @param name
	 */
	public FamilyMemberStatus(Integer code, String name)
	{
		super(code, name);
	}


	public FamilyMemberStatus(CodeTable codeTable)
	{
		super(codeTable.getId(), codeTable.getName());
	}

}
