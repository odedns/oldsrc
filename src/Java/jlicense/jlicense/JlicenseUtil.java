package jlicense;

import java.util.jar.*;
import java.io.*;
import java.util.*;

/**
 * Title:       JlicenseUtil
 * Description: Utility functions for the Jlicense project.
 * Copyright:    Copyright (c) 2001
 * Company:     NA
 * @author      Oded Nissan
 * @version 1.0
 */



public class JlicenseUtil {

        public JlicenseUtil() {
        }

	public static byte[] findInClasspath(String name)
	{
		return(findInClasspath(name, "class"));
	}
	/**
	 * find a class or a jar in the classpath.
	 * @param name the name of the class to find.
	 * @return String the full path of the file.
	 */
	public static byte[] findInClasspath(String name, String ext)
	{
		String classPath = System.getProperty("java.class.path");
		System.out.println("classPath = " + classPath);
		StringTokenizer st = new StringTokenizer(classPath,";");
		String dir;
		String full=null;
		Vector jarList = new Vector();
		boolean found = false;
		byte b[] = null;
		while(st.hasMoreElements()) {
			dir= st.nextToken();
			if(dir.endsWith(".jar")) {
				jarList.add(dir);
				continue;
			}
			full = dir + "/" + name + "." + ext;
			System.out.println("trying to find : " + full);
			try {
				File f = new File(full);
				if(f.exists()) {
					found = true;
					break;
				}
				// file was found break out of loop.
			}
			catch (Exception ex) {
			        ex.printStackTrace();
			}
		}
		if(found) {
			b = readClassFile(full);
		} else {
			Object jars[] = jarList.toArray();
			for(int i=0; i<jars.length; ++i) {
				String js = (String ) jars[i];
				b= getFileFromJar(name,ext,js);
				if(null !=b) {
					break;
				}
			}

		}

		return(b);
	}


	public static byte[] getClassFromJar(String className, String jarFileName)
	{
		return(getFileFromJar(className,"class",jarFileName));
	}
	public static byte[] getFileFromJar(String className, String ext, String jarFileName)
	{
		try {
			String name = className.replace('.','/') + "." + ext;
			System.out.println("string to find : " + name + " in : " + jarFileName);
		        JarFile jf = new JarFile(new File(jarFileName));
			JarEntry je = (JarEntry)jf.getEntry(name);
			if(null == je) {
				return(null);
			}
	        	BufferedInputStream bis = new BufferedInputStream(jf.getInputStream(je));
		        byte b[] = new byte[bis.available()];
			System.out.println("available = " + bis.available());
	        	bis.read(b);
		        bis.close();
			return(b);
		} catch(Exception e) {
			e.printStackTrace();
			return(null);
		}
	}

	private static byte[] readClassFile(String fname)
	{
		byte b[] = null;
		try {
		        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fname));
			b = new byte[bis.available()];
	                bis.read(b);
		        bis.close();
		} catch (IOException e) {
			e.printStackTrace();
			b = null;
		}
		return(b);
	}
	public static void main(String argv[])
	{
		System.out.println("in JlicenseUtil.main() ");
		byte b[] = findInClasspath("javax.ejb.EJBContext");

		if(b != null) {
			System.out.println("found ...");
		} else {
			System.out.println("not found ...");
		}



	}
}