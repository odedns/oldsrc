package tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;



public class NIOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String fname = "c:/temp/HelloWorld.java";
		try {
			File f = new File(fname);
			long l = f.length();
			System.out.println("file len =" + l);
			FileInputStream fs = new FileInputStream(fname);			
			FileChannel fc = fs.getChannel();
			ByteBuffer dst = ByteBuffer.allocate((int)l);
			int stat = fc.read(dst);
			byte b[] = dst.array();
			String s = new String(b);
			System.out.println("len=" + b.length);
			System.out.println(s);
			fc.close();
			fs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
