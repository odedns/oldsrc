package mataf.desktop.beans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.StringTokenizer;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DesktopUtils {

	/**
	 * Constructor for DesktopUtils.
	 */
	public DesktopUtils() {
		super();
	}
	
	public static Color colorTokenizer(Object obj) {
		String colorStr;
		int red,green,blue ;
		colorStr=(String)obj ; 
		StringTokenizer st = new StringTokenizer(colorStr,",") ;
		red  = Integer.parseInt(st.nextToken());
   		green= Integer.parseInt(st.nextToken());
   		blue = Integer.parseInt(st.nextToken());
		return new Color(red,green,blue) ;  
	}
	
	public static Dimension getComponentSize(Object size) {
		String sizeStr;
		int width,height ;
		sizeStr=(String)size ; 
		StringTokenizer st = new StringTokenizer(sizeStr,",") ;
		width  = Integer.parseInt(st.nextToken());
   		height= Integer.parseInt(st.nextToken());
   		
		return new Dimension(width,height);  
	}
		
	/**
	 * Set the Font of the component from the desktop XML Definitions file
	 */
	public static Font setComponentFont(Object obj)	{
    	String poalimFont, fontType;
    	int size, style =0 ;
    	String styleobj ;
		poalimFont=(String)obj ; 
		StringTokenizer st = new StringTokenizer(poalimFont,",") ;
		fontType = st.nextToken() ;
		
		styleobj = st.nextToken() ;
		if (styleobj.toLowerCase().equals("plain")){
			style = Font.PLAIN ;	
		}else if (styleobj.toLowerCase().equals("bold")){
			style = Font.BOLD ;
		}else if (styleobj.toLowerCase().equals("italic")){
			style = Font.ITALIC ;
		}else if (styleobj.toLowerCase().equals("bold italic")){
			style = Font.ITALIC + Font.BOLD ;
		}		
		
	
		size   = Integer.parseInt(st.nextToken());		
		return new java.awt.Font(fontType,style,size) ;
	}

}
