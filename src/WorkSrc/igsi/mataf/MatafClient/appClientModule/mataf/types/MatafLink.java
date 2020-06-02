package mataf.types;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.LabelUI;

import mataf.utils.FontFactory;

import com.ibm.dse.desktop.Desktop;


/** 
 * @author Nati Dykshtein & R.K.
 * 
 * <code>MatafLink</code> is a custom Button
 * that L&F like a link.
 */


public class MatafLink extends MatafLabel
{
	private static final	Cursor 		HAND_CURSOR;
	private static final  Cursor	    DEFAULT_CURSOR = Cursor.getDefaultCursor();
	private static final	Font		FONT;

	private static final Color		BLUE_COLOR 	= Color.BLUE;
	private static final Color		DISABLED_COLOR = UIManager.getColor("Button.disabledText");

	static 
	{
		HAND_CURSOR		= Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		FONT 			= FontFactory.createFont("Arial",Font.BOLD,14);
	}	
	
	public MatafLink()
	{
		super("רכיב קישור");
		initComponent(false, false);
	}
	
	public MatafLink(String text)
	{
		super(text);
		initComponent(false, false);
	}
	/**
	 * @param text
	 * @param alignment
	 */
	public MatafLink(String text, int alignment)
	{
		super(text, alignment);
		initComponent(false, false);
	}
	
	private void initComponent(boolean withIcon, boolean isDataExchanger)
	{
		setUI(new MatafLinkUI());
		setCursor(HAND_CURSOR);
		setForeground(BLUE_COLOR);
		setFont(FONT);
		
		/**
		 * Used to change cursor when mouse is over the link,
		 * and to activate the operation when clicking it.
		 */
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(isEnabled())
				{
					fireCoordinationEvent();
					repaint();
				}
			}

			public void mouseEntered(MouseEvent e)
			{
				if(isEnabled())
					Desktop.getFrame().setCursor(HAND_CURSOR);		
			}

			public void mouseExited(MouseEvent e)
			{
				if(isEnabled())
					Desktop.getFrame().setCursor(DEFAULT_CURSOR);
			}
		});
		
	}
	
	/**
	 * 
	 */
	public void setForeground(Color fg)
	{		
		super.setForeground( isEnabled() ? BLUE_COLOR : DISABLED_COLOR);
	}
	
/////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////// U s e r     I n t e r f a c e ///////////////////////////
/////////////////////////////////////////////////////////////////////////////////////	
	
	/* (non-Javadoc)
	 * @see javax.swing.JLabel#setUI(javax.swing.plaf.LabelUI)
	 */
	public void setUI(LabelUI ui)
	{
		// TODO Auto-generated method stub
		super.setUI(new MatafLinkUI());
	}

}

