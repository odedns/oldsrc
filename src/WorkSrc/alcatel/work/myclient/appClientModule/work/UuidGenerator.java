/**
 * Created on 27/03/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package work;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UuidGenerator
{
	static int ip = 0;
	static int counter = 0;
	private static UuidGenerator uuid = null;
	/**
	 * 
	 */
	public static UuidGenerator getInstance() 
	{		
		if(null != uuid) {
			return(uuid);		
		}

		uuid = new UuidGenerator();
		try {
		
			InetAddress addr = InetAddress.getLocalHost();
			byte b[]= addr.getAddress();
			int ip = bytesToInt(b);
		} catch(Exception e){
			ip = 0;
			e.printStackTrace();		
		}
		return(uuid);
	}

	/**
	 * convert a byte array into an int.
	 * @param b byte array to convert.
	 * @return int.
	 */
	public static int bytesToInt(byte b[])
	{
		int n;
		n = (int )b[0];
		n |= (int ) b[1] << 8;
		n |= (int ) b[2] << 16;
		n |= (int ) b[3] << 24;
		return(n);
	}

	
   private short getHiTime() {
	   return (short) ( System.currentTimeMillis() >>> 32 );
   }
   private int getLoTime() {
	   return (int) System.currentTimeMillis();
   }


	public long generate()
	{
		++counter;
		long millis = System.currentTimeMillis();
		long l = ip + counter + millis;
		System.out.println("ip= " + ip + "\tcounter=" + counter);
		return(l);	
	}
	
	public static void main(String args[])
	{
		for(int i=0; i < 100; ++i) {
		
			long l =  UuidGenerator.getInstance().generate();
			String s = Long.toHexString(l);
			System.out.println("Uuid =" + s);
		}
	}
	
}
