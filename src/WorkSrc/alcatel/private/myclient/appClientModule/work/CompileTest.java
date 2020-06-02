/*
 * Created on 13/11/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package work;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import onjlib.utils.FileSystemClassLoader;

import com.sun.tools.javac.Main;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */



public class CompileTest {

	public static void main(String[] args) {
		
		String s = "public class HelloWorld {\n public static void main(String[] args) {\nSystem.out.println(\"HelloWorld\"); \n}\n}";
		String file = "c:/temp/HelloWorld.java";
		try {

			/*
			 * create a java source file.
			 */
			PrintWriter out = new PrintWriter(new FileOutputStream(file));
			out.println(s);
		    out.flush();
		    out.close();
		    
		    /*
		     * compile the java source file.
		     */
			PrintWriter dos = new PrintWriter(new File("c:/temp/javac.log"));
			String argv[] = new String[] { file };
			int stat = Main.compile(argv,dos);
			if(stat != 0) {
				throw new Exception("Compile error");
			}
	
			/*
			 * now load the class.
			 * and invoke it main method.
			 *
			 */
			FileSystemClassLoader fcl = new FileSystemClassLoader("c:/temp");
			Object o  = fcl.findClass("HelloWorld").newInstance();
			Method mainMethod = o.getClass().getMethod("main", new Class [] {String [].class});
			Object list[] = new Object[1];
			mainMethod.invoke(o, list);
			
			System.out.println("invoked generated class");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
