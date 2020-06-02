package hoshen.startup.ejb;

import java.util.*;

import org.apache.log4j.Logger;

import hoshen.scheduler.HoshenTaskInfo;
import hoshen.scheduler.HoshenTaskManager;
import hoshen.startup.HoshenStartupConfig;
import hoshen.startup.HoshenStartupTaskIF;

/**
 * Bean implementation class for Enterprise Bean: HoshenStartupBean
 */
public class HoshenStartupBean implements javax.ejb.SessionBean {
	private javax.ejb.SessionContext mySessionCtx;
	private static Logger log = Logger.getLogger(HoshenStartupBean.class);
	private List m_taskList;
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
	
	
	/**
	 * startup bean's start method.
	 * Read all tasks from the xml configuration file and
	 * run them.
	 * @return boolean true on success.
	 */
	public boolean start()
	{
		log.info("in HoshenStartupBean start");
		try {
		
			HoshenStartupConfig config = HoshenStartupConfig.getInstance();
			m_taskList = config.getTaskInfoList();
			Iterator iter = m_taskList.iterator();
			while(iter.hasNext()) {
				HoshenTaskInfo info = (HoshenTaskInfo) iter.next();
				log.info("Executing startup task: " + info.getName());
				HoshenStartupTaskIF task = (HoshenStartupTaskIF) Class.forName(info.getClassName()).newInstance();
				task.execute();
			}
			
		} catch (Exception e) {
				e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * startup bean's stop method.
	 *
	 */
	public void stop()
	{
		log.debug("in HoshenStartupBean stop");
		
		try {
		
			
			Iterator iter = m_taskList.iterator();
			while(iter.hasNext()) {
				HoshenTaskInfo info = (HoshenTaskInfo) iter.next();
				log.info("Executing stop on startup task: " + info.getName());
				HoshenStartupTaskIF task = (HoshenStartupTaskIF) Class.forName(info.getClassName()).newInstance();
				task.stop();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
