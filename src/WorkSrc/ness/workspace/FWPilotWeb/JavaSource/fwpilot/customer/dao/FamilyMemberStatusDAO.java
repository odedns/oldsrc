/*
 * Created on: 03/02/2005
 * @author: baruch hizkya
 * @version $Id: FamilyMemberStatusDAO.java,v 1.1 2005/02/21 17:07:28 baruch Exp $
 */

package fwpilot.customer.dao;

import java.util.*;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.ConnectionProvider;
import fwpilot.customer.vo.FamilyMemberStatus;
import fwpilot.general.dao.CodeTableDAO;
import fwpilot.general.vo.CodeTable;


public class FamilyMemberStatusDAO extends CodeTableDAO
{
	
	private static final String FILE_NAME = "fwpilot/customer/dao/sql";
	private static final String PREFIX    = "familyMemberStatus";
	
	public static FamilyMemberStatus findById(Integer id, ConnectionProvider seq) throws PersistenceException
	{
		CodeTable codeTable = findById(seq,FILE_NAME,PREFIX,id.intValue());
		return new FamilyMemberStatus(codeTable);
	}

	public static List findAll(ConnectionProvider seq) throws PersistenceException
	{
		List list = findAll(FILE_NAME,PREFIX,seq);
		List newList = new ArrayList();
		for (int i=0; i<list.size();i++)
		{
			CodeTable codeTable = (CodeTable)list.get(i);
			newList.add(new FamilyMemberStatus(codeTable));
		}
		
		return newList;
	}
}
