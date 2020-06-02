package hoshen.scheduler.ejb;

import org.apache.log4j.Logger;

import hoshen.common.utils.exception.HoshenException;
import hoshen.scheduler.HoshenTaskIF;
import hoshen.scheduler.HoshenTaskInfo;
import hoshen.scheduler.HoshenTaskManager;

import com.ibm.websphere.scheduler.TaskStatus;

/**
 * Bean implementation class for Enterprise Bean: BatchProcess
 */
public class HoshenProcessBean implements javax.ejb.SessionBean  {
	private javax.ejb.SessionContext mySessionCtx;
	private static Logger log = Logger.getLogger(HoshenProcessBean.class);
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
	
	public void process(TaskStatus status)
	{
		log.debug("in process() id= " + status.getTaskId());
		try {
		
			HoshenTaskManager manager = HoshenTaskManager.getInstance();
			HoshenTaskInfo taskInfo = manager.getHoshenTaskInfo(status.getTaskId());
			if(null != taskInfo) {
			
				log.debug("got tasinfo = " + taskInfo.getName()+ " className =" + taskInfo.getClassName());
				HoshenTaskIF task = (HoshenTaskIF) Class.forName(taskInfo.getClassName()).newInstance();
				task.execute();
			} else {
				log.error("Error retrieving task");
			}
			
		} catch(Exception he) {
			he.printStackTrace();
			log.error(he);
		}
		
		
	}
}
