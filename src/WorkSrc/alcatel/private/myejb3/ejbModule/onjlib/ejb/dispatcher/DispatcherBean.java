package onjlib.ejb.dispatcher;

import javax.ejb.Stateless;

import onjlib.command.*;

/**
 * Bean implementation class for Enterprise Bean: Dispatcher
 */

@Stateless
public class DispatcherBean implements Dispatcher {
	private javax.ejb.SessionContext mySessionCtx;
	
	
	
	
	public CommandParams executeCommand(CommandParams params)
	{
		String cmdName = params.getCommandClassName();
		System.out.println("execute command : " + cmdName);
		try {
			CommandIF cmd = (CommandIF) Class.forName(cmdName).newInstance();
			params = cmd.execute(params);
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		return(params);
	}
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
	
	
}
