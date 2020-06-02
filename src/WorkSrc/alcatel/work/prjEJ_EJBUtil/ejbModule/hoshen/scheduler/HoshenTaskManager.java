/*
 * Created on 11/09/2005
 */
 package hoshen.scheduler;

import hoshen.common.alerts.AlertsFacade;
import hoshen.common.alerts.entities.AlertParameter;
import hoshen.common.utils.exception.HoshenException;
import hoshen.common.utils.settings.PropertiesQuerier;

import java.util.*;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.log4j.Logger;

import com.ibm.websphere.scheduler.*;

/**
 * @author odedn
 * 
 * The HoshenTaskManager reads the schedulred tasks from the scheduler.properties file 
 * and loads the tasks into the WAS Scheduler.
 * The HoshenTaskManager supplied an API to add and cancel or remove tasks from the 
 * WAS scheduler. 
 *
 */
public class HoshenTaskManager {
	
	private static HoshenTaskManager m_instance = null;
	private static Scheduler m_sched = null;
	private static TaskHandlerHome m_taskHandlerHome = null;
	private static NotificationSinkHome m_notificationHome = null;
	private static Map m_taskTbl = null;
	private static Logger log = Logger.getLogger(HoshenTaskManager.class);
	private static final int ALERT_ADD_TASK = 1002;
	private static final int ALERT_ERROR_ADD_TASK = 1001;
	
	/**
	 * obtain the HoshenTaskManager singleton instance.
	 * @return HoshenTaskManager instance.
	 */
	public static synchronized HoshenTaskManager getInstance() throws HoshenException
	{
		if(null == m_instance) {
			m_instance = new HoshenTaskManager();
		}
		return(m_instance);
	}


	/**
	 * private constructor.
	 * activated by the getInstance method only.
	 *
	 */
	private HoshenTaskManager() throws HoshenException
	{
		try {
			
			/*
			 * initialize configuration.
			 */
			HoshenSchedulerConfig config = HoshenSchedulerConfig.getInstance();
			
			/*
			 * check if the scheduler is disabled.
			 */
			if(config.isDisabled()) {
				System.out.println("scheduler is disabled.");
				return;
			}
		
			InitialContext ctx = new InitialContext();
			Object o = ctx.lookup(config.getTaskHandlerJndi());
			m_taskHandlerHome = (TaskHandlerHome) PortableRemoteObject.narrow(o,TaskHandlerHome.class);
			o = ctx.lookup(config.getNotificationSinkJndi());
			m_notificationHome = (NotificationSinkHome) PortableRemoteObject.narrow(o,NotificationSinkHome.class);
			String jndiName = config.getJndiName();
			m_sched = (Scheduler) ctx.lookup(jndiName);
			m_taskTbl = new HashMap();
			
			if(config.isCleanupTasks()) {
				log.info("HoshenTaskManager() cleaning up tasks");
				cleanUpTasks();		
			}
			
			
			/*
			 *  add predefined tasks to the scheduler.
			 */
			List taskList = config.getTaskInfoList();
			addTasks(taskList);
			
		} catch(Exception e){
			e.printStackTrace();
			throw new HoshenException("Error initializing HoshenTaskManager",e);
		}
			
	}
	
	
	/**
	 * add a task to be performed by the scheduler.
	 * If the task exists just return its status.
	 * @param task a HoshenTaskInfo object.
	 * @throws HoshenException in case of error.
	 */
	public String addTask(HoshenTaskInfo task) throws HoshenException
	{
		TaskStatus status = null;
		AlertParameter params[] = new AlertParameter[1];
		params[0] = new AlertParameter(task.getName(),null);
		
		try {
								
			Iterator iter = m_sched.findTaskStatusByName(task.getName());
			if(null != iter && iter.hasNext()) {				
				TaskStatus ts = (TaskStatus) iter.next();
				log.debug("addTask found task: " + ts.getTaskId());
				AlertsFacade.addAlert(ALERT_ADD_TASK,
                            PropertiesQuerier.getIntValueWithKey("EJ_COMPONENT", "EJ"),
                            null,// more than 16 component
                            null, //responsible region
                            null, // responsible site
                            null, //
                            null, //
                            params, null);
				return(ts.getTaskId());
			}
			
			BeanTaskInfo taskInfo = (BeanTaskInfo) m_sched.createTaskInfo(BeanTaskInfo.class);
			taskInfo.setName(task.getName());
			taskInfo.setTaskHandler(m_taskHandlerHome);
			taskInfo.setNotificationSink(m_notificationHome, TaskNotificationInfo.ALL_EVENTS);
			taskInfo.setStartTime(task.getStartDate());
			log.debug("task start date= " + task.getStartDate().toString());
			
			taskInfo.setRepeatInterval(task.getRepeatInterval());
			taskInfo.setNumberOfRepeats(task.getNumberOfRepeats());			
			status = m_sched.create(taskInfo);
			/*
			 * save the hoshen task info in a task hash
			 * table.
			 */
			m_taskTbl.put(status.getTaskId(), task);
			HoshenSchdulerUtils.saveTaskClassName(status.getTaskId(),task.getClassName());
			log.info("add task: " + task.getName());
			AlertsFacade.addAlert(ALERT_ADD_TASK,
                    PropertiesQuerier.getIntValueWithKey("EJ_COMPONENT", "EJ"),
                    null,// more than 16 component
                    null, //responsible region
                    null, // responsible site
                    null, //
                    null, //
                    params, null);

		} catch(Exception e) {
			params = new AlertParameter[2];
			params[0] = new AlertParameter(task.getName(),null);
			params[1] =  new AlertParameter(e.getMessage(),null);
			AlertsFacade.addAlert(ALERT_ERROR_ADD_TASK,
                    PropertiesQuerier.getIntValueWithKey("EJ_COMPONENT", "EJ"),
                    null,// more than 16 component
                    null, //responsible region
                    null, // responsible site
                    null, //
                    null, //
                    params, null);
			throw new HoshenException("Error adding task to scheduler",e);
		}
		return(status.getTaskId());	
	}
	
