package jlicense;

import java.io.*;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class JlicenseClassLoader extends ClassLoader {

        public JlicenseClassLoader() {
		super();
		System.out.println("JlicenseClassloader() ...");
        }

	public JlicenseClassLoader(ClassLoader parent)
	{
		super(parent);
		System.out.println("JlicenseClassloader(parent) ...");
	}



 /**
   *  This is the method where the task of class loading
   *  is delegated to our custom loader.
   *
   * @param  name the name of the class
   * @return the resulting <code>Class</code> object
   * @exception ClassNotFoundException if the class could not be found
   */
  protected Class findClass(String name) throws ClassNotFoundException
  {
    FileInputStream fi = null;

    try
    {


      String path = "/dev/classes/" + name + ".dat";
      System.out.println("MyClassLoader finding class: " + path);
      fi = new FileInputStream(path);
      byte[] classBytes = new byte[fi.available()];
      fi.read(classBytes);
      System.out.println("class read ... classBytes.length = " + classBytes.length);
      //definePackage(name);
      return(defineClass(name, classBytes, 0, classBytes.length));

    }    catch (Exception e)    {
      // We could not find the class, so indicate the problem with an exception
      e.printStackTrace();
      throw new ClassNotFoundException(name);
    }
    finally
    {
      if ( null != fi )
      {
        try
        {
          fi.close();
        }
        catch (Exception e){}
      }
    }
  }
}
