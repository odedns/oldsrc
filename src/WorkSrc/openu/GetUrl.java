

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class GetUrl
{

    public static void main(String argv[])
    {

		if(argv.length < 1) {
			System.out.println("usage GetUrl <url>");
			System.exit(1);
		}
        try
        {
            URL url = new URL(argv[0]);
            URLConnection urlconnection = url.openConnection();
            InputStream inputstream = urlconnection.getInputStream();
            DataInputStream datainputstream = new DataInputStream(inputstream);
            String s;
            while((s = datainputstream.readLine()) != null)  {
                System.out.println(s);
			}
            datainputstream.close();
			/*
            String s1;
            for(int i = 0; (s1 = urlconnection.getHeaderField(i)) != null; i++)
                System.out.println(i + " : " + s1);

            System.in.read();
            return;
			*/
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
