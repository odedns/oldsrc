package mataf.utils;

import java.util.*;
/**
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringUtils {

	/**
	 * Constructor for StringUtils.
	 */
	private StringUtils() {
		super();
	}
	/**
	 * trims leading and trailing whitespaces from the string.
	 * @param s the string to trim from.
	 * @return a trimmed string.
	 **/
	public static String trim(String s)
	{
		int len = s.length();
		int nlen = len;
		int offs = 0;
		char v[] = new char [len];

		s.getChars(0,len, v,0);
		for(int i = len-1; i >=0 && Character.isWhitespace(v[i]); --i) {
			nlen--;
		}
		for(int i = 0; i < nlen && Character.isWhitespace(v[i]); ++i) {
			offs++;
		}
		return (new String(v,offs,nlen - offs));
	}
	
	
  public static String handleCommas(String s)
  {
		  StringBuffer sb = new StringBuffer(s);
		  sb = handleCommas(sb);
		  return(sb.toString());
  }

  /**
   * Convert single commas to double commas, so that sql can handle
   * the input string.
   *
   */
  public static StringBuffer handleCommas(StringBuffer sb)
  {
    char c;
    StringBuffer sb2 = new StringBuffer(sb.length());
    for(int i=0; i < sb.length(); ++i) {
      c = sb.charAt(i);
      if(c == '\'') {
        sb2.append('\'');
      }
      sb2.append(c);
    } // for
    return(sb2);

  }
	/**
     * convert a String containing serveral Strings seperated by
	 * the delimiter char into a String array.
	 * @param s the String to split.
	 * @param delim the delimiter character in the String.
	 * @return String[] a String array.
	 */
	public static String[] toStringArray(String s, char delim)
	{
		ArrayList ar = new ArrayList();
		StringTokenizer st = new StringTokenizer(s, new Character(delim).toString());
		String token=null;
		while(st.hasMoreTokens()) {
			token = st.nextToken();
			ar.add(token.trim());
		}
		String v[] = new String[ar.size()];
		return((String[])ar.toArray(v));
	}


	/**
	 * check if a String comtains a given substring.
	 * @param s the String to search.
	 * @param subs the substring to find.
	 * @return boolean true if a match is found or false
	 * otherwise.
	 */
	public static boolean contains(String s , String subs)
	{
		boolean res = false;
		int slen = s.length();
		int subLen = subs.length();
		String tmp = null;
		for(int i=0; i <= slen - subLen; ++i) {
			tmp = s.substring(i, i + subLen);
			if(tmp.equals(subs)) {
				res = true; 
				break;	
			}
			
		}
		return(res);		
		
	}

}
