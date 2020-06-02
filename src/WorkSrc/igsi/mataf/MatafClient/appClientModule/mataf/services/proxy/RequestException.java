package mataf.services.proxy;

/**
 * @author Oded Nissan 11/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RequestException extends Exception {
	
	int m_errCode;
	String m_msg;
	/**
	 * Constructor for RequestException.
	 */
	public RequestException() {
		super();
	}

	/**
	 * Constructor for RequestException.
	 * @param arg0
	 */
	public RequestException(String arg0) {
		super(arg0);
	}
	
	public RequestException(int code, String msg)
	{
		m_errCode = code;
		m_msg = msg;	
	}
	
	public String getMessage()
	{
		return(m_msg);	
	}
	
	public int getErrorCode()
	{
		return(m_errCode);
	}

}
