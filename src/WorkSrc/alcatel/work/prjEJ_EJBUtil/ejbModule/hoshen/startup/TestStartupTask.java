/*
 * Created on 19/09/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoshen.startup;

/**
 * @author odedn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestStartupTask implements HoshenStartupTaskIF {

	/* (non-Javadoc)
	 * @see hoshen.startup.HoshenStartupTaskIF#stop()
	 */
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("in TeststartupTask.stop()");
	}

	/* (non-Javadoc)
	 * @see hoshen.scheduler.HoshenTaskIF#execute()
	 */
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("in TeststartupTask.execute()");

	}

}
