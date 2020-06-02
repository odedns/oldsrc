package hoshen.scheduler.ejb;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.websphere.scheduler.TaskNotificationInfo;
import com.ibm.websphere.scheduler.TaskStatus;

/**
 * Bean implementation class for Enterprise Bean: ReceiveNotification
 */
public class ReceiveNotificationBean implements javax.ejb.SessionBean {
	private javax.ejb.SessionContext mySessionCtx;
	private static Logger log = Logger.getLogger(ReceiveNotificationBean.class);
	
	private Map m_evenMap;
	
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext() {
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx) {
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException {
		m_evenMap = new HashMap();
		
		
		m_evenMap.put(new Integer(TaskNotificationInfo.CANCELLED),"CANCELLED");
		m_evenMap.put(new Integer(TaskNotificationInfo.COMPLETE),"COMPLETE");
		m_evenMap.put(new Integer(TaskNotificationInfo.FIRED),"FIRED");
		m_evenMap.put(new Integer(TaskNotificationInfo.FIRE_DELAYED),"FIRE_DELAYED");
		m_evenMap.put(new Integer(TaskNotificationInfo.FIRE_FAILED),"FIRE_FAILED");
		m_evenMap.put(new Integer(TaskNotificationInfo.FIRING),"FIRING");
		m_evenMap.put(new Integer(TaskNotificationInfo.PURGED),"PURGED");
		m_evenMap.put(new Integer(TaskNotificationInfo.RESUMED),"RESUMED");
		m_evenMap.put(new Integer(TaskNotificationInfo.SCHEDULED),"SCHEDULED");
		m_evenMap.put(new Integer(TaskNotificationInfo.SUSPENDED),"SUSPENDED");
		
		
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate() {
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate() {
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove() {
	}
	
	public void handleEvent(TaskNotificationInfo task)
	{	
		int eventType = task.getEventType();
		String eventTypeStr = (String) m_evenMap.get(new Integer(eventType));
		
		if(null == eventTypeStr) {
			eventTypeStr = "UNKNOWN EVENT";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("Received Notification TaskId: ");
		sb.append(task.getTaskStatus().getTaskId());
		sb.append("\tTaskName: ");
		sb.append(task.getTaskStatus().getName());
		sb.append("\tEvent: ");
		sb.append(eventTypeStr);
		
		log.info(sb);
	}
	
	
}
