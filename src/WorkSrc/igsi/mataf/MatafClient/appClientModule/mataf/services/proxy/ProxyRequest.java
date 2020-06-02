package mataf.services.proxy;

import java.util.HashMap;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProxyRequest {

	int m_command;
	long m_id;
	HashMap m_params;
	/**
	 * Constructor for ProxyRequest.
	 */
	public ProxyRequest() {
		super();
		m_params = new HashMap();
		m_id = System.currentTimeMillis();
	}
	
	/**
	 * create a ProxyRequest object.
	 * @param cmd the command.
	 * @param params the HashMap containing request params.
	 */
	public ProxyRequest(int cmd, HashMap params)
	{
		m_command = cmd;
		m_params = params;
	}
	
	/**
	 * get the command string.
	 * 	@return String the command string/
	 */
	public int getCommand()
	{
		return(m_command);
	}
	
	
	/**
	 * set the command for the request.
	 * @param command the command to execute.
	 */
	public void setCommand(int cmd)
	{
		m_command = cmd;
	}
	
	/**
	 * get the request id.
	 * @return id the request id.
	 */
	public long getId()
	{
		return(m_id);
	}
	/**
	 * set the request id.
	 * @param id the request id.
	 */
	public void setId(long id)
	{
		m_id= id;
	}

	/**
	 * get the request parameters.
	 * @return HashMap containing the request parameters.
	 */	
	public HashMap getParams()
	{
		return(m_params);
	}
	
	/**
	 * set the request parameters.
	 * @param params the HashMap containing 
	 * request parameters.
	 */
	public void setParams(HashMap params)
	{
		m_params = params;
	}
	
	/**
	 * add a parameter to the request.
	 * @param name the name of the paramteter.
	 * @param val the value of the parameter.
	 */
	public void addParam(String name, Object val)
	{
		m_params.put(name,val);
	}
	
	/**
	 * format the ProxyRequest to a String.
	 * @return String the PrxoyRequest as a String.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(m_command);
		sb.append("-");
		sb.append(m_id);
		sb.append("-");
		sb.append(m_params.toString());
		return(sb.toString());		
			
	}

}
