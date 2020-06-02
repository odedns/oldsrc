/* Created on 11/09/2006 */
package work;

import java.io.*;
import java.net.URL;

import onjlib.utils.Path;

/**
 * 
 * @author Odedn
 */
public class FileRename {

	public static void main(String[] args)
	{
		String name = "test.txt";
		System.out.println("FileRename.main()");
		String fname  = Path.findFileInClassPath(name);
		if(fname == null) {
			System.out.println("file not found: " + name);
		} else {
			System.out.println("fname = " + fname);
			File f = new File(fname);
			File toFile = new File(f.getParentFile().toString() + '/' + "test.bak");
			System.out.println("toFile= " + toFile.getPath());
			f.renameTo(toFile);
		}
		
	}
	
	
	
	
	/**
	 * finds the specific file in the system classpath.
	 * useful for finding configuration files.
	 * @param name the file name.
	 * @return InputStream an InputStream to the located file.
	 */
	public static URL findFile(String name)
	{
		URL url = name.getClass().getResource("/" + name);
		if(url == null) {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			url = cl.getResource("/" + name);			
		}
		return(url);
	}
}
