
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import onjlib.utils.Console;

class URLTest
{

    public static void main(String args[])
    {
        try
        {
  	    String urls = Console.readString("Enter Url:");
            URL url = new URL(urls);
            URLConnection urlconnection = url.openConnection();
            System.out.println("conetentype = " + urlconnection.getContentType());
            InputStream inputstream = urlconnection.getInputStream();
            DataInputStream datainputstream = new DataInputStream(inputstream);
            String s;
            while((s = datainputstream.readLine()) != null)
                System.out.println(s);
            datainputstream.close();
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

    URLTest()
    {
    }
}
