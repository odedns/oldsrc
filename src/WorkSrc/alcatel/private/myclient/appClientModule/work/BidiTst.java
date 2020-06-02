/*
 * Created on 15/08/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package work;

import java.text.Bidi;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BidiTst {

	public static void main(String[] args) {
		
		String s = "תכשמנ תינכתה - 017 01 0001 יאמצעטנמלא יאמצעטנמלא תלעפהב הלקת";
		//String s = "דדוע";
		
		System.out.println("s = " +s );
		try {
		
			Bidi bidi = new Bidi(s,Bidi.DIRECTION_RIGHT_TO_LEFT);
			byte levels[] = new byte[bidi.getRunCount()];
			System.out.println("bid=" + bidi.toString());
			System.out.println("charAt(0)=" + s.charAt(0));
			char c[] = s.toCharArray();
			Character chars[] = new Character[c.length];
			for(int i=0; i < c.length; ++i) {
				chars[i] = new Character(c[i]);
			}
			Bidi.reorderVisually(levels,0,chars,0,levels.length);
			String s2 = charArrayToString(chars);
			System.out.println(s2);
			System.out.println("charAt(0)=" + chars[0]);
			/*
			for(int i=0; i < chars.length; ++i) {
				System.out.print(chars[i]);				
			}
			System.out.println();
			*/
			
			
		} catch(Exception ue) {
			ue.printStackTrace();
		}
		
	}
	
	static String charArrayToString(Character chars[])
	{
		char c[] = new char[chars.length];
		for(int  i=0; i < chars.length; ++i) {
			c[i] = chars[i].charValue();
		}
		
		String s = new String(c);
		return(s);
		
	}
}
