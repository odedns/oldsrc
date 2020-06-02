package tests;


/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (08/02/2004 13:11:15).  
 */
public class SBTest
{
	public static void main(String[] args)
	{
		StringBuffer sb = new StringBuffer();
		System.out.println("Java Version ="+System.getProperty("java.version"));
		String t = "TEST2";
		sb.append("TEST1");

		sb.append('.');

		sb.append(t);
		System.out.println("sb="+sb.append(".").append("Test3"));
	}
}
