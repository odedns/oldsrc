package fwpilot.customer.dao;

import java.util.*;

import com.ness.fw.bl.*;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.util.TypesUtil;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;
import fwpilot.customer.vo.*;

public class CustomerDAO extends LockableDAO implements Customer,Identifiable
{

	protected static String PROPS_FILE_NAME = "fwpilot/customer/dao/sql";

	private Integer id;
	private Integer identification;
	private String firstName;
	private String lastName;
	private String englishFirstName;
	private String englishLastName;
	private Date birthDate;
	private Integer telephone;
	private Integer fax;
	private Integer mobilePhone;
	private Integer type;

//	private Integer sex;
	SexStatus sex;

	private Integer numberOfChilds;
	private boolean smoking;

//	private Integer familyStatus;
	FamilyMemberStatus familyStatus;

	private LockParameters lockParameters = null;

	/**
	 * Holds all the customer FamilyMember’s 
	 */
	private DAOList familyMemberList;

	/**
	 * Holds all the customer addresses 
	 */
	private DAOList addressList;

	/**
	 * Holds all the customer addresses 
	 */
	private DAOList professionList;

	/**
	 * Default constructor
	 */
	public CustomerDAO()
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
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Customer.insert");

		setLockId(0); // start value

		List params = new ArrayList();
		params.add(identification);
		params.add(firstName);
		params.add(lastName);
		params.add(telephone);
		params.add(fax);
		params.add(birthDate);
		params.add(type);
		params.add(englishLastName);
		params.add(englishFirstName);
		params.add(mobilePhone);
		params.add(sex.getId());
		params.add(numberOfChilds);
		params.add(TypesUtil.convertBooleanToNumber(smoking));
		params.add(familyStatus == null ? null : familyStatus.getId());

		params.add(new Integer(getLockId()));
		
		params.add(new Integer(24));
		params.add(new Integer(24));

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
		params.add(identification);
		params.add(firstName);
		params.add(lastName);
		params.add(telephone);
		params.add(fax);
		params.add(birthDate);
		params.add(type);
		params.add(englishLastName);
		params.add(englishFirstName);
		params.add(mobilePhone);
		params.add(sex.getId());
		params.add(numberOfChilds);
		params.add(TypesUtil.convertBooleanToNumber(smoking));
		params.add(familyStatus.getId());
		params.add(new Integer(24));

