package webservices;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WSBean implements Serializable {

	String m_data;
	
	public void setData(String data)
	{
		m_data = data;	
		System.out.println("setData: " + data);
	}
	
	public String getData()
	{
		System.out.println("getData: " + m_data);
		return(m_data);	
	}
	
	public HashMap  execute(HashMap params)
	{
		System.out.println("params=" + params.toString());
		params.put("1","one");
		return params;
					
	}
}
