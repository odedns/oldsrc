package fwpilot.agreement.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.DAOList;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.util.TypesUtil;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.agreement.vo.AgreementPackage;
import fwpilot.agreement.vo.Package;

public class AgreementPackageDAO extends DAO implements AgreementPackage,Identifiable
{
	protected static String PROPS_FILE_NAME = "fwpilot/agreement/dao/sql";
	
	private Integer agreementId;
	private Integer packageID;	
	
	private Double insuranceSum;
	private Double premia;
	private Double afterDiscount;
	private Integer type;
	
	private PackageDAO pack = null; 

	/**
	 * Default constructor
	 */
	public AgreementPackageDAO()
	{
		super();
//		insuranceSum = new Double(0);
//		premia = new Double(0);
//		type = new Integer(0);
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
		params.add(packageID);
		params.add(insuranceSum);
		params.add(premia);
		params.add(afterDiscount);
		params.add(type);
		
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementPackage.insert"), params, transaction);
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
		params.add(insuranceSum);
		params.add(premia);
		params.add(afterDiscount);
		params.add(type);
		params.add(transaction.getUserId());

		// keys
		params.add(agreementId);
		params.add(packageID);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementPackage.update"), params, transaction);
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
		params.add(packageID);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementPackage.delete"), params, transaction);
	}


	protected static void deleteByParentId(Integer parentId, Transaction transaction, Batch batch) throws PersistenceException
	{
		List params = new ArrayList();
		params.add(parentId);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AgreementPackage.deleteByParentId"), 
			params, transaction, batch);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	protected void setKeyValues(Map keys) 
	{
		keys.put("agreementId", agreementId);
		keys.put("packageID", packageID);
	}

	public static DAOList findByAgreement(int agreementId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		DAOList packages = new DAOList();
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AgreementPackage.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AgreementPackage.findByAgreement"); 
		
		SqlService ss = new SqlService(sqlStatement);
		
		ss.addParameter(new Integer(agreementId));
				
		Page p = Query.execute(ss, cp);
		while (p.next())
		{
			AgreementPackageDAO pack = new AgreementPackageDAO();
			pack.loadObjectData(p);
			packages.add(pack);
		}
		
		return packages;
	}


	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		agreementId = p.getInteger("AGREEMENT_ID"); 
		packageID = p.getInteger("PACKAGE_ID");
		insuranceSum = new Double(p.getDouble("INSURANCE_SUM"));
		premia = new Double(p.getDouble("PREMIA"));
		afterDiscount = TypesUtil.convertNumberToDouble(p.getObject("AFTER_DISCOUNT"));
		type = p.getInteger("TYPE"); 

	}


	/**
	 * @return
	 */
	public Integer getId()
	{
		return getPackageID();
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
	public Integer getPackageID()
	{
		return packageID;
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
	 * @param i
	 */
	public void setPackageID(Integer i)
	{
		packageID = i;
		setStateAsDirty();
	}


	public void loadPackage(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		if(pack == null)
		{
			pack = PackageDAO.findByID(packageID.intValue(), cp);
		}
	}

	public Package getPackage()
	{
		return pack;
	}

	/**
	 * @return
	 */
	public Double getAfterDiscount()
	{
		return afterDiscount;
	}

	/**
	 * @return
	 */
	public Double getInsuranceSum()
	{
		return insuranceSum;
	}

	/**
	 * @return
	 */
	public Double getPremia()
	{
		return premia;
	}

	/**
	 * @return
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param double1
	 */
	public void setAfterDiscount(Double double1)
	{
		afterDiscount = double1;
		setStateAsDirty();
	}

	/**
	 * @param d
	 */
	public void setInsuranceSum(Double d)
	{
		insuranceSum = d;
		setStateAsDirty();
	}

	/**
	 * @param d
	 */
	public void setPremia(Double d)
	{
		premia = d;
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

	/* (non-Javadoc)
	 * @see com.ness.fw.bl.DAO#deleteContainedObjects(com.ness.fw.persistence.Transaction, java.util.Map)
	 */
	protected void deleteContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException
	{
		//System.out.println("deleteContainedObjects in AgreementPackage");
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.bl.DAO#saveContainedObjects(com.ness.fw.persistence.Transaction, java.util.Map)
	 */
	protected void saveContainedObjects(Transaction transaction, Map keys)
		throws BusinessLogicException, PersistenceException
	{
		//System.out.println("saveContainedObjects in AgreementPackage");
	}

}
