package tests;

import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (30/10/2003 14:45:45).  
 */
public class SpeedTest 
{
	public static long NUMBER_OF_ELEMENTS = 400000;
	
	private static Vector v = new Vector();

	/**
	 * Constructor for SpeedTest.
	 */
	public static void fillVector() 
	{
		for(int i=0;i<NUMBER_OF_ELEMENTS;i++)
			v.add(new Integer(i));
	}
	
	public static void enumerateVector()
	{
		long t = System.currentTimeMillis();		
		for(Enumeration e = v.elements();e.hasMoreElements();e.nextElement())
			;
			
		System.out.println("Enumeration Time Taken : "+ (System.currentTimeMillis()-t)+"ms" );
	}
	
	public static void iterateVector()
	{
		long t = System.currentTimeMillis();
		int size = v.size();		
		for(int i=0;i<size;i++)
			;
		System.out.println("For-Iteration Time Taken : "+ (System.currentTimeMillis()-t)+"ms" );
	}


	public static void main(String[] args)
	{
		System.out.print("Creating Vector with "+NUMBER_OF_ELEMENTS+" elements...");
		fillVector();
		System.out.println("Done.");
		iterateVector();
		enumerateVector();
	}
}
