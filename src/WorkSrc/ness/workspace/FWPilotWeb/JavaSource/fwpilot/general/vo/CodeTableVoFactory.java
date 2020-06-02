/*
 * Created on: 06/02/2005
 * @author: baruch hizkya
 * @version $Id: CodeTableVoFactory.java,v 1.1 2005/02/21 17:07:26 baruch Exp $
 */

package fwpilot.general.vo;

import java.util.List;

import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.cache.CacheManager;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.resources.SystemResources;

import fwpilot.general.bpc.PLCodeTableBPC;

/**
 * @author bhizkya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CodeTableVoFactory
{


	protected static List getAll(String entity) throws FatalException  
	{
		List values;
		try
		{
			if (SystemResources.getInstance().getString("useCache").equals("true"))
			{
				 values = CacheManager.getAll(entity);				 
			}
			else
			{
				PLCodeTableBPC contianer = new PLCodeTableBPC();
				contianer.setEntity(entity);
				BPOProxy.execute("getAllCmd",contianer);
				values = contianer.getValues();		
			}
		}
		catch (BPOCommandNotFoundException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BPOCommandException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BusinessLogicException e)
		{
			throw new FatalException("bpo",e);
		} 
		catch (ResourceException e)
		{
			throw new FatalException("resources",e);
		}

		return values;
	}

	protected static Object getById(String entity, Integer id) throws PersistenceException, FatalException 
	{
		CodeTable codeTable;
		try
		{
			if (SystemResources.getInstance().getString("useCache").equals("true"))
			{
				codeTable = (CodeTable)CacheManager.get(entity,id);
			}
			else
			{
				PLCodeTableBPC contianer = new PLCodeTableBPC();
				contianer.setEntity(entity);
				contianer.setId(id);
				BPOProxy.execute("getByIdCmd",contianer);
				codeTable = contianer.getCodeTable();						
			}
		}

		catch (BPOCommandNotFoundException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BPOCommandException e)
		{
			throw new FatalException("bpo",e);
		}
		catch (BusinessLogicException e)
		{
			throw new FatalException("bpo",e);
		} 
		catch (ResourceException e)
		{
			throw new FatalException("resources",e);
		}

		return codeTable;
	}


// ******************************
// These functions used for using db without cache
// ******************************
/*
	protected static CodeTable getById(String fileName, String prefix, int id) throws PersistenceException
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		CodeTable codeTable = findById(seq,  fileName,  prefix,  id);
		seq.end();
		return codeTable;
	}

	protected static List getAll(String fileName, String prefix) throws PersistenceException 
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		List list = findAll(fileName, prefix, seq);
		seq.end();
		return list;
	}

	protected static CodeTable findById(ConnectionSequence seq, String fileName, String prefix, int id) throws PersistenceException
	{
		CodeTable codeTable = null;
		String sqlStatement = seq.getProperty(fileName,prefix+ ".findById");
		SqlService ss = new SqlService(sqlStatement);	
		ss.addParameter(new Integer(id));	
		Page p = Query.execute(ss, seq);
		seq.end();
		while (p.next())
		{
			codeTable = new CodeTable(p.getInt("ID"), p.getString("NAME"));
		}
		return codeTable;
	}

	protected static List findAll(String fileName, String prefix, ConnectionSequence seq) throws PersistenceException 
	{ 
		List values = new ArrayList();
		String sqlStatement = seq.getProperty(fileName,prefix+ ".genericSelect");
		SqlService ss = new SqlService(sqlStatement);		
		Page p = Query.execute(ss, seq);
		seq.end();
		while (p.next())
		{
			values.add(new CodeTable(p.getInt("ID"), p.getString("NAME")));
		}
		return values;
	}
*/
}
