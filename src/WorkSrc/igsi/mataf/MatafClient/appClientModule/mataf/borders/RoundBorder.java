package mataf.borders;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

/** 
 * A customizable round border.
 * 
 * @author Nati Dykstein.
 */
public	class RoundBorder extends AbstractBorder
{		
	private Color  borderColor;
	private int	borderWidth;
	private int	arcDiameter;
	
	
	public RoundBorder()
	{
		this(Color.red, 1, 5);
	}
	
	public RoundBorder(Color borderColor)
	{
		this(borderColor, 1 , 5);
	}
	
	public RoundBorder(Color borderColor, int borderWidth)
	{
		this(borderColor, borderWidth, 5);
	}
	
	public RoundBorder(Color borderColor, int borderWidth,int arcDiameter)
	{
		this.borderColor = borderColor;
		this.borderWidth = borderWidth;
		this.arcDiameter = arcDiameter;
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y,
								int width, int height)
	{
		Graphics2D g2 = (Graphics2D)g;
		Dimension size = c.getSize();
			
		g2.setColor(borderColor);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(borderWidth));
		g2.drawRoundRect(borderWidth-1, borderWidth-1,
						(int)(size.getWidth())  - borderWidth,
						(int)(size.getHeight()) - borderWidth,
						arcDiameter,
						arcDiameter);
	}
	
	/**
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(Component)
	 */
	public Insets getBorderInsets(Component c) 
	{
		int i = getBorderWidth();
		return new Insets(i,i,i,i);
	}
	
	/**
	 * Returns the arcDiameter.
	 * @return int
	 */
	public int getArcDiameter() {
		return arcDiameter;
	}

	/**
	 * Returns the borderColor.
	 * @return Color
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * Returns the borderWidth.
	 * @return int
	 */
	public int getBorderWidth() {
		return borderWidth;
	}

	/**
	 * Sets the arcDiameter.
	 * @param arcDiameter The arcDiameter to set
	 */
	public void setArcDiameter(int arcDiameter) {
		this.arcDiameter = arcDiameter;
	}

	/**
	 * Sets the borderColor.
	 * @param borderColor The borderColor to set
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * Sets the borderWidth.
	 * @param borderWidth The borderWidth to set
	 */
	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}

}