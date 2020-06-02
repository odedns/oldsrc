package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.RelatedIdentifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.agreement.vo.AgreementCharacteristics;
;


public class AgreementCharacteristicsDAO extends DAO implements AgreementCharacteristics, RelatedIdentifiable
{
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";
	private Integer agreementId;
	private Integer charId;
	private String name;
	
	/**
	 * Default constructor
	 */
	public AgreementCharacteristicsDAO()
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
		agreementId = ((Integer)keys.get("agreementId"));
		
		List params = new ArrayList();
		params.add(agreementId);
		params.add(charId);
		params.add(transaction.getUserId());
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementCharacteristics.insert"), params, transaction);
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
		params.add(agreementId);
		params.add(charId);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementCharacteristics.delete"), params, transaction);
	}

	protected static void deleteByParentId(Integer parentId, Transaction transaction, Batch batch) throws PersistenceException
	{
		List params = new ArrayList();
		params.add(parentId);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementCharacteristics.deleteByParentId"), 
			params, transaction, batch);
	}
	

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("characteristicsId", charId);
	}

	protected void update(Transaction transaction, Map keys) throws PersistenceException
	{
		
	}


	public static DAOList findByAgreement(int agreementId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList profs = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AgreementCharacteristics.findByAgreement"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(agreementId));
		
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			AgreementCharacteristicsDAO prof = new AgreementCharacteristicsDAO();
			prof.loadObjectData(p);
			profs.add(prof);
		}
		return profs;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		agreementId = p.getInteger("AGREEMENT_ID"); 
		charId = p.getInteger("CHAR_ID"); 
		name = p.getString("NAME");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return " Agreement Characteristics: charId = " + charId  + " name = " + name;  
	}

	/**
	 * @return
	 */
	public Integer getRelatedId()
	{
		return getCharId();
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
	public Integer getCharId()
	{
		return charId;
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
	public void setName(String string)
	{
		name = string;
		setStateAsDirty();
	}

	/**
	 * @param i
	 */
	public void setCharId(Integer i)
	{
		charId = i;
		setStateAsDirty();
	}

}
