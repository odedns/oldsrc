package mataf.types.table;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import mataf.utils.FontFactory;

/**
 * This is the default header renderer for the MatafTable class.
 *
 * PENDING : Create a custom OceanTableHeaderUI, and add it to the
 * 				OceanLookAndFeel.
 *  
 * @author Nati Dykstein. Creation Date : (03/02/2004 15:10:14).  
 */
public class MatafTableHeaderRenderer extends DefaultTableCellRenderer
{
	//private static final Color TABLE_HEADER_BACKGROUND_COLOR;
	//private static final Color TABLE_HEADER_FOREGROUND_COLOR;
	//private static final Font  TABLE_HEADER_FONT;
	
	static
	{
		
		
		//TABLE_HEADER_BACKGROUND_COLOR = new Color(159,183,207);
		//TABLE_HEADER_FOREGROUND_COLOR = Color.white;
		//TABLE_HEADER_FONT = FontFactory.createFont("Arial", Font.PLAIN, 14); 
	}
	
	public MatafTableHeaderRenderer()	
	{
		//setFont(TABLE_HEADER_FONT);
		setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		//setBackground(TABLE_HEADER_BACKGROUND_COLOR);
		//setForeground(TABLE_HEADER_FOREGROUND_COLOR);
	}
}
