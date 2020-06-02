package mataf.services.chequereader;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CRException extends Exception
{
	private int iErrorCode=0;
	
	public static final int CRE_OK						= 0;
	public static final int CRE_RMTREQ_FAILED 			= 1;
	public static final int CRE_SPV_CONNECTION_FAILED 	= 2;
	public static final int CRE_LANDP_OPEN_FAILED		= 3; //6
	public static final int CRE_LANDP_ARM_FAILED			= 4;
	public static final int CRE_READING_ABROTED			= 5; //3
	public static final int CRE_ERROR_IN_READING			= 6;
	
	public static final int CRE_CHQ_FUNC_NOT_SUPP		= 7; //P1
	public static final int CRE_CHQ_INCORRECT_DATA_LEN	= 8; //P2
	public static final int CRE_CHQ_PREV_OPEN_MISS		= 9; //P3
	public static final int CRE_CHQ_NO_DATA_TO_PASS		= 10;//P7
	public static final int CRE_CHQ_SRVR_READ_LEN_ERR	= 11;//PR
	public static final int CRE_CHQ_SRVR_STATUS_NOTOK	= 12;//PS
	public static final int CRE_CHQ_INVALID_PARM_LEN		= 13;//PZ
	
	public static final int CRE_CHQ_DEV_NOT_ONLINE		= 14;//I1
	public static final int CRE_CHQ_DEV_CABLE_BROKEN		= 15;//I2
	public static final int CRE_CHQ_SYS_MODL_NOT_SUPP	= 16;//I3
	public static final int CRE_CHQ_PREV_FUNC_NOT_COMPLT	= 17;//I5
	public static final int CRE_CHQ_DEV_NO_HANDLRS		= 18;//I8
	
	public static final int CRE_CHQ_GENERAL_FAIL			= 19;//U1
	public static final int CRE_CHQ_NO_RESPONSE			= 20;//U2
	public static final int CRE_CHQ_TRANS_ERR_ADPTR		= 21;//U3
	public static final int CRE_CHQ_DEV_SELF_TEST_ERR	= 22;//U4
	public static final int CRE_CHQ_DEV_DRVR_NAME_ERR	= 23;//U6

	public static final int CRE_ERROR_KILLING_SRVR		= 24;
	public static final int CRE_ERROR_CLOSING_SRVR		= 25;
	public static final int CRE_ERROR_EJECTING_SRVR		= 26;
	
	public static final int CRE_TIMEOUT					= 27;//2
	public static final int CRE_ERR_DOC_WRONGLY_FED		= 28;//1
	public static final int CRE_ERR_DOC_STUCK_INSIDE		= 29;//8
	public static final int CRE_ERR_BAD_CHARACTER		= 30;//5
	public static final int CRE_ERR_FRAME_ERROR			= 31;//7
	public static final int CRE_ERR_LOADING_OPTIONS		= 32;//4
	
	public static final int CRE_UNKNOWN_ERROR			= 99;

	/**
	 * Constructor for CRException.
	 */
	public CRException()
	{
		super();
	}

	public int GetErrorCode()
	{
		return iErrorCode;
	}

	/**
	 * Constructor for CRException.
	 * @param message
	 */
	public CRException(String message)
	{
		super(message);
	}

	public CRException(int iErrCode)
	{
		super("CRErrCode: "+iErrCode);
		iErrorCode=iErrCode;
	}


	public CRException(int iErrCode,String message)
	{
		super("CRErrCode: "+iErrCode+" "+message);
		iErrorCode=iErrCode;
	}
	/**
	 * Constructor for CRException.
	 * @param cause
	 */
	public CRException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * Constructor for CRException.
	 * @param message
	 * @param cause
	 */
	public CRException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
