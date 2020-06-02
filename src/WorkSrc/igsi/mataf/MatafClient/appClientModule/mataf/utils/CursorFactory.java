package mataf.utils;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import com.ibm.dse.desktop.Desktop;

import mataf.dse.appl.OpenDesktop;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CursorFactory {
	
	public static final String ICON_STAMP_CURSOR = "icon_stamp";
	public static final String ICON_CLOCK_CURSOR = "icon_clock";
	private static final String CURSORS_DIR = "images/icons/";
	private static HashMap m_map = new HashMap();

	/**
	 * cannot instantiate.
	 */
	private CursorFactory()
	{
	}

	/**
	 * try to get a cursor from the HashMap.
	 * If no found create a custom cursor and store it 
	 * in the HashMap.
	 * @param name the name of the cursor.
	 * @return Cursor the custom cursor created.
	 */
	public static Cursor	getCursor(String name)
	{
		Cursor curs = (Cursor) m_map.get(name);
		if(null == curs) {
			Image curImage = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource(CURSORS_DIR + name + ".gif"));			
			Dimension dim = Toolkit.getDefaultToolkit().getBestCursorSize(0,0);
			Point pt = new Point((int) dim.getHeight()-10, (int)dim.getWidth()-10);
			curs = Toolkit.getDefaultToolkit().createCustomCursor(curImage,pt,name);
			m_map.put(name,curs);
		}
		return(curs);
	}
	
	
	/*
	 * main test
	 */
	public static void main(String[] args) {
		
		Frame f = new Frame();
		f.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {System.exit(0);}
	    });
		f.setSize(400,400);
		f.show();
		Cursor curs = getCursor(ICON_STAMP_CURSOR);
		f.setCursor(curs);	
	}
}
