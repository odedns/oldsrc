package fwpilot.customer.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.RelatedIdentifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.*;
import fwpilot.customer.vo.CustomerProfession;

/**
 * @author yharnof
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CustomerProfessionDAO extends DAO implements CustomerProfession,RelatedIdentifiable
{
	private Integer customerId;
	private Integer professionId;
	private String name;
	
	protected static String PROPS_FILE_NAME = "fwpilot/customer/dao/sql";

	/**
	 * Default constructor
	 */
	public CustomerProfessionDAO()
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
		customerId = ((Integer)keys.get("customerId"));
		
		List params = new ArrayList();
		params.add(customerId);
		params.add(professionId);
		params.add(new Integer(24));
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "CustomerProfession.insert");
		executeStatement(sqlStatement,params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		SqlService ss = new SqlService(transaction.getProperty(PROPS_FILE_NAME, "CustomerProfession.delete"));
		// keys
		ss.addParameter(customerId);
		ss.addParameter(professionId);
		
		transaction.execute(ss);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected static void deleteByCustomer(Transaction transaction, Integer customerId, Batch batch)
		throws PersistenceException
	{
		// keys
		List params = new ArrayList(1);
		params.add(customerId);
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "CustomerProfession.deleteByCustomer"),params,transaction,batch);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected static void deleteByCustomer(Transaction transaction, Integer customerId)
		throws PersistenceException
	{
		SqlService ss = new SqlService(transaction.getProperty(PROPS_FILE_NAME, "CustomerProfession.deleteByCustomer"));
		// keys
		ss.addParameter(customerId);	
		transaction.execute(ss);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys)
	{
		keys.put("professionId", professionId);
	}

	protected void update(Transaction transaction, Map keys) throws PersistenceException
	{
		
	}

	public static DAOList findByCustomer(int customerId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList profs = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "CustomerProfession.findByCustomer"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(customerId));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			CustomerProfessionDAO prof = new CustomerProfessionDAO();
			prof.loadObjectData(p);
			profs.add(prof);
		}
		return profs;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		customerId = p.getInteger("CUSTOMER_ID"); 
		professionId = p.getInteger("PROFESSION_ID"); 
		name = p.getString("NAME");

	}

	/**
	 * @return
	 */
	public Integer getRelatedId()
	{
		return getProfessionId();
	}

	/**
	 * @return
	 */
	public Integer getCustomerId()
	{
		return customerId;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public Integer getProfessionId()
	{
		return professionId;
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
	 * @param i
	 */
	public void setProfessionId(Integer i)
	{
		professionId = i;
		setStateAsDirty();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return " Customer Profession: professionId = " + professionId  + " name = " + name;  
	}

}
