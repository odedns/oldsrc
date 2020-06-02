package jlicense;
import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class JlicenseGenerator {

        public JlicenseGenerator() {
        }

	public static void generateWrapper(String className) throws IOException
	{
		/**
		 * generate a wrapper for the specific class.
		 */
		StringBuffer sb = new StringBuffer(200);
		sb.append("import java.lang.reflect.*;\n");
		sb.append("public class " + className + "Wrapper { \n public static void main(String argv[]) {\n");
		sb.append("try {\n Class main = Class.forName(\"" + className + "\");\n");
		sb.append("String s[] = new String[1];\n Class args[] = new Class[1];\n");
		sb.append("args[0] =  s.getClass();\n"	+ className +  " a = (" + className + ") main.newInstance();\n");
		sb.append("Method m = main.getMethod(\"main\",args);\n Object margs[] =  new Object[1];\n");
		sb.append("margs[0] = argv;\n    	m.invoke(a,margs);\n");
		sb.append(" } catch(Exception e) {\n e.printStackTrace();\n}\n }\n}\n");
		BufferedWriter bw = new BufferedWriter(new FileWriter(className + "Wrapper.java"));
		bw.write(sb.toString());
		bw.close();



	}
	public static void main(String argv[])
	{
		try {
			JlicenseGenerator.generateWrapper("Foo");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}


	}
}