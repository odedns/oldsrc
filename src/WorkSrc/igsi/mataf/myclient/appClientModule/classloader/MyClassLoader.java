package classloader;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyClassLoader extends ClassLoader {


	public MyClassLoader(ClassLoader parent)
	{
		super(parent);	
	}
	/**
	 * @see java.lang.ClassLoader#findClass(String)
	 */

	protected Class findClass(String name) throws ClassNotFoundException {
		System.out.println("finding class : " + name);
		return super.findClass(name);
	}

	/**
	 * @see java.lang.ClassLoader#loadClass(String)
	 */
	public Class loadClass(String name) throws ClassNotFoundException {
		System.out.println("loadClass(): " + name);
		return super.loadClass(name);
	}

	/**
	 * @see java.lang.ClassLoader#loadClass(String, boolean)
	 */
	protected synchronized Class loadClass(String name, boolean resolve)
		throws ClassNotFoundException {
		System.out.println("loadClass(resolve):"  + name);
		return super.loadClass(name, resolve);
	}

}
