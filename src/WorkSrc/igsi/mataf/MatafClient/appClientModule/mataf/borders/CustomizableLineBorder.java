package mataf.borders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 *	A line border with customizable width of each side.
 * 
 * @author Nati Dykstein. Creation Date : (02/07/2003 15:42:30).  
 */
public class CustomizableLineBorder extends AbstractBorder 
{
	private	int top;	
	private	int left;
	private	int bottom;
	private	int right;
	
	private	Color color;
	
	public CustomizableLineBorder()
	{
		this(Color.black, 1, 1, 1, 1);
	}
	
	public CustomizableLineBorder(Color color)
	{
		this(color, 1, 1, 1, 1);
	}
	
	public CustomizableLineBorder(int top, int left, int bottom, int right)
	{
		this(Color.black, top, left, bottom, right);
	}
	
	public CustomizableLineBorder(Color color, int top, int left, int bottom, int right)
	{	
		this.color = color;
	
		this.top 	 = top;	
		this.left 	 = left;
		this.bottom = bottom;
		this.right  = right;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y,
								int width, int height)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(color);

		// Top
		if(top>0)
		{
			g2.setStroke(new BasicStroke(top));
			g2.drawLine(0,0,c.getWidth(),0);
		}
		
		// Left
		if(left>0)
		{
			g2.setStroke(new BasicStroke(left));
			g2.drawLine(0,0,0,c.getHeight());
		}
		
		// Bottom
		if(bottom>0)
		{
			g2.setStroke(new BasicStroke(bottom));
			g2.drawLine(0,c.getHeight(),c.getWidth(),c.getHeight());
		}
		
		// Right
		if(right>0)
		{
			g2.setStroke(new BasicStroke(right));
			g2.drawLine(c.getWidth(),0,c.getWidth(),c.getHeight());
		}		
	}

	/**
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(Component)
	 */
	public Insets getBorderInsets(Component c) 
	{
		return new Insets(top, left, bottom, right);
	}
}
