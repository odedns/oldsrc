
import java.net.*;
import java.io.*;


class ProxyServer {

ServerSocket m_srvSock;
Socket m_wsock;
DataInputStream m_webIn;
DataOutputStream m_webOut;


void fail(Exception e, String msg)
{
	System.err.println(msg + "." + e);
	System.exit(1);
}

ProxyServer(int port) 
{
	try {
		m_srvSock = new ServerSocket(port,2);
	}
	catch(IOException e) {
		fail(e, "ProxyServer");
	}

}

public void run()
{
	Socket clntSock;
	DataInputStream dis;
	DataOutputStream dos;
	byte b[] = new byte[1024];

	try {
		while(true) {
			clntSock = m_srvSock.accept();
			dis = new DataInputStream(
					clntSock.getInputStream());
			dos = new DataOutputStream(clntSock.getOutputStream());
			dis.read(b);
			System.out.println("Browser Connection");
			String s = new String(b);
			System.out.println("length = " + b.length);
			System.out.println("data = " + s);

			/*
			 * open socket to the real web server
			 * each http request means a new socket
			 * to the server.
			 */
			m_wsock = new Socket("localhost",80);
			m_webIn = new DataInputStream(m_wsock.getInputStream());
			m_webOut = new DataOutputStream(m_wsock.getOutputStream());
			System.out.println("Sending to web server");
			m_webOut.writeBytes(s);
			System.out.println("Receiving from web server");
			int n = 0;
			n = m_webIn.read(b);
			while(n > 0) {
			//	System.out.println("datalen = " + n);
				dos.write(b);
				dos.flush();
				n = m_webIn.read(b);
			}
			m_webIn.close();
			m_webOut.close();
			m_wsock.close();
		}

	} catch(IOException e) {
		System.out.println("Error " + e);
	}

} // end run

public static void main(String[] args)
{
	ProxyServer srv = new ProxyServer(8080);

	System.out.println("Starting ProxyServer .... ");
	srv.run();

}


} 
