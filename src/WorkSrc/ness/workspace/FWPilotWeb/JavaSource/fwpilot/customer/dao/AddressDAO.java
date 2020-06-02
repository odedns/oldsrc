package fwpilot.customer.dao;

import java.util.*;
import com.ness.fw.persistence.exceptions.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.util.TypesUtil;
import com.ness.fw.common.exceptions.PersistenceException;
import fwpilot.customer.vo.Address;

public class AddressDAO extends DAO implements Address, Identifiable
{

	protected static String PROPS_FILE_NAME = "fwpilot/customer/dao/sql";

	private Integer id = new Integer(-1);
	private Integer parentId = new Integer(-1);
	private String city;
	private String street;
	private Integer streetNumber;
	private Integer type;
	private boolean main;
	private Integer telephone;
	private Integer fax;
	
	
	/**
	 * create new empty Customer object 
	 */
	public  AddressDAO()
	{
		super();
	}

	/** Insert this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @return Map The keys of the inserted object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	protected void insert(Transaction transaction, Map keys) throws PersistenceException
		
	{

		if(type.intValue() == TYPE_CUSTOMER)
			parentId = ((Integer)keys.get("customerId"));
		else if(type.intValue() == TYPE_CHILD)
			parentId = ((Integer)keys.get("familyMemberId"));

		List params = new ArrayList();
		params.add(parentId);
		params.add(city);
		params.add(street);
		params.add(streetNumber);
		params.add(type);
		params.add(TypesUtil.convertBooleanToNumber(main));
		params.add(telephone);
		params.add(fax);
		params.add(new Integer(24));
		params.add(new Integer(24));
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Address.insert");
		id = executeInsertStatement(sqlStatement,params, transaction, true);
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
		params.add(city);
		params.add(street);
		params.add(streetNumber);
		params.add(type);
		params.add(TypesUtil.convertBooleanToNumber(main));
		params.add(telephone);
		params.add(fax);
		params.add(new Integer(24));

		// keys
		params.add(id);
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Address.update");
		executeStatement(sqlStatement, params, transaction);
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
		// keys
		params.add(id);
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Address.delete");
		executeStatement(sqlStatement, params, transaction);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("addressId", id);
	}

	/**
	 * @param p
	 */
	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
				
		id = p.getInteger("ID"); 
		parentId = p.getInteger("PARENT_ID"); 
		city = p.getString("CITY");
		street = p.getString("STREET");
		streetNumber = (Integer) p.getObject("STREET_NUMBER");
		type = p.getInteger("TYPE");
		main = TypesUtil.convertNumberToBoolean(p.getInt("MAIN"));
		telephone = TypesUtil.convertNumberToInteger(p.getObject("TELEPHONE"));
		fax = TypesUtil.convertNumberToInteger(p.getObject("FAX"));
		 		
	}

	/**
	 * @param childrenId  or keys ?????????????????????
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static AddressDAO findByID(int addressId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		AddressDAO address = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Address.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Address.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(addressId));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			address = new AddressDAO(); 
			address.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Address does not exist");
		}
		return address;
	}

	public static DAOList findByCustomer(int customerId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		return findByParentId(customerId, TYPE_CUSTOMER, cp);
	}

	public static DAOList findByChildren(int childrenId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		return findByParentId(childrenId, TYPE_CHILD, cp);
	}
	
	private static DAOList findByParentId(int parentId, int type, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList childrens = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Address.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "Address.findByParentId"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(parentId));
		ss.addParameter(new Integer(type));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			AddressDAO child = new AddressDAO();
			child.loadObjectData(p);
			childrens.add(child);
		}
		
		return childrens;
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public static void deleteByParentId(Transaction transaction, Integer parentId, Batch batch) throws PersistenceException
	{
		List params = new ArrayList(1);
		params.add(parentId);
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Address.deleteByParentId"), params, transaction, batch);
	}

	/**
	 * @return
	 */
	public String getCity()
	{
		return city;
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
	public Integer getParentId()
	{
		return parentId;
	}

	/**
	 * @return
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * @return
	 */
	public Integer getStreetNumber()
	{
		return streetNumber;
	}

	/**
	 * @return
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param string
	 */
	public void setCity(String string)
	{
		city = string;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setId(Integer i)
	{
		id = i;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setParentId(Integer i)
	{
		parentId = i;
		setStateAsDirty();
	}

	/**
	 * @param string
	 */
	public void setStreet(String string)
	{
		street = string;
		setStateAsDirty();
	}

	/**
	 * @param integer
	 */
	public void setStreetNumber(Integer integer)
	{
		streetNumber = integer;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setType(Integer i)
	{
		type = i;
		setStateAsDirty();
	}

	/**
	 * @return
	 */
	public boolean isMain()
	{
		return main;
	}

	/**
	 * @param b
	 */
	public void setMain(boolean b)
	{
		main = b;
		setStateAsDirty();
	}

	/**
	 * @return
	 */
	public Integer getFax()
	{
		return fax;
	}

	/**
	 * @return
	 */
	public Integer getTelephone()
	{
		return telephone;
	}

	/**
	 * @param integer
	 */
	public void setFax(Integer integer)
	{
		fax = integer;
		setStateAsDirty();
	}

	/**
	 * @param integer
	 */
	public void setTelephone(Integer integer)
	{
		telephone = integer;
		setStateAsDirty();
	}

}
