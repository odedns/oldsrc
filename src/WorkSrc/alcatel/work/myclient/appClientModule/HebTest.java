/*
 * Created on 11/08/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HebTest {

	public static void main(String[] args) {
		
		String s = " מחרוזת בעברית ";
		String s2 = " מחרוזת נוספת ";
		System.out.println(s);
		
		StringBuffer sb = new StringBuffer();
		sb.append(s);
		sb.append(s2);
		
		System.out.println(sb.toString());
	}
}
