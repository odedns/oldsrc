package work;
import java.text.Bidi;


/*
 * Created on 10/08/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author odedn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Tst {

	public static void main(String[] args) {
		
		String s = "תכשמנ תינכתה - 017 01 0001 יאמצעטנמלא יאמצעטנמלא תלעפהב הלקת";
		//String s = "דדוע";
		
		System.out.println("s = " +s );
		try {
		
			Bidi bidi = new Bidi(s,Bidi.DIRECTION_RIGHT_TO_LEFT);
			byte levels[] = new byte[bidi.getRunCount()];
			String v[] = new String[levels.length];
		
			//v[0] = s;
			for(int i=0; i < levels.length; ++i) {
				int start = bidi.getRunStart(i);
				int limit = bidi.getRunLimit(i);
				int level = bidi.getRunLevel(i);
				String str = s.substring(start,limit);
				levels[i] = (byte)level;
				v[i] = str;				
				
			} // for
			
			Bidi.reorderVisually(levels,0,v,0,levels.length);
			System.out.println("v[0]=" + v[0]);
			
				
			/*
			System.out.println("file.encoding =" + System.getProperty("file.encoding"));
			SortedMap map = Charset.availableCharsets();
			System.out.println("map=" + map.toString());
			String s2 = new String(s.getBytes("ISO-8859-8"),"Cp1252");
			System.out.println("s2 = " +s2 );
		*/
		} catch(Exception ue) {
			ue.printStackTrace();
		}
		
	}
}
