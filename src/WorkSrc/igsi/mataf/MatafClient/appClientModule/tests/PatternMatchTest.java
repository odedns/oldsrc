package tests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author O702998
 *
 * Class: PatternMatchTest.java
 * Description:
 * Created at: 14/01/2004
 * 
 */
public class PatternMatchTest {

	public static void main(String[] args) {
		try {
//			Pattern p = Pattern.compile("(\\p{Alpha}\\p{Alnum}+)(\\.?)(\\p{Alpha}\\p{Alnum}+)\\((\\p{Digit}+)\\)");
			String sp1 = "~(\\w+)";	
			String sp2 = "^(\\p{Alpha}\\w+)\\.?(\\p{Alpha}\\w+)\\((\\p{Digit}+)\\)";	
			String st = "Record_11.~field_22(12)";
			String sr = "";
			Pattern p = Pattern.compile(new String(sp1));
			Matcher m = p.matcher(new String(st));
			StringBuffer sb = new StringBuffer();	
			while (m.find()) {
				m.appendReplacement(sb,st.substring(m.start(1),m.end(1))+"_DESC");
			}
			m.appendTail(sb);
			sr = new String(sb);
			System.out.println(sb);				
			p = Pattern.compile(new String(sp2));
			m = p.matcher(new String(sr));
			while (m.find()) {
				sr = sr.substring(m.start(1),m.end(1))+"."+sr.substring(m.start(3),m.end(3))+"."+sr.substring(m.start(2),m.end(2));
			}
			System.out.println(sr);				
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
