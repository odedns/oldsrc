package fwpilot.agreement.bpo;

import java.util.List;

import com.ness.fw.bl.*;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.DuplicateKeyException;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;
import com.ness.fw.util.Message;

import fwpilot.agreement.bpc.*;
import fwpilot.agreement.dao.*;
import fwpilot.agreement.vo.Agreement;
import fwpilot.agreement.vo.Status;
import fwpilot.agreement.vo.Type;
import fwpilot.customer.dao.ProfessionDAO;
import fwpilot.general.vo.CodeTableVO;

public class PLAgreementBPO extends BusinessProcess
{

	public static void saveAgreement(PLAgreementBPC container) throws FatalException, BusinessLogicException
	{
		Transaction trans = null;
		try
		{
			AgreementDAO agreement = (AgreementDAO) container.getAgreement();
			trans = TransactionFactory.createTransaction(container.getUserAuthData());
			trans.begin();		
			agreement.save(trans);
			trans.commit();
			trans = null;
		}
	
		catch (DuplicateKeyException e)
		{
			Message message = new Message("GE0026",Message.SEVERITY_ERROR);
			BusinessLogicException ble = new BusinessLogicException("duplicate key",message);
			throw ble;
		}

		catch (PersistenceException pe)
		{
			throw new FatalException(pe);
		}

		finally
		{
			if(trans != null)
			{
				try
				{
					trans.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException(pe);
				}
			}
		}		
	}

