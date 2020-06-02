/*
 * Created on: 06/02/2005
 * @author: baruch hizkya
 * @version $Id: TypeVOFactory.java,v 1.1 2005/02/21 17:07:23 baruch Exp $
 */

package fwpilot.agreement.vo;

import java.util.*;

import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.general.vo.CodeTable;
import fwpilot.general.vo.CodeTableVoFactory;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TypeVOFactory extends CodeTableVoFactory
{

	private static final String FILE_NAME = "fwpilot/agreement/dao/sql";
	private static final String PREFIX    = "type";
	
	public static Type getById(int id) throws FatalException 
	{
		CodeTable codeTable;
		try
		{
			codeTable = (CodeTable)getById(PREFIX,  new Integer(id));
		}
		catch (PersistenceException e)
		{
			throw new FatalException("persistance",e);
		}
		
		return new Type(codeTable.getId(),codeTable.getName()); 	 
	}
	
	public static List getAll() throws FatalException
	{
		List list;
		List newList = new ArrayList();

		list = getAll(PREFIX);
		for (int i=0; i<list.size(); i++)
		{
			CodeTable codeTable = (CodeTable)list.get(i);
			newList.add(new Type(codeTable.getId(),codeTable.getName()));			
		}

		return newList;
	}
}
