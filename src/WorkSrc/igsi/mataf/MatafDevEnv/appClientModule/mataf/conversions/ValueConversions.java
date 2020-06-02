package mataf.conversions;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ValueConversions {

	static HashMap m_typeMap = new HashMap();
	static {
		m_typeMap.put("0","record");
		m_typeMap.put("1","number");
		m_typeMap.put("2","amount");
		m_typeMap.put("3","english");
		m_typeMap.put("4","hebrew");
		m_typeMap.put("5","HHMMSS");
		m_typeMap.put("6","boolean");
		m_typeMap.put("7","MMYY");
		m_typeMap.put("8","DDMM");
		m_typeMap.put("9","YYMMDD");
		m_typeMap.put("10","DDMMYY");
		m_typeMap.put("11","DDMMYYYY");
		m_typeMap.put("12","YYYYMMDD");
		m_typeMap.put("13","binary date");
		m_typeMap.put("14","packed");
		m_typeMap.put("15","binary");
		m_typeMap.put("16","hex");
		m_typeMap.put("17","choice");
		m_typeMap.put("18","bit");
		m_typeMap.put("19","logical key");
		m_typeMap.put("20","password");
		m_typeMap.put("21","null type");
		m_typeMap.put("22","MMYYYY");		
		m_typeMap.put("23","DDYYYY");
		m_typeMap.put("24","YY");
		m_typeMap.put("25","YYYY");
		m_typeMap.put("26","transaction id");
		m_typeMap.put("27","MMDD");
		m_typeMap.put("28","HHMM");
		m_typeMap.put("29","default");		
		
	}
	/**
	 * Constructor for ValueConversions.
	 */
	private ValueConversions() {
		super();
	}
	
	/**
	 * get a value from the field map type.
	 */
	public static String getFieldType(String type)
	{
		String s= (String) m_typeMap.get(type);
		return(s);
	}
	
	/**
	 * convert hebrew yn string to
	 * a boolean.
	 * @param value the value of the hebrew yn
	 * String.
	 * @return a String containin "true" or "false".
	 */
	public static String cvtYn(String value)
	{
		String s = "false";
		if(value.length() < 1) {
			return(s);
		}
		if(value.equals("ë") || 0x8B == (byte) value.charAt(0)) {
			s = "true";
		}
		return(s);		
	}
	
	/**
	 * Convert hebrew allow deny values.
	 * @param value the value of the hebrew String.
	 * @return the converted string.
	 */
	public static String cvtAllowDeny(String value)
	{
		String s = "";
		if(null == value || value.length() < 1) {
			return(s);	
		}
		char c = s.charAt(0);
		switch(c) {
			case 0x8E:
				s = "î";
				break;
			case 0x80:
				s = "à";
				break;
			default:
				s = "î";
				break;
		}
		return(s);
		
	}	

	/**
	 * Convert a letter to its mataf
	 * group representation.
	 * @param val a String representing a mataf field
	 * group.
	 * @return String the group name in the 
	 * Workbench.
	 */	 	 
	public static String cvtGroup(String val)
	{
		if(val.length() == 0) {
			return("Other");
		}
		char groupChar = val.charAt(0);
		String grp = null;
		switch(groupChar) {
			case 'T':
				grp = "Teller";
				break;
			case 'B':
				grp = "Tashtit";
				break;
			case 'G':
				grp ="Global";
				break;
			case 'D':
				grp = "Shivuk";
				break;
			case 'C':
				grp = "Common";
				break;
			case 'K':
				grp = "Pratim";
				break;
			case 'H':
				grp = "Halvaa";
				break;
			case 'L':
				grp = "EmdatLakoach";
				break;
			case 'M':
				grp = "Matach";
				break;
			case 'N':
				grp = "NiarotErech";
				break;
			case 'O':
				grp = "Cheshbon";
				break;
			case 'P':
				grp = "Pikdonot";
				break;
			case 'S':
				grp = "Queries";
				break;
			case 'A':
				grp = "Tiful";
				break;
			case 'X':
				grp = "Other";
				break;
			default:
				grp = "Other";
				break;
				
		}
		return(grp);	
	}
		
	

}
