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
public class ProxyReply {
	long m_id;
	int m_retCode;
	HashMap m_params;
	int m_errCode;	
	String m_errMsg;
	
	
	/**
	 * create a proxyReply object.
	 */
	public ProxyReply()
	{
		m_params = new HashMap();
	}


	/**
	 * get the request id.
	 * @return id the request id.
	 */
	public int getRetCode()
	{
		return(m_retCode);
	}
	/**
	 * set the request return code.
	 * @param retCode the request return code.
	 */
	public void setRetCode(int retCode)
	{
		m_retCode = retCode;
	}

	/**
	 * get the request error code.
	 * @return int the request error code.
	 */
	public int getErrCode()
	{
		return(m_errCode);
	}
	/**
	 * set the request error code
	 * @param errCode the request error code.
	 */
	public void setErrCode(int errCode)
	{
		m_errCode = errCode;
	}
	
	
	/**
	 * get the error message.
	 * @return the error message.
	 */
	public String getErrMsg()
	{
		return(m_errMsg);
	}
	
	/**
	 * set the error message.
	 * @param msg the error message to set.
	 */
	public void setErrMsg(String msg)
	{
		m_errMsg = msg;
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
		sb.append(m_id);
		sb.append("-");
		sb.append(m_params.toString());				
		return(sb.toString());		
			
	}
}
