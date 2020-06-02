package mataf.services.proxy;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import mataf.logger.GLogger;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProxyReceiveThread extends Thread {

	ServerSocket m_sock;
	boolean m_flag = true;
	RequestDispatcher m_dispatcher;
		
	
	/**
	 * Constructor for ProxyReceiveThread.
	 */
	public ProxyReceiveThread() {
		super();		
	}

	
	/**
	 * create the receive thread.
	 * @param port the port the thread should 
	 * listen for incoming messages.
	 */
	public ProxyReceiveThread(int port, RequestDispatcher rd)
		throws IOException
	{
		GLogger.info("binding to port :" + port);
		m_sock = new ServerSocket(port);
		m_dispatcher = rd;
		setName("ProxyReceiveThread");
	}

	/**
	 * stop the thread by setting the flag
	 * to false to exit the loop in the run
	 * method.
	 */
	public void stopThread()
	{
		m_flag = false;		
	}
	
	/**
	 * Thread's run method.
	 * loop and listen for 
	 * incoming messages.
	 * read the message 
	 * parse the message
	 * activate the appropriate function and 
	 * send the response.
	 */
	public void run()
	{
		GLogger.info("ProxyReceiveThread started ..");
		try {	
			Socket inSock= m_sock.accept();
			DataInputStream dis = new DataInputStream(inSock.getInputStream());
			DataOutputStream dos = new DataOutputStream(inSock.getOutputStream());
			byte b[] = null;
			int len = 0;	
			while(m_flag) {									
				try {	
					//GLogger.debug("entering while loop");			
					len = dis.readInt();
					b = new byte[len];
					dis.readFully(b,0,len);
					String req = new String(b);
					// process the request.
					//GLogger.debug("got : \n" + req);
					m_dispatcher.handleRequest(req);
					String reply = m_dispatcher.getReply();
					b = reply.getBytes();
					len = b.length;					
					dos.writeInt(len);
					dos.write(b);
					dos.flush();
					/*
					 *  get the XML reply string
					 * and send it to the caller.
					 */
				} catch(IOException ie) {
					/**
					 * in case of EOFException we need
					 * to call accept again since the 
					 * connection from the client was broken.
					 * unless the thread was stopped.
					 */
					if(m_flag) {
						GLogger.error(this.getClass(), null, null,ie,false);
						inSock= m_sock.accept();
						dis = new DataInputStream(inSock.getInputStream());
						dos = new DataOutputStream(inSock.getOutputStream());						

					}
				} 
				 
			}	// while	

			dos.close();
			dis.close();	
			inSock.close();
			m_sock.close();			
		} catch(Exception ie) {
			ie.printStackTrace();	
		}
			
		
	} // run
	
	
	
}
