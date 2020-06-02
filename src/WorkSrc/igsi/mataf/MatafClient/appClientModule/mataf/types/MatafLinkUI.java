/*
 * Created on 12/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.types;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.ocean.OceanLabelUI;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafLinkUI extends OceanLabelUI
{

	private static final Color		BLUE_COLOR 	= Color.BLUE;
	private static final Color		DISABLED_COLOR = UIManager.getColor("Button.disabledText");

	/**
	 * 
	 */
	public MatafLinkUI()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicLabelUI#paintDisabledText(javax.swing.JLabel, java.awt.Graphics, java.lang.String, int, int)
	 */
	protected void paintDisabledText(
		JLabel arg0,
		Graphics arg1,
		String arg2,
		int arg3,
		int arg4)
	{
		paintEnabledText(arg0,arg1,arg2,arg3,arg4);
	}

	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicLabelUI#paintEnabledText(javax.swing.JLabel, java.awt.Graphics, java.lang.String, int, int)
	 */
	protected void paintEnabledText(
		JLabel l,
		Graphics g,
		String s,
		int textX,
		int textY)
	{
		char szText[]=s.toCharArray();
		int iTextHeight=0;
		int iTextWidth=g.getFontMetrics().charsWidth(szText,0,szText.length);
		
		Color clrOld=g.getColor();
		
		if(!l.isEnabled())
			g.setColor(DISABLED_COLOR);
		else
			g.setColor(BLUE_COLOR);
		BasicGraphicsUtils.drawString(g,s,0,textX,textY);
		g.drawLine(textX,textY+iTextHeight+1,textX+iTextWidth,textY+iTextHeight+1);
		g.setColor(clrOld);
		System.out.println("paintEnabledText");
	}

}
