package fwpilot.general.dao;

import java.util.*;

import com.ness.fw.bl.Identifiable;
import com.ness.fw.bl.LockParameters;
import com.ness.fw.bl.LockableDAO;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;

import fwpilot.general.vo.WorkQueueItem;
;

public class WorkQueueItemDAO extends LockableDAO implements WorkQueueItem,Identifiable
{

	protected static String PROPS_FILE_NAME = "fwpilot/general/dao/sql";
	
	protected Integer id;
	protected Integer type;
	protected Integer status;
	protected Integer priority;
	protected Integer processId;
	protected Integer stepId;	
	protected Integer customerId;
	protected Integer agentId;
	protected Date startDate;
	protected Date endDate;
	protected Integer responsibleUserId;

	private LockParameters lockParameters = null;
	
	/**
	 * Default constructor
	 */
	public WorkQueueItemDAO() throws PersistenceException
	{
		super();
	}

	/** Insert this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @return Map The keys of the inserted object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void insert(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();
		params.add(type);
		params.add(status);
		params.add(priority);
		params.add(processId);
		params.add(stepId);
		params.add(customerId);
		params.add(startDate);
		params.add(endDate);
		params.add(responsibleUserId);
		params.add(agentId);
		
		params.add(new Integer(getLockId()));
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		id = executeInsertStatement(transaction.getProperty(PROPS_FILE_NAME, "WorkQueueItem.insert"),params, transaction, true);
		
	}

	/** Update this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void update(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();
		params.add(type);
		params.add(status);
		params.add(priority);
		params.add(processId);
		params.add(stepId);
		params.add(customerId);
		params.add(startDate);
		params.add(endDate);
		params.add(responsibleUserId);
		params.add(agentId);
		
		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "WorkQueueItem.update"), params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		// keys
		List params = new ArrayList();
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "WorkQueueItem.delete"), params, transaction);
	}

	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	public void setKeyValues(Map keys) 
	{
		keys.put("workQueueId", id);
	}

	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static WorkQueueItemDAO findByID(int id, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		WorkQueueItemDAO workQueueItem = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "WorkQueueItem.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "WorkQueueItem.findByID"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(id));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			workQueueItem = new WorkQueueItemDAO();
			workQueueItem.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("WorkQueueItem does not exist");
		}
		return workQueueItem;
	}

	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
		
		id = p.getInteger("ID"); 
		setLockId(p.getInt("LOCK_ID")); 
		type = p.getInteger("TYPE");
		status = p.getInteger("STATUS");
		priority = p.getInteger("PRIORITY");
		processId = p.getInteger("PROCESS_ID");
		stepId = p.getInteger("STEP_ID");
		customerId = p.getInteger("CUSTOMER_ID");
		startDate = p.getDate("START_DATE");
		endDate = p.getDate("END_DATE");
		responsibleUserId = p.getInteger("RESPONSIBLE");
		agentId = p.getInteger("AGENT_ID");
		
	}

	public static Page getQueueByType(int searchType, int userId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "WorkQueueItem.getQueueByType.main"); 
		
		if(searchType != TYPE_ALL)
		{
			sqlStatement += cp.getProperty(PROPS_FILE_NAME, "WorkQueueItem.getQueueByType.type"); 
		}
		sqlStatement += cp.getProperty(PROPS_FILE_NAME, "WorkQueueItem.getQueueByType.order");
		
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(userId));
		if(searchType != TYPE_ALL)
		{
			ss.addParameter(new Integer(searchType));
		}		
		
		Page p = Query.execute(ss, cp);
		return p;
	}

	/**
	 * @return LockParameters  
	 */
	protected LockParameters getLockParameters ()
	{
		if (lockParameters == null)
		{
			List keys = new ArrayList(1);
			keys.add(id);
			lockParameters =
				new LockParameters(
					keys,
					PROPS_FILE_NAME,
					"WorkQueueItem",
					"WorkQueueItem");
		}
		return lockParameters;
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
	public Integer getPriority()
	{
		return priority;
	}

	/**
	 * @return
	 */
	public Integer getProcessId()
	{
		return processId;
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
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * @return
	 */
	public Integer getStepId()
	{
		return stepId;
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
	public void setEndDate(Date date)
	{
		endDate = date;
	}

	/**
	 * @param i
	 */
	public void setPriority(Integer i)
	{
		priority = i;
	}

	/**
	 * @param i
	 */
	public void setProcessId(Integer i)
	{
		processId = i;
	}

	/**
	 * @param date
	 */
	public void setStartDate(Date date)
	{
		startDate = date;
	}

	/**
	 * @param i
	 */
	public void setStatus(Integer i)
	{
		status = i;
	}

	/**
	 * @param i
	 */
	public void setStepId(Integer i)
	{
		stepId = i;
	}

	/**
	 * @param i
	 */
	public void setType(Integer i)
	{
		type = i;
	}

	/**
	 * @return
	 */
	public Integer getResponsibleUserId()
	{
		return responsibleUserId;
	}

	/**
	 * @param i
	 */
	public void setResponsibleUserId(Integer i)
	{
		responsibleUserId = i;
	}

	/**
	 * @return
	 */
	public Integer getAgentId()
	{
		return agentId;
	}

	/**
	 * @return
	 */
	public Integer getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param i
	 */
	public void setAgentId(Integer i)
	{
		agentId = i;
	}

	/**
	 * @param i
	 */
	public void setCustomerId(Integer i)
	{
		customerId = i;
	}

}
