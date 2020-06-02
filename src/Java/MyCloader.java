
import java.lang.reflect.*;


/*
class MyCloader extends ClassLoader {
	public synchronized Class loadClass(String name, boolean b)
		throws ClassNotFoundException
	{
		System.out.println("loading class " + name);
		return(null);
	}
	
}
*/


class Wrapper {
	public static void main(String argv[])
	{
		try {
			//ClassLoader loader = new MyCloader();
			// Class main = loader.loadClass("A",true).newInstance();
			Class main = Class.forName("A");
			String s[] = new String[1];
			Class args[] = new Class[1];
			args[0] =  s.getClass();
			A a = (A) main.newInstance();
			Method m = main.getMethod("main",args);
			Object margs[] =  new Object[1];
			margs[0] = s;
			m.invoke(a,margs);

		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}
	