	/**
	 * cancel a scheduled task.
	 * @param taskId The task id
	 * @throws HoshenException in case of error.
	 */
	public void cancelTask(String taskId) 
	{
		try {					
			String key = (String) m_taskTbl.remove(taskId);
			m_sched.cancel(taskId,true);
			HoshenSchdulerUtils.deleteTaskInfo(taskId);			
			log.info("cancelling task: " + taskId);
		} catch(Exception e ) {
			log.error("Error canceling task: " + e);
		}
	}
	
	/**
	 * cancel a scheduled task.
	 * @param taskId The task id
	 * @throws HoshenException in case of error.
	 */
	public void purgeTask(String taskId) 
	{
		try {					
			String key = (String) m_taskTbl.remove(taskId);
			m_sched.purge(taskId);
			HoshenSchdulerUtils.deleteTaskInfo(taskId);
			log.info("purging task: " + taskId);
		} catch(Exception e ) {
			log.error("Error purging task: " + e);
		}
	}
	
	/**
	 * Add all tasks in the list to the scheduler.
	 * @param taskList a list of HoshenTaskInfo objects.
	 * @throws HoshenException
	 */
	public void addTasks(List taskList) throws HoshenException
	{
		Iterator iter = taskList.iterator();
		while(iter.hasNext()) {
			HoshenTaskInfo taskInfo = (HoshenTaskInfo) iter.next();
			addTask(taskInfo);
		}
	}
	
	/**
	 * Cancel all scheduled tasks.
	 * @throws HoshenException in case of error.
	 */
	public void cancelAllTasks()
	{
		Set keys = m_taskTbl.keySet();
		Iterator iter = keys.iterator();
		while(iter.hasNext()) {
			String taskId = (String) iter.next();
			cancelTask(taskId);
		}
		
	}
	
	
	public void cleanUpTasks() throws HoshenException
	{
		log.debug("cleanup Tasks");
		List taskList = HoshenSchdulerUtils.findAllTasks();
		Iterator iter = taskList.iterator();
		while(iter.hasNext()) {			
			String taskId = (String) iter.next();
			cancelTask(taskId);
			
		}
		
	}
	/**
	 * get a HoshenTaskInfo object from the task table
	 * by task id
	 * @param taskId id of the task  info to retrieve.
	 * @return HoshenTaskInfo object.
	 */
	public HoshenTaskInfo getHoshenTaskInfo(String taskId)
	{
		HoshenTaskInfo info = (HoshenTaskInfo) m_taskTbl.get(taskId);
		if(null == info) {
			try {
				log.debug("trying to retrieve taskinfo from db taskId=" + taskId);
				info = HoshenSchdulerUtils.findTaskInfo(taskId);
			} catch(HoshenException he) {
				log.error("Error in getHoshenTaskInfo: " + he);
				info = null;
			}
		}
		
		return(info);
	}
	
	
}	
	