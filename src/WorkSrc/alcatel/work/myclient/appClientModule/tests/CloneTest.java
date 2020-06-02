package tests;

import java.lang.reflect.Field;




class MyBase implements Cloneable {
	protected int m_n;
	protected float m_f;
	
	MyBase()
	{
		m_n = 5;
		m_f = 105;
	}
	public Object clone()
	{
		Object o = null;
		try {
			o=super.clone();	
		} catch(CloneNotSupportedException ce) {
			ce.printStackTrace();	
		}
		return(o);
	}	
	
}


class MyDerived extends MyBase {
	private String m_data;
	
	MyDerived()
	{
	}
	MyDerived(String data)
	{
		m_data = data;
	}

	public String toString()
	{
		return(new String(m_n + ":" + m_f + ":" + m_data));
	}		
}
	
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CloneTest {

	/**
	 * Constructor for CloneTest.
	 */
	public CloneTest() {
		super();
	}
	
	/**
	 * perform a copy on an object by using
	 * reflections.
	 * @param o the object to clone.
	 * @return Object the cloned object or null if
	 * there was an error.
	 */
	public static Object copy(Object o)
	{
		Object copy = null;		
		
		try {
			Class c = o.getClass();
			copy = c.newInstance();
			Field fld[] = c.getDeclaredFields();	
						
			for(int i=0; i < fld.length; ++i) {
				System.out.println("field:" + fld[i].getName() + "=" + fld[i].get(o).toString());
				fld[i].set(copy,fld[i].get(o));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			copy = null;	
		}	
		return(copy);
	}

	public static void main(String[] args) {
		
		MyDerived d = new MyDerived("foo data");
		MyDerived d1 = (MyDerived) copy(d);
		
		System.out.println("d1 = " + d1.toString());		
	}
}
