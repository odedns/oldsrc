package fwpilot.customer.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ness.fw.persistence.exceptions.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.customer.vo.FamilyMember;
import fwpilot.customer.vo.RelatedTypeStatus;
import fwpilot.customer.vo.RelatedTypeVOFactory;
import fwpilot.customer.vo.SexStatus;
import fwpilot.customer.vo.SexVOFactory;

public class FamilyMemberDAO extends DAO implements FamilyMember,Identifiable
{

	protected static String PROPS_FILE_NAME = "fwpilot/customer/dao/sql";
	
	private Integer id;
	private Integer customerId;
	private String firstName;
	private String lastName;
	private Date birthDate;

	RelatedTypeStatus type;
//	private Integer type;
	
	SexStatus sex;
	//private Integer sex;
	
	private Integer identification;
	
	/**
	 * Holds all the familyMember addresses 
	 */
	private DAOList addressList;
	
	/**
	 * Default constructor
	 */
	public FamilyMemberDAO()
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
		params.add(identification);
		params.add(sex.getId());
		params.add(firstName);
		params.add(lastName);
		params.add(birthDate);
		params.add(type.getId());
		params.add(new Integer(24));
		params.add(new Integer(24));

		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "FamilyMember.insert");
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
		params.add(identification);
		params.add(sex.getId());
		params.add(firstName);
		params.add(lastName);
		params.add(birthDate);
		params.add(type.getId());
		params.add(new Integer(24));

		// keys
		params.add(id);
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "FamilyMember.update");
		executeStatement(sqlStatement, params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();
		// keys
		params.add(id);
		
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "FamilyMember.delete");
		executeStatement(sqlStatement, params, transaction);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys)
	{
		keys.put("familyMemberId", id);
	}


	/**
	 * @param p
	 */
	private void loadObjectData (Page p) throws FatalException
	{
		setStateAsNonDirty();
				
		id = p.getInteger("ID"); 
		customerId = p.getInteger("CUSTOMER_ID"); 
		firstName = p.getString("FIRST_NAME");
		lastName = p.getString("LAST_NAME");
		birthDate = p.getDate("BIRTH_DATE");

//		type = p.getInteger("TYPE"); 
		type = RelatedTypeVOFactory.getById(p.getInteger("TYPE").intValue());

		identification = p.getInteger("IDENTIFICATION"); 

		//sex = p.getInteger("SEX"); 
		sex = SexVOFactory.getById(p.getInteger("SEX").intValue());

	}

	/**
	 * @return
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @return
	 */
	public String getFirstName()
	{
		return firstName;
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
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param date
	 */
	public void setBirthDate(Date date)
	{
		birthDate = date;
		setStateAsDirty();
	}


	/**
	 * @param string
	 */
	public void setFirstName(String string)
	{
		firstName = string;
		setStateAsDirty();
	}

	/**
	 * @param string
	 */
	public void setLastName(String string)
	{
		lastName = string;
		setStateAsDirty();
	}

	/**
	 * @param familyMemberrenId  or keys ?????????????????????
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static FamilyMemberDAO findByID(int familyMemberId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		FamilyMemberDAO member = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "FamilyMember.genericSelect") 
		+ cp.getProperty(PROPS_FILE_NAME, "FamilyMember.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(familyMemberId));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			member = new FamilyMemberDAO();
			member.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("FamilyMember does not exist");
		}
		return member;
	}

	public static DAOList findByCustomer(int customerId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		DAOList members = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "FamilyMember.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "FamilyMember.findByCustomer"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(customerId));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			FamilyMemberDAO member = new FamilyMemberDAO();
			member.loadObjectData(p);
			members.add(member);
		}
		return members;
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	public static void deleteByCustomer(Transaction transaction, Integer customerId, Batch batch)
		throws PersistenceException
	{
		List params = new ArrayList(1);
		params.add(customerId);		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "FamilyMember.deleteByCustomer"), params, transaction,batch);
	}

	// ********** Handle familyMember addresses **************

	public DAOList getAddressList (ConnectionProvider cp) throws PersistenceException
	{
		loadAddressList(cp);
		return addressList; 
	}

	private void loadAddressList (ConnectionProvider cp) throws PersistenceException
	{
		if(addressList == null)
		{
			if(isNewlyCreated())
				addressList = new DAOList();
			else
				addressList = AddressDAO.findByChildren(id.intValue(), cp);
		}
	}

	private void checkAddress() throws PersistenceException
	{
		if(addressList == null)
		{
			ConnectionSequence seq = ConnectionSequence.beginSequence();
			loadAddressList(seq);
			seq.end();
		}
//			throw new PersistenceException("addressList was not loaded. call to getAddressList method");	
	}

	public int getAddressCount (ConnectionProvider cp) throws PersistenceException
	{
		checkAddress();
		return addressList.size(); 
	}


	public void addAddress (AddressDAO address, ConnectionProvider cp) throws PersistenceException
	{
		checkAddress();
		address.setType(new Integer(AddressDAO.TYPE_CHILD));
		addressList.add(address);
	}

	public boolean removeAddress (int addressId, ConnectionProvider cp) throws PersistenceException
	{
		checkAddress();

		boolean exist = false;
		
		for (int i = 0 ; i < addressList.size() ; i++)
		{
			AddressDAO current = (AddressDAO) addressList.get(i);
			if(current.getId().intValue() == addressId)
			{
				current.setStateAsDeleted();
				exist = true;
				break;
			}
		}
		return exist; 
	}
	
	// ********** End Handle familyMember addresses **************
		
	/** Activate the save operation on the contained objects.
	 *  can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
	
		// save the addresses of the customer
		saveDAOList(addressList, transaction, keys);
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
	public RelatedTypeStatus getType()
	{
		return type;
	}

	/**
	 * @param i
	 */
	public void setCustomerId(Integer i)
	{
		customerId = i;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setType(RelatedTypeStatus relatedTypeStatus )
	{
		type = relatedTypeStatus;
		setStateAsDirty();
	}

	/**
	 * @return
	 */
	public SexStatus getSex()
	{
		return sex;
	}

	/**
	 * @param i
	 */
	public void setSex(SexStatus sexStatus)
	{
		sex = sexStatus;
		setStateAsDirty();
	}

	/**
	 * @return
	 */
	public Integer getIdentification()
	{
		return identification;
	}

	/**
	 * @param i
	 */
	public void setIdentification(Integer i)
	{
		identification = i;
		setStateAsDirty();
	}

}
