package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.*;

import fwpilot.agreement.vo.Package;
import fwpilot.agreement.vo.Program;

public class PackageDAO extends DAO implements Package,Identifiable
{
	private Integer id;
	
	private String name;
	private Integer minimalStartAge;
	private Integer maximalStartAge;
	private Integer endAge;
	private String description;
	
	private DAOList programList;
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";


	/**
	 * Default constructor
	 */
	public PackageDAO() throws PersistenceException
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
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Package.insert");

		List params = new ArrayList();
		params.add(name);
		params.add(minimalStartAge);
		params.add(maximalStartAge);
		params.add(endAge);
		params.add(description);
		
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		id = executeInsertStatement(sqlStatement, params, transaction, true);
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
		params.add(name);
		params.add(minimalStartAge);
		params.add(maximalStartAge);
		params.add(endAge);
		params.add(description);
		params.add(transaction.getUserId());

		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Package.update"), params, transaction);
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
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Package.delete"), params, transaction);
	}

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("packageId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static PackageDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		PackageDAO pack = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Package.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Package.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			pack = new PackageDAO();
			pack.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Package does not exist");
		}
		return pack;
	}

	public static DAOList findAll(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList packages = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Package.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Package.findAll"); 
		SqlService ss = new SqlService(sqlStatement);
				
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			PackageDAO pack = new PackageDAO();
			pack.loadObjectData(p);
			packages.add(pack);
		}
		
		return packages;
	}


	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		name = p.getString("NAME");
		description = p.getString("DESCRIPTION");
		minimalStartAge = p.getInteger("MINIMAL_START_AGE");
		maximalStartAge = p.getInteger("MAXIMAL_START_AGE");
		endAge = p.getInteger("END_AGE");

	}
	// ********** Handle Catalog package's **************

	private void initProgramsIfNewlyCreated()
	{
		if (programList == null && isNewlyCreated()) 
		{
			programList = new DAOList();
		}		
	}


	private void initProgramList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			programList = new DAOList();
		}
		else
		{
			programList = ProgramDAO.findByPackage(id.intValue(), cp);
		}
	}

	public void reloadProgramList (ConnectionProvider cp) throws PersistenceException
	{
		initProgramList(cp);
	}

	public void loadProgramList (ConnectionProvider cp) throws PersistenceException
	{
		if(programList == null)
		{
			initProgramList(cp);
		}
	}

	public ValueObjectList getProgramList()
	{
		return programList;
	}
	
	public int getProgramsCount () throws PersistenceException
	{
		initProgramsIfNewlyCreated();
		return programList.size(); 
	}

	public Program getProgram (Integer programId)
	{
		initProgramsIfNewlyCreated();
		return (ProgramDAO) programList.getIdentifiableById(programId);		
	}

	public void addProgram (ProgramDAO program) throws PersistenceException
	{
		initProgramsIfNewlyCreated();		
		programList.add(program);
	}

	public boolean removeProgram (int programId) throws PersistenceException
	{
		initProgramsIfNewlyCreated();		
		ProgramDAO current = (ProgramDAO) programList.getIdentifiableById(new Integer(programId));
		if(current != null)
		{
			current.setStateAsDeleted();
		}
		return current != null;
	}
	// ********** End Handle Package programs **************

	/** Activate the save operation on the contained objects.
	 *  can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
		// save the modified programs
		saveDAOList(programList, transaction, keys);
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
	public String getName()
	{
		return name;
	}


	public Integer getLockId() 
	{
		return new Integer(0);
	}

	public void lock(Transaction transaction)
		throws LockException 
	{
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

	/**
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string)
	{
		description = string;
		setStateAsDirty();
	}

}
