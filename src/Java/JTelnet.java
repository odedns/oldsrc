
import java.io.*;
import java.net.*;

public class JTelnet {

	public static void main(String argv[])
	{
		byte b[]  = new byte[1024];
		try {
			InetAddress addr = InetAddress.getByName("localhost");
			Socket s = new Socket(addr,23);
			BufferedInputStream in = new BufferedInputStream(s.getInputStream());

			BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
			while(true) {
				in.read(b,0,200);
				System.out.println("b = " + b);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}


	}
}
