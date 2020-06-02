package fwpilot.general.dao;

import java.util.*;
import com.ness.fw.bl.DAO;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;

import fwpilot.general.vo.CodeTable;


public class CodeTableDAO extends DAO implements Identifiable
{
	
	public CodeTableDAO()
	{
		super();
	}


	
	protected static CodeTable findById(ConnectionProvider seq, String fileName, String prefix, int id) throws PersistenceException
	{
		CodeTable codeTable = null;
		String sqlStatement = seq.getProperty(fileName,prefix+ ".findById");
		SqlService ss = new SqlService(sqlStatement);	
		ss.addParameter(new Integer(id));	
		Page p = Query.execute(ss, seq);
		while (p.next())
		{
			codeTable = new CodeTable(p.getInteger("ID"), p.getString("NAME"));
		}
		return codeTable;
	}

	protected static List findAll(String fileName, String prefix, ConnectionProvider seq) throws PersistenceException 
	{ 
		List values = new ArrayList();
		String sqlStatement = seq.getProperty(fileName,prefix+ ".genericSelect");
		SqlService ss = new SqlService(sqlStatement);		
		Page p = Query.execute(ss, seq);
		while (p.next())
		{
			values.add(new CodeTable(p.getInteger("ID"), p.getString("NAME")));
		}
		return values;
	}

	protected void insert(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException
	{
		// not implemented

	}

	protected void update(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException
	{
		// not implemented

	}

	protected void delete(Transaction transaction, Map keys) throws PersistenceException, BusinessLogicException
	{
		// not implemented
	}

	protected void setKeyValues(Map keys)
	{
		// not implemented

	}


	public Integer getId()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
