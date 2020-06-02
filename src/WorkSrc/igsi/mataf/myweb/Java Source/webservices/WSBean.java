package webservices;

import java.io.Serializable;

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
	}
	
	public String getData()
	{
		return(m_data);	
	}
}
