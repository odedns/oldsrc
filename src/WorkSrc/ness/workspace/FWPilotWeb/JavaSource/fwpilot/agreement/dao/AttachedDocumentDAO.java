package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.agreement.vo.AttachedDocument;
;

/**
 * @author yharnof
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AttachedDocumentDAO extends DAO implements AttachedDocument, Identifiable
{
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";
	
	protected Integer id;
	protected Integer agreementId;
	protected Integer docId;
	protected Integer system;
	protected Date attachDate;
	protected String description;

	/**
	 * Default constructor
	 */
	public AttachedDocumentDAO()
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
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "AttachedDocument.insert");

		agreementId = ((Integer)keys.get("agreementId"));

		List params = new ArrayList();		
		params.add(agreementId);
		params.add(system);
		params.add(docId);
		params.add(attachDate);
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
		params.add(agreementId);
		params.add(system);
		params.add(docId);
		params.add(attachDate);
		params.add(description);
		params.add(transaction.getUserId());

		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AttachedDocument.update"), params, transaction);
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
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AttachedDocument.delete"), params, transaction);
	}

	protected static void deleteByParentId(Integer parentId, Transaction transaction, Batch batch) throws PersistenceException
	{
		List params = new ArrayList();
		params.add(parentId);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AttachedDocument.deleteByParentId"), 
			params, transaction, batch);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("attachedDocumentId",id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static AttachedDocumentDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		AttachedDocumentDAO attachedDocument = null;
		
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AttachedDocument.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AttachedDocument.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			attachedDocument = new AttachedDocumentDAO();
			attachedDocument.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("AttachedDocument does not exist");
		}
		return attachedDocument;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		agreementId = p.getInteger("AGREEMENT_ID");
		system = p.getInteger("SYSTEM");
		docId = p.getInteger("DOC_ID");
		description = p.getString("DESCRIPTION");
		attachDate = p.getDate("ATTACH_DATE");

	}

	public static DAOList findByAgreement(int agreementId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList documents = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AttachedDocument.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AttachedDocument.findByAgreement"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(agreementId));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			AttachedDocumentDAO doc = new AttachedDocumentDAO();
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
	public Date getAttachDate()
	{
		return attachDate;
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
	public Integer getDocId()
	{
		return docId;
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
	public Integer getSystem()
	{
		return system;
	}

	/**
	 * @param i
	 */
	public void setAgreementId(Integer i)
	{
		agreementId = i;
		setStateAsDirty();
	}

	/**
	 * @param date
	 */
	public void setAttachDate(Date date)
	{
		attachDate = date;
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
	 * @param i
	 */
	public void setDocId(Integer i)
	{
		docId = i;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setSystem(Integer i)
	{
		system = i;
		setStateAsDirty();
	}

}