	public static void findAgreements(PLFindAgreementsBPC container) throws ObjectNotFoundException, PersistenceException
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		BasicPagingService service = AgreementDAO.findAgreements(container.getCriteriaFields(), seq);
		seq.end();
		container.setPagingService(service);			
	}
			
	public static void loadDocument(PLAgreementBPC container) throws PersistenceException 
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		AgreementDAO agreement = (AgreementDAO)container.getAgreement();
		agreement.loadDocumentList(seq);
		seq.end();					
	}
		
	public static void getCatalogTree(PLAgreementBPC container) throws PersistenceException
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		container.setPage(CatalogDAO.getCatalogTree(seq));
		seq.end();
	}
	
	public static void loadPackage(PLAgreementBPC container) throws PersistenceException
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		AgreementPackageDAO ap = (AgreementPackageDAO)container.getAgreementPackage();
		ap.loadPackage(seq);
		((PackageDAO)ap.getPackage()).loadProgramList(seq);
		container.setAgreementPackage(ap);
		seq.end();					
	}
	
	public static void loadAgreement(PLAgreementBPC container) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		ConnectionSequence seq = ConnectionSequence.beginSequence();
		initAgreement(container,seq);
		getAgreementPackagesTree(container,seq);
		loadCharacteristics(container,seq);
		loadAgreementPackages(container,seq);
		loadAgreementCustomer(container,seq);
		seq.end();					
	}


	private static void initAgreement(PLAgreementBPC container,ConnectionSequence seq) throws ObjectNotFoundException, PersistenceException, FatalException
	{
		String mode = container.getMode() == null ? "new" : container.getMode();
		Agreement agreement = null;
		Agreement copyAgreement;
		Integer agreementId = container.getId();

		// new mode
		if (mode.equals("new") )
		{
			agreement = new AgreementDAO();
			// set defaults
		}
		// detail
		else 
		{
			agreement = loadAgreement(agreementId,seq);
			if (mode.equals("update"))
			{
			}
			else if (mode.equals("copy"))
			{
				copyAgreement = new AgreementDAO();

				// copy the desire fields
				copyAgreement.setName(agreement.getName());
				Status status = new Status(agreement.getStatus().getId(),agreement.getStatus().getName());

				copyAgreement.setStatus(status);
				copyAgreement.setDescription(agreement.getDescription());
				Type type = new Type(agreement.getType().getId(),agreement.getType().getName());
				copyAgreement.setType(type);
				copyAgreement.setCustomerType(new Integer(Agreement.CUSTOMER_TYPE_GENERAL));

//				container.setCopyAgreement(copyAgreement);

				agreement = copyAgreement;

			}
		}
		
		// Setting the loaded agreement
		container.setAgreement(agreement); 

	}


	private static void getAgreementPackagesTree(PLAgreementBPC container, ConnectionSequence seq) throws PersistenceException 
	{
		AgreementDAO agreement = (AgreementDAO)container.getAgreement();
		container.setPage(agreement.getAgreementPackagesTree(seq));
	}

	private static void loadCharacteristics(PLAgreementBPC container, ConnectionSequence seq) throws PersistenceException
	{
		AgreementDAO agreement = (AgreementDAO)container.getAgreement();
		agreement.loadCharacteristicList(seq);
		container.setAgreement(agreement);
	}

	private static Agreement loadAgreement(Integer agreementId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException, FatalException 
	{
		AgreementDAO agreement= AgreementDAO.findByID(agreementId.intValue(), cp);
		return agreement;
	}

	private static void loadAgreementCustomer(PLAgreementBPC container, ConnectionSequence seq) throws ObjectNotFoundException, PersistenceException, FatalException 
	{
		AgreementDAO agreement = (AgreementDAO) container.getAgreement();
		agreement.loadCustomer(seq);
	}
	
	private static void loadAgreementPackages(PLAgreementBPC container, ConnectionSequence seq) throws PersistenceException 
	{
		AgreementDAO agreement = (AgreementDAO) container.getAgreement();
		agreement.loadPackageList(seq);
	}
	
	public static void loadAgreementCustomer(PLAgreementBPC container) throws FatalException
	{
		AgreementDAO agreement = (AgreementDAO) container.getAgreement();
		ConnectionSequence seq;
		try
		{
			seq = ConnectionSequence.beginSequence();
			agreement.reloadCustomer(seq);
			seq.end();
			container.setAgreement(agreement);
		}
		catch (PersistenceException e)
		{
			throw new FatalException(e);
		}
	}
	
/*	public static void loadCharacteristics(PLAgreementBPC container) throws FatalException
	{
		ConnectionSequence seq;
		try
		{
			seq = ConnectionSequence.beginSequence();
			List professions = new CodeTablesUtil().getCharacteristics(seq);
			container.setValues(professions);
			seq.end();
		}
		catch (PersistenceException e)
		{
			throw new FatalException(e);
		}	
	} */

	public static void loadCharacteristics(PLAgreementBPC container) throws FatalException
	{
		try
		{
			ConnectionSequence sequence = ConnectionSequence.beginSequence();
			List professions = ProfessionDAO.findAll(sequence);
			container.setValues(professions);
			sequence.end();
		}
		catch (PersistenceException e)
		{
			throw new FatalException(e);
		}	
	}

	public static void loadCharacteristicById(PLAgreementBPC container) throws FatalException
	{
		try
		{
			ConnectionSequence sequence = ConnectionSequence.beginSequence();
			CodeTableVO codeTable = ProfessionDAO.findById(container.getId(),sequence);
			container.setCodeTable(codeTable);
			sequence.end();
		}
		catch (PersistenceException e)
		{
			throw new FatalException(e);
		}	
	}

	public static void deleteAgreement(PLAgreementBPC container) throws BusinessLogicException, FatalException
	{
		Transaction trans = null;
		
		try
		{
			
			trans = TransactionFactory.createTransaction(container.getUserAuthData());
			trans.begin();
			AgreementDAO agreement = (AgreementDAO)loadAgreement(container.getId(),trans);
			
			agreement.setStateAsDeleted();
			agreement.save(trans);
			
			trans.commit();
			trans = null;
		}
		catch (DuplicateKeyException e)
		{
			Message message = new Message("GE0026",Message.SEVERITY_ERROR);
			BusinessLogicException ble = new BusinessLogicException("duplicate key",message);
			throw ble;
		}

		catch (PersistenceException pe)
		{
			throw new FatalException(pe);
		}

		finally
		{
			if(trans != null)
			{
				try
				{
					trans.rollback();
				}
				catch (PersistenceException pe)
				{
					throw new FatalException(pe);
				}
			}
		}		
							
	}


}
