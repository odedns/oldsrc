package fwpilot.customer.bpo;

import java.util.List;

import com.ness.fw.bl.BusinessProcess;
import com.ness.fw.common.exceptions.*;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.DuplicateKeyException;
import com.ness.fw.persistence.exceptions.LockException;
import com.ness.fw.persistence.exceptions.ObjectNotFoundException;
import com.ness.fw.util.Message;
import com.ness.fw.util.MessagesContainer;

import fwpilot.customer.bpc.*;
import fwpilot.customer.dao.CustomerDAO;
import fwpilot.customer.dao.CustomerDAOManager;
import fwpilot.general.dao.CodeTablesUtil;

public class PLCustomerBPO extends BusinessProcess
{

	public static void saveCustomer(PLCustomerBPC container) throws BusinessLogicException, FatalException
	{
		Transaction trans = null;
		try
		{
			CustomerDAO customer = (CustomerDAO)container.getCustomer();
	
			trans = TransactionFactory.createTransaction(container.getUserAuthData());
			
			trans.begin();
			
			LockManager lockManager = new LockManager();
			
			lockManager.addLockable(customer);
			
			lockManager.lock(trans);
			
			Batch batch = new Batch();
			
			customer.save(trans, batch);
			
			trans.execute(batch);
			
			trans.commit();
			trans = null;
		}

		catch (LockException le)
		{
			throw new BusinessLogicException("lock",le.getMessagesContainer());
		}

		catch (DuplicateKeyException dke)
		{
			Message message = new Message("GE0026",Message.SEVERITY_ERROR);
			BusinessLogicException ble = new BusinessLogicException();
			ble.addMessage(message);
			throw ble;
		}
	
		catch (PersistenceException pe)
		{
			throw new FatalException("persistance",pe);
		}

		finally
		{
			if(trans != null)
			{
				try
				{
					trans.rollback();
				}
				catch (PersistenceException e)
				{
					throw new FatalException("persistance",e);
				}
			}
		}		
	}


	/**
	 * Finding customers according to the critertia
	 * @param container
	 * @throws GeneralException
	 */
	public static void findCustomers(PLFindCustomersBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();
			PagingService pagingService = CustomerDAO.findCustomers(container.getCriteriaFields(), seq);
			((RandomPagingService)pagingService).getPageCount(seq);
			container.setPagingService(pagingService);		
			container.setTotalPages(((RandomPagingService)pagingService).getPageCount(seq));
		}

		catch (PersistenceException e)
		{
			throw new FatalException("persistance",e);
		}

		finally
		{
			if (seq != null)
			{
				try
				{
					seq.end();
				}
				catch (PersistenceException e)
				{
					throw new FatalException("persistance",e);
				} 
			} 
		}		
	}
	
	/**
	 * Loading customer by id
	 * @param container
	 * @throws GeneralException
	 */
	public static void loadCustomer(PLCustomerBPC container) throws FatalException, BusinessLogicException
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();
			CustomerDAO customer = null;
			Integer customerId = container.getCustomerId();
			if (customerId != null)
			{
				customer = CustomerDAO.findByID(customerId.intValue(), seq);
			}
			else
			{
				customer = new CustomerDAO();
				customer.setType(container.getCustomerType());
			}
		
			customer.loadProfessionList(seq);
			container.setCustomer(customer);		
		}

		catch (ObjectNotFoundException e)
		{
			container.setCustomerExists(false);
			throw new BusinessLogicException();
		}

		catch (PersistenceException e)
		{
			throw new FatalException("persistance",e);
		}

		finally
		{
			if (seq != null)
			{
				try
				{
					seq.end();
				}
				catch (PersistenceException e)
				{
					throw new FatalException("persistance",e);
				} 
			} 
		}		

				
	}

	public static void loadAdress(PLCustomerBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();
			CustomerDAO customer = (CustomerDAO)container.getCustomer();
			customer.loadAddressList(seq);
			container.setCustomer(customer);					
		}
		catch (PersistenceException e)
		{
			throw new FatalException("persistance",e);		
		}
	}

	public static void loadRelated(PLCustomerBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();
			CustomerDAO customer = (CustomerDAO)container.getCustomer();
			customer.loadFamilyMemberList(seq);
			container.setCustomer(customer);							
		}
		catch (PersistenceException e)
		{
			throw new FatalException("persistance",e);		
		}
	}
	
	public static void loadProfessions(PLCustomerBPC container) throws FatalException
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
	}
	
	/**
	 * Finding customers according to the critertia
	 * @param container
	 * @throws GeneralException
	 */
	public static void initCustomers(PLCustomerBPC container) throws FatalException
	{
		ConnectionSequence seq = null;
		try
		{
			seq = ConnectionSequence.beginSequence();
			CustomerDAOManager manager = CustomerDAO.findCustomers(seq);
			container.setCustomerManager(manager);
		}

		catch (PersistenceException e)
		{
			throw new FatalException(e);
		}
		
		finally
		{
			if (seq != null)
			{
				try
				{
					seq.end();
				}
				catch (PersistenceException e)
				{
					throw new FatalException("persistance",e);
				} 
			} 
		}			
	}

	
	public static void saveCustomers(PLCustomerBPC container) throws BusinessLogicException, FatalException
	{
		Transaction trans = null;
		try
		{
			trans = TransactionFactory.createTransaction(container.getUserAuthData());
			CustomerDAOManager customerManager  = (CustomerDAOManager)container.getCustomerManager();
	
			trans.begin();			
			LockManager lockManager = new LockManager();
			lockManager.addLockableList(customerManager.getLockableList());
			lockManager.lock(trans);
			Batch batch = new Batch();
			customerManager.save(trans,null,batch);	
			trans.execute(batch);		
			trans.commit();
			trans = null;
			
			// if no erros, add some info messages
			Message message = new Message("LK0015",Message.SEVERITY_INFO);
			MessagesContainer cont = new MessagesContainer();
			cont.addMessage(message);
			container.addMessages(cont);
		} 
			
		catch (LockException le)
		{
			throw new BusinessLogicException("lock",le.getMessagesContainer());
		}
		catch (ObjectNotFoundException e)
		{
			throw new BusinessLogicException("object not found",e.getMessagesContainer());			
		}
		catch (PersistenceException e)
		{
			throw new FatalException(e);
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
