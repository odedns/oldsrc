package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import fwpilot.agreement.vo.Catalog;

public class CatalogDAO extends DAO implements Catalog,Identifiable
{
	
	private Integer id;
	private String name;
	
	private DAOList packageList;
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";


	/**
	 * Default constructor
	 */
	public CatalogDAO() throws PersistenceException
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
		List params = new ArrayList();		
		params.add(id);
		params.add(name);
		
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Catalog.insert"), params, transaction);
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
		params.add(transaction.getUserId());

		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Catalog.update"), params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		// keys
		List params = new ArrayList();		
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Catalog.delete"), params, transaction);
	}

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("catalogId", id);
	}

	/**
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static CatalogDAO findMainCatalog(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		return findByID(MAIN_CATALOG, cp);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static CatalogDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		CatalogDAO catalog = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Catalog.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Catalog.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			catalog = new CatalogDAO();
			catalog.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Catalog does not exist");
		}
		return catalog;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		name = p.getString("NAME");

	}


	// ********** Handle Catalog package's **************

	private void loadPackageList (ConnectionProvider cp) throws PersistenceException
	{
		if(packageList == null)
		{
			if(isNewlyCreated())
				packageList = new DAOList();
			else
				packageList = PackageDAO.findAll(cp);
		}
	}

	public DAOList getPackageList (ConnectionProvider cp) throws PersistenceException
	{
		loadPackageList(cp);
		return packageList; 
	}

	private void checkPackages() throws PersistenceException
	{
		if(packageList == null)
		{
			ConnectionSequence seq = ConnectionSequence.beginSequence();
			loadPackageList(seq);
			seq.end();
		}
	}
	
	public int getPackagesCount() throws PersistenceException
	{
		checkPackages();
		return packageList.size(); 
	}

	public PackageDAO getPackage (int packageId) throws PersistenceException
	{
		checkPackages();
		return (PackageDAO) packageList.getIdentifiableById(new Integer(packageId));
	}

	public void addPackage (PackageDAO pack) throws PersistenceException
	{
		checkPackages();
		packageList.add(pack);
	}

	public boolean removePackage (int packageId) throws PersistenceException
	{
		checkPackages();
		PackageDAO current = (PackageDAO) packageList.getIdentifiableById(new Integer(packageId));
		if(current != null)
		{
			current.setStateAsDeleted();
		}
		return current != null;
	}
	// ********** End Handle Catalog package's **************

	/** Activate the save operation on the contained objects.
	 *  can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
		// save the packages
		saveDAOList(packageList, transaction, keys);
	}

	public static Page getCatalogTree(ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Catalog.getCatalogTree");
		 
		SqlService ss = new SqlService(sqlStatement);

		return Query.execute(ss, cp);
	}

	public void setId(Integer i)
	{
		id = i;
		setStateAsDirty();
	}
	
	public Integer getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String string)
	{
		name = string;
		setStateAsDirty();
	}


}