		// keys
		params.add(id);

		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Customer.update"), params, transaction);
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
	
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "Customer.delete"), params, transaction);
	}


	/* (non-Javadoc)
	 * @see com.ness.fw.bl.DAO#deleteContainedObjects(com.ness.fw.persistence.Transaction, java.util.Map)
	 */
	protected void deleteContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{

		FamilyMemberDAO.deleteByCustomer(transaction,id, getCurrentBatch());
		AddressDAO.deleteByParentId(transaction, id, getCurrentBatch());
		CustomerProfessionDAO.deleteByCustomer(transaction, id, getCurrentBatch());
//		CustomerProfessionDAO.deleteByCustomer(transaction, id);
	}



	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("customerId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static CustomerDAO findByID(int id, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException, FatalException
	{
		CustomerDAO customer = null;
		String sqlStatement =
			cp.getProperty(PROPS_FILE_NAME, "Customer.genericSelect")
				+ cp.getProperty(PROPS_FILE_NAME, "Customer.findByID");
		SqlService ss = new SqlService(sqlStatement);

		// keys
		ss.addParameter(new Integer(id));

		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			customer = new CustomerDAO();
			customer.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Customer does not exist");
		}
		return customer;
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static CustomerDAO findByIdentification(int identification, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException, FatalException
	{
		CustomerDAO customer = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Customer.genericSelect")
			+ cp.getProperty(PROPS_FILE_NAME, "Customer.findByIdentification");
		SqlService ss = new SqlService(sqlStatement);

		// keys
		ss.addParameter(new Integer(identification));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			customer = new CustomerDAO();
			customer.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Customer does not exist");
		}
		return customer;
	}

	/**
	 * @param p
	 */
	private void loadObjectData(Page p) throws FatalException
	{
		setStateAsNonDirty();

		id = p.getInteger("ID");
		setLockId(p.getInt("LOCK_ID"));
		identification = p.getInteger("IDENTIFICATION");

		firstName = p.getString("FIRST_NAME");
		lastName = p.getString("LAST_NAME");
		englishFirstName = p.getString("ENGLISH_FIRST_NAME");
		englishLastName = p.getString("ENGLISH_LAST_NAME");
		telephone = TypesUtil.convertNumberToInteger(p.getObject("TELEPHONE"));
		fax = TypesUtil.convertNumberToInteger(p.getObject("FAX"));
		mobilePhone =
			TypesUtil.convertNumberToInteger(p.getObject("MOBILE_PHONE"));
		birthDate = p.getDate("BIRTH_DATE");
		type = p.getInteger("TYPE");

//		sex = p.getInteger("SEX");
		sex = SexVOFactory.getById(p.getInteger("SEX").intValue());

		numberOfChilds =
			TypesUtil.convertNumberToInteger(p.getObject("NUM_CHILDS"));
		smoking = TypesUtil.convertNumberToBoolean(p.getInt("SMOKING"));

//		familyStatus =
//			TypesUtil.convertNumberToInteger(p.getObject("FAMILY_STATUS"));

		if(p.getObject("FAMILY_STATUS") != null)
		{
			familyStatus = FamilyMemberVOFactory.getById(TypesUtil.convertNumberToInteger(p.getObject("FAMILY_STATUS")).intValue());
		}

	}


	// ********** Handle Customer addresses **************

	public Address createAddress()
	{
		Address address = new AddressDAO();
		return address;
	}


	private void initAddressIfNewlyCreated()
	{
		if (addressList == null && isNewlyCreated()) 
		{
			addressList = new DAOList();
		}		
	}

	public ValueObjectList getAddressList()
	{
		return addressList;
	}

	public void reloadAddressList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException 
	{
		initAddressList(cp);
	}

	public void loadAddressList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException 
	{
		if (addressList == null)
		{
			initAddressList(cp);
		}
	}

	private void initAddressList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException 
	{
		if (isNewlyCreated())
		{
			addressList = new DAOList();
		}
		else
		{
			addressList = AddressDAO.findByCustomer(id.intValue(), cp);
		}
	}


	public int getAddressCount()
	{
		initAddressIfNewlyCreated();
		return addressList.size();
	}

	public Address getAddress(Integer addressId)
	{
		initAddressIfNewlyCreated();
		return (Address)addressList.getIdentifiableById(addressId);		
	}

	public Address getAddressByUID(Integer uID)
	{
		initAddressIfNewlyCreated();
		return (Address)addressList.getByUID(uID);		
	}

	public void addAddress(Address address)
	{
		initAddressIfNewlyCreated();
		address.setType(new Integer(Address.TYPE_CUSTOMER));
		addressList.add((AddressDAO)address);
	}

	public boolean removeAddress(Address obj)
	{
		return ((AddressDAO)obj).setStateAsDeleted();
	}

	// ********** End Handle Customer addresses **************


	// ********** Handle Customer FamilyMember's **************

	public FamilyMember createFamilyMemeber()
	{
		FamilyMember familyMember = new FamilyMemberDAO();
		return familyMember;
	}

	private void initFamilyMemeberIfNewlyCreated()
	{
		if (familyMemberList == null && isNewlyCreated()) 
		{
			familyMemberList = new DAOList();
		}		
	}

	public void reloadFamilyMemberList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		initFamilyMemberList(cp);
	}


	public void loadFamilyMemberList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		if (familyMemberList == null)
		{
			initFamilyMemberList(cp);
		}
	}

	private void initFamilyMemberList(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		if (isNewlyCreated())
		{
			familyMemberList = new DAOList();
		}
		else
		{
			familyMemberList = FamilyMemberDAO.findByCustomer(id.intValue(), cp);
		}
	}

	public ValueObjectList getFamilyMemberList()
	{
		return familyMemberList;
	}


	public int getFamilyMembersCount()
	{
		initFamilyMemeberIfNewlyCreated();
		return familyMemberList.size();
	}

	public FamilyMember getFamilyMember(Integer familyMemberId)
	{
		initFamilyMemeberIfNewlyCreated();		
		return (FamilyMember)familyMemberList.getIdentifiableById(familyMemberId);		
	}

	public FamilyMember getFamilyMemberByUID(Integer uID) 
	{
		initFamilyMemeberIfNewlyCreated();		
		return (FamilyMember)familyMemberList.getByUID(uID);		
	}

	public void addFamilyMember(FamilyMember familyMember)
	{
		initFamilyMemeberIfNewlyCreated();		
		familyMemberList.add((FamilyMemberDAO)familyMember);
	}

	public boolean removeFamilyMember(FamilyMember obj)
	{
		initFamilyMemeberIfNewlyCreated();				
		return ((FamilyMemberDAO)obj).setStateAsDeleted();
	}

	// ********** End Handle Customer FamilyMember's **************


	// ********** Handle Customer professions **************

	private void initProfessionIfNewlyCreated()
	{
		if (professionList == null && isNewlyCreated()) 
		{
			professionList = new DAOList();
		}		
	}


	public ValueObjectList getProfessionList()
	{
		return professionList;
	}

	public void loadProfessionList(ConnectionProvider cp) throws PersistenceException
	{
		if (professionList == null)
		{
			initProfessionList(cp);
		}
	}

	private void reloadProfessionList(ConnectionProvider cp) throws PersistenceException
	{
		initProfessionList(cp);
	}


	private void initProfessionList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			professionList = new DAOList();
		}
		else
		{
			professionList = CustomerProfessionDAO.findByCustomer(id.intValue(), cp);
		}
	}

	public int getProfessionsCount() 
	{
		initProfessionIfNewlyCreated();
		return professionList.size();
	}

	public void addProfession(Integer professionId)
	{
		initProfessionIfNewlyCreated();
		CustomerProfessionDAO prof = new CustomerProfessionDAO();
		prof.setProfessionId(professionId);
		professionList.add(prof);
	}

	public boolean removeProfession(Integer professionId)
	{
		initProfessionIfNewlyCreated();
		boolean exist = false;

		for (int i = 0; i < professionList.size(); i++)
		{
			CustomerProfessionDAO current =
				(CustomerProfessionDAO) professionList.get(i);
			if (current.getProfessionId() == professionId)
			{
				current.setStateAsDeleted();
				exist = true;
				break;
			}
		}
		return exist;
	}

	public void setProfessions(List selectedIds) 
	{
		initProfessionIfNewlyCreated();
		
		List newSelectedIds = 
			professionList.setSelectedRelatedIdentifiables(selectedIds);		

		int newSelectedCount = newSelectedIds.size();
		for (int i = 0; i < newSelectedCount; i++)
		{
			// add new item to the list
			addProfession(((Integer)newSelectedIds.get(i)));
		}

	}

	public ArrayList getSelectedProfessions()
	{
		return professionList.getRelatedIdentifiableSelectedKeys();
	}


	// ********** End Handle Customer professions **************


	/**
	 * @return LockParameters  
	 */
	protected LockParameters getLockParameters ()
	{
		if (lockParameters == null)
		{
			List keys = new ArrayList(1);
			keys.add(getId());
			lockParameters =
				new LockParameters(
					keys,
					PROPS_FILE_NAME,
					"Customer",
					"customer");
		}
		return lockParameters;
	}
	
	public static Page findByName(String name, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{
		String sqlStatement =
			cp.getProperty(PROPS_FILE_NAME, "Customer.genericSelect")
				+ cp.getProperty(PROPS_FILE_NAME, "Customer.findByName");
		SqlService ss = new SqlService(sqlStatement);
	
		// keys
		ss.addLikeParameter(name, SqlService.LIKE_STARTS_WITH, cp);
	
		return Query.execute(ss, cp);
	}
	
	public static CustomerDAOManager findCustomers(ConnectionProvider cp) throws PersistenceException, FatalException
	{
		DAOList customers = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Customer.genericSelect");
		SqlService ss = new SqlService(sqlStatement);
				
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			CustomerDAO customer = new CustomerDAO();
			customer.loadObjectData(p);
			customers.add(customer);
		}

		CustomerDAOManager listManager = new CustomerDAOManager(customers);
		return listManager;
	}


	public static RandomPagingService findCustomers(Map criteriaFields, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{
		SqlService ss = new SqlService("");
		StringBuffer sb = new StringBuffer(1024);
		sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.main"));
	
	
		int likeType = SqlService.LIKE_CONTAINS;
		if(criteriaFields.get("likeType") != null)
			likeType = ((Number)criteriaFields.get("likeType")).intValue(); 
	
		if (criteriaFields.get("id") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.id"));
			ss.addParameter(criteriaFields.get("id"));
		}
	
		if (criteriaFields.get("type") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.type"));
			ss.addParameter(criteriaFields.get("type"));
		}
	
		if (criteriaFields.get("name") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.name"));
			ss.addLikeParameter(
				criteriaFields.get("name"), likeType, cp);
			ss.addLikeParameter(
				criteriaFields.get("name"), likeType, cp);
		}
	
		if (criteriaFields.get("city") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.city"));
			ss.addLikeParameter(
				criteriaFields.get("city"),likeType, cp);
		}
	
		if (criteriaFields.get("telephone") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.telephone"));
			ss.addParameter(criteriaFields.get("telephone"));
		}
	
		sb.append(cp.getProperty(PROPS_FILE_NAME, "Customer.findCustomers.order"));
		ss.setStatementString(sb.toString());
		
		RandomPagingService randomPagingService = new RandomPagingService(ss,10);
		return randomPagingService;
	}

	/** Activate the save operation on the contained objects.
	 *  can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws BusinessLogicException Any BusinessLogicException that may occur.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
		// save the modified FamilyMember's of the customer
	    saveDAOList(familyMemberList, transaction, keys);

		// save the addresses of the customer 
		saveDAOList(addressList, transaction, keys);

		// save the addresses of the customer 
		saveDAOList(professionList, transaction, keys);
	}


	public String getEnglishFirstName()
	{
		return englishFirstName;
	}
	
	public String getEnglishLastName()
	{
		return englishLastName;
	}
	
	public FamilyMemberStatus getFamilyStatus()
	{
		return familyStatus;
	}
	
	public Integer getMobilePhone()
	{
		return mobilePhone;
	}
	
	public Integer getNumberOfChilds()
	{
		return numberOfChilds;
	}
	
	public SexStatus getSex()
	{
		return sex;
	}
	
	public boolean isSmoking()
	{
		return smoking;
	}
	
	public Integer getType()
	{
		return type;
	}
	
	public void setEnglishFirstName(String string)
	{
		englishFirstName = string;
		setStateAsDirty();
	}
	
	public void setEnglishLastName(String string)
	{
		englishLastName = string;
		setStateAsDirty();
	}
	
	public void setFamilyStatus(FamilyMemberStatus memberStatus)
	{
		familyStatus = memberStatus;
		setStateAsDirty();
	}
	
	public void setMobilePhone(Integer integer)
	{
		mobilePhone = integer;
		setStateAsDirty();
	}
	
	public void setNumberOfChilds(Integer integer)
	{
		numberOfChilds = integer;
		setStateAsDirty();
	}
	
	public void setSex(SexStatus sexStatus)
	{
		sex = sexStatus;
		setStateAsDirty();
	}
	
	public void setSmoking(boolean b)
	{
		smoking = b;
		setStateAsDirty();
	}
	
	public void setType(Integer i)
	{
		type = i;
		setStateAsDirty();
	}
	
	public Integer getIdentification()
	{
		return identification;
	}
	
	public void setIdentification(Integer i)
	{
		identification = i;
		setStateAsDirty();
	}
	
	public Date getBirthDate()
	{
		return birthDate;
	}
	
	public Integer getFax()
	{
		return fax;
	}
	
	public String getName()
	{
		if (firstName != null)
			return lastName + " " + firstName;
		else
			return lastName;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public Integer getTelephone()
	{
		return telephone;
	}
	
	public void setBirthDate(Date date)
	{
		birthDate = date;
		setStateAsDirty();
	}
	
	public void setFax(Integer integer)
	{
		fax = integer;
		setStateAsDirty();
	}
	
	public void setFirstName(String string)
	{
		firstName = string;
		setStateAsDirty();
	}
	
	public void setLastName(String string)
	{
		lastName = string;
		setStateAsDirty();
	}
	
	public void setTelephone(Integer integer)
	{
		telephone = integer;
		setStateAsDirty();
	} 
		
}