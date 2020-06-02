package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.bl.ValueObjectList;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.util.TypesUtil;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.agreement.vo.*;
import fwpilot.customer.dao.CustomerDAO;
import fwpilot.customer.vo.Customer;

public class AgreementDAO extends DAO implements Agreement,Identifiable
{

	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";

	private Integer id;

//	private Integer status = new Integer(STATUS_PRIMARY);
//	private Integer type = new Integer(TYPE_AGREEMENT);

	// should hold status objects insted of integers
	Status status = new Status(new Integer(STATUS_PRIMARY),"");
	Type type = new Type(new Integer(TYPE_AGREEMENT),"");

	private String name;
	private String description;
	private boolean automaticStart;
	private boolean canBeCanceled;
	private Date startDate;
	private Date endDate;
	private Integer minimalPeriod;
	private Integer customerType = new Integer(CUSTOMER_TYPE_GENERAL);
	private Integer customerId;

	private int lockId = 0;

	private CustomerDAO customer = null;

	private DAOList documentList;
	private DAOList packageList;
	private DAOList approvalList;
	private DAOList characteristicList;

	/**
	 * Default constructor
	 */
	public AgreementDAO()
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
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "Agreement.insert");
		lockId = 0; // start value

		List params = new ArrayList();
		params.add(status.getId());
		params.add(name);
		params.add(description);

		params.add(type.getId());
		params.add(TypesUtil.convertBooleanToNumber(automaticStart));
		params.add(TypesUtil.convertBooleanToNumber(canBeCanceled));
		params.add(startDate);
		params.add(endDate);
		params.add(minimalPeriod);
		params.add(customerType);
		params.add(customerId);

		params.add(new Integer(lockId));
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
		params.add(status.getId());
		params.add(name);
		params.add(description);
		params.add(type.getId());
		params.add(TypesUtil.convertBooleanToNumber(automaticStart));
		params.add(TypesUtil.convertBooleanToNumber(canBeCanceled));
		params.add(startDate);
		params.add(endDate);
		params.add(minimalPeriod);
		params.add(customerType);
		params.add(customerId);
		params.add(transaction.getUserId());

		// keys
		params.add(id);

		executeStatement(transaction.getProperty(PROPS_FILE_NAME,"Agreement.update"), params, transaction);
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

		executeStatement(transaction.getProperty(PROPS_FILE_NAME,"Agreement.delete"), params, transaction);
	}

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("agreementId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static AgreementDAO findByID(int id, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException, FatalException
	{
		AgreementDAO agreement = null;
		String sqlStatement =
			cp.getProperty(PROPS_FILE_NAME, "Agreement.genericSelect")
				+ cp.getProperty(PROPS_FILE_NAME, "Agreement.findByID");
		SqlService ss = new SqlService(sqlStatement);

		// keys
		ss.addParameter(new Integer(id));

		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			agreement = new AgreementDAO();
			agreement.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("Agreement does not exist");
		}
		return agreement;
	}

	private void loadObjectData(Page p) throws FatalException
	{
		setStateAsNonDirty();

		id = p.getInteger("ID");

//		status = p.getInteger("STATUS");
		status = StatusVOFactory.getById(p.getInteger("STATUS").intValue());

		name = p.getString("NAME");
		description = p.getString("DESCRIPTION");

//		type = p.getInteger("TYPE");
		type = TypeVOFactory.getById(p.getInteger("TYPE").intValue());

		automaticStart =
			TypesUtil.convertNumberToBoolean(p.getInt("AUTOMATIC_START"));
		canBeCanceled =
			TypesUtil.convertNumberToBoolean(p.getInt("CAN_BE_CANCELED"));
		startDate = p.getDate("START_DATE");
		endDate = p.getDate("END_DATE");
		minimalPeriod =
			TypesUtil.convertNumberToInteger(p.getObject("MINIMAL_PERIOD"));
		customerType = p.getInteger("CUSTOMER_TYPE");
		customerId =
			TypesUtil.convertNumberToInteger(p.getObject("CUSTOMER_ID"));

		lockId = p.getInt("LOCK_ID");
	}

	public static Page findByName(String name, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{
		String sqlStatement =
			cp.getProperty(PROPS_FILE_NAME, "Agreement.genericSelect")
				+ cp.getProperty(PROPS_FILE_NAME, "Agreement.findByName");
		SqlService ss = new SqlService(sqlStatement);

		// keys
		ss.addLikeParameter(name, SqlService.LIKE_STARTS_WITH, cp);

		return Query.execute(ss, cp);
	}

	public Page getAgreementPackagesTree(ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{		
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "Agreement.getAgreementPackagesTree");
		SqlService ss = new SqlService(sqlStatement);

		ss.addParameter(id);

		return Query.execute(ss, cp);
	}

	public static BasicPagingService findAgreements(Map criteriaFields, ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException
	{
		SqlService ss = new SqlService("");
		StringBuffer sb = new StringBuffer(1024);
		sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.main"));
		
		int likeType = SqlService.LIKE_CONTAINS;
		if(criteriaFields.get("likeType") != null)
			likeType = ((Number)criteriaFields.get("likeType")).intValue(); 
		 

		if (criteriaFields.get("id") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.id"));
			ss.addParameter(criteriaFields.get("id"));
		}

		if (criteriaFields.get("name") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.name"));
			ss.addLikeParameter(
				criteriaFields.get("name"), SqlService.LIKE_CONTAINS, cp);
		}

		if (criteriaFields.get("description") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.description"));
			ss.addLikeParameter(
				criteriaFields.get("description"), likeType, cp);
		}

		if (criteriaFields.get("customerId") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.customerId"));
			ss.addParameter(criteriaFields.get("customerId"));
		}

		if (criteriaFields.get("type") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.type"));
			ss.addParameter(criteriaFields.get("type"));
		}

		if (criteriaFields.get("status") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.status"));
			ss.addParameter(criteriaFields.get("status"));
		}

		if (criteriaFields.get("date") != null)
		{
			sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.dates"));
			ss.addParameter(criteriaFields.get("date"));
		}

		sb.append(cp.getProperty(PROPS_FILE_NAME, "Agreement.findAgreements.order"));
		ss.setStatementString(sb.toString());

		SequentialPagingService pagingService = new SequentialPagingService(ss, 10);
		pagingService.addPagingKey("A.ID");
		pagingService.addOrderedPagingKey("A.NAME",AbstractSequentialPagingService.ASCEND);
		
		return pagingService;

	}

	// ********** Handle Agreement documents **************

	//------------------------------------------------------

	/**
	 * inits the DocumentDetail DAOList when this Agreement is 
	 * newly created
	 */
	private void initDocumentsIfNewlyCreated()
	{
		if (documentList == null && isNewlyCreated()) 
		{
			documentList = new DAOList();
		}		
	}

	public void reloadDocumentList(ConnectionProvider cp) throws PersistenceException
	{
		initDocumentList(cp);
	}


	public void loadDocumentList(ConnectionProvider cp) throws PersistenceException
	{
		if (documentList == null)
		{
			initDocumentList(cp);
		}
	}

	private void initDocumentList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			documentList = new DAOList();
		}
		else
		{
			documentList = AttachedDocumentDAO.findByAgreement(id.intValue(), cp);
		}
	}

	public ValueObjectList getDocumentList()
	{
		return documentList;
	}


	public int getDocumentsCount()
	{
		initDocumentsIfNewlyCreated();
		return documentList.size();
	}

	public int getNonDeletedDocumentsCount()
	{
		initDocumentsIfNewlyCreated();
		int count = 0;
		for (int i = 0; i < documentList.size(); i++)
		{
			AttachedDocumentDAO current = (AttachedDocumentDAO) documentList.get(i);
			if (!current.isDeleted())
			{
				count++;
			}
		}

		return count;
	}

	public AttachedDocument getDocument(Integer documentId)
	{
		initDocumentsIfNewlyCreated();		
		return (AttachedDocument)documentList.getIdentifiableById(documentId);		
	}

	public AttachedDocument getDocumentByUID(Integer UId)
	{
		initDocumentsIfNewlyCreated();		
		return (AttachedDocument)documentList.getByUID(UId);
	}

	/**
	 * @param index
	 * @return documentDetail according to index in the List
	 */
	public AttachedDocument getDocumentByIndex(int index) 
	{
		initDocumentsIfNewlyCreated();
		if(index<documentList.size())
		 {
			return (AttachedDocument)documentList.get(index);	
		}
		
		return null;
	}

	public void addDocument(AttachedDocument doc)
	{
		initDocumentsIfNewlyCreated();
		documentList.add((AttachedDocumentDAO)doc);
	}

	public boolean removeDocument(AttachedDocument obj)
	{
		initDocumentsIfNewlyCreated();
		return ((AttachedDocumentDAO)obj).setStateAsDeleted();
	}
	
	public void removeAllDocuments()
	{
		documentList.setAllAsDeleted();
	}

	public AttachedDocument createDocument()
	{
		AttachedDocument doc = new AttachedDocumentDAO();
		doc.setSystem(new Integer(AttachedDocument.SYSTEM_DIMUT));
		return doc;
	}

	// ********** End Handle Agreement documents **************




	// ********** Handle Agreement Packages **************

	public void reloadPackageList(ConnectionProvider cp) throws PersistenceException
	{
		initPackageList(cp);
	}
	
	public void loadPackageList(ConnectionProvider cp) throws PersistenceException
	{
		if (packageList == null)
		{
			initPackageList(cp);
		}
	}

	private void initPackageList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			packageList = new DAOList();
		}
		else
		{
			packageList = AgreementPackageDAO.findByAgreement(id.intValue(), cp);
		}
	}

	public ValueObjectList getPackagesList()
	{
		return packageList;
	}

	/**
	 * inits the packagesDetail DAOList when this Agreement is 
	 * newly created
	 */
	private void initPackagesIfNewlyCreated()
	{
		if (packageList == null && isNewlyCreated()) 
		{
			packageList = new DAOList();
		}		
	}

	public int getPackagesCount()
	{
		initPackagesIfNewlyCreated();
		return packageList.size();
	}

	public int getNonDeletedPackagesCount()
	{
		initPackagesIfNewlyCreated();

		int count = 0;
		for (int i = 0; i < packageList.size(); i++)
		{
			AgreementPackageDAO current = (AgreementPackageDAO) packageList.get(i);
			if (!current.isDeleted())
			{
				count++;
			}
		}
		return count;
	}

	public AgreementPackage getPackage(Integer packageId)
	{
		initPackagesIfNewlyCreated();
		return (AgreementPackageDAO) packageList.getIdentifiableById(packageId);		
	}

	public void addPackage(AgreementPackage pack) 
	{
		initPackagesIfNewlyCreated();
		packageList.add((AgreementPackageDAO)pack);
	}

	public boolean removePackage(AgreementPackage obj)
	{
		initPackagesIfNewlyCreated();
		return ((AgreementPackageDAO)obj).setStateAsDeleted();
	}

	public AgreementPackage createAgreementPackage()
	{
		AgreementPackage ap = null;
		ap = new AgreementPackageDAO();
		return ap;
		
	}


	// ********** End Handle Agreement Packages **************


	// ********** Handle Agreement Approvals **************

	public void reloadApprovalList(ConnectionProvider cp) throws PersistenceException
	{
		initApprovalList(cp);
	}
	
	public void loadApprovalList(ConnectionProvider cp) throws PersistenceException
	{
		if (approvalList == null)
		{
			initApprovalList(cp);
		}
	}

	private void initApprovalList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			approvalList = new DAOList();
		}
		else
		{
			approvalList = AgreementApprovalDAO.findByAgreement(id.intValue(), cp);
		}
	}

	public ValueObjectList getApprovalList()
	{
		return approvalList;
	}

	/**
	 * inits the approvalDetail DAOList when this Agreement is 
	 * newly created
	 */
	private void initApprovalIfNewlyCreated()
	{
		if (approvalList == null && isNewlyCreated()) 
		{
			approvalList = new DAOList();
		}		
	}

	public int getApprovalsCount()
	{
		initApprovalIfNewlyCreated();
		return approvalList.size();
	}

	public int getNonDeletedApprovalsCount()
	{
		initApprovalIfNewlyCreated();
		int count = 0;
		for (int i = 0; i < approvalList.size(); i++)
		{
			AgreementApprovalDAO current = (AgreementApprovalDAO) approvalList.get(i);
			if (!current.isDeleted())
			{
				count++;
			}
		}
		return count;
	}

	public AgreementApproval getApproval(Integer approvalId)
	{
		initApprovalIfNewlyCreated();
		return (AgreementApproval) approvalList.getIdentifiableById(approvalId);
	}

	public void addApproval(AgreementApproval approval)
	{
		initApprovalIfNewlyCreated();
		approvalList.add((AgreementApprovalDAO)approval);
	}

	public boolean removeApproval(AgreementApproval obj)
	{
		return ((AgreementApprovalDAO)obj).setStateAsDeleted();
	}
	// ********** End Handle Agreement Approvals **************


	// ********** Handle Agreement Characteristics **************


	public void reloadCharacteristicList(ConnectionProvider cp) throws PersistenceException
	{
		initCharacteristicList(cp);
	}
	
	public void loadCharacteristicList(ConnectionProvider cp) throws PersistenceException
	{
		if (characteristicList == null)
		{
			initCharacteristicList(cp);
		}
	}
	
	private void initCharacteristicList(ConnectionProvider cp) throws PersistenceException
	{
		if (isNewlyCreated())
		{
			characteristicList = new DAOList();
		}
		else
		{
			characteristicList = AgreementCharacteristicsDAO.findByAgreement(id.intValue(), cp);
		}
	}

	public ValueObjectList getCharacteristicList()
	{
		return characteristicList;
	}
	

	/**
	 * inits the characteristicDetail DAOList when this Agreement is 
	 * newly created
	 */
	private void initCharacteristicsIfNewlyCreated()
	{
		if (characteristicList == null && isNewlyCreated()) 
		{
			characteristicList = new DAOList();
		}		
	}


	public int getCharacteristicsCount()
	{
		initCharacteristicsIfNewlyCreated();
		return characteristicList.size();
	}

	public int getNonDeletedCharacteristicsCount()
	{
		initCharacteristicsIfNewlyCreated();

		int count = 0;
		for (int i = 0; i < characteristicList.size(); i++)
		{
			AgreementCharacteristicsDAO current =
				(AgreementCharacteristicsDAO) characteristicList.get(i);
			if (!current.isDeleted())
			{
				count++;
			}
		}
		return count;
	}

	public AgreementCharacteristics getCharacteristic(Integer charId)
	{
		initCharacteristicsIfNewlyCreated();	
		return (AgreementCharacteristics) characteristicList.getIdentifiableById(charId);
	}

	// TODO : check why it isn't an object, but id
	public void addCharacteristic(Integer charId)
	{
		initCharacteristicsIfNewlyCreated();			
		AgreementCharacteristicsDAO obj = new AgreementCharacteristicsDAO();
		obj.setCharId(charId);
		characteristicList.add(obj);
	}

	// TODO : check why it isn't an object, but id
	public boolean removeCharacteristic(Integer charId)
	{
		initCharacteristicsIfNewlyCreated();					
		AgreementCharacteristicsDAO current = (AgreementCharacteristicsDAO) characteristicList.getIdentifiableById(charId);
		if(current != null)
		{
			current.setStateAsDeleted();
		}
		return current != null;
	}

	public void setCharacteristics(List selectedIds)
	{
		initCharacteristicsIfNewlyCreated();					
		
		List newSelectedIds = characteristicList.setSelectedRelatedIdentifiables(selectedIds);		

		int newSelectedCount = newSelectedIds.size();
		for (int i = 0; i < newSelectedCount; i++)
		{
			// add new item to the list
			addCharacteristic(((Integer)newSelectedIds.get(i)));
		}
	}

	// ********** End Handle Agreement Characteristic **************

	/** Activate the save operation on the contained objects.
	 *  can use the savePersistables method.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{
		
		// save the modified documents of the agreement
		saveDAOList(documentList,transaction, keys);

		// save the modified packages of the agreement
		saveDAOList(packageList,transaction, keys);

		// save the modified approvals of the agreement
		saveDAOList(approvalList,transaction, keys);

		// save the modified Characteristics of the agreement
		saveDAOList(characteristicList,transaction, keys);
	}


	/* ******************************************************* */
	/* Setters & Getters */
	/* ******************************************************* */

	/**
	 * @return
	 */
	public boolean isAutomaticStart()
	{
		return automaticStart;
	}

	/**
	 * @return
	 */
	public boolean isCanBeCanceled()
	{
		return canBeCanceled;
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
	public Integer getCustomerType()
	{
		return customerType;
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
	public Date getEndDate()
	{
		return endDate;
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
	public Integer getMinimalPeriod()
	{
		return minimalPeriod;
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
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @return
	 */
	public Status getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @param b
	 */
	public void setAutomaticStart(boolean b)
	{
		automaticStart = b;
		setStateAsDirty();
	}

	/**
	 * @param b
	 */
	public void setCanBeCanceled(boolean b)
	{
		canBeCanceled = b;
		setStateAsDirty();
	}

	/**
	 * @param integer
	 */
	public void setCustomerId(Integer integer)
	{
		customerId = integer;
		customer = null;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setCustomerType(Integer i)
	{
		customerType = i;
		setStateAsDirty();
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
	 * @param date
	 */
	public void setEndDate(Date date)
	{
		endDate = date;
		setStateAsDirty();
	}

	/**
	 * @param integer
	 */
	public void setMinimalPeriod(Integer integer)
	{
		minimalPeriod = integer;
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
	 * @param date
	 */
	public void setStartDate(Date date)
	{
		startDate = date;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setStatus(Status status)
	{
		this.status = status;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setType(Type type)
	{
		this.type = type;
		setStateAsDirty();
	}

	/**
	 * @return
	 */

	public void reloadCustomer(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		if (customerId != null)
		{
			customer = CustomerDAO.findByID(customerId.intValue(), cp);
		}
	}


	public void loadCustomer(ConnectionProvider cp)
		throws ObjectNotFoundException, PersistenceException, FatalException
	{
		if (customer == null && customerId != null)
		{
			customer = CustomerDAO.findByID(customerId.intValue(), cp);
		}
	}

	public Customer getCustomer()
	{
		return customer;
	}
	
	
	/* (non-Javadoc)
	 * @see com.ness.fw.bl.DAO#deleteContainedObjects(com.ness.fw.persistence.Transaction, java.util.Map)
	 */
	protected void deleteContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException, FatalException
	{

		AgreementPackageDAO.deleteByParentId(id,transaction, getCurrentBatch());
		AttachedDocumentDAO.deleteByParentId(id,transaction, getCurrentBatch());
		AgreementApprovalDAO.deleteByParentId(id,transaction, getCurrentBatch());
		AgreementCharacteristicsDAO.deleteByParentId(id,transaction, getCurrentBatch());
	}
}
