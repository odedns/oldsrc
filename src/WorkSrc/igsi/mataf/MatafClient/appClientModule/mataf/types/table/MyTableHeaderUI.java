package mataf.types.table;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicLabelUI;
import javax.swing.plaf.ocean.OceanLabelUI;

import mataf.utils.FontFactory;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (24/05/2004 19:40:11).  
 */
public class MyTableHeaderUI extends BasicLabelUI
{
	
	static
	{
		UIManager.put("MyTableHeader.background", new Color(159,183,207));
		UIManager.put("MyTableHeader.foreground", Color.white);
		UIManager.put("MyTableHeader.font", FontFactory.createFont("Arial", Font.PLAIN, 14));
	}

	/**
	 * 
	 */
	public MyTableHeaderUI()
	{
		super();		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicLabelUI#installDefaults(javax.swing.JLabel)
	 */
	protected void installDefaults(JLabel c)
	{
		LookAndFeel.installColorsAndFont(c, "MyTableHeader.background", "MyTableHeader.foreground", "MyTableHeader.font");
	}
}
