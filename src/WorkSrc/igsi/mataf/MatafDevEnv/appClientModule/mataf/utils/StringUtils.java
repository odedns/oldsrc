package mataf.utils;

/**
 * @author odedn
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


}
