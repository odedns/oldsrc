package mataf.services.proxy;

/**
 * PrinterConstants used by the proxy classes and by the startupClientOp class.
 * Each constant represent a different handler for a group of commands.
 * PrinterConstants must be the same across the 2 platforms.(Java Client & C RunTime)
 * 
 * @author Nati Dykstein. Creation Date : (07/07/2003 18:07:14).  
 */
public interface RTCommands
{
	public static final String EVENT_LOGIN_COMMAND	= "10051";//  : "1");
	public static final String TRANSACTION_COMMAND	= "100051";// : "2");
	public static final String ICON_COMMANDS 		= "10001";//  : "3");
	public static final String TEXT_COMMANDS 		= "10002";//  : "4");
	public static final String ERRORLINE_COMMANDS 	= "10003";//  : "5");
	public static final String MESSAGE_COMMANDS 		= "10004";//  : "6");
	public static final String MENUITEMS_COMMANDS 	= "10052";//  : "7");
	public static final String SHUTDOWN_COMMANDS 	= "100052";// : "8");
	public static final String RT_KEYBOARD_COMMANDS	= "100053";// : "10");

	public static final String GLOBAL_SYNCH_COMMAND 	= "10053";//  : "11");
	public static final String AUTHENTICATE_COMMAND 	= "100054";// : "12");
	public static final String MANAGERSLIST_COMMAND 	= "100055";// : "13");
	public static final String GLOBAL_UPDATE_COMMAND  = "100056";
	public static final String CHECK_ACCESS_COMMAND   = "100057";
	public static final String WRAP_JOURNAL_COMMAND   = "10056";
}