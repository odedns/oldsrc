package work;
import java.util.*;
import java.io.*;


public class T1 {


    private static String fixValue(String value)
    {
        StringBuffer buf = new StringBuffer();
        int size = value.length();
        for(int i = 0; i < size; i++)
        {
            char ch = value.charAt(i);
            if(ch != '\\')
                buf.append(ch);
        }

        return buf.toString();
    }

    public static Properties readProperties(BufferedReader r)
        throws IOException
    {
        Properties p = null;
        String line = null;
        String attribute = null;
        String value = null;
        int pos = 0;
        while((line = r.readLine()) != null) 
        {
            line = line.trim();
            if(line.length() != 0 && !line.startsWith("#"))
            {
                pos = line.indexOf("=");
                if(pos == -1)
                {
                    System.out.println("Invalid line in config file: " + line);
                } else
                {
                    attribute = line.substring(0, pos).toLowerCase().trim();
                    value = line.substring(pos + 1).trim();
//                    value = fixValue(value);
                    if(p == null)
                        p = new Properties();
                    p.put(attribute, value);
                }
            }
        }
        r.close();
        return p;
    }


		public static void main(String args[])
		{
			Properties p = null;
			String fname = "d:/ldapbrowser/mataf.cfg";
			try {
				
			    BufferedReader r = new BufferedReader(new FileReader(fname));
    	        p = readProperties(r);
        	    if(p != null)	{
        	    	System.out.println("p = " + p.toString());	
        	    }
				
			} catch(Exception e) {
				e.printStackTrace();	
			}

		}

}
