package onjlib.ejb.emptest;

import java.io.Serializable;

public class Empinfo implements Serializable {
	int m_id;
	String m_name;
	int m_dept;

	public Empinfo(int id, String name, int dept)
	{
		m_id = id;
		m_name = name;
		m_dept = dept;
	}

	public String toString()
	{
		return(new String(m_id+ ":" + m_name + ":" + m_dept));
	}
}
