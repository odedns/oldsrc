/*
 * Created on: 07/06/2004
 * Author Amit Mendelson
 * @version $Id: WFWorkItem.java,v 1.6 2005/04/26 11:41:03 amit Exp $
 */
package com.ness.fw.workflow;

import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.Message;

import java.util.Date;
import java.util.Calendar;

/**
 * This class replaces MQWF API WFWorkItem class for all applicative purposes.
 * It contains necessary information about the WFWorkItem and its relevant data
 * structures.
 */
public class WFWorkItem
{

	//private variables section

	/**
	 * workItemType - represents the MQWF WFWorkItem type: regular WorkItem, 
	 * notification or ActivityInstance (ActivityInstance is currently optional, 
	 * not used in the code).
	 */
	private int workItemType = -1;

	/**
	 * Is used either to replace the data structures of MQWF, so the
	 * application will be able to see the information, or to pass information
	 * to the MQWF WFWorkItem (E.g., when checking out the WFWorkItem).
	 */
	private WorkFlowServiceParameterMap parametersMap = null;

	/**
	 * Name of the WFProcessInstance this WFWorkItem belongs to.
	 */
	private String processName = null;

	/**
	 * ID of the WFProcessInstance this WFWorkItem belongs to.
	 */	
	private String processID = null;

	/**
	 * Category of the Process this WFWorkItem belongs to.
	 */
	private String processCategory = null;
	
	/**
	 * Process' template name.
	 */
	private String processTemplateName = null;
	
	/**
	 * Process' start time.
	 */
	private Date processStartTime = null;


	/**
	 * Name of the ActivityInstance/WFWorkItem.
	 */
	private String activityName = null;

	/**
	 * Creation time of the WFWorkItem.
	 */
	private Date creationTime = null;

	/**
	 * The time this WFWorkItem started running.
	 */
	private Date startTime = null;

	/**
	 * The time to postpone this WFWorkItem.
	 * Is used by postponeWorkItem().
	 */
	private Date postponeTime = null;

	///**
	// * The expiration time - the time this workItem will expire.
	// */
	//private Date expirationTime = null;

	/**
	 * The time this WFWorkItem has actually finished.
	 */
	private Date endTime = null;

	/**
	 * The time this WFWorkItem was received by this user.
	 */
	private Date receivedTime = null;

	/**
	 * Last time this WFWorkItem was modified.
	 */
	private Date lastModificationTime = null;

	/**
	 * ID of the workItem.
	 */
	private String ID = null;

	/**
	 * State of the WFWorkItem.
	 */
	private int state = -1;

	/**
	 * Priority of the WFWorkItem (equals to the priority of the relevant
	 * WFProcessInstance).
	 */
	private int processInstancePriority = -1;

	/**
	 * Last handler of the WFWorkItem (Id of it)
	 */
	private String owner = null;

	/**
	 * Name of Last handler of the WFWorkItem
	 */
	private String ownerName = null;
	
	/**
	 * Is the WorkItem open for customer
	 */
	//TODO: Consider, maybe redundant!
	private boolean isOpenForCustomer = false;
	
	/**
	 * Keeps information about the implementation/flow name
	 * connected to this workItem.
	 */
	private String implementation = null;

	//Constructors section

	/**
	 * Constructing an empty WFWorkItem with the required 
	 * WorkFlowServiceParameterMap.
	 * @param parametersMap - the WorkFlowServiceParameterMap.
	 * @throws NullParametersException
	 */
	public WFWorkItem(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowLogicException
	{

		if (parametersMap == null)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.PARAMETERS_MAP_NULL,
				new Message(
					WFExceptionMessages.PARAMETERS_MAP_NULL,
					Message.SEVERITY_ERROR));
		}

