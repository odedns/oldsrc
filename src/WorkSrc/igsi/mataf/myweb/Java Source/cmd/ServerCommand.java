package cmd;

/**
 * @author Oded Nissan 01/03/2004
 * 
 * The ServerCommand is an abstract implemenation of 
 * the CommandIF interface. All server side commands should 
 * extend this class.
 * 
 */
public abstract class ServerCommand implements CommandIF {

	/**
	 * @see cmd.CommandIF#execute(CommandParams)
	 */
	public abstract CommandParams execute(CommandParams params) throws Exception;


}
