
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Tests {

  public Tests() {
  }


  /**
   * replace a pattern inside a string.
   * @param s the string to process.
   * @param oldPat the old pattern to replace.
   * @param newPat the new pattern.
   * @return a new processed String.
   */
  static String replaceStr(String s, String oldPat, String newPat)
  {
	int currIndex = 0;
	int index;
	int oldPatLen = oldPat.length();
	StringBuffer sb = new StringBuffer(s.length());
	String before=null;
	while(-1 < (index = s.indexOf(oldPat,currIndex))) {
		before=s.substring(currIndex,index);
		sb.append(before);
		sb.append(newPat);
		currIndex=index+ oldPatLen;
	}
	String after=s.substring(currIndex);
	sb.append(after);

	return(sb.toString());

  }
  public static void main(String argv[])
  {
	String s = "what the fuck is bar>? is this my bar> or yours ??";
	String oldPat="bar";
	String newPat="foo";

	System.out.println(replaceStr(s,oldPat,newPat));
	/*
		s = "a=http://yam.openu.ac.il/opus/bin/7-1 and ddd\"http://yam.openu.ac.il/opus/bin/7-1";
		String s2 = s.replaceAll("http://yam.openu.ac.il/opus/bin/7-1", "foo");
		System.out.println("s = " + s);
		System.out.println("s2 = " + s2);
		*/

	String url="http://localhost/opus?name=שלום עליכם&type=infolet";

	System.out.println(url);

	try {
		String newUrl = URLEncoder.encode(url);
		System.out.println(newUrl);
	}
	catch (Exception ex) {
		ex.printStackTrace();
	}




  }


}