		this.parametersMap = parametersMap;
	}

	/**
	 * This constructor is meant for usage in MQWorkFlowService:
	 * generate the WI (Package-level visibility!)
	 * Is required for a Notification WI (as Notifications has
	 * no containers).
	 */
	protected WFWorkItem()
	{
	}

	//public methods section

	/**
	 * Returns name of the relevant WFProcessInstance.
	 * @return processName - name of the WFProcessInstance.
	 */
	public String getProcessName()
	{
		return processName;
	}

	/**
	 * Returns ID of the relevant WFProcessInstance.
	 * @return processID - ID of the WFProcessInstance.
	 */
	public String getProcessID()
	{
		return processID;
	}

	/**
	 * Returns the category of the relevant WFProcessInstance
	 * @return processCategory - category of the WFProcessInstance.
	 */
	public String getProcessCategory()
	{
		return processCategory;
	}

	/**
	 * Returns the TemplateName of the relevant WFProcessInstance
	 * @return processTemplateName - TemplateName of the WFProcessInstance.
	 */
	public String getProcessTemplateName()
	{
		return processTemplateName;
	}

	/**
	 * Returns the StartTime of the relevant WFProcessInstance
	 * @return processStartTime - StartTime of the WFProcessInstance.
	 */
	public Date getProcessStartTime()
	{
		return processStartTime;
	}

	/**
	 * Returns name of the relevant ActivityInstance
	 * @return activityName - name of the ActivityInstance
	 */
	public String getActivityName()
	{
		return activityName;
	}

	/**
	 * Returns the time this activity was created.
	 * @return creationTime - creation time of the activity.
	 */
	public Date getActivityCreationTime()
	{
		return creationTime;
	}

	/**
	 * Returns start time of the activity.
	 * This method can return null, in case the activity hasn't started yet!
	 * @return startTime - time the activity has started.
	 */
	public Date getActivityStartTime()
	{
		return startTime;
	}

	/**
	 * Returns the postpone time of the activity.
	 * This method can return null, in case the activity has no postpone time.
	 * @return postponeTime
	 */
	public Date getActivityPostponeTime()
	{
		return postponeTime;
	}

	/**
	 * Returns end time of the activity.
	 * This method can return null, in case the activity hasn't ended yet!
	 * @return actualEndTime
	 */
	public Date getActivityEndTime()
	{
		return endTime;
	}

	///**
	// * Returns expiration of the activity.
	// * This method can return null, in case expiration time was not set.
	// * @return actualEndTime
	// */
	//public Date getActivityExpirationTime()
	//{
	//	return expirationTime;
	//}

	/**
	 * Returns the time this activity was received by this user.
	 * @return revceivedTime
	 */
	public Date getActivityReceivedTime()
	{
		return receivedTime;
	}

	/**
	 * Returns the last time this activity was modified.
	 * @return lastModificationTime.
	 */
	public Date getActivityLastModificationTime()
	{
		return lastModificationTime;
	}

	/**
	 * Calculate number of days for activity completion.
	 * Note: the number is rounded to the close integer.
	 * @return long - number of days for completion.
	 */
	public long getNumberOfDaysForActivityCompletion()
	{

		Logger.debug(
			"WFWorkItem",
			"getNumberOfDaysForActivityCompletion started, postponeTime: "
				+ postponeTime);

		if (postponeTime == null)
		{
			return Integer.parseInt(
				SystemResources.getInstance().getProperty(
					WFConstants.UNDEFINED_POSTPONE_TIME_VALUE));
		}
		Calendar currentDate = Calendar.getInstance();
		Logger.debug(
			"WFWorkItem",
			"getNumberOfDaysForActivityCompletion, currentDate: "
				+ currentDate.getTime());

		/*
		 * The times are in milliseconds. in order to receive number of days,
		 * should divide the result in the sum: 1000(milliseconds)*60(seconds)
		 * *60(minutes)*24(hours).
		 */

		long divide = 1000 * 3600 * 24;
		long resultInMilliseconds =
			postponeTime.getTime() - currentDate.getTime().getTime();
		Logger.debug(
			"WFWorkItem",
			"getNumberOfDaysForActivityCompletion, resultInMilliseconds: "
				+ resultInMilliseconds);

		return (resultInMilliseconds / divide);

	}

	/**
	 * getPriority
	 * @return processInstancePriority - priority of the relevant WFProcessInstance
	 */
	public int getPriority()
	{
		return processInstancePriority;
	}

	/**
	 * getOwner
	 * @return owner of the WFWorkItem
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * getID
	 * @return ID of the WFWorkItem
	 */
	public String getID()
	{
		return ID;
	}

	/**
	 * getState
	 * @return state of the WFWorkItem
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * getImplementation
	 * @return Implementation
	 */
	public String getImplementation()
	{
		return implementation;
	}

	/**
	 * set state of the WFWorkItem.
	 * The WFWorkItem states are sequencial, so the range check here should be enough.
	 * @param state - the state to set.
	 * @throws WorkFlowLogicException in case the state has an illegal value.
	 */
	public void setState(int state) throws WorkFlowLogicException
	{
		if ((state < WFConstants.WORKITEM_STATE_CHECKED_OUT)
			|| (state > WFConstants.WORKITEM_STATE_UNDEFINED))
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.ILLEGAL_WORK_ITEM_STATE,
				new Message(
					WFExceptionMessages.ILLEGAL_WORK_ITEM_STATE,
					Message.SEVERITY_ERROR));
		}
		this.state = state;
	}

	/**
	 * getParametersMap
	 * @return WorkFlowServiceParametersMap
	 */
	public WorkFlowServiceParameterMap getParametersMap()
	{
		return parametersMap;
	}

	/**
	 * setParametersMap
	 * @param parametersMap - the WorkFlowServiceParameterMap instance to set.
	 * @throws WorkFlowLogicException in case the WFWorkItem is not a notification,
	 * the WorkFlowServiceParameterMap field is mandatory.
	 */
	protected void setParametersMap(WorkFlowServiceParameterMap parametersMap)
		throws WorkFlowLogicException
	{

		if (parametersMap == null)
		{

			/*
			 * Notifications have no containers.
			 * If the WFWorkItem is a notification, it's OK that the
			 * parametersMap will be null.
			 * Otherwise, it is an error, and an exception must be thrown.
			 */

			if (this.workItemType != WFConstants.NOTIFICATION_TYPE)
			{
				throw new WorkFlowLogicException(
					WFExceptionMessages.WORK_ITEM_MISSING_PARAMETERS_MAP,
					new Message(
						WFExceptionMessages.WORK_ITEM_MISSING_PARAMETERS_MAP,
						Message.SEVERITY_ERROR));
			}
		}
		this.parametersMap = parametersMap;
	}

	/**
	 * This method is used to check if the WFWorkItem is a new (unmodified)
	 * WFWorkItem. A WFWorkItem will be defined as new in case its last modification
	 * time equals the time it was received by the user.
	 * @return true if the WFWorkItem is new, false otherwise.
	 */
	public boolean isNewWorkItem()
	{
		if (lastModificationTime == receivedTime)
		{
			return true;
		}
		return false;
	}

	/**
	 * This method is used to check if the WFWorkItem type is a notification type.
	 * @return true if the WFWorkItem is a notification, false otherwise.
	 */
	public boolean isNotification()
	{
		if (this.workItemType == WFConstants.NOTIFICATION_TYPE)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Indication if the WFWorkItem is open for the customer.
	 * @return 
	 */
	//TODO: Consider, maybe redundant!
	public boolean isOpenForCustomer()
	{
		return isOpenForCustomer;
	}
	
	/**
	 * Set the value of isOpenForCustomer.
	 * @param isOpenForCustomer
	 */
	//TODO: Consider, maybe redundant!
	protected void setIsOpenForCustomer(boolean isOpenForCustomer)
	{
		this.isOpenForCustomer = isOpenForCustomer;
	}

	/**
	 * setType
	 * Currently only two types are legal - NOTIFICATION_TYPE and
	 * WORK_ITEM_TYPE. a third type ACTIVITY_INSTANCE_TYPE is currently not supported
	 * but can be supported in the future.
	 * @param workItemType - the type of the WFWorkItem
	 * @throws WorkFlowLogicException in case the type is not one of the legal
	 * WFWorkItem types.
	 */
	protected void setType(int workItemType) throws WorkFlowLogicException
	{
		if ((workItemType != WFConstants.NOTIFICATION_TYPE)
			&& (workItemType != WFConstants.WORKITEM_TYPE))
		{
			//			&&
			//			(workItemType!=WFConstants.ACTIVITY_INSTANCE_TYPE)) {

			throw new WorkFlowLogicException(
				WFExceptionMessages.WORK_ITEM_TYPE_INVALID,
				new Message(
					WFExceptionMessages.WORK_ITEM_TYPE_INVALID,
					Message.SEVERITY_ERROR));
		}
		this.workItemType = workItemType;
	}

	/**
	 * Sets the WFProcessInstance name for the WFWorkItem.
	 * @param processName - the WFProcessInstance name.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setProcessName(String processName)
		throws NullParametersException
	{

		if (processName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_PROCESS_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_PROCESS_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		this.processName = processName;
	}

	/**
	 * Sets the WFProcessInstance ID for the WFWorkItem.
	 * @param processID - the WFProcessInstance ID.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setProcessID(String processID)
		throws NullParametersException
	{

		if (processID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_PROCESS_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_PROCESS_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}

		this.processID = processID;
	}

	/**
	 * Sets the Category of the WFWorkItem.
	 * @param processCategory - the process category.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setCategory(String processCategory)
		throws NullParametersException
	{

		if (processCategory == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_PROCESS_CATEGORY_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_PROCESS_CATEGORY_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.processCategory = processCategory;
	}

	/**
	 * Sets the processTemplateName of the WFWorkItem.
	 * @param processTemplateName - the process templateName.
	 */
	protected void setProcessTemplateName(String processTemplateName)
	{

		this.processTemplateName = processTemplateName;
	}

	/**
	 * Sets the activity name according to the given parameter.
	 * @param activityName - name of the ActivityInstance.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setActivityName(String activityName)
		throws NullParametersException
	{

		if (activityName == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ACTIVITY_NAME_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ACTIVITY_NAME_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.activityName = activityName;
	}

	/**
	 * Sets the creationTime.
	 * @param creationTime
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setCreationTime(Date creationTime)
		throws NullParametersException
	{

		if (creationTime == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_CREATION_TIME_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_CREATION_TIME_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.creationTime = creationTime;
	}

	/**
	 * There is no checking if the start time is null, since the activity might
	 * have not been run yet!
	 * @param startTime - StartTime of the WFWorkItem.
	 */
	protected void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * There is no checking if the start time is null, since the activity might
	 * have not been run yet!
	 * @param processStartTime - StartTime of the parent ProcessInstance.
	 */
	protected void setProcessStartTime(Date processStartTime)
	{
		this.processStartTime = processStartTime;
	}

	/**
	 * There is no checking if the end time is null, since the activity might
	 * have not been finished yet!
	 * @param postponeTime - Time the workItem is expected to finish.
	 */
	protected void setPostponeTime(Date postponeTime)
	{

		this.postponeTime = postponeTime;
	}

	/**
	 * There is no checking if the actual end time is null, since the activity
	 * might have not been finished yet!
	 * @param endTime - Time the WFWorkItem has actually ended.
	 */
	protected void setEndTime(Date endTime)
	{

		this.endTime = endTime;
	}

	///**
	// * There is no checking if the expirationTime is null, since the 
	// * expiration time might not be defined.
	// * @param expirationTime - Time the WFWorkItem has actually ended.
	// */
	//protected void setExpirationTime(Date expirationTime)
	//{
	//
	//	this.expirationTime = expirationTime;
	//}

	/**
	 * Sets receivedTime field.
	 * @param receivedTime - the time this ActivityInstance was received by 
	 * the user.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setReceivedTime(Date receivedTime)
		throws NullParametersException
	{

		if (receivedTime == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_RECEIVED_TIME_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_RECEIVED_TIME_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.receivedTime = receivedTime;
	}

	/**
	 * Sets the lastModificationTime field.
	 * @param lastModificationTime
	 */
	protected void setLastModificationTime(Date lastModificationTime)
	{

		this.lastModificationTime = lastModificationTime;
	}

	/**
	 * Sets the WFWorkItem priority (= the relevant WFProcessInstance's priority).
	 * @param processInstancePriority
	 * @throws WorkFlowLogicException in case of negative priority.
	 */
	protected void setPriority(int processInstancePriority)
		throws WorkFlowLogicException
	{

		if (processInstancePriority < 0)
		{
			throw new WorkFlowLogicException(
				WFExceptionMessages.INVALID_PRIORITY_VALUE,
				new Message(
					WFExceptionMessages.INVALID_PRIORITY_VALUE,
					Message.SEVERITY_ERROR));
		}
		this.processInstancePriority = processInstancePriority;
	}

	/**
	 * Sets the owner of the WFWorkItem.
	 * @param owner the new owner of the WFWorkItem.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setOwner(String owner) throws NullParametersException
	{
		if (owner == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_OWNER_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_OWNER_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.owner = owner;
	}

	/**
	 * Sets the WFWorkItem ID.
	 * @param ID the ID to be set.
	 * @throws NullParametersException in case the parameter is null.
	 */
	protected void setID(String ID) throws NullParametersException
	{
		if (ID == null)
		{
			throw new NullParametersException(
				WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
				new Message(
					WFExceptionMessages.WORK_ITEM_ID_IS_NULL,
					Message.SEVERITY_ERROR));
		}
		this.ID = ID;
	}

	/**
	 * Sets the implementation of the WFWorkItem.
	 * @param implementation the new implementation of the WFWorkItem.
	 */
	protected void setImplementation(String implementation)
	{
		this.implementation = implementation;
	}

	/**
	 * @return ownerName
	 */
	public String getOwnerName()
	{
		return ownerName;
	}

	/**
	 * @param ownerName
	 */
	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

}
