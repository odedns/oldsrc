package mataf.types.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.ibm.dse.gui.ColumnFormatter;

/**
 * 
 * @author Nati Dykstein. Creation Date : (21/09/2003 13:48:09).  
 */
public class MatafTableCellRenderer extends DefaultTableCellRenderer 
{
	private static final Color TABLE_BG_COLOR = new Color(235, 235, 235);
	
	/** Combo-box's down arrow image.*/
	private static final ImageIcon 	ARROW_IMAGE;
	
	private MatafTable 	 table;
	private ColumnFormatter converter;
	
	static
	{
		ARROW_IMAGE				= new ImageIcon("downArrow.gif");
	}
	
	public MatafTableCellRenderer(MatafTable table) 
	{	
		this.table = table;
	}

	/**
	 * Constructor for MatafTableCellRenderer.
	 * @param aConverter
	 */
	public MatafTableCellRenderer(ColumnFormatter converter, MatafTable table) 
	{
		this.converter = converter;
		this.table = table;
	}
	
	/**
	 * Returns the default renderer as suggeseted by JTable class with
	 * some modifications.
	 */	
	public Component getTableCellRendererComponent(JTable table,Object value,
													boolean isSelected,
													boolean hasFocus,
													int row,int column) 
	{
		// Get the column class.
		Class columnClass = table.getModel().getColumnClass(column);

		// Get the default renderer for this class.
		TableCellRenderer tRenderer = table.getDefaultRenderer(columnClass);

		// Get the default renderer for this class.
		JComponent renderer = (JComponent)
			tRenderer.getTableCellRendererComponent(
												table,
												value,
												isSelected, 
												hasFocus, 
												row,
												column);

		
		// Get the table.
		MatafTable mTable = (MatafTable)table;
		
		// Add our behavior to the default renderer.
		if(renderer instanceof JLabel)
		{
			JLabel labelRenderer = ((JLabel)renderer);
	//		if(table.getComponentOrientation().isLeftToRight())
	//			labelRenderer.setHorizontalAlignment(SwingConstants.LEFT);
	//		else
				labelRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
			
			// We're rendering the MatafComboTextField class.
		/*	if(columnClass.equals(MatafComboTextField.class))
			{
				String text = labelRenderer.getText();
				if(text.trim().equals(""))
				{
					labelRenderer.setHorizontalAlignment(SwingConstants.CENTER);
					labelRenderer.setText("F4 לבחירה");
				}
			}*/
		}
		
												
		if(mTable.isCellInError(row, column))
			renderer.setBorder(BorderFactory.createLineBorder(Color.red));
		else
			if(hasFocus)
			{
				renderer.setBorder(BorderFactory.createLineBorder(Color.yellow));
			}
			else
			{
				renderer.setBorder(null);
			}

		// Fill odd and even rows with different backgrounds.
		if(!isSelected)
			renderer.setBackground( (row % 2)==0 ? Color.white : TABLE_BG_COLOR);
			
		if(hasFocus)
			renderer.setBackground(Color.white);

		return renderer;
	}
}
