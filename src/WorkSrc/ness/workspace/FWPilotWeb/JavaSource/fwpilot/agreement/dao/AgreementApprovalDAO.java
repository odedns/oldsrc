package fwpilot.agreement.dao;

import java.util.*;
import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;
import fwpilot.agreement.vo.AgreementApproval;

public class AgreementApprovalDAO extends DAO implements AgreementApproval,Identifiable
{
	protected Integer id;
	protected Integer agreementId;
	protected Integer type;
	protected Integer status;
	protected Date approvalDate;
	
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";

	/**
	 * Default constructor
	 */
	public AgreementApprovalDAO()
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
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "AgreementApproval.insert");
		
		agreementId = ((Integer)keys.get("agreementId"));

		List params = new ArrayList();
		params.add(agreementId);
		params.add(type);
		params.add(status);
		params.add(approvalDate);
		
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
		params.add(agreementId);
		params.add(type);
		params.add(status);
		params.add(approvalDate);
		params.add(transaction.getUserId());

		// keys
		params.add(id);

		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementApproval.update"), params, transaction);		
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
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementApproval.delete"), params, transaction);		
	}

	protected static void deleteByParentId(Integer parentId, Transaction transaction, Batch batch) throws PersistenceException
	{
		List params = new ArrayList();
		params.add(parentId);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementApproval.deleteByParentId"), 
			params, transaction, batch);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys)
	{
		keys.put("agreementApprovalId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static AgreementApprovalDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		AgreementApprovalDAO approval = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AgreementApproval.genericSelect") 
		+ cp.getProperty(PROPS_FILE_NAME, "AgreementApproval.findByID"); 

		SqlService ss = new SqlService(sqlStatement);
		
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			approval = new AgreementApprovalDAO();
			approval.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("AgreementApproval does not exist");
		}
		return approval;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		agreementId = p.getInteger("AGREEMENT_ID");
		type = p.getInteger("TYPE");
		status = p.getInteger("STATUS");
		approvalDate = p.getDate("APPROVAL_DATE");
	}

	public static DAOList findByAgreement(int agreementId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList documents = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AgreementApproval.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AgreementApproval.findByAgreement"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(agreementId));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			AgreementApprovalDAO doc = new AgreementApprovalDAO();
			doc.loadObjectData(p);
			documents.add(doc);
		}
		
		return documents;
	}

	/**
	 * @return
	 */
	public Integer getAgreementId()
	{
		return agreementId;
	}

	/**
	 * @return
	 */
	public Date getApprovalDate()
	{
		return approvalDate;
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
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param date
	 */
	public void setApprovalDate(Date date)
	{
		approvalDate = date;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setStatus(Integer i)
	{
		status = i;
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

}
