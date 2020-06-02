import java.io.*;

public class Readuni {

	public static void main(String argv[])
	{
		System.out.println("Now reading unicode ");
		System.out.print("enter line : ");
		try {
			BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
			String line = br.readLine();
			System.out.println("line = " + line);

			System.out.println("Now reading ascii ");
			DataInputStream dis = new DataInputStream(System.in);
			line = dis.readLine();
			System.out.println("line = " + line);
		} catch(IOException ie) {
			ie.printStackTrace();
		}

	}
}
