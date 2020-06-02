
package onjlib.ejb.mytest;

import javax.ejb.*;


public class MytestBean implements SessionBean {
	public SessionContext m_sc;
	public String m_data;


	public MytestBean()
	{
		System.out.println("constructor MytestBean called ");
	}

	public void setSessionContext(SessionContext sc)
	{
		m_sc = sc;
		System.out.println("*** setSessionContext ***");
		System.out.println("sessionContext = " + m_sc.toString());
	}

	public void ejbRemove()
	{
		System.out.println("*** ejbRemove ***");
	}

	public void ejbActivate()
	{
		System.out.println("*** ejbActivate ***");
	}

	public void ejbPassivate()
	{
		System.out.println("*** ejbPassivate ***");
		System.out.println("======================================");
	}

	public void ejbCreate()
	{
		System.out.println("======================================");
		System.out.println("*** ejbCreate ***");
		System.out.println("sessionContext = " + m_sc.toString());
	}

	public String getData()
	{
		System.out.println("getData() Called");
		return(m_data);
	}

	public void setData(String data)
	{
		System.out.println("setData() Called");
		m_data = data;
	}
}

