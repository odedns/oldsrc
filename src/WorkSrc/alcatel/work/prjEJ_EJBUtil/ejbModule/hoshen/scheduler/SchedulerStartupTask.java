/*
 * Created on 19/09/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoshen.scheduler;

import org.apache.log4j.Logger;

import hoshen.common.utils.exception.HoshenException;
import hoshen.startup.HoshenStartupTaskIF;

/**
 * @author odedn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SchedulerStartupTask implements HoshenStartupTaskIF {

	private static Logger log = Logger.getLogger(SchedulerStartupTask.class);
	/* (non-Javadoc)
	 * @see hoshen.startup.HoshenStartupTaskIF#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
		log.debug("SchedulerStartupTask.stop()");

	}

	/* (non-Javadoc)
	 * @see hoshen.scheduler.HoshenTaskIF#execute()
	 */
	public void execute() {
		// TODO Auto-generated method stub
		log.debug("SchedulerStartupTask.execute()");
		
		try {				
			HoshenTaskManager manager = HoshenTaskManager.getInstance();
		} catch(HoshenException he) {
			log.error("Error in SchedulerStartupTask: " + he);
		}

	}

}
