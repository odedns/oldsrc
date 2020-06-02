
import java.io.*;

public class RandomAccess {

	public static void main(String argv[])
	{
		try {
			
			RandomAccessFile rf = new RandomAccessFile("rnd.txt","rw");
			for(int i=0; i<10; ++i) {
				rf.writeInt(i);
			}
			rf.seek(0);
			System.out.println("i = " + rf.readInt());
			rf.seek(4*4);
			System.out.println("i = " + rf.readInt());
			rf.seek(4*2);
			System.out.println("i = " + rf.readInt());
			rf.writeInt(100);
			rf.seek(4*3);
			System.out.println("i = " + rf.readInt());
			
			rf.skipBytes(2000000);
			rf.writeInt(200);
			rf.close();


		} catch(Exception e) {
			e.printStackTrace();
		}

	} /* main */
} /* class */
		
