package mataf.desktop.handlers;

/**
 * Constants used by the proxy classes and by the startupClientOp class.
 * Each constant represent a different handler for a  group of commands.
 * 
 * @author Nati Dykstein. Creation Date : (07/07/2003 18:07:14).  
 */
public interface RTCommands 
{
	public static final String EVENT_LOGIN_COMMAND	= "1";
	public static final String TRANSACTION_COMMAND	= "2";
	public static final String ICON_COMMANDS 		= "3";
	public static final String TEXT_COMMANDS 		= "4";
	public static final String ERRORLINE_COMMANDS 	= "5";
	public static final String MESSAGE_COMMANDS 		= "6";
	public static final String MENUITEMS_COMMANDS 	= "7";
	public static final String SHUTDOWN_COMMANDS 	= "8";
	public static final String CURSOR_COMMANDS 		= "9";
	public static final String RT_KEYBOARD_COMMANDS	= "10";
}
