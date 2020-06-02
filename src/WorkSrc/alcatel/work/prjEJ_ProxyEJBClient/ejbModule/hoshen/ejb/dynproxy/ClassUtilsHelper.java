/*
 * Created on 13/12/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package hoshen.ejb.dynproxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yakovl
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClassUtilsHelper {
	Class c;

	//	 Convert list
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * Given a <code>List</code> of class names, this method converts them
	 * into classes.
	 * </p>
	 * 
	 * <p>
	 * A new <code>List</code> is returned. If the class name cannot be found,
	 * <code>null</code> is stored in the <code>List</code>. If the class
	 * name in the <code>List</code> is <code>null</code>,
	 * <code>null</code> is stored in the output <code>List</code>.
	 * </p>
	 * 
	 * @param classNames
	 *            the classNames to change
	 * @return a <code>List</code> of Class objects corresponding to the class
	 *         names, <code>null</code> if null input
	 * @throws ClassCastException
	 *             if classNames contains a non String entry
	 */
	public static List convertClassNamesToClasses(List classNames) {
		if (classNames == null) {
			return null;
		}

		List classes = new ArrayList(classNames.size());
		for (Iterator it = classNames.iterator(); it.hasNext();) {
			String className = (String) it.next();
			try {
				classes.add(Class.forName(className));
			} catch (Exception ex) {
				try
				{
					classes.add(getPrimitiveType(className));
				}
				catch (Exception e) {
					classes.add(null);
				}
				
			}
		}
		return classes;
	}

	/**
	 * <p>
	 * Given a <code>List</code> of <code>Class</code> objects, this method
	 * converts them into class names.
	 * </p>
	 * 
	 * <p>
	 * A new <code>List</code> is returned. <code>null</code> objects will
	 * be copied into the returned list as <code>null</code>.
	 * </p>
	 * 
	 * @param classes
	 *            the classes to change
	 * @return a <code>List</code> of class names corresponding to the Class
	 *         objects, <code>null</code> if null input
	 * @throws ClassCastException
	 *             if <code>classes</code> contains a non- <code>Class</code>
	 *             entry
	 */
	public static List convertClassesToClassNames(List classes) {
		if (classes == null) {
			return null;
		}
		List classNames = new ArrayList(classes.size());
		for (Iterator it = classes.iterator(); it.hasNext();) {
			Class cls = (Class) it.next();
			if (cls == null) {
				classNames.add(null);
			} else {
				classNames.add(cls.getName());
			}
		}
		return classNames;
	}

	private static Map primitives = new HashMap();

	static {

		primitives.put("boolean", Boolean.TYPE);

		primitives.put("byte", Byte.TYPE);

		primitives.put("char", Character.TYPE);

		primitives.put("short", Short.TYPE);

		primitives.put("int", Integer.TYPE);

		primitives.put("long", Long.TYPE);

		primitives.put("double", Double.TYPE);

		primitives.put("float", Float.TYPE);

	}

	public static Class getPrimitiveType(String name) {

		return (Class) primitives.get(name);

	}

}
