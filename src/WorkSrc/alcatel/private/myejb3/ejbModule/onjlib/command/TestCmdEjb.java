/*
 * Created on 14/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.command;

import onjlib.command.*;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestCmdEjb {

	public static void main(String[] args) {
		
		
		HashCmdParams params = new HashCmdParams();
		params.setCommandClassName("onjlib.command.TestSrvCmd");
		params.setParam("1", "one");
		CommandParams out = CommandDispatcher.executeCommand(params);
		System.out.println("out = " + out.toString());
		
	}
}
