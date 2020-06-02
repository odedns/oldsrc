package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.agreement.vo.Program;
;

public class ProgramDAO extends DAO implements Program, Identifiable
{
	private Integer id;
	private Integer packageID;	
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";
	
	private String name;
	private Integer minimalStartAge;
	private Integer maximalStartAge;
	private Integer endAge;
	private String description;
	
	/**
	 * Default constructor
	 */
	public ProgramDAO() throws PersistenceException
	{
		super();
	}

	/** Insert this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @return Map The keys of the inserted object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void insert(Transaction transaction, Map keys)
		throws PersistenceException
	{
		packageID = ((Integer)keys.get("packageId"));
		
		List params = new ArrayList();
		params.add(packageID);
		params.add(name);
		params.add(minimalStartAge);
		params.add(maximalStartAge);
		params.add(endAge);
		params.add(description);
		
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		id = executeInsertStatement("Program.insert", params, transaction, true);
		
	}

	/** Update this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void update(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();
		params.add(packageID);
		params.add(name);
		params.add(minimalStartAge);
		params.add(maximalStartAge);
		params.add(endAge);
		params.add(description);
		params.add(transaction.getUserId());

		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Program.update"), params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();
		params.add(id);
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Program.delete"), params, transaction);
	}

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("programId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static ProgramDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		ProgramDAO program = null;
		
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Program.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Program.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			program = new ProgramDAO();
			program.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Program does not exist");
		}
		return program;
	}

	public static DAOList findByPackage(int programId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList programs = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Program.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Program.findByPackage"); 
		SqlService ss = new SqlService(sqlStatement);
		
		ss.addParameter(new Integer(programId));
				
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			ProgramDAO program = new ProgramDAO();
			program.loadObjectData(p);
			programs.add(program);
		}
		
		return programs;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		packageID = p.getInteger("PACKAGE_ID"); 
		name = p.getString("NAME");
		description = p.getString("DESCRIPTION");
		minimalStartAge = p.getInteger("MINIMAL_START_AGE");
		maximalStartAge = p.getInteger("MAXIMAL_START_AGE");
		endAge = p.getInteger("END_AGE");
	}

	/**
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}


	/**
	 * @return
	 */
	public Integer getPackageID()
	{
		return packageID;
	}

	/**
	 * @param i
	 */
	public void setPackageID(Integer i)
	{
		packageID = i;
		setStateAsDirty();
	}
	

	/**
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return
	 */
	public Integer getEndAge()
	{
		return endAge;
	}

	/**
	 * @return
	 */
	public Integer getMaximalStartAge()
	{
		return maximalStartAge;
	}

	/**
	 * @return
	 */
	public Integer getMinimalStartAge()
	{
		return minimalStartAge;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string)
	{
		description = string;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setEndAge(Integer i)
	{
		endAge = i;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setMaximalStartAge(Integer i)
	{
		maximalStartAge = i;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setMinimalStartAge(Integer i)
	{
		minimalStartAge = i;
		setStateAsDirty();
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
		setStateAsDirty();
	}

}
