import java.io.*;
import java.util.*;

class Data {
	String s;
}
class EHashtable  extends Hashtable {
	Data getData()
	{
		return(null);
	}
}

	

class B {
	public static void main(String argv[])
	{
		EHashtable hash = new EHashtable();
		hash.put("one","1");
		hash.put("two","2");

		System.out.println("hash = " + hash.toString());
		try {
			FileOutputStream fout = new FileOutputStream("serialize.dat");
			/*
			ObjectOutputStream o = new ObjectOutputStream(fout);
			o.writeObject(hash);
			o.flush();
			fout.close();
			*/

			ByteArrayOutputStream bos = new ByteArrayOutputStream(5000);
			ObjectOutputStream o2 = new ObjectOutputStream(bos);
			o2.writeObject(hash);
			o2.flush();
			System.out.println("bos size = " + bos.size());
			bos.writeTo(fout);
			fout.close();

			/*
			 * read object from file.
			 */

			FileInputStream fin = new FileInputStream("serialize.dat");
			ObjectInputStream in = new ObjectInputStream(fin);
			Hashtable h = (Hashtable)in.readObject();
			System.out.println("h = " + h.toString());



		} catch(Exception e) {
			e.printStackTrace();
		}
		

	}
}



