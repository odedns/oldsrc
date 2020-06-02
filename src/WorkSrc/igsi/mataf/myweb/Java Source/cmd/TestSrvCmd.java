package cmd;

/**
 * @author Oded Nissan 01/03/2004
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestSrvCmd extends ServerCommand {

	/**
	 * @see cmd.CommandIF#execute(CommandParams)
	 */
	public CommandParams execute(CommandParams params) throws Exception {
		
		System.out.println("in TestSrvCmd params= " + params.toString());
		((HashCmdParams) params).setParam("2","two");
		return(params);
	}


}
