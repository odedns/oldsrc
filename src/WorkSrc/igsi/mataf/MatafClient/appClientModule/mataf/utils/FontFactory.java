package mataf.utils;

import java.awt.Font;
import java.util.Hashtable;

/**
 *
 * Class creates fonts following the Singleton design pattern.
 * 
 * @author Nati Dykstein. Creation Date : (11/08/2003 18:14:00).  
 */
public class FontFactory
{

	private static Hashtable 	fontsTable   = new Hashtable();
	private static Font 		DEFAULT_FONT = new Font("Arial",Font.PLAIN,14);
	
	private FontFactory(){/** Prevent Instancetiation.*/}
	
	/**
	 * Returns the desired font.
	 * If font does not exists it creates it.
	 */
	public static synchronized Font createFont(String family, int type, int size)
	{
		// Create the unique key for this font.
		String key = family+"-"+type+"-"+size;
		
		// Try to get it from our fonts cache.
		Font f = (Font)fontsTable.get(key);
		
		// A new font was requested.
		if(f==null)
		{
			// Create the font.
			f = new Font(family, type, size);
			// Put it in out fonts cache.
			fontsTable.put(key,f);
		}
		return f;
	}
	
	/**
	 * Returns the application default font type.
	 */
	public static synchronized Font getDefaultFont()
	{
		return DEFAULT_FONT;
	}
}

