package work;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

class MyFoo {	
	String m_s;
	int m_n;
	
	public String toString()
	{
		return(m_s + ":" + m_n);
	}
}

public class T2 {

	
	static void setFoo(MyFoo f)
	{
		f.m_s = "some string";
		f.m_n = 100;	
	}
	static void setFoo2(MyFoo f)
	{
		MyFoo f2= new MyFoo();
		f2.m_s = "some string2";
		f2.m_n = 102;	
		f = f2;
	}
	
	public static void main(String[] args) {
		
		MyFoo f = new MyFoo();
		setFoo(f);
		System.out.println("f = " + f.toString());
		setFoo2(f);
		System.out.println("f = " + f.toString());

	}
}
