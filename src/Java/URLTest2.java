// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   URLTest.java

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

class URLTest2
{

    public static void main(String args[])
    {
        try
        {
            URL url = new URL("http://localhost:7001/login.jsp");
            URLConnection urlconnection = url.openConnection();
	    /*
            System.out.println("conetentype = " + urlconnection.getContentType());
            InputStream inputstream = urlconnection.getInputStream();
            DataInputStream datainputstream = new DataInputStream(inputstream);
            String s;
            while((s = datainputstream.readLine()) != null)
                System.out.println(s);
            datainputstream.close();
	    */
            String s1;
            for(int i = 0; (s1 = urlconnection.getHeaderField(i)) != null; i++)
                System.out.println(i + " : " + s1);

            System.in.read();
            return;
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
