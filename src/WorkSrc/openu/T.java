import java.util.Date;
import java.util.TimeZone;
import java.net.*;

import onjlib.utils.Console;

public class T {


		public static String spacesToHttp(String s)
		{
			StringBuffer sb = new StringBuffer();
			char c=0;
			for(int i=0; i < s.length(); ++i) {
				c = s.charAt(i);
				if(c == ' ') {
					sb.append("%20");
				} else {
					sb.append(c);
				}
			} // for
			return(sb.toString());

		}

		public static String compressSpaces(String s)
		{
			boolean gotSpace = false;
			char c;
			StringBuffer sb = new StringBuffer(s.length());
			for(int i=0; i < s.length(); ++i) {
				c = s.charAt(i);
				if(Character.isSpaceChar(c)) {
				    if(!gotSpace) {
						sb.append(c);
						gotSpace = true;
				    }
				} else {
						sb.append(c);
						gotSpace = false;
				}

			}
			return(sb.toString());
		}

		public static void main(String argv[])
		{
				System.out.println("Date=" + new Date().toString());
				System.out.println("TimeZone: " + TimeZone.getDefault().toString());
				System.out.println("user.timezone: " + System.getProperty("user.timezone"));
				try {
					String s = URLEncoder.encode("test oded","UTF-8");
	    			System.out.println("s = " + s);
					s = T.spacesToHttp("oded Test");
	    			System.out.println("s = " + s);

					s = Console.readString("Enter String :");
					System.out.println("before compress spaces=" + s);
					System.out.println("after compress spaces=" + compressSpaces(s));
				}
				catch (java.io.UnsupportedEncodingException ex) {
				    ex.printStackTrace();
				}



		}

}
