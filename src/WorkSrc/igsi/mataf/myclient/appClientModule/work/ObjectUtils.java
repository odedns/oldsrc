package work;
import java.io.*;
import java.lang.reflect.*;



class Test implements Serializable {
	int n;
	String s;
	float f;
	transient int i;
	
	public String toString()
	{
		return(s + "-" + n + "-" + f + "-" + i);	
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
public class ObjectUtils {

	private ObjectUtils()
	{
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
			Field fld[] = c.getFields();	
						
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


	
	/**
	 * perform a deep clone on an object by using
	 * serialization.
	 * @param o the object to clone.
	 * @return Object the cloned object or null if
	 * there was an error.
	 */
	public static Object deepCopy(Object o)
	{
		Object copy = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(o);
			os.flush();
			os.close();			
			byte b[] = bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			ObjectInputStream is = new ObjectInputStream(bis);		
			copy = is.readObject();			
		} catch(Exception ie) {
			ie.printStackTrace();
			copy = null;	
		}
	
		return(copy);	
	}
	
	
	public static void main(String args[])
	{
		String s = "foo";
		
		String copy = (String) ObjectUtils.deepCopy(s);
		System.out.println("copy = " + copy);
		
		Test t = new Test();
		t.n = 10;
		t.f = (float) 1.1;
		t.s = "foo";
		t.i = 1000;
		
		System.out.println("t = " + t.toString());
		
		Test tCopy = (Test) ObjectUtils.copy(t);
		System.out.println("tCopy = " + tCopy.toString());
		
	}
}
