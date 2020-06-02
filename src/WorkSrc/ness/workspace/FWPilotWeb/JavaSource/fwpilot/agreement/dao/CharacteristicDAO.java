/*
 * Created on: 03/02/2005
 * @author: baruch hizkya
 * @version $Id: CharacteristicDAO.java,v 1.1 2005/02/21 17:07:27 baruch Exp $
 */

package fwpilot.agreement.dao;

import java.util.ArrayList;
import java.util.List;

import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.persistence.ConnectionProvider;
import fwpilot.agreement.vo.Characteristic;
import fwpilot.general.dao.CodeTableDAO;
import fwpilot.general.vo.CodeTable;


public class CharacteristicDAO extends CodeTableDAO
{
	
	private static final String FILE_NAME = "fwpilot/agreement/dao/sql";
	private static final String PREFIX    = "characteristic";
	
	public static Characteristic findById(Integer id, ConnectionProvider seq) throws PersistenceException
	{
		CodeTable codeTable = findById(seq,FILE_NAME,PREFIX,id.intValue());
		return new Characteristic(codeTable);
	}

	public static List findAll(ConnectionProvider seq) throws PersistenceException
	{
		List list = findAll(FILE_NAME,PREFIX,seq);
		List newList = new ArrayList();
		for (int i=0; i<list.size();i++)
		{
			CodeTable codeTable = (CodeTable)list.get(i);
			newList.add(new Characteristic(codeTable));
		}
		
		return newList;
	}

	
}
