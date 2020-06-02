package mataf.ui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * UI for the PoalimButton class.
 * 
 * PENDING : Check the use of inheritance directly from BasicButtonUI 
 * 			 and write the focus rendering here.
 * 
 * @author Nati Dykshtein
 * Created : 28/04/2003 15:12:17
 */
public class MatafButtonUI extends MetalButtonUI
{		
	/** Predefined button colors. */		
	private static final Color	DISABLED_TEXT_COLOR;	
	private static final Color	DARK_ORANGE;
	private static final Color	ORANGE;
	private static final Color	LIGHT_ORANGE;
	private static final Color	DARKER_GRAY;	
	private static final Color	DARK_GRAY;
	private static final Color	GRAY;
	private static final Color	LIGHT_GRAY;	
	
	private ButtonModel 	model;
	private AbstractButton button;
	
	static
	{			
		DISABLED_TEXT_COLOR = new Color(230, 231, 232);
		DARK_ORANGE			= new Color(229 , 141, 88);
		ORANGE 				= new Color(248, 172, 126);
		LIGHT_ORANGE 		= new Color(221, 182, 158);
		DARKER_GRAY			= new Color(67 , 67 , 67 );
		DARK_GRAY 			= new Color(122, 122, 122);
		GRAY 				= new Color(174, 184, 198);
		LIGHT_GRAY 			= new Color(207, 207, 207);
	}
	
	public MatafButtonUI(ButtonModel model,AbstractButton button)
	{
		this.model = model;
		this.button = button;
	}
	/**
	 * Returns the background color according to the button's state.
	 */
	private Color getBGColor()
	{
		if(!model.isEnabled())
			return GRAY;
		if(button.isRolloverEnabled() && model.isRollover())
			return DARK_ORANGE;
		
		return ORANGE;
	}
	
	/**
	 * Draw button's background color.
	 */
	private void drawBackground(Graphics g, Dimension size)
	{		
		g.setColor(getBGColor());
		g.fillRect(0, 0, size.width, size.height);
	}
	
	private Color getOuterLeftCornerColor()
	{
		if(button.isRolloverEnabled() && model.isRollover())
			return LIGHT_ORANGE;
		if(model.isArmed() && model.isPressed())
			return DARK_GRAY;

		return LIGHT_GRAY;
	}
	
	private Color getInnerLeftCornerColor()
	{		
		return DARKER_GRAY;		
	}
	
	private Color getOuterRightCornerColor()
	{		
		if(model.isArmed() && model.isPressed())
			return LIGHT_ORANGE;
		return DARK_GRAY;
	}
	
	private Color getInnerRightCornerColor()
	{		
		return DARKER_GRAY;		
	}	
	
	
	/**
	 * Draws the button's text.
	 * Method sends a different mnemonicIndex to support Right-To-Left
	 * Component Orientation.
	 */
	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text)
	{
		g.setColor(model.isEnabled() ? b.getForeground() :
										DISABLED_TEXT_COLOR);
		
		// Move text when button is pressed.
		int pressedOffset = (model.isArmed() && model.isPressed()) ? 1 : 0;
			
		// Add text pressed offset.
		textRect.x+=pressedOffset;
		textRect.y+=pressedOffset;
		
		
		ButtonModel model = b.getModel();
   	    FontMetrics fm = g.getFontMetrics();
   	    g.setFont(button.getFont());
   	    // Consider component orientaion.
   	    int mnemonicIndex = b.getComponentOrientation()==ComponentOrientation.RIGHT_TO_LEFT ?
		   	    (text.length()-1) - b.getDisplayedMnemonicIndex() :
		   	    b.getDisplayedMnemonicIndex();
		   	    			
		// Draw the Text
		if(model.isEnabled()) {
		    // Paint the text normally.
		    g.setColor(b.getForeground());
		    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text, mnemonicIndex,
						  textRect.x + getTextShiftOffset(),
						  textRect.y + fm.getAscent() + getTextShiftOffset());
		}
		else 
		{
		    // Paint the text disabled.
		    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text, mnemonicIndex,
						  textRect.x, textRect.y + fm.getAscent());
		}
	}
	
	
	/**
	 * Delegates the rendering to the specific methods.
	 */
	public void paint(Graphics g, JComponent c)
	{
		AbstractButton b = (AbstractButton) c;
		Dimension size = b.getSize();		
		
		if(button.isContentAreaFilled())
		{
			drawBackground(g, size);
			paint3DEffect(g, size);
		}
		
		super.paint(g, c);
	}
	
	/** 
	 * Draw the button with effect of raised or lowered.
	 * The lines are drawn in the following order :
	 * Top, Left, Bottom, Right.
	 */
	private void paint3DEffect(Graphics g, Dimension size)
	{		
		int x  = size.width-1;
		int y  = size.height-1;
		
		// Top
		g.setColor(getOuterLeftCornerColor());
		g.drawLine(0, 0, x-1, 0);

		
		// Left
		g.setColor(getOuterLeftCornerColor());
		g.drawLine(0, 1, 0, y-1);

		
		// Bottom
		if(!(model.isArmed() && model.isPressed()))
		{		
			g.setColor(getInnerRightCornerColor());
			g.drawLine(1, y-1, x-1, y-1);
		}
		g.setColor(getOuterRightCornerColor());
		g.drawLine(0, y, x, y);
		
		
		// Right
		g.setColor(getOuterRightCornerColor());
		g.drawLine(x, y-1, x, 0);
		if(!(model.isArmed() && model.isPressed()))
		{
			g.setColor(getInnerRightCornerColor());
			g.drawLine(x-1, y-1, x-1, 1);
		}
	}
	
	protected void paintButtonPressed(Graphics g, AbstractButton b) 
	{
  		// Overrides MetalButtonUI behavior to do nothing.
  		// Behavior is already defined in PoalimButtonUI's 
  		// paint3DEffect() method.
  		
  		int x  = b.getSize().width-1;
		int y  = b.getSize().height-1;
		
		// Top
		g.setColor(getOuterLeftCornerColor());
		g.drawLine(0, 0, x-1, 0);
		g.setColor(getInnerLeftCornerColor());
		g.drawLine(1, 1, x-1, 1);
		
		// Left
		g.setColor(getOuterLeftCornerColor());
		g.drawLine(0, 1, 0, y-1);
		g.setColor(getInnerLeftCornerColor());
		g.drawLine(1, 2, 1, y-1);
		
		
		// Bottom		
		g.setColor(getOuterRightCornerColor());
		g.drawLine(0, y, x, y);
		
		// Right
		g.setColor(getOuterRightCornerColor());
		g.drawLine(x, y-1, x, 0);
	}
	
}
